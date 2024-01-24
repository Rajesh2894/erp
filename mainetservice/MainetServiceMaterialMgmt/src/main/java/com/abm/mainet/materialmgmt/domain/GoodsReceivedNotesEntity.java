package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * @author preeti.choubey
 *
 */
@Entity
@Table(name = "mm_grn")
public class GoodsReceivedNotesEntity implements Serializable {

	private static final long serialVersionUID = -7220184218438919206L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "grnid")
	private Long grnid;

	@Column(name = "grnno")
	private String grnno;

	@Column(name = "receiveddate")
	private Date receiveddate;

	@Column(name = "storeid")
	private Long storeid;

	@Column(name = "poid", nullable = true)
	private Long poid;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "ATD_PATH", nullable = true)
	private String atd_Path;

	@Column(name = "ATD_FNAME", nullable = true)
	private String atd_Fname;

	@Column(name = "inspectorname", nullable = true)
	private Long inspectorname;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inspectiondate", nullable = true)
	private Date inspectiondate;

	@Column(name = "Status")
	private String Status;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private Long langId;

	@Temporal(TemporalType.DATE)
	@Column(name = "LMODDATE", nullable = true)
	private Date lmoDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedby;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date Updateddate;

	@Column(name = "LG_IP_MAC", nullable = true)
	private String lgipmac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgipmacupd;

	@Column(name = "WF_Flag", nullable = true)
	private String wfflag;

	@OneToMany(mappedBy = "goodsReceivedNote", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<GoodsreceivedNotesitemEntity> goodsItemDetail = new ArrayList<>();

	@OneToMany(mappedBy = "goodsReceivedNote", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<GrnInspectionItemDetEntity> inspectionDetEntityList = new ArrayList<>();

	public Long getGrnid() {
		return grnid;
	}

	public void setGrnid(Long grnid) {
		this.grnid = grnid;
	}

	public String getGrnno() {
		return grnno;
	}

	public void setGrnno(String grnno) {
		this.grnno = grnno;
	}

	public Date getReceiveddate() {
		return receiveddate;
	}

	public void setReceiveddate(Date receiveddate) {
		this.receiveddate = receiveddate;
	}

	public Long getStoreid() {
		return storeid;
	}

	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}

	public Long getPoid() {
		return poid;
	}

	public void setPoid(Long poid) {
		this.poid = poid;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAtd_Path() {
		return atd_Path;
	}

	public void setAtd_Path(String atd_Path) {
		this.atd_Path = atd_Path;
	}

	public String getAtd_Fname() {
		return atd_Fname;
	}

	public void setAtd_Fname(String atd_Fname) {
		this.atd_Fname = atd_Fname;
	}

	public Long getInspectorname() {
		return inspectorname;
	}

	public void setInspectorname(Long inspectorname) {
		this.inspectorname = inspectorname;
	}

	public Date getInspectiondate() {
		return inspectiondate;
	}

	public void setInspectiondate(Date inspectiondate) {
		this.inspectiondate = inspectiondate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public Date getLmoDate() {
		return lmoDate;
	}

	public void setLmoDate(Date lmoDate) {
		this.lmoDate = lmoDate;
	}

	public Long getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Long updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdateddate() {
		return Updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		Updateddate = updateddate;
	}

	public String getLgipmac() {
		return lgipmac;
	}

	public void setLgipmac(String lgipmac) {
		this.lgipmac = lgipmac;
	}

	public String getLgipmacupd() {
		return lgipmacupd;
	}

	public void setLgipmacupd(String lgipmacupd) {
		this.lgipmacupd = lgipmacupd;
	}

	public String getWfflag() {
		return wfflag;
	}

	public void setWfflag(String wfflag) {
		this.wfflag = wfflag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<GoodsreceivedNotesitemEntity> getGoodsItemDetail() {
		return goodsItemDetail;
	}

	public void setGoodsItemDetail(List<GoodsreceivedNotesitemEntity> goodsItemDetail) {
		this.goodsItemDetail = goodsItemDetail;
	}

	public List<GrnInspectionItemDetEntity> getInspectionDetEntityList() {
		return inspectionDetEntityList;
	}

	public void setInspectionDetEntityList(List<GrnInspectionItemDetEntity> inspectionDetEntityList) {
		this.inspectionDetEntityList = inspectionDetEntityList;
	}

	public String[] getPkValues() {
		return new String[] { "ITM", "mm_grn", "grnid" };
	}
}
