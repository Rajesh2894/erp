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

@XmlRootElement(namespace = "http://bhuiyan.cg.nic.in/", name = "PlotDetails")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlotDetails {

    @XmlElement
    private String plotno;

    @XmlElement
    private String area_foot;

    @XmlElement
    private String tax_free_area;

    @XmlElement
    private String rights_type_nm;

    @XmlElement
    private String base_tax;

    @XmlElement
    private String base_env_tax;

    @XmlElement
    private String base_dev_tax;

    @XmlElement
    private String remark;

    @XmlElement
    private String Department;

    @XmlElement
    private List<NajoolOwnerDetails> najoolOwnerDetails = new ArrayList<>(0);

    public String getPlotno() {
        return plotno;
    }

    public void setPlotno(String plotno) {
        this.plotno = plotno;
    }

    public String getArea_foot() {
        return area_foot;
    }

    public void setArea_foot(String area_foot) {
        this.area_foot = area_foot;
    }

    public String getTax_free_area() {
        return tax_free_area;
    }

    public void setTax_free_area(String tax_free_area) {
        this.tax_free_area = tax_free_area;
    }

    public String getRights_type_nm() {
        return rights_type_nm;
    }

    public void setRights_type_nm(String rights_type_nm) {
        this.rights_type_nm = rights_type_nm;
    }

    public String getBase_tax() {
        return base_tax;
    }

    public void setBase_tax(String base_tax) {
        this.base_tax = base_tax;
    }

    public String getBase_env_tax() {
        return base_env_tax;
    }

    public void setBase_env_tax(String base_env_tax) {
        this.base_env_tax = base_env_tax;
    }

    public String getBase_dev_tax() {
        return base_dev_tax;
    }

    public void setBase_dev_tax(String base_dev_tax) {
        this.base_dev_tax = base_dev_tax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public List<NajoolOwnerDetails> getNajoolOwnerDetails() {
        return najoolOwnerDetails;
    }

    public void setNajoolOwnerDetails(List<NajoolOwnerDetails> najoolOwnerDetails) {
        this.najoolOwnerDetails = najoolOwnerDetails;
    }

}
