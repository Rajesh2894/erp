package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class AdditionalOwnerInfoDTO implements Serializable {

	private static final long serialVersionUID = 7004069998515128768L;
	private String ownerTitle;
	private String ownerFirstName;
	private String ownerMiddleName;
	private String ownerLastName;
	private Long cao_id;
	private TbCsmrInfoDTO csIdn;
	private String cao_address;
	private String cao_contactno;
	private Long orgid;
	private Long userId;
	private Long langId;
	private Date lmoddate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long gender;
	private Long caoUID;

	// additional owner detail for change of ownership
	private Long caoNewTitle;
	private String caoNewFName;
	private String caoNewMName;
	private String caoNewLName;
	private Long caoNewGender;
	private Long caoNewUID;
	private String isDeleted;

	public String getOwnerTitle() {
		return ownerTitle;
	}

	public void setOwnerTitle(final String ownerTitle) {
		this.ownerTitle = ownerTitle;
	}

	public String getOwnerFirstName() {
		return ownerFirstName;
	}

	public void setOwnerFirstName(final String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}

	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}

	public void setOwnerMiddleName(final String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}

	public String getOwnerLastName() {
		return ownerLastName;
	}

	public void setOwnerLastName(final String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}

	public Long getCao_id() {
		return cao_id;
	}

	public void setCao_id(final Long cao_id) {
		this.cao_id = cao_id;
	}

	public String getCao_address() {
		return cao_address;
	}

	public void setCao_address(final String cao_address) {
		this.cao_address = cao_address;
	}

	public String getCao_contactno() {
		return cao_contactno;
	}

	public void setCao_contactno(final String cao_contactno) {
		this.cao_contactno = cao_contactno;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(final Long orgid) {
		this.orgid = orgid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(final Long langId) {
		this.langId = langId;
	}

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(final Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(final Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(final Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(final String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(final Long gender) {
		this.gender = gender;
	}

	public void setLgIpMacUpd(final String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public TbCsmrInfoDTO getCsIdn() {
		return csIdn;
	}

	public void setCsIdn(final TbCsmrInfoDTO csIdn) {
		this.csIdn = csIdn;
	}

	public Long getCaoUID() {
		return caoUID;
	}

	public void setCaoUID(final Long caoUID) {
		this.caoUID = caoUID;
	}

	public Long getCaoNewTitle() {
		return caoNewTitle;
	}

	public void setCaoNewTitle(final Long caoNewTitle) {
		this.caoNewTitle = caoNewTitle;
	}

	public String getCaoNewFName() {
		return caoNewFName;
	}

	public void setCaoNewFName(final String caoNewFName) {
		this.caoNewFName = caoNewFName;
	}

	public String getCaoNewMName() {
		return caoNewMName;
	}

	public void setCaoNewMName(final String caoNewMName) {
		this.caoNewMName = caoNewMName;
	}

	public String getCaoNewLName() {
		return caoNewLName;
	}

	public void setCaoNewLName(final String caoNewLName) {
		this.caoNewLName = caoNewLName;
	}

	public Long getCaoNewGender() {
		return caoNewGender;
	}

	public void setCaoNewGender(final Long caoNewGender) {
		this.caoNewGender = caoNewGender;
	}

	public Long getCaoNewUID() {
		return caoNewUID;
	}

	public void setCaoNewUID(final Long caoNewUID) {
		this.caoNewUID = caoNewUID;
	}

	/**
	 * @return the isDeleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(final String isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AdditionalOwnerInfoDTO other = (AdditionalOwnerInfoDTO) obj;
		if (cao_id == null) {
			if (other.cao_id != null) {
				return false;
			}
		} else if (!cao_id.equals(other.cao_id)) {
			return false;
		}
		return true;
	}

}
