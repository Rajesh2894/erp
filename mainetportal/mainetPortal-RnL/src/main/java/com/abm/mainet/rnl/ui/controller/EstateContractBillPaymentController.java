package com.abm.mainet.rnl.ui.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.constant.MainetConstants.ServiceShortCode;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.rnl.dto.EstatePropReqestDTO;
import com.abm.mainet.rnl.dto.PropInfoDTO;
import com.abm.mainet.rnl.dto.PropertyResDTO;
import com.abm.mainet.rnl.service.IRLBILLMasterService;
import com.abm.mainet.rnl.service.IRNLChecklistAndChargeService;
import com.abm.mainet.rnl.service.MPBCancellationService;
import com.abm.mainet.rnl.ui.model.EstateContractBillPaymentModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/EstateContractBillPayment.html")
public class EstateContractBillPaymentController extends AbstractFormController<EstateContractBillPaymentModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstateContractBillPaymentController.class);
    
    @Autowired
	private MPBCancellationService mPBCancellationService;
    
    @Autowired
    private ICommonBRMSService commonBRMSService;
    
    @Autowired
    private IRNLChecklistAndChargeService iRNLChecklistAndChargeService;
    
    @Autowired
    private IRLBILLMasterService iRLBILLMasterService;
    
    @Autowired
    private IOrganisationService organisationService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<ContractAgreementSummaryDTO> propInfoDtoList = iRLBILLMasterService
				.fetchSummaryData(orgId);
        this.getModel().setContractAgreementList(propInfoDtoList);
        if (CollectionUtils.isNotEmpty(propInfoDtoList)) {
        this.getModel().setPropertyDetails(propInfoDtoList.get(0).getPropertyDetails());}
        return defaultResult();

    }
    
    @RequestMapping(method = RequestMethod.POST, params = "serachBillPayment")
    public ModelAndView searchRLBillRecords(@RequestParam("contNo") String contNo,
            @RequestParam("propertyContractNo") final String propertyContractNo,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
       String errorMsg = "rnl.renewal.billPayment.NoRecord";
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
    	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENVIRNMENT_VARIABLE.ENV_PSCL)) {
        	Organisation org = organisationService.getOrgByOrgShortCode(MainetConstants.PROJECT_SHORTCODE.PRAYAGRAJ_ULB);
        	orgId = org.getOrgid(); 
        }
        ContractAgreementSummaryDTO contractAgreementSummaryDTO = iRLBILLMasterService
				.fetchSearchData(contNo,propertyContractNo,orgId);
        if (contractAgreementSummaryDTO != null) {
        	getModel().setContractNo(contractAgreementSummaryDTO.getContNo());
	        List<Object[]> listObject = getModel().getPropertyDetails();
			for (Object[] obj : listObject) {
				if (obj[1].equals(String.valueOf(contractAgreementSummaryDTO.getContNo()))) {
					getModel().setPropertyContractNo(String.valueOf(obj[3]));
					
				}
			}
			contractAgreementSummaryDTO.setContAmount(new BigDecimal(String.format("%.0f", contractAgreementSummaryDTO.getContAmount())));
			getModel().setContractAgreementSummaryDTO(contractAgreementSummaryDTO);
			getModel().setFormFlag("Y");
			return new ModelAndView("EstateContractBillPaymentValidn", MainetConstants.CommonConstants.COMMAND,getModel());
		} else {
			// no record found message
			errorMsg = "rnl.renewal.billPayment.ContractInvalid";
			getModel().setFormFlag("N");
		}

        getModel().addValidationError(getApplicationSession().getMessage(errorMsg));
        return defaultMyResult();

    }
    
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getTaxCharges", method = { RequestMethod.POST })
	public ModelAndView getTaxCharges(final HttpServletRequest request) {

		this.getModel().bind(request);
		EstateContractBillPaymentModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView mv = new ModelAndView("EstateContractBillPaymentValidn", MainetConstants.FORM_NAME, getModel());

		Map<String, String> requestParam = new HashMap<>();

		DefaultUriTemplateHandler uriHandler = new DefaultUriTemplateHandler();
		uriHandler.setParsePath(true);
		requestParam.put("contNo", String.valueOf(this.getModel().getContractAgreementSummaryDTO().getContId()));
		requestParam.put("inputAmount", String.valueOf(this.getModel().getInputAmount()));
		requestParam.put(MainetConstants.Common.ORGID, String.valueOf(orgId));

		URI uri = uriHandler.expand(ServiceEndpoints.BRMS_RNL_URL.CHECK_AMOUNT, requestParam);

		List<String> flatNoList = (List<String>) JersyCall.callRestTemplateClient(requestParam, uri.toString());

		if (!flatNoList.isEmpty()) {
			getModel().addValidationError(getApplicationSession().getMessage("rnl.bill.pay.more.amount"));
			getModel().setPayAmount(0.0);
		}

		if (getModel().hasValidationErrors()) {
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		} 
		if (model.getInputAmount() < getModel().getContractAgreementSummaryDTO().getPenalty().doubleValue()) {
            // error MSG
            getModel().addValidationError(getApplicationSession().getMessage("rnl.bill.pay.less.amount"));
            getModel().setPayAmount(0.0);
            getModel().setInputAmount(0.0);
        }
        if (getModel().hasValidationErrors()) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
        if (!getModel().hasValidationErrors()) {
			WSRequestDTO initReq = new WSRequestDTO();
			final Organisation orgnisation = UserSession.getCurrent().getOrganisation();
			final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp("APL",MainetConstants.EstateBooking.CAA_PREFIX, orgnisation);

			initReq.setModelName(MainetConstants.RNL_Common.CHECKLIST_RNL_MODEL_NAME);
			final WSResponseDTO response = commonBRMSService.initializeModel(initReq);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {

				final List<Object> rnlRateMasterList = JersyCall.castResponse(response, RNLRateMaster.class, 1);
				final RNLRateMaster rnlRateMaster = (RNLRateMaster) rnlRateMasterList.get(0);
				rnlRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				rnlRateMaster.setDeptCode("RNL");
				WSRequestDTO taxReqDTO = new WSRequestDTO();
				rnlRateMaster.setOrgId(orgnisation.getOrgid());
				rnlRateMaster.setServiceCode("CBP");
				rnlRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
				taxReqDTO.setDataModel(rnlRateMaster);

				final WSResponseDTO taxResponseDTO = iRNLChecklistAndChargeService.getApplicableTaxes(rnlRateMaster,Utility.getOrgId(), "CBP");

				if (taxResponseDTO.getWsStatus() != null&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {

					if (!taxResponseDTO.isFree()) {
				
						//final List<?> rates = (List<?>) taxResponseDTO.getResponseObj();
						final List<?> rates = JersyCall.castResponse(taxResponseDTO, RNLRateMaster.class);
						final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
						for (final Object rate : rates) {
							final RNLRateMaster master1 = (RNLRateMaster) rate;
							master1.setOrgId(orgnisation.getOrgid());
							master1.setServiceCode("CBP");
							master1.setDeptCode("RNL");
							master1.setChargeApplicableAt(CommonMasterUtility.findLookUpDesc(MainetConstants.EstateBooking.CAA_PREFIX,
											UserSession.getCurrent().getOrganisation().getOrgid(),Long.parseLong(rnlRateMaster.getChargeApplicableAt())));

							master1.setTaxSubCategory(getSubCategoryDesc(master1.getTaxSubCategory(),UserSession.getCurrent().getOrganisation()));
							// set payable Amt from user input
							master1.setTotalAmount(model.getInputAmount());
							requiredCHarges.add(master1);
						}
						WSRequestDTO chargeReqDTO = new WSRequestDTO();
						chargeReqDTO.setDataModel(requiredCHarges);
						 final List<ChargeDetailDTO> charges = iRNLChecklistAndChargeService.getApplicableCharges(requiredCHarges);
					
						/*
						 * final WSResponseDTO applicableCharges = iRNLChecklistAndChargeService
						 * .getApplicableTaxes(requiredCHarges, Utility.getOrgId(), "CBP");
						 * 
						 * final List<ChargeDetailDTO> charges = (List<ChargeDetailDTO>)
						 * applicableCharges .getResponseObj();
						 */
						if (charges == null) {
							throw new FrameworkException("Charges not Found in brms Sheet");
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

					} else {
						/*
						 * getModel().
						 * addValidationError("Problem while checking dependent param for rnl rate ");
						 */
						getModel().setPayAmount(model.getInputAmount());
						getModel().setInputAmount(model.getInputAmount());
					}
				}
			}
			 else {
					getModel().addValidationError("Problem while checking dependent param for rnl rate ");
				}
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}
	
	
	private String getSubCategoryDesc(final String taxsubCategory, final Organisation org) {
		String subCategoryDesc = "";
		final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(MainetConstants.EstateBooking.TAC_PREFIX,
				2, org);
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

		List<Object[]> listObject = getModel().getPropertyDetails();
		List<Object[]> objectFinalList = new ArrayList<Object[]>();

		for (Object[] obj : listObject) {
			if (obj[1].equals(contNo)) {
				objectFinalList.add(obj);
			}
		}
		return objectFinalList;
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "redirectToLeasePayment")
	public ModelAndView redirectToLeasePayment(final HttpServletRequest httpServletRequest)throws JsonParseException, JsonMappingException, IOException {
	sessionCleanup(httpServletRequest);
	bindModel(httpServletRequest);
	Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENVIRNMENT_VARIABLE.ENV_PSCL)) {
    	Organisation org = organisationService.getOrgByOrgShortCode(MainetConstants.PROJECT_SHORTCODE.PRAYAGRAJ_ULB);
    	orgId = org.getOrgid();
    }
    List<ContractAgreementSummaryDTO> propInfoDtoList = iRLBILLMasterService
			.fetchSummaryData(orgId);
    this.getModel().setContractAgreementList(propInfoDtoList);
    if (CollectionUtils.isNotEmpty(propInfoDtoList)) {
    this.getModel().setPropertyDetails(propInfoDtoList.get(0).getPropertyDetails());
    }
	return new ModelAndView("EstateContractBillPaymentValidn", MainetConstants.FORM_NAME, this.getModel());
	}
	
    
}
