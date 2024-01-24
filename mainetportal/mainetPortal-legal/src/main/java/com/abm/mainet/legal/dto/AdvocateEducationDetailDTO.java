package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class AdvocateEducationDetailDTO implements Serializable {

	private static final long serialVersionUID = -8631332616459928207L;
	
    private Long eduId;
	
	private String qualificationCourse;
	
	private String instituteState;
	
	private String boardUniversity;
	 
	private String result;
	
	private Long passingYear;
	
	private Double percentage;
	 
	private Long orgid;
	
	private Long createdBy;
	
	private Date createdDate;
	
	private Long updatedBy;
	 
	private Date updatedDate;
	
	private String lgIpMac;
	 
	private String lgIpMacUpd;


	public Long getEduId() {
		return eduId;
	}

	public void setEduId(Long eduId) {
		this.eduId = eduId;
	}

	public String getQualificationCourse() {
		return qualificationCourse;
	}

	public void setQualificationCourse(String qualificationCourse) {
		this.qualificationCourse = qualificationCourse;
	}

	public String getInstituteState() {
		return instituteState;
	}

	public void setInstituteState(String instituteState) {
		this.instituteState = instituteState;
	}

	public String getBoardUniversity() {
		return boardUniversity;
	}

	public void setBoardUniversity(String boardUniversity) {
		this.boardUniversity = boardUniversity;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getPassingYear() {
		return passingYear;
	}

	public void setPassingYear(Long passingYear) {
		this.passingYear = passingYear;
	}

	

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	
	
	
	

}
