package com.abm.mainet.tradeLicense.ui.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ICancellationLicenseService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.TradeLicenseApplicationServiceImpl;
import com.abm.mainet.tradeLicense.ui.model.CancellationLicenseFormModel;

@Controller
@RequestMapping("LicenseCancellationByForce.html")
public class LicenseCancelByForceController extends AbstractFormController<CancellationLicenseFormModel> {

	private static final Logger LOGGER = Logger.getLogger(TradeLicenseApplicationServiceImpl.class);
	@Autowired
	IFileUploadService fileUpload;
	@Autowired
	ITradeLicenseApplicationService tradeLicenseApplicationService;
	@Autowired 
	ICancellationLicenseService cancelService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		// Defect #108699
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("C", "LIS", orgId);
		// code changed for showing only issued data and if any license in process of
		// CBN service it can't be show in drop down
		this.getModel().setTradeMasterDetailDTO(
				tradeLicenseApplicationService.getActiveApplicationIdByOrgId(orgId).parallelStream()
						.filter(trd -> (trd != null && trd.getTrdStatus() != null)
								&& (!trd.getTrdStatus().equals(lookUpId) || trd.getTrdStatus() != lookUpId))
						.collect(Collectors.toList()));
		return new ModelAndView("CancelByForce", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "saveByForceCancelForm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveTradeLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		CancellationLicenseFormModel model = this.getModel();
		model.getTradeDetailDTO().setFree(false);
		model.getTradeDetailDTO().setCancelBy(MainetConstants.CANCEL_BY_FORCE);
		model.getTradeDetailDTO().setCancelDate(new Date());
		LookUp lookup = null;
		try {
			lookup = CommonMasterUtility.getValueFromPrefixLookUp("C", "LIS",
					UserSession.getCurrent().getOrganisation());
			model.getTradeDetailDTO().setTrdStatus(lookup.getLookUpId());
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Prefix Not Found for LIS");
		}

		Boolean dto = cancelService.saveCancellationLicenseByForceForm(model.getTradeDetailDTO());
		if (dto) {
			model.setSuccessMessage(ApplicationSession.getInstance().getMessage("license.cancel.save")+model.getTradeDetailDTO().getTrdLicno());
		} else {
			model.setSuccessMessage("Cancellation Failed");
		}
		return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

	}

	@RequestMapping(params = MainetConstants.TradeLicense.GET_LICENSE_DETAILS, method = RequestMethod.POST)
	public ModelAndView getLicenseDetails(HttpServletRequest httpServletRequest,
			@RequestParam("trdLicno") String trdLicno) {
		this.getModel().bind(httpServletRequest);
		CancellationLicenseFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);
		// for filtering only Approval Item Defect#107828
		if (tradeDTO != null) {
			List<TradeLicenseItemDetailDTO> itemDto = tradeLicenseApplicationService
					.getTradeLicenseHistDetBuTrdId(tradeDTO.getTradeLicenseItemDetailDTO().get(0).getTriId());
			if (tradeDTO.getTradeLicenseItemDetailDTO().get(0).getTriStatus() != null
					&& tradeDTO.getTradeLicenseItemDetailDTO().get(0).getTriStatus().equals(MainetConstants.FlagM)) {
				itemDto.get(itemDto.size() - 2).setItemCategory1(MainetConstants.FlagA);

				tradeDTO.getTradeLicenseItemDetailDTO().add(0, itemDto.get(itemDto.size() - 2));
			} else {
				tradeDTO.getTradeLicenseItemDetailDTO().get(0).setItemCategory1(MainetConstants.FlagA);
			}
			List<TradeLicenseItemDetailDTO> trdItemList = tradeDTO.getTradeLicenseItemDetailDTO().parallelStream()
					.filter(trd -> trd != null
							&& (trd.getTriStatus() != null && trd.getTriStatus().equalsIgnoreCase(MainetConstants.FlagA)
									|| (trd.getItemCategory1() != null
											&& trd.getItemCategory1().equals(MainetConstants.FlagA))))
					.collect(Collectors.toList());
			tradeDTO.setTradeLicenseItemDetailDTO(trdItemList);
		}
		tradeDTO.setCancelDate(new Date());
		model.setTradeDetailDTO(tradeDTO);
		// Defect#106851

		if (!model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
			// Defect #108769
			StringBuilder ownName = new StringBuilder();
			String ownerName = null;
			for (TradeLicenseOwnerDetailDTO ownDto : model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO()) {
				if (StringUtils.isNotBlank(ownDto.getTroName()))
					ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
			}
			ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
			if (StringUtils.isNotBlank(ownerName)) {
				model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().get(0).setTroName(ownerName);
			}
		}
		if (trdLicno != null) {
			Long lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("I", "LIS", orgId);
			if (!model.getTradeDetailDTO().getTrdStatus().equals(lookUpId)) {
				String troName = tradeLicenseApplicationService.getApprovedBuisnessName(trdLicno, orgId, lookUpId);
				if (troName != null) {
					model.getTradeDetailDTO().setTrdBusnm(troName);
				}
			}

		}
		if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
			model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
			model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
			model.setLicenseDetails(MainetConstants.FlagY);
			model.setChecklistCheck(MainetConstants.FlagY);
			model.setViewMode(MainetConstants.FlagV);

		} else {
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
		}
		return defaultMyResult();

	}

}
