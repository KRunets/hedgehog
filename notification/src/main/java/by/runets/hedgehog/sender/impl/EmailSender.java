package by.runets.hedgehog.sender.impl;

import by.runets.hedgehog.domain.Notification;
import by.runets.hedgehog.sender.Sender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static by.runets.hedgehog.utils.Constants.*;

@Service(EMAIL_SENDER)
public class EmailSender implements Sender {

    private static final Logger LOG = LogManager.getLogger(EmailSender.class);

    @Value(MAIL_SUBJECT_KEY)
    private String subject;
    @Value(MAIL_FROM_KEY)
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(Notification notification) {
        final MimeMessage message = mailSender.createMimeMessage();

        try {
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

            mimeMessageHelper.setTo(notification.getRecipient());
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(notification.getBody());

            mailSender.send(message);
        } catch (MessagingException e) {
            LOG.error("Error occurred during message sending", e);
        }
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
