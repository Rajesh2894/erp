package com.abm.mainet.common.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.TbWorkOrderDetailEntity;
import com.abm.mainet.common.domain.TbWorkOrderEntity;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.dto.TbWorkOrder;
import com.abm.mainet.common.dto.TbWorkOrderDetail;
import com.abm.mainet.common.dto.WorkOrderGridEntityList;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.PlumberHoleManDTO;
import com.abm.mainet.common.integration.dto.PlumberMasterDTO;
import com.abm.mainet.common.mapper.TbWorkOrderDetailServiceMapper;
import com.abm.mainet.common.mapper.TbWorkOrderServiceMapper;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.repository.TbWorkOrderDetailJpaRepository;
import com.abm.mainet.common.repository.TbWorkOrderJpaRepository;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

/**
 * Implementation of TbWorkOrderService
 */
@Service
@Transactional
public class TbWorkOrderServiceImpl implements TbWorkOrderService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(TbWorkOrderServiceImpl.class);

    @Resource
    private TbWorkOrderJpaRepository tbWorkOrderJpaRepository;

    @Resource
    private TbWorkOrderServiceMapper tbWorkOrderServiceMapper;

    @Resource
    private TbWorkOrderDetailJpaRepository tbWorkOrderDetailJpaRepository;

    @Resource
    private TbWorkOrderDetailServiceMapper TbWorkOrderDetailServiceMapper;

    @Resource
    private TbRejectionMstServiceMapper tbRejectionMstServiceMapper;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private IWorkflowExecutionService workflowExecutionService;
    
    @Resource
    private TbDepartmentService tbDepartmentService;


    @Autowired
    private ServiceMasterService serviceMasterService;
    
    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public TbWorkOrder findById(final Long woId) {
        final TbWorkOrderEntity tbWorkOrderEntity = tbWorkOrderJpaRepository.findOne(woId);
        return tbWorkOrderServiceMapper.mapTbWorkOrderEntityToTbWorkOrder(tbWorkOrderEntity);
    }

    @Override
    public TbWorkOrder save(final TbWorkOrder tbWorkOrder) {
        return update(tbWorkOrder);
    }

    @Override
    public TbWorkOrder create(final TbWorkOrder tbWorkOrder) {
        String WPCcode = "WO";
        String FinancialYear = null;
        String workorderNo;
        final Long EmpId = UserSession.getCurrent().getEmployee().getEmpId();
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long LanguageId = Long.valueOf(UserSession.getCurrent().getLanguageId());
        final Long WoServiceId = tbWorkOrder.getWoServiceId();
        final Long WoApplicationId = tbWorkOrder.getWoApplicationId();
        final Long woId = seqGenFunctionUtility.generateSequenceNo("COM", "TB_WORK_ORDER", "WO_ID", orgid, null, null);
        final Long WoDeptId = tbWorkOrder.getWoDeptId();
        final String MacAddress = UserSession.getCurrent().getEmployee().getEmppiservername();
        final TbWorkOrderEntity tbWorkOrderEntity = new TbWorkOrderEntity();
        final TbDepartment dept = tbDepartmentService.findById(WoDeptId);
        
        tbWorkOrderServiceMapper.mapTbWorkOrderToTbWorkOrderEntity(tbWorkOrder, tbWorkOrderEntity);
        final TbWorkOrderDetailEntity tbWorkOrderDetailEntity = new TbWorkOrderDetailEntity();
        tbWorkOrderEntity.setLmoddate(new Date());
        tbWorkOrderEntity.setUpdatedDate(new Date());
        tbWorkOrderEntity.setWoOrderDate(new Date());
        tbWorkOrderEntity.setUserId(EmpId);
        tbWorkOrderEntity.setOrgid(orgid);
        tbWorkOrderEntity.setLangId(LanguageId);
        tbWorkOrderEntity.setLgIpMac(MacAddress);
        tbWorkOrderEntity.setWoId(woId);
        tbWorkOrderEntity.setWoDeptId(WoDeptId);
       /* final List<LookUp> lookUpList = CommonMasterUtility.getListLookup("WPC", UserSession.getCurrent().getOrganisation());
        for (final LookUp Lookup1 : lookUpList) {

            if (Lookup1.getLookUpId() == tbWorkOrder.getWoAllocation().longValue()) {
                WPCcode = Lookup1.getLookUpCode();
            }
        }*/
        try {
            FinancialYear = Utility.getFinancialYearFromDate(new Date());
        } catch (final Exception e) {

            LOGGER.error("error in getFinyear" + e);
        }

        workorderNo = WPCcode + MainetConstants.operator.FORWARD_SLACE + dept.getDpDeptcode().toUpperCase() + MainetConstants.operator.FORWARD_SLACE + FinancialYear + MainetConstants.operator.FORWARD_SLACE
                +  String.format("%04d", woId);
        tbWorkOrderEntity.setWoOrderNo(workorderNo);

        final TbWorkOrderEntity tbWorkOrderEntitySaved = tbWorkOrderJpaRepository.save(tbWorkOrderEntity);
        for (final TbApprejMas WorkOrderRemark : tbWorkOrder.getWorkOrderRemarklist()) {
            tbWorkOrderDetailEntity.setWdApplicationId(WoApplicationId);
            tbWorkOrderDetailEntity.setWdServiceId(WoServiceId);
            tbWorkOrderDetailEntity.setTbWorkOrder(tbWorkOrderEntity);
            tbWorkOrderDetailEntity.setLmoddate(new Date());
            tbWorkOrderDetailEntity.setUpdatedDate(new Date());
            tbWorkOrderDetailEntity.setUserId(EmpId);
            tbWorkOrderDetailEntity.setOrgid(orgid);
            tbWorkOrderDetailEntity.setLangId(LanguageId);
            tbWorkOrderDetailEntity.setLgIpMac(MacAddress);
            tbWorkOrderDetailEntity.setWdRemarkId(WorkOrderRemark.getIsSelected());
            final Long woDId = seqGenFunctionUtility.generateSequenceNo("COM", "TB_WORK_ORDER_DETAIL", "WD_ID", orgid, null,
                    null);
            tbWorkOrderDetailEntity.setWdId(woDId);
            tbWorkOrderDetailJpaRepository.save(tbWorkOrderDetailEntity);
        }

        String processName = serviceMasterService.getProcessName(WoServiceId,
                orgid);
        if (processName != null) {
            final Employee employee = UserSession.getCurrent().getEmployee();
            WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
            WorkflowTaskAction workflowAction = new WorkflowTaskAction();
            workflowAction.setTaskId(tbWorkOrder.getTaskId());
            workflowAction.setApplicationId(WoApplicationId);
            workflowAction.setDateOfAction(new Date());
            workflowAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
            workflowAction.setOrgId(orgid);
            workflowAction.setEmpId(EmpId);
            workflowAction.setModifiedBy(EmpId);
            workflowAction.setEmpType(employee.getEmplType());
            workflowAction.setEmpName(employee.getEmpname());
            workflowAction.setCreatedBy(EmpId);
            workflowAction.setCreatedDate(new Date());
            workflowdto.setProcessName(processName);
            workflowdto.setWorkflowTaskAction(workflowAction);
            try {
                workflowExecutionService.updateWorkflow(workflowdto);
            } catch (final Exception e) {
                throw new FrameworkException("Exception in work order generation for jbpm workflow : " + e.getMessage(),
                        e);
            }
        }

        return tbWorkOrderServiceMapper.mapTbWorkOrderEntityToTbWorkOrder(tbWorkOrderEntitySaved);
    }

    @Override
    public TbWorkOrder update(final TbWorkOrder tbWorkOrder) {
        final TbWorkOrderEntity tbWorkOrderEntity = tbWorkOrderJpaRepository.findOne(tbWorkOrder.getWoId());
        tbWorkOrderServiceMapper.mapTbWorkOrderToTbWorkOrderEntity(tbWorkOrder, tbWorkOrderEntity);
        final TbWorkOrderEntity tbWorkOrderEntitySaved = tbWorkOrderJpaRepository.save(tbWorkOrderEntity);
        return tbWorkOrderServiceMapper.mapTbWorkOrderEntityToTbWorkOrder(tbWorkOrderEntitySaved);
    }

    @SuppressWarnings("unused")
    @Override
    @Transactional(readOnly = true)
    public List<WorkOrderGridEntityList> findWorkOrderPrintList(final Long artId, final Long serviceId) {

        final List<WorkOrderGridEntityList> workOrderGridEntityList = new ArrayList<>();
        new ArrayList<WorkOrderGridEntityList>();

       /* final Iterable<Object[]> objList = tbWorkOrderJpaRepository.findWorkOrderPrintList(serviceId,
                UserSession.getCurrent().getOrganisation().getOrgid());*/
        final Iterable<Object[]> objListwoPlumber = tbWorkOrderJpaRepository.WorkOrderPrintListwithAndWithPlumber(serviceId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        /*if (objList != null) {
            for (final Object[] obj : objList) {
                if (obj[0] != null) {
                    final WorkOrderGridEntityList entity = new WorkOrderGridEntityList();
                    entity.setApplicationId((Long) obj[0]);
                    entity.setConsumerName((String) obj[1]);
                    entity.setWoPrintFlg((String) obj[2]);
                    workOrderGridEntityList.add(entity);
                }
            }
        }*/
        if (objListwoPlumber != null) {
            for (final Object[] obj : objListwoPlumber) {
                if (obj[0] != null) {
                    final WorkOrderGridEntityList entity = new WorkOrderGridEntityList();
                    entity.setApplicationId((Long) obj[0]);
                    entity.setConsumerName((String) obj[1]);
                    entity.setPlumberId((Long) obj[2]);
                    workOrderGridEntityList.add(entity);
                }
            }
        }

        return workOrderGridEntityList;

    }

    @Override
    @Transactional(readOnly = true)
    public List<TbWorkOrderDetail> findByApplicationID(final Long applicationId) {
        final List<TbWorkOrderDetailEntity> tbRejectionMstEntity = tbWorkOrderDetailJpaRepository
                .findByApplicationID(applicationId, UserSession.getCurrent().getOrganisation().getOrgid());
        final List<TbWorkOrderDetail> beans = new ArrayList<>();
        for (final TbWorkOrderDetailEntity tbWorkOrderDetailEntity : tbRejectionMstEntity) {
            beans.add(TbWorkOrderDetailServiceMapper.mapTbWorkOrderDetailEntityToTbWorkOrderDetail(tbWorkOrderDetailEntity));
        }
        return beans;
    }

    @Override
    public void delete(final Long woId) {
        tbWorkOrderJpaRepository.delete(woId);
    }

    @Override
    public void ForPrintflagupdate(final String workOrderNo) {
        final TbWorkOrderEntity tbWorkOrderEntity = tbWorkOrderJpaRepository.ForPrintflagupdate(workOrderNo,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if ((tbWorkOrderEntity != null) && (tbWorkOrderEntity.getWoPrintFlg() == null)) {
            tbWorkOrderEntity.setWoPrintFlg(MainetConstants.RnLCommon.Y_FLAG);
            tbWorkOrderJpaRepository.save(tbWorkOrderEntity);
        }

    }

    public TbWorkOrderJpaRepository getTbWorkOrderJpaRepository() {
        return tbWorkOrderJpaRepository;
    }

    public void setTbWorkOrderJpaRepository(final TbWorkOrderJpaRepository tbWorkOrderJpaRepository) {
        this.tbWorkOrderJpaRepository = tbWorkOrderJpaRepository;
    }

    public TbWorkOrderServiceMapper getTbWorkOrderServiceMapper() {
        return tbWorkOrderServiceMapper;
    }

    public void setTbWorkOrderServiceMapper(final TbWorkOrderServiceMapper tbWorkOrderServiceMapper) {
        this.tbWorkOrderServiceMapper = tbWorkOrderServiceMapper;
    }

	@Override
	public Long getModuleWisePlumberId(Long applicationId, Long serviceId, Long orgId)
			throws ClassNotFoundException, LinkageError {
		Long plumberId = null;
		Class<?> clazz = null;
		Object dynamicServiceInstance = null;
		String serviceClassName = null;

		String serviceShortCode = serviceMasterService.fetchServiceShortCode(serviceId, orgId);
		serviceClassName = messageSource.getMessage(serviceShortCode, new Object[] {}, StringUtils.EMPTY,
				Locale.ENGLISH);

		clazz = ClassUtils.forName(serviceClassName,
				ApplicationContextProvider.getApplicationContext().getClassLoader());

		dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
				.autowire(clazz, 4, false);
		final Method method = ReflectionUtils.findMethod(clazz,
				ApplicationSession.getInstance().getMessage("workorder.plumber.id"),
				new Class[] { Long.class, Long.class });
		plumberId = (Long) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
				new Object[] { applicationId, orgId });
		
		return plumberId;
	}


	@Override
	@Transactional
	public List<PlumberMasterDTO> getPlumberList(Long applicationId, Long serviceId, Long orgId){

		List<PlumberMasterDTO> plumberList = new ArrayList<>();
		Class<?> clazz = null;
		Object dynamicServiceInstance = null;
		String serviceClassName = null;
		try {
			//String serviceShortCode = serviceMasterService.fetchServiceShortCode(serviceId, orgId);
			serviceClassName = messageSource.getMessage("WNC", new Object[] {}, StringUtils.EMPTY,
					Locale.ENGLISH);

			clazz = ClassUtils.forName(serviceClassName,
					ApplicationContextProvider.getApplicationContext().getClassLoader());

			dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
					.autowire(clazz, 4, false);
			final Method method = ReflectionUtils.findMethod(clazz,
					ApplicationSession.getInstance().getMessage("workorder.plumber.list"),
					new Class[] { Long.class, Long.class });
			plumberList = (List<PlumberMasterDTO>) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
					new Object[] { applicationId, orgId });
			
		} catch (LinkageError | Exception e) {
			LOGGER.error(e.getMessage());
		    throw new FrameworkException("Exception in fetching plumber list for application id : " + applicationId, e);
		}
		
		return plumberList;
		
	}

	@Override
	public void updatePlumberAndHoleManDetails(PlumberHoleManDTO plumberHoleManDTO, Long applicationId, Long orgId, Long serviceId) {

		Class<?> clazz = null;
		Object dynamicServiceInstance = null;
		String serviceClassName = null;
		try {
			String serviceShortCode = serviceMasterService.fetchServiceShortCode(serviceId, orgId);
			serviceClassName = messageSource.getMessage(serviceShortCode, new Object[] {}, StringUtils.EMPTY,
					Locale.ENGLISH);

			clazz = ClassUtils.forName(serviceClassName,
					ApplicationContextProvider.getApplicationContext().getClassLoader());

			dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getAutowireCapableBeanFactory()
					.autowire(clazz, 4, false);
			final Method method = ReflectionUtils.findMethod(clazz,
					ApplicationSession.getInstance().getMessage("workorder.csmr.update"),
					new Class[] {PlumberHoleManDTO.class, Long.class, Long.class });
			ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
					new Object[] { plumberHoleManDTO, applicationId, orgId });
			
		} catch (LinkageError | Exception e) {
			LOGGER.error(e.getMessage());
		    throw new FrameworkException("Exception in updating plumber and holeman for application id : " + applicationId, e);
		}
		
	}
	
}
