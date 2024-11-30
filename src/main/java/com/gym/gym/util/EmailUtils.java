package com.gym.gym.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.gym.gym.exception.EmailSendingException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	private final JavaMailSender sender;

	private final SpringTemplateEngine templateEngine;

	public EmailUtils(JavaMailSender sender, SpringTemplateEngine templateEngine) {
		this.sender = sender;
		this.templateEngine = templateEngine;
	}

	public void sendEmail(String destinatary, String subject, Context context, String templateName) {
		try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(destinatary);
			helper.setSubject(subject);

			String htmlContent = templateEngine.process(templateName, context);
			helper.setText(htmlContent, true);

			sender.send(message);
		} catch (MessagingException e) {
			throw new EmailSendingException(e.getMessage());
		}
	}

}
