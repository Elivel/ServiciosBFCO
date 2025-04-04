package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public class ConsultaClaiSteps {
    WebDriver driver;

    // Paso 1: El usuario accede a la página de login
   @Given("the user accesses the login page")
    public void the_user_accesses_the_login_page() throws InterruptedException {
       PageFeatures.pageReportes();
    }

    // Paso 4: El usuario consulta el reporte
    @Given("queries the directory {string}")
    public void queries_the_directory(String directoryName) throws InterruptedException {
        PageFeatures.pagedirectories(directoryName);
    }

    // Paso 5: El usuario aplica los parámetros necesarios
    @When("applies the necessary parameters")
    public void applies_the_necessary_parameters() throws InterruptedException {
        // PageFeatures.pageDescargaReport();
    }

    // Paso 6: El reporte debería ser descargado exitosamente
    @Then("the report should be downloaded successfully")
    public void the_report_should_be_downloaded_successfully() {
        PageFeatures.pageReportDesktop();
    }

    @Given("queries the report {string}")
    public void queries_the_report(String reportName) throws InterruptedException {
        log.info("estoy en el report: {}", reportName);
        PageFeatures.pagedirectories(reportName);
    }

    @And("{string} = {string}")
    public void add_parameter(String parameterName, String parameterValue) {
        log.info("add_parameter: {} = {}", parameterName, parameterValue);
        PageFeatures.pagesetParameter(parameterName, parameterValue);

    }

    //@After
    //public void tearDown() {
      //  if (driver != null) {
        //    driver.quit();  // Close the browser window
        //}
    //}
}



