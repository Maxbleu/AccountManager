package com.juanfran.accountsmanager.managers;


import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;
import com.juanfran.accountsmanager.interfaces.IGmailManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class GmailManager implements IGmailManager {
    private final String gmail;
    private final String password;
    private final Logger logger;

    @Autowired
    public GmailManager(String gmail, String password) {
        this.gmail = gmail;
        this.password = password;
        this.logger = OrchestratorProyectDependences.getLogger();

    }

    /**
     * Este método se encara de enviar emails a los
     * clientes de la aplicación
     *
     * @param to
     * @param subject
     * @param content
     */
    public void sendEmail(String to, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(gmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(gmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException e) {
            this.logger.error(e.getMessage());
        }
    }
}