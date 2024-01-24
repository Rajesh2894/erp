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

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "NajoolOwnerDetails")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class NajoolOwnerDetails {

    @XmlElement
    private List<NajoolOwnerDetailsList> najoolOwnerDetailsList = new ArrayList<>(0);

    public List<NajoolOwnerDetailsList> getNajoolOwnerDetailsList() {
        return najoolOwnerDetailsList;
    }

    public void setNajoolOwnerDetailsList(List<NajoolOwnerDetailsList> najoolOwnerDetailsList) {
        this.najoolOwnerDetailsList = najoolOwnerDetailsList;
    }

}
