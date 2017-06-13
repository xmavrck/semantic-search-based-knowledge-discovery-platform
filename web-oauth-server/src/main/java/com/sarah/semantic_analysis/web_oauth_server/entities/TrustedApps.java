package com.sarah.semantic_analysis.web_oauth_server.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * The class TrustedApps.
 * 
 * @author chandan
 */
@Document
public class TrustedApps {
	@Id
	private String clientId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
