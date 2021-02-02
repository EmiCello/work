package config;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(classes = ConfigApplication.class)
public class ConfigApplicationTest {
    @Test
    public void contextLoads() {

    }
}
