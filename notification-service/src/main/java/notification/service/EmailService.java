package notification.service;

import notification.domains.EmailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendEmail(EmailInfo emailInfo) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mailtrap.io");
        mailSender.setPort(2525);
        mailSender.setUsername("8472a372031c68");
        mailSender.setPassword("944ab9d3d61f0d");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailInfo.getEmail());
        mailMessage.setTo("test@test.com");
        mailMessage.setSubject("You've got an email from " + emailInfo.getName());
        mailMessage.setText(emailInfo.getFeedback());

        mailSender.send(mailMessage);
    }
}