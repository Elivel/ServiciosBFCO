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
import org.junit.jupiter.api.*;


public class ConsultaClaiTest {
    private static WebDriver driver;

    @Test
    public void testGoogleSearch() throws InterruptedException {
        PageTest.google();

    }
    @Test
    public void testConsultaClai() throws InterruptedException {
        PageTest.pageReportes();
        PageTest.pageConsultaClai();


    }
    @Test
    public void testDetallado() throws InterruptedException{
        PageTest.pageReportes();
        Thread.sleep(10000);
        PageTest.mastercardReport();
        PageTest.detalladoMCCAReport();




    }
    @Test
    public void testClearing () throws InterruptedException {
        PageTest.pageReportes();
        Thread.sleep(3000);
        PageTest.mastercardReport();
        Thread.sleep(3000);
        PageTest.clearingReport();



    }
}
