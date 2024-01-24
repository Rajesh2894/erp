package com.abm.mainet.adh.ui.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.domain.ADHBillMasEntity;
import com.abm.mainet.adh.service.IADHBillMasService;
import com.abm.mainet.adh.service.IAdvertisementContractMappingService;
import com.abm.mainet.adh.ui.model.ADHBillContractModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author cherupelli.srikanth
 * @since 15 November 2019
 */
@Controller
@RequestMapping("/ADHContractBillPayment.html")
public class ADHBillPaymentController extends AbstractFormController<ADHBillContractModel> {

    @Autowired
    private IContractAgreementService iContractAgreementService;

    @Autowired
    private IADHBillMasService ADHBillMasService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {
	sessionCleanup(request);

	this.getModel().setFormFlag(MainetConstants.FlagN);
	return index();
    }

    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SEARCH_ADH_BILL_RECORD, method = {
	    RequestMethod.POST })
    public ModelAndView searchADHBillRecord(
	    @RequestParam(MainetConstants.AdvertisingAndHoarding.CONTRACT_NO) String contractNo,
	    HttpServletRequest request) {

	this.getModel().bind(request);
	ADHBillContractModel model = this.getModel();
	ModelAndView mv = new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADH_BILL_PAYMENT_VALIDN,
		MainetConstants.FORM_NAME, this.getModel());
	ContractAgreementSummaryDTO contractAgreementSummaryDTO = iContractAgreementService
		.findByContractNo(UserSession.getCurrent().getOrganisation().getOrgid(), contractNo);
	model.getContractAgreementSummaryDTO().setContNo(contractNo);
	if (contractAgreementSummaryDTO != null) {

	    Long contractExist = ApplicationContextProvider.getApplicationContext()
		    .getBean(IAdvertisementContractMappingService.class)
		    .findContractByContIdAndOrgId(contractAgreementSummaryDTO.getContId(),
			    UserSession.getCurrent().getOrganisation().getOrgid());
	    if (contractExist != null && contractExist > 0) {
		List<ADHBillMasEntity> adhBillMasList = ADHBillMasService.finByContractId(
			contractAgreementSummaryDTO.getContId(), UserSession.getCurrent().getOrganisation().getOrgid(),
			MainetConstants.FlagN, MainetConstants.FlagB);
		if (CollectionUtils.isNotEmpty(adhBillMasList)) {
		    model.setAdhBillEnityList(adhBillMasList);
		    contractAgreementSummaryDTO = ADHBillMasService.getReceiptAmountDetailsForBillPayment(
			    contractAgreementSummaryDTO.getContId(), contractAgreementSummaryDTO,
			    UserSession.getCurrent().getOrganisation().getOrgid(), adhBillMasList);
		    this.getModel().setFormFlag(MainetConstants.FlagY);
		    if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
			    ContractMastDTO contractMastDTO = iContractAgreementService.findById(contractAgreementSummaryDTO.getContId(),  UserSession.getCurrent().getOrganisation().getOrgid());
			    contractAgreementSummaryDTO.setBalanceAmount(BigDecimal.valueOf(contractMastDTO.getContBalanceAmt()).setScale(2));
		    }
		} else {
		    model.addValidationError(
			    getApplicationSession().getMessage("adh.validate.no.dues.exist") + " " + contractNo);
		}
		this.getModel().setContractAgreementSummaryDTO(contractAgreementSummaryDTO);
	    } else {
		model.addValidationError(getApplicationSession()
			.getMessage("Contract No: " + contractNo + " is not mapped with any of the hoarding"));
	    }

	} else {
	    model.addValidationError(
		    getApplicationSession().getMessage("adh.validate.no.record.found") + " " + contractNo);
	}
	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
	return mv;
    }

}
