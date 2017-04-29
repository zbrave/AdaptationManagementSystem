package com.mertaydar.springmvcsecurity.dao;

public interface MailSend {
	
	public void sendSimpleMessage(String to, String subject, String text);

}
