package com.abm.mainet.common.integration.payment.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.integration.payment.dto.PgBankParameterDetailDto;
import com.abm.mainet.common.integration.payment.ui.model.PgBankParameterDetailModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;

/**
 * @author cherupelli.srikanth
 * @since 24 sep 2020
 */
@Controller
@RequestMapping("/PgBankParameterDetail.html")
public class PgBankParameterDetailController extends AbstractFormController<PgBankParameterDetailModel>{

	@RequestMapping(method= {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		PgBankParameterDetailDto schemeDetailDto = new PgBankParameterDetailDto();
		this.getModel().getPgBankDetailDtoList().add(schemeDetailDto);
		return index();
	}
}
