package test;

import io.cucumber.java.After;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
@Slf4j
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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
        Thread.sleep(2000);

    }

    public static void clearingReport(String parameterName, String parameterValue) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dailyOperation = driver.findElement(By.linkText("Daily operation"));
        dailyOperation.click();
        Thread.sleep(2000);
        //click clearing PMD
        WebElement reportClearingPMD = driver.findElement(By.linkText("CLEARING (PMD)"));
        reportClearingPMD.click();
// Esperar hasta que el iframe sea visible
        WebElement iframeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe.viewer[ng-show='!failureReason']")));
        if (iframeElement.isDisplayed()) {
            System.out.println("El iframe principal es visible.");
            driver.switchTo().frame(iframeElement); // Switch to iframe using WebElement
        } else {
            System.out.println("El iframe principal no es visible.");
            return; // Salir si no es visible
        }

        // Segundo Iframe
// Esperar que el iframe secundario esté disponible. Verificar si el iframe es visible (en caso de que sea oculto por el estilo).
        WebElement innerIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ReportViewerControl_ctl04_ctl03_ctl02")));

        // Forzar la visibilidad del iframe si está oculto
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.display = 'block';", innerIframe);

        // Esperar hasta que el iframe secundario sea visible
        wait.until(ExpectedConditions.visibilityOf(innerIframe));
        if (innerIframe.isDisplayed()) {
            System.out.println("El iframe interno es visible.");
            driver.switchTo().frame(innerIframe); // Switch to iframe using WebElement
        } else {
            System.out.println("El iframe interno no es visible.");
            driver.switchTo().defaultContent();
            return;
        }
            // el parametro
        //WebElement container = wait.until(ExpectedConditions.elementToBeClickable(By.name("ReportViewerControl$ctl04$ctl03$txtValue")));
        // Forzar desplazamiento al campo de entrada (en caso de que no esté visible en la pantalla)
        WebElement container = wait.until(ExpectedConditions.elementToBeClickable(By.id("ReportViewerControl_ctl04_ctl03_txtValue")));
        container.clear();
        container.sendKeys(parameterValue);

       // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", container);
        //wait.until(ExpectedConditions.visibilityOf(container));
        // WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-parametername=\"" + parameterName + "\\\"] input")));
// Si el contenedor no es null, interactuar con él
          //  if (container != null) {
            //    container.clear();
              //  container.sendKeys(parameterValue);
            //} else {
             //   System.out.println("El contenedor no fue encontrado.");
            //}
// Volver al contenido principal después de trabajar dentro del iframe
            //driver.switchTo().defaultContent();
        }
    public static void parametro(String parameterName, String parameterValue) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click en "Daily operation"
        WebElement dailyOperation = driver.findElement(By.linkText("Daily operation"));
        dailyOperation.click();
        Thread.sleep(2000); // Puedes reemplazar Thread.sleep con una espera explícita si es necesario

        // Click en "CLEARING (PMD)"
        WebElement reportClearingPMD = driver.findElement(By.linkText("CLEARING (PMD)"));
        reportClearingPMD.click();

        // Esperar hasta que el primer iframe sea visible
        WebElement iframeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("iframe.viewer[ng-show='!failureReason']")));

        // Verificar si el primer iframe es visible
        if (iframeElement.isDisplayed()) {
            System.out.println("El iframe principal es visible.");
            driver.switchTo().frame(iframeElement); // Cambiar al iframe principal
        } else {
            System.out.println("El iframe principal no es visible.");
            return; // Salir si no es visible
        }

        // Esperar que el iframe secundario esté disponible
        WebElement innerIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ReportViewerControl_ctl04_ctl03_ctl02")));

        // Forzar la visibilidad del iframe si está oculto
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.display = 'block';", innerIframe);

        // Esperar hasta que el iframe secundario sea visible
        wait.until(ExpectedConditions.visibilityOf(innerIframe));

        // Verificar si el iframe interno es visible
        if (innerIframe.isDisplayed()) {
            System.out.println("El iframe interno es visible.");
            driver.switchTo().frame(innerIframe); // Cambiar al segundo iframe
        } else {
            System.out.println("El iframe interno no es visible.");
            driver.switchTo().defaultContent(); // Volver al contenido principal
            return; // Salir si no es visible
        }

        // Ahora interactuamos con el campo de texto
        // Esperar hasta que el campo de fecha sea visible y esté listo para hacer clic
        WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(By.id("ReportViewerControl_ctl04_ctl03_txtValue")));

        // Asegúrate de que el campo de texto es visible
        if (inputField.isDisplayed()) {
            System.out.println("El campo de fecha es visible y clickeable.");

            // Limpiar el campo y escribir la fecha
            inputField.clear(); // Limpiar cualquier valor previo
            inputField.sendKeys(parameterValue); // Escribir la fecha (por ejemplo: "5/4/2025")

            // También podrías enviar un "Enter" si la página lo requiere para confirmar el valor
            inputField.sendKeys(Keys.ENTER);
        } else {
            System.out.println("El campo de fecha no es visible o no es clickeable.");
        }

        // Volver al contenido principal después de interactuar con el iframe
        driver.switchTo().defaultContent();
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
