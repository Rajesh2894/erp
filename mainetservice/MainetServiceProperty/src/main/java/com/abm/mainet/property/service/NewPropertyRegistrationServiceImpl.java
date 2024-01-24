package com.abm.mainet.property.service;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.repository.SelfAssessmentMasJpaRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;

@Service
@WebService(endpointInterface = "com.abm.mainet.property.service.NewPropertyRegistrationService")
@Api(value = "/newPropertyReg")
@Path("/newPropertyReg")
public class NewPropertyRegistrationServiceImpl implements NewPropertyRegistrationService {
    /**
     * 
     */
    private static final long serialVersionUID = 2374902677901270163L;

    @Resource
    private LocationMasJpaRepository locationMasJpaRepository;

    @Resource
    private SelfAssessmentMasJpaRepository selfAssessmentJpaRepository;

    @Resource
    private ServiceMasterRepository serviceMasterRepository;

    @Resource
    private DepartmentService departmentService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private IProvisionalAssesmentMstService iProvisionalAssesmentMstService;

    @Resource
    private IFileUploadService fileUploadService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private CommonService commonService;

    @Transactional
    @Override
    public void saveNewPropertyRegistration(ProvisionalAssesmentMstDto provAsseMstDto, Long orgId, Long empId, Long deptId,
            int langId, List<Long> finYearList) {
        RequestDTO reqDto = new RequestDTO();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        setRequestApplicantDetails(reqDto, provAsseMstDto, orgId, deptId, langId, empId, provAsseMstDto.getSmServiceId());
        final Long applicationNo = applicationService.createApplication(reqDto);
        iProvisionalAssesmentMstService.saveProvisionalAssessment(provAsseMstDto, orgId, empId,
                finYearList, applicationNo);
        if ((provAsseMstDto.getDocs() != null) && !provAsseMstDto.getDocs().isEmpty()) {
            reqDto.setApplicationId(applicationNo);
            fileUploadService.doFileUpload(provAsseMstDto.getDocs(), reqDto);
        }
        sendSmsAndMail(provAsseMstDto, organisation, langId, empId);
    }

    private void setRequestApplicantDetails(final RequestDTO reqDto, ProvisionalAssesmentMstDto asseMstDto, Long orgId,
            Long deptId, int langId, Long empId, Long serviceId) {
        ProvisionalAssesmentOwnerDtlDto ownDtlDto = asseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0);
        reqDto.setfName(ownDtlDto.getAssoOwnerName());
        reqDto.setMobileNo(ownDtlDto.getAssoMobileno());
        reqDto.setEmail(asseMstDto.getAssEmail());
        reqDto.setPincodeNo(asseMstDto.getAssPincode());
        reqDto.setDeptId(deptId);
        reqDto.setPayStatus(MainetConstants.PAYMENT.FREE);
        if (reqDto.getGender() != null) {
            reqDto.setGender(ownDtlDto.getGenderId().toString());
        }
        reqDto.setOrgId(orgId);
        reqDto.setServiceId(serviceId);
        reqDto.setLangId(Long.valueOf(langId));
        reqDto.setUserId(empId);
    }

    private void sendSmsAndMail(final ProvisionalAssesmentMstDto provAsseMstDto, Organisation organisation, int langId,
            Long userId) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(provAsseMstDto.getAssEmail());
        dto.setMobnumber(provAsseMstDto.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoMobileno());
        dto.setUserId(userId);
        ServiceMaster service = serviceMasterRepository.getServiceMaster(provAsseMstDto.getSmServiceId(),
                provAsseMstDto.getOrgId());
        if (langId == MainetConstants.MARATHI) {
            dto.setServName(service.getSmServiceNameMar());
        } else {
            dto.setServName(service.getSmServiceName());
        }
        dto.setAppNo(provAsseMstDto.getApmApplicationId().toString());
        dto.setPropertyNo(provAsseMstDto.getAssNo());
        ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "NewPropertyRegistration.html",
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, organisation, langId);
    }

    @Override
    @Transactional
    public void callWorkFlow(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId, Long serviceId, Long deptId) {
        ApplicationMetadata applicationData = new ApplicationMetadata();
        final ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
        applicantDetailDto.setOrgId(orgId);
        applicationData.setApplicationId(provisionalAssesmentMstDto.getApmApplicationId());
        applicationData.setIsCheckListApplicable(
                (provisionalAssesmentMstDto.getDocs() != null && !provisionalAssesmentMstDto.getDocs().isEmpty()) ? true
                        : false);
        applicationData.setOrgId(orgId);
        applicantDetailDto.setServiceId(serviceId);
        applicantDetailDto.setDepartmentId(deptId);
        applicantDetailDto.setUserId(provisionalAssesmentMstDto.getCreatedBy());
        applicantDetailDto.setOrgId(orgId);
        applicantDetailDto.setDwzid1(provisionalAssesmentMstDto.getAssWard1());
        applicantDetailDto.setDwzid2(provisionalAssesmentMstDto.getAssWard2());
        applicantDetailDto.setDwzid3(provisionalAssesmentMstDto.getAssWard3());
        applicantDetailDto.setDwzid4(provisionalAssesmentMstDto.getAssWard4());
        applicantDetailDto.setDwzid5(provisionalAssesmentMstDto.getAssWard5());
        commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
    }

}
