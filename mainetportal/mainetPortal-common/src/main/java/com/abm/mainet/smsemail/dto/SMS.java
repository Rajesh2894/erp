package com.abm.mainet.smsemail.dto;

public class SMS {
private String message;
private String to[];

public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String[] getTo() {
	return to;
}
public void setTo(String[] to) {
	this.to = to;
}



}
