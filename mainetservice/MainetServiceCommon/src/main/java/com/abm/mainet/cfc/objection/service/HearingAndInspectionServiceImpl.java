package com.abm.mainet.cfc.objection.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.cfc.objection.domain.HearingMasterEntity;
import com.abm.mainet.cfc.objection.domain.InspectionMasterEntity;
import com.abm.mainet.cfc.objection.dto.HearingInspectionDto;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.repository.HearingMasRepository;
import com.abm.mainet.cfc.objection.repository.InspectionMasRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;

@Service
public class HearingAndInspectionServiceImpl implements HearingAndInspectionService {

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private HearingMasRepository hearingMasRepository;

    @Autowired
    private InspectionMasRepository inspectionMasRepository;

    @Autowired
    private IWorkflowActionService iWorkflowActionService;

    @Autowired
    private IFileUploadService fileUploadService;
    
    @Autowired
	private DepartmentService departmentService;

	@Autowired
	private MessageSource messageSource;

    @Override
    public void saveInspectionMaster(HearingInspectionDto hearingInspectionDto, Long orgId, Long empId, String macAddress) {
        InspectionMasterEntity insMastEnt = new InspectionMasterEntity();
        BeanUtils.copyProperties(hearingInspectionDto, insMastEnt);
        if (hearingInspectionDto.getInsHaerId() == null) {
            String inspectionNo = getInspectionNo(orgId);
            hearingInspectionDto.setInsHearNo(inspectionNo);
            insMastEnt.setInsHearNo(inspectionNo);
            insMastEnt.setCreatedBy(empId);
            insMastEnt.setLgIpMac(macAddress);
            insMastEnt.setCreatedDate(new Date());
            insMastEnt.setOrgId(orgId);
        } else {
            insMastEnt.setUpdatedBy(empId);
            insMastEnt.setLgIpMacUpd(macAddress);
            insMastEnt.setUpdatedDate(new Date());
        }
        inspectionMasRepository.save(insMastEnt);
    }

    @Override
    public void saveHearingMaster(HearingInspectionDto hearingInspectionDto, Long orgId, Long empId, String macAddress,
            ObjectionDetailsDto objDto) {
        HearingMasterEntity heaMastEnt = new HearingMasterEntity();
        BeanUtils.copyProperties(hearingInspectionDto, heaMastEnt);
        if (hearingInspectionDto.getInsHaerId() == null) {
            String hearingNo = getHearingNo(orgId);
            hearingInspectionDto.setInsHearNo(hearingNo);
            heaMastEnt.setInsHearNo(hearingNo);
            heaMastEnt.setCreatedBy(empId);
            heaMastEnt.setLgIpMac(macAddress);
            heaMastEnt.setOrgId(orgId);
            heaMastEnt.setCreatedDate(new Date());
        } else {
            heaMastEnt.setUpdatedBy(empId);
            heaMastEnt.setLgIpMacUpd(macAddress);
            heaMastEnt.setUpdatedDate(new Date());
        }
        hearingMasRepository.save(heaMastEnt);
        if ((hearingInspectionDto.getHearingDocs() != null) &&
                !hearingInspectionDto.getHearingDocs().isEmpty()) {

            RequestDTO reqDto = new RequestDTO();
            setRequestApplicantDetails(reqDto, objDto,
                    objDto.getOrgId(), objDto.getUserId());
            reqDto.setApplicationId(Long.valueOf(hearingInspectionDto.getInsHearNo()));
            reqDto.setReferenceId(hearingInspectionDto.getInsHearNo());
            fileUploadService.doFileUpload(hearingInspectionDto.getHearingDocs(), reqDto);
        }

    }

    @Override
    public HearingInspectionDto getHearingDetailByObjId(Long orgId, Long objId) {
        HearingMasterEntity hearMastEnt = hearingMasRepository.getHearingDetailByObjId(orgId, objId);
        HearingInspectionDto hearDto = new HearingInspectionDto();
        BeanUtils.copyProperties(hearMastEnt, hearDto);
        return hearDto;
    }

    @Override
    public HearingInspectionDto getInspectionByObjId(Long orgId, Long objId) {
        InspectionMasterEntity inspectionMasterEntity = inspectionMasRepository.getInspectionByObjId(orgId, objId);
        HearingInspectionDto hearDto = new HearingInspectionDto();
        BeanUtils.copyProperties(inspectionMasterEntity, hearDto);
        return hearDto;
    }

