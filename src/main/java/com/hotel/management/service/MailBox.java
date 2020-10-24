package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import javax.mail.MessageRemovedException;

import org.springframework.stereotype.Service;

import com.hotel.management.model.Mail;

@Service
public interface MailBox {

	public List<Mail> getEmails(int msgs, int msgNo, String name);
	
	public Mail getEmail(int msgNo, String date, String folderName);

	public boolean deleteMessagesFromDate(String folderNm, String dateTodelete, int messageCount);

	public boolean moveMessagesFromDate(int messageNumber, String dateOfMessage, String fromFolder, String toFolder) throws MessageRemovedException;

}