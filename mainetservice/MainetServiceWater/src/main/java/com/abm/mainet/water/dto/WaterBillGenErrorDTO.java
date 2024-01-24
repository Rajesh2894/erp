package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Rahul.Yadav
 *
 */
public class WaterBillGenErrorDTO implements Serializable {

    private static final long serialVersionUID = -6864177993212943611L;

    private Long errId;

    private Long csIdn;

    private Date errDate;

    private String errMsg;

    private Long userId;

    private Long updatedBy;

    private Date updatedDate;

    private Long orgId;

    private String connumber;

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

    public String getConnumber() {
        return connumber;
    }

    public void setConnumber(final String connumber) {
        this.connumber = connumber;
    }

}
