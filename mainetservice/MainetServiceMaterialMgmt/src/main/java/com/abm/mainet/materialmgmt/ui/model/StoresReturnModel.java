package com.abm.mainet.materialmgmt.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.abm.mainet.materialmgmt.dto.StoresReturnDTO;
import com.abm.mainet.materialmgmt.service.IStoressReturnService;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Component
@Scope("session")
public class StoresReturnModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private StoresReturnDTO storesReturnDTO = new StoresReturnDTO();

	private List<StoresReturnDTO> storesReturnDTOList = new ArrayList<>();

	private String saveMode;

	private long levelCheck;

	private List<Object[]> storeIdNameList;

	private List<Object[]> mdnIdNameList;

	private List<Object[]> binLocObjectList;

	private static Logger LOGGER = Logger.getLogger(StoresReturnModel.class);
	
	
	@Resource
	private TbFinancialyearService financialyearService;
	
	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;
	
	@Resource
	private IStoressReturnService storessReturnService;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;
	
	
	@Override
	public boolean saveForm() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empID = UserSession.getCurrent().getEmployee().getEmpId();
		Date sysDate = new Date();
		Long langId = Long.valueOf(UserSession.getCurrent().getLanguageId());
		String macAddr = UserSession.getCurrent().getEmployee().getEmppiservername();

		if (null == storesReturnDTO.getStoreReturnId()) {
			storesReturnDTO.setOrgId(orgId);
			storesReturnDTO.setUserId(empID);
			storesReturnDTO.setUserName(UserSession.getCurrent().getEmployee().getEmpname());
			storesReturnDTO.setCreateDate(sysDate);
			storesReturnDTO.setLangId(langId);
			storesReturnDTO.setLgIpMac(macAddr);
			storesReturnDTO.setStatus(MainetConstants.FlagP); //Pending for Item Return Entry
			
			storesReturnDTO.getStoresReturnDetailList().forEach(returnDetailDTO -> {
				returnDetailDTO.setOrgId(orgId);
				returnDetailDTO.setUserId(empID);
				returnDetailDTO.setCreateDate(sysDate);
				returnDetailDTO.setLangId(langId);
				returnDetailDTO.setLgIpMac(macAddr);
				returnDetailDTO.setStatus(MainetConstants.FlagY);
				returnDetailDTO.setDisposalFlag(MainetConstants.FlagN);
			});
			genrateStoresReturnNo(storesReturnDTO);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.store.return.success") + 
					MainetConstants.WHITE_SPACE + storesReturnDTO.getStoreReturnNo());
		} else {
			storesReturnDTO.setUpdatedBy(empID);
			storesReturnDTO.setUpdatedDate(sysDate);
			storesReturnDTO.setLgIpMacUpd(macAddr);
			storesReturnDTO.setStatus(MainetConstants.FlagY); //Item Return Entry Done
			
			storesReturnDTO.getStoresReturnDetailList().forEach(returnDetailDTO -> {
				returnDetailDTO.setUpdatedBy(empID);
				returnDetailDTO.setUpdatedDate(sysDate);
				returnDetailDTO.setLgIpMacUpd(macAddr);
				if(null == returnDetailDTO.getDisposalFlag())
					returnDetailDTO.setDisposalFlag(MainetConstants.FlagN);
			});
			this.getWorkflowActionDto().setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
			this.getWorkflowActionDto().setComments("AUTO APPROVED");
			prepareWorkFlowTaskAction(this.getWorkflowActionDto());
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.store.return.entry.success"));
		}
		storessReturnService.saveStoresReturnData(storesReturnDTO);
		
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("STR", storesReturnDTO.getOrgId());
		if (0L == levelCheck)
			storessReturnService.initializeWorkFlowForFreeService(storesReturnDTO, serviceMas);
		else if (1L == levelCheck)
			storessReturnService.updateWorkFlowService(this.getWorkflowActionDto(), serviceMas);

		return true;
	}

	private void genrateStoresReturnNo(StoresReturnDTO storesReturnDTO) {
		FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		final Long sequence = seqGenFunctionUtility.generateSequenceNo("MMM", "mm_storereturn", "storereturnid",
				storesReturnDTO.getOrgId(), MainetConstants.FlagF, null);
		String returnNo = "SRN" + MainetMMConstants.MmItemMaster.STR + finacialYear
				+ String.format(MainetConstants.CommonMasterUi.PADDING_FIVE, sequence);
		storesReturnDTO.setStoreReturnNo(returnNo);
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

	public StoresReturnDTO getStoresReturnDTO() {
		return storesReturnDTO;
	}

	public void setStoresReturnDTO(StoresReturnDTO storesReturnDTO) {
		this.storesReturnDTO = storesReturnDTO;
	}

	public List<StoresReturnDTO> getStoresReturnDTOList() {
		return storesReturnDTOList;
	}

	public void setStoresReturnDTOList(List<StoresReturnDTO> storesReturnDTOList) {
		this.storesReturnDTOList = storesReturnDTOList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public long getLevelCheck() {
		return levelCheck;
	}

	public void setLevelCheck(long levelCheck) {
		this.levelCheck = levelCheck;
	}

	public List<Object[]> getStoreIdNameList() {
		return storeIdNameList;
	}

	public void setStoreIdNameList(List<Object[]> storeIdNameList) {
		this.storeIdNameList = storeIdNameList;
	}

	public List<Object[]> getMdnIdNameList() {
		return mdnIdNameList;
	}

	public void setMdnIdNameList(List<Object[]> mdnIdNameList) {
		this.mdnIdNameList = mdnIdNameList;
	}

	public List<Object[]> getBinLocObjectList() {
		return binLocObjectList;
	}

	public void setBinLocObjectList(List<Object[]> binLocObjectList) {
		this.binLocObjectList = binLocObjectList;
	}

	public static Logger getLOGGER() {
		return LOGGER;
	}

	public static void setLOGGER(Logger lOGGER) {
		LOGGER = lOGGER;
	}

}
