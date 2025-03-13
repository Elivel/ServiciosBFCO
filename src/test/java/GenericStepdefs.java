import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import picocli.CommandLine;
import tech.falabella.qa.Application;

public class GenericStepdefs {

    @Given("a valid configuration")
    public void aValidConfigurationFile() {
        String[] args = { "-c", "--file", "result.tar", "file1.txt", "file2.txt" };
        Application.main(args);
    }
    @And("^(.*)=\"(.*)\"$")
    public void addArgument(String key, String value) {

    }

    @When("the scheduled job is executed")
    public void theScheduledJobIsExecuted() {

    }

    @Then("^the report must be generated correctly and saved as a CSV file \"(.*)\"$")
    public void theReportMustBeGeneratedCorrectlyAndSavedAsACSVFile(String file) {
    }
}
