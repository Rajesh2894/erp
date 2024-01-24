package com.abm.mainet.adh.ui.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.service.IADHBillMasService;
import com.abm.mainet.adh.ui.model.ADHBillContractModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;

@Controller
@RequestMapping("/ADHContractBillPayment.html")
public class ADHBillPaymentController extends AbstractFormController<ADHBillContractModel>{
	
	@Autowired
    private IADHBillMasService iADHBillMasService;
	
	 @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	    public ModelAndView index(final HttpServletRequest request) {
	        sessionCleanup(request);
	        this.getModel().setFormFlag(MainetConstants.FlagN);
	        return defaultResult();

	    }
	 
	 @RequestMapping(params = "searchADHBillRecord", method = {RequestMethod.POST })
	 public ModelAndView searchADHBillRecord(@RequestParam("contractNo") String contractNo, HttpServletRequest request) {

		this.getModel().bind(request);
		ADHBillContractModel model = this.getModel();
		model.getContractAgreementSummaryDTO().setContNo(contractNo);
		ModelAndView mv = new ModelAndView(MainetConstants.AdvertisingAndHoarding.ADH_BILL_PAYMENT_VALIDN,
				MainetConstants.FORM_NAME, this.getModel());
		ContractAgreementSummaryDTO contractAgreementSummaryDTO = iADHBillMasService
				.findByContractNo(UserSession.getCurrent().getOrganisation().getOrgid(), contractNo);
		model.getContractAgreementSummaryDTO().setContNo(contractNo);
		if (contractAgreementSummaryDTO != null) {
			if (contractAgreementSummaryDTO.getErrorMsg() != null) {
				model.addValidationError(contractAgreementSummaryDTO.getErrorMsg());
			} else {
				this.getModel().setFormFlag(MainetConstants.FlagY);
				contractAgreementSummaryDTO.setContAmount(new BigDecimal(String.format("%.0f", contractAgreementSummaryDTO.getContAmount())));
				contractAgreementSummaryDTO.setBalanceAmount(new BigDecimal(String.format("%.0f", contractAgreementSummaryDTO.getBalanceAmount())));
				if(contractAgreementSummaryDTO.getOverdueAmount()!=null) {
					contractAgreementSummaryDTO.setOverdueAmount(new BigDecimal(String.format("%.0f", contractAgreementSummaryDTO.getOverdueAmount())));
				}
				this.getModel().setContractAgreementSummaryDTO(contractAgreementSummaryDTO);
			}
		} else {
			model.addValidationError(
					getApplicationSession().getMessage("adh.validate.no.record.found") + " " + contractNo);
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	    

}
