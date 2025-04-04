package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

@Slf4j
public class PageFeatures {
    private static WebDriver driver;

    @Before
    public static void setUp(){
        if (driver == null) {
            driver = new ChromeDriver();
        }

    }
    public static void pageReportes()  {
        driver.get("http://f8sc00008/Reports/browse/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("Inicio - SQL Server 2019 Reporting Services"));
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Cierra el navegador después de cada escenario
        }
    }

    public static void pagesetParameter(String parameterName, String parameterValue) {
        WebElement container = driver.findElement(By.cssSelector("[data-parametername='"+parameterName+"']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
       // WebElement directoryLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(parameterName)));
        //directoryLink.click();
        log.info("pageSetParameter: {}", container.toString());
        WebElement input = container.findElement(By.tagName("input"));
        input.clear();
        input.sendKeys(parameterValue);
    }

    public static void pageReportDesktop() {
        // Validación de la descarga (esto puede variar según la configuración de tu navegador)
        File downloadedFile = new File("C:/Downloads/CONSULTA_CLAI.csv");  // Verifica la carpeta de descargas
        //assert downloadedFile.exists();  // Asegúrate de que el archivo se haya descargado
        assertTrue(downloadedFile.exists());  // Asegúrate de que el archivo haya sido descargado
    }

    public static void pagedirectories(String directoryName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement directoryLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(directoryName)));
        directoryLink.click();
        //WebElement pagedirectoris = driver.findElement(By.linkText(directoryName)); //primera carpeta
        //pagedirectoris.click();
        //Thread.sleep(3000);
    }

}
