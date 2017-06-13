package com.sarah.semantic_analysis.web_oauth_server.controller.user;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.dto.OauthRegParam;
import com.sarah.semantic_analysis.web_oauth_server.entities.ClientApp;
import com.sarah.semantic_analysis.web_oauth_server.service.ConsumerService;
import com.sarah.semantic_analysis.web_oauth_server.utils.ConfigUtils;
import com.sarah.semantic_analysis.web_oauth_server.validators.AppsValidator;

/**
 * The class AppsController.
 * 
 * @author chandan
 */
@Controller
public class AppsController {
	/**
	 * consumerService
	 */
	@Autowired
	public ConsumerService consumerService;
	/**
	 * props
	 */
	@Autowired
	public ConfigUtils props;
	/**
	 * validator
	 */
	@Autowired
	AppsValidator validator;

	/**
	 * get
	 * 
	 * @param request
	 * @param mm
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = Constants.Controllers.APPS, method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap mm, HttpSession session) {
		List<ClientApp> list = null;
		try {
			if (session.getAttribute("apperror") != null) {
				mm.addAttribute("error", session.getAttribute("apperror"));
			}
			list = consumerService.findByUserId(String.valueOf(request.getAttribute("userId")));
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<ClientApp>();
		}
		mm.addAttribute("results", list);
		return "apps";
	}

	/**
	 * edit
	 * 
	 * @param id
	 * @param mm
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = Constants.Controllers.EDIT_APP, method = RequestMethod.GET)
	public String edit(@RequestParam("id") String id, ModelMap mm, HttpSession session) {
		try {
			mm.addAttribute("result", consumerService.get(id));
			if (session.getAttribute("apperror") != null) {
				mm.addAttribute("error", session.getAttribute("apperror"));
				session.removeAttribute("apperror");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mm.addAttribute("result", new ClientApp());
		}
		return "editapp";
	}

	/**
	 * update
	 * 
	 * @param clientApp
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = Constants.Controllers.EDIT_APP, method = RequestMethod.POST)
	public String update(ClientApp clientApp, HttpServletRequest request, HttpSession session) {
		try {
			clientApp.setUserId(String.valueOf(request.getAttribute("userId")));

			String errors = validator.validate(clientApp);
			if (errors == null) {
				ClientApp app = consumerService.findByAppName(clientApp.getAppName());
				if (app == null || (app != null && app.getId().equals(clientApp.getId()))) {
					consumerService.saveOrUpdate(clientApp);
					return "redirect:/" + props.getSemanticAnalysisOAuthBaseUrl() + "/apps";
				} else {
					session.setAttribute("apperror", "This App Name is already used.");
				}
			} else {
				session.setAttribute("apperror", errors);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("apperror", "Some Internal Error Occured.Please try again later.");
		}
		return "redirect:/" + props.getSemanticAnalysisOAuthBaseUrl() + "/edit-app?id=" + clientApp.getId();

	}

	/**
	 * delete
	 * 
	 * @param id
	 * @param session
	 * @param mm
	 * @return String
	 */
	@RequestMapping(value = Constants.Controllers.DELETE_APP, method = RequestMethod.GET)
	public String delete(@RequestParam("id") String id, HttpSession session, ModelMap mm) {
		try {
			consumerService.remove(id);
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("apperror", "Some Internal Error Occured.Please try again later.");
		}
		return "redirect:/" + props.getSemanticAnalysisOAuthBaseUrl() + "/apps";
	}

	/**
	 * registerApp
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerApp() {
		return "register";
	}

	/**
	 * saveClientApp
	 * 
	 * @param clientApp
	 * @param request
	 * @return Object
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = Constants.Controllers.REGISTER_APP, method = RequestMethod.POST, produces = "application/x-www-form-urlencoded")
	public Object saveClientApp(OauthRegParam clientApp, HttpServletRequest request)
			throws UnsupportedEncodingException {
		try {
			// TODO: when we will be proving service to third party users, then
			// we going to implement this developerId feature
			// clientApp.setDeveloperId(String.valueOf(request.getAttribute("userId")));
			String errors = validator.validate(clientApp);
			if (errors == null) {
				ClientApp app = consumerService.findByAppName(clientApp.getAppName());
				if (app == null) {
					return consumerService.saveClientApp(clientApp);
				} else {
					return "error=This App Name is already registered&code=209";
				}
			} else {
				return "error=" + URLEncoder.encode(errors, "UTF-8") + "&code=209";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error=" + URLEncoder.encode("Some unknown error occured", "UTF-8") + "&code=209";
		}
	}

}