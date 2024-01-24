package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_SW_SURVEY_MAST_HIST")
public class SurveyFormMasterHistory implements Serializable{
    

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SU_ID_H")
    private Long suIdH;
    
    @Column(name = "SU_ID")
    private Long suId;

    @Column(name = "LOC_ID")
    private Long locId;

    @Column(name = "COD_WARD1")
    private Long codWard1;

    @Column(name = "COD_WARD2")
    private Long codWard2;

    @Column(name = "COD_WARD3")
    private Long codWard3;

    @Column(name = "COD_WARD4")
    private Long codWard4;

    @Column(name = "COD_WARD5")
    private Long codWard5;

    @Column(name = "SU_DATE")
    private Date suDate;
    
    @Column(name = "SU_NAME")
    private String suName;
    
    @Column(name = "SU_MOBILENO")
    private String suMobileNo;
    
    @Column(name = "SU_PRESIDENT")
    private String suPresident;
    
    @Column(name = "SU_COMMITEENAME")
    private String commiteeName;
    
    @Column(name = "SU_REMARK")
    private String remark;
    
    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;
    
    @Column(name = "H_STATUS", length = 1, nullable = false)
    private String hStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    public SurveyFormMasterHistory() {       
    }

    public Long getSuId() {
        return suId;
    }

    public void setSuId(Long suId) {
        this.suId = suId;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getCodWard1() {
        return codWard1;
    }

    public void setCodWard1(Long codWard1) {
        this.codWard1 = codWard1;
    }

    public Long getCodWard2() {
        return codWard2;
    }

    public void setCodWard2(Long codWard2) {
        this.codWard2 = codWard2;
    }

    public Long getCodWard3() {
        return codWard3;
    }

    public void setCodWard3(Long codWard3) {
        this.codWard3 = codWard3;
    }

    public Long getCodWard4() {
        return codWard4;
    }

    public void setCodWard4(Long codWard4) {
        this.codWard4 = codWard4;
    }

    public Long getCodWard5() {
        return codWard5;
    }

    public void setCodWard5(Long codWard5) {
        this.codWard5 = codWard5;
    }

    public Date getSuDate() {
        return suDate;
    }

    public void setSuDate(Date suDate) {
        this.suDate = suDate;
    }

    public String getSuName() {
        return suName;
    }

    public void setSuName(String suName) {
        this.suName = suName;
    }

    public String getSuMobileNo() {
        return suMobileNo;
    }

    public void setSuMobileNo(String suMobileNo) {
        this.suMobileNo = suMobileNo;
    }

    public String getSuPresident() {
        return suPresident;
    }

    public void setSuPresident(String suPresident) {
        this.suPresident = suPresident;
    }

    public String getCommiteeName() {
        return commiteeName;
    }

    public void setCommiteeName(String commiteeName) {
        this.commiteeName = commiteeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    public Long getSuIdH() {
        return suIdH;
    }

    public void setSuIdH(Long suIdH) {
        this.suIdH = suIdH;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_SURVEY_MAST_HIST", "SU_ID_H" };
    }


}
