/**
 * 
 */
package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author cherupelli.srikanth
 *
 */
@Entity
@Table(name="TB_LGL_ATTENDEE_DETAILS")
public class AttendeeDetails implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7642750283055692648L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name="ATTENDEE_ID")	
    private Long attendeeId;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CJD_ID") 	
    private JudgementDetail tbCaseJudgeDetail;
    
    @Column(name="ATTENDEE_NAME") 	
    private String attendeeName;
    
    @Column(name="ATTENDEE_ADDRESS")	
    private String attendeeAddress;
    
    @Column(name="CREATED_BY")	
    private Long createdBy;
    
    @Column(name="CREATED_DATE")	
    private Date createdDate;
    
    @Column(name="ORGID") 	
    private Long orgId;
    
    @Column(name="LG_IP_MAC")	
    private String lgIpMac;
    
    @Column(name="UPDATED_BY") 	
    private Long updatedBy;
    
    @Column(name="UPDATED_DATE") 	
    private Date updatedDate;
    
    @Column(name="LG_IP_MAC_UPD")	
    private String lgIpMacUpd;

    public Long getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(Long attendeeId) {
        this.attendeeId = attendeeId;
    }

    public JudgementDetail getTbCaseJudgeDetail() {
        return tbCaseJudgeDetail;
    }

    public void setTbCaseJudgeDetail(JudgementDetail tbCaseJudgeDetail) {
        this.tbCaseJudgeDetail = tbCaseJudgeDetail;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public String getAttendeeAddress() {
        return attendeeAddress;
    }

    public void setAttendeeAddress(String attendeeAddress) {
        this.attendeeAddress = attendeeAddress;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }
    
    public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_ATTENDEE_DETAILS", "ATTENDEE_ID" };
    }
    
}
