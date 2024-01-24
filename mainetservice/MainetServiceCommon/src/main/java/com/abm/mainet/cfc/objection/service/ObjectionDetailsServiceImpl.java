package com.abm.mainet.cfc.objection.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.objection.dao.IObjectionServiceDAO;
import com.abm.mainet.cfc.objection.domain.TbObjectionEntity;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.repository.ObjectionMastRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.IDepartmentDAO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.LocationOperationWZMapping;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.repository.LocationMasJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;

@Service
@WebService(endpointInterface = "com.abm.mainet.cfc.objection.service.IObjectionDetailsService")
@Api(value = "/objection")
@Path("/objection")
public class ObjectionDetailsServiceImpl implements IObjectionDetailsService {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(ObjectionDetailsServiceImpl.class);

	@Autowired
	IObjectionServiceDAO iObjectionServiceDAO;

	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Resource
	private DepartmentService departmentService;

	@Autowired
	private MessageSource messageSource;

	@Resource
	private ApplicationService applicationService;

	@Resource
	private IDepartmentDAO iDepartmentDAO;

	@Resource
	private LocationMasJpaRepository locationMasJpaRepository;

	@Autowired
	private ObjectionMastRepository objectionMastRepository;

	@Autowired
	private DepartmentService DepartmentService;

	@Resource
	private TbServicesMstService tbServicesMstService;

	@Autowired
	private NoticeMasterService noticeMasterService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Resource
	private IFileUploadService fileUploadService;

	@Autowired
	private IWorkflowActionService iWorkflowActionService;

	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;

	@Autowired
	private IOrganisationService iOrganisationService;

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private IFinancialYearService iFinancialYear;

	@Autowired
	private CommonService commonService;

	@Autowired
	TbTaxMasService tbTaxMasService;

	@Override
	@WebMethod(exclude = true)
	public List<ObjectionDetailsDto> getObjectionList(ObjectionDetailsDto objectionDetailsDto) {

		List<TbObjectionEntity> tbObjectionEntityList = iObjectionServiceDAO.getObjectionDetails(objectionDetailsDto);
		List<ObjectionDetailsDto> objectDetailsList = new ArrayList<>();
		ObjectionDetailsDto dto = null;
		Integer iSerialNumber = 1;
		for (int i = 0; i < tbObjectionEntityList.size(); i++) {
			dto = new ObjectionDetailsDto();
			dto.setObjectionSerialNumber(iSerialNumber);
			dto.setServiceId(tbObjectionEntityList.get(i).getServiceId());
			dto.setObjectionReferenceNumber(tbObjectionEntityList.get(i).getObjectionReferenceNumber());
			dto.setObjectionAddReferenceNumber(tbObjectionEntityList.get(i).getObjectionAddReferenceNumber());
			dto.setObjectionNumber(tbObjectionEntityList.get(i).getObjectionNumber());
			dto.setObjectionStatus(tbObjectionEntityList.get(i).getObjectionStatus());
			dto.setObjectionDeptId(tbObjectionEntityList.get(i).getObjectionDeptId());
			dto.setObjectionDetails(tbObjectionEntityList.get(i).getObjectionDetails());
			objectDetailsList.add(objectionDetailsDto);
			iSerialNumber++;
		}
		return objectDetailsList;
	}

	@Override
	@POST
	@Path("/getDepartmentList")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<LookUp> getDepartmentList(@RequestBody ObjectionDetailsDto objDto) {
		Set<LookUp> departments = new HashSet<>();
		List<Department> depts = DepartmentService.getDepartments(MainetConstants.STATUS.ACTIVE);

		depts.forEach(dept -> {
			LookUp detData = new LookUp();
			detData.setDescLangFirst(dept.getDpDeptdesc());
			detData.setDescLangSecond(dept.getDpNameMar());
			detData.setLookUpId(dept.getDpDeptid());
			detData.setLookUpCode(dept.getDpDeptcode());
			departments.add(detData);
		});
		return departments;

	}

