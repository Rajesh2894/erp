
package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.property.dto.NoDuesCertificateDto;
import com.abm.mainet.property.service.PropertyNoDuesCertificateService;
import com.abm.mainet.property.ui.model.PropertyNoDuesCertificateModel;

/**
 * @author cherupelli.srikanth
 * @since 27 January 2021
 */
@Controller
@RequestMapping("/PropertyNoDuesCertificateAuthorization.html")
public class PropertyNoDuesCertificateAuthorizationController extends AbstractFormController<PropertyNoDuesCertificateModel>{
	
	@Autowired
	private PropertyNoDuesCertificateService propertyNoDuesCertificate;
	
	@Autowired
	private ServiceMasterService serviceMaster;
	
	@Autowired
    IFileUploadService fileUpload;
	
	@Autowired
    private IWorkFlowTypeService workFlowTypeService;
	
	@Autowired
	private ILocationMasService iLocationMasService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private IFinancialYearService iFinancialYear;

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appNo") final long applicationId, @RequestParam("taskId") final long serviceId,
			@RequestParam("actualTaskId") final long taskId, final HttpServletRequest httpServletRequest)
			throws Exception {
        this.sessionCleanup(httpServletRequest);
		PropertyNoDuesCertificateModel model = this.getModel();
		model.bind(httpServletRequest);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.getWorkflowActionDto().setTaskId(taskId);
		model.getWorkflowActionDto().setApplicationId(applicationId);
		ServiceMaster serviceDto = serviceMaster.getServiceMaster(serviceId, orgId);
		if (serviceDto != null) {
			model.setServiceMaster(serviceDto);
			model.setServiceId(serviceDto.getSmServiceId());
			model.setServiceName(serviceDto.getSmServiceName());
			model.setServiceShrtCode(serviceDto.getSmShortdesc());
			model.setDeptId(serviceDto.getTbDepartment().getDpDeptid());
		}
		model.setOrgId(orgId);
		NoDuesCertificateDto noDuesDto = new NoDuesCertificateDto();
		noDuesDto.setOrgId(orgId);
		noDuesDto.setApmApplicationId(applicationId);
		NoDuesCertificateDto dto = propertyNoDuesCertificate.getNoDuesDetails(noDuesDto);
		model.setNoDuesCertificateDto(dto);
		model.setApplicantDetailDto(dto.getApplicantDto());
		model.setAllowToGenerate(MainetConstants.Y_FLAG);
		model.setAuthFlag(MainetConstants.FlagY);
		model.setApmApplicationId(applicationId);
		model.setPayableFlag(MainetConstants.FlagN);

		model.setAttachmentList(iChecklistVerificationService.findAttachmentsForAppId(applicationId, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));

		List<LookUp> locList = model.getLocation();
		List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(orgId,
				serviceDto.getTbDepartment().getDpDeptid());
		if (location != null && !location.isEmpty()) {
			location.forEach(loc -> {
				LookUp lookUp = new LookUp();
				lookUp.setLookUpId(loc.getLocId());
				lookUp.setDescLangFirst(loc.getLocNameEng());
				lookUp.setDescLangSecond(loc.getLocNameReg());
				locList.add(lookUp);
			});
		}

		if (MainetConstants.Property.EXT.equals(model.getServiceShrtCode())
				|| MainetConstants.Property.DUBLICATE_BILL.equals(model.getServiceShrtCode())) {
			List<FinancialYear> financialYearList = iFinancialYear.getAllFinincialYear();
			String financialYear = null;
			Map<Long, String> yearMap = new HashMap<>(0);
			for (final FinancialYear finYearTemp : financialYearList) {
				try {
					financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
				} catch (Exception e) {
					e.printStackTrace();
				}
				yearMap.put(finYearTemp.getFaYear(), financialYear);
			}
			model.setFinancialYearMap(yearMap);
		}
		return defaultResult();
    }
	
	
	@Override
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SAVE, method = RequestMethod.POST)
	public ModelAndView saveform(HttpServletRequest request) {
		JsonViewObject responseObj = null;
		this.getModel().bind(request);
		PropertyNoDuesCertificateModel model = this.getModel();
		if (StringUtils.isBlank(model.getWorkflowActionDto().getDecision())) {
			getModel().addValidationError(getApplicationSession().getMessage("adh.approval.decision"));
			return defaultMyResult();
		}

		if (StringUtils.isBlank(model.getWorkflowActionDto().getComments())) {
			getModel().addValidationError(getApplicationSession().getMessage("adh.approval.remarks"));
			return defaultMyResult();
		}

		RequestDTO requestDto = new RequestDTO();
		requestDto.setReferenceId(String.valueOf(model.getApmApplicationId()));
		requestDto.setDepartmentName(MainetConstants.DEPT_SHORT_NAME.PROPERTY);
		requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDto.setServiceId(model.getServiceId());
		requestDto.setDeptId(model.getServiceMaster().getTbDepartment().getDpDeptid());
		requestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDto.setApplicationId(model.getApmApplicationId());
		List<DocumentDetailsVO> docs = new ArrayList<>();
		DocumentDetailsVO document = new DocumentDetailsVO();
		document.setDocumentSerialNo(1L);
		docs.add(document);
		model.setApprovalDocumentAttachment(fileUpload.prepareFileUpload(docs));
		fileUpload.doFileUpload(model.getApprovalDocumentAttachment(), requestDto);
		List<Long> documentIds = ApplicationContextProvider.getApplicationContext()
				.getBean(IChecklistVerificationService.class)
				.fetchAllAttachIdByReferenceId(String.valueOf(model.getApmApplicationId()),
						UserSession.getCurrent().getOrganisation().getOrgid());

		model.getWorkflowActionDto().setAttachementId(documentIds);
		boolean lastApproval = workFlowTypeService
				.isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());
		model.setLastApproval(lastApproval);
		if (lastApproval
				&& StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
						MainetConstants.WorkFlow.Decision.APPROVED)
				&& StringUtils.equalsIgnoreCase(model.getServiceMaster().getSmScrutinyChargeFlag(),
						MainetConstants.FlagY)) {

			boolean approvalFlag = this.getModel().AgencyRegistrationApprovalAction();
			if (approvalFlag) {
				responseObj = JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("Loi charges generated succeesfully. Your LOI No : "
								+ model.getWorkflowActionDto().getLoiDetails().get(0).getLoiNumber()));

			} else {
				responseObj = JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("Loi charges not found. Please define rule"));
			}

		} else if (lastApproval
				&& StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
						MainetConstants.WorkFlow.Decision.APPROVED)
				&& StringUtils.equalsIgnoreCase(model.getServiceMaster().getSmScrutinyChargeFlag(),
						MainetConstants.FlagN)) {
			if (model.closeWorkFlowTask()) {
				responseObj = JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("adh.application.approval.success.message"));
			} else {
				responseObj = JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("adh.application.approval.fail.message"));
			}

		} else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.APPROVED)) {

			boolean approvalFlag = this.getModel().AgencyRegistrationApprovalAction();
			if (approvalFlag) {
				responseObj = JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("adh.application.approval.success.message"));
			} else {
				responseObj = JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("adh.application.approval.fail.message"));
			}

		} else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.REJECTED)) {
			boolean approvalFlag = this.getModel().AgencyRegistrationApprovalAction();
			if (approvalFlag) {
				responseObj = JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("Application rejected successfully"));
			} else {
				responseObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("Application rejected Failed"));
			}
		}
		return jsonResult(responseObj);

	}

	    @RequestMapping(params = "checkLastApproval", method = RequestMethod.POST)
	public ModelAndView checkLastApproval(HttpServletRequest httpServletRequest) throws Exception {
		getModel().bind(httpServletRequest);
		PropertyNoDuesCertificateModel model = this.getModel();
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isBlank(model.getWorkflowActionDto().getDecision())) {
			getModel().addValidationError(getApplicationSession().getMessage("adh.approval.decision"));

		}
		if (StringUtils.isBlank(model.getWorkflowActionDto().getComments())) {
			getModel().addValidationError(getApplicationSession().getMessage("adh.approval.remarks"));

		}
		if (model.hasValidationErrors()) {
			return defaultMyResult();
		} else if (!getModel().updateForm()) {
			return defaultMyResult();
		}
		boolean lastApproval = workFlowTypeService
				.isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());
		model.setLastApproval(lastApproval);
		if (lastApproval
				&& StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
						MainetConstants.WorkFlow.Decision.APPROVED)
				&& StringUtils.equalsIgnoreCase(model.getServiceMaster().getSmScrutinyChargeFlag(),
						MainetConstants.FlagY)) {
			model.setSuccessFlag(MainetConstants.FlagY);
			if (model.saveLoiData()) {
				model.setPayableFlag(MainetConstants.FlagY);
				List<TbLoiDet> loiDetailList = new ArrayList<>();
				if (CollectionUtils.isNotEmpty(model.getLoiDetail())) {
					Double totalAmount = new Double(0);
					for (TbLoiDet detail : model.getLoiDetail()) {
						TbLoiDet loiDetail = new TbLoiDet();
						BeanUtils.copyProperties(detail, loiDetail);
						String taxDesc = ApplicationContextProvider.getApplicationContext()
								.getBean(TbTaxMasService.class).findTaxDescByTaxIdAndOrgId(detail.getLoiChrgid(),
										UserSession.getCurrent().getOrganisation().getOrgid());
						loiDetail.setLoiRemarks(taxDesc);
						totalAmount = totalAmount + loiDetail.getLoiAmount().doubleValue();
						loiDetailList.add(loiDetail);
						model.setTotalLoiAmount(totalAmount);

					}
				}
				model.setLoiDetail(loiDetailList);

			} else {
				model.addValidationError(getApplicationSession()
						.getMessage("Problrm occured while fetching LOI Charges from BRMS Sheet"));
			}
		} else {
			model.setSuccessFlag(MainetConstants.FlagN);
			this.getModel().setPayableFlag(MainetConstants.FlagN);
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		mv = defaultMyResult();
		return mv;

	}
}
