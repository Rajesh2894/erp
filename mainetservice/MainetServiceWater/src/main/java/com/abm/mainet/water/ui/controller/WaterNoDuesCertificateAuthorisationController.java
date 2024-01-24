package com.abm.mainet.water.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.WaterNoDuesEntity;
import com.abm.mainet.water.dto.NoDueCerticateDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterNoDuesCertificateService;
import com.abm.mainet.water.ui.model.NoDuesCertificateModel;

/**
 * @author Arun Shinde
 *
 */
@Controller
@RequestMapping("/NoDuesCertAuthoController.html")
public class WaterNoDuesCertificateAuthorisationController extends AbstractFormController<NoDuesCertificateModel> {

	private static final Logger LOGGER = Logger.getLogger(WaterNoDuesCertificateAuthorisationController.class);

	@Autowired
	private WaterCommonService waterCommonService;

	@Autowired
	private WaterNoDuesCertificateService waterNoDuesCertificateService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private IWorkflowActionService iWorkflowActionService;

	@Autowired
	private WaterNoDuesCertificateService noDuesCertificateService;

	@Autowired
	private IFileUploadService fileService;

	@Autowired
	private ICFCApplicationAddressService cfcApplicationAddressService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView defaultLoad(@RequestParam("appNo") final long applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			@RequestParam("taskName") final String taskName, final HttpServletRequest httpServletRequest)
			throws Exception {
		sessionCleanup(httpServletRequest);
		fileService.sessionCleanUpForFileUpload();
		NoDuesCertificateModel approvalModel = this.getModel();
		approvalModel.getWorkflowActionDto().setTaskId(taskId);
		approvalModel.getWorkflowActionDto().setApplicationId(applicationId);
		approvalModel.getWorkflowActionDto().setServiceId(serviceId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		final TbCfcApplicationMstEntity tbCfcApplicationMst = tbCfcApplicationMstJpaRepository.findOne(applicationId);
		CFCApplicationAddressEntity cfcAddress = cfcApplicationAddressService
				.getApplicationAddressByAppId(applicationId, orgId);
		TbKCsmrInfoMH csmrInfoMH = waterCommonService.fetchConnectionDetailsByConnNo(tbCfcApplicationMst.getRefNo(),
				orgId);

		WaterNoDuesEntity waterNoDuesEntity = waterNoDuesCertificateService.getNoDuesDetailsByApplId(applicationId,
				orgId);

		NoDuesCertificateReqDTO reqDto = new NoDuesCertificateReqDTO();
		ApplicantDetailDTO appliDetDto = new ApplicantDetailDTO();
		reqDto.setConsumerNo(tbCfcApplicationMst.getRefNo());
		reqDto.setConsumerName(csmrInfoMH.getCsName());
		reqDto.setConsumerAddress((csmrInfoMH.getCsAdd() != null ? csmrInfoMH.getCsAdd() : MainetConstants.BLANK)
				+ MainetConstants.WHITE_SPACE
				+ (csmrInfoMH.getCsLanear() != null ? csmrInfoMH.getCsLanear() : MainetConstants.BLANK)
				+ MainetConstants.WHITE_SPACE
				+ (csmrInfoMH.getCsRdcross() != null ? csmrInfoMH.getCsRdcross() : MainetConstants.WHITE_SPACE)
				+ MainetConstants.WHITE_SPACE
				+ (csmrInfoMH.getCsBldplt() != null ? csmrInfoMH.getCsBldplt() : MainetConstants.BLANK));
		reqDto.setNoOfCopies(waterNoDuesEntity.getCaCopies());
		reqDto.setDuesAmount(waterNoDuesEntity.getCaWaterDue());
		this.getModel().setReqDTO(reqDto);

		appliDetDto.setApplicantTitle(tbCfcApplicationMst.getApmTitle());
		appliDetDto.setApplicantFirstName(tbCfcApplicationMst.getApmFname());
		appliDetDto.setApplicantMiddleName(tbCfcApplicationMst.getApmMname());
		appliDetDto.setApplicantLastName(tbCfcApplicationMst.getApmLname());
		appliDetDto.setMobileNo(cfcAddress.getApaMobilno());
		appliDetDto.setEmailId(cfcAddress.getApaEmail());
		appliDetDto.setDwzid1(waterNoDuesEntity.getWtN1());
		appliDetDto.setDwzid2(waterNoDuesEntity.getWtN2());
		appliDetDto.setAreaName(cfcAddress.getApaAreanm());
		appliDetDto.setRoadName(cfcAddress.getApaRoadnm());
		appliDetDto.setVillageTownSub(cfcAddress.getApaBlockName());
		appliDetDto.setPinCode(cfcAddress.getApaPincode().toString());
		appliDetDto.setBplNo(tbCfcApplicationMst.getApmBplNo());
		this.getModel().setApplicantDetailDto(appliDetDto);
		this.getModel().getOfflineDTO().setAmountToShow(waterNoDuesEntity.getRmAmount());

		return new ModelAndView("NoDuesCertificateAuthorisation", MainetConstants.FORM_NAME, getModel());
	}

	// Save authorisation form
	@RequestMapping(params = "saveNoDuesAuthoform", method = RequestMethod.POST)
	public ModelAndView saveAuthorization(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		LOGGER.info("Save form starts");
		getModel().bind(httpServletRequest);
		WorkflowTaskAction workFlowActionDto = this.getModel().getWorkflowActionDto();
		List<DocumentDetailsVO> docs = this.getModel().getCheckList();
		docs = fileUpload.setFileUploadMethod(docs);

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Employee emp = UserSession.getCurrent().getEmployee();
		Department dept = departmentService.getDepartment(MainetConstants.DEPT_SHORT_NAME.WATER, MainetConstants.FlagA);
		saveUploadedFile(docs, orgId, emp, dept.getDpDeptid(), UserSession.getCurrent().getLanguageId(),
				workFlowActionDto.getServiceId(), workFlowActionDto.getApplicationId());

		callWorkflow(workFlowActionDto.getApplicationId(), workFlowActionDto, orgId,
				UserSession.getCurrent().getEmployee(), workFlowActionDto.getServiceId());
		LOGGER.info("Save form ends");
		return jsonResult(JsonViewObject
				.successResult(ApplicationSession.getInstance().getMessage("water.select.app.authorised")));
	}

	private void saveUploadedFile(List<DocumentDetailsVO> docs, Long orgId, Employee emp, Long dpDeptid, int languageId,
			Long serviceId, Long applicationId) {
		LOGGER.info("Trying to upload files");
		RequestDTO reqDto = new RequestDTO();
		reqDto.setApplicationId(applicationId);
		reqDto.setDeptId(dpDeptid);
		reqDto.setOrgId(orgId);
		reqDto.setServiceId(serviceId);
		reqDto.setLangId(Long.valueOf(languageId));
		reqDto.setUserId(emp.getEmpId());
		fileUpload.doFileUpload(docs, reqDto);
		LOGGER.info("Files uploaded end");
	}

	public Boolean callWorkflow(Long apmAppId, WorkflowTaskAction workFlowActionDto, Long orgId, Employee emp,
			Long serviceId) {
		LOGGER.info("Call for workflow updation starts");
		List<Long> attachementId = iChecklistVerificationService.fetchAttachmentIdByAppid(apmAppId, orgId);
		workFlowActionDto.setAttachementId(attachementId);
		iWorkflowActionService.updateWorkFlow(workFlowActionDto, emp, orgId, serviceId);
		LOGGER.info("Call for workflow updation ends");
		return true;
	}

	// Print No Dues Certificate
	@RequestMapping(method = RequestMethod.POST, params = "noDueCertificatePrint")
	public ModelAndView noDueCertificatePrint(final HttpServletRequest httpServletRequest) {
		LOGGER.info("Start the noDueCertificatePrint()");
		try {
			getModel().bind(httpServletRequest);
			NoDuesCertificateModel model = getModel();
			WorkflowTaskAction workFlowActionDto = this.getModel().getWorkflowActionDto();
			NoDueCerticateDTO dto = noDuesCertificateService.getNoDuesApplicationData(
					workFlowActionDto.getApplicationId(), UserSession.getCurrent().getOrganisation().getOrgid());
			if (dto != null) {
				dto.setApproveBy(UserSession.getCurrent().getEmployee().getEmploginname());
				dto.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
				model.setNodueCertiDTO(dto);
				return new ModelAndView("NoDuesCertiFormPrint", MainetConstants.CommonConstants.COMMAND, getModel());
			}
		} catch (final Exception exception) {
			logger.error("Exception found in noDueCertificatePrint  method : ", exception);
		}
		return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
	}
}
