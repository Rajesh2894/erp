package com.abm.mainet.tradeLicense.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.TransperLicenseModel;

@Controller
@RequestMapping("/LicenseSearchDetails.html")
public class LicenseDetailsSearchController extends AbstractFormController<TransperLicenseModel> {

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		// For setting help document
		getModel().setCommonHelpDocs("LicenseSearchDetails.html");
		getModel().setLicenseDetails("N");
		return new ModelAndView("LicenseDetailsSearch", MainetConstants.FORM_NAME, getModel());

	}

	@RequestMapping(params = MainetConstants.TradeLicense.GET_LICENSE_DETAILS, method = RequestMethod.POST)
	public ModelAndView getLicenseDetails(HttpServletRequest httpServletRequest,
			@RequestParam("trdLicno") String trdLicno) {
		this.getModel().bind(httpServletRequest);
		TransperLicenseModel model = this.getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, 0L);
		if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
			model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
			model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
			model.setLicenseDetails(MainetConstants.FlagY);
			model.setViewMode(MainetConstants.FlagE);

		}

		model.setTradeDetailDTO(tradeDTO);
		if (tradeDTO != null && tradeDTO.getLicenseNo()!=null) {
			// 124309 - to show multiple owner if there
			if (!model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
				StringBuilder ownName = new StringBuilder();
				String ownerName = "";
				List<TradeLicenseOwnerDetailDTO> ownerDetailDTOList = model.getTradeDetailDTO()
						.getTradeLicenseOwnerdetailDTO();
				if (CollectionUtils.isNotEmpty(ownerDetailDTOList)) {

					for (TradeLicenseOwnerDetailDTO ownDto : ownerDetailDTOList) {
						if (StringUtils.isNotBlank(ownDto.getTroName()))
							ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
					}
				}

				if (ownName.length() > 0) {
					ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
				}
				if (StringUtils.isNotBlank(ownerName)) {
					model.setOwnerName(ownerName);
				}
			}

		} else {
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
		}
		return  defaultMyResult();
	}
}
