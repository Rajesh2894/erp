package com.abm.mainet.rnl.ui.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.model.Organization;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bill.service.BillMasterCommonService;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowAction;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.domain.EstateContractMapping;
import com.abm.mainet.rnl.domain.RLBillMaster;
import com.abm.mainet.rnl.dto.EstateContMappingDTO;
import com.abm.mainet.rnl.dto.EstateMaster;
import com.abm.mainet.rnl.repository.ReportSummaryRepository;
import com.abm.mainet.rnl.service.BRMSRNLService;
import com.abm.mainet.rnl.service.IEstateContractMappingService;
import com.abm.mainet.rnl.service.IRLBILLMasterService;
import com.abm.mainet.rnl.ui.model.EstateContractBillPaymentModel;
import com.abm.mainet.rnl.ui.model.EstateContractMappingModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/EstateContractBillPayment.html")
public class EstateContractBillPaymentController extends AbstractFormController<EstateContractBillPaymentModel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EstateContractBillPaymentController.class);

	@Autowired
	private IContractAgreementService iContractAgreementService;

	@Autowired
	private BillMasterCommonService billMasterService;

	@Autowired
	private IRLBILLMasterService iRLBILLMasterService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private IContractAgreementService contractAgreementService;

	@Autowired
	private IEstateContractMappingService iEstateContractMappingService;

	@Autowired
	private ContractAgreementRepository contractAgreementRepository;

	@Autowired
	private BRMSRNLService brmsRNLService;
	
	@Autowired
	ReportSummaryRepository reportSummaryRepository;
	
	@Autowired
	private IWorkflowActionService workflowActionService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest request) {
		sessionCleanup(request);
		final Organisation orgnisation = UserSession.getCurrent().getOrganisation();
		orgnisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		
		// T#37721 contract list with contract number - party name
		List<ContractAgreementSummaryDTO> contractAgreementSummaryList = new ArrayList<ContractAgreementSummaryDTO>();
		List<ContractAgreementSummaryDTO> contractAgreementFinalList = new ArrayList<ContractAgreementSummaryDTO>();
		try {
			contractAgreementSummaryList = contractAgreementService.getContractAgreementSummaryData(
					UserSession.getCurrent().getOrganisation().getOrgid(), null, null, null, null, null, null);
		} catch (Exception ex) {
			throw new FrameworkException("Exception while Contract List Data : " + ex.getMessage());
		}
		// re-filter with contract entry in tb_rl_bill_mast present or not
		contractAgreementFinalList = contractAgreementSummaryList.stream()
				.filter(agreement -> !(iRLBILLMasterService.finByContractId(agreement.getContId(),
						UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.CommonConstants.N,
						MainetConstants.CommonConstants.B)).isEmpty())
				.collect(Collectors.toList());
		this.getModel().setContractAgreementList(contractAgreementFinalList);

		// fetch properties for bill pay and re-filter
		List<Object[]> objectList = iEstateContractMappingService.fetchEstatePropertyForBillPay("Y"/* Map Active */,
				UserSession.getCurrent().getOrganisation().getOrgid());
		List<Object[]> objectFinalList = new ArrayList<Object[]>();
		objectFinalList = objectList.stream()
				.filter(obj -> !(iRLBILLMasterService.finByContractId((Long) obj[0],
						UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.CommonConstants.N,
						MainetConstants.CommonConstants.B)).isEmpty())
				.collect(Collectors.toList());
		this.getModel().setPropertyDetails(objectFinalList);
		
		// for estate name
		if (Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_TSCL)) {
		List<Object[]> estateListName = iEstateContractMappingService.fetchEstateDetails("Y"/* Map Active */,
				UserSession.getCurrent().getOrganisation().getOrgid(), Long.valueOf(UserSession.getCurrent().getLanguageId()));
		List<Object[]> finalEstateList = new ArrayList<Object[]>();
		finalEstateList = estateListName.stream()
				.filter(obj -> !(iRLBILLMasterService.finByContractId((Long) obj[0],
						UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.CommonConstants.N,
						MainetConstants.CommonConstants.B)).isEmpty())
				.collect(Collectors.toList());
		this.getModel().setEstateDetails(finalEstateList);
		}
		return defaultResult();

	}

	// method name change by ISRAT ON - 18 OCT 2019 behalf of SAMADHAN SIR because
	// new logic for display data
	@RequestMapping(method = RequestMethod.POST, params = "serachBillPaymentOld")
	public ModelAndView searchRLBillRecordsOld(@RequestParam("contNo") final String contNo,
			final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);

		final Organisation orgnisation = UserSession.getCurrent().getOrganisation();
		final ServiceMaster service = serviceMasterService.getServiceByShortName(MainetConstants.EstateContract.CBP,
				orgnisation.getOrgid());
		getModel().setServiceMaster(service);
		final EstateContractBillPaymentModel model = getModel();
		getModel().setContractNo(contNo);
		if (validateData(model)) {
			final ContractAgreementSummaryDTO contractAgreementSummaryDTO = iContractAgreementService
					.findByContractNo(orgnisation.getOrgid(), getModel().getContractNo());
			getModel().setContractAgreementSummaryDTO(contractAgreementSummaryDTO);
			final List<RLBillMaster> billMasters = iRLBILLMasterService.finByContractId(
					contractAgreementSummaryDTO.getContId(), orgnisation.getOrgid(), MainetConstants.CommonConstants.N,
					MainetConstants.CommonConstants.B);
			final List<TbBillMas> billMasData = new ArrayList<>();
			TbBillMas tbWtBillMas;

			final RNLRateMaster master = getInterestRate();
			final double interest = master.getPercentageRate();
			getModel().setBrmsTaxCode(master.getTaxCode());
			for (final RLBillMaster rlBillMaster : billMasters) {
				tbWtBillMas = new TbBillMas();
				tbWtBillMas.setBmDuedate(rlBillMaster.getDueDate());

				tbWtBillMas.setBmTotalAmount(rlBillMaster.getAmount());
				tbWtBillMas.setBmTotalBalAmount(rlBillMaster.getBalanceAmount());
				tbWtBillMas.setBmIdno(rlBillMaster.getBillId());
				tbWtBillMas.setBmPaidFlag(MainetConstants.CommonConstants.N);
				tbWtBillMas.setOrgid(rlBillMaster.getOrgId());
				tbWtBillMas.setBmBilldt(rlBillMaster.getStartDate());

				tbWtBillMas.setBmIntValue(interest / 100);

				billMasData.add(tbWtBillMas);
			}
			getModel().setBillMasList(billMasData);
			boolean flag = false;
			int count = 0;
			final Calendar currentDate = Calendar.getInstance();
			final Calendar billDate = Calendar.getInstance();
			currentDate.setTime(new Date());
			// currentDate.add(Calendar.DATE, 29);

			for (final TbBillMas tbWtBillMas1 : billMasData) {
				billDate.setTime(tbWtBillMas1.getBmDuedate());
				if (billDate.before(currentDate)) {
					count++;
					flag = true;
				}
			}
			TbBillMas balanceDataAmount = null;
			TbBillMas currentBalAmount = null;
			final List<TbBillMas> newList = new ArrayList<>(billMasData.subList(0, count));
			getModel().setForTaxCalulation(newList);
			if (flag) {
				billMasterService.processBillNewData(newList, null);
				final int sizeOfList = newList.size();
				balanceDataAmount = newList.get(sizeOfList - 1);
				currentBalAmount = newList.get(sizeOfList - 2);
				getModel().setBmTotalAmount(currentBalAmount.getBmTotalAmount());
				getModel().setBmTotalBalAmount(currentBalAmount.getBmTotalBalAmount());
			}

			getModel().setBillMas(balanceDataAmount);
			return new ModelAndView(MainetConstants.EstateContract.RNL_BILL_PAY,
					MainetConstants.CommonConstants.COMMAND, getModel());
		} else {
			return defaultMyResult();
		}

	}

	@RequestMapping(method = RequestMethod.POST, params = "serachBillPayment")
	public ModelAndView searchRLBillRecords(@RequestParam("contNo") final String contNo,
			@RequestParam("propertyContractNo") final String propertyContractNo,
			final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		String errorMsg = MainetConstants.RnlBillPayment.NO_RECORD;
		final Organisation orgnisation = UserSession.getCurrent().getOrganisation();
		final ServiceMaster service = serviceMasterService.getServiceByShortName(MainetConstants.EstateContract.CBP,
				orgnisation.getOrgid());
		getModel().setServiceMaster(service);
		final EstateContractBillPaymentModel model = getModel();
		// D#88671
		getModel().setContractNo(StringUtils.isEmpty(contNo) ? propertyContractNo : contNo);
		
		if (propertyContractNo != null) {
			List<PropertyDetailDto> listDTO = new ArrayList<PropertyDetailDto>();
			List<EstateMaster> estateDto = new ArrayList<EstateMaster>();
			
			List<Object[]> listObject = getModel().getPropertyDetails();
			for (Object[] obj : listObject) {
				PropertyDetailDto dto = new PropertyDetailDto();
				dto.setPropertyType(String.valueOf(obj[1]));
				dto.setPropNo(String.valueOf(obj[3]));
				dto.setUasge(String.valueOf(obj[5]));

				listDTO.add(dto);
			}
			String usageId = "";
			if (listDTO != null) {
				for (PropertyDetailDto pro : listDTO) {
					if (contNo.equals(pro.getPropertyType())) {
						getModel().getOfflineDTO().setPropNoConnNoEstateNoV(pro.getPropNo());
						getModel().setUsage(pro.getUasge());

					}
				}
			}
		}

		if (validateData(model)) {
			ContractAgreementSummaryDTO contractAgreementSummaryDTO = iContractAgreementService
					.findByContractNo(orgnisation.getOrgid(), getModel().getContractNo());

			if (contractAgreementSummaryDTO != null) {
				final EstateContMappingDTO estateContMappingDTO = iEstateContractMappingService
						.findByContractId(contractAgreementSummaryDTO.getContId());
				getModel().getOfflineDTO().setPropNoConnNoEstateNoL(estateContMappingDTO.getCode());
				List<RLBillMaster> billMasters;
				// check contract entry in tb_rl_bill_mast present or not
				if (!Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_TSCL)) {
				 billMasters = iRLBILLMasterService.finByContractId(
						contractAgreementSummaryDTO.getContId(), orgnisation.getOrgid(),
						MainetConstants.CommonConstants.N, MainetConstants.CommonConstants.B);
				}
				else
				{
					 billMasters = iRLBILLMasterService.finByContractIdPaidFlag(
							contractAgreementSummaryDTO.getContId(), orgnisation.getOrgid(),
							MainetConstants.CommonConstants.N, MainetConstants.CommonConstants.B);
				}
				if (billMasters.isEmpty()) {
					// no record found message
				} else {
					final Calendar currentDate = Calendar.getInstance();
					final Calendar billDate = Calendar.getInstance();
					currentDate.setTime(new Date());
					// get receipt AMT details --ISRAT
					contractAgreementSummaryDTO = iRLBILLMasterService.getReceiptAmountDetailsForBillPayment(
							contractAgreementSummaryDTO.getContId(), contractAgreementSummaryDTO,
							orgnisation.getOrgid());
					getModel().setContractAgreementSummaryDTO(contractAgreementSummaryDTO);

					Double arrAmt = contractAgreementSummaryDTO.getArrearsAmt().doubleValue();
					Long taxMasLookUpId = null;
					String Usagelookupcode = null;
					List<LookUp> lookUp2 = CommonMasterUtility.getLevelData("USA", 1, orgnisation);
					for (LookUp lookUp : lookUp2) {
						if (getModel().getUsage() != null && !getModel().getUsage().isEmpty()) {
							if (lookUp.getLookUpId() == Long.parseLong(getModel().getUsage())) {
								taxMasLookUpId = lookUp.getLookUpId();
								Usagelookupcode = lookUp.getLookUpCode();
							}
						}
					}
					
					Double sgstAmt = arrAmt * 9 / 100;
					Double cgstAmt = arrAmt * 9 / 100;
					BigDecimal sgstamount = new BigDecimal(sgstAmt);
					BigDecimal cgstamount = new BigDecimal(cgstAmt);
					if (Usagelookupcode != null && Usagelookupcode.equals("RESNG")) {
						getModel().getContractAgreementSummaryDTO().setArrearSgstAmt(0.0);
						getModel().getContractAgreementSummaryDTO().setArrearCgstAmt(0.0);

					} else {
						getModel().getContractAgreementSummaryDTO()
								.setArrearSgstAmt(sgstamount.setScale(0, RoundingMode.HALF_UP).doubleValue());
						getModel().getContractAgreementSummaryDTO()
								.setArrearCgstAmt(cgstamount.setScale(0, RoundingMode.HALF_UP).doubleValue());
					}
					getModel().getContractAgreementSummaryDTO().setArrearsSgstCgstTotalAmt(
							arrAmt + getModel().getContractAgreementSummaryDTO().getArrearSgstAmt()
									+ getModel().getContractAgreementSummaryDTO().getArrearCgstAmt());

					Double currAmt = contractAgreementSummaryDTO.getSumOfCurrentAmt().doubleValue();
					Double currSgstAmt = currAmt * 9 / 100;
					Double currCgstAmt = currAmt * 9 / 100;

					BigDecimal currSgstamount = new BigDecimal(currSgstAmt);
					BigDecimal currCgstamount = new BigDecimal(currCgstAmt);
					if (Usagelookupcode != null && Usagelookupcode.equals("RESNG")) {
						getModel().getContractAgreementSummaryDTO().setCurrentSgstAmt(0.0);
						getModel().getContractAgreementSummaryDTO().setCurrentCgstAmt(0.0);

					} else {
						getModel().getContractAgreementSummaryDTO()
								.setCurrentSgstAmt(currSgstamount.setScale(0, RoundingMode.HALF_UP).doubleValue());
						getModel().getContractAgreementSummaryDTO()
								.setCurrentCgstAmt(currCgstamount.setScale(0, RoundingMode.HALF_UP).doubleValue());
					}
					getModel().getContractAgreementSummaryDTO().setCurrentSgstCgstTotalAmt(
							currAmt + getModel().getContractAgreementSummaryDTO().getCurrentSgstAmt()
									+ getModel().getContractAgreementSummaryDTO().getCurrentCgstAmt());

					getModel().getContractAgreementSummaryDTO()
							.setTotalArrCurrSgstAmt(getModel().getContractAgreementSummaryDTO().getArrearSgstAmt()
									+ getModel().getContractAgreementSummaryDTO().getCurrentSgstAmt());
					getModel().getContractAgreementSummaryDTO()
							.setTotalArrcurrCgstAmt(getModel().getContractAgreementSummaryDTO().getArrearCgstAmt()
									+ getModel().getContractAgreementSummaryDTO().getCurrentCgstAmt());

					Double totalFinalAmt = getModel().getContractAgreementSummaryDTO().getCurrentSgstCgstTotalAmt()
							+ getModel().getContractAgreementSummaryDTO().getArrearsSgstCgstTotalAmt();
					getModel().getContractAgreementSummaryDTO().setTotalArrCurrAmt(totalFinalAmt);
					getModel().setFormFlag("Y");
					return new ModelAndView("EstateContractBillPaymentValidn", MainetConstants.CommonConstants.COMMAND,
							getModel());
				}
			} else {
				errorMsg = MainetConstants.RnlBillPayment.INVALID_CONTRACT;
				getModel().setFormFlag("N");
			}
		}

		getModel().addValidationError(getApplicationSession().getMessage(errorMsg));
		return defaultMyResult();

	}

	@RequestMapping(method = RequestMethod.POST, params = "getBillDetailbyContAndMobile")
	public ModelAndView getBillDetailbyContAndMobile(@RequestParam("contNo") final String contNo,
			@RequestParam("propertyContractNo") final String propertyContractNo,
			@RequestParam("mobileNo") final String mobileNo,
			@RequestParam(name = "estateName", required = false) final String estateName ,
			final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		this.getModel().setFormFlag("N");
		String errorMsg = MainetConstants.RnlBillPayment.INVALID_MOBILE;
		final Organisation orgnisation = UserSession.getCurrent().getOrganisation();
		orgnisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		final ServiceMaster service = serviceMasterService.getServiceByShortName(MainetConstants.EstateContract.CBP,
				orgnisation.getOrgid());
		getModel().setServiceMaster(service);
		getModel().setContractNo(StringUtils.isEmpty(contNo) ? propertyContractNo : contNo);
		Long langId = Long.valueOf(UserSession.getCurrent().getLanguageId());
		
		List<Object[]> searchLists = new ArrayList<>();
		if (Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_PSCL)) {	
			EstateContMappingDTO estateContMappingDTO = new EstateContMappingDTO();
			
			if(contNo != null) {
				estateContMappingDTO = iEstateContractMappingService
						.findContractsByContractNo(orgnisation.getOrgid(), contNo);
				WorkflowRequest workflowActionDto = workflowActionService.findWorkflowActionCommentAndDecisionByAppId(Long.parseLong(estateContMappingDTO.getWfRefno()), orgnisation.getOrgid());
//				searchLists = iContractAgreementService
//						.getBillDetailbyContAndMobileWorkFlow(getModel().getContractNo(), estateContMappingDTO.getWfRefno(), orgnisation.getOrgid());
//				if(searchLists.isEmpty()) {
//					getModel().addValidationError(getApplicationSession().getMessage("rnl.workflow.notApproved") + contNo);
//					return defaultMyResult();
//				}
				
				
				
				if (StringUtils.equalsIgnoreCase(workflowActionDto.getLastDecision(), MainetConstants.WorkFlow.Decision.APPROVED)
					    && StringUtils.equalsIgnoreCase(workflowActionDto.getStatus(), MainetConstants.DASHBOARD.CLOSED)) {
					searchLists = iContractAgreementService
							.getBillDetailbyContAndMobileWorkFlow(getModel().getContractNo(), estateContMappingDTO.getWfRefno(), orgnisation.getOrgid());
				}else {
					getModel().addValidationError(getApplicationSession().getMessage("rnl.workflow.notApproved") + contNo);
					return defaultMyResult();
				}
			}
			
		}else {
			if (Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_TSCL)) {
				searchLists =iEstateContractMappingService.searchByData(contNo, propertyContractNo, estateName, mobileNo, orgnisation.getOrgid());
			}else {
				searchLists =iContractAgreementService
						.getBillDetailbyContAndMobile(getModel().getContractNo(), mobileNo, orgnisation.getOrgid());
			}
			
		}

		List<ContractAgreementSummaryDTO> listDTO = new ArrayList<>(0);
		if(!searchLists.isEmpty()) {
			for(Object[] searchList:searchLists) {
				ContractAgreementSummaryDTO contractSummary=new ContractAgreementSummaryDTO();
				if(contNo != null) {
					if(!propertyContractNo.isEmpty()) {
						contractSummary.setPropertyNo(propertyContractNo);
					}	
				}else {
					List<Object[]> listObject = this.getModel().getPropertyDetails();
					for (Object[] obj : listObject) {
						if (obj[1].equals(String.valueOf(String.valueOf(searchList[1])))) {
							contractSummary.setPropertyNo((String) obj[3]);
							
						}
					}
				}
		
				contractSummary.setContId(Long.valueOf(searchList[0].toString()));
				contractSummary.setContNo(String.valueOf(searchList[1]));
				contractSummary.setContp2Name(String.valueOf(searchList[7]));
				
				if (Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_TSCL)) {
					if(langId.equals(1L)) {
						contractSummary.setEstateName(String.valueOf(searchList[14]));
					}else {
						contractSummary.setEstateName(String.valueOf(searchList[15]));
					}
				}
		
				final List<RLBillMaster> billMasters = iRLBILLMasterService.finByContractId(
						contractSummary.getContId(), orgnisation.getOrgid(),
						MainetConstants.CommonConstants.N, MainetConstants.CommonConstants.B);
				if(billMasters.size() != 0) {
					listDTO.add(contractSummary);
				}
			}
		}else {
			getModel().addValidationError(getApplicationSession().getMessage("rnl.seacrch.valid.nofound"));
			return defaultMyResult();
		}
		if (listDTO.size() != 0) {
			this.getModel().setListContractAgreementDTO(listDTO);
			return defaultMyResult();
		} 
		else {
			getModel().addValidationError(getApplicationSession().getMessage(errorMsg));
			return defaultMyResult();
		}
	}

	// T#86585
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getTaxCharges", method = { RequestMethod.POST })
	public ModelAndView getTaxCharges(final HttpServletRequest request) {

		this.getModel().bind(request);
		EstateContractBillPaymentModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		ModelAndView mv = new ModelAndView("EstateContractBillPaymentValidn", MainetConstants.FORM_NAME, getModel());
		final Organisation orgnisation = UserSession.getCurrent().getOrganisation();
		Long taxMasLookUpId = null;
		String Usagelookupcode = null;
		List<LookUp> lookUp2 = CommonMasterUtility.getLevelData("USA", 1, orgnisation);
		for (LookUp lookUp : lookUp2) {
			if (getModel().getUsage() != null && !getModel().getUsage().isEmpty()) {
				if (lookUp.getLookUpId() == Long.parseLong(getModel().getUsage())) {
					taxMasLookUpId = lookUp.getLookUpId();
					Usagelookupcode = lookUp.getLookUpCode();
				}
			}
		}
	
		BigDecimal TotalBalanceAmount =new BigDecimal(0.0);
		Double balanceAmount   = iRLBILLMasterService.getBalanceAmountByContractId(
				model.getContractAgreementSummaryDTO().getContId(),
				UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.CommonConstants.N);
		if (!Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_PSCL)) {
			if (Usagelookupcode != null && !Usagelookupcode.equals("RESNG")) {
				TotalBalanceAmount = BigDecimal.valueOf(balanceAmount + (balanceAmount * 18/100));
				balanceAmount = TotalBalanceAmount.setScale(0, RoundingMode.HALF_UP).doubleValue();
			}
		}
		
		if (Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_TSCL)) {
			Date currentDate = new Date();
			ContractMastDTO dto = iContractAgreementService.findById(model.getContractAgreementSummaryDTO().getContId(), orgId);
			/*
			 * Date suspendedDate = dto.getSuspendDate(); if(suspendedDate != null &&
			 * (suspendedDate.before(currentDate) || suspendedDate.equals(currentDate))) {
			 * getModel().addValidationError(getApplicationSession().getMessage(
			 * "rnl.bill.pay.suspended.or.terminated")); }
			 */
		}
		
		// check if paidAmt is more than alreadyPaidAmt
		if (model.getInputAmount() > balanceAmount) {
			getModel().addValidationError(getApplicationSession().getMessage("rnl.bill.pay.more.amount"));
			getModel().setPayAmount(0.0);
			getModel().setInputAmount(0.0);
		}
		if (getModel().hasValidationErrors()) {
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}
		if (model.getInputAmount() < getModel().getContractAgreementSummaryDTO().getPenalty().doubleValue()) {
			getModel().addValidationError(getApplicationSession().getMessage("rnl.bill.pay.less.amount"));
			getModel().setPayAmount(0.0);
			getModel().setInputAmount(0.0);
		}

		if (getModel().hasValidationErrors()) {
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}
		

		if (Usagelookupcode != null && Usagelookupcode.equals("RESNG")) {
			getModel().setPayAmount(model.getInputAmount());
		} else {

			// [START] BRMS call initialize model

			WSResponseDTO response = initializeModelForTax();
			final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp("APL",
					PrefixConstants.NewWaterServiceConstants.CAA, orgnisation);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> rnlRateMasterList = RestClient.castResponse(response, RNLRateMaster.class, 0);
				final RNLRateMaster rnlRateMaster = (RNLRateMaster) rnlRateMasterList.get(0);
				rnlRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

				rnlRateMaster.setDeptCode(MainetConstants.RnLCommon.RL_SHORT_CODE);

				// setting default parameter RNL rate master parameter
				WSRequestDTO taxReqDTO = new WSRequestDTO();
				rnlRateMaster.setOrgId(orgnisation.getOrgid());
				rnlRateMaster.setServiceCode(MainetConstants.EstateContract.CBP);
				rnlRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
				taxReqDTO.setDataModel(rnlRateMaster);

				final WSResponseDTO taxResponseDTO = brmsRNLService.getApplicableTaxes(taxReqDTO);

				if (taxResponseDTO.getWsStatus() != null
						&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {

					if (!taxResponseDTO.isFree()) {
						final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
						final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
						for (final Object rate : rates) {
							final RNLRateMaster master1 = (RNLRateMaster) rate;
							master1.setOrgId(orgnisation.getOrgid());
							master1.setServiceCode(MainetConstants.EstateContract.CBP);
							master1.setDeptCode(MainetConstants.RNL_DEPT_CODE);
							master1.setChargeApplicableAt(
									CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.CAA,
											UserSession.getCurrent().getOrganisation().getOrgid(),
											Long.parseLong(rnlRateMaster.getChargeApplicableAt())));

							master1.setTaxSubCategory(getSubCategoryDesc(master1.getTaxSubCategory(),
									UserSession.getCurrent().getOrganisation()));
							// set payable Amt from user input
							master1.setTotalAmount(model.getInputAmount());
							requiredCHarges.add(master1);
						}
						WSRequestDTO chargeReqDTO = new WSRequestDTO();
						chargeReqDTO.setDataModel(requiredCHarges);
						WSResponseDTO applicableCharges = brmsRNLService.getApplicableCharges(chargeReqDTO);
						final List<ChargeDetailDTO> charges = (List<ChargeDetailDTO>) applicableCharges
								.getResponseObj();
						if (charges == null) {
							throw new FrameworkException("Charges not Found in brms Sheet");
						} else {
							Organisation org = new Organisation();

							LookUp lookUp1 = CommonMasterUtility.getValueFromPrefixLookUp("DC", "GCC", org);
							if (lookUp1 != null && lookUp1.getOtherField().equals("Y")) {

								// check here charges from rules sheet
								// and also use to display tax name and code
								List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
								// payment AMT +total Taxable AMT
								Double finalAmt = model.getInputAmount();
								for (final ChargeDetailDTO charge : charges) {
									BigDecimal amount = new BigDecimal(charge.getChargeAmount());
									// BigDecimal bigDecimal=new BigDecimal(outstandingAmt);
									// amount.setOutstandingAmt(amount.setScale(2, RoundingMode.HALF_UP));
									charge.setChargeAmount(amount.setScale(0, RoundingMode.HALF_UP).doubleValue());
									// charge.setChargeAmount(amount.doubleValue());
									chargeList.add(charge);
									finalAmt -= charge.getChargeAmount();
								}
								// set inside contractAgreementSummaryDTO charge list
								getModel().getContractAgreementSummaryDTO().setChargeList(chargeList);
								getModel().setPayAmount(model.getInputAmount());
								getModel().setInputAmount(finalAmt);

							} else {

								// check here charges from rules sheet
								// and also use to display tax name and code
								List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
								// payment AMT +total Taxable AMT
								Double finalAmt = model.getInputAmount();
								for (final ChargeDetailDTO charge : charges) {
									BigDecimal amount = new BigDecimal(charge.getChargeAmount());
									charge.setChargeAmount(amount.doubleValue());
									chargeList.add(charge);
									finalAmt += charge.getChargeAmount();
								}
								// set inside contractAgreementSummaryDTO charge list
								getModel().getContractAgreementSummaryDTO().setChargeList(chargeList);
								getModel().setPayAmount(finalAmt);
								
							}

						}

					} else {
						/*
						 * getModel().
						 * addValidationError("Problem while checking dependent param for rnl rate ");
						 */
						getModel().setPayAmount(model.getInputAmount());
						getModel().setInputAmount(model.getInputAmount());
					}
				} else {
					getModel().addValidationError("Problem while initialize rnl rate master model");
				}

			}
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@SuppressWarnings("unchecked")
	public void findApplicableCheckListAndCharges(final long orgId, final HttpServletRequest httpServletRequest) {

		getModel().bind(httpServletRequest);
		final EstateContractBillPaymentModel model = getModel();
		// [START] BRMS call initialize model

		WSResponseDTO response = initializeModelForTax();
		final Organisation orgnisation = UserSession.getCurrent().getOrganisation();
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp("APL",
				PrefixConstants.NewWaterServiceConstants.CAA, orgnisation);

		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> rnlRateMasterList = RestClient.castResponse(response, RNLRateMaster.class, 0);
			final RNLRateMaster rnlRateMaster = (RNLRateMaster) rnlRateMasterList.get(0);
			rnlRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

			rnlRateMaster.setDeptCode(MainetConstants.RnLCommon.RL_SHORT_CODE);
			// rnlRateMaster.setTaxCode(tbTaxMas.getTaxCode());

			// setting default parameter RNL rate master parameter
			WSRequestDTO taxReqDTO = new WSRequestDTO();
			rnlRateMaster.setOrgId(orgId);
			rnlRateMaster.setServiceCode(MainetConstants.EstateContract.CBP);
			rnlRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
			taxReqDTO.setDataModel(rnlRateMaster);

			final WSResponseDTO taxResponseDTO = brmsRNLService.getApplicableTaxes(taxReqDTO);

			if (taxResponseDTO.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {

				if (!taxResponseDTO.isFree()) {
					// final List<?> rates = RestClient.castResponse(taxResponseDTO,
					// RNLRateMaster.class);
					final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
					final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
					for (final Object rate : rates) {
						final RNLRateMaster master1 = (RNLRateMaster) rate;
						master1.setOrgId(orgId);
						master1.setServiceCode(MainetConstants.EstateContract.CBP);
						master1.setDeptCode(MainetConstants.RNL_DEPT_CODE);
						master1.setChargeApplicableAt(CommonMasterUtility.findLookUpDesc(
								PrefixConstants.LookUpPrefix.CAA, UserSession.getCurrent().getOrganisation().getOrgid(),
								Long.parseLong(rnlRateMaster.getChargeApplicableAt())));

						master1.setTaxSubCategory(getSubCategoryDesc(master1.getTaxSubCategory(),
								UserSession.getCurrent().getOrganisation()));
						// set payable Amt from user input
						master1.setTotalAmount(model.getInputAmount());
						requiredCHarges.add(master1);
					}
					WSRequestDTO chargeReqDTO = new WSRequestDTO();
					chargeReqDTO.setDataModel(requiredCHarges);
					WSResponseDTO applicableCharges = brmsRNLService.getApplicableCharges(chargeReqDTO);
					final List<ChargeDetailDTO> charges = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
					if (charges == null) {
						throw new FrameworkException("Charges not Found in brms Sheet");
					} else {
						// check here charges from rules sheet
						// and also use to display tax name and code
						List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
						for (final ChargeDetailDTO charge : charges) {
							BigDecimal amount = new BigDecimal(charge.getChargeAmount());
							charge.setChargeAmount(amount.doubleValue());
							chargeList.add(charge);
						}
					}

				} else {
					throw new FrameworkException("Problem while checking dependent param for rnl rate .");
				}
			} else {
				throw new FrameworkException("Problem while initialize rnl rate master model.");
			}

		}

	}

	private boolean validateData(final EstateContractBillPaymentModel model) {
		if (getModel().getContractNo() == null || getModel().getContractNo().isEmpty()) {
			model.addValidationError(
					ApplicationSession.getInstance().getMessage(MainetConstants.EstateContract.CONTRACT_NO));
			return false;
		}
		return true;

	}

	private WSResponseDTO getTaxPercentage(final RNLRateMaster rate, final long orgid) {
		final WSRequestDTO dto = new WSRequestDTO();
		dto.setDataModel(rate);
		final WSResponseDTO applicableTaxes = RestClient.callBRMS(dto,
				ServiceEndpoints.BRMSMappingURL.RNL_TAX_PERCENTAGE_URI);
		return applicableTaxes;
	}

	private RNLRateMaster getInterestRate() {

		final Organisation orgnisation = UserSession.getCurrent().getOrganisation();
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp("RCPT",
				PrefixConstants.NewWaterServiceConstants.CAA, orgnisation);

		final TbTaxMas tbTaxMas = tbTaxMasService.findAllTaxesForBillGeneration(orgnisation.getOrgid(),
				getModel().getServiceMaster().getTbDepartment().getDpDeptid(), chargeApplicableAt.getLookUpId(), null)
				.get(0);

		getModel().setTaxDesc(tbTaxMas.getTaxDesc());
		RNLRateMaster responseObj = new RNLRateMaster();
		WSResponseDTO response = initializeModelForTax();
		new WSRequestDTO();
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> rnlRateMasterList = RestClient.castResponse(response, RNLRateMaster.class, 0);
			final RNLRateMaster rnlRateMaster = (RNLRateMaster) rnlRateMasterList.get(0);
			rnlRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

			rnlRateMaster.setDeptCode(MainetConstants.RnLCommon.RL_SHORT_CODE);
			rnlRateMaster.setTaxCode(tbTaxMas.getTaxCode());
			rnlRateMaster.setTaxType(CommonMasterUtility
					.getNonHierarchicalLookUpObject(Long.valueOf(tbTaxMas.getTaxMethod()), orgnisation)
					.getLookUpDesc());
			rnlRateMaster.setTaxCategory(CommonMasterUtility
					.getHierarchicalLookUp(Long.valueOf(tbTaxMas.getTaxCategory1()), orgnisation).getLookUpDesc());
			rnlRateMaster.setTaxSubCategory(CommonMasterUtility
					.getHierarchicalLookUp(Long.valueOf(tbTaxMas.getTaxCategory2()), orgnisation).getLookUpDesc());

			rnlRateMaster.setTaxCategory(CommonMasterUtility
					.getHierarchicalLookUp(Long.valueOf(tbTaxMas.getTaxCategory1()), orgnisation).getLookUpDesc());
			rnlRateMaster.setTaxSubCategory(CommonMasterUtility
					.getHierarchicalLookUp(Long.valueOf(tbTaxMas.getTaxCategory2()), orgnisation).getLookUpDesc());
			rnlRateMaster.setRateStartDate(new Date().getTime());
			rnlRateMaster.setChargeApplicableAt(chargeApplicableAt.getLookUpDesc());
			rnlRateMaster.setOccupancyType(MainetConstants.PropMaster.Lease);

			response = getTaxPercentage(rnlRateMaster, orgnisation.getOrgid());

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {

				final String jsonStr = getJsonString(response.getResponseObj());
				try {
					responseObj = new ObjectMapper().readValue(jsonStr, RNLRateMaster.class);
				} catch (final Exception e) {
					LOGGER.error("Error while reading value from response:" + e.getMessage(), e);
				}

			}

		}
		return responseObj;
	}

	private String getJsonString(final Object responseObject) {

		@SuppressWarnings("unchecked")
		final LinkedHashMap<Long, Object> outPutObject = (LinkedHashMap<Long, Object>) responseObject;
		final String jsonString = new JSONObject(outPutObject).toString();
		return jsonString;

	}

	private WSResponseDTO initializeModelForTax() {
		final WSRequestDTO dto = new WSRequestDTO();
		dto.setModelName(MainetConstants.RnLCommon.RNLRATEMASTER);
		final WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
		return response;
	}

	private String getSubCategoryDesc(final String taxsubCategory, final Organisation org) {
		String subCategoryDesc = "";
		final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.TAC_PREFIX,
				MainetConstants.EstateBooking.LEVEL, org);
		for (final LookUp lookup : subCategryLookup) {
			if (lookup.getLookUpCode().equals(taxsubCategory)) {
				subCategoryDesc = lookup.getDescLangFirst();
				break;
			}
		}
		return subCategoryDesc;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getPorpertyDetail")
	public @ResponseBody List<Object[]> searchRLBillRecords(@RequestParam("contNo") final String contNo,
			final HttpServletRequest httpServletRequest) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<Object[]> listObject = iEstateContractMappingService.fetchEstatePropertyDetails(contNo,orgId);
		List<Object[]> objectFinalList = new ArrayList<Object[]>();

		for (Object[] obj : listObject) {
			if (obj[5].equals(contNo)) {
				objectFinalList.add(obj);
			}
		}
		// listObject.clear();
		// listObject.addAll(objectFinalList);

		return objectFinalList;
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "getEstateName")
	public @ResponseBody List<Object[]> searchEstateName(@RequestParam("contNo") final String contNo,
			final HttpServletRequest httpServletRequest) {

		List<Object[]> listObject = getModel().getEstateDetails();
		List<Object[]> objectFinalList = new ArrayList<Object[]>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (!contNo.isEmpty()) {
			objectFinalList = iEstateContractMappingService.fetchEstatePropertyDetails(contNo, orgId);
		} else {
			for (Object[] obj : listObject) {
				if (obj[1].equals(contNo)) {
					objectFinalList.add(obj);
				}
			}
		}
		return objectFinalList;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getPropertyDetails")
	public @ResponseBody List<Object[]> searchPropertyDetails(@RequestParam("contNo") final String contNo,
			@RequestParam(name = "estateName", required = false) final String estateName ,
			final HttpServletRequest httpServletRequest) {
		
		List<Object[]> objectFinalList = new ArrayList<Object[]>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		if(!contNo.isEmpty()) {
			objectFinalList = iEstateContractMappingService.fetchEstatePropertyDetails(contNo, orgId);
		}else {
			List<Object[]> listObject = iEstateContractMappingService.fetchPropertyByEstateName(estateName, orgId);
			for (Object[] obj : listObject) {
				if (obj[5].equals(estateName)) {
					objectFinalList.add(obj);
				}
			}
		}
		return objectFinalList;
	}
	
	@RequestMapping(params = "getBalanceAmount", method = RequestMethod.POST)
	public @ResponseBody String getBalanceAmount(@RequestParam("contId") final Long contId) {

		boolean exists = false;
		exists = iEstateContractMappingService.existsBySmShortdescAndAdditionalRefNo(contId);

		if (exists) {
			return "Y";
		} else {
			return "N";
		}
	}

}
