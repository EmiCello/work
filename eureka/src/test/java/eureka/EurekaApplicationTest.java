package eureka;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(classes = EurekaApplication.class)
public class EurekaApplicationTest {
    @Test
    public void contextLoads() {

    }
}
