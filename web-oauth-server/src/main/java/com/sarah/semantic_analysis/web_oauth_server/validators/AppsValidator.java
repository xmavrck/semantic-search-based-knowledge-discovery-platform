package com.sarah.semantic_analysis.web_oauth_server.validators;

import org.springframework.stereotype.Component;

import com.sarah.semantic_analysis.web_oauth_server.dto.OauthRegParam;
import com.sarah.semantic_analysis.web_oauth_server.entities.ClientApp;
/**
 * The class AppsValidator.
 * 
 * @author chandan
 */
@Component
public class AppsValidator {
	
	/**
	 * validate
	 * 
	 * @param client
	 * @return String
	 */	
	public String validate(OauthRegParam client) {
		StringBuilder error = null;
		if (client.getAppName().trim().equals("")) {
			if (error == null)
				error = new StringBuilder();
			error.append("Client App Name is required").append("\n");
		}
		if (client.getAppUrl().trim().equals("")) {
			if (error == null)
				error = new StringBuilder();
			error.append("App Url is required").append("\n");
		}
		if (client.getCallBackUrl().trim().equals("")) {
			if (error == null)
				error = new StringBuilder();
			error.append("CallBack Url is required").append("\n");
		}
		if (error != null)
			return error.toString();
		return null;
	}
	/**
	 * validate
	 * 
	 * @param client
	 * @return String
	 */	
	public String validate(ClientApp client) {
		StringBuilder error = null;
		if (client.getAppName().trim().equals("")) {
			if (error == null)
				error = new StringBuilder();
			error.append("Client App Name is required").append("\n");
		}
		if (client.getAppUrl().trim().equals("")) {
			if (error == null)
				error = new StringBuilder();
			error.append("App Url is required").append("\n");
		}
		if (client.getCallBackUrl().trim().equals("")) {
			if (error == null)
				error = new StringBuilder();
			error.append("CallBack Url is required").append("\n");
		}
		if (error != null)
			return error.toString();
		return null;
	}
}
