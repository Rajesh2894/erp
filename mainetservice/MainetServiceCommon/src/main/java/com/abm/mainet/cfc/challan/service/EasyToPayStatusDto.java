package com.abm.mainet.cfc.challan.service;

public class EasyToPayStatusDto {
	private String username;

	private  String appKey;

	private  String origP2pRequestId ;

	public String getUsername() {
		return username;
	}


	public String getOrigP2pRequestId() {
		return origP2pRequestId;
	}

	@Override
	public String toString() {
		return "EasyToPayStatusDto [username=" + username + ", appkey=" + appKey + ", origP2pRequestId="
				+ origP2pRequestId + "]";
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public void setOrigP2pRequestId(String origP2pRequestId) {
		this.origP2pRequestId = origP2pRequestId;
	}


	public String getAppKey() {
		return appKey;
	}


	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
}
