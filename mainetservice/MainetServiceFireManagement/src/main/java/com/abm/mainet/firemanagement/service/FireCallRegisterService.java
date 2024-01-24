package com.abm.mainet.firemanagement.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.dao.IFireCallRegisterDAO;
import com.abm.mainet.firemanagement.domain.FireCallRegister;
import com.abm.mainet.firemanagement.domain.OccuranceBookEntity;
import com.abm.mainet.firemanagement.domain.TbFmComplainClosure;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.dto.OccuranceBookDTO;
import com.abm.mainet.firemanagement.repository.FireCallClosureRepository;
import com.abm.mainet.firemanagement.repository.FireCallRegisterRepository;
import com.abm.mainet.firemanagement.repository.OccuranceBookRepository;
//import com.abm.mainet.vehicle.management.repository.GenVehicleMasterRepository;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Service Implementation for managing ComplainRegister.
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.firemanagement.service.IFireCallRegisterService")
@Path(value = "/fireCallRegisterService")
@Api(value = "/fireCallRegisterService")
public class FireCallRegisterService implements IFireCallRegisterService {

	
	private static final Logger LOGGER = Logger.getLogger(FireCallRegisterService.class);
	
	@Autowired
	private FireCallRegisterRepository fireCallRegisterRepository;

	@Autowired
	private IFireCallRegisterDAO fireCallRegisterDAO;
	
	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	
	
	@Autowired
	private FireCallClosureRepository fireCallClosureRepository;   

          
     // @Autowired
	   //private GenVehicleMasterRepository vehicleMasterRepository;
	 
	 @Autowired
		private IEmployeeService employeeService;

	 @Autowired
	 private OccuranceBookRepository occurRepo;


	@Override
	@Transactional
	@POST
	@Path(value = "/save")
	@ApiOperation(value = "save Fire Call Register", notes = "save Fire Call Register", response = FireCallRegisterDTO.class)
	public FireCallRegisterDTO save(@RequestBody FireCallRegisterDTO fireCallRegisterDTO) {
		FireCallRegister master = new FireCallRegister();
        fireCallRegisterDTO.setCpdFireStation(mapToString(fireCallRegisterDTO.getCpdFireStationList()));
		fireCallRegisterDTO.setFireStationsAttendCall(mapToString(fireCallRegisterDTO.getFireStationsAttendCallList()));
		fireCallRegisterDTO.setCallAttendEmployee(mapToString(fireCallRegisterDTO.getCallAttendEmployeeList()));
		BeanUtils.copyProperties(fireCallRegisterDTO, master);
                 master.setComplaintStatus(fireCallRegisterDTO.getStatusFlag());
		master.setDutyOfficer(Long.valueOf(fireCallRegisterDTO.getDutyOfficer()));
		if(fireCallRegisterDTO.getAssignVehicleList()!=null && !fireCallRegisterDTO.getAssignVehicleList().isEmpty())
		master.setAssignVehicle(fireCallRegisterDTO.getAssignVehicleList().stream().collect(Collectors.joining(",")));
		master = fireCallRegisterRepository.save(master);
		BeanUtils.copyProperties(master, fireCallRegisterDTO);
		return fireCallRegisterDTO;
	}

	
	
