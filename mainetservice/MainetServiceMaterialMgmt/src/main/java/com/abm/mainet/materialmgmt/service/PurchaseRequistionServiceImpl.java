package com.abm.mainet.materialmgmt.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.materialmgmt.dao.PurchaseRequistionDao;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionDetEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionYearDetEntity;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDetDto;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDto;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionYearDetDto;
import com.abm.mainet.materialmgmt.mapper.PurchaseRequisitionMapper;
import com.abm.mainet.materialmgmt.repository.ItemMasterRepository;
import com.abm.mainet.materialmgmt.repository.PurchaseRequistionRepository;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;
import io.swagger.annotations.Api;


@WebService(endpointInterface = "com.abm.mainet.materialmgmt.service.IPurchaseRequistionService")
@Api(value = "/PurchaseReq")
@Path("/PurchaseReq")
@Service
public class PurchaseRequistionServiceImpl implements IPurchaseRequistionService {

	@Autowired
	private ItemMasterRepository itemMasterRepository;

	@Autowired
	private PurchaseRequistionRepository purchaseRequistionRepository;

	@Autowired
	private PurchaseRequisitionMapper purchaseRequisitionMapper;

	@Autowired
	private PurchaseRequistionDao purchaseRequistionDao;
	
    @Resource
    private ServiceMasterService serviceMasterService;
    
    @Autowired
	private ItemMasterService itemMasterService;
    
    @Autowired
    private CommonService commonService;
    
    @Resource
    private IServiceMasterDAO iServiceMasterDAO;
    
    private static final Logger LOGGER = Logger.getLogger(PurchaseRequistionServiceImpl.class);
   

	@Override
	@Transactional
	public void purchaseReqSave(PurchaseRequistionDto purchaseRequistionDto, Long levelCheck, List<Long> removeDetailsIds,
					List<Long> removeYearIds, WorkflowTaskAction workflowTaskAction) {
		PurchaseRequistionEntity purchaseRequistionEntity = purchaseRequisitionMapper.mapPurchaseRequistionDtoToPurchaseRequistionEntity(purchaseRequistionDto,removeDetailsIds);
		createNewFinancialYearsDetails(purchaseRequistionDto, purchaseRequistionEntity,removeYearIds);
		purchaseRequistionRepository.save(purchaseRequistionEntity);		
		if (!removeYearIds.isEmpty())
			purchaseRequistionRepository.updateStatusForYears(purchaseRequistionEntity.getUpdatedBy(),removeYearIds);
		if (!removeDetailsIds.isEmpty())
			purchaseRequistionRepository.updateStatusForDetails(purchaseRequistionEntity.getUpdatedBy(),removeDetailsIds);
		
		if (0L == levelCheck && MainetConstants.FlagP.equalsIgnoreCase(purchaseRequistionDto.getStatus()))
			initializeWorkFlowForFreeService(purchaseRequistionDto);
		else if (1L == levelCheck)
			updateWorkFlowPurchaseService(workflowTaskAction);		
	}
	
	// set data for work definition financial year related details if entered
	private void createNewFinancialYearsDetails(PurchaseRequistionDto purchaseRequistionDto, PurchaseRequistionEntity purchaseRequistionEntity, List<Long> removeYearIds) {
		if (purchaseRequistionDto.getYearDto() != null && !purchaseRequistionDto.getYearDto().isEmpty()) {
			List<PurchaseRequistionYearDetEntity> defYearEntityList = new ArrayList<>();
			purchaseRequistionDto.getYearDto().forEach(defYear -> {
				if (!removeYearIds.contains(defYear.getYearId())) {
					if (defYear.getFaYearId() != null || (defYear.getFinanceCodeDesc() != null && !defYear.getFinanceCodeDesc().equals(StringUtils.EMPTY))
							|| defYear.getYearPercntWork() != null || (defYear.getYeDocRefNo() != null && !defYear.getYeDocRefNo().equals(StringUtils.EMPTY))
							|| defYear.getYeBugAmount() != null || defYear.getSacHeadId() != null) {
						PurchaseRequistionYearDetEntity defYearEntity = new PurchaseRequistionYearDetEntity();
						BeanUtils.copyProperties(defYear, defYearEntity);
						setCreateYearCommonDetails(purchaseRequistionDto, defYearEntity);
						defYearEntity.setPurchaseRequistionEntity(purchaseRequistionEntity);
						defYearEntityList.add(defYearEntity);
					}
				}
			});

			purchaseRequistionEntity.setPurchaseRequistionYearDetEntity(defYearEntityList);
		}
	}
		
