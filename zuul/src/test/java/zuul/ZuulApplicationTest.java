package zuul;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;


@WebAppConfiguration
@ContextConfiguration(classes = ZuulApplication.class)
public class ZuulApplicationTest {

    @Test
    public void contextLoads() {

    }
}
