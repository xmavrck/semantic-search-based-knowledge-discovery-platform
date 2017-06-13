package com.sarah.semantic_analysis.web_oauth_server.controller.user;

import java.util.Calendar;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.entities.User;
import com.sarah.semantic_analysis.web_oauth_server.service.UserService;
import com.sarah.semantic_analysis.web_oauth_server.utils.CodeUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.ConfigUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.EmailUtils;

/**
 * The class CodeMatchController.
 * 
 * @author chandan
 */
@Controller
public class CodeMatchController {

	/**
	 * usermasterService
	 */
	@Autowired
	public UserService usermasterService;
	/**
	 * javaMailSender
	 */
	@Autowired
	JavaMailSender javaMailSender;
	/**
	 * codeUtils
	 */
	@Autowired
	CodeUtils codeUtils;
	/**
	 * propertiesFile
	 */
	@Autowired
	ConfigUtils propertiesFile;
	/**
	 * emailUtils
	 */
	@Autowired
	EmailUtils emailUtils;

	/**
	 * activation
	 * 
	 * @param session
	 * @param mm
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.ACTIVATION)
	public String activation(HttpSession session, ModelMap mm) {
		return "activation";
	}

	/**
	 * accountActivationVerification
	 * 
	 * @param session
	 * @param mm
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.ACCOUNT_ACTIVATION_CODE)
	public String accountActivationVerification(HttpSession session, ModelMap mm) {
		return "accountactivationverification";
	}

	/**
	 * resendCode
	 * 
	 * @param session
	 * @param mm
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.RESEND)
	public String resendCode(HttpSession session, ModelMap mm) {
		if (session.getAttribute("resendlinkerror") != null) {
			mm.addAttribute("resendEmailIdError", String.valueOf(session.getAttribute("resendlinkerror")));
			session.removeAttribute("resendlinkerror");
		}
		if (session.getAttribute("emailId") != null) {
			mm.addAttribute("emailId", String.valueOf(session.getAttribute("emailId")));
			session.removeAttribute("emailId");
		}
		return "resendlink";
	}

	/**
	 * resendPassCode
	 * 
	 * @param userMaster
	 * @param session
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.POST, value = Constants.Controllers.RESEND)
	public String resendPassCode(User userMaster, HttpSession session) {
		try {
			final User um = usermasterService.findByEmail(userMaster.getEmailId());
			if (um == null) {
				session.setAttribute("resendlinkerror", "Sorry,Email Id not found in our records.");
			} else {
				if (um.getIsEnabled()) {
					session.setAttribute("resendlinkerror", "Your account is already active.Please login to continue.");
				} else {
					final String randomCode = codeUtils.generateRandomCode();
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(propertiesFile.getAccountActivationLinkExpiry()));
					int code = usermasterService.updatePassCodeAndPassCodeAddDate(randomCode, cal.getTime(),
							userMaster.getEmailId());
					if (code == 200) {
						try {
							emailUtils.sendMail(um.getFirstName().toLowerCase(), um.getLastName().toLowerCase(),
									um.getEmailId().toLowerCase(), randomCode);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
						return "redirect:/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/account-activation";
					} else {
						session.setAttribute("resendlinkerror",
								"Sorry,Some internal error occured.Please try again later.");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("resendlinkerror", "Sorry,Email Id not found in our records.");
		}
		session.setAttribute("emailId", userMaster.getEmailId());
		return "redirect:/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/resendlink";
	}
}
