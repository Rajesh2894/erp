package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.domain.Organisation;

public class ProperySearchDto implements Serializable {

    private static final long serialVersionUID = -6599341518218151848L;

    private String proertyNo;

    private String oldPid;

    private String mobileno;

    private String ownerName;

    private String rowId;

    private long orgId;

    private long deptId;

    private long applicationId;

    private String guardianName;

    private Long locId;

    private Long assWard1;

    private Long assWard2;

    private Long assWard3;

    private Long assWard4;

    private Long assWard5;

    private Long bmIdNo;

    /*
     * private FileNetApplicationClient fileNetAppClient;
     */
    private byte[] billFile;

    private String filePath;

    private Organisation org;

    private Long recptId;

    private int langId;

    private Long empId;

    private String appDate;

    private String deptName;

    private String deptShortName;

    private String khasraNo;

    private String villageNo;

    private String outstandingAmt;

    private String receiptAmt;

    private String receiptDate;

    private String vsrNo;

    private String districtId;

    private String tehsilId;

    private Long recptNo;

    private String fromAmout;

    private String toAmount;

    private Long propLvlRoadType;

    private String proAssdUsagetypeDesc;

    private String proAssdConstruTypeDesc;

    private String proAssdRoadfactorDesc;

    private Long assdUsagetype1;

    private Long assdUsagetype2;

    private Long assdUsagetype3;

    private Long assdUsagetype4;

    private Long assdUsagetype5;

    private Long assdConstruType;

    private String address;

    private String groupPropCheck;

    private String groupPropNo;

    private String groupPropName;

    private String parentPropNo;

    private String parentPropName;

    private double waterOutstanding;

    private String flatNo;

    private String statusFlag;

    private String eMail;

    private String houseNo;

    private String status;

    private String errorMsg;
    
    private Long parentGrp1;

    private Long parentGrp2;

    private Long parentGrp3;

    private Long parentGrp4;

    private Long parentGrp5;
    
    private String specNotSearchType;
    
    private String billMethodChangeFlag;
    
    private Long assParshadWard1;

	private Long assParshadWard2;

	private Long assParshadWard3;

	private Long assParshadWard4;

	private Long assParshadWard5;
	
	private String occupierName;
	
	private String newHouseNo;
	
    private String buildingNo;
    
    private String clusterNo;
    
    private String billingMethod;
    
    private List<String> flatNoList;

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getProertyNo() {
        return proertyNo;
    }

    public void setProertyNo(String proertyNo) {
        this.proertyNo = proertyNo;
    }

    public String getOldPid() {
        return oldPid;
    }

    public void setOldPid(String oldPid) {
        this.oldPid = oldPid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getEditFlag() {
        return getRowId();
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public void setEditFlag(String l) {

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

    public Long getBmIdNo() {
        return bmIdNo;
    }

    public void setBmIdNo(Long bmIdNo) {
        this.bmIdNo = bmIdNo;
    }

    public byte[] getBillFile() {
        return billFile;
    }

    public void setBillFile(byte[] billFile) {
        this.billFile = billFile;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Organisation getOrg() {
        return org;
    }

    public void setOrg(Organisation org) {
        this.org = org;
    }

    public Long getRecptId() {
        return recptId;
    }

    public void setRecptId(Long recptId) {
        this.recptId = recptId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptShortName() {
        return deptShortName;
    }

    public void setDeptShortName(String deptShortName) {
        this.deptShortName = deptShortName;
    }

    public String getOutstandingAmt() {
        return outstandingAmt;
    }

    public void setOutstandingAmt(String outstandingAmt) {
        this.outstandingAmt = outstandingAmt;
    }

    public String getReceiptAmt() {
        return receiptAmt;
    }

    public void setReceiptAmt(String receiptAmt) {
        this.receiptAmt = receiptAmt;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getVillageNo() {
        return villageNo;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getTehsilId() {
        return tehsilId;
    }

    public void setTehsilId(String tehsilId) {
        this.tehsilId = tehsilId;
    }

    public void setVillageNo(String villageNo) {
        this.villageNo = villageNo;
    }

    public String getKhasraNo() {
        return khasraNo;
    }

    public void setKhasraNo(String khasraNo) {
        this.khasraNo = khasraNo;
    }

    public String getVsrNo() {
        return vsrNo;
    }

    public void setVsrNo(String vsrNo) {
        this.vsrNo = vsrNo;
    }

    public Long getRecptNo() {
        return recptNo;
    }

    public void setRecptNo(Long recptNo) {
        this.recptNo = recptNo;
    }

    public String getFromAmout() {
        return fromAmout;
    }

    public void setFromAmout(String fromAmout) {
        this.fromAmout = fromAmout;
    }

    public String getToAmount() {
        return toAmount;
    }

    public void setToAmount(String toAmount) {
        this.toAmount = toAmount;
    }

    public String getProAssdUsagetypeDesc() {
        return proAssdUsagetypeDesc;
    }

    public void setProAssdUsagetypeDesc(String proAssdUsagetypeDesc) {
        this.proAssdUsagetypeDesc = proAssdUsagetypeDesc;
    }

    public String getProAssdConstruTypeDesc() {
        return proAssdConstruTypeDesc;
    }

    public void setProAssdConstruTypeDesc(String proAssdConstruTypeDesc) {
        this.proAssdConstruTypeDesc = proAssdConstruTypeDesc;
    }

    public String getProAssdRoadfactorDesc() {
        return proAssdRoadfactorDesc;
    }

    public void setProAssdRoadfactorDesc(String proAssdRoadfactorDesc) {
        this.proAssdRoadfactorDesc = proAssdRoadfactorDesc;
    }

    public Long getPropLvlRoadType() {
        return propLvlRoadType;
    }

    public void setPropLvlRoadType(Long propLvlRoadType) {
        this.propLvlRoadType = propLvlRoadType;
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

    public Long getAssdConstruType() {
        return assdConstruType;
    }

    public void setAssdConstruType(Long assdConstruType) {
        this.assdConstruType = assdConstruType;
    }

    public double getWaterOutstanding() {
        return waterOutstanding;
    }

    public void setWaterOutstanding(double waterOutstanding) {
        this.waterOutstanding = waterOutstanding;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGroupPropCheck() {
        return groupPropCheck;
    }

    public void setGroupPropCheck(String groupPropCheck) {
        this.groupPropCheck = groupPropCheck;
    }

    public String getGroupPropNo() {
        return groupPropNo;
    }

    public void setGroupPropNo(String groupPropNo) {
        this.groupPropNo = groupPropNo;
    }

    public String getGroupPropName() {
        return groupPropName;
    }

    public void setGroupPropName(String groupPropName) {
        this.groupPropName = groupPropName;
    }

    public String getParentPropNo() {
        return parentPropNo;
    }

    public void setParentPropNo(String parentPropNo) {
        this.parentPropNo = parentPropNo;
    }

    public String getParentPropName() {
        return parentPropName;
    }

    public void setParentPropName(String parentPropName) {
        this.parentPropName = parentPropName;
    }

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

	public Long getParentGrp1() {
		return parentGrp1;
	}

	public void setParentGrp1(Long parentGrp1) {
		this.parentGrp1 = parentGrp1;
	}

	public Long getParentGrp2() {
		return parentGrp2;
	}

	public void setParentGrp2(Long parentGrp2) {
		this.parentGrp2 = parentGrp2;
	}

	public Long getParentGrp3() {
		return parentGrp3;
	}

	public void setParentGrp3(Long parentGrp3) {
		this.parentGrp3 = parentGrp3;
	}

	public Long getParentGrp4() {
		return parentGrp4;
	}

	public void setParentGrp4(Long parentGrp4) {
		this.parentGrp4 = parentGrp4;
	}

	public Long getParentGrp5() {
		return parentGrp5;
	}

	public void setParentGrp5(Long parentGrp5) {
		this.parentGrp5 = parentGrp5;
	}

	public String getSpecNotSearchType() {
		return specNotSearchType;
	}

	public void setSpecNotSearchType(String specNotSearchType) {
		this.specNotSearchType = specNotSearchType;
	}

	public String getBillMethodChangeFlag() {
		return billMethodChangeFlag;
	}

	public void setBillMethodChangeFlag(String billMethodChangeFlag) {
		this.billMethodChangeFlag = billMethodChangeFlag;
	}

	public Long getAssParshadWard1() {
		return assParshadWard1;
	}

	public void setAssParshadWard1(Long assParshadWard1) {
		this.assParshadWard1 = assParshadWard1;
	}

	public Long getAssParshadWard2() {
		return assParshadWard2;
	}

	public void setAssParshadWard2(Long assParshadWard2) {
		this.assParshadWard2 = assParshadWard2;
	}

	public Long getAssParshadWard3() {
		return assParshadWard3;
	}

	public void setAssParshadWard3(Long assParshadWard3) {
		this.assParshadWard3 = assParshadWard3;
	}

	public Long getAssParshadWard4() {
		return assParshadWard4;
	}

	public void setAssParshadWard4(Long assParshadWard4) {
		this.assParshadWard4 = assParshadWard4;
	}

	public Long getAssParshadWard5() {
		return assParshadWard5;
	}

	public void setAssParshadWard5(Long assParshadWard5) {
		this.assParshadWard5 = assParshadWard5;
	}

	public String getOccupierName() {
		return occupierName;
	}

	public void setOccupierName(String occupierName) {
		this.occupierName = occupierName;
	}

	public String getNewHouseNo() {
		return newHouseNo;
	}

	public void setNewHouseNo(String newHouseNo) {
		this.newHouseNo = newHouseNo;
	}

	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	public String getClusterNo() {
		return clusterNo;
	}

	public void setClusterNo(String clusterNo) {
		this.clusterNo = clusterNo;
	}

	public String getBillingMethod() {
		return billingMethod;
	}

	public void setBillingMethod(String billingMethod) {
		this.billingMethod = billingMethod;
	}

	public List<String> getFlatNoList() {
		return flatNoList;
	}

	public void setFlatNoList(List<String> flatNoList) {
		this.flatNoList = flatNoList;
	}
	
	
}
