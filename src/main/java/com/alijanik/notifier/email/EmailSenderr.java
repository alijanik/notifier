package com.alijanik.notifier.email;


import com.alijanik.notifier.common.logger.Logger;
import com.alijanik.notifier.config.ConfigEmail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSenderr {

    private final ConfigEmail configEmail;

    public EmailSenderr(ConfigEmail configEmail) {
        this.configEmail = configEmail;
    }

    private Session getSession() {
        final Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.configEmail.host);
        props.put("mail.smtp.port", this.configEmail.port);
        final String username = this.configEmail.username;
        final String password = this.configEmail.password;
        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public boolean send(EmailContent emailContent) {
        Session session = this.getSession();
        try {
            Message message = new MimeMessage(session);
            message.setSubject(emailContent.getSubject());
            message.addFrom(InternetAddress.parse(emailContent.getFrom()));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emailContent.getToString()));
            message.setContent(emailContent.getContent(), "text/html; charset=utf-8");

            Transport.send(message);
            Logger.log("Message successfully sent!");
            return true;
        } catch (MessagingException e) {
            Logger.log("Exception @ sending email. Message: %s", e.getMessage());
            return false;
        }
    }
}
