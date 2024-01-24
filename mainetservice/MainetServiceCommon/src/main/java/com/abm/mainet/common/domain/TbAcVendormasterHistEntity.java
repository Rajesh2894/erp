package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = " TB_VENDORMASTER_HIST")
public class TbAcVendormasterHistEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_VM_VENDORID", nullable = false)
    private Long hvmVendorid;
    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
	
	@Column(name = "VM_VENDORID", nullable = false)
    private Long vmVendorid;
	
    @Column(name = "VM_VENDORCODE")
    private String vmVendorcode;

    @Column(name = "CPD_VENDORTYPE")
    private Long cpdVendortype;

    @Column(name = "CPD_VENDORSUBTYPE")
    private Long cpdVendorSubType;

    @Column(name = "VM_VENDORNAME")
    private String vmVendorname;

    @Column(name = "VM_VENDORNAME_PAYTO")
    private String vmVendornamePayto;

    @Column(name = "VM_VENDORADD")
    private String vmVendoradd;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Column(name = "VM_PAN_NUMBER")
    private String vmPanNumber;

    @Column(name = "VM_UID_NO")
    private Long vmUidNo;

    @Column(name = "TIN_NUMBER")
    private String tinNumber;

    @Column(name = "TAN_NUMBER")
    private String tanNumber;

    @Column(name = "RTGS_VENDOR_FLAG")
    private String rtgsvendorflag;

    @Column(name = "BANKACCOUNTNUMBER")
    private String bankaccountnumber;

    @Column(name = "IFSC_CODE")
    private String ifscCode;

    @Column(name = "VM_CPD_STATUS")
    private Long vmCpdStatus;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "LANG_ID")
    private Long langId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LMODDATE")
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "FI04_N1")
    private Long fi04N1;

    @Column(name = "FI04_V1")
    private String fi04V1;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FI04_D1")
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1;

    @Column(name = "PAC_HEAD_ID")
    private Long pacHeadId;

    @Column(name = "EMPID")
    private Long empId;

    @Column(name = "VM_AUTHO_ID")
    private Long vmAuthoId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VM_AUTHO_DATE")
    private Date vmAuthoDate;

    @Column(name = "VM_AUTHO_FLG", length = 1)
    private String vmAuthoFlg;

    @Column(name = "VM_GST_NO")
    private String vmGstNo;

    @Column(name = "VM_CLASS")
    private Long vendorClass;
    
    @Column(name = "H_STATUS", length = 1)
   	private Character hStatus;
    
    @Column(name = "PFMS_VENDORID", length = 20)
    private String pfmsVendorId;
    
    @Column(name = "ADD_MOBILE_NO")
    private String addMobileNo;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------

    @Column(name = "BANKID")
    private Long bankId;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public TbAcVendormasterHistEntity() {
        super();
    }
    public String[] getPkValues() {
        return new String[] { "AC", " TB_VENDORMASTER_HIST", "H_VM_VENDORID" };
    }
	public Long getHvmVendorid() {
		return hvmVendorid;
	}
	public void setHvmVendorid(Long hvmVendorid) {
		this.hvmVendorid = hvmVendorid;
	}
	public Long getVmVendorid() {
		return vmVendorid;
	}
	public void setVmVendorid(Long vmVendorid) {
		this.vmVendorid = vmVendorid;
	}
	public String getVmVendorcode() {
		return vmVendorcode;
	}
	public void setVmVendorcode(String vmVendorcode) {
		this.vmVendorcode = vmVendorcode;
	}
	public Long getCpdVendortype() {
		return cpdVendortype;
	}
	public void setCpdVendortype(Long cpdVendortype) {
		this.cpdVendortype = cpdVendortype;
	}
	public Long getCpdVendorSubType() {
		return cpdVendorSubType;
	}
	public void setCpdVendorSubType(Long cpdVendorSubType) {
		this.cpdVendorSubType = cpdVendorSubType;
	}
	public String getVmVendorname() {
		return vmVendorname;
	}
	public void setVmVendorname(String vmVendorname) {
		this.vmVendorname = vmVendorname;
	}
	public String getVmVendornamePayto() {
		return vmVendornamePayto;
	}
	public void setVmVendornamePayto(String vmVendornamePayto) {
		this.vmVendornamePayto = vmVendornamePayto;
	}
	public String getVmVendoradd() {
		return vmVendoradd;
	}
	public void setVmVendoradd(String vmVendoradd) {
		this.vmVendoradd = vmVendoradd;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getVmPanNumber() {
		return vmPanNumber;
	}
	public void setVmPanNumber(String vmPanNumber) {
		this.vmPanNumber = vmPanNumber;
	}
	public Long getVmUidNo() {
		return vmUidNo;
	}
	public void setVmUidNo(Long vmUidNo) {
		this.vmUidNo = vmUidNo;
	}
	public String getTinNumber() {
		return tinNumber;
	}
	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}
	public String getTanNumber() {
		return tanNumber;
	}
	public void setTanNumber(String tanNumber) {
		this.tanNumber = tanNumber;
	}
	public String getRtgsvendorflag() {
		return rtgsvendorflag;
	}
	public void setRtgsvendorflag(String rtgsvendorflag) {
		this.rtgsvendorflag = rtgsvendorflag;
	}
	public String getBankaccountnumber() {
		return bankaccountnumber;
	}
	public void setBankaccountnumber(String bankaccountnumber) {
		this.bankaccountnumber = bankaccountnumber;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public Long getVmCpdStatus() {
		return vmCpdStatus;
	}
	public void setVmCpdStatus(Long vmCpdStatus) {
		this.vmCpdStatus = vmCpdStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getLangId() {
		return langId;
	}
	public void setLangId(Long langId) {
		this.langId = langId;
	}
	public Date getLmoddate() {
		return lmoddate;
	}
	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
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
	public Long getFi04N1() {
		return fi04N1;
	}
	public void setFi04N1(Long fi04n1) {
		fi04N1 = fi04n1;
	}
	public String getFi04V1() {
		return fi04V1;
	}
	public void setFi04V1(String fi04v1) {
		fi04V1 = fi04v1;
	}
	public Date getFi04D1() {
		return fi04D1;
	}
	public void setFi04D1(Date fi04d1) {
		fi04D1 = fi04d1;
	}
	public String getFi04Lo1() {
		return fi04Lo1;
	}
	public void setFi04Lo1(String fi04Lo1) {
		this.fi04Lo1 = fi04Lo1;
	}
	public Long getPacHeadId() {
		return pacHeadId;
	}
	public void setPacHeadId(Long pacHeadId) {
		this.pacHeadId = pacHeadId;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public Long getVmAuthoId() {
		return vmAuthoId;
	}
	public void setVmAuthoId(Long vmAuthoId) {
		this.vmAuthoId = vmAuthoId;
	}
	public Date getVmAuthoDate() {
		return vmAuthoDate;
	}
	public void setVmAuthoDate(Date vmAuthoDate) {
		this.vmAuthoDate = vmAuthoDate;
	}
	public String getVmAuthoFlg() {
		return vmAuthoFlg;
	}
	public void setVmAuthoFlg(String vmAuthoFlg) {
		this.vmAuthoFlg = vmAuthoFlg;
	}
	public String getVmGstNo() {
		return vmGstNo;
	}
	public void setVmGstNo(String vmGstNo) {
		this.vmGstNo = vmGstNo;
	}
	public Long getVendorClass() {
		return vendorClass;
	}
	public void setVendorClass(Long vendorClass) {
		this.vendorClass = vendorClass;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public Character gethStatus() {
		return hStatus;
	}
	public void sethStatus(Character hStatus) {
		this.hStatus = hStatus;
	}
	
	
	public String getPfmsVendorId() {
		return pfmsVendorId;
	}
	public void setPfmsVendorId(String pfmsVendorId) {
		this.pfmsVendorId = pfmsVendorId;
	}
	
	public String getAddMobileNo() {
		return addMobileNo;
	}
	public void setAddMobileNo(String addMobileNo) {
		this.addMobileNo = addMobileNo;
	}
	
	@Override
	public String toString() {
		return "TbAcVendormasterHistEntity [hvmVendorid=" + hvmVendorid + ", vmVendorid=" + vmVendorid
				+ ", vmVendorcode=" + vmVendorcode + ", cpdVendortype=" + cpdVendortype + ", cpdVendorSubType="
				+ cpdVendorSubType + ", vmVendorname=" + vmVendorname + ", vmVendornamePayto=" + vmVendornamePayto
				+ ", vmVendoradd=" + vmVendoradd + ", emailId=" + emailId + ", mobileNo=" + mobileNo + ", vmPanNumber="
				+ vmPanNumber + ", vmUidNo=" + vmUidNo + ", tinNumber=" + tinNumber + ", tanNumber=" + tanNumber
				+ ", rtgsvendorflag=" + rtgsvendorflag + ", bankaccountnumber=" + bankaccountnumber + ", ifscCode="
				+ ifscCode + ", vmCpdStatus=" + vmCpdStatus + ", remark=" + remark + ", orgid=" + orgid + ", userId="
				+ userId + ", langId=" + langId + ", lmoddate=" + lmoddate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", fi04N1="
				+ fi04N1 + ", fi04V1=" + fi04V1 + ", fi04D1=" + fi04D1 + ", fi04Lo1=" + fi04Lo1 + ", pacHeadId="
				+ pacHeadId + ", empId=" + empId + ", vmAuthoId=" + vmAuthoId + ", vmAuthoDate=" + vmAuthoDate
				+ ", vmAuthoFlg=" + vmAuthoFlg + ", vmGstNo=" + vmGstNo + ", vendorClass=" + vendorClass + ", hStatus="
				+ hStatus + ", pfmsVendorId=" + pfmsVendorId + ", bankId=" + bankId + "]";
	}
	

		
}
