package com.abm.mainet.rts.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.common.dto.CitizenDashBoardResDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.CitizenDashBoardService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.rts.dto.FirstAppealDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.rts.service.IFirstAppealService")
@Api(value = "/first-appeal")
@Path("/first-appeal")
@Service
public class FirstAppealServiceImpl implements IFirstAppealService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FirstAppealServiceImpl.class);

    @Autowired
    private ICFCApplicationMasterService cfcService;

    @Autowired
    private TbServicesMstService tbServicesMstService;

    @Autowired
    private IObjectionDetailsService objectionDetailsService;

    @Autowired
    private TbDepartmentService tbDepartmentService;

    @Autowired
    private LocationMasJpaRepository locationMasJpaRepository;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private IWorkflowActionService iWorkflowActionService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CitizenDashBoardService citizenDashBoardService;

    @Transactional(readOnly = true)
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch First Appeal Data ", notes = "Fetch First Appeal Data", response = Object.class)
    @Path("/fetch-first-appeal-list-test")
    public List<FirstAppealDto> fetchFirstAppealDatatTest(final CitizenDashBoardReqDTO request) {
        List<FirstAppealDto> entities = new ArrayList<>();
        // fetch all services based on orgId and deptId
        Long deptId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.RightToService.RTS_DEPT_CODE);
        List<TbServicesMst> services = tbServicesMstService.findALlActiveServiceByDeptId(deptId, request.getOrgId());
        List<Long> rtsServiceIds = services.stream()
                .map(TbServicesMst::getSmServiceId).collect(Collectors.toList());
        if (!rtsServiceIds.isEmpty()) {
            // fetch data based on orgId and mobile no
            List<TbCfcApplicationMstEntity> cfcApplications = cfcService.fetchCfcApplicationByServiceIdandMobileNo(rtsServiceIds,
                    request.getOrgId(), request.getMobileNo());
            for (TbCfcApplicationMstEntity cfcApplication : cfcApplications) {
                // fetch data from tb_cfc_application_mst and tb_cfc_application_address
                // here check createdDate + SLA +Status validation
                if (!StringUtils.equals(cfcApplication.getApmApplClosedFlag(), MainetConstants.FlagC)) {
                    ApplicantDetailDTO applicant = getApplicantInfo(cfcApplication.getApmApplicationId(), request.getOrgId());
                    FirstAppealDto entity = new FirstAppealDto();
                    entity.setApplicantDetailDTO(applicant);
                    entity.setApplicationNo(cfcApplication.getApmApplicationId());
                    entity.setStatus(cfcApplication.getApmApplSuccessFlag());
                    entity.setServiceId(cfcApplication.getTbServicesMst().getSmServiceId());
                    entity.setServiceName(cfcApplication.getTbServicesMst().getSmServiceName());
                    entity.setApplicationDate(Utility.dateToString(cfcApplication.getApmApplicationDate()));
                    entity.setDeptId(cfcApplication.getTbServicesMst().getTbDepartment().getDpDeptid());
                    entities.add(entity);
                }
            }
        }

        return entities;
    }

    @Transactional(readOnly = true)
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch First Appeal Data ", notes = "Fetch First Appeal Data", response = Object.class)
    @Path("/fetch-first-appeal-list")
    public List<CitizenDashBoardResDTO> fetchFirstAppealData(final CitizenDashBoardReqDTO request) {
        List<CitizenDashBoardResDTO> entities = new ArrayList<>();
        if (request.isEmpty()) {
            LOGGER.error("Invalid request to fetch FIRST APPEAL dashboard data. " + request);
            return entities;
        }
        // fetch list from citizen DASHBOARD view
        List<CitizenDashBoardResDTO> citizenApplications = citizenDashBoardService.getAllApplicationsOfCitizen(request);
        // get objectionOn value from prefix OBJ
        Organisation organisation = new Organisation();
        organisation.setOrgid(request.getOrgId());
        LookUp lookupObj = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.RightToService.FIRST_APPEAL_PREF_CODE, MainetConstants.RightToService.PREFIX_OBJ,
                organisation);
        if (StringUtils.isEmpty(lookupObj.getOtherField())) {
            LOGGER.error("LOOKUP OBJ other value not define ");
            return entities = new ArrayList<>();
        } else {
            int lookupDays = Integer.valueOf(lookupObj.getOtherField());
            for (CitizenDashBoardResDTO citizen : citizenApplications) {
                if (citizen.getDpDeptcode().equals(MainetConstants.RightToService.RTS_DEPT_CODE)
                        && (citizen.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)
                                || citizen.getStatus().equals(MainetConstants.WorkFlow.Status.PENDING))
                        && dateSatisfy(Utility.stringToDate(citizen.getAppDate(), MainetConstants.DATE_FORMAT_WITH_HOURS_TIME),
                                citizen.getSmServiceDuration(), lookupDays)) {
                    // check in TB_OBJECTION_MAST with appId is present or not if not than add in list
                    ServiceMaster sm = tbServicesMstService
                            .findShortCodeByOrgId(MainetConstants.RightToService.SERVICE_CODE.FIRST_APPEAL, request.getOrgId());
                    if (sm != null) {
                        // here appId is String if it contains other than Number than it throw exception parsing
                        if (!citizen.getAppId().matches(MainetConstants.NUMERIC_PATTERN)) {
                            LOGGER.error("Application id is not numeric" + citizen.getAppId());
                            return entities = new ArrayList<>();
                        }
                        ObjectionDetailsDto objDto = objectionDetailsService.getObjectionDetailByIds(request.getOrgId(),
                                sm.getSmServiceId(), Long.valueOf(citizen.getAppId()), lookupObj.getLookUpId());
                        if (objDto.getObjectionId() == null) {
                            CitizenDashBoardResDTO application = new CitizenDashBoardResDTO();
                            application = citizen;
                            entities.add(application);
                        }
                    } else {
                        LOGGER.error("FIRST SERVICE MASTER CONFIGURE FOR FIRST APPEAL");
                        return entities = new ArrayList<>();
                    }
                }
            }
        }
        // filter with RTS_SERVICE/PENDING/CLOSED/+service SLA DURATION

        return entities;
    }

    private boolean dateSatisfy(Date applicationDate, Long smServDuration, int lookupDays) {
        // make from Date and to Date by using no of days
        if (smServDuration != null) {
            Date fromDate = Utility.getAddedDateBy2(applicationDate, smServDuration.intValue());
            Date toDate = Utility.getAddedDateBy2(fromDate, lookupDays);
            return Utility.compareDate(fromDate, new Date()) && Utility.compareDate(new Date(), toDate);
        } else {
            LOGGER.error("configure the service SLA duration");
            return false;
        }
    }

    private ApplicantDetailDTO getApplicantInfo(Long applicationId, Long orgId) {
        TbCfcApplicationMstEntity cfcApplicationMstEntity = cfcService.getCFCApplicationByApplicationId(applicationId, orgId);
        CFCApplicationAddressEntity addressEntity = cfcService.getApplicantsDetails(applicationId);
        ApplicantDetailDTO applicant = new ApplicantDetailDTO();
        applicant.setApplicantFirstName(
                cfcApplicationMstEntity.getApmFname() != null ? cfcApplicationMstEntity.getApmFname() : "NA");
        applicant.setApplicantMiddleName(
                cfcApplicationMstEntity.getApmMname() != null ? cfcApplicationMstEntity.getApmMname() : "NA");
        applicant.setApplicantLastName(
                cfcApplicationMstEntity.getApmLname() != null ? cfcApplicationMstEntity.getApmLname() : "NA");
        applicant.setApplicantTitle(cfcApplicationMstEntity.getApmTitle() != null ? cfcApplicationMstEntity.getApmTitle() : 0);
        applicant.setGender(cfcApplicationMstEntity.getApmSex() != null ? cfcApplicationMstEntity.getApmSex() : "NA");
        setGender(applicant, orgId);
        applicant.setMobileNo(addressEntity.getApaMobilno() != null ? addressEntity.getApaMobilno() : "NA");
        applicant.setEmailId(addressEntity.getApaEmail() != null ? addressEntity.getApaEmail() : "NA");
        applicant.setFlatBuildingNo(addressEntity.getApaFlatBuildingNo() != null ? addressEntity.getApaFlatBuildingNo() : "NA");
        applicant.setBuildingName(addressEntity.getApaBldgnm() != null ? addressEntity.getApaBldgnm() : "NA");
        applicant.setAreaName(addressEntity.getApaAreanm() != null ? addressEntity.getApaAreanm() : "NA");
        applicant.setRoadName(addressEntity.getApaRoadnm() != null ? addressEntity.getApaRoadnm() : "NA");
        applicant.setVillageTownSub(addressEntity.getApaCityName() != null ? addressEntity.getApaCityName() : "NA");
        applicant.setPinCode(addressEntity.getApaPincode() != null ? addressEntity.getApaPincode().toString() : "NA");
        applicant.setBlockName(addressEntity.getApaBlockName() != null ? addressEntity.getApaBlockName() : "NA");
        applicant.setServiceId(cfcApplicationMstEntity.getTbServicesMst().getSmServiceId());
        applicant.setDepartmentId(cfcApplicationMstEntity.getTbServicesMst().getTbDepartment().getDpDeptid());
        if (cfcApplicationMstEntity.getApmUID() != null)
            applicant.setAadharNo(cfcApplicationMstEntity.getApmUID().toString());
        applicant.setBplNo(cfcApplicationMstEntity.getApmBplNo() != null ? cfcApplicationMstEntity.getApmFname() : "NA");
        if (applicant.getBplNo() != null && !applicant.getBplNo().isEmpty()) {
            applicant.setIsBPL(MainetConstants.FlagY);
        } else {
            applicant.setIsBPL(MainetConstants.FlagN);
        }
        return applicant;
    }

    private void setGender(ApplicantDetailDTO applicant, Long orgId) {
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.GENDER, organisation);
        for (final LookUp lookUp : lookUps) {
            if ((applicant.getGender() != null) && !applicant.getGender().isEmpty()) {
                if (lookUp.getLookUpCode().equals(applicant.getGender())) {
                    applicant.setGender(String.valueOf(lookUp.getLookUpId()));
                    break;
                }
            }
        }
    }

    @POST
    @Consumes("application/json")
    @ApiOperation(value = "get applicant data by application Id", notes = "get applicant data by application Id")
    @Path("/get-applicant-data")
    @Override
    @Transactional(readOnly = true)
    public FirstAppealDto getApplicantData(final RequestDTO requestDTO) {
        // ApplicantDetailDTO applicant = getApplicantInfo(requestDTO.getApplicationId(), requestDTO.getOrgId());
        FirstAppealDto entity = new FirstAppealDto();
        TbCfcApplicationMstEntity cfcApplicationMstEntity = cfcService
                .getCFCApplicationByApplicationId(requestDTO.getApplicationId(), requestDTO.getOrgId());
        CFCApplicationAddressEntity addressEntity = cfcService.getApplicantsDetails(requestDTO.getApplicationId());
        ApplicantDetailDTO applicant = new ApplicantDetailDTO();
        applicant.setApplicantFirstName(
                cfcApplicationMstEntity.getApmFname() != null ? cfcApplicationMstEntity.getApmFname() : "NA");
        applicant.setApplicantMiddleName(
                cfcApplicationMstEntity.getApmMname() != null ? cfcApplicationMstEntity.getApmMname() : "NA");
        applicant.setApplicantLastName(
                cfcApplicationMstEntity.getApmLname() != null ? cfcApplicationMstEntity.getApmLname() : "NA");
        applicant.setApplicantTitle(cfcApplicationMstEntity.getApmTitle() != null ? cfcApplicationMstEntity.getApmTitle() : 0);
        applicant.setGender(cfcApplicationMstEntity.getApmSex() != null ? cfcApplicationMstEntity.getApmSex() : 0 + "");
        setGender(applicant, requestDTO.getOrgId());
        applicant.setMobileNo(addressEntity.getApaMobilno() != null ? addressEntity.getApaMobilno() : "NA");
        applicant.setEmailId(addressEntity.getApaEmail() != null ? addressEntity.getApaEmail() : "NA");
        applicant.setFlatBuildingNo(addressEntity.getApaFlatBuildingNo() != null ? addressEntity.getApaFlatBuildingNo() : "NA");
        applicant.setBuildingName(addressEntity.getApaBldgnm() != null ? addressEntity.getApaBldgnm() : "NA");
        applicant.setAreaName(addressEntity.getApaAreanm() != null ? addressEntity.getApaAreanm() : "NA");
        applicant.setRoadName(addressEntity.getApaRoadnm() != null ? addressEntity.getApaRoadnm() : "NA");
        applicant.setVillageTownSub(addressEntity.getApaCityName() != null ? addressEntity.getApaCityName() : "NA");
        applicant.setPinCode(addressEntity.getApaPincode() != null ? addressEntity.getApaPincode().toString() : "NA");
        applicant.setBlockName(addressEntity.getApaBlockName() != null ? addressEntity.getApaBlockName() : "NA");
        applicant.setServiceId(cfcApplicationMstEntity.getTbServicesMst().getSmServiceId());
        applicant.setDepartmentId(cfcApplicationMstEntity.getTbServicesMst().getTbDepartment().getDpDeptid());
        applicant.setApmUID(cfcApplicationMstEntity.getApmUID());
        if (cfcApplicationMstEntity.getApmUID() != null)
            applicant.setAadharNo(cfcApplicationMstEntity.getApmUID().toString());
        entity.setApplicantDetailDTO(applicant);
        entity.setApplicationNo(cfcApplicationMstEntity.getApmApplicationId());
        entity.setApplicationDate(Utility.dateToString(cfcApplicationMstEntity.getApmApplicationDate()));
        entity.setDeptId(applicant.getDepartmentId());

        ServiceMaster frtsService = tbServicesMstService
                .findShortCodeByOrgId(MainetConstants.RightToService.SERVICE_CODE.FIRST_APPEAL,
                        requestDTO.getOrgId());
        ObjectionDetailsDto objection = objectionDetailsService.getObjectionDetailByAppId(requestDTO.getOrgId(),
                frtsService.getSmServiceId(), requestDTO.getApplicationId());
        if (objection != null) {
            entity.setReasonForAppeal(objection.getObjectionReason());
            entity.setGroundForAppeal(objection.getObjectionDetails());
        }
        return entity;
    }

    @Override
    @POST
    @Path("/save-first-appeal")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public ObjectionDetailsDto saveFirstAppealInObjection(ObjectionDetailsDto objDto) {
        // objectionDetailsService.saveObjectionAndCallWorkFlow(objDto);
        // 1st check application is already punch or not if punch than throw error MSG
        ServiceMaster frtsService = tbServicesMstService
                .findShortCodeByOrgId(MainetConstants.RightToService.SERVICE_CODE.FIRST_APPEAL, objDto.getOrgId());
        ObjectionDetailsDto objection = objectionDetailsService.getObjectionDetailByAppId(objDto.getOrgId(),
                frtsService.getSmServiceId(), objDto.getApmApplicationId());
        if (objection != null) {
            List<String> error = new ArrayList<>();
            // throw validation this application id is already FRTS punch
            error.add(objDto.getApmApplicationId() + "  is alreday register for first appeal");
            objDto.setErrorList(error);
            return objDto;
        }

        // mostly in case of External Appeal
        if (objDto.getLocId() != null) {
            setDeptWardZone(objDto);
        }
        objDto.setServiceId(tbServicesMstService
                .findShortCodeByOrgId(MainetConstants.RightToService.SERVICE_CODE.FIRST_APPEAL, objDto.getOrgId())
                .getSmServiceId());
        // get DEPT id also
        objDto.setObjectionDeptId(tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.RightToService.RTS_DEPT_CODE));
        objectionDetailsService.saveAndUpateObjectionMaster(objDto, objDto.getUserId(),
                objDto.getIpAddress());
        RequestDTO reqDto = new RequestDTO();
        setRequestApplicantDetails(reqDto, objDto, objDto.getOrgId(),
                objDto.getUserId());
        fileUploadService.doFileUpload(objDto.getDocs(), reqDto);

        initiateWorkflow(objDto);

        return objDto;
    }

    private void setDeptWardZone(ObjectionDetailsDto objectionDto) {
        LocationOperationWZMapping locOperationWZ = locationMasJpaRepository
                .findbyLocationAndDepartment(objectionDto.getLocId(), objectionDto.getObjectionDeptId());
        if (locOperationWZ != null) {
            if (locOperationWZ.getCodIdOperLevel1() != null) {
                objectionDto.setCodIdOperLevel1(locOperationWZ.getCodIdOperLevel1());
            }
            if (locOperationWZ.getCodIdOperLevel2() != null) {
                objectionDto.setCodIdOperLevel1(locOperationWZ.getCodIdOperLevel1());
            }
            if (locOperationWZ.getCodIdOperLevel3() != null) {
                objectionDto.setCodIdOperLevel1(locOperationWZ.getCodIdOperLevel1());
            }
            if (locOperationWZ.getCodIdOperLevel4() != null) {
                objectionDto.setCodIdOperLevel1(locOperationWZ.getCodIdOperLevel1());
            }
            if (locOperationWZ.getCodIdOperLevel5() != null) {
                objectionDto.setCodIdOperLevel1(locOperationWZ.getCodIdOperLevel1());
            }
        }
    }

    private void setRequestApplicantDetails(final RequestDTO reqDto, ObjectionDetailsDto objDto, Long orgId,
            Long empId) {
        reqDto.setApplicationId(objDto.getApmApplicationId());
        reqDto.setfName(reqDto.getfName());
        reqDto.setDeptId(objDto.getObjectionDeptId());
        reqDto.setOrgId(orgId);
        reqDto.setServiceId(objDto.getServiceId());
        reqDto.setLangId(Long.valueOf(objDto.getLangId()));
        reqDto.setUserId(empId);
        reqDto.setReferenceId(objDto.getObjectionNumber());
    }

    private void signalWorkFlow(ObjectionDetailsDto objectionDto) {
        WorkflowTaskAction workflowAction = new WorkflowTaskAction();
        workflowAction.setApplicationId(objectionDto.getApmApplicationId());
        workflowAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        workflowAction.setReferenceId(objectionDto.getObjectionNumber());
        workflowAction.setIsPaymentGenerated(objectionDto.getIsPaymentGenerated());
        workflowAction.setPaymentMode(objectionDto.getPaymentMode());
        Employee emp = new Employee();
        emp.setEmpId(objectionDto.getUserId());
        emp.setEmpname(objectionDto.getEname());
        emp.setEmplType(objectionDto.getEmpType());
        workflowAction.setIsObjectionAppealApplicable(true);
        iWorkflowActionService.signalWorkFlow(workflowAction, emp, objectionDto.getOrgId(), objectionDto.getServiceId(),
                MainetConstants.WorkFlow.Signal.HEARING);
    }

    private void initiateWorkflow(ObjectionDetailsDto objectionDto) {
        ApplicationMetadata applicationData = new ApplicationMetadata();
        final ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
        applicantDetailDto.setOrgId(objectionDto.getOrgId());
        // applicationData.setApplicationId(provisionalAssesmentMstDto.getApmApplicationId());
        applicationData.setReferenceId(objectionDto.getObjectionNumber());
        applicationData.setIsCheckListApplicable(false);
        applicationData.setOrgId(objectionDto.getOrgId());
        applicantDetailDto.setServiceId(objectionDto.getServiceId());
        applicantDetailDto.setDepartmentId(objectionDto.getObjectionDeptId());
        applicantDetailDto.setUserId(objectionDto.getUserId());
        applicantDetailDto.setOrgId(objectionDto.getOrgId());
        /*
         * applicantDetailDto.setDwzid1(provisionalAssesmentMstDto.getAssWard1());
         * applicantDetailDto.setDwzid2(provisionalAssesmentMstDto.getAssWard2());
         * applicantDetailDto.setDwzid3(provisionalAssesmentMstDto.getAssWard3());
         * applicantDetailDto.setDwzid4(provisionalAssesmentMstDto.getAssWard4());
         * applicantDetailDto.setDwzid5(provisionalAssesmentMstDto.getAssWard5());
         */
        commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
    }

}
