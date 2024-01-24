package com.abm.mainet.materialmgmt.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.GoodsreceivedNotesitemDto;
import com.abm.mainet.materialmgmt.service.Igoodsrecievednoteservice;

@Component
@Scope("session")
public class GoodsReceivedNotesItemModel extends AbstractFormModel{

	@Autowired
	private Igoodsrecievednoteservice goodsrecievednoteservice;
	
	@Autowired
    private IFileUploadService fileUpload;
	
	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Autowired
	private TbFinancialyearService financialyearService;
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	
	private static final long serialVersionUID = 1157757988199312984L;

	private GoodsReceivedNotesDto goodsNotesDto = new GoodsReceivedNotesDto();
	
	private List<Object[]> storeIdAndNameList = new ArrayList<>();
	
	private List<GoodsreceivedNotesitemDto> goodsItemList = new ArrayList<>();
	
	private List<ItemMasterDTO> itemList;
	
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	 
	private List<DocumentDetailsVO> documents = new ArrayList<>();
	
	private String saveMode;
	
	List<GoodsReceivedNotesDto> goodsReceivedNotesDtoList= new ArrayList<>();
	
	private Long storeId;

	private List<Object[]> purchaseOrderObjects = new ArrayList<>();
	
	@Override
	public boolean saveForm() {
		boolean status = false;
		if (null == goodsNotesDto.getGrnid()) {
			goodsNotesDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			goodsNotesDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			goodsNotesDto.setLmoDate(new Date());
			goodsNotesDto.setLgipmac(getClientIpAddress());
			goodsNotesDto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			goodsNotesDto.setStatus(MainetConstants.FlagA);

			goodsNotesDto.getGoodsreceivedNotesItemList().forEach(itemDto -> {
				itemDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				itemDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				itemDto.setLmodDate(new Date());
				itemDto.setLgIpMac(getClientIpAddress());
				itemDto.setLangID(Long.valueOf(UserSession.getCurrent().getLanguageId()));
				itemDto.setStatus(MainetConstants.FlagA);
			});

			generateGRNNumber(goodsNotesDto);
			
			goodsrecievednoteservice.saveGoodsReceivedNotes(goodsNotesDto);
			status = true;

			FileUploadDTO requestDTO = new FileUploadDTO();
			requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setIdfId("MMM" + MainetConstants.WINDOWS_SLASH + goodsNotesDto.getGrnno() + MainetConstants.WINDOWS_SLASH + goodsNotesDto.getGrnno());
			requestDTO.setDepartmentName("MMM");
			requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			List<DocumentDetailsVO> dto = getDocuments();
			setDocuments(fileUpload.setFileUploadMethod(getDocuments()));
			setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
			fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
			int i = 0;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				getDocuments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
				i++;
			}
		}
		return status;
	}
	
	public void generateGRNNumber(GoodsReceivedNotesDto goodsNotesDto) {
		final Long javaFq = seqGenFunctionUtility.generateSequenceNo(MainetConstants.DEPT_SHORT_NAME.STORE, "mm_grn", "grnid", goodsNotesDto.getOrgId(),
				MainetConstants.RnLCommon.Flag_C, null);
		FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		final String year[] = finacialYear.split("-");
		final String finYear = year[0].substring(2) + year[1];
		String number = "GRN" + MainetConstants.DEPT_SHORT_NAME.STORE + finYear
				+ String.format(MainetConstants.LOI.LOI_NO_FORMAT, Long.parseLong(javaFq.toString()));
		goodsNotesDto.setGrnno(number);
		this.setSuccessMessage(ApplicationSession.getInstance().getMessage("saved.Successfully.With.GRN.No") + " " + goodsNotesDto.getGrnno());
	}

	public GoodsReceivedNotesDto getGoodsNotesDto() {
		return goodsNotesDto;
	}

	public void setGoodsNotesDto(GoodsReceivedNotesDto goodsNotesDto) {
		this.goodsNotesDto = goodsNotesDto;
	}

	public List<GoodsreceivedNotesitemDto> getGoodsItemList() {
		return goodsItemList;
	}

	public void setGoodsItemList(List<GoodsreceivedNotesitemDto> goodsItemList) {
		this.goodsItemList = goodsItemList;
	}

	public List<Object[]> getStoreIdAndNameList() {
		return storeIdAndNameList;
	}

	public void setStoreIdAndNameList(List<Object[]> storeIdAndNameList) {
		this.storeIdAndNameList = storeIdAndNameList;
	}

	public List<ItemMasterDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemMasterDTO> itemList) {
		this.itemList = itemList;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public List<GoodsReceivedNotesDto> getGoodsReceivedNotesDtoList() {
		return goodsReceivedNotesDtoList;
	}

	public void setGoodsReceivedNotesDtoList(List<GoodsReceivedNotesDto> goodsReceivedNotesDtoList) {
		this.goodsReceivedNotesDtoList = goodsReceivedNotesDtoList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public List<DocumentDetailsVO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDetailsVO> documents) {
		this.documents = documents;
	}

	public List<Object[]> getPurchaseOrderObjects() {
		return purchaseOrderObjects;
	}

	public void setPurchaseOrderObjects(List<Object[]> purchaseOrderObjects) {
		this.purchaseOrderObjects = purchaseOrderObjects;
	}

}
