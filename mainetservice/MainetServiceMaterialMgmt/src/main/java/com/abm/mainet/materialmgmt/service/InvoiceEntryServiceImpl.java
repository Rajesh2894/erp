package com.abm.mainet.materialmgmt.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.materialmgmt.dao.IInvoiceEntryDao;
import com.abm.mainet.materialmgmt.domain.InvoiceEntryDetailEntity;
import com.abm.mainet.materialmgmt.domain.InvoiceEntryEntity;
import com.abm.mainet.materialmgmt.domain.InvoiceEntryGRNEntity;
import com.abm.mainet.materialmgmt.domain.InvoiceEntryOverheadsEntity;
import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.InvoiceEntryDTO;
import com.abm.mainet.materialmgmt.dto.InvoiceEntryDetailDTO;
import com.abm.mainet.materialmgmt.dto.InvoiceEntryGRNDTO;
import com.abm.mainet.materialmgmt.dto.InvoiceEntryOverheadsDTO;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderDto;
import com.abm.mainet.materialmgmt.repository.GoodsReceivedNotesRepository;
import com.abm.mainet.materialmgmt.repository.IInvoiceEntryRepository;
import com.abm.mainet.materialmgmt.repository.PurchaseOrderRepository;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Service
@Transactional
public class InvoiceEntryServiceImpl implements IInvoiceEntryService {

	@Autowired
	private IInvoiceEntryRepository invoiceEntryRepository;

	@Autowired
	private TbAcVendormasterService tbAcVendormasterService;

	@Resource
	private ItemMasterService itemMasterService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private IInvoiceEntryDao iInvoiceEntryDao;

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;

	@Autowired
	private IStoreMasterService iStoreMasterService;

	@Autowired
	private GoodsReceivedNotesRepository goodsReceivedNotesRepository;

	@Override
	
	public void saveInvoiceEntryData(InvoiceEntryDTO invoiceEntryDTO) {
		InvoiceEntryEntity invoiceEntryEntity = new InvoiceEntryEntity();
		BeanUtils.copyProperties(invoiceEntryDTO, invoiceEntryEntity);

		invoiceEntryDTO.getInvoiceEntryGRNDTOList().forEach(grnDetailDto -> {
			InvoiceEntryGRNEntity invoiceGRNEntity = new InvoiceEntryGRNEntity();
			BeanUtils.copyProperties(grnDetailDto, invoiceGRNEntity);
			invoiceGRNEntity.setInvoiceEntryEntity(invoiceEntryEntity);
			invoiceEntryEntity.getInvoiceGRNEntityList().add(invoiceGRNEntity);
		});
		invoiceEntryDTO.getInvoiceEntryDetailDTOList().forEach(invoiceDetailDto -> {
			InvoiceEntryDetailEntity invoiceDetailEntity = new InvoiceEntryDetailEntity();
			BeanUtils.copyProperties(invoiceDetailDto, invoiceDetailEntity);
			invoiceDetailEntity.setInvoiceEntryEntity(invoiceEntryEntity);
			invoiceEntryEntity.getInvoiceDetailEntityList().add(invoiceDetailEntity);
		});
		invoiceEntryDTO.getInvoiceOverheadsDTOList().forEach(overheadDto -> {
			InvoiceEntryOverheadsEntity invoiceOverheadsEntity = new InvoiceEntryOverheadsEntity();
			BeanUtils.copyProperties(overheadDto, invoiceOverheadsEntity);
			invoiceOverheadsEntity.setInvoiceEntryEntity(invoiceEntryEntity);
			invoiceEntryEntity.getInvoiceOverheadsEntityList().add(invoiceOverheadsEntity);
		});
		invoiceEntryRepository.save(invoiceEntryEntity);
	}


	@Override
	public void initializeWorkFlowForFreeService(InvoiceEntryDTO invoiceEntryDTO, ServiceMaster service) {
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(invoiceEntryDTO.getUserName());
		applicantDto.setServiceId(service.getSmServiceId());
		applicantDto.setDepartmentId(service.getTbDepartment().getDpDeptid());
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(invoiceEntryDTO.getUserId());
		applicationMetaData.setReferenceId(invoiceEntryDTO.getInvoiceNo());
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(invoiceEntryDTO.getOrgId());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Override
	public String updateWorkFlowService(WorkflowTaskAction workflowTaskAction, ServiceMaster service) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(service.getSmServiceName());
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return null;
	}

