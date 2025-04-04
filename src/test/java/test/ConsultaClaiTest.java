package test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;


public class ConsultaClaiTest {
    private static WebDriver driver;


    public void testGoogleSearch() throws InterruptedException {
        PageTest.google();

    }

    public void testConsultaClai() throws InterruptedException {
        PageTest.pageReportes();
        PageTest.pageConsultaClai();

    }

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
