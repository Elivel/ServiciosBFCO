package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PageTest {
    private static WebDriver driver;

    public static void google () throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.bing.com/");
        Thread.sleep(5000);
        PageTest.google();
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("ChromeDriver");
        searchBox.submit();
        Thread.sleep(5000);  // Let the user actually see something!
        driver.quit();

    }
    @BeforeAll
    public static void pageReportes()throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://f8sc00008/Reports");
        Thread.sleep(10000);
    }
    @After
    public static void pageConsultaClai()throws InterruptedException{
        WebElement carpconciliacion = driver.findElement(By.linkText("Conciliacion_Liquidacion")); //primera carpeta
        carpconciliacion.click();
        // Hacer scroll hacia abajo
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        // Esperar a que cargue la página (opcional, si se necesita)
        try {
            Thread.sleep(2000);  // No es lo más óptimo, mejor usar WebDriverWait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement reportconsultacali = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div/section[2]/tiles-view/section/div/div/div/ul/li[25]/report-tile/rs-tile/a[1]")); //reporte
        reportconsultacali.click();
        Thread.sleep(5000);
        WebElement inputNroAutorizacion = driver.findElement(By.name("ReportViewerControl$ctl04$ctl05$txtValue"));

        if (inputNroAutorizacion.isEnabled()) {
            inputNroAutorizacion.click();
            inputNroAutorizacion.sendKeys("399300");
        } else {
            System.out.println("El campo de entrada está deshabilitado.");
        }

    }
    public static void mastercardReport() throws InterruptedException {
        WebElement mastercard = driver.findElement(By.linkText("Mastercard")); //primera carpeta
        mastercard.click();
        Thread.sleep(3000);

    }
}
