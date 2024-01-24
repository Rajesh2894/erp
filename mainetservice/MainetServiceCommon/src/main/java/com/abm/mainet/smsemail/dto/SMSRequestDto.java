package com.abm.mainet.smsemail.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SMSRequestDto implements Serializable{

private String sender;	
private String route;
private String  country;
private String unicode;

private String var1;	
private String var2;
private String mobiles;
private String template_id;
private String content;
private String templateId;
private String purpose;
private String userloginId;
private String tokenId;
private String mobileNo;
private String moduleId;
private String userId;
private String tpUserId;
private String emailId;
private String toEmailId;
private String body;
private String subject;

private List<SMS> sms=new ArrayList<>();

public String getSender() {
	return sender;
}

public void setSender(String sender) {
	this.sender = sender;
}

public String getRoute() {
	return route;
}

public void setRoute(String route) {
	this.route = route;
}

public String getCountry() {
	return country;
}

public void setCountry(String country) {
	this.country = country;
}

public String getUnicode() {
	return unicode;
}

public void setUnicode(String unicode) {
	this.unicode = unicode;
}

public List<SMS> getSms() {
	return sms;
}

public void setSms(List<SMS> sms) {
	this.sms = sms;
}

public String getVar1() {
	return var1;
}

public void setVar1(String var1) {
	this.var1 = var1;
}

public String getVar2() {
	return var2;
}

public void setVar2(String var2) {
	this.var2 = var2;
}

public String getMobiles() {
	return mobiles;
}

public void setMobiles(String mobiles) {
	this.mobiles = mobiles;
}

public String getTemplate_id() {
	return template_id;
}

public void setTemplate_id(String template_id) {
	this.template_id = template_id;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public String getTemplateId() {
	return templateId;
}

public void setTemplateId(String templateId) {
	this.templateId = templateId;
}

public String getPurpose() {
	return purpose;
}

public void setPurpose(String purpose) {
	this.purpose = purpose;
}

public String getTokenId() {
	return tokenId;
}

public void setTokenId(String tokenId) {
	this.tokenId = tokenId;
}

public String getMobileNo() {
	return mobileNo;
}

public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}

public String getUserloginId() {
	return userloginId;
}

public void setUserloginId(String userloginId) {
	this.userloginId = userloginId;
}

public String getModuleId() {
	return moduleId;
}

public void setModuleId(String moduleId) {
	this.moduleId = moduleId;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getTpUserId() {
	return tpUserId;
}

public void setTpUserId(String tpUserId) {
	this.tpUserId = tpUserId;
}

public String getEmailId() {
	return emailId;
}

public void setEmailId(String emailId) {
	this.emailId = emailId;
}

public String getToEmailId() {
	return toEmailId;
}

public void setToEmailId(String toEmailId) {
	this.toEmailId = toEmailId;
}

public String getBody() {
	return body;
}

public void setBody(String body) {
	this.body = body;
}

public String getSubject() {
	return subject;
}

public void setSubject(String subject) {
	this.subject = subject;
}
	
}
