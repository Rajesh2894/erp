package com.abm.mainet.workManagement.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.workManagement.dto.ScheduleOfRateDetDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;
import com.abm.mainet.workManagement.dto.WorkOrderContractDetailsDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.service.ScheduleOfRateService;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.service.WorksMeasurementSheetService;
import com.abm.mainet.workManagement.service.WorksWorkFlowService;
import com.abm.mainet.workManagement.ui.model.RevisedEstimateModel;

/**
 * @author vishwajeet.kumar
 * @since 10 april 2019
 */
@Controller
@RequestMapping("/WorksRevisedEstimate.html")
public class RevisedEstimateController extends AbstractFormController<RevisedEstimateModel> {

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private IContractAgreementService contractAgreementService;

    @Autowired
    private ScheduleOfRateService scheduleOfRateService;

    @Autowired
    private WorksMeasurementSheetService measurementSheetService;
    
    @Autowired
    private TbAcVendormasterService tbAcVendormasterService;

    private static final Logger LOGGER = Logger.getLogger(RevisedEstimateController.class);

    /**
     * This Method is used for Revised Estimate Summary Page
     * @param httpServletRequest
     * @return workRevisedEstimateSummary.jsp
     * 
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        this.getModel().setCommonHelpDocs("WorksRevisedEstimate.html");
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
        List<TbAcVendormaster> vendorList = getVendorList(orgId);
        this.getModel().setVendorList(vendorList);
        this.getModel().setWorkOrderContractDetailsDto(listContract);
        populateModel(this.getModel());
        return index();

    }
    
    public List<TbAcVendormaster> getVendorList(final Long orgId) {
		
		final Long statusId = CommonMasterUtility.getIdFromPrefixLookUpDesc("Active", "VSS", 1);
		
		List<TbAcVendormaster> allActiveVendors = tbAcVendormasterService.getAllActiveVendors(orgId, statusId);
		return allActiveVendors;
	}

    /**
     * Method is used for Get All Reviesd Estimate Data
     * @param contractAgreementNo
     * @param contractAgreementDate
     * @param venderId
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "filterRevisedEstimateListData", method = RequestMethod.POST)
    public List<WorkOrderDto> getFilterRevisedEstimateListData(@RequestParam("contractAgreementNo") String contractAgreementNo,
            @RequestParam("contractAgreementDate") String contractAgreementDate,
            @RequestParam("venderId") final Long venderId) {
        if (contractAgreementDate != null && contractAgreementDate.isEmpty()) {
            contractAgreementDate = null;
        }
        String deptCode = MainetConstants.WorksManagement.WORKS_MANAGEMENT;
        Long deptId = departmentService.getDepartmentIdByDeptCode(deptCode);
        List<ContractAgreementSummaryDTO> contractAgreementSummaryDTO = new ArrayList<>();
        try {
            contractAgreementSummaryDTO = contractAgreementService
                    .getContractAgreementSummaryData(UserSession.getCurrent().getOrganisation().getOrgid(), contractAgreementNo,
                            contractAgreementDate, deptId, venderId, null, null);
        } catch (Exception exception) {
            LOGGER.error(
                    "Error Occured when calling contract Agreement details ( method Name getFilterRevisedEstimateListData()) "
                            + exception);
            throw new FrameworkException("Error Occured when calling contract Agreement details" + exception);
        }
        WorkOrderDto workOrderDto = new WorkOrderDto();
        List<WorkOrderDto> workOrderDtoList = new ArrayList<>();
        // WorkOrderDto workResponseDto = new WorkOrderDto();
        if (contractAgreementSummaryDTO != null) {
            contractAgreementSummaryDTO.forEach(contData -> {
                workOrderDto.setContractNo(contData.getContNo());
                workOrderDto.setContractDate(contData.getContDate());
                workOrderDto.setVendorName(contData.getContp2Name());
                workOrderDto.setContId(contData.getContId());
                WorkOrderDto workResponseDto = workOrderService.fetchWorkOrderByContId(contData.getContId(),
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
            });
        }
        return workOrderDtoList;
    }

    /**
     * This Method is used for Add Revised Estimate
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "AddRevisedEstimate", method = RequestMethod.POST)
    public ModelAndView addContractVariation(final HttpServletRequest httpServletRequest) throws Exception {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setUnitLookUpList(CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.WUT,
                UserSession.getCurrent().getOrganisation()));
        this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
        this.getModel().setWorkOrderContractDetailsDto(workOrderService.getAllContractDetailsInWorkOrderByOrgId(orgId));
        return new ModelAndView("AddRevisedEstimate", MainetConstants.FORM_NAME,
                this.getModel());

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

    /**
     * select sor And Non Sor Details Table
     * @param httpServletRequest
     * @param sorNonSor
     * @param contractNo
     * @param reviseFlag
     * @return
     */
    @RequestMapping(params = "sorAndNonSorList", method = RequestMethod.POST)
    public ModelAndView sorAndNonSorDetailsTable(final HttpServletRequest httpServletRequest,
            @RequestParam("sorNonSor") String sorNonSor, @RequestParam("contractNo") Long contractNo,
            @RequestParam("reviseFlag") String reviseFlag) {
        bindModel(httpServletRequest);

        ModelAndView modelAndView = new ModelAndView();
        Long orgId=UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setWorkeReviseFlag(reviseFlag);
        this.getModel().setUnitLookUpList(CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.WUT,
                UserSession.getCurrent().getOrganisation()));
        populateModel(this.getModel());
        this.getModel().setNewContractId(contractNo);

