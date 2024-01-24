package com.abm.mainet.intranet.dto.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.intranet.domain.Choice;
import com.abm.mainet.intranet.domain.Poll;
import com.abm.mainet.intranet.domain.PollDetails;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class IntranetPollDTO implements Serializable{
	
private static final long serialVersionUID = 2110704719241314572L;

private Long pollid;
private PollDetails	 id;
private Poll poll;
private String question;
private List<Choice> choices = new ArrayList<>();
private Long orgid;
private Long updatedBy;
private Date updatedDate;
private Long userId;
private int langId;
private String lgIpMac;
private String lgIpMacUpd;
private Date lmoddate;
private Long createdBy;
private String pollStatus;
private String text;
private String pollName;
private Long pollDeptId;
private Date pollFromDate;
private Date pollToDate;


public Long getPollid() {
	return pollid;
}
public void setPollid(Long pollid) {
	this.pollid = pollid;
}
public PollDetails getId() {
	return id;
}
public void setId(PollDetails id) {
	this.id = id;
}
public Poll getPoll() {
	return poll;
}
public void setPoll(Poll poll) {
	this.poll = poll;
}
public String getQuestion() {
	return question;
}
public void setQuestion(String question) {
	this.question = question;
}
public List<Choice> getChoices() {
	return choices;
}
public void setChoices(List<Choice> choices) {
	this.choices = choices;
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
public String getPollStatus() {
	return pollStatus;
}
public void setPollStatus(String pollStatus) {
	this.pollStatus = pollStatus;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public String getPollName() {
	return pollName;
}
public void setPollName(String pollName) {
	this.pollName = pollName;
}
public Long getPollDeptId() {
	return pollDeptId;
}
public void setPollDeptId(Long pollDeptId) {
	this.pollDeptId = pollDeptId;
}
public Date getPollFromDate() {
	return pollFromDate;
}
public void setPollFromDate(Date pollFromDate) {
	this.pollFromDate = pollFromDate;
}
public Date getPollToDate() {
	return pollToDate;
}
public void setPollToDate(Date pollToDate) {
	this.pollToDate = pollToDate;
}

    


	
}
