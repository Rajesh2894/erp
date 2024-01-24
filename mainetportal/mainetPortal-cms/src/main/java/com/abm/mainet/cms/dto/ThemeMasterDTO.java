package com.abm.mainet.cms.dto;

import java.io.Serializable;
import java.util.Date;

public class ThemeMasterDTO implements Serializable {
    private static final long serialVersionUID = 4619668733455738539L;

    private Long themeId;
    private String section;
    private String status;
    private long orgid;
    private long createdBy;
    private Date createdDate;
    private long updatedBy;
    private Date updatedDate;
    private String lgipmac;
    private String lgipmacupd;

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (null == status || status.length() == 0) {
            status = "I";
        }
        this.status = status;
    }

    public long getOrgid() {
        return orgid;
    }

    public void setOrgid(long orgid) {
        this.orgid = orgid;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgipmac() {
        return lgipmac;
    }

    public void setLgipmac(String lgipmac) {
        this.lgipmac = lgipmac;
    }

    public String getLgipmacupd() {
        return lgipmacupd;
    }

    public void setLgipmacupd(String lgipmacupd) {
        this.lgipmacupd = lgipmacupd;
    }

}
