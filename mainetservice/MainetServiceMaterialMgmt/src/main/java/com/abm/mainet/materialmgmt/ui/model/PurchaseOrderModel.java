package com.abm.mainet.materialmgmt.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderDto;
import com.abm.mainet.materialmgmt.service.IPurchaseOrderService;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Component
@Scope("session")
public class PurchaseOrderModel extends AbstractFormModel {
	
	@Autowired
	private IPurchaseOrderService purchaseOrderService;

	@Autowired
	IFileUploadService fileUpload;
	
	@Autowired
	private TbFinancialyearService financialyearService;

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;
	
	private static final long serialVersionUID = 1L;

	private PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();

	List<PurchaseOrderDto> purchaseOrderDtoList= new ArrayList<>();
	
	private List<Object[]> storeIdNameList;
			
	private List<TbAcVendormaster> vendors = new ArrayList<>();

	private String saveMode;

	private List<Object[]> requisitionObject;


	private List<Long> removedIds;
	private List<Long> fileCountUpload;
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	
	private String removeOverheadIds;
	private String removeTncIds;
	private String removeEncIds;
	
	private Boolean isNotSaveAsDraft;
	
    @Override
    public boolean saveForm() {
    	boolean status = true;
    	
    	if(MainetConstants.CommonConstants.CHAR_Y != purchaseOrderDto.getStatus())
    		purchaseOrderDto.setStatus(MainetConstants.CommonConstants.CHAR_D); // For Draft Mode

    	List<Long> removeOverheadIdList = new ArrayList<>();
    	List<Long> removeTncIdList = new ArrayList<>();
    	List<Long> removeEncIdList = new ArrayList<>();
    	
    	Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empID = UserSession.getCurrent().getEmployee().getEmpId();
		Date sysDate = new Date();
		Long langId = Long.valueOf(UserSession.getCurrent().getLanguageId());
		String macAddr = UserSession.getCurrent().getEmployee().getEmppiservername();
    	
		if (null == purchaseOrderDto.getPoId()) {
			purchaseOrderDto.setOrgId(orgId);
			purchaseOrderDto.setUserId(empID);
			purchaseOrderDto.setLangId(langId);
			purchaseOrderDto.setLmoDate(sysDate);
			purchaseOrderDto.setLgIpMac(macAddr);
			purchaseOrderDto.setWfFlag(MainetConstants.MENU.TRUE);
			
			purchaseOrderDto.getPurchaseOrderDetDto().forEach(purOrderDetails->{
				purOrderDetails.setOrgId(orgId);
				purOrderDetails.setUserId(empID);
				purOrderDetails.setLmoDate(sysDate);
				purOrderDetails.setLangId(langId);
				purOrderDetails.setLgIpMac(macAddr);
				purOrderDetails.setStatus(MainetConstants.CommonConstants.CHAR_Y);
			});
			
			purchaseOrderDto.getPurchaseOrderOverheadsDto().forEach(purOrderOverHeadDetails->{
				purOrderOverHeadDetails.setOrgId(orgId);
				purOrderOverHeadDetails.setUserId(empID);
				purOrderOverHeadDetails.setLmodDate(sysDate);
				purOrderOverHeadDetails.setLangId(langId);
				purOrderOverHeadDetails.setLgIpMac(macAddr);
				purOrderOverHeadDetails.setStatus(MainetConstants.CommonConstants.CHAR_Y);
			});
			
			purchaseOrderDto.getPurchaseorderTncDto().forEach(purchaseorderTncDto->{
				purchaseorderTncDto.setOrgId(orgId);
				purchaseorderTncDto.setUserId(empID);
				purchaseorderTncDto.setLmodDate(sysDate);
				purchaseorderTncDto.setLangId(langId);
				purchaseorderTncDto.setLgIpMac(macAddr);
				purchaseorderTncDto.setStatus(MainetConstants.CommonConstants.CHAR_Y);
			});
			
			purchaseOrderDto.getPurchaseorderAttachmentDto().forEach(purchaseorderAttachmentDto->{
				purchaseorderAttachmentDto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
				purchaseorderAttachmentDto.setOrgId(orgId);
				purchaseorderAttachmentDto.setCreatedBy(empID);
				purchaseorderAttachmentDto.setCreatedDate(sysDate);
				purchaseorderAttachmentDto.setLgIpMac(macAddr);
				purchaseorderAttachmentDto.setAtdStatus(MainetConstants.CommonConstants.CHAR_Y);
			});
			
			genratePurchaseOrderNo(purchaseOrderDto);
			
			if(MainetConstants.CommonConstants.CHAR_Y != purchaseOrderDto.getStatus())
        		this.setSuccessMessage(ApplicationSession.getInstance().getMessage("purchase.Order.Saved.Successfully.With.PO.No") + MainetConstants.WHITE_SPACE + purchaseOrderDto.getPoNo());
        	else
        		this.setSuccessMessage(ApplicationSession.getInstance().getMessage("material.purchase.order.submit.success") + MainetConstants.WHITE_SPACE + purchaseOrderDto.getPoNo());
		} else {
			purchaseOrderDto.setOrgId(orgId);
    		purchaseOrderDto.setUpdatedBy(empID);
    		purchaseOrderDto.setUpdatedDate(sysDate);
    		purchaseOrderDto.setLgIpMacUpd(macAddr);
    		purchaseOrderDto.setLangId(langId);
    		purchaseOrderDto.setWfFlag(MainetConstants.MENU.TRUE);
    			    			
    		purchaseOrderDto.getPurchaseOrderDetDto().forEach(purOrderDetails->{
    			purOrderDetails.setOrgId(orgId);
    			purOrderDetails.setUpdatedBy(empID);
    			purOrderDetails.setUpdatedDate(sysDate);
    			purOrderDetails.setLangId(langId);
    			purOrderDetails.setLgIpMacUpd(macAddr);
    		});
    			
    		purchaseOrderDto.getPurchaseOrderOverheadsDto().forEach(purOrderOverHeadDetails->{
    			purOrderOverHeadDetails.setOrgId(orgId);
    			purOrderOverHeadDetails.setUpdatedBy(empID);
    			purOrderOverHeadDetails.setUpdatedDate(sysDate);
    			purOrderOverHeadDetails.setLangId(langId);
    			purOrderOverHeadDetails.setLgIpMacUpd(macAddr);
    		});
    			
    		purchaseOrderDto.getPurchaseorderTncDto().forEach(purchaseorderTncDto->{
    			purchaseorderTncDto.setOrgId(orgId);
    			purchaseorderTncDto.setUpdateBy(empID);
    			purchaseorderTncDto.setUpdatedDate(sysDate);
    			purchaseorderTncDto.setLangId(langId);
    			purchaseorderTncDto.setLgIpMacUpd(macAddr);
    		});
    			
    		purchaseOrderDto.getPurchaseorderAttachmentDto().forEach(purchaseorderAttachmentDto->{
    			purchaseorderAttachmentDto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
    			purchaseorderAttachmentDto.setOrgId(orgId);
    			purchaseorderAttachmentDto.setUpdatedBy(empID);
    			purchaseorderAttachmentDto.setUpdatedDate(sysDate);
    			purchaseorderAttachmentDto.setLgIpMacUpd(macAddr);
    		});   		
    		
    		final String oIds = getRemoveOverheadIds();
    		if (null != oIds && !oIds.isEmpty()) {
    			final String array[] = oIds.split(MainetConstants.operator.COMMA);
    			for (final String string : array)
    				removeOverheadIdList.add(Long.valueOf(string));
    		}

    		final String tIds = getRemoveTncIds();
    		if (null != tIds && !tIds.isEmpty()) {
    			final String array[] = tIds.split(MainetConstants.operator.COMMA);
    			for (final String string : array)
    				removeTncIdList.add(Long.valueOf(string));
    		}

    		final String eIds = getRemoveEncIds();
    		if (null != eIds && !eIds.isEmpty()) {
    			final String array[] = eIds.split(MainetConstants.operator.COMMA);
    			for (final String string : array)
    				removeEncIdList.add(Long.valueOf(string));
    		}
    		   		
        	if(MainetConstants.CommonConstants.CHAR_Y != purchaseOrderDto.getStatus())
        		this.setSuccessMessage(ApplicationSession.getInstance().getMessage("material.purchase.order.update.success"));
        	else
        		this.setSuccessMessage(ApplicationSession.getInstance().getMessage("material.purchase.order.submit.success") + MainetConstants.WHITE_SPACE + purchaseOrderDto.getPoNo());
        	
    	}
	
		purchaseOrderService.savePurchaseOrder(purchaseOrderDto,removeOverheadIdList, removeTncIdList, removeEncIdList);
	    return status;
    }
    
    
    private void genratePurchaseOrderNo(PurchaseOrderDto purchaseOrderDto) {
		FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		final String year[] = finacialYear.split(MainetConstants.operator.HIPHEN);
	    final String finYear = year[0].substring(2) + year[1];
		final Long sequence = seqGenFunctionUtility.generateSequenceNo("MMM", MainetMMConstants.MmItemMaster.MM_PURCHASEORDER, "poid",
						UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagF, null);
		String poNo = "PO" + MainetMMConstants.MmItemMaster.STR + finYear + String.format(MainetConstants.LOI.LOI_NO_FORMAT, sequence);
		purchaseOrderDto.setPoNo(poNo);			
	}
	
