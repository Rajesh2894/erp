package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpecialNoticeReportDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -494486849485524026L;
    private String orgName;
    private String letterHeading;
    private String noticeNoL;
    private String noticeNoV;
    private String noticeDateL;
    private String noticeDateV;
    private String deptNameL;
    private String to;
    private String name;
    private String subjectL;
    private String subjectV;
    private String referenceL;
    private String referenceV;
    private String addressL;
    private String addressV;
    private String noticeDueDateL;
    private String noticeDueDateV;
    private String propNoL;
    private String propNoV;
    private String oldPropNoL;
    private String oldPropNoV;
    private String assessmentDateL;
    private String assessmentDateV;
    private String ward1L;
    private String ward1V;
    private String ward2L;
    private String ward2V;
    private String ward3L;
    private String ward3V;
    private String ward4L;
    private String ward4V;
    private String ward5L;
    private String ward5V;
    private String nameTitle;
    private String letterBody;
    private String propDetailsL;
    private String idNoL;
    private String floorL;
    private String usageType1L;
    private String usageType2L;
    private String usageType3L;
    private String usageType4L;
    private String usageType5L;
    private String constructionClassL;
    private String assessAreaL;
    private String alvL;
    private String rvL;
    private String total;
    private String totalArea;
    private String totalRv;
    private String totalAlv;
    private String footerBody;
    private String footerSing;
    private String footerName;
    private String totalcarpetArea;
    private String firstAssessmentDate;
    private String propertyAge;
   
    private String surveyNo;
    private String plotNo;
    private String specialNoticeDueDays;
    private String orgNameReg;
    private String ownerNameReg; 
    private String illegalFlag;
    private Date dueDateAfterSpecialNot;
    
    private List<SpecialNoticeReportDetailDto> specNotDetDtoList = new ArrayList<>();

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getNoticeNoL() {
        return noticeNoL;
    }

    public void setNoticeNoL(String noticeNoL) {
        this.noticeNoL = noticeNoL;
    }

    public String getNoticeNoV() {
        return noticeNoV;
    }

    public void setNoticeNoV(String noticeNoV) {
        this.noticeNoV = noticeNoV;
    }

    public String getNoticeDateL() {
        return noticeDateL;
    }

    public void setNoticeDateL(String noticeDateL) {
        this.noticeDateL = noticeDateL;
    }

    public String getNoticeDateV() {
        return noticeDateV;
    }

    public void setNoticeDateV(String noticeDateV) {
        this.noticeDateV = noticeDateV;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectL() {
        return subjectL;
    }

    public void setSubjectL(String subjectL) {
        this.subjectL = subjectL;
    }

    public String getSubjectV() {
        return subjectV;
    }

    public void setSubjectV(String subjectV) {
        this.subjectV = subjectV;
    }

    public String getReferenceL() {
        return referenceL;
    }

    public void setReferenceL(String referenceL) {
        this.referenceL = referenceL;
    }

    public String getReferenceV() {
        return referenceV;
    }

    public void setReferenceV(String referenceV) {
        this.referenceV = referenceV;
    }

    public String getWard1L() {
        return ward1L;
    }

    public void setWard1L(String ward1l) {
        ward1L = ward1l;
    }

    public String getWard1V() {
        return ward1V;
    }

    public void setWard1V(String ward1v) {
        ward1V = ward1v;
    }

    public String getWard2L() {
        return ward2L;
    }

    public void setWard2L(String ward2l) {
        ward2L = ward2l;
    }

    public String getWard2V() {
        return ward2V;
    }

    public void setWard2V(String ward2v) {
        ward2V = ward2v;
    }

    public String getWard3L() {
        return ward3L;
    }

    public void setWard3L(String ward3l) {
        ward3L = ward3l;
    }

    public String getWard3V() {
        return ward3V;
    }

    public void setWard3V(String ward3v) {
        ward3V = ward3v;
    }

    public String getWard4L() {
        return ward4L;
    }

    public void setWard4L(String ward4l) {
        ward4L = ward4l;
    }

    public String getWard4V() {
        return ward4V;
    }

    public void setWard4V(String ward4v) {
        ward4V = ward4v;
    }

    public String getWard5L() {
        return ward5L;
    }

    public void setWard5L(String ward5l) {
        ward5L = ward5l;
    }

    public String getWard5V() {
        return ward5V;
    }

    public void setWard5V(String ward5v) {
        ward5V = ward5v;
    }

    public String getLetterHeading() {
        return letterHeading;
    }

    public void setLetterHeading(String letterHeading) {
        this.letterHeading = letterHeading;
    }

    public String getLetterBody() {
        return letterBody;
    }

    public void setLetterBody(String letterBody) {
        this.letterBody = letterBody;
    }

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public String getPropDetailsL() {
        return propDetailsL;
    }

    public void setPropDetailsL(String propDetailsL) {
        this.propDetailsL = propDetailsL;
    }

    public String getIdNoL() {
        return idNoL;
    }

    public void setIdNoL(String idNoL) {
        this.idNoL = idNoL;
    }

    public String getConstructionClassL() {
        return constructionClassL;
    }

    public void setConstructionClassL(String constructionClassL) {
        this.constructionClassL = constructionClassL;
    }

    public String getAssessAreaL() {
        return assessAreaL;
    }

    public void setAssessAreaL(String assessAreaL) {
        this.assessAreaL = assessAreaL;
    }

    public String getAlvL() {
        return alvL;
    }

    public void setAlvL(String alvL) {
        this.alvL = alvL;
    }

    public String getRvL() {
        return rvL;
    }

    public void setRvL(String rvL) {
        this.rvL = rvL;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(String totalArea) {
        this.totalArea = totalArea;
    }

    public String getTotalRv() {
        return totalRv;
    }

    public void setTotalRv(String totalRv) {
        this.totalRv = totalRv;
    }

    public String getTotalAlv() {
        return totalAlv;
    }

    public void setTotalAlv(String totalAlv) {
        this.totalAlv = totalAlv;
    }

    public String getFooterBody() {
        return footerBody;
    }

    public void setFooterBody(String footerBody) {
        this.footerBody = footerBody;
    }

    public String getFooterSing() {
        return footerSing;
    }

    public void setFooterSing(String footerSing) {
        this.footerSing = footerSing;
    }

    public String getFooterName() {
        return footerName;
    }

    public void setFooterName(String footerName) {
        this.footerName = footerName;
    }

    public String getAddressL() {
        return addressL;
    }

    public void setAddressL(String addressL) {
        this.addressL = addressL;
    }

    public String getAddressV() {
        return addressV;
    }

    public void setAddressV(String addressV) {
        this.addressV = addressV;
    }

    public String getNoticeDueDateL() {
        return noticeDueDateL;
    }

    public void setNoticeDueDateL(String noticeDueDateL) {
        this.noticeDueDateL = noticeDueDateL;
    }

    public String getNoticeDueDateV() {
        return noticeDueDateV;
    }

    public void setNoticeDueDateV(String noticeDueDateV) {
        this.noticeDueDateV = noticeDueDateV;
    }

    public String getPropNoL() {
        return propNoL;
    }

    public void setPropNoL(String propNoL) {
        this.propNoL = propNoL;
    }

    public String getOldPropNoL() {
        return oldPropNoL;
    }

    public void setOldPropNoL(String oldPropNoL) {
        this.oldPropNoL = oldPropNoL;
    }

    public String getOldPropNoV() {
        return oldPropNoV;
    }

    public void setOldPropNoV(String oldPropNoV) {
        this.oldPropNoV = oldPropNoV;
    }

    public String getAssessmentDateL() {
        return assessmentDateL;
    }

    public void setAssessmentDateL(String assessmentDateL) {
        this.assessmentDateL = assessmentDateL;
    }

    public String getUsageType1L() {
        return usageType1L;
    }

    public void setUsageType1L(String usageType1L) {
        this.usageType1L = usageType1L;
    }

    public String getUsageType2L() {
        return usageType2L;
    }

    public void setUsageType2L(String usageType2L) {
        this.usageType2L = usageType2L;
    }

    public String getUsageType3L() {
        return usageType3L;
    }

    public void setUsageType3L(String usageType3L) {
        this.usageType3L = usageType3L;
    }

    public String getUsageType4L() {
        return usageType4L;
    }

    public void setUsageType4L(String usageType4L) {
        this.usageType4L = usageType4L;
    }

    public String getUsageType5L() {
        return usageType5L;
    }

    public void setUsageType5L(String usageType5L) {
        this.usageType5L = usageType5L;
    }

    public String getFloorL() {
        return floorL;
    }

    public void setFloorL(String floorL) {
        this.floorL = floorL;
    }

    public List<SpecialNoticeReportDetailDto> getSpecNotDetDtoList() {
        return specNotDetDtoList;
    }

    public void setSpecNotDetDtoList(List<SpecialNoticeReportDetailDto> specNotDetDtoList) {
        this.specNotDetDtoList = specNotDetDtoList;
    }

    public String getDeptNameL() {
        return deptNameL;
    }

    public void setDeptNameL(String deptNameL) {
        this.deptNameL = deptNameL;
    }

    public String getPropNoV() {
        return propNoV;
    }

    public void setPropNoV(String propNoV) {
        this.propNoV = propNoV;
    }

    public String getAssessmentDateV() {
        return assessmentDateV;
    }

    public void setAssessmentDateV(String assessmentDateV) {
        this.assessmentDateV = assessmentDateV;
    }

	public String getTotalcarpetArea() {
		return totalcarpetArea;
	}

	public void setTotalcarpetArea(String totalcarpetArea) {
		this.totalcarpetArea = totalcarpetArea;
	}

	public String getFirstAssessmentDate() {
		return firstAssessmentDate;
	}

	public void setFirstAssessmentDate(String firstAssessmentDate) {
		this.firstAssessmentDate = firstAssessmentDate;
	}

	public String getPropertyAge() {
		return propertyAge;
	}

	public void setPropertyAge(String propertyAge) {
		this.propertyAge = propertyAge;
	}
	

	public String getSurveyNo() {
		return surveyNo;
	}

	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}
	

	public String getPlotNo() {
		return plotNo;
	}

	public void setPlotNo(String plotNo) {
		this.plotNo = plotNo;
	}

	public String getSpecialNoticeDueDays() {
		return specialNoticeDueDays;
	}

	public void setSpecialNoticeDueDays(String specialNoticeDueDays) {
		this.specialNoticeDueDays = specialNoticeDueDays;
	}

	public String getOrgNameReg() {
		return orgNameReg;
	}

	public void setOrgNameReg(String orgNameReg) {
		this.orgNameReg = orgNameReg;
	}

	public String getOwnerNameReg() {
		return ownerNameReg;
	}

	public void setOwnerNameReg(String ownerNameReg) {
		this.ownerNameReg = ownerNameReg;
	}

	public String getIllegalFlag() {
		return illegalFlag;
	}

	public void setIllegalFlag(String illegalFlag) {
		this.illegalFlag = illegalFlag;
	}

	public Date getDueDateAfterSpecialNot() {
		return dueDateAfterSpecialNot;
	}

	public void setDueDateAfterSpecialNot(Date dueDateAfterSpecialNot) {
		this.dueDateAfterSpecialNot = dueDateAfterSpecialNot;
	}
		    
}
