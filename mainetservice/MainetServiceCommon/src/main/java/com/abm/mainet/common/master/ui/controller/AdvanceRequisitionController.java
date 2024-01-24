package com.abm.mainet.common.master.ui.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.acccount.dto.AdvanceRequisitionDto;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.IAdvanceRequisitionService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.ui.model.AdvanceRequisitionModel;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Controller
@RequestMapping("/AdvanceRequisition.html")
public class AdvanceRequisitionController extends AbstractFormController<AdvanceRequisitionModel> {

    @Resource
    private TbAcVendormasterService tbAcVendormasterService;
    @Resource
    private IAdvanceRequisitionService advanceRequisitionService;
    @Resource
    private TbDepartmentService iTbDepartmentService;
    @Resource
    private IFileUploadService fileUpload;
    @Resource
    private IWorkflowTyepResolverService iWorkflowTyepResolverService;
    @Resource
    private ServiceMasterService iServiceMasterService;
    @Resource
    private IWorkFlowTypeService iWorkFlowTypeService;
    @Resource
    private IAttachDocsService attachDocsService;

    @RequestMapping(method = { RequestMethod.POST,RequestMethod.GET})
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel();
        AdvanceRequisitionModel model = this.getModel();
        setSliProfixValue(model);
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        this.getModel()
                .setAdvanceType(CommonMasterUtility.getListLookup(MainetConstants.AdvanceEntry.ATY, organisation));

