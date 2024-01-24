package com.abm.mainet.property.dto;

import java.io.Serializable;

public class BBPSRequestDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private String propertyNumber;
	private String flatNumber;
	
	public String getPropertyNumber() {
		return propertyNumber;
	}
	public void setPropertyNumber(String propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	public String getFlatNumber() {
		return flatNumber;
	}
	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}
	@Override
	public String toString() {
		return "BBPSRequestDTO [propertyNumber=" + propertyNumber + ", flatNumber=" + flatNumber + "]";
	}
	
}
