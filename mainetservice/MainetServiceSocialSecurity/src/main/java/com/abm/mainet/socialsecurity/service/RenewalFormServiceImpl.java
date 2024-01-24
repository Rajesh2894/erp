/**
 * 
 */
package com.abm.mainet.socialsecurity.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.socialsecurity.domain.SocialSecurityApplicationForm;
import com.abm.mainet.socialsecurity.repository.SchemeApplicationFormRepository;
import com.abm.mainet.socialsecurity.ui.dto.RenewalFormDto;
import com.abm.mainet.socialsecurity.ui.model.RenewalFormModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author priti.singh
 *
 */

@Produces({ "application/xml", "application/json" })
@WebService(endpointInterface = "com.abm.mainet.socialsecurity.service.RenewalFormService")
@Api(value = "/renewalFormService")
@Path("/renewalFormService")
@Service
public class RenewalFormServiceImpl implements RenewalFormService {

    private static final Logger logger = Logger.getLogger(RenewalFormServiceImpl.class);

    @Autowired
    private SchemeApplicationFormRepository schemeApplicationFormRepository;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IWorkflowActionService iWorkflowActionService;

    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;
    
    @Autowired
    private TbDepartmentService iTbDepartmentService;
    
    @Autowired
    private ServiceMasterService iServiceMasterService;
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Override
    @POST
    @ApiOperation(value = MainetConstants.SocialSecurity.FIND_RENEWAL_DETAILS, notes = MainetConstants.SocialSecurity.FIND_RENEWAL_DETAILS)
    @Path("/findRenewalDetails")
    @Transactional(readOnly = true)

    public RenewalFormDto findRenewalDetails(Long applicationId, String beneficiarynumber, Long orgId) {
        RenewalFormDto dto = new RenewalFormDto();
        try {
            SocialSecurityApplicationForm entity = schemeApplicationFormRepository.findDatabyBenef(applicationId.toString(),
                    beneficiarynumber, orgId);

            BeanUtils.copyProperties(entity, dto);

        } catch (Exception ex) {
            
            throw new FrameworkException(
                    "Exception occurs while finding data from Renewal Form please check inputs", ex);
        }
        return dto;

    }

    @Override
    @POST
    @ApiOperation(value = MainetConstants.SocialSecurity.SAVE_RENEWAL_OF_LIFE_CERTIFICATE, notes = MainetConstants.SocialSecurity.SAVE_RENEWAL_OF_LIFE_CERTIFICATE)
    @Path("/saveRenewalFormDetails")
    @Transactional

    public RenewalFormDto saveRenewalDetails(RenewalFormDto dto) {
        RenewalFormDto newDto;
        try {
            newDto = prepareAndSaveApplicationMaster(dto);
            schemeApplicationFormRepository.updateValidtoDate(dto.getBeneficiaryno(), dto.getValidtoDate());
            
            dto.setMasterAppId(dto.getMasterAppId());
            if ((dto.getUploaddoc() != null) && !dto.getUploaddoc().isEmpty()) {
                fileUploadService.doFileUpload(dto.getUploaddoc(), prepareRequestDto(dto));
            }
           
            initiateWorkFlowForFreeService(newDto);
             
            newDto.setStatusFlag(true);

        } catch (Exception ex) {
            
            throw new FrameworkException(
                    "Exception occured while saving the Renewal Form Details so please check all mandatory fields", ex);
        }

        return dto;
    }

