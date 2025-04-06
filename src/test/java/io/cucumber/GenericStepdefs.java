package io.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Slf4j
public class GenericStepdefs {

    private WebDriver driver;

    @Before
    public void setUp() {
        if (driver == null)
            driver = new ChromeDriver();
    }

    @SneakyThrows
    @Given("open web browser in {string}")
    public void open_web_browser_in(String uriReport) {

    }

    @SneakyThrows
    @Given("queries the directory {string}")
    public void queries_the_directory(String directoryName) {

    }

    @SneakyThrows
    @Given("queries the report {string}")
    public void queries_the_report(String reportName) {

    }

    @SneakyThrows
    @When("applies the necessary parameters")
    public void applies_the_necessary_parameters() {

    }

    @SneakyThrows
    @Then("the report should be downloaded successfully")
    public void the_report_should_be_downloaded_successfully() {

    }

    @SneakyThrows
    @And("{string} = {string}")
    public void add_parameter(String parameterName, String parameterValue) {

    }
}
