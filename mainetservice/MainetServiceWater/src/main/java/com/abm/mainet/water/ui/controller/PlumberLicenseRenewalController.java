package com.abm.mainet.water.ui.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.ui.validator.FileUploadServiceValidator;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;
import com.abm.mainet.water.service.PlumberLicenseService;
import com.abm.mainet.water.ui.model.PlumberLicenseFormModel;

@Controller
@RequestMapping("/PlumberLicenseRenewal.html")
public class PlumberLicenseRenewalController extends AbstractFormController<PlumberLicenseFormModel> {

	@Autowired
	private PlumberLicenseService plumberLicenseService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	TbFinancialyearService tbFinancialyearService;

	@Resource
	private IFileUploadService fileUpload;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		final PlumberLicenseFormModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (model.getPlumberLicenseReqDTO() == null)
			model.setPlumberLicenseReqDTO(new PlumberLicenseRequestDTO());
		model.getPlumberLicenseReqDTO().setOrgId(orgId);
		model.getPlumberLicenseReqDTO().setFlag(MainetConstants.FlagY);
		try {
			final ServiceMaster service = serviceMaster.getServiceMasterByShortCode("PLR", orgId);
			model.setServiceId(service.getSmServiceId());
			model.setServiceCode(service.getSmShortdesc());
		} catch (Exception ex) {
			throw new FrameworkException(
					"Service Master Not Configured for Plumber License Renewal For servicecode = PLR ", ex);
		}

		model.setFromEvent(false);
		this.getModel()
				.setLookUpList(CommonMasterUtility.getListLookup("ECN", UserSession.getCurrent().getOrganisation()));
		ModelAndView mv = null;
		mv = new ModelAndView("PlumberLicenseRenewal", MainetConstants.FORM_NAME, getModel());
		mv.addObject(MainetConstants.CommonConstants.COMMAND, getModel());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getLicApplicantDet")
	public ModelAndView searchWaterRecords(final HttpServletRequest httpServletRequest) throws Exception {
		bindModel(httpServletRequest);

		final PlumberLicenseFormModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (StringUtils.isBlank(model.getPlumberLicenseReqDTO().getPlumberLicenceNo())) {
			model.getPlumberLicenseReqDTO().setFlag(MainetConstants.FlagY);
			getModel().addValidationError(ApplicationSession.getInstance().getMessage("enter.license"));
		} else {
			PlumberLicenseRequestDTO result = plumberLicenseService.getPlumberDetailsByLicenseNumber(orgId,
					model.getPlumberLicenseReqDTO().getPlumberLicenceNo());
			FinancialYear financialYear = tbFinancialyearService.getFinanciaYearByDate(new Date());
			if (result != null) {
				if (!Utility.compareDate(result.getPlumLicToDate(), financialYear.getFaToDate())
						|| result.getPlumLicToDate().compareTo(financialYear.getFaToDate()) == 0) {
					model.addValidationError(ApplicationSession.getInstance().getMessage("Plumber Licence Expiry Date")
							+ " " + result.getPlumLicToDate() + " " + "You are not eligible to apply this service");
				} else {
					String renewalMsg = plumberLicenseService.getRenewalValidateMsg(result.getPlumberId());
					if (StringUtils.isNoneBlank(renewalMsg)) {
						getApplicationSession();
						model.addValidationError(ApplicationSession.getInstance().getMessage(renewalMsg));
					} else {
						result.setFlag(MainetConstants.FlagN);
						model.setPlumberLicenseReqDTO(result);
						model.setApplicationData(result.getPlumberId(), result.getApplicant());
					}
				}
			} else {
				model.getPlumberLicenseReqDTO().setFlag(MainetConstants.FlagY);
				getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));
			}
		}

		ModelAndView mv = new ModelAndView("PlumberLicenseRenewalValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getCheckListAndCharges")
	public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView modelAndView = null;
		final PlumberLicenseFormModel model = getModel();
		try {
			final ServiceMaster service = serviceMaster.getServiceByShortName("PLR", orgId);
			String serviceCode = "PLR";
			model.setServiceId(service.getSmServiceId());
			model.getPlumberLicenseReqDTO().setServiceId(service.getSmServiceId());
			model.getPlumberLicenseReqDTO().setOrgId(orgId);
			model.findApplicableCheckListAndCharges(serviceCode, orgId);

			modelAndView = new ModelAndView("PlumberLicenseRenewalValidn", MainetConstants.CommonConstants.COMMAND,
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
		JsonViewObject respObj = null;
		this.bindModel(httpServletRequest);
		PlumberLicenseFormModel model = this.getModel();
		fileUploadService.validateUpload(model.getBindingResult());
		if (model.hasValidationErrors()) {
			return defaultMyResult();
		}
		PlumberLicenseResponseDTO responseDTO = new PlumberLicenseResponseDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Integer langId = UserSession.getCurrent().getLanguageId();
		Long userId = UserSession.getCurrent().getEmployee().getEmpId();
		final Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE,
				MainetConstants.STATUS.ACTIVE);
		model.getPlumberLicenseReqDTO().setDeptId(deptId);
		model.getPlumberLicenseReqDTO().setOrgId(orgId);
		model.getPlumberLicenseReqDTO().setLangId(langId.longValue());
		model.getPlumberLicenseReqDTO().setUserId(userId);

		try {

			List<DocumentDetailsVO> docs = model.mapCheckList(model.getCheckList(), model.getBindingResult());
			if (model.hasValidationErrors()) {
				return defaultMyResult();
			}
			model.getPlumberLicenseReqDTO().setDocumentList(docs);
			model.getPlumberLicenseReqDTO().setAmount(model.getAmountToPay());
			responseDTO = plumberLicenseService.savePlumberRenewalData(model.getPlumberLicenseReqDTO());
			if (responseDTO != null && (responseDTO.getStatus() != null)
					&& responseDTO.getStatus().equals(PrefixConstants.NewWaterServiceConstants.SUCCESS)) {
				if ((model.getIsFree() != null)
						&& model.getIsFree().equals(PrefixConstants.NewWaterServiceConstants.NO)) {
					model.save();
					return jsonResult(JsonViewObject.successResult(
							getApplicationSession().getMessage("water.plum.app.no ") + responseDTO.getApplicationId()
									+ getApplicationSession().getMessage ("water.plum.ren.save")));

				}
				return jsonResult(JsonViewObject.successResult(
						getApplicationSession().getMessage("water.plum.app.no") + responseDTO.getApplicationId()
								+ getApplicationSession().getMessage("water.plum.ren.save")));
			} else {
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("water.application.failure"));
			}
		} catch (final Exception e1) {
			throw new FrameworkException(
					"Sorry,Your application for Plumber has not been saved due to some technical problem.", e1);
		}

		return jsonResult(respObj);
	}

}
