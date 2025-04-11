package tech.falabella.qa.adapter;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import tech.falabella.qa.port.IngestionPort;
import tech.falabella.qa.report.Parameters;
import tech.falabella.qa.report.Tuple;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@Getter
@RequiredArgsConstructor
public class ScrapingIngestionAdapter<T extends Tuple> implements IngestionPort {

    private final String uriReport;
    private final Map<String, Parameters.Value> parameters;
    private final String output;
    private final char separator;
    private final boolean skipHeader;
    private final Function<String[], T> aMapFun;
    private File file;

    @Override
    public void generate() {
        var driver = openBrowser();

        try {
            driver.get(uriReport);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            WebElement iframeReport = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe.viewer[ng-show='!failureReason']")));
            driver.switchTo().frame(iframeReport);

            parameters.forEach((key, value) -> {
                WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-parametername=\"" + key + "\"] " + value.type)));

                if (container != null) {
                    if (value.type.equalsIgnoreCase("select")){
                        Select select = new Select(container);
                        select.selectByVisibleText(value.defaultValue); // Ejemplo
                    }
                    else {
                        container.clear();
                        container.sendKeys(value.defaultValue);
                    }

                }
            });
            //ver informe
            if (!parameters.isEmpty()) {
                WebElement verInforme = wait.until(ExpectedConditions.elementToBeClickable(By.name("ReportViewerControl$ctl04$ctl00")));
                verInforme.click();

            }


            JavascriptExecutor js = (JavascriptExecutor) driver;
            Thread.sleep(5000);
            //WebElement tablageneral = wait.until(ExpectedConditions.elementToBeClickable(By.id("ReportViewerControl_fixedTable")));

            js.executeScript("$find('ReportViewerControl').exportReport('CSV');");

            // Espera un tiempo razonable para que la descarga se complete
            Thread.sleep(5000); // Ajusta este tiempo según el tamaño del archivo y la velocidad de la red

            getLatestFile();
        } catch (Exception exception) {
            log.error("error generando el archivo. Causa: {}", exception.getMessage(), exception);
            throw new RuntimeException(exception.getMessage(), exception);
        } finally {
           driver.quit();
        }

    }

    @Override
    public Collection<T> getData() {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withIgnoreQuotations(false)
                .withQuoteChar('"')
                .build();

        try (Reader reader = Files.newBufferedReader(file.toPath());
             CSVReader csvReader = new CSVReaderBuilder(reader)
                     .withSkipLines(skipHeader ? 1 : 0)
                     .withCSVParser(parser)
                     .build()) {
            return csvReader.readAll().parallelStream()
                    .filter(line -> line.length > 0 && Stream.of(line).anyMatch(cell -> !cell.trim().isEmpty()))
                    .map(aMapFun).toList();
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }
        return List.of();
    }

    private WebDriver openBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"
            //   ,"--headless", "--disable-gpu" no abre el navegador, ejecuta prueba por debajo
                       );
        options.setExperimentalOption("prefs",
                Map.of("download.default_directory", output,
                        "profile.default_content_settings.popups", 0,
                        "download.prompt_for_download", false,
                        "download.directory_upgrade", true,
                        "safebrowsing.enabled", false));

        var driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        return driver;
    }


    private void getLatestFile() {
        File[] files = new File(output).listFiles();
        if (files == null || files.length == 0)
            return;

        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        file = files[0];
        log.info("ultimo archivo {}", file.toString());
    }
}
