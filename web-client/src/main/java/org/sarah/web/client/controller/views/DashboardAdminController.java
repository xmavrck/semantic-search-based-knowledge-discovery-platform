package org.sarah.web.client.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The class DashboardAdminController.
 * 
 * @author chandan
 */
@Controller
@RequestMapping("/dashboard/admin")
public class DashboardAdminController {
	/**
	 * dashboardAdmin
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String dashboardAdmin() {
		return "admin_dashboardadmin";
	}

	/**
	 * employees
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/employees")
	public String employees() {
		return "admin_manageemployees";
	}

	/**
	 * changePassword
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/change-password")
	public String changePassword() {
		return "admin_changepassword";
	}
}
