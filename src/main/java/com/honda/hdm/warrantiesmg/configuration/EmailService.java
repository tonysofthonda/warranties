package com.honda.hdm.warrantiesmg.configuration;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.honda.hdm.warrantiesmg.app.web.model.EmailDto;

@Service
public class EmailService {
	
	private static final Logger logger = LogManager.getLogger(EmailService.class);
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private NotificationProperties notificationProperties;
	
	/**
	 * Sends an email with the provided parameters
	 * @param sentTo List of e-mails to be sent to
	 * @param subject The subject of the e-mail
	 * @param message The (HTML) message of the e-mail
	 * @return True if the email was sent, false otherwise
	 * @throws MailException 
	 * @throws MessagingException 
	 */
	public void send(@NotNull List<String> sentTo, @NotNull String subject, @NotNull String message) throws IllegalArgumentException, MailException, MessagingException{
		logger.debug("Sending email to:" + sentTo);
		MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        
		helper.setTo(sentTo.toArray(new String[sentTo.size()]));
		helper.setFrom(notificationProperties.getSender());
		helper.setSubject(subject);
		helper.setText(message, true);
		
		sender.send(mimeMessage);
		logger.debug("E-mail sent correctly");
	}
	
	/**
	 * Sends an email using an EmailDto
	 * @param email DTO for an email, has sentTo, message and subject
	 * @return true if the email was sent, false otherwise
	 * @throws MessagingException 
	 */
	public void send(EmailDto email) throws IllegalArgumentException, MailException, MessagingException{
		send(email.getSentTo(), email.getSubject(), email.getMessage());
	}
	
	public NotificationTypeEnum getType() {
		return NotificationTypeEnum.EMAIL;
	}
}