	@Override
	@POST
	@Path("/getLocationList")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<LookUp> getLocationByDepartmentInLookup(@RequestBody ObjectionDetailsDto objDto) {
		Set<LookUp> locations = new HashSet<>();
		List<LocationMasEntity> locationList = locationMasJpaRepository
				.findAllLocationWithWZMappingByDeptId(objDto.getOrgId(), objDto.getObjectionDeptId());
		locationList.forEach(location -> {
			LookUp detData = new LookUp();
			detData.setDescLangFirst(location.getLocNameEng());
			detData.setDescLangSecond(location.getLocNameReg());
			detData.setLookUpId(location.getLocId());
			locations.add(detData);
		});
		return locations;

	}

	@Override
	@POST
	@Path("/getservicesByDeptId")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<LookUp> getservicesByDeptIdWithLookup(@RequestBody ObjectionDetailsDto objDto) {
		Set<LookUp> serviceLookup = new HashSet<>();
		List<TbServicesMst> serviceList = tbServicesMstService.findALlActiveServiceByDeptId(objDto.getObjectionDeptId(),
				objDto.getOrgId());
		serviceList.forEach(service -> {
			LookUp detData = new LookUp();
			detData.setDescLangFirst(service.getSmServiceName());
			detData.setDescLangSecond(service.getSmServiceNameMar());
			detData.setLookUpId(service.getSmServiceId());
			detData.setLookUpCode(service.getSmShortdesc());
			serviceLookup.add(detData);
		});
		return serviceLookup;
	}

	public ObjectionDetailsDto saveObjectionAndCallWorkFlow(ObjectionDetailsDto objectionDetailsDto) {
		saveObjection(objectionDetailsDto);
		sendSmsAndEmail(objectionDetailsDto);
		return objectionDetailsDto;
	}

	@Transactional
	public void saveObjection(ObjectionDetailsDto objectionDetailsDto) {
		if (objectionDetailsDto.getLocId() != null) {
			setDeptWardZone(objectionDetailsDto);
		}
		saveAndUpateObjectionMaster(objectionDetailsDto, objectionDetailsDto.getUserId(),
				objectionDetailsDto.getIpAddress());
		RequestDTO reqDto = new RequestDTO();
		setRequestApplicantDetails(reqDto, objectionDetailsDto, objectionDetailsDto.getOrgId(),
				objectionDetailsDto.getUserId());
		fileUploadService.doFileUpload(objectionDetailsDto.getDocs(), reqDto);
		Organisation org = new Organisation();
		ServiceMaster service = serviceMasterService.getServiceMaster(objectionDetailsDto.getServiceId(),
				objectionDetailsDto.getOrgId());
		String objOn = CommonMasterUtility.getNonHierarchicalLookUpObject(objectionDetailsDto.getObjectionOn(), org)
				.getLookUpCode();
		if (MainetConstants.Objection.ObjectionOn.BILL.equals(objOn)
				|| (service != null && service.getSmShortdesc().equalsIgnoreCase(MainetConstants.Property.MOS)
						&& MainetConstants.Objection.ObjectionOn.MUTATION.equals(objOn))) {
			initiateWorkflow(objectionDetailsDto);
		} else {
			signalWorkFlow(objectionDetailsDto);
		}

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
		iWorkflowActionService.signalWorkFlow(workflowAction, emp, objectionDto.getOrgId(), objectionDto.getServiceId(),
				MainetConstants.WorkFlow.Signal.HEARING);
	}

