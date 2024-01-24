package com.abm.mainet.common.integration.dto;

public class Consent{
    private String consentId;
    private String timestamp;
    private DataConsumer dataConsumer;
    private DataProvider dataProvider;
    private Purpose purpose;
    private User user;
    private Data data;
    private Permission permission;
	public String getConsentId() {
		return consentId;
	}
	public void setConsentId(String consentId) {
		this.consentId = consentId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public DataConsumer getDataConsumer() {
		return dataConsumer;
	}
	public void setDataConsumer(DataConsumer dataConsumer) {
		this.dataConsumer = dataConsumer;
	}
	public DataProvider getDataProvider() {
		return dataProvider;
	}
	public void setDataProvider(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	public Purpose getPurpose() {
		return purpose;
	}
	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
    
}
