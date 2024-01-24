package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.Date;

public class NoticeGenSearchDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4832635775825908378L;

    private String specNotSearchType;

    private String propertyNo;

    private String oldPropertyNo;

    private Long applicationId;

    private long assId;

    private String finYear;

    private String genNotCheck;

    private String locationName;

    private String ownerName;

    private Long assPropType1;

    private Long assPropType2;

    private Long assPropType3;

    private Long assPropType4;

    private Long assPropType5;

    private Long assdUsagetype1;

    private Long assdUsagetype2;

    private Long assdUsagetype3;

    private Long assdUsagetype4;

    private Long assdUsagetype5;

    private Long assWard1;

    private Long assWard2;

    private Long assWard3;

    private Long assWard4;

    private Long assWard5;

    private Long locId;

    private Long assOwnerType;

    private Long noticeNo;

    private Long noticeDate;

    private String checkStatus;

    private Long noticeType;

    private String mobileNo;

    private String emailId;
    
    private Long orgId;
    
    private String flatNo;
    
    private String billNo;
    
    private String billDate;

    // used only when it coming from View Property details -Y
    private String noticeEntry;
    
    private Date billDistribDate;
    
    private Long bmIdNo;
    
    private String action;
        
    private String parentPropName;
    
    private String isGroup;
    
    private String parentPropNo;
    
    private String splNotDueDatePass;
    
    private double fromAmount;
    
    private double toAmount;
    
    private Date multiBillDistrDate;
    
    private Date dueDate;
    
    private String currentBillNotExistFlag;
    
    private String dueDateNotCrossFlag;
    
    private Long parshadAssWard1;
    
    private Long parshadAssWard2;
    
    private Long parshadAssWard3;
    
    private Long parshadAssWard4;
    
    private Long parshadAssWard5;
    
    private String houseNo;

    public long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(long noticeId) {
        this.noticeId = noticeId;
    }

    private long noticeId;

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getOldPropertyNo() {
        return oldPropertyNo;
    }

    public void setOldPropertyNo(String oldPropertyNo) {
        this.oldPropertyNo = oldPropertyNo;
    }

    public Long getAssPropType1() {
        return assPropType1;
    }

    public void setAssPropType1(Long assPropType1) {
        this.assPropType1 = assPropType1;
    }

    public Long getAssPropType2() {
        return assPropType2;
    }

    public void setAssPropType2(Long assPropType2) {
        this.assPropType2 = assPropType2;
    }

    public Long getAssPropType3() {
        return assPropType3;
    }

    public void setAssPropType3(Long assPropType3) {
        this.assPropType3 = assPropType3;
    }

    public Long getAssPropType4() {
        return assPropType4;
    }

    public void setAssPropType4(Long assPropType4) {
        this.assPropType4 = assPropType4;
    }

    public Long getAssPropType5() {
        return assPropType5;
    }

    public void setAssPropType5(Long assPropType5) {
        this.assPropType5 = assPropType5;
    }

    public Long getAssWard1() {
        return assWard1;
    }

    public void setAssWard1(Long assWard1) {
        this.assWard1 = assWard1;
    }

    public Long getAssWard2() {
        return assWard2;
    }

    public void setAssWard2(Long assWard2) {
        this.assWard2 = assWard2;
    }

    public Long getAssWard3() {
        return assWard3;
    }

    public void setAssWard3(Long assWard3) {
        this.assWard3 = assWard3;
    }

    public Long getAssWard4() {
        return assWard4;
    }

    public void setAssWard4(Long assWard4) {
        this.assWard4 = assWard4;
    }

    public Long getAssWard5() {
        return assWard5;
    }

    public void setAssWard5(Long assWard5) {
        this.assWard5 = assWard5;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getAssOwnerType() {
        return assOwnerType;
    }

    public void setAssOwnerType(Long assOwnerType) {
        this.assOwnerType = assOwnerType;
    }

    public Long getAssdUsagetype1() {
        return assdUsagetype1;
    }

    public void setAssdUsagetype1(Long assdUsagetype1) {
        this.assdUsagetype1 = assdUsagetype1;
    }

    public Long getAssdUsagetype2() {
        return assdUsagetype2;
    }

    public void setAssdUsagetype2(Long assdUsagetype2) {
        this.assdUsagetype2 = assdUsagetype2;
    }

    public Long getAssdUsagetype3() {
        return assdUsagetype3;
    }

    public void setAssdUsagetype3(Long assdUsagetype3) {
        this.assdUsagetype3 = assdUsagetype3;
    }

    public Long getAssdUsagetype4() {
        return assdUsagetype4;
    }

    public void setAssdUsagetype4(Long assdUsagetype4) {
        this.assdUsagetype4 = assdUsagetype4;
    }

    public Long getAssdUsagetype5() {
        return assdUsagetype5;
    }

    public void setAssdUsagetype5(Long assdUsagetype5) {
        this.assdUsagetype5 = assdUsagetype5;
    }

    public String getSpecNotSearchType() {
        return specNotSearchType;
    }

    public void setSpecNotSearchType(String specNotSearchType) {
        this.specNotSearchType = specNotSearchType;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public long getAssId() {
        return assId;
    }

    public void setAssId(long assId) {
        this.assId = assId;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
    }

    public String getGenNotCheck() {
        return genNotCheck;
    }

    public void setGenNotCheck(String genNotCheck) {
        this.genNotCheck = genNotCheck;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(Long noticeNo) {
        this.noticeNo = noticeNo;
    }

    public Long getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Long noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Long getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Long noticeType) {
        this.noticeType = noticeType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getNoticeEntry() {
        return noticeEntry;
    }

    public void setNoticeEntry(String noticeEntry) {
        this.noticeEntry = noticeEntry;
    }

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public Date getBillDistribDate() {
		return billDistribDate;
	}

	public void setBillDistribDate(Date billDistribDate) {
		this.billDistribDate = billDistribDate;
	}

	public Long getBmIdNo() {
		return bmIdNo;
	}

	public void setBmIdNo(Long bmIdNo) {
		this.bmIdNo = bmIdNo;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getParentPropName() {
		return parentPropName;
	}

	public void setParentPropName(String parentPropName) {
		this.parentPropName = parentPropName;
	}

	public String getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}
	
	public String getParentPropNo() {
		return parentPropNo;
	}

	public void setParentPropNo(String parentPropNo) {
		this.parentPropNo = parentPropNo;
	}

	public String getSplNotDueDatePass() {
		return splNotDueDatePass;
	}

	public void setSplNotDueDatePass(String splNotDueDatePass) {
		this.splNotDueDatePass = splNotDueDatePass;
	}

	public double getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(double fromAmount) {
		this.fromAmount = fromAmount;
	}

	public double getToAmount() {
		return toAmount;
	}

	public void setToAmount(double toAmount) {
		this.toAmount = toAmount;
	}

	public Date getMultiBillDistrDate() {
		return multiBillDistrDate;
	}

	public void setMultiBillDistrDate(Date multiBillDistrDate) {
		this.multiBillDistrDate = multiBillDistrDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getCurrentBillNotExistFlag() {
		return currentBillNotExistFlag;
	}

	public void setCurrentBillNotExistFlag(String currentBillNotExistFlag) {
		this.currentBillNotExistFlag = currentBillNotExistFlag;
	}

	public String getDueDateNotCrossFlag() {
		return dueDateNotCrossFlag;
	}

	public void setDueDateNotCrossFlag(String dueDateNotCrossFlag) {
		this.dueDateNotCrossFlag = dueDateNotCrossFlag;
	}

	public Long getParshadAssWard1() {
		return parshadAssWard1;
	}

	public void setParshadAssWard1(Long parshadAssWard1) {
		this.parshadAssWard1 = parshadAssWard1;
	}

	public Long getParshadAssWard2() {
		return parshadAssWard2;
	}

	public void setParshadAssWard2(Long parshadAssWard2) {
		this.parshadAssWard2 = parshadAssWard2;
	}

	public Long getParshadAssWard3() {
		return parshadAssWard3;
	}

	public void setParshadAssWard3(Long parshadAssWard3) {
		this.parshadAssWard3 = parshadAssWard3;
	}

	public Long getParshadAssWard4() {
		return parshadAssWard4;
	}

	public void setParshadAssWard4(Long parshadAssWard4) {
		this.parshadAssWard4 = parshadAssWard4;
	}

	public Long getParshadAssWard5() {
		return parshadAssWard5;
	}

	public void setParshadAssWard5(Long parshadAssWard5) {
		this.parshadAssWard5 = parshadAssWard5;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}	
	
}
