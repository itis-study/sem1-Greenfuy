package ru.itis.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailService implements Runnable {
    private String to;
    private String subject;
    private String message;

    public EmailService(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public static void sendEmail(String to, String subject, String message) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.mail.ru");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator("oldwin.4@bk.ru", "JhvqDrijvDZbVeptaauP"));
            email.setStartTLSEnabled(true);
            email.setFrom("oldwin.4@bk.ru");
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(to);
            email.send();
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        sendEmail(to, subject, message);
    }
}
