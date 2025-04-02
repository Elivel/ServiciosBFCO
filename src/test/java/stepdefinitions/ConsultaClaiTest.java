package stepdefinitions;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class ConsultaClaiTest {
    private static WebDriver driver;
    @Test
    public void login() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://f8sc00008/Reports");
        Thread.sleep(10000);
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
       // inputNroAutorizacion.click();
        //inputNroAutorizacion.sendKeys("399300");
       // WebElement btnVerInforma = driver.findElement(By.name("ReportViewerControl$ctl04$ctl00")); //btn ver informe
        //btnVerInforma.click();

    }
    @Test
    public void testGoogleSearch() throws InterruptedException {
        PageTest.google();// Let the user actually see something!

    }
    @Test
    public void test1() throws InterruptedException {
        PageTest.pageReportes();
        PageTest.pageConsultaClai();

    }
    @Test
    public void testDetallado() throws InterruptedException{
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://f8sc00008/Reports");
        Thread.sleep(10000);
        WebElement mastercard = driver.findElement(By.linkText("Mastercard")); //primera carpeta
        mastercard.click();
        Thread.sleep(3000);
        WebElement Billing = driver.findElement(By.linkText("Billing")); //primera carpeta
        Billing.click();
        Thread.sleep(5000);
        WebElement detalladoEvento = driver.findElement(By.linkText("DETALLADO POR EVENTO MCCA")); //primera carpeta
        detalladoEvento.click();
        //select parametros
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       // WebElement fecha = driver.findElement(By.id("ReportViewerControl_ctl04_ctl03_txtValue"));
       // Thread.sleep(3000);

        //WebElement fecha= wait.until(ExpectedConditions.elementToBeClickable(By.id("ReportViewerControl_ctl04_ctl03_txtValue")));
       // fecha.click();
        //fecha.sendKeys("19/7/2023");
        Thread.sleep(3000);
        WebElement selectElement = driver.findElement(By.id("ReportViewerControl_ctl04_ctl05_ddValue"));
        Select select = new Select(selectElement);
        select.selectByIndex(6);  // Selecciona la opción en la posición 3 (0 es la primera opción)



    }
    @Test
    public void testClearing () throws InterruptedException {
        PageTest.pageReportes();
        PageTest.mastercardReport();
        WebElement detalladoEvento = driver.findElement(By.linkText("Daily operation"));



    }
}
