package com.abm.mainet.common.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DeptWiseAnswerDataLQPEntity {

	@Id
	@Column(name = "num")
	private int id;

	@Column(name = "question_id")
	private String questionId;

	@Column(name = "QUESTION_DATE")
	private String questionDate;

	@Column(name = "INWARD_NO")
	private String inwardNo;

	@Column(name = "QUESTION_TYPE")
	private String questionType;

	@Column(name = "RAISED_BY_ASSEMBLY")
	private String raisedByAssembly;

	@Column(name = "DP_DEPTDESC")
	private String deptName;

	@Column(name = "QUESTION_SUBJECT")
	private String questionSubject;

	@Column(name = "MLA_NAME")
	private String mlaName;

	@Column(name = "QUESTION")
	private String question;

	@Column(name = "MEETING_DATE")
	private String meetingDate;

	@Column(name = "DEADLINE_DATE")
	private String deadlineDate;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "Question_CREATED_BY")
	private String questionCreatedBy;

	@Column(name = "Questionee")
	private String questionee;

	@Column(name = "REOPEN_DATE")
	private String reopenDate;

	@Column(name = "ANSWER_DATE")
	private String answerDate;

	@Column(name = "ANSWER")
	private String answer;

	@Column(name = "ANSWER_REMARK")
	private String answerRemark;

	@Column(name = "Question_Answered_BY")
	private String questionAnsweredBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionDate() {
		return questionDate;
	}

	public void setQuestionDate(String questionDate) {
		this.questionDate = questionDate;
	}

	public String getInwardNo() {
		return inwardNo;
	}

	public void setInwardNo(String inwardNo) {
		this.inwardNo = inwardNo;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getRaisedByAssembly() {
		return raisedByAssembly;
	}

	public void setRaisedByAssembly(String raisedByAssembly) {
		this.raisedByAssembly = raisedByAssembly;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getQuestionSubject() {
		return questionSubject;
	}

	public void setQuestionSubject(String questionSubject) {
		this.questionSubject = questionSubject;
	}

	public String getMlaName() {
		return mlaName;
	}

	public void setMlaName(String mlaName) {
		this.mlaName = mlaName;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(String deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getQuestionCreatedBy() {
		return questionCreatedBy;
	}

	public void setQuestionCreatedBy(String questionCreatedBy) {
		this.questionCreatedBy = questionCreatedBy;
	}

	public String getQuestionee() {
		return questionee;
	}

	public void setQuestionee(String questionee) {
		this.questionee = questionee;
	}

	public String getReopenDate() {
		return reopenDate;
	}

	public void setReopenDate(String reopenDate) {
		this.reopenDate = reopenDate;
	}

	public String getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(String answerDate) {
		this.answerDate = answerDate;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswerRemark() {
		return answerRemark;
	}

	public void setAnswerRemark(String answerRemark) {
		this.answerRemark = answerRemark;
	}

	public String getQuestionAnsweredBy() {
		return questionAnsweredBy;
	}

	public void setQuestionAnsweredBy(String questionAnsweredBy) {
		this.questionAnsweredBy = questionAnsweredBy;
	}

	@Override
	public String toString() {
		return "DeptWiseAnswerDataLQPEntity [id=" + id + ", questionId=" + questionId + ", questionDate=" + questionDate
				+ ", inwardNo=" + inwardNo + ", questionType=" + questionType + ", raisedByAssembly=" + raisedByAssembly
				+ ", deptName=" + deptName + ", questionSubject=" + questionSubject + ", mlaName=" + mlaName
				+ ", question=" + question + ", meetingDate=" + meetingDate + ", deadlineDate=" + deadlineDate
				+ ", status=" + status + ", questionCreatedBy=" + questionCreatedBy + ", questionee=" + questionee
				+ ", reopenDate=" + reopenDate + ", answerDate=" + answerDate + ", answer=" + answer + ", answerRemark="
				+ answerRemark + ", questionAnsweredBy=" + questionAnsweredBy + "]";
	}

}
