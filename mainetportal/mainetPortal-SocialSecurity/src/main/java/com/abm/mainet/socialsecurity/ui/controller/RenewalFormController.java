/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.service.IServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.socialsecurity.service.RenewalFormService;
import com.abm.mainet.socialsecurity.ui.dto.RenewalFormDto;
import com.abm.mainet.socialsecurity.ui.dto.SchemeAppEntityToDto;
import com.abm.mainet.socialsecurity.ui.model.RenewalFormModel;

/**
 * @author priti.singh
 *
 */
@Controller
@RequestMapping("RenewalForm.html")
public class RenewalFormController extends AbstractFormController<RenewalFormModel> {

	@Autowired
	IServiceMasterService iPortalService;

	@Autowired
	private IPortalServiceMasterService portalServiceMasterService;

	@Autowired
	private RenewalFormService renewalFormService;
	
	

	private static final Logger LOGGER = Logger.getLogger(RenewalFormController.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();

		RenewalFormModel renModel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PortalService portalService = iPortalService
				.getPortalServiceMaster(MainetConstants.SocialSecurity.RENEWAL_OF_LIFE_CERTIFICATE_SERVICE_CODE, orgId);
		renModel.setServiceId(portalService.getServiceId());
		renModel.setDeptId(portalService.getPsmDpDeptid());
		this.getModel().setCommonHelpDocs("RenewalForm.html");
		return defaultResult();
	}

// save renewal form details

	@RequestMapping(params = "saveRenewalFormDetails", method = RequestMethod.POST)
	public ModelAndView saveRenewalFormDetails(final Model model, final HttpServletRequest httpServletRequest) {

		bindModel(httpServletRequest);
		JsonViewObject respObj;
		ModelAndView mv = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Long langId = (long) UserSession.getCurrent().getLanguageId();
		String ulbName = UserSession.getCurrent().getOrganisation().getOrgShortNm();
		String ipMacAddress = Utility.getClientIpAddress(httpServletRequest);
		RenewalFormModel renModel = this.getModel();
		RenewalFormDto dto = renModel.getRenewalFormDto();

		dto.setOrgId(orgId);
		dto.setCreatedBy(empId);
		dto.setCreatedDate(new Date());
		dto.setLgIpMac(ipMacAddress);
		dto.setLangId(langId);
		dto.setUlbName(ulbName);

		RequestDTO requestDTO1 = new RequestDTO();
		requestDTO1.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO1.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDTO1.setDepartmentName("Social WelFare Department");
		requestDTO1.setStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
		requestDTO1.setIdfId(dto.getApplicationId().toString());
		requestDTO1.setDepartmentName(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		requestDTO1.setApplicationId(dto.getApplicationId());
		requestDTO1.setDeptId(renModel.getDeptId());
		requestDTO1.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
		requestDTO1.setServiceId(renModel.getServiceId());
		requestDTO1.setReferenceId(dto.getApplicationId().toString());

		dto.setUploaddoc(this.getModel().getFileUploadList(renModel.getUploadFileList()));

		if (renModel.validateInputs()) {
		if (renModel.saveForm()) {
			return jsonResult(
                    JsonViewObject.successResult(ApplicationSession.getInstance().getMessage(renModel.getSuccessMessage())));
		} else {
			return jsonResult(
                    JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("social.sec.notsave.success")));

		}
		}
		mv = new ModelAndView("RenewalFormValidn", MainetConstants.FORM_NAME, getModel());

        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;

	}

// Get data on enter beneficiary number

	@RequestMapping(params = "getDataonBenefNo", method = RequestMethod.POST)
	public ModelAndView getDataonBenefNo(final HttpServletRequest httpServletRequest) {

		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		RenewalFormModel model = this.getModel();
		RenewalFormDto dto = model.getRenewalFormDto();
		dto.setOrgId(orgId);

		SchemeAppEntityToDto entity = renewalFormService.fetchDataOnBenef(dto);
		if (entity != null) {
			final Long serviceId = portalServiceMasterService
					.getServiceId(MainetConstants.SocialSecurity.RENEWAL_OF_LIFE_CERTIFICATE_SERVICE_CODE, orgId);

			PortalService portalservice = portalServiceMasterService.getService(entity.getSelectSchemeName(), orgId);

			final Long deptId = portalServiceMasterService.getServiceDeptIdId(serviceId);
			dto.setDeptId(deptId);
			dto.setServiceId(serviceId);

			dto.setSelectSchemeName(entity.getSelectSchemeName());
			dto.setNameofApplicant(entity.getNameofApplicant());
			dto.setSelectSchemeNamedesc(portalservice.getServiceName());
			//dto.setSelectSchemeNamedesc("PM Cares");
			dto.setLastDateofLifeCerti(entity.getLastDateofLifeCerti());
			dto.setApplicationId(entity.getApplicationId());
			if(entity.getApmApplicationId()!=null) {
			dto.setApplicationId(Long.valueOf(entity.getApmApplicationId()));
			}
			dto.setOrgId(entity.getOrgId());
			dto.setMobNum(entity.getMobNum());
		} else {
			model.addValidationError(ApplicationSession.getInstance().getMessage("social.sec.valid.benefnum"));
		}
		return defaultMyResult();

	}

}