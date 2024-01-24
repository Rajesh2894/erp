package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author deepika.pimpale
 *
 */
@Entity
@Table(name = "TB_WT_PLUM_MAS")
public class PlumberMaster implements Serializable {
    private static final long serialVersionUID = 2097677752776244932L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PLUM_ID", precision = 12, scale = 0, nullable = false)
    // comments : Primary key of this table,one up generation no.
    private long plumId;

    @Column(name = "PLUM_APPL_DATE", nullable = true)
    private Date plumAppDate;

    @Column(name = "PLUM_LNAME", length = 100, nullable = true)
    private String plumLname;

    @Column(name = "PLUM_FNAME", length = 100, nullable = true)
    private String plumFname;

    @Column(name = "PLUM_MNAME", length = 100, nullable = true)
    private String plumMname;

    @Column(name = "PLUM_SEX", length = 1, nullable = true)
    private String plumSex;

    @Column(name = "PLUM_DOB", nullable = true)
    private String plumDOB;

    @Column(name = "PLUM_CONTACT_PERSONNM", length = 100, nullable = true)
    private String plumContactPersonName;

    @Column(name = "PLUM_LIC_NO", length = 20, nullable = true)
    private String plumLicNo;

    @Column(name = "PLUM_LIC_ISSUE_FLG", length = 1, nullable = true)
    private String plumLicIssueFlag;

    @Column(name = "PLUM_LIC_ISSUE_DATE", nullable = true)
    private String plumLicIssueDate;

    @Column(name = "PLUM_LIC_FROM_DATE", nullable = true)
    private Date plumLicFromDate;

    @Column(name = "PLUM_LIC_TO_DATE", nullable = true)
    private Date plumLicToDate;

    @Column(name = "ORGID", precision = 4, scale = 0, nullable = false)
    // comments : Organization id
    private Long orgid;

    @Column(name = "USER_ID", precision = 7, scale = 0, nullable = true)
    // comments : User id
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    // comments : Language id
    private Long langId;

    @Column(name = "LMODDATE", nullable = true)
    // comments : Last Modification Date
    private Date lmoddate;

    @Column(name = "UPDATED_BY", precision = 7, scale = 0, nullable = true)
    // comments : User id who update the data
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Date on which data is going to update
    private Date updatedDate;

    @Column(name = "PLUM_INTERVIEW_REMARK", length = 200, nullable = true)
    private String plumInterviewRemark;

    @Column(name = "PLUM_INTERVIEW_CLR", length = 1, nullable = true)
    private String plumInterviewClr;

    @Column(name = "PLUM_LIC_TYPE", length = 1, nullable = true)
    private String plumLicType;

    @Column(name = "PLUM_FAT_HUS_NAME", length = 50, nullable = true)
    private String plumFatherName;

    @Column(name = "PLUM_CONTACT_NO", length = 20, nullable = true)
    private String plumContactNo;

    @Column(name = "PLUM_CPDTITLE", length = 30, nullable = true)
    private String plumCpdTitle;

    @Column(name = "PLUM_ADD", length = 30, nullable = true)
    private String plumAddress;

    @Column(name = "PLUM_INTERVIEW_DTTM", nullable = true)
    private Date plumInterviewDate;

    @Column(name = "PLUM_CSCID1", precision = 12, scale = 0, nullable = true)
    private String plumCscid1;

    @Column(name = "PLUM_CSCID2", precision = 12, scale = 0, nullable = true)
    private String plumCscid2;

    @Column(name = "PLUM_CSCID3", precision = 12, scale = 0, nullable = true)
    private String plumCscid3;

    @Column(name = "PLUM_CSCID4", precision = 12, scale = 0, nullable = true)
    private String plumCscid4;

    @Column(name = "PLUM_CSCID5", precision = 12, scale = 0, nullable = true)
    private String plumCscid5;

    @Column(name = "PLUM_OLD_LIC_NO", length = 20, nullable = true)
    private String plumOldLicNo;

    @Column(name = "PORTED", length = 1, nullable = true)
    private String ported;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : stores ip information
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : stores ip information
    private String lgIpMacUpd;

    @Column(name = "PLUM_ENTRY_FLAG", length = 1, nullable = true)
    private String plumEntryFlag;

    /*
     * @Column(name = "WT_V2", length = 100, nullable = true) private String wtv2;
     */

    @Column(name = "WT_V3", length = 100, nullable = true)
    private String wtv3;

    @Column(name = "WT_V4", length = 100, nullable = true)
    private String wtv4;

    @Column(name = "WT_V5", length = 100, nullable = true)
    private String wtv5;

    @Column(name = "WT_N1", length = 15, nullable = true)
    private String wtn1;

    @Column(name = "WT_N2", length = 15, nullable = true)
    private String wtn2;