	private void initiateWorkflow(ObjectionDetailsDto objectionDto) {
		ApplicationMetadata applicationData = new ApplicationMetadata();
		final ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
		ServiceMaster service = serviceMasterService.getServiceMaster(objectionDto.getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		
		//Set in case if approval task is needed after hearing process
		if (service != null && service.getSmShortdesc().equals(MainetConstants.Property.MOS)
				|| service.getSmShortdesc().equals("OAB")) {
			applicationData.setIsApprReqInObjection(MainetConstants.FlagY);
		}
		applicantDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		// applicationData.setApplicationId(provisionalAssesmentMstDto.getApmApplicationId());
		applicationData.setReferenceId(objectionDto.getObjectionNumber());
		applicationData.setIsCheckListApplicable(false);
		applicationData.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		applicantDetailDto.setServiceId(objectionDto.getServiceId());
		applicantDetailDto.setDepartmentId(objectionDto.getObjectionDeptId());
		applicantDetailDto.setUserId(objectionDto.getUserId());
		applicantDetailDto.setOrgId(objectionDto.getOrgId());
		applicantDetailDto.setDwzid1(objectionDto.getCodIdOperLevel1());
		applicantDetailDto.setDwzid2(objectionDto.getCodIdOperLevel2());
		applicationData.setApplicationId(objectionDto.getApmApplicationId());
		/*
		 * applicantDetailDto.setDwzid1(provisionalAssesmentMstDto.getAssWard1());
		 * applicantDetailDto.setDwzid2(provisionalAssesmentMstDto.getAssWard2());
		 * applicantDetailDto.setDwzid3(provisionalAssesmentMstDto.getAssWard3());
		 * applicantDetailDto.setDwzid4(provisionalAssesmentMstDto.getAssWard4());
		 * applicantDetailDto.setDwzid5(provisionalAssesmentMstDto.getAssWard5());
		 */
		commonService.initiateWorkflowfreeService(applicationData, applicantDetailDto);
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

	@Transactional
	@Override
	public void saveAndUpateObjectionMaster(ObjectionDetailsDto objectionDetailsDto, Long empId, String ipAddress) {
		String objectionNumber = generateObjectionNumber(objectionDetailsDto);
		objectionDetailsDto.setObjectionNumber(objectionNumber);
		TbObjectionEntity objEnt = new TbObjectionEntity();
		BeanUtils.copyProperties(objectionDetailsDto, objEnt);
		if (objEnt.getObjectionId() == null) {
			objEnt.setCreatedBy(empId);
			objEnt.setLgIpMac(ipAddress);
			objEnt.setCreatedDate(new Date());
			objEnt.setObjectionDate(new Date());

		} else {
			objEnt.setUpdatedBy(empId);
			objEnt.setLgIpMacUpd(ipAddress);
			objEnt.setUpdatedDate(new Date());
		}
		objectionMastRepository.save(objEnt);
	}

	private String generateObjectionNumber(ObjectionDetailsDto objectionDetailsDto) {

		final Long seq = seqGenFunctionUtility.generateSequenceNo(MainetConstants.CommonConstants.COM,
				MainetConstants.OBJECTION_COMMON.OBJECTION_TB_OBJECTION_ENTITY,
				MainetConstants.OBJECTION_COMMON.OBJECTION_NUMBER, objectionDetailsDto.getOrgId(),
				MainetConstants.FlagF, null);
		Organisation org = iOrganisationService.getOrganisationById(objectionDetailsDto.getOrgId());
		String deptCode = tbDepartmentService.findDepartmentShortCodeByDeptId(objectionDetailsDto.getObjectionDeptId(),
				objectionDetailsDto.getOrgId());
		String currentYear = null;
		try {
			currentYear = Utility.getFinancialYearFromDate(new Date());
		} catch (Exception e) {
		}
		final String year[] = currentYear.split("-");
		final String finYear = year[0].substring(2) + year[1].substring(2);
		final String num = seq.toString();
		final String paddingAppNo = String.format(MainetConstants.CommonMasterUi.CD, Integer.parseInt(num));
		return org.getOrgShortNm().concat(deptCode).concat(finYear).concat(paddingAppNo);
	}

	private void sendSmsAndEmail(ObjectionDetailsDto objectionDetailsDto) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(objectionDetailsDto.geteMail());
		dto.setAppName(String.join(" ", Arrays.asList(objectionDetailsDto.getfName(), objectionDetailsDto.getmName(),
				objectionDetailsDto.getlName())));
		dto.setMobnumber(objectionDetailsDto.getMobileNo());
		if (objectionDetailsDto.getApmApplicationId() != null) {
			dto.setAppNo(objectionDetailsDto.getApmApplicationId().toString());
		}
		dto.setReferenceNo(objectionDetailsDto.getObjectionReferenceNumber());
		dto.setObjectionNo(objectionDetailsDto.getObjectionNumber());
		dto.setFrmDt(Utility.dateToString(objectionDetailsDto.getObjectionDate()));
		String paymentUrl = MainetConstants.OBJECTION_COMMON.OBJECTION_SMS_EMAIL;
		Organisation org = new Organisation();
		org.setOrgid(objectionDetailsDto.getOrgId());
		// Added Changes As per told by Rajesh Sir For Sms and Email
		dto.setUserId(objectionDetailsDto.getUserId());
		iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER, paymentUrl,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, org, Utility.getDefaultLanguageId(org));

	}

