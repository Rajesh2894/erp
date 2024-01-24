package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentMastHistEntity;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.ui.model.MutationModel;

/**
 * Added for approval task at the end of Hearing BPM process
 * 
 * @author Arun Shinde
 *
 */

@Controller
@RequestMapping("/MutationFinalApproval.html")
public class MutationFinalApprovalController extends AbstractFormController<MutationModel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MutationFinalApprovalController.class);

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private AssesmentMastService assesmentMastService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private ILocationMasService iLocationMasService;

	@Autowired
	private IObjectionDetailsService objectionDetailsService;

	@Autowired
	private AssesmentMstRepository assesmentMstRepository;

	@Autowired
	private IWorkflowActionService iWorkflowActionService;

	@Autowired
	private MutationService mutationService;

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView defaultLoad(@RequestParam("appNo") final long applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest) throws Exception {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " defaultLoad() method");
		this.sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		MutationModel model = this.getModel();
		model.setOrgId(orgId);
		ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
		model.setServiceId(service.getSmServiceId());
		model.setDeptId(service.getTbDepartment().getDpDeptid());
		LOGGER.info("<-----Before calling getObjectionDetailByAppId() method ---->");
		ObjectionDetailsDto obj = objectionDetailsService.getObjectionDetailByAppId(orgId, serviceId, applicationId);

		// In case of individual property to check if having multiple flats
		List<AssesmentMastEntity> assesmentMastEntityList = new ArrayList<>();
		Set<String> set = new HashSet<>();
		if (obj != null && StringUtils.isNotBlank(obj.getFlatNo())) {
			LOGGER.info("<-----Before calling getPropDetailFromAssByPropNoFlatNo() method ---->");
			assesmentMastEntityList = assesmentMstRepository.getAssessmentMstByPropNo(orgId,
					obj.getObjectionReferenceNumber());
			// Filter out distinct flat no's and add it in set
			assesmentMastEntityList.stream().filter(flat -> set.add(flat.getFlatNo())).collect(Collectors.toList());
		}
		if (!set.isEmpty()) {
			getModel().setFlatNoList(new ArrayList<>(set));
		}

		LOGGER.info("<-----Before calling fetchAssessmentHistMasterByPropNoOrFlat() method ----> ");
		AssesmentMastHistEntity prevAssHistMast = assesmentMastService.fetchAssessmentHistMasterByPropNoOrFlat(orgId,
				obj.getObjectionReferenceNumber(), obj.getFlatNo() != null ? obj.getFlatNo() : null);

		List<PropertyTransferOwnerDto> prevOwnerDtoList = new ArrayList<>();
		// In case of whole billing case or individual billing with single flat
		if (CollectionUtils.isEmpty(assesmentMastEntityList) || set.size() <= 1) {
			prevOwnerDtoList = assesmentMastService.fetchAssOwnerHistByPropNoNApplId(orgId,
					prevAssHistMast.getApmApplicationId());
		} else {
			// In case of individual billing with multiple flat
			prevOwnerDtoList = assesmentMastService.fetchAssDetailHistByApplicationId(orgId, obj.getFlatNo(),
					prevAssHistMast.getApmApplicationId());
			this.getModel().setShowFlag(MainetConstants.FlagY);
		}
		model.setPropTransferDto(new PropertyTransferMasterDto());
		model.getPropTransferDto().setPropTransferOwnerList(prevOwnerDtoList);

		ProvisionalAssesmentMstDto assMst = null;
		if (StringUtils.isNotBlank(obj.getFlatNo())) {
			assMst = assesmentMastService.fetchLatestAssessmentByPropNoAndFlatNo(orgId,
					obj.getObjectionReferenceNumber(), obj.getFlatNo());
		} else {
			assMst = assesmentMastService.fetchAssessmentMasterByPropNo(orgId, obj.getObjectionReferenceNumber());
		}
		setDescriptionOfOwners(model, prevAssHistMast, assMst);
		assMst.setLocationName(iLocationMasService.getLocationNameById(assMst.getLocId(), orgId));
		model.setProvisionalAssesmentMstDto(assMst);
		model.setDropDownValues(assMst, UserSession.getCurrent().getOrganisation());
		getModel().setServiceId(service.getSmServiceId());
		getModel().setDeptId(service.getTbDepartment().getDpDeptid());
		getModel().getWorkflowActionDto().setTaskId(taskId);
		getModel().getWorkflowActionDto().setApplicationId(applicationId);
		LOGGER.info("End --> " + this.getClass().getSimpleName() + " defaultLoad() method");
		return new ModelAndView("MutationFinalApproval", MainetConstants.FORM_NAME, getModel());
	}

	private void setDescriptionOfOwners(MutationModel model, AssesmentMastHistEntity prevAssHistMast,
			ProvisionalAssesmentMstDto assMst) {
		LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(assMst.getAssOwnerType(),
				UserSession.getCurrent().getOrganisation());
		model.setOwnershipPrefix(ownerType.getLookUpCode());
		assMst.setProAssOwnerTypeName(ownerType.getDescLangFirst());
		LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
				MainetConstants.Property.propPref.OWT, UserSession.getCurrent().getOrganisation());
		model.setOwnershipTypeValue(lookup.getDescLangFirst());

		LookUp ownerTypeNew = CommonMasterUtility.getNonHierarchicalLookUpObject(prevAssHistMast.getAssOwnerType(),
				UserSession.getCurrent().getOrganisation());
		model.setOwnershipPrefixNew(ownerTypeNew.getLookUpCode());
	}

	@RequestMapping(params = "saveAfterApproval", method = RequestMethod.POST)
	public ModelAndView saveAfterMutApproval(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		LOGGER.info("Begin--> " + this.getClass().getSimpleName() + " saveAfterMutApproval() method");
		getModel().bind(httpServletRequest);
		MutationModel model = getModel();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Employee emp = UserSession.getCurrent().getEmployee();
		final WorkflowTaskAction workFlowActionDto = model.getWorkflowActionDto();

		List<DocumentDetailsVO> docs = this.getModel().getCheckList();
		docs = fileUpload.setFileUploadMethod(docs);
		String multipleFlats = "";
		if (CollectionUtils.isNotEmpty(model.getFlatNoList()) && getModel().getFlatNoList().size() > 1) {
			multipleFlats = MainetConstants.FlagY;
		}
		try {
			mutationService.updateMutationDetails(model.getProvisionalAssesmentMstDto(), model.getPropTransferDto(),
					model.getWorkflowActionDto(), emp, model.getServiceId(), model.getOwnershipPrefixNew(),
					multipleFlats, org);
			saveUploadedFile(docs, org.getOrgid(), emp, model.getDeptId(), UserSession.getCurrent().getLanguageId(),
					model.getServiceId(), workFlowActionDto.getApplicationId());
			iWorkflowActionService.updateWorkFlow(workFlowActionDto, emp, org.getOrgid(), model.getServiceId());
		} catch (Exception e) {
			LOGGER.error("Exception occurred while saving data : " + e.getMessage());
			model.addValidationError(getApplicationSession().getMessage("property.saveWorkflowError"));
		}
		if (model.hasValidationErrors()) {
			return customDefaultMyResult("MutationFinalApproval");
		}
		LOGGER.info("End --> " + this.getClass().getSimpleName() + " saveAfterMutApproval() method");
		return jsonResult(JsonViewObject
				.successResult(ApplicationSession.getInstance().getMessage("property.mutation.approvalSave")));
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
}
