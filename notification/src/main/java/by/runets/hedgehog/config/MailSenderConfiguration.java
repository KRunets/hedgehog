package by.runets.hedgehog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import static by.runets.hedgehog.utils.Constants.*;

@Configuration
public class MailSenderConfiguration {

    @Value(MAIL_HOST)
    private String mailHost;
    @Value(MAIL_PORT)
    private Integer mailPort;

    @Bean
    public JavaMailSender javaMailSender() {
        var javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(mailPort);

        var properties = new Properties();
        properties.put(MAIL_TRANSPORT_PROTOCOL, SMTP);
        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }

}