	@Override
	@POST
	@Path("/saveObjection")
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectionDetailsDto saveObjectionWithValidation(@RequestBody ObjectionDetailsDto objDto) {
		Map<String, Long> result = getApplicationNumberByRefNo(objDto);
		objDto.setApmApplicationId(result.get(MainetConstants.Objection.APPLICTION_NO));
		List<String> error = validateObjection(objDto);
		if (error.isEmpty()) {
			saveObjectionAndCallWorkFlow(objDto);
		}
		return objDto;

	}

	private List<String> validateObjection(ObjectionDetailsDto objDto) {
		List<String> error = new ArrayList<>();
		if (objDto.getApmApplicationId() != null) {
			final ApplicationSession session = ApplicationSession.getInstance();
			Organisation org = new Organisation();
			org.setOrgid(objDto.getOrgId());
			String objOn = CommonMasterUtility.getNonHierarchicalLookUpObject(objDto.getObjectionOn(), org)
					.getLookUpCode();
			if (MainetConstants.Objection.ObjectionOn.NOTICE.equals(objOn)) {
				if (objDto.getNoticeNo() != null && !objDto.getNoticeNo().isEmpty()) {
					boolean validNoticeNo = true;
					int notCount=0;
					notCount = noticeMasterService.getCountOfNotByRefNoAndNotNo(objDto.getOrgId(),
							objDto.getObjectionReferenceNumber(), objDto.getNoticeNo());
					
					//120052 - In case of individual property where prop no is not unique.
					if (notCount <= 0)
						notCount = noticeMasterService.getCountOfNotByApplNoAndNotNo(objDto.getOrgId(),
								objDto.getApmApplicationId(), objDto.getNoticeNo());
					//
					if (notCount <= 0) {
						error.add(session.getMessage("obj.validation.noticeNo.valid"));
						validNoticeNo = false;
					}
					if (validNoticeNo) {
						int count =0;
						count=noticeMasterService.getCountOfNotBeforeDueDateByRefNoAndNotNo(objDto.getOrgId(),
								objDto.getObjectionReferenceNumber(), objDto.getNoticeNo());
						
						//120052 - In case of individual property where prop no is not unique.
						if (count <= 0)
							count = noticeMasterService.getCountOfNotBeforeDueDateByApplNoAndNotNo(objDto.getOrgId(),
									objDto.getApmApplicationId(), objDto.getNoticeNo());
						//
						if (count <= 0) {
							error.add(session.getMessage("obj.validation.noticeNo"));
						}
					}
				}
			} /*
				 * else if (MainetConstants.Objection.ObjectionOn.FIRST_APPEAL.equals(objOn)) {
				 * if (objDto.getServiceId() != null) { boolean isRTSService =
				 * serviceMasterService.isServiceRTS(objDto.getServiceId(), objDto.getOrgId());
				 * if (!isRTSService) { error.add(session.getMessage("obj.validation.rts")); } }
				 * }
				 */
		}
		return error;
	}

