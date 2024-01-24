package com.abm.mainet.materialmgmt.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.GoodsreceivedNotesitemDto;
import com.abm.mainet.materialmgmt.dto.GrnInspectionItemDetDTO;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDto;
import com.abm.mainet.materialmgmt.service.Igoodsrecievednoteservice;
import com.abm.mainet.materialmgmt.ui.validator.GoodsRecievedNotesInspectionValidator;

@Component
@Scope("session")
public class InspectionItemsModel extends AbstractFormModel {

	private static final long serialVersionUID = 4916926127322278881L;

	private GrnInspectionItemDetDTO inspectionItemsDto = new GrnInspectionItemDetDTO();

	private Map<Long, List<GrnInspectionItemDetDTO>> inspectionItemsMap = new LinkedHashMap<>();
		
	private List<GrnInspectionItemDetDTO> inspectionItemsList = new ArrayList<>();
	
	private String managementMethod;
	
	private int indexCount;
	
	private List<EmployeeDTO> employeeDtoList = new ArrayList<>();

	private List<ItemMasterDTO> itemList;
	
	private List<Object[]> employeesObject;

	private List<Object[]> binLocObjectList;
	
	private List<Object[]> grnObjectList;
	
	private PurchaseRequistionDto purchaserequistionDto = new PurchaseRequistionDto();

	private List<PurchaseRequistionDto> purchaseRequistionList = new ArrayList<>();

	private GoodsreceivedNotesitemDto grnitemDto = new GoodsreceivedNotesitemDto();

	private List<GoodsreceivedNotesitemDto> grnitemDtoList = new ArrayList<>();

	private GoodsReceivedNotesDto goodsReceivedNotesDto = new GoodsReceivedNotesDto();

	private List<GoodsReceivedNotesDto> goodsReceivedNotesDtoList = new ArrayList<>();

	private List<GoodsReceivedNotesDto> goodsReceivedNotesHelperDtoList = new ArrayList<>();
	
	private ItemMasterDTO dto = new ItemMasterDTO();
	
	private String saveMode;
	
	private String inspectionStatus;
	
	private List<Object[]> purchaseOrderObjects = new ArrayList<>();

	private List<Object[]> storeIdAndNameList = new ArrayList<>();

	@Autowired
	private Igoodsrecievednoteservice goodsrecievednoteservice;

	Map<Long, String> grnNoMap = new HashMap<>();

	private int rowCount;

	private String modeType;
	
	private String removeInBatchIds;
	private String removeSerialIds;
	private String removeNotInBatchIds;
	private String itemNumberList;
	
