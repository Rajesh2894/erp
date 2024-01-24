/**
 * 
 */
package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.service.IPlumberLicenseFormService;
import com.abm.mainet.water.ui.model.DuplicatePlumberLicenseModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Controller
@RequestMapping("/DuplicatePlumberLicense.html")
public class DuplicatePlumberLicenseController extends AbstractFormController<DuplicatePlumberLicenseModel> {
	@Autowired
	private IPlumberLicenseFormService plumberLicenseService;

	@Autowired
	private IServiceMasterService serviceMaster;

	@Autowired
	IPortalServiceMasterService iPortalService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		final DuplicatePlumberLicenseModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (model.getPlumberLicenseReqDTO() == null)
			model.setPlumberLicenseReqDTO(new PlumberLicenseRequestDTO());
		model.getPlumberLicenseReqDTO().setOrgId(orgId);
		model.setFlag("Y");
		final Long serviceId = serviceMaster.getServiceId("DPL", orgId);
		final Long deptId = iPortalService.getServiceDeptIdId(serviceId);
		model.setServiceId(serviceId);
		getModel().setDeptId(deptId);
		this.getModel()
				.setLookUpList(CommonMasterUtility.getListLookup("ECN", UserSession.getCurrent().getOrganisation()));
		ModelAndView mv = null;
		mv = new ModelAndView("DuplicatePlumberLicense", MainetConstants.FORM_NAME, getModel());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getLicApplicantDet")
	public ModelAndView searchWaterRecords(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);

		final DuplicatePlumberLicenseModel model = getModel();
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
				long dateCompare = 0;
				if (result.getPlumLicToDate() != null) {
					dateCompare = Utility.getCompareDate(result.getPlumLicToDate());
				}
				if (result.getPlumberLicenceNo() == null || result.getPlumberId() == 0l) {
					model.setFlag(MainetConstants.FlagY);
					getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));
				} else if (dateCompare < 0l) {
					model.setFlag(MainetConstants.FlagY);
					getModel().addValidationError(
							ApplicationSession.getInstance().getMessage("water.duplicatePlumber.RenewalMsg"));
				}
			} else {
				model.setFlag("Y");
				getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));
			}
		}
		ModelAndView mv = null;
		mv = new ModelAndView("DuplicatePlumberLicenseValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getCheckListAndCharges")
	public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView modelAndView = null;
		final DuplicatePlumberLicenseModel model = getModel();
		try {
			String serviceCode = "DPL";
			model.getPlumberLicenseReqDTO().setServiceId(model.getServiceId());
			model.getPlumberLicenseReqDTO().setOrgId(orgId);
			model.findApplicableCheckListAndCharges(serviceCode, orgId);
			modelAndView = new ModelAndView("DuplicatePlumberLicenseValidn", MainetConstants.REQUIRED_PG_PARAM.COMMAND,
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

	@RequestMapping(params = "saveDuplicatePlumberLicense", method = RequestMethod.POST)
	public ModelAndView saveDuplicatePlumberLicense(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final DuplicatePlumberLicenseModel model = getModel();
		ModelAndView modelAndView = null;
		try {
			if (model.saveForm()) {
				final CommonChallanDTO offline = model.getOfflineDTO();
				if ((offline.getOnlineOfflineCheck() != null)
						&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {
					return jsonResult(JsonViewObject.successResult(getApplicationSession()
							.getMessage("Your Application No. " + model.getPlumberLicenseReqDTO().getApplicationId()
									+ " for Duplicate Plumber License has been saved successfully.")));
				} else {
					return jsonResult(JsonViewObject.successResult(getApplicationSession()
							.getMessage("Your Application No. " + model.getPlumberLicenseReqDTO().getApplicationId()
									+ " for Duplicate Plumber License has been saved successfully.")));
				}
			}
		} catch (final Exception e1) {
			throw new FrameworkException(
					"Sorry,Your application for Plumber has not been saved due to some technical problem.", e1);
		}
		modelAndView = new ModelAndView("DuplicatePlumberLicenseValidn", MainetConstants.FORM_NAME, getModel());
		modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
				getModel().getBindingResult());
		return modelAndView;
	}

}