	@Override
	@WebMethod(exclude = true)
	public List<Department> getActiveDepartment() {
		return iDepartmentDAO.getAllDepartment(MainetConstants.STATUS.ACTIVE);
	}

	@Override
	@WebMethod(exclude = true)
	public ObjectionDetailsDto getObjectionDetailByAppId(Long orgid, Long serviceId, Long applicationId) {
		ObjectionDetailsDto objDto = null;
		TbObjectionEntity objEnt = objectionMastRepository.getObjectionDetailByAppId(orgid, applicationId, serviceId);
		if (objEnt != null) {
			objDto = new ObjectionDetailsDto();
			BeanUtils.copyProperties(objEnt, objDto);
		}
		return objDto;
	}

	@Override
	public List<ObjectionDetailsDto> getObjectionDetailListByAppId(Long orgid, Long serviceId, Long applicationId) {
		List<ObjectionDetailsDto> objDetailList = new ArrayList<>();
		List<TbObjectionEntity> objectionEntity = objectionMastRepository.getObjectionDetailListByAppId(orgid,
				applicationId, serviceId);

		if (objectionEntity != null && !objectionEntity.isEmpty()) {
			objectionEntity.forEach(objDetail -> {
				ObjectionDetailsDto objDet = new ObjectionDetailsDto();
				BeanUtils.copyProperties(objDetail, objDet);
				objDetailList.add(objDet);
			});

		}
		return objDetailList;
	}

	@Override
	@WebMethod(exclude = true)
	public ObjectionDetailsDto getObjectionDetailByObjNo(Long orgid, String objNo) {
		ObjectionDetailsDto objDto = null;
		TbObjectionEntity objEnt = objectionMastRepository.getObjectionDetailByObjNo(orgid, objNo);
		if (objEnt != null) {
			objDto = new ObjectionDetailsDto();
			BeanUtils.copyProperties(objEnt, objDto);
		}
		return objDto;
	}

	@Override
	@WebMethod(exclude = true)
	public ObjectionDetailsDto getObjectionDetailByObjId(Long orgid, Long ObjId) {
		ObjectionDetailsDto objDto = null;
		TbObjectionEntity objEnt = objectionMastRepository.getObjectionDetailByObjId(orgid, ObjId);
		if (objEnt != null) {
			objDto = new ObjectionDetailsDto();
			BeanUtils.copyProperties(objEnt, objDto);
		}
		return objDto;
	}

