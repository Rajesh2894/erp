package com.abm.mainet.water.ui.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.master.ui.validator.FileUploadServiceValidator;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.service.PlumberLicenseService;
import com.abm.mainet.water.ui.model.PlumberLicenseFormModel;

@Controller
@RequestMapping("/PlumberRenewalApproval.html")
public class PlumberRenewalApprovalController extends AbstractFormController<PlumberLicenseFormModel> {

	@Autowired
	PlumberLicenseService plumberLicenseService;

	@Autowired
	ServiceMasterService iServiceMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		final PlumberLicenseFormModel model = getModel();
		model.getPlumberLicenseReqDTO().setFlag(MainetConstants.FlagY);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		model.setServiceCode("PLR");
		this.getModel()
				.setLookUpList(CommonMasterUtility.getListLookup("ECN", UserSession.getCurrent().getOrganisation()));
		ModelAndView mv = null;
		model.setFromEvent(true);
		mv = new ModelAndView("PlumberRenewalApprove", MainetConstants.FORM_NAME, getModel());
		mv.addObject(MainetConstants.CommonConstants.COMMAND, getModel());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getApplicantDetails")
	public ModelAndView searchApplicantRecords(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);

		final PlumberLicenseFormModel model = getModel();
		if (model.getPlumberLicenseReqDTO().getApplicationId() == null
				&& model.getPlumberLicenseReqDTO().getReceiptNo() == null) {
			getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.enter.data"));
		} else {
			List<Object[]> result = plumberLicenseService.getReceiptDetails(model.getPlumberLicenseReqDTO());
			Long receiptNo = model.getPlumberLicenseReqDTO().getReceiptNo();
			if (result == null) {
				getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));
			} else {
				model.getPlumberLicenseReqDTO().setFlag(MainetConstants.FlagN);
				Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
				final ServiceMaster service = iServiceMasterService.getServiceMasterByShortCode("PLR", orgId);
				if (service.getSmServiceId() == model.getPlumberLicenseReqDTO().getServiceId().longValue()) {
					model.getPlumberLicenseReqDTO().setReceiptNo(receiptNo);
					model.populateApplicationData(model.getPlumberLicenseReqDTO().getApplicationId());
					Date curentDate = new Date();
					Calendar calendar = Calendar.getInstance();
					Integer year = calendar.get(Calendar.YEAR);
					Integer nextYear = year + 1;
					String toDate = MainetConstants.Common_Constant.NUMBER.THREE
							+ MainetConstants.Common_Constant.NUMBER.ONE + MainetConstants.WINDOWS_SLASH
							+ MainetConstants.Common_Constant.NUMBER.ZERO_THREE + MainetConstants.WINDOWS_SLASH + year;
					String nextToDate = MainetConstants.Common_Constant.NUMBER.THREE
							+ MainetConstants.Common_Constant.NUMBER.ONE + MainetConstants.WINDOWS_SLASH
							+ MainetConstants.Common_Constant.NUMBER.ZERO_THREE + MainetConstants.WINDOWS_SLASH
							+ nextYear;
					Date validToDate = Utility.stringToDate(toDate);
					Date nextYrValidToDate = Utility.stringToDate(nextToDate);
					model.getPlumberLicenseReqDTO().setPlumRenewFromDate(new Date());

					if (curentDate.before(validToDate)) {
						model.getPlumberLicenseReqDTO().setPlumRenewToDate(validToDate);
					} else if (curentDate.after(validToDate)) {
						model.getPlumberLicenseReqDTO().setPlumRenewToDate(nextYrValidToDate);
					}
				} else {
					model.getPlumberLicenseReqDTO().setFlag(MainetConstants.FlagY);
					model.getPlumberLicenseReqDTO().setApplicationId(null);
					getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));
				}
			}
		}
		ModelAndView mv = null;

		mv = new ModelAndView("PlRenewalAppData", MainetConstants.CommonConstants.COMMAND, model);
		if (model.getBindingResult() != null) {
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		}

		return mv;

	}

	@RequestMapping(params = "updateRenewal", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateRenewal(final HttpServletRequest httpServletRequest) {
		this.bindModel(httpServletRequest);
		PlumberLicenseFormModel model = this.getModel();
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		model.getPlumberLicenseReqDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		String toDate = Utility.dateToString(model.getPlumberLicenseReqDTO().getPlumLicToDate());
		String renewToDate = Utility.dateToString(model.getPlumberLicenseReqDTO().getPlumRenewToDate());

		if (toDate.equals(renewToDate)) {
			getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.license.generated"));
			object.put(MainetConstants.WorksManagement.CHECK_FLAG, MainetConstants.FlagD);
		}

		boolean saveflag = plumberLicenseService.updateValiDates(model.getPlumberLicenseReqDTO());
		boolean renewFlag = plumberLicenseService.updatPlumberRenewalDates(model.getPlumberLicenseReqDTO());

		if (saveflag && renewFlag) {
			model.getPlumberLicenseReqDTO().setPlumLicFromDate(model.getPlumberLicenseReqDTO().getPlumRenewFromDate());
			model.getPlumberLicenseReqDTO().setPlumLicFromDate(model.getPlumberLicenseReqDTO().getPlumRenewToDate());
			object.put(MainetConstants.WorksManagement.CHECK_FLAG, MainetConstants.FlagY);
			object.put("licNo", model.getPlumberLicenseReqDTO().getPlumberLicenceNo());

		} else {
			object.put(MainetConstants.WorksManagement.CHECK_FLAG, MainetConstants.FlagN);
		}

		return object;
	}

	@RequestMapping(params = "printPlumberLicense", method = RequestMethod.POST)
	public ModelAndView printPlumberLicense(final HttpServletRequest httpServletRequest) {

		bindModel(httpServletRequest);
		final PlumberLicenseFormModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final ServiceMaster service = iServiceMasterService.getServiceMasterByShortCode("PLR", orgId);
		model.setServiceId(service.getSmServiceId());
		model.setServiceName(service.getSmServiceName());
		return new ModelAndView("PlumberLicenseLetter", MainetConstants.FORM_NAME, model);
	}
}
