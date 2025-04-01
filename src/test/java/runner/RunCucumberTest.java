package runner;

import io.cucumber.junit.platform.engine.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions"
)
@Cucumber
@CucumberContextConfiguration
public class RunCucumberTest {
}