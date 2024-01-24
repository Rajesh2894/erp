package com.abm.mainet.common.integration.property.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.common.integration.dto.CommonAppResponseDTO;

public class PropertyDetailDto extends CommonAppResponseDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7954890898487713821L;
    private String address;
    private Long pinCode;
    private Long deptId;
    private String propNo;
    private Long applNo;
    private String flatNo;
    private Long serviceId;
    private String location;
    private String primaryOwnerName;
    private String gardianOwnerName;
    private String primaryOwnerMobNo;
    private String ownerEmail;
    private String gender;
    private double totalOutsatandingAmt;
    private String uasge;
    private String ward1;
    private String ward2;
    private String ward3;
    private String ward4;
    private String ward5;
    private Long wd1;
    private Long wd2;
    private Long wd3;
    private Long wd4;
    private Long wd5;
    private Long orgId;
    private String corrAddress;
    private String occupancyType;
    private String assLandTypeDesc;
    private String tppSurveyNumber;
    private String tppKhataNo;
    private String tppPlotNo;
    private String proAssdRoadfactorDesc;
    private String areaName;
    private Long pTaxCollEmpId;
    private String pTaxCollEmpName;
    private String pTaxCollEmpEmailId;
    private String pTaxCollEmpMobNo;
    private Long landTypeId;
    private Long roadTypeId;
    private Double assPlotArea;
    private String assessmentCheckFlag;
    private String jointOwnerName;
    private String otp;
    private Date updatedDate;
    private Long ownerRelationId;
    private String ownerNameReg;
    private String jointOwnerNameReg;
    private String propAddressReg;
    private String usageTypeReg;
    private String propertyType;
    private String propertyTypeReg;
    private double penaltyAmount;
    private double totalAmount;
    private String serviceShortCode;
    private String oldPropNo;
    private String uniquePropertyId;
    private String transferTypeDesc;
    private String fullOwnerName;
    private double totalArv;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
    }

    public Long getApplNo() {
        return applNo;
    }

    public void setApplNo(Long applNo) {
        this.applNo = applNo;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrimaryOwnerName() {
        return primaryOwnerName;
    }

    public void setPrimaryOwnerName(String primaryOwnerName) {
        this.primaryOwnerName = primaryOwnerName;
    }

    public String getGardianOwnerName() {
        return gardianOwnerName;
    }

    public void setGardianOwnerName(String gardianOwnerName) {
        this.gardianOwnerName = gardianOwnerName;
    }

    public String getPrimaryOwnerMobNo() {
        return primaryOwnerMobNo;
    }

    public void setPrimaryOwnerMobNo(String primaryOwnerMobNo) {
        this.primaryOwnerMobNo = primaryOwnerMobNo;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getTotalOutsatandingAmt() {
        return totalOutsatandingAmt;
    }

    public void setTotalOutsatandingAmt(double totalOutsatandingAmt) {
        this.totalOutsatandingAmt = totalOutsatandingAmt;
    }

    public String getUasge() {
        return uasge;
    }

    public void setUasge(String uasge) {
        this.uasge = uasge;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getWard1() {
        return ward1;
    }

    public void setWard1(String ward1) {
        this.ward1 = ward1;
    }

    public String getWard2() {
        return ward2;
    }

    public void setWard2(String ward2) {
        this.ward2 = ward2;
    }

    public String getWard3() {
        return ward3;
    }

    public void setWard3(String ward3) {
        this.ward3 = ward3;
    }

    public String getWard4() {
        return ward4;
    }

    public void setWard4(String ward4) {
        this.ward4 = ward4;
    }

    public String getWard5() {
        return ward5;
    }

    public void setWard5(String ward5) {
        this.ward5 = ward5;
    }

    public Long getWd1() {
        return wd1;
    }

    public void setWd1(Long wd1) {
        this.wd1 = wd1;
    }

    public Long getWd2() {
        return wd2;
    }

    public void setWd2(Long wd2) {
        this.wd2 = wd2;
    }

    public Long getWd3() {
        return wd3;
    }

    public void setWd3(Long wd3) {
        this.wd3 = wd3;
    }

    public Long getWd4() {
        return wd4;
    }

    public void setWd4(Long wd4) {
        this.wd4 = wd4;
    }

    public Long getWd5() {
        return wd5;
    }

    public void setWd5(Long wd5) {
        this.wd5 = wd5;
    }

    public String getCorrAddress() {
        return corrAddress;
    }

    public void setCorrAddress(String corrAddress) {
        this.corrAddress = corrAddress;
    }

    public String getOccupancyType() {
        return occupancyType;
    }

    public void setOccupancyType(String occupancyType) {
        this.occupancyType = occupancyType;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getAssLandTypeDesc() {
        return assLandTypeDesc;
    }

    public void setAssLandTypeDesc(String assLandTypeDesc) {
        this.assLandTypeDesc = assLandTypeDesc;
    }

    public String getTppSurveyNumber() {
        return tppSurveyNumber;
    }

    public void setTppSurveyNumber(String tppSurveyNumber) {
        this.tppSurveyNumber = tppSurveyNumber;
    }

    public String getTppKhataNo() {
        return tppKhataNo;
    }

    public void setTppKhataNo(String tppKhataNo) {
        this.tppKhataNo = tppKhataNo;
    }

    public String getTppPlotNo() {
        return tppPlotNo;
    }

    public void setTppPlotNo(String tppPlotNo) {
        this.tppPlotNo = tppPlotNo;
    }

    public String getProAssdRoadfactorDesc() {
        return proAssdRoadfactorDesc;
    }

    public void setProAssdRoadfactorDesc(String proAssdRoadfactorDesc) {
        this.proAssdRoadfactorDesc = proAssdRoadfactorDesc;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getpTaxCollEmpId() {
        return pTaxCollEmpId;
    }

    public void setpTaxCollEmpId(Long pTaxCollEmpId) {
        this.pTaxCollEmpId = pTaxCollEmpId;
    }

    public String getpTaxCollEmpName() {
        return pTaxCollEmpName;
    }

    public void setpTaxCollEmpName(String pTaxCollEmpName) {
        this.pTaxCollEmpName = pTaxCollEmpName;
    }

    public String getpTaxCollEmpEmailId() {
        return pTaxCollEmpEmailId;
    }

    public void setpTaxCollEmpEmailId(String pTaxCollEmpEmailId) {
        this.pTaxCollEmpEmailId = pTaxCollEmpEmailId;
    }

    public String getpTaxCollEmpMobNo() {
        return pTaxCollEmpMobNo;
    }

    public void setpTaxCollEmpMobNo(String pTaxCollEmpMobNo) {
        this.pTaxCollEmpMobNo = pTaxCollEmpMobNo;
    }

    public Long getLandTypeId() {
        return landTypeId;
    }

    public void setLandTypeId(Long landTypeId) {
        this.landTypeId = landTypeId;
    }

    public Long getRoadTypeId() {
        return roadTypeId;
    }

    public void setRoadTypeId(Long roadTypeId) {
        this.roadTypeId = roadTypeId;
    }

    public Double getAssPlotArea() {
        return assPlotArea;
    }

    public void setAssPlotArea(Double assPlotArea) {
        this.assPlotArea = assPlotArea;
    }

    public String getAssessmentCheckFlag() {
        return assessmentCheckFlag;
    }

    public void setAssessmentCheckFlag(String assessmentCheckFlag) {
        this.assessmentCheckFlag = assessmentCheckFlag;
    }

    public String getJointOwnerName() {
        return jointOwnerName;
    }

    public void setJointOwnerName(String jointOwnerName) {
        this.jointOwnerName = jointOwnerName;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

	public Long getOwnerRelationId() {
		return ownerRelationId;
	}

	public void setOwnerRelationId(Long ownerRelationId) {
		this.ownerRelationId = ownerRelationId;
	}

	public String getOwnerNameReg() {
		return ownerNameReg;
	}

	public void setOwnerNameReg(String ownerNameReg) {
		this.ownerNameReg = ownerNameReg;
	}

	public String getPropAddressReg() {
		return propAddressReg;
	}

	public void setPropAddressReg(String propAddressReg) {
		this.propAddressReg = propAddressReg;
	}
	
	public String getUsageTypeReg() {
		return usageTypeReg;
	}

	public void setUsageTypeReg(String usageTypeReg) {
		this.usageTypeReg = usageTypeReg;
	}

	public String getJointOwnerNameReg() {
		return jointOwnerNameReg;
	}

	public void setJointOwnerNameReg(String jointOwnerNameReg) {
		this.jointOwnerNameReg = jointOwnerNameReg;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyTypeReg() {
		return propertyTypeReg;
	}

	public void setPropertyTypeReg(String propertyTypeReg) {
		this.propertyTypeReg = propertyTypeReg;
	}

	public double getPenaltyAmount() {
		return penaltyAmount;
	}

	public void setPenaltyAmount(double penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getServiceShortCode() {
		return serviceShortCode;
	}

	public void setServiceShortCode(String serviceShortCode) {
		this.serviceShortCode = serviceShortCode;
	}

	public String getOldPropNo() {
		return oldPropNo;
	}

	public void setOldPropNo(String oldPropNo) {
		this.oldPropNo = oldPropNo;
	}

	public String getUniquePropertyId() {
		return uniquePropertyId;
	}

	public void setUniquePropertyId(String uniquePropertyId) {
		this.uniquePropertyId = uniquePropertyId;
	}

	public String getTransferTypeDesc() {
		return transferTypeDesc;
	}

	public void setTransferTypeDesc(String transferTypeDesc) {
		this.transferTypeDesc = transferTypeDesc;
	}

	public String getFullOwnerName() {
		return fullOwnerName;
	}

	public void setFullOwnerName(String fullOwnerName) {
		this.fullOwnerName = fullOwnerName;
	}

	public double getTotalArv() {
		return totalArv;
	}

	public void setTotalArv(double totalArv) {
		this.totalArv = totalArv;
	}        	
	
	
}
