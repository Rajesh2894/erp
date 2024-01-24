package com.abm.mainet.property.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "DiversionPlotDetailsByRecordcode")
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiversionPlotDetailsByRecordcode {

	@XmlElement
	private String Plotno;

	public String getPlotno() {
		return Plotno;
	}

	public void setPlotno(String plotno) {
		Plotno = plotno;
	}
}
