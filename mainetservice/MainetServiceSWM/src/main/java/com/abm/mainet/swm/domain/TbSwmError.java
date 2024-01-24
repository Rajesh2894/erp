package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Ajay.Kumar
 *
 */
@Entity
@Table(name = "TB_SW_ERROR")
public class TbSwmError implements Serializable {

    private static final long serialVersionUID = 6590891425418298006L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ERR_ID")
    private Long errId;

    @Column(name = "ERR_LABEL1", nullable = true, length = 500)
    private String errLabel1;

    @Column(name = "ERR_LABEL2", nullable = true, length = 500)
    private String errLabel2;

    @Column(name = "ERR_LABEL3", nullable = true, length = 500)
    private String errLabel3;

    @Column(name = "ERR_DATA", nullable = true, length = 2000)
    private String errData;

    @Column(name = "ERR_DESCRIPTION", nullable = true, length = 2000)
    private String errDescription;

    @Column(name = "FILE_NAME ", nullable = true, length = 500)
    private String fileName;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "ERR_FLAG", length = 3)
    private String errFlag;

    public Long getErrId() {
        return errId;
    }

    public void setErrId(Long errId) {
        this.errId = errId;
    }

    public String getErrLabel1() {
        return errLabel1;
    }

    public void setErrLabel1(String errLabel1) {
        this.errLabel1 = errLabel1;
    }

    public String getErrLabel2() {
        return errLabel2;
    }

    public void setErrLabel2(String errLabel2) {
        this.errLabel2 = errLabel2;
    }

    public String getErrLabel3() {
        return errLabel3;
    }

    public void setErrLabel3(String errLabel3) {
        this.errLabel3 = errLabel3;
    }

    public String getErrData() {
        return errData;
    }

    public void setErrData(String errData) {
        this.errData = errData;
    }

    public String getErrDescription() {
        return errDescription;
    }

    public void setErrDescription(String errDescription) {
        this.errDescription = errDescription;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getErrFlag() {
        return errFlag;
    }

    public void setErrFlag(String errFlag) {
        this.errFlag = errFlag;
    }

    public String[] getPkValues() {
        return new String[] { "SWM", "TB_SW_ERROR", "ERR_ID" };
    }

}