	@Override
	@WebMethod(exclude = true)
	public Map<String, Long> getApplicationNumberByRefNo(ObjectionDetailsDto objDto) {
		Map<String, Long> result = null;
		Class<?> clazz = null;
		String serviceClassName = null;
		String deptCode = null;
		Object dynamicServiceInstance = null;
		try {
			deptCode = departmentService.getDeptCode(objDto.getObjectionDeptId());
			serviceClassName = messageSource.getMessage(
					ApplicationSession.getInstance().getMessage("objection.lbl") + deptCode, new Object[] {},
					StringUtils.EMPTY, Locale.ENGLISH);
			if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
				clazz = ClassUtils.forName(serviceClassName,
						ApplicationContextProvider.getApplicationContext().getClassLoader());
				dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
						.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
				final Method method = ReflectionUtils.findMethod(clazz, "getApplicationNumberByRefNo",
						new Class[] { String.class, Long.class, Long.class, Long.class });
				result = (Map<String, Long>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
						new Object[] { objDto.getObjectionReferenceNumber(), objDto.getServiceId(), objDto.getOrgId(),
								objDto.getUserId() });
			}
		} catch (Exception e) {
			throw new FrameworkException(
					"Exception in Objection for finding reference Number  :" + objDto.getObjectionReferenceNumber(), e);
		}
		return result;
	}

	@Override
	@WebMethod(exclude = true)
	public boolean isValidBillNoForObjection(ObjectionDetailsDto objDto) {
		boolean result = false;
		Class<?> clazz = null;
		String serviceClassName = null;
		String deptCode = null;
		Object dynamicServiceInstance = null;
		try {
			deptCode = departmentService.getDeptCode(objDto.getObjectionDeptId());
			serviceClassName = messageSource.getMessage(
					ApplicationSession.getInstance().getMessage("objection.lbl") + deptCode, new Object[] {},
					StringUtils.EMPTY, Locale.ENGLISH);
			if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
				clazz = ClassUtils.forName(serviceClassName,
						ApplicationContextProvider.getApplicationContext().getClassLoader());
				dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
						.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
				final Method method = ReflectionUtils.findMethod(clazz, "isValidBillNoForObjection",
						new Class[] { String.class, String.class, Date.class, Long.class, Long.class });
				result = (boolean) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
						new Object[] { objDto.getObjectionReferenceNumber(), objDto.getBillNo().toString(),
								objDto.getBillDueDate(), objDto.getServiceId(), objDto.getOrgId() });
			}
		} catch (Exception e) {
			throw new FrameworkException(
					"Exception in Objection for finding reference Number  :" + objDto.getObjectionReferenceNumber(), e);
		}
		return result;
	}

	@Transactional
	public List<ObjectionDetailsDto> searchObjectionDetails(long orgId, Long objectionDeptId, Long serviceId,
			String refNo, Long objectionOn) {

		List<ObjectionDetailsDto> objDetailList = new ArrayList<>();
		List<TbObjectionEntity> objectionEntity = iObjectionServiceDAO.getObjectionDetails(orgId, objectionDeptId,
				serviceId, refNo, objectionOn);

		if (objectionEntity != null && !objectionEntity.isEmpty()) {
			objectionEntity.forEach(objDetail -> {
				ObjectionDetailsDto objDet = new ObjectionDetailsDto();
				BeanUtils.copyProperties(objDetail, objDet);
				objDet.setObjectionDate(objDetail.getObjectionDate());
				// description of objectionOn using Prefix OBJ
				if (objDetail.getObjectionOn() != null) {
					objDet.setObjectionAppealType(
							CommonMasterUtility.getCPDDescription(objDetail.getObjectionOn(), MainetConstants.FlagE));
				}
				objDetailList.add(objDet);
			});

		}
		return objDetailList;
	}

	@Override
	@WebMethod(exclude = true)
	@POST
	@Path("/getObjectionCharges")
	@Produces(MediaType.APPLICATION_JSON)
	public CommonChallanDTO getCharges(@RequestBody ObjectionDetailsDto objDto) {

		Organisation org = new Organisation();
		LookUp taxAppAtObj = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
				MainetConstants.NewWaterServiceConstants.CAA, org);
		LookUp lookup = CommonMasterUtility.getHieLookupByLookupCode("OB", PrefixConstants.LookUpPrefix.TAC, 2,
				objDto.getOrgId());

		if (lookup != null && taxAppAtObj != null) {
			List<TbTaxMasEntity> tbTaxMas = tbTaxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(),
					objDto.getObjectionDeptId(), taxAppAtObj.getLookUpId(), lookup.getLookUpId());

		}
		CommonChallanDTO result = null;
		Class<?> clazz = null;
		String serviceClassName = null;
		String deptCode = null;
		Object dynamicServiceInstance = null;
		try {
			deptCode = departmentService.getDeptCode(objDto.getObjectionDeptId());
			serviceClassName = messageSource.getMessage(
					ApplicationSession.getInstance().getMessage("objection.lbl") + deptCode, new Object[] {},
					StringUtils.EMPTY, Locale.ENGLISH);
			if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
				clazz = ClassUtils.forName(serviceClassName,
						ApplicationContextProvider.getApplicationContext().getClassLoader());
				dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
						.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
				final Method method = ReflectionUtils.findMethod(clazz, "getCharges",
						new Class[] { Long.class, Long.class, String.class });
				result = (CommonChallanDTO) ReflectionUtils.invokeMethod(method, dynamicServiceInstance, new Object[] {
						objDto.getServiceId(), objDto.getOrgId(), objDto.getObjectionReferenceNumber() });
			}
		} catch (Exception e) {
			throw new FrameworkException(
					"Exception in Objection for finding reference Number  :" + objDto.getObjectionReferenceNumber(), e);
		}
		return result;
	}

	@Override
	@WebMethod(exclude = true)
	@Transactional(readOnly = true)
	public List<ObjectionDetailsDto> getAllInactiveObjectionList() {
		List<ObjectionDetailsDto> objDetailList = new ArrayList<>();
		List<TbObjectionEntity> objectionEntity = objectionMastRepository.getAllInactiveObjectionList();

		if (objectionEntity != null && !objectionEntity.isEmpty()) {
			objectionEntity.forEach(objDetail -> {
				ObjectionDetailsDto objDet = new ObjectionDetailsDto();
				BeanUtils.copyProperties(objDetail, objDet);
				objDet.setObjectionDate(objDetail.getObjectionDate());
				objDetailList.add(objDet);
			});

		}
		return objDetailList;
	}

	// Defect #34042 --> to get application details by reference number
	@Override
	@WebMethod(exclude = true)
	public ObjectionDetailsDto fetchRtiAppDetailByRefNo(ObjectionDetailsDto objDto) {
		String objectionReferenceNumber = objDto.getObjectionReferenceNumber();
		Long OrgId = objDto.getOrgId();
		Class<?> clazz = null;
		String deptCode = null;
		Object dynamicServiceInstance = null;
		ObjectionDetailsDto result = new ObjectionDetailsDto();

		try {

			deptCode = departmentService.getDeptCode(objDto.getObjectionDeptId());
			String serviceClassName = messageSource.getMessage(
					ApplicationSession.getInstance().getMessage("objection.lbl") + deptCode, new Object[] {},
					StringUtils.EMPTY, Locale.ENGLISH);
			if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
				clazz = ClassUtils.forName(serviceClassName,
						ApplicationContextProvider.getApplicationContext().getClassLoader());
				dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
						.getAutowireCapableBeanFactory().autowire(clazz, 2, false);

				final Method method = ReflectionUtils.findMethod(clazz, MainetConstants.RTI_APPLICATION,
						new Class[] { String.class, Long.class });

				result = (ObjectionDetailsDto) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
						new Object[] { objectionReferenceNumber, OrgId });
				objDto.setApplicationDate(result.getApplicationDate());
				objDto.setApmApplicationId(result.getApmApplicationId());
                //comment due to wrong setting of title id
				objDto.setDispatchNo(result.getDispatchNo());
				objDto.setDispachDate(result.getDispachDate());
				objDto.setDeliveryDate(result.getDeliveryDate());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objDto;
	}

	@Override
	public ObjectionDetailsDto getObjectionDetailByIds(Long orgid, Long serviceId, Long applicationId,
			Long objectionOn) {
		TbObjectionEntity objEnt = objectionMastRepository.getObjectionDetailByIds(orgid, applicationId, serviceId,
				objectionOn);
		ObjectionDetailsDto objDto = new ObjectionDetailsDto();
		if (objEnt != null) {
			BeanUtils.copyProperties(objEnt, objDto);
		}
		return objDto;
	}

	@Override
	public List<ObjectionDetailsDto> fetchObjectionsByMobileNoAndIds(String mobileNo, Long orgId, Long serviceId,
			Long objectionOn) {
		List<ObjectionDetailsDto> objDetailList = new ArrayList<>();
		List<TbObjectionEntity> entities = objectionMastRepository.fetchObjectionsByMobileNoAndIds(orgId, serviceId,
				objectionOn, mobileNo);
		if (entities != null && !entities.isEmpty()) {
			entities.forEach(objDetail -> {
				ObjectionDetailsDto objDet = new ObjectionDetailsDto();
				BeanUtils.copyProperties(objDetail, objDet);
				objDetailList.add(objDet);
			});
		}
		return objDetailList;
	}

	@Override
	@Transactional
	public void updateStatusOfOjection(Long objectionId, Long updatedBy, String lgIpMacUpd, String objectionStatus) {
		objectionMastRepository.updateObjectionStatus(updatedBy, lgIpMacUpd, objectionStatus, objectionId);
	}

	@Override
	@Transactional
	public ObjectionDetailsDto saveRTSAppealInObjection(ObjectionDetailsDto objDto) {
		saveAndUpateObjectionMaster(objDto, objDto.getUserId(), objDto.getIpAddress());
		RequestDTO reqDto = new RequestDTO();
		setRequestApplicantDetails(reqDto, objDto, objDto.getOrgId(), objDto.getUserId());
		fileUploadService.doFileUpload(objDto.getDocs(), reqDto);
		initiateWorkflow(objDto);
		return objDto;
	}

	@Override
	public ObjectionDetailsDto saveLicenseObjectionData(ObjectionDetailsDto objectionDto) {	
		if (objectionDto.getLocId() != null) {
			setDeptWardZone(objectionDto);
		}
		Long applicationId = null;
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(MainetConstants.TradeLicense.LICENSE_OBJ,
						objectionDto.getOrgId());
		RequestDTO requestDto = setApplicantRequestDto(objectionDto, sm);		
		saveAndUpateObjectionMaster(objectionDto, objectionDto.getUserId(), objectionDto.getIpAddress());
		fileUploadService.doFileUpload(objectionDto.getDocs(), requestDto);
		initiateWorkflow(objectionDto);
		return objectionDto;
	}

	private RequestDTO setApplicantRequestDto(ObjectionDetailsDto objDto, ServiceMaster sm) {
		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(objDto.getCreatedBy());

		requestDto.setOrgId(objDto.getOrgId());
		requestDto.setLangId(Long.valueOf(objDto.getLangId()));
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		// setting applicant info
		requestDto.setfName(objDto.getfName());
		requestDto.setEmail(objDto.geteMail());
		requestDto.setMobileNo(objDto.getMobileNo());
		requestDto.setUserId(objDto.getUserId());
		requestDto.setAreaName(objDto.getAddress());
		requestDto.setReferenceId(objDto.getObjectionAddReferenceNumber());
		return requestDto;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	@WebMethod(exclude = true)
	public List<String> getFlatListByRefNo(String refNo, Long serviceId, Long objectionDeptId, Long orgId) {
		List<String> flatList = new ArrayList<>();
		Class<?> clazz = null;
		String serviceClassName = null;
		String deptCode = null;
		Object dynamicServiceInstance = null;
		try {
			deptCode = departmentService.getDeptCode(objectionDeptId);
			serviceClassName = messageSource.getMessage(
					ApplicationSession.getInstance().getMessage("objection.lbl") + deptCode, new Object[] {},
					StringUtils.EMPTY, Locale.ENGLISH);
			if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {
				clazz = ClassUtils.forName(serviceClassName,
						ApplicationContextProvider.getApplicationContext().getClassLoader());
				dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
						.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
				final Method method = ReflectionUtils.findMethod(clazz, "getFlatListByRefNo",
						new Class[] { String.class, Long.class });
				flatList = (List<String>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
						new Object[] { refNo, orgId });
			}
		} catch (Exception e) {
			throw new FrameworkException(
					"Exception in Objection for finding Flat list with reference Number  :" + refNo, e);
		}
		return flatList;
	}
	
	@Override
	@WebMethod(exclude = true)
	public ObjectionDetailsDto getObjectionDetailByRefNo(Long orgId, String objectionReferenceNumber) {
		ObjectionDetailsDto objDto = null;

		List<TbObjectionEntity> objEntList = objectionMastRepository.getObjectionEntityByRefNo(orgId, objectionReferenceNumber);
		for(TbObjectionEntity objEnt : objEntList)
		{
			objDto = new ObjectionDetailsDto();
			BeanUtils.copyProperties(objEnt, objDto);
			break;
		}
		
		return objDto;
	}


}
