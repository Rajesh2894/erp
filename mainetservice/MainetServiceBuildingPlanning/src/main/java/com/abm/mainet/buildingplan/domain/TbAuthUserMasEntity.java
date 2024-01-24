package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BPMS_AUTH_USER_MSTR")
public class TbAuthUserMasEntity implements Serializable {

	private static final long serialVersionUID = 4696981702552111212L;

	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ID", nullable = false)
    private Long authUserId;
	
	@ManyToOne
	@JoinColumn(name = "TCP_DEV_REG_NO")
	private TbDeveloperRegistrationEntity developerRegMas;

    @Column(name = "FULL_NAME", length = 45, nullable = true)
    private String authUserName;

    @Column(name = "MOBILE_NO", length = 10, nullable = true)
    private Long authMobileNo;

    @Column(name = "EMAIL", length = 45, nullable = true)
    private String authEmail;

    @Column(name = "GENDER", length = 1, nullable = true)
    private Long authGender;
    
    @Column(name = "DOB ", nullable = true)
    private Date authDOB;

    @Column(name = "PAN_NO", length = 10, nullable = true)
    private String authPanNumber;

    @Column(name = "BR_NAME", length = 45, nullable = true)
    private String br_name;

    @Column(name = "DS_NAME", length = 45, nullable = true)
    private String ds_name;

    @Column(name = "IS_VERIFIED", length = 1, nullable = true)
    private String panVerifiedFlag;

    @Column(name = "IS_ACTIVE", length = 1, nullable = true)
    private String isActive;

    @Column(name = "CREATED_BY", length = 45, nullable = true)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "MODIFIED_BY", length = 45, nullable = true)
    private Long updatedBy;

    @Column(name = "MODIFIED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "IS_DELETED", length = 1, nullable = true)
    private String isDeleted;


	public String getBr_name() {
		return br_name;
	}

	public void setBr_name(String br_name) {
		this.br_name = br_name;
	}

	public String getDs_name() {
		return ds_name;
	}

	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}

	
	public String[] getPkValues() {
        return new String[] { "DRN", "TB_BPMS_AUTH_USER_MSTR", "ID" };
    }


	public String getAuthUserName() {
		return authUserName;
	}

	public void setAuthUserName(String authUserName) {
		this.authUserName = authUserName;
	}

	public Long getAuthMobileNo() {
		return authMobileNo;
	}

	public void setAuthMobileNo(Long authMobileNo) {
		this.authMobileNo = authMobileNo;
	}

	public String getAuthEmail() {
		return authEmail;
	}

	public void setAuthEmail(String authEmail) {
		this.authEmail = authEmail;
	}

	public Long getAuthGender() {
		return authGender;
	}

	public void setAuthGender(Long authGender) {
		this.authGender = authGender;
	}

	public Date getAuthDOB() {
		return authDOB;
	}

	public void setAuthDOB(Date authDOB) {
		this.authDOB = authDOB;
	}

	public String getAuthPanNumber() {
		return authPanNumber;
	}

	public void setAuthPanNumber(String authPanNumber) {
		this.authPanNumber = authPanNumber;
	}

	public TbDeveloperRegistrationEntity getDeveloperRegMas() {
		return developerRegMas;
	}

	public void setDeveloperRegMas(TbDeveloperRegistrationEntity developerRegMas) {
		this.developerRegMas = developerRegMas;
	}

	public Long getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(Long authUserId) {
		this.authUserId = authUserId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getPanVerifiedFlag() {
		return panVerifiedFlag;
	}

	public void setPanVerifiedFlag(String panVerifiedFlag) {
		this.panVerifiedFlag = panVerifiedFlag;
	}
	
}