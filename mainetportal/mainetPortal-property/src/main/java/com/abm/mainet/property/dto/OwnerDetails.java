package com.abm.mainet.property.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "OwnerDetails")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class OwnerDetails {

    @XmlElement
    private String ocdname;
    @XmlElement
    private String ocdname1;
    @XmlElement
    private String ofather;
    @XmlElement
    private String ofather1;
    @XmlElement
    private String gender;
    @XmlElement
    private String ocaste_code;
    @XmlElement
    private String ocastenm;
    @XmlElement
    private String oaddr1;
    @XmlElement
    private String mobileno;
    @XmlElement
    private String aadharno;
    @XmlElement
    private String loanbook;

    public String getOcdname() {
        return ocdname;
    }

    public void setOcdname(String ocdname) {
        this.ocdname = ocdname;
    }

    public String getOcdname1() {
        return ocdname1;
    }

    public void setOcdname1(String ocdname1) {
        this.ocdname1 = ocdname1;
    }

    public String getOfather() {
        return ofather;
    }

    public void setOfather(String ofather) {
        this.ofather = ofather;
    }

    public String getOfather1() {
        return ofather1;
    }

    public void setOfather1(String ofather1) {
        this.ofather1 = ofather1;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOcaste_code() {
        return ocaste_code;
    }

    public void setOcaste_code(String ocaste_code) {
        this.ocaste_code = ocaste_code;
    }

    public String getOcastenm() {
        return ocastenm;
    }

    public void setOcastenm(String ocastenm) {
        this.ocastenm = ocastenm;
    }

    public String getOaddr1() {
        return oaddr1;
    }

    public void setOaddr1(String oaddr1) {
        this.oaddr1 = oaddr1;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getAadharno() {
        return aadharno;
    }

    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }

    public String getLoanbook() {
        return loanbook;
    }

    public void setLoanbook(String loanbook) {
        this.loanbook = loanbook;
    }

}
