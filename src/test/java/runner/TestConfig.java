package runner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public SomeBean someBean() {
        return new SomeBean();
    }
}
