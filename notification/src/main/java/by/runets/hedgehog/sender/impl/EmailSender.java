package by.runets.hedgehog.sender.impl;

import by.runets.hedgehog.domain.Notification;
import by.runets.hedgehog.sender.Sender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import static by.runets.hedgehog.utils.Constants.*;

@Service(EMAIL_SENDER)
public class EmailSender implements Sender {

    @Value(MAIL_SUBJECT_KEY)
    private String subject;
    @Value(MAIL_FROM_KEY)
    private String from;

    @Autowired
    private MailSender mailSender;

    @Override
    public void send(Notification notification) {
        final SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(notification.getRecipient());
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(notification.getBody());

        mailSender.send(message);
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
}
