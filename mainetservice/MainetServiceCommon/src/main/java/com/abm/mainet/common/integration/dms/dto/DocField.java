package com.abm.mainet.common.integration.dms.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocField {

	private String fieldName;
	private String fieldValue;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	
}
