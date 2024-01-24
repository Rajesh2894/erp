package com.abm.mainet.common.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicationDetail;
import com.abm.mainet.common.dto.ApplicationStatusDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationAddressJpaRepository;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;

import io.swagger.annotations.Api;

/**
 * @author Arun.Chavda
 *
 */
@Service
@Repository
@WebService(endpointInterface = "com.abm.mainet.common.service.ApplicationService")
@Api("/applicationservice")
@Path("/applicationservice")
public class ApplicationServiceImpl implements ApplicationService {

	private static final Logger LOGGER = Logger.getLogger(ApplicationServiceImpl.class);

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

	@Resource
	private TbCfcApplicationAddressJpaRepository tbCfcApplicationAddressJpaRepository;

	@Autowired
	private IWorkflowActionService workflowActionService;

	@Autowired
	private IWorkflowRequestService workflowRequestService;

	@Autowired
	private AuditService auditService;

	@Autowired
	private DepartmentService departmentService;
	@Resource
	private TbCfcApplicationMstService tbCfcApplicationMstService;
	
	@Autowired
	private IOrganisationService orgService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.common.service.ApplicationService#createApplication()
	 */
	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public Long createApplication(final RequestDTO requestDto) {
		Long appNo = null;
		final Date sysDate = UtilityService.getSQLDate(new Date());
		final String date = sysDate.toString();
		final String[] dateParts = date.split("-");
		final String year = dateParts[0];
		final String month = dateParts[1];
		final String day = dateParts[2];
		final String subString = year.substring(2);
		final String YYMMDDDate = subString.concat(month).concat(day);
		Long number = 0l;
		
		// custom sequence generate using sequence master if condition satisfy
		// #134523 - custom application no generation based on sequence config master
		Long deptId =null;
		Organisation org = orgService.getOrganisationById(requestDto.getOrgId());
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SFAC)) {
			deptId = requestDto.getDeptId();
		}else {
			deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
				PrefixConstants.STATUS_ACTIVE_PREFIX);
		}
		SequenceConfigMasterDTO configMasterDTO = null;
		configMasterDTO = seqGenFunctionUtility.loadSequenceData(requestDto.getOrgId(), deptId,
				MainetConstants.CommonMasterUi.TB_CFC_APP_MST,
				MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID);
		if (configMasterDTO.getSeqConfigId() != null) {
			CommonSequenceConfigDto commonSequenceConfigDto = new CommonSequenceConfigDto();
			// #111859 By Arun
			/*
			 * if (StringUtils.isNotEmpty(requestDto.getCustomField())) {
			 * commonSequenceConfigDto.setCustomField(requestDto.getCustomField()); }
			 */
			String sequenceNo = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonSequenceConfigDto);
			if (sequenceNo == null) {
				throw new FrameworkException("sequence not generated for " + requestDto.getDepartmentName());
			} else {
				appNo = Long.valueOf(sequenceNo);
				if (appNo <= 0) {
					throw new FrameworkException("sequence not generated for " + requestDto.getDepartmentName());
				}
			}
		} else {
			String orgId = requestDto.getOrgId().toString();
			number = seqGenFunctionUtility.generateSequenceNo(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER,
					MainetConstants.CommonMasterUi.TB_CFC_APP_MST,
					MainetConstants.SCRUTINY_COMMON_PARAM.APM_APPLICATION_ID, requestDto.getOrgId(),
					MainetConstants.CommonMasterUi.D, null);
			final String paddingAppNo = String.format(MainetConstants.LOI.LOI_NO_FORMAT,
					Long.parseLong(number.toString()));
			String appNumber = orgId.concat(YYMMDDDate).concat(paddingAppNo);
			appNo = Long.parseLong(appNumber);
		}

		final TbCfcApplicationMstEntity applicationMaster = new TbCfcApplicationMstEntity();
		final ServiceMaster serviceMaster = new ServiceMaster();
		// comma seprated name Defect #111802
		/*
		 * List<Object[]> cfcMaster = tbCfcApplicationMstJpaRepository
		 * .getApplicantInfo(requestDto.getApplicationId(),requestDto.getOrgId());
		 */

		serviceMaster.setSmServiceId(requestDto.getServiceId());

		applicationMaster.setApmApplicationDate(sysDate);
		applicationMaster.setTbServicesMst(serviceMaster);
		applicationMaster.setApmTitle(requestDto.getTitleId());
		applicationMaster.setApmSex(requestDto.getGender());
		applicationMaster.setApmFname(requestDto.getfName());
		// Defect #111802
		/*
		 * if(cfcMaster !=null && cfcMaster.size()>0 && cfcMaster.get(0) !=null) {
		 * Object[] mstEntity=cfcMaster.get(0); if(mstEntity[0] !=null)
		 * applicationMaster.setApmFname(mstEntity[0].toString()); }
		 */
		applicationMaster.setApmMname(requestDto.getmName());
		applicationMaster.setApmLname(requestDto.getlName());
		final Organisation organisation = new Organisation();
		organisation.setOrgid(requestDto.getOrgId());
		applicationMaster.setTbOrganisation(organisation);
		applicationMaster.setUserId(requestDto.getUserId());
		applicationMaster.setLmoddate(sysDate);
		applicationMaster.setLangId(requestDto.getLangId());
		applicationMaster.setApmApplicationId(appNo);
		applicationMaster.setCcdApmType(requestDto.getApplicationType());
		applicationMaster.setApmBplNo(requestDto.getBplNo());
		applicationMaster.setApmUID(requestDto.getUid());
		applicationMaster.setLgIpMac(Utility.getMacAddress());
		applicationMaster.setApmBplYearIssue(requestDto.getYearOfIssue());
		applicationMaster.setApmBplIssueAuthority(requestDto.getBplIssuingAuthority());
		applicationMaster.setApmOrgnName(requestDto.getApmOrgnName());
		applicationMaster.setApmPayStatFlag(requestDto.getPayStatus());
		if (requestDto.getReferenceId() != null) {
			applicationMaster.setRefNo(requestDto.getReferenceId());
		}
		if(requestDto.getVendorCode() != null || StringUtils.isNotEmpty(requestDto.getVendorCode())) {
			applicationMaster.setCtzCitizenid(requestDto.getVendorCode());
		}
		if (StringUtils.isNotEmpty(requestDto.getAadhaarNo())) {
			applicationMaster.setApmResid(requestDto.getAadhaarNo());
		}
		applicationMaster.setApmMode(requestDto.getApmMode());
		try {
			tbCfcApplicationMstJpaRepository.save(applicationMaster);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		final CFCApplicationAddressEntity applicationAddress = new CFCApplicationAddressEntity();
		applicationAddress.setApmApplicationId(appNo);
		applicationAddress.setApaBlockno(requestDto.getBlockNo());
		applicationAddress.setApaFloor(requestDto.getFloor());
		applicationAddress.setApaWing(requestDto.getWing());
		applicationAddress.setApaBldgnm(requestDto.getBldgName());
		applicationAddress.setApaHsgCmplxnm(requestDto.getHouseComplexName());
		applicationAddress.setApaRoadnm(requestDto.getRoadName());
		applicationAddress.setApaAreanm(requestDto.getAreaName());
		applicationAddress.setApaPincode(requestDto.getPincodeNo());
		applicationAddress.setApaPhone1(requestDto.getPhone1());
		applicationAddress.setApaPhone2(requestDto.getPhone2());
		applicationAddress.setApaEmail(requestDto.getEmail());
		applicationAddress.setApaMobilno(requestDto.getMobileNo());
		applicationAddress.setApaWardNo(requestDto.getWardNo());
		applicationAddress.setApaBlockName(requestDto.getBlockName());
		applicationAddress.setApaZoneNo(requestDto.getZoneNo());
		applicationAddress.setApaWardNo(requestDto.getWardNo());
		applicationAddress.setApaCityName(requestDto.getCityName());
		applicationAddress.setApaFlatBuildingNo(requestDto.getFlatBuildingNo());

		applicationAddress.setOrgId(organisation);
		applicationAddress.setUserId(requestDto.getUserId());
		applicationAddress.setLangId(requestDto.getLangId());
		applicationAddress.setLmodDate(sysDate);
		applicationAddress.setLgIpMac(Utility.getMacAddress());

		tbCfcApplicationAddressJpaRepository.save(applicationAddress);

		return appNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.common.service.ApplicationService#updateApplication()
	 */
	@Override
	@WebMethod(exclude = true)
	public void updateApplication(final TbCfcApplicationMstEntity applicationMaster) {
		tbCfcApplicationMstJpaRepository.save(applicationMaster);

	}

	@Override
	@Transactional(readOnly = true)
	public ApplicationStatusDTO getApplicationStatus(long applicationId, short langId) {
		ApplicationStatusDTO applicationStatusDTO = new ApplicationStatusDTO();
		applicationStatusDTO.setApplicationId(applicationId);
		Optional<TbCfcApplicationMstEntity> reuslt = tbCfcApplicationMstJpaRepository
				.findByApmApplicationId(applicationId);
		if (!reuslt.isPresent()) {
			applicationStatusDTO.setErrors(MainetConstants.InputError.Application_Status_Not_Found);
			return applicationStatusDTO;
		}

		TbCfcApplicationMstEntity application = reuslt.get();
		applicationStatusDTO.setServiceName(application.getTbServicesMst().getSmServiceName());
		applicationStatusDTO.setOrganizationName(application.getTbOrganisation().getONlsOrgname());
		applicationStatusDTO.setServiceNameReg(application.getTbServicesMst().getSmServiceNameMar());
		applicationStatusDTO.setOrganizationNameReg(application.getTbOrganisation().getONlsOrgnameMar());
		applicationStatusDTO.setDate(application.getApmApplicationDate());
		applicationStatusDTO.setFormattedDate(
				Utility.dateToString(application.getApmApplicationDate(), MainetConstants.DATE_HOUR_FORMAT));

		WorkflowRequest workflowRequest = workflowRequestService.findByApplicationId(applicationId);
		if (workflowRequest != null) {

			applicationStatusDTO.setEmpName(application.getApmFname().concat(" ")
					.concat(StringUtils.isNotBlank(application.getApmLname()) ? application.getApmLname() : "").trim());

			applicationStatusDTO.setDeptName(application.getTbServicesMst().getTbDepartment().getDpDeptdesc());
			// D#131808
			if (langId == 2) {
				applicationStatusDTO.setStatus(ApplicationSession.getInstance()
						.getMessage("workflow.status.hindi." + workflowRequest.getStatus()));
			} else {
				applicationStatusDTO.setStatus(workflowRequest.getStatus());
			}
			applicationStatusDTO.setLastDecision(workflowRequest.getLastDecision());
			List<WorkflowTaskActionWithDocs> actionsTaken = workflowActionService
					.getWorkflowActionLogByApplicationId(String.valueOf(applicationId), langId);
			List<WorkflowTaskActionWithDocs> actionsPending = workflowActionService
					.getWorkflowPendingActionByUuid(String.valueOf(applicationId), langId);
			applicationStatusDTO.getActions().addAll(actionsTaken);
			applicationStatusDTO.getActions().addAll(actionsPending);

		} else {
			if (application.getApmApplClosedFlag() != null
					&& application.getApmApplClosedFlag().equals(MainetConstants.Common_Constant.YES))
				applicationStatusDTO.setStatus(MainetConstants.WorkFlow.Status.CLOSED);
			else
				applicationStatusDTO.setStatus(MainetConstants.WorkFlow.Status.PENDING);
		}

		return applicationStatusDTO;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public void updateApplicationForSupplementaryBill(RequestDTO requestDto) {
		TbCfcApplicationMstEntity applicationMaster = new TbCfcApplicationMstEntity();
		applicationMaster = tbCfcApplicationMstJpaRepository.findOne(requestDto.getApplicationId());

		applicationMaster.setApmFname(requestDto.getfName());
		applicationMaster.setLmoddate(new Date());
		applicationMaster.setUpdatedBy(requestDto.getUserId());
		applicationMaster.setUpdatedDate(new Date());
		applicationMaster.setLgIpMacUpd(Utility.getMacAddress());
		try {
			applicationMaster = tbCfcApplicationMstJpaRepository.save(applicationMaster);
			// TbCfcApplicationMstEntityHistory applicationMasterHistory = new
			// TbCfcApplicationMstEntityHistory();
			// applicationMasterHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
			// auditService.createHistory(applicationMaster, applicationMasterHistory);
		} catch (final Exception e) {
			LOGGER.error("Exception Occured While Updating Application Master", e);
			throw new FrameworkException(
					"Exception Occured While Updating Application Master For " + requestDto.getApplicationId());
		}
		CFCApplicationAddressEntity applicationAddress = tbCfcApplicationAddressJpaRepository
				.findOne(requestDto.getApplicationId());

		applicationAddress.setApaHsgCmplxnm(requestDto.getHouseComplexName());
		applicationAddress.setApaRoadnm(requestDto.getRoadName());
		applicationAddress.setApaAreanm(requestDto.getAreaName());
		applicationAddress.setApaPincode(requestDto.getPincodeNo());
		applicationAddress.setApaEmail(requestDto.getEmail());
		applicationAddress.setApaMobilno(requestDto.getMobileNo());
		applicationAddress.setApaCityName(requestDto.getCityName());
		applicationAddress.setLmodDate(new Date());
		applicationAddress.setUpdatedBy(requestDto.getUserId());
		applicationAddress.setUpdatedDate(new Date());
		applicationAddress.setLgIpMacUpd(Utility.getMacAddress());

		try {
			tbCfcApplicationAddressJpaRepository.save(applicationAddress);
			// CFCApplicationAddressEntityHistory applicationAddressHistory = new
			// CFCApplicationAddressEntityHistory();
			// applicationAddressHistory.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
			// auditService.createHistory(applicationAddress, applicationAddressHistory);
		} catch (final Exception e) {
			LOGGER.error("Exception Occured While Updating Application Address", e);
			throw new FrameworkException(
					"Exception Occured While Updating Application Address For " + requestDto.getApplicationId());
		}

	}

	@Override
	public String getPaymentStatusFlagByApplNo(Long applNo, Long orgid) {

		return tbCfcApplicationMstJpaRepository.getApplicantPymentStatusByApplNo(applNo, orgid);
	}

	@Override
	public String getLicenseNoByAppId(Long applicationId, Long orgId) {
		return tbCfcApplicationMstJpaRepository.getLicenseNoByAppIdAndOrgId(applicationId, orgId);

	}

	@Override
	public Long getOrgId(Long applicationId) {

		return tbCfcApplicationMstService.findById(applicationId).getOrgid();
	}
	
	
	@Override
	public String getApplicantName(Long applicationId, long orgId) {
		List<Object[]> names = tbCfcApplicationMstJpaRepository.getApplicantInfo(applicationId, orgId);
		if(names.isEmpty()) {
			return MainetConstants.NO_RECORD_FOUND;
		}
		StringBuilder applicantName = new StringBuilder();
		names.forEach(name->{
			applicantName.append(name[0]);
			if(StringUtils.isNotEmpty((String) name[1])) {
				applicantName.append(MainetConstants.WHITE_SPACE);
				applicantName.append(name[1]);
			}
			if(StringUtils.isNotEmpty((String) name[2])) {
				applicantName.append(MainetConstants.WHITE_SPACE);
				applicantName.append(name[2]);
			}
		});
		ApplicationDetail app=new ApplicationDetail();
		app.setApmApplicationNameEng(applicantName.toString());
		JSONObject jsonObject = new JSONObject(app);
        return jsonObject.toString();
	}
}
