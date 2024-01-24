package com.abm.mainet.vehiclemanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.exception.FrameworkException;

import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.vehiclemanagement.dao.IPetrolRequisitionDAO;
import com.abm.mainet.vehiclemanagement.domain.PetrolRequisitionDetails;
import com.abm.mainet.vehiclemanagement.dto.PetrolRequisitionDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.repository.PetrolRequestRepository;

@Service
public class PetrolRequisitionService implements IPetrolRequisitionService {

	@Autowired
	private IGenVehicleMasterService vehicleMasterService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private IPetrolRequisitionDAO iPetrolRequisitionDAO;

	@Autowired
	private PetrolRequestRepository petrolRequisitionRepository;

	@Autowired
	private GroupMasterService groupMasterService;

	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	
	

	@Override
	public PetrolRequisitionDTO save(@RequestBody PetrolRequisitionDTO petrolRequisitionDTO) {
		
		PetrolRequisitionDetails master = new PetrolRequisitionDetails();
		BeanUtils.copyProperties(petrolRequisitionDTO, master);
		petrolRequisitionRepository.save(master);
		petrolRequisitionDTO.setRequestId(master.getRequestId());
		return petrolRequisitionDTO;

	}
	


	

	@Override
	public List<PetrolRequisitionDTO> getAllRecord(Long orgId) {

		List<PetrolRequisitionDetails> listPetrolRequisitions = petrolRequisitionRepository.findByOrgid(orgId);
		List<PetrolRequisitionDTO> dtoList = new ArrayList<PetrolRequisitionDTO>();
		listPetrolRequisitions.forEach(entity -> {
			PetrolRequisitionDTO dto = new PetrolRequisitionDTO();
			BeanUtils.copyProperties(entity, dto);
			String deptName = departmentService.fetchDepartmentDescById(entity.getDepartment());
			dto.setDeptDesc(departmentService.fetchDepartmentDescById(entity.getDepartment()));
			EmployeeBean employee = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
					.findById((entity.getDriverName()));
			dto.setDeptDesc(deptName);
			dto.setDriverDesc(employee.getFullName());
			dtoList.add(dto);

		});
		return dtoList;

	}


	@Override
	public List<PetrolRequisitionDTO> getAllVehicles(Long orgid) {
		List<Object[]> list = vehicleMasterService.getAllVehiclesWithoutEmp(orgid);
		List<PetrolRequisitionDTO> listDto = new ArrayList<PetrolRequisitionDTO>();
		for (Object[] obj : list) {
			PetrolRequisitionDTO dto = new PetrolRequisitionDTO();
			// dto.setDeptDesc(obj[0].toString());
			dto.setVeNo(obj[0].toString());
			dto.setVehicleTypeDesc(obj[1].toString());
			dto.setVeChasisSrno(obj[7].toString());
			listDto.add(dto);
		}

		return listDto;
	}


	@Override
	public List<PetrolRequisitionDTO> searchPetrolRequest(Date fromDate, Date toDate, Long department, Long veNo,
			Long orgid) {
		List<PetrolRequisitionDTO> listLogBookDTOs = new ArrayList<PetrolRequisitionDTO>();
		List<PetrolRequisitionDetails> list = iPetrolRequisitionDAO.searchPetrolRequestForm(fromDate, toDate, department, veNo,
				orgid);
		list.forEach(entity -> {
			PetrolRequisitionDTO dto = new PetrolRequisitionDTO();
			BeanUtils.copyProperties(entity, dto);
			/*
			 * Department
			 * department=ApplicationContextProvider.getApplicationContext().getBean(
			 * DepartmentService.class) .fetchDepartmentDescById(entity.getDepartment()));
			 * dto.setDeptDesc(department.getDpDeptdesc());
			 */
			// String deptName =
			// departmentService.fetchDepartmentDescById(entity.getDepartment());
			
			if(entity.getRequestStatus().equals("O"))
				dto.setRequestStatus("OPEN");
			if(entity.getRequestStatus().equals("R"))
				dto.setRequestStatus("REJECTED");
			if(entity.getRequestStatus().equals("A"))
				dto.setRequestStatus("APPROVED");
		
				SLRMEmployeeMasterDTO employee = ApplicationContextProvider.getApplicationContext().getBean(ISLRMEmployeeMasterService.class).searchEmployeeDetails(entity.getDriverName(), orgid);
				if(employee!=null && employee.getFullName()!=null) {
				dto.setDriverDesc(employee.getFullName());
				}
			//EmployeeBean employee = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class).findById((entity.getDriverName()));
			//dto.setDriverDesc(employee.getFullName());
			dto.setDeptDesc(departmentService.fetchDepartmentDescById(entity.getDepartment()));
			dto.setVeNo(vehicleMasterService.fetchVehicleNoByVeId(entity.getVeId()));
			dto.setVeChasisSrno(vehicleMasterService.fetchChasisNoByVeIdAndOrgid(entity.getVeId(), entity.getOrgid()));
			listLogBookDTOs.add(dto);
		});
		return listLogBookDTOs;
	}

