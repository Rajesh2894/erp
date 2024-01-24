package com.abm.mainet.asset.ui.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.asset.service.IAssetRequisitionService;
import com.abm.mainet.asset.service.IInformationService;
import com.abm.mainet.asset.ui.dto.AssetRequisitionDTO;
import com.abm.mainet.asset.ui.model.AssetRequisitionModel;
import com.abm.mainet.asset.ui.validator.AssetDetailsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;


@Controller
@RequestMapping(value = { MainetConstants.AssetManagement.ASSET_REQUISITION_URL, "ITAssetRequisition.html" })
public class AssetRequisitionController extends AbstractFormController<AssetRequisitionModel> {

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IAssetRequisitionService assetRequisitionService;
    
    @Autowired
    private IInformationService astService;
    
    @Autowired
    private IEmployeeService employeeService;
    
	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;
	
	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setCommonHelpDocs("AssetRequisition.html");
        // T#92467 TB_SYSMODFUNCTION->SM_SHORTDESC(departmentShortCode)
        String smShortDesc = httpServletRequest.getParameter("eventSMShortDesc");
        UserSession.getCurrent().setModuleDeptCode(
                AssetDetailsValidator.getModuleDeptCode(smShortDesc, httpServletRequest.getRequestURI(),
                        "ITAssetRequisition.html"));
        this.getModel().setDepartmentsList(
                ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());
        this.getModel().setLocList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
        List<AssetRequisitionDTO> requisitionList = assetRequisitionService.fetchSearchData(null, null,
                null, UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getModuleDeptCode());
        this.getModel().setRequisitionList(requisitionList);
        this.getModel().setAstRequisitionDTO(new AssetRequisitionDTO());
        return defaultResult();
        // return new ModelAndView("AssetRequisition", MainetConstants.FORM_NAME, model);
    }

    @ResponseBody
    @RequestMapping(params = "searchReqData", method = RequestMethod.POST)
    public List<AssetRequisitionDTO> searchReqData(@RequestParam(value = "astCategoryId") final Long astCategoryId,
            @RequestParam(value = "locId") final Long locId,
            @RequestParam(value = "deptId") final Long deptId,
            final HttpServletRequest request) {
        getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<AssetRequisitionDTO> requisitionList = assetRequisitionService.fetchSearchData(astCategoryId, locId,
                deptId, orgId, UserSession.getCurrent().getModuleDeptCode());
        return requisitionList;
    }

    @RequestMapping(params = "addRequisition", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView addRequisition(final HttpServletRequest request) {
        getModel().bind(request);
        this.getModel().setCompletedFlag("N");
        fileUpload.sessionCleanUpForFileUpload();
        
        
        
        this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
        this.getModel().setApprovalViewFlag(MainetConstants.MODE_CREATE);
        this.getModel().setAstRequisitionDTO(new AssetRequisitionDTO());
        this.getModel().getAstRequisitionDTO().setAstLoc(UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId());
        this.getModel().getAstRequisitionDTO().setAstDept(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid());
        
        return new ModelAndView("addRequisition", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView astRegFormApproval(@RequestParam("appNo") final String appNo,
            @RequestParam("taskId") final String taskId,
            @RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
            @RequestParam(value = "taskName", required = false) final String taskName,
            final HttpServletRequest httpServletRequest, final Model model) {
        this.sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.bindModel(httpServletRequest);
        AssetRequisitionModel astRequisitionModel = this.getModel();
        astRequisitionModel.setTaskId(actualTaskId);
        astRequisitionModel.getWorkflowActionDto().setReferenceId(appNo);
        astRequisitionModel.getWorkflowActionDto().setTaskId(actualTaskId);
        Long astRequisitionId = Long.parseLong(appNo.substring(appNo.lastIndexOf('/') + 1));
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        astRequisitionModel
                .setAstRequisitionDTO(assetRequisitionService.getAstRequisitionDataById(astRequisitionId));
        
        boolean lastApproval = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
                .isLastTaskInCheckerTaskList(actualTaskId);
        astRequisitionModel.setLevelCheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		if (lastApproval) {
			Long assetStatusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.STATUS.ACTIVE,PrefixConstants.ASSET_PREFIX.ASEET_STATUS, orgId);
			astRequisitionModel.getAstRequisitionDTO().setAstTotalQty(new BigDecimal(astService.getAllAssetCount(astRequisitionModel.getAstRequisitionDTO().getOrgId(),
					astRequisitionModel.getAstRequisitionDTO().getAstCategory(), assetStatusId)));
			BigDecimal total=(new BigDecimal(astService.getAssetCount(astRequisitionModel.getAstRequisitionDTO().getOrgId(),
							astRequisitionModel.getAstRequisitionDTO().getAstCategory(), assetStatusId)));
			astRequisitionModel.getAstRequisitionDTO().setAstRemainingQty(total.subtract(astRequisitionModel.getAstRequisitionDTO().getDispatchQuantity()));
			astRequisitionModel.setAstCodeList(astService.getAssetCode(orgId,astRequisitionModel.getAstRequisitionDTO().getAstCategory()));
			astRequisitionModel.setEmpList(employeeService.findAllEmployeeByDept(orgId,astRequisitionModel.getAstRequisitionDTO().getAstDept()));
			astRequisitionModel.setApprovalLastFlag("Y");
			
			
		} else {
			astRequisitionModel.setApprovalLastFlag("N");
		}
		 this.getModel().setCompletedFlag("N");
        astRequisitionModel.setDepartmentsList(
                ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class).findAll());
        astRequisitionModel.setLocList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
        // populateModel(astModel, asstInfoDTO.getAssetId(), "V", model);
        astRequisitionModel.setApprovalViewFlag(MainetConstants.MODE_VIEW);
        astRequisitionModel.setSaveMode(MainetConstants.MODE_VIEW);
        astRequisitionModel.getWorkflowActionDto().setComments("");
        astRequisitionModel.getWorkflowActionDto().setDecision(null);
        return new ModelAndView("AssetRequisitionApproval", MainetConstants.FORM_NAME, astRequisitionModel);

    }

    /**
     * Maker Checker submit
     * 
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "saveDecision", method = RequestMethod.POST)
    public ModelAndView approvalDecision(final HttpServletRequest httpServletRequest) {
        JsonViewObject respObj = null;
        this.bindModel(httpServletRequest);
        AssetRequisitionModel asstModel = this.getModel();
        
        String decision = asstModel.getWorkflowActionDto().getDecision();
        this.getModel().setCompletedFlag("N");
        boolean updFlag = asstModel.updateApprovalFlag(UserSession.getCurrent().getOrganisation().getOrgid(),
                asstModel.getAstRequisitionDTO().getDeptCode());
        if (updFlag) {
            if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.approved"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.reject"));
            else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK))
                respObj = JsonViewObject
                        .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.sendBack"));
        } else {
            respObj = JsonViewObject
                    .successResult(ApplicationSession.getInstance().getMessage("assest.info.application.failure"));
        }
        return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "getSerialNo")
    public @ResponseBody List<String> getSerialNo(@RequestParam("astCode") final String astCode,
    		final HttpServletRequest request) {
        bindModel(request);
        List<String> serialNo=astService.getSerialNo(UserSession.getCurrent().getOrganisation().getOrgid(), astCode);
       return serialNo;
        
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "quantityCheck")
	public @ResponseBody Long getAssetCount(@RequestParam("astCategory") final Long astCategory,
			@RequestParam("dispatchQuantity") final Long dispatchQuantity, final HttpServletRequest request) {
		bindModel(request);
		Long assetStatusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.STATUS.ACTIVE,
				PrefixConstants.ASSET_PREFIX.ASEET_STATUS, UserSession.getCurrent().getOrganisation().getOrgid());
		Long id = astService.getAssetCount(UserSession.getCurrent().getOrganisation().getOrgid(), astCategory,
				assetStatusId);
		return id;
		
		
	}  
    
    @Override
    @RequestMapping(params = "viewRefNoDetails") 
   	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
   			@RequestParam("taskId") final long serviceId,@RequestParam("actualTaskId") final long taskId, final HttpServletRequest request )throws Exception{      
   		
        sessionCleanup(request);
        AssetRequisitionModel asstModel = this.getModel();
        Long astRequisitionId = Long.parseLong(applicationId.substring(applicationId.lastIndexOf('/') + 1));
        AssetRequisitionDTO dto = assetRequisitionService.getAstRequisitionDataById(astRequisitionId);
        asstModel.setAstRequisitionDTO(dto);
        this.getModel().setApprovalViewFlag("V");
        this.getModel().setApprovalLastFlag("N");
        this.getModel().setCompletedFlag("Y");
        asstModel.setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId));
        asstModel.setLevelCheck(iWorkflowTaskService.findByTaskId(taskId).getCurentCheckerLevel());
       
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
       
        asstModel.setAstlist(assetRequisitionService.getAstRequisitionById(astRequisitionId));
       
        asstModel.setEmpList(employeeService.findAllEmployeeByDept(orgId,asstModel.getAstRequisitionDTO().getAstDept()));
        asstModel.setLocList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
         this.getModel().setDepartmentsList( ApplicationContextProvider.getApplicationContext().getBean( TbDepartmentService.class).findAll());
         asstModel.getWorkflowActionDto().setTaskId(taskId);
         asstModel.setServiceId(serviceId);
        asstModel.setSaveMode(MainetConstants.MODE_VIEW); 
        return new ModelAndView("AssetRequisitionApproval",MainetConstants.FORM_NAME,this. getModel());
       
   	}

    


}
