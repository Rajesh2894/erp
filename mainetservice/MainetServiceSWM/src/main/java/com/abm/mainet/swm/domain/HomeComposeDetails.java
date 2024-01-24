/**
 * 
 */
package com.abm.mainet.swm.domain;

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
@Table(name="TB_SW_HOMECOUMPOSE")
public class HomeComposeDetails {
    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name="SW_HCOM_ID")
    private Long swHomeCompId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REGISTRATION_ID")
    private SolidWasteConsumerMaster tbSolidWasteConsumerMaster;
    
    @Column(name="SW_HCOM_ITEM")	
    private Long swHomeComItem;
    
    @Column(name="SW_HCOM_COLLECTION")	
    private Long swHomeCompCollection;
    
    @Column(name="SW_HCOM_PREPARED")	
    private Long swHomeCompPepared;
    
    @Column(name="SW_HCOM_DATE")	
    private Date swHomeCompDate;
    
    @Column(name="ORGID")	
    private Long orgId;
    
    @Column(name="CREATED_BY")	
    private Long createdBy;
    
    @Column(name="CREATION_DATE")	
    private Date createdDate;
    
    @Column(name="UPDATED_BY")	
    private Long updatedBy;
    
    @Column(name="UPDATED_DATE")	
    private Date updatedDate;
    
    @Column(name="LG_IP_MAC")	
    private String lgIpMac;
    
    @Column(name="LG_IP_MAC_UPD")	
    private String lgIpMacUpd;

    public Long getSwHomeCompId() {
        return swHomeCompId;
    }

    public void setSwHomeCompId(Long swHomeCompId) {
        this.swHomeCompId = swHomeCompId;
    }

    public SolidWasteConsumerMaster getTbSolidWasteConsumerMaster() {
        return tbSolidWasteConsumerMaster;
    }

    public void setTbSolidWasteConsumerMaster(SolidWasteConsumerMaster tbSolidWasteConsumerMaster) {
        this.tbSolidWasteConsumerMaster = tbSolidWasteConsumerMaster;
    }

    public Long getSwHomeComItem() {
        return swHomeComItem;
    }

    public void setSwHomeComItem(Long swHomeComItem) {
        this.swHomeComItem = swHomeComItem;
    }

    public Long getSwHomeCompCollection() {
        return swHomeCompCollection;
    }

    public void setSwHomeCompCollection(Long swHomeCompCollection) {
        this.swHomeCompCollection = swHomeCompCollection;
    }

    public Long getSwHomeCompPepared() {
        return swHomeCompPepared;
    }

    public void setSwHomeCompPepared(Long swHomeCompPepared) {
        this.swHomeCompPepared = swHomeCompPepared;
    }

    public Date getSwHomeCompDate() {
        return swHomeCompDate;
    }

    public void setSwHomeCompDate(Date swHomeCompDate) {
        this.swHomeCompDate = swHomeCompDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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
    
    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_HOMECOUMPOSE", "SW_HCOM_ID" };
    }

}
