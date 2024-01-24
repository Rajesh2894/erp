package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Rahul.Yadav
 * @since 15 Jun 2016
 */
@Entity
@Table(name = "TB_WT_BILL_GEN_ERROR")
public class TbWtBillGenError implements Serializable {
    private static final long serialVersionUID = -7083409209468312505L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ERR_ID", precision = 12, scale = 0, nullable = false)
    private Long errId;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = true)
    private Long csIdn;

    @Column(name = "ERR_DATE", nullable = true)
    private Date errDate;

    @Column(name = "ERR_MSG", length = 200, nullable = true)
    private String errMsg;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Date getErrDate() {
        return errDate;
    }

    public void setErrDate(final Date errDate) {
        this.errDate = errDate;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(final String errMsg) {
        this.errMsg = errMsg;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getErrId() {
        return errId;
    }

    public void setErrId(final Long errId) {
        this.errId = errId;
    }

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_BILL_GEN_ERROR", "ERR_ID" };
    }

}