package com.abm.mainet.materialmgmt.ui.model;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.TaxDefinationDto;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillDedDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillExpDetailDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.InvoiceEntryDTO;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderDto;
import com.abm.mainet.materialmgmt.repository.IInvoiceEntryRepository;
import com.abm.mainet.materialmgmt.service.IInvoiceEntryService;
import com.abm.mainet.materialmgmt.ui.validator.InvoiceEntryValidator;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Component
@Scope("session")
public class InvoiceEntryModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private List<TbAcVendormaster> vendors = new ArrayList<>();

	private List<ItemMasterDTO> itemNameList = new ArrayList<>();

	private List<Object[]> storeIdNameList;

	private List<BinLocMasDto> binLocList = new ArrayList<>();

	private InvoiceEntryDTO invoiceEntryDTO = new InvoiceEntryDTO();

	private List<InvoiceEntryDTO> invoiceEntryDTOList = new ArrayList<>();

	private List<PurchaseOrderDto> purchaseOrderDtoList = new ArrayList<>();

	private List<GoodsReceivedNotesDto> goodsReceivedNotesMultiselectList = new ArrayList<>();

	private List<GoodsReceivedNotesDto> goodsReceivedNotesDtoList = new ArrayList<>();

	private String saveMode;

	private Character invoiceSaveStatus;

	private static Logger LOGGER = Logger.getLogger(InvoiceEntryModel.class);

	private String removeOverheadIds;

	private long levelCheck;

	private List<DocumentDetailsVO> documents = new ArrayList<>();

	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	private List<AttachDocs> attachDocsList = new ArrayList<>();

	private VendorBillApprovalDTO approvalDTO = new VendorBillApprovalDTO();

	private VendorBillExpDetailDTO billExpDetailDTO = new VendorBillExpDetailDTO();

	private List<VendorBillExpDetailDTO> expDetListDto = new ArrayList<>();

	private List<TaxDefinationDto> listTaxDefinationDto = new ArrayList<>();

	@Autowired
	private IInvoiceEntryService invoiceEntryService;

	@Resource
	private TbAcVendormasterService tbAcVendormasterService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private TbFinancialyearService financialyearService;

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private DepartmentService departmentService;

	@Autowired
	private IInvoiceEntryRepository invoiceEntryRepository;

	@Autowired
	private ILocationMasService locMasService;

	@Resource
	private ServiceMasterService serviceMasterService;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean saveForm() {
		boolean status = false;

		validateBean(invoiceEntryDTO, InvoiceEntryValidator.class);
		if (1L == this.levelCheck)
			validateBean(this.getWorkflowActionDto(), CheckerActionValidator.class);
		if (hasValidationErrors())
			return false;
		else {
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			Long empID = UserSession.getCurrent().getEmployee().getEmpId();
			Date sysDate = new Date();
			Long langId = Long.valueOf(UserSession.getCurrent().getLanguageId());
			String macAddr = Utility.getMacAddress();

			if (null == invoiceEntryDTO.getInvoiceId()) {
				invoiceEntryDTO.setOrgId(orgId);
				invoiceEntryDTO.setUserId(empID);
				invoiceEntryDTO.setUserName(UserSession.getCurrent().getEmployee().getEmpname());
				invoiceEntryDTO.setLmodDate(sysDate);
				invoiceEntryDTO.setLangId(langId);
				invoiceEntryDTO.setLgIpMac(macAddr);
				invoiceEntryDTO.setPaymentStatus(MainetConstants.CommonConstants.CHAR_N);
				invoiceEntryDTO.setPaymentMade(BigDecimal.ZERO);
				invoiceEntryDTO.setInvoiceStatus(MainetConstants.FileTypes.PDF);
				invoiceEntryDTO.setWfFlag(MainetConstants.FlagY);

				invoiceEntryDTO.getInvoiceEntryGRNDTOList().forEach(grnDetailDto -> {
					grnDetailDto.setOrgId(orgId);
					grnDetailDto.setUserId(empID);
					grnDetailDto.setLmodDate(sysDate);
					grnDetailDto.setLangId(langId);
					grnDetailDto.setLgIpMac(macAddr);
					grnDetailDto.setStatus(MainetConstants.CommonConstants.CHAR_Y);
				});

				invoiceEntryDTO.getInvoiceEntryDetailDTOList().forEach(itemDetailDto -> {
					itemDetailDto.setOrgId(orgId);
					itemDetailDto.setUserId(empID);
					itemDetailDto.setLmodDate(sysDate);
					itemDetailDto.setLangId(langId);
					itemDetailDto.setLgIpMac(macAddr);
					itemDetailDto.setStatus(MainetConstants.CommonConstants.CHAR_Y);
				});

				invoiceEntryDTO.getInvoiceOverheadsDTOList().forEach(ovehheadDetailDto -> {
					ovehheadDetailDto.setOrgId(orgId);
					ovehheadDetailDto.setUserId(empID);
					ovehheadDetailDto.setLmodDate(sysDate);
					ovehheadDetailDto.setLangId(langId);
					ovehheadDetailDto.setLgIpMac(macAddr);
					ovehheadDetailDto.setStatus(MainetConstants.CommonConstants.CHAR_Y);
				});

				genrateInvoiceNo(invoiceEntryDTO);
				setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.invoice.submit.success")
							+ MainetConstants.WHITE_SPACE + invoiceEntryDTO.getInvoiceNo());
			} else {
				invoiceEntryDTO.setUpdatedBy(empID);
				invoiceEntryDTO.setUpdatedDate(sysDate);
				invoiceEntryDTO.setLgIpMacUpd(macAddr);

				invoiceEntryDTO.getInvoiceEntryGRNDTOList().forEach(grnDetailDto -> {
					grnDetailDto.setUpdatedBy(empID);
					grnDetailDto.setUpdatedDate(sysDate);
					grnDetailDto.setLgIpMacUpd(macAddr);
					if (MainetConstants.WorkFlow.Decision.REJECTED.equals(this.getWorkflowActionDto().getDecision()))
						grnDetailDto.setStatus(MainetConstants.AccountBillEntry.R);
				});

				invoiceEntryDTO.getInvoiceEntryDetailDTOList().forEach(itemDetailDto -> {
					itemDetailDto.setUpdatedBy(empID);
					itemDetailDto.setUpdatedDate(sysDate);
					itemDetailDto.setLgIpMacUpd(macAddr);
					if (MainetConstants.WorkFlow.Decision.REJECTED.equals(this.getWorkflowActionDto().getDecision()))
						itemDetailDto.setStatus(MainetConstants.AccountBillEntry.R);
				});

				invoiceEntryDTO.getInvoiceOverheadsDTOList().forEach(ovehheadDetailDto -> {
					ovehheadDetailDto.setUpdatedBy(empID);
					ovehheadDetailDto.setUpdatedDate(sysDate);
					ovehheadDetailDto.setLgIpMacUpd(macAddr);
					if (MainetConstants.WorkFlow.Decision.REJECTED.equals(this.getWorkflowActionDto().getDecision()))
						ovehheadDetailDto.setStatus(MainetConstants.AccountBillEntry.R);
				});

				prepareWorkFlowTaskAction(this.getWorkflowActionDto());
				if (MainetConstants.WorkFlow.Decision.REJECTED.equals(this.getWorkflowActionDto().getDecision()))
					setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.application.rejected.success"));
				else
					this.setSuccessMessage(ApplicationSession.getInstance().getMessage("application.Approved.Successfully"));
			}

			invoiceEntryService.saveInvoiceEntryData(invoiceEntryDTO);

			ServiceMaster service = serviceMasterService.getServiceMasterByShortCode(
					MainetConstants.ServiceShortCode.STORE_INV_SHORT_CODE, invoiceEntryDTO.getOrgId());
			if ( 0L == this.levelCheck)
				invoiceEntryService.initializeWorkFlowForFreeService(invoiceEntryDTO, service);
			else if (1L == this.levelCheck)
				invoiceEntryService.updateWorkFlowService(this.getWorkflowActionDto(), service);
			
			if (1L == this.levelCheck)
				setWorkFlowStatus();

			status = true;

			/*********** File Upload *************/
			List<Long> removeFileById = null;
			String fileId = invoiceEntryDTO.getRemoveFileById();
			if (fileId != null && !fileId.isEmpty()) {
				removeFileById = new ArrayList<>();
				String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
				for (String fields : fileArray)
					removeFileById.add(Long.valueOf(fields));
			}
			if (removeFileById != null && !removeFileById.isEmpty())
				tbAcVendormasterService.updateUploadedFileDeleteRecords(removeFileById, invoiceEntryDTO.getUserId());

			FileUploadDTO requestDTO = new FileUploadDTO();
			requestDTO.setOrgId(orgId);
			requestDTO.setStatus(MainetConstants.FlagA);
			requestDTO.setIdfId(MainetConstants.DEPT_SHORT_NAME.STORE + MainetConstants.WINDOWS_SLASH + invoiceEntryDTO.getInvoiceNo());
			requestDTO.setDepartmentName(MainetConstants.DEPT_SHORT_NAME.STORE);
			requestDTO.setUserId(empID);
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

	private void genrateInvoiceNo(InvoiceEntryDTO invoiceEntryDTO) {
		FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(new Date());
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		final Long sequence = seqGenFunctionUtility.generateSequenceNo("MMM", "mm_invoice", "invoiceid",
				UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagF, null);
		String invoiceNo = "INV" + MainetMMConstants.MmItemMaster.STR + finacialYear
				+ String.format(MainetConstants.CommonMasterUi.PADDING_FIVE, sequence);
		invoiceEntryDTO.setInvoiceNo(invoiceNo);
	}

	private WorkflowTaskAction prepareWorkFlowTaskAction(WorkflowTaskAction workflowActionDto) {
		this.getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		workflowActionDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		workflowActionDto.setEmpName(UserSession.getCurrent().getEmployee().getEmpname() + " " + UserSession.getCurrent().getEmployee().getEmplname());
		workflowActionDto.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		workflowActionDto.setDateOfAction(new Date());
		workflowActionDto.setCreatedDate(new Date());
		workflowActionDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		workflowActionDto.setReferenceId(this.getWorkflowActionDto().getReferenceId());
		workflowActionDto.setPaymentMode(MainetConstants.FlagF);
		workflowActionDto.setIsFinalApproval(false);
		return workflowActionDto;
	}

	private void setWorkFlowStatus() {
		this.getWorkflowActionDto().setReferenceId(this.invoiceEntryDTO.getInvoiceNo());
		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowRequestService.class)
				.getWorkflowRequestByAppIdOrRefId(null, this.getWorkflowActionDto().getReferenceId(), orgId);

		if (workflowRequest != null && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED))
			invoiceEntryService.updateWorkFlowStatus(this.getWorkflowActionDto().getReferenceId(), orgId,
					MainetConstants.AccountBillEntry.R, MainetConstants.FlagC);

		if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
			invoiceEntryService.updateWorkFlowStatus(this.getWorkflowActionDto().getReferenceId(), orgId,
					MainetConstants.CommonConstants.CHAR_A, MainetConstants.FlagC);
			if (null != CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE))
				saveBillApprval(invoiceEntryDTO);
		}
	}

	/**
	 * Save Store Invoice Approval and And Push data in Account Module to generate Vendor Bills
	 * @param invoiceEntryDTO
	 */
	private void saveBillApprval(InvoiceEntryDTO invoiceEntryDTO) {
		ResponseEntity<?> responseEntity = null;
		List<VendorBillDedDetailDTO> deductionDetList = new ArrayList<>();
		approvalDTO.setBillEntryDate(Utility.dateToString(new Date()));
		approvalDTO.setBillTypeId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("SPL", AccountPrefix.ABT.toString(),
				invoiceEntryDTO.getOrgId()));
		approvalDTO.setOrgId(invoiceEntryDTO.getOrgId());
		approvalDTO.setNarration("Bill Against Store Invoice No :" + MainetConstants.WHITE_SPACE + invoiceEntryDTO.getInvoiceNo());
		approvalDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		approvalDTO.setCreatedDate(Utility.dateToString(new Date()));
		approvalDTO.setLgIpMacAddress(UserSession.getCurrent().getEmployee().getEmppiservername());
		approvalDTO.setVendorId(invoiceEntryDTO.getVendorId());
		approvalDTO.setInvoiceNumber(invoiceEntryDTO.getInvoiceNo());
		approvalDTO.setInvoiceAmount(invoiceEntryDTO.getInvoiceAmt());

		/** fetch Acc head Id from acc head of PR */
		Long sacHeadId = invoiceEntryRepository.fetchAccHeadIdsForInvoice(invoiceEntryDTO.getPoId(), invoiceEntryDTO.getOrgId());
		billExpDetailDTO.setBudgetCodeId(sacHeadId); 
		billExpDetailDTO.setAmount(invoiceEntryDTO.getInvoiceAmt());
		billExpDetailDTO.setSanctionedAmount(invoiceEntryDTO.getInvoiceAmt());
		expDetListDto.add(billExpDetailDTO);
		approvalDTO.setExpDetListDto(expDetListDto);

		invoiceEntryDTO.getInvoiceOverheadsDTOList().forEach(overhedOrDeductDto -> {
			VendorBillDedDetailDTO dedDetailDTO = new VendorBillDedDetailDTO();
			/** to get Linked Account Head Id with respective Tax for Deduction */
			Long deductionHeadId = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
					.fetchSacHeadIdForReceiptDet(invoiceEntryDTO.getOrgId(),
							Long.valueOf(overhedOrDeductDto.getDescription()), MainetConstants.FlagA);
			dedDetailDTO.setBchId(deductionHeadId);
			dedDetailDTO.setBudgetCodeId(deductionHeadId);
			dedDetailDTO.setDeductionAmount(overhedOrDeductDto.getAmount());
			deductionDetList.add(dedDetailDTO);
		});
		approvalDTO.setDedDetListDto(deductionDetList);

		/**  sending locationId >> getting exception in initializeWorkflowForOtherModules() in rest-call  */
		long locationId = UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId();
		long fieldId = 0;
		if (UserSession.getCurrent().getLoggedLocId() != null) {
			final TbLocationMas locMas = locMasService.findById(locationId);			
			if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) 
				fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
		}
		if (fieldId == 0) {
			throw new NullPointerException("fieldId is not linked with Location Master for[locId="
					+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
					+ UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
		}
		approvalDTO.setFieldId(locationId);
		approvalDTO.setDepartmentId(departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.STORE));
		try {
			responseEntity = RestClient.callRestTemplateClient(approvalDTO, ServiceEndpoints.SALARY_POSTING);
			if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				String[] split = responseEntity.getBody().toString().split(":");
				logger.info("Vendor Bill Generated with Invoice No:" + " " + split[1]);
				this.setSuccessMessage(ApplicationSession.getInstance().getMessage("material.management.approve.sli"));
			}
		} catch (Exception exception) {
			throw new FrameworkException("error occured while bill posting to account module ", exception);
		}
	}

	public List<TbAcVendormaster> getVendors() {
		return vendors;
	}

	public void setVendors(List<TbAcVendormaster> vendors) {
		this.vendors = vendors;
	}

	public List<ItemMasterDTO> getItemNameList() {
		return itemNameList;
	}

	public void setItemNameList(List<ItemMasterDTO> itemNameList) {
		this.itemNameList = itemNameList;
	}

	public List<BinLocMasDto> getBinLocList() {
		return binLocList;
	}

	public void setBinLocList(List<BinLocMasDto> binLocList) {
		this.binLocList = binLocList;
	}

	public InvoiceEntryDTO getInvoiceEntryDTO() {
		return invoiceEntryDTO;
	}

	public void setInvoiceEntryDTO(InvoiceEntryDTO invoiceEntryDTO) {
		this.invoiceEntryDTO = invoiceEntryDTO;
	}

	public List<InvoiceEntryDTO> getInvoiceEntryDTOList() {
		return invoiceEntryDTOList;
	}

	public void setInvoiceEntryDTOList(List<InvoiceEntryDTO> invoiceEntryDTOList) {
		this.invoiceEntryDTOList = invoiceEntryDTOList;
	}

	public List<PurchaseOrderDto> getPurchaseOrderDtoList() {
		return purchaseOrderDtoList;
	}

	public void setPurchaseOrderDtoList(List<PurchaseOrderDto> purchaseOrderDtoList) {
		this.purchaseOrderDtoList = purchaseOrderDtoList;
	}

	public List<GoodsReceivedNotesDto> getGoodsReceivedNotesMultiselectList() {
		return goodsReceivedNotesMultiselectList;
	}

	public void setGoodsReceivedNotesMultiselectList(List<GoodsReceivedNotesDto> goodsReceivedNotesMultiselectList) {
		this.goodsReceivedNotesMultiselectList = goodsReceivedNotesMultiselectList;
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

	public Character getInvoiceSaveStatus() {
		return invoiceSaveStatus;
	}

	public void setInvoiceSaveStatus(Character invoiceSaveStatus) {
		this.invoiceSaveStatus = invoiceSaveStatus;
	}

	public String getRemoveOverheadIds() {
		return removeOverheadIds;
	}

	public void setRemoveOverheadIds(String removeOverheadIds) {
		this.removeOverheadIds = removeOverheadIds;
	}

	public long getLevelCheck() {
		return levelCheck;
	}

	public void setLevelCheck(long levelCheck) {
		this.levelCheck = levelCheck;
	}

	public List<DocumentDetailsVO> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentDetailsVO> documents) {
		this.documents = documents;
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

	public static Logger getLOGGER() {
		return LOGGER;
	}

	public static void setLOGGER(Logger lOGGER) {
		LOGGER = lOGGER;
	}

	public List<TaxDefinationDto> getListTaxDefinationDto() {
		return listTaxDefinationDto;
	}

	public void setListTaxDefinationDto(List<TaxDefinationDto> listTaxDefinationDto) {
		this.listTaxDefinationDto = listTaxDefinationDto;
	}

	public List<Object[]> getStoreIdNameList() {
		return storeIdNameList;
	}

	public void setStoreIdNameList(List<Object[]> storeIdNameList) {
		this.storeIdNameList = storeIdNameList;
	}

}
