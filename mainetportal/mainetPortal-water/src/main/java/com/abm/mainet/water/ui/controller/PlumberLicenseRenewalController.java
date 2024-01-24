/**
 * 
 */
package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.service.IPlumberLicenseFormService;
import com.abm.mainet.water.ui.model.PlumberLicenseRenewalModel;

@Controller
@RequestMapping("/PlumberLicenseRenewal.html")
public class PlumberLicenseRenewalController extends AbstractFormController<PlumberLicenseRenewalModel> {

	@Autowired
	private IPlumberLicenseFormService plumberLicenseService;

	@Autowired
	private IServiceMasterService serviceMaster;

	@Autowired
	IPortalServiceMasterService iPortalService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		final PlumberLicenseRenewalModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (model.getPlumberLicenseReqDTO() == null)
			model.setPlumberLicenseReqDTO(new PlumberLicenseRequestDTO());
		model.getPlumberLicenseReqDTO().setOrgId(orgId);
		model.setFlag("Y");
		final Long serviceId = serviceMaster.getServiceId("PLR", orgId);
		final Long deptId = iPortalService.getServiceDeptIdId(serviceId);
		model.setServiceId(serviceId);
		getModel().setDeptId(deptId);
		this.getModel()
				.setLookUpList(CommonMasterUtility.getListLookup("ECN", UserSession.getCurrent().getOrganisation()));
		ModelAndView mv = null;
		mv = new ModelAndView("PlumberLicenseRenewal", MainetConstants.FORM_NAME, getModel());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getLicApplicantDet")
	public ModelAndView searchWaterRecords(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);

		final PlumberLicenseRenewalModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (model.getPlumberLicenseReqDTO().getPlumberLicenceNo().isEmpty()) {
			model.setFlag("Y");
			getModel().addValidationError(ApplicationSession.getInstance().getMessage("enter.license"));
		} else {

			PlumberLicenseRequestDTO result = plumberLicenseService.getPlumberDetailsByLicenseNumber(orgId,
					model.getPlumberLicenseReqDTO().getPlumberLicenceNo());

			if (result != null) {
				model.setFlag("N");
				model.setApplicantDetailDto(result.getApplicant());
				model.setPlumberLicenseReqDTO(result);
				model.setPlumberExperienceDTOList(result.getPlumberExperienceDTOList());
				// model.populateApplicationData(result.getApplicationId());
			} else {

				model.setFlag("Y");
				getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));

			}

		}

		ModelAndView mv = null;
		mv = new ModelAndView("PlumberLicenseRenewalValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	@RequestMapping(method = RequestMethod.POST, params = "getCheckListAndCharges")
	public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView modelAndView = null;
		final PlumberLicenseRenewalModel model = getModel();
		try {

			String serviceCode = "PLR";
			model.getPlumberLicenseReqDTO().setServiceId(model.getServiceId());
			model.getPlumberLicenseReqDTO().setOrgId(orgId);
			model.findApplicableCheckListAndCharges(serviceCode, orgId);

			modelAndView = new ModelAndView("PlumberLicenseRenewalValidn", MainetConstants.REQUIRED_PG_PARAM.COMMAND,
					model);
			if (model.getBindingResult() != null) {
				modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						getModel().getBindingResult());
			}
		} catch (final Exception ex) {
			logger.error("Exception has been occurred in getChecklist and charges", ex);
			modelAndView = defaultExceptionFormView();
		}
		return modelAndView;
	}

	@Override
	@RequestMapping(params = "saveLicRenewal", method = RequestMethod.POST)
	public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		final PlumberLicenseRenewalModel model = getModel();
		ModelAndView modelAndView = null;
		if (model.saveForm()) {
			final CommonChallanDTO offline = model.getOfflineDTO();
			if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {
				return jsonResult(JsonViewObject.successResult(getApplicationSession()
						.getMessage("Your Application No. " + model.getPlumberLicenseReqDTO().getApplicationId()
								+ " for Plumber License Renewal has been saved successfully.")));
			} else {
				return jsonResult(JsonViewObject.successResult(getApplicationSession()
						.getMessage("Your Application No. " + model.getPlumberLicenseReqDTO().getApplicationId()
								+ " for Plumber License Renewal has been saved successfully.")));
			}
		}

		modelAndView = new ModelAndView("PlumberLicenseRenewalValidn", MainetConstants.FORM_NAME, getModel());
		modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
				getModel().getBindingResult());
		return modelAndView;
	}

}
