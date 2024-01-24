package com.abm.mainet.intranet.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_intranet")
public class IntranetMaster implements Serializable {

	private static final long serialVersionUID = 4904163124181302322L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "IN_ID", nullable = false, precision = 10)
	private Long inId;
	
	@Column(name = "IN_DOC_NAME", length = 400)
	private String docName;

	@Column(name = "IN_DOC_DESC", length = 400)
	private String docDesc;

	@Column(name = "IN_DEPT_ID", precision = 10)
	private Long deptId;
	
	@Column(name = "IN_DOC_CAT_TYPE", precision = 10)
	private Long docCateType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "IN_DOC_FROM_DATE")
	private Date docFromDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "IN_DOC_TO_DATE")
	private Date docToDate;
		
	@Column(name = "IN_DOC_CAT_ORDER", precision = 10)
	private Long docCatOrder;
	
	@Column(name = "IN_DOC_ORDER_NO", precision = 10)
	private Long docOrderNo;
	
	@Column(name = "IN_STATUS", length = 1)
	private String docStatus;

	@Column(name = "LANG_ID", nullable = false, precision = 10)
	private Long langId;

	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lmoddate;

	@Column(nullable = false, precision = 10)
	private Long orgid;

	@Column(name = "USER_ID", nullable = false, precision = 10)
	private Long userId;
	
	@Column(name="UPDATED_BY")
	private Long updatedBy;

	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
    @Column(name = "ATD_PATH", length = 255)
    private String attPath;
    
    @Column(name = "ATD_FNAME", length = 1000)
    private String attFname;
    
    @Transient
	private String DeptDesc;
    
    @Transient
	private String docCatDesc;
	
	public Long getInId() {
		return inId;
	}

	public void setInId(Long inId) {
		this.inId = inId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDocCateType() {
		return docCateType;
	}

	public void setDocCateType(Long docCateType) {
		this.docCateType = docCateType;
	}

	public Date getDocFromDate() {
		return docFromDate;
	}

	public void setDocFromDate(Date docFromDate) {
		this.docFromDate = docFromDate;
	}

	public Date getDocToDate() {
		return docToDate;
	}

	public void setDocToDate(Date docToDate) {
		this.docToDate = docToDate;
	}

	public Long getDocCatOrder() {
		return docCatOrder;
	}

	public void setDocCatOrder(Long docCatOrder) {
		this.docCatOrder = docCatOrder;
	}

	public Long getDocOrderNo() {
		return docOrderNo;
	}

	public void setDocOrderNo(Long docOrderNo) {
		this.docOrderNo = docOrderNo;
	}

	public String getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
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

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getAttPath() {
		return attPath;
	}

	public void setAttPath(String attPath) {
		this.attPath = attPath;
	}
	
	public String getAttFname() {
		return attFname;
	}

	public void setAttFname(String attFname) {
		this.attFname = attFname;
	}
	
	public String getDeptDesc() {
		return DeptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		DeptDesc = deptDesc;
	}

	public String getDocCatDesc() {
		return docCatDesc;
	}

	public void setDocCatDesc(String docCatDesc) {
		this.docCatDesc = docCatDesc;
	}

	public static String[] getPkValues() {
		return new String[] { "HD", "tb_intranet", "IN_ID" };
	}
	
	
}