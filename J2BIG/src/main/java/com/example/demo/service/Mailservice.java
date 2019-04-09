package com.example.demo.service;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public interface Mailservice {
    void sendMessage(String receiveMail,String IdentityCode) throws Exception;
    MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String IdentityCode) throws Exception;
}
