package com.mertaydar.springmvcsecurity.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.mertaydar.springmvcsecurity.dao.MailSend;

public class MailSendImpl implements MailSend {
	
	@Autowired
    public JavaMailSender emailSender;
	
	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        System.out.println("to: "+to+subject+text);
        emailSender.send(message);
    }
}
