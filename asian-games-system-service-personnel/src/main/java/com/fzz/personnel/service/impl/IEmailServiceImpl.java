package com.fzz.personnel.service.impl;

import com.fzz.personnel.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class IEmailServiceImpl implements IEmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.nickname}")
    private String nickname;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMail(List<String> emails, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        for(String email:emails){
            message.setFrom(nickname+'<'+sender+'>');
            message.setTo(email);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);
        }

    }

    @Override
    public void sendHtmlMail(String receiveEmail, String subject, String emailContent) throws MessagingException {

    }

    @Override
    public void sendAttachmentsMail(String receiveEmail, String subject, String emailContent, List<String> filePathList) throws MessagingException {

    }
}
