package com.abm.mainet.materialmgmt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Path;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IDuplicateReceiptService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.materialmgmt.dao.ExpiredItemDao;
import com.abm.mainet.materialmgmt.domain.ExpiryItemsDetEntity;
import com.abm.mainet.materialmgmt.domain.ExpiryItemsEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionEntity;
import com.abm.mainet.materialmgmt.dto.ExpiryItemDetailsDto;
import com.abm.mainet.materialmgmt.dto.ExpiryItemsDto;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDto;
import com.abm.mainet.materialmgmt.mapper.ExpiryItemsMapper;
import com.abm.mainet.materialmgmt.repository.BinLocMasRepository;
import com.abm.mainet.materialmgmt.repository.ExpiryItemRepository;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;

import io.swagger.annotations.Api;


@WebService(endpointInterface = "com.abm.mainet.materialmgmt.service.ExpirtyItemService")
@Api(value = "/expiryDisposal")
@Path("/expiryDisposal")
@Service
public class ExpirtyItemServiceImpl implements ExpirtyItemService {

	@Autowired
	private ExpiryItemRepository expiryItemRepository;

	@Autowired
	private ExpiryItemsMapper expiryItemsMapper;

	@Autowired
	private ExpiredItemDao expiredItemDao;

	@Resource
	private ServiceMasterService serviceMasterService;

	@Autowired
	private CommonService commonService;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;

	@Autowired
	private ItemMasterService itemMasterService;

	@Autowired
	private BinLocMasRepository binLocMasRepository;

	@Autowired
	private IStoreMasterService storeMasterService;
	
	@Autowired
	private ServiceMasterService seviceMasterService;
	
	@Autowired
	private IReceiptEntryService  iReceiptEntryService;
	
