package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.TbBillMas;

public class NoDuesCertificateDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String propNo;
	
	private String oldpropno;
	
	private String applicantName;
	
	private String appliAddress;
	
	private Long appliPincode;
	
    private String appliMobileno;
    
    private String appliEmail;
    
	private String flatNo;

    private Long orgId;
    
    private Long deptId;
    
    private Long apmApplicationId;
    
	private String applicationNo;

    private Long smServiceId;
    
    private int langId;
    
    private Long empId;
    
    private String appliChargeFlag;
    
    private double totalPaybleAmt;
    
    private String propAddress;
    
    private String corresPropAddress;
    
    private double totalOutsatandingAmt;
    
    private String ownerName;
    
    private String ownerAddress;
    
    private String ownerCorrAdd;
    
    private String serviceName;
    
    private Long noOfCopies;
    
	private String macAddress;

    private String successFlag;
    
	private String serviceUrl;

	private Long smChklstVerify;

	private String smScrutinyApplicableFlag;

	private Long smFeesSchedule;

	private String smProcessLookUpCode;

	private String smShortCode;

    private List<DocumentDetailsVO> docs = new ArrayList<>(0);
    
    List<BillDisplayDto> charges = new ArrayList<>(0);

	private ApplicantDetailDTO applicantDto = null;

	private List<NoDuesPropertyDetailsDto> propertyDetails = new ArrayList<>(0);

	private Map<Long, String> financialYearMap = null;

	private String outwardNo;
	
	List<TbBillMas> billMasList = new ArrayList<TbBillMas>(0);
	
	private Date firstSemDate;
	
	private Long currentFinYearId;
	
	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getAppliAddress() {
		return appliAddress;
	}

	public void setAppliAddress(String appliAddress) {
		this.appliAddress = appliAddress;
	}

	public Long getAppliPincode() {
		return appliPincode;
	}

	public void setAppliPincode(Long appliPincode) {
		this.appliPincode = appliPincode;
	}

	public String getAppliMobileno() {
		return appliMobileno;
	}

	public void setAppliMobileno(String appliMobileno) {
		this.appliMobileno = appliMobileno;
	}

	public String getAppliEmail() {
		return appliEmail;
	}

	public void setAppliEmail(String appliEmail) {
		this.appliEmail = appliEmail;
	}


	public List<DocumentDetailsVO> getDocs() {
		return docs;
	}

	public void setDocs(List<DocumentDetailsVO> docs) {
		this.docs = docs;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
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

	public String getAppliChargeFlag() {
		return appliChargeFlag;
	}

	public void setAppliChargeFlag(String appliChargeFlag) {
		this.appliChargeFlag = appliChargeFlag;
	}

	public double getTotalPaybleAmt() {
		return totalPaybleAmt;
	}

	public void setTotalPaybleAmt(double totalPaybleAmt) {
		this.totalPaybleAmt = totalPaybleAmt;
	}

	public List<BillDisplayDto> getCharges() {
		return charges;
	}

	public void setCharges(List<BillDisplayDto> charges) {
		this.charges = charges;
	}

	public String getPropNo() {
		return propNo;
	}

	public void setPropNo(String propNo) {
		this.propNo = propNo;
	}

	public String getOldpropno() {
		return oldpropno;
	}

	public void setOldpropno(String oldpropno) {
		this.oldpropno = oldpropno;
	}

	public String getPropAddress() {
		return propAddress;
	}

	public void setPropAddress(String propAddress) {
		this.propAddress = propAddress;
	}

	public String getCorresPropAddress() {
		return corresPropAddress;
	}

	public void setCorresPropAddress(String corresPropAddress) {
		this.corresPropAddress = corresPropAddress;
	}

	public double getTotalOutsatandingAmt() {
		return totalOutsatandingAmt;
	}

	public void setTotalOutsatandingAmt(double totalOutsatandingAmt) {
		this.totalOutsatandingAmt = totalOutsatandingAmt;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerAddress() {
		return ownerAddress;
	}

	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}

	public String getOwnerCorrAdd() {
		return ownerCorrAdd;
	}

	public void setOwnerCorrAdd(String ownerCorrAdd) {
		this.ownerCorrAdd = ownerCorrAdd;
	}

	public Long getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Long noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	public ApplicantDetailDTO getApplicantDto() {
		return applicantDto;
	}

	public void setApplicantDto(ApplicantDetailDTO applicantDto) {
		this.applicantDto = applicantDto;
	}

	public List<NoDuesPropertyDetailsDto> getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(List<NoDuesPropertyDetailsDto> propertyDetails) {
		this.propertyDetails = propertyDetails;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public Long getSmChklstVerify() {
		return smChklstVerify;
	}

	public void setSmChklstVerify(Long smChklstVerify) {
		this.smChklstVerify = smChklstVerify;
	}

	public String getSmScrutinyApplicableFlag() {
		return smScrutinyApplicableFlag;
	}

	public void setSmScrutinyApplicableFlag(String smScrutinyApplicableFlag) {
		this.smScrutinyApplicableFlag = smScrutinyApplicableFlag;
	}

	public Long getSmFeesSchedule() {
		return smFeesSchedule;
	}

	public void setSmFeesSchedule(Long smFeesSchedule) {
		this.smFeesSchedule = smFeesSchedule;
	}

	public String getSmProcessLookUpCode() {
		return smProcessLookUpCode;
	}

	public void setSmProcessLookUpCode(String smProcessLookUpCode) {
		this.smProcessLookUpCode = smProcessLookUpCode;
	}

	public String getSmShortCode() {
		return smShortCode;
	}

	public void setSmShortCode(String smShortCode) {
		this.smShortCode = smShortCode;
	}

	public Map<Long, String> getFinancialYearMap() {
		return financialYearMap;
	}

	public void setFinancialYearMap(Map<Long, String> financialYearMap) {
		this.financialYearMap = financialYearMap;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getOutwardNo() {
		return outwardNo;
	}

	public void setOutwardNo(String outwardNo) {
		this.outwardNo = outwardNo;
	}

	public List<TbBillMas> getBillMasList() {
		return billMasList;
	}

	public void setBillMasList(List<TbBillMas> billMasList) {
		this.billMasList = billMasList;
	}

	public Date getFirstSemDate() {
		return firstSemDate;
	}

	public void setFirstSemDate(Date firstSemDate) {
		this.firstSemDate = firstSemDate;
	}

	public Long getCurrentFinYearId() {
		return currentFinYearId;
	}

	public void setCurrentFinYearId(Long currentFinYearId) {
		this.currentFinYearId = currentFinYearId;
	}
	
	
}
