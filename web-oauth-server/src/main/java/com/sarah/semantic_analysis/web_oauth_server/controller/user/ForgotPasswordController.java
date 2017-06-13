package com.sarah.semantic_analysis.web_oauth_server.controller.user;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.dto.MatchCodeDto;
import com.sarah.semantic_analysis.web_oauth_server.entities.User;
import com.sarah.semantic_analysis.web_oauth_server.entities.UserAccountRecovery;
import com.sarah.semantic_analysis.web_oauth_server.service.UserAccountRecoveryService;
import com.sarah.semantic_analysis.web_oauth_server.service.UserService;
import com.sarah.semantic_analysis.web_oauth_server.utils.CodeUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.ConfigUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.EmailUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.EncryptionUtils;
import com.sarah.semantic_analysis.web_oauth_server.validators.ResetPasswordValidator;

/**
 * The class ForgotPasswordController.
 * 
 * @author chandan
 */
@Controller
public class ForgotPasswordController {

	/**
	 * codeUtils
	 */
	@Autowired
	CodeUtils codeUtils;

	/**
	 * emailUtils
	 */
	@Autowired
	EmailUtils emailUtils;

	/**
	 * encryptionUtils
	 */
	@Autowired
	EncryptionUtils encryptionUtils;

	/**
	 * userAccountRecoveryService
	 */
	@Autowired
	UserAccountRecoveryService userAccountRecoveryService;
	/**
	 * iUsermasterService
	 */
	@Autowired
	public UserService iUsermasterService;

	/**
	 * propertiesFile
	 */
	@Autowired
	ConfigUtils propertiesFile;

	/**
	 * validator
	 */
	@Autowired
	ResetPasswordValidator validator;

	/**
	 * expireLink
	 */
	Integer expireLink = -30;

