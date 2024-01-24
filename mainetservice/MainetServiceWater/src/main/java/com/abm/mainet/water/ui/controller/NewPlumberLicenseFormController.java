/**
 * 
 */
package com.abm.mainet.water.ui.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.ui.validator.FileUploadServiceValidator;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dto.PlumberExperienceDTO;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;
import com.abm.mainet.water.dto.PlumberQualificationDTO;
import com.abm.mainet.water.service.PlumberLicenseService;
import com.abm.mainet.water.ui.model.PlumberLicenseFormModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/NewPlumberLicenseForm.html")
public class NewPlumberLicenseFormController extends AbstractFormController<PlumberLicenseFormModel> {

	@Resource
	private PlumberLicenseService plumberLicenseService;

	@Resource
	private ServiceMasterService service;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		this.getModel()
				.setLookUpList(CommonMasterUtility.getListLookup("ECN", UserSession.getCurrent().getOrganisation()));
		ModelAndView mv = null;
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
			mv = new ModelAndView("NewPlumberLicenseFormNew", MainetConstants.FORM_NAME, getModel());
		}else {
			mv = new ModelAndView("PlumberLicenseDesclaimer", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(MainetConstants.CommonConstants.COMMAND, getModel());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getPlumberForm")
	public ModelAndView plumberForm(final HttpServletRequest httpServletRequest) {
		ModelAndView mv = null;
		mv = new ModelAndView("NewPlumberLicenseForm", MainetConstants.FORM_NAME, getModel());
		mv.addObject(MainetConstants.CommonConstants.COMMAND, getModel());
		return mv;
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "getCheckListAndCharges")
	public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView modelAndView = null;
		final PlumberLicenseFormModel model = getModel();
		boolean isPSCL = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL);
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
			if (!isPSCL && ((model.getPlumberImage() == null) || model.getPlumberImage().isEmpty())) {
				getModel().addValidationError(
						ApplicationSession.getInstance().getMessage("water.plumberLicense.valMsg.uploadPlumberPhoto"));
				return customDefaultMyResult("NewPlumberLicenseForm");
			} else {
				getModel().findApplicableCheckListAndCharges("WPL", orgId);
				modelAndView = new ModelAndView("NewPlumberLicenseFormValidn", MainetConstants.CommonConstants.COMMAND,
						getModel());
				if (getModel().getBindingResult() != null) {
					modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							getModel().getBindingResult());
				}
			}
		} catch (final Exception ex) {
			modelAndView = defaultExceptionFormView();
		}

		return modelAndView;
	}

	@RequestMapping(params = "SaveAndViewApplication", method = RequestMethod.POST)
	public ModelAndView SaveAndViewApplication(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ModelAndView mv = new ModelAndView("NewPlumberLicenseForm", MainetConstants.FORM_NAME, getModel());
		final PlumberLicenseFormModel model = getModel();
		final PlumberLicenseRequestDTO reqDTO = model.getPlumberLicenseReqDTO();
		setPlumberLicenseData();
		reqDTO.setApplicant(model.getApplicantDetailDto());
		List<DocumentDetailsVO> docs = model.getCheckList();
		docs = model.setFileUploadMethod(docs);
		reqDTO.setDocumentList(docs);
		if (!model.validateInputs(docs, reqDTO.getPlumberImage())) {
			PlumberLicenseResponseDTO outPutObject = new PlumberLicenseResponseDTO();

			outPutObject = plumberLicenseService.savePlumberLicenseDetails(reqDTO);
			if (outPutObject != null && (outPutObject.getStatus() != null)
					&& outPutObject.getStatus().equals(PrefixConstants.NewWaterServiceConstants.SUCCESS)) {

				if (PrefixConstants.NewWaterServiceConstants.SUCCESS.equals(outPutObject.getStatus())) { // free
					plumberLicenseService.initiateWorkFlowForFreeService(reqDTO);
				}
				if ((model.getIsFree() != null)
						&& model.getIsFree().equals(PrefixConstants.NewWaterServiceConstants.NO)) {
					model.save();

				}
			} else {
				return mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
			}
		} else {
			mv = customDefaultMyResult("NewPlumberLicenseForm");
		}

		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	private void setPlumberLicenseData() {
		final UserSession session = UserSession.getCurrent();
		final PlumberLicenseFormModel model = getModel();
		final PlumberLicenseRequestDTO requestDTO = model.getPlumberLicenseReqDTO();
		ApplicantDetailDTO dto = model.getApplicantDetailDto();
		requestDTO.setPlumberQualificationDTOList(model.getPlumberQualificationDTOList());
		requestDTO.setPlumberExperienceDTOList(model.getPlumberExperienceDTOList());
		final ServiceMaster service1 = service.getServiceMasterByShortCode("WPL",
				UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setServiceId(service1.getSmServiceId());
		model.setServiceId(service1.getSmServiceId());
		requestDTO.setServiceId(service1.getSmServiceId());
		requestDTO.setOrgId(session.getOrganisation().getOrgid());
		requestDTO.setApplicant(dto);
		requestDTO.setDeptId(service1.getTbDepartment().getDpDeptid());
		requestDTO.setPlumberFName(dto.getApplicantFirstName());
		requestDTO.setPlumberMName(dto.getApplicantMiddleName());
		requestDTO.setPlumberLName(dto.getApplicantLastName());
		requestDTO.setPlumAppDate(new Date());
		requestDTO.setAmount(model.getAmountToPay());

		requestDTO.setUserId(session.getEmployee().getEmpId());
		requestDTO.setLangId((long) session.getLanguageId());
		requestDTO.setLgIpMac(session.getEmployee().getEmppiservername());
		final List<PlumberQualificationDTO> qualificationDTOs = requestDTO.getPlumberQualificationDTOList();
		final List<PlumberExperienceDTO> experienceDTOs = requestDTO.getPlumberExperienceDTOList();

		List<PlumberQualificationDTO> plumberQualificationsList = new ArrayList<>(0);
		PlumberQualificationDTO plumberQualification = null;
		for (final PlumberQualificationDTO qualification : qualificationDTOs) {

			plumberQualification = new PlumberQualificationDTO();
			plumberQualification.setPlumQualification(qualification.getPlumQualification());
			plumberQualification.setPlumInstituteName(qualification.getPlumInstituteName());
			plumberQualification.setPlumInstituteAddress(qualification.getPlumInstituteAddress());
			plumberQualification.setPlumPassYear(qualification.getPlumPassYear());
			plumberQualification.setPlumPassMonth(qualification.getPlumPassMonth());
			plumberQualification.setPlumPercentGrade(qualification.getPlumPercentGrade());
			plumberQualification.setUserId(session.getEmployee().getEmpId());
			plumberQualification.setLangId((int) (long) session.getLanguageId());
			plumberQualification.setOrgId(session.getOrganisation().getOrgid());
			plumberQualification.setLgIpMac(session.getEmployee().getEmppiservername());
			plumberQualificationsList.add(plumberQualification);
		}

		PlumberExperienceDTO plumberExperience = null;
		final List<PlumberExperienceDTO> plumberExperiencesList = new ArrayList<>();
		for (final PlumberExperienceDTO experienceDTO : experienceDTOs) {
			plumberExperience = new PlumberExperienceDTO();
			plumberExperience.setPlumCompanyName(experienceDTO.getPlumCompanyName());
			plumberExperience.setPlumCompanyAddress(experienceDTO.getPlumCompanyAddress());
			plumberExperience.setUserId(session.getEmployee().getEmpId());
			plumberExperience.setLangId((int) (long) session.getLanguageId());
			plumberExperience.setOrgId(session.getOrganisation().getOrgid());
			plumberExperience.setLgIpMac(session.getEmployee().getEmppiservername());
			plumberExperience.setLmodDate(new Date());
			plumberExperiencesList.add(plumberExperience);
		}
	}

	@RequestMapping(params = "getUploadedImage", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody final String getdataOfUploadedImage(final HttpServletRequest httpServletRequest) {
		try {

			if ((FileUploadUtility.getCurrent().getFileMap() != null)
					&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
				for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
					if ((entry.getKey() != null) && (entry.getKey().longValue() == 0)) {
						for (final File file : entry.getValue()) {
							String fileName = null;
							try {
								final String path = file.getPath().replace(MainetConstants.DOUBLE_BACK_SLACE,
										MainetConstants.operator.FORWARD_SLACE);
								fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
							} catch (final Exception e) {
								e.printStackTrace();
							}

							return fileName;
						}
					}

				}
			}
			return MainetConstants.BLANK;
		} catch (final Exception e) {

			e.printStackTrace();
		}
		return MainetConstants.BLANK;
	}

}
