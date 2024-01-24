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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "mm_grn_items")
public class GoodsreceivedNotesitemEntity implements Serializable {

	private static final long serialVersionUID = -7220184218438919206L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "grnitemid")
	private Long grnitemid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grnid")
	private GoodsReceivedNotesEntity goodsReceivedNote;

	@Column(name = "storeid")
	private Long storeid;

	@Column(name = "uom", nullable = true)
	private Long uom;

	@Column(name = "management", nullable = true)
	private String management;

	@Column(name = "orederqty")
	private Double orederqty;

	@Column(name = "prevrecqty")
	private Double prevrecqt;

	@Column(name = "receivedqty")
	private Double receivedqty;

	@Column(name = "acceptqty", nullable = true)
	private Double acceptqty;

	@Column(name = "rejectqty", nullable = true)
	private Double rejectqty;

	@Column(name = "inspectorremarks")
	private String inspectorremarks;

	@Column(name = "storeremarks")
	private String storeremarks;

	@Column(name = "Status")
	private String status;

	@ManyToOne
	@JoinColumn(name = "ITEMID", nullable = false)
	private ItemMasterEntity itemMasterEntity;

	@OneToMany(mappedBy = "itemEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Where(clause = "Status!='N'")
	private List<GrnInspectionItemDetEntity> grnItemEntities = new ArrayList<>();

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LANGID")
	private Long langID;

	@Column(name = "LMODDATE")
	private Date lmodDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	public Long getGrnitemid() {
		return grnitemid;
	}

	public void setGrnitemid(Long grnitemid) {
		this.grnitemid = grnitemid;
	}

	public GoodsReceivedNotesEntity getGoodsReceivedNote() {
		return goodsReceivedNote;
	}

	public void setGoodsReceivedNote(GoodsReceivedNotesEntity goodsReceivedNote) {
		this.goodsReceivedNote = goodsReceivedNote;
	}

	public Long getStoreid() {
		return storeid;
	}

	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}

	public Long getUom() {
		return uom;
	}

	public void setUom(Long uom) {
		this.uom = uom;
	}

	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
	}

	public Double getOrederqty() {
		return orederqty;
	}

	public void setOrederqty(Double orederqty) {
		this.orederqty = orederqty;
	}

	public Double getPrevrecqt() {
		return prevrecqt;
	}

	public void setPrevrecqt(Double prevrecqt) {
		this.prevrecqt = prevrecqt;
	}

	public Double getReceivedqty() {
		return receivedqty;
	}

	public void setReceivedqty(Double receivedqty) {
		this.receivedqty = receivedqty;
	}

	public Double getAcceptqty() {
		return acceptqty;
	}

	public void setAcceptqty(Double acceptqty) {
		this.acceptqty = acceptqty;
	}

	public Double getRejectqty() {
		return rejectqty;
	}

	public void setRejectqty(Double rejectqty) {
		this.rejectqty = rejectqty;
	}

	public String getInspectorremarks() {
		return inspectorremarks;
	}

	public void setInspectorremarks(String inspectorremarks) {
		this.inspectorremarks = inspectorremarks;
	}

	public String getStoreremarks() {
		return storeremarks;
	}

	public void setStoreremarks(String storeremarks) {
		this.storeremarks = storeremarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ItemMasterEntity getItemMasterEntity() {
		return itemMasterEntity;
	}

	public void setItemMasterEntity(ItemMasterEntity itemMasterEntity) {
		this.itemMasterEntity = itemMasterEntity;
	}

	public List<GrnInspectionItemDetEntity> getGrnItemEntities() {
		return grnItemEntities;
	}

	public void setGrnItemEntities(List<GrnInspectionItemDetEntity> grnItemEntities) {
		this.grnItemEntities = grnItemEntities;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLangID() {
		return langID;
	}

	public void setLangID(Long langID) {
		this.langID = langID;
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

	public String[] getPkValues() {
		return new String[] { "ITM", "mm_grn_items", "grnitemid" };
	}

}
