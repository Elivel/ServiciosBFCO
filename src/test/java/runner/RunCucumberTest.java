package runner;

import io.cucumber.junit.platform.engine.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberOptions(
        features = "src/test/resources/features", // Asegúrate de que la ruta es correcta
        glue = "stepdefinitions" // Cambia a "stepdefinitions" si tus clases de Step Definitions están en ese paquete
)
@Cucumber
@CucumberContextConfiguration
@ContextConfiguration(classes = TestConfig.class) // Asegúrate de definir una clase de configuración si usas Spring
public class RunCucumberTest {
}
