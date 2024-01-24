package com.abm.mainet.water.ui.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.service.IPlumberLicenseFormService;
import com.abm.mainet.water.ui.model.PlumberLicenseFormModel;

@Controller
@RequestMapping("/PlumberLicenseForm.html")
public class PlumberLicenseFormController extends AbstractFormController<PlumberLicenseFormModel> {

	private static Logger log = Logger.getLogger(PlumberLicenseFormController.class);
	
	@Autowired
	private transient IServiceMasterService serviceMaster;
	@Autowired
	IPortalServiceMasterService iPortalService;

	@Autowired
	IPlumberLicenseFormService iPlumberLicenseFormService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		try {
			getModel().initializeApplicantDetail();
			final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			final Long serviceId = serviceMaster.getServiceId(MainetConstants.ServiceShortCode.PlUMBER_LICENSE, orgId);
			final Long deptId = iPortalService.getServiceDeptIdId(serviceId);
			getModel().setServiceId(serviceId);
			getModel().setDeptId(deptId);
			getModel().getPlumberQualificationDTOList().clear();
			getModel().setCommonHelpDocs("PlumberLicenseForm.html");
			this.getModel().setLookUpList(
					CommonMasterUtility.getListLookup("ECN", UserSession.getCurrent().getOrganisation()));
		} catch (final Exception ex) {
			log.error("Error Occurred while rendering Form:", ex);
			return defaultExceptionView();
		}
		ModelAndView mv = null;
		mv = new ModelAndView("PlumberLicenseDesclaimer", MainetConstants.FORM_NAME, getModel());
		mv.addObject(MainetConstants.REQUIRED_PG_PARAM.COMMAND, getModel());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getPlumberForm")
	public ModelAndView plumberForm(final HttpServletRequest httpServletRequest) {
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		ModelAndView mv = null;
		mv = new ModelAndView("PlumberLicenseForm", MainetConstants.FORM_NAME, getModel());
		mv.addObject(MainetConstants.REQUIRED_PG_PARAM.COMMAND, getModel());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getCheckListAndCharges")
	public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView modelAndView = null;
		final PlumberLicenseFormModel model = getModel();
		try {
			for (final Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (entry.getKey().longValue() == 0) {
					final Set<File> set = entry.getValue();
					final File file = set.iterator().next();
					String bytestring = MainetConstants.BLANK;
					final Base64 base64 = new Base64();
					try {
						bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
					} catch (final IOException e) {
						logger.error("Exception has been occurred in file byte to string conversions", e);
					}
					final String plumberImage = file.getName();
					model.setPlumberImage(plumberImage);
					model.getPlumberLicenseReqDTO().setPlumberImage(plumberImage);
					model.getPlumberLicenseReqDTO().setImageByteCode(bytestring);
					break;
				}
			}
			if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && (model.getPlumberImage() == null || model.getPlumberImage().isEmpty())) {
				getModel().addValidationError(
						ApplicationSession.getInstance().getMessage("water.plumberLicense.valMsg.uploadPlumberPhoto"));
			} else {
				model.findApplicableCheckListAndCharges(getModel().getServiceId(), orgId);
			}
			modelAndView = new ModelAndView("PlumberLicenseFormValidn", MainetConstants.REQUIRED_PG_PARAM.COMMAND,
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

	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView savePlumberLicenseForm(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final PlumberLicenseFormModel model = getModel();
		ModelAndView modelAndView = null;
		if (model.saveForm()) {
			final CommonChallanDTO offline = model.getOfflineDTO();
			if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {
				return jsonResult(JsonViewObject.successResult( 
						ApplicationSession.getInstance().getMessage("successBtn.appliNo") + MainetConstants.WHITE_SPACE + model.getPlumberLicenseReqDTO().getApplicationId()
								+ MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("water.successBtn.plumberLic.saveSuccess ")));
				
			} else {
				return jsonResult(JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("successBtn.appliNo") + MainetConstants.WHITE_SPACE+ model.getPlumberLicenseReqDTO().getApplicationId()
								+ MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("water.successBtn.plumberLic.saveSuccess")));
			}
		}

		modelAndView = new ModelAndView("PlumberLicenseFormValidn", MainetConstants.FORM_NAME, getModel());
		modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
				getModel().getBindingResult());
		return modelAndView;
	}

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView defaultLoad(@RequestParam("appId") final long appId,
			final HttpServletRequest httpServletRequest) throws Exception {
		getModel().bind(httpServletRequest);

		PlumberLicenseFormModel model = this.getModel();
		try {
			final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			PlumberLicenseRequestDTO requestDto = new PlumberLicenseRequestDTO();
			final PlumberLicenseRequestDTO response = iPlumberLicenseFormService.getApplicationDetails(appId, orgId);

			if (response != null) {
				this.getModel().setLookUpList(
						CommonMasterUtility.getListLookup("ECN", UserSession.getCurrent().getOrganisation()));
				model.setPlumberExperienceDTOList(response.getPlumberExperienceDTOList());
				model.setPlumberQualificationDTOList(response.getPlumberQualificationDTOList());
				model.setPlumberLicenseReqDTO(response);
				model.setApplicantDetailDto(response.getApplicant());
				model.querySearchResults(appId);
			}
		} catch (Exception exception) {
			throw new FrameworkException(exception);
		}
		return new ModelAndView("plumberLicenseDashboardView", MainetConstants.FORM_NAME, getModel());
	}
	
}
