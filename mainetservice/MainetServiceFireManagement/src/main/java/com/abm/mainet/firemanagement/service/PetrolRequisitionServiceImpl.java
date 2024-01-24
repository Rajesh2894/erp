/*package com.abm.mainet.firemanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.jws.WebMethod;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.GroupMaster;
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
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.firemanagement.dao.IPetrolRequisitionDAO;
import com.abm.mainet.firemanagement.domain.FmPetrolRequisition;
import com.abm.mainet.firemanagement.dto.PetrolRequisitionDTO;
import com.abm.mainet.firemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.firemanagement.repository.PetrolRequisitionRepository;
//import com.abm.mainet.vehicle.management.service.IGenVehicleMasterService;

@Service
public class PetrolRequisitionServiceImpl implements IPetrolRequisitionService {

	//@Autowired
	//private IGenVehicleMasterService vehicleMasterService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private IPetrolRequisitionDAO iPetrolRequisitionDAO;

	@Autowired
	private PetrolRequisitionRepository petrolRequisitionRepository;

	@Autowired
	private GroupMasterService groupMasterService;

	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;

	@Override
	public PetrolRequisitionDTO save(@RequestBody PetrolRequisitionDTO petrolRequisitionDTO) {

		FmPetrolRequisition master = new FmPetrolRequisition();
		BeanUtils.copyProperties(petrolRequisitionDTO, master);
		petrolRequisitionRepository.save(master);
		petrolRequisitionDTO.setRequestId(master.getRequestId());
		return petrolRequisitionDTO;

	}

	@Override
	public List<PetrolRequisitionDTO> getAllRecord(Long orgId) {

		List<FmPetrolRequisition> listPetrolRequisitions = petrolRequisitionRepository.findByOrgid(orgId);
		// List<PetrolRequisition> listFire = fireCallRegisterDAO.searchFireData(orgId,
		// status);
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
	public List<PetrolRequisitionDTO> searchFireCallRegisterwithDate(Date fromDate, Date toDate, String veNo,
			Long orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PetrolRequisitionDTO getVehicleById(Long veID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PetrolRequisitionDTO> getAllVehicles(Long orgid) {
		List<Object[]> list =null; //vehicleMasterService.getAllVehicles(orgid);
		 String serverURI=ApplicationSession.getInstance().getMessage("VEHICLE_MASTER_All")+orgid;
		   ResponseEntity<?> Response = RestClient.callRestTemplateClient(null, serverURI, HttpMethod.GET);
		   if(Response!=null  && Response.getStatusCode()==HttpStatus.OK) {
		        list=(List<Object[]>) Response.getBody();
		    }
		List<PetrolRequisitionDTO> listDto = new ArrayList<PetrolRequisitionDTO>();
		for (Object[] obj : list) {
			PetrolRequisitionDTO dto = new PetrolRequisitionDTO();
			// dto.setDeptDesc(obj[0].toString());
			dto.setVeNo(obj[1].toString());
			// dto.setVehicleTypeDesc(obj[0].toString());
			listDto.add(dto);
		}
		
		if(CollectionUtils.isNotEmpty(list)) {
			for (Object object : list) {
				PetrolRequisitionDTO dto = new PetrolRequisitionDTO();
				int count=0;
				List<Object[]>  objectArray=(List<Object[]>)object;
				for(Object obj:objectArray) {
					if(count==1)
				    dto.setVeNo(obj.toString());
					count++;
				}
				listDto.add(dto);
	    		}	
			}
		
		

		return listDto;
	}

	@Override
	public List<PetrolRequisitionDTO> fetchVeNoByDeptAndVeType(Long department, Long vehicleType, Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PetrolRequisitionDTO saveDetails(PetrolRequisitionDTO petrolRequisitionDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PetrolRequisitionDTO> searchPetrolRequest(Date fromDate, Date toDate, Long department, String veNo,
			Long orgid) {
		List<PetrolRequisitionDTO> listLogBookDTOs = new ArrayList<PetrolRequisitionDTO>();
		List<FmPetrolRequisition> list = iPetrolRequisitionDAO.searchPetrolRequestForm(fromDate, toDate, department, veNo,
				orgid);
		list.forEach(entity -> {
			PetrolRequisitionDTO dto = new PetrolRequisitionDTO();
			BeanUtils.copyProperties(entity, dto);
			
			 * Department
			 * department=ApplicationContextProvider.getApplicationContext().getBean(
			 * DepartmentService.class) .fetchDepartmentDescById(entity.getDepartment()));
			 * dto.setDeptDesc(department.getDpDeptdesc());
			 
			// String deptName =
			// departmentService.fetchDepartmentDescById(entity.getDepartment());
			EmployeeBean employee = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
					.findById((entity.getDriverName()));
			dto.setDriverDesc(employee.getFullName());
			dto.setDeptDesc(departmentService.fetchDepartmentDescById(entity.getDepartment()));
			listLogBookDTOs.add(dto);
		});
		return listLogBookDTOs;
	}

	@Override
	@Transactional
	public PetrolRequisitionDTO getDetailById(Long requestId) {
		FmPetrolRequisition petrolRequisition = petrolRequisitionRepository.findOne(requestId);
		PetrolRequisitionDTO petrolDto = new PetrolRequisitionDTO();
		BeanUtils.copyProperties(petrolRequisition, petrolDto);
		return petrolDto;

	}

	@Override
	public Boolean checkEmployeeRole(UserSession ses) {
		@SuppressWarnings("deprecation")
		LookUp lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue("FIRE_APPROVER", "DLO", 1);
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
		FmPetrolRequisition PetrolEntity = new FmPetrolRequisition();
		
		//cfcApplEntiry.setApmApplicationId(Long.valueOf(entity.getVeNo()));
		cfcApplEntiry.setApmApplicationId(entity.getRequestId());
		cfcApplEntiry.setRefNo(entity.getVeNo());
		cfcApplEntiry.setUpdatedDate(new Date());
		cfcApplEntiry.setLmoddate(new Date());
		//cfcApplEntiry.setUpdatedBy(entity.getUserId()); //ashish no column updatedBy

		PetrolEntity.setVeNo(entity.getVeNo());
		// PetrolEntity.setCmplntId(entity.getCmplntId()); //ashish
		// PetrolEntity.setUpdatedBy(entity.getUserId()); //ashish no column updatedBy
		PetrolEntity.setUpdatedDate(new Date());
		// FireEntity.setLmoddate(new Date()); //ashish no column lmodDate

		if (lastDecision.equals("REJECTED")) {
			cfcApplEntiry.setApmAppRejFlag("R");
			// cfcApplEntiry.setAppAppRejBy(entity.getSmServiceId()); //ashish no column
			// serviceId
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("C");
			cfcApplEntiry.setApmApplicationDate(new Date());
			PetrolEntity.setRequestStatus("R");
		}

		else if (status.equals("APPROVED") && lastDecision.equals("PENDING")) {
			cfcApplEntiry.setApmApplSuccessFlag("P");
			// cfcApplEntiry.setApmApprovedBy(entity.getSmServiceId()); //ashish no column
			// serviceId
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(new Date());
			PetrolEntity.setRequestStatus("I");
		}

		else if (status.equals("APPROVED") && lastDecision.equals("CLOSED")) {
			cfcApplEntiry.setApmApplSuccessFlag("C");
			// cfcApplEntiry.setApmApprovedBy(entity.getSmServiceId()); //ashish no column
			// serviceId
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
	public List<PetrolRequisitionDTO> getDetailByVehNo(Long complainNo, Long orgid) {

		List<FmPetrolRequisition> list = iPetrolRequisitionDAO.getDetailByVehNo(complainNo, orgid);
		List<PetrolRequisitionDTO> listLogBookDTOs = new ArrayList<PetrolRequisitionDTO>();
		list.forEach(entity -> {
			PetrolRequisitionDTO dto = new PetrolRequisitionDTO();
			
			BeanUtils.copyProperties(entity, dto);
					
			EmployeeBean employee = ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class).findById((entity.getDriverName()));
			
			String deptName = departmentService.fetchDepartmentDescById(entity.getDepartment());
			//dto.setDeptDesc(departmentService.fetchDepartmentDescById(entity.getDepartment()));
			dto.setDriverDesc(employee.getFullName());
			dto.setVeNo(entity.getVeNo());
			dto.setDeptDesc(deptName);
			dto.setRequestId(entity.getRequestId());
			dto.setDepartment(entity.getDepartment());
			
			listLogBookDTOs.add(dto);
		});
		return listLogBookDTOs;

	}

	
	 * @Override public void initiateWorkFlowWorksService(WorkflowTaskAction
	 * prepareWorkFlowTaskAction, WorkflowMas workFlowMas, String string, String
	 * flaga) { // TODO Auto-generated method stub
	 * 
	 * }
	 
	
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

			
			 * Task manager assignment is depends no LDAP integration his check added in
			 * BRm/BPM layer
			 
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

			
			 * Reviewer TaskAssignment has been removed from here,because it will be fetch
			 * on the fly by BPM to Service callback.
			 

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
	public String updateWorkFlowFireService(WorkflowTaskAction workflowTaskAction) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	@Transactional
	public String updateWorkFlowFireService(WorkflowTaskAction workflowTaskAction) {
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
		//fireCallRegisterRepository.updateComplainStatus(complainNo, status);
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
	
	
	
	

}
*/