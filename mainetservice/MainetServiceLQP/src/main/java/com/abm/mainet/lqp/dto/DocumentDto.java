package com.abm.mainet.lqp.dto;

import java.io.Serializable;

public class DocumentDto implements Serializable {

    private static final long serialVersionUID = 8433677147466151996L;

    private String remark;
    private String attPath;
    private Long attId;
    private String attFname;
    private String attBy;
    private String dmsDocId;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttPath() {
        return attPath;
    }

    public void setAttPath(String attPath) {
        this.attPath = attPath;
    }

    public Long getAttId() {
        return attId;
    }

    public void setAttId(Long attId) {
        this.attId = attId;
    }

    public String getAttFname() {
        return attFname;
    }

    public void setAttFname(String attFname) {
        this.attFname = attFname;
    }

    public String getAttBy() {
        return attBy;
    }

    public void setAttBy(String attBy) {
        this.attBy = attBy;
    }

    public String getDmsDocId() {
        return dmsDocId;
    }

    public void setDmsDocId(String dmsDocId) {
        this.dmsDocId = dmsDocId;
    }

}
