package com.sarah.semantic_analysis.web_oauth_server.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * The class EmailUtils.
 * 
 * @author chandan
 */
@Component
public class EmailUtils {

	/**
	 * javaMailSender
	 */
	@Autowired
	JavaMailSender javaMailSender;
	/**
	 * configUtils
	 */
	@Autowired
	ConfigUtils configUtils;
	/**
	 * NO_OF_CONCURRENT_THREADS
	 */
	final static int NO_OF_CONCURRENT_THREADS = 10;
	/**
	 * THREAD_POOL
	 */
	private static final Executor THREAD_POOL = Executors.newFixedThreadPool(NO_OF_CONCURRENT_THREADS);

	/**
	 * sendMail
	 * 
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param key
	 * @throws MessagingException
	 */
	public void sendMail(String firstName, String lastName, String emailId, String key) throws MessagingException {
		StringBuilder textmessage = new StringBuilder("<p>Dear ").append(firstName).append(" ").append(lastName)
				.append(",").append("You have selected ").append(emailId).append(" as your new ")
				.append(configUtils.getCompany())
				.append(" ID. To verify this email address belongs to you, Please click this Link:")
				.append("<br/><a href='" + configUtils.getAccountActivationLink()).append(key).append("&emailId=")
				.append(emailId).append("'>Link</a>").append(".")
				.append("This code will expire in three hours after this email was sent.<br/>")
				.append("<b>Why you received this email.</b><br/>").append(configUtils.getCompany())
				.append(" requires verification whenever an email address is selected as an ")
				.append(configUtils.getCompany()).append(" ID. Your ").append(configUtils.getCompany())
				.append(" ID cannot be used until you verify it.")
				.append("If you did not make this request, you can ignore this email. No " + configUtils.getCompany())
				.append(" ID will be created without verification.</p><br/><br/>").append(configUtils.getCompany())
				.append(" Support");
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(configUtils.getSemanticAnalysisOauthEmailId());
		helper.setTo(emailId);
		helper.setSubject("Verify your " + configUtils.getCompany() + " ID email address");
		message.setContent(textmessage.toString(), "text/html");
		sendEmail(message);
	}

	/**
	 * sendForgotEmail
	 * 
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param key
	 * @throws MessagingException
	 */
	public void sendForgotEmail(String firstName, String lastName, String emailId, String key)
			throws MessagingException {
		StringBuilder textmessage = new StringBuilder("<p>Dear ").append(firstName).append(" ").append(lastName)
				.append(",").append("You recently requested a password reset for your ")
				.append(configUtils.getCompany()).append(" ID. To complete the process, click the link below. ")
				.append("<br/><br/><a href='" + configUtils.getForgotPasswordLink()).append(key).append("&emailId=")
				.append(emailId).append("'>Reset Now</a>");
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(configUtils.getSemanticAnalysisOauthEmailId());
		helper.setTo(emailId);
		helper.setSubject("" + configUtils.getCompany() + " password recovery email");
		message.setContent(textmessage.toString(), "text/html");
		sendEmail(message);
	}

	/**
	 * sendEmployeeAccountCreation
	 * 
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param password
	 * @return String
	 * @throws MessagingException
	 */
	public void sendEmployeeAccountCreation(String firstName, String lastName, String emailId, String password)
			throws MessagingException {
		StringBuilder textmessage = new StringBuilder("<p>Dear ").append(firstName).append(" ").append(lastName)
				.append(",").append("Your account has been created in ").append(configUtils.getCompany())
				.append(". Please login to your account using these credentials,").append("<br/><br/><b>Username:</b>")
				.append(emailId).append("<br/><b>Password:</b>").append(password);
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(configUtils.getSemanticAnalysisOauthEmailId());
		helper.setTo(emailId);
		helper.setSubject("" + configUtils.getCompany() + " Account Creation Email");
		message.setContent(textmessage.toString(), "text/html");
		sendEmail(message);
	}

	/**
	 * sendEmployeeAccountDeActivation
	 * 
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @throws MessagingException
	 */
	public void sendEmployeeAccountDeActivation(String firstName, String lastName, String emailId)
			throws MessagingException {
		StringBuilder textmessage = new StringBuilder("<p>Dear ").append(firstName).append(" ").append(lastName)
				.append(",")
				.append("<br/>Your account has been deactivated for some reasons.Please contact adminstrator for furthur details.");
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(configUtils.getSemanticAnalysisOauthEmailId());
		helper.setTo(emailId);
		helper.setSubject("" + configUtils.getCompany() + " Account Deactivation Email");
		message.setContent(textmessage.toString(), "text/html");
		sendEmail(message);
	}

	/**
	 * sendEmail
	 * 
	 * @param mimeMessage
	 * @throws MessagingException
	 */
	private void sendEmail(final MimeMessage mimeMessage) throws MessagingException {
		THREAD_POOL.execute(new Runnable() {
			public void run() {
				javaMailSender.send(mimeMessage);
			}
		});
	}

}
