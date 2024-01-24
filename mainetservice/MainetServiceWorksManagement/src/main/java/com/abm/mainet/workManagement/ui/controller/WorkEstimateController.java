package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.workManagement.dto.ReadExcelData;
import com.abm.mainet.workManagement.dto.ScheduleOfRateDetDto;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.SchemeMasterDTO;
import com.abm.mainet.workManagement.dto.WmsMaterialMasterDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WriteExcelData;
import com.abm.mainet.workManagement.service.ScheduleOfRateService;
import com.abm.mainet.workManagement.service.SchemeMasterService;
import com.abm.mainet.workManagement.service.WmsLeadLiftMasterService;
import com.abm.mainet.workManagement.service.WmsMaterialMasterService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.service.WorksMeasurementSheetService;
import com.abm.mainet.workManagement.service.WorksWorkFlowService;
import com.abm.mainet.workManagement.ui.model.WorkEstimateModel;

/**
 * @author vishwajeet.kumar
 * @since 5 feb 2018
 */
@Controller
@RequestMapping("/WorkEstimate.html")
public class WorkEstimateController extends AbstractFormController<WorkEstimateModel> {

    @Autowired
    private ScheduleOfRateService scheduleOfRateService;

    @Autowired
    private WmsProjectMasterService iProjectMasterService;

    @Autowired
    private WorkEstimateService workEstimateService;

    @Autowired
    private WorkDefinitionService workDefinitionService;

    @Autowired
    private SchemeMasterService schememasterservice;

    @Autowired
    private WmsMaterialMasterService materialMasterService;

    @Autowired
    private WmsLeadLiftMasterService leadLiftMasterService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private  IEmployeeService employeeService ;
    /**
     * This Method is used for return work summary pages
     * 
     * @param httpServletRequest
     * @return workEstimateSummary
     */
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {

        sessionCleanup(httpServletRequest);
        fileUpload.sessionCleanUpForFileUpload();
        this.getModel().setParentOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setCommonHelpDocs("WorkEstimate.html");
                                                    this.getModel().setProjectMasterList(
                iProjectMasterService.getActiveProjectMasterListByOrgId(this.getModel().getParentOrgId()));
        this.getModel().prepareAllMasterTypeList();
        List<Long> workIdList = new ArrayList<>();
        workIdList.add(0l);
        this.getModel().setWorkDefinitionDto(workDefinitionService
                .findAllWorkDefinitionsExcludedWork(this.getModel().getParentOrgId(), workIdList, null));
        populateModel(this.getModel());
        return index();
    }

