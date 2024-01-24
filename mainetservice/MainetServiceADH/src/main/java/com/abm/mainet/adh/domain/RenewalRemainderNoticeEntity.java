package com.abm.mainet.adh.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author cherupelli.srikanth
 * @since 27 September 2019
 */
@Entity
@Table(name = "TB_ADH_NOTICE")
public class RenewalRemainderNoticeEntity implements Serializable {

    private static final long serialVersionUID = -9063455366628750325L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "notice_id")
    private Long noticeId;

    @Column(name = "notice_no")
    private String noticeNo;

    @Column(name = "AGN_ID")
    private Long agencyId;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "notice_type")
    private Long noticeType;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "lg_ip_mac")
    private String lgIpMac;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    public Long getNoticeId() {
	return noticeId;
    }

    public void setNoticeId(Long noticeId) {
	this.noticeId = noticeId;
    }

    public String getNoticeNo() {
	return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
	this.noticeNo = noticeNo;
    }

    public Long getAgencyId() {
	return agencyId;
    }

    public void setAgencyId(Long agencyId) {
	this.agencyId = agencyId;
    }

    public String getRemarks() {
	return remarks;
    }

    public void setRemarks(String remarks) {
	this.remarks = remarks;
    }

    public Long getNoticeType() {
	return noticeType;
    }

    public void setNoticeType(Long noticeType) {
	this.noticeType = noticeType;
    }

    public Long getOrgId() {
	return orgId;
    }

    public void setOrgId(Long orgId) {
	this.orgId = orgId;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
	this.createdBy = createdBy;
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
	return new String[] { "ADH", "TB_ADH_NOTICE", "notice_id" };
    }
}
