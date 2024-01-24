package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TbAcVendormaster implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long vmVendorid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private String vmVendorcode;

    private String venderCodeAndName;

    private Long cpdVendortype;

    private String cpdVendortypeDesc;

    private Long cpdVendorSubType;

    private String cpdVendorSubTypeDesc;

    @Size(max = 200)
    private String vmVendorname;

    @Size(max = 200)
    private String vmVendornamePayto;

    @Size(max = 200)
    private String vmVendoradd;

    @Size(max = 100)
    private String emailId;

    @Size(max = 10)
    private String mobileNo;

    @Size(max = 20)
    private String vmPanNumber;

    @Size(max = 20)
    private String vmPanNumberTemp;

    private Long vmUidNo;

    @Size(max = 20)
    private String tinNumber;

    @Size(max = 20)
    private String pfAcNumber;

    @Size(max = 1)
    private String rtgsvendorflag;

    private Long bankId;

    @Size(max = 20)
    private String bankaccountnumber;

    @Size(max = 20)
    private String ifscCode;

    private Long vmCpdStatus;

    private String vmCpdStatusDesc;

    @Size(max = 200)
    private String remark;

    private Long orgid;

    private Long userId;

    private Long langId;

    private Date lmoddate;

    private Long updatedBy;

    private Long empId;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private Long fi04N1;

    @Size(max = 100)
    private String fi04V1;

    private Date fi04D1;

    @Size(max = 1)
    private String fi04Lo1;

    private Long tbCustbanks;

    private Long pacHeadId;

    private Long functionId;

    private Long vmAuthoId;
    private Date vmAuthoDate;
    private String vmAuthoFlg;

    private String hasError;

    // Added For GST Number

    @Size(max = 15)
    private String vmGstNo;

    private Long vendorClass;

    private String vendorClassName;

    private String acHeadCode;

    private String deductionAmt;

    private String sacHeadId;

    private String sliMode;

    private boolean vendorDetCheckFlag;
    
    private String pfmsVendorId;

    @Transient
    private String uploadFileName;
   
    private List<DocumentDetailsVO> attach = new ArrayList<>();   

    private List<AttachDocs> attachDocsList = new ArrayList<>();
    
    private String removeFileById;
    
    private String msmeNo;
    
    @Size(max = 10)
    private String addMobileNo;
    private String accOldHeadCode;
    
    @Size(max = 200)
    private String vmVendornameReg;
    
    private String leaseFlag;
    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setVmVendorid(final Long vmVendorid) {
        this.vmVendorid = vmVendorid;
    }

    public Long getVmVendorid() {
        return vmVendorid;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setVmVendorcode(final String vmVendorcode) {
        this.vmVendorcode = vmVendorcode;
    }

    public String getVmVendorcode() {
        return vmVendorcode;
    }

    public void setCpdVendortype(final Long cpdVendortype) {
        this.cpdVendortype = cpdVendortype;
    }

    public Long getCpdVendortype() {
        return cpdVendortype;
    }

    public void setCpdVendorSubType(final Long cpdVendorSubType) {
        this.cpdVendorSubType = cpdVendorSubType;
    }

    public Long getCpdVendorSubType() {
        return cpdVendorSubType;
    }

    public void setVmVendorname(final String vmVendorname) {
        this.vmVendorname = vmVendorname;
    }

    public String getVmVendorname() {
        return vmVendorname;
    }

    public void setVmVendoradd(final String vmVendoradd) {
        this.vmVendoradd = vmVendoradd;
    }

    public String getVmVendoradd() {
        return vmVendoradd;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setVmPanNumber(final String vmPanNumber) {
        this.vmPanNumber = vmPanNumber;
    }

    public String getVmPanNumber() {
        return vmPanNumber;
    }

    public void setVmUidNo(final Long vmUidNo) {
        this.vmUidNo = vmUidNo;
    }

    public Long getVmUidNo() {
        return vmUidNo;
    }

    public void setTinNumber(final String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getTinNumber() {
        return tinNumber;
    }
   

    public String getPfAcNumber() {
		return pfAcNumber;
	}

	public void setPfAcNumber(String pfAcNumber) {
		this.pfAcNumber = pfAcNumber;
	}

	public void setRtgsvendorflag(final String rtgsvendorflag) {
        this.rtgsvendorflag = rtgsvendorflag;
    }

    public String getRtgsvendorflag() {
        return rtgsvendorflag;
    }

    public void setBankaccountnumber(final String bankaccountnumber) {
        this.bankaccountnumber = bankaccountnumber;
    }

    public String getBankaccountnumber() {
        return bankaccountnumber;
    }

    public void setIfscCode(final String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setVmCpdStatus(final Long vmCpdStatus) {
        this.vmCpdStatus = vmCpdStatus;
    }

    public Long getVmCpdStatus() {
        return vmCpdStatus;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setFi04N1(final Long fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    /**
     * @return the vmVendornamePayto
     */
    public String getVmVendornamePayto() {
        return vmVendornamePayto;
    }

    /**
     * @param vmVendornamePayto the vmVendornamePayto to set
     */
    public void setVmVendornamePayto(final String vmVendornamePayto) {
        this.vmVendornamePayto = vmVendornamePayto;
    }

    /**
     * @return the vmPanNumberTemp
     */
    public String getVmPanNumberTemp() {
        return vmPanNumberTemp;
    }

    /**
     * @return the tbCustbanks
     */
    public Long getTbCustbanks() {
        return tbCustbanks;
    }

    /**
     * @param tbCustbanks the tbCustbanks to set
     */
    public void setTbCustbanks(final Long tbCustbanks) {
        this.tbCustbanks = tbCustbanks;
    }

    /**
     * @return the empId
     */
    public Long getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(final Long empId) {
        this.empId = empId;
    }

    /**
     * @return the pacHeadId
     */
    public Long getPacHeadId() {
        return pacHeadId;
    }

    /**
     * @param pacHeadId the pacHeadId to set
     */
    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    /**
     * @return the vmAuthoId
     */
    public Long getVmAuthoId() {
        return vmAuthoId;
    }

    /**
     * @param vmAuthoId the vmAuthoId to set
     */
    public void setVmAuthoId(final Long vmAuthoId) {
        this.vmAuthoId = vmAuthoId;
    }

    /**
     * @return the vmAuthoDate
     */
    public Date getVmAuthoDate() {
        return vmAuthoDate;
    }

    /**
     * @param vmAuthoDate the vmAuthoDate to set
     */
    public void setVmAuthoDate(final Date vmAuthoDate) {
        this.vmAuthoDate = vmAuthoDate;
    }

    /**
     * @return the vmAuthoFlg
     */
    public String getVmAuthoFlg() {
        return vmAuthoFlg;
    }

    /**
     * @param vmAuthoFlg the vmAuthoFlg to set
     */
    public void setVmAuthoFlg(final String vmAuthoFlg) {
        this.vmAuthoFlg = vmAuthoFlg;
    }

    /**
     * @return the bankId
     */
    public Long getBankId() {
        return bankId;
    }

    /**
     * @param bankId the bankId to set
     */
    public void setBankId(final Long bankId) {
        this.bankId = bankId;
    }

    public String getVmCpdStatusDesc() {
        return vmCpdStatusDesc;
    }

    public void setVmCpdStatusDesc(final String vmCpdStatusDesc) {
        this.vmCpdStatusDesc = vmCpdStatusDesc;
    }

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public String getCpdVendortypeDesc() {
        return cpdVendortypeDesc;
    }

    public void setCpdVendortypeDesc(final String cpdVendortypeDesc) {
        this.cpdVendortypeDesc = cpdVendortypeDesc;
    }

    public String getCpdVendorSubTypeDesc() {
        return cpdVendorSubTypeDesc;
    }

    public void setCpdVendorSubTypeDesc(final String cpdVendorSubTypeDesc) {
        this.cpdVendorSubTypeDesc = cpdVendorSubTypeDesc;
    }

    public String getVenderCodeAndName() {
        return venderCodeAndName;
    }

    public void setVenderCodeAndName(final String venderCodeAndName) {
        this.venderCodeAndName = venderCodeAndName;
    }

    /**
     * @param vmPanNumberTemp the vmPanNumberTemp to set
     */
    public void setVmPanNumberTemp(final String vmPanNumberTemp) {
        this.vmPanNumberTemp = vmPanNumberTemp;
    }

    public String getVmGstNo() {
        return vmGstNo;
    }

    public void setVmGstNo(final String vmGstNo) {
        this.vmGstNo = vmGstNo;
    }

    public Long getVendorClass() {
        return vendorClass;
    }

    public void setVendorClass(Long vendorClass) {
        this.vendorClass = vendorClass;
    }

    public String getVendorClassName() {
        return vendorClassName;
    }

    public void setVendorClassName(String vendorClassName) {
        this.vendorClassName = vendorClassName;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    public String getAcHeadCode() {
        return acHeadCode;
    }

    public void setAcHeadCode(String acHeadCode) {
        this.acHeadCode = acHeadCode;
    }

    public String getDeductionAmt() {
        return deductionAmt;
    }

    public void setDeductionAmt(String deductionAmt) {
        this.deductionAmt = deductionAmt;
    }

    public String getSliMode() {
        return sliMode;
    }

    public void setSliMode(String sliMode) {
        this.sliMode = sliMode;
    }

    public String getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(String sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public boolean getVendorDetCheckFlag() {
        return vendorDetCheckFlag;
    }

    public void setVendorDetCheckFlag(boolean vendorDetCheckFlag) {
        this.vendorDetCheckFlag = vendorDetCheckFlag;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }	

	public List<DocumentDetailsVO> getAttach() {
		return attach;
	}

	public void setAttach(List<DocumentDetailsVO> attach) {
		this.attach = attach;
	}
	
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public String getRemoveFileById() {
		return removeFileById;
	}

	public void setRemoveFileById(String removeFileById) {
		this.removeFileById = removeFileById;
	}
	

	public String getPfmsVendorId() {
		return pfmsVendorId;
	}

	public void setPfmsVendorId(String pfmsVendorId) {
		this.pfmsVendorId = pfmsVendorId;
	}

	public String getMsmeNo() {
		return msmeNo;
	}

	public void setMsmeNo(String msmeNo) {
		this.msmeNo = msmeNo;
	}
	
	public String getAddMobileNo() {
		return addMobileNo;
	}

	public void setAddMobileNo(String addMobileNo) {
		this.addMobileNo = addMobileNo;
	}

	public String getAccOldHeadCode() {
		return accOldHeadCode;
	}

	public void setAccOldHeadCode(String accOldHeadCode) {
		this.accOldHeadCode = accOldHeadCode;
	}

	public String getVmVendornameReg() {
		return vmVendornameReg;
	}

	public void setVmVendornameReg(String vmVendornameReg) {
		this.vmVendornameReg = vmVendornameReg;
	}

	@Override
	public String toString() {
		return "TbAcVendormaster [vmVendorid=" + vmVendorid + ", vmVendorcode=" + vmVendorcode + ", venderCodeAndName="
				+ venderCodeAndName + ", cpdVendortype=" + cpdVendortype + ", cpdVendortypeDesc=" + cpdVendortypeDesc
				+ ", cpdVendorSubType=" + cpdVendorSubType + ", cpdVendorSubTypeDesc=" + cpdVendorSubTypeDesc
				+ ", vmVendorname=" + vmVendorname + ", vmVendornamePayto=" + vmVendornamePayto + ", vmVendoradd="
				+ vmVendoradd + ", emailId=" + emailId + ", mobileNo=" + mobileNo + ", vmPanNumber=" + vmPanNumber
				+ ", vmPanNumberTemp=" + vmPanNumberTemp + ", vmUidNo=" + vmUidNo + ", tinNumber=" + tinNumber
				+ ", pfAcNumber=" + pfAcNumber + ", rtgsvendorflag=" + rtgsvendorflag + ", bankId=" + bankId
				+ ", bankaccountnumber=" + bankaccountnumber + ", ifscCode=" + ifscCode + ", vmCpdStatus=" + vmCpdStatus
				+ ", vmCpdStatusDesc=" + vmCpdStatusDesc + ", remark=" + remark + ", orgid=" + orgid + ", userId="
				+ userId + ", langId=" + langId + ", lmoddate=" + lmoddate + ", updatedBy=" + updatedBy + ", empId="
				+ empId + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd
				+ ", fi04N1=" + fi04N1 + ", fi04V1=" + fi04V1 + ", fi04D1=" + fi04D1 + ", fi04Lo1=" + fi04Lo1
				+ ", tbCustbanks=" + tbCustbanks + ", pacHeadId=" + pacHeadId + ", functionId=" + functionId
				+ ", vmAuthoId=" + vmAuthoId + ", vmAuthoDate=" + vmAuthoDate + ", vmAuthoFlg=" + vmAuthoFlg
				+ ", hasError=" + hasError + ", vmGstNo=" + vmGstNo + ", vendorClass=" + vendorClass
				+ ", vendorClassName=" + vendorClassName + ", acHeadCode=" + acHeadCode + ", deductionAmt="
				+ deductionAmt + ", sacHeadId=" + sacHeadId + ", sliMode=" + sliMode + ", vendorDetCheckFlag="
				+ vendorDetCheckFlag + ", pfmsVendorId=" + pfmsVendorId + ", uploadFileName=" + uploadFileName
				+ ", attach=" + attach + ", attachDocsList=" + attachDocsList + ", removeFileById=" + removeFileById 
				+ ", vmVendornameReg=" + vmVendornameReg +"]";
	}

	public String getLeaseFlag() {
		return leaseFlag;
	}

	public void setLeaseFlag(String leaseFlag) {
		this.leaseFlag = leaseFlag;
	}

	
		
}
