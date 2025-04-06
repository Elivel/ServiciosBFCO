package tech.falabella.qa.adapter;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tech.falabella.qa.port.IngestionPort;
import tech.falabella.qa.report.Tuple;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Getter
@RequiredArgsConstructor
public class ScrapingIngestionAdapter<T extends Tuple> implements IngestionPort {

    private final String uriReport;
    private final Map<String, String> parameters;
    private final Path output;
    private final char separator;
    private final boolean skipHeader;
    private final Function<String[], T> aMapFun;

    @Override
    public void generate() {
        var driver = new ChromeDriver();
        driver.get(uriReport);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement iframeReport = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe.viewer[ng-show='!failureReason']")));
        driver.switchTo().frame(iframeReport);

        parameters.entrySet().parallelStream().forEach(param -> {
            WebElement container = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-parametername=\"" + param.getKey() + "\"] input")));

            if (container != null) {
                container.clear();
                container.sendKeys(param.getValue());
            }
        });

        WebElement verInforme = wait.until(ExpectedConditions.elementToBeClickable(By.name("ReportViewerControl$ctl04$ctl00")));
        verInforme.click();

        WebElement btndescarga = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ReportViewerControl_ctl05_ctl04_ctl00_ButtonLink")));
        btndescarga.click();

        WebElement optioncsv = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[title=\"CSV (delimitado por comas)\"]")));
        optioncsv.click();

        // ToDo: read last download and move file to output
    }

    @Override
    public Collection<T> getData() {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withIgnoreQuotations(false)
                .withQuoteChar('"')
                .build();

        try (Reader reader = Files.newBufferedReader(output);
             CSVReader csvReader = new CSVReaderBuilder(reader)
                     .withSkipLines(skipHeader ? 1 : 0)
                     .withCSVParser(parser)
                     .build()) {
            return csvReader.readAll().parallelStream().map(aMapFun).toList();
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }
        return List.of();
    }
}
