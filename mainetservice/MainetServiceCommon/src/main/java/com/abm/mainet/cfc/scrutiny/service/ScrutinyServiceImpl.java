/**
 *
 */
package com.abm.mainet.cfc.scrutiny.service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import org.springframework.beans.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.scrutiny.dao.ScrutinyDAO;
import com.abm.mainet.cfc.scrutiny.domain.TbScrutinyLabelValueEntity;
import com.abm.mainet.cfc.scrutiny.domain.TbScrutinyLabelValueEntityKey;
import com.abm.mainet.cfc.scrutiny.domain.TbScrutinyLabelValueHistEntity;
import com.abm.mainet.cfc.scrutiny.domain.TbScrutinyLabelsEntity;
import com.abm.mainet.cfc.scrutiny.dto.LicenseApplicationLandAcquisitionDetailDTO;
import com.abm.mainet.cfc.scrutiny.dto.LicenseGrantedDTO;
import com.abm.mainet.cfc.scrutiny.dto.NotingDTO;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLabelDTO;
import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.cfc.scrutiny.dto.SiteAffectedDTO;
import com.abm.mainet.cfc.scrutiny.dto.ViewCFCScrutinyLabelValue;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.repository.CFCAttechmentRepository;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.master.repository.TbScrutinyDocJpaRepository;
import com.abm.mainet.common.master.repository.TbScrutinyLabelValueEntityJpaRepository;
import com.abm.mainet.common.master.repository.TbScrutinyLabelsJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.LoiDetail;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.repository.WorkFlowTypeRepository;
import com.abm.mainet.common.workflow.repository.WorkflowMappingRepository;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;

/**
 * The Class ScrutinyServiceImpl.
 *
 * @author Lalit.Prusti
 */
@Service
public class ScrutinyServiceImpl implements ScrutinyService {

	private static Logger LOG = Logger.getLogger(ScrutinyServiceImpl.class);

	@Resource
	private ScrutinyDAO scrutinyDAO;

	@Resource
	EmployeeJpaRepository employeeJpaRepository;

	@Resource
	ServiceMasterRepository serviceMasterRepository;

	@Resource
	TbScrutinyLabelsJpaRepository labelsJpaRepository;

	@Resource
	TbScrutinyLabelValueEntityJpaRepository labelValueEntityJpaRepository;

	@Resource
	private TbScrutinyDocJpaRepository scrutinyDocJpaRepository;

	@Resource
	private ICFCApplicationMasterService icfcApplicationMasterService;

	@Resource
	private ServiceMasterService serviceMasterService;

	@Resource
	private TbLoiMasService loiMasService;

	@Resource
	ICFCApplicationMasterService cfcService;
	@Resource
	private IWorkflowExecutionService workflowExecutionService;
	@Autowired
	private ICFCApplicationAddressService iCFCApplicationAddressService;
	
	@Autowired
    private IWorkFlowTypeService iWorkFlowTypeService;
	
	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;
	
	@Autowired
	DepartmentService departmentService;
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private WorkFlowTypeRepository workFlowTypeRepository;
	
	@Autowired
	AuditService auditService;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	private WorkflowMappingRepository workFlowMapRepo;
	
