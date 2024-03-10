/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionCore;

import Config.env;
import Utils.Correo;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

//SOLO TOCAR LAS CREDENCIALES
public class SendEmailThread implements Runnable {

//    public final static String PORT_SMTP = "465";
//    public final static String PROTOCOL = env.PROTOCOL;
//    public final static String HOST = "smtp.googlemail.com";
//    public final static String USER = env.EMAIL_ENVIAR;
//    public final static String EMAIL = env.EMAIL_ENVIAR;
//    public final static String EMAIL_PASS = env.PASS_EMAIL_ENVIAR;
     public final static String PORT_SMTP = "25";
    public final static String PROTOCOL = env.PROTOCOL;
    public final static String HOST = env.HOST_EMAIL;
    public final static String USER = env.USER_SERVER_EMAIL;
    public final static String PASSWORD = env.PASS_EMAIL;
    public final static String EMAIL = env.EMAIL_SERVER;
    public final static String EMAIL_PASS = env.PASS_EMAIL;

    private final Correo email;

    public SendEmailThread(Correo email) {
        this.email = email;
    }

    //---------------------------------------------------------------------------        
    @Override
    public void run() {
//        Properties properties = new Properties();
//        properties.put("mail.transport.protocol", PROTOCOL);
//        properties.setProperty("mail.smtp.host", HOST);
//        properties.setProperty("mail.smtp.port", PORT_SMTP);
////        properties.setProperty("mail.smtp.tls.enable", "true");//cuando user tecnoweb
//        properties.setProperty("mail.smtp.ssl.enable", "true");//cuando usen Gmail
//        properties.setProperty("mail.smtp.auth", "true");
//
//        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(USER, EMAIL_PASS);
//            }
//        });

        try {
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(EMAIL));
//
//            InternetAddress[] toAddresses = {new InternetAddress(email.getTo())};
//
//            message.setRecipients(MimeMessage.RecipientType.TO, toAddresses);
//            message.setSubject(email.getSubject());
//
//            Multipart multipart = new MimeMultipart("alternative");
//            MimeBodyPart htmlPart = new MimeBodyPart();
//
//            htmlPart.setContent(email.getMessage(), "text/html; charset=utf-8");
//            multipart.addBodyPart(htmlPart);
//            message.setContent(multipart);
//            message.saveChanges();
//
//            Transport.send(message);

            Properties props = System.getProperties();
            props.put("mail.smtp.host", HOST);
            Session session = Session.getInstance(props, null);
            MimeMessage msg = new MimeMessage(session);
            InternetAddress[] toAddresses = {new InternetAddress(email.getTo())};

            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(EMAIL, env.USER_SERVER_EMAIL));
            msg.setReplyTo(InternetAddress.parse(EMAIL, false));
            msg.setRecipients(MimeMessage.RecipientType.TO, toAddresses);
            msg.setSubject(email.getSubject(), "UTF-8");
            Multipart multipart = new MimeMultipart("alternative");
            MimeBodyPart htmlPart = new MimeBodyPart();

            htmlPart.setContent(email.getMessage(), "text/html; charset=utf-8");
            multipart.addBodyPart(htmlPart);
            msg.setContent(multipart);
            msg.saveChanges();

            System.out.println("Message is ready");
            Transport.send(msg);
            System.out.println("EMail Sent Successfully!!");

        } catch (NoSuchProviderException | AddressException ex) {
            Logger.getLogger(SendEmailThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(SendEmailThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SendEmailThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
