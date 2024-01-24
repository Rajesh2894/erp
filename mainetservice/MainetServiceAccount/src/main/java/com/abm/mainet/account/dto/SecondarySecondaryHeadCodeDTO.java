package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SecondarySecondaryHeadCodeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
private String function;
private String primaryHeadCode;
private String secondaryHeadCode;
private String status;
private List<SecondarySecondaryHeadCodeDTO> secondList=new ArrayList<>();


public String getFunction() {
	return function;
}
public void setFunction(String function) {
	this.function = function;
}
public String getPrimaryHeadCode() {
	return primaryHeadCode;
}
public void setPrimaryHeadCode(String primaryHeadCode) {
	this.primaryHeadCode = primaryHeadCode;
}
public String getSecondaryHeadCode() {
	return secondaryHeadCode;
}
public void setSecondaryHeadCode(String secondaryHeadCode) {
	this.secondaryHeadCode = secondaryHeadCode;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public List<SecondarySecondaryHeadCodeDTO> getSecondList() {
	return secondList;
}
public void setSecondList(List<SecondarySecondaryHeadCodeDTO> secondList) {
	this.secondList = secondList;
}


	
}
