package com.abm.mainet.property.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "ArrayOfDiversionPlotDetails")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class ArrayOfDiversionPlotDetails {

	@XmlElement
    private List<DiversionPlotDetails> DiversionPlotDetails = new ArrayList<>(0);

	public List<DiversionPlotDetails> getDiversionPlotDetails() {
		return DiversionPlotDetails;
	}

	public void setDiversionPlotDetails(List<DiversionPlotDetails> diversionPlotDetails) {
		DiversionPlotDetails = diversionPlotDetails;
	}



}
