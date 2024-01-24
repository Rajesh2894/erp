package com.abm.mainet.cms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_EIP_FAQ_HIST")
public class FrequentlyAskedQuestionsHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5655966570137872816L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "FAQ_ID_H", nullable = false, precision = 12, scale = 0)
    private long faqHistId;

    @Column(name = "FAQ_ID", nullable = false, precision = 12, scale = 0)
    private long faqId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false)
    @ForeignKey(name = "FK_FAQ_ORGID")
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    @ForeignKey(name = "FK_FAQ_EMPID")
    private Employee userId;

    @Column(name = "ANSWER_EN", length = 4000, nullable = false)
    private String answerEn;											// ANSWER_EN
                            											// VARCHAR2(500)
                            											// N
    @Column(name = "ANSWER_REG", length = 4000, nullable = true)
    private String answerReg;											// ANSWER_REG
    // NVARCHAR2(1000)
    // Y
    @Column(name = "QUESTION_EN", length = 250, nullable = false)
    private String questionEn;										// QUESTION_EN
                              										// VARCHAR2(250)
                              										// N
    @Column(name = "QUESTION_REG", length = 1000, nullable = true)
    private String questionReg;										// QUESTION_REG
                               										// NVARCHAR2(1000)
                               										// Y

    @Column(name = "SEQ_NO", precision = 19, scale = 0, nullable = false)
    private long seqNo;												// SEQ_NO
                       												// NUMBER(19)
                       												// N

    @Column(name = "ISDELETED", length = 1, nullable = false)
    private String isDeleted;											// ISDELETED
                             											// VARCHAR2(1)
                             											// N 'N'
    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private int langId;												// LANG_ID
                       												// NUMBER(7)
                       												// N

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;											// CREATED_DATE
                          											// DATE
    @JsonIgnore																					// N
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "UPDATED_BY", nullable = true)
    @ForeignKey(name = "FK_FAQ_UPD_EMPID")
    private Employee updatedBy;											// UPDATED_BY
                               											// NUMBER(7)
                               											// Y

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;										// UPDATED_DATE
                             										// DATE
                             										// Y

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;											// LG_IP_MAC
                           											// VARCHAR2(100)
                           											// Y

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;										// LG_IP_MAC_UPD
                              										// VARCHAR2(100)
                              										// Y
    @Column(name = "UNAUTHENTICATION", length = 100, nullable = true)
    private String rejectionresion;

    @Column(name = "H_STATUS", length = 1)
    private String status;
    
    @Column(name = "REMARK", length = 1000, nullable = false)
    private String remark;

    public String getRejectionresion() {
        return rejectionresion;
    }

    public void setRejectionresion(final String rejectionresion) {
        this.rejectionresion = rejectionresion;
    }

    @Column(name = "CHEKER_FLAG", length = 1, nullable = false)
    private String chekkerflag;

    public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    /**
     * @return the faqId
     */
    public long getFaqId() {
        return faqId;
    }

    /**
     * @param faqId the faqId to set
     */
    public void setFaqId(final long faqId) {
        this.faqId = faqId;
    }

    /**
     * @return the answerEn
     */
    public String getAnswerEn() {
        return answerEn;
    }

    /**
     * @param answerEn the answerEn to set
     */
    public void setAnswerEn(final String answerEn) {
        this.answerEn = answerEn;
    }

    /**
     * @return the answerReg
     */
    public String getAnswerReg() {
        return answerReg;
    }

    /**
     * @param answerReg the answerReg to set
     */
    public void setAnswerReg(final String answerReg) {
        this.answerReg = answerReg;
    }

    /**
     * @return the questionEn
     */
    public String getQuestionEn() {
        return questionEn;
    }

    /**
     * @param questionEn the questionEn to set
     */
    public void setQuestionEn(final String questionEn) {
        this.questionEn = questionEn;
    }

    /**
     * @return the questionReg
     */
    public String getQuestionReg() {
        return questionReg;
    }

    /**
     * @param questionReg the questionReg to set
     */
    public void setQuestionReg(final String questionReg) {
        this.questionReg = questionReg;
    }

    /**
     * @return the seqNo
     */
    public long getSeqNo() {
        return seqNo;
    }

    /**
     * @param seqNo the seqNo to set
     */
    public void setSeqNo(final long seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * @return the isDeleted
     */
    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the updatedBy
     */
    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    @Override
    public Employee getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    @Override
    public int getLangId() {
        return langId;
    }

    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("FrequentlyAskedQuestions [faqId=");
        builder.append(faqId);
        builder.append(", answerEn=");
        builder.append(answerEn);
        builder.append(", answerReg=");
        builder.append(answerReg);
        builder.append(", questionEn=");
        builder.append(questionEn);
        builder.append(", questionReg=");
        builder.append(questionReg);
        builder.append(", seqNo=");
        builder.append(seqNo);
        builder.append(", isDeleted=");
        builder.append(isDeleted);
        builder.append(", langId=");
        builder.append(langId);
        builder.append(", lmodDate=");
        builder.append(lmodDate);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", lgIpMac=");
        builder.append(lgIpMac);
        builder.append(", lgIpMacUpd=");
        builder.append(lgIpMacUpd);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public long getId() {

        return getFaqId();
    }

    public long getFaqHistId() {
        return faqHistId;
    }

    public void setFaqHistId(long faqHistId) {
        this.faqHistId = faqHistId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_FAQ_HIST", "faqHistId" };
    }
    
    /**
     * @return the remark
     */
	public String getRemark() {
		return remark;
	}

	 /**
     * @param remark the remark to set
     */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
