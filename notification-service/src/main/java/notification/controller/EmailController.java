package notification.controller;

import notification.config.EmailConfiguration;
import notification.domains.EmailInfo;
import notification.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/feedback")
public class EmailController {

    @Autowired
    private EmailConfiguration emailConfiguration;
    @Autowired
    EmailService emailService;

    @PostMapping
    public void sendFeedback(@RequestBody EmailInfo emailInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Feedback is not valid.");
        }
        emailService.sendEmail(emailInfo);
    }
}
