package com.abm.mainet.common.domain;

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

/**
 * @author sadik.shaikh
 *
 */
@Entity
@Table(name = "tb_res_configdet")
public class TbResourceDtls implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4962336530657914594L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "res_det_id", nullable = false)
	private Long resDetId;

	@Column(name = "field_id", length = 12, nullable = false)
	private String fieldId;

	@ManyToOne
	@JoinColumn(name = "res_id", referencedColumnName = "res_id")
	private TbResource tbResource;

	@Column(name = "ORGID", length = 12, nullable = false)
	private Long orgId;

	@Column(name = "is_mandatory", length = 1, nullable = true)
	private String isMandatory;

	@Column(name = "is_visible", length = 1, nullable = true)
	private String isVisible;

	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	// comments : User Id
	private Long userId;

	@Column(name = "CREATED_DATE", nullable = false)
	// comments : Creation date
	private Date lmodDate;

	@Column(name = "UPDATED_BY", nullable = true, updatable = true)
	// comments : Updated by
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	// comments : Updated date
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", precision = 100, nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", precision = 100, nullable = false)
	private String lgIpMacUpd;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getResDetId() {
		return resDetId;
	}

	public void setResDetId(Long resDetId) {
		this.resDetId = resDetId;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public TbResource getTbResource() {
		return tbResource;
	}

	public void setTbResource(TbResource tbResource) {
		this.tbResource = tbResource;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
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

	public static String[] getPkValues() {
		return new String[] { "COM", "tb_res_configdet", "res_det_id" };
	}
}
