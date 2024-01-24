package com.abm.mainet.common.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.payment.dao.PaymentDAO;
import com.abm.mainet.common.integration.payment.entity.PGBankDetail;
import com.abm.mainet.common.integration.payment.entity.PGBankParameter;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.model.mapping.CommonModelMapper;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

@Service
public class CommonServiceImpl implements CommonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Resource
    private CommonModelMapper serviceMapper;

    @Resource
    private ServiceMasterRepository serviceMasterRepository;

    @Resource
    private ServiceMasterService serviceMasterService;

    @Autowired
    ICFCApplicationMasterService cfcService;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private IWorkflowExecutionService workflowExecutionService;

    @Resource
    private IWorkflowTyepResolverService iWorkflowTyepResolverService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private MessageSource messageSource;

    @Resource
    private PaymentDAO paymentDAO;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.service.CommonService#initiateWorkflowfreeService(com.abm.mainet.common.workflow.dto.
     * ApplicationMetadata, com.abm.mainet.common.dto.ApplicantDetailDTO)
     */
    @Override
    public void initiateWorkflowfreeService(ApplicationMetadata applicationData,
            ApplicantDetailDTO applicantDetailsDto) {
        String checklistFlag = null;

        String processName = serviceMasterService.getProcessName(applicantDetailsDto.getServiceId(),
                applicationData.getOrgId());
        if (processName != null) {
            Organisation org = new Organisation();
            org.setOrgid(applicationData.getOrgId());
            ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(applicantDetailsDto.getServiceId(),
                    applicationData.getOrgId());
            WorkflowTaskAction workflowAction = new WorkflowTaskAction();
            if (applicationData.getIsCheckListApplicable() != null && applicationData.getIsCheckListApplicable().equals(true)) {
                List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(
                        applicationData.getApplicationId(),
                        applicationData.getOrgId());
                workflowAction.setAttachementId(attachmentId);
                final List<LookUp> lookUps = CommonMasterUtility.getLookUps("APL",
                        org);
                for (final LookUp lookUp : lookUps) {
                    if (serviceMaster.getSmChklstVerify() != null
                            && lookUp.getLookUpId() == serviceMaster.getSmChklstVerify()) {
                        checklistFlag = lookUp.getLookUpCode();
                        break;
                    }
                }
                if (MainetConstants.Common_Constant.ACTIVE_FLAG.equalsIgnoreCase(checklistFlag)
                        && MainetConstants.FlagY.equalsIgnoreCase(serviceMaster.getSmCheckListReq())) {
                    applicationData.setIsCheckListApplicable(true);
                } else if (MainetConstants.Common_Constant.ACTIVE_FLAG.equalsIgnoreCase(checklistFlag)
                        && MainetConstants.FlagN.equalsIgnoreCase(serviceMaster.getSmCheckListReq())) {
                    applicationData.setIsCheckListApplicable(false);
                } else if (MainetConstants.Common_Constant.NO.equalsIgnoreCase(checklistFlag)) {
                    applicationData.setIsCheckListApplicable(false);
                } else if (MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistFlag)) {
                    applicationData.setIsCheckListApplicable(false);
                }
            }
            boolean autoescalate = false;
         
            WorkflowMas mas=null;
            if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL) && MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION.equals(serviceMaster.getSmShortdesc())) {
           	 mas = ApplicationContextProvider.getApplicationContext()
	                    .getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(org.getOrgid(),
                   		applicantDetailsDto.getDepartmentId(), applicantDetailsDto.getServiceId(),applicantDetailsDto.getConnectonSize(),
                           null,  applicantDetailsDto.getDwzid1(), applicantDetailsDto.getDwzid2(), applicantDetailsDto.getDwzid3(),
                           applicantDetailsDto.getDwzid4(), applicantDetailsDto.getDwzid5());
           	 
            }else if(applicantDetailsDto.getConnectonSize() != null) {
			 mas = ApplicationContextProvider.getApplicationContext()
	                    .getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(org.getOrgid(),
	                    		applicantDetailsDto.getDepartmentId(), applicantDetailsDto.getServiceId(),applicantDetailsDto.getConnectonSize(),
	                            null, null, null, null, null, null);
            }
            else if(applicantDetailsDto.getExtIdentifier()!=null){
            	mas = iWorkflowTyepResolverService.resolveExtWorkflowType(applicationData.getOrgId(),
                        applicantDetailsDto.getDepartmentId(), applicantDetailsDto.getServiceId(),applicantDetailsDto.getExtIdentifier(),
                        applicantDetailsDto.getDwzid1(), applicantDetailsDto.getDwzid2(), applicantDetailsDto.getDwzid3(),
                        applicantDetailsDto.getDwzid4(), applicantDetailsDto.getDwzid5());
            }
            else {
			 mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(applicationData.getOrgId(),
                    applicantDetailsDto.getDepartmentId(), applicantDetailsDto.getServiceId(),
                    applicantDetailsDto.getDwzid1(), applicantDetailsDto.getDwzid2(), applicantDetailsDto.getDwzid3(),
                    applicantDetailsDto.getDwzid4(), applicantDetailsDto.getDwzid5());
            }
  
            if (mas != null) {
                String code = CommonMasterUtility
                        .getNonHierarchicalLookUpObject(mas.getWorkflowMode(), mas.getOrganisation())
                        .getLookUpCode();
                if (code.equals("AE")) {
                    autoescalate = true;
                }
                applicationData.setWorkflowId(mas.getWfId());
            }
            WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
            applicationData.setIsAutoEscalate(autoescalate);
            applicationData.setIsFreeService(true);
            if (MainetConstants.Y_FLAG.equalsIgnoreCase(serviceMaster.getSmScrutinyApplicableFlag())) {
                applicationData.setIsScrutinyApplicable(true);
            } else {
                applicationData.setIsScrutinyApplicable(false);
            }
            TaskAssignment requesterTaskAssignment = setRequesterTask(applicationData,
                    applicantDetailsDto);
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TCP)) {
				if (StringUtils.equalsIgnoreCase(serviceMaster.getSmShortdesc(),
						MainetConstants.BuildingPlanning.SERVICE_CODE_TPPR)) {
					requesterTaskAssignment.setDeptId(applicantDetailsDto.getDepartmentId());
					requesterTaskAssignment.setServiceId(applicantDetailsDto.getServiceId());
					requesterTaskAssignment.setUrl(MainetConstants.BuildingPlanning.PROF_REG_FORM_URL);
				}
				if (StringUtils.equalsIgnoreCase(serviceMaster.getSmShortdesc(),
						MainetConstants.BuildingPlanning.NEW_LICENSE_SERVICE)) {
					requesterTaskAssignment.setDeptId(applicantDetailsDto.getDepartmentId());
					requesterTaskAssignment.setServiceId(applicantDetailsDto.getServiceId());
					requesterTaskAssignment.setUrl("ApplicationAuthorization.html");
				}
				if (null != applicantDetailsDto.getDwzid1()) {
					workflowAction.setCodIdOperLevel1(applicantDetailsDto.getDwzid1());
				}
				if (null != applicantDetailsDto.getDwzid2()) {
					workflowAction.setCodIdOperLevel2(applicantDetailsDto.getDwzid2());
				}
				if (null != applicantDetailsDto.getDwzid3()) {
					workflowAction.setCodIdOperLevel3(applicantDetailsDto.getDwzid3());
				}
				if (null != applicantDetailsDto.getDwzid4()) {
					workflowAction.setCodIdOperLevel4(applicantDetailsDto.getDwzid4());
				}
				if (null != applicantDetailsDto.getDwzid5()) {
					workflowAction.setCodIdOperLevel5(applicantDetailsDto.getDwzid5());
				}
			}
            workflowdto.setRequesterTaskAssignment(requesterTaskAssignment);
            applicationData.setPaymentMode(MainetConstants.PAYMENT.FREE);
            workflowdto.setApplicationMetadata(applicationData);
            workflowdto
                    .setProcessName(processName);
            if(applicationData.getApplicationId() !=null && applicationData.getReferenceId() ==null) {
            	
            	 workflowAction.setApplicationId(applicationData.getApplicationId());
                 workflowAction.setReferenceId(applicationData.getApplicationId().toString());
            }else {
            	
            	workflowAction.setApplicationId(applicationData.getApplicationId());
                 workflowAction.setReferenceId(applicationData.getReferenceId());
            	
            }
            
            workflowAction.setDateOfAction(new Date());
          workflowAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
            workflowAction.setOrgId(applicationData.getOrgId());
            workflowAction.setEmpId(applicantDetailsDto.getUserId());
            workflowAction.setCreatedBy(applicantDetailsDto.getUserId());
            workflowAction.setCreatedDate(new Date());
            workflowdto.setWorkflowTaskAction(workflowAction);
            try {
                workflowExecutionService.initiateWorkflow(workflowdto);
            } catch (Exception e) {
                throw new FrameworkException("Exception while creating workflow for free services", e);
            }

        }
    }

    private TaskAssignment setRequesterTask(final ApplicationMetadata applicationData,
            final ApplicantDetailDTO applicantDetailsDto) {
        TaskAssignment assignment = new TaskAssignment();
        Set<String> actorIds = new HashSet<>();
        assignment
                .setActorId(applicantDetailsDto.getUserId() + MainetConstants.operator.COMMA + applicantDetailsDto.getMobileNo());
        actorIds.add(Long.toString(applicantDetailsDto.getUserId()));
        actorIds.add(applicantDetailsDto.getMobileNo());
        assignment.setActorIds(actorIds);
        assignment.setOrgId(applicationData.getOrgId());
        return assignment;
    }

    @Override
    public String findTitleDesc(final long titleId, final Organisation org) {

        String title = StringUtils.EMPTY;
        final List<LookUp> lookUps = CommonMasterUtility.getLookUps(PrefixConstants.LookUp.TITLE, org);
        if ((lookUps != null) && !lookUps.isEmpty()) {
            for (final LookUp lookUp : lookUps) {
                if (lookUp.getLookUpId() == titleId) {
                    title = lookUp.getLookUpDesc();
                    break;
                }
            }
        } else {
            throw new FrameworkException("No LookUps found for TTL Prefix for OrganizationId=" + org.getOrgid());
        }

        return title == null ? MainetConstants.BLANK : title;
    }

    @Override
    @Transactional
    public ApplicantDetailDTO populateApplicantInfo(ApplicantDetailDTO dto, final TbCfcApplicationMstEntity cfcEntity) {

        if (dto == null) {
            dto = new ApplicantDetailDTO();
        }
        final long applicationId = cfcEntity.getApmApplicationId();

        return initializeApplicantAddressDetail(initializeApplicationDetail(dto, cfcEntity),
                cfcService.getApplicantsDetails(applicationId));
    }

    private ApplicantDetailDTO initializeApplicationDetail(final ApplicantDetailDTO dto, final TbCfcApplicationMstEntity entity) {

        dto.setApplicantTitle(entity.getApmTitle());
        dto.setApplicantFirstName(entity.getApmFname());
        dto.setApplicantMiddleName(entity.getApmMname());
        dto.setApplicantLastName(entity.getApmLname());

        return dto;
    }

    /**
     * 
     * @param dto
     * @param addressEntity
     * @return
     */
    private ApplicantDetailDTO initializeApplicantAddressDetail(final ApplicantDetailDTO dto,
            final CFCApplicationAddressEntity addressEntity) {

        dto.setMobileNo(addressEntity.getApaMobilno());
        dto.setEmailId(addressEntity.getApaEmail());
        dto.setFloorNo(addressEntity.getApaFloor());
        dto.setBuildingName(addressEntity.getApaBldgnm());
        dto.setRoadName(addressEntity.getApaRoadnm());
        dto.setAreaName(addressEntity.getApaAreanm());
        if (addressEntity.getApaPincode() != null) {
            dto.setPinCode(Long.toString(addressEntity.getApaPincode()));
        }
        return dto;

    }

    @Override
    public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId,
            final Long serviceId, final Long orgId) {
        WardZoneBlockDTO wardZoneBlockDTO = new WardZoneBlockDTO();
        try {
            Class<?> clazz = null;
            Object dynamicServiceInstance = null;
            String serviceClassName = null;
            final ServiceMaster service = serviceMasterService.getServiceMaster(serviceId, orgId);
            serviceClassName = messageSource.getMessage(
                    service.getSmShortdesc(), new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            

            if (serviceClassName != null && !(MainetConstants.BLANK.equalsIgnoreCase(serviceClassName))) {
                clazz = ClassUtils.forName(serviceClassName,
                        ApplicationContextProvider.getApplicationContext()
                                .getClassLoader());

                dynamicServiceInstance = ApplicationContextProvider
                        .getApplicationContext().getAutowireCapableBeanFactory()
                        .autowire(clazz, 4, false);
                final Method method = ReflectionUtils.findMethod(clazz,
                        "getWordZoneBlockByApplicationId", new Class[] { Long.class,
                                Long.class, Long.class });
                wardZoneBlockDTO = (WardZoneBlockDTO) ReflectionUtils.invokeMethod(
                        method, dynamicServiceInstance, new Object[] { applicationId,
                                serviceId, orgId });
            }
        } catch (LinkageError | Exception e) {
            LOGGER.error("Exception in getWordZoneBlockByApplicationId for applicationId:" + applicationId, e);
        }
        return wardZoneBlockDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.scrutiny.service.ScrutinyService# findServiceActionUrl(long)
     */
    @Override
    @Transactional
    public List<String> findServiceActionUrl(final long applicationId, final long orgId) {

        final long smServiceId = cfcService.getServiceIdByApplicationId(applicationId, orgId);
        TbCfcApplicationMstEntity cfcMst = cfcService.getCFCApplicationByApplicationId(applicationId, orgId);
        List<String> paramList = null;
        paramList = serviceMasterService.getServiceActionUrlParams(smServiceId, orgId);
        paramList.add(MainetConstants.INDEX.ONE, Long.toString(smServiceId));
        paramList.add(MainetConstants.INDEX.TWO,cfcMst.getRefNo());
        return paramList;
    }

    @Override
    @Transactional
    public Map<Long, String> getAllPgBank(final long orgid) throws FrameworkException {
        return paymentDAO.getAllPgBank(orgid);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentTransactionMas getOnlineTransactionMasByTrackId(final Long trackId) {
        return paymentDAO.getOnlineTransactionMasByTrackId(trackId);
    }

    @Override
    @Transactional(readOnly = true)
    public PGBankDetail getBankDetailByBankName(String cbBankName, Long orgId) {
        return paymentDAO.getBankDetailByBankName(cbBankName, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PGBankParameter> getMerchantMasterParamByPgId(Long PgId, long orgId) {
        return paymentDAO.getMerchantMasterParamByBankId(PgId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getcsIdnByConnectionNo(String connectionNo, Long orgId)
    		throws ClassNotFoundException, LinkageError{
    	Long csIdn = null;
		Class<?> clazz = null;
		Object dynamicServiceInstance = null;
		String serviceClassName = null;
        serviceClassName = "com.abm.mainet.water.service.NewWaterConnectionServiceImpl";
		clazz = ClassUtils.forName(serviceClassName,
				ApplicationContextProvider.getApplicationContext().getClassLoader());
		dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
				.autowire(clazz, 4, false);
		final Method method = ReflectionUtils.findMethod(clazz,
				ApplicationSession.getInstance().getMessage("water.connection.details"),
				new Class[] { String.class, Long.class });
		csIdn = (Long) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
				new Object[] { connectionNo, orgId });
		
		return csIdn;   	
    }
    
    @Override
    @Transactional
    public Map<Long, String> getAllPgBankByDeptCode(final long orgid, final String deptCode) throws FrameworkException {
        return paymentDAO.getAllPgBankByDeptCode(orgid, deptCode);
    }
}