	private void setCreateYearCommonDetails(PurchaseRequistionDto purchaseRequistionDto, PurchaseRequistionYearDetEntity defYearEntity) {
		defYearEntity.setCreatedBy(purchaseRequistionDto.getUpdatedBy());
		defYearEntity.setCreatedDate(new Date());
		defYearEntity.setLgIpMac(purchaseRequistionDto.getLgIpMac());
		defYearEntity.setYeActive(MainetConstants.Common_Constant.YES);
		defYearEntity.setOrgId(purchaseRequistionDto.getOrgId());
	}

	
	@Transactional
	private void initializeWorkFlowForFreeService(PurchaseRequistionDto purchaseRequistionDto) {
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode("PRA", purchaseRequistionDto.getOrgId());
		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(purchaseRequistionDto.getUserName());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(serviceMas.getTbDepartment().getDpDeptid());
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(purchaseRequistionDto.getUserId());
		applicationMetaData.setReferenceId(purchaseRequistionDto.getPrNo());
		applicationMetaData.setIsCheckListApplicable(false);
		applicationMetaData.setOrgId(purchaseRequistionDto.getOrgId());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}	

	@Override
	@Transactional
	public void updateWorkFlowPurchaseService(WorkflowTaskAction workflowTaskAction) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
	    workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
	    workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
	    try {
	        ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
	    } catch (Exception exception) {
	        throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
	    }
	}

	@Override
	@Transactional
	public void purchaseReqFordelete(Long prid, Long orgId) {
		purchaseRequistionRepository.delete(prid);
	}

	@Override
	@Transactional
	public List<PurchaseRequistionDto> purchaseReqSearchForSummaryData(Long storeid, String prno,
			Date fromDate, Date toDate, Long orgId) {
		List<Object[]> requisionEntities = purchaseRequistionDao.searchPurchaseRequisitionDataByAll(storeid, prno,
				fromDate, toDate, orgId);
		final List<PurchaseRequistionDto> requistionDtoList = new ArrayList<>();
		PurchaseRequistionDto requistionDto;
		String[] requestDate;
		for(Object[] entity : requisionEntities) {
			requistionDto = new PurchaseRequistionDto();
			requistionDto.setPrId(Long.parseLong(entity[0].toString()));
			requistionDto.setPrNo(entity[1].toString());
			requestDate = entity[2].toString().split(MainetConstants.WHITE_SPACE);
			requistionDto.setPrDate(Utility.stringToDate(
					LocalDate.parse(requestDate[0]).format(DateTimeFormatter.ofPattern(MainetConstants.DATE_FORMAT))));			
			requistionDto.setStoreId(Long.parseLong(entity[3].toString()));
			requistionDto.setStatus(entity[4].toString());			
			requistionDtoList.add(requistionDto);
		}
		return requistionDtoList;
	}

	@Override
	@Transactional(readOnly = true)
	public PurchaseRequistionDto purchaseReqSearchForEditAndView(Long prid, Long orgId) {
		 PurchaseRequistionEntity purchaseRequistionEntity= purchaseRequistionRepository.findOne(prid);
		 return mapRequisitionEntutyToRequisitionDTO(purchaseRequistionEntity, orgId);
	}

	@Override
	@Transactional
	public PurchaseRequistionDto getPurchaseReuisitonByPONumber(String prNo, Long orgId) {
		 PurchaseRequistionEntity purchaseRequistionEntity= purchaseRequistionRepository.findByPrNoAndOrgId(prNo, orgId);
		 return mapRequisitionEntutyToRequisitionDTO(purchaseRequistionEntity, orgId);
	}
	
	private PurchaseRequistionDto mapRequisitionEntutyToRequisitionDTO(PurchaseRequistionEntity purchaseRequistionEntity, Long orgId) {
		 PurchaseRequistionDto purchaseRequistionDto = new PurchaseRequistionDto();
		 BeanUtils.copyProperties(purchaseRequistionEntity, purchaseRequistionDto);
		 purchaseRequistionEntity.getPurchaseRequistionDetEntity().forEach(requistionDetEntity->{
				PurchaseRequistionDetDto requistionDetDto = new PurchaseRequistionDetDto();
				BeanUtils.copyProperties(requistionDetEntity, requistionDetDto);
				Object[] itemObj = (Object[]) itemMasterService.getItemDetailObjectByItemId(requistionDetDto.getItemId())[0];
				requistionDetDto.setItemName(itemObj[1].toString());
				requistionDetDto.setUonName(CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
						Long.valueOf(itemObj[2].toString()), orgId, "UOM").getLookUpDesc());
				purchaseRequistionDto.getPurchaseRequistionDetDtoList().add(requistionDetDto);
		 });
		 purchaseRequistionEntity.getPurchaseRequistionYearDetEntity().forEach(requistionYearDetEntity->{
			 PurchaseRequistionYearDetDto requistionYearDetDto = new PurchaseRequistionYearDetDto();
			 BeanUtils.copyProperties(requistionYearDetEntity, requistionYearDetDto);
			 purchaseRequistionDto.getYearDto().add(requistionYearDetDto);
		 });
		 return purchaseRequistionDto;
	}
	
	

	@Override
	@Transactional
	public Long getUomByUsingItemCode(Long orgId, Long itemId) {
		return itemMasterRepository.getUomByitemCode(orgId, itemId);
	}

	@Override
    @Transactional(readOnly = true)
	public List<PurchaseRequistionDto> fetchPurchaseRequistionList(Long deptId,Long orgId) {
		// Status Y in Query is Pending
		List<PurchaseRequistionEntity> entities = purchaseRequistionRepository.findByDepartmentAndOrgIdAndStatusOrderByPrId(deptId,orgId,"A");
		final List<PurchaseRequistionDto> bean = new ArrayList<>();
		for (final PurchaseRequistionEntity purchaseRequistionEntity : entities) {
			bean.add(purchaseRequisitionMapper
					.mapPurchaseRequistionEntityToPurchaseRequistionDto(purchaseRequistionEntity));
		}
		return bean;
	}

	@Override
	@Transactional(readOnly = true)
	public Long getPrIdById(String appNo, Long orgId) {
		Long expiryId = purchaseRequistionRepository.getDataById(appNo, orgId);
		return expiryId;
	}

	@Override
	@Transactional
	public void updatePurchaseStatus(long orgid, String AppId, String flagr) {
		purchaseRequistionRepository.updatePurchaseStatus(orgid, AppId, flagr);
		
	}

	@Override
	@Transactional
	public void updateStatusPurchaseReq(PurchaseRequistionDto dto) {
		purchaseRequistionRepository.updateStatusPurchaseReq(dto.getPrIds(),dto.getStatus(), dto.getUpdatedBy());
	}

	@Override
    @Transactional(readOnly = true)
	public Map<String,String> fetchPurchaseRequistionCode(PurchaseRequistionDto dto) {
		List<PurchaseRequistionEntity> entities = purchaseRequistionRepository.findByOrgIdAndPrIdIn(dto.getOrgId(), dto.getPrIds());
		Map<String,String> prCodes=new HashMap<>();
		for (final PurchaseRequistionEntity purchaseRequistionEntity : entities) {
			prCodes.put(purchaseRequistionEntity.getPrId()+"", purchaseRequistionEntity.getPrNo());
		}
		return prCodes;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PurchaseRequistionYearDetDto> getAllYearDetEntity(Long orgId, Long prId) {
		List<PurchaseRequistionYearDetEntity> entities =purchaseRequistionRepository.getAllYearDetEntity(orgId, prId);
		List<PurchaseRequistionYearDetDto> dtos=new ArrayList<>();
		PurchaseRequistionYearDetDto dto=null;
		for (PurchaseRequistionYearDetEntity purchaseRequistionYearDetEntity : entities) {
			dto=new PurchaseRequistionYearDetDto();
			BeanUtils.copyProperties(purchaseRequistionYearDetEntity, dto);
			dtos.add(dto);
		}
		
		return dtos;
	}
		
	@Override
	@Transactional
	public VendorBillApprovalDTO getBudgetExpenditureDetails(VendorBillApprovalDTO billApprovalDTO) {
		VendorBillApprovalDTO vendorBillApprovalDTO = null;
		ResponseEntity<?> response = null;

		try {
			response = RestClient.callRestTemplateClient(billApprovalDTO, ServiceEndpoints.SALARY_BILL_BUDGET_DETAILS);
			if ((response != null) && (response.getStatusCode() == HttpStatus.OK)) {
				LOGGER.info("get Budget Expenditure Details done successfully ::");
				vendorBillApprovalDTO = (VendorBillApprovalDTO) RestClient.castResponse(response,
						VendorBillApprovalDTO.class);
			} else {
				LOGGER.error("get Budget Expenditure Details failed due to :"
						+ (response != null ? response.getBody() : MainetConstants.BLANK));

				throw new FrameworkException("get Budget Expenditure Details failed due to :"
						+ (response != null ? response.getBody() : MainetConstants.BLANK));
			}
		} catch (Exception ex) {
			LOGGER.error("Exception occured while fetching Budget Expenditure Details : " + ex);
			throw new FrameworkException(ex);
		}
		return vendorBillApprovalDTO;
	}
	
	@Override
	public List<Object[]> purchaseReqForTender(Long orgId) {
		// Status T in Query is Tender
		return purchaseRequistionRepository.getPrIdNumbersForPurchaseOrder(orgId, MainetConstants.FlagT);
	}

	@SuppressWarnings("null")
	@Override
	@Transactional(readOnly = true)
	public List<PurchaseRequistionDto> getAllDataListPrId(Long orgId, List<Long> prId) {

		List<PurchaseRequistionEntity> entities = purchaseRequistionRepository.findByOrgIdAndPrIdIn(orgId, prId);
		List<PurchaseRequistionDto> dtoList = new ArrayList<>();
		PurchaseRequistionDto dto = null;
		for (PurchaseRequistionEntity purchaseRequistionEntity : entities) {
			dto = new PurchaseRequistionDto();
			BeanUtils.copyProperties(purchaseRequistionEntity, dto);
			
			 List<PurchaseRequistionDetDto> PurReqDetdtoList=new ArrayList<>();
			 PurchaseRequistionDetDto PurReqDetdto=null;
			 for(PurchaseRequistionDetEntity det:purchaseRequistionEntity.getPurchaseRequistionDetEntity()) {
				 ItemMasterDTO tbMGItemMaster = new ItemMasterDTO();
					tbMGItemMaster.setItemId(det.getItemId());
					tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
					LookUp uomPrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(tbMGItemMaster.getUom(), UserSession.getCurrent().getOrganisation());
					PurReqDetdto.setUonName(uomPrefix.getLookUpDesc());
				 PurReqDetdtoList.add(PurReqDetdto);
			 }
			 PurchaseRequistionDto purchaseDto =purchaseRequisitionMapper.mapPurchaseRequistionEntityToPurchaseRequistionDto(purchaseRequistionEntity);
			 purchaseDto.setPurchaseRequistionDetDtoList(PurReqDetdtoList);
			 
			dtoList.add(dto);
		}
		return dtoList;

	}
	

	@Override
	@Transactional(readOnly = true)
	public Date getPrDateByPrId(Long orgId, String prNo) {
		Date prDate = purchaseRequistionRepository.findPrDateByOrgIdAndPrNo(orgId, prNo);
		return prDate;
	}

	
	@Override
    @Transactional(readOnly = true)
	public List<PurchaseRequistionDetDto> fetchPurchaseReqDetailList(PurchaseRequistionDto dto) {
		List<PurchaseRequistionDetEntity> requistionDetEntityList = purchaseRequistionRepository
				.findPurchaseRequistionDetEntitiesByOrgIdAndPridIn(dto.getOrgId(), dto.getPrIds());
		
		List<Long> itemIds = requistionDetEntityList.stream().map(list -> list.getItemId()).collect(Collectors.toList());
		List<Object[]> itemIdNameObjects = itemMasterRepository.getItemIdNameListByItemIdsOrgId(itemIds, dto.getOrgId());
		Map<Long, String> itemIdNameMap = new HashMap<>();
		itemIdNameObjects.forEach(itemIdNameObj -> itemIdNameMap.put(Long.valueOf(itemIdNameObj[0].toString()), itemIdNameObj[1].toString()));

		final List<PurchaseRequistionDetDto> requistionDtoList = new ArrayList<>();
		requistionDetEntityList.forEach(requistionDetEntity -> {
			PurchaseRequistionDetDto requistionDetDto = new PurchaseRequistionDetDto();
			BeanUtils.copyProperties(requistionDetEntity, requistionDetDto);
			requistionDetDto.setItemName(itemIdNameMap.get(requistionDetDto.getItemId()));
			requistionDtoList.add(requistionDetDto);
		});
		return requistionDtoList;
	}


	
}
