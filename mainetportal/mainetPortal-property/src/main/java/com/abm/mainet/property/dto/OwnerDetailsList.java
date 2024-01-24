package com.abm.mainet.property.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;


@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "ownerdetails")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class OwnerDetailsList {

	@XmlElement
    private List<OwnerDetails> OwnerDetails = new ArrayList<>(0);

    public List<OwnerDetails> getOwnerDetails() {
		return OwnerDetails;
	}

	public void setOwnerDetails(List<OwnerDetails> ownerDetails) {
		OwnerDetails = ownerDetails;
	}
}