	@SuppressWarnings("deprecation")
	public boolean saveForm() {
		boolean status = false;
		
		if (goodsReceivedNotesDto.getGrnid() != null) {
			goodsReceivedNotesDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			goodsReceivedNotesDto.setUpdatedby(UserSession.getCurrent().getEmployee().getEmpId());
			goodsReceivedNotesDto.setUpdateddate(new Date());
			goodsReceivedNotesDto.setLgipmacupd(Utility.getMacAddress());			
			goodsReceivedNotesDto.setStatus(this.getInspectionStatus());
			
			goodsReceivedNotesDto.getGoodsreceivedNotesItemList().forEach(goodsreceivedNotesitemDto -> {
				goodsreceivedNotesitemDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				goodsreceivedNotesitemDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				goodsreceivedNotesitemDto.setLgIpMacUpd(Utility.getMacAddress());
				goodsreceivedNotesitemDto.setUpdatedDate(new Date());	
				goodsreceivedNotesitemDto.setStatus(this.getInspectionStatus());
				//to eliminate duplication of Grand Children 
				goodsreceivedNotesitemDto.getIspectionItemsList().clear();
			
				this.getInspectionItemsMap().get(goodsreceivedNotesitemDto.getGrnitemid()).forEach(detDto -> {
					if (detDto.getGrnitemid().equals(goodsreceivedNotesitemDto.getGrnitemid())) {
						detDto.setLangId(goodsreceivedNotesitemDto.getLangID());
						detDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
						detDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
						detDto.setLgIpMacUpd(Utility.getMacAddress());
						detDto.setLmoDate(new Date());
						detDto.setStatus('Y');

						goodsreceivedNotesitemDto.getIspectionItemsList().add(detDto);
					}
				});
			});
			
			validateBean(goodsReceivedNotesDto, GoodsRecievedNotesInspectionValidator.class);
			if (hasValidationErrors()) {
				return false;
			} else {
				
				List<Long> removeInBatchIdList = new ArrayList<>();
		    	List<Long> removeSerialIdList = new ArrayList<>();
		    	List<Long> removeNotInBatchList = new ArrayList<>();
				
				final String inBatchIds = getRemoveInBatchIds();
				if (null != inBatchIds && !inBatchIds.isEmpty()) {
					final String array[] = inBatchIds.split(MainetConstants.operator.COMMA);
					for (final String string : array) {
						removeInBatchIdList.add(Long.valueOf(string));
					}
				}

				final String serialIds = getRemoveSerialIds();
				if (null != serialIds && !serialIds.isEmpty()) {
					final String array[] = serialIds.split(MainetConstants.operator.COMMA);
					for (final String string : array) {
						removeSerialIdList.add(Long.valueOf(string));
					}
				}

				final String notInBatchIds = getRemoveNotInBatchIds();
				if (null != notInBatchIds && !notInBatchIds.isEmpty()) {
					final String array[] = notInBatchIds.split(MainetConstants.operator.COMMA);
					for (final String string : array) {
						removeNotInBatchList.add(Long.valueOf(string));
					}
				}

				goodsrecievednoteservice.saveGrnInspectionData(goodsReceivedNotesDto, removeInBatchIdList, removeSerialIdList, removeNotInBatchList);
				status = true;
				if(this.getInspectionStatus().equals("D"))
				this.setSuccessMessage(ApplicationSession.getInstance().getMessage("gRN.Inspection.Saved.As.Draft.Succesfully"));
				else if(this.getInspectionStatus().equals("I"))
					this.setSuccessMessage(ApplicationSession.getInstance().getMessage("GRN.Inspection.Submitted.Succesfully"));
					
				else if(this.getInspectionStatus().equals("S"))
					
				this.setSuccessMessage(ApplicationSession.getInstance().getMessage("GRN.Store.Entry.Submitted.Succesfully"));
			}
		}
		return status;
	}
	
	
		
	public GrnInspectionItemDetDTO getInspectionItemsDto() {
		return inspectionItemsDto;
	}

	public void setInspectionItemsDto(GrnInspectionItemDetDTO inspectionItemsDto) {
		this.inspectionItemsDto = inspectionItemsDto;
	}

	public List<GrnInspectionItemDetDTO> getInspectionItemsList() {
		return inspectionItemsList;
	}

	public void setInspectionItemsList(List<GrnInspectionItemDetDTO> inspectionItemsList) {
		this.inspectionItemsList = inspectionItemsList;
	}

	public List<EmployeeDTO> getEmployeeDtoList() {
		return employeeDtoList;
	}

	public void setEmployeeDtoList(List<EmployeeDTO> employeeDtoList) {
		this.employeeDtoList = employeeDtoList;
	}

