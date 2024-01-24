package com.abm.mainet.smsemail.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SMSRequestDto implements Serializable{

private String sender;	
private String route;
private String  country;
private String unicode;

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





	
}
