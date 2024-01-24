package com.abm.mainet.materialmgmt.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.MaterialDispatchNoteDTO;
import com.abm.mainet.materialmgmt.dto.MaterialDispatchNoteItemsEntryDTO;
import com.abm.mainet.materialmgmt.service.IMaterialDispatchNoteService;
import com.abm.mainet.materialmgmt.ui.validator.MaterialDispatchNoteValidator;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Component
@Scope("session")
public class MaterialDispatchNoteModel extends AbstractFormModel {
	
	@Resource
	private TbFinancialyearService financialyearService;

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private IMaterialDispatchNoteService materialDispatchNoteService;
	
	@Resource
	private IServiceMasterDAO iServiceMasterDAO;
	
	@Resource
	private IWorkflowRequestService iWorkflowRequestService;

	private static final long serialVersionUID = 1L;

	private MaterialDispatchNoteDTO dispatchNoteDTO = new MaterialDispatchNoteDTO();
	
	private List<MaterialDispatchNoteDTO> dispatchNoteDTOList = new ArrayList<>();

	private List<Object[]> storeIdNameList;

	private List<Object[]> itemIdNameList;

	private List<Object[]> storeIndentList;

	private boolean lastChecker;

	private long levelCheck;

	private int indexCount;

	private String management;

	private String saveMode;

	private BigDecimal remainQtyToBeIssued;
	
	private MaterialDispatchNoteItemsEntryDTO noteItemsEntryDTO = new MaterialDispatchNoteItemsEntryDTO();

	private List<MaterialDispatchNoteItemsEntryDTO> noteItemsEntryDTOList = new ArrayList<>();

	private Map<Long, List<MaterialDispatchNoteItemsEntryDTO>> noteItemsEntryMap = new LinkedHashMap<>();

	private List<String> itemNumberList = new ArrayList<>();

	private Map<String, List<String>> itemNumbersMap = new LinkedHashMap<>();

	private List<BinLocMasDto> binLocList = new ArrayList<>();
	
	private List<Object[]> binLocObjectList;
	
	@SuppressWarnings("unused")
	private static Logger LOGGER = Logger.getLogger(MaterialDispatchNoteModel.class);

