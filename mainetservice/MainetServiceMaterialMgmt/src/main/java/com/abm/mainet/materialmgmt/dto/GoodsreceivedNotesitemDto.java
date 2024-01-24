package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;

public class GoodsreceivedNotesitemDto implements Serializable {

	private static final long serialVersionUID = 3042461726911980382L;
	private Long grnitemid;
	private Long grnid;
	private Date receiveddate;
	private Long storeid;
	private Long poid;
	private Long itemid;
	private Long uom;
	private String uomDesc;
	private String management;
	private Double orederqty;
	private Double prevrecqt;
	private Double receivedqty;
	private Double acceptqty;
	private Double rejectqty;
	private Double remainingQty;
	private String inspectorremarks;
	private String storeremarks;
	private String status;	
	private Long orgId;
	private Long userId;
	private Long langID;
	private Date lmodDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String itemDesc;
	private Long methodId;
	private String managementCode;
	private String managementDesc;
    private String isExpiry;
	private ItemMasterDTO itemDto = new ItemMasterDTO();
	private GrnInspectionItemDetDTO inspectionDto;
	
	private List<GrnInspectionItemDetDTO> ispectionItemsList = new ArrayList<>();
	
	public Long getGrnitemid() {
		return grnitemid;
	}

	public void setGrnitemid(Long grnitemid) {
		this.grnitemid = grnitemid;
	}

	public Long getGrnid() {
		return grnid;
	}

	public void setGrnid(Long grnid) {
		this.grnid = grnid;
	}

	public Long getStoreid() {
		return storeid;
	}

	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
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


	public ItemMasterDTO getItemDto() {
		return itemDto;
	}

	public void setItemDto(ItemMasterDTO itemDto) {
		this.itemDto = itemDto;
	}

	public Double getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(Double remainingQty) {
		this.remainingQty = remainingQty;
	}

	public String getUomDesc() {
		return uomDesc;
	}

	public void setUomDesc(String uomDesc) {
		this.uomDesc = uomDesc;
	}

	public Long getMethodId() {
		return methodId;
	}

	public void setMethodId(Long methodId) {
		this.methodId = methodId;
	}

	public Date getReceiveddate() {
		return receiveddate;
	}

	public void setReceiveddate(Date receiveddate) {
		this.receiveddate = receiveddate;
	}

	public Long getPoid() {
		return poid;
	}

	public void setPoid(Long poid) {
		this.poid = poid;
	}

	public String getManagementCode() {
		return managementCode;
	}

	public void setManagementCode(String managementCode) {
		this.managementCode = managementCode;
	}

	public GrnInspectionItemDetDTO getInspectionDto() {
		return inspectionDto;
	}

	public void setInspectionDto(GrnInspectionItemDetDTO inspectionDto) {
		this.inspectionDto = inspectionDto;
	}

	public String getManagementDesc() {
		return managementDesc;
	}

	public void setManagementDesc(String managementDesc) {
		this.managementDesc = managementDesc;
	}

	public String getIsExpiry() {
		return isExpiry;
	}

	public void setIsExpiry(String isExpiry) {
		this.isExpiry = isExpiry;
	}

	public List<GrnInspectionItemDetDTO> getIspectionItemsList() {
		return ispectionItemsList;
	}

	public void setIspectionItemsList(List<GrnInspectionItemDetDTO> ispectionItemsList) {
		this.ispectionItemsList = ispectionItemsList;
	}

}