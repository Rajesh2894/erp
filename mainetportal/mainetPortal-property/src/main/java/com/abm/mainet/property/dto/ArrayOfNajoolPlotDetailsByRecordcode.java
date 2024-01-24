package com.abm.mainet.property.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "ArrayOfNajoolPlotDetailsByRecordcode")
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrayOfNajoolPlotDetailsByRecordcode {
	
	@XmlElement
	private List<NajoolPlotDetailsByRecordcode> NajoolPlotDetailsByRecordcode = new ArrayList<>(0);

	public List<NajoolPlotDetailsByRecordcode> getNajoolPlotDetailsByRecordcode() {
		return NajoolPlotDetailsByRecordcode;
	}

	public void setNajoolPlotDetailsByRecordcode(List<NajoolPlotDetailsByRecordcode> najoolPlotDetailsByRecordcode) {
		NajoolPlotDetailsByRecordcode = najoolPlotDetailsByRecordcode;
	}
	
}