    @Override
    public HearingInspectionDto getInspectionByObjNo(Long orgId, String objNo) {
        InspectionMasterEntity inspectionMasterEntity = inspectionMasRepository.getInspectionByObjNo(orgId, objNo);
        HearingInspectionDto inspDto = null;
        if (inspectionMasterEntity != null) {
            inspDto = new HearingInspectionDto();
            BeanUtils.copyProperties(inspectionMasterEntity, inspDto);
        }
        return inspDto;
    }

    private String getInspectionNo(Long orgId) {
        final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.CommonConstants.COM,
                MainetConstants.Objection.TB_HEARING_MAS,
                MainetConstants.Objection.INS_ID, orgId,
                MainetConstants.FlagF, null);
        return sequence.toString();
    }

    private String getHearingNo(Long orgId) {
        final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.CommonConstants.COM,
                MainetConstants.Objection.TB_HEARING_MAS,
                MainetConstants.Objection.HR_ID, orgId,
                MainetConstants.FlagC, null);
        return sequence.toString();
    }

    @Override
    public void callWorkflow(Employee emp, ObjectionDetailsDto objDto, String decision) {
        WorkflowTaskAction workflowAction = new WorkflowTaskAction();
        workflowAction.setApplicationId(objDto.getApmApplicationId());
        workflowAction.setDecision(decision);
        workflowAction.setTaskId(objDto.getTaskId());
        workflowAction.setReferenceId(objDto.getObjectionNumber());
        iWorkflowActionService.updateWorkFlow(workflowAction, emp, objDto.getOrgId(),
                objDto.getServiceId());
    }

    private void setRequestApplicantDetails(final RequestDTO reqDto, ObjectionDetailsDto objDto, Long orgId, Long empId) {
        reqDto.setApplicationId(objDto.getApmApplicationId());
        reqDto.setfName(reqDto.getfName());
        reqDto.setDeptId(objDto.getObjectionDeptId());
        reqDto.setOrgId(orgId);
        reqDto.setServiceId(objDto.getServiceId());
        reqDto.setLangId(Long.valueOf(objDto.getLangId()));
        reqDto.setUserId(empId);
        reqDto.setReferenceId(objDto.getObjectionNumber());
    }
    
    @Override
    public void callWorkflow(Employee emp, ObjectionDetailsDto objDto, String decision,String decisionInFavorOf) {
        WorkflowTaskAction workflowAction = new WorkflowTaskAction();
        workflowAction.setApplicationId(objDto.getApmApplicationId());
        workflowAction.setDecision(decision);
        workflowAction.setTaskId(objDto.getTaskId());
        workflowAction.setReferenceId(objDto.getObjectionNumber());
        workflowAction.setDecisionFavorFlag(decisionInFavorOf);
        iWorkflowActionService.updateWorkFlow(workflowAction, emp, objDto.getOrgId(),
                objDto.getServiceId());
    }
        
	@SuppressWarnings("unchecked")
	@Override
	@WebMethod(exclude = true)
	public List<String> getSelectedOwnerInfoByApplId(Long apmApplicationId, Long orgid) {
		List<String> ownerInfoList = new ArrayList<>();
		Class<?> clazz = null;
		String serviceClassName = null;
		Object dynamicServiceInstance = null;
		try {
			serviceClassName = messageSource.getMessage(
					ApplicationSession.getInstance().getMessage("objection.lbl")
							+ MainetConstants.DEPT_SHORT_NAME.PROPERTY,
					new Object[] {}, StringUtils.EMPTY, Locale.ENGLISH);
			if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
				clazz = ClassUtils.forName(serviceClassName,
						ApplicationContextProvider.getApplicationContext().getClassLoader());
				dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
						.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
				final Method method = ReflectionUtils.findMethod(clazz, "getOwnerInfoByApplId",
						new Class[] { Long.class, Long.class });
				ownerInfoList = (List<String>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
						new Object[] { apmApplicationId, orgid });
			}
		} catch (Exception e) {
			throw new FrameworkException(
					"Exception in  finding owner details with reference Number  : " + apmApplicationId, e);
		}
		return ownerInfoList;
	}

}
