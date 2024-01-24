package com.abm.mainet.cms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Bhavesh.Gadhe
 */
@Entity
@Table(name = "TB_EIP_CONTACT_US")
public class EIPContactUs extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6253713505901306673L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CONTACT_ID", nullable = false)
    private long contactUsId;

    @Column(name = "ADDRESS1_EN", nullable = true, length = 500)
    private String address1En;

    @Column(name = "ADDRESS1_REG", nullable = true, length = 1000)
    private String address1Reg;

    @Column(name = "ADDRESS2_EN", nullable = true, length = 500)
    private String address2En;

    @Column(name = "ADDRESS2_REG", nullable = true, length = 1000)
    private String address2Reg;

    @Column(name = "CITY_EN", nullable = true, length = 200)
    private String cityEn;

    @Column(name = "CITY_REG", nullable = true, length = 400)
    private String cityReg;

    @Column(name = "CNAME_EN", nullable = true, length = 100)
    private String contactNameEn;										// CNAME_EN

    @Column(name = "CNAME_REG", nullable = true, length = 200)
    private String contactNameReg;										// CNAME_REG

    @Column(name = "COUNTRY_EN", nullable = true, length = 100)
    private String countryEn;                                              // COUNTRY_EN

    @Column(name = "COUNTRY_REG", nullable = true, length = 200)
    private String countryReg;
    @Column(name = "DEPT_EN", nullable = true, length = 100)
    private String departmentEn;                                      // CNAME_EN

    @Column(name = "DEPT_REG", nullable = true, length = 200)
    private String departmentReg;

    @Column(name = "DESIGNATION_EN", nullable = true, length = 100)
    private String designationEn;										// DESIGNATION_EN

    @Column(name = "DESIGNATION_REG", nullable = true, length = 200)
    private String designationReg;										// DESIGNATION_REG

    @Column(name = "EMAIL1", nullable = true, length = 100)
    private String email1;

    @Column(name = "EMAIL2", nullable = true, length = 100)
    private String email2;                                                 // EMAIL2

    @Column(name = "FAXNO1_EN", nullable = true, length = 15)
    private String faxNo1En;                                               // FAXNO1_EN

    @Column(name = "FAXNO1_REG", nullable = true, length = 30)
    private String faxNo1Reg;                                              // FAXNO1_REG

    @Column(name = "FAXNO2_EN", nullable = true, length = 15)
    private String faxNo2En;                                               // FAXNO2_EN

    @Column(name = "FAXNO2_REG", nullable = true, length = 30)
    private String faxNo2Reg;                                              // FAXNO2_REG

    @Column(name = "PINCODE_EN", nullable = true, length = 10)
    private String pincodeEn;                                              // PINCODE_EN

    @Column(name = "PINCODE_MR", nullable = true, length = 20)
    private String pincodeReg;                                         // PINCODE_REG

    @Column(name = "STATE_EN", nullable = true, length = 100)
    private String stateEn;                                                // STATE_EN

    @Column(name = "STATE_REG", nullable = true, length = 200)
    private String stateReg;                                               // STATE_REG
    // EMAIL1

    @Column(name = "TELNO1_EN", nullable = true, length = 15)
    private String telephoneNo1En;

    @Column(name = "TELNO2_EN", nullable = true, length = 15)
    private String telephoneNo2En;                                     // TELNO2_EN

    @Column(name = "TELNO3_EN", nullable = true, length = 15)
    private String telephoneNo3En;                                     // TELNO3_EN

    @Column(name = "TELNO1_REG", nullable = true, length = 30)
    private String telephoneNo1Reg;                                        // TELNO1_REG

    @Column(name = "TELNO2_REG", nullable = true, length = 30)
    private String telephoneNo2Reg;                                        // TELNO2_REG

    @Column(name = "TELNO3_REG", nullable = true, length = 30)
    private String telephoneNo3Reg;                                        // TELNO3_REG

    @Column(name = "REMARKS_EN", nullable = true, length = 100)
    private String remarksEn;                                              // REMARKS_EN

    @Column(name = "REMARKS_REG", nullable = true, length = 100)
    private String remarksReg;             // TELNO1_EN

    @Column(name = "MUNCIPALITY_NAME", length = 100, nullable = true)
    private String muncipalityName;

    @Column(name = "SEQUENCE_NO", precision = 3, scale = 0, nullable = true)
    private Double sequenceNo;

    @Column(name = "MUNCIPALITY_NAME_REG", length = 100, nullable = true)
    private String muncipalityNameReg;

    @Column(name = "FLAG", nullable = true, length = 15)
    private String flag;

    @Column(name = "ISDELETED", nullable = false, length = 1)
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID", nullable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USERID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "UPDATED_BY", nullable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true, length = 100)
    private String lgIpMacUpd;

    @Column(name = "CHEKER_FLAG", length = 1, nullable = false)
    private String chekkerflag;
    
    @Column(name = "REMARK", length = 1000, nullable = false)
    private String remark;

    public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    @Override
    public long getId() {
        return contactUsId;
    }

    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    @Override
    public Employee getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public int getLangId() {
        return 0;
    }

    @Override
    public void setLangId(final int langId) {

    }

    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("EIPContactUs [contactUsId=");
        builder.append(contactUsId);
        builder.append(", address1En=");
        builder.append(", address1Reg=");
        builder.append(", address2En=");
        builder.append(", address2Reg=");
        builder.append(", cityEn=");
        builder.append(", cityReg=");
        builder.append(", contactNameEn=");
        builder.append(contactNameEn);
        builder.append(", contactNameReg=");
        builder.append(contactNameReg);
        builder.append(", countryEn=");
        builder.append(", countryReg=");
        builder.append(", designationEn=");
        builder.append(designationEn);
        builder.append(", designationReg=");
        builder.append(designationReg);
        builder.append(", email1=");
        builder.append(email1);
        builder.append(", email2=");
        builder.append(", faxNo1En=");
        builder.append(", faxNo1Reg=");
        builder.append(", faxNo2En=");
        builder.append(", faxNo2Reg=");
        builder.append(", pincodeEn=");
        builder.append(", pincodeReg=");
        builder.append(", stateEn=");
        builder.append(", stateReg=");
        builder.append(", telephoneNo1En=");
        builder.append(telephoneNo1En);
        builder.append(", telephoneNo2En=");
        builder.append(", telephoneNo3En=");
        builder.append(", telephoneNo1Reg=");
        builder.append(", telephoneNo2Reg=");
        builder.append(", telephoneNo3Reg=");
        builder.append(", isDeleted=");
        builder.append(isDeleted);
        builder.append(", lmodDate=");
        builder.append(lmodDate);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", lgIpMac=");
        builder.append(lgIpMac);
        builder.append(", lgIpMacUpd=");
        builder.append(lgIpMacUpd);
        builder.append("]");
        return builder.toString();
    }

    /**
     * @return the contactUsId
     */
    public long getContactUsId() {
        return contactUsId;
    }

    /**
     * @param contactUsId the contactUsId to set
     */
    public void setContactUsId(final long contactUsId) {
        this.contactUsId = contactUsId;
    }

    /**
     * @return the contactNameEn
     */
    public String getContactNameEn() {
        return contactNameEn;
    }

    /**
     * @param contactNameEn the contactNameEn to set
     */
    public void setContactNameEn(final String contactNameEn) {
        this.contactNameEn = contactNameEn;
    }

    /**
     * @return the contactNameReg
     */
    public String getContactNameReg() {
        return contactNameReg;
    }

    /**
     * @param contactNameReg the contactNameReg to set
     */
    public void setContactNameReg(final String contactNameReg) {
        this.contactNameReg = contactNameReg;
    }

    /**
     * @return the designationEn
     */
    public String getDesignationEn() {
        return designationEn;
    }

    /**
     * @param designationEn the designationEn to set
     */
    public void setDesignationEn(final String designationEn) {
        this.designationEn = designationEn;
    }

    /**
     * @return the designationReg
     */
    public String getDesignationReg() {
        return designationReg;
    }

    /**
     * @param designationReg the designationReg to set
     */
    public void setDesignationReg(final String designationReg) {
        this.designationReg = designationReg;
    }

    /**
     * @return the email1
     */
    public String getEmail1() {
        return email1;
    }

    /**
     * @param email1 the email1 to set
     */
    public void setEmail1(final String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(final String email2) {
        this.email2 = email2;
    }

    public String getFaxNo1En() {
        return faxNo1En;
    }

    public void setFaxNo1En(final String faxNo1En) {
        this.faxNo1En = faxNo1En;
    }

    public String getFaxNo1Reg() {
        return faxNo1Reg;
    }

    public void setFaxNo1Reg(final String faxNo1Reg) {
        this.faxNo1Reg = faxNo1Reg;
    }

    public String getFaxNo2En() {
        return faxNo2En;
    }

    public void setFaxNo2En(final String faxNo2En) {
        this.faxNo2En = faxNo2En;
    }

    public String getFaxNo2Reg() {
        return faxNo2Reg;
    }

    public void setFaxNo2Reg(final String faxNo2Reg) {
        this.faxNo2Reg = faxNo2Reg;
    }

    public String getPincodeEn() {
        return pincodeEn;
    }

    public void setPincodeEn(final String pincodeEn) {
        this.pincodeEn = pincodeEn;
    }

    public String getPincodeReg() {
        return pincodeReg;
    }

    public void setPincodeReg(final String pincodeReg) {
        this.pincodeReg = pincodeReg;
    }

    public String getStateEn() {
        return stateEn;
    }

    public void setStateEn(final String stateEn) {
        this.stateEn = stateEn;
    }

    public String getStateReg() {
        return stateReg;
    }

    public void setStateReg(final String stateReg) {
        this.stateReg = stateReg;
    }

    /**
     * @return the telephoneNo1En
     */
    public String getTelephoneNo1En() {
        return telephoneNo1En;
    }

    /**
     * @param telephoneNo1En the telephoneNo1En to set
     */
    public void setTelephoneNo1En(final String telephoneNo1En) {
        this.telephoneNo1En = telephoneNo1En;
    }

    public String getDepartmentEn() {
        return departmentEn;
    }

    public void setDepartmentEn(final String departmentEn) {
        this.departmentEn = departmentEn;
    }

    public String getDepartmentReg() {
        return departmentReg;
    }

    public void setDepartmentReg(final String departmentReg) {
        this.departmentReg = departmentReg;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(final String flag) {
        this.flag = flag;
    }

    /**
     * @return the telephoneNo2En
     */
    public String getTelephoneNo2En() {
        return telephoneNo2En;
    }

    public void setTelephoneNo2En(final String telephoneNo2En) {
        this.telephoneNo2En = telephoneNo2En;
    }

    public String getTelephoneNo3En() {
        return telephoneNo3En;
    }

    public void setTelephoneNo3En(final String telephoneNo3En) {
        this.telephoneNo3En = telephoneNo3En;
    }

    public String getTelephoneNo1Reg() {
        return telephoneNo1Reg;
    }

    public void setTelephoneNo1Reg(final String telephoneNo1Reg) {
        this.telephoneNo1Reg = telephoneNo1Reg;
    }

    public String getTelephoneNo2Reg() {
        return telephoneNo2Reg;
    }

    public void setTelephoneNo2Reg(final String telephoneNo2Reg) {
        this.telephoneNo2Reg = telephoneNo2Reg;
    }

    public String getTelephoneNo3Reg() {
        return telephoneNo3Reg;
    }

    public void setTelephoneNo3Reg(final String telephoneNo3Reg) {
        this.telephoneNo3Reg = telephoneNo3Reg;
    }

    public String getRemarksEn() {
        return remarksEn;
    }

    public void setRemarksEn(final String remarksEn) {
        this.remarksEn = remarksEn;
    }

    public String getAddress1En() {
        return address1En;
    }

    public void setAddress1En(final String address1En) {
        this.address1En = address1En;
    }

    public String getAddress1Reg() {
        return address1Reg;
    }

    public void setAddress1Reg(final String address1Reg) {
        this.address1Reg = address1Reg;
    }

    public String getAddress2En() {
        return address2En;
    }

    public void setAddress2En(final String address2En) {
        this.address2En = address2En;
    }

    public String getAddress2Reg() {
        return address2Reg;
    }

    public void setAddress2Reg(final String address2Reg) {
        this.address2Reg = address2Reg;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(final String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCityReg() {
        return cityReg;
    }

    public void setCityReg(final String cityReg) {
        this.cityReg = cityReg;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(final String countryEn) {
        this.countryEn = countryEn;
    }

    public String getCountryReg() {
        return countryReg;
    }

    public void setCountryReg(final String countryReg) {
        this.countryReg = countryReg;
    }

    public String getRemarksReg() {
        return remarksReg;
    }

    public void setRemarksReg(final String remarksReg) {
        this.remarksReg = remarksReg;
    }

    public String getMuncipalityName() {
        return muncipalityName;
    }

    public void setMuncipalityName(final String muncipalityName) {
        this.muncipalityName = muncipalityName;
    }

    public Double getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(final Double sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getMuncipalityNameReg() {
        return muncipalityNameReg;
    }

    public void setMuncipalityNameReg(final String muncipalityNameReg) {
        this.muncipalityNameReg = muncipalityNameReg;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_CONTACT_US", "CONTACT_ID" };
    }
  
    @Transient
    private String chekkerflag1;

	public String getChekkerflag1() {
		return chekkerflag1;
	}

	public void setChekkerflag1(String chekkerflag1) {
		this.chekkerflag1 = chekkerflag1;
	}
	
	 /**
     * @return the remark
     */
	public String getRemark() {
		return remark;
	}

	 /**
     * @param remark the remark to set
     */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
