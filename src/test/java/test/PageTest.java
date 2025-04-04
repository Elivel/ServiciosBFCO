package test;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    public static void pageReportes()throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://f8sc00008/Reports");
        Thread.sleep(10000);
    }

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nroAutorizacionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("ReportViewerControl_ctl04_ctl05_txtValue")));
        //WebElement nroAutorizacionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nroAutorizacion")));
        nroAutorizacionInput.click();
        nroAutorizacionInput.clear();
        nroAutorizacionInput.sendKeys("399300");
        nroAutorizacionInput.submit();


    }

    public static void mastercardReport() throws InterruptedException {
        WebElement mastercard = driver.findElement(By.linkText("Mastercard")); //primera carpeta
        mastercard.click();
        Thread.sleep(3000);

    }

    public static void clearingReport() throws InterruptedException {
        WebElement detalladoEvento = driver.findElement(By.linkText("Daily operation"));
        detalladoEvento.click();
        Thread.sleep(2000);
        WebElement reportClearingPMD = driver.findElement(By.linkText("CLEARING (PMD)"));
        reportClearingPMD.click();
        //no encuentra el elemento
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement fecha = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label/span[text()='Fecha Proceso']")));
       // WebElement fecha = driver.findElement(By.xpath("//label/span[text()='Fecha Proceso']"));
        fecha.click();
        fecha.clear();

    }

    public static void detalladoMCCAReport() throws InterruptedException {
        WebElement  Billing = driver.findElement(By.linkText("Billing")); //primera carpeta
        Billing.click();
        Thread.sleep(5000);
        WebElement detalladoEvento = driver.findElement(By.linkText("DETALLADO POR EVENTO MCCA")); //primera carpeta
        detalladoEvento.click();
        Thread.sleep(3000);
        WebElement selectElement = driver.findElement(By.id("ReportViewerControl_ctl04_ctl05_ddValue"));
        Select select = new Select(selectElement);
        select.selectByIndex(6);

    }
    public static void popupLoginDate(){
        // Paso 2: El usuario ingresa el nombre de usuario y la contraseña
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // timer modal
        //WebElement loginPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Nombre de usuario')]"))); // Esperamos hasta que el texto "Nombre de usuario" esté visible
        WebElement usernameField = driver.findElement(By.xpath("//label[contains(text(),'Nombre de usuario')]/following-sibling::input"));
        WebElement passwordField = driver.findElement(By.xpath("//label[contains(text(),'Contraseña')]/following-sibling::input"));

        usernameField.sendKeys("elvelasquezl@bancofalabella.com.co");
        passwordField.sendKeys("Colombia2024@2025.");
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(),'Acceder')]"));
        loginButton.click();  // Hacer clic en el botón de acceso
        // passwordField.submit();  // Enviar formulario de login
        // Opcional: Esperar a que la página cargue después de login, si es necesario.
        wait.until(ExpectedConditions.urlContains("http://f8sc00008/Reports/browse/"));
        // valida que se visualice la pagina de reportes
        WebElement reportPageElement = driver.findElement(By.id("reportsPage"));  // Elemento visible de la página de reportes
        assert reportPageElement.isDisplayed();
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Close the browser window
        }
    }
}
