package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**
 * Persistent class for entity stored in table "TB_TAX_MAS"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_TAX_MAS")
// Define named queries here
@NamedQueries({ @NamedQuery(name = "TbTaxMasEntity.countAll", query = "SELECT COUNT(x) FROM TbTaxMasEntity x") })
public class TbTaxMasEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------------------------
	// ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
	// ----------------------------------------------------------------------
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "TAX_ID", nullable = false)
	private Long taxId;

	// ----------------------------------------------------------------------
	// ENTITY DATA FIELDS
	// ----------------------------------------------------------------------
	@Column(name = "TAX_DESC", nullable = false)
	private String taxDesc;

	@Column(name = "TAX_METHOD", nullable = false)
	private String taxMethod;

	@Column(name = "TAX_VALUE_TYPE", nullable = false)
	private String taxValueType;

	@Column(name = "PARENT_CODE")
	private Long parentCode;

	@Column(name = "TAX_GROUP")
	private String taxGroup;

	@Column(name = "TAX_PRINT_ON1")
	private String taxPrintOn1;

	@Column(name = "TAX_CODE")
	private String taxCode;

	@Column(name = "TAX_DISPLAY_SEQ")
	private Long taxDisplaySeq;

	@Column(name = "COLL_MTD")
	private Long collMtd;

	@Column(name = "COLL_SEQ")
	private Long collSeq;

	@Column(name = "ORGID")
	private Long orgid;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "TAX_CATEGORY1")
	private Long taxCategory1;

	@Column(name = "TAX_CATEGORY2")
	private Long taxCategory2;

	@Column(name = "TAX_CATEGORY3")
	private Long taxCategory3;

	@Column(name = "TAX_CATEGORY4")
	private Long taxCategory4;

	@Column(name = "TAX_CATEGORY5")
	private Long taxCategory5;

	@Column(name = "TAX_APPLICABLE")
	private Long taxApplicable;

	@Column(name = "SM_SERVICE_ID")
	private Long smServiceId;

	@Column(name = "TAX_PRINT_ON2")
	private String taxPrintOn2;

	@Column(name = "TAX_PRINT_ON3")
	private String taxPrintOn3;

	@Column(name = "TAX_DESC_ID", nullable = false)
	private Long taxDescId;

	@Column(name = "Tax_Active", nullable = true)
	private String taxActive;

	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	// ENTITY LINKS ( RELATIONSHIP )
	// ----------------------------------------------------------------------
	@OneToMany(mappedBy = "tbTaxMas", targetEntity = TbTaxDetMasEntity.class, cascade = CascadeType.ALL)
	@Where(clause = "STATUS='A'")
	private List<TbTaxDetMasEntity> listOfTbTaxDetMas;

	@ManyToOne
	@JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
	private Department department;

	/*
	 * @OneToMany(mappedBy = "", targetEntity = TbTaxAcMappingEntity.class) private
	 * List<TbTaxAcMappingEntity> listOfTbTaxBudgetCodeMas;
	 */

	// ----------------------------------------------------------------------
	// CONSTRUCTOR(S)
	// ----------------------------------------------------------------------
	public TbTaxMasEntity() {
		super();
	}

	public Long getTaxCategory1() {
		return taxCategory1;
	}

	public void setTaxCategory1(final Long taxCategory1) {
		this.taxCategory1 = taxCategory1;
	}

	public Long getTaxCategory2() {
		return taxCategory2;
	}

	public void setTaxCategory2(final Long taxCategory2) {
		this.taxCategory2 = taxCategory2;
	}

	public Long getTaxCategory3() {
		return taxCategory3;
	}

	public void setTaxCategory3(final Long taxCategory3) {
		this.taxCategory3 = taxCategory3;
	}

	public Long getTaxCategory4() {
		return taxCategory4;
	}

	public void setTaxCategory4(final Long taxCategory4) {
		this.taxCategory4 = taxCategory4;
	}

	public Long getTaxCategory5() {
		return taxCategory5;
	}

	public void setTaxCategory5(final Long taxCategory5) {
		this.taxCategory5 = taxCategory5;
	}

	// ----------------------------------------------------------------------
	// GETTER & SETTER FOR THE KEY FIELD
	// ----------------------------------------------------------------------
	public void setTaxId(final Long taxId) {
		this.taxId = taxId;
	}

	public Long getTaxId() {
		return taxId;
	}

	// ----------------------------------------------------------------------
	// GETTERS & SETTERS FOR FIELDS
	// ----------------------------------------------------------------------
	// --- DATABASE MAPPING : TAX_DESC ( NVARCHAR2 )
	public void setTaxDesc(final String taxDesc) {
		this.taxDesc = taxDesc;
	}

	public String getTaxDesc() {
		return taxDesc;
	}

	// --- DATABASE MAPPING : TAX_METHOD ( NVARCHAR2 )
	public void setTaxMethod(final String taxMethod) {
		this.taxMethod = taxMethod;
	}

	public String getTaxMethod() {
		return taxMethod;
	}

	// --- DATABASE MAPPING : TAX_VALUE_TYPE ( NVARCHAR2 )
	public void setTaxValueType(final String taxValueType) {
		this.taxValueType = taxValueType;
	}

	public String getTaxValueType() {
		return taxValueType;
	}

	// --- DATABASE MAPPING : PARENT_CODE ( NUMBER )
	public void setParentCode(final Long parentCode) {
		this.parentCode = parentCode;
	}

	public Long getParentCode() {
		return parentCode;
	}

	// --- DATABASE MAPPING : TAX_GROUP ( NVARCHAR2 )
	public void setTaxGroup(final String taxGroup) {
		this.taxGroup = taxGroup;
	}

	public String getTaxGroup() {
		return taxGroup;
	}

	// --- DATABASE MAPPING : ORGID ( NUMBER )
	public void setOrgid(final Long orgid) {
		this.orgid = orgid;
	}

	public Long getOrgid() {
		return orgid;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	/*
	 * public List<TbTaxAcMappingEntity> getListOfTbTaxBudgetCodeMas() { return
	 * listOfTbTaxBudgetCodeMas; }
	 * 
	 * public void setListOfTbTaxBudgetCodeMas(final List<TbTaxAcMappingEntity>
	 * listOfTbTaxBudgetCodeMas) { this.listOfTbTaxBudgetCodeMas =
	 * listOfTbTaxBudgetCodeMas; }
	 */

	// ----------------------------------------------------------------------
	// GETTERS & SETTERS FOR LINKS
	// ----------------------------------------------------------------------
	public void setListOfTbTaxDetMas(final List<TbTaxDetMasEntity> listOfTbTaxDetMas) {
		this.listOfTbTaxDetMas = listOfTbTaxDetMas;
	}

	public List<TbTaxDetMasEntity> getListOfTbTaxDetMas() {
		return listOfTbTaxDetMas;
	}

	/**
	 * @return the taxCode
	 */
	public String getTaxCode() {
		return taxCode;
	}

	/**
	 * @param taxCode
	 *            the taxCode to set
	 */
	public void setTaxCode(final String taxCode) {
		this.taxCode = taxCode;
	}

	/**
	 * @return the taxDisplaySeq
	 */
	public Long getTaxDisplaySeq() {
		return taxDisplaySeq;
	}

	/**
	 * @param taxDisplaySeq
	 *            the taxDisplaySeq to set
	 */
	public void setTaxDisplaySeq(final Long taxDisplaySeq) {
		this.taxDisplaySeq = taxDisplaySeq;
	}

	/**
	 * @return the collMtd
	 */
	public Long getCollMtd() {
		return collMtd;
	}

	/**
	 * @param collMtd
	 *            the collMtd to set
	 */
	public void setCollMtd(final Long collMtd) {
		this.collMtd = collMtd;
	}

	/**
	 * @return the collSeq
	 */
	public Long getCollSeq() {
		return collSeq;
	}

	/**
	 * @param collSeq
	 *            the collSeq to set
	 */
	public void setCollSeq(final Long collSeq) {
		this.collSeq = collSeq;
	}

	/**
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(final Department department) {
		this.department = department;
	}

	public Long getTaxApplicable() {
		return taxApplicable;
	}

	public void setTaxApplicable(final Long taxApplicable) {
		this.taxApplicable = taxApplicable;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(final Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public String getTaxPrintOn1() {
		return taxPrintOn1;
	}

	public void setTaxPrintOn1(final String taxPrintOn1) {
		this.taxPrintOn1 = taxPrintOn1;
	}

	public String getTaxPrintOn2() {
		return taxPrintOn2;
	}

	public void setTaxPrintOn2(final String taxPrintOn2) {
		this.taxPrintOn2 = taxPrintOn2;
	}

	public String getTaxPrintOn3() {
		return taxPrintOn3;
	}

	public void setTaxPrintOn3(final String taxPrintOn3) {
		this.taxPrintOn3 = taxPrintOn3;
	}

	public Long getTaxDescId() {
		return taxDescId;
	}

	public void setTaxDescId(final Long taxDescId) {
		this.taxDescId = taxDescId;
	}

	// ----------------------------------------------------------------------
	// toString METHOD
	// ----------------------------------------------------------------------
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(taxId);
		sb.append("]:");
		sb.append(taxDesc);
		sb.append("|");
		sb.append(taxMethod);
		sb.append("|");
		sb.append(taxValueType);
		sb.append("|");
		sb.append(parentCode);
		sb.append("|");
		sb.append(taxGroup);
		sb.append("|");
		sb.append(taxPrintOn1);
		sb.append("|");
		sb.append(taxPrintOn2);
		sb.append("|");
		sb.append(taxPrintOn3);
		sb.append("|");
		sb.append(orgid);
		sb.append("|");
		sb.append(updatedBy);
		sb.append("|");
		sb.append(updatedDate);
		sb.append("|");
		sb.append(taxDescId);

		return sb.toString();
	}

	public String getTaxActive() {
		return taxActive;
	}

	public void setTaxActive(String taxActive) {
		this.taxActive = taxActive;
	}

	public String[] getPkValues() {
		return new String[] { "COM", "TB_TAX_MAS", "TAX_ID" };
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
}
