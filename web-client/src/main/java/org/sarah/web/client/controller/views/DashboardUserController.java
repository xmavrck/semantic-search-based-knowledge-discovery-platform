package org.sarah.web.client.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The class DashboardUserController.
 * 
 * @author chandan
 */
@Controller
@RequestMapping("/dashboard/user")
public class DashboardUserController {
	/**
	 * dashboardUser
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String dashboardUser() {
		return "user_searchpage";
	}

	/**
	 * changePassword
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/change-password")
	public String changePassword() {
		return "user_changepassword";
	}
}
