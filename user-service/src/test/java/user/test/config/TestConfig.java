package user.test.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import user.dal.UserDalImpl;

@TestConfiguration
public class TestConfig {
    @Bean
    public UserDalImpl getUserDalImpl() {
        return new UserDalImpl();
    }
}
