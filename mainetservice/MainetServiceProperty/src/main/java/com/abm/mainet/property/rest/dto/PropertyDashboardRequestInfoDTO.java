package com.abm.mainet.property.rest.dto;

import java.io.Serializable;

/**
 * @author Mithila.Jondhale
 * @since 03-08-2023
 */

public class PropertyDashboardRequestInfoDTO  implements Serializable{

	private static final long serialVersionUID = -6302709822967685417L;
	
	private String apiId;
	private Long ver;
	private Long action;
	private Long did;
	private String key;
	private String msgId;
	private Long ts;
	private String authToken;
	private Object userInfo ;
	
	
	public String getApiId() {
		return apiId;
	}
	public Long getVer() {
		return ver;
	}
	public void setVer(Long ver) {
		this.ver = ver;
	}
	public Long getAction() {
		return action;
	}
	public void setAction(Long action) {
		this.action = action;
	}
	public Long getDid() {
		return did;
	}
	public void setDid(Long did) {
		this.did = did;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public Long getTs() {
		return ts;
	}
	public void setTs(Long ts) {
		this.ts = ts;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public Object getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Object userInfo) {
		this.userInfo = userInfo;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	
}