	@Override
	@Transactional
	public PetrolRequisitionDTO getDetailById(Long requestId) {
		PetrolRequisitionDetails petrolRequisition = petrolRequisitionRepository.findOne(requestId);
		PetrolRequisitionDTO petrolDto = new PetrolRequisitionDTO();
		BeanUtils.copyProperties(petrolRequisition, petrolDto);
		return petrolDto;

	}

	@Override
	public Boolean checkEmployeeRole(UserSession ses) {
		@SuppressWarnings("deprecation")
		LookUp lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue("PETROL_APPROVER", "DLO", 1);
		GroupMaster groupMaster = groupMasterService.findByGmId(ses.getEmployee().getGmid(),
				ses.getOrganisation().getOrgid());
		boolean checkFinalAproval = false;
		if (lookup.getLookUpCode().equalsIgnoreCase(groupMaster.getGrCode())) {
			checkFinalAproval = true;
		}
		return checkFinalAproval;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updatePetrolApproveStatus(PetrolRequisitionDTO entity, String status, String lastDecision) {
		TbCfcApplicationMstEntity cfcApplEntiry = new TbCfcApplicationMstEntity();
		PetrolRequisitionDetails PetrolEntity = new PetrolRequisitionDetails();
		
		
		cfcApplEntiry.setApmApplicationId(entity.getRequestId());
		cfcApplEntiry.setRefNo(entity.getVeNo());
		cfcApplEntiry.setUpdatedDate(new Date());
		cfcApplEntiry.setLmoddate(new Date());
		
		final Organisation organisation = new Organisation();
		organisation.setOrgid(entity.getOrgid());
		cfcApplEntiry.setTbOrganisation(organisation);
		cfcApplEntiry.setUserId(entity.getUserId());
		
		PetrolEntity.setVeId(entity.getVeId());
		PetrolEntity.setUpdatedDate(new Date());
		

		if (lastDecision.equals("REJECTED")) {
			cfcApplEntiry.setApmAppRejFlag("R");
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmFname(entity.geteName());
			
			cfcApplEntiry.setLangId(entity.getLangId());
			cfcApplEntiry.setApmApplClosedFlag("C");
			cfcApplEntiry.setApmApplicationDate(new Date());
			PetrolEntity.setRequestStatus("R");
			
			PetrolEntity.setPetrolWFStatus("REJECTED");
		}

		else if (status.equals("APPROVED") && lastDecision.equals("PENDING")) {
			cfcApplEntiry.setApmApplSuccessFlag("P");
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(new Date());
			PetrolEntity.setRequestStatus("I");
		}

		else if (status.equals("APPROVED") && lastDecision.equals("CLOSED")) {
			cfcApplEntiry.setApmApplSuccessFlag("C");
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(entity.getCreatedDate());
			cfcApplEntiry.setApmApplicationDate(new Date());
			
			PetrolEntity.setRequestStatus("C");
		}
		tbCfcApplicationMstJpaRepository.save(cfcApplEntiry);
	}

	@Override
	@Transactional
		public void updatePetrolWorkFlowStatus(Long requestId, String taskNamePrevious, long orgId, Long puId) {
			petrolRequisitionRepository.updateWorkFlowStatus(requestId, orgId, puId, taskNamePrevious);
		}
	@Override
	@Transactional
	public void updatePetrolWorkFlowStatus(Long requestId, String taskNamePrevious, long orgId) {
		petrolRequisitionRepository.updateWorkFlowStatus(requestId, orgId, taskNamePrevious);
	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public void updatePetrolStatus(Long referenceId, String flaga) {
		petrolRequisitionRepository.updatePetrolStatus(referenceId, flaga);
	}

	@Override
	public List<PetrolRequisitionDTO> getDetailByVehNo(String complainNo, Long orgid) {

		List<PetrolRequisitionDetails> list = iPetrolRequisitionDAO.getDetailByVehNo(complainNo, orgid);
		List<PetrolRequisitionDTO> listLogBookDTOs = new ArrayList<PetrolRequisitionDTO>();
		list.forEach(entity -> {
			PetrolRequisitionDTO dto = new PetrolRequisitionDTO();
			
			BeanUtils.copyProperties(entity, dto);
					
			EmployeeBean employee = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class).findById((entity.getDriverName()));
			
			String deptName = departmentService.fetchDepartmentDescById(entity.getDepartment());
			dto.setVeNo(vehicleMasterService.fetchVehicleNoByVeId(entity.getVeId()));
			dto.setVeChasisSrno(vehicleMasterService.fetchChasisNoByVeIdAndOrgid(entity.getVeId(), entity.getOrgid()));
			if(employee!=null && employee.getFullName()!=null) {
				dto.setDriverDesc(employee.getFullName());
			}
			dto.setVeId(entity.getVeId());
			dto.setDeptDesc(deptName);
			dto.setFuelTypeDesc(CommonMasterUtility.getCPDDescription(entity.getFuelType(), MainetConstants.BLANK));
			dto.setRequestId(entity.getRequestId());
			dto.setDepartment(entity.getDepartment());
			
			listLogBookDTOs.add(dto);
		});
		return listLogBookDTOs;

	}

	@Override
	@Transactional
	@WebMethod(exclude = true)
	public String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas,
			String url, String workFlowFlag) {
		try {

			WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();

			workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
			ApplicationMetadata applicationMetadata = new ApplicationMetadata();

			applicationMetadata.setReferenceId(workflowActionDto.getReferenceId());
			applicationMetadata.setApplicationId(workflowActionDto.getApplicationId());
			applicationMetadata.setOrgId(workflowActionDto.getOrgId());
			applicationMetadata.setWorkflowId(workFlowMas.getWfId());
			applicationMetadata.setPaymentMode(workflowActionDto.getPaymentMode());
			applicationMetadata.setIsCheckListApplicable(false);

			ApplicationSession appSession = ApplicationSession.getInstance();

			/*
			 * Task manager assignment is depends no LDAP integration his check added in
			 * BRm/BPM layer
			 */
			TaskAssignment assignment = new TaskAssignment();

			assignment.setActorId(workflowActionDto.getEmpId().toString());
			assignment.addActorId(workflowActionDto.getEmpId().toString());
			assignment.setOrgId(workflowActionDto.getOrgId());
			assignment.setServiceEventId(-1L);
			String reqTaskname = MainetConstants.WorkFlow.EventLabels.INITIATOR;
			assignment.setServiceEventName(appSession.getMessage(reqTaskname, reqTaskname,
					new Locale(MainetConstants.DEFAULT_LOCALE_STRING), (Object[]) null));

			assignment.setServiceEventNameReg(appSession.getMessage(reqTaskname, reqTaskname,
					new Locale(MainetConstants.REGIONAL_LOCALE_STRING), (Object[]) null));

			assignment.setDeptId(workFlowMas.getDepartment().getDpDeptid());
			assignment.setDeptName(workFlowMas.getDepartment().getDpDeptdesc());
			assignment.setDeptNameReg(workFlowMas.getDepartment().getDpNameMar());
			assignment.setServiceId(workFlowMas.getService().getSmServiceId());
			assignment.setServiceName(workFlowMas.getService().getSmServiceNameMar());
			assignment.setServiceEventNameReg(workFlowMas.getService().getSmServiceNameMar());
			assignment.setUrl(url);

			/*
			 * Reviewer TaskAssignment has been removed from here,because it will be fetch
			 * on the fly by BPM to Service callback.
			 */

			workflowProcessParameter.setRequesterTaskAssignment(assignment);
			workflowProcessParameter.setApplicationMetadata(applicationMetadata);
			workflowProcessParameter.setWorkflowTaskAction(workflowActionDto);
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.initiateWorkflow(workflowProcessParameter);

		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Initiate Workflow methods", exception);
		}
		return null;

	}