	@Override
	public boolean saveForm() {
		boolean status = false;

		if( 0L == this.levelCheck && noteItemsEntryMap.isEmpty())
			this.addValidationError(ApplicationSession.getInstance().getMessage("material.management.mdn.issue.details.validate"));
		if(1L == this.levelCheck)
			validateBean(dispatchNoteDTO, MaterialDispatchNoteValidator.class);
		if(this.hasValidationErrors()) {
			return false;
		} else {
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			Long empID = UserSession.getCurrent().getEmployee().getEmpId();
			Date sysDate = new Date();
			Long langId = Long.valueOf(UserSession.getCurrent().getLanguageId());
			String macAddr = UserSession.getCurrent().getEmployee().getEmppiservername();

			if (dispatchNoteDTO.getMdnId() == null) {
				dispatchNoteDTO.setOrgId(orgId);
				dispatchNoteDTO.setUserId(empID);
				dispatchNoteDTO.setLmodDate(sysDate);
				dispatchNoteDTO.setLangId(langId);
				dispatchNoteDTO.setLgIpMac(macAddr);
				dispatchNoteDTO.setStatus(MainetConstants.FlagI); /** Issued,  Prefix: IDS */
				dispatchNoteDTO.setWfFlag(MainetConstants.WorkFlow.Status.PENDING);

				dispatchNoteDTO.getMatDispatchItemList().forEach(matDispatchItemDTO -> {
					matDispatchItemDTO.setOrgId(orgId);
					matDispatchItemDTO.setUserId(empID);
					matDispatchItemDTO.setLmodDate(sysDate);
					matDispatchItemDTO.setLangId(langId);
					matDispatchItemDTO.setLgIpMac(macAddr);
					matDispatchItemDTO.setStatus(MainetConstants.Y_FLAG);
					
					if(noteItemsEntryMap.containsKey(matDispatchItemDTO.getSiItemId())) {
						noteItemsEntryMap.get(matDispatchItemDTO.getSiItemId()).forEach(noteItemsEntryDTO -> {
							if( matDispatchItemDTO.getSiItemId().equals(noteItemsEntryDTO.getSiItemId())) {
								noteItemsEntryDTO.setOrgId(orgId);
								noteItemsEntryDTO.setUserId(empID);
								noteItemsEntryDTO.setLmodDate(sysDate);
								noteItemsEntryDTO.setLangId(langId);
								noteItemsEntryDTO.setLgIpMac(macAddr);
								noteItemsEntryDTO.setStatus(MainetConstants.Y_FLAG);
								matDispatchItemDTO.getMatDispatchItemsEntryList().add(noteItemsEntryDTO);						
							}
						});
					}
				});
				generateMDNNumber(dispatchNoteDTO);
			} else {
				dispatchNoteDTO.setUpdatedBy(empID);
				dispatchNoteDTO.setUpdatedDate(sysDate);
				dispatchNoteDTO.setLgIpMacUpd(macAddr);
				dispatchNoteDTO.setStatus(MainetConstants.FlagS); /** Inspection And Store Entry,  Prefix: IDS */
				dispatchNoteDTO.setWfFlag(MainetConstants.WorkFlow.Status.COMPLETED);

				dispatchNoteDTO.getMatDispatchItemList().forEach(matDispatchItemDTO -> {
					matDispatchItemDTO.setUpdatedBy(empID);
					matDispatchItemDTO.setUpdatedDate(sysDate);
					matDispatchItemDTO.setLgIpMacUpd(macAddr);
					/** To eliminate duplication of Grand Children */
					matDispatchItemDTO.getMatDispatchItemsEntryList().clear();
					
					noteItemsEntryMap.get(matDispatchItemDTO.getMdnItemId()).forEach(noteItemsEntryDTO -> {
						noteItemsEntryDTO.setUpdatedBy(empID);
						noteItemsEntryDTO.setUpdatedDate(sysDate);
						noteItemsEntryDTO.setLgIpMacUpd(macAddr);					
						matDispatchItemDTO.getMatDispatchItemsEntryList().add(noteItemsEntryDTO);
					});
				});
				this.getWorkflowActionDto().setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
				this.getWorkflowActionDto().setComments("AUTO APPROVED");
				prepareWorkFlowTaskAction(this.getWorkflowActionDto());
				this.setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.mdn.inspection.success"));
			}

			materialDispatchNoteService.saveMaterialDispatchNote(dispatchNoteDTO);
			ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("MDN", dispatchNoteDTO.getOrgId());
			if (0L == levelCheck)
				materialDispatchNoteService.initializeWorkFlowForFreeService(dispatchNoteDTO, serviceMas);
			else if (1L == levelCheck)
				materialDispatchNoteService.updateWorkFlowService(this.getWorkflowActionDto(), serviceMas);

			status = true;			
		}		
		return status;
	}
	
	
	private void generateMDNNumber(MaterialDispatchNoteDTO dispatchNoteDTO) {
		FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(dispatchNoteDTO.getLmodDate());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		final Long sequence = seqGenFunctionUtility.generateSequenceNo("MMM", "mm_mdn", "mdnid",
				dispatchNoteDTO.getOrgId(), MainetConstants.FlagF, null);
		String mdnNumber = "MDN" + MainetMMConstants.MmItemMaster.STR + finacialYear
				+ String.format(MainetConstants.CommonMasterUi.PADDING_FIVE, sequence);
		dispatchNoteDTO.setMdnNumber(mdnNumber);
		setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.mdn.issue.success")
				+ MainetConstants.WHITE_SPACE + dispatchNoteDTO.getMdnNumber() + MainetConstants.operator.DOT);
	}
	
	
	private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {
		this.getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmpname() + MainetConstants.WHITE_SPACE 
				+ UserSession.getCurrent().getEmployee().getEmplname());
		workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		workflowActionDto.setDateOfAction(new Date());
		workflowActionDto.setCreatedDate(new Date());
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setReferenceId(this.getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		return workflowActionDto;
	}
	
	

	public MaterialDispatchNoteDTO getDispatchNoteDTO() {
		return dispatchNoteDTO;
	}

	public void setDispatchNoteDTO(MaterialDispatchNoteDTO dispatchNoteDTO) {
		this.dispatchNoteDTO = dispatchNoteDTO;
	}

	public List<MaterialDispatchNoteDTO> getDispatchNoteDTOList() {
		return dispatchNoteDTOList;
	}


	public void setDispatchNoteDTOList(List<MaterialDispatchNoteDTO> dispatchNoteDTOList) {
		this.dispatchNoteDTOList = dispatchNoteDTOList;
	}


	public List<Object[]> getStoreIdNameList() {
		return storeIdNameList;
	}

	public void setStoreIdNameList(List<Object[]> storeIdNameList) {
		this.storeIdNameList = storeIdNameList;
	}

	public List<Object[]> getItemIdNameList() {
		return itemIdNameList;
	}

	public void setItemIdNameList(List<Object[]> itemIdNameList) {
		this.itemIdNameList = itemIdNameList;
	}

	public List<Object[]> getStoreIndentList() {
		return storeIndentList;
	}

	public void setStoreIndentList(List<Object[]> storeIndentList) {
		this.storeIndentList = storeIndentList;
	}

	public boolean isLastChecker() {
		return lastChecker;
	}

	public void setLastChecker(boolean lastChecker) {
		this.lastChecker = lastChecker;
	}

	public long getLevelCheck() {
		return levelCheck;
	}

	public void setLevelCheck(long levelCheck) {
		this.levelCheck = levelCheck;
	}

	public int getIndexCount() {
		return indexCount;
	}

	public void setIndexCount(int indexCount) {
		this.indexCount = indexCount;
	}

	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public BigDecimal getRemainQtyToBeIssued() {
		return remainQtyToBeIssued;
	}

	public void setRemainQtyToBeIssued(BigDecimal remainQtyToBeIssued) {
		this.remainQtyToBeIssued = remainQtyToBeIssued;
	}

	public MaterialDispatchNoteItemsEntryDTO getNoteItemsEntryDTO() {
		return noteItemsEntryDTO;
	}

	public void setNoteItemsEntryDTO(MaterialDispatchNoteItemsEntryDTO noteItemsEntryDTO) {
		this.noteItemsEntryDTO = noteItemsEntryDTO;
	}

	public List<MaterialDispatchNoteItemsEntryDTO> getNoteItemsEntryDTOList() {
		return noteItemsEntryDTOList;
	}

	public void setNoteItemsEntryDTOList(List<MaterialDispatchNoteItemsEntryDTO> noteItemsEntryDTOList) {
		this.noteItemsEntryDTOList = noteItemsEntryDTOList;
	}

	public Map<Long, List<MaterialDispatchNoteItemsEntryDTO>> getNoteItemsEntryMap() {
		return noteItemsEntryMap;
	}

	public void setNoteItemsEntryMap(Map<Long, List<MaterialDispatchNoteItemsEntryDTO>> noteItemsEntryMap) {
		this.noteItemsEntryMap = noteItemsEntryMap;
	}

	public List<String> getItemNumberList() {
		return itemNumberList;
	}

	public void setItemNumberList(List<String> itemNumberList) {
		this.itemNumberList = itemNumberList;
	}

	public Map<String, List<String>> getItemNumbersMap() {
		return itemNumbersMap;
	}

	public void setItemNumbersMap(Map<String, List<String>> itemNumbersMap) {
		this.itemNumbersMap = itemNumbersMap;
	}

	public List<BinLocMasDto> getBinLocList() {
		return binLocList;
	}

	public void setBinLocList(List<BinLocMasDto> binLocList) {
		this.binLocList = binLocList;
	}

	public List<Object[]> getBinLocObjectList() {
		return binLocObjectList;
	}

	public void setBinLocObjectList(List<Object[]> binLocObjectList) {
		this.binLocObjectList = binLocObjectList;
	}

}
