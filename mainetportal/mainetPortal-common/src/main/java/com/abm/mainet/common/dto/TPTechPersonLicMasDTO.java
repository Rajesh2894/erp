package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.constant.MainetConstants;

public class TPTechPersonLicMasDTO implements Serializable {

    private static final long serialVersionUID = -3443296896563269750L;

    private long licId;

    private String licNo;

    private Date licFromdate;

    private Date licTodate;

    private Long licServiceId;

    private Long licApplicationId;

    private String licApplicantAddr;

    private Long licTechperType;

    private String licAgency;

    private String licQualification;

    private String licCntArcregno;

    private long orgid;

    private int langId;

    private long userid;

    private Date lmodDate;

    private Long updatedByEmp;

    private Date updatedDate;

    private Date licDate;

    private Long licTitle;

    private String licFname;

    private String licMname;

    private String licLname;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long licTrsId;

    private Long licDwzid;

    private String oldLicNo;

    private String licDataEntMode;

    private String licPhoneno;

    private String licEmail;

    private Long licNationality;

    private String licDob;

    private Double licYearofexp;

    private List<String> fileList = new ArrayList<>();

    public long getLicId() {
        return licId;
    }

    public void setLicId(final long licId) {
        this.licId = licId;
    }

    public String getLicNo() {
        return licNo;
    }

    public void setLicNo(final String licNo) {
        this.licNo = licNo;
    }

    public Date getLicFromdate() {
        return licFromdate;
    }

    public void setLicFromdate(final Date licFromdate) {
        this.licFromdate = licFromdate;
    }

    public Date getLicTodate() {
        return licTodate;
    }

    public void setLicTodate(final Date licTodate) {
        this.licTodate = licTodate;
    }

    public Long getLicServiceId() {
        return licServiceId;
    }

    public void setLicServiceId(final Long licServiceId) {
        this.licServiceId = licServiceId;
    }

    public Long getLicApplicationId() {
        return licApplicationId;
    }

    public void setLicApplicationId(final Long licApplicationId) {
        this.licApplicationId = licApplicationId;
    }

    public String getLicApplicantAddr() {
        return licApplicantAddr;
    }

    public void setLicApplicantAddr(final String licApplicantAddr) {
        this.licApplicantAddr = licApplicantAddr;
    }

    public Long getLicTechperType() {
        return licTechperType;
    }

    public void setLicTechperType(final Long licTechperType) {
        this.licTechperType = licTechperType;
    }

    public String getLicAgency() {
        return licAgency;
    }

    public void setLicAgency(final String licAgency) {
        this.licAgency = licAgency;
    }

    public String getLicQualification() {
        return licQualification;
    }

    public void setLicQualification(final String licQualification) {
        this.licQualification = licQualification;
    }

    public String getLicCntArcregno() {
        return licCntArcregno;
    }

    public void setLicCntArcregno(final String licCntArcregno) {
        this.licCntArcregno = licCntArcregno;
    }

    public long getOrgid() {
        return orgid;
    }

    public void setOrgid(final long orgid) {
        this.orgid = orgid;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(final long userid) {
        this.userid = userid;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Long getUpdatedByEmp() {
        return updatedByEmp;
    }

    public void setUpdatedByEmp(final Long updatedByEmp) {
        this.updatedByEmp = updatedByEmp;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getLicDate() {
        return licDate;
    }

    public void setLicDate(final Date licDate) {
        this.licDate = licDate;
    }

    public Long getLicTitle() {
        return licTitle;
    }

    public void setLicTitle(final Long licTitle) {
        this.licTitle = licTitle;
    }

    public String getLicFname() {
        return licFname;
    }

    public void setLicFname(final String licFname) {
        this.licFname = licFname;
    }

    public String getLicMname() {
        return licMname;
    }

    public void setLicMname(final String licMname) {
        this.licMname = licMname;
    }

    public String getLicLname() {
        return licLname;
    }

    public void setLicLname(final String licLname) {
        this.licLname = licLname;
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

    public Long getLicTrsId() {
        return licTrsId;
    }

    public void setLicTrsId(final Long licTrsId) {
        this.licTrsId = licTrsId;
    }

    public Long getLicDwzid() {
        return licDwzid;
    }

    public void setLicDwzid(final Long licDwzid) {
        this.licDwzid = licDwzid;
    }

    public String getOldLicNo() {
        return oldLicNo;
    }

    public void setOldLicNo(final String oldLicNo) {
        this.oldLicNo = oldLicNo;
    }

    public String getLicDataEntMode() {
        return licDataEntMode;
    }

    public void setLicDataEntMode(final String licDataEntMode) {
        this.licDataEntMode = licDataEntMode;
    }

    public String getLicPhoneno() {
        return licPhoneno;
    }

    public void setLicPhoneno(final String licPhoneno) {
        this.licPhoneno = licPhoneno;
    }

    public String getLicEmail() {
        return licEmail;
    }

    public void setLicEmail(final String licEmail) {
        this.licEmail = licEmail;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("TPTechPersonLicMasDTO [licId=");
        builder.append(licId);
        builder.append(", licNo=");
        builder.append(licNo);
        builder.append(", licFromdate=");
        builder.append(licFromdate);
        builder.append(", licTodate=");
        builder.append(licTodate);
        builder.append(", licServiceId=");
        builder.append(licServiceId);
        builder.append(", licApplicationId=");
        builder.append(licApplicationId);
        builder.append(", licApplicantAddr=");
        builder.append(licApplicantAddr);
        builder.append(", licTechperType=");
        builder.append(licTechperType);
        builder.append(", licAgency=");
        builder.append(licAgency);
        builder.append(", licQualification=");
        builder.append(licQualification);
        builder.append(", licCntArcregno=");
        builder.append(licCntArcregno);
        builder.append(", orgid=");
        builder.append(orgid);
        builder.append(", langId=");
        builder.append(langId);
        builder.append(", userid=");
        builder.append(userid);
        builder.append(", lmodDate=");
        builder.append(lmodDate);
        builder.append(", updatedByEmp=");
        builder.append(updatedByEmp);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", licDate=");
        builder.append(licDate);
        builder.append(", licTitle=");
        builder.append(licTitle);
        builder.append(", licFname=");
        builder.append(licFname);
        builder.append(", licMname=");
        builder.append(licMname);
        builder.append(", licLname=");
        builder.append(licLname);
        builder.append(", lgIpMac=");
        builder.append(lgIpMac);
        builder.append(", lgIpMacUpd=");
        builder.append(lgIpMacUpd);
        builder.append(", licTrsId=");
        builder.append(licTrsId);
        builder.append(", licDwzid=");
        builder.append(licDwzid);
        builder.append(", oldLicNo=");
        builder.append(oldLicNo);
        builder.append(", licDataEntMode=");
        builder.append(licDataEntMode);
        builder.append(", licPhoneno=");
        builder.append(licPhoneno);
        builder.append(", licEmail=");
        builder.append(licEmail);
        builder.append(MainetConstants.operator.LEFT_SQUARE_BRACKET);
        return builder.toString();
    }

    public Long getLicNationality() {
        return licNationality;
    }

    public void setLicNationality(final Long licNationality) {
        this.licNationality = licNationality;
    }

    public String getLicDob() {
        return licDob;
    }

    public void setLicDob(final String licDob) {
        this.licDob = licDob;
    }

    public Double getLicYearofexp() {
        return licYearofexp;
    }

    public void setLicYearofexp(final Double licYearofexp) {
        this.licYearofexp = licYearofexp;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(final List<String> fileList) {
        this.fileList = fileList;
    }

}
