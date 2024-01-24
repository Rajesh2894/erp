package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_DEPOADV_MAS")
public class AccountDepositeAndAdvnMasterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DAMPP_ID", nullable = false)
    private Long dampId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "CPD_ID_HDM", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity tbComparamDetHdm;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "CPD_ID_DTY_ATY", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity tbComparamDetDtyAty;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "CPD_ID", referencedColumnName = "CPD_ID")
    private TbComparamDetEntity tbComparamDet;

    @Column(name = "DEPT_ID", precision = 12, scale = 0, nullable = true)
    private Long dept;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "FUND_ID", referencedColumnName = "FUND_ID")
    private AccountFundMasterEntity fundMasterEntity;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "FUNCTION_ID", referencedColumnName = "FUNCTION_ID")
    private AccountFunctionMasterEntity functionMasterEntity;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "FIELD_ID", referencedColumnName = "FIELD_ID")
    private AccountFieldMasterEntity fieldMasterEntity;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "PAC_HEAD_ID", referencedColumnName = "PAC_HEAD_ID")
    private AccountHeadPrimaryAccountCodeMasterEntity primaryCodeEntity;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "SAC_HEAD_ID", referencedColumnName = "SAC_HEAD_ID")
    private AccountHeadSecondaryAccountCodeMasterEntity secondaryCodeEntity;

    @Column(name = "REMARK_DESCRIPTION", length = 500, nullable = true)
    private String remarkDesc;

    @Column(name = "STATUS", length = 1, nullable = true)
    private String status;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "USER_ID", nullable = false)
    private Long createdBy;

    @Column(name = "LANG_ID", nullable = false)
    private int langId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE", nullable = false)
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "FI04_N1", length = 15)
    private Long fi04N1;

    @Column(name = "FI04_V1", length = 100)
    private String fi04V1;

    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(final Long userId) {
        createdBy = userId;
    }

    public Long getUserId() {
        return createdBy;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public int getLangId() {
        return langId;
    }

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : LG_IP_MAC ( VARCHAR2 )
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    // --- DATABASE MAPPING : LG_IP_MAC_UPD ( VARCHAR2 )
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_FUNCTION_MASTER", "FUNCTION_ID" };
    }

    /**
     * @return the dampId
     */
    public Long getDampId() {
        return dampId;
    }

    /**
     * @param dampId the dampId to set
     */
    public void setDampId(final Long dampId) {
        this.dampId = dampId;
    }

    /**
     * @return the tbComparamDetHdm
     */
    public TbComparamDetEntity getTbComparamDetHdm() {
        return tbComparamDetHdm;
    }

    /**
     * @param tbComparamDetHdm the tbComparamDetHdm to set
     */
    public void setTbComparamDetHdm(final TbComparamDetEntity tbComparamDetHdm) {
        this.tbComparamDetHdm = tbComparamDetHdm;
    }

    /**
     * @return the tbComparamDetDtyAty
     */
    public TbComparamDetEntity getTbComparamDetDtyAty() {
        return tbComparamDetDtyAty;
    }

    /**
     * @param tbComparamDetDtyAty the tbComparamDetDtyAty to set
     */
    public void setTbComparamDetDtyAty(final TbComparamDetEntity tbComparamDetDtyAty) {
        this.tbComparamDetDtyAty = tbComparamDetDtyAty;
    }

    /**
     * @return the tbComparamDet
     */
    public TbComparamDetEntity getTbComparamDet() {
        return tbComparamDet;
    }

    /**
     * @param tbComparamDet the tbComparamDet to set
     */
    public void setTbComparamDet(final TbComparamDetEntity tbComparamDet) {
        this.tbComparamDet = tbComparamDet;
    }

    /**
     * @return the dept
     */
    public Long getDept() {
        return dept;
    }

    /**
     * @param dept the dept to set
     */
    public void setDept(final Long dept) {
        this.dept = dept;
    }

    /**
     * @return the fundMasterEntity
     */
    public AccountFundMasterEntity getFundMasterEntity() {
        return fundMasterEntity;
    }

    /**
     * @param fundMasterEntity the fundMasterEntity to set
     */
    public void setFundMasterEntity(final AccountFundMasterEntity fundMasterEntity) {
        this.fundMasterEntity = fundMasterEntity;
    }

    /**
     * @return the functionMasterEntity
     */
    public AccountFunctionMasterEntity getFunctionMasterEntity() {
        return functionMasterEntity;
    }

    /**
     * @param functionMasterEntity the functionMasterEntity to set
     */
    public void setFunctionMasterEntity(
            final AccountFunctionMasterEntity functionMasterEntity) {
        this.functionMasterEntity = functionMasterEntity;
    }

    /**
     * @return the fieldMasterEntity
     */
    public AccountFieldMasterEntity getFieldMasterEntity() {
        return fieldMasterEntity;
    }

    /**
     * @param fieldMasterEntity the fieldMasterEntity to set
     */
    public void setFieldMasterEntity(final AccountFieldMasterEntity fieldMasterEntity) {
        this.fieldMasterEntity = fieldMasterEntity;
    }

    /**
     * @return the primaryCodeEntity
     */
    public AccountHeadPrimaryAccountCodeMasterEntity getPrimaryCodeEntity() {
        return primaryCodeEntity;
    }

    /**
     * @param primaryCodeEntity the primaryCodeEntity to set
     */
    public void setPrimaryCodeEntity(
            final AccountHeadPrimaryAccountCodeMasterEntity primaryCodeEntity) {
        this.primaryCodeEntity = primaryCodeEntity;
    }

    /**
     * @return the secondaryCodeEntity
     */
    public AccountHeadSecondaryAccountCodeMasterEntity getSecondaryCodeEntity() {
        return secondaryCodeEntity;
    }

    /**
     * @param secondaryCodeEntity the secondaryCodeEntity to set
     */
    public void setSecondaryCodeEntity(
            final AccountHeadSecondaryAccountCodeMasterEntity secondaryCodeEntity) {
        this.secondaryCodeEntity = secondaryCodeEntity;
    }

    /**
     * @return the remarkDesc
     */
    public String getRemarkDesc() {
        return remarkDesc;
    }

    /**
     * @param remarkDesc the remarkDesc to set
     */
    public void setRemarkDesc(final String remarkDesc) {
        this.remarkDesc = remarkDesc;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * @return the fi04N1
     */
    public Long getFi04N1() {
        return fi04N1;
    }

    /**
     * @param fi04n1 the fi04N1 to set
     */
    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    /**
     * @return the fi04D1
     */
    public Date getFi04D1() {
        return fi04D1;
    }

    /**
     * @param fi04d1 the fi04D1 to set
     */
    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    /**
     * @return the fi04Lo1
     */
    public String getFi04Lo1() {
        return fi04Lo1;
    }

    /**
     * @param fi04Lo1 the fi04Lo1 to set
     */
    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    /**
     * @return the fi04V1
     */
    public String getFi04V1() {
        return fi04V1;
    }

    /**
     * @param fi04v1 the fi04V1 to set
     */
    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

}
