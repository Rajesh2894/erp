package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author sadik.shaikh
 *
 */
@Entity
@Table(name = "tb_res_configmaster")
public class TbResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3702579469811870943L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "res_id", nullable = false)
	private Long resId;

	@Column(name = "page_id", length = 12, nullable = false)
	private String pageId;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tbResource", cascade = CascadeType.ALL)
	private List<TbResourceDtls> fieldDetails;

	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	// comments : User Id
	private Long userId;

	@Column(name = "CREATED_DATE", nullable = false)
	// comments : Creation date
	private Date createdDate;

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

	@Column(name = "ORGID", length = 12, nullable = false)
	private Long orgId;

	@Column(name = "field_type", length = 20, nullable = false)
	private String field_type;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getField_type() {
		return field_type;
	}

	public void setField_type(String field_type) {
		this.field_type = field_type;
	}

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public List<TbResourceDtls> getFieldDetails() {
		return fieldDetails;
	}

	public void setFieldDetails(List<TbResourceDtls> fieldDetails) {
		this.fieldDetails = fieldDetails;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
		return new String[] { "COM", "tb_res_configmaster", "res_id" };
	}
}
