/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.BlockAllocationDetailDto;
import com.abm.mainet.sfac.dto.BlockAllocationDto;
import com.abm.mainet.sfac.service.AllocationOfBlocksService;
import com.abm.mainet.sfac.service.CBBOMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.ChangeofBlockApprovalModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.CHANGE_OF_BLOCK_APPROVAL)
public class ChangeofBlockApprovalController extends AbstractFormController<ChangeofBlockApprovalModel>{
	
	private static final Logger logger = Logger.getLogger(ChangeofBlockApprovalController.class);
	
	@Autowired
	AllocationOfBlocksService allocationOfBlocksService;
	
	@Autowired
    private TbOrganisationService organisationService;
	
	@Autowired
	private TbDepartmentService departmentService;
	
	@Autowired
	private IAMasterService iaMasterService;
	
	@Autowired
	private IChecklistVerificationService checklistVerificationService;
	
    @Autowired
    private IAttachDocsService attachDocsService;
    
    @Autowired
	private IOrganisationService orgService;
    
	@Autowired
	private CBBOMasterService cbboMasterService;

	@ResponseBody
	@RequestMapping(params = MainetConstants.Sfac.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView showDetails(
			@RequestParam(MainetConstants.Sfac.APP_NO) final String applicationId,
			@RequestParam(value = MainetConstants.Sfac.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.Sfac.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.Sfac.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.Sfac.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
		ChangeofBlockApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("ChangeofBlockApproval.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		List<BlockAllocationDetailDto>  inactiveDtoList = new ArrayList<BlockAllocationDetailDto>();
		List<BlockAllocationDetailDto>  changedDtoList = new ArrayList<BlockAllocationDetailDto>();
		BlockAllocationDto  dto = allocationOfBlocksService.fetchBlockDetailsbyAppId(Long.valueOf(applicationId));
		if (dto != null)  {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 2,
					UserSession.getCurrent().getOrganisation());
			List<LookUp> distList = new ArrayList<LookUp>();
			List<LookUp> blockList = new ArrayList<LookUp>();
			for (BlockAllocationDetailDto detDto : dto.getBlockDetailDto()) {
				if (detDto.getStatus() != null && (detDto.getStatus().equals(MainetConstants.FlagI) || detDto.getStatus().equals(MainetConstants.FlagA)))
					inactiveDtoList.add(detDto);
				if (detDto.getStatus() != null && detDto.getStatus().equals(MainetConstants.FlagC) && null != detDto.getStateId())
					changedDtoList.add(detDto);
				if (detDto.getStateId() != null) {
					distList = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == detDto.getStateId())
							.collect(Collectors.toList());
					this.getModel().setDistrictList(distList);
				}
			}
			this.getModel().setBlockAllocationDto(dto);
		}
		
		//if (detDto.getDistId() != null) {
			List<LookUp> blckList = CommonMasterUtility.getLevelData("SDB", 3,
					UserSession.getCurrent().getOrganisation());
			/*blockList = blckList.stream().filter(lookUp -> lookUp.getLookUpParentId() == detDto.getDistId())
					.collect(Collectors.toList());*/
			this.getModel().setBlockList(blckList);
	//	}

	//	if (detDto.getAllocationCategory() != null) {
			List<LookUp> alcList = CommonMasterUtility.getLevelData("ALC", 2,
					UserSession.getCurrent().getOrganisation());
			this.getModel().setAllocationSubCatgList(alcList);
		//}
		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setStateList(stateList);
		final List<LookUp> allcCatgList = CommonMasterUtility.getLevelData("ALC", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setAllocationCatgList(allcCatgList);
		List<LookUp> allcationList = CommonMasterUtility.getLevelData("ALC", 2, UserSession.getCurrent().getOrganisation());
		this.getModel().setAllcSubCatgTargetList(allcationList);
		
		this.getModel().getNewBlockAllocationDto().setBlockDetailDto(changedDtoList);
		this.getModel().getBlockAllocationDto().setBlockDetailDto(inactiveDtoList);
		Organisation organisation = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
		List<CommonMasterDto> masterDtoList = iaMasterService.getMasterDetail(organisation.getOrgid());
		this.getModel().setCommonMasterDtoList(masterDtoList);
		//this.getModel().setCbboMasterList(cbboMasterService.findAllCBBO());
		
	/*	List<CFCAttachment> attachment = null;
		for (int i = 0; i < changedDtoList.size(); i++) {
			attachment = new ArrayList<>();
			attachment = checklistVerificationService.getDocumentUploadedByRefNo(
					changedDtoList.get(i).getApplicationId().toString(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			if (attachment != null) {
				// this.getAttachments().add(attachment.get(0));
				changedDtoList.get(i).setViewImg(attachment);
			}
		}*/
	/*	List<CFCAttachment>	documentList = checklistVerificationService.getDocumentUploaded(Long.valueOf(applicationId),
				UserSession.getCurrent().getOrganisation().getOrgid());
		if(CollectionUtils.isNotEmpty(documentList))
			this.getModel().setDocumentList(documentList);*/
		String path = (MainetConstants.Sfac.CBR + MainetConstants.FILE_PATH_SEPARATOR + applicationId);
		logger.info("orgid : shortCode : applicationId "+ path);
        this.getModel()
        .setAttachDocsList(attachDocsService.findByCode(dto.getOrgId(),MainetConstants.Sfac.CBR + MainetConstants.FILE_PATH_SEPARATOR + applicationId));
        logger.info("doclist"+ this.getModel().getAttachDocsList());
		approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		this.getModel().setOrgList(organisationService.findAll());
		Long langId=  (long) UserSession.getCurrent().getLanguageId();
		this.getModel().setDepartmentList(departmentService.findByOrgId(orgId,langId));
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(UserSession.getCurrent().getOrganisation()));
		
		return new ModelAndView(MainetConstants.Sfac.CHANGE_BLOCK_APPROVAL, MainetConstants.FORM_NAME, this.getModel());
	}
}
