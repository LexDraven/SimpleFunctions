package Net;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailSender {
    private String host;
    private Properties properties;
    private PasswordAuthentication pa;

    public MailSender(String user, String password) {
        host="smtp.gmail.com";
        properties = new Properties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        setNewAuthPair(user,password);
    }

    public void setNewAuthPair (String user, String password) {
        pa = new PasswordAuthentication(user,password);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void sendEmail(String addressTo, String title, String message){
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return pa;
                    }
                });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(pa.getUserName()));
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(addressTo));
            mimeMessage.setSubject(title);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
            System.out.println("Message sent successfully...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
