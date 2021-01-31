package notification.test.config;

import notification.config.EmailConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public EmailConfiguration getConfiguration() {
        EmailConfiguration emailConfiguration = new EmailConfiguration();
        emailConfiguration.setHost("host");
        emailConfiguration.setUser("user");
        emailConfiguration.setPort(2245);
        emailConfiguration.setPassword("12345678");
        return new EmailConfiguration();
    }
}