	/**
	 * accountactivationEmployee
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.EMPLOYEE_ACCOUNT_ACTIVATION)
	public String accountactivationEmployee() {
		return "employeeaccountverification";
	}

	/**
	 * accountactivation
	 * 
	 * @param key
	 * @param emailId
	 * @param session
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.ACCOUNT_ACTIVATION)
	public String accountactivation(@RequestParam("key") String key, @RequestParam("emailId") String emailId,
			HttpSession session) {
		try {
			User obj = iUsermasterService.findByEmailIdAndLinkKey(emailId, key);
			if (obj != null) {
				obj.setIsEnabled(true);
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, expireLink);
				obj.setExpiryDate(cal.getTime());
				iUsermasterService.update(obj);
				return "redirect:/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/activation";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/linkexpired";
	}

	/**
	 * forgotId
	 * 
	 * @param session
	 * @param mm
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.FORGET_PASSWORD)
	public String forgotId(HttpSession session, ModelMap mm) {
		if (session.getAttribute("forgotpasserror") != null) {
			mm.addAttribute("forgotEmailIdError", String.valueOf(session.getAttribute("forgotpasserror")));
			session.removeAttribute("forgotpasserror");
		}
		if (session.getAttribute("emailId") != null) {
			mm.addAttribute("emailId", String.valueOf(session.getAttribute("emailId")));
			session.removeAttribute("emailId");
		}
		return "forgetpassword";
	}

	/**
	 * forgotid
	 * 
	 * @param dto
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.POST, value = Constants.Controllers.FORGET_PASSWORD)
	public String forgotid(MatchCodeDto dto, HttpServletRequest request, HttpSession session) {
		try {
			final User usermaster = iUsermasterService.findByEmail(dto.getEmailId());
			if (usermaster != null) {

				final String randomCode = codeUtils.generateRandomCode();
				UserAccountRecovery userAccountRecovery = new UserAccountRecovery();
				userAccountRecovery.setUserId(usermaster.getId());
				userAccountRecovery.setLinkKey(randomCode);

				userAccountRecoveryService.findAndDeleteByUserId(usermaster.getId());
				try {
					emailUtils.sendForgotEmail(usermaster.getFirstName() + "", usermaster.getLastName() + "",
							usermaster.getEmailId(), randomCode);
				} catch (Exception e) {
					e.printStackTrace();
				}
				UserAccountRecovery uar = userAccountRecoveryService.add(userAccountRecovery);
				if (uar == null) {
					session.setAttribute("forgotpasserror", "Sorry,Some internal error occured.");
				} else {
					return "redirect:/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/forgot-email-success";
				}
			} else {
				session.setAttribute("forgotpasserror", "This email doesn't find in our records.");
			}
		} catch (Exception e) {
			session.setAttribute("forgotpasserror", "Sorry,Some internal error occured.Please try again later.");
			e.printStackTrace();
		}
		session.setAttribute("emailId", dto.getEmailId());
		return "redirect:/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/forgetpassword";
	}

	/**
	 * reset
	 * 
	 * @param sstt
	 * @param dto
	 * @param session
	 * @return String
	 * @throws InvalidKeyException,
	 *             NoSuchAlgorithmException, NoSuchPaddingException,
	 *             IllegalBlockSizeException,BadPaddingException
	 */
	@RequestMapping(method = RequestMethod.POST, value = Constants.Controllers.RESET)
	public String reset(@RequestParam("sstt") String sstt, MatchCodeDto dto, HttpSession session)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		Map<String, String> RESPONSE = new HashMap<String, String>();
		try {
			RESPONSE = validator.validate(dto);
			if (RESPONSE.size() == 0) {
				UserAccountRecovery obj = userAccountRecoveryService.findByEmailIdAndResetKey(dto.getEmailId(), sstt);
				if (obj != null) {
					User usermaster = iUsermasterService.findByEmail(dto.getEmailId());
					usermaster.setPassword(encryptionUtils.encrpt(dto.getNewPassword()));
					iUsermasterService.update(usermaster);
					userAccountRecoveryService.deleteByUsermasterUserId(usermaster.getId());
					return "redirect:/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl()
							+ "/password-changed-success";
				} else {
					RESPONSE.put("error", "Sorry, This link is expired. Please start password recovery again!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			RESPONSE.put("error", "Sorry,Some internal error occured.Please try after sometime.");
		}
		session.setAttribute("reseterror", RESPONSE);
		return "redirect:/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/reset?sstt=" + sstt + "&emailId="
				+ dto.getEmailId();
	}

	/**
	 * verify
	 * 
	 * @param key
	 * @param emailId
	 * @param session
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.VERIFY)
	public String verify(@RequestParam("key") String key, @RequestParam("emailId") String emailId,
			HttpSession session) {
		try {
			UserAccountRecovery obj = userAccountRecoveryService.findByEmailIdAndLinkKey(emailId, key);
			if (obj != null) {
				String randomCode = codeUtils.generateRandomCode();
				obj.setResetKey(randomCode);

				session.setAttribute("emailId", emailId);
				userAccountRecoveryService.update(obj);
				return "redirect:" + propertiesFile.getResetLink() + randomCode + "&emailId=" + emailId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/linkexpired";
	}

	/**
	 * linkexpired
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.LINK_EXPIRED)
	public String linkexpired() {
		return "linkexpired";
	}

	/**
	 * forgotResetEmail
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.FORGOT_EMAIL_SUCCESS)
	public String forgotResetEmail() {
		return "forgotemailsuccess";
	}

	/**
	 * reset
	 * 
	 * @param session
	 * @param mm
	 * @param request
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.RESET)
	public String reset(HttpSession session, ModelMap mm, HttpServletRequest request) {
		try {
			if (session.getAttribute("reseterror") != null) {
				mm.addAllAttributes((Map<String, String>) session.getAttribute("reseterror"));
				session.removeAttribute("reseterror");
			}
			if (request.getParameter("sstt") == null || request.getParameter("emailId") == null) {
				throw new RuntimeException();
			}
			mm.addAttribute("sstt", request.getParameter("sstt"));
			mm.addAttribute("emailId", request.getParameter("emailId"));
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/linkexpired";
		}
		return "resetpassword";
	}

	/**
	 * passwordchangedsuccess
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.PASSWORD_CHANGED_SUCCESS)
	public String passwordchangedsuccess() {
		return "passwordchangedsuccess";
	}
}
