package com.abm.mainet.rts.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.common.dto.CitizenDashBoardResDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
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
import com.abm.mainet.common.workflow.service.IWorkflowActionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.rts.service.ISecondAppealService")
@Api(value = "/second-appeal")
@Path("/second-appeal")
@Service
public class SecondAppealServiceImpl implements ISecondAppealService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecondAppealServiceImpl.class);

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
    @ApiOperation(value = "Fetch Second Appeal Data ", notes = "Fetch Second Appeal Data", response = Object.class)
    @Path("/fetch-second-appeal-list")
    public List<CitizenDashBoardResDTO> fetchSecondAppealData(final CitizenDashBoardReqDTO request) {
        List<CitizenDashBoardResDTO> entities = new ArrayList<>();
        if (request.isEmpty()) {
            LOGGER.error("Invalid request to fetch Second APPEAL dashboard data. " + request);
            return entities;
        }
        ServiceMaster sm = tbServicesMstService
                .findShortCodeByOrgId(MainetConstants.RightToService.SERVICE_CODE.FIRST_APPEAL, request.getOrgId());
        if (sm != null) {
            // get objectionOn value from prefix OBJ
            Organisation organisation = new Organisation();
            organisation.setOrgid(request.getOrgId());
            LookUp lookupSRTSObj = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.RightToService.SECOND_APPEAL_PREF_CODE, MainetConstants.RightToService.PREFIX_OBJ,
                    organisation);
            if (StringUtils.isEmpty(lookupSRTSObj.getOtherField())) {
                LOGGER.error("LOOKUP OBJ other value not define ");
                return entities = new ArrayList<>();
            } else {
                // Rules for 2nd Appeal
                // if REJECTED: 1st appeal updated date + 30
                // if APPROVED: 1st appeal updated date + 30
                // if PENDING: 1st Appeal created date + 45
                // days consider from prefix not from constant
                int lookupDays = Integer.valueOf(lookupSRTSObj.getOtherField());// SRTS other value
                // fetch list from TB_OBJECTION_MAST
                LookUp lookupObj = CommonMasterUtility.getValueFromPrefixLookUp(
                        MainetConstants.RightToService.FIRST_APPEAL_PREF_CODE, MainetConstants.RightToService.PREFIX_OBJ,
                        organisation);
                List<ObjectionDetailsDto> firstAppealObjList = objectionDetailsService.fetchObjectionsByMobileNoAndIds(
                        request.getMobileNo(), request.getOrgId(),
                        sm.getSmServiceId(), lookupObj.getLookUpId());
                for (ObjectionDetailsDto firstAppeal : firstAppealObjList) {
                    if (firstAppeal.getObjectionStatus().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)
                            && (firstAppeal.getUpdatedDate() != null && dateSatisfy(firstAppeal.getUpdatedDate(), lookupDays))) {
                        CitizenDashBoardResDTO application = makeDataForSecondAppeal(organisation, request, firstAppeal);
                        if (application.getAppId() != null)
                            entities.add(application);
                    } else if (firstAppeal.getObjectionStatus().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.PENDING)
                            && dateSatisfyPending(firstAppeal.getCreatedDate(), sm.getSmServiceDuration(), lookupDays)) {
                        CitizenDashBoardResDTO application = makeDataForSecondAppeal(organisation, request, firstAppeal);
                        if (application.getAppId() != null)
                            entities.add(application);
                    } else if (firstAppeal.getObjectionStatus().equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)
                            && (firstAppeal.getUpdatedDate() != null && dateSatisfy(firstAppeal.getUpdatedDate(), lookupDays))) {
                        CitizenDashBoardResDTO application = makeDataForSecondAppeal(organisation, request, firstAppeal);
                        if (application.getAppId() != null)
                            entities.add(application);
                    }
                }
            }
        } else {
            LOGGER.error("FIRST SERVICE MASTER CONFIGURE FOR FIRST APPEAL And Second Appeal");
            return entities = new ArrayList<>();
        }
        return entities;
    }

    private boolean dateSatisfy(Date compareDate, int days) {
        // within date range satisfy
        return Utility.compareDate(new Date(), Utility.getAddedDateBy2(compareDate, days));

    }

    private boolean dateSatisfyPending(Date date, Long smServDuration, int limitDays) {
        if (smServDuration != null) {
            Date fromDate = Utility.getAddedDateBy2(date, smServDuration.intValue());
            Date toDate = Utility.getAddedDateBy2(fromDate, limitDays);
            return Utility.compareDate(fromDate, new Date()) && Utility.compareDate(new Date(), toDate);
        } else {
            LOGGER.error("configure the First Appeal service SLA duration");
            return false;
        }
    }

    private CitizenDashBoardResDTO makeDataForSecondAppeal(Organisation organisation, CitizenDashBoardReqDTO request,
            ObjectionDetailsDto firstAppeal) {
        CitizenDashBoardResDTO application = new CitizenDashBoardResDTO();
        // get objectionOn value from prefix OBJ
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.RightToService.SECOND_APPEAL_PREF_CODE, MainetConstants.RightToService.PREFIX_OBJ,
                organisation);
        ServiceMaster secondAppealService = tbServicesMstService
                .findShortCodeByOrgId(MainetConstants.RightToService.SERVICE_CODE.SECOND_APPEAL, request.getOrgId());
        ObjectionDetailsDto objDto = objectionDetailsService.getObjectionDetailByIds(request.getOrgId(),
                secondAppealService.getSmServiceId(), firstAppeal.getApmApplicationId(), lookup.getLookUpId());
        // get service name by applicationId
        TbCfcApplicationMstEntity cfcData = cfcService.getCFCApplicationByApplicationId(firstAppeal.getApmApplicationId(),
                request.getOrgId());

        if (objDto.getObjectionId() == null) {
            application.setAppId(String.valueOf(firstAppeal.getApmApplicationId()));
            application.setRefId(firstAppeal.getObjectionNumber());
            // application.setApplicantName(firstAppeal.getfName() + " " + firstAppeal.getlName());
            application.setAppDate(Utility.dateToString(firstAppeal.getCreatedDate()));
            application.setStatus(firstAppeal.getObjectionStatus());
            //
            application.setServiceName(cfcData.getTbServicesMst().getSmServiceName());
            application.setSmServiceNameMar(cfcData.getTbServicesMst().getSmServiceNameMar());
            application.setSmShortdesc(cfcData.getTbServicesMst().getSmShortdesc());
            application.setSmServiceDuration(cfcData.getTbServicesMst().getSmServiceDuration());
            application.setDpDeptcode(cfcData.getTbServicesMst().getTbDepartment().getDpDeptcode());
            application.setOrgId(request.getOrgId());
        }
        return application;
    }

    @Override
    @POST
    @Path("/save-second-appeal")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public ObjectionDetailsDto saveSecondAppealInObjection(ObjectionDetailsDto objDto) {
        if (objDto.getLocId() != null) {
            setDeptWardZone(objDto);
        }
        objDto.setServiceId(tbServicesMstService
                .findShortCodeByOrgId(MainetConstants.RightToService.SERVICE_CODE.SECOND_APPEAL, objDto.getOrgId())
                .getSmServiceId());
        objectionDetailsService.saveAndUpateObjectionMaster(objDto, objDto.getUserId(),
                objDto.getIpAddress());
        RequestDTO reqDto = new RequestDTO();
        reqDto.setApplicationId(objDto.getApmApplicationId());
        reqDto.setfName(reqDto.getfName());
        reqDto.setDeptId(objDto.getObjectionDeptId());
        reqDto.setOrgId(objDto.getOrgId());
        reqDto.setServiceId(objDto.getServiceId());
        reqDto.setLangId(Long.valueOf(objDto.getLangId()));
        reqDto.setUserId(objDto.getUserId());
        reqDto.setReferenceId(objDto.getObjectionNumber());
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