	@Override
	@Transactional
	public String updateWorkFlowPetrolService(WorkflowTaskAction workflowTaskAction) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
        workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
        workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
        try {
        	ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class).updateWorkflow(workflowProcessParameter);
        } catch (Exception exception) {
            throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
        }
        return null;
	}

	
	@Transactional
	@WebMethod(exclude = true)
	public void updateComplainStatus(Long requestId, String flagr) {
		petrolRequisitionRepository.updateComplainStatus(requestId, flagr);

	}
	
	@Override
	@Transactional
	@WebMethod(exclude = true)
	public String updateWorkFlowWorksService(WorkflowTaskAction workflowTaskAction) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return null;
	}

	@Override
	public PetrolRequisitionDTO findByComplainNo(String referenceId, long orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePetrolStatus(String referenceId, String flagr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PetrolRequisitionDTO> fetchVeNoByDeptAndVeType(Long department, Long vehicleType, Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String fetchVehicleNoByVeId(Long veId, String requestStatus) {
	final String reqStatus=petrolRequisitionRepository.getRequestStatus(veId,requestStatus);
     return reqStatus;
	}





	@Override
	public String getRemark(String refId, Long orgId) {
		List<Object[]> workFlowTask = petrolRequisitionRepository.findWorkFlowTaskByRefId(refId, orgId);
		String remark=null;
		if (workFlowTask != null) {
			for (Object[] file : workFlowTask) {
				Object obj = file[9];
				if (obj != null) {
					remark = obj.toString();
					break;
				}
			}
		}
		return remark;
	}

}