	@Override
	public void updateWorkFlowStatus(String invoiceNo, Long orgId, Character invoiceStatus, String wfFlag) {
		invoiceEntryRepository.updateWorkFlowStatus(invoiceNo, orgId, invoiceStatus, wfFlag);
	}
	
	@Override
	public void updateInvoicePaymentDet(String invoiceNo,  BigDecimal invoiceAmt, Long orgId) {
		invoiceEntryRepository.updateInvoicePaymentDet(invoiceNo, invoiceAmt, orgId,  MainetConstants.CommonConstants.CHAR_Y);
	}
	

	@Override
	public List<PurchaseOrderDto> getPONumbersByStore(Long storeId, Long orgId) {
		// storeID, orgId, decision == 'Y' == Accepted item, status == 'Y' == Active item
		List<Object[]> entitiesList = invoiceEntryRepository.fetchPOListByStore(storeId, orgId,
				MainetConstants.CommonConstants.CHAR_Y, MainetConstants.CommonConstants.CHAR_Y);
		List<PurchaseOrderDto> purchaseOrderDtoList = new ArrayList<>();
		PurchaseOrderDto purchaseOrderDto;
		for (Object[] obj : entitiesList) {
			purchaseOrderDto = new PurchaseOrderDto();
			purchaseOrderDto.setPoId(Long.valueOf(obj[0].toString()));
			purchaseOrderDto.setPoNo(obj[1].toString());
			purchaseOrderDtoList.add(purchaseOrderDto);
		}
		return purchaseOrderDtoList;
	}

	@Override
	public List<PurchaseOrderDto> getPONumbersForInvoiceSummary(Long orgId) {
		List<Object[]> entitiesList = invoiceEntryRepository.fetchPOListFromInvoice(orgId);
		List<PurchaseOrderDto> purchaseOrderDtoList = new ArrayList<>();
		PurchaseOrderDto purchaseOrderDto;
		for (Object[] obj : entitiesList) {
			purchaseOrderDto = new PurchaseOrderDto();
			purchaseOrderDto.setPoId(Long.valueOf(obj[0].toString()));
			purchaseOrderDto.setPoNo(obj[1].toString());
			purchaseOrderDtoList.add(purchaseOrderDto);
		}
		return purchaseOrderDtoList;
	}

	@Override
	public List<GoodsReceivedNotesDto> getGRNNumberListByPoId(Long storeId, Long poId, long orgId) {
		// storeID, poId, orgId, decision == 'Y' == Accepted item, status == 'Y' ==
		// Active item
		List<Object[]> entitiesList = invoiceEntryRepository.fetchGRNListByStoreAndPoId(storeId, poId, orgId,
				MainetConstants.FlagD, MainetConstants.CommonConstants.CHAR_Y, MainetConstants.CommonConstants.CHAR_Y);
		List<GoodsReceivedNotesDto> goodsReceivedNotesDtoList = new ArrayList<>();
		GoodsReceivedNotesDto goodsNotesDto;
		for (Object[] obj : entitiesList) {
			goodsNotesDto = new GoodsReceivedNotesDto();
			goodsNotesDto.setGrnid(Long.valueOf(obj[0].toString()));
			goodsNotesDto.setGrnno(obj[1].toString());
			goodsNotesDto.setVendorId(Long.valueOf(obj[2].toString()));
			goodsReceivedNotesDtoList.add(goodsNotesDto);
		}
		goodsReceivedNotesDtoList.get(0).setVendorName(
				tbAcVendormasterService.getVendorNameById(goodsReceivedNotesDtoList.get(0).getVendorId(), orgId));
		return goodsReceivedNotesDtoList;
	}