	public List<ItemMasterDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemMasterDTO> itemList) {
		this.itemList = itemList;
	}

	public List<Object[]> getEmployeesObject() {
		return employeesObject;
	}

	public void setEmployeesObject(List<Object[]> employeesObject) {
		this.employeesObject = employeesObject;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	/*public List<BinLocMasDto> getBinLocList() {
		return binLocList;
	}
	
	public void setBinLocList(List<BinLocMasDto> binLocList) {
		this.binLocList = binLocList;
	}*/

	public List<Object[]> getBinLocObjectList() {
		return binLocObjectList;
	}

	public void setBinLocObjectList(List<Object[]> binLocObjectList) {
		this.binLocObjectList = binLocObjectList;
	}

	public List<Object[]> getGrnObjectList() {
		return grnObjectList;
	}

	public void setGrnObjectList(List<Object[]> grnObjectList) {
		this.grnObjectList = grnObjectList;
	}

	public PurchaseRequistionDto getPurchaserequistionDto() {
		return purchaserequistionDto;
	}

	public void setPurchaserequistionDto(PurchaseRequistionDto purchaserequistionDto) {
		this.purchaserequistionDto = purchaserequistionDto;
	}

	public List<PurchaseRequistionDto> getPurchaseRequistionList() {
		return purchaseRequistionList;
	}

	public void setPurchaseRequistionList(List<PurchaseRequistionDto> purchaseRequistionList) {
		this.purchaseRequistionList = purchaseRequistionList;
	}

	public GoodsreceivedNotesitemDto getGrnitemDto() {
		return grnitemDto;
	}
	
	public void setGrnitemDto(GoodsreceivedNotesitemDto grnitemDto) {
		this.grnitemDto = grnitemDto;
	}
	
	public List<GoodsreceivedNotesitemDto> getGrnitemDtoList() {
		return grnitemDtoList;
	}
	
	public void setGrnitemDtoList(List<GoodsreceivedNotesitemDto> grnitemDtoList) {
		this.grnitemDtoList = grnitemDtoList;
	}

	public ItemMasterDTO getDto() {
		return dto;
	}

	public void setDto(ItemMasterDTO dto) {
		this.dto = dto;
	}

	public Map<Long, String> getGrnNoMap() {
		return grnNoMap;
	}

	public void setGrnNoMap(Map<Long, String> grnNoMap) {
		this.grnNoMap = grnNoMap;
	}

	public GoodsReceivedNotesDto getGoodsReceivedNotesDto() {
		return goodsReceivedNotesDto;
	}

	public void setGoodsReceivedNotesDto(GoodsReceivedNotesDto goodsReceivedNotesDto) {
		this.goodsReceivedNotesDto = goodsReceivedNotesDto;
	}

	public List<GoodsReceivedNotesDto> getGoodsReceivedNotesHelperDtoList() {
		return goodsReceivedNotesHelperDtoList;
	}

	public void setGoodsReceivedNotesHelperDtoList(List<GoodsReceivedNotesDto> goodsReceivedNotesHelperDtoList) {
		this.goodsReceivedNotesHelperDtoList = goodsReceivedNotesHelperDtoList;
	}

	public List<GoodsReceivedNotesDto> getGoodsReceivedNotesDtoList() {
		return goodsReceivedNotesDtoList;
	}

	public void setGoodsReceivedNotesDtoList(List<GoodsReceivedNotesDto> goodsReceivedNotesDtoList) {
		this.goodsReceivedNotesDtoList = goodsReceivedNotesDtoList;
	}

	public String getManagementMethod() {
		return managementMethod;
	}

	public void setManagementMethod(String managementMethod) {
		this.managementMethod = managementMethod;
	}

	public int getIndexCount() {
		return indexCount;
	}

	public void setIndexCount(int indexCount) {
		this.indexCount = indexCount;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public Map<Long, List<GrnInspectionItemDetDTO>> getInspectionItemsMap() {
		return inspectionItemsMap;
	}

	public void setInspectionItemsMap(Map<Long, List<GrnInspectionItemDetDTO>> inspectionItemsMap) {
		this.inspectionItemsMap = inspectionItemsMap;
	}

	public String getInspectionStatus() {
		return inspectionStatus;
	}

	public void setInspectionStatus(String inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}

	public String getRemoveSerialIds() {
		return removeSerialIds;
	}

	public void setRemoveSerialIds(String removeSerialIds) {
		this.removeSerialIds = removeSerialIds;
	}

	public String getRemoveInBatchIds() {
		return removeInBatchIds;
	}

	public void setRemoveInBatchIds(String removeInBatchIds) {
		this.removeInBatchIds = removeInBatchIds;
	}

	public String getRemoveNotInBatchIds() {
		return removeNotInBatchIds;
	}

	public void setRemoveNotInBatchIds(String removeNotInBatchIds) {
		this.removeNotInBatchIds = removeNotInBatchIds;
	}

	public String getItemNumberList() {
		return itemNumberList;
	}

	public void setItemNumberList(String itemNumberList) {
		this.itemNumberList = itemNumberList;
	}

	public List<Object[]> getPurchaseOrderObjects() {
		return purchaseOrderObjects;
	}

	public void setPurchaseOrderObjects(List<Object[]> purchaseOrderObjects) {
		this.purchaseOrderObjects = purchaseOrderObjects;
	}

	public List<Object[]> getStoreIdAndNameList() {
		return storeIdAndNameList;
	}

	public void setStoreIdAndNameList(List<Object[]> storeIdAndNameList) {
		this.storeIdAndNameList = storeIdAndNameList;
	}

}
