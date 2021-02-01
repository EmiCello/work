package notification.service;

import notification.config.EmailConfiguration;
import notification.domains.EmailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private EmailConfiguration emailConfiguration;
    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendEmail(EmailInfo emailInfo) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfiguration.getHost());
        mailSender.setPort(this.emailConfiguration.getPort());
        mailSender.setUsername(this.emailConfiguration.getUser());
        mailSender.setPassword(this.emailConfiguration.getPassword());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailInfo.getEmail());
        mailMessage.setTo("test@test.com");
        mailMessage.setSubject("You've got an email from " + emailInfo.getName());
        mailMessage.setText(emailInfo.getFeedback());

        mailSender.send(mailMessage);
    }
}