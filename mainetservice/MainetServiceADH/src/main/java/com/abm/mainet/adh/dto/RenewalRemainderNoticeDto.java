package com.abm.mainet.adh.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cherupelli.srikanth
 * @since 27 September 2019
 */
public class RenewalRemainderNoticeDto implements Serializable {

    private static final long serialVersionUID = -3903405145825120109L;

    private Long noticeId;

    private String noticeNo;

    private Long agencyId;

    private String remarks;

    private Long noticeType;

    private Long orgId;

    private Date createdDate;

    private Long createdBy;

    private String lgIpMac;

    private Long updatedBy;

    private Date updatedDate;

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
