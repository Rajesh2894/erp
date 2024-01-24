package com.abm.mainet.cfc.checklist.dto;

import java.io.Serializable;
import java.util.List;

public class CheckListVerificationReportParentDTO implements Serializable {
    private static final long serialVersionUID = -3204049018898575377L;

    private String applicantName;
    private String applicationAdd;
    private String subject;
    private String applicationDate;
    private String topRightImage;
    private String topLeftImage;
    private String orgLabel;
    private String signPath;
    private String colon;
    private String lHeading;
    private String lTo;
    private String lRejSub;
    private String lRejRef;
    private String lRefLine;
    private String lLastLine;
    private String refFill;
    private String lletterNo;
    private String ldate;
    private String lSirMadam;
    private String lSrNo;
    private String tletterno;
    private String lUlbAddr;
    private String lLastLine2;
    private String parent;
    private String page;
    private String of;
    private String subject1;
    private String subject2;
    private String refFill1;
    private String refFill2;
    private String refFill3;
    private String lRefLine1;
    private String lRefLine2;
    private String deptName;
    
    public String getParent() {
        return parent;
    }

    public void setParent(final String parent) {
        this.parent = parent;
    }

    public String getlLastLine2() {
        return lLastLine2;
    }

    public void setlLastLine2(final String lLastLine2) {
        this.lLastLine2 = lLastLine2;
    }

    public String getlUlbAddr() {
        return lUlbAddr;
    }

    public void setlUlbAddr(final String lUlbAddr) {
        this.lUlbAddr = lUlbAddr;
    }

    public String getlDocName() {
        return lDocName;
    }

    public void setlDocName(final String lDocName) {
        this.lDocName = lDocName;
    }

    public String getlObsDetail() {
        return lObsDetail;
    }

    public void setlObsDetail(final String lObsDetail) {
        this.lObsDetail = lObsDetail;
    }

    private String lDocName;
    private String lObsDetail;

    private List<CheckListVerificationReportChildDTO> childDTO;

    public String getlLastLine() {
        return lLastLine;
    }

    public void setlLastLine(final String lLastLine) {
        this.lLastLine = lLastLine;
    }

    public String getLletterNo() {
        return lletterNo;
    }

    public void setLletterNo(final String lletterNo) {
        this.lletterNo = lletterNo;
    }

    public String getLdate() {
        return ldate;
    }

    public void setLdate(final String ldate) {
        this.ldate = ldate;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicationAdd() {
        return applicationAdd;
    }

    public void setApplicationAdd(final String applicationAdd) {
        this.applicationAdd = applicationAdd;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(final String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getTopRightImage() {
        return topRightImage;
    }

    public void setTopRightImage(final String topRightImage) {
        this.topRightImage = topRightImage;
    }

    public String getTopLeftImage() {
        return topLeftImage;
    }

    public void setTopLeftImage(final String topLeftImage) {
        this.topLeftImage = topLeftImage;
    }

    public String getOrgLabel() {
        return orgLabel;
    }

    public void setOrgLabel(final String orgLabel) {
        this.orgLabel = orgLabel;
    }

    public String getSignPath() {
        return signPath;
    }

    public void setSignPath(final String signPath) {
        this.signPath = signPath;
    }

    public String getlHeading() {
        return lHeading;
    }

    public void setlHeading(final String lHeading) {
        this.lHeading = lHeading;
    }

    public String getlTo() {
        return lTo;
    }

    public void setlTo(final String lTo) {
        this.lTo = lTo;
    }

    public String getlRejSub() {
        return lRejSub;
    }

    public void setlRejSub(final String lRejSub) {
        this.lRejSub = lRejSub;
    }

    public String getlRejRef() {
        return lRejRef;
    }

    public void setlRejRef(final String lRejRef) {
        this.lRejRef = lRejRef;
    }

    public String getlRefLine() {
        return lRefLine;
    }

    public void setlRefLine(final String lRefLine) {
        this.lRefLine = lRefLine;
    }

    public String getRefFill() {
        return refFill;
    }

    public void setRefFill(final String refFill) {
        this.refFill = refFill;
    }

    public List<CheckListVerificationReportChildDTO> getChildDTO() {
        return childDTO;
    }

    public void setChildDTO(final List<CheckListVerificationReportChildDTO> childDTO) {
        this.childDTO = childDTO;
    }

    public String getlSirMadam() {
        return lSirMadam;
    }

    public void setlSirMadam(final String lSirMadam) {
        this.lSirMadam = lSirMadam;
    }

    public String getlSrNo() {
        return lSrNo;
    }

    public void setlSrNo(final String lSrNo) {
        this.lSrNo = lSrNo;
    }

    public String getTletterno() {
        return tletterno;
    }

    public void setTletterno(final String tletterno) {
        this.tletterno = tletterno;
    }

    public String getColon() {
        return colon;
    }

    public void setColon(final String colon) {
        this.colon = colon;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public String getOf() {
        return of;
    }

    public void setOf(final String of) {
        this.of = of;
    }

    public String getSubject1() {
        return subject1;
    }

    public void setSubject1(final String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject2() {
        return subject2;
    }

    public void setSubject2(final String subject2) {
        this.subject2 = subject2;
    }

    public String getRefFill1() {
        return refFill1;
    }

    public void setRefFill1(final String refFill1) {
        this.refFill1 = refFill1;
    }

    public String getRefFill2() {
        return refFill2;
    }

    public void setRefFill2(final String refFill2) {
        this.refFill2 = refFill2;
    }

    public String getRefFill3() {
        return refFill3;
    }

    public void setRefFill3(final String refFill3) {
        this.refFill3 = refFill3;
    }

    public String getlRefLine1() {
        return lRefLine1;
    }

    public void setlRefLine1(final String lRefLine1) {
        this.lRefLine1 = lRefLine1;
    }

    public String getlRefLine2() {
        return lRefLine2;
    }

    public void setlRefLine2(final String lRefLine2) {
        this.lRefLine2 = lRefLine2;
    }

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

    
}