    @Override
    public String getbeneficiarynumber(String ulbName, String serviceSortCode, Long orgId) {
        try {
            Long squenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE,
                    "TB_SWD_SCHEME_APPLICATION", "BENEFICIARY_NUMBER", orgId, MainetConstants.FlagC, null);
            String benefiNo = ulbName + "/" + serviceSortCode + "/" + squenceNo;
            return benefiNo;
        } catch (Exception e) {
            
            throw new FrameworkException("proper input not getting for generate beneficiary number", e);
        }

    }

    @Override
    @Transactional
    public void initiateWorkFlowForFreeService(RenewalFormDto dto) {
        boolean checklist = false;
        if ((dto.getDocumentList() != null) && !dto.getDocumentList().isEmpty()) {
            checklist = true;
        }
        ApplicationMetadata applicationData = new ApplicationMetadata();
        if(dto.getMasterAppId()!=null) {
        applicationData.setApplicationId(dto.getMasterAppId());
        }
        applicationData.setIsCheckListApplicable(checklist);
        applicationData.setOrgId(dto.getOrgId());
        if(dto.getBeneficiaryno()!=null) {
        applicationData.setReferenceId(dto.getBeneficiaryno());
        }
        dto.getApplicant().setServiceId(dto.getServiceId());
        dto.getApplicant().setDepartmentId(dto.getDeptId());
        dto.getApplicant().setUserId(dto.getCreatedBy());
        dto.getApplicant().setOrgId(dto.getOrgId());
        commonService.initiateWorkflowfreeService(applicationData, dto.getApplicant());
    }

    // this code is for the document upload
    private RequestDTO prepareRequestDto(RenewalFormDto newDto) {

        final RequestDTO reqDto = new RequestDTO();
        reqDto.setfName(newDto.getNameofApplicant());
        reqDto.setServiceId(newDto.getServiceId());
        reqDto.setDeptId(newDto.getDeptId());
        reqDto.setUserId(newDto.getCreatedBy());
        reqDto.setOrgId(newDto.getOrgId());
        reqDto.setLangId(newDto.getLangId());
        reqDto.setApplicationId(newDto.getMasterAppId());
        reqDto.setPayStatus(MainetConstants.FlagF);
        reqDto.setReferenceId(newDto.getMasterAppId().toString());

        return reqDto;

    }

    @Transactional
    public boolean saveDecision(RenewalFormDto renewalFormDto, Long orgId, Employee emp,
            WorkflowTaskAction workFlowActionDto, RequestDTO req) {
        boolean status = false;
        
        try {
            iWorkflowActionService.updateWorkFlow(workFlowActionDto, emp, orgId, req.getServiceId());
            // for saving the documents
            if ((renewalFormDto.getDocumentList() != null) && !renewalFormDto.getDocumentList().isEmpty()) {
                fileUploadService.doFileUpload(renewalFormDto.getDocumentList(), req);
                
            }
            
            // sms n email when application gets rejected
            WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
                    .getBean(IWorkflowRequestService.class)
                    .getWorkflowRequestByAppIdOrRefId(renewalFormDto.getMasterAppId(),renewalFormDto.getBeneficiaryno() ,renewalFormDto.getOrgId());

            
            
            if (workflowRequest != null
                    && workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
                // sms and email (when application form rejects (workflow))
                final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
                Organisation org = UserSession.getCurrent().getOrganisation();
                smsDto.setMobnumber(renewalFormDto.getMobNum().toString());  
                smsDto.setAppNo(renewalFormDto.getMasterAppId().toString());
                ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                        .getServiceMasterByShortCode(MainetConstants.SocialSecurity.RENEWAL_OF_LIFE_CERTIFICATE_SERVICE_CODE,
                                UserSession.getCurrent().getOrganisation().getOrgid());
              
                smsDto.setServName(sm.getSmServiceName());  
                String url = "RenewalForm.html";
                org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                int langId = UserSession.getCurrent().getLanguageId();
                ismsAndEmailService.sendEmailSMS("SWD", url,
                        MainetConstants.SocialSecurity.REJECTED, smsDto, org, langId);
               
            }
         // sms n email when application gets rejected /*end*/
            
            // if user is last (last date of certificate gets updated with new valid date) 
             renewalFormDto.setLastDateofLifeCerti(renewalFormDto.getValidtoDate());

            if (iWorkFlowTypeService.isLastTaskInCheckerTaskList(workFlowActionDto.getTaskId())) {
                schemeApplicationFormRepository.updateLastDateofLifeCerti(renewalFormDto.getBeneficiaryno(),
                        renewalFormDto.getLastDateofLifeCerti());
                
                SocialSecurityApplicationForm entity =  schemeApplicationFormRepository.fetchDataOnBenef(renewalFormDto.getBeneficiaryno(), orgId);
                renewalFormDto.setMobNum(entity.getMobNum());
              
              // sms and email (when application form approves (workflow))
                 final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
                 Organisation org = UserSession.getCurrent().getOrganisation();
                
                 smsDto.setMobnumber(renewalFormDto.getMobNum().toString());  
                 if(renewalFormDto.getMasterAppId()!=null) {
                 smsDto.setAppNo(renewalFormDto.getMasterAppId().toString());
                 }
                 ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                         .getServiceMasterByShortCode(MainetConstants.SocialSecurity.RENEWAL_OF_LIFE_CERTIFICATE_SERVICE_CODE,
                                 UserSession.getCurrent().getOrganisation().getOrgid());
              //   setServiceMaster(sm);
                 smsDto.setServName(sm.getSmServiceName());  
                 String url = "RenewalForm.html";
                 org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                 smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                 int langId = UserSession.getCurrent().getLanguageId();
                 ismsAndEmailService.sendEmailSMS("SWD", url,
                         PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, smsDto, org, langId);
                 
            }
            status = true;
        } catch (Exception ex) {
            throw new FrameworkException(
                    "exception occurs while updating workflow,upload docs,updating table", ex);
        }
        return status;
    }

    @Override
    @POST
    @Path("/fetchDataOnBenef")
    @Transactional(readOnly = true)
    @Produces(MediaType.APPLICATION_JSON)
    public SocialSecurityApplicationForm fetchDataOnBenef(RenewalFormDto renewalFormDto) {

        return schemeApplicationFormRepository.fetchDataOnBenef(renewalFormDto.getBeneficiaryno(), renewalFormDto.getOrgId());
    }

    @POST
    @ApiOperation(value = MainetConstants.SocialSecurity.WORKFLOW_FORM_VIEW, notes = MainetConstants.SocialSecurity.WORKFLOW_FORM_VIEW)
    @Path("/workflowFormView")
    @Transactional
    public RenewalFormDto workflowFormView(RenewalFormDto renewalFormDto, Long orgId, Long applicationId) {

        RenewalFormModel renmodel = new RenewalFormModel();

        SocialSecurityApplicationForm entity = schemeApplicationFormRepository.fetchAllData(applicationId.toString(), orgId);
        RenewalFormDto dto = new RenewalFormDto();
        BeanUtils.copyProperties(entity, dto);

        renmodel.getRenewalFormDto().setBeneficiaryno(entity.getBeneficiarynumber());
        renmodel.getRenewalFormDto().setApplicationId(applicationId);
        renmodel.getRenewalFormDto().setNameofApplicant(entity.getNameofApplicant());
        renmodel.getRenewalFormDto().setSelectSchemeName(entity.getSelectSchemeName());
        renmodel.getRenewalFormDto().setLastDateofLifeCerti(entity.getLastDateofLifeCerti());
        renmodel.getRenewalFormDto().setValidtoDate(entity.getValidtoDate());

        return renewalFormDto;

    }
    
    
    @Override
    @Transactional
    public RenewalFormDto prepareAndSaveApplicationMaster(RenewalFormDto dto) {

        TbDepartment deptObj = iTbDepartmentService.findDeptByCode(dto.getOrgId(), MainetConstants.FlagA,
                MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
        ServiceMaster sm = iServiceMasterService
                .getServiceMasterByShortCode(MainetConstants.SocialSecurity.RENEWAL_OF_LIFE_CERTIFICATE_SERVICE_CODE, dto.getOrgId());

        final RequestDTO applicantDetailDTO = new RequestDTO();
        applicantDetailDTO.setfName(dto.getNameofApplicant());
        applicantDetailDTO.setMobileNo(dto.getMobNum().toString());
        applicantDetailDTO.setEmail("");
        applicantDetailDTO.setPincodeNo(dto.getPinCode());
        applicantDetailDTO.setServiceId(sm.getSmServiceId());
        applicantDetailDTO.setDeptId(deptObj.getDpDeptid());
        applicantDetailDTO.setUserId(dto.getCreatedBy());
        applicantDetailDTO.setOrgId(dto.getOrgId());
        applicantDetailDTO.setLangId(dto.getLangId());
        applicantDetailDTO.setPayStatus(MainetConstants.FlagF);
        applicantDetailDTO.setReferenceId(dto.getBeneficiaryno());
        
        final Long applicationId = applicationService.createApplication(applicantDetailDTO);
        dto.setMasterAppId(applicationId);
        dto.setDeptId(deptObj.getDpDeptid());
        dto.setApplicableServiceId(sm.getSmServiceId());
        return dto;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<RenewalFormDto> getAllData(Long orgId) {
        List<RenewalFormDto> renewalformdtolist = new ArrayList<RenewalFormDto>();
        try {
            List<SocialSecurityApplicationForm> entity = schemeApplicationFormRepository.fetchAlldata(orgId); 
            
            if (!entity.isEmpty()) {
                entity.forEach(ent -> {
                    
                if(ent.getValidtoDate()==null) {   
                RenewalFormDto dto = new RenewalFormDto();
                    BeanUtils.copyProperties(ent, dto);
                    dto.setBeneficiaryno(ent.getBeneficiarynumber());
                    renewalformdtolist.add(dto);
                }
                });  
                
        }

        } catch (Exception ex) {
            logger.warn("Exception occurs while finding data from application form please check inputs" + ex);
            throw new FrameworkException(
                    "Exception occurs while finding data from application form please check inputs", ex);
        }
        return renewalformdtolist;
    } 

}