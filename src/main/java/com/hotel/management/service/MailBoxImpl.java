package com.hotel.management.service;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.config.EmailProperties;
import com.hotel.management.model.Mail;

@Service
public class MailBoxImpl implements MailBox {

	/**
	 * Returns a Properties object which is configured for a POP3/IMAP server
	 *
	 * @param protocol either "imap" or "pop3"
	 * @param host
	 * @param port
	 * @return a Properties object
	 */

	@Autowired
	private EmailProperties emailProperties;
	
	private Logger logger = LoggerFactory.getLogger(MailBoxImpl.class);

	private Properties getServerProperties() {

		String protocol = emailProperties.getProtocolImap();
		String host = emailProperties.getHostImap();
		String port = emailProperties.getPortImap();

		Properties properties = new Properties();

		// server setting
		properties.put(String.format("mail.%s.host", protocol), host);
		properties.put(String.format("mail.%s.port", protocol), port);

		// SSL setting
		properties.setProperty(String.format("mail.%s.socketFactory.class", protocol),
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
		properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), String.valueOf(port));

		return properties;
	}

	/**
	 * Returns a list of addresses in String format separated by comma
	 *
	 * @param address an array of Address objects
	 * @return a string represents a list of addresses
	 */
	private String parseAddresses(Address[] address) {
		String listAddress = "";

		if (address != null) {
			for (int i = 0; i < address.length; i++) {
				listAddress += address[i].toString() + ", ";
			}
		}
		if (listAddress.length() > 1) {
			listAddress = listAddress.substring(0, listAddress.length() - 2);
		}

		return listAddress;
	}

	private String getFolderGmail(String name) {

		String sent = "[Gmail]/Sent Mail";
		String inbox = "Inbox";
		String trash = "[Gmail]/Trash";
		String draft = "[Gmail]/Drafts";
		String spam = "[Gmail]/Spam";
		String all = "[Gmail]/All Mail";
		String important = "[Gmail]/Important";
		String archive = "[Gmail]/Archive";
		String starred = "[Gmail]/Starred";
		String social = "[Gmail]/Social";

		if (name.equals("sent"))
			return sent;

		else if (name.equals("inbox"))
			return inbox;

		else if (name.equals("trash"))
			return trash;

		else if (name.equals("draft"))
			return draft;

		else if (name.equals("spam"))
			return spam;

		else if (name.equals("all"))
			return all;

		else if (name.equals("important"))
			return important;

		else if (name.equals("archive"))
			return archive;

		else if (name.equals("starred"))
			return starred;

		else if (name.equals("social"))
			return social;

		else
			return null;
	}

	public static String writePart(Part p) throws Exception {

		String content = "";

		if (p instanceof Message)

			System.out.println("----------------------------");
		System.out.println("CONTENT-TYPE: " + p.getContentType());

		// check if the content is plain text
		if (p.isMimeType("text/plain")) {
			System.out.println("This is plain text");
			System.out.println("---------------------------");
			// System.out.println((String) p.getContent());

			content = p.getContent().toString();
		}
		// check if the content has attachment
		else if (p.isMimeType("multipart/*")) {
			System.out.println("This is a Multipart");
			System.out.println("---------------------------");
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++)
				writePart(mp.getBodyPart(i));
		}
		// check if the content is a nested message
		else if (p.isMimeType("message/rfc822")) {
			System.out.println("This is a Nested Message");
			System.out.println("---------------------------");
			writePart((Part) p.getContent());
		}
		// check if the content is an inline image
		else if (p.isMimeType("image/jpeg")) {
			System.out.println("--------> image/jpeg");
			Object o = p.getContent();

			InputStream x = (InputStream) o;
			// Construct the required byte array
			System.out.println("x.length = " + x.available());
			int i = 0;
			byte[] bArray = new byte[x.available()];
			while ((i = (int) ((InputStream) x).available()) > 0) {
				int result = (int) (((InputStream) x).read(bArray));
				if (result == -1)
					break;
			}
			FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
			f2.write(bArray);
			content = p.getContent().toString();

		} else if (p.getContentType().contains("image/")) {
			System.out.println("content type" + p.getContentType());
			File f = new File("image" + new Date().getTime() + ".jpg");
			DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			com.sun.mail.util.BASE64DecoderStream test = (com.sun.mail.util.BASE64DecoderStream) p.getContent();
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = test.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		} else {
			Object o = p.getContent();
			if (o instanceof String) {
				System.out.println("This is a string");
				System.out.println("---------------------------");
				content = o.toString();
				// System.out.println((String) o);
			} else if (o instanceof InputStream) {
				System.out.println("This is just an input stream");
				System.out.println("---------------------------");
				InputStream is = (InputStream) o;
				is = (InputStream) o;
				int c;
				while ((c = is.read()) != -1)
					System.out.write(c);
			} else {
				System.out.println("This is an unknown type");
				System.out.println("---------------------------");
				System.out.println(o.toString());
			}
		}

		return content;

	}

	@Override
	public List<Mail> getEmails(int msgs, int rows, String name) {

		String folderName = getFolderGmail(name);

		String protocol = emailProperties.getProtocolImap();

		Properties properties = getServerProperties();
		Session session = Session.getDefaultInstance(properties);

		Message[] messages = null;

		List<Mail> mails = new ArrayList<>();

		if (folderName != null) {

			try {
				// connects to the message store
				Store store = session.getStore(protocol);
				store.connect(emailProperties.getUsername(), emailProperties.getPassword());

				// opens the inbox folder
				// Folder folderInbox = store.getFolder("[Gmail]/Trash");

				Folder folder = store.getFolder(folderName);
				folder.open(Folder.READ_ONLY);

				// fetches new messages from server

				int totalnoOfMsgs = folder.getMessageCount();

				if (totalnoOfMsgs > rows) {

					int end = totalnoOfMsgs;

					if (rows != 0 && rows < totalnoOfMsgs)
						end = totalnoOfMsgs - rows - 1;

					int start = end - msgs + 1;

					if (start < 1)
						start = 1;

					System.out.println(start + " " + end + " " + totalnoOfMsgs + " " + folderName);

					messages = folder.getMessages(start, end);

					// Sort messages from recent to oldest
					Arrays.sort(messages, (m1, m2) -> {
						try {
							return m2.getSentDate().compareTo(m1.getSentDate());
						} catch (MessagingException e) {
							throw new RuntimeException(e);
						}
					});

					for (int i = 0; i < messages.length; i++) {
						Message msg = messages[i];
						Address[] fromAddress = msg.getFrom();
						String from = fromAddress[0].toString();
						String subject = msg.getSubject();
						String toList = parseAddresses(msg.getRecipients(RecipientType.TO));
						String ccList = parseAddresses(msg.getRecipients(RecipientType.CC));
						String sentDate = msg.getSentDate().toString();

						Mail mail = new Mail();

						mail.setMailCc(ccList);
						mail.setMailFrom(from);
						mail.setMailTo(toList);
						mail.setMailSubject(subject);
						mail.setTime(sentDate);
						mail.setMsgNo(msg.getMessageNumber());
						mails.add(mail);

					}

					// disconnect
					folder.close(false);
					store.close();
				}
			} catch (NoSuchProviderException ex) {
				System.out.println("No provider for protocol: " + protocol);
				ex.printStackTrace();
			} catch (MessagingException ex) {
				System.out.println("Could not connect to the message store");
				ex.printStackTrace();
			}
		}

		return mails;
	}

	@Override
	public boolean deleteMessagesFromDate(String folderNm, String dateTodelete, int messageNumber) {

		String folderName = getFolderGmail(folderNm);

		String protocol = emailProperties.getProtocolImap();
		boolean mdeleted = false;

		Properties properties = getServerProperties();
		Session session = Session.getDefaultInstance(properties);

		System.out.println(folderName);

		try {

			// connects to the message store
			Store store = session.getStore(protocol);
			store.connect(emailProperties.getUsername(), emailProperties.getPassword());

			// opens the inbox folder
			Folder folder = store.getFolder(folderName);
			folder.open(Folder.READ_WRITE);

			// fetches messages from server

			int totalMessages = folder.getMessageCount();

			int start = 0;
			int end = 0;

			start = messageNumber - 5;
			end = messageNumber + 5;

			if (end > totalMessages)
				end = totalMessages;

			if (start > totalMessages)
				start = totalMessages;

			if (start < 1)
				start = 1;

			System.out.println("asasasas");

			Message[] messages = folder.getMessages(start, end);

			System.out.println("asasasas");

			System.out.println(start + "\t" + end);

			for (int i = 0; i < messages.length; i++) {

				System.out.println("looping ");

				Message message = messages[i];
				String sentDate = message.getSentDate().toString();

				System.out.println(sentDate);

				System.out.println(dateTodelete);

				if (sentDate.equals(dateTodelete)) {
					System.out.println("will be deleted now");
					message.setFlag(Flags.Flag.DELETED, true);
					mdeleted = true;
					break;
				}

			}

			// expunges the folder to remove messages which are marked deleted
			boolean expunge = true;
			folder.close(expunge);

			// another way:
			// folderInbox.expunge();
			// folderInbox.close(false);

			// disconnect
			store.close();
		} catch (NoSuchProviderException ex) {
			mdeleted = false;
			System.out.println("No provider.");
			ex.printStackTrace();
		} catch (MessagingException ex) {
			mdeleted = false;
			System.out.println("Could not connect to the message store.");
			ex.printStackTrace();
		}

		return mdeleted;
	}

	@Override
	public boolean moveMessagesFromDate(int messageNumber, String dateOfMessage, String fromFolder, String toFolder) {

		String folderfrom = getFolderGmail(fromFolder);

		String folderTo = getFolderGmail(toFolder);

		String protocol = emailProperties.getProtocolImap();

		boolean moved = false;

		Properties properties = getServerProperties();
		Session session = Session.getDefaultInstance(properties);

		if (folderfrom != null && folderTo != null) {
			try {

				// connects to the message store
				Store store = session.getStore(protocol);
				store.connect(emailProperties.getUsername(), emailProperties.getPassword());

				// opens the inbox folder
				Folder folder = store.getFolder(folderfrom);

				Folder folderGmailTo = store.getFolder(folderTo);

				folder.open(Folder.READ_WRITE);

				// fetches messages from server

				int totalMessages = folder.getMessageCount();

				int start = 0;
				int end = 0;

				start = messageNumber - 5;
				end = messageNumber + 5;

				if (end > totalMessages)
					end = totalMessages;

				if (start > totalMessages)
					start = totalMessages;

				if (start < 1)
					start = 1;

				Message[] messages = folder.getMessages(start, end);

				System.out.println(start + "\t" + end);

				Message[] messageMove = new Message[1];

				for (int i = 0; i < messages.length; i++) {

					Message message = messages[i];
					String sentDate = message.getSentDate().toString();

					System.out.println(message.getMessageNumber());

					if (sentDate.equals(dateOfMessage)) {
						messageMove[0] = message;
						folder.copyMessages(messageMove, folderGmailTo);
						System.out.println("copied");
						//message.setFlag(Flags.Flag.DELETED, true);
						//System.out.println("removed");
						moved = true;
						break;
					}

				}

				// expunges the folder to remove messages which are marked deleted
				boolean expunge = true;
				folder.close(expunge);

				// another way:
				// folderInbox.expunge();
				// folderInbox.close(false);

				// disconnect
				store.close();
			} catch (NoSuchProviderException ex) {
				moved = false;
				System.out.println("No provider.");
				ex.printStackTrace();
			} catch (MessagingException ex) {
				moved = false;
				System.out.println("Could not connect to the message store.");
				ex.printStackTrace();
			}

		}

		return moved;
	}

	@Override
	public Mail getEmail(int msgNo, String dateOfMessage, String folderNm) {

		String saveDirectory = "E:\\";

		String folderName = getFolderGmail(folderNm);

		String protocol = emailProperties.getProtocolImap();

		Properties properties = getServerProperties();
		Session session = Session.getDefaultInstance(properties);

		Message[] messages = null;

		Mail mail = new Mail();

		if (folderName != null) {

			try {
				// connects to the message store
				Store store = session.getStore(protocol);
				store.connect(emailProperties.getUsername(), emailProperties.getPassword());

				Folder folder = store.getFolder(folderName);
				folder.open(Folder.READ_ONLY);

				int totalnoOfMsgs = folder.getMessageCount();

				int end = msgNo + 5;
				int start = msgNo - 5;

				if (end > totalnoOfMsgs)
					end = totalnoOfMsgs;

				if (start < 1)
					start = 1;

				System.out.println(start + " " + end + " " + totalnoOfMsgs + " " + folderName);

				messages = folder.getMessages(start, end);

				for (int i = 0; i < messages.length; i++) {

					Message msg = messages[i];
					String sentDate = msg.getSentDate().toString();

					if (sentDate.equals(dateOfMessage)) {

						Address[] fromAddress = msg.getFrom();
						String from = fromAddress[0].toString();
						String subject = msg.getSubject();
						String toList = parseAddresses(msg.getRecipients(RecipientType.TO));
						String ccList = parseAddresses(msg.getRecipients(RecipientType.CC));

						String contentType = msg.getContentType();
						String messageContent = "";

						// store attachment file name, separated by comma
						String attachFiles = "";

						try {

							if (contentType.contains("multipart")) {
								System.out.println("this is multi");
								// content may contain attachments
								Multipart multiPart = (Multipart) msg.getContent();
								int numberOfParts = multiPart.getCount();
								for (int partCount = 0; partCount < numberOfParts; partCount++) {
									MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
									if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
										// this part is attachment
										String fileName = part.getFileName();
										attachFiles += fileName + ", ";
										part.saveFile(saveDirectory + File.separator + fileName);
									} else {
										// this part may be the message content
										messageContent = part.getContent().toString();
									}
								}

								if (attachFiles.length() > 1) {
									attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
								}
							} else if (msg.getContent() instanceof String) {
								Object content = msg.getContent();
								if (content != null) {
									System.out.println("this is text");
									messageContent = content.toString();
								}
							}

						} catch (IOException e) {
							// TODO: handle exception
						}

						mail.setContentType(contentType);
						mail.setMailContent(messageContent);
						mail.setMailCc(ccList);
						mail.setMailFrom(from);
						mail.setMailTo(toList);
						mail.setMailSubject(subject);
						mail.setTime(sentDate);
						mail.setMsgNo(msg.getMessageNumber());

						break;

					}
				}

				// disconnect
				folder.close(false);
				store.close();

			} catch (

			NoSuchProviderException ex) {
				System.out.println("No provider for protocol: " + protocol);
				ex.printStackTrace();
			} catch (MessagingException ex) {
				System.out.println("Could not connect to the message store");
				ex.printStackTrace();
			}
		}

		return mail;

	}

	private String getTextFromMessage(Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

}
