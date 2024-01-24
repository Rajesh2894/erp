package com.abm.mainet.property.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "ArrayOfDiversionPlotDetailsByRecordcode")
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrayOfDiversionPlotDetailsByRecordcode {

	@XmlElement
	private List<DiversionPlotDetailsByRecordcode> DiversionPlotDetailsByRecordcode = new ArrayList<>(0);

	public List<DiversionPlotDetailsByRecordcode> getDiversionPlotDetailsByRecordcode() {
		return DiversionPlotDetailsByRecordcode;
	}

	public void setDiversionPlotDetailsByRecordcode(
			List<DiversionPlotDetailsByRecordcode> diversionPlotDetailsByRecordcode) {
		DiversionPlotDetailsByRecordcode = diversionPlotDetailsByRecordcode;
	}
}
