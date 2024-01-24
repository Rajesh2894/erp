package com.abm.mainet.smsemail.dto;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.AbstractDTO;

/**
 *
 * @author Kavali.Kiran
 *
 */
@Component
public class SMSAndEmailDTO extends AbstractDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * below four fields are mandatory to set before passing dto for mail and sms
     */
    private String deptShortCode;
    private String serviceUrl;
    private String templateType;
    private String transactionType;

    private String appName;
    private String appNo;
    private String appDate;
    private String appAmount;
    private String email;
    private String servName;
    private String challanNo;
    private String mobnumber;
    private String servDur;
    private String challanAmt;
    private String regNo;
    private String regReason;

    private String newOrgOrDept;
    private String cc;
    private String bcc;

    private String loiAmt;
    private String loiNo;
    private String firstApplNo;
    private String hearDate;
    private String msg;
    private String agencyNo;
    private String password;
    private String userName;

    private String contractNo;
    private String ownerName;

    private String hearingNo;
    private String cancelDate;

    private String noticeNo;
    private String noticeDate;
    private String hearingText;
    private String agencyName;
    private String agencyContractNo;

    private String suspensionDate;

    private String billNo;
    private String FrmDt;
    private String ToDt;
    private String dueDt;
    private double amount;

    private String n_reg;
    private String v_sysdate;
    private String orgName;
    private String conNo;
    private String inward_type;
    private String v_marked_you;
    private String v_muncipality_name;
    private String v_emp_action;
    private String v_out_reg_date;
    private String v_out_acct_date;

    private String currentDate;
    private String P1; // used for contractNo for Quartz Job Scheduler
    private String P2; // used for Contract end date for Quartz Job Scheduler
    private String gracePeriod;
    private String tenantNo;
    private String tntTenantNo;
    private String empName;
    private String caseID;
    private String servNameMar;
    private List<String> MobilenumberList;
    private String dispMode;
    private int langId;
    private String contextPath;
    private String organizationName;
    private String docVerificationDate;
    private String noOfWorkingDays;
    private Long orgId;
    private String tokenNumber;
    private String status;
    private String decision;

    private String oneTimePassword;

    private String tenderNo;
    private String tenderDate;
    private String tenderWorkName;
    private String loaNo;
    private String loaDate;
    private String propertyNo;
    private String referenceNo;
    private String objectionNo;

    // used for council meeting sms and email body
    private String type;
    private Date date;
    private String place;
    private StringBuilder emailBody;
    private String licNo;

    // used for Care Module Department user sms and email body

    private String slaDays;

    private String subtype;

    private String businessName;
    
    private Long serviceId;
    
    private String remarks;
    
    private String subject;
    
    private String caseDate;
	
	private String flatNo;
    
    public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    private List<File> filesForAttach = new ArrayList<>();

    Map<String, String> inlineImages = new HashMap<String, String>();

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(final String contextPath) {
        this.contextPath = contextPath;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDispMode() {
        return dispMode;
    }

    public void setDispMode(final String dispMode) {
        this.dispMode = dispMode;
    }

    public String getHearDate() {
        return hearDate;
    }

    public void setHearDate(final String hearDate) {
        this.hearDate = hearDate;
    }

    public String getFirstApplNo() {
        return firstApplNo;
    }

    public void setFirstApplNo(final String firstApplNo) {
        this.firstApplNo = firstApplNo;
    }

    public String getLoiAmt() {
        return loiAmt;
    }

    public void setLoiAmt(final String loiAmt) {
        this.loiAmt = loiAmt;
    }

    public String getLoiNo() {
        return loiNo;
    }

    public void setLoiNo(final String loiNo) {
        this.loiNo = loiNo;
    }

    public List<File> getFilesForAttach() {
        return filesForAttach;
    }

    public void setFilesForAttach(final List<File> filesForAttach) {
        this.filesForAttach = filesForAttach;
    }

    public String getAppName() {
        return appName;
    }

    public String getServName() {
        return servName;
    }

    public void setServName(final String servName) {
        this.servName = servName;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(final String challanNo) {
        this.challanNo = challanNo;
    }

    public String getServDur() {
        return servDur;
    }

    public void setServDur(final String servDur) {
        this.servDur = servDur;
    }

    public String getChallanAmt() {
        return challanAmt;
    }

    public void setChallanAmt(final String challanAmt) {
        this.challanAmt = challanAmt;
    }

    public void setAppName(final String appName) {
        this.appName = appName;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(final String appNo) {
        this.appNo = appNo;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(final String appDate) {
        this.appDate = appDate;
    }

    public String getAppAmount() {
        return appAmount;
    }

    public void setAppAmount(final String appAmount) {
        this.appAmount = appAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getMobnumber() {
        return mobnumber;
    }

    public void setMobnumber(final String mobnumber) {
        this.mobnumber = mobnumber;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(final String regNo) {
        this.regNo = regNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getAgencyNo() {
        return agencyNo;
    }

    public void setAgencyNo(final String agencyNo) {
        this.agencyNo = agencyNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(final String contractNo) {
        this.contractNo = contractNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    public String getHearingNo() {
        return hearingNo;
    }

    public void setHearingNo(final String hearingNo) {
        this.hearingNo = hearingNo;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(final String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getAgencyContractNo() {
        return agencyContractNo;
    }

    public void setAgencyContractNo(final String agencyContractNo) {
        this.agencyContractNo = agencyContractNo;
    }

    public String getSuspensionDate() {
        return suspensionDate;
    }

    public void setSuspensionDate(final String suspensionDate) {
        this.suspensionDate = suspensionDate;
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(final String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(final String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getHearingText() {
        return hearingText;
    }

    public void setHearingText(final String hearingText) {
        this.hearingText = hearingText;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(final String agencyName) {
        this.agencyName = agencyName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(final String billNo) {
        this.billNo = billNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public String getFrmDt() {
        return FrmDt;
    }

    public void setFrmDt(final String frmDt) {
        FrmDt = frmDt;
    }

    public String getToDt() {
        return ToDt;
    }

    public void setToDt(final String toDt) {
        ToDt = toDt;
    }

    public String getDueDt() {
        return dueDt;
    }

    public void setDueDt(final String dueDt) {
        this.dueDt = dueDt;
    }

    public String getN_reg() {
        return n_reg;
    }

    public void setN_reg(final String n_reg) {
        this.n_reg = n_reg;
    }

    public String getV_sysdate() {
        return v_sysdate;
    }

    public void setV_sysdate(final String v_sysdate) {
        this.v_sysdate = v_sysdate;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(final String orgName) {
        this.orgName = orgName;
    }

    public String getConNo() {
        return conNo;
    }

    public void setConNo(final String conNo) {
        this.conNo = conNo;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(final String currentDate) {
        this.currentDate = currentDate;
    }

    public String getP1() {
        return P1;
    }

    public void setP1(final String p1) {
        P1 = p1;
    }

    public String getP2() {
        return P2;
    }

    public void setP2(final String p2) {
        P2 = p2;
    }

    public String getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(final String gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public String getInward_type() {
        return inward_type;
    }

    public void setInward_type(final String inward_type) {
        this.inward_type = inward_type;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(final String cc) {
        this.cc = cc;
    }

    public String getV_marked_you() {
        return v_marked_you;
    }

    public void setV_marked_you(final String v_marked_you) {
        this.v_marked_you = v_marked_you;
    }

    public String getV_muncipality_name() {
        return v_muncipality_name;
    }

    public void setV_muncipality_name(final String v_muncipality_name) {
        this.v_muncipality_name = v_muncipality_name;
    }

    public String getV_emp_action() {
        return v_emp_action;
    }

    public void setV_emp_action(final String v_emp_action) {
        this.v_emp_action = v_emp_action;
    }

    public String getV_out_reg_date() {
        return v_out_reg_date;
    }

    public void setV_out_reg_date(final String v_out_reg_date) {
        this.v_out_reg_date = v_out_reg_date;
    }

    public String getV_out_acct_date() {
        return v_out_acct_date;
    }

    public void setV_out_acct_date(final String v_out_acct_date) {
        this.v_out_acct_date = v_out_acct_date;
    }

    public String getRegReason() {
        return regReason;
    }

    public void setRegReason(final String regReason) {
        this.regReason = regReason;
    }

    public String getNewOrgOrDept() {
        return newOrgOrDept;
    }

    public void setNewOrgOrDept(final String newOrgOrDept) {
        this.newOrgOrDept = newOrgOrDept;
    }

    public String getTenantNo() {
        return tenantNo;
    }

    public void setTenantNo(final String tenantNo) {
        this.tenantNo = tenantNo;
    }

    public String getTntTenantNo() {
        return tntTenantNo;
    }

    public void setTntTenantNo(final String tntTenantNo) {
        this.tntTenantNo = tntTenantNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(final String empName) {
        this.empName = empName;
    }

    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(final String caseID) {
        this.caseID = caseID;
    }

    public String getServNameMar() {
        return servNameMar;
    }

    public void setServNameMar(final String servNameMar) {
        this.servNameMar = servNameMar;
    }

    public List<String> getMobilenumberList() {
        return MobilenumberList;
    }

    public void setMobilenumberList(final List<String> mobilenumberList) {
        MobilenumberList = mobilenumberList;
    }

    public String getDocVerificationDate() {
        return docVerificationDate;
    }

    public void setDocVerificationDate(final String docVerificationDate) {
        this.docVerificationDate = docVerificationDate;
    }

    public String getNoOfWorkingDays() {
        return noOfWorkingDays;
    }

    public void setNoOfWorkingDays(final String noOfWorkingDays) {
        this.noOfWorkingDays = noOfWorkingDays;
    }

    public String getDeptShortCode() {
        return deptShortCode;
    }

    public void setDeptShortCode(final String deptShortCode) {
        this.deptShortCode = deptShortCode;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(final String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(final String templateType) {
        this.templateType = templateType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(final String transactionType) {
        this.transactionType = transactionType;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(final String bcc) {
        this.bcc = bcc;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(final String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(final String decision) {
        this.decision = decision;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(final String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public String getTenderNo() {
        return tenderNo;
    }

    public void setTenderNo(String tenderNo) {
        this.tenderNo = tenderNo;
    }

    public String getTenderDate() {
        return tenderDate;
    }

    public void setTenderDate(String tenderDate) {
        this.tenderDate = tenderDate;
    }

    public String getTenderWorkName() {
        return tenderWorkName;
    }

    public void setTenderWorkName(String tenderWorkName) {
        this.tenderWorkName = tenderWorkName;
    }

    public String getLoaNo() {
        return loaNo;
    }

    public void setLoaNo(String loaNo) {
        this.loaNo = loaNo;
    }

    public String getLoaDate() {
        return loaDate;
    }

    public void setLoaDate(String loaDate) {
        this.loaDate = loaDate;
    }

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getObjectionNo() {
        return objectionNo;
    }

    public void setObjectionNo(String objectionNo) {
        this.objectionNo = objectionNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public StringBuilder getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(StringBuilder emailBody) {
        this.emailBody = emailBody;
    }

    public String getSlaDays() {
        return slaDays;
    }

    public void setSlaDays(String slaDays) {
        this.slaDays = slaDays;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Map<String, String> getInlineImages() {
        return inlineImages;
    }

    public void setInlineImages(Map<String, String> inlineImages) {
        this.inlineImages = inlineImages;
    }

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCaseDate() {
		return caseDate;
	}

	public void setCaseDate(String caseDate) {
		this.caseDate = caseDate;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}
	
}
