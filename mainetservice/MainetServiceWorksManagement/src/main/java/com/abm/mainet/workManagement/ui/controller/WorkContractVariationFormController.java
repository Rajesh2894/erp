package com.abm.mainet.workManagement.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkOrderContractDetailsDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.service.WorksWorkFlowService;
import com.abm.mainet.workManagement.ui.model.WorkContractVariationFormModel;

@Controller
@RequestMapping("/ContractVariation.html")
public class WorkContractVariationFormController extends AbstractFormController<WorkContractVariationFormModel> {

    @Autowired
    private IContractAgreementService contractAgreementService;

    @Resource
    DepartmentService departmentService;

    @Autowired
    private WorkOrderService workOrderService;

    @RequestMapping(params = MainetConstants.WorksManagement.ADD_CONTRACT_VARIATION, method = RequestMethod.POST)
    public ModelAndView addContractVariation(final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setUnitLookUpList(CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.WUT,
                UserSession.getCurrent().getOrganisation()));
        this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
        this.getModel().setWorkOrderContractDetailsDto(workOrderService.getAllContractDetailsInWorkOrderByOrgId(orgId));
        return new ModelAndView(MainetConstants.WorksManagement.ADD_CONTRACT_VARIATION, MainetConstants.FORM_NAME,
                this.getModel());

    }

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);

        this.getModel().setCommonHelpDocs("ContractVariation.html");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        List<WorkOrderContractDetailsDto> listContract = new ArrayList<>();
        List<WorkOrderContractDetailsDto> workContractList = workOrderService.getAllSummaryContractDetails(orgId);
        for (WorkOrderContractDetailsDto workContract : workContractList) {
            if (StringUtils.isNotEmpty(workContract.getWorkOrderStatus())) {
                if (workContract.getWorkOrderStatus().equals(MainetConstants.FlagD)) {
                    workContract.setWorkOrderStatus(MainetConstants.TASK_STATUS_DRAFT);
                }
                if (workContract.getWorkOrderStatus().equals(MainetConstants.FlagP)) {
                    workContract.setWorkOrderStatus(MainetConstants.TASK_STATUS_PENDING);
                }
                if (workContract.getWorkOrderStatus().equals(MainetConstants.FlagA)) {
                    workContract.setWorkOrderStatus(MainetConstants.TASK_STATUS_APPROVED);
                }
                if (workContract.getWorkOrderStatus().equals(MainetConstants.FlagR)) {
                    workContract.setWorkOrderStatus(MainetConstants.TASK_STATUS_REJECTED);
                }
            }
            listContract.add(workContract);
        }

        this.getModel().setWorkOrderContractDetailsDto(listContract);
        populateModel(this.getModel());
        return new ModelAndView(MainetConstants.WorksManagement.CONTRACT_VARIATION, MainetConstants.FORM_NAME,
                this.getModel());

    }

    @RequestMapping(params = MainetConstants.WorksManagement.FILTER_CONTRACTVARIATIONLIST_DATA, method = RequestMethod.POST)
    public @ResponseBody List<WorkOrderDto> getfilterEstimateListData(final HttpServletRequest request,
            @RequestParam(MainetConstants.WorksManagement.CONTRACTNO) final String contractNo,
            @RequestParam(MainetConstants.WorksManagement.CONTRACTDATE) String contractDate,
            @RequestParam(MainetConstants.WorksManagement.VENDOR_ID) final Long venderId) throws Exception {

        if (contractDate != null && contractDate.isEmpty()) {
            contractDate = null;
        }
        String deptCode = MainetConstants.WorksManagement.WORKS_MANAGEMENT;
        Long deptId = departmentService.getDepartmentIdByDeptCode(deptCode);
        final List<ContractAgreementSummaryDTO> contractAgreementSummaryDTO = contractAgreementService
                .getContractAgreementSummaryData(UserSession.getCurrent().getOrganisation().getOrgid(), contractNo,
                        contractDate, deptId, venderId, null, null);
        WorkOrderDto workOrderDto = null;
        WorkOrderDto workResponseDto = null;
        List<WorkOrderDto> workOrderDtoList = new ArrayList<>();
        for (ContractAgreementSummaryDTO contract : contractAgreementSummaryDTO) {
            workOrderDto = new WorkOrderDto();
            workOrderDto.setContractNo(contract.getContNo());
            workOrderDto.setContractDate(contract.getContDate());
            workOrderDto.setVendorName(contract.getContp2Name());
            workOrderDto.setContId(contract.getContId());
            workResponseDto = workOrderService.fetchWorkOrderByContId(contract.getContId(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            if (StringUtils.isNotEmpty(workResponseDto.getWorkOrderStatus())) {
                if (workResponseDto.getWorkOrderStatus().equals(MainetConstants.FlagD)) {
                    workOrderDto.setWorkOrderStatus(MainetConstants.TASK_STATUS_DRAFT);
                }
                if (workResponseDto.getWorkOrderStatus().equals(MainetConstants.FlagP)) {
                    workOrderDto.setWorkOrderStatus(MainetConstants.TASK_STATUS_PENDING);
                }
                if (workResponseDto.getWorkOrderStatus().equals(MainetConstants.FlagA)) {
                    workOrderDto.setWorkOrderStatus(MainetConstants.TASK_STATUS_APPROVED);
                }
                if (workResponseDto.getWorkOrderStatus().equals(MainetConstants.FlagR)) {
                    workOrderDto.setWorkOrderStatus(MainetConstants.TASK_STATUS_REJECTED);
                }
            }
            workOrderDto.setWorkId(workResponseDto.getWorkId());
            workOrderDtoList.add(workOrderDto);
        }

        return workOrderDtoList;
    }

    @RequestMapping(params = MainetConstants.WorksManagement.VIEW_CONTRACT_VARIATION, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView viewContractVariation(final HttpServletRequest request,
            @RequestParam(MainetConstants.WorksManagement.CONTRACT_ID) final Long contId,
            @RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode,
            @RequestParam(MainetConstants.WorksManagement.ESTIM_TYPE) final String estimateType) {
        bindModel(request);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setWorkeReviseFlag(MainetConstants.FlagN);
        this.getModel().setSaveMode(mode);
        this.getModel().setNewContractId(contId);
        if (request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE) == null
                || (!request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE).toString()
                        .equals(MainetConstants.WorksManagement.WCV))) {
            this.getModel().setSaveMode(mode);
        } else {
            this.getModel().setRequestFormFlag(
                    request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE).toString());
            this.getModel().setSaveMode(mode);
            this.getModel().setWorkOrderContractDetailsDto(workOrderService.getAllSummaryContractDetails(orgId));
        }
        this.getModel().setAllContractVariationData(estimateType, contId, orgId);
        this.getModel().setAllExistingContractVariationData(estimateType, contId, orgId);
        List<WorkEstimateMasterDto> dtos = ApplicationContextProvider.getApplicationContext().getBean(WorkEstimateService.class)
                .getAllRevisedContarctEstimateDetailsByContrcatId(contId, orgId, null);
        if (dtos != null && !dtos.isEmpty()) {
            this.getModel().setWorkeReviseFlag(dtos.get(0).getWorkeReviseFlag());
        }

        return new ModelAndView(MainetConstants.WorksManagement.ADD_CONTRACT_VARIATION, MainetConstants.FORM_NAME,
                this.getModel());

    }

    @RequestMapping(params = MainetConstants.WorksManagement.GETNONSORFORM_LIST, method = RequestMethod.POST)
    public ModelAndView nonSorFormList(final HttpServletRequest httpServletRequest,
            @RequestParam(MainetConstants.WorksManagement.VALUE) String value,
            @RequestParam(MainetConstants.WorksManagement.contractNo) Long contractNo,
            @RequestParam(MainetConstants.WorksManagement.STATUS) String status) {
        bindModel(httpServletRequest);
        ModelAndView modelAndView = new ModelAndView();
        this.getModel().setWorkeReviseFlag(status);
        this.getModel().setUnitLookUpList(CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.WUT,
                UserSession.getCurrent().getOrganisation()));
        populateModel(this.getModel());
        // Add mode and For new entry
        if (this.getModel().getWorkeReviseFlag().equals(MainetConstants.FlagN)
                && this.getModel().getSaveMode().equals(MainetConstants.WorksManagement.ADD)) {

            this.getModel().getWorkEstimateNonSorFormList().clear();
            this.getModel().getMeasurementsheetViewDataVariation().clear();
            this.getModel().getWorkEstimateBillQuantityList().clear();
            if (value.equals(MainetConstants.FlagS)) {
                return new ModelAndView(MainetConstants.WorksManagement.MEASUREMENT_SHEET_VARIATION,
                        MainetConstants.FORM_NAME, this.getModel());
            } else if (value.equals(MainetConstants.FlagN)) {
                return new ModelAndView(MainetConstants.WorksManagement.WORK_NONSOR_TABLEDETAILS,
                        MainetConstants.FORM_NAME, this.getModel());
            } else if (value.equals(MainetConstants.FlagB)) {
                return new ModelAndView(MainetConstants.WorksManagement.BILL_OF_QUANTITY, MainetConstants.FORM_NAME,
                        this.getModel());
            }
        }

        // Add Mode For extisting Entry
        if (this.getModel().getWorkeReviseFlag().equals(MainetConstants.WorksManagement.EDIT)
                && this.getModel().getSaveMode().equals(MainetConstants.WorksManagement.ADD)) {
            this.getModel().setAllExistingContractVariationData(value, contractNo,
                    UserSession.getCurrent().getOrganisation().getOrgid());
            if (value.equals(MainetConstants.FlagS)) {
                return new ModelAndView(MainetConstants.WorksManagement.MEASUREMENT_SHEET_VARIATION,
                        MainetConstants.FORM_NAME, this.getModel());
            } else if (value.equals(MainetConstants.FlagN)) {
                return new ModelAndView(MainetConstants.WorksManagement.WORK_NONSOR_TABLEDETAILS,
                        MainetConstants.FORM_NAME, this.getModel());
            } else if (value.equals(MainetConstants.FlagB)) {
                return new ModelAndView(MainetConstants.WorksManagement.BILL_OF_QUANTITY, MainetConstants.FORM_NAME,
                        this.getModel());
            }

        }
        // edit mode
        if (this.getModel().getSaveMode().equals(MainetConstants.WorksManagement.EDIT)
                || this.getModel().getSaveMode().equals(MainetConstants.WorksManagement.VIEW)
                || this.getModel().getSaveMode().equals(MainetConstants.WorksManagement.WCV)) {
            this.getModel().setAllContractVariationData(value, this.getModel().getNewContractId(),
                    UserSession.getCurrent().getOrganisation().getOrgid());

            if (value.equals(MainetConstants.FlagS)) {
                return new ModelAndView(MainetConstants.WorksManagement.MEASUREMENT_SHEET_VARIATION,
                        MainetConstants.FORM_NAME, this.getModel());
            } else if (value.equals(MainetConstants.FlagN)) {
                return new ModelAndView(MainetConstants.WorksManagement.WORK_NONSOR_TABLEDETAILS,
                        MainetConstants.FORM_NAME, this.getModel());
            } else if (value.equals(MainetConstants.FlagB)) {
                return new ModelAndView(MainetConstants.WorksManagement.BILL_OF_QUANTITY, MainetConstants.FORM_NAME,
                        this.getModel());
            }

        }
        return modelAndView;

    }

    /**
     * get Contract Details
     * 
     * @param httpServletRequest
     * @param contractId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GETCONTRACT_DETAILS, method = RequestMethod.POST)
    public ContractAgreementSummaryDTO getContractDetails(final HttpServletRequest httpServletRequest,
            @RequestParam(MainetConstants.WorksManagement.CONTRACTID) long contractId) throws Exception {
        bindModel(httpServletRequest);
        ContractAgreementSummaryDTO agreementSummary = null;
        String deptCode = MainetConstants.WorksManagement.WORKS_MANAGEMENT;
        Long deptId = departmentService.getDepartmentIdByDeptCode(deptCode);
        final List<ContractAgreementSummaryDTO> listContractDto = contractAgreementService
                .getContractAgreementSummaryData(UserSession.getCurrent().getOrganisation().getOrgid(), null, null,
                        deptId, null, null, null);
        for (ContractAgreementSummaryDTO contractAgreement : listContractDto) {
            if (contractAgreement.getContId() == contractId) {
                agreementSummary = contractAgreement;
                break;
            }
        }
        return agreementSummary;

    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_SOR_DETAILS)
    public ModelAndView saveSORDetails(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().saveSORData(this.getModel().getMeasurementsheetViewDataVariation());
        return defaultResult();

    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_NONSOR_DETAILS)
    public ModelAndView saveNonSORDetails(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().saveNonSORData(this.getModel().getWorkEstimateNonSorFormList());
        return defaultResult();

    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_BILLQUANTITY_DETAILS)
    public ModelAndView saveBillQuantityDetails(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().saveBillQuantityData(this.getModel().getWorkEstimateBillQuantityList());
        return defaultResult();

    }

    @RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SEND_FOR_APPROVAL, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> sendForApproval(final HttpServletRequest request,
            @RequestParam(name = MainetConstants.WorksManagement.CONTRACT_ID) final Long contId,
            @RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode) {
        bindModel(request);
        String flag = null;
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ContractMastDTO contractMastDTO = contractAgreementService.findById(contId, orgId);
        this.getModel().setContractNo(contractMastDTO.getContNo());
        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.WorksManagement.CVA, orgId);
        WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowTyepResolverService.class)
                .resolveServiceWorkflowType(orgId, service.getTbDepartment().getDpDeptid(), service.getSmServiceId(),
                        null, null, null, null, null);

        if (workFlowMas != null) {
            flag = ApplicationContextProvider.getApplicationContext().getBean(WorksWorkFlowService.class)
                    .initiateWorkFlowWorksService(
                            this.getModel().prepareWorkFlowTaskAction(), workFlowMas,
                            MainetConstants.WorksManagement.WORK_CONTRACT_VARIATION, MainetConstants.FlagA);
            workOrderService.updateContractVariationStatus(contId, MainetConstants.FlagP);
            flag = MainetConstants.FlagY;
        } else {
            flag = MainetConstants.FlagN;
        }
        map.put(MainetConstants.WorksManagement.CHECK_FLAG, flag);

        return map;
    }

    /**
     * Used to get Default Value of HSF PREFIX
     * 
     * @param workEstimateModel
     */
    private void populateModel(WorkContractVariationFormModel model) {

        // check for sub category flag is active or not
        List<LookUp> lookUpList = CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.HSF,
                UserSession.getCurrent().getOrganisation());
        if (lookUpList != null && !lookUpList.isEmpty()) {
            model.setCpdModeHideSor(lookUpList.get(0).getLookUpCode());
        } else {
            model.setCpdModeHideSor(null);
        }
    }

}
