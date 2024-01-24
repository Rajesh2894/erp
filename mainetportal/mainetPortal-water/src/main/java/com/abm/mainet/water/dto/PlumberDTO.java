package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.dto.DocumentDetailsVO;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class PlumberDTO implements Serializable {

    private static final long serialVersionUID = -6658412878000776494L;

    private Long plumberId;
    private String plumberLicenceNo;
    private String plumberFName;
    private String plumberMName;
    private String plumberLName;
    private String plumberFullName;
    private Date plumAppDate;
    private String plumSex;
    private String plumDOB;
    private String plumContactPersonName;
    private String plumLicIssueFlag;
    private String plumLicIssueDate;
    private Date plumLicFromDate;
    private Date plumLicToDate;
    private Long orgid;
    private Long userId;
    private Long langId;
    private Date lmoddate;
    private Long updatedBy;
    private Date updatedDate;
    private String plumInterviewRemark;
    private String plumInterviewClr;
    private String plumLicType;
    private String plumFatherName;
    private String plumContactNo;
    private String plumCpdTitle;
    private String plumAddress;
    private Date plumInterviewDate;
    private String plumCscid1;
    private String plumCscid2;
    private String plumCscid3;
    private String plumCscid4;
    private String plumCscid5;
    private String plumOldLicNo;
    private String ported;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String plumEntryFlag;
    private String wtv2;
    private String wtv3;
    private String wtv4;
    private String wtv5;
    private String wtn1;
    private String wtn2;
    private String wtn3;
    private String wtn4;
    private String wtn5;
    private Date wtd1;
    private Date wtd2;
    private Date wtd3;
    private String wtlo1;
    private String wtlo2;
    private String wtlo3;
    private List<String> fileList;
    private Long applicationId;
    private Long serviceId;
    private List<String> fileCheckList;
    private List<DocumentDetailsVO> documentList;
    private String plumberImage;
    private Long orgId;
    private Long deptId;
    private int uploadedDocSize;

    public Long getPlumberId() {
        return plumberId;
    }

    public void setPlumberId(final Long plumberId) {
        this.plumberId = plumberId;
    }

    public String getPlumberLicenceNo() {
        return plumberLicenceNo;
    }

    public void setPlumberLicenceNo(final String plumberLicenceNo) {
        this.plumberLicenceNo = plumberLicenceNo;
    }

    public String getPlumberFName() {
        return plumberFName;
    }

    public void setPlumberFName(final String plumberFName) {
        this.plumberFName = plumberFName;
    }

    public String getPlumberMName() {
        return plumberMName;
    }

    public void setPlumberMName(final String plumberMName) {
        this.plumberMName = plumberMName;
    }

    public String getPlumberLName() {
        return plumberLName;
    }

    public void setPlumberLName(final String plumberLName) {
        this.plumberLName = plumberLName;
    }

    public String getPlumberFullName() {
        return plumberFullName;
    }

    public void setPlumberFullName(final String plumberFullName) {
        this.plumberFullName = plumberFullName;
    }

    public Date getPlumAppDate() {
        return plumAppDate;
    }

    public void setPlumAppDate(final Date plumAppDate) {
        this.plumAppDate = plumAppDate;
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

    public String getWtv2() {
        return wtv2;
    }

    public void setWtv2(final String wtv2) {
        this.wtv2 = wtv2;
    }

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

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(final List<String> fileList) {
        this.fileList = fileList;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public List<String> getFileCheckList() {
        return fileCheckList;
    }

    public void setFileCheckList(final List<String> fileCheckList) {
        this.fileCheckList = fileCheckList;
    }

    public List<DocumentDetailsVO> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(final List<DocumentDetailsVO> documentList) {
        this.documentList = documentList;
    }

    public String getPlumberImage() {
        return plumberImage;
    }

    public void setPlumberImage(final String plumberImage) {
        this.plumberImage = plumberImage;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    public int getUploadedDocSize() {
        return uploadedDocSize;
    }

    public void setUploadedDocSize(final int uploadedDocSize) {
        this.uploadedDocSize = uploadedDocSize;
    }

}
