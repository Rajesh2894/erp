package com.abm.mainet.tradeLicense.ui.controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.TradeManualReceiptEntryModel;

/**
 * @author Arun Shinde
 *
 */
@Controller
@RequestMapping(value = "/TradeManualReceiptEntry.html")
public class TradeManualReceiptEntryController extends AbstractFormController<TradeManualReceiptEntryModel> {

	private static final Logger LOGGER = Logger.getLogger(TradeManualReceiptEntryController.class);

	@Autowired
	ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private IFileUploadService uploadService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		sessionCleanup(request);
		return new ModelAndView("TradeManualReceiptEntry", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "getTradeLicDetails", method = RequestMethod.POST)
	public ModelAndView getTradeLicDetails(HttpServletRequest request, HttpServletResponse response) {
		uploadService.sessionCleanUpForFileUpload();
		getModel().bind(request);
		TradeMasterDetailDTO dto = this.getModel().getTradeMasterDetailDTO();
		TradeMasterDetailDTO tradeDto = null;
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		try {
			if (StringUtils.isNotBlank(dto.getTrdLicno())) {
				tradeDto = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(dto.getTrdLicno(), orgid);
			} else {
				tradeDto = tradeLicenseApplicationService.getTradeLicenseByOldLiscenseNo(dto.getTrdOldlicno(), orgid);
			}
			if (tradeDto != null) {
				this.getModel().setTradeMasterDetailDTO(tradeDto);

				Stream<String> nameList = tradeDto.getTradeLicenseOwnerdetailDTO().stream()
						.map(data -> data.getTroName() + MainetConstants.WHITE_SPACE + data.getTroMname())
						.collect(Collectors.toList()).stream();
				String ownerFullName = nameList.collect(Collectors.joining(MainetConstants.BLANK_WITH_SPACE));
				this.getModel().setOwnerFullName(ownerFullName);

			} else {
				getModel().addValidationError(getApplicationSession().getMessage("trade.validLicenseNoOldNew"));
				ModelAndView mv = new ModelAndView("TradeManualReceiptEntryValidn", MainetConstants.FORM_NAME,
						this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;

			}
		} catch (Exception e) {
			LOGGER.error("Exception occur while fetching License Details ", e);
		}

		return new ModelAndView("TradeManualReceiptEntryDetails", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "backToMainPage", method = RequestMethod.POST)
	public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		this.sessionCleanup(httpServletRequest);
		return new ModelAndView("TradeManualReceiptEntryValidn", MainetConstants.FORM_NAME, this.getModel());
	}

}
