package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
public class WorkTimeMasterDto implements Serializable{
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 7317376634302405619L;
		private Long wrId;
	    private Date wrEntryDate;
	    private Date wrStartTime;
	    private Date wrEndTime;
	    private Long wrWeekType;
	    private String wrWorkWeek;
	    private String wrOddWorkWeek;
	    private String wrEvenWorkWeek;
	    private Date wrValidEndDate;
	    private Long orgId;
	    private Long createdBy;
	    private Long updatedBy;
	    private Date createdDate;
	    private Date updatedDate;
	    @JsonIgnore
	    @Size(max=100)
	    private String lgIpMac;
	    @JsonIgnore
	    @Size(max=100)
	    private String lgIpMacUpd;
	    private String wrStartTimeString;
	    private String wrEndTimeString;
	    private String worrkWeekFlag;
	    
		public Long getWrId() {
			return wrId;
		}
		public void setWrId(Long wrId) {
			this.wrId = wrId;
		}
		public Date getWrEntryDate() {
			return wrEntryDate;
		}
		public void setWrEntryDate(Date wrEntryDate) {
			this.wrEntryDate = wrEntryDate;
		}
		public Date getWrStartTime() {
			return wrStartTime;
		}
		public void setWrStartTime(Date wrStartTime) {
			this.wrStartTime = wrStartTime;
		}
		public Date getWrEndTime() {
			return wrEndTime;
		}
		public void setWrEndTime(Date wrEndTime) {
			this.wrEndTime = wrEndTime;
		}
		public Long getWrWeekType() {
			return wrWeekType;
		}
		public void setWrWeekType(Long wrWeekType) {
			this.wrWeekType = wrWeekType;
		}
		public String getWrWorkWeek() {
			return wrWorkWeek;
		}
		public void setWrWorkWeek(String wrWorkWeek) {
			this.wrWorkWeek = wrWorkWeek;
		}
		public String getWrOddWorkWeek() {
			return wrOddWorkWeek;
		}
		public void setWrOddWorkWeek(String wrOddWorkWeek) {
			this.wrOddWorkWeek = wrOddWorkWeek;
		}
		public String getWrEvenWorkWeek() {
			return wrEvenWorkWeek;
		}
		public void setWrEvenWorkWeek(String wrEvenWorkWeek) {
			this.wrEvenWorkWeek = wrEvenWorkWeek;
		}
		public Date getWrValidEndDate() {
			return wrValidEndDate;
		}
		public void setWrValidEndDate(Date wrValidEndDate) {
			this.wrValidEndDate = wrValidEndDate;
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
		public Long getUpdatedBy() {
			return updatedBy;
		}
		public void setUpdatedBy(Long updatedBy) {
			this.updatedBy = updatedBy;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
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
		public String getWrStartTimeString() {
			return wrStartTimeString;
		}
		public void setWrStartTimeString(String wrStartTimeString) {
			this.wrStartTimeString = wrStartTimeString;
		}
		public String getWrEndTimeString() {
			return wrEndTimeString;
		}
		public void setWrEndTimeString(String wrEndTimeString) {
			this.wrEndTimeString = wrEndTimeString;
		}
		public String getWorrkWeekFlag() {
			return worrkWeekFlag;
		}
		public void setWorrkWeekFlag(String worrkWeekFlag) {
			this.worrkWeekFlag = worrkWeekFlag;
		}
	    
	    

}
