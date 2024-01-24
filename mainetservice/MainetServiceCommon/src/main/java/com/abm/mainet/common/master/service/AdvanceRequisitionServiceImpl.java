package com.abm.mainet.common.master.service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AdvanceRequisition;
import com.abm.mainet.common.integration.acccount.dto.AdvanceRequisitionDto;
import com.abm.mainet.common.master.dao.CommonAdvanceEntryDao;
import com.abm.mainet.common.master.repository.CommonAdvanceEntryRepository;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@Service
public class AdvanceRequisitionServiceImpl implements IAdvanceRequisitionService {

	private static Logger logger = Logger.getLogger(AdvanceRequisitionServiceImpl.class);

	@Resource
	private CommonAdvanceEntryRepository commonAdvanceEntryRepository;
	@Resource
	private CommonAdvanceEntryDao commonAdvanceEntryDao;
	@Resource
	private SeqGenFunctionUtility seqGenFunctionUtility;
	@Resource
	private TbAcVendormasterService tbAcVendormasterService;
	@Resource
	private IEmployeeService iEmployeeService;
	@Resource
	private TbFinancialyearService financialyearService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private IWorkflowExecutionService workflowExecutionService;

	@Override
	@Transactional
	public Map<Long, String> getBudgetHeadAllData(final Long acountSubType, final Organisation organisation,
			final int langId) {

		final Long orgId = organisation.getOrgid();
		final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.IsLookUp.ACTIVE,
				PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, langId, organisation);
		final Long statusId = statusLookup.getLookUpId();
		final LookUp advanceLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				PrefixConstants.StandardAccountHeadMapping.AD, PrefixConstants.TbAcVendormaster.SAM, langId,
				organisation);
		final Long advanceTypeId = advanceLookup.getLookUpId();
		final Map<Long, String> budgtHeadId = new LinkedHashMap<>();
		final List<Object[]> prBudgetCode = commonAdvanceEntryRepository.getAdvBudgetHeadIds(statusId, advanceTypeId,
				acountSubType, orgId);
		for (final Object[] objects : prBudgetCode) {
			budgtHeadId.put((Long) objects[0], (String) objects[1]);
		}
		return budgtHeadId;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AdvanceRequisitionDto> getFilterRequisition(Date advanceEntryDate, Long vendorId, Long deptId,
			Long orgId) {

		List<AdvanceRequisitionDto> dtoList = new ArrayList<>();
		AdvanceRequisitionDto dto = null;
		try {
			final List<AdvanceRequisition> entities = commonAdvanceEntryDao.getFilterRequisition(advanceEntryDate,
					vendorId, deptId, orgId);
			for (AdvanceRequisition advanceRequisition : entities) {
				dto = new AdvanceRequisitionDto();
				org.springframework.beans.BeanUtils.copyProperties(advanceRequisition, dto);
				dto.setAdvDateStr(Utility.dateToString(dto.getEntryDate()));
				dtoList.add(dto);
			}
		} catch (final Exception exception) {
			throw new FrameworkException("Error occured while searching Advance Requisition Data", exception);
		}
		return dtoList;

	}

	@Override
	@Transactional
	public AdvanceRequisitionDto saveUpdateAdvanceRequisition(AdvanceRequisitionDto advanceRequisitionDto,
			String removeFileById) {
		AdvanceRequisition advanceRequisition = new AdvanceRequisition();
		try {
			org.springframework.beans.BeanUtils.copyProperties(advanceRequisitionDto, advanceRequisition);
			if (advanceRequisition.getAdvNo() == null) {
				String advNumber = updateAdvanceNumverNo(advanceRequisition.getEntryDate(),
						advanceRequisition.getOrgid(), advanceRequisition.getDeptId());
				advanceRequisition.setAdvNo(advNumber);
				advanceRequisitionDto.setAdvNo(advNumber);
			}
			commonAdvanceEntryRepository.save(advanceRequisition);
		} catch (Exception e) {
			throw new FrameworkException("error occurred while saving Requisition form ", e);
		}
		return advanceRequisitionDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdvanceRequisitionDto> getWorkOrderDetails(String deptCode, Long venderId, Long orgId) {

		List<AdvanceRequisitionDto> workOrderDetails = new ArrayList<>();
		String deptRestCallKey = ApplicationSession.getInstance().getMessage(deptCode + "_ADVANCE_REQUISATION");
		ResponseEntity<?> responseEntity = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("venderId", String.valueOf(venderId));
		requestParam.put("orgId", String.valueOf(orgId));
		URI uri = dd.expand(deptRestCallKey, requestParam);
		try {
			responseEntity = RestClient.callRestTemplateClient(workOrderDetails, uri.toString());
			HttpStatus statusCode = responseEntity.getStatusCode();
			if (statusCode == HttpStatus.OK) {
				workOrderDetails = (List<AdvanceRequisitionDto>) responseEntity.getBody();
			}
		} catch (Exception ex) {
			logger.error("Exception occured while fetching work order details : ", ex);
			return workOrderDetails;
		}
		return workOrderDetails;
	}

	@Override
	@Transactional(readOnly = true)
	public AdvanceRequisitionDto getAdvanceRequisitionById(Long advId) {
		AdvanceRequisitionDto advanceRequisitionDto = new AdvanceRequisitionDto();
		try {
			AdvanceRequisition advanceRequisition = commonAdvanceEntryRepository.findOne(advId);
			org.springframework.beans.BeanUtils.copyProperties(advanceRequisition, advanceRequisitionDto);
		} catch (Exception e) {
			throw new FrameworkException("error occurred while calling methhod getAdvanceRequisitionById ", e);
		}
		return advanceRequisitionDto;
	}

	@Override
	public Map<Long, String> getEmployeeVendorType(String advType, Long referenceId, Long orgId) {
		Map<Long, String> empList = new HashMap<>();
		try {
			if (advType.equals(MainetConstants.FlagC)) {
				List<TbAcVendormaster> acVendormasters = tbAcVendormasterService.getActiveVendors(orgId, referenceId);
				for (TbAcVendormaster tbAcVendormaster : acVendormasters) {
					empList.put(tbAcVendormaster.getVmVendorid(), tbAcVendormaster.getVmVendorname());
				}
			} else {
				List<Employee> employees = iEmployeeService.findAllEmployeeByDept(orgId, referenceId);
				for (Employee employee : employees) {
					empList.put(employee.getEmpId(), employee.getEmpname());
				}
			}
		} catch (Exception e) {
			throw new FrameworkException("error occurred while calling methhod getEmployeeVendorType ", e);
		}
		return empList;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal getUsedContractAmountByReferenceNumber(String referenceNumber, Long orgId) {
		return commonAdvanceEntryRepository.getUsedContractAmountByReferenceNumber(referenceNumber, orgId);
	}

	public String updateAdvanceNumverNo(Date estimateDate, Long orgId, Long deptId) {

		// get financial by date
		FinancialYear financiaYear = financialyearService.getFinanciaYearByDate(estimateDate);

		// get financial year formate
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());

		// gerenerate sequence
		final Long sequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.CommonConstants.COM,
				MainetConstants.TB_ADVANCE_REQ, MainetConstants.ADV_NO, orgId, MainetConstants.FlagC,
				financiaYear.getFaYear());
		String deptCode = departmentService.getDeptCode(deptId);

		String arnNumber = MainetConstants.ARN + MainetConstants.WINDOWS_SLASH + deptCode
				+ MainetConstants.WINDOWS_SLASH + finacialYear + MainetConstants.WINDOWS_SLASH
				+ String.format("%03d", sequence);

		return arnNumber;
	}

	@Override
	@Transactional
	public void updateAdvanceRequisitionMode(Long advId, String satusFlag) {
		commonAdvanceEntryRepository.updateAdvanceRequisitionMode(advId, satusFlag);
	}

	@Override
	public AdvanceRequisitionDto getAdvanceRequisitionByArn(String arnNumber,Long orgId) {
		AdvanceRequisitionDto advanceRequisitionDto = new AdvanceRequisitionDto();
		try {
			AdvanceRequisition advanceRequisition = commonAdvanceEntryRepository.getAdvanceRequisitionByArn(arnNumber, orgId);
			org.springframework.beans.BeanUtils.copyProperties(advanceRequisition, advanceRequisitionDto);
		} catch (Exception e) {
			throw new FrameworkException("error occurred while calling methhod getAdvanceRequisitionByArn ", e);
		}
		return advanceRequisitionDto;
	}

	public void initiateWorkFlow(WorkflowTaskAction workflowActionDto, Long workFlowId, String url,
			String workFlowFlag) {

		try {
			WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();

			workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);

			ApplicationMetadata applicationMetadata = new ApplicationMetadata();

			applicationMetadata.setReferenceId(workflowActionDto.getReferenceId());
			applicationMetadata.setOrgId(workflowActionDto.getOrgId());
			applicationMetadata.setWorkflowId(workFlowId);
			applicationMetadata.setPaymentMode(workflowActionDto.getPaymentMode());
			applicationMetadata.setIsCheckListApplicable(false);

			/*
			 * Task manager assignment is depends no LDAP integration his check added in
			 * BRm/BPM layer
			 */
			TaskAssignment assignment = new TaskAssignment();

			assignment.setActorId(workflowActionDto.getEmpId().toString());
			assignment.addActorId(workflowActionDto.getEmpId().toString());
			assignment.setOrgId(workflowActionDto.getOrgId());
			assignment.setUrl(url);

			/*
			 * Reviewer TaskAssignment has been removed from here,because it will be fetch
			 * on the fly by BPM to Service callback.
			 */

			workflowProcessParameter.setRequesterTaskAssignment(assignment);
			workflowProcessParameter.setApplicationMetadata(applicationMetadata);
			workflowProcessParameter.setWorkflowTaskAction(workflowActionDto);
			if (workFlowFlag.equals(MainetConstants.FlagU)) {
				workflowExecutionService.updateWorkflow(workflowProcessParameter);
			} else {
				workflowExecutionService.initiateWorkflow(workflowProcessParameter);
			}

		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when call Workflow methods", exception);
		}
	}

	@Override
	@Transactional
	public void updateBillNumberByAdvId(String billNumber, Long advId) {
		commonAdvanceEntryRepository.updateBillNumberByAdvId(billNumber, advId);

	}

}