    /**
     * This Method is used for filter Estimate List of Data on search criteria
     * 
     * @param request
     * @param estimateNo
     * @param projectId
     * @param workName
     * @param status
     * @param fromDate
     * @param toDate
     * @return workDefination
     */
    @RequestMapping(params = MainetConstants.WorksManagement.FILTER_ESTIMATE_LIST_DATA, method = RequestMethod.POST)
    public @ResponseBody List<WorkDefinitionDto> getfilterEstimateListData(final HttpServletRequest request,
            @RequestParam(MainetConstants.WorksManagement.ESTIMATE_NO) final String estimateNo,
            @RequestParam(MainetConstants.WorksManagement.PROJECT_ID) final Long projectId,
            @RequestParam(MainetConstants.WorksManagement.WORK_NAME) final Long workName,
            @RequestParam(MainetConstants.WorksManagement.STATUS) final String status,
            @RequestParam(MainetConstants.WorksManagement.FROM_DATE) final Date fromDate,
            @RequestParam(MainetConstants.WorksManagement.TO_DATE) final Date toDate) {

        List<WorkDefinitionDto> workDefination = workDefinitionService.getWorkDefinationBySearch(
                this.getModel().getParentOrgId(), estimateNo, projectId, workName, status, fromDate, toDate);
        TbLocationMas location = null;
        int langId = UserSession.getCurrent().getLanguageId();
        for (WorkDefinitionDto work : workDefination) {
            location = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                    .findById(work.getLocIdSt());
            work.setLocationDesc(location.getLocNameEng());
            if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
            	if (work.getWorkStatus().equals(MainetConstants.FlagA)) {
            		if(langId==1)
            			work.setWorkStatus(MainetConstants.TASK_STATUS_APPROVED);
            		else
            			work.setWorkStatus(ApplicationSession.getInstance().getMessage("wms.summary.status.approved"));
                }
                if (work.getWorkStatus().equals(MainetConstants.FlagP)) {
                	if(langId==1)
                		work.setWorkStatus(MainetConstants.TASK_STATUS_PENDING);
                	else
            			work.setWorkStatus(ApplicationSession.getInstance().getMessage("wms.summary.status.pending"));
                }
                if (work.getWorkStatus().equals(MainetConstants.FlagI)) {
                	if(langId==1)
                		work.setWorkStatus(MainetConstants.TASK_STATUS_INITIATED);
                	else
                		work.setWorkStatus(ApplicationSession.getInstance().getMessage("wms.summary.status.initiated"));
                }
                if (work.getWorkStatus().equals(MainetConstants.FlagD)) {
                	if(langId==1)
                		work.setWorkStatus(MainetConstants.TASK_STATUS_DRAFT);
                	else
                		work.setWorkStatus(ApplicationSession.getInstance().getMessage("wms.summary.status.draft"));
                }
                if (work.getWorkStatus().equals(MainetConstants.FlagR)) {
                	if(langId==1)
                		work.setWorkStatus(MainetConstants.TASK_STATUS_REJECTED);
                	else
                		work.setWorkStatus(ApplicationSession.getInstance().getMessage("wms.summary.status.rejected"));
                }
                if (work.getWorkStatus().equals(MainetConstants.FlagC)) {
                	if(langId==1)
                		work.setWorkStatus(MainetConstants.TASK_STATUS_COMPLETED);
                	else
                		work.setWorkStatus(ApplicationSession.getInstance().getMessage("wms.summary.status.completed"));
                }
                if (work.getWorkStatus().equals(MainetConstants.WorksManagement.FLAG_T)) {
                	if(langId==1)
                		work.setWorkStatus(MainetConstants.WorksManagement.TENDER_GENERATED);
                	else
                		work.setWorkStatus(ApplicationSession.getInstance().getMessage("wms.summary.status.tender.generated"));
                }
                if (work.getWorkStatus().equals(MainetConstants.WorksManagement.TECH_SANCTION)) {
                	if(langId==1)
                		work.setWorkStatus(MainetConstants.WorksManagement.TECH_SANC_APPROVE);
                	else
                		work.setWorkStatus(ApplicationSession.getInstance().getMessage("wms.summary.status.technical.sanction.approved"));
                }
                if (work.getWorkStatus().equals(MainetConstants.WorksManagement.ADMIN_SANCTION)) {
                	if(langId==1)
                		work.setWorkStatus(MainetConstants.WorksManagement.ADMIN_SANC_APPROVE);
                	else
                		work.setWorkStatus(ApplicationSession.getInstance().getMessage("wms.summary.status.admin.sanction.approved"));
                }
            }
            else{
            	if (work.getWorkStatus().equals(MainetConstants.FlagA)) {
                    work.setWorkStatus(MainetConstants.TASK_STATUS_APPROVED);
                }
                if (work.getWorkStatus().equals(MainetConstants.FlagP)) {
                    work.setWorkStatus(MainetConstants.TASK_STATUS_PENDING);
                }
                if (work.getWorkStatus().equals(MainetConstants.FlagI)) {
                    work.setWorkStatus(MainetConstants.TASK_STATUS_INITIATED);
                }
                if (work.getWorkStatus().equals(MainetConstants.FlagD)) {
                    work.setWorkStatus(MainetConstants.TASK_STATUS_DRAFT);
                }
                if (work.getWorkStatus().equals(MainetConstants.FlagR)) {
                    work.setWorkStatus(MainetConstants.TASK_STATUS_REJECTED);
                }
                if (work.getWorkStatus().equals(MainetConstants.FlagC)) {
                    work.setWorkStatus(MainetConstants.TASK_STATUS_COMPLETED);
                }
                if (work.getWorkStatus().equals(MainetConstants.WorksManagement.FLAG_T)) {
                    work.setWorkStatus(MainetConstants.WorksManagement.TENDER_GENERATED);
                }
                if (work.getWorkStatus().equals(MainetConstants.WorksManagement.TECH_SANCTION)) {
                    work.setWorkStatus(MainetConstants.WorksManagement.TECH_SANC_APPROVE);
                }
                if (work.getWorkStatus().equals(MainetConstants.WorksManagement.ADMIN_SANCTION)) {
                    work.setWorkStatus(MainetConstants.WorksManagement.ADMIN_SANC_APPROVE);
                }
            }
            
        }
        return workDefination;
    }

    /**
     * This Method is used for display add pages
     * 
     * @param request
     * @return Add Work Estimate
     */
    @RequestMapping(params = MainetConstants.WorksManagement.ADD_WORK_ESTIMATE, method = RequestMethod.POST)
    public ModelAndView addWorkEstimate(@RequestParam(value = "workId", required = false) Long workId,
            @RequestParam(value = "projId", required = false) Long projId, final HttpServletRequest request) {
        bindModel(request);
        //D94997
		if(projId !=null && workId !=null) {
        	sessionCleanup(request);
        }
        if (this.getModel().getSaveMode() == null) {
            this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
        }
 
        if (workId != null && projId != null) {

            this.getModel()
                    .setProjectMasterList(iProjectMasterService
                            .getActiveProjectMasterListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid())
                            .stream().filter(c -> c.getProjId().equals(projId)).collect(Collectors.toList()));
            this.getModel().setProjectId(this.getModel().getProjectMasterList().get(0).getProjId());

            this.getModel().prepareAllMasterTypeList();

            List<WorkDefinitionDto> workDefinitionDtos = new ArrayList<>();
            workDefinitionDtos.add(workDefinitionService.findAllWorkDefinitionById(workId));
            this.getModel().setWorkDefinitionDto(workDefinitionDtos);

            this.getModel().setNewWorkId(this.getModel().getWorkDefinitionDto().get(0).getWorkId());
        }

        if (this.getModel().getNewWorkId() != null && workId == null) {

            if (this.getModel().getSaveMode().equals(MainetConstants.FlagM)) {
                List<WorkEstimateMasterDto> list = new ArrayList<>();
                List<WorkEstimateMasterDto> mbSorList = workEstimateService.getEstimationByWorkIdAndType(
                        this.getModel().getNewWorkId(), MainetConstants.WorksManagement.MS);
                List<WorkEstimateMasterDto> sorList = workEstimateService
                        .getEstimationByWorkIdAndType(this.getModel().getNewWorkId(), MainetConstants.FlagS);
                list.addAll(mbSorList);
                list.addAll(sorList);
                this.getModel().preapereEditModeData(list);
            } else {
                this.getModel().preapereEditModeData(workEstimateService
                        .getEstimationByWorkIdAndType(this.getModel().getNewWorkId(), MainetConstants.FlagS));
            }

            List<Long> workIdList = new ArrayList<>();
            workIdList.add(0l);
            //D94997
			this.getModel().setWorkDefinitionDto(workDefinitionService
                    .findAllWorkDefinitionsExcludedWork(UserSession.getCurrent().getOrganisation().getOrgid(), workIdList, null));
        }
        return new ModelAndView(MainetConstants.WorksManagement.ADD_WORK_ESTIMATE, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * This Method is used for select All SorData
     * 
     * @param actionParam
     * @param sorId
     * @param request
     * @return AddWorkEstimateList
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SELECT_ALL_SOR_DATA)
    public ModelAndView selectAllSor(
            @RequestParam(MainetConstants.WorksManagement.ACTION_PARAM) final String actionParam,
            @RequestParam(name = MainetConstants.WorksManagement.SOR_ID, required = false) final Long sorId,
            @RequestParam(name = "workFormPrevious", required = false) Long workFormPrevious,
            final HttpServletRequest request) {

        bindModel(request);
        WorkEstimateModel model = this.getModel();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setEsimateType(actionParam);
        if (actionParam.equals(MainetConstants.FlagS)) {
            if (sorId != null) {
                model.setSorCommonId(sorId);
                // model.setSorDetailsList(scheduleOfRateService.findSORMasterWithDetailsBySorId(sorId).getDetDto());
            }
            model.setScheduleOfrateMstList(scheduleOfRateService.getAllActiveScheduleRateMstList(orgId));
        } else if (actionParam.equals(MainetConstants.FlagP)) {
            // Defect #34705--condition changed to show works from previous work condition && ---> ||
            if (this.getModel().getSaveMode().equals("A") || this.getModel().getSaveMode().equals("C")  || this.getModel().getNewWorkId() == null) {
                /* if (this.getModel().getWorkId() == null) { */
                model.setWorkId(sorId);
                /*
                 * List<Long> workIdList = workEstimateService.findAllWorkIdExcludeEstimateTypeU(this.getModel().
                 * getParentOrgId()); if (workIdList.isEmpty()) { workIdList.add(0l); }
                 */
                  //Defect #92903
                this.getModel().setWorkDefinitionDto(
                        workDefinitionService.findAllWorkByOrgIdWithWorkStatus(orgId));
                List<WorkEstimateMasterDto> list = workEstimateService.getWorkEstimateByWorkId(model.getWorkId(),
                        orgId);
                List<ScheduleOfRateDetDto> sorList = new ArrayList<>();
                if (!list.isEmpty()) {
                    model.setSorCommonId(list.get(0).getSorId());
                    list.forEach(dto -> {
                        ScheduleOfRateDetDto sorDetailsDto = new ScheduleOfRateDetDto();
                        if (dto.getWorkMbFlag().equals("SOR")) {
                            sorDetailsDto.setSordId(dto.getSordId());
                            sorDetailsDto.setSorId(dto.getSorId());
                            sorDetailsDto.setSordCategory(dto.getSordCategory());
                            sorDetailsDto.setSordCategoryDesc(dto.getSordCategoryStr());
                            sorDetailsDto.setSordSubCategory(dto.getSordSubCategory());
                            sorDetailsDto.setSorDIteamNo(dto.getSorDIteamNo());
                            sorDetailsDto.setSorDDescription(dto.getSorDDescription());
                            sorDetailsDto.setSorIteamUnitDesc(dto.getSorIteamUnitDesc());
                            sorDetailsDto.setSorBasicRate(dto.getSorBasicRate());
                            sorDetailsDto.setSorLabourRate(dto.getSorLabourRate());
                            // sorDetailsDto.setFlag(dto.getWorkMbFlag());
                            sorDetailsDto.setSorIteamUnit(dto.getSorIteamUnit());
                            // sorDetailsDto.setWorkEstimateId(null);
                            sorList.add(sorDetailsDto);
                        }
                    });
                }
                model.setSorDetailsList(sorList);
            }
            model.setScheduleOfrateMstList(scheduleOfRateService.getAllActiveScheduleRateMstList(orgId));
        }      
		if (model.getSaveMode().equals("E")) {
			model.setChaperList(scheduleOfRateService.getChapterList(orgId, this.getModel().getSorCommonId()));
		} else {
			model.setChaperList(scheduleOfRateService.getChapterList(orgId, sorId));
		}
        
        return new ModelAndView(MainetConstants.WorksManagement.ADD_WORK_ESTIMATE_LIST, MainetConstants.FORM_NAME, model);

    }

    /**
     * This method is used for Add Labour
     * 
     * @param request
     * @return AddWorkLabour
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ADD_WORK_LABOUR)
    public ModelAndView addLabour(final HttpServletRequest request) {

        bindModel(request);
        final Long orgId = this.getModel().getParentOrgId();
        this.getModel().setScheduleOfrateMstList(scheduleOfRateService.getAllActiveScheduleRateMstList(orgId));
        this.getModel().getMaterialMasterList().forEach(obj -> {
            if (this.getModel().getMaterialMapKey().longValue() == obj.getMaId().longValue()) {
                this.getModel().setRateTypeAllMaster(obj);
            }
        });
        if (!this.getModel().getMaterialSubList().isEmpty()
                && this.getModel().getMaterialSubList().containsKey(this.getModel().getMaterialMapKey())) {
            this.getModel().setAddAllRatetypeEntity(
                    this.getModel().getMaterialSubList().get(this.getModel().getMaterialMapKey()));
        } else {
            this.getModel().setAllRateByPrifixType(workEstimateService.getChildRateAnalysisListByMaterialId(
                    this.getModel().getMaterialMapKey(), this.getModel().getRateAnalysisWorkId()));
        }
        return new ModelAndView(MainetConstants.WorksManagement.ADD_WORK_LABOUR, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * This Method is uesd for add Machinery data
     * 
     * @param sorDId
     * @param workEId
     * @param sorId
     * @param request
     * @return AddWorkMachinery
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ADD_WORK_MACHINERY)
    public ModelAndView addMachinery(@RequestParam(name = MainetConstants.WorksManagement.SOR_NAME) final Long sorDId,
            @RequestParam(name = MainetConstants.WorksManagement.WORK_EID) final Long workEId,
            @RequestParam(name = MainetConstants.WorksManagement.SOR_ID) final Long sorId,
            final HttpServletRequest request) {
        bindModel(request);
        final Long orgId = this.getModel().getParentOrgId();
        this.getModel().setMachinaryWorkId(workEId);
        this.getModel().setMachinarySorId(sorId);
        this.getModel().setMachinarySorDId(sorDId);
        this.getModel().setActiveScheduleRateList(scheduleOfRateService.getAllActiveScheduleRateMstList(orgId));
        this.getModel().setSorDetailsDto(scheduleOfRateService.findSorItemDetailsBySordId(sorDId));
        this.getModel().prepareRateAnalysisData(materialMasterService.getMaterialListBySorId(sorId));
        this.getModel().setLeadMasterList(leadLiftMasterService.editLeadLiftData(sorId, MainetConstants.FlagL, orgId));
        this.getModel().setLiftMasterList(leadLiftMasterService.editLeadLiftData(sorId, MainetConstants.FlagF, orgId));

        List<WorkEstimateMasterDto> dtos = new ArrayList<>();
        List<WorkEstimateMasterDto> estimateMasterDtos = workEstimateService.getRateAnalysisListByEstimateId(workEId,
                MainetConstants.FlagC);

        for (WorkEstimateMasterDto workEstimateMasterDto : estimateMasterDtos) {
            if (workEstimateMasterDto.getMaPId() == null
                    && workEstimateMasterDto.getWorkEstimFlag().equals(MainetConstants.FlagC)) {
                workEstimateMasterDto.setRateList(this.getModel().getMachineryMasterList());
                dtos.add(workEstimateMasterDto);
            }
        }
        this.getModel().setAddMachinaryList(dtos);
        this.getModel().setScheduleOfrateMstList(scheduleOfRateService.getAllActiveScheduleRateMstList(orgId));
        return new ModelAndView(MainetConstants.WorksManagement.ADD_WORK_MACHINERY, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * This method is used for add Labour form
     * 
     * @param sorDId
     * @param workEId
     * @param sorId
     * @param request
     * @return AddWorkLabourForm
     */

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.LABOUR_FORM)
    public ModelAndView addLabourForm(@RequestParam(name = MainetConstants.WorksManagement.SOR_NAME) final Long sorDId,
            @RequestParam(name = MainetConstants.WorksManagement.WORK_EID) final Long workEId,
            @RequestParam(name = MainetConstants.WorksManagement.SOR_ID) final Long sorId,
            final HttpServletRequest request) {
        bindModel(request);
        this.getModel().setLabourWorkId(workEId);
        this.getModel().setLabourSorId(sorId);
        this.getModel().setLabourSorDId(sorDId);
        final Long orgId = this.getModel().getParentOrgId();
        this.getModel().setActiveScheduleRateList(scheduleOfRateService.getAllActiveScheduleRateMstList(orgId));
        this.getModel().setSorDetailsDto(scheduleOfRateService.findSorItemDetailsBySordId(sorDId));
        this.getModel().prepareRateAnalysisData(materialMasterService.getMaterialListBySorId(sorId));
        this.getModel().setLeadMasterList(leadLiftMasterService.editLeadLiftData(sorId, MainetConstants.FlagL, orgId));
        this.getModel().setLiftMasterList(leadLiftMasterService.editLeadLiftData(sorId, MainetConstants.FlagF, orgId));
        List<WorkEstimateMasterDto> dtos = new ArrayList<>();
        List<WorkEstimateMasterDto> estimateMasterDtos = workEstimateService.getRateAnalysisListByEstimateId(workEId,
                MainetConstants.FlagA);

        for (WorkEstimateMasterDto workEstimateMasterDto : estimateMasterDtos) {
            if (workEstimateMasterDto.getMaPId() == null
                    && workEstimateMasterDto.getWorkEstimFlag().equals(MainetConstants.FlagA)) {
                workEstimateMasterDto.setRateList(this.getModel().getLabourMasterList());
                dtos.add(workEstimateMasterDto);
            }
        }
        this.getModel().setAddLabourList(dtos);
        this.getModel().setScheduleOfrateMstList(scheduleOfRateService.getAllActiveScheduleRateMstList(orgId));
        return new ModelAndView(MainetConstants.WorksManagement.ADDLABOUR_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * This Method is used for display measurement sheet
     * 
     * @param request
     * @return AddMeasurementSheet
     */

    @RequestMapping(params = MainetConstants.WorksManagement.ADD_MEASUREMENT_SHEET, method = RequestMethod.POST)
    public ModelAndView AddMeasurementSheet(final HttpServletRequest request) {
        bindModel(request);
        String measurementsheetFlag = null;
        if (this.getModel().getSaveMode().equals(MainetConstants.FlagM)) {
            measurementsheetFlag = MainetConstants.WorksManagement.MS;
        } else {
            measurementsheetFlag = MainetConstants.FlagS;
        }
        this.getModel().setMeasurementsheetViewData(
                workEstimateService.getEstimationByWorkIdAndType(this.getModel().getNewWorkId(), measurementsheetFlag));       
        this.getModel().calculateTotalEstimatedAmount();
        return new ModelAndView(MainetConstants.WorksManagement.ADD_MEASUREMENT_SHEET, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * This method is used for select all SOR data on the basis of this attribute
     * 
     * @param sorDId
     * @param workEId
     * @param sorId
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SELECT_ALL_SOR_DATA_BY_SORID)
    public ModelAndView selectAllSor(@RequestParam(name = MainetConstants.WorksManagement.SOR_NAME) final Long sorDId,
            @RequestParam(name = MainetConstants.WorksManagement.WORK_EID) final Long workEId,
            @RequestParam(name = MainetConstants.WorksManagement.SOR_ID) final Long sorId,
            final HttpServletRequest request) {
        bindModel(request);

        this.getModel().setSorDetailsDto(scheduleOfRateService.findSorItemDetailsBySordId(sorDId));
        this.getModel().setSorId(sorDId);
        this.getModel().setMeasurementWorkId(workEId);
        this.getModel().setMeasureDetailsList(ApplicationContextProvider.getApplicationContext()
                .getBean(WorksMeasurementSheetService.class).getWorkEstimateDetailsByWorkEId(workEId));
        return new ModelAndView(MainetConstants.WorksManagement.MEASUREMENT_SHEET_DETAILS, MainetConstants.FORM_NAME,
                this.getModel());
    }

    /**
     * This Method is used show rate analysis pages
     * 
     * @param sorDId
     * @param workEId
     * @param sorId
     * @param request
     * @return openRateAnalysis
     */
    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.OPEN_RATE_ANALYSIS)
    public ModelAndView openRateAnalysis(
            @RequestParam(name = MainetConstants.WorksManagement.SOR_NAME) final Long sorDId,
            @RequestParam(name = MainetConstants.WorksManagement.WORK_EID) final Long workEId,
            @RequestParam(name = MainetConstants.WorksManagement.SOR_ID) final Long sorId,
            final HttpServletRequest request) {
        bindModel(request);
        final Long orgId = this.getModel().getParentOrgId();
        this.getModel().setActiveScheduleRateList(scheduleOfRateService.getAllActiveScheduleRateMstList(orgId));
        this.getModel().setSorDetailsDto(scheduleOfRateService.findSorItemDetailsBySordId(sorDId));
        this.getModel().prepareRateAnalysisData(materialMasterService.getMaterialListBySorId(sorId));
        this.getModel().setLeadMasterList(leadLiftMasterService.editLeadLiftData(sorId, MainetConstants.FlagL, orgId));
        this.getModel().setLiftMasterList(leadLiftMasterService.editLeadLiftData(sorId, MainetConstants.FlagF, orgId));
        this.getModel().setRateAnalysisWorkId(workEId);
        this.getModel().setRateAnalysisSorId(sorId);
        this.getModel().setRateAnalysisSorDId(sorDId);
        this.getModel().setRateAnalysisMaterialList(
                workEstimateService.getRateAnalysisListByEstimateId(workEId, MainetConstants.FlagM));
        return new ModelAndView(MainetConstants.WorksManagement.RATE_ANALYSIS_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.OPEN_SAVE_RATE_FROM_OTHER_TYPE)
    public ModelAndView openSaveRateFromOthertype(final HttpServletRequest request) {
        bindModel(request);
        return new ModelAndView(MainetConstants.WorksManagement.RATE_ANALYSIS_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(params = MainetConstants.WorksManagement.GET_DATE_BY_SORNAME, method = RequestMethod.POST)
    public @ResponseBody String getDateBySorName(
            @RequestParam(name = MainetConstants.WorksManagement.SOR_NAME, required = false) Long sorName) {
        String dateValue = null;
        if (sorName != null) {
            ScheduleOfRateMstDto mstDto = scheduleOfRateService.findSORMasterWithDetailsBySorId(sorName);
            dateValue = new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(mstDto.getSorFromDate())
                    + MainetConstants.operator.COMMA + mstDto.getSorToDate();
        } else {
            dateValue = MainetConstants.BLANK + MainetConstants.operator.COMMA + MainetConstants.BLANK;
        }
        return dateValue;
    }

    @RequestMapping(params = MainetConstants.WorksManagement.SAVE_RATE_ANALYSIS, method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveRateAnalysis(final HttpServletRequest request) {
        this.bindModel(request);
        this.getModel().saveRateAnalysisEntity();
        return index();
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.SAVE_ESTIMATE_DATA, method = RequestMethod.POST)
    public List<String> saveEstimateData(final HttpServletRequest request) {
        this.bindModel(request);
        List<String> status = new ArrayList<>();
        try {
            status = this.getModel().saveEstimateData();
        } catch (Exception e) {
            throw new FrameworkException("Excpetion Occured while saving Estimate Data", e);
        }
        return status;
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_LBH_FORM)
    public ModelAndView SaveLbhForm(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().SaveLbhForm();
        return new ModelAndView(MainetConstants.WorksManagement.ADD_WORK_OVERHEADS_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ADD_WORK_ENCLOSUERS_FORM)
    public ModelAndView addWorkEnclosures(final HttpServletRequest request) {
        bindModel(request);
        fileUpload.sessionCleanUpForFileUpload();
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
                .getBean(IAttachDocsService.class)
                .findByCode(this.getModel().getParentOrgId(), this.getModel().getNewWorkCode());
        this.getModel()
                .setTotalEstimateAmount(workEstimateService.getTotalEstimateAmount(this.getModel().getNewWorkId()));
        this.getModel().setAttachDocsList(attachDocs);
        return new ModelAndView(MainetConstants.WorksManagement.ADD_WORK_ENCLOSUERS_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ADD_WORK_OVERHEADS_FORM)
    public ModelAndView addOverHeadsDetails(final HttpServletRequest request) {
        bindModel(request);
        Organisation org = UserSession.getCurrent().getOrganisation();
        this.getModel().setOverHeadPercentLookUp(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VTY, org));
        this.getModel().setEstimOverHeadDetDto(
                workEstimateService.getEstimateOverHeadDetByWorkId(this.getModel().getNewWorkId()));
        this.getModel()
                .setTotalEstimateAmount(workEstimateService.getTotalEstimateAmount(this.getModel().getNewWorkId()));

        this.getModel().setOverHeadLookUp(CommonMasterUtility.getLookUps("OHT", org));
        return new ModelAndView(MainetConstants.WorksManagement.ADD_WORK_OVERHEADS_FORM, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ADD_WORK_NON_SOR_FORM)
    public ModelAndView addNonSorItems(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().setUnitLookUpList(CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.WUT,
                UserSession.getCurrent().getOrganisation()));
        String nonSorFlag = null;
        if (this.getModel().getSaveMode().equals(MainetConstants.FlagM)) {
            nonSorFlag = MainetConstants.WorksManagement.MN;
        } else {
            nonSorFlag = MainetConstants.FlagN;
        }
        List<WorkEstimateMasterDto> list = workEstimateService
                .getEstimationByWorkIdAndType(this.getModel().getNewWorkId(), nonSorFlag);

        if (this.getModel().getEstimateTypeId().equals("P") && list.isEmpty()) {
            List<WorkEstimateMasterDto> masterDtos = workEstimateService
                    .getEstimationByWorkIdAndType(this.getModel().getWorkpId(), nonSorFlag);
            List<WorkEstimateMasterDto> nonSorList = new ArrayList<>();
            masterDtos.forEach(nonSorDto -> {
                WorkEstimateMasterDto wmsDto = new WorkEstimateMasterDto();
                wmsDto.setSorDIteamNo(nonSorDto.getSorDIteamNo());
                wmsDto.setSorDDescription(nonSorDto.getSorDDescription());
                wmsDto.setWorkEstimQuantity(nonSorDto.getWorkEstimQuantity());
                wmsDto.setSorIteamUnit(nonSorDto.getSorIteamUnit());
                wmsDto.setSorBasicRate(nonSorDto.getSorBasicRate());
                wmsDto.setWorkEstimAmount(nonSorDto.getWorkEstimAmount());
                nonSorList.add(wmsDto);
                /*
                 * if (nonSorDto.getWorkEstemateId() != null && nonSorDto.getWorkId() != null && nonSorDto.getWorkeEstimateNo() !=
                 * null && nonSorDto.getEstimateType() != null && nonSorDto.getWorkEstimAmount() != null) {
                 * nonSorDto.setWorkeEstimateNo(null); nonSorDto.setWorkEstemateId(null); nonSorDto.setWorkEstimActive(null);
                 * nonSorDto.setWorkEstimFlag(null); // nonSorDto.setWorkEstimPId(null); nonSorDto.setWorkId(null);
                 * nonSorDto.setEstimateType(null); nonSorDto.setLgIpMac(null); nonSorDto.setLgIpMacUpd(null);
                 * nonSorDto.setCreatedBy(null); nonSorDto.setCreatedDate(null); nonSorDto.setUpdatedBy(null);
                 * nonSorDto.setUpdatedDate(null); nonSorDto.setContractId(null); nonSorDto.setWorkeReviseFlag(null);
                 * nonSorDto.setOrgId(null); nonSorDto.setWorkEstimAmount(null); nonSorDto.setWorkEstimAmountUtl(null);
                 * nonSorList.add(nonSorDto); }
                 */
            });
            this.getModel().setWorkEstimateNonSorList(nonSorList);
        } else {
            this.getModel().setWorkEstimateNonSorList(
                    workEstimateService.getEstimationByWorkIdAndType(this.getModel().getNewWorkId(), nonSorFlag));
        }
        this.getModel()
                .setTotalEstimateAmount(workEstimateService.getTotalEstimateAmount(this.getModel().getNewWorkId()));
        return new ModelAndView(MainetConstants.WorksManagement.ADD_WORK_NON_SOR_FORM, MainetConstants.FORM_NAME,
                this.getModel());

    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_OVERHEAD_DATA)
    public ModelAndView saveOverHeadData(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().prepareOverHeadData(this.getModel().getEstimOverHeadDetDto());
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_NON_SOR_DATA)
    public ModelAndView saveNonSORData(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().prepareNonSORData(this.getModel().getEstimateMasterDto());
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_MACHINERY_DATA)
    public ModelAndView saveMachineryData(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().prepareMachineryData(this.getModel().getEstimateMasterDto());
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_LABOURFORM)
    public ModelAndView saveLabourFormData(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().prepareLabourFormData(this.getModel().getEstimateMasterDto());
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_LABOUR_DATA)
    public ModelAndView saveLabourData(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().prepareAddLabourData(this.getModel().getEstimateMasterDto());
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_WORK_ENCLOSURES_DATA)
    public ModelAndView saveWorkEnclosuresData(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().prepareWorkEnclosuresData(this.getModel().getEstimateMasterDto());
        workDefinitionService.updateWorkDefinationMode(this.getModel().getNewWorkId(), MainetConstants.FlagD);
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.FILE_COUNT_UPLOAD)
    public ModelAndView fileCountUpload(final HttpServletRequest request) {
        bindModel(request);
        this.getModel().getFileCountUpload().clear();

        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            this.getModel().getFileCountUpload().add(entry.getKey());
        }
        int fileCount = FileUploadUtility.getCurrent().getFileMap().entrySet().size();
        this.getModel().getFileCountUpload().add(fileCount + 1L);
        List<DocumentDetailsVO> attachments = new ArrayList<>();
        for (int i = 0; i <= this.getModel().getAttachments().size(); i++)
            attachments.add(new DocumentDetailsVO());
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

            attachments.get(entry.getKey().intValue()).setDoc_DESC_ENGL(
                    this.getModel().getAttachments().get(entry.getKey().intValue()).getDoc_DESC_ENGL());
        }
        if (attachments.get(this.getModel().getAttachments().size()).getDoc_DESC_ENGL() == null)
            attachments.get(this.getModel().getAttachments().size()).setDoc_DESC_ENGL(MainetConstants.BLANK);
        else {
            DocumentDetailsVO ob = new DocumentDetailsVO();
            ob.setDoc_DESC_ENGL(MainetConstants.BLANK);
            attachments.add(ob);
        }
        this.getModel().setAttachments(attachments);
        return new ModelAndView(MainetConstants.WorksManagement.WORK_ENCLOSURES_FILE_UPLOAD, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(params = MainetConstants.WorksManagement.EDIT_ESTIMATION, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView editEstimation(final HttpServletRequest request,
            @RequestParam(name = MainetConstants.WorkDefination.WORK_ID) final Long workId,
            @RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode) {
        bindModel(request);

        if (request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE) == null
                || (!request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE).toString()
                        .equals(MainetConstants.WorksManagement.APPROVAL)
                        && !request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE).toString()
                                .equals(MainetConstants.WorksManagement.TNDR))) {
            this.getModel().setSaveMode(mode);
            if (mode.equals(MainetConstants.FlagM)) {
                this.getModel().setParentOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                this.getModel().setProjectMasterList(
                        iProjectMasterService.getActiveProjectMasterListByOrgId(this.getModel().getParentOrgId()));
            }
        } else {
            this.getModel().setParentOrgId(
                    (Long) request.getSession().getAttribute(MainetConstants.WorksManagement.WORKS_PARENT_ORGID));
            this.getModel().setRequestFormFlag(
                    request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE).toString());
            if (this.getModel().getParentOrgId() == null) {
                this.getModel().setParentOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            }

            this.getModel().setProjectMasterList(
                    iProjectMasterService.getActiveProjectMasterListByOrgId(this.getModel().getParentOrgId()));
            this.getModel().prepareAllMasterTypeList();
            List<Long> workIdList = workEstimateService.getAllActiveDistinctWorkId(this.getModel().getParentOrgId());
            if (workIdList.isEmpty())
                workIdList.add(0l);
            this.getModel().setSaveMode(mode);
            this.getModel().setWorkDefinitionDto(workDefinitionService
                    .findAllWorkDefinitionsExcludedWork(this.getModel().getParentOrgId(), workIdList, null));
            request.getSession().removeAttribute(MainetConstants.WorksManagement.SAVEMODE);
            request.getSession().removeAttribute(MainetConstants.WorksManagement.WORKS_PARENT_ORGID);
        }

        WorkDefinitionDto definitionDto = workDefinitionService.findAllWorkDefinitionById(workId);
        this.getModel().setNewWorkCode(definitionDto.getWorkcode());
        this.getModel().setWorkId(definitionDto.getWorkId());

        if (this.getModel().getSaveMode().equals(MainetConstants.FlagM)) {
            List<WorkEstimateMasterDto> list = new ArrayList<>();
            List<WorkEstimateMasterDto> mbSorList = workEstimateService.getEstimationByWorkIdAndType(workId,
                    MainetConstants.WorksManagement.MS);
            List<WorkEstimateMasterDto> sorList = workEstimateService.getEstimationByWorkIdAndType(workId,
                    MainetConstants.FlagS);
            list.addAll(mbSorList);
            list.addAll(sorList);
            this.getModel().preapereEditModeData(list);
        } else {
            this.getModel().preapereEditModeData(
                    workEstimateService.getEstimationByWorkIdAndType(workId, MainetConstants.FlagS));
        }

        List<Long> workIdList = new ArrayList<>();
        workIdList.add(0l);
        this.getModel().setWorkDefinitionDto(workDefinitionService
                .findAllWorkDefinitionsExcludedWork(this.getModel().getParentOrgId(), workIdList, null));
        populateModel(this.getModel());
        return new ModelAndView(MainetConstants.WorksManagement.ADD_WORK_ESTIMATE, MainetConstants.FORM_NAME,
                this.getModel());
    }

    @RequestMapping(params = MainetConstants.WorksManagement.GET_RATE_LIST_BY_TYPE, method = RequestMethod.POST)
    public @ResponseBody List<WmsMaterialMasterDto> getRateListByType(final HttpServletRequest request,
            @RequestParam(name = MainetConstants.WorksManagement.RATE_TYPES) final String rateType) {
        bindModel(request);
        WorkEstimateModel model = this.getModel();
        List<WmsMaterialMasterDto> rateList = new ArrayList<>();
        if (rateType.equals(MainetConstants.FlagL)) {
            rateList = model.getLoadtMasterList();
        } else if (rateType.equals(MainetConstants.FlagA)) {
            rateList = model.getLabourMasterList();
        } else if (rateType.equals(MainetConstants.FlagS)) {
            rateList = model.getStackingMasterList();
        } else if (rateType.equals(MainetConstants.FlagC)) {
            rateList = model.getMachineryMasterList();
        } else if (rateType.equals(MainetConstants.WorksManagement.L_E)) {
            rateList = model.getLeadRateList();
        } else if (rateType.equals(MainetConstants.WorksManagement.L_I)) {
            rateList = model.getLiftRateList();
        } else if (rateType.equals(MainetConstants.FlagU)) {
            rateList = model.getUnLoadtMasterList();
        }
        return rateList;
    }

    @RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SEND_FOR_APPROVAL, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> sendForApproval(final HttpServletRequest request,
            @RequestParam(name = MainetConstants.WorkDefination.WORK_ID) final Long workId,
            @RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode) {
        bindModel(request);
        String flag = null;
        Map<String, Object> object = new LinkedHashMap<String, Object>();

        WorkDefinitionDto workDefinitionDto = workDefinitionService.findAllWorkDefinitionById(workId);
        Long projectId = workDefinitionDto.getProjId();

        /*
         * This is Change Behalf Nilima Mam Date 02/08/2019
         * @ aurthor Suhel Chaudhry
         */

        Long sourceOfFund = workDefinitionDto.getSouceOffund();

        WmsProjectMasterDto wmsProjectMasterDto = iProjectMasterService.getProjectMasterByProjId(projectId);
        Long schId = wmsProjectMasterDto.getSchId();
        SchemeMasterDTO SchemeMasterDTO = schememasterservice.getSchemeMasterBySchemeId(schId);
        Long getwmschcodeid2 = SchemeMasterDTO.getWmSchCodeId2();

        this.getModel().setNewWorkCode(workDefinitionDto.getWorkcode());

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        BigDecimal workEstimationAmount = workEstimateService.getTotalEstimateAmount(workId);

        String lookUpCode = CommonMasterUtility
                .getNonHierarchicalLookUpObject(workDefinitionDto.getWorkType(), organisation).getLookUpCode();
        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.WorksManagement.WOA, orgId);
        if (lookUpCode.equals(MainetConstants.FlagL)) {
            flag = MainetConstants.FlagL;
        } else {
        	//#134188 to check amount based workflow is defined or not 
          boolean result = ApplicationContextProvider.getApplicationContext()  
        		  .getBean(IWorkflowTyepResolverService.class).checkAmtBasedWorkflowExist(orgId,sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(),getwmschcodeid2);
          WorkflowMas workFlowMas;
          if (result) {
        	  //if result is true then pass amount 
        	  workFlowMas   = ApplicationContextProvider.getApplicationContext()
                        .getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
                                sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(), workEstimationAmount, sourceOfFund,
                                getwmschcodeid2, null, null, null, null, null);  
           }else {
        	 //if result is false then amount passed as null
        	   workFlowMas   = ApplicationContextProvider.getApplicationContext()
                       .getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
                               sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(), null, sourceOfFund,
                               getwmschcodeid2, null, null, null, null, null);   
           }
        	   
            if (workFlowMas != null) {
                flag = ApplicationContextProvider.getApplicationContext().getBean(WorksWorkFlowService.class)
                        .initiateWorkFlowWorksService(this.getModel().prepareWorkFlowTaskAction(), workFlowMas,
                                MainetConstants.WorksManagement.WORK_EST_APPROVAL, MainetConstants.FlagA);
                if (flag.equals(MainetConstants.SUCCESS_MSG)) {
                    workDefinitionService.updateWorkDefinationMode(workId, MainetConstants.FlagP);
                    flag = MainetConstants.FlagY;
                } else if (flag.equals(MainetConstants.FAILURE_MSG)) {
                    flag = MainetConstants.FlagF;
                }
            } else {
                flag = MainetConstants.FlagN;
            }
        }
        object.put(MainetConstants.WorksManagement.CHECK_FLAG, flag);
        return object;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(params = MainetConstants.WorksManagement.EXPORT_EXCEL, method = { RequestMethod.GET })
    public void exportRateExcelData(final HttpServletResponse response, final HttpServletRequest request) {

        try {
            WriteExcelData data = new WriteExcelData(MainetConstants.WorksManagement.DIRECT_ESTIMATE, request,
                    response);
            populateModel(this.getModel());
            if (this.getModel().getModeCpd().equals(MainetConstants.FlagY))
                data.setAlternetKeyForHeader(MainetConstants.WorksManagement.WORKESTIMATE_CPDMODE);
            data.getExpotedExcelSheet(new ArrayList<>(), WorkEstimateMasterDto.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.LOAD_EXCEL_DATA, method = RequestMethod.POST)
    public List<String> loadValidateAndLoadExcelData(final HttpServletRequest request) {

        WorkEstimateModel model = this.getModel();
        this.getModel().bind(request);
        String filePath = null;
        List<String> errorList = new ArrayList<>();
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            Set<File> list = entry.getValue();
            for (final File file : list) {
                filePath = file.toString();
                break;
            }
        }
        try {
            ReadExcelData data = new ReadExcelData<>(filePath, WorkEstimateMasterDto.class);
            data.parseExcelList();
            List<String> errlist = data.getErrorList();
            List<WorkEstimateMasterDto> dtos = data.getParseData();
            if (model.getProjectId() == null)
                errorList.add(ApplicationSession.getInstance().getMessage("work.Def.valid.select.projName"));
            if (model.getNewWorkId() == null)
                errorList.add(ApplicationSession.getInstance().getMessage("work.estimate.select.work.name"));
            if (model.getEstimateTypeId() == null)
                errorList.add(ApplicationSession.getInstance().getMessage("work.estimate.select.estimate.type"));

            Organisation organisation = new Organisation();
            organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            int countError = 1;
            for (WorkEstimateMasterDto wmsDto : dtos) {

                wmsDto.setSordCategory(CommonMasterUtility.getIdFromPrefixLookUpDesc(wmsDto.getSordCategoryStr(),
                        MainetConstants.WorksManagement.WKC, (int) UserSession.getCurrent().getLanguageId(),
                        organisation));
                wmsDto.setSorIteamUnit(CommonMasterUtility.getIdFromPrefixLookUpDesc(wmsDto.getSorIteamUnitStr(),
                        MainetConstants.WorksManagement.WUT, (int) UserSession.getCurrent().getLanguageId(),
                        organisation));
                if (wmsDto.getSordCategory() == -1 || wmsDto.getSordCategory() == null)
                    errorList.add(ApplicationSession.getInstance().getMessage("sor.select.chaapter.row") + " " + countError);
                if (wmsDto.getSorIteamUnit() == -1 || wmsDto.getSorIteamUnit() == null)
                    errorList
                            .add(ApplicationSession.getInstance().getMessage("work.estimate.select.unit") + " " + countError);
                if (wmsDto.getSorDDescription() == null)
                    errorList.add(ApplicationSession.getInstance().getMessage("work.estimate.enter.description")
                            + " " + countError);
                if (wmsDto.getSorDIteamNo() == null)
                    errorList.add(
                            ApplicationSession.getInstance().getMessage("work.estimate.enter.item.code") + " " + countError);
                if (wmsDto.getMeNos() == null || wmsDto.getMeNos() == 0)
                    errorList.add(ApplicationSession.getInstance().getMessage("work.estimate.enter.nos") + " " + countError);
                if (wmsDto.getSorBasicRate() == null)
                    errorList.add(ApplicationSession.getInstance().getMessage("work.estimate.enter.rate") + " " + countError);
                if (wmsDto.getWorkEstimQuantity() == null)
                    errorList.add(
                            ApplicationSession.getInstance().getMessage("work.estimate.enter.quantity") + " " + countError);
                if (wmsDto.getSorBasicRate() != null && wmsDto.getWorkEstimQuantity() != null) {
                    wmsDto.setWorkEstimAmount(wmsDto.getSorBasicRate().multiply(wmsDto.getWorkEstimQuantity()));
                }
                countError++;
            }
            if (!errorList.isEmpty()) {
                errorList.addAll(errlist);
            } else {
                model.setWorkEstimateList(dtos);
                model.saveEstimateData();
                fileUpload.sessionCleanUpForFileUpload();
            }
        } catch (Exception e) {
            errorList.add("Error While Uploading Excel: " + model.getExcelFilePathDirects()
                    + ": For More Details Check Error Log.");
        }

        return errorList;
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GETWORKS_LIST, method = RequestMethod.POST)
    public List<WorkDefinitionDto> getWorksList(HttpServletRequest httpServletReuest,
            @RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId) {
        List<Long> workIdList = new ArrayList<>();
        List<WorkDefinitionDto> obj = new ArrayList<WorkDefinitionDto>(); 
        if (this.getModel().getNewWorkId() == null) {
            workIdList = workEstimateService.getAllActiveDistinctWorkId(this.getModel().getParentOrgId());
            if (workIdList.isEmpty()) {
                workIdList.add(0l);
            }
        } else {
            workIdList.add(0l);
        }
        
        obj = workDefinitionService.findAllWorkDefinitionsExcludedWork(this.getModel().getParentOrgId(), workIdList,
                projId);
        obj.forEach(dto -> {
			dto.setWorkName("" + dto.getWorkcode() + " - " + dto.getWorkName());
		});
		this.getModel().setWorkDefinationList(obj);
		this.getModel().setCommonHelpDocs("WorkEstimate.html");
        return obj;
    }

    /**
     * Used to get All Active Works Name By ProjectId
     * 
     * @param orgId
     * @param projId
     * @return
     */
    @RequestMapping(params = MainetConstants.WorksManagement.WORKS_NAME, method = RequestMethod.POST)
    public @ResponseBody List<WorkDefinitionDto> getAllActiveWorksNameByProjectId(
            @RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<WorkDefinitionDto> obj = workDefinitionService.findAllWorkDefinationByProjId(orgId, projId);
        //changes
        obj.forEach(dto -> {
			dto.setWorkName("" + dto.getWorkcode() + " - " + dto.getWorkName());
		});
		this.getModel().setWorkDefinationList(obj);
		this.getModel().setCommonHelpDocs("WorkEstimate.html");
		//to
        return obj;
    }

    /**
     * Used to get Default Value of HSF PREFIX
     * 
     * @param workEstimateModel
     */
    private void populateModel(WorkEstimateModel workEstimateModel) {

        // check for sub category flag is active or not
        List<LookUp> lookUpList = CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.HSF,
                UserSession.getCurrent().getOrganisation());
        if (lookUpList != null && !lookUpList.isEmpty()) {
            workEstimateModel.setModeCpd(lookUpList.get(0).getLookUpCode());
        } else {
            workEstimateModel.setModeCpd(null);
        }
        //Defect #92745
        List<WorkDefinitionDto> workDefination = workDefinitionService.findAllWorkDefinitionsByOrgId(this.getModel().getParentOrgId());
        TbLocationMas location = null;

        for (WorkDefinitionDto work : workDefination) {
            location = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                    .findById(work.getLocIdSt());
            work.setLocationDesc(location.getLocNameEng());
            if(work.getWorkStatus() != null) {
            if (work.getWorkStatus().equals(MainetConstants.FlagA)) {
                work.setWorkStatus(MainetConstants.TASK_STATUS_APPROVED);
            }
            if (work.getWorkStatus().equals(MainetConstants.FlagP)) {
                work.setWorkStatus(MainetConstants.TASK_STATUS_PENDING);
            }
            if (work.getWorkStatus().equals(MainetConstants.FlagI)) {
                work.setWorkStatus(MainetConstants.TASK_STATUS_INITIATED);
            }
            if (work.getWorkStatus().equals(MainetConstants.FlagD)) {
                work.setWorkStatus(MainetConstants.TASK_STATUS_DRAFT);
            }
            if (work.getWorkStatus().equals(MainetConstants.FlagR)) {
                work.setWorkStatus(MainetConstants.TASK_STATUS_REJECTED);
            }
            if (work.getWorkStatus().equals(MainetConstants.FlagC)) {
                work.setWorkStatus(MainetConstants.TASK_STATUS_COMPLETED);
            }
            if (work.getWorkStatus().equals(MainetConstants.WorksManagement.FLAG_T)) {
                work.setWorkStatus(MainetConstants.WorksManagement.TENDER_GENERATED);
            }
            if (work.getWorkStatus().equals(MainetConstants.WorksManagement.TECH_SANCTION)) {
                work.setWorkStatus(MainetConstants.WorksManagement.TECH_SANC_APPROVE);
            }
            if (work.getWorkStatus().equals(MainetConstants.WorksManagement.ADMIN_SANCTION)) {
                work.setWorkStatus(MainetConstants.WorksManagement.ADMIN_SANC_APPROVE);
            }
            }
          
        }
        this.getModel().setWorkDefinationList(workDefination);      
    }

    @RequestMapping(params = MainetConstants.WorksManagement.GET_WORKFLOW_HISTORY, method = RequestMethod.POST)
    public @ResponseBody ModelAndView getWorkFlowHistory(
            @RequestParam(name = MainetConstants.WorkDefination.WORK_ID) final Long workId,
            @RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode, ModelMap modelMap) {

        String workCode = workDefinitionService.findAllWorkDefinitionById(workId).getWorkcode();

        WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowRequestService.class).getWorkflowRequestByAppIdOrRefId(null, workCode,
                        UserSession.getCurrent().getOrganisation().getOrgid());
           if(workflowRequest != null) {
           List<WorkflowTaskActionWithDocs> actionHistory = ApplicationContextProvider.getApplicationContext()
                .getBean(IWorkflowActionService.class).getActionLogByUuidAndWorkflowId(workCode,
                        workflowRequest.getId(), (short) UserSession.getCurrent().getLanguageId());
            for (WorkflowTaskActionWithDocs workflowTaskAction : actionHistory) {
            	  Employee employee =  employeeService.findEmployeeByIdAndOrgId(workflowTaskAction.getEmpId(), workflowTaskAction.getOrgId());
            	  //from changes
            	  StringBuffer empName=new StringBuffer(employee.getEmpname());
            	  if(employee.getEmpmname() != null && !(employee.getEmpmname()).isEmpty())
            		  empName.append(" "+employee.getEmpmname());
            	  if(employee.getEmplname()!=null)
            		  empName.append(" "+employee.getEmplname());
            	  workflowTaskAction.setEmpName(empName.toString()); //to
				/* workflowTaskAction.setEmpName(employee.getEmpname()); */
            	  workflowTaskAction.setEmpEmail(employee.getEmpemail());
            	  workflowTaskAction.setEmpGroupDescEng(employee.getDesignation().getDsgname());
            	  workflowTaskAction.setEmpGroupDescReg(employee.getDesignation().getDsgnameReg());
            	  modelMap.addAllAttributes(actionHistory);
			}
           
        modelMap.put(MainetConstants.WorksManagement.ACTIONS, actionHistory);
            }
        return new ModelAndView(MainetConstants.WorksManagement.WORK_WORKFLOW_HISTORY, MainetConstants.FORM_NAME,
                modelMap);
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.WorksManagement.GET_ALL_ITEMS_LIST, method = RequestMethod.POST)
    public List<ScheduleOfRateDetDto> getAllItemsList(HttpServletRequest httpServletReuest,
            @RequestParam(MainetConstants.WorksManagement.SOR_CHAPTER_VAL) Long chapterValue) {
        return scheduleOfRateService.getAllItemsListByChapterId(chapterValue, this.getModel().getSorCommonId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
    }

    @ResponseBody
    @RequestMapping(params = "approvedLegacyData", method = RequestMethod.POST)
    public String approvedLegacyData(@RequestParam("workId") final Long workId) {
        String s = MainetConstants.FlagF;
        if (workId != null) {
            workDefinitionService.updateWorkDefinationMode(workId, MainetConstants.FlagA);
            s = MainetConstants.FlagS;
        }
        return s;
    }
}
