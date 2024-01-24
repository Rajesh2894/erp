package com.abm.mainet.cfc.loi.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.cfc.loi.domain.DishonourChargeEntity;
import com.abm.mainet.cfc.loi.repository.DishonurChargeEntryRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.repository.WorkflowMappingRepository;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;


@Service
public class DishonourChargeEntryServiceImpl implements DishonourChargeEntryService {

	private static final Logger LOGGER = Logger.getLogger(DishonourChargeEntryServiceImpl.class);

	@Autowired
	private IWorkflowExecutionService iWorkflowExecutionService;
	@Autowired
	private IEmployeeService empService;

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private DishonurChargeEntryRepository disHonourRepo;
	@Autowired
	private WorkflowMappingRepository workFlowTypeRepository;

	@Autowired
	private MessageSource messageSource;
	@Autowired
	IOrganisationService iOrganisationService;
	@Resource
	private TbTaxMasService taxMasService;
	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Override
	public Boolean saveDishonourCharge(Long appId, Long orgId, Long empId, BigDecimal amt, String remark) {
		Organisation organisation = iOrganisationService.getOrganisationById(orgId);

		DishonourChargeEntity entity = new DishonourChargeEntity();
		entity.setApmApplicationId(appId);
		entity.setCreatedBy(empId);
		entity.setCreatedDate(new Date());
		entity.setDisHnAm(amt);
		entity.setOrgid(orgId);
		entity.setIsDishnrChgPaid(MainetConstants.FlagN);
		entity.setDisHnRemarks(remark);
		Object[] userTask = null;
		String processName = null;
		EmployeeBean emp = empService.findById(empId);
		List<Object[]> userTaskList = workFlowTypeRepository.getTaskIdByAppId(appId);
		if (CollectionUtils.isNotEmpty(userTaskList) && userTaskList.size() > 1) {
			userTask = userTaskList.get(userTaskList.size() - 1);
		} else {
			userTask = userTaskList.get(0);
		}

		ServiceMaster servMast = serviceMaster.getServiceMaster(Long.parseLong(userTask[1].toString()), orgId);
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
				PrefixConstants.NewWaterServiceConstants.CAA, organisation);
		LookUp lookUp1 = CommonMasterUtility.getHieLookupByLookupCode("CDC", "TAC", 2, orgId);
		List<TbTaxMasEntity> taxesMaster = null;
		Long deptId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER);
		try {
			// fetching dishonur tax details by deptId ,orgid and subcategoryId
			taxesMaster = taxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(orgId, deptId,
					chargeApplicableAt.getLookUpId(), lookUp1.getLookUpId());
			entity.setTaxId(taxesMaster.get(0).getTaxId());
		} catch (Exception e) {
			LOGGER.error("Exception at the time of fetching Tax master data ");
		}
		if (servMast != null) {
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(servMast.getSmProcessId(), orgId,
					PrefixConstants.LookUp.BPT);
			processName = lookUp.getLookUpCode();
			try {
				updateModuleWiseStatus(appId, servMast.getSmServiceId(), servMast.getSmShortdesc(), orgId);
			} catch (Exception e) {
				LOGGER.error("Exception at the time of Update status ");
			}
		}
		disHonourRepo.save(entity);
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(orgId);
		taskAction.setEmpId(empId);
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(empId);
		if (emp != null) {
			taskAction.setEmpName(emp.getEmpname());
			taskAction.setEmpEmail(emp.getEmpemail());
			taskAction.setEmpType(emp.getEmplType());
		}
		taskAction.setPaymentMode(MainetConstants.FlagY);
		taskAction.setApplicationId(appId);
		taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);

		taskAction.setIsFinalApproval(true);
		if (servMast.getSmCheckListReq().equals("Y")) {
			taskAction.setDecision(MainetConstants.WorkFlow.Decision.REJECTED);
			taskAction.setPaymentMode(null);
			taskAction.setIsFinalApproval(null);
		}
		taskAction.setTaskId(Long.parseLong(userTask[0].toString()));

		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		// #129656 BPM Process as maker-checker for all
		workflowProcessParameter.setProcessName(processName.toLowerCase());

		workflowProcessParameter.setWorkflowTaskAction(taskAction);
		try {
			iWorkflowExecutionService.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Boolean updateModuleWiseStatus(final Long applicationId, final Long serviceId, final String serviceShortDesc,
			final long orgId) throws ClassNotFoundException, LinkageError {
		Boolean flag = null;
		Class<?> clazz = null;
		Object dynamicServiceInstance = null;
		String serviceClassName = null;
		serviceClassName = messageSource.getMessage(serviceShortDesc, new Object[] {}, StringUtils.EMPTY,
				Locale.ENGLISH);

		clazz = ClassUtils.forName(serviceClassName,
				ApplicationContextProvider.getApplicationContext().getClassLoader());

		dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
				.autowire(clazz, 4, false);
		final Method method = ReflectionUtils.findMethod(clazz,
				ApplicationSession.getInstance().getMessage("cfc.update.status"),
				new Class[] { Long.class, Long.class });
		flag = (Boolean) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
				new Object[] { applicationId, orgId });
		return flag;
	}

}