        List<WorkEstimateMasterDto> workIdAndSorId = ApplicationContextProvider.getApplicationContext()
                .getBean(WorkEstimateService.class).getSorIdWithContractId(
                        contractNo, UserSession.getCurrent().getOrganisation().getOrgid());
             //Defect #85537
        if (!workIdAndSorId.isEmpty()) {
            this.getModel().setReviseSorId(workIdAndSorId.get(0).getSorId());
            this.getModel().setSorId(workIdAndSorId.get(0).getSorId());
            this.getModel().setNewWorkId(workIdAndSorId.get(0).getWorkId());
        }
        this.getModel().setEstimateRadioFlag(sorNonSor);
        if (!workIdAndSorId.isEmpty()) {
        this.getModel().setChaperList(scheduleOfRateService.getChapterList(orgId,workIdAndSorId.get(0).getSorId()));
        }
        /* Add Mode Of Revised Flag Is New (N) */

        if (this.getModel().getWorkeReviseFlag().equals(MainetConstants.FlagN)
                && this.getModel().getSaveMode().equals(MainetConstants.WorksManagement.ADD)) {
            if (sorNonSor.equals(MainetConstants.FlagS)) {
                if (!workIdAndSorId.isEmpty()) {
                    this.getModel().setReviseSorId(workIdAndSorId.get(0).getSorId());
                    this.getModel().setSorId(workIdAndSorId.get(0).getSorId());
                    this.getModel().setNewWorkId(workIdAndSorId.get(0).getWorkId());
                }
                return new ModelAndView("reviseEstimateSorList", MainetConstants.FORM_NAME, this.getModel());
            } else if (sorNonSor.equals(MainetConstants.FlagN)) {
                if (!workIdAndSorId.isEmpty()) {
                    this.getModel().setNewWorkId(workIdAndSorId.get(0).getWorkId());
                }

                return new ModelAndView(MainetConstants.WorksManagement.WORK_NONSOR_TABLEDETAILS,
                        MainetConstants.FORM_NAME, this.getModel());
            }
        }
        /* Add Mode Of Revised Flag Is Existing (E) */

        if (this.getModel().getWorkeReviseFlag().equals(MainetConstants.WorksManagement.EDIT)
                && this.getModel().getSaveMode().equals(MainetConstants.WorksManagement.ADD)) {
            if (this.getModel().getRevisedEstimateSorList().isEmpty())
                this.getModel().addExistingRevisedEstimateData(sorNonSor, contractNo,
                        UserSession.getCurrent().getOrganisation().getOrgid());
            if (sorNonSor.equals(MainetConstants.FlagS)) {

                return new ModelAndView("reviseEstimateSorList", MainetConstants.FORM_NAME, this.getModel());
            } else if (sorNonSor.equals(MainetConstants.FlagN)) {

                this.getModel().addExistingRevisedEstimateData(sorNonSor, contractNo,
                        UserSession.getCurrent().getOrganisation().getOrgid());
                return new ModelAndView(MainetConstants.WorksManagement.WORK_NONSOR_TABLEDETAILS,
                        MainetConstants.FORM_NAME, this.getModel());
            }
        }
        /* Edit Mode Of Revised Flag Is Existing (E) */

