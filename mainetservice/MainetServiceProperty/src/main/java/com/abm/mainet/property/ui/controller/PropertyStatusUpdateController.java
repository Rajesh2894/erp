package com.abm.mainet.property.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.IProvisionalAssesmentMstService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.ui.model.PropertyStatusUpdateModel;

/**
 * @author Arun Shinde
 *
 */
@Controller
@RequestMapping("/PropertyStatusUpdate.html")
public class PropertyStatusUpdateController extends AbstractFormController<PropertyStatusUpdateModel> {

	private static final Logger LOGGER = Logger.getLogger(PropertyStatusUpdateController.class);

	@Autowired
	private AssesmentMastService assesmentMastService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private MutationService mutationService;

	@Autowired
	private IWorkflowRequestService workflowReqService;

	@Autowired
	private IProvisionalAssesmentMstService provisionalAssesmentMstService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		this.sessionCleanup(request);
		return new ModelAndView("PropertyStatusUpdate", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "getPropertyDetails", method = RequestMethod.POST)
	public ModelAndView getMutationDetail(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "propNo") String propNo,
			@RequestParam(value = "oldPropNo") String oldPropNo) {
		this.getModel().bind(httpServletRequest);
		fileUploadService.sessionCleanUpForFileUpload();
		Organisation org = UserSession.getCurrent().getOrganisation();
		ProvisionalAssesmentMstDto assMstDto = null;
		if (StringUtils.isNotBlank(propNo)) {
			LOGGER.info("<--------------fetchAssessmentMasterByPropNo() method starts------------->");
			assMstDto = assesmentMastService.fetchAssessmentMasterByPropNo(org.getOrgid(), propNo);
		} else {
			LOGGER.info("<--------------fetchAssessmentMasterByOldPropNo() method starts---------->");
			assMstDto = assesmentMastService.fetchAssessmentMasterByOldPropNo(org.getOrgid(), oldPropNo);
		}
		if (assMstDto == null) {
			getModel().addValidationError(
					ApplicationSession.getInstance().getMessage("property.noDues.propertySearchValid"));
			ModelAndView mv = new ModelAndView("PropertyStatusUpdateValidn", MainetConstants.FORM_NAME,
					this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}
		this.getModel().setProvisionalAssesmentMstDto(assMstDto);
		
		// To check mutation is already in progress or not
		try {
			PropertyTransferMasterDto transferMasDto = mutationService.getMutationByPropNo(assMstDto.getAssNo(),
					org.getOrgid());
			if (transferMasDto != null && transferMasDto.getApmApplicationId() != null) {
				WorkflowRequest workflowRequest = workflowReqService
						.getWorkflowRequestByAppIdOrRefId(transferMasDto.getApmApplicationId(), null, org.getOrgid());
				if (MainetConstants.TASK_STATUS_PENDING.equalsIgnoreCase(workflowRequest.getStatus())) {
					getModel().addValidationError(
							ApplicationSession.getInstance().getMessage("property.validation.mutaion.processValid"));
					ModelAndView mv = new ModelAndView("PropertyStatusUpdateValidn", MainetConstants.FORM_NAME,
							this.getModel());
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							getModel().getBindingResult());
					return mv;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while fetching mutation details: " + e);
		}
		// end

		//  To check assessment application already in progress
		List<ProvisionalAssesmentMstEntity> provAssesMastList = provisionalAssesmentMstService
				.getPropDetailFromProvAssByPropNo(org.getOrgid(), assMstDto.getAssNo(), MainetConstants.FlagA);
		if (CollectionUtils.isNotEmpty(provAssesMastList)) {
			ProvisionalAssesmentMstEntity provAssesMstDto = provAssesMastList.get(provAssesMastList.size() - 1);
			if (provAssesMstDto != null && provAssesMstDto.getApmApplicationId() != null) {
				WorkflowRequest workflowRequest = workflowReqService
						.getWorkflowRequestByAppIdOrRefId(provAssesMstDto.getApmApplicationId(), null, org.getOrgid());
				if (MainetConstants.WorkFlow.Status.PENDING.equalsIgnoreCase(workflowRequest.getStatus())) {
					getModel().addValidationError(
							ApplicationSession.getInstance().getMessage("property.validation.assessment.processValid"));
					ModelAndView modelview = new ModelAndView("PropertyStatusUpdateValidn", MainetConstants.FORM_NAME,
							this.getModel());
					modelview.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							getModel().getBindingResult());
					return modelview;
				}
			}
		}
		//

		LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(assMstDto.getAssOwnerType(),
				UserSession.getCurrent().getOrganisation());
		getModel().setOwnershipPrefix(ownerType.getLookUpCode());		
		this.getModel().setDropDownValues(org);
		this.getModel().setStatusFlag(assMstDto.getAssActive());
		return new ModelAndView("PropertyStatusDetailsEdit", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "backToSearch", method = { RequestMethod.POST })
	public ModelAndView backToSearch(HttpServletRequest request) {
		this.sessionCleanup(request);
		return new ModelAndView("PropertyStatusUpdateValidn", MainetConstants.FORM_NAME, this.getModel());
	}

}