	@Override
	public InvoiceEntryDTO fetchInvoiceGRNAndItemDataByGRNId(InvoiceEntryDTO invoiceEntryDTO, Long orgId) {
		// to get GoodsReceivedNotesEntity Data >> Note: Status != D in query >> GRN Nos. after Inspection and Store Entry
		List<Object[]> grnEntityObjectList = invoiceEntryRepository.getGRNDataByGRNIds(invoiceEntryDTO.getGrnIdList(),
				orgId, MainetConstants.FlagD);
		InvoiceEntryGRNDTO invoiceEntryGRNDTO;
		for (Object[] grnEntityObject : grnEntityObjectList) {
			invoiceEntryGRNDTO = new InvoiceEntryGRNDTO();
			invoiceEntryGRNDTO.setGrnId(Long.valueOf(grnEntityObject[0].toString()));
			invoiceEntryGRNDTO.setGrnNumber(grnEntityObject[1].toString());
			invoiceEntryGRNDTO.setGrnDate(Utility.stringToDate(LocalDate.parse(grnEntityObject[2].toString())
					.format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
			invoiceEntryDTO.getInvoiceEntryGRNDTOList().add(invoiceEntryGRNDTO);
		}

		// to get Item Details Data >> Note: Decision == Y and Status == Y in query
		List<Object[]> goodsReceivedNotesItemEntity = invoiceEntryRepository.getItemDataByGRNIds(
				invoiceEntryDTO.getGrnIdList(), orgId, MainetConstants.CommonConstants.CHAR_Y,
				MainetConstants.CommonConstants.CHAR_Y);
		InvoiceEntryDetailDTO invoiceEntryDetailDTO;
		for (Object[] grnItemEntity : goodsReceivedNotesItemEntity) {
			invoiceEntryDetailDTO = new InvoiceEntryDetailDTO();
			invoiceEntryDetailDTO.setGrnId(Long.valueOf(grnItemEntity[0].toString()));
			invoiceEntryDetailDTO.setGrnItemEntryId(Long.valueOf(grnItemEntity[1].toString()));
			invoiceEntryDetailDTO.setItemId(Long.valueOf(grnItemEntity[2].toString()));
			ItemMasterDTO tbMGItemMaster = new ItemMasterDTO();
			tbMGItemMaster.setItemId(invoiceEntryDetailDTO.getItemId());
			tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
			invoiceEntryDetailDTO.setItemName(tbMGItemMaster.getName() != null ? tbMGItemMaster.getName() : MainetConstants.BLANK);
			invoiceEntryDetailDTO.setUomName(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
					tbMGItemMaster.getUom(), orgId, MainetMMConstants.MmItemMaster.UOM_PREFIX).getLookUpDesc());

			invoiceEntryDetailDTO.setQuantity(new BigDecimal(grnItemEntity[3].toString()).setScale(2, RoundingMode.HALF_UP));
			invoiceEntryDetailDTO.setUnitPrice(new BigDecimal(grnItemEntity[4].toString()).setScale(2, RoundingMode.HALF_UP));
			invoiceEntryDetailDTO.setTaxableValue(invoiceEntryDetailDTO.getQuantity()
					.multiply(invoiceEntryDetailDTO.getUnitPrice()).setScale(2, RoundingMode.HALF_UP));
			invoiceEntryDetailDTO.setTax((invoiceEntryDetailDTO.getTaxableValue())
					.multiply(new BigDecimal(grnItemEntity[5].toString()).setScale(2, RoundingMode.HALF_UP))
					.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
			invoiceEntryDetailDTO.setTotalAmt(invoiceEntryDetailDTO.getTaxableValue()
					.add(invoiceEntryDetailDTO.getTax()).setScale(2, RoundingMode.HALF_UP));

			invoiceEntryDTO.getInvoiceEntryDetailDTOList().add(invoiceEntryDetailDTO);
		}
		return invoiceEntryDTO;
	}

	@Override
	public List<InvoiceEntryDTO> fetchInvoiceEntrySummaryData(Long invoiceId, Long poId, Date fromDate, Date toDate,
			Long storeId, Long vendorId, Long orgId) {
		List<Object[]> invoiceEntryList = iInvoiceEntryDao.searchInvoiceEntrySummaryData(invoiceId, poId, fromDate,
				toDate, storeId, vendorId, orgId);
		List<InvoiceEntryDTO> invoiceEntryDTOList = new ArrayList<>();
		InvoiceEntryDTO invoiceEntryDTO;
		String[] invoiceDate;
		for (Object[] object : invoiceEntryList) {
			invoiceEntryDTO = new InvoiceEntryDTO();
			invoiceEntryDTO.setInvoiceId(Long.valueOf(object[0].toString()));
			invoiceEntryDTO.setInvoiceNo(object[1].toString());
			invoiceDate = object[2].toString().split(MainetConstants.WHITE_SPACE);
			invoiceEntryDTO.setInvoiceDate(Utility.stringToDate(
					LocalDate.parse(invoiceDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));
			invoiceEntryDTO.setInvoiceAmt(new BigDecimal(object[3].toString()).setScale(2, RoundingMode.HALF_UP));
			invoiceEntryDTO.setPoId(Long.valueOf(object[4].toString()));
			invoiceEntryDTO.setPoNumber(purchaseOrderRepository.getAllPurchaseOrderNoById(orgId, invoiceEntryDTO.getPoId()));
			invoiceEntryDTO.setStoreId(Long.valueOf(object[5].toString()));
			invoiceEntryDTO.setStoreName(iStoreMasterService.getStorenameByStoreId(invoiceEntryDTO.getStoreId()));
			invoiceEntryDTO.setVendorId(Long.valueOf(object[6].toString()));
			invoiceEntryDTO.setVendorName(tbAcVendormasterService.getVendorNameById(invoiceEntryDTO.getVendorId(), orgId));
			invoiceEntryDTO.setInvoiceStatus(object[7].toString().charAt(0));
			invoiceEntryDTOList.add(invoiceEntryDTO);
		}
		return invoiceEntryDTOList;
	}

	
	@Override
	public InvoiceEntryDTO getInvoiceApprovalData(String invoiceNo, Long orgId) {
		InvoiceEntryEntity invoiceEntryEntity = invoiceEntryRepository.findByInvoiceNoAndOrgId(invoiceNo, orgId);
		return getInvoiceEntityToInvoiceDto(invoiceEntryEntity, orgId);
	}

	
	@Override
	public InvoiceEntryDTO invoiceEntryEditAndView(Long invoiceId, Long orgId) {
		InvoiceEntryEntity invoiceEntryEntity = invoiceEntryRepository.findByInvoiceIdAndOrgId(invoiceId, orgId);
		return getInvoiceEntityToInvoiceDto(invoiceEntryEntity, orgId);
	}
	

	@Override
	public InvoiceEntryDTO getInvoiceEntityToInvoiceDto(InvoiceEntryEntity invoiceEntryEntity, Long orgId) {
		InvoiceEntryDTO invoiceEntryDTO = new InvoiceEntryDTO();
		BeanUtils.copyProperties(invoiceEntryEntity, invoiceEntryDTO);
		invoiceEntryDTO.setStoreName(iStoreMasterService.getStorenameByStoreId(invoiceEntryDTO.getStoreId()));
		invoiceEntryDTO.setVendorName(tbAcVendormasterService.getVendorNameById(invoiceEntryDTO.getVendorId(), orgId));
		invoiceEntryDTO.setPoNumber(purchaseOrderRepository.getAllPurchaseOrderNoById(orgId, invoiceEntryDTO.getPoId()));
		
		invoiceEntryEntity.getInvoiceGRNEntityList().forEach(grnInvoiceEntity -> {
			InvoiceEntryGRNDTO invoiceEntryGRNDTO = new InvoiceEntryGRNDTO();
			BeanUtils.copyProperties(grnInvoiceEntity, invoiceEntryGRNDTO);
			invoiceEntryGRNDTO.setGrnNumber(
					goodsReceivedNotesRepository.findGRNNumberByOrgIdAndGRNId(orgId, invoiceEntryGRNDTO.getGrnId()));
			invoiceEntryDTO.getInvoiceEntryGRNDTOList().add(invoiceEntryGRNDTO);
		});
		invoiceEntryEntity.getInvoiceDetailEntityList().forEach(invoiceDetailEntity -> {
			InvoiceEntryDetailDTO invoiceEntryDetailDTO = new InvoiceEntryDetailDTO();
			BeanUtils.copyProperties(invoiceDetailEntity, invoiceEntryDetailDTO);
			ItemMasterDTO tbMGItemMaster = new ItemMasterDTO();
			tbMGItemMaster.setItemId(invoiceEntryDetailDTO.getItemId());
			tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
			invoiceEntryDetailDTO.setItemName(tbMGItemMaster.getName() != null ? tbMGItemMaster.getName() : MainetConstants.BLANK);
			invoiceEntryDetailDTO.setUomName(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
					tbMGItemMaster.getUom(), orgId, MainetMMConstants.MmItemMaster.UOM_PREFIX).getLookUpDesc());
			invoiceEntryDetailDTO.setTaxableValue(invoiceEntryDetailDTO.getQuantity()
					.multiply(invoiceEntryDetailDTO.getUnitPrice()).setScale(2, RoundingMode.HALF_UP));
			invoiceEntryDTO.getInvoiceEntryDetailDTOList().add(invoiceEntryDetailDTO);
		});
		invoiceEntryEntity.getInvoiceOverheadsEntityList().forEach(invoiceOverheadEntity -> {
			InvoiceEntryOverheadsDTO invoiceEntryOverheadsDTO = new InvoiceEntryOverheadsDTO();
			BeanUtils.copyProperties(invoiceOverheadEntity, invoiceEntryOverheadsDTO);
			invoiceEntryDTO.getInvoiceOverheadsDTOList().add(invoiceEntryOverheadsDTO);
		});
		return invoiceEntryDTO;
	}


}