        if (this.getModel().getSaveMode().equals(MainetConstants.WorksManagement.EDIT)
                || this.getModel().getSaveMode().equals(MainetConstants.WorksManagement.VIEW)
                || this.getModel().getSaveMode().equals(MainetConstants.WorksManagement.WCV)) {

            if (this.getModel().getRevisedEstimateSorList().isEmpty())
                this.getModel().editAndViewRevisedEstimate(sorNonSor, this.getModel().getNewContractId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());

            if (sorNonSor.equals(MainetConstants.FlagS)) {
                return new ModelAndView("reviseEstimateSorList", MainetConstants.FORM_NAME, this.getModel());
            } else if (sorNonSor.equals(MainetConstants.FlagN)) {

                this.getModel().editAndViewRevisedEstimate(sorNonSor, this.getModel().getNewContractId(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
                return new ModelAndView(MainetConstants.WorksManagement.WORK_NONSOR_TABLEDETAILS,
                        MainetConstants.FORM_NAME, this.getModel());
            }
        }

        return modelAndView;
    }

    /**
     * Get All Sor details with SorId
     * @param httpServletReuest
     * @param chapterValue
     * @return sor details
     */
    @ResponseBody
    @RequestMapping(params = "getAllRevisedItemsList", method = RequestMethod.POST)
    public List<ScheduleOfRateDetDto> getAllItemsList(HttpServletRequest httpServletReuest,
            @RequestParam(MainetConstants.WorksManagement.SOR_CHAPTER_VAL) Long chapterValue) {
        List<ScheduleOfRateDetDto> rateDetDtos = scheduleOfRateService.getAllItemsListByChapterId(chapterValue,
                this.getModel().getReviseSorId(), UserSession.getCurrent().getOrganisation().getOrgid());
        if (rateDetDtos != null)
            rateDetDtos.forEach(sor -> {
                sor.setSorId(this.getModel().getReviseSorId());
            });
        return rateDetDtos;
    }

    /**
     * This method is used for select all SOR data on the basis of this attribute
     * 
     * @param sorDId
     * @param sorId
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SELECT_ALL_SOR_DATA_BY_SORID)
    public ModelAndView selectAllSor(@RequestParam("sordId") final Long sordId,
            @RequestParam("sorId") final Long sorId, @RequestParam(name = "workEid", required = false) Long workEid,
            final HttpServletRequest request) {
        bindModel(request);
        if (workEid == 0) {
            workEid = null;
        }
        this.getModel().setSorDetailsDto(scheduleOfRateService.findSorItemDetailsBySordId(sordId));
        this.getModel().setSorId(sorId);
        this.getModel().setRevisedEstimateKey(sordId);
        WorkEstimateMasterDto estimateMasterDto = new WorkEstimateMasterDto();
        this.getModel().getRevisedEstimateSorList();
        if (!this.getModel().getRemoveSorDId().isEmpty()) {
            String fileArray[] = this.getModel().getRemoveSorDId().split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                this.getModel().getRevisedEstimateSorList().forEach(s -> {
                    if (s.getSordId().equals(Long.valueOf(fields))) {
                        this.getModel().getRevisedEstimateSorList().remove(s);
                    }
                });
            }
        }
        if (workEid != null) {
            if (!this.getModel().getMeasureMentSubList().containsKey(this.getModel().getRevisedEstimateKey())) {
                estimateMasterDto = ApplicationContextProvider.getApplicationContext().getBean(WorkEstimateService.class)
                        .findById(UserSession.getCurrent().getOrganisation().getOrgid(), workEid);
            }
        }
        this.getModel().getMeasureDetailsREList();
        if (this.getModel().getSaveMode().equals(MainetConstants.FlagV)) {
            List<WorkEstimateMeasureDetailsDto> msList = measurementSheetService.getWorkEstimateDetailsByWorkEId(workEid);
            if (!msList.isEmpty()) {
                this.getModel().setMeasureDetailsREList(msList);
            }
        } else if (estimateMasterDto != null && StringUtils.isNotEmpty(estimateMasterDto.getWorkeReviseFlag())) {
            List<WorkEstimateMeasureDetailsDto> msList = measurementSheetService
                    .getWorkEstimateDetailsByWorkEId(estimateMasterDto.getWorkEstemateId());
            if (!msList.isEmpty()) {
                this.getModel().setMeasureDetailsREList(msList);
            }
        } else if (this.getModel().getMeasureMentSubList().containsKey(this.getModel().getRevisedEstimateKey())) {
            this.getModel().setMeasureDetailsREList(
                    this.getModel().getMeasureMentSubList().get(this.getModel().getRevisedEstimateKey()));
        } else {
            this.getModel().setMeasureDetailsREList(null);
        }
        return new ModelAndView("revisedEstimateMSDetails", MainetConstants.FORM_NAME, this.getModel());
    }

    /**
     * save Revised Estimate LBH Form
     * @param request
     * @return
     */
    @RequestMapping(params = "saveRevisedLbhForm", method = RequestMethod.POST)
    public ModelAndView saveRevisedEstimateLBHForm(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().saveRevisedEstimateLBHform();
        this.getModel().setWorkeReviseFlag(this.getModel().getWorkeReviseFlag());
        this.getModel().setMeasureDetailsREList(this.getModel().getMeasureDetailsREList());
        // this.getModel().setNewContractId(this.getModel().getMsRevisedEstimate().get(0).getContractId());
        // this.getModel().setEstimateRadioFlag("S");
        return defaultResult();
    }

    /**
     * save List Of SOR Details
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_SOR_DETAILS)
    public ModelAndView saveSORDetails(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().saveRevisedEstimateSORData(this.getModel().getRevisedEstimateSorList());
        return defaultResult();

    }

    /**
     * Save Non Sor Data For Revised Estimate
     * @param request
     * @return default Page
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_NONSOR_DETAILS)
    public ModelAndView saveRevisedEstimateNonSORDetails(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().saveNonSorDataForRevisedEstimate(this.getModel().getWorkEstimateNonSorFormList());
        return defaultResult();

    }

    /**
     * Is Used For View And Edit Revised Estimate
     * @param request
     * @param contId
     * @param mode
     * @param estimateType
     * @return
     */
    @RequestMapping(params = "editViewRevisedEstimate", method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView viewRevisedEstimate(final HttpServletRequest request,
            @RequestParam(MainetConstants.WorksManagement.CONTRACT_ID) final Long contId,
            @RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode,
            @RequestParam(MainetConstants.WorksManagement.ESTIM_TYPE) final String estimateType) {
        bindModel(request);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // this.getModel().setWorkeReviseFlag(MainetConstants.FlagN);
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
        return new ModelAndView("AddRevisedEstimate", MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * show Current Form
     * @param request
     * @return
     */
    @RequestMapping(params = "showCurrentForm", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView showCurrentForm(final HttpServletRequest request) {
        getModel().bind(request);
        RevisedEstimateModel estimateModel = this.getModel();
        return new ModelAndView("AddRevisedEstimate", MainetConstants.FORM_NAME,
                estimateModel);
    }

    /**
     * this method is used for Send For Approval For Revised Estimate
     * @param request
     * @param contId
     * @param mode
     * @return
     */
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
                            this.getModel().prepareWorkFlowRevisedEstimate(), workFlowMas,
                            MainetConstants.WorksManagement.WORK_CONTRACT_VARIATION, MainetConstants.FlagA);
            workOrderService.updateContractVariationStatus(contId, MainetConstants.FlagP);
            flag = MainetConstants.FlagY;
        } else {
            flag = MainetConstants.FlagN;
        }
        map.put(MainetConstants.WorksManagement.CHECK_FLAG, flag);
        return map;
    }

    @RequestMapping(params = "clearList", method = RequestMethod.POST)
    public @ResponseBody void clearDataList(HttpServletRequest httpServletRequest) {
        if (!this.getModel().getRevisedEstimateSorList().isEmpty()) {
            this.getModel().getRevisedEstimateSorList().clear();
        }
        if (!this.getModel().getWorkEstimateNonSorFormList().isEmpty())
            this.getModel().getWorkEstimateNonSorFormList().clear();
    }

    /**
     * Used to get Default Value of HSF PREFIX
     * 
     * @param workEstimateModel
     */
    private void populateModel(RevisedEstimateModel model) {

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
