package com.sarah.semantic_analysis.web_oauth_server.controller.user;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.entities.User;
import com.sarah.semantic_analysis.web_oauth_server.service.UserService;
import com.sarah.semantic_analysis.web_oauth_server.utils.CodeUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.ConfigUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.EmailUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.EncryptionUtils;
import com.sarah.semantic_analysis.web_oauth_server.validators.SignUpValidator;

/**
 * The class EmployeeSignUpController.
 * 
 * @author chandan
 */
@Controller
@RequestMapping(Constants.Controllers.SIGNUP_EMPLOYEE)
public class EmployeeSignUpController implements Constants, Constants.Roles {
	/**
	 * usermasterService
	 */
	@Autowired
	UserService usermasterService;

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
	 * props
	 */
	@Autowired
	ConfigUtils props;
	/**
	 * encryptionUtils
	 */
	@Autowired
	EncryptionUtils encryptionUtils;

	/**
	 * signUpValidator
	 */
	@Autowired
	SignUpValidator signUpValidator;

	/**
	 * signUpEmployee
	 * 
	 * @param mv
	 * @param mm
	 * @param userMaster
	 * @param request
	 * @param results
	 * @param session
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String signUpEmployee(ModelAndView mv, ModelMap mm, final User userMaster, HttpServletRequest request,
			BindingResult results, HttpSession session) throws UnsupportedEncodingException {
		Map<String, String> RESPONSE = null;
		try {
			RESPONSE = signUpValidator.validate(userMaster, "", false);
			if (RESPONSE.size() == 0) {
				final String randomCode = codeUtils.generateRandomCode();
				userMaster.setPassword(randomCode);
				System.out.println("randomCode " + randomCode);
				userMaster.setDateAdded(new Date());
				userMaster.setRole(ROLE_EMPLOYEE);
				userMaster.setIsEnabled(false);
				User user = usermasterService.add(userMaster);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RESPONSE.put("error", "Sorry,Some internal error occured.Please try after sometime.");
		}
		if (RESPONSE != null && RESPONSE.size() > 0) {
			System.out.println("Response " + RESPONSE);
			session.setAttribute("signuperror", RESPONSE);
			session.setAttribute("user", userMaster);
			return "redirect:/" + props.getSemanticAnalysisOAuthBaseUrl() + "/signup-employee";
		} else {
			return "redirect:/" + props.getSemanticAnalysisOAuthBaseUrl() + "/account-activation-employee";
		}
	}

	/**
	 * signUp
	 * 
	 * @param session
	 * @param mm
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String signUp(HttpSession session, ModelMap mm) {
		mm.addAttribute("modelFactoryHost", props.getSemanticAnalysisHostname());
		if (session.getAttribute("signuperror") != null) {
			mm.addAllAttributes((Map<String, String>) session.getAttribute("signuperror"));
			session.removeAttribute("signuperror");
		}
		if (session.getAttribute("user") != null) {
			mm.addAttribute("user", (User) session.getAttribute("user"));
			session.removeAttribute("user");
		}
		if (session.getAttribute("emailId") != null && session.getAttribute("name") != null) {
			User user = new User();
			user.setEmailId(String.valueOf(session.getAttribute("emailId")));
			String fullname = String.valueOf(session.getAttribute("name"));
			String name[] = fullname.split(" ");
			if (name != null) {
				user.setFirstName(name[0]);
				if (name.length > 1) {
					StringBuilder lastName = new StringBuilder();
					for (int i = 0; i < name.length; i++) {
						if (i != 0) {
							lastName.append(name[i]).append(" ");
						}
					}
					user.setLastName(lastName.toString());
				}
			}
			mm.addAttribute("user", user);
			session.removeAttribute("emailId");
			session.removeAttribute("name");
		}
		return "signup_employee";
	}

}
