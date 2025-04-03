package stepdefinitions;

import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

import static org.junit.Assert.assertTrue;

@Slf4j
public class PageFeatures {
    private static WebDriver driver;

    @Before
    public static void pageReportes() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://f8sc00008/Reports");
    }

    public static void pagesetParameter(String parameterName, String parameterValue) {
        WebElement propetiers = driver.findElement(By.cssSelector("data-parametername"));
        log.info("pageSetParameter: {}", propetiers.toString());
    }

    public static void pageReportDesktop() {
        // Validación de la descarga (esto puede variar según la configuración de tu navegador)
        File downloadedFile = new File("C:/Downloads/CONSULTA_CLAI.csv");  // Verifica la carpeta de descargas
        //assert downloadedFile.exists();  // Asegúrate de que el archivo se haya descargado
        assertTrue(downloadedFile.exists());  // Asegúrate de que el archivo haya sido descargado
    }

    public static void pagedirectories(String directoryName) throws InterruptedException {
        WebElement pagedirectoris = driver.findElement(By.linkText(directoryName)); //primera carpeta
        pagedirectoris.click();
        //Thread.sleep(3000);
    }
}
