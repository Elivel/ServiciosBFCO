package io.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericStepdefs {

    @Given("open web browser in {string}")
    public void open_web_browser_in(String uriReport) throws InterruptedException {

    }

    @Given("queries the directory {string}")
    public void queries_the_directory(String directoryName) throws InterruptedException {

    }

    @Given("queries the report {string}")
    public void queries_the_report(String reportName) throws InterruptedException {

    }

    @When("applies the necessary parameters")
    public void applies_the_necessary_parameters() throws InterruptedException {

    }

    @Then("the report should be downloaded successfully")
    public void the_report_should_be_downloaded_successfully() {

    }

    @And("{string} = {string}")
    public void add_parameter(String parameterName, String parameterValue) {

    }
}
