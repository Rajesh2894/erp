package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Persistent class for entity stored in table "TB_VENDORMASTER"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_VENDORMASTER")
// Define named queries here
@NamedQueries({
        @NamedQuery(name = "TbAcVendormasterEntity.countAll", query = "SELECT COUNT(x) FROM TbAcVendormasterEntity x")
})
public class TbAcVendormasterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VM_VENDORID", nullable = false)
    private Long vmVendorid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
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
    private String pfAcNumber;

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
    
    @Column(name = "PFMS_VENDORID", length = 20)
    private String pfmsVendorId;
    
    @Column(name = "ADD_MOBILE_NO")
    private String addMobileNo;
    
    @Column(name = "VM_VENDORNAME_REG ")
    private String vmVendornameReg;

    // ----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    // ----------------------------------------------------------------------

    @Column(name = "BANKID")
    private Long bankId;
    @Column(name = "MSME_NO")
    private String msmeNo;
    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public TbAcVendormasterEntity() {
        super();
    }

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
    // --- DATABASE MAPPING : VM_VENDORCODE ( NVARCHAR2 )
    public void setVmVendorcode(final String vmVendorcode) {
        this.vmVendorcode = vmVendorcode;
    }

    public String getVmVendorcode() {
        return vmVendorcode;
    }

    // --- DATABASE MAPPING : CPD_VENDORTYPE ( NUMBER )
    public void setCpdVendortype(final Long cpdVendortype) {
        this.cpdVendortype = cpdVendortype;
    }

    public Long getCpdVendortype() {
        return cpdVendortype;
    }

    // --- DATABASE MAPPING : CPD_VENDOR_STATUS ( NUMBER )

    // --- DATABASE MAPPING : VM_VENDORNAME ( NVARCHAR2 )
    public void setVmVendorname(final String vmVendorname) {
        this.vmVendorname = vmVendorname;
    }

    /**
     * @return the cpdVendorSubType
     */
    public Long getCpdVendorSubType() {
        return cpdVendorSubType;
    }

    /**
     * @param cpdVendorSubType the cpdVendorSubType to set
     */
    public void setCpdVendorSubType(final Long cpdVendorSubType) {
        this.cpdVendorSubType = cpdVendorSubType;
    }

    public String getVmVendorname() {
        return vmVendorname;
    }

    // --- DATABASE MAPPING : VM_VENDORADD ( NVARCHAR2 )
    public void setVmVendoradd(final String vmVendoradd) {
        this.vmVendoradd = vmVendoradd;
    }

    public String getVmVendoradd() {
        return vmVendoradd;
    }

    // --- DATABASE MAPPING : EMAIL_ID ( NVARCHAR2 )
    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    // --- DATABASE MAPPING : MOBILE_NO ( NVARCHAR2 )
    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    // --- DATABASE MAPPING : VM_PAN_NUMBER ( NVARCHAR2 )
    public void setVmPanNumber(final String vmPanNumber) {
        this.vmPanNumber = vmPanNumber;
    }

    public String getVmPanNumber() {
        return vmPanNumber;
    }

    // --- DATABASE MAPPING : VM_UID_NO ( NUMBER )
    public void setVmUidNo(final Long vmUidNo) {
        this.vmUidNo = vmUidNo;
    }

    public Long getVmUidNo() {
        return vmUidNo;
    }

    // --- DATABASE MAPPING : TIN_NUMBER ( NVARCHAR2 )
    public void setTinNumber(final String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    // --- DATABASE MAPPING : TAN_NUMBER ( NVARCHAR2 )
   

    // --- DATABASE MAPPING : SERVICETAXNUMBER ( NVARCHAR2 )

    // --- DATABASE MAPPING : BANKACCOUNTNUMBER ( NVARCHAR2 )
    public void setBankaccountnumber(final String bankaccountnumber) {
        this.bankaccountnumber = bankaccountnumber;
    }

    public String getPfAcNumber() {
		return pfAcNumber;
	}

	public void setPfAcNumber(String pfAcNumber) {
		this.pfAcNumber = pfAcNumber;
	}

	public String getBankaccountnumber() {
        return bankaccountnumber;
    }

    // --- DATABASE MAPPING : IFSC_CODE ( NVARCHAR2 )
    public void setIfscCode(final String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    // --- DATABASE MAPPING : VM_CPD_STATUS ( NUMBER )
    public void setVmCpdStatus(final Long vmCpdStatus) {
        this.vmCpdStatus = vmCpdStatus;
    }

    public Long getVmCpdStatus() {
        return vmCpdStatus;
    }

    // --- DATABASE MAPPING : REMARK ( NVARCHAR2 )
    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // --- DATABASE MAPPING : USER_ID ( NUMBER )
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : LG_IP_MAC ( VARCHAR2 )
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    // --- DATABASE MAPPING : LG_IP_MAC_UPD ( VARCHAR2 )
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    // --- DATABASE MAPPING : FI04_N1 ( NUMBER )
    public void setFi04N1(final Long fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    // --- DATABASE MAPPING : FI04_V1 ( NVARCHAR2 )
    public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    // --- DATABASE MAPPING : FI04_D1 ( DATE )
    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    // --- DATABASE MAPPING : FI04_LO1 ( CHAR )
    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
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
     * @return the rtgsvendorflag
     */
    public String getRtgsvendorflag() {
        return rtgsvendorflag;
    }

    /**
     * @param rtgsvendorflag the rtgsvendorflag to set
     */
    public void setRtgsvendorflag(final String rtgsvendorflag) {
        this.rtgsvendorflag = rtgsvendorflag;
    }

	// ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(vmVendorid);
        sb.append("]:");
        sb.append(vmVendorcode);
        sb.append("|");
        sb.append(cpdVendortype);
        sb.append("|");
        sb.append(cpdVendorSubType);
        sb.append("|");
        sb.append(vmVendorname);
        sb.append("|");
        sb.append(vmVendornamePayto);
        sb.append("|");
        sb.append(vmVendoradd);
        sb.append("|");
        sb.append(emailId);
        sb.append("|");
        sb.append(mobileNo);
        sb.append("|");
        sb.append(vmPanNumber);
        sb.append("|");
        sb.append(vmUidNo);
        sb.append("|");
        sb.append(tinNumber);
        sb.append("|");
        sb.append(pfAcNumber);
        sb.append("|");
        sb.append(rtgsvendorflag);
        sb.append("|");
        sb.append(bankaccountnumber);
        sb.append("|");
        sb.append(ifscCode);
        sb.append("|");
        sb.append(vmCpdStatus);
        sb.append("|");
        sb.append(remark);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(fi04N1);
        sb.append("|");
        sb.append(fi04V1);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append(fi04Lo1);
        sb.append("|");
        sb.append(empId);
        sb.append("|");
        sb.append(pacHeadId);
        sb.append("|");
        sb.append(vmAuthoId);
        sb.append("|");
        sb.append(vmAuthoDate);
        sb.append("|");
        sb.append(vmAuthoFlg);
        sb.append("|");
        sb.append(vmVendornameReg);
        return sb.toString();
    }

    public String[] getPkValues() {
        return new String[] { "AC", "TB_VENDORMASTER", "VM_VENDORID" };
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

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(final Long bankId) {
        this.bankId = bankId;
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

	public String getPfmsVendorId() {
		return pfmsVendorId;
	}

	public String getMsmeNo() {
		return msmeNo;
	}

	public void setMsmeNo(String msmeNo) {
		this.msmeNo = msmeNo;
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
 
	public String getVmVendornameReg() {
		return vmVendornameReg;
	}

	public void setVmVendornameReg(String vmVendornameReg) {
		this.vmVendornameReg = vmVendornameReg;
	}
}
