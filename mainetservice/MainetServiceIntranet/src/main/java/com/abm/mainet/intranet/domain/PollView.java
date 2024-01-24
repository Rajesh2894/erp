package com.abm.mainet.intranet.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "tb_poll_view")
public class PollView {
	
		@Id
		@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
		@GeneratedValue(generator = "MyCustomGenerator")
		@Column(name = "poll_View_id", nullable = false, precision = 10)
		private Long pid;
		
		@Column(name="poll_id")
		private Long pollId;
		
		@Column(name = "poll_que")
		private String pollQue;
		
		@Column(name="choice_id")
		private Long choiceId;
		
		@Column(name = "choice_desc")
		private String choiceDesc;
		
		@Column(name = "choice_val")
		private String choiceDescVal;
		
		@Column(name = "loggedin_empid")
		private Long loggedinEmpId;
	    
		@Column(name="ORG_ID", nullable = false, precision = 10)
		private Long orgid;
	    
		@Column(name="UPDATED_BY")
		private Long updatedBy;

		@Column(name="UPDATED_DATE")
		private Date updatedDate;
		
		@Column(name = "USER_ID", nullable = false, precision = 10)
		private Long userId;
		
		@Column(name = "LANG_ID", nullable = false, precision = 10)
		private int langId;
		
		@Column(name = "LG_IP_MAC", length = 100)
		private String lgIpMac;

		@Column(name = "LG_IP_MAC_UPD", length = 100)
		private String lgIpMacUpd;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(nullable = false)
		private Date lmoddate;

	    @CreatedBy
	    @Column(name="created_by")
	    private Long createdBy;
	    
		public String getChoiceDesc() {
			return choiceDesc;
		}

		public void setChoiceDesc(String choiceDesc) {
			this.choiceDesc = choiceDesc;
		}

		public String getChoiceDescVal() {
			return choiceDescVal;
		}

		public void setChoiceDescVal(String choiceDescVal) {
			this.choiceDescVal = choiceDescVal;
		}

		public String getPollQue() {
			return pollQue;
		}

		public void setPollQue(String pollQue) {
			this.pollQue = pollQue;
		}

		public Long getPid() {
			return pid;
		}

		public void setPid(Long pid) {
			this.pid = pid;
		}

		public Long getPollId() {
			return pollId;
		}

		public void setPollId(Long pollId) {
			this.pollId = pollId;
		}

		public Long getChoiceId() {
			return choiceId;
		}

		public void setChoiceId(Long choiceId) {
			this.choiceId = choiceId;
		}

		public Long getLoggedinEmpId() {
			return loggedinEmpId;
		}

		public void setLoggedinEmpId(Long loggedinEmpId) {
			this.loggedinEmpId = loggedinEmpId;
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

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public int getLangId() {
			return langId;
		}

		public void setLangId(int langId) {
			this.langId = langId;
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

		public Date getLmoddate() {
			return lmoddate;
		}

		public void setLmoddate(Date lmoddate) {
			this.lmoddate = lmoddate;
		}

		public Long getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(Long createdBy) {
			this.createdBy = createdBy;
		}

		public static String[] getPkValues() {
			return new String[] { "HD", "tb_poll_view", "pid" };
		}

		

}
