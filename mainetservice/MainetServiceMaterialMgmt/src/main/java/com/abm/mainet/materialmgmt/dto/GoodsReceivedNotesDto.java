package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class GoodsReceivedNotesDto implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private Long grnid;
	private String grnno;
	private Date receiveddate;
	private Long storeid;
	private Long poid;
	private String remarks;
	private String atd_Path;
	private String atd_Fname;
	private Long inspectorname;
	private Date inspectiondate;
	private String Status;
	private Long orgId;
	private Long userId;
	private Long langId;
    private Date lmoDate;
    private Long updatedby;
    private Date Updateddate;
    private String lgipmac;
    private String lgipmacupd;
    private String wfflag;
    private String receivedQtyDate;
    private Date toDate;
    private Date fromDate;
    private String storeName;
    private Date poDate;
    private String purchaseNo;
    private Long smServiceId;
    private String poNumber;
    
    List<DocumentDetailsVO> docList = new ArrayList<>();
	private List<AttachDocs> fetchIntDocList = new ArrayList<>();
    
    private GoodsreceivedNotesitemDto goodsReceivedItemDto = new GoodsreceivedNotesitemDto();
    private List<GoodsreceivedNotesitemDto> goodsreceivedNotesItemList = new ArrayList<>();
    private List<StoreMasterDTO> storemasterlist = new ArrayList<>();
	
    private Long vendorId;
    private String vendorName;
    
    
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
	
	public List<StoreMasterDTO> getStoremasterlist() {
		return storemasterlist;
	}

	public void setStoremasterlist(List<StoreMasterDTO> storemasterlist) {
		this.storemasterlist = storemasterlist;
	}

	public GoodsreceivedNotesitemDto getGoodsReceivedItemDto() {
		return goodsReceivedItemDto;
	}

	public void setGoodsReceivedItemDto(GoodsreceivedNotesitemDto goodsReceivedItemDto) {
		this.goodsReceivedItemDto = goodsReceivedItemDto;
	}

	public String getReceivedQtyDate() {
		return receivedQtyDate;
	}

	public void setReceivedQtyDate(String receivedQtyDate) {
		this.receivedQtyDate = receivedQtyDate;
	}

	public List<GoodsreceivedNotesitemDto> getGoodsreceivedNotesItemList() {
		return goodsreceivedNotesItemList;
	}

	public void setGoodsreceivedNotesItemList(List<GoodsreceivedNotesitemDto> goodsreceivedNotesItemList) {
		this.goodsreceivedNotesItemList = goodsreceivedNotesItemList;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public List<DocumentDetailsVO> getDocList() {
		return docList;
	}

	public void setDocList(List<DocumentDetailsVO> docList) {
		this.docList = docList;
	}
	
	
	public List<AttachDocs> getFetchIntDocList() {
		return fetchIntDocList;
	}

	public void setFetchIntDocList(List<AttachDocs> fetchIntDocList) {
		this.fetchIntDocList = fetchIntDocList;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	
	
}
