package com.abm.mainet.property.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "KhasraDetailsNew")
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)

public class KhasraDetailsNew {
	
    @XmlElement
    private String Khasra_No;
    
    @XmlElement
    private String ErrorMessage;

	public String getKhasra_No() {
		return Khasra_No;
	}

	public void setKhasra_No(String khasra_No) {
		Khasra_No = khasra_No;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}
}
