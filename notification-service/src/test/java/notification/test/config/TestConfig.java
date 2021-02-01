package notification.test.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@TestConfiguration
public class TestConfig {

    @Bean
    public JavaMailSenderImpl emailSender(){
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        emailSender.setHost("smtp.mailtrap.io");
        emailSender.setPort(2525);
        emailSender.setUsername("d85dffc94125ad");
        emailSender.setPassword("e34bd2b392a5ae");
        Properties mailProps = new Properties();
        mailProps.setProperty("mail.transport.protocol","smtp");
        mailProps.setProperty("mail.smtp.auth","true");
        mailProps.setProperty("mail.smtp.starttls.enable","true");
        mailProps.setProperty("mail.debug","false");
        emailSender.setJavaMailProperties(mailProps);
        return emailSender;
    }
}