	@Autowired
    private CFCAttechmentRepository cFCAttechmentRepository;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public ScrutinyLabelDTO populateScrutinyLabelData(final long applicationId, final long empId, final long gmId,
			final long orgId, final long serviceId, final int langId, final String refNo,Long currentLevel) {

		LOG.info("Start the populateScrutinyLabelData()");
		
		final ScrutinyLabelDTO scrutinyLabelDTO = new ScrutinyLabelDTO();
		Long tricod1 = 0L;
		final ServiceMaster servicename = serviceMasterService.getServiceMaster(serviceId, orgId);
		try {
			scrutinyLabelDTO.setApplicationId(applicationId + MainetConstants.BLANK);

			final TbCfcApplicationMstEntity cfcApplicationMaster = icfcApplicationMasterService
					.getCFCApplicationByApplicationId(applicationId, orgId);
			final CFCApplicationAddressEntity address = iCFCApplicationAddressService
					.getApplicationAddressByAppId(applicationId, orgId);
			Organisation org = new Organisation();
			if (cfcApplicationMaster != null) {
				String userName = (cfcApplicationMaster.getApmFname() == null ? MainetConstants.BLANK
						: cfcApplicationMaster.getApmFname() + MainetConstants.WHITE_SPACE);
				userName += cfcApplicationMaster.getApmMname() == null ? MainetConstants.BLANK
						: cfcApplicationMaster.getApmMname() + MainetConstants.WHITE_SPACE;
				userName += cfcApplicationMaster.getApmLname() == null ? MainetConstants.BLANK
						: cfcApplicationMaster.getApmLname();
				scrutinyLabelDTO.setApplicantName(userName);
				//code added for set email and mobile no for Defect#121682
				if(address!=null) {
					scrutinyLabelDTO.setEmail(address.getApaEmail());
					scrutinyLabelDTO.setMobNo(address.getApaMobilno());
				}

				scrutinyLabelDTO.setServiceName(servicename.getSmServiceName());
				scrutinyLabelDTO.setServiceNameReg(servicename.getSmServiceNameMar());
				scrutinyLabelDTO.setServiceShortCode(servicename.getSmShortdesc());
				scrutinyLabelDTO.setSmServiceId(serviceId);
				scrutinyLabelDTO.setRefNo(refNo);
				// US#113590
				
				org.setOrgid(orgId);
				try {
					if (ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
							.getDeptCode(servicename.getTbDepartment().getDpDeptid())
							.equals(MainetConstants.TradeLicense.MARKET_LICENSE)
							&& (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)|| Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL))) {
						Map<String, Long> itemMap = getApplicationDetails(applicationId);

						if (itemMap.get(MainetConstants.TRI_COD1) != null
								&& itemMap.get(MainetConstants.TRI_COD1) > 0) {
							List<TbScrutinyLabelsEntity> list = ApplicationContextProvider.getApplicationContext()
									.getBean(TbScrutinyLabelsJpaRepository.class).checkScrutinyLabelExistOrNot(
											serviceId, orgId, itemMap.get(MainetConstants.TRI_COD1));
							if (list != null && !list.isEmpty()) {
								tricod1 = itemMap.get(MainetConstants.TRI_COD1);
							}
						}
					}
				} catch (Exception e) {
					LOG.error("Exception occur  when fetching Item Category and SubCategory");
				}
			}
              //  105334 - to get license end date by ApplicationId
			try {
				if (departmentService.getDeptCode(servicename.getTbDepartment().getDpDeptid())
						.equals(MainetConstants.TradeLicense.MARKET_LICENSE)) {
					String liceseEndDate = getAppDetailByAppId(applicationId);
					
					if (StringUtils.isNotBlank(liceseEndDate))
						if(!(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL) 
								&& 	(servicename.getSmShortdesc().equals(MainetConstants.TradeLicense.TRANSFER_SERVICE_SHORT_CODE) 
										|| servicename.getSmShortdesc().equals(MainetConstants.TradeLicense.SERVICE_SHORT_CODE)))) {
							scrutinyLabelDTO.setLicsenseDate(liceseEndDate);
						}
				}
			} catch (Exception e) {
				LOG.error("Exception occur  when fetching licenes detail");
			}
             //#140388
			try {
				if (servicename.getTbDepartment() != null && servicename.getTbDepartment().getDpDeptid() != null) {
					String deptCode = departmentService.getDeptCode(servicename.getTbDepartment().getDpDeptid());
					if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SUDA)
							&& MainetConstants.TradeLicense.MARKET_LICENSE.equals(deptCode)) {
						scrutinyLabelDTO.setReamrkValidFlag(MainetConstants.FlagY);
					}
				}
			} catch (Exception e) {
				LOG.error("Exception occur  when setting remark flag  for suda" + e);
			}
			// US#113590 end
			final List<ViewCFCScrutinyLabelValue> scrutinyLabelValues = new ArrayList<>();
			final List<ViewCFCScrutinyLabelValue> scrutinyFieldLabelValues = new ArrayList<>();
			final List<ViewCFCScrutinyLabelValue> finalScrutinyFieldLabelValues = new ArrayList<>();
			final List<ViewCFCScrutinyLabelValue> finalScrutinyFieldLabelDocument = new ArrayList<>();
			Map<String, String> map = new HashMap<String, String>();
			 List<Object> fndAllscrutinyLabelValue = new ArrayList<Object>();
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TCP)) {
				fndAllscrutinyLabelValue= scrutinyDAO.getAllscrutinyLabelValueListTCP(orgId, serviceId,
						null, applicationId, tricod1,currentLevel,UserSession.getCurrent().getEmployee().getGmid());
			}else {
			fndAllscrutinyLabelValue= scrutinyDAO.getfindAllscrutinyLabelValueList(orgId, serviceId,
					null, applicationId, tricod1,currentLevel);
			}
			if (fndAllscrutinyLabelValue != null) {

				final int listSize = fndAllscrutinyLabelValue.size();

				for (int iCounter = 0; iCounter < listSize; iCounter++) {

					final Object[] obj = (Object[]) fndAllscrutinyLabelValue.get(iCounter);
					ViewCFCScrutinyLabelValue labelValue = null;

					labelValue = new ViewCFCScrutinyLabelValue();

					labelValue.setScrutinyId(Long.valueOf(obj[0].toString()));

					if ((obj[1].toString() != null) && !obj[1].toString().isEmpty()) {
						labelValue.setSlLabel((obj[1].toString()));
					}

					if ((obj[2] != null) && !obj[2].toString().isEmpty()) {
						labelValue.setSlAuthorising(obj[2].toString());
					}

					if ((obj[3] != null) && !obj[3].toString().isEmpty()) {
						labelValue.setSlDatatype(obj[3].toString());
					}

					if ((obj[4] != null) && !obj[4].toString().isEmpty()) {
						labelValue.setSlDisplayFlag(obj[4].toString());
					}

					if ((obj[5] != null) && !obj[5].toString().isEmpty()) {
						labelValue.setSlFormMode(obj[5].toString());
					}

					if ((obj[6] != null) && !obj[6].toString().isEmpty()) {
						labelValue.setSlFormName(obj[6].toString());
					}

					if (obj[7] != null) {
						labelValue.setSlLabelId(Long.valueOf(obj[7].toString()));
					}

					if ((obj[8] != null) && !obj[8].toString().isEmpty()) {
						labelValue.setSlLabelMar(obj[8].toString());
					}

					if ((obj[9] != null) && !obj[9].toString().isEmpty()) {
						labelValue.setSlPreValidation(obj[9].toString());
					}

					if ((obj[10] != null) && !obj[10].toString().isEmpty()) {
						labelValue.setSlValidationText(obj[10].toString());
					}

					if ((obj[11] != null) && !obj[11].toString().isEmpty()) {
						labelValue.setSlTableColumn(obj[11].toString());
					}

					if ((obj[12] != null) && !obj[12].toString().isEmpty()) {
						labelValue.setSlWhereClause(obj[12].toString());
					}

					if ((obj[13] != null) && !obj[13].toString().isEmpty()) {
						labelValue.setSlDsgid(Long.valueOf(obj[13].toString()));
					}

					if ((obj[14] != null) && !obj[14].toString().isEmpty()) {
						labelValue.setLevels(Long.valueOf(obj[14].toString()));
					}

					if ((obj[15] != null) && !obj[15].toString().isEmpty()) {
						labelValue.setSvValue(obj[15].toString());
					}
					if ((obj[16] != null) && !obj[16].toString().isEmpty()) {
						labelValue.setApplicationId(Long.valueOf(obj[16].toString()));
					} else {

						labelValue.setApplicationId(applicationId);
					}

					if ((obj[17] != null) && !obj[17].toString().isEmpty()) {
						labelValue.setResolutionComments(obj[17].toString());
					}
					
					if ((obj[18] != null) && !obj[18].toString().isEmpty()) {
						labelValue.setSlQuery(obj[18].toString());
					}
					labelValue.setOrgId(orgId);
					if(labelValue.getSlDatatype()!=null && labelValue.getSlDatatype().equals("COLUMN")) {
						scrutinyFieldLabelValues.add(labelValue);
					}else if(labelValue.getSlDatatype()!=null && labelValue.getSlDatatype().equals("DOCUMENT")) {
						finalScrutinyFieldLabelDocument.add(labelValue);
					}else {
						scrutinyLabelValues.add(labelValue);
					}
					
				}
			}
				
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TCP) && StringUtils.equals("NTL", servicename.getSmShortdesc())) {
				Class<?> clazz = null;
				Object dynamicServiceInstance = null;
				String serviceClassName = null;
				serviceClassName = "com.abm.mainet.tradeLicense.service.TradeLicenseApplicationServiceImpl";
				clazz = ClassUtils.forName(serviceClassName,
						ApplicationContextProvider.getApplicationContext().getClassLoader());
				dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
						.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
				final Method method = ReflectionUtils.findMethod(clazz,
						ApplicationSession.getInstance().getMessage("getApplicationDetail"),
						new Class[] { Long.class });
				map = (Map<String, String>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
						new Object[] { applicationId });
			}
			
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TCP)) {
				Class<?> clazz = null;
				Object dynamicServiceInstance = null;
				String serviceClassName = null;
				serviceClassName = "com.abm.mainet.buildingplan.service.NewLicenseFormServiceImpl";
				clazz = ClassUtils.forName(serviceClassName,
						ApplicationContextProvider.getApplicationContext().getClassLoader());
				dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
						.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
				final Method method = ReflectionUtils.findMethod(clazz,
						ApplicationSession.getInstance().getMessage("getApplicationDetail"),
						new Class[] { Long.class ,Long.class});
				map = (Map<String, String>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
						new Object[] { applicationId,orgId });
			}

			scrutinyLabelDTO.setRoleId(String.valueOf(gmId));
			if ((scrutinyLabelValues != null) && (scrutinyLabelValues.size() > 0)) {
				final Map<Long, List<ViewCFCScrutinyLabelValue>> desgLabelMap = filterListAsPerDesignation(
						scrutinyLabelDTO, scrutinyLabelValues, langId, orgId);
				scrutinyLabelDTO.setDesgWiseScrutinyLabelMap(desgLabelMap);
			}
			if(!finalScrutinyFieldLabelDocument.isEmpty()) {
				finalScrutinyFieldLabelValues.addAll(finalScrutinyFieldLabelDocument);
			}
			if ((scrutinyFieldLabelValues != null) && (scrutinyFieldLabelValues.size() > 0) && !map.isEmpty()) {
				for (final ViewCFCScrutinyLabelValue object : scrutinyFieldLabelValues) {
					if (map.containsKey(object.getSlQuery())) {
						object.setInputData(map.get(object.getSlQuery()));
						finalScrutinyFieldLabelValues.add(object);
					}
				}
			}
			if((finalScrutinyFieldLabelValues != null) && (finalScrutinyFieldLabelValues.size() > 0) && !map.isEmpty()) {
				Collections.sort(finalScrutinyFieldLabelValues, Comparator.comparing(ViewCFCScrutinyLabelValue::getSlLabelId));
				final Map<Long, List<ViewCFCScrutinyLabelValue>> desgFieldLabelMap = filterListAsPerDesignation(
						scrutinyLabelDTO, finalScrutinyFieldLabelValues, langId, orgId);
				scrutinyLabelDTO.setDesgWiseFieldLabelMap(desgFieldLabelMap);
				
			}
			
			//getNoteSheetData
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TCP)) {
			getnoteSheetData(applicationId,scrutinyLabelDTO,map);
			}
			

		} catch (final Exception exception) {
			LOG.error("Exception occur in populateScrutinyLabelData() ", exception);

		}
		return scrutinyLabelDTO;
	}

	@SuppressWarnings("unchecked")
	private void getnoteSheetData(final long applicationId,ScrutinyLabelDTO scrutinyLabelDTO,Map<String, String> map) {
		Long orgId=UserSession.getCurrent().getOrganisation().getOrgid();
		final List<ViewCFCScrutinyLabelValue> scrutinyLabelValues = new ArrayList<>();
		final List<NotingDTO> notingList = new ArrayList<>();
		final List<Object> notingData = scrutinyDAO.getNotingData(applicationId);
		List<Object[]> taskList = workFlowMapRepo.getTaskIdAndCommentByAppId(applicationId);
		if (notingData != null) {
			final int listSize = notingData.size();
		for (int iCounter = 0; iCounter < listSize; iCounter++) {
			final Object[] obj = (Object[]) notingData.get(iCounter);
			ViewCFCScrutinyLabelValue labelValue = null;
			labelValue = new ViewCFCScrutinyLabelValue();
			if ((obj[0].toString() != null) && !obj[0].toString().isEmpty()) {
				labelValue.setSlLabel((obj[0].toString()));
			}
			if ((obj[1] != null) && !obj[1].toString().isEmpty()) {
				labelValue.setLevels(Long.valueOf(obj[1].toString()));
			}
			if ((obj[2] != null) && !obj[2].toString().isEmpty()) {
				labelValue.setResolutionComments(obj[2].toString());
			}
			if ((obj[3] != null) && !obj[3].toString().isEmpty()) {
				labelValue.setSvValue(obj[3].toString());
			}
			if ((obj[4] != null) && !obj[4].toString().isEmpty()) {
				labelValue.setStringDate((Date) obj[4]);
			}
			if (obj[5] != null) {
				labelValue.setSlLabelId(Long.valueOf(obj[5].toString()));
			}
			if ((obj[6] != null) && !obj[6].toString().isEmpty()) {
				labelValue.setApplicationId(Long.valueOf(obj[6].toString()));
			} else {
				labelValue.setApplicationId(applicationId);
			}
			if ((obj[9] != null) && !obj[9].toString().isEmpty()) {
				labelValue.setSlDsgid(Long.valueOf(obj[9].toString()));
			}
			if ((obj[12] != null) && !obj[12].toString().isEmpty()) {
				labelValue.setEmpName(obj[12].toString());
			}
			if ((obj[13] != null) && !obj[13].toString().isEmpty()) {
				labelValue.setSlDsgName(obj[13].toString());
			}
			if ((obj[14] != null) && !obj[14].toString().isEmpty()) {
				labelValue.setTaskId(Long.valueOf(obj[14].toString()));
			}
			if ((obj[15] != null) && !obj[15].toString().isEmpty()) {
				labelValue.setSlDatatype(obj[15].toString());
			}
			if ((obj[16] != null) && !obj[16].toString().isEmpty()) {
				labelValue.setSlQuery(obj[16].toString());
			}
			
			if (labelValue.getSlDatatype() != null && labelValue.getSlDatatype().equals("COLUMN")) {
				if (map.containsKey(labelValue.getSlQuery())) {
					labelValue.setInputData(map.get(labelValue.getSlQuery()));
				}
			}
			labelValue.setOrgId(orgId);
		    scrutinyLabelValues.add(labelValue);
		}
	
	}
		
		for (Object[] ob : taskList) {
			NotingDTO dto = new NotingDTO();
			List<ViewCFCScrutinyLabelValue> newlabelValue = new ArrayList<>();
			List<ViewCFCScrutinyLabelValue> newFieldlabelValue = new ArrayList<>();
			List<LicenseGrantedDTO> licenseGrantedDTO = new ArrayList<LicenseGrantedDTO>();
			List<LicenseApplicationLandAcquisitionDetailDTO> listLandDetailDTO = new ArrayList<LicenseApplicationLandAcquisitionDetailDTO>();
			List<SiteAffectedDTO> listDto = new ArrayList<SiteAffectedDTO>();
			if (Long.valueOf(ob[0].toString()) != null) {
				dto.setTaskId(Long.valueOf(ob[0].toString()));
			}
			if (ob[1].toString() != null) {
				dto.setRemark(ob[1].toString());
			}
			if (ob[2].toString() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a");
				String formattedDateTime = dateFormat.format((Date)ob[2]);
				dto.setStringDate(formattedDateTime);
			}
			if (ob[3].toString() != null) {
				dto.setEmpName(ob[3].toString());
			}
			if (ob[4].toString()!= null) {
				dto.setSlDsgName(ob[4].toString());
			}

			for (ViewCFCScrutinyLabelValue sc : scrutinyLabelValues) {
				if (sc.getTaskId()!=null && sc.getTaskId().equals(dto.getTaskId())) {
					if(sc.getSlDatatype()!=null && sc.getSlDatatype().equals("COLUMN") || sc.getSlDatatype().equals("DOCUMENT")) {
						newFieldlabelValue.add(sc);
					}else {
					newlabelValue.add(sc);
					}
				}
			}
			if(!newFieldlabelValue.isEmpty())
				dto.setScrutinyFieldLabelValues(newFieldlabelValue);

			if (!newlabelValue.isEmpty()) {
				dto.setScrutinyLabelValues(newlabelValue);

				try {
					Class<?> clazz = null;
					Object dynamicServiceInstance = null;
					String serviceClassName = null;
					serviceClassName = "com.abm.mainet.buildingplan.service.NewLicenseFormServiceImpl";
					clazz = ClassUtils.forName(serviceClassName,
							ApplicationContextProvider.getApplicationContext().getClassLoader());
					dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
							.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
					final Method method = ReflectionUtils.findMethod(clazz,
							ApplicationSession.getInstance().getMessage("getApplicationNotingDetail"),
							new Class[] { Long.class, Long.class, Long.class,String.class });
					listLandDetailDTO = (List<LicenseApplicationLandAcquisitionDetailDTO>) ReflectionUtils.invokeMethod(
							method, dynamicServiceInstance,
							new Object[] { applicationId, orgId, dto.getTaskId(),"P"});

					if (!listLandDetailDTO.isEmpty()) {
						dto.setListLandDetailDTO(listLandDetailDTO);
						dto.setLandCfcAttachment(iChecklistVerificationService.getDocumentUploadedByRefNo(
								applicationId + MainetConstants.WINDOWS_SLASH +"NF"+dto.getTaskId(),
								UserSession.getCurrent().getOrganisation().getOrgid()));
					}
				} catch (Exception e) {
					LOG.error("Exception occur in getApplicationNotingDetail ", e);
				}
				try {
					Class<?> clazz = null;
					Object dynamicServiceInstance = null;
					String serviceClassName = null;
					serviceClassName = "com.abm.mainet.buildingplan.service.SiteAffecServiceImpl";
					clazz = ClassUtils.forName(serviceClassName,
							ApplicationContextProvider.getApplicationContext().getClassLoader());
					dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
							.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
					final Method method = ReflectionUtils.findMethod(clazz,
							ApplicationSession.getInstance().getMessage("getApplicationNotingDetail"),
							new Class[] { Long.class, Long.class, Long.class });
					listDto = (List<SiteAffectedDTO>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
							new Object[] { applicationId, orgId, dto.getScrutinyLabelValues().get(0).getLevels() });

					if (!listDto.isEmpty()) {
						dto.setSiteAffectedDTO(listDto);
						dto.setSiteCfcAttachment(iChecklistVerificationService.getDocumentUploadedByRefNo(
								applicationId + MainetConstants.WINDOWS_SLASH +"S"+dto.getTaskId(),
								UserSession.getCurrent().getOrganisation().getOrgid()));
					}
				} catch (Exception e) {
					LOG.error("Exception occur in Site getApplicationNotingDetail ", e);
				}

				try {
					Class<?> clazz = null;
					Object dynamicServiceInstance = null;
					String serviceClassName = null;
					serviceClassName = "com.abm.mainet.buildingplan.service.SiteAffecServiceImpl";
					clazz = ClassUtils.forName(serviceClassName,
							ApplicationContextProvider.getApplicationContext().getClassLoader());
					dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
							.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
					final Method method = ReflectionUtils.findMethod(clazz,
							ApplicationSession.getInstance().getMessage("getApplicationNotingDetailLicense"),
							new Class[] { Long.class, Long.class, Long.class });
					licenseGrantedDTO = (List<LicenseGrantedDTO>) ReflectionUtils.invokeMethod(method,
							dynamicServiceInstance,
							new Object[] { applicationId, orgId, dto.getScrutinyLabelValues().get(0).getLevels() });

					if (!licenseGrantedDTO.isEmpty()) {
						dto.setListLicenseDto(licenseGrantedDTO);
					}
				} catch (Exception e) {
					LOG.error("Exception occur in getApplicationNotingDetailLicense ", e);
				}
				dto.setjECfcAttachment(iChecklistVerificationService.getDocumentUploadedByRefNo(
						applicationId + MainetConstants.WINDOWS_SLASH +"J"+ dto.getTaskId(),
						UserSession.getCurrent().getOrganisation().getOrgid()));

				dto.setLaoCfcAttachment(iChecklistVerificationService.getDocumentUploadedByRefNo(
						applicationId + MainetConstants.WINDOWS_SLASH +"VF"+ dto.getTaskId(),
						UserSession.getCurrent().getOrganisation().getOrgid()));
				if (!dto.getLaoCfcAttachment().isEmpty()) {
					String remark = null;
					try {
						Class<?> clazz = null;
						Object dynamicServiceInstance = null;
						String serviceClassName = null;
						serviceClassName = "com.abm.mainet.buildingplan.service.NewLicenseFormServiceImpl";
						clazz = ClassUtils.forName(serviceClassName,
								ApplicationContextProvider.getApplicationContext().getClassLoader());
						dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
								.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
						final Method method = ReflectionUtils.findMethod(clazz,
								ApplicationSession.getInstance().getMessage("getLaoRemark"),
								new Class[] { Long.class, Long.class });
						remark = (String) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
								new Object[] { applicationId, orgId });
						dto.setLaoRemark(remark);
					} catch (Exception e) {
						LOG.error("Exception occur in get Loa Remark ", e);
					}
				}
			}
			notingList.add(dto);
		}
        scrutinyLabelDTO.setNotingList(notingList);
	}

	private Map<Long, List<ViewCFCScrutinyLabelValue>> filterListAsPerDesignation(
			final ScrutinyLabelDTO scrutinyLabelDTO, final List<ViewCFCScrutinyLabelValue> cfcScrutinyLabelValues,
			final int langId, final long orgId) {

		final Map<Long, String> desgNameMap = new HashMap<>(0);

		final Map<Long, List<ViewCFCScrutinyLabelValue>> desgLabelMap = new LinkedHashMap<>(0);

		List<ViewCFCScrutinyLabelValue> viewCFCScrutinyLabelValues = null;

		for (final ViewCFCScrutinyLabelValue viewCFCScrutinyLabelValue : cfcScrutinyLabelValues) {
			viewCFCScrutinyLabelValues = null;
			if (desgLabelMap.containsKey(viewCFCScrutinyLabelValue.getSlDsgid())) {
				viewCFCScrutinyLabelValues = desgLabelMap.get(viewCFCScrutinyLabelValue.getSlDsgid());
			}
			if (viewCFCScrutinyLabelValues == null) {
				viewCFCScrutinyLabelValues = new ArrayList<>();
			}
			viewCFCScrutinyLabelValues.add(viewCFCScrutinyLabelValue);

			desgLabelMap.put(viewCFCScrutinyLabelValue.getSlDsgid(), viewCFCScrutinyLabelValues);

			if ((viewCFCScrutinyLabelValue.getSlDsgid() != null)
					&& !desgNameMap.containsKey(viewCFCScrutinyLabelValue.getSlDsgid())) {

				final GroupMaster gmMaster = scrutinyDAO.getDesignationName(viewCFCScrutinyLabelValue.getSlDsgid());
				if (gmMaster != null) {
					if (langId == 1) {
						desgNameMap.put(viewCFCScrutinyLabelValue.getSlDsgid(), gmMaster.getGrDescEng());
					} else {
						desgNameMap.put(viewCFCScrutinyLabelValue.getSlDsgid(), gmMaster.getGrDescReg());
					}
				}
			}
		}

		scrutinyLabelDTO.setDesgNameMap(desgNameMap);

		scrutinyLabelDTO.setDsgWiseScrutinyDocMap(
				getAllDesgWiseScrutinyDoc(Long.parseLong(scrutinyLabelDTO.getApplicationId()), orgId));

		final List<String> list = new ArrayList<>();
		list.add(MainetConstants.YESL);
		list.add(MainetConstants.NOL);
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TCP)) 
		list.add(MainetConstants.CommonConstants.NA);
		scrutinyLabelDTO.setDislist(list);
		
		final List<String> decisionlist = new ArrayList<>();
		decisionlist.add("In Order");
		decisionlist.add("Not In Order");
		decisionlist.add("In Order With Conditions");
		scrutinyLabelDTO.setDecisionList(decisionlist);

		return desgLabelMap;
	}

	@Transactional(readOnly = true)
	public Map<Long, List<LookUp>> getAllDesgWiseScrutinyDoc(final long applId, final long orgId) {
		final Map<Long, List<LookUp>> desWiseScrutinyDocMap = new HashMap<>(0);

		LookUp lookUp = null;

		List<LookUp> lookUps = null;

		final List<CFCAttachment> docs = scrutinyDAO.getAllScrutinyDocDesgWise(applId, orgId);
		if (docs != null) {
			for (final CFCAttachment doc : docs) {
				lookUp = new LookUp();

				lookUp.setDescLangFirst(doc.getAttPath());
				lookUp.setDescLangSecond(doc.getAttPath());
				lookUp.setLookUpCode(doc.getAttFname());
				lookUp.setLookUpId(doc.getAttId());
				lookUp.setOtherField(doc.getClmRemark());
				lookUp.setLookUpType(doc.getDmsDocId());

				if (desWiseScrutinyDocMap.containsKey(doc.getGmId())) {
					lookUps = desWiseScrutinyDocMap.get(doc.getGmId());

					lookUps.add(lookUp);

					desWiseScrutinyDocMap.put(doc.getGmId(), lookUps);
				} else {
					lookUps = new ArrayList<>(0);

					lookUps.add(lookUp);

					desWiseScrutinyDocMap.put(doc.getGmId(), lookUps);
				}
			}
		}
		return desWiseScrutinyDocMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.cfc.scrutiny.service.ScrutinyService#
	 * saveScrutinyValueBylabelId(com.abm.mainetservice.web.common.bean.
	 * ScrutinyLableValueDTO)
	 */
	@Override
	@Transactional
	public boolean saveScrutinyValueBylabelId(final ScrutinyLableValueDTO lableValueDTO) {
		boolean flag = false;
		LOG.info("Start the saveScrutinyValueBylabelId ");
		try {

			if ((lableValueDTO.getApplicationId() != null) && (lableValueDTO.getApplicationId() != 0)
					&& (lableValueDTO.getLableId() != null) && (lableValueDTO.getLableId() != 0)) {
				TbScrutinyLabelValueEntity entity = null;
				final TbScrutinyLabelValueEntityKey key = new TbScrutinyLabelValueEntityKey();
				final Employee employee = new Employee();
				employee.setEmpId(lableValueDTO.getUserId());
				final Organisation organisation = new Organisation();
				organisation.setOrgid(lableValueDTO.getOrgId());
				entity = labelValueEntityJpaRepository.findScrutinyLabelValueData(lableValueDTO.getLableId(),
						lableValueDTO.getApplicationId(), lableValueDTO.getOrgId());

				if (entity == null) {
					entity = new TbScrutinyLabelValueEntity();
					key.setSaApplicationId(lableValueDTO.getApplicationId());
					key.setSlLabelId(lableValueDTO.getLableId());
					entity.setLabelValueKey(key);
					entity.setLangId(lableValueDTO.getLangId().intValue());
					entity.setOrgId(organisation);
					entity.setUserId(employee);
					entity.setLevels(lableValueDTO.getLevel());
					entity.setSvValue(lableValueDTO.getLableValue());
					if(lableValueDTO.getResolutionComments()!=null)
					entity.setResolutionComments(lableValueDTO.getResolutionComments());
					entity.setLmodDate(new Date());
				} else {
					if(lableValueDTO.getLableValue()!=null)
					entity.setSvValue(lableValueDTO.getLableValue());
					if(lableValueDTO.getResolutionComments()!=null)
					entity.setResolutionComments(lableValueDTO.getResolutionComments());
					entity.setUpdatedBy(employee);
					entity.setUpdatedDate(new Date());
				}

				final TbScrutinyLabelValueEntity upentity = labelValueEntityJpaRepository.save(entity);

				if (upentity != null) {
					if (upentity.getSlLabelId() != null) {

						flag = true;
					}
				}

			}

		} catch (final Exception exception) {
			LOG.error("Exception occur in saveScrutinyValueBylabelId() ", exception);
			return flag;
		}
		return flag;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.cfc.scrutiny.service.ScrutinyService#
	 * saveCompleteScrutinyLabel(com.abm.mainetservice.web.common.entity.
	 * TbScrutinyLabelValueEntity)
	 */
	@Override
	@Transactional
	public boolean saveCompleteScrutinyLabel(final List<ViewCFCScrutinyLabelValue> list, final UserSession userSession,
			final ScrutinyLabelDTO scrutinyLabelDTO, final boolean updateFlag, Long taskId, List<Long> attachmentId,
			String wokflowDecision, String remark) {
		boolean flag = false;
		LOG.info("Start the saveScrutinyValueBylabelId ");
		TbScrutinyLabelValueEntity scrutinyLabelValue = null;
		TbScrutinyLabelValueEntityKey scrutinyLabelValueKey = null;
		final Date date = new Date();

		try {
			if ((list != null) && (!list.isEmpty())) {
			for (final ViewCFCScrutinyLabelValue viewCFCScrutinyLabelValue : list) {
				scrutinyLabelValue = new TbScrutinyLabelValueEntity();

				scrutinyLabelValueKey = new TbScrutinyLabelValueEntityKey();

				if ((viewCFCScrutinyLabelValue.getSaApplicationId() != null)
						&& (viewCFCScrutinyLabelValue.getSaApplicationId() != 0)) {
					scrutinyLabelValueKey.setSaApplicationId(viewCFCScrutinyLabelValue.getSaApplicationId());

				} else {
					scrutinyLabelValueKey.setSaApplicationId(viewCFCScrutinyLabelValue.getApplicationId());

				}

				scrutinyLabelValueKey.setSlLabelId(viewCFCScrutinyLabelValue.getSlLabelId());
				scrutinyLabelValue.setLabelValueKey(scrutinyLabelValueKey);
				scrutinyLabelValue.setLevels(viewCFCScrutinyLabelValue.getLevels());
				scrutinyLabelValue.setSvValue(viewCFCScrutinyLabelValue.getSvValue());
				scrutinyLabelValue.setLangId(userSession.getLanguageId());
				scrutinyLabelValue.setLmodDate(date);
				scrutinyLabelValue.setOrgId(userSession.getOrganisation());
				scrutinyLabelValue.setUpdatedBy(userSession.getEmployee());
				scrutinyLabelValue.setUpdatedDate(date);
				scrutinyLabelValue.setUserId(userSession.getEmployee());
				if(viewCFCScrutinyLabelValue.getResolutionComments()!=null)
				scrutinyLabelValue.setResolutionComments(viewCFCScrutinyLabelValue.getResolutionComments());
				if(scrutinyLabelDTO.getTaskId()!=null)
				scrutinyLabelValue.setTaskId(scrutinyLabelDTO.getTaskId());
				labelValueEntityJpaRepository.save(scrutinyLabelValue);
				TbScrutinyLabelValueHistEntity scrutinyDetailHistEntity = new TbScrutinyLabelValueHistEntity();
				BeanUtils.copyProperties(scrutinyLabelValue, scrutinyDetailHistEntity);
				scrutinyDetailHistEntity.sethStatus(MainetConstants.FlagA);
				scrutinyDetailHistEntity.setSlLabelId(viewCFCScrutinyLabelValue.getSlLabelId());
				auditService.createHistoryForObject(scrutinyDetailHistEntity);

			}
		}
			if (updateFlag) {
				final Organisation orgid = UserSession.getCurrent().getOrganisation();
				
				String shortCode = serviceMasterService
						.fetchServiceShortCode(scrutinyLabelDTO.getSmServiceId(), orgid.getOrgid());
				String processName = serviceMasterService
						.getProcessName(Long.valueOf(scrutinyLabelDTO.getSmServiceId()), orgid.getOrgid());
				if (processName != null) {
					final List<TbLoiMas> mst = loiMasService.getloiByApplicationId(
							Long.valueOf(scrutinyLabelDTO.getApplicationId()),
							Long.valueOf(scrutinyLabelDTO.getSmServiceId()), userSession.getOrganisation().getOrgid());

					final Employee empId = UserSession.getCurrent().getEmployee();
					UserTaskDTO userTaskdto = iWorkflowTaskService.findByTaskId(taskId);
					WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
					WorkflowTaskAction workflowAction = new WorkflowTaskAction();
					workflowAction.setIsLoiGenerated(false);
					workflowAction.setApplicationId(Long.valueOf(scrutinyLabelDTO.getApplicationId()));
					
					if (Utility.isEnvPrefixAvailable(userSession.getOrganisation(), MainetConstants.ENV_TCP)) {
						wokflowDecision = setValuesForScrutinyInChecker(taskId, wokflowDecision, userTaskdto,
								workflowAction, mst);
						
					}else{
						if (mst != null && !mst.isEmpty()) {
							workflowAction.setIsLoiGenerated(true);
							workflowAction.setLoiAmount(mst.get(0).getLoiAmount().doubleValue());
						}
					}
					if(null != userTaskdto.getReferenceId()){
						workflowAction.setReferenceId(userTaskdto.getReferenceId());
					}
					workflowAction.setTaskId(taskId);
					
					workflowAction.setDateOfAction(new Date());
					workflowAction.setDecision(wokflowDecision);
					workflowAction.setOrgId(orgid.getOrgid());
					workflowAction.setEmpId(empId.getEmpId());
					workflowAction.setModifiedBy(empId.getEmpId());
					workflowAction.setEmpType(empId.getEmplType());
					workflowAction.setEmpName(empId.getEmpname());
					workflowAction.setCreatedBy(empId.getEmpId());
					workflowAction.setCreatedDate(new Date());
					workflowAction.setAttachementId(attachmentId);
					workflowAction.setComments(remark);
					workflowdto.setProcessName(processName);
					workflowdto.setWorkflowTaskAction(workflowAction);
					workflowExecutionService.updateWorkflow(workflowdto);
					flag = true;

				}
				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && MainetConstants.RoadCuttingConstant.RCP.equals(shortCode) && MainetConstants.WorkFlow.Decision.REJECTED.equals(wokflowDecision)){
				List<WorkflowProcessParameter> workflowProcessParameters=(List<WorkflowProcessParameter>) userSession.getObjectMap().get("RejectTask");
				for (WorkflowProcessParameter workflowProcessParameter : workflowProcessParameters) {
					workflowExecutionService.updateWorkflow(workflowProcessParameter);
				}
				}
			}
		}
		
		catch (final Exception exception) {
			LOG.error("Exception occur while calling jbpm workflow in saveCompleteScrutinyLabel() ", exception);
			return flag;
		}
		return flag;

	}

	private String setValuesForScrutinyInChecker(Long taskId, String wokflowDecision, UserTaskDTO userTaskdto,
			WorkflowTaskAction workflowAction, List<TbLoiMas> mst) {//This method is written to set values while Approved decision because we are putting Scrutiny for in maker checker process
		boolean isLastAuthorizer = iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId);
		if (isLastAuthorizer) {
			workflowAction.setIsFinalApproval(true);
			workflowAction.setIsObjectionAppealApplicable(false);
			if (mst != null && !mst.isEmpty()) {
				List<LoiDetail> loiDetList = new ArrayList<>();
	            LoiDetail loidet = new LoiDetail();
	            loidet.setLoiNumber(mst.get(0).getLoiNo());
	            loidet.setLoiPaymentApplicable(false);
	            loidet.setIsComplianceApplicable(false);
	            loidet.setIsApprovalLetterGenerationApplicable(false);
	            loiDetList.add(loidet);
	            workflowAction.setLoiDetails(loiDetList);
			}
		} else {
			workflowAction.setIsFinalApproval(false);
			workflowAction.setIsObjectionAppealApplicable(false);
		}
		List<Object[]> ddzList = workFlowTypeRepository.findKhrsColumnsByApplicationNoAndOrgId(userTaskdto.getApplicationId(), userTaskdto.getOrgId());
		for (Object[] ddz : ddzList) {
			workflowAction.setCodIdOperLevel1(ddz.length > 0 ? Long.valueOf(ddz[0].toString())  : null);
			workflowAction.setCodIdOperLevel2(ddz.length > 1 ? Long.valueOf(ddz[1].toString()) : null);
			workflowAction.setCodIdOperLevel3(ddz.length > 2 ? Long.valueOf(ddz[2].toString()) : null);
			workflowAction.setCodIdOperLevel4(ddz.length > 3 ? Long.valueOf(ddz[3].toString()) : null);
		}
		if (MainetConstants.WorkFlow.Decision.FORWARD_TO_DEPARTMENT.equals(wokflowDecision)) {
			wokflowDecision = MainetConstants.WorkFlow.Decision.APPROVED;
		}
		if(null != userTaskdto.getReferenceId()){
			workflowAction.setApplicationId(null);
		}
		return wokflowDecision;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.cfc.scrutiny.service.ScrutinyService#
	 * saveAllScrutinyDoc(java.util.List)
	 */
	@Override
	@Transactional
	public boolean saveAllScrutinyDoc(final List<CFCAttachment> scrutinyDocs) {
		boolean flag = false;
		LOG.info("Start the saveAllScrutinyDoc ");
		try {

			for (final CFCAttachment entity : scrutinyDocs) {
				scrutinyDocJpaRepository.save(entity);
			}
			flag = true;
		} catch (final Exception exception) {
			LOG.error("Exception occur in saveAllScrutinyDoc() ", exception);
			return flag;
		}
		return flag;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.web.cfc.scrutiny.service.ScrutinyService#
	 * getValueByLabelQuery(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public String getValueByLabelQuery(final String query) {

		return scrutinyDAO.getValueByLabelQuery(query);
	}

	// US#113590
	@Override
	@WebMethod(exclude = true)
	public Map<String, Long> getApplicationDetails(Long applicatioId) {
		Map<String, Long> result = null;
		Class<?> clazz = null;
		String serviceClassName = null;
		String deptCode = null;
		Object dynamicServiceInstance = null;
		try {
			serviceClassName = "com.abm.mainet.tradeLicense.service.TradeLicenseApplicationServiceImpl";
			if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {

				clazz = ClassUtils.forName(serviceClassName,
						ApplicationContextProvider.getApplicationContext().getClassLoader());
				dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
						.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
				final Method method = ReflectionUtils.findMethod(clazz, "getItemDetailsByApplicationId",
						new Class[] { Long.class });
				result = (Map<String, Long>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
						new Object[] { applicatioId });
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception in Objection for finding reference Number  :" + applicatioId, e);
		}
		return result;
	}
	
	@Override
	@WebMethod(exclude = true)
	public Map<String, Long> getWaterApplicationDetails(Long applicatioId) {
		Map<String, Long> result = null;
		Class<?> clazz = null;
		String serviceClassName = null;
		String deptCode = null;
		Object dynamicServiceInstance = null;
		try {
			serviceClassName = "com.abm.mainet.water.service.NewWaterConnectionServiceImpl";
			if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {

				clazz = ClassUtils.forName(serviceClassName,
						ApplicationContextProvider.getApplicationContext().getClassLoader());
				dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
						.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
				final Method method = ReflectionUtils.findMethod(clazz, "getMeterData",
						new Class[] { Long.class });
				result = (Map<String, Long>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
						new Object[] { applicatioId });
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception in Objection for finding reference Number  :" + applicatioId, e);
		}
		return result;
	}
	

	@Override
	public Boolean checkLoiGeneratedOrNot(ScrutinyLabelDTO dto, Long desgId,
			List<ViewCFCScrutinyLabelValue> labelList) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		TbScrutinyLabelsEntity tbScrtEnt = null;
		if (!CollectionUtils.isEmpty(labelList)) {
			Boolean flag = false;
			for (ViewCFCScrutinyLabelValue vwScrLblVal : labelList) {
				if (vwScrLblVal.getSlFormName() != null
						&& vwScrLblVal.getSlFormName().equals(MainetConstants.SMS_EMAIL_URL.LOI_GENERATION)) {
					flag = true;
					final ServiceMaster servicename = serviceMasterService.getServiceMaster(dto.getSmServiceId(),
							orgId);
					if (dto != null) {
						if (ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
								.getDeptCode(servicename.getTbDepartment().getDpDeptid())
								.equals(MainetConstants.TradeLicense.MARKET_LICENSE)
								&& (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
										MainetConstants.ENV_SKDCL) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
												MainetConstants.ENV_PSCL))) {
							Map<String, Long> result = getApplicationDetails(Long.valueOf(dto.getApplicationId()));
							if (result != null && result.get(MainetConstants.TRI_COD1) != null) {
								List<TbScrutinyLabelsEntity> scrEntList = labelsJpaRepository
										.checkScrutinyLabelExistOrNot(dto.getSmServiceId(), orgId,
												result.get(MainetConstants.TRI_COD1));
								if (scrEntList != null && !scrEntList.isEmpty()) {
									tbScrtEnt = labelsJpaRepository.checkLoiGenerationExistOrNotForSkdcl(
											dto.getSmServiceId(), orgId, desgId, result.get(MainetConstants.TRI_COD1));
								} else {
									tbScrtEnt = labelsJpaRepository.checkLoiGenerationExistOrNotForSkdcl(
											dto.getSmServiceId(), orgId, desgId, 0L);
								}
							}
						} else {
							tbScrtEnt = labelsJpaRepository.checkLoiGenerationExistOrNot(dto.getSmServiceId(), orgId,
									desgId);
						}
						if (tbScrtEnt != null) {
							TbLoiMas mas = loiMasService.getloiByApplicationIdForDeletion(
									Long.valueOf(dto.getApplicationId()), orgId.longValue());
							if (mas != null) {
								return true;
							}
						}
					}
				}
			}
			if (!flag) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean checkMeteredOrNot(ScrutinyLabelDTO dto, Long desgId,
			List<ViewCFCScrutinyLabelValue> labelList) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		TbScrutinyLabelsEntity tbScrtEnt = null;
		if (!CollectionUtils.isEmpty(labelList)) {
			Boolean flag = false;
			for (ViewCFCScrutinyLabelValue vwScrLblVal : labelList) {
				if (vwScrLblVal.getSlFormName() != null
						&& vwScrLblVal.getSlFormName().equals("MeterDetailsConnectionForm.html")) {
					flag = true;
					if (dto != null) {
							tbScrtEnt = labelsJpaRepository.checkMeteredExistOrNot(dto.getSmServiceId(), orgId,
									desgId);
						if (tbScrtEnt != null) {
							Map<String, Long> result = getWaterApplicationDetails(Long.valueOf(dto.getApplicationId()));
							
							if (result != null && result.get("CsMeteredccn") != null) {
								return true;
							}
						}
					}
				}
			}
			if (!flag) {
				return true;
			}
		}
		return false;
	}
	
	
	
	// 105334 - to get license end date by ApplicationId
	@Override
	@WebMethod(exclude = true)
	public String getAppDetailByAppId(Long applicationId) {

		String result = null;
		Class<?> clazz = null;
		String serviceClassName = null;
		Object dynamicServiceInstance = null;
		try {
			serviceClassName = "com.abm.mainet.tradeLicense.service.TradeLicenseApplicationServiceImpl";
			if (serviceClassName != null && !MainetConstants.BLANK.equals(serviceClassName)) {

				clazz = ClassUtils.forName(serviceClassName,
						ApplicationContextProvider.getApplicationContext().getClassLoader());
				dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
						.getAutowireCapableBeanFactory().autowire(clazz, 4, false);
				final Method method = ReflectionUtils.findMethod(clazz, "getLicenseDetailsByApplId",
						new Class[] { Long.class });
				result = (String) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
						new Object[] { applicationId });
			}
		} catch (Exception e) {
			throw new FrameworkException("Exception in Objection for finding reference Number  :" + applicationId, e);
		}
		return result;

	}
	@Override
	@WebMethod(exclude = true)
	@Transactional
	public boolean updateStatusFlagByRefId(String refId, Long orgId,String deptCode,Long empId) {
		boolean flag=false;
		try{
        	Class<?> clazz = null;
            Object dynamicServiceInstance = null;
        	String serviceClassName =  messageSource.getMessage(
					ApplicationSession.getInstance().getMessage("OBJECTION.") + deptCode, new Object[] {},
					StringUtils.EMPTY, Locale.ENGLISH);
            clazz = ClassUtils.forName(serviceClassName, ApplicationContextProvider.getApplicationContext()
                    .getClassLoader());
            final Method method = ReflectionUtils.findMethod(clazz, "updateStatusFlagByRefId",
					new Class[] {String.class, Long.class,Long.class });
            
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
                    .autowire(clazz, 4, false);
            
            flag =  (boolean) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
                    new Object[] { refId, orgId ,empId});
        } catch (LinkageError | Exception e) {
        	LOG.info("Exception occured while fetching csIdn from applicationId " + e.getMessage());
        }
		return flag;
	
}
	
	
	@Override
	public String getGroupValue(Long levels,Long slLabelId,Long smServiceId,Long orgId) {
		 String group =labelsJpaRepository.getGroupValue(levels,slLabelId,smServiceId,orgId);
		return group;
	}
	
	@Override
	public List<CFCAttachment> getDocumentUploadedListByGroupId(String groupValue,Long applicationId) {
		 List<CFCAttachment> docs=scrutinyDocJpaRepository.getDocumentUploadedListByGroupId(groupValue,applicationId);
		return docs;
	}
	
}