        this.getModel().setDepartmentsList(iTbDepartmentService.findMappedDepartments(organisation.getOrgid()));
        this.getModel().setVendorList(tbAcVendormasterService.findAll(organisation.getOrgid()));
        this.getModel().setCommonHelpDocs("AdvanceRequisition.html");
        return index();
    }

    @RequestMapping(params = "searchRequisition", method = RequestMethod.POST)
    public @ResponseBody List<AdvanceRequisitionDto> getFilterRequisition(final HttpServletRequest request,
            @RequestParam("advanceEntryDate") final Date advanceEntryDate,
            @RequestParam("vendorId") final Long vendorId, @RequestParam("deptId") final Long deptId) {

        List<AdvanceRequisitionDto> list = advanceRequisitionService.getFilterRequisition(advanceEntryDate, vendorId,
                deptId, UserSession.getCurrent().getOrganisation().getOrgid());

        if (!list.isEmpty()) {
            for (AdvanceRequisitionDto requitionList : list) {
                if (requitionList.getAdvStatus().equals(MainetConstants.FlagP)) {
                    requitionList.setAdvStatus(MainetConstants.TASK_STATUS_PENDING);
                } else if (requitionList.getAdvStatus().equals(MainetConstants.FlagD)) {
                    requitionList.setAdvStatus(MainetConstants.TASK_STATUS_DRAFT);
                } else if (requitionList.getAdvStatus().equals(MainetConstants.FlagA)) {
                    requitionList.setAdvStatus(MainetConstants.TASK_STATUS_APPROVED);
                } else if (requitionList.getAdvStatus().equals(MainetConstants.FlagR)) {
                    requitionList.setAdvStatus(MainetConstants.TASK_STATUS_REJECTED);
                }
            }
        }
        return list;
    }

    @RequestMapping(method = RequestMethod.POST, params = "AdvanceRequisitionForm")
    public ModelAndView addAdvanceRequisitionForm(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().setSaveMode(MainetConstants.FlagA);
        return new ModelAndView("AdvanceRequisitionForm", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "ActionRequisition", method = RequestMethod.POST)
    public ModelAndView editActionRequisition(@RequestParam("advId") final Long advId,
            @RequestParam("flag") final String flag) {
        this.getModel().setSaveMode(flag);
        this.getModel().setAdvanceRequisitionDto(advanceRequisitionService.getAdvanceRequisitionById(advId));
        return new ModelAndView("AdvanceRequisitionForm", MainetConstants.FORM_NAME, this.getModel());
    }

    @ResponseBody
    @RequestMapping(params = "getBudgetHeadCodeDesc", method = RequestMethod.POST)
    public Map<Long, String> getBudgetHeadCodeDesc(HttpServletRequest httpServletReuest,
            @RequestParam("acountSubType") Long acountSubType) {
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        return advanceRequisitionService.getBudgetHeadAllData(acountSubType, organisation, langId);
    }

    @ResponseBody
    @RequestMapping(params = "getEmployeType", method = RequestMethod.POST)
    public Map<Long, String> getEmployeeList(HttpServletRequest httpServletReuest,
            @RequestParam("accountTypeCode") String accountTypeCode, @RequestParam("deptId") Long deptId) {

        Map<Long, String> empType = new HashMap<>();
        if (accountTypeCode.equals(MainetConstants.FlagC)) {
            final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getOrganisation());
            final Long vendorStatus = lookUpVendorStatus.getLookUpId();
            empType = advanceRequisitionService.getEmployeeVendorType(accountTypeCode, vendorStatus,
                    UserSession.getCurrent().getOrganisation().getOrgid());
        } else {
            empType = advanceRequisitionService.getEmployeeVendorType(accountTypeCode, deptId,
                    UserSession.getCurrent().getOrganisation().getOrgid());
        }
        return empType;
    }

    @ResponseBody
    @RequestMapping(params = "getWorkOrderDetails", method = RequestMethod.POST)
    public List<AdvanceRequisitionDto> getWorkOrderDetails(@RequestParam("deptCode") final String deptCode,
            @RequestParam("vendorId") final Long vendorId,
            @RequestParam("referenceNumber") final String referenceNumber) {

        List<AdvanceRequisitionDto> advanceRequisitionDto = advanceRequisitionService.getWorkOrderDetails(deptCode,
                vendorId, UserSession.getCurrent().getOrganisation().getOrgid());
        return advanceRequisitionDto;
    }

    /**
     * Used to get Default Value of SLI PREFIX
     * 
     * @param model
     */
    public void setSliProfixValue(AdvanceRequisitionModel model) {
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (defaultVal != null) {
            model.setSliStatus(CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE)
                    .getLookUpCode());
        }
    }

    @ResponseBody
    @RequestMapping(params = "getTotalUsedAmount", method = RequestMethod.POST)
    public BigDecimal getTotalUsedAmount(@RequestParam("referenceNumber") final String referenceNumber) {
        BigDecimal usedAmount = null;
        usedAmount = advanceRequisitionService.getUsedContractAmountByReferenceNumber(referenceNumber,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (usedAmount == null) {
            usedAmount = new BigDecimal(MainetConstants.ZERO);
        }
        return usedAmount;
    }

    @RequestMapping(params = MainetConstants.SEND_FOR_APPROVAL, method = RequestMethod.POST)
    public @ResponseBody String sendForApproval(@RequestParam("advId") final Long advId,
            @RequestParam("flag") final String flag) {
        String statusFlag = null;
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setAdvanceRequisitionDto(advanceRequisitionService.getAdvanceRequisitionById(advId));
        ServiceMaster sm = iServiceMasterService.getServiceMasterByShortCode(MainetConstants.CAR, orgId);
        WorkflowMas workFlowMas = iWorkflowTyepResolverService.resolveServiceWorkflowType(orgId,
                sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(), null, null, null, null, null);

        if (workFlowMas != null) {
            advanceRequisitionService.initiateWorkFlow(
                    this.getModel().prepareWorkFlowTaskAction(new WorkflowTaskAction()), workFlowMas.getWfId(),
                    MainetConstants.ADVANCE_REQUISITION_URL, MainetConstants.FlagA);
            advanceRequisitionService.updateAdvanceRequisitionMode(advId, MainetConstants.FlagP);
        } else {
            statusFlag = MainetConstants.FlagN;
        }
        return statusFlag;
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SHOWDETAILS, method = RequestMethod.POST)
    public ModelAndView AdvanceRequisition(
            @RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final String arnNumber,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.ACTUAL_TASKID, required = false) final Long actualTaskId,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_ID, required = false) final Long serviceId,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
            @RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
            final HttpServletRequest httpServletRequest, final Model model) {

        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        AdvanceRequisitionModel approvalModel = this.getModel();
        Long parentOrgId = iWorkFlowTypeService.findById(workflowId).getCurrentOrgId();
        approvalModel.setParentOrgId(parentOrgId);
        approvalModel.setSaveMode(MainetConstants.WorksManagement.APPROVAL);
        approvalModel.setServiceId(serviceId);
        setSliProfixValue(approvalModel);
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        this.getModel()
                .setAdvanceType(CommonMasterUtility.getListLookup(MainetConstants.AdvanceEntry.ATY, organisation));

        approvalModel.setDepartmentsList(iTbDepartmentService.findMappedDepartments(parentOrgId));
        approvalModel.setAdvanceRequisitionDto(advanceRequisitionService.getAdvanceRequisitionByArn(arnNumber, parentOrgId));
        final List<AttachDocs> attachDocs = attachDocsService.findByCode(parentOrgId,
                approvalModel.getAdvanceRequisitionDto().getAdvNo());
        this.getModel().setAttachDocsList(attachDocs);
        approvalModel.setWorkflowId(workflowId);
        approvalModel.getWorkflowActionDto().setReferenceId(arnNumber);
        approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
        approvalModel.setActualTaskId(actualTaskId);
        approvalModel.setTaskName(taskName);
        approvalModel.setWokflowMode(true);
        approvalModel.setSaveMode(MainetConstants.MODE_VIEW);
        setSliProfixValue(approvalModel);
        return new ModelAndView(MainetConstants.ADVANCE_REQUISITION_FORM, MainetConstants.FORM_NAME, this.getModel());

    }

}
