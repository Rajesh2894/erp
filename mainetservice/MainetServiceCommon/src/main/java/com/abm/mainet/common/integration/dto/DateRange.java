package com.abm.mainet.common.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DateRange{
	private String from;
    @JsonProperty("to") 
    private String myto;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getMyto() {
		return myto;
	}
	public void setMyto(String myto) {
		this.myto = myto;
	}
	
    
    
}