	public PurchaseOrderDto getPurchaseOrderDto() {
		return purchaseOrderDto;
	}

	public void setPurchaseOrderDto(PurchaseOrderDto purchaseOrderDto) {
		this.purchaseOrderDto = purchaseOrderDto;
	}

	public List<TbAcVendormaster> getVendors() {
		return vendors;
	}


	public void setVendors(List<TbAcVendormaster> vendors) {
		this.vendors = vendors;
	}


	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<PurchaseOrderDto> getPurchaseOrderDtoList() {
		return purchaseOrderDtoList;
	}

	public void setPurchaseOrderDtoList(List<PurchaseOrderDto> purchaseOrderDtoList) {
		this.purchaseOrderDtoList = purchaseOrderDtoList;
	}

	public List<Object[]> getStoreIdNameList() {
		return storeIdNameList;
	}

	public void setStoreIdNameList(List<Object[]> storeIdNameList) {
		this.storeIdNameList = storeIdNameList;
	}

	public List<Long> getRemovedIds() {
		return removedIds;
	}

	public void setRemovedIds(List<Long> removedIds) {
		this.removedIds = removedIds;
	}

	public List<Long> getFileCountUpload() {
		return fileCountUpload;
	}

	public void setFileCountUpload(List<Long> fileCountUpload) {
		this.fileCountUpload = fileCountUpload;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public String getRemoveEncIds() {
		return removeEncIds;
	}

	public void setRemoveEncIds(String removeEncIds) {
		this.removeEncIds = removeEncIds;
	}

	public String getRemoveTncIds() {
		return removeTncIds;
	}

	public void setRemoveTncIds(String removeTncIds) {
		this.removeTncIds = removeTncIds;
	}

	public String getRemoveOverheadIds() {
		return removeOverheadIds;
	}

	public void setRemoveOverheadIds(String removeOverheadIds) {
		this.removeOverheadIds = removeOverheadIds;
	}

	public Boolean getIsNotSaveAsDraft() {
		return isNotSaveAsDraft;
	}

	public void setIsNotSaveAsDraft(Boolean isNotSaveAsDraft) {
		this.isNotSaveAsDraft = isNotSaveAsDraft;
	}


	public List<Object[]> getRequisitionObject() {
		return requisitionObject;
	}


	public void setRequisitionObject(List<Object[]> requisitionObject) {
		this.requisitionObject = requisitionObject;
	}

}
