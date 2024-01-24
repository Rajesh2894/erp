package com.abm.mainet.cms.dto;

public class EventSensorAccTockenReqDTO {

	private String authorizationKey;
	private String password;
	private String username;
	private String grantType;
	
	public String getAuthorizationKey() {
		return authorizationKey;
	}
	public void setAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	
}
