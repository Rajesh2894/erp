/**
 * 
 */
package com.abm.mainet.tradeLicense.ui.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.TradeDataEntyDto;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.DataEntrySuiteModel;

/**
 * @author Saiprasad.Vengurekar
 *
 */

@Controller
@RequestMapping("/dataEntrySuites.html")
public class DataEntrySuitesController extends AbstractFormController<DataEntrySuiteModel> {
	private static final Logger LOGGER = Logger.getLogger(DataEntrySuitesController.class);
	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	ITradeLicenseApplicationService tradeService;

	@Resource
	private IAttachDocsService attachDocsService;

	/**
	 * Used to default Data Entry Suite Summary page
	 * 
	 * @param httpServletRequest
	 * @return defaultResult
	 */

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		fileUpload.sessionCleanUpForFileUpload();
		// revert this code for populating lcense no field in Data Entry Suite form
		/*
		 * this.getModel().setMasList( tradeService.getFilteredNewTradeLicenceList(null,
		 * null, null, null, null, null, null, null, orgId));
		 */
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.AS,
				MainetConstants.TradeLicense.MLI, UserSession.getCurrent().getOrganisation());
		if (lookUp != null) {
			if (lookUp.getOtherField().equals(MainetConstants.Y_FLAG))
				this.getModel().setPropertyActive(MainetConstants.FlagY);
		}
       //Defect #138334
		this.getModel().setPrintable(isPrintable());
		return defaultResult();
	}

	/**
	 * To create Data Entry Suite
	 * 
	 * @param mode
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.CREATE_DATA_ENTRY)
	public ModelAndView createDataEntrySuite(
			@RequestParam(name = MainetConstants.WorksManagement.MODE, required = false) String mode,
			final HttpServletRequest request) {
		DataEntrySuiteModel model = this.getModel();

		model.setSaveMode(MainetConstants.MODE_CREATE);
		
		return new ModelAndView(MainetConstants.TradeLicense.DATA_ENTRYSUITE_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * To edit Data Entry Suite
	 * 
	 * @param trdId
	 * @param formMode
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.EDIT_DATA_ENTRYSUITE, method = RequestMethod.POST)
	public ModelAndView editDataEntrySuite(@RequestParam(MainetConstants.TradeLicense.TRD_ID) final Long trdId,
			@RequestParam(name = MainetConstants.WorksManagement.FORM_MODE, required = false) String formMode,
			final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		DataEntrySuiteModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		boolean validate = tradeService.validateDataEntrySuite(trdId, orgId);
		if (validate) {
			model.setSaveMode(MainetConstants.FlagV);
			model.setViewMode(MainetConstants.FlagV);
		} else {
			model.setSaveMode(formMode);
			if (formMode.equals("V")) {
				model.setViewMode(MainetConstants.FlagV);
			}
		}
		model.setTradeMasterDetailDTO(tradeService.getTradeLicenseById(trdId));
		TradeMasterDetailDTO dto = model.getTradeMasterDetailDTO();
		// User Story #112674 start
		LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(dto.getTrdStatus(), orgId, "LIS");
		if (formMode.equals(MainetConstants.FlagE)) {
			Boolean flag = false;
			if (lookup != null && lookup.getLookUpCode().equals(MainetConstants.FlagT)) {
				getModel()
						.addValidationError(ApplicationSession.getInstance().getMessage("renewal.valid.licenseStatus"));
				flag = true;
			}
			if (lookup != null && lookup.getLookUpCode().equals(MainetConstants.FlagC)) {
				getModel().addValidationError(ApplicationSession.getInstance().getMessage("trade.licNo.cancel"));
				flag = true;
			}
			if (flag) {
				model.setSaveMode(MainetConstants.FlagV);
				model.setViewMode(MainetConstants.FlagV);
			}
		}
		// end
		Organisation org = UserSession.getCurrent().getOrganisation();
		LookUp gisFlag = CommonMasterUtility.getValueFromPrefixLookUp("GIS", "MLI", org);
		if (gisFlag != null) {
			getModel().setGisValue(gisFlag.getOtherField());
			String GISURL = ServiceEndpoints.GisItegration.GIS_URI
					+ ServiceEndpoints.GisItegration.LICENSE_TRADE_LAYER_NAME;
			model.getTradeMasterDetailDTO().setgISUri(GISURL);
		}

		// Defect#121749
		if (model.getTradeMasterDetailDTO() != null
				&& !CollectionUtils.isEmpty(model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO())) {
			model.getTradeMasterDetailDTO()
					.setTradeLicenseOwnerdetailDTO(model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO()
							.stream()
							.filter(trd -> trd.getTroPr() != null && (trd.getTroPr().equals(MainetConstants.FlagA)
									|| trd.getTroPr().equals(MainetConstants.FlagD)))
							.collect(Collectors.toList()));
		}
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(
				UserSession.getCurrent().getOrganisation().getOrgid(), model.getTradeMasterDetailDTO().getTrdLicno());
		this.getModel().setAttachDocsList(attachDocs);
		ModelAndView mv = new ModelAndView(MainetConstants.TradeLicense.DATA_ENTRYSUITE_FORM, MainetConstants.FORM_NAME,
				this.getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
		return mv;

	}

	/**
	 * To get Data Entry List for Summary Page
	 * 
	 * @param oldLicenseNo
	 * @param businessAddress
	 * @param trdWard1
	 * @param trdWard2
	 * @param trdWard3
	 * @param trdWard4
	 * @param trdWard5
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.TradeLicense.GET_DATA_ENTRYSUITE, method = RequestMethod.POST)
	public List<TradeDataEntyDto> getDataEntryList(
			@RequestParam(name = MainetConstants.TradeLicense.LICENSE_TYPE) Long licenseType,
			@RequestParam(name = MainetConstants.TradeLicense.OLD_LICENSENO) String oldLicenseNo,
			@RequestParam(name = MainetConstants.TradeLicense.NEW_LICENSENO) String newLicenseNo,
			@RequestParam(name = MainetConstants.TradeLicense.TRD_WARD1) Long trdWard1,
			@RequestParam(name = MainetConstants.TradeLicense.TRD_WARD2, required = false) Long trdWard2,
			@RequestParam(name = MainetConstants.TradeLicense.TRD_WARD3, required = false) Long trdWard3,
			@RequestParam(name = MainetConstants.TradeLicense.TRD_WARD4, required = false) Long trdWard4,
			@RequestParam(name = MainetConstants.TradeLicense.TRD_WARD5, required = false) Long trdWard5,
			@RequestParam(name = MainetConstants.TradeLicense.PRI_OWNER, required = false) String  ownerName,
			@RequestParam(name = MainetConstants.TradeLicense.BUSINESS_NAME, required = false) String busName) {
		if (trdWard1 == null)
			trdWard1 = 0l;
		if (trdWard2 == null)
			trdWard2 = 0l;
		if (trdWard3 == null)
			trdWard3 = 0l;
		if (trdWard4 == null)
			trdWard4 = 0l;
		if (trdWard5 == null)
			trdWard5 = 0l;

		List<TradeDataEntyDto> trdDtoList = tradeService.getFilteredNewTradeLicenceList(licenseType, oldLicenseNo,
				newLicenseNo, trdWard1, trdWard2, trdWard3, trdWard4, trdWard5,
				UserSession.getCurrent().getOrganisation().getOrgid(), busName,  ownerName);

		return trdDtoList;
	}

	/**
	 * To get Ownership Type Div
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param ownershipType
	 * @param type
	 * @return
	 */
	/*
	 * @RequestMapping(params = MainetConstants.TradeLicense.GET_OWNERSHIP_TYPE_DIV,
	 * method = RequestMethod.POST) public ModelAndView
	 * getOwnershipTypeDiv(HttpServletRequest httpServletRequest,
	 * HttpServletResponse httpServletResponse,
	 * 
	 * @RequestParam(value = MainetConstants.TradeLicense.OWNERSHIP_TYPE) String
	 * ownershipType,
	 * 
	 * @RequestParam(value = MainetConstants.TradeLicense.TYPE) String type) {
	 * this.getModel().bind(httpServletRequest); DataEntrySuiteModel model =
	 * this.getModel(); //model.setOpenMode(MainetConstants.FlagD); if
	 * (type.equals(MainetConstants.FlagY)) { List<Long> ids = new ArrayList<>();
	 * model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().forEach(
	 * ownDto -> { if (ownDto.getTroId() != null) { ids.add(ownDto.getTroId()); }
	 * }); model.getTradeMasterDetailDTO().setOwnIds(ids);
	 * model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().clear();
	 * model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().add(new
	 * TradeLicenseOwnerDetailDTO()); } model.setOwnershipPrefix(ownershipType);
	 * return new ModelAndView("TradeDataEntryOwnership", MainetConstants.FORM_NAME,
	 * model); }
	 */

	@RequestMapping(params = MainetConstants.TradeLicense.GET_OWNERSHIP_TYPE_DIV, method = RequestMethod.POST)
	public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = MainetConstants.TradeLicense.OWNERSHIP_TYPE) String ownershipType) {
		this.getModel().bind(httpServletRequest);

		DataEntrySuiteModel model = this.getModel();
		/* if (model.getTradeMasterDetailDTO().getApmApplicationId() == null) { */
		// model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().clear();
		// model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().add(new
		// TradeLicenseOwnerDetailDTO());
		// }
		model.setOwnershipPrefix(ownershipType);
          //Defect #138350
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.TSCL)&&this.getModel().getSaveMode().equals(MainetConstants.MODE_CREATE)) {
			//Defect #158152 created new jsp for ownership type div
			return new ModelAndView("ThaneTradeDataEntrySuiteOwnership", MainetConstants.FORM_NAME, this.getModel());
		}
		else if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.TSCL)) {
			//Defect #158152 created new jsp for ownership type div
			return new ModelAndView("ThaneTradeDataEntrySuiteEdit", MainetConstants.FORM_NAME, this.getModel());
		}
		return new ModelAndView("TradeDataEntryOwnership", MainetConstants.FORM_NAME, model);
	}

	/**
	 * To validate Old License No
	 * 
	 * @param oldLiscenseNo
	 * @return String
	 */

	@RequestMapping(params = MainetConstants.TradeLicense.VALIDATE_OLDLICNO, method = RequestMethod.POST)
	public @ResponseBody String validateOldLiscenseNo(
			@RequestParam(value = MainetConstants.TradeLicense.OLDLICNO, required = false) final String oldLiscenseNo) {
		String isValid = null;
		TradeMasterDetailDTO mastDto = tradeService.getTradeLicenseByOldLiscenseNo(oldLiscenseNo,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (mastDto != null) {
			isValid = MainetConstants.FlagY;
		}
		return isValid;
	}

	/**
	 * To get Property details from Property Number
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.TradeLicense.GET_PROPERTY_DETAILS, method = RequestMethod.POST)
	public TradeMasterDetailDTO getPropertyDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		DataEntrySuiteModel model = this.getModel();
		TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.TradeLicense.AS,
				MainetConstants.TradeLicense.MLI, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		if (lookUp != null && lookUp.getOtherField().equals(MainetConstants.Y_FLAG)) {
			tradeDTO.setOrgid(orgId);
			tradeMasterDetailDTO = tradeService.getPropertyDetailsByPropertyNumber(tradeDTO);
			if (tradeMasterDetailDTO != null) {
				this.getModel().setTradeMasterDetailDTO(tradeMasterDetailDTO);
			}
		}
		return tradeMasterDetailDTO;

	}
  //Defect #138334
	private String isPrintable() {
		try {
			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagP, "PRT",
					UserSession.getCurrent().getOrganisation());
			if ((lookUp != null && StringUtils.isNotBlank(lookUp.getOtherField()))
					&& lookUp.getOtherField().equals(MainetConstants.FlagY)) {
				return MainetConstants.FlagY;
			}
		} catch (Exception e) {
			LOGGER.info("PRT prefix not found");
			return MainetConstants.FlagN;
		}
		return MainetConstants.FlagN;
	}
}
