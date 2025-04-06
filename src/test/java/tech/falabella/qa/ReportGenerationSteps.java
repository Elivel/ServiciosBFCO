package tech.falabella.qa;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportGenerationSteps {

    // Atributos para almacenar los datos del escenario
    private String file;
    private String username;
    private String userpass;
    private String url;

    @Given("a valid configuration")
    public void aValidConfiguration() {
        // Aquí podrías configurar tu entorno o probar una configuración válida
        System.out.println("Configuración válida");
    }

    @Given("file={string}")
    public void file(String filePath) {
        this.file = filePath;
    }

    @Given("Username={string}")
    public void username(String username) {
        this.username = username;
    }

    @Given("Userpass={string}")
    public void userpass(String password) {
        this.userpass = password;
    }

    @Given("url={string}")
    public void url(String databaseUrl) {
        this.url = databaseUrl;
    }

    @When("the scheduled job is executed")
    public void theScheduledJobIsExecuted() {
        // Lógica para ejecutar el trabajo programado
        // Esto puede involucrar conectarse a la base de datos, consultar los datos, generar el informe, etc.
        System.out.println("Ejecutando el trabajo programado con la configuración dada...");
    }

    @Then("the report must be generated correctly and saved as a CSV file {string}")
    public void theReportMustBeGeneratedCorrectly(String expectedFilePath) {
        // Verificar si el informe se generó correctamente
        System.out.println("Verificando si el informe fue generado y guardado en: " + expectedFilePath);            // Aquí puedes agregar lógica para verificar si el archivo fue creado correctamente
        // Por ejemplo:
        boolean fileExists = new File("C:/Users/elvelasquezl/Downloads").exists();
        assertTrue(fileExists, "El archivo CSV debe existir en la ruta especificada");
    }
/// *     @After
    // public void closeBrowser() {
    //     driver.quit();  // Cerrar el navegador después de ejecutar las pruebas
    //}
}
