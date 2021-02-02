package notification.service;


import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import notification.domains.EmailInfo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Message;
import javax.mail.MessagingException;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    GreenMail greenMail;

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        greenMail = new GreenMail(ServerSetupTest.ALL);
        greenMail.start();
    }

    @Test
    public void testSendEmail() throws MessagingException {
        emailService.sendEmail(EmailInfo.builder()
            .name("Johny")
            .email("test@test.com")
            .feedback("This is text from EmailServiceTest :)")
            .build());

        Message[] messages = greenMail.getReceivedMessages();
        Assert.assertNotNull(messages);
        Assert.assertEquals(1, messages.length);
    }
}

