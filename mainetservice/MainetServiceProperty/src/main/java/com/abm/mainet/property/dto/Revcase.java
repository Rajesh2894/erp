package com.abm.mainet.property.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "Revcase")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Revcase {

    @XmlElement
    private String RevenueofficeName;

    @XmlElement
    private String RevenueCaseNo;

    public String getRevenueofficeName() {
        return RevenueofficeName;
    }

    public void setRevenueofficeName(String revenueofficeName) {
        RevenueofficeName = revenueofficeName;
    }

    public String getRevenueCaseNo() {
        return RevenueCaseNo;
    }

    public void setRevenueCaseNo(String revenueCaseNo) {
        RevenueCaseNo = revenueCaseNo;
    }
}
