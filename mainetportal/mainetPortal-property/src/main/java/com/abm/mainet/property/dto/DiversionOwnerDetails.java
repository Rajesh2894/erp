package com.abm.mainet.property.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "DiversionOwnerDetails")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class DiversionOwnerDetails {

    @XmlElement
    private List<DiversionOwnerDetailsList> diversionOwnerDetailsList = new ArrayList<>(0);

	public List<DiversionOwnerDetailsList> getDiversionOwnerDetailsList() {
		return diversionOwnerDetailsList;
	}

	public void setDiversionOwnerDetailsList(List<DiversionOwnerDetailsList> diversionOwnerDetailsList) {
		this.diversionOwnerDetailsList = diversionOwnerDetailsList;
	}





}
