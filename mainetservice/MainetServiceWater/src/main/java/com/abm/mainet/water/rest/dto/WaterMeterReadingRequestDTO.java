package com.abm.mainet.water.rest.dto;

import java.io.Serializable;

public class WaterMeterReadingRequestDTO implements Serializable {
    private static final long serialVersionUID = -1456765128009861135L;

    private Long orgid;

    private Long csIdn;

    private String csCcn;

    private String csRemark;

    private Long userId;

    private int langId;

    private String status;

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public String getCsCcn() {
        return csCcn;
    }

    public void setCsCcn(final String csCcn) {
        this.csCcn = csCcn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public String getCsRemark() {
        return csRemark;
    }

    public void setCsRemark(final String csRemark) {
        this.csRemark = csRemark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
