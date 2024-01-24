package com.abm.mainet.water.rest.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.cfc.checklist.dto.ChecklistServiceDTO;
import com.abm.mainet.cfc.checklist.dto.DocumentResubmissionRequestDTO;
import com.abm.mainet.cfc.checklist.dto.DocumentResubmissionResponseDTO;
import com.abm.mainet.cfc.checklist.modelmapper.ChecklistMapper;
import com.abm.mainet.cfc.checklist.service.IChecklistSearchService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.ChangeOfOwnershipRepository;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.domain.AdditionalOwnerInfo;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBillDetEntity;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.domain.TbWtExcessAmt;
import com.abm.mainet.water.domain.WaterPenaltyEntity;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NewWaterConnectionResponseDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterPenaltyDto;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.repository.TbWtExcessAmtJpaRepository;
import com.abm.mainet.water.repository.WaterPenaltyRepository;
import com.abm.mainet.water.rest.dto.KDMCWaterDetailsRequestDTO;
import com.abm.mainet.water.rest.dto.KDMCWaterDetailsResponseDTO;
import com.abm.mainet.water.rest.dto.ViewCsmrConnectionDTO;
import com.abm.mainet.water.rest.dto.WaterTaxDetailsDto;
import com.abm.mainet.water.service.BillMasterService;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.utility.WaterCommonUtility;
import com.google.common.util.concurrent.AtomicDouble;

/**
 * @author deepika.pimpale
 *
 */
@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/newWaterConnectionForm")
public class NewWaterConnectionServiceController {

	@Resource
	private IChecklistSearchService checklistSearchService;

	@Resource
	private ChecklistMapper mapper;

	@Resource
	private IChecklistVerificationService documentUplodService;

	@Resource
	private NewWaterConnectionService waterConnectionService;

	@Resource
	private ChangeOfOwnershipRepository iChangeOfOwnershipRepository;
	
	@Resource
    private TbWtBillMasJpaRepository tbWtBillMasJpaRepository;
	
	@Resource
	private WaterPenaltyRepository waterPenaltyRepository;
	
    @Resource
    private TbTaxMasService tbTaxMasService;

    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private NewWaterRepository newWaterRepository;
    
    @Autowired
	private IReceiptEntryService iReceiptEntryService;
    
    @Autowired
    private IFinancialYearService financialYearService;
    
    @Resource
    private TbWtBillMasService tbWtBillMasService;
    
    @Resource
    private BillMasterService billMasterService;
    
    @Resource
    private TbWtExcessAmtJpaRepository tbWtExcessAmtJpaRepository;
    
	private static final Logger LOGGER = LoggerFactory.getLogger(NewWaterConnectionServiceController.class);

