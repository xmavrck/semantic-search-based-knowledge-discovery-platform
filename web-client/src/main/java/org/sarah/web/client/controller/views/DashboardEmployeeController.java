package org.sarah.web.client.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The class DashboardEmployeeController.
 * 
 * @author chandan
 */
@Controller
@RequestMapping("/dashboard/employee")
public class DashboardEmployeeController {
	/**
	 * dashboardEmployee
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String dashboardEmployee() {
		return "employee_dashboardemployee";
	}

	/**
	 * scrappyJobs
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/scrappy-jobs", method = RequestMethod.GET)
	public String scrappyJobs() {
		return "employee_webcrawlingjobs";
	}

	/**
	 * scrappyJob
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/create-scrappy-job", method = RequestMethod.GET)
	public String scrappyJob() {
		return "employee_addwebcrawlingjob";
	}

	/**
	 * viewScrappyJob
	 * 
	 * @param id
	 * @return String
	 */
	@RequestMapping(value = "/scrappy-jobs/{id}", method = RequestMethod.GET)
	public String viewScrappyJob(@PathVariable String id) {
		return "employee_viewwebcrawlingjobs";
	}

	/**
	 * changePassword
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/change-password")
	public String changePassword() {
		return "employee_changepassword";
	}

}