	@Override
	@Transactional
	@POST
	@Path(value = "/save")
	@ApiOperation(value = "save Fire Call Register", notes = "save Fire Call Register", response = FireCallRegisterDTO.class)
	public FireCallRegisterDTO saveFireCallcloser(@RequestBody FireCallRegisterDTO fireCallRegisterDTO) {
		TbFmComplainClosure master = new TbFmComplainClosure();
		fireCallRegisterDTO.setFireStationsAttendCall(mapToString(fireCallRegisterDTO.getFireStationsAttendCallList()));
		fireCallRegisterDTO.setCallAttendEmployee(mapToString(fireCallRegisterDTO.getCallAttendEmployeeList()));
		BeanUtils.copyProperties(fireCallRegisterDTO, master);
		master.setCpdFireStation(fireCallRegisterDTO.getCpdFireStation());
		master.setVehicleOutTime(fireCallRegisterDTO.getVehicleOutTime());
		master.setDutyOfficer(Long.valueOf(fireCallRegisterDTO.getDutyOfficer()));
		master.setOrgid(fireCallRegisterDTO.getOrgid());
		master.setClosureId(null);
		master.setTime(fireCallRegisterDTO.getTime());
		master.setTime(fireCallRegisterDTO.getCallAttendTime());
		if(fireCallRegisterDTO.getAssignVehicleList()!=null && !fireCallRegisterDTO.getAssignVehicleList().isEmpty())
		master.setAssignVehicle(mapToString(fireCallRegisterDTO.getAssignVehicleList()));
		master = fireCallClosureRepository.save(master);
		fireCallRegisterRepository.updateCallRegisterInProgress(fireCallRegisterDTO.getCmplntId(), fireCallRegisterDTO.getOrgid(), "I");
		BeanUtils.copyProperties(master, fireCallRegisterDTO);
		fireCallRegisterDTO.setClosureId(master.getClosureId());
		return fireCallRegisterDTO;
	}

	
	
	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/getAll/{orgId}")
	@ApiOperation(value = "Get All Fire Call", notes = "Get All Fire Call", response = FireCallRegisterDTO.class, responseContainer = "List")
	public List<FireCallRegisterDTO> findAll(
			@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgid) {
		return fireCallRegisterRepository.findByOrgid(orgid).stream().map(entity -> {
			FireCallRegisterDTO dto = new FireCallRegisterDTO();
			BeanUtils.copyProperties(entity, dto);
			dto.setFireStationsAttendCallList(mapToList(dto.getFireStationsAttendCall()));
			return dto;
		}).collect(Collectors.toList());
	}

