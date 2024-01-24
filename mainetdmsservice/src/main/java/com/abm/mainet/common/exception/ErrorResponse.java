package com.abm.mainet.common.exception;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	private String message;

	public static Map<?, ?> getResponse(String code, String message) {
		ObjectMapper oMapper = new ObjectMapper();
		ErrorResponse obj = new ErrorResponse();
		obj.setCode(code);
		obj.setMessage(message);
		Map<?, ?> map = oMapper.convertValue(obj, Map.class);
		return map;
	}

	public ErrorResponse() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
