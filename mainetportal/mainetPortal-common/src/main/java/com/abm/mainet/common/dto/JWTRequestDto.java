package com.abm.mainet.common.dto;

import java.io.Serializable;

public class JWTRequestDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
