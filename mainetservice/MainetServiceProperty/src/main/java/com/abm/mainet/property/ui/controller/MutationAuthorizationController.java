package com.abm.mainet.property.ui.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.checklist.domain.ChecklistStatusView;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.TaskAssignmentRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.ITaskAssignmentService;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.dto.PublicNoticeDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.PropertyTransferService;
import com.abm.mainet.property.ui.model.MutationModel;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Controller
@RequestMapping("/MutationAuthorization.html")
public class MutationAuthorizationController extends AbstractFormController<MutationModel> {

    private static final Logger LOGGER = Logger.getLogger(MutationAuthorizationController.class);
    @Autowired
    private PropertyTransferService propertyTransferService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private AssesmentMastService assesmentMastService;

    @Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private MutationService mutationService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private ILocationMasService iLocationMasService;

    @Autowired
    private IWorkflowTaskService iWorkflowTaskService;

    @Autowired
    private IObjectionDetailsService objectionDetailsService;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private ICFCApplicationMasterService applicationMasterService;
    
	@Autowired
	private ICFCApplicationAddressService iCFCApplicationAddressService;
	
    @Autowired
    private ISMSAndEmailService ismsAndEmailService;
    
    @Autowired
    private IWorkflowRequestService workflowReqService;
    
    @Autowired
    private IWorkflowActionService workflowActionService;

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView defaultLoad(@RequestParam("appNo") final long applicationId, @RequestParam("taskId") final long serviceId,
            @RequestParam("actualTaskId") final long taskId, final HttpServletRequest httpServletRequest) throws Exception {
        this.sessionCleanup(httpServletRequest);
        getData(applicationId, serviceId, taskId);
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
        	//getModel().setSendbackToEmpFisrtLevel(true);
        	getModel().setAuthLevel(iWorkFlowTypeService.curentCheckerLevel(taskId));
        	WorkflowRequest workflowRequest = workflowReqService
					.getWorkflowRequestByAppIdOrRefId(applicationId, null, UserSession.getCurrent().getOrganisation().getOrgid());
			if (workflowRequest != null && MainetConstants.WorkFlow.Decision.SEND_BACK.equalsIgnoreCase(workflowRequest.getLastDecision())
					&& getModel().isAuthLevel()) {
				getModel().setHideUserAction(true);
				getModel().setMutationLevelFlag(MainetConstants.FlagE);
				List<WorkflowTaskActionWithDocs> taskActionWithDocs = workflowActionService.getActionLogByUuidAndWorkflowId(String.valueOf(applicationId), 
						workflowRequest.getId(), (short)UserSession.getCurrent().getLanguageId());
				if(CollectionUtils.isNotEmpty(taskActionWithDocs)) {
					List<WorkflowTaskActionWithDocs> remarkList = taskActionWithDocs.stream().filter(remark -> 
					MainetConstants.WorkFlow.Decision.SEND_BACK_REMARK.equals(remark.getDecision()))
					.collect(Collectors.toList());
					if(CollectionUtils.isNotEmpty(remarkList)) {
						getModel().getWorkflowActionDto().setComments(remarkList.get(remarkList.size()-1).getComments());
					}
				}
				return new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME, getModel());
			}
				
        }
        return new ModelAndView("MutationAuthView", MainetConstants.FORM_NAME, getModel());
    }

    @Override
    @RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
    public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
            @RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
            final HttpServletRequest httpServletRequest) throws Exception {
        this.sessionCleanup(httpServletRequest);
        getData(Long.valueOf(applicationId), serviceId, taskId);
        return new ModelAndView("MutationAuthViewDetails", MainetConstants.FORM_NAME, getModel());
    }

    public void getData(long applicationId, long serviceId, long taskId) throws Exception {
        fileUpload.sessionCleanUpForFileUpload();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        MutationModel model = this.getModel();
        model.setAssType(MainetConstants.Property.MUT);
        model.setOrgId(orgId);
        ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
        model.setServiceShortCode(service.getSmShortdesc());
        model.setServiceId(service.getSmServiceId());
        model.setDeptId(service.getTbDepartment().getDpDeptid());
        model.setServiceMast(service);
        PropertyTransferMasterDto dto = propertyTransferService.getPropTransferMstByAppId(orgId, applicationId);
        model.setNoOfDaysAuthFlag(MainetConstants.FlagN);
        if (iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId) == true) {
            List<ObjectionDetailsDto> objectionDetailsDto = objectionDetailsService.getObjectionDetailListByAppId(orgId,
                    serviceId,
                    applicationId);
            objectionDetailsDto.forEach(objDetail -> {
                if (!objDetail.getObjectionStatus().equals(MainetConstants.FlagI)) {
                    getModel().setNoOfDaysAuthFlag(MainetConstants.FlagI);
                }
            });
            Date afterAddingDaysToCreatedDate = null;
            try {
                String noOfDays = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.PNC,
                        UserSession.getCurrent().getOrganisation()).getOtherField();
                LocalDate convertCreateDateToLocalDate = dto.getCreatedDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                afterAddingDaysToCreatedDate = Date.from(
                        convertCreateDateToLocalDate.plusDays(Long.valueOf(noOfDays))
                                .atStartOfDay(ZoneId.systemDefault()).toInstant());
            } catch (Exception e) {
                // TODO: handle exception
            }
            if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL) && afterAddingDaysToCreatedDate != null && Utility.compareDate(new Date(), afterAddingDaysToCreatedDate)) {
                getModel().setNoOfDaysAuthFlag(MainetConstants.FlagY);
            }
        }
        dto.setApmApplicationId(applicationId);
        dto.setSmServiceId(serviceId);
        dto.setDeptId(service.getTbDepartment().getDpDeptid());
        LookUp mutationType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                dto.getTransferType(), UserSession.getCurrent().getOrganisation());
        if (mutationType != null) {
            dto.setTransferTypeDesc(mutationType.getLookUpDesc());
        }
		ProvisionalAssesmentMstDto assMst = null;
		if (StringUtils.isNotBlank(dto.getFlatNo())) {
			List<ProvisionalAssesmentMstDto> assMstList = assesmentMastService.getPropDetailFromAssByPropNoFlatNo(orgId,
					dto.getProAssNo(), dto.getFlatNo());
			if (CollectionUtils.isNotEmpty(assMstList)) {
				assMst = assMstList.get(0);
			}
		} else {
			assMst = assesmentMastService.fetchAssessmentMasterByPropNo(orgId, dto.getProAssNo());
		}
        setDescriptionOfOwners(model, dto, assMst);
        model.setApmApplicationId(applicationId);
        model.setPropTransferDto(dto);
        model.setMutationLevelFlag("A");
        assMst.setLocationName(iLocationMasService.getLocationNameById(assMst.getLocId(), orgId));
        model.setProvisionalAssesmentMstDto(assMst);
        getModel().setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
        if(CollectionUtils.isNotEmpty(getModel().getDocumentList())) {
        	if(getModel().getDocumentList().get(0) != null) {
        		ChecklistStatusView applicantDetails = new ChecklistStatusView();
            	applicantDetails.setApmApplicationId(getModel().getDocumentList().get(0).getApplicationId());
            	applicantDetails.setApmApplicationDate(getModel().getDocumentList().get(0).getAttDate());
            	applicantDetails.setServiceName(serviceMaster.getServiceNameByServiceId(getModel().getDocumentList().get(0).getServiceId()));
            	getModel().setApplicationDetails(applicantDetails);
        	}
        }

        UserTaskDTO task = null;
        if (taskId > 0) {
            Long curentCheckerLevel = 1l;
            Long currentCheckerGroup = 1l;
            try {
                task = iWorkflowTaskService.findByTaskId(taskId);
            } catch (Exception e) {
                logger.warn("Unable to retrieve task data for task id " + taskId, e);
            }
            if (task != null) {
                if (task.getCurrentCheckerGroup() != null && task.getCurentCheckerLevel() != null) {

                    curentCheckerLevel = task.getCurentCheckerLevel();
                    currentCheckerGroup = task.getCurrentCheckerGroup();

                    TaskAssignmentRequest taskAssignmentRequest = new TaskAssignmentRequest();
                    taskAssignmentRequest.setWorkflowTypeId(task.getWorkflowId());
                    taskAssignmentRequest.setServiceEventName(MainetConstants.WorkFlow.EventNames.CHECKER);
                    LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> checkerLevelGroups = ApplicationContextProvider
                            .getApplicationContext().getBean(ITaskAssignmentService.class)
                            .getEventLevelGroupsByWorkflowTypeAndEventName(taskAssignmentRequest);
                    if (currentCheckerGroup == checkerLevelGroups.size()) {
                        LinkedHashMap<String, TaskAssignment> lastCheckerLevelGroup = checkerLevelGroups.get(
                                MainetConstants.GROUP + MainetConstants.operator.UNDER_SCORE + currentCheckerGroup);
                        if (lastCheckerLevelGroup.size() - curentCheckerLevel == 1) {
                            model.setBeforeLastAuthorizer(true);
                        }
                    }

                    model.setCheckListVrfyFlag(service.getSmCheckListReq());
                    model.setCurentCheckerLevel(curentCheckerLevel);
                }
            }
        }
        // US#96773 KDMC
        try {
            final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp("BPN", "ENV",
                    UserSession.getCurrent().getOrganisation());
            if (lookup != null
                    && StringUtils.equals(lookup.getOtherField(), MainetConstants.FlagY)) {
                model.setBypassPublicNotice(lookup.getOtherField());
            }
        } catch (Exception e) {
            LOGGER.error("No prefix found for ENV(BPN)" + e);
        }
        // end

        model.setLastAuthorizer(iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId));
        getModel().getWorkflowActionDto().setApplicationId(applicationId);

        if (assMst.getAssLandType() != null && assMst.getAssLandType() > 0) {
            assMst.setAssLandTypeDesc(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(assMst.getAssLandType(),
                            UserSession.getCurrent().getOrganisation())
                    .getDescLangFirst());
            LookUp landTypePrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(
                    assMst.getAssLandType(), UserSession.getCurrent().getOrganisation());
            getModel().setLandTypePrefix(landTypePrefix.getLookUpCode());
        }
		// To set flat level owner name in case of Individual property
		if (assMst.getBillMethod() != null && MainetConstants.FlagI.equals(CommonMasterUtility
				.getNonHierarchicalLookUpObject(assMst.getBillMethod(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode())) {
			getModel().setOwnershipPrefix(MainetConstants.Property.SO);
			List<ProvisionalAssesmentOwnerDtlDto> ownerDtoList = new ArrayList<>();
			ProvisionalAssesmentOwnerDtlDto ownerDto = new ProvisionalAssesmentOwnerDtlDto();
			ProvisionalAssesmentDetailDto detailDto = assMst.getProvisionalAssesmentDetailDtoList()
					.get(assMst.getProvisionalAssesmentDetailDtoList().size() - 1);
			ownerDto.setAssoOwnerName(detailDto.getOccupierName());
			ownerDto.setAssoOwnerNameReg(detailDto.getOccupierNameReg());
			ownerDto.seteMail(detailDto.getOccupierEmail());
			ownerDto.setAssoMobileno(detailDto.getOccupierMobNo());
			ownerDtoList.add(ownerDto);
			assMst.setProvisionalAssesmentOwnerDtlDtoList(ownerDtoList);
		}
		
        model.setProvisionalAssesmentMstDto(assMst);
        if (getModel().getLandTypePrefix() != null) {
            getModel().setlandTypeDetails();
        }
        model.setDropDownValues(assMst, UserSession.getCurrent().getOrganisation());
        model.getWorkflowActionDto().setTaskId(taskId);
        boolean lastApproval = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
                .isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());
        if (lastApproval && StringUtils.equals(service.getSmScrutinyChargeFlag(), MainetConstants.FlagY)) {
            getModel().setLoiChargeApplFlag(MainetConstants.FlagY);
        } else {
            getModel().setLoiChargeApplFlag(MainetConstants.FlagN);
        }
        // US#89348 Sum ALV/ARV of all floor.
        List<Double> totalARV = new ArrayList<Double>();
        List<ProvisionalAssesmentDetailDto> detailDtoList = assMst.getProvisionalAssesmentDetailDtoList();
        for (ProvisionalAssesmentDetailDto detailDto : detailDtoList) {
            if (detailDto.getAssdRv() != null) {
                totalARV.add(detailDto.getAssdRv());
            }
        }
        if (CollectionUtils.isNotEmpty(totalARV)) {
            Double arv = totalARV.stream().mapToDouble(Double::doubleValue).sum();
            assMst.setTotalArv(arv);
        } else {
            assMst.setTotalArv(0.0);
        }// END of US#89348
        //US#137279 START
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)
        		|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
        	TbCfcApplicationMstEntity masterEntity = applicationMasterService
    				.getCFCApplicationByRefNoOrAppNo(null, applicationId, orgId);
        	
        	if(masterEntity != null) {
        		CFCApplicationAddressEntity addressEntity = applicationMasterService
    					.getApplicantsDetails(masterEntity.getApmApplicationId());
    			ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
    			BeanUtils.copyProperties(addressEntity, applicantDto);
    			BeanUtils.copyProperties(masterEntity, applicantDto);
    			if (masterEntity.getApmTitle() != null)
    				applicantDto.setApplicantTitle(masterEntity.getApmTitle());
    			applicantDto.setApplicantFirstName(masterEntity.getApmFname());
    			applicantDto.setApplicantMiddleName(masterEntity.getApmMname());
    			applicantDto.setApplicantLastName(masterEntity.getApmLname());
    			if(addressEntity != null) {
    				if(addressEntity.getApaPincode() != null) {
    					applicantDto.setPinCode(String.valueOf(addressEntity.getApaPincode()));
    				}
    				applicantDto.setEmailId(addressEntity.getApaEmail());
    				applicantDto.setMobileNo(addressEntity.getApaMobilno());
    				applicantDto.setDwzid1(addressEntity.getApaZoneNo());
    				applicantDto.setDwzid2(addressEntity.getApaWardNo());
    				if(addressEntity.getApaBlockno() != null) {
    					applicantDto.setDwzid3(Long.valueOf(addressEntity.getApaBlockno()));
    				}
    				applicantDto.setRoadName(addressEntity.getApaRoadnm());
    				applicantDto.setAreaName(addressEntity.getApaCityName());
    				applicantDto.setBuildingName(addressEntity.getApaBldgnm());
    				applicantDto.setBlockName(addressEntity.getApaBlockName());
    			}
    			getModel().setApplicantDetailDto(applicantDto);
        	}
        }//US#137279 END
        getModel().checkListDecentralized(applicationId, serviceId, orgId);

    }

    private void setDescriptionOfOwners(MutationModel model, PropertyTransferMasterDto dto, ProvisionalAssesmentMstDto assMst) {
        LookUp ownerType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                assMst.getAssOwnerType(), UserSession.getCurrent().getOrganisation());
        model.setOwnershipPrefix(ownerType.getLookUpCode());
        LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownerType.getLookUpCode(),
                MainetConstants.Property.propPref.OWT,
                UserSession.getCurrent().getOrganisation());
        model.setOwnershipTypeValue(lookup.getDescLangFirst());
        assMst.setProAssOwnerTypeName(ownerType.getDescLangFirst());
        LookUp ownerTypeNew = CommonMasterUtility.getNonHierarchicalLookUpObject(
                dto.getOwnerType(), UserSession.getCurrent().getOrganisation());

        if (MainetConstants.Property.SO.equals(ownerType.getLookUpCode())
                || MainetConstants.Property.JO.equals(ownerType.getLookUpCode())) {
            for (ProvisionalAssesmentOwnerDtlDto owner : assMst.getProvisionalAssesmentOwnerDtlDtoList()) {
                if (owner.getGenderId() != null) {
                    owner.setProAssGenderId(
                            CommonMasterUtility
                                    .getNonHierarchicalLookUpObject(owner.getGenderId(),
                                            UserSession.getCurrent().getOrganisation())
                                    .getDescLangFirst());
                } else {
                    owner.setProAssGenderId(MainetConstants.CommonConstants.NA);
                }
                if (owner.getRelationId() != null) {
                    owner.setProAssRelationId(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                    UserSession.getCurrent().getOrganisation()).getDescLangFirst());
                } else {
                    owner.setProAssRelationId(MainetConstants.CommonConstants.NA);
                }
            }
        }
        if (MainetConstants.Property.SO.equals(ownerTypeNew.getLookUpCode())
                || MainetConstants.Property.JO.equals(ownerTypeNew.getLookUpCode())) {
            for (PropertyTransferOwnerDto ownerNew : dto.getPropTransferOwnerList()) {
                if (ownerNew.getGenderId() != null) {
                    ownerNew.setGenderIdDesc(
                            CommonMasterUtility
                                    .getNonHierarchicalLookUpObject(ownerNew.getGenderId(),
                                            UserSession.getCurrent().getOrganisation())
                                    .getDescLangFirst());
                } else {
                    ownerNew.setGenderIdDesc(MainetConstants.CommonConstants.NA);
                }
                if (ownerNew.getRelationId() != null) {
                    ownerNew.setRelationIdDesc(
                            CommonMasterUtility.getNonHierarchicalLookUpObject(ownerNew.getRelationId(),
                                    UserSession.getCurrent().getOrganisation()).getDescLangFirst());
                } else {
                    ownerNew.setRelationIdDesc(MainetConstants.CommonConstants.NA);
                }
            }
        }

        model.setOwnershipPrefixNew(ownerTypeNew.getLookUpCode());
        dto.setProAssOwnerTypeName(ownerTypeNew.getDescLangFirst());

        
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
        	 Organisation organisation = UserSession.getCurrent().getOrganisation();
             
             StringBuilder ownerFullName = new StringBuilder();
             int ownerSize = 1;
             List<ProvisionalAssesmentOwnerDtlDto> ownerList = assMst.getProvisionalAssesmentOwnerDtlDtoList();
             for (ProvisionalAssesmentOwnerDtlDto owner : ownerList) {
                 if (owner.getAssoOType() != null && owner.getAssoOType().equals(MainetConstants.Property.PRIMARY_OWN)) {

                     if (StringUtils.isEmpty(ownerFullName.toString())) {
                         ownerFullName.append(owner.getAssoOwnerName());
                         ownerFullName.append(MainetConstants.WHITE_SPACE);
                         if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                             LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                     organisation);
                             ownerFullName.append(reltaionLookUp.getDescLangFirst());
                         } else {
                             ownerFullName.append("Contact person - ");
                         }
                         if (StringUtils.isNotBlank(owner.getAssoGuardianName())) {
                             ownerFullName.append(MainetConstants.WHITE_SPACE);
                             ownerFullName.append(owner.getAssoGuardianName());
                         }
                     } else {
                         ownerFullName.append(owner.getAssoOwnerName());
                         ownerFullName.append(MainetConstants.WHITE_SPACE);
                         if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                             LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                     organisation);
                             ownerFullName.append(reltaionLookUp.getDescLangFirst());
                         } else {
                             ownerFullName.append("Contact person - ");
                         }
                         ownerFullName.append(MainetConstants.WHITE_SPACE);
                         ownerFullName.append(owner.getAssoGuardianName());
                     }
                     if (ownerSize < ownerList.size()) {
                         ownerFullName.append("," + " ");
                     }
                     ownerSize = ownerSize + 1;
                 } else {
                     ownerFullName.append(owner.getAssoOwnerName());
                     ownerFullName.append(MainetConstants.WHITE_SPACE);
                     if (owner.getRelationId() != null && owner.getRelationId() > 0) {
                         LookUp reltaionLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(owner.getRelationId(),
                                 organisation);
                         ownerFullName.append(reltaionLookUp.getDescLangFirst());
                     } else {
                         ownerFullName.append("Contact person - ");
                     }
                     ownerFullName.append(MainetConstants.WHITE_SPACE);
                     ownerFullName.append(owner.getAssoGuardianName());
                     if (ownerSize < ownerList.size()) {
                         ownerFullName.append("," + " ");
                     }
                 }
             }
             
             assMst.setOwnerFullName(ownerFullName.toString());
        }
       
    }

    @RequestMapping(params = "editMutation", method = RequestMethod.POST)
    public ModelAndView editMutation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<LookUp> locList = getModel().getLocation();
        List<LocationMasEntity> location = iLocationMasService.findWZMappedLocationByOrgIdAndDeptId(orgId,
                getModel().getDeptId());
        if (location != null && !location.isEmpty()) {
            location.forEach(loc -> {
                LookUp lookUp = new LookUp();
                lookUp.setLookUpId(loc.getLocId());
                lookUp.setDescLangFirst(loc.getLocNameEng());
                lookUp.setDescLangSecond(loc.getLocNameReg());
                locList.add(lookUp);
            });
        }
        /*
         * this.getModel().setDropDownValues(); getCheckListAndcharges(getModel());
         */
        return new ModelAndView("MutationFormValidn", MainetConstants.FORM_NAME, getModel());
    }

    @RequestMapping(params = "saveMutationWithoutEdit", method = RequestMethod.POST)
    public ModelAndView saveMutationWithoutEdit(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        MutationModel model = getModel();
        final WorkflowTaskAction workFlowActionDto = model.getWorkflowActionDto();
        model.setApmApplicationId(workFlowActionDto.getApplicationId());
        // httpServletRequest.getSession().setAttribute("APP_NO", workFlowActionDto.getApplicationId());
        if (MainetConstants.FlagY.equals(model.getApprovalFlag())) {
            mutationService.saveMutationApproval(workFlowActionDto, model.getPropTransferDto(),
                    UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getEmployee(), model.getDeptId(),
                    model.getLoiCharges());
            return jsonResult(JsonViewObject.successResult(
                    ApplicationSession.getInstance().getMessage("property.mutation.approvalSave")));
        } else {
        	ServiceMaster service = serviceMaster.getServiceMaster(model.getServiceId(), model.getOrgId());
            model.validateBean(workFlowActionDto, CheckerActionValidator.class);
            if (model.hasValidationErrors()) {
                return customDefaultMyResult("MutationAuthView");
            }
            mutationService.saveUploadedFile(model.getPropTransferDto(), UserSession.getCurrent().getOrganisation().getOrgid(),
                    UserSession.getCurrent().getEmployee(), model.getDeptId(), UserSession.getCurrent().getLanguageId(),
                    model.getServiceId());
            if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
            	String noticeNo = mutationService.saveAuthorizationWithoutEdit(model.getWorkflowActionDto(),
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        UserSession.getCurrent().getEmployee(), model.getPropTransferDto(), false,
                        model.getDeptId(), model.isBeforeLastAuthorizer(), model.getClientIpAddress());
                model.getPublicNoticeDto().setNoticeNo(noticeNo);
            }else {
            	String noticeNo = mutationService.saveAuthorizationWithoutEdit(model.getWorkflowActionDto(),
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        UserSession.getCurrent().getEmployee(), model.getPropTransferDto(), model.isLastAuthorizer(),
                        model.getDeptId(), model.isBeforeLastAuthorizer(), model.getClientIpAddress());
                model.getPublicNoticeDto().setNoticeNo(noticeNo);
            }
            
			SMSAndEmailDTO dto = new SMSAndEmailDTO();
			List<PropertyTransferOwnerDto> ownerDtoList = model.getPropTransferDto().getPropTransferOwnerList();
			StringBuilder owner = new StringBuilder();
			for (PropertyTransferOwnerDto owners : ownerDtoList) {
				if (StringUtils.isBlank(owner.toString())) {
					owner.append(owners.geteMail());
				} else if (StringUtils.isNotBlank(owners.geteMail())) {
					owner.append(MainetConstants.operator.COMMA + owners.geteMail());
				}
			}
			dto.setEmail(owner.toString());			
			dto.setMobnumber(ownerDtoList.get(0).getMobileno());			
			dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			dto.setAppNo(model.getPropTransferDto().getApmApplicationId().toString());
			dto.setMsg(workFlowActionDto.getDecision());		
			ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
					"MutationAuthorization.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto,
					UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());
            
            if (StringUtils.equals(workFlowActionDto.getDecision(), MainetConstants.WorkFlow.Decision.REJECTED)) {
                return jsonResult(JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("property.reject")));
            } else if (StringUtils.equals(workFlowActionDto.getDecision(), MainetConstants.WorkFlow.Decision.SEND_BACK)) {
                return jsonResult(JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("property.sendback")));
            } else if (StringUtils.equals(workFlowActionDto.getDecision(),
                    MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)) {
                return jsonResult(JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("property.forward")));
            }else if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
            		&& model.isLastAuthorizer() && StringUtils.equals(service.getSmScrutinyChargeFlag(), MainetConstants.FlagY)) {
            	return jsonResult(JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("mutation.save.loi.gen")+ " : " + model.getLoiNo()));
            }
            if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && model.isLastAuthorizer()) {
            	return jsonResult(JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("property.mutation.lastauthSave") + model.getPropTransferDto().getProAssNo()));
            }else {
            	return jsonResult(JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("property.mutation.authSave")));
            }
            
        }
    }

    @RequestMapping(params = "saveMutationWithEdit", method = RequestMethod.POST)
    public ModelAndView saveMutationWithEdit(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        MutationModel model = this.getModel();
        final WorkflowTaskAction workFlowActionDto = model.getWorkflowActionDto();
        model.getPropTransferDto().setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        if (MainetConstants.FlagY.equals(model.getApprovalFlag())) {
            mutationService.saveMutationApproval(workFlowActionDto, model.getPropTransferDto(),
                    UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getEmployee(), model.getDeptId(),
                    model.getLoiCharges());
            return jsonResult(JsonViewObject.successResult(
                    ApplicationSession.getInstance().getMessage("property.mutation.approvalSave")));
        } else {
        	if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
            		&& getModel().isAuthLevel() && getModel().isHideUserAction()) {
        		workFlowActionDto.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        		workFlowActionDto.setComments("Ok");
        		mutationService.saveAuthorizationWithEdit(model.getWorkflowActionDto(), model.getPropTransferDto(),
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        UserSession.getCurrent().getEmployee(), model.getDeptId());
        		return jsonResult(JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("property.mutation.save")));
        	}
            model.validateBean(workFlowActionDto, CheckerActionValidator.class);
            if (model.hasValidationErrors()) {
                return customDefaultMyResult("MutationForm");
            }
            mutationService.saveUploadedFile(model.getPropTransferDto(), UserSession.getCurrent().getOrganisation().getOrgid(),
                    UserSession.getCurrent().getEmployee(), model.getDeptId(), UserSession.getCurrent().getLanguageId(),
                    model.getServiceId());
            String noticeNo = mutationService.saveAuthorizationWithEdit(model.getWorkflowActionDto(), model.getPropTransferDto(),
                    UserSession.getCurrent().getOrganisation().getOrgid(),
                    UserSession.getCurrent().getEmployee(), model.getDeptId());
            model.getPublicNoticeDto().setNoticeNo(noticeNo);
            return jsonResult(JsonViewObject.successResult(
                    ApplicationSession.getInstance().getMessage("property.mutation.authSave")));
        }

    }

    @RequestMapping(params = "printPublicNoticeReport")
    public ModelAndView printPublicNoticeReport(final HttpServletRequest request) {
        MutationModel model = this.getModel();
        ProvisionalAssesmentMstDto proAssMast = model.getProvisionalAssesmentMstDto();
        PropertyTransferMasterDto tranferDto = model.getPropTransferDto();
        Organisation org = UserSession.getCurrent().getOrganisation();
        String noOfDays = CommonMasterUtility.getDefaultValueByOrg(MainetConstants.Property.PNC,
                org).getOtherField();
        String deptName = departmentService.getDepartment(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "A").getDpDeptdesc();

        PublicNoticeDto publicNoticeDto = model.getPublicNoticeDto();
        if (deptName != null) {
            publicNoticeDto.setDeptName(deptName);
        }
        // Defect #91614
        String dsgName = UserSession.getCurrent().getEmployee().getDesignation().getDsgname();
        if (dsgName != null) {
            publicNoticeDto.setDsgName(dsgName);
        }
        publicNoticeDto.setPropNo(proAssMast.getAssNo());
        publicNoticeDto.setOldProNo(proAssMast.getAssOldpropno());
        publicNoticeDto.setNoOfDays(Long.valueOf(noOfDays));
        publicNoticeDto.setApplicateName(tranferDto.getPropTransferOwnerList().get(0).getOwnerName());

        List<String> ownerNames = new ArrayList<>();
        for (int i = 0; i < tranferDto.getPropTransferOwnerList().size(); i++) {
            String ownerName = tranferDto.getPropTransferOwnerList().get(i).getOwnerName();
            ownerNames.add(ownerName);
        }
        model.setDisplayOwnerNames(ownerNames);
        publicNoticeDto.setNewOwner(tranferDto.getPropTransferOwnerList().get(0).getOwnerName());
        publicNoticeDto.setPreviousOwner(proAssMast.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoOwnerName());
        // Defect #90419
        if (tranferDto.getApmApplicationId() != null) {
            publicNoticeDto.setApplicationNo(tranferDto.getApmApplicationId().toString());
        }
        SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        String notDate = sm.format(new Date());
        String appDate = sm.format(tranferDto.getCreatedDate());
        publicNoticeDto.setNoticeDate(notDate);
        publicNoticeDto.setAppDate(appDate);

        /*
         * if (proAssMast.getAssWard1() != null) {// pending publicNoticeDto.setWard1L("Zone");
         * publicNoticeDto.setWard1V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard1(), org)
         * .getDescLangFirst()); } if (proAssMast.getAssWard2() != null) { publicNoticeDto.setWard2L("Ward");
         * publicNoticeDto.setWard2V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard2(), org)
         * .getDescLangFirst()); }
         */
        final List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.Property.propPref.WZB, org);

        if (proAssMast.getAssWard1() != null) {
            publicNoticeDto.setWard1L(lookupList.get(0).getLookUpDesc());// need to change label from prefix for hierarchical
            publicNoticeDto.setWard1V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard1(), org)
                    .getDescLangFirst());
        }
        if (proAssMast.getAssWard2() != null) {
            publicNoticeDto.setWard2L(lookupList.get(1).getLookUpDesc());
            publicNoticeDto.setWard2V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard2(), org)
                    .getDescLangFirst());
        }
        if (proAssMast.getAssWard3() != null) {
            publicNoticeDto.setWard3L(lookupList.get(2).getLookUpDesc());
            publicNoticeDto.setWard3V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard3(), org)
                    .getDescLangFirst());
        }
        if (proAssMast.getAssWard4() != null) {
            publicNoticeDto.setWard4L(lookupList.get(3).getLookUpDesc());
            publicNoticeDto.setWard4V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard4(), org)
                    .getDescLangFirst());
        }
        if (proAssMast.getAssWard5() != null) {
            publicNoticeDto.setWard5L(lookupList.get(4).getLookUpDesc());
            publicNoticeDto.setWard5V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard5(), org)
                    .getDescLangFirst());
        }

        model.setPublicNoticeDto(publicNoticeDto);
        return new ModelAndView("PropertyPublicNotice", MainetConstants.FORM_NAME, getModel());
    }

    
    @Override
    @RequestMapping(params = "PrintReport")
    public ModelAndView printRTIStatusReport(HttpServletRequest request) {
    	MutationModel model = this.getModel();
        ProvisionalAssesmentMstDto assMast = model.getProvisionalAssesmentMstDto();
        PropertyTransferMasterDto tranferDto = model.getPropTransferDto();
        Organisation org = UserSession.getCurrent().getOrganisation();
        SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
    	if (Utility.isEnvPrefixAvailable(org, MainetConstants.Property.MUT)) {
        	ChallanReceiptPrintDTO printDto = new ChallanReceiptPrintDTO();
        	if (StringUtils.isNotEmpty(org.getONlsOrgnameMar())) {
        		printDto.setOrgNameMar(org.getONlsOrgnameMar());
            }
            if (StringUtils.isNotEmpty(org.getOrgAddressMar())) {
            	model.getOfflineDTO().setOrgAddressMar(org.getOrgAddressMar());
            }
            generateCertificateNo(tranferDto, org);
            model.getOfflineDTO().setCertificateNo(tranferDto.getCertificateNo());
        	printDto.setDate(sm.format(new Date()));
        	model.getOfflineDTO().setReferenceNo(tranferDto.getApmApplicationId().toString());
        	model.getOfflineDTO().setPropNoConnNoEstateNoL(tranferDto.getProAssNo());
        	if (StringUtils.isNotEmpty(assMast.getTppPlotNo())) {
        		model.getOfflineDTO().setFlatNo(assMast.getTppPlotNo());
        	}
        	StringBuilder transferOwnerFullName = new StringBuilder();
            tranferDto.getPropTransferOwnerList().forEach(owner -> {
                if (StringUtils.isEmpty(transferOwnerFullName.toString())) {
                    transferOwnerFullName.append(owner.getOwnerName() + MainetConstants.WHITE_SPACE);
                } else {
                    transferOwnerFullName.append(
                            MainetConstants.operator.AMPERSAND + MainetConstants.WHITE_SPACE + owner.getOwnerName());
                }
            });
        	model.getOfflineDTO().setTransferOwnerFullName(transferOwnerFullName.toString());
        	if (tranferDto.getCreatedDate() != null)
        		model.getOfflineDTO().setTransferInitiatedDate(sm.format(tranferDto.getCreatedDate()));
        	if (tranferDto.getRegNo() != null)
        		model.getOfflineDTO().setRegNo(tranferDto.getRegNo());
            if (tranferDto.getActualTransferDate() != null)
            	model.getOfflineDTO().setTransferDate(sm.format(tranferDto.getActualTransferDate()));
        	model.getOfflineDTO().setTransferAddress(assMast.getAssAddress());
        	printDto.setTotalReceivedAmount(model.getTotalLoiAmount().doubleValue());
        	model.setReceiptDTO(printDto);
        	model.getOfflineDTO().setSlaSmDuration(model.getServiceMast().getSmServiceDuration());
        }
    	return new ModelAndView("ChallanAtUlbLoiPrint", MainetConstants.FORM_NAME, getModel());
    }

	private void generateCertificateNo(PropertyTransferMasterDto tranferDto, Organisation org) {
		SequenceConfigMasterDTO configMasterDTO = null; 
		try {
			Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.PropMaster.PropShortType,
			        PrefixConstants.STATUS_ACTIVE_PREFIX);

			configMasterDTO = seqGenFunctionUtility.loadSequenceData(org.getOrgid(), deptId,
			        MainetConstants.Property.TB_AS_TRANSFER_MST, MainetConstants.Property.CERTIFICATE_NO);

			CommonSequenceConfigDto commonSequenceConfigDto = new CommonSequenceConfigDto();

			String certificateNo = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO,
			        commonSequenceConfigDto);
			mutationService.updateCertificateNo(certificateNo, UserSession.getCurrent().getEmployee().getEmpId(),
			        tranferDto.getApmApplicationId(), org.getOrgid());
			tranferDto.setCertificateNo(certificateNo);
		}catch (Exception e) {
            LOGGER.error("Sequence no. is not configured in Sequence Master");
        }
	}
    @RequestMapping(params = "printMutationCertificate")
    public ModelAndView printMutationCertificate(final HttpServletRequest request) {
        MutationModel model = this.getModel();
        ProvisionalAssesmentMstDto proAssMast = model.getProvisionalAssesmentMstDto();
        PropertyTransferMasterDto tranferDto = model.getPropTransferDto();
        Organisation org = UserSession.getCurrent().getOrganisation();
        String deptName = departmentService.getDepartment(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "A").getDpDeptdesc();

        PublicNoticeDto publicNoticeDto = model.getPublicNoticeDto();
        if (deptName != null) {
            publicNoticeDto.setDeptName(deptName);
        }
        publicNoticeDto.setPropNo(proAssMast.getAssNo());
        publicNoticeDto.setHouseNo(proAssMast.getTppPlotNo());
        publicNoticeDto.setOldProNo(proAssMast.getAssOldpropno());
        publicNoticeDto.setApplicateName(tranferDto.getPropTransferOwnerList().get(0).getOwnerName());
        if (Utility.isEnvPrefixAvailable(org, MainetConstants.Property.MUT)) {
            if (StringUtils.isNotEmpty(org.getONlsOrgnameMar())) {
                publicNoticeDto.setOrgNameMar(org.getONlsOrgnameMar());
            }
            if (StringUtils.isNotEmpty(org.getOrgAddressMar())) {
                publicNoticeDto.setOrgAddressMar(org.getOrgAddressMar());
            }
        }

        List<String> ownerNames = new ArrayList<>();
        StringBuilder newOwnerFullName = new StringBuilder();
        for (int i = 0; i < tranferDto.getPropTransferOwnerList().size(); i++) {
            String ownerName = tranferDto.getPropTransferOwnerList().get(i).getOwnerName();
            String guardianName = tranferDto.getPropTransferOwnerList().get(i).getGuardianName();
            ownerNames.add(ownerName);
            if(StringUtils.isNotBlank(guardianName)) {
            	newOwnerFullName.append(MainetConstants.WHITE_SPACE + ownerName + MainetConstants.WHITE_SPACE + guardianName
                    + MainetConstants.operator.AMPERSENT);
            }else {
            	newOwnerFullName.append(MainetConstants.WHITE_SPACE + ownerName + MainetConstants.operator.AMPERSENT);
            }
        }
        model.setDisplayOwnerNames(ownerNames);
        publicNoticeDto.setNewOwnerFullName(newOwnerFullName.deleteCharAt((newOwnerFullName.length() - 1)).toString());
        publicNoticeDto.setApplicationNo(Long.valueOf(model.getApmApplicationId()).toString());
        publicNoticeDto.setNewOwner(tranferDto.getPropTransferOwnerList().get(0).getOwnerName());
        publicNoticeDto.setPreviousOwner(proAssMast.getProvisionalAssesmentOwnerDtlDtoList().get(0).getAssoOwnerName());

        Double rv = 0.0;		// #96145
        if (CollectionUtils.isNotEmpty(proAssMast.getProvisionalAssesmentDetailDtoList())) {
            for (int j = 0; j < proAssMast.getProvisionalAssesmentDetailDtoList().size(); j++) {
                if (proAssMast.getProvisionalAssesmentDetailDtoList().get(j).getAssdRv() != null)
                    rv += proAssMast.getProvisionalAssesmentDetailDtoList().get(j).getAssdRv();
            }
        }
        publicNoticeDto.setRv(rv);
        StringBuilder oldOwnerName = new StringBuilder();
        if (CollectionUtils.isNotEmpty(proAssMast.getProvisionalAssesmentOwnerDtlDtoList())) {
            for (int j = 0; j < proAssMast.getProvisionalAssesmentOwnerDtlDtoList().size(); j++) {
            	if(StringUtils.isNotBlank(proAssMast.getProvisionalAssesmentOwnerDtlDtoList().get(j).getAssoGuardianName())) {
            		oldOwnerName.append(proAssMast.getProvisionalAssesmentOwnerDtlDtoList().get(j).getAssoOwnerName()
                        + MainetConstants.WHITE_SPACE
                        + proAssMast.getProvisionalAssesmentOwnerDtlDtoList().get(j).getAssoGuardianName()
                        + MainetConstants.operator.AMPERSENT);
				} else {
					oldOwnerName.append(proAssMast.getProvisionalAssesmentOwnerDtlDtoList().get(j).getAssoOwnerName()
							+ MainetConstants.WHITE_SPACE + MainetConstants.operator.AMPERSENT);
				}
            }
        }
        publicNoticeDto.setOldOwnerFullName(oldOwnerName.deleteCharAt((oldOwnerName.length() - 1)).toString());
        publicNoticeDto.setTransferType(tranferDto.getTransferTypeDesc());

        SimpleDateFormat sm = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        String notDate = sm.format(new Date());
        String appDate = sm.format(tranferDto.getCreatedDate());
        publicNoticeDto.setNoticeDate(notDate);
        publicNoticeDto.setAppDate(appDate);
        if (CollectionUtils.isNotEmpty(proAssMast.getProvisionalAssesmentDetailDtoList())
                && proAssMast.getProvisionalAssesmentDetailDtoList().get(0).getFirstAssesmentDate() != null) {
            publicNoticeDto
                    .setAssesmentDate(
                            sm.format(proAssMast.getProvisionalAssesmentDetailDtoList().get(0).getFirstAssesmentDate()));
        }

        final List<LookUp> lookupList = CommonMasterUtility.getListLookup(MainetConstants.Property.propPref.WZB, org);

        if (proAssMast.getAssWard1() != null) {
            publicNoticeDto.setWard1L(lookupList.get(0).getLookUpDesc());// need to change label from prefix for hierarchical
            publicNoticeDto.setWard1V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard1(), org)
                    .getDescLangFirst());
        }
        if (proAssMast.getAssWard2() != null) {
            publicNoticeDto.setWard2L(lookupList.get(1).getLookUpDesc());
            publicNoticeDto.setWard2V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard2(), org)
                    .getDescLangFirst());
        }
        if (proAssMast.getAssWard3() != null) {
            publicNoticeDto.setWard3L(lookupList.get(2).getLookUpDesc());
            publicNoticeDto.setWard3V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard3(), org)
                    .getDescLangFirst());
        }
        if (proAssMast.getAssWard4() != null) {
            publicNoticeDto.setWard4L(lookupList.get(3).getLookUpDesc());
            publicNoticeDto.setWard4V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard4(), org)
                    .getDescLangFirst());
        }
        if (proAssMast.getAssWard5() != null) {
            publicNoticeDto.setWard5L(lookupList.get(4).getLookUpDesc());
            publicNoticeDto.setWard5V(CommonMasterUtility.getHierarchicalLookUp(proAssMast.getAssWard5(), org)
                    .getDescLangFirst());
        }

        SequenceConfigMasterDTO configMasterDTO = null;  // #96145
        try {
            Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.PropMaster.PropShortType,
                    PrefixConstants.STATUS_ACTIVE_PREFIX);

            configMasterDTO = seqGenFunctionUtility.loadSequenceData(org.getOrgid(), deptId,
                    MainetConstants.Property.TB_AS_TRANSFER_MST, MainetConstants.Property.CERTIFICATE_NO);

            CommonSequenceConfigDto commonSequenceConfigDto = new CommonSequenceConfigDto();

            String certificateNo = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO,
                    commonSequenceConfigDto);
            publicNoticeDto.setCertificateNo(certificateNo);

            mutationService.updateCertificateNo(certificateNo, UserSession.getCurrent().getEmployee().getEmpId(),
                    tranferDto.getApmApplicationId(),
                    org.getOrgid());

            // US#102200
            String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "="
                    + ApplicationSession.getInstance().getMessage("property.birtName.mutationCertificate")
                    + "&__format=pdf&RP_ORGID=" + org.getOrgid() + "&RP_PropNo=" + proAssMast.getAssNo()
                    + "&RP_CertNo=" + certificateNo;
            Utility.pushDocumentToDms(URL, proAssMast.getAssNo(), MainetConstants.Property.PROP_DEPT_SHORT_CODE, fileUpload);

        } catch (Exception e) {
            LOGGER.error("Sequence no. is not configured in Sequence Master");
        }
        // sms and email
        try {
            Organisation organisation = UserSession.getCurrent().getOrganisation();
            final LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp("EC", "ENV", organisation);
            if (lookup != null && StringUtils.isNotBlank(lookup.getOtherField())
                    && StringUtils.equals(lookup.getOtherField(), MainetConstants.FlagY)) {
                List<File> filesForAttach = new ArrayList<File>();
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                    List<File> list = new ArrayList<>(entry.getValue());
                    filesForAttach.addAll(list);
                }
                if (!tranferDto.getPropTransferOwnerList().isEmpty()) {
                    for (int i = 0; i < tranferDto.getPropTransferOwnerList().size(); i++) {
                        if (!tranferDto.getPropTransferOwnerList().get(i).geteMail().isEmpty()) {
                            final SMSAndEmailDTO dto = new SMSAndEmailDTO();
                            dto.setEmail(tranferDto.getPropTransferOwnerList().get(i).geteMail());
                            dto.setMobnumber(tranferDto.getPropTransferOwnerList().get(i).getMobileno());
                            dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                            dto.setFilesForAttach(filesForAttach);
                            dto.setPropertyNo(tranferDto.getProAssNo());
                            dto.setAppNo((tranferDto.getApmApplicationId()).toString());
                            ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
                                    MainetConstants.Property.PROP_DEPT_SHORT_CODE, "MutationAuthorization.html",
                                    PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, organisation, 1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Prefix is not configured in Prefix Master");
        }
        // US#96773
        TbCfcApplicationMstEntity applicationMstEntity = applicationMasterService
                .getCFCApplicationByApplicationId(model.getApmApplicationId(), org.getOrgid());
        if (applicationMstEntity != null && applicationMstEntity.getRefNo() != null) {
            publicNoticeDto.setReferenceNo(applicationMstEntity.getRefNo());
        }
        model.setPublicNoticeDto(publicNoticeDto);

        if (Utility.isEnvPrefixAvailable(org, MainetConstants.Property.MUT)) {
        	return new ModelAndView("PropertyMutationCertificateSKDCL", MainetConstants.FORM_NAME, getModel());
        } else {
            return new ModelAndView("PropertyMutationCertificate", MainetConstants.FORM_NAME, getModel());
        }
    }

    @RequestMapping(params = "printMutationLoiGenerationCertificate")
    public ModelAndView printMutationLoiGenerationCertificate(final HttpServletRequest request) {
        MutationModel model = this.getModel();
        ProvisionalAssesmentMstDto proAssMast = model.getProvisionalAssesmentMstDto();
        PropertyTransferMasterDto tranferDto = model.getPropTransferDto();

        	return new ModelAndView("PropertyMutationLoiGenerationCertificate", MainetConstants.FORM_NAME, getModel());
    }

    
    @RequestMapping(params = "generateLoiCharges", method = RequestMethod.POST)
    public ModelAndView generateLoiCharges(HttpServletRequest httpServletRequest) {
        this.getModel().bind(httpServletRequest);
        MutationModel model = this.getModel();
        if (model.saveLoiData()) {
            model.setLoiChargeApplFlag(MainetConstants.FlagN);
            model.setShowFlag(MainetConstants.FlagY);
            List<TbLoiDet> loiDetailList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(model.getLoiDetail())) {
                Double totalAmount = new Double(0);

                for (TbLoiDet detail : model.getLoiDetail()) {

                    TbLoiDet loiDetail = new TbLoiDet();
                    BeanUtils.copyProperties(detail, loiDetail);
                    String taxDesc = ApplicationContextProvider.getApplicationContext()
                            .getBean(TbTaxMasService.class).findTaxDescByTaxIdAndOrgId(detail.getLoiChrgid(),
                                    UserSession.getCurrent().getOrganisation().getOrgid());
                    loiDetail.setLoiRemarks(taxDesc);
                    totalAmount = totalAmount + loiDetail.getLoiAmount().doubleValue();
                    loiDetailList.add(loiDetail);
                    model.setTotalLoiAmount(totalAmount);

                }

            }
            if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
            	model.setTotalAmntIncApplFee(model.getTotalLoiAmount());
                model.setTotalLoiAmount(model.getTotalLoiAmount() - (model.getApplicationFee()));
                Date date = new Date();
        		this.getModel().setDate(Utility.dateToString(date, MainetConstants.DATE_FORMAT));
            }
            if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
            	Date date = new Date();
        		this.getModel().setDate(Utility.dateToString(date, MainetConstants.DATE_FORMAT));
            }
            model.setLoiDetail(loiDetailList);

        } else {
            model.addValidationError(getApplicationSession()
                    .getMessage("Problrm occured while fetching LOI Charges from BRMS Sheet"));
        }
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
            return new ModelAndView("MutationAuthLoiGeneration", MainetConstants.FORM_NAME, getModel());

        }else {
            return new ModelAndView("MutationAuthView", MainetConstants.FORM_NAME, getModel());

        }
    }
}