    @Override
	@Transactional(readOnly = true)
	public List<FireCallRegisterDTO> findAllFire(
			@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgid) {
		   List<FireCallRegisterDTO> newlist=new ArrayList<FireCallRegisterDTO>();
		   List<FireCallRegisterDTO> list = fireCallRegisterRepository.findByOrgid(orgid).stream().map(entity -> {
			FireCallRegisterDTO dto = new FireCallRegisterDTO();
			BeanUtils.copyProperties(entity, dto);
			dto.setDutyOfficer(String.valueOf(entity.getDutyOfficer()));
			dto.setFireStationsAttendCallList(mapToList(dto.getFireStationsAttendCall()));
			dto.setDutyOfficerList(mapToList(dto.getDutyOfficer()));
			return dto;
		    }).collect(Collectors.toList());
		   List<EmployeeBean> employeeList = employeeService.getAllEmployee(orgid);
		   
		   for(FireCallRegisterDTO  dto:list) {
				 if(Constants.DRAFT_STATUS.equals(dto.getComplaintStatus())) {
				        // Remove milliseconds from time start
					    String time = dto.getTime();
					    int lenth = time.trim().length();
					    String timehhmm = time.substring(0, lenth-3);
					    dto.setTime(timehhmm);
					    // Remove milliseconds from time end
					 newlist.add(dto);
				 }
		   }   
		    for(EmployeeBean emp:employeeList) {
		    	for(FireCallRegisterDTO fireDto:newlist) {
		    		if(emp.getEmpId().toString().equals(fireDto.getDutyOfficer())) {
		    			fireDto.setDutyOfficer(emp.getFullName());
		    		}
		    	}
		    }
		   
		   
		   
		   return newlist;
	}


	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/get/{Id}")
	@ApiOperation(value = "Get Complain by Id", notes = "Get Complain by Complain Id", response = FireCallRegisterDTO.class)
	public FireCallRegisterDTO findOne(@ApiParam(value = "Call Id", required = true) @PathParam("Id") Long id) {
		List<TbFmComplainClosure> list = fireCallClosureRepository.findByComId(id,UserSession.getCurrent().getOrganisation().getOrgid());
		FireCallRegisterDTO dto = new FireCallRegisterDTO(); 
		if(list!=null && ! list.isEmpty()) {
		BeanUtils.copyProperties(list.get(0), dto);
		dto.setDutyOfficer(list.get(0).getDutyOfficer().toString()); //(dto.getDutyOfficer().toString());
		dto.setCpdFireStationList(mapToList(list.get(0).getCpdFireStation()));
		dto.setFireStationsAttendCallList(mapToList(list.get(0).getFireStationsAttendCall()));
		dto.setCallAttendEmployeeList(mapToList(dto.getCallAttendEmployee()));
		}else {
			FireCallRegister master = fireCallRegisterRepository.findOne(id);
			BeanUtils.copyProperties(master, dto);
			dto.setDutyOfficer(master.getDutyOfficer().toString());
			dto.setCpdFireStationList(mapToList(master.getCpdFireStation()));
			dto.setFireStationsAttendCallList(mapToList(dto.getFireStationsAttendCall()));
			dto.setCallAttendEmployeeList(mapToList(dto.getCallAttendEmployee()));
			if(master.getAssignVehicle()!=null && !master.getAssignVehicle().isEmpty())
			dto.setAssignVehicleList(mapToList(master.getAssignVehicle()));
		}
		return dto;
		
	}
	
	
	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/get/{Id}")
	@ApiOperation(value = "Get Complain by Id", notes = "Get Complain by Complain Id", response = FireCallRegisterDTO.class)
	public FireCallRegisterDTO findOne1(@ApiParam(value = "Call Id", required = true) @PathParam("Id") Long id) {
		//List<TbFmComplainClosure> list = fireCallClosureRepository.findByComId(id,UserSession.getCurrent().getOrganisation().getOrgid());
		FireCallRegisterDTO dto = new FireCallRegisterDTO(); 
		/*
		 * if(list!=null && ! list.isEmpty()) { BeanUtils.copyProperties(list.get(0),
		 * dto); dto.setDutyOfficer(list.get(0).getDutyOfficer().toString());
		 * dto.setCpdFireStationList(mapToList(list.get(0).getCpdFireStation()));
		 * dto.setFireStationsAttendCallList(mapToList(list.get(0).
		 * getFireStationsAttendCall()));
		 * 
		 * }else {
		 */
			TbFmComplainClosure master = fireCallClosureRepository.findOne(id);
			BeanUtils.copyProperties(master, dto);
			dto.setDutyOfficer(master.getDutyOfficer().toString());
			dto.setCpdFireStationList(mapToList(master.getCpdFireStation()));
			dto.setFireStationsAttendCallList(mapToList(dto.getFireStationsAttendCall()));
			dto.setCallAttendEmployeeList(mapToList(dto.getCallAttendEmployee()));
			if(master.getAssignVehicle()!=null && !master.getAssignVehicle().isEmpty())
			dto.setAssignVehicleList(mapToList(master.getAssignVehicle()));
		//}
		return dto;
		
	}
	
	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/get/{orgId}/{complainNo}")
	@ApiOperation(value = "Get Complain by orgId and complainNo", notes = "Get Complain by Organisation Id and Complain No", response = FireCallRegisterDTO.class)
	public FireCallRegisterDTO findByComplainNo(
			@ApiParam(value = "complain No", required = true) @PathParam("complainNo") String complainNo,
			@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgid) {
		FireCallRegister master = fireCallRegisterRepository.findByCmplntNoAndOrgid(complainNo, complainNo, orgid);
		FireCallRegisterDTO dto = new FireCallRegisterDTO();
		BeanUtils.copyProperties(master, dto);
		dto.setFireStationsAttendCallList(mapToList(dto.getFireStationsAttendCall()));
		dto.setCallAttendEmployeeList(mapToList(dto.getCallAttendEmployee()));
		return dto;
	}
		
	
	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/get/{orgId}/{complainNo}")
	@ApiOperation(value = "Get Complain by orgId and complainNo", notes = "Get Complain by Organisation Id and Complain No", response = FireCallRegisterDTO.class)
	public FireCallRegisterDTO findByComplainNoCallCloser(
			@ApiParam(value = "complain No", required = true) @PathParam("complainNo") String complainNo,
			@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgid) {
		TbFmComplainClosure master = fireCallClosureRepository.findByCmplntNoAndOrgid(complainNo, orgid);
		FireCallRegisterDTO dto = new FireCallRegisterDTO();
		BeanUtils.copyProperties(master, dto);
		dto.setFireStationsAttendCallList(mapToList(dto.getFireStationsAttendCall()));
		dto.setCallAttendEmployeeList(mapToList(dto.getCallAttendEmployee()));
		return dto;
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

	/**
	 * Method Is used for Update Work flow
	 * 
	 * @param workflowTaskAction
	 * @return string
	 */
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
	@Transactional
	@WebMethod(exclude = true)
	public void updateComplainStatus(String complainNo, String status) {
		fireCallRegisterRepository.updateComplainStatus(complainNo, status);
		fireCallClosureRepository.updateComplainStatus(Long.valueOf(complainNo), status);

	}

	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/search")
	@ApiOperation(value = "Get call by orgId and complainNo or complaint Status or fireStation", notes = "Get call by orgId and complainNo or complaint Status or fireStation", response = FireCallRegisterDTO.class, responseContainer = "List")
	public List<FireCallRegisterDTO> searchFireCallRegister(
			@ApiParam(value = "complain No", required = false) @QueryParam("complainNo") String complainNo,
			@ApiParam(value = "complaint Status", required = false) @QueryParam("complaintStatus") String complaintStatus,
			@ApiParam(value = "fireStation", required = false) @QueryParam("fireStation") String fireStation,
			@ApiParam(value = "Organisation Id", required = true) @QueryParam("orgId") Long orgid) {
		
		
		List<FireCallRegisterDTO> listdCallRegisterDTOs = new ArrayList<FireCallRegisterDTO>();
		List<FireCallRegister> list = fireCallRegisterDAO.searchFireCallRegister(complainNo, complaintStatus, fireStation, orgid);
		
		list.forEach(entity -> {
			FireCallRegisterDTO dto = new FireCallRegisterDTO();
			BeanUtils.copyProperties(entity, dto);

			String cpdFireStation = entity.getCpdFireStation();
			String joinedString = Constants.FIRE_CALL_BLANK_STATION;
			if (cpdFireStation != null) {
				String[] splitIds = cpdFireStation.split(",");
				List<String> fireStationList = new ArrayList<>();
				for (int i = Constants.FIRE_ZERO; i < splitIds.length; i++) {
					String lookuDesc = CommonMasterUtility
							.getNonHierarchicalLookUpObjectByPrefix(Long.parseLong(splitIds[i]), orgid, Constants.FIRE_CALL_PREFIX)
							.getLookUpDesc();
					fireStationList.add(lookuDesc);
				}

				if (fireStationList.size() > Constants.FIRE_ZERO) {
					joinedString = String.join(",", fireStationList);
				}
				dto.setFsDesc(joinedString);
			} else {
				dto.setFsDesc(joinedString);
			}
			listdCallRegisterDTOs.add(dto);

		});
		return listdCallRegisterDTOs;
	}

	
	
	private List<String> mapToList(String comaSeparatedValue){
		if(comaSeparatedValue != null)
		return Arrays.asList(comaSeparatedValue.split(","));
		return null;
	}
	
	private String mapToString(List<String> array) {
		if(CollectionUtils.isNotEmpty(array))
		return array.stream().collect(Collectors.joining(","));
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

	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateFireApproveStatus(FireCallRegisterDTO entity, String status, String lastDecision) {
		
		TbCfcApplicationMstEntity cfcApplEntiry=new TbCfcApplicationMstEntity();
		FireCallRegister FireEntity = new FireCallRegister();
		TbFmComplainClosure FireCloserEntity = new TbFmComplainClosure();
		
		cfcApplEntiry.setApmApplicationId(entity.getCmplntId()); //Long.valueOf(entity.getCmplntNo())
		cfcApplEntiry.setRefNo(entity.getCmplntNo());
		cfcApplEntiry.setUpdatedDate(new Date());
		cfcApplEntiry.setLmoddate(new Date());
	    		
		FireEntity.setCmplntNo(entity.getCmplntNo());
		FireCloserEntity.setCmplntNo(entity.getCmplntNo());
		FireEntity.setUpdatedDate(new Date());
		FireCloserEntity.setUpdatedDate(new Date());
			    
		if(lastDecision.equals("REJECTED")){
			cfcApplEntiry.setApmAppRejFlag("R");
		    cfcApplEntiry.setRejectionDt(new Date());
		    cfcApplEntiry.setApmApplClosedFlag("C");
		    cfcApplEntiry.setApmApplicationDate(new Date());
		    FireEntity.setComplaintStatus("SB");
		    FireCloserEntity.setComplaintStatus("SB");
		    
		}
		
		else if(status.equals("APPROVED") && lastDecision.equals("PENDING")){
			cfcApplEntiry.setApmApplSuccessFlag("P");
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(new Date());
			FireEntity.setComplaintStatus("I");
			FireCloserEntity.setComplaintStatus("I");
		}
		else if(status.equals("APPROVED") && lastDecision.equals("CLOSED")){
			cfcApplEntiry.setApmApplSuccessFlag("C");
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag("O");
			cfcApplEntiry.setApmApplicationDate(new Date());
			FireEntity.setComplaintStatus("C");
			FireCloserEntity.setComplaintStatus("C");
		}
		
		
		tbCfcApplicationMstJpaRepository.save(cfcApplEntiry);
	}

	@Override
	@Transactional
	public void updateFireWorkFlowStatus(Long cmplntId, String taskNamePrevious, Long orgId) {
		fireCallRegisterRepository.updateWorkFlowStatus(cmplntId, orgId, taskNamePrevious);	
		fireCallClosureRepository.updateWorkFlowStatus(cmplntId, orgId, taskNamePrevious);	
	}

	    @SuppressWarnings("unchecked")
		@Override
		@Transactional
		public List<FireCallRegisterDTO> getAllVehiclesAssign(long orgId,long deptId) {
			   List<FireCallRegisterDTO> dtos = new ArrayList<FireCallRegisterDTO>();
	    	   FireCallRegisterDTO callRegister=null;
	    	   Map<String, String> requestParam = new HashMap<>();
			   requestParam.put("orgId", String.valueOf(orgId));
			   requestParam.put("deptId", String.valueOf(deptId));
			   DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
				dd.setParsePath(true);
				URI uri = dd.expand(ServiceEndpoints.VehicleManagement.VEHICLE_MASTER_BY_DEPT, requestParam);
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<List<GenVehicleMasterDTO>> rateResponse =
				        restTemplate.exchange(uri,
				                    HttpMethod.GET, null, new ParameterizedTypeReference<List<GenVehicleMasterDTO>>() {
				            });
				List<GenVehicleMasterDTO> masters = rateResponse.getBody();
				
				for (GenVehicleMasterDTO genVehicleMasterDTO : masters) {
					callRegister =new FireCallRegisterDTO();
					callRegister.setAssignVehicle(genVehicleMasterDTO.getVeId());
					callRegister.setVehNoDesc(genVehicleMasterDTO.getVeNo());
					dtos.add(callRegister);
				}
				
			return dtos;
		}
		
	
	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/search")
	@ApiOperation(value = "Get call by orgId and complainNo or complaint Status or fireStation", notes = "Get call by orgId and complainNo or complaint Status or fireStation", response = FireCallRegisterDTO.class, responseContainer = "List")
	public List<FireCallRegisterDTO> searchFireCallCloser(
			@ApiParam(value = "complain No", required = false) @QueryParam("complainNo") String complainNo,
			@ApiParam(value = "complaint Status", required = false) @QueryParam("complaintStatus") String complaintStatus,
			@ApiParam(value = "fireStation", required = false) @QueryParam("fireStation") String fireStation,
			@ApiParam(value = "Organisation Id", required = true) @QueryParam("orgId") Long orgid) {

		return StreamSupport.stream(fireCallRegisterDAO
				.searchFireCallCloser(complainNo, complaintStatus, fireStation, orgid).spliterator(), false)
				.map(entity -> {
					FireCallRegisterDTO dto = new FireCallRegisterDTO();
					BeanUtils.copyProperties(entity, dto);
					dto.setFireStationsAttendCallList(mapToList(dto.getFireStationsAttendCall()));
					dto.setCallAttendEmployeeList(mapToList(dto.getCallAttendEmployee()));
					return dto;
				}).collect(Collectors.toList());

	}
            


                 @Override
	public List<FireCallRegisterDTO> searchFireCallRegisterReg(Date fromDate, Date toDate, String fireStation,
			String complainNo, Long orgid, String status) {
		List<FireCallRegisterDTO> fireCallRegisterDTOList= new ArrayList<FireCallRegisterDTO>();
		List<FireCallRegister> list= fireCallRegisterDAO.searchFireCallRegisterReg(fromDate, toDate, fireStation, complainNo, orgid, status);
		
		list.forEach(entity->{
			FireCallRegisterDTO dto = new FireCallRegisterDTO();
			BeanUtils.copyProperties(entity, dto);
			dto.setDutyOfficer(String.valueOf(entity.getDutyOfficer()));
	        // Remove milliseconds from time start
		    String time = dto.getTime();
		    int lenth = time.trim().length();
		    String timehhmm = time.substring(0, lenth-3);
		    dto.setTime(timehhmm);
		    // Remove milliseconds from time end
			fireCallRegisterDTOList.add(dto);
		});
		
		 List<EmployeeBean> employeeList = employeeService.getAllEmployee(orgid);
		 
		 for(EmployeeBean emp:employeeList) {
		    	for(FireCallRegisterDTO fireDto:fireCallRegisterDTOList) {
		    		if(emp.getEmpId().toString().equals(fireDto.getDutyOfficer())) {
		    			fireDto.setDutyOfficer(emp.getFullName());
		    		}
		    	}
		    }
	
		
		return fireCallRegisterDTOList;
	}

	
	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/get/{orgId}/{complainNo}")
	@ApiOperation(value = "Get Complain by orgId and complainNo", notes = "Get Complain by Organisation Id and Complain No", response = FireCallRegisterDTO.class)
	public FireCallRegisterDTO findByCallCloserId(
			@ApiParam(value = "closer Id", required = true) @PathParam("closerId") Long closerId,
			@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgid) {
		
		TbFmComplainClosure closureEntity = fireCallClosureRepository.findByCloserId(closerId, orgid);
		FireCallRegisterDTO dto = new FireCallRegisterDTO();
		BeanUtils.copyProperties(closureEntity, dto);
		return dto;
	}



	@Override
	@Transactional(readOnly = true)
	public List<FireCallRegisterDTO> findByCloserCompId(Long compId, Long orgId) {
		List<FireCallRegisterDTO> listDto=new ArrayList<FireCallRegisterDTO>();
		List<TbFmComplainClosure> listEntity = fireCallClosureRepository.findByComId(compId, orgId);
		
		listEntity.forEach(entity->{
			FireCallRegisterDTO dto=new FireCallRegisterDTO();
			BeanUtils.copyProperties(entity, dto);
			listDto.add(dto);
		});
		
		return listDto;

	}



	@Override
	@Transactional(rollbackFor =Exception.class)
	public FireCallRegisterDTO getcloserDataAfterAprovalAndSaveInCallRegister(Long closerId, Long orgid) {
		 TbFmComplainClosure Closerentity = fireCallClosureRepository.findByClosureId(closerId, orgid);
		 FireCallRegister fireCallRegister=new FireCallRegister();
		 BeanUtils.copyProperties(Closerentity, fireCallRegister);
		 fireCallRegister.setCmplntId(Closerentity.getCmplntId());
		 fireCallRegister.setFireWFStatus("CLOSED");
		 fireCallRegisterRepository.save(fireCallRegister);
		return null;
	}


@Override
	public FireCallRegisterDTO getFireById(Long cmplntId) {
		FireCallRegister fireRegister = fireCallRegisterRepository.findOne(cmplntId);
		FireCallRegisterDTO fireRegisterDTO= new FireCallRegisterDTO();
		BeanUtils.copyProperties(fireRegister, fireRegisterDTO);
		fireRegisterDTO.setDutyOfficer(fireRegister.getDutyOfficer().toString());
		fireRegisterDTO.setCpdFireStationList(mapToList(fireRegisterDTO.getCpdFireStation()));
		if(fireRegister.getAssignVehicle()!=null && !fireRegister.getAssignVehicle().isEmpty())
		fireRegisterDTO.setAssignVehicleList(mapToList(fireRegister.getAssignVehicle()));
		return fireRegisterDTO;
	}


	@Override
	@Transactional(rollbackFor =Exception.class)
	public void updatecomplaintStatusInSB(Long cmplId, Long orgId, String status) {
		fireCallRegisterRepository.updateCallRegisterInProgress(cmplId, orgId, "SB");
		
	}
	
	
	@Override
	public List<Long> getEmpId(Long depId, Long orgid) {
		List<Long> empId=fireCallRegisterRepository.getEmpId(depId , orgid);
		
		return empId;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Long> getEmpIds(Long depId, Long orgid) {
		List<Long> empId=fireCallRegisterRepository.getEmpId(orgid, depId);
		
		return empId;
	}



	@Override
	public OccuranceBookDTO findAlloccurrences(String cmplntNo,Long orgId) {
		List<OccuranceBookEntity> entity=occurRepo.findAlloccurrences(cmplntNo,orgId);
		OccuranceBookDTO dto=new OccuranceBookDTO();
		if(CollectionUtils.isNotEmpty(entity)) {
		BeanUtils.copyProperties(entity.get(0), dto);
		}
		return dto;
	}



	@Override
	@Transactional(readOnly = true)
	public GenVehicleMasterDTO getVehicleByVehicleMasterId(Long vehicleId,Long orgId) {
		 GenVehicleMasterDTO dto=null;
		  String serverURI=ServiceEndpoints.VehicleManagement.VEHICLE_MASTER_BY_ID+vehicleId;
		  try {
				if(new URI(serverURI).isAbsolute()) {
					  ResponseEntity<?> response = RestClient.callRestTemplateClient(null, serverURI, HttpMethod.GET,Object.class);
					  if(response!=null && response.getStatusCode()==HttpStatus.OK && response.getBody()!=null) {
						  dto = (GenVehicleMasterDTO)RestClient.castResponse(response, GenVehicleMasterDTO.class);
						  }else {
							  LOGGER.info("Vehicle not found against vehicle id "+vehicleId);
						  }
				  }else {
					  LOGGER.info("URL is incorrect ");
				  }
			} catch (URISyntaxException e) {
				 LOGGER.info("Error while getting vehicle detail ",e);
			}
		return dto;
	}



	@Override
	@Transactional
	public void updateFinalCallClosureComment(String cmplntNo, String comment, Long orgId) {
		fireCallRegisterRepository.updateFinalCallClosureComment(cmplntNo,comment, orgId);	
		List<TbFmComplainClosure> list=fireCallClosureRepository.findByComplId(cmplntNo, orgId);
		fireCallClosureRepository.updateFinalCallClosureComment(list.get(0).getClosureId(),comment, orgId);
	}
	

}
