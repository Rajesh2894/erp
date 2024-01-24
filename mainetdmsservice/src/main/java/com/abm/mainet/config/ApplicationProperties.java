package com.abm.mainet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

	@Value("${user.id}")
	private String userid;

	@Value("${user.password}")
	private String userPassword;

	@Value("${alfresco.browser.URL}")
	private String alfrescoBrowserURL;

	@Value("${application.URL}")
	private String applicationURL;

	@Value("${alfresco.ws.statusofid.url}")
	private String alfrescoWsStatusofIdURL;

	@Value("${alfresco.ws.alfticket.url}")
	private String alfrescoWsAlfTicketURL;

	@Value("${alfresco.ws.quickshare.url}")
	private String alfrescoWsQuickShareURL;

	@Value("${alfresco.ws.quickunshare.url}")
	private String alfrescoWsQuickUnshareURL;
	
	@Value("${alfresco.siteName}")
	private String siteName;

	public String getUserid() {
		return userid;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getAlfrescoBrowserURL() {
		return alfrescoBrowserURL;
	}

	public String getApplicationURL() {
		return applicationURL;
	}

	public String getAlfrescoWsStatusofIdURL() {
		return alfrescoWsStatusofIdURL;
	}

	public String getAlfrescoWsAlfTicketURL() {
		return alfrescoWsAlfTicketURL;
	}

	public String getAlfrescoWsQuickShareURL() {
		return alfrescoWsQuickShareURL;
	}

	public String getAlfrescoWsQuickUnshareURL() {
		return alfrescoWsQuickUnshareURL;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}	
	
}
