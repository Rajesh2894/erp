/**
 * 
 */
package com.abm.mainet.socialsecurity.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author priti.singh
 *
 */

@Entity
@Table(name = "TB_SWD_SCHEME_APPLICATION")
public class SocialSecurityApplicationForm implements Serializable {

    private static final long serialVersionUID = -6664054119735051186L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SAPI_ID", nullable = false)
    private Long applicationId;

    @Column(name = "SDSCH_ID", nullable = false)
    private Long selectSchemeName;

    @Column(name = "SAPI_NAME", nullable = false)
    private String nameofApplicant;

    @Column(name = "SAPI_DATE", nullable = false)
    private Date applicationDob;

    @Column(name = "SAPI_AGE", nullable = false)
    private Long ageason;

    @Column(name = "SAPI_GEN", nullable = false)
    private Long genderId;

    @Column(name = "SAPI_ADDRESS", nullable = false)
    private String applicantAdress;

    @Column(name = "SAPI_PINCODE", nullable = false)
    private Long pinCode;

    @Column(name = "SAPI_MOBILE", nullable = false)
    private Long mobNum;

    @Column(name = "SAPI_EDUCATION", nullable = false)
    private Long educationId;

    @Column(name = "SAPI_CLASS", nullable = false)
    private Long classs;

    @Column(name = "SAPI_MARTIALSTATUS", nullable = false)
    private Long maritalStatusId;

    @Column(name = "SAPI_CATEGORY", nullable = false)
    private Long categoryId;

    @Column(name = "SAPI_ANNUAL", nullable = false)
    private BigDecimal annualIncome;

    @Column(name = "SAPI_DISABILITY", nullable = false)
    private Long typeofDisId;// DOUBT

    @Column(name = "SAPI_PERCENTAGE", nullable = false)
    private Long percenrofDis;

    @Column(name = "SAPI_BPL", nullable = false)
    private String bplid;

    @Column(name = "SAPI_INSPECTIONYE", nullable = false)
    private Long bplinspectyr;

    @Column(name = "SAPI_FAMILYID", nullable = false)
    private String bplfamily;

    @Column(name = "SAPI_BANKID", nullable = false)
    private Long bankNameId;

    @Column(name = "SAPI_ACCOUNTID", nullable = false)
    private Long accountNumber;

    @Column(name = "SAPI_BRANCH_ID", nullable = false)
    private Long branchname;

    @Column(name = "SAPI_IFSC_ID", nullable = false)
    private String ifsccode;

    @Column(name = "SAPI_APP_FNAME", nullable = false)
    private String nameofFather;

    @Column(name = "SAPI_APP_MNAME", nullable = false)
    private String nameofMother;

    @Column(name = "SAPI_APP_CONTNO", nullable = false)
    private Long contactNumber;

    @Column(name = "SAPI_APP_FINC", nullable = false)
    private String detailsoffamIncomeSource;

    @Column(name = "SAPI_APP_ANNUAL", nullable = false)
    private BigDecimal annualIncomeoffam;

    @Column(name = "APM_APPLICATION_ID", nullable = false)
    private String apmApplicationId;

    @Column(name = "SAPI_STATUS", nullable = false)
    private String sapiStatus;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;
    
    @Column(name = "SAPI_LAST_PAYMENTDT", nullable = true)
    private Date lastPaymentDate;
    
    @Column(name = "SAPI_LAST_PAYMENTNO", nullable = true)
    private Long referenceno;
    
    @Column(name = "SAIP_TR_TY", nullable = true)
    private String datalegacyflag;
    
    @Column(name = "BENEFICIARY_NUMBER", nullable = true)
    private String beneficiarynumber;
    
    @Column(name = "SAPI_LAST_LIFECERTDATE", nullable = true)
    private Date lastDateofLifeCerti;

   // @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALID_TO_DATE", nullable = true)
    private Date validtoDate;