	@Autowired
	private IDuplicateReceiptService iDuplicateReceiptService;	
	
	
	@Override
	public List<ExpiryItemsDto> findByAllGridSearchData(Long storeId, String movementNo, Date fromDate, Date toDate,
			Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExpiryItemsDto> findExpiryItemDetailsByFinId(Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ExpiryItemDetailsDto> getEpiryItem(Date fromDate, Date toDate, long orgid) {
		List<ExpiryItemDetailsDto> list = new ArrayList<ExpiryItemDetailsDto>();
		ExpiryItemDetailsDto exryItemDetDto = null;
		List<Object[]> expiryItemDetail = expiryItemRepository.getExpiryItemDataByDate(orgid, fromDate, toDate);
		for (final Object[] obj : expiryItemDetail) {
			exryItemDetDto = new ExpiryItemDetailsDto();
			exryItemDetDto.setStoreId(Long.valueOf(obj[2].toString()));
			exryItemDetDto.setBinLocation(Long.valueOf(obj[11].toString()));
			exryItemDetDto.setItemId(Long.valueOf(obj[3].toString()));
			exryItemDetDto.setQuantity(Double.valueOf(obj[3].toString()));
			list.add(exryItemDetDto);
		}
		return list;
	}

	@Override
	@Transactional
	public ExpiryItemsDto saveExpirtyItem(ExpiryItemsDto expiryItemsDto, CommonChallanDTO offline, 
			long levelCheck, WorkflowTaskAction workflowTaskAction, ApplicantDetailDTO applicantDetailDTO) {

		ExpiryItemsEntity expiryItemsEntity = expiryItemsMapper.mapExpiryItemsDtoToExpiryItemsEntity(expiryItemsDto);
		expiryItemRepository.save(expiryItemsEntity);
		if (0L == levelCheck && null == expiryItemsDto.getScrapNo()) {
			initializeWorkFlowForFreeService(expiryItemsDto, applicantDetailDTO);
		} else if (1L == levelCheck) {
			ApplicationContextProvider.getApplicationContext().getBean(ExpirtyItemService.class)
					.updateWorkFlowService(workflowTaskAction, applicantDetailDTO, expiryItemsDto);
		}		
		return expiryItemsMapper.mapExpiryItemsEntityToExpiryItemsDto(expiryItemsEntity);
	}
	
	
	@Transactional
	public void initializeWorkFlowForFreeService(ExpiryItemsDto requestDto, ApplicantDetailDTO applicantDto) {
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicationMetaData.setApplicationId(requestDto.getApplicationId()); // passed applId
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(requestDto.getOrgId());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}


	@Override
	@Transactional
	public String updateWorkFlowService(WorkflowTaskAction workflowTaskAction, ApplicantDetailDTO applicantDetailDTO,  ExpiryItemsDto expiryItemsDto) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		String processName = serviceMasterService.getProcessName(applicantDetailDTO.getServiceId(), expiryItemsDto.getOrgId());
		workflowProcessParameter.setProcessName(processName);
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return null;
	}

	
	@Override
	@Transactional
	public List<ExpiryItemsDto> expiryItemSearchForSummaryData(Long storeNameId, String movementNo, Date fromDate,
			Date toDate, long orgid) {
		List<ExpiryItemsDto> expiryItemsDtoList = new ArrayList<>();
		List<ExpiryItemsEntity> expiryItemsEntitieList = expiredItemDao.searchExpiredSummaryData(storeNameId, movementNo, fromDate, toDate,	orgid);
		
		expiryItemsEntitieList.forEach(expiryItemsEntity->{
			ExpiryItemsDto expiryItemsDto = new ExpiryItemsDto();
			BeanUtils.copyProperties(expiryItemsEntity, expiryItemsDto);
			expiryItemsDto.setStoreName(storeMasterService.getStorenameByStoreId(expiryItemsDto.getStoreId()));
			
			Double totalDisposedQuantity = 0.0;
			for( ExpiryItemsDetEntity expiryItemsDetEntity : expiryItemsEntity.getExpiryItemsDetEntity()) 
				totalDisposedQuantity += expiryItemsDetEntity.getQuantity();
			
			if(MainetConstants.FlagT.equalsIgnoreCase(expiryItemsDto.getStatus())) {
				if(1L <= expiryItemRepository.checkForContractAggreement(expiryItemsEntity.getExpiryId(), expiryItemsEntity.getOrgId()))
					expiryItemsDto.setIsContractDone(MainetConstants.FlagY);				
			}
			
			expiryItemsDto.setTotalDisposedQuantity(totalDisposedQuantity);
			expiryItemsDtoList.add(expiryItemsDto);
		});				
		return expiryItemsDtoList;
	}


	@Override
	@Transactional
	public ExpiryItemsDto getExpiryItemDataById(Long expiryId, Long orgId) {
		ExpiryItemsEntity expiryItemsEntity = expiryItemRepository.findOne(expiryId);
		ExpiryItemsDto expiryItemsDto = new ExpiryItemsDto();
		BeanUtils.copyProperties(expiryItemsEntity, expiryItemsDto);
		expiryItemsEntity.getExpiryItemsDetEntity().forEach(expiryItemsDetEntity -> {
			ExpiryItemDetailsDto expiryItemsDetdto = new ExpiryItemDetailsDto();
			BeanUtils.copyProperties(expiryItemsDetEntity, expiryItemsDetdto);
			
			expiryItemsDetdto.setBinLocName(binLocMasRepository.getDefNameByDefIdAndOrgId(expiryItemsDetdto.getBinLocation(), orgId));
			ItemMasterDTO tbMGItemMaster = new ItemMasterDTO();
			tbMGItemMaster.setItemId(expiryItemsDetdto.getItemId());
			tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
			expiryItemsDetdto.setItemName(tbMGItemMaster.getName() != null ? tbMGItemMaster.getName() : "");
			expiryItemsDetdto.setUomName(CommonMasterUtility.getNonHierarchicalLookUpObject(tbMGItemMaster.getUom(), UserSession.getCurrent().getOrganisation())
					.getLookUpDesc());

			expiryItemsDto.getExpiryItemDetailsDtoList().add(expiryItemsDetdto);
		});
		return expiryItemsDto;
	}

	
	@Override
	@Transactional
	public ExpiryItemsDto getExpiryItemDataByApplicationNo(Long appNo, Long orgId) {
		ExpiryItemsEntity expiryItemsEntity = expiryItemRepository.findByApplicationIdAndOrgIdOrderByExpiryIdDesc(appNo, orgId);
		ExpiryItemsDto expiryItemsDto = new ExpiryItemsDto();
		BeanUtils.copyProperties(expiryItemsEntity, expiryItemsDto);
		expiryItemsEntity.getExpiryItemsDetEntity().forEach(expiryItemsDetEntity -> {
			ExpiryItemDetailsDto expiryItemsDetdto = new ExpiryItemDetailsDto();
			BeanUtils.copyProperties(expiryItemsDetEntity, expiryItemsDetdto);
			
			expiryItemsDetdto.setBinLocName(binLocMasRepository.getDefNameByDefIdAndOrgId(expiryItemsDetdto.getBinLocation(), orgId));
			ItemMasterDTO tbMGItemMaster = new ItemMasterDTO();
			tbMGItemMaster.setItemId(expiryItemsDetdto.getItemId());
			tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
			expiryItemsDetdto.setItemName(tbMGItemMaster.getName() != null ? tbMGItemMaster.getName() : "");
			expiryItemsDetdto.setUomName(CommonMasterUtility.getNonHierarchicalLookUpObject(tbMGItemMaster.getUom(), UserSession.getCurrent().getOrganisation())
					.getLookUpDesc());

			expiryItemsDto.getExpiryItemDetailsDtoList().add(expiryItemsDetdto);
		});
		return expiryItemsDto;
	}
		
	
	@Override
	@Transactional(readOnly = true)
	public ChallanReceiptPrintDTO getDuplicateReceiptDetail(Long expiryId, Long orgId) {
		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		TbServiceReceiptMasEntity receiptMasterEntity = new TbServiceReceiptMasEntity();
		ChallanReceiptPrintDTO receiptDto = new ChallanReceiptPrintDTO();

		ServiceMaster serviceMas = seviceMasterService.getServiceByShortName("DSA", orgId);
		Long deptId = serviceMas.getTbDepartment().getDpDeptid();

		receiptMasterEntity = iReceiptEntryService.getMaxReceiptIdByAdditinalRefNoAndDeptId(expiryId.toString(), deptId);
		if (receiptMasterEntity == null) {
			return null;
		}

		TbServiceReceiptMasBean receiptMasBean = iReceiptEntryService.findReceiptById(receiptMasterEntity.getRmRcptid(), orgId);
		receiptDto = iDuplicateReceiptService.getRevenueReceiptDetails(receiptMasBean.getRmRcptid(), receiptMasBean.getRmRcptno(),
				receiptMasBean.getAdditionalRefNo(), orgId, 0);
		if (receiptDto != null) {
			receiptDto.setPaymentMode(CommonMasterUtility.getNonHierarchicalLookUpObject(receiptMasBean.getReceiptModeDetailList().getCpdFeemode(),
					organisation).getLookUpDesc());
		}
		receiptDto.setReferenceNo(receiptDto.getOld_propNo_connNo_V());
		return receiptDto;
	}
		

	@Override
	@Transactional
	public void updateDispoalStatus(Long orgid, Long referenceId, String flagr) {
		expiryItemRepository.updateDispoalStatus(orgid, referenceId, flagr);
	}

	
	/**
	 * to get Expired Item Data from Item Opening Balance
	 */
	@Override
	@Transactional
	public ExpiryItemsDto getExpiredItemsDataforExpiry(Long storeId, Date expiryDate, Long orgId) {

		ExpiryItemsDto expiryItemsDto = new ExpiryItemsDto();
		List<ExpiryItemDetailsDto> expiryItemDetailsDtos = new ArrayList<>();
		List<Object[]> entitiesList = expiryItemRepository.getDataByStoreAndExpDate(storeId, expiryDate, orgId);
		ExpiryItemDetailsDto itemDetailsDto;
		for (Object[] obj : entitiesList) {
			if (Double.valueOf(obj[3].toString()) > 0.0 ) {
				itemDetailsDto = new ExpiryItemDetailsDto();
				if (obj[0] != null)
					itemDetailsDto.setBinLocation(Long.valueOf(obj[0].toString()));
					itemDetailsDto.setBinLocName(binLocMasRepository.getDefNameByDefIdAndOrgId(itemDetailsDto.getBinLocation(), orgId));
				if (obj[1] != null) {
					itemDetailsDto.setItemId(Long.valueOf(obj[1].toString()));
					ItemMasterDTO tbMGItemMaster = new ItemMasterDTO();
					tbMGItemMaster.setItemId(Long.valueOf(obj[1].toString()));
					tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
					itemDetailsDto.setItemName(tbMGItemMaster.getName() != null ? tbMGItemMaster.getName() : "");
					itemDetailsDto.setUomName(CommonMasterUtility.getNonHierarchicalLookUpObject(tbMGItemMaster.getUom(),
							UserSession.getCurrent().getOrganisation()).getLookUpDesc());
				}
				if (obj[2] != null)
					itemDetailsDto.setItemNo(obj[2].toString());
				if (obj[3] != null)
					itemDetailsDto.setQuantity(Double.valueOf(obj[3].toString()));
				expiryItemDetailsDtos.add(itemDetailsDto);
			}
		}
		expiryItemsDto.setExpiryItemDetailsDtoList(expiryItemDetailsDtos);
		return expiryItemsDto;
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public List<ExpiryItemsDto> fecthExpiryDisposalListForTender(Long deptId, Long orgId) {
		// Status == A in Query is Approved
		List<ExpiryItemsEntity> entitiesList = expiryItemRepository.findByDepartmentAndOrgIdAndStatusOrderByExpiryId(deptId,orgId,MainetConstants.FlagA);
		final List<ExpiryItemsDto> expiryItemsDtoList = new ArrayList<>();
		entitiesList.forEach(expiryItemsEntity->{
			ExpiryItemsDto expiryItemsDto = new ExpiryItemsDto();
			BeanUtils.copyProperties(expiryItemsEntity, expiryItemsDto);
			expiryItemsDtoList.add(expiryItemsDto);
		});
		return expiryItemsDtoList;		
	}
	

	@Override
	@Transactional
	public void updateExpiryDisposalStatus(ExpiryItemsDto expiryItemsDto) {
		expiryItemRepository.updateStatusPurchaseReq(expiryItemsDto.getExpiryIds(), expiryItemsDto.getStatus(), expiryItemsDto.getUpdatedBy());
	}
	
	
	@Override
    @Transactional(readOnly = true)
	public Map<String,String> fetchExpiryDisposalCodes(ExpiryItemsDto expiryItemsDto) {
		List<ExpiryItemsEntity> expiryItemsEntitieList = expiryItemRepository.findByOrgIdAndExpiryIdIn(expiryItemsDto.getOrgId(), expiryItemsDto.getExpiryIds());
		Map<String,String> expCodes=new HashMap<>();
		for (final ExpiryItemsEntity expiryItemsEntity : expiryItemsEntitieList) {
			expCodes.put(expiryItemsEntity.getExpiryId()+"", expiryItemsEntity.getScrapNo());
		}
		return expCodes;
	}
	
	
}
