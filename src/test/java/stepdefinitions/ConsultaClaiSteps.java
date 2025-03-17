package stepdefinitions;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConsultaClaiSteps {

    WebDriver driver;

    // Inicializar WebDriver antes de cada escenario
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:Users/elvelasquezl/Downloads/chrome-win64/chrome.exe");  // Ruta correcta del chromedriver
        driver = new ChromeDriver();  // Inicializar el driver
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    // Paso 1: El usuario accede a la página de login
    @Given("the user accesses the login page")
    public void the_user_accesses_the_login_page() {
            driver.get("http://f8sc00008/Reports/");  // URL de la página de login
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  // Espera implícita para cargar los elementos
        }

     //   driver.get("http://f8sc00008/Reports/");  // URL de la página de login
   //     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  // Espera implícita para cargar los elementos


    // Paso 2: El usuario ingresa el nombre de usuario y la contraseña
    @Given("enters the username {string} and the password {string}")
    public void enters_the_username_and_the_password(String username, String password) {
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
        wait.until(ExpectedConditions.urlContains("http://f8sc00008/Reports/browse/"));  // Cambia esto por la URL que aparece después de un login exitoso
    }

    // Paso 3: El usuario ve la página de reportes
    @When("the user views the reports page")
    public void the_user_views_the_reports_page() {
        WebElement reportPageElement = driver.findElement(By.id("reportsPage"));  // Elemento visible de la página de reportes
        assert reportPageElement.isDisplayed();  // Asegurarse de que la página de reportes esté visible
    }

    // Paso 4: El usuario consulta el reporte
    @When("queries the report {string}")
    public void queries_the_report(String reportName) {
        WebElement carpconciliacion = driver.findElement(By.linkText("Conciliacion_Liquidacion")); //primera carpeta
        carpconciliacion.click();
        WebElement reportconsultacali = driver.findElement(By.linkText("CONSULTA_CLAI")); //reporte
        reportconsultacali.click();
        WebElement inputNroAutorizacion = driver.findElement(By.name("ReportViewerControl$ctl04$ctl05$txtValue"));
        inputNroAutorizacion.sendKeys("399300");
        WebElement btnVerInforma = driver.findElement(By.id("ReportViewerControl_ctl04_ctl00")); //btn ver informe
        btnVerInforma.click();
       // inputNroAutorizacion.sendKeys(Keys.ENTER); //sino sirve el clic en el botón informe


    }

    // Paso 5: El usuario aplica los parámetros necesarios
    @When("applies the necessary parameters")
    public void applies_the_necessary_parameters() {
        WebElement btnGuardar = driver.findElement (By.id("ReportViewerControl_ctl05_ctl04_ctl00_ButtonImg"));
        btnGuardar.click();
        WebElement menuDesplegable = driver.findElement(By.className("MenuBarBkGnd"));
        Select select = new Select (menuDesplegable);
        select.selectByVisibleText("CSV (delimitado por comas)");

    }

    // Paso 6: El reporte debería ser descargado exitosamente
    @Then("the report should be downloaded successfully")
    public void the_report_should_be_downloaded_successfully() {
        // Validación de la descarga (esto puede variar según la configuración de tu navegador)
        File downloadedFile = new File("C:/Downloads/CONSULTA_CLAI.csv");  // Verifica la carpeta de descargas
        assert downloadedFile.exists();  // Asegúrate de que el archivo se haya descargado
    }

    // Limpiar recursos después de cada prueba
   // @After
   // public void closeBrowser() {
     //   driver.quit();  // Cerrar el navegador después de ejecutar las pruebas
    }