    public static String[] getPkValues() {
        return new String[] { "SWD", "TB_SWD_SCHEME_APPLICATION", "SAPI_ID" };
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getSelectSchemeName() {
        return selectSchemeName;
    }

    public void setSelectSchemeName(Long selectSchemeName) {
        this.selectSchemeName = selectSchemeName;
    }

    public String getNameofApplicant() {
        return nameofApplicant;
    }

    public void setNameofApplicant(String nameofApplicant) {
        this.nameofApplicant = nameofApplicant;
    }

    public Date getApplicationDob() {
        return applicationDob;
    }

    public void setApplicationDob(Date applicationDob) {
        this.applicationDob = applicationDob;
    }

    public Long getAgeason() {
        return ageason;
    }

    public void setAgeason(Long ageason) {
        this.ageason = ageason;
    }

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public String getApplicantAdress() {
        return applicantAdress;
    }

    public void setApplicantAdress(String applicantAdress) {
        this.applicantAdress = applicantAdress;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public Long getMobNum() {
        return mobNum;
    }

    public void setMobNum(Long mobNum) {
        this.mobNum = mobNum;
    }

    public Long getEducationId() {
        return educationId;
    }

    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    public Long getClasss() {
        return classs;
    }

    public void setClasss(Long classs) {
        this.classs = classs;
    }

    public Long getMaritalStatusId() {
        return maritalStatusId;
    }

    public void setMaritalStatusId(Long maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public Long getTypeofDisId() {
        return typeofDisId;
    }

    public void setTypeofDisId(Long typeofDisId) {
        this.typeofDisId = typeofDisId;
    }

    public Long getPercenrofDis() {
        return percenrofDis;
    }

    public void setPercenrofDis(Long percenrofDis) {
        this.percenrofDis = percenrofDis;
    }

    public String getBplid() {
        return bplid;
    }

    public void setBplid(String bplid) {
        this.bplid = bplid;
    }

    public Long getBplinspectyr() {
        return bplinspectyr;
    }

    public void setBplinspectyr(Long bplinspectyr) {
        this.bplinspectyr = bplinspectyr;
    }

    public String getBplfamily() {
        return bplfamily;
    }

    public void setBplfamily(String bplfamily) {
        this.bplfamily = bplfamily;
    }

    public Long getBankNameId() {
        return bankNameId;
    }

    public void setBankNameId(Long bankNameId) {
        this.bankNameId = bankNameId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getBranchname() {
        return branchname;
    }

    public void setBranchname(Long branchname) {
        this.branchname = branchname;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getNameofFather() {
        return nameofFather;
    }

    public void setNameofFather(String nameofFather) {
        this.nameofFather = nameofFather;
    }

    public String getNameofMother() {
        return nameofMother;
    }

    public void setNameofMother(String nameofMother) {
        this.nameofMother = nameofMother;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDetailsoffamIncomeSource() {
        return detailsoffamIncomeSource;
    }

    public void setDetailsoffamIncomeSource(String detailsoffamIncomeSource) {
        this.detailsoffamIncomeSource = detailsoffamIncomeSource;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public BigDecimal getAnnualIncomeoffam() {
        return annualIncomeoffam;
    }

    public void setAnnualIncomeoffam(BigDecimal annualIncomeoffam) {
        this.annualIncomeoffam = annualIncomeoffam;
    }

    public String getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(String apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getSapiStatus() {
        return sapiStatus;
    }

    public void setSapiStatus(String sapiStatus) {
        this.sapiStatus = sapiStatus;
    }

	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}

	public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	public Long getReferenceno() {
		return referenceno;
	}

	public void setReferenceno(Long referenceno) {
		this.referenceno = referenceno;
	}

	public String getDatalegacyflag() {
		return datalegacyflag;
	}

	public void setDatalegacyflag(String datalegacyflag) {
		this.datalegacyflag = datalegacyflag;
	}

	public String getBeneficiarynumber() {
		return beneficiarynumber;
	}

	public void setBeneficiarynumber(String beneficiarynumber) {
		this.beneficiarynumber = beneficiarynumber;
	}

	public Date getLastDateofLifeCerti() {
		return lastDateofLifeCerti;
	}

	public void setLastDateofLifeCerti(Date lastDateofLifeCerti) {
		this.lastDateofLifeCerti = lastDateofLifeCerti;
	}

	public Date getValidtoDate() {
		return validtoDate;
	}

	public void setValidtoDate(Date validtoDate) {
		this.validtoDate = validtoDate;
	}
    
    

}
