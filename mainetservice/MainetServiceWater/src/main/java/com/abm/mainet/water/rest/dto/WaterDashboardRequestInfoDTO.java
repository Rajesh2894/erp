package com.abm.mainet.water.rest.dto;

import java.util.List;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

public class WaterDashboardRequestInfoDTO {
	
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
		public void setApiId(String apiId) {
			this.apiId = apiId;
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

		
	
	

}

	

