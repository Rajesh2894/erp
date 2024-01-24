package com.abm.mainet.water.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.ViewBillMas;
import com.abm.mainet.water.dto.ViewCsmrConnectionDTO;
import com.abm.mainet.water.dto.WaterDataEntrySearchDTO;
import com.abm.mainet.water.service.ViewWaterDetailService;
import com.abm.mainet.water.ui.model.ViewWaterDetailsModel;
import com.abm.mainet.water.ui.model.WaterBillPaymentModel;

/**
 * @author Bhagyashri.dongardive
 *
 */
@Controller
@RequestMapping("/ViewWaterDetails.html")
public class ViewWaterDetailsController extends AbstractFormController<ViewWaterDetailsModel> {

	@Autowired
	ViewWaterDetailService viewWaterDetailsService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setShowForm(MainetConstants.FlagN);
		return index();
	}

	@ResponseBody
	@RequestMapping(params = "searchWaterDetails", method = RequestMethod.POST)
	public List<WaterDataEntrySearchDTO> searchWaterDetails(HttpServletRequest request) {
		ViewWaterDetailsModel model = this.getModel();
		model.bind(request);
		model.getEntrySearchDto().setOrgId(Utility.getOrgId());
		List<WaterDataEntrySearchDTO> searchConnectionDetails = viewWaterDetailsService
				.searchPropertyDetails(model.getEntrySearchDto());
		return searchConnectionDetails;
	}

	@RequestMapping(params = "getWaterDetail", method = { RequestMethod.POST })
	public ModelAndView getWaterConnectiondetail(HttpServletRequest request,
			@RequestParam(value = "connNo") String connNo) {
		this.getModel().bind(request);
		ModelAndView mv = new ModelAndView("ViewWaterDetailsValidn", MainetConstants.FORM_NAME, this.getModel());
		TbCsmrInfoDTO infoDto = new TbCsmrInfoDTO();
		infoDto.setCsCcn(connNo);
		infoDto.setOrgId(Utility.getOrgId());
		infoDto.setCsOldccn(this.getModel().getViewConnectionDto().getCsOldccn());
		ViewCsmrConnectionDTO waterViewConnDto = viewWaterDetailsService.getWaterViewByConnectionNumber(infoDto);
		if (StringUtils.isBlank(waterViewConnDto.getCsCcn())) {
			this.getModel()
					.addValidationError(getApplicationSession().getMessage("water.validation.connectionNo"));
		} else {

			this.getModel().setViewConnectionDto(waterViewConnDto);
			infoDto.setCsIdn(this.getModel().getViewConnectionDto().getCsIdn());
			this.getModel().setCollectionDetails(viewWaterDetailsService.getCollectionDetails(infoDto));

			this.getModel().setShowForm(MainetConstants.FlagY);
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

		return mv;
	}

	@RequestMapping(params = "viewWaterDet", method = { RequestMethod.POST })
	public ModelAndView vieBillAndPropDetails(@RequestParam("bmIdNo") final long bmIdNo,
			final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		ViewWaterDetailsModel model = this.getModel();
		List<ViewBillMas> billMasList = new ArrayList<>(0);
		model.getViewConnectionDto().getViewBillMasList().stream().filter(bill -> bill.getBmIdno() == bmIdNo)
				.forEach(bills -> {
					billMasList.add(bills);
				});
		model.getViewConnectionDto().setViewBillMasWithDetList(billMasList);
		return new ModelAndView("ViewWaterTaxDetails", "command", model);
	}

	@RequestMapping(params = "backToWaterDet", method = RequestMethod.POST)
	public ModelAndView backToPropDet(HttpServletRequest request) {
		ViewWaterDetailsModel model = this.getModel();
		model.bind(request);
		return new ModelAndView("ViewWaterDetailsValidn", "command", model);
	}

	@RequestMapping(params = "receiptDownload", method = { RequestMethod.POST })
	public ModelAndView receiptDownload(@RequestParam("reciptId") Long reciptId,
			@RequestParam("receiptNo") Long receiptNo, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		bindModel(httpServletRequest);
		ViewCsmrConnectionDTO connectionDto = this.getModel().getViewConnectionDto();
		TbCsmrInfoDTO infoDto = new TbCsmrInfoDTO();
		infoDto.setCsCcn(connectionDto.getCsCcn());
		infoDto.setCsIdn(connectionDto.getCsIdn());
		infoDto.setReceiptNumber(String.valueOf(receiptNo));
		infoDto.setReceiptId(reciptId);
		infoDto.setLangId(UserSession.getCurrent().getLanguageId());
		ChallanReceiptPrintDTO receiptDto = viewWaterDetailsService.getRevenueReceiptDetails(infoDto);
		if (receiptDto != null) {
			receiptDto.setPropNo_connNo_estateNo_L("Connection No.");
			getModel().setReceiptDTO(receiptDto);
			return new ModelAndView("revenueReceiptPrint", MainetConstants.FORM_NAME, getModel());
		}
		return null;

	}
	
	@RequestMapping(params = "backOnViewSearch", method = RequestMethod.POST)
	public ModelAndView backOnViewSearch(HttpServletRequest request) {
		sessionCleanup(request);
		ViewWaterDetailsModel model = this.getModel();
		model.setShowForm(MainetConstants.FlagN);
		return new ModelAndView("ViewWaterDetailsValidn", "command", model);
	}
	
    @RequestMapping(params = "generateWaterReceipt", method = RequestMethod.POST)
	public @ResponseBody String generateWaterReceipt(HttpServletRequest httpServletRequest,@RequestParam("connectionNo") final String connectionNo,
			@RequestParam("waterReceiptDate") final String waterReceiptDate) throws Exception {
    		getModel().bind(httpServletRequest);
    		ViewWaterDetailsModel model = this.getModel();
				return ServiceEndpoints.WATER_BIRT_REPORT_URL + "=ReceiptReprintingReport_ConnectionNoWise.rptdesign&ULBName="
				+ UserSession.getCurrent().getOrganisation().getOrgid()+"&ConnectionNoWise="+ connectionNo+"&ReceiptDate="+ waterReceiptDate;
	}
}
