package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_DEPARTMENT_HIST")
public class DepartmentHistory implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_DEPTID", nullable = false, precision = 12, scale = 0)
    private Long hDeptid;

    @Column(name = "DP_DEPTID")
    private Long dpDeptid;

    @Column(name = "DP_DEPTCODE")
    private String dpDeptcode;

    @Column(name = "DP_DEPTDESC")
    private String dpDeptdesc;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "LANG_ID")
    private Short langId;

    @Column(name = "LMODDATE")
    private Date lmoddate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DP_NAME_MAR")
    private String dpNameMar;

    @Column(name = "SUB_DEPT_FLG")
    private String subDeptFlg;

    @Column(name = "UPDATED_BY")
    private Integer updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "DP_SMFID")
    private Long dpSmfid;

    @Column(name = "DP_CHECK")
    private String dpCheck;

    @Column(name = "DP_PREFIX")
    private String dpPrefix;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "H_STATUS")
    private String hStatus;;

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public String getDpDeptcode() {
        return dpDeptcode;
    }

    public void setDpDeptcode(final String dpDeptcode) {
        this.dpDeptcode = dpDeptcode;
    }

    public String getDpDeptdesc() {
        return dpDeptdesc;
    }

    public void setDpDeptdesc(final String dpDeptdesc) {
        this.dpDeptdesc = dpDeptdesc;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public Short getLangId() {
        return langId;
    }

    public void setLangId(final Short langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getDpNameMar() {
        return dpNameMar;
    }

    public void setDpNameMar(final String dpNameMar) {
        this.dpNameMar = dpNameMar;
    }

    public String getSubDeptFlg() {
        return subDeptFlg;
    }

    public void setSubDeptFlg(final String subDeptFlg) {
        this.subDeptFlg = subDeptFlg;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getDpSmfid() {
        return dpSmfid;
    }

    public void setDpSmfid(final Long dpSmfid) {
        this.dpSmfid = dpSmfid;
    }

    public String getDpCheck() {
        return dpCheck;
    }

    public void setDpCheck(final String dpCheck) {
        this.dpCheck = dpCheck;
    }

    public String getDpPrefix() {
        return dpPrefix;
    }

    public void setDpPrefix(final String dpPrefix) {
        this.dpPrefix = dpPrefix;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long gethDeptid() {
        return hDeptid;
    }

    public void sethDeptid(final Long hDeptid) {
        this.hDeptid = hDeptid;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(final String hStatus) {
        this.hStatus = hStatus;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "TB_DEPARTMENT_HIST", "H_DEPTID" };
    }
}