	@RequestMapping(value = "/saveNewWaterConnection", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object saveWater(@RequestBody final NewWaterConnectionReqDTO requestDTO, final HttpServletRequest request,
			final BindingResult bindingResult) {

		NewWaterConnectionResponseDTO responseDTO = new NewWaterConnectionResponseDTO();
		Assert.notNull(requestDTO, "Request DTO Can not be null");
		if (!bindingResult.hasErrors()) {
			try {
				Organisation organisation = new Organisation();
				organisation.setOrgid(requestDTO.getOrgId());
				responseDTO = waterConnectionService.saveWaterApplication(requestDTO);
				if (PrefixConstants.NewWaterServiceConstants.SUCCESS.equals(responseDTO.getStatus())) { // free
					waterConnectionService.initiateWorkFlowForFreeService(requestDTO);
					WaterCommonUtility.sendSMSandEMail(requestDTO.getApplicantDTO(), requestDTO.getApplicationId(),
							requestDTO.getPayAmount(), MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION,
							organisation);
				}

			} catch (final Exception ex) {
				LOGGER.error("error while saving New Water Connection:", ex);
			}
		} else {
			LOGGER.error("error while saving New Water Connection :", bindingResult.getAllErrors());
		}
		return responseDTO;
	}

	@RequestMapping(value = "/searchApplicantDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object searchApplicantDetails(@RequestBody final DocumentResubmissionRequestDTO requestVO,
			final HttpServletRequest request, final BindingResult bindingResult) {

		final DocumentResubmissionResponseDTO response = new DocumentResubmissionResponseDTO();

		try {

			final ChecklistServiceDTO checklistDetail = mapper
					.mapChecklistStatusViewToChecklistServiceDTO(checklistSearchService
							.getCheckListDataByApplication(requestVO.getOrgId(), requestVO.getApplicationId()));

			final List<CFCAttachmentsDTO> attachmentList = documentUplodService.getUploadedDocumentByDocumentStatus(
					requestVO.getApplicationId(), requestVO.getApplicationStatus(), requestVO.getOrgId());

			response.setChecklistDetail(checklistDetail);
			response.setAttachmentList(attachmentList);
			response.setStatus(MainetConstants.Req_Status.SUCCESS);
		} catch (final Exception ex) {

			response.setStatus(MainetConstants.Req_Status.FAIL);
			LOGGER.error("source dto object does not match to destination dto:", bindingResult.getAllErrors());
		}

		return response;

	}

	@RequestMapping(value = "/viewWaterConnectionDeatils", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getWaterConectionDetailByConnNo(@RequestParam("connNo") String connNo,
			@RequestParam("orgId") Long orgId) {
		ResponseEntity<?> responseEntity = null;
		ViewCsmrConnectionDTO waterConnDetail = null;
		try {

			waterConnDetail = waterConnectionService.viewConnectionDetailsByconnNo(connNo, orgId);
			if (waterConnDetail != null) {
				responseEntity = ResponseEntity.status(HttpStatus.OK).body(waterConnDetail);

				LOGGER.info(
						"Connection Detail in json formate:" + new ObjectMapper().writeValueAsString(waterConnDetail));

			} else {
				responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No Data Available Against connection No :" + connNo);
				LOGGER.info("No Data Available Against connection No: " + connNo);
			}

		} catch (Exception ex) {
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
			LOGGER.error("Error While Fetching connection detail: " + ex.getMessage(), ex);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/getkdmcWaterDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getkdmcWaterDetails(@RequestBody final KDMCWaterDetailsRequestDTO kdmcWaterDetailsRequestDTO,
			final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {
		LOGGER.info("In method : getkdmcWaterDetails");

		Assert.notNull(kdmcWaterDetailsRequestDTO, "Request DTO Can not be null");
		ResponseEntity<?> responseEntity = null;
        final KDMCWaterDetailsResponseDTO kdmcWaterDetailsResponseDTO = new KDMCWaterDetailsResponseDTO();
        String cause = null;
        try {
        	TbKCsmrInfoMH csmrInfo = null;
        	try {
        	 
        		csmrInfo = newWaterRepository.getWaterConnectionDetailsConNumber(
					kdmcWaterDetailsRequestDTO.getWaterConnNo(), kdmcWaterDetailsRequestDTO.getOrgId());
        		
    		}catch(Exception ex) {

            	cause = "Connection number is invalid";
    			kdmcWaterDetailsResponseDTO.setErrorCode(HttpStatus.BAD_REQUEST.name());
                kdmcWaterDetailsResponseDTO.setCause(cause);
                kdmcWaterDetailsResponseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(kdmcWaterDetailsResponseDTO);
    			LOGGER.error("Exception while calling getWaterConnectionDetailsConNumber() for connection no: " + " " + ex.getMessage(), ex);
        	}
        	
        	 if(csmrInfo!=null) {
        		 kdmcWaterDetailsResponseDTO.setLangId(kdmcWaterDetailsRequestDTO.getLangId());
        		 kdmcWaterDetailsResponseDTO.setConnId(csmrInfo.getCsIdn());
        		 kdmcWaterDetailsResponseDTO.setConnNo(kdmcWaterDetailsRequestDTO.getWaterConnNo());
        		 kdmcWaterDetailsResponseDTO.setOrgId(kdmcWaterDetailsRequestDTO.getOrgId());
        		 String name = (csmrInfo.getCsName()!=null ? csmrInfo.getCsName()+" " : "") +
	                		(csmrInfo.getCsMname()!=null ? csmrInfo.getCsName()+" " : "" ) +
	                		(csmrInfo.getCsLname()!=null ? csmrInfo.getCsLname() : "");
	                
        		 kdmcWaterDetailsResponseDTO.setOwnerName(name);
        		 
        		 if(csmrInfo.getApplicationNo()!=null) {
        			 final List<AdditionalOwnerInfo> additionalOwnerInfos = iChangeOfOwnershipRepository
        						.fetchAdditionalOwners(csmrInfo.getApplicationNo());
        			 if(CollectionUtils.isNotEmpty(additionalOwnerInfos)) {
        				 List<String> additionOwners = new ArrayList<String>(0);
        				 additionalOwnerInfos.stream().forEach(addOwner ->{
        					 additionOwners.add((addOwner.getCaoNewFName()!=null ? addOwner.getCaoNewFName() : "") +(
        							 addOwner.getCaoNewMName()!=null ? addOwner.getCaoNewMName() : "")+(
        									 addOwner.getCaoNewLName()!=null ? addOwner.getCaoNewLName() : ""));
        				 });
        				 kdmcWaterDetailsResponseDTO.setOwnerAdd(additionOwners);
        			 }
        		 }
            	 AtomicDouble totPayableAmt = new AtomicDouble(0);
            	 List<TbWtBillMasEntity> billMasList = tbWtBillMasJpaRepository
     		            .fetchBillMasData(csmrInfo.getCsIdn(), kdmcWaterDetailsRequestDTO.getOrgId());
 				 Double pendingSum = 0.0;
 				 List<WaterPenaltyEntity> penaltyList = null;
                 if(CollectionUtils.isNotEmpty(billMasList)) {
                     if (billMasList != null && !billMasList.isEmpty()) {
                         billMasList.get(billMasList.size() - 1).getBillDetEntity().forEach(det -> {
                        	 totPayableAmt.addAndGet(det.getBdCurBalTaxamt() + det.getBdPrvBalArramt());
                         });
                     
	                     penaltyList = waterPenaltyRepository
	 							.getWaterPenaltyByCCnNo(String.valueOf(csmrInfo.getCsIdn()), csmrInfo.getOrgId());
	                     if(CollectionUtils.isNotEmpty(penaltyList)) {
	                    	 pendingSum = penaltyList.stream().mapToDouble(penlaty->penlaty.getPendingAmount()).sum();

	                     }
                     }else {
                    	 TbServiceReceiptMasEntity receiptOfLatestPaidBill = iReceiptEntryService
 								.getLatestReceiptDetailByAddRefNo(kdmcWaterDetailsRequestDTO.getOrgId(),
 										String.valueOf(csmrInfo.getCsIdn()));
                    	 if(receiptOfLatestPaidBill!=null && billMasList.get(billMasList.size() - 1).getBmTotalBalAmount() == 0) {
                        	 cause = "Payment is made for all bill(s) for this connection number";
                    	 }
                     }
                     kdmcWaterDetailsResponseDTO.setTotalPayAmt(Double.valueOf(Math.round(totPayableAmt.get())));
	 				 kdmcWaterDetailsResponseDTO.setBillNo(billMasList.get(billMasList.size() - 1).getBmNo());
	 				 
	 				 Organisation org = new Organisation();
	 				 org.setOrgid(csmrInfo.getOrgId());
	 				 final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
	                         PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA, org);

	 				 final Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE,
	                        MainetConstants.STATUS.ACTIVE);
	                 // Here fetching all taxes applicable at the time of payment
	                 List<TbTaxMas> taxListPenalty = tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), deptId,
	                         chargeApplicableAtBillReceipt.getLookUpId());
	                 
	                 List<WaterTaxDetailsDto> waterTaxes = new ArrayList<>();
	                 List<TbWtBillMasEntity> unPaidBills = billMasList.stream().filter(bill->bill.getBmPaidFlag()!=null && bill.getBmPaidFlag().
	                		 equals(MainetConstants.FlagN)).collect(Collectors.toCollection(LinkedList::new)); 
	                 
	                 TbWtBillMasEntity latestBill = unPaidBills.get(unPaidBills.size()-1);
	                 LinkedList<TbWtBillDetEntity> latestBillDetails = latestBill.getBillDetEntity().stream().collect(Collectors.toCollection(LinkedList::new));
	                 Map<String, List<TbWtBillDetEntity>> mapOfTaxNameWiseDetails = new HashMap<>();
	                 Map<Object, List<TbWtBillDetEntity>> bmIdNoWiseTaxDetails = latestBillDetails.stream().collect
                			 (Collectors.groupingBy(billDet-> billDet.getBmIdNo(), LinkedHashMap::new, Collectors.toList()));
	                 for(Entry<Object, List<TbWtBillDetEntity>> bmIdWiseTax : bmIdNoWiseTaxDetails.entrySet()) {
	                	 for(TbWtBillDetEntity billDet: bmIdWiseTax.getValue()) {
	                		 TbTaxMas taxMas = tbTaxMasService.findTaxByTaxIdAndOrgId(billDet.getTaxId(), org.getOrgid());
		                	 if(taxMas!=null) {
		                		 if(mapOfTaxNameWiseDetails.containsKey(taxMas.getTaxDesc())) {
		                			 List<TbWtBillDetEntity> existingTaxEntity = mapOfTaxNameWiseDetails.get(taxMas.getTaxDesc());
		                			 existingTaxEntity.get(0).setBdPrvBalArramt(Math.round(existingTaxEntity.get(0).getBdPrvBalArramt()) + 
		                					 Math.round(billDet.getBdPrvBalArramt()));
		                			 existingTaxEntity.get(0).setBdCurBalTaxamt(Math.round(existingTaxEntity.get(0).getBdCurBalTaxamt()) + 
		                					 Math.round(billDet.getBdCurBalTaxamt()));
		                			 existingTaxEntity.get(0).setBdCurTaxamt(Math.round(existingTaxEntity.get(0).getBdPrvBalArramt()) + 
		                					 Math.round(existingTaxEntity.get(0).getBdCurBalTaxamt()));
		                			 mapOfTaxNameWiseDetails.put(taxMas.getTaxDesc(), existingTaxEntity);
		                		 }else {
		                			 billDet.setBdCurTaxamt(Math.round(billDet.getBdCurBalTaxamt()) + Math.round(billDet.getBdPrvBalArramt()));
		                			 mapOfTaxNameWiseDetails.put(taxMas.getTaxDesc(), Arrays.asList(billDet));
		                		 }
		                	 }
	                	 }
	                 }
	                 
	                 for(Entry<String, List<TbWtBillDetEntity>> taxWiseDet : mapOfTaxNameWiseDetails.entrySet()) {
	                	 WaterTaxDetailsDto taxDetail = new WaterTaxDetailsDto();
	             		 taxDetail.setTaxName(taxWiseDet.getKey());
	             		 taxDetail.setBalArrearsAmt(Double.valueOf(Math.round(taxWiseDet.getValue().get(0).getBdPrvBalArramt())));
	             		 taxDetail.setBalTaxAmt(Double.valueOf(Math.round(taxWiseDet.getValue().get(0).getBdCurBalTaxamt())));
	             		 taxDetail.setTaxAmt(Double.valueOf(Math.round(taxWiseDet.getValue().get(0).getBdCurTaxamt())));
	             		 waterTaxes.add(taxDetail); 
	                 }
	                 // adding penalty
	                 kdmcWaterDetailsResponseDTO.setWaterTaxDetailList(waterTaxes);
	                 List<WaterPenaltyDto> dynamicPenalty = getDynamicPenalty(taxListPenalty, billMasList, csmrInfo);
	                 Double dynamicPenaltyAmt = 0d;
	                 if(dynamicPenalty!=null && !dynamicPenalty.isEmpty()) {
	                	 dynamicPenaltyAmt  = dynamicPenalty.stream().mapToDouble(penalty->penalty.getPendingAmount()).sum();
	                 }
	                 kdmcWaterDetailsResponseDTO.setTotalInterestAmt(dynamicPenaltyAmt > pendingSum ? Double.valueOf(Math.round(dynamicPenaltyAmt)) : 
	                	 Double.valueOf(Math.round(pendingSum)));
	 				 kdmcWaterDetailsResponseDTO.setTotalActPayAmt(Double.valueOf(Math.round(pendingSum)) + Math.round(totPayableAmt.get()));
	 				 
	                List<TbBillMas> billList = billMasterService.getBillMasterListByUniqueIdentifier(csmrInfo.getCsIdn(), org.getOrgid());
	                Double adjustmentAmountForWaterSkdcl = billMasterService.getAdjustmentAmountForWaterSkdcl(deptId, csmrInfo.getCsIdn(),
	                		org.getOrgid(), billList);
	 				kdmcWaterDetailsResponseDTO.setAdjustmentAmount(adjustmentAmountForWaterSkdcl!=null? adjustmentAmountForWaterSkdcl : 0d);

	 				TbWtExcessAmt excessAmt = tbWtExcessAmtJpaRepository.findExcessAmountByCsIdnAndOrgId(csmrInfo.getCsIdn(),
                            org.getOrgid());
	 				kdmcWaterDetailsResponseDTO.setAdvancePaymentAmount(excessAmt!=null && excessAmt.getExcAmt()!=null ? excessAmt.getExcAmt().doubleValue() : 0d);
                	
                 }else {
                	 cause = "Bill(s) are not present for this connection number";
                 }
                 kdmcWaterDetailsResponseDTO.setErrorCode(null);
                 kdmcWaterDetailsResponseDTO.setCause(cause);
                 kdmcWaterDetailsResponseDTO.setErrorMsg(null);
                 kdmcWaterDetailsResponseDTO.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
 				 responseEntity = ResponseEntity.status(HttpStatus.OK).body(kdmcWaterDetailsResponseDTO);
        	
        	 }
        	 
        }catch(Exception ex) {

        	cause = "Something went wrong";
			kdmcWaterDetailsResponseDTO.setErrorCode(HttpStatus.BAD_REQUEST.name());
            kdmcWaterDetailsResponseDTO.setCause(cause);
            kdmcWaterDetailsResponseDTO.setErrorMsg("Something has wrong with this request, please try again!");
            kdmcWaterDetailsResponseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(kdmcWaterDetailsResponseDTO);
			LOGGER.error("Exception while calling getWaterConnectionDetailsConNumber() for connection no: " + " " + ex.getMessage(), ex);
        }

		return responseEntity;
	}

	private List<WaterPenaltyDto> getDynamicPenalty(List<TbTaxMas> taxList, List<TbWtBillMasEntity> billMasList, TbKCsmrInfoMH csmrInfo) {
		
		Organisation org = new Organisation();
		org.setOrgid(csmrInfo.getOrgId());
        List<WaterPenaltyDto> surChargeList=new ArrayList<>();
        Long finYearId = financialYearService.getFinanceYearId(new Date());
        final Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE,
                MainetConstants.STATUS.ACTIVE);
        TbCsmrInfoDTO csmrInfoDto = new TbCsmrInfoDTO();
        BeanUtils.copyProperties(csmrInfo, csmrInfoDto);
        
        List<TbBillMas> billMasListDto = tbWtBillMasService.getUnpaidBillEntityToDto(billMasList);
        
        for (TbTaxMas tbTaxMas : taxList) {
            if (StringUtils.equalsIgnoreCase(tbTaxMas.getTaxActive(), MainetConstants.FlagY)) {

                LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(tbTaxMas.getTaxCategory2(),
                		org.getOrgid());
                if (StringUtils.equalsIgnoreCase(lookUp.getLookUpCode(), MainetConstants.ReceiptForm.SC)) {
                    if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
                    	LOGGER.info("fetch bill data  calculate surcharge for skdcl");
                    	TbServiceReceiptMasEntity receiptOfLatestPaidBill = iReceiptEntryService.getLatestReceiptDetailByAddRefNo(org.getOrgid(),
                    			String.valueOf(csmrInfo.getCsIdn()));
                        Date manualReceiptDate = receiptOfLatestPaidBill != null ? receiptOfLatestPaidBill.getRmDate(): null;
						 surChargeList = tbWtBillMasService.calculateSurcharge(org, deptId, billMasListDto, tbTaxMas,finYearId,
								 csmrInfoDto, UserSession.getCurrent().getUserIp(), null, 
										 "Y", manualReceiptDate, surChargeList);
                    }
                }
            }
        }
        return surChargeList;
	}
}