    @Column(name = "WT_N3", length = 15, nullable = true)
    private String wtn3;

    @Column(name = "WT_N4", length = 15, nullable = true)
    private String wtn4;

    @Column(name = "WT_N5", length = 15, nullable = true)
    private String wtn5;

    @Column(name = "WT_D1", nullable = true)
    private Date wtd1;

    @Column(name = "WT_D2", nullable = true)
    private Date wtd2;

    @Column(name = "WT_D3", nullable = true)
    private Date wtd3;

    @Column(name = "WT_LO1", length = 15, nullable = true)
    private String wtlo1;

    @Column(name = "WT_LO2", length = 15, nullable = true)
    private String wtlo2;

    @Column(name = "WT_LO3", length = 15, nullable = true)
    private String wtlo3;

    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = true)
    private Long apmApplicationId;

    @Column(name = "PLUM_IMAGE", length = 100, nullable = true)
    private String plumImage;

    @Column(name = "PLUM_IMAGE_PATH", length = 500, nullable = true)
    private String plumImagePath;

    @Transient
    private Long serviceId;

    public long getPlumId() {
        return plumId;
    }

    public void setPlumId(final long plumId) {
        this.plumId = plumId;
    }

    public Date getPlumAppDate() {
        return plumAppDate;
    }

    public void setPlumAppDate(final Date plumAppDate) {
        this.plumAppDate = plumAppDate;
    }

    public String getPlumLname() {
        return plumLname;
    }

    public void setPlumLname(final String plumLname) {
        this.plumLname = plumLname;
    }

    public String getPlumFname() {
        return plumFname;
    }

    public void setPlumFname(final String plumFname) {
        this.plumFname = plumFname;
    }

    public String getPlumMname() {
        return plumMname;
    }

    public void setPlumMname(final String plumMname) {
        this.plumMname = plumMname;
    }

    public String getPlumSex() {
        return plumSex;
    }

    public void setPlumSex(final String plumSex) {
        this.plumSex = plumSex;
    }

    public String getPlumDOB() {
        return plumDOB;
    }

    public void setPlumDOB(final String plumDOB) {
        this.plumDOB = plumDOB;
    }

    public String getPlumContactPersonName() {
        return plumContactPersonName;
    }

    public void setPlumContactPersonName(final String plumContactPersonName) {
        this.plumContactPersonName = plumContactPersonName;
    }

    public String getPlumLicNo() {
        return plumLicNo;
    }

    public void setPlumLicNo(final String plumLicNo) {
        this.plumLicNo = plumLicNo;
    }

    public String getPlumLicIssueFlag() {
        return plumLicIssueFlag;
    }

    public void setPlumLicIssueFlag(final String plumLicIssueFlag) {
        this.plumLicIssueFlag = plumLicIssueFlag;
    }

    public String getPlumLicIssueDate() {
        return plumLicIssueDate;
    }

    public void setPlumLicIssueDate(final String plumLicIssueDate) {
        this.plumLicIssueDate = plumLicIssueDate;
    }

    public Date getPlumLicFromDate() {
        return plumLicFromDate;
    }

    public void setPlumLicFromDate(final Date plumLicFromDate) {
        this.plumLicFromDate = plumLicFromDate;
    }

    public Date getPlumLicToDate() {
        return plumLicToDate;
    }

    public void setPlumLicToDate(final Date plumLicToDate) {
        this.plumLicToDate = plumLicToDate;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
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

    public String getPlumInterviewRemark() {
        return plumInterviewRemark;
    }

    public void setPlumInterviewRemark(final String plumInterviewRemark) {
        this.plumInterviewRemark = plumInterviewRemark;
    }

    public String getPlumInterviewClr() {
        return plumInterviewClr;
    }

    public void setPlumInterviewClr(final String plumInterviewClr) {
        this.plumInterviewClr = plumInterviewClr;
    }

    public String getPlumLicType() {
        return plumLicType;
    }

    public void setPlumLicType(final String plumLicType) {
        this.plumLicType = plumLicType;
    }

    public String getPlumFatherName() {
        return plumFatherName;
    }

    public void setPlumFatherName(final String plumFatherName) {
        this.plumFatherName = plumFatherName;
    }

    public String getPlumContactNo() {
        return plumContactNo;
    }

    public void setPlumContactNo(final String plumContactNo) {
        this.plumContactNo = plumContactNo;
    }

    public String getPlumCpdTitle() {
        return plumCpdTitle;
    }

    public void setPlumCpdTitle(final String plumCpdTitle) {
        this.plumCpdTitle = plumCpdTitle;
    }

    public String getPlumAddress() {
        return plumAddress;
    }

    public void setPlumAddress(final String plumAddress) {
        this.plumAddress = plumAddress;
    }

    public Date getPlumInterviewDate() {
        return plumInterviewDate;
    }

    public void setPlumInterviewDate(final Date plumInterviewDate) {
        this.plumInterviewDate = plumInterviewDate;
    }

    public String getPlumCscid1() {
        return plumCscid1;
    }

    public void setPlumCscid1(final String plumCscid1) {
        this.plumCscid1 = plumCscid1;
    }

    public String getPlumCscid2() {
        return plumCscid2;
    }

    public void setPlumCscid2(final String plumCscid2) {
        this.plumCscid2 = plumCscid2;
    }

    public String getPlumCscid3() {
        return plumCscid3;
    }

    public void setPlumCscid3(final String plumCscid3) {
        this.plumCscid3 = plumCscid3;
    }

    public String getPlumCscid4() {
        return plumCscid4;
    }

    public void setPlumCscid4(final String plumCscid4) {
        this.plumCscid4 = plumCscid4;
    }

    public String getPlumCscid5() {
        return plumCscid5;
    }

    public void setPlumCscid5(final String plumCscid5) {
        this.plumCscid5 = plumCscid5;
    }

    public String getPlumOldLicNo() {
        return plumOldLicNo;
    }

    public void setPlumOldLicNo(final String plumOldLicNo) {
        this.plumOldLicNo = plumOldLicNo;
    }

    public String getPorted() {
        return ported;
    }

    public void setPorted(final String ported) {
        this.ported = ported;
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

    public String getPlumEntryFlag() {
        return plumEntryFlag;
    }

    public void setPlumEntryFlag(final String plumEntryFlag) {
        this.plumEntryFlag = plumEntryFlag;
    }

    /*
     * public String getWtv2() { return wtv2; } public void setWtv2(String wtv2) { this.wtv2 = wtv2; }
     */

    public String getWtv3() {
        return wtv3;
    }

    public void setWtv3(final String wtv3) {
        this.wtv3 = wtv3;
    }

    public String getWtv4() {
        return wtv4;
    }

    public void setWtv4(final String wtv4) {
        this.wtv4 = wtv4;
    }

    public String getWtv5() {
        return wtv5;
    }

    public void setWtv5(final String wtv5) {
        this.wtv5 = wtv5;
    }

    public String getWtn1() {
        return wtn1;
    }

    public void setWtn1(final String wtn1) {
        this.wtn1 = wtn1;
    }

    public String getWtn2() {
        return wtn2;
    }

    public void setWtn2(final String wtn2) {
        this.wtn2 = wtn2;
    }

    public String getWtn3() {
        return wtn3;
    }

    public void setWtn3(final String wtn3) {
        this.wtn3 = wtn3;
    }

    public String getWtn4() {
        return wtn4;
    }

    public void setWtn4(final String wtn4) {
        this.wtn4 = wtn4;
    }

    public String getWtn5() {
        return wtn5;
    }

    public void setWtn5(final String wtn5) {
        this.wtn5 = wtn5;
    }

    public Date getWtd1() {
        return wtd1;
    }

    public void setWtd1(final Date wtd1) {
        this.wtd1 = wtd1;
    }

    public Date getWtd2() {
        return wtd2;
    }

    public void setWtd2(final Date wtd2) {
        this.wtd2 = wtd2;
    }

    public Date getWtd3() {
        return wtd3;
    }

    public void setWtd3(final Date wtd3) {
        this.wtd3 = wtd3;
    }

    public String getWtlo1() {
        return wtlo1;
    }

    public void setWtlo1(final String wtlo1) {
        this.wtlo1 = wtlo1;
    }

    public String getWtlo2() {
        return wtlo2;
    }

    public void setWtlo2(final String wtlo2) {
        this.wtlo2 = wtlo2;
    }

    public String getWtlo3() {
        return wtlo3;
    }

    public void setWtlo3(final String wtlo3) {
        this.wtlo3 = wtlo3;
    }

    /**
     * @return the apmApplicationId
     */
    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    /**
     * @param apmApplicationId the apmApplicationId to set
     */
    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    /**
     * @return the plumImage
     */
    public String getPlumImage() {
        return plumImage;
    }

    /**
     * @param plumImage the plumImage to set
     */
    public void setPlumImage(final String plumImage) {
        this.plumImage = plumImage;
    }

    /**
     * @return the plumImagePath
     */
    public String getPlumImagePath() {
        return plumImagePath;
    }

    /**
     * @param plumImagePath the plumImagePath to set
     */
    public void setPlumImagePath(final String plumImagePath) {
        this.plumImagePath = plumImagePath;
    }

    /**
     * @return the serviceId
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_PLUM_MAS", "PLUM_ID" };
    }

}
