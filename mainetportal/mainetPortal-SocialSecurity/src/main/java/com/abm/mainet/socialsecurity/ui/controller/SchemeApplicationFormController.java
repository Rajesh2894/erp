/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.socialsecurity.service.ISchemeApplicationFormService;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;
import com.abm.mainet.socialsecurity.ui.dto.CriteriaDto;
import com.abm.mainet.socialsecurity.ui.model.SchemeApplicationFormModel;
import com.abm.mainet.socialsecurityl.ui.validator.ApplicationFormValidator;

/**
 * @author priti.singh
 *
 */
@Controller
@RequestMapping("SchemeApplicationForm.html")
public class SchemeApplicationFormController extends AbstractFormController<SchemeApplicationFormModel> {

	@Autowired
	private ICommonBRMSService brmsCommonService;
	@Autowired
	private IFileUploadService fileUpload;
	@Autowired
	private ISchemeApplicationFormService schemeApplicationFormService;

	private static final Logger LOGGER = Logger.getLogger(SchemeApplicationFormController.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		SchemeApplicationFormModel appModel = this.getModel();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		appModel.getAndSetPrefix(orgId, langId, org);
		this.getModel().setCommonHelpDocs("SchemeApplicationForm.html");

		return index();

	}

	@RequestMapping(params = "saveCheckListAppdetails", method = RequestMethod.POST)
	public ModelAndView saveCheckListAppdetails(final Model model, final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Long langId = (long) UserSession.getCurrent().getLanguageId();
		String ulbName = UserSession.getCurrent().getOrganisation().getOrgShortNm();
		String ipMacAddress = Utility.getClientIpAddress(httpServletRequest);
		SchemeApplicationFormModel appModel = this.getModel();
		ApplicationFormDto dto = appModel.getApplicationformdto();
		dto.setOrgId(orgId);
		dto.setCreatedBy(empId);
		dto.setCreatedDate(new Date());
		dto.setLgIpMac(ipMacAddress);
		dto.setLangId(langId);
		dto.setUlbName(ulbName);
		dto.setServiceCode("IGNDS");
		appModel.validateBean(dto, ApplicationFormValidator.class);
		// this call is for checklist validation
		List<DocumentDetailsVO> docs = appModel.getCheckList();
		if (docs != null) {
			// docs = fileUpload.setFileUploadMethod(docs);
			docs = fileUpload.convertFileToByteString(docs);
		}
		setGridId(appModel);
		dto.setDocumentList(docs);
		FileUploadServiceValidator.getCurrent().validateUpload(appModel.getBindingResult());
		// test fileUpload.validateUpload(appModel.getBindingResult());
		// this call is for checklist validation end
		if (appModel.hasValidationErrors()) {
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, appModel.getBindingResult());
			return new ModelAndView(MainetConstants.SocialSecurity.CHECK_LIST_FORM, MainetConstants.FORM_NAME,
					appModel);
		}
		JsonViewObject respObj;
		if (appModel.saveForm()) {
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage(appModel.getSuccessMessage()));

		} else {
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("Not Save Successfully"));

		}
		return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

		/* return new ModelAndView(new MappingJackson2JsonView()); */
	}

	@RequestMapping(params = "showCheckList", method = RequestMethod.POST)
	public ModelAndView showCheckList(final Model model, final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		SchemeApplicationFormModel schemeAppmodel = this.getModel();
		try {
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			List<DocumentDetailsVO> docs = null;
			final WSRequestDTO initRequestDto = new WSRequestDTO();
			initRequestDto.setModelName(MainetConstants.SocialSecurity.SOCIAL_CHECKLIST);
			WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
			if (response.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				List<Object> checklist = JersyCall.castResponse(response, CheckListModel.class, 0);
				CheckListModel checkListModel = (CheckListModel) checklist.get(0);
				checkListModel.setOrgId(orgId);
                //D#132886
				checkListModel.setServiceCode("SAA");
				final WSRequestDTO checkRequestDto = new WSRequestDTO();
				checkRequestDto.setDataModel(checkListModel);
				docs = brmsCommonService.getChecklist(checkListModel);
				if (response.getWsStatus() != null
						&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
					// docs = (List<DocumentDetailsVO>) checklistResp.getResponseObj();
					if (docs != null && !docs.isEmpty()) {
						long cnt = 1;
						for (final DocumentDetailsVO doc : docs) {
							doc.setDocumentSerialNo(cnt);
							cnt++;
						}
					}
					schemeAppmodel.setCheckList(docs);

				}
				return new ModelAndView(MainetConstants.SocialSecurity.CHECK_LIST_FORM, MainetConstants.FORM_NAME,
						schemeAppmodel);

			} else {
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
			}
		} catch (FrameworkException e) {
			LOGGER.info(e.getErrMsg());
			schemeAppmodel.setCheckList(null);
			return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
		}

	}

	public void setGridId(SchemeApplicationFormModel appModel) {
		CriteriaDto dto = new CriteriaDto();

		List<Long> factorsId = new ArrayList<Long>();
		List<Long> criteriaId = new ArrayList<Long>();
		Long ageCriteria = 0l;
		List<LookUp> lookups = CommonMasterUtility.getLevelData(MainetConstants.SocialSecurity.FTR, 1,
				UserSession.getCurrent().getOrganisation());
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		// set the factors values
		Long categoryFactorId = appModel.getCategoryList().get(0).getLookUpParentId();
		Long bplFactorId = appModel.getBplList().get(0).getLookUpParentId();
		Long educationFactorId = appModel.getEducationList().get(0).getLookUpParentId();
		Long genderFactorId = appModel.getGenderList().get(0).getLookUpParentId();
		Long maritalFactorId = appModel.getMaritalstatusList().get(0).getLookUpParentId();
		Long disabilityFactorId = appModel.getTypeofdisabilityList().get(0).getLookUpParentId();
		Long ageFactorId = lookups.get(0).getLookUpId();
		// adding factors to a list
		factorsId.addAll(Arrays.asList(categoryFactorId, bplFactorId, educationFactorId, genderFactorId,
				maritalFactorId, disabilityFactorId));

		// set the criteria values
		Long categoryId = appModel.getApplicationformdto().getCategoryId() != null
				? appModel.getApplicationformdto().getCategoryId()
				: 0l;
		Long bplId = appModel.getApplicationformdto().getBplid() != null ? appModel.getApplicationformdto().getBplid()
				: 0l;
		Long genderId = appModel.getApplicationformdto().getGenderId() != null
				? appModel.getApplicationformdto().getGenderId()
				: 0l;
		Long maritalId = appModel.getApplicationformdto().getMaritalStatusId() != null
				? appModel.getApplicationformdto().getMaritalStatusId()
				: 0l;
		Long disabilityId = appModel.getApplicationformdto().getTypeofDisId() != null
				? appModel.getApplicationformdto().getTypeofDisId()
				: 0l;
		Long educationId = appModel.getApplicationformdto().getEducationId() != null
				? appModel.getApplicationformdto().getEducationId()
				: 0l;
		// adding criteria to a list
		List<LookUp> lookupsCriteria = CommonMasterUtility.getLevelData(MainetConstants.SocialSecurity.FTR, 2,
				UserSession.getCurrent().getOrganisation());
		for (LookUp l : lookupsCriteria) {
			if (l.getLookUpParentId() == ageFactorId) {

				if (l.getLookUpCode().equalsIgnoreCase("MAE") && appModel.getApplicationformdto().getAgeason() >= 1
						&& appModel.getApplicationformdto().getAgeason() <= 17) {
					ageCriteria = l.getLookUpId();
					break;
				} else if (l.getLookUpCode().equalsIgnoreCase("ADT")
						&& appModel.getApplicationformdto().getAgeason() >= 18
						&& appModel.getApplicationformdto().getAgeason() <= 57) {
					ageCriteria = l.getLookUpId();
					break;
				} else if (l.getLookUpCode().equalsIgnoreCase("ODA")
						&& appModel.getApplicationformdto().getAgeason() > 57) {
					ageCriteria = l.getLookUpId();
					break;
				}

			}

		}

		criteriaId
				.addAll(Arrays.asList(categoryId, bplId, genderId, maritalId, disabilityId, educationId, ageCriteria));

		dto.setCriterias(criteriaId);
		dto.setFactrors(factorsId);
		dto.setOrgId(orgId);
		dto.setServiceId(appModel.getApplicationformdto().getSelectSchemeName());

		Long gridId = schemeApplicationFormService.getCriteriaGridId(dto);
		appModel.getApplicationformdto().setGridId(gridId);

	}

}
