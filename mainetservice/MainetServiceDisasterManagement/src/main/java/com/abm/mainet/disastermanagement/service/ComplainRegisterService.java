package com.abm.mainet.disastermanagement.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
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
import com.abm.mainet.disastermanagement.constant.DisasterConstant;
import com.abm.mainet.disastermanagement.dao.IComplainRegisterDAO;
import com.abm.mainet.disastermanagement.domain.ComplainRegister;
import com.abm.mainet.disastermanagement.domain.ComplainScrutiny;
import com.abm.mainet.disastermanagement.dto.CallScrutinyDTO;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;
import com.abm.mainet.disastermanagement.repository.ComplainRegisterRepository;
import com.abm.mainet.disastermanagement.repository.ComplainScrutinyRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Service Implementation for managing ComplainRegister.
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.disastermanagement.service.IComplainRegisterService")
@Path(value = "/complainRegisterService")
@Api(value = "/complainRegisterService")
public class ComplainRegisterService implements IComplainRegisterService {

	@Autowired
	private final ComplainRegisterRepository complainRegisterRepository = null;
	
	@Autowired
	private final ComplainScrutinyRepository complainScrutinyRepository = null;
	
	@Autowired
	private IComplainRegisterDAO iComplainRegisterDAO;
	
	@Autowired
	private IEmployeeService iEmployeeService;
	
	
	 @Resource
	 private ILocationMasService iLocationMasService;

	
	  @Override
	  
	  @Transactional
	  
	  @POST
	  
	  @Path(value = "/save")
	  
	  @ApiOperation(value = "save Complain", notes = "save Complain", response =
	  ComplainRegisterDTO.class) public ComplainRegisterDTO save(@RequestBody
	  ComplainRegisterDTO complainRegisterDTO) { ComplainRegister master = new
	  ComplainRegister(); 
	  complainRegisterDTO.setComplainStatus("C");
	  BeanUtils.copyProperties(complainRegisterDTO, master);
	  master = complainRegisterRepository.save(master);
	  BeanUtils.copyProperties(master, complainRegisterDTO); 
      //sms & email start
	  
	  SMSAndEmailDTO dto = new SMSAndEmailDTO();
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		int langId = UserSession.getCurrent().getLanguageId();
	  dto.setAppNo(master.getComplainNo());
      dto.setAppDate(master.getCreatedDate().toString());
      dto.setCc(iLocationMasService.getLocationNameById(master.getLocation(), master.getOrgid()));

      List<ComplainScrutiny>  scrutinyList=iComplainRegisterDAO.getComplainScrutinyData(master.getOrgid(), master.getComplainNo());
      List<Long> employeIds =new ArrayList<>(); 
      
      for (ComplainScrutiny scrutinyObj:scrutinyList) {
    	  employeIds.add(scrutinyObj.getUserId());
      }
      
		List<Employee> mobileList = iEmployeeService.getEmpDetailListByEmpIdList(employeIds, complainRegisterDTO.getOrgid());
		for(Employee e:mobileList) {
			dto.setMobnumber(e.getEmpmobno()); 
			dto.setEmail(e.getEmpemail());  
			dto.setUserId(e.getEmpId());			
			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS("DM", "InjuryDetails.html", "GM", dto, organisation, langId);
		}

		//For Complainer 
		dto.setMobnumber(complainRegisterDTO.getComplainerMobile()); 
		dto.setEmail(UserSession.getCurrent().getEmployee().getEmpemail());  
		dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());			
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS("DM", "InjuryDetails.html", "GM", dto, organisation, langId);
		//sms & email end
	  
	  
		return complainRegisterDTO;
	  }
	 
	 
	 
	/*
	 * @Override
	 * 
	 * @Transactional
	 * 
	 * @POST
	 * 
	 * @Path(value = "/save")
	 * 
	 * @ApiOperation(value = "save Complain", notes = "save Complain", response =
	 * ComplainRegisterDTO.class) public ComplainRegisterDTO save(@RequestBody
	 * ComplainRegisterDTO complainRegisterDTO) { ComplainRegister master = new
	 * ComplainRegister(); SMSAndEmailDTO dto = new SMSAndEmailDTO(); Organisation
	 * organisation = UserSession.getCurrent().getOrganisation(); int langId =
	 * UserSession.getCurrent().getLanguageId(); List<String> emplist=new
	 * ArrayList<>(); BeanUtils.copyProperties(complainRegisterDTO, master); //sms &
	 * email start dto.setAppNo(master.getComplainNo());
	 * dto.setAppDate(master.getCreatedDate().toString()); master =
	 * complainRegisterRepository.save(master); //sms & email start
	 * dto.setAppNo(master.getComplainNo());
	 * dto.setAppDate(master.getCreatedDate().toString());
	 * 
	 * 
	 * emplist.add(StringUtils.substringBefore(complainRegisterDTO.
	 * getStrEmployeeList(), DisasterConstant.COMMA));
	 * 
	 * List<Long> employeIds = emplist .stream() .map(emp -> Long.valueOf(emp))
	 * .collect(Collectors.toList());
	 * 
	 * List<Employee> mobileList =
	 * iEmployeeService.getEmpDetailListByEmpIdList(employeIds,
	 * complainRegisterDTO.getOrgid());
	 * 
	 * for(Employee e:mobileList) { dto.setMobnumber(e.getEmpmobno());
	 * dto.setEmail(e.getEmpemail()); dto.setUserId(e.getEmpId());
	 * ApplicationContextProvider.getApplicationContext().getBean(
	 * ISMSAndEmailService.class).sendEmailSMS("DM", "InjuryDetails.html", "GM",
	 * dto, organisation, langId); } //For Complainer
	 * dto.setMobnumber(complainRegisterDTO.getComplainerMobile());
	 * dto.setEmail(UserSession.getCurrent().getEmployee().getEmpemail());
	 * dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	 * ApplicationContextProvider.getApplicationContext().getBean(
	 * ISMSAndEmailService.class).sendEmailSMS("DM", "InjuryDetails.html", "GM",
	 * dto, organisation, langId); //sms & email end
	 * 
	 * BeanUtils.copyProperties(master, complainRegisterDTO); return
	 * complainRegisterDTO; }
	 */
	 
	 
	 

	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/getAll/{orgId}")
	@ApiOperation(value = "Get All Complain", notes = "Get All Complain", response = ComplainRegisterDTO.class, responseContainer = "List")
	public List<ComplainRegisterDTO> findAll(
			@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgid) {
		return complainRegisterRepository.findByOrgid(orgid).stream().map(entity -> {
			ComplainRegisterDTO dto = new ComplainRegisterDTO();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/get/{Id}")
	@ApiOperation(value = "Get Complain by Id", notes = "Get Complain by Complain Id", response = ComplainRegisterDTO.class)
	public ComplainRegisterDTO findOne(@ApiParam(value = "Complain Id", required = true) @PathParam("Id") Long id) {
		ComplainRegister master = complainRegisterRepository.findOne(id);
		ComplainRegisterDTO dto = new ComplainRegisterDTO();
		BeanUtils.copyProperties(master, dto);
		return dto;
	}

	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/get/{orgId}/{complainNo}")
	@ApiOperation(value = "Get Complain by orgId and complainNo", notes = "Get Complain by Organisation Id and Complain No", response = ComplainRegisterDTO.class)
	public ComplainRegisterDTO findByComplainNo(
			@ApiParam(value = "complain No", required = true) @PathParam("complainNo") String complainNo,
			@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgid) {
		ComplainRegister master = complainRegisterRepository.findByComplainNoAndOrgid(complainNo, orgid);
		ComplainRegisterDTO dto = new ComplainRegisterDTO();
		BeanUtils.copyProperties(master, dto);
		return dto;
	}
	
	@Override
	public ComplainRegisterDTO getComplainById(Long complainId) {
		ComplainRegister injury = complainRegisterRepository.getComplainByComplainId(complainId);
		ComplainRegisterDTO complainRegisterDTO = new ComplainRegisterDTO();
		BeanUtils.copyProperties(injury, complainRegisterDTO);
		return complainRegisterDTO;

	}

	/**
	 * Method is used for initiate work flow
	 * 
	 * @param workflowActionDto
	 * @param workFlowId
	 * @param url
	 * @param workFlowFlag
	 * @return String
	 */
	
	@Override
	public List<ComplainRegisterDTO> getComplaintStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 @Override public List<CallScrutinyDTO> getDepartmentNameAndEmpName(Long complaintId) {
		  List<CallScrutinyDTO> listcallScrutinyDTO=new ArrayList<>(); List<Object []>
		  complains =complainRegisterRepository.fetchComplaints(complaintId);
		  for(Object obj[] :complains) { 
			  CallScrutinyDTO dto = new CallScrutinyDTO();
		  dto.setTbDepartment((String) obj[0] != null ?(String) obj[0]:"NA");
		  dto.setEmpName(((String) obj[1] != null ?(String) obj[1]:"NA")+MainetConstants.WHITE_SPACE + ((String) obj[9] != null ?(String) obj[9]:"NA"));
		  dto.setComplainStatus((String) obj[2]!= null ?(String) obj[2]:"NA");
		  dto.setComplaintRemark((String) obj[3] != null ?(String) obj[3]:"NA");
		  if(obj[5] != null ) {
		  dto.setCallAttendDate((Date)obj[5]);
		  }
		  dto.setCallAttendTime( obj[6] != null ? ((Time)obj[6]).toString().substring(0,5):"NA");
		  dto.setCallAttendEmployee(obj[7] != null ?(String) obj[7]:"NA");
		  dto.setReasonForDelay( obj[8] != null ?(String) obj[8]:"NA");
		  
		  listcallScrutinyDTO.add(dto); 
		  }
		  return listcallScrutinyDTO; 
		  }
	
	@Override public List<ComplainRegisterDTO> getAllComplaint(Long orgId, String complainStatus) { 

			  List<ComplainRegister> listOfComplaints = iComplainRegisterDAO.getComplainData(orgId,complainStatus); 

			  List<ComplainRegisterDTO> dtoList = new ArrayList<ComplainRegisterDTO>(); 
			  listOfComplaints.forEach(entity -> {
			  ComplainRegisterDTO dto = new ComplainRegisterDTO();
			  BeanUtils.copyProperties(entity, dto);
			  dtoList.add(dto);
			  });
			  return dtoList;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ComplainRegisterDTO> findInjuryDetails(Long complaintType1, Long complaintType2, Long location,
			String complainNo, Long orgId, String srutinyStatus) {

		/*List<ComplainRegister> list = iComplainRegisterDAO.searchInjuryDetails(complaintType1, complaintType2, location,
				complainNo, orgId,complainStatus);
		List<ComplainRegisterDTO> injurySummaryDTO = new ArrayList<ComplainRegisterDTO>();
		list.forEach(entity -> {
			ComplainRegisterDTO dto = new ComplainRegisterDTO();
			BeanUtils.copyProperties(entity, dto);
			LookUp lookup = CommonMasterUtility.getHierarchicalLookUp(entity.getComplaintType1(), orgId);
			LookUp lookup1 = CommonMasterUtility.getHierarchicalLookUp(entity.getComplaintType2(), orgId);
			dto.setCodDesc(lookup.getLookUpDesc());
			dto.setCodDesc1(lookup1.getLookUpDesc());
			injurySummaryDTO.add(dto);
		});
		return injurySummaryDTO;*/
		
		
		List<Object[]> objects = complainRegisterRepository.getComplaintClosureSummaryList(complaintType1, complaintType2,
				location, complainNo, orgId, srutinyStatus);
		List<ComplainRegisterDTO> complainRegisterDTOList = new ArrayList<ComplainRegisterDTO>();
		ComplainRegisterDTO complainRegisterDTO;
		for (Object[] complaintData : objects) {
			complainRegisterDTO = new ComplainRegisterDTO();
			complainRegisterDTO.setComplainId(Long.valueOf(complaintData[0].toString()));
			complainRegisterDTO.setComplainNo(complaintData[1].toString());
			complainRegisterDTO.setCodDesc(CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(complaintData[2].toString()), orgId).getLookUpDesc());
			complainRegisterDTO.setCodDesc1(CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(complaintData[3].toString()), orgId).getLookUpDesc());
			complainRegisterDTO.setComplaintDescription(complaintData[4].toString());
			if(complaintData[6]!=null) {
				complainRegisterDTO.setComplaintStatus(complaintData[6].toString());
			}
			complainRegisterDTOList.add(complainRegisterDTO);
		}
		return complainRegisterDTOList;
		
		
		
		
		
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
		complainRegisterRepository.updateComplainStatus(complainNo, status);

	}

	@Override
	@Transactional(readOnly=true)
	public ComplainRegisterDTO getComplainRegData(String complainId, Long orgId,String complainStatus) {
		ComplainRegister complainRegmaster = iComplainRegisterDAO.getComplainRegData(complainId, orgId,complainStatus);
		ComplainRegisterDTO complainRegDto = new ComplainRegisterDTO();
		BeanUtils.copyProperties(complainRegmaster, complainRegDto);
		return complainRegDto;
		
	}

	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/get/{orgId}/{complainNo}/{fromDate}/{toDate}")
	@ApiOperation(value = "Get Complain by orgId and complainNo and formDate and toDate ", notes = "Get Complain by Organisation Id and Complain No and from date and to date", response = ComplainRegisterDTO.class)
	public List<ComplainRegisterDTO> findByCompNoFrmDtToDt(
			@ApiParam(value = "complain No", required = true) @PathParam("complainNo") String complainNo,
			@ApiParam(value = "from Date", required = true) @PathParam("frmDate") Date frmDate,
			@ApiParam(value = "To From", required = true) @PathParam("toDate") Date toDate,
			@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgid,
			@ApiParam(value = "Form Status", required = true) @PathParam("formStatusStr") String formStatusStr,
			@ApiParam(value = "User Id", required = true) @PathParam("userId") Long userId,
			@ApiParam(value = "Dept Id", required = true) @PathParam("deptId") Long deptId) {
	
		List<ComplainRegisterDTO> list = new ArrayList<ComplainRegisterDTO>();
		List<Object[]> complainRegList = iComplainRegisterDAO.findByCompNoFrmDtToDate(complainNo, frmDate, toDate, orgid, formStatusStr, userId, deptId);
		//List<ComplainScrutiny> complainRegList = iComplainRegisterDAO.findByCompNoFrmDtToDate(complainNo, frmDate, toDate, orgid, formStatusStr, userId, deptId);
		
		for(Object[] obj : complainRegList){
			ComplainRegisterDTO dto = new ComplainRegisterDTO();
			dto.setComplainNo(String.valueOf(obj[0]));
			dto.setCreatedDate((Date)obj[1]);
			dto.setComplaintType1((Long)obj[2]);
			dto.setComplaintDescription(String.valueOf(obj[3]));
			dto.setComplainId(Long.parseLong(String.valueOf(obj[4])));
			LookUp compTypeDesc = CommonMasterUtility.getHierarchicalLookUp(dto.getComplaintType1(), orgid);		
			dto.setComplaintType1Desc(compTypeDesc.getLookUpDesc());
			list.add(dto);
		}
	
		return list;
	}

	@Override
	@Transactional
	public void updateComplainRegData(String complainNo, String status, String remark, Long orgid, Long empId,ComplainRegisterDTO complainRegisterDTO) {
		iComplainRegisterDAO.updateComplainRegData(complainNo, status, remark, orgid,empId,complainRegisterDTO);
	}

	@Override
	@Transactional(readOnly = true)
	@GET
	@Path(value = "/getAll/{orgId}/{userId}/{deptId}/{formStatusStr}")
	@ApiOperation(value = "Get All Complain", notes = "Get All Complain", response = ComplainRegisterDTO.class, responseContainer = "List")
	public List<ComplainRegisterDTO> getcomplaintData(@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgId,
			@ApiParam(value = "User Id", required = true) @PathParam("userId") Long userId,
			@ApiParam(value = "Department Id", required = true) @PathParam("deptId") Long deptId,
			@ApiParam(value = "Form_Status", required = true) @PathParam("formStatusStr") String formStatusStr) {
		
		List<ComplainRegisterDTO> list = new ArrayList<ComplainRegisterDTO>();
		List<Object[]> complainRegList = iComplainRegisterDAO.getcomplaintData(orgId, userId, deptId, formStatusStr);
		//List<ComplainRegister> complainRegList = iComplainRegisterDAO.getcomplaintData(orgId, userId, deptId);
		
		for(Object[] obj : complainRegList){
			ComplainRegisterDTO dto = new ComplainRegisterDTO();
			dto.setComplainNo(String.valueOf(obj[0]));
			dto.setCreatedDate((Date)obj[1]);
			dto.setComplaintType1((Long)obj[2]);
			dto.setComplaintDescription(String.valueOf(obj[3]));
			dto.setComplainId(Long.parseLong(String.valueOf(obj[4])));
			LookUp compTypeDesc = CommonMasterUtility.getHierarchicalLookUp(dto.getComplaintType1(), orgId);		
			dto.setComplaintType1Desc(compTypeDesc.getLookUpDesc());
			list.add(dto);
		}

		return list;
	}

	
	// Save Complain Registration Data
	@Transactional(rollbackFor = Exception.class)
	@WebMethod(exclude = true)
	@GET
	@POST
	@Path(value = "/saveComplainRegistration")
	@Override
	public void saveComplainRegistration(ComplainRegisterDTO complainRegisterDTO) {
		String formStatusStr = DisasterConstant.OPEN;
		
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		int langId = UserSession.getCurrent().getLanguageId();
		List<ComplainScrutiny> compScrtnylist = new ArrayList<ComplainScrutiny>();
		List<String>  deptlist=new ArrayList<>();
		List<String>  emplist=new ArrayList<>();
		int countVal=StringUtils.countMatches(complainRegisterDTO.getStrDepartmentList(),DisasterConstant.COMMA);
		if(countVal>0) {
			deptlist=Arrays.asList(complainRegisterDTO.getStrDepartmentList().split(DisasterConstant.COMMA));
			emplist=Arrays.asList(complainRegisterDTO.getStrEmployeeList().split(DisasterConstant.COMMA));			
		}
		else {
			deptlist.add(StringUtils.substringBefore(complainRegisterDTO.getStrDepartmentList(), DisasterConstant.COMMA));
			emplist.add(StringUtils.substringBefore(complainRegisterDTO.getStrEmployeeList(), DisasterConstant.COMMA));
		}
		if(deptlist.size()>0) {
			for(int i=0;i<deptlist.size();i++) {
				ComplainScrutiny compScr1 = new ComplainScrutiny();
				compScr1.setDeptId(Long.valueOf(deptlist.get(i).toString()));
				compScr1.setUserId(Long.valueOf(emplist.get(i).toString()));
				compScr1.setComplainNo(complainRegisterDTO.getComplainNo());
				compScr1.setComplainScrutinyStatus(DisasterConstant.PENDING);
				compScr1.setCreatedDate(new Date());
				compScr1.setOrgid(complainRegisterDTO.getOrgid());
				compScrtnylist.add(compScr1);				
			}
		}

		complainRegisterDTO.setComplainScrutinyLst(compScrtnylist);	
		ComplainRegister complainParentEntity = new ComplainRegister();
		// Save ComplainRegister
		BeanUtils.copyProperties(complainRegisterDTO, complainParentEntity);
		complainParentEntity.setcomplainStatus(DisasterConstant.OPEN);
		complainParentEntity.setDepartment(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid().toString());
		complainParentEntity.setComplainScrutiny(compScrtnylist);
        complainRegisterRepository.save(complainParentEntity);
        
        //sms & email start
        try {
		List<Long> employeIds = emplist
				.stream()
				.map(emp -> Long.valueOf(emp))
				.collect(Collectors.toList());
		
		List<Employee> mobileList = iEmployeeService.getEmpDetailListByEmpIdList(employeIds, complainRegisterDTO.getOrgid());
		String compTypeDesc =(CommonMasterUtility.getHierarchicalLookUp(complainRegisterDTO.getComplaintType2(), organisation.getOrgid()).getLookUpDesc());	
		
		for(Employee e:mobileList) {
			SMSAndEmailDTO dto = new SMSAndEmailDTO();
			dto.setAppNo(complainParentEntity.getComplainNo());
		    dto.setAppDate(complainParentEntity.getCreatedDate().toString());
		    dto.setCc(iLocationMasService.getLocationNameById(complainParentEntity.getLocation(), organisation.getOrgid()));
			dto.setMobnumber(e.getEmpmobno()); 
			dto.setEmail(e.getEmpemail());  
			dto.setUserId(e.getEmpId());
			dto.setType(compTypeDesc);
			ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS("DM", "ComplainRegister.html", "GM", dto, organisation, langId);
		}

		//For Complainer 
		SMSAndEmailDTO dto1 = new SMSAndEmailDTO();
		dto1.setAppNo(complainParentEntity.getComplainNo());
		dto1.setAppDate(complainParentEntity.getCreatedDate().toString());
		dto1.setCc(iLocationMasService.getLocationNameById(complainParentEntity.getLocation(), organisation.getOrgid()));
		dto1.setMobnumber(complainRegisterDTO.getComplainerMobile());   
		dto1.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		dto1.setType(compTypeDesc);
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS("DM", "ComplainRegister.html", "GM", dto1, organisation, langId);
		
		//For Complainer Mobile 2
		if(complainRegisterDTO.getComplainerMobile1()!=null && !complainRegisterDTO.getComplainerMobile1().isEmpty()) {
		SMSAndEmailDTO dto2 = new SMSAndEmailDTO();
		dto2.setAppNo(complainParentEntity.getComplainNo());
		dto2.setAppDate(complainParentEntity.getCreatedDate().toString());
		dto2.setCc(iLocationMasService.getLocationNameById(complainParentEntity.getLocation(), organisation.getOrgid()));
		dto2.setMobnumber(complainRegisterDTO.getComplainerMobile1());   
		dto2.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		dto2.setType(compTypeDesc);
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS("DM", "ComplainRegister.html", "GM", dto2, organisation, langId);
		 }
		}
        catch (Exception e) {
        	e.printStackTrace();
        }
		//sms & email end
		
	}
	
	@Override
	public void updateComplainRegistration(ComplainRegisterDTO complainRegList,
			ComplainRegisterDTO complainRegisterDTO) {
		List<String> deptlist = new ArrayList<>();
		List<String> emplist = new ArrayList<>();
		int countVal = StringUtils.countMatches(complainRegList.getStrDepartmentList(), DisasterConstant.COMMA);
		if (countVal > 0) {
			deptlist = Arrays.asList(complainRegList.getStrDepartmentList().split(DisasterConstant.COMMA));
			emplist = Arrays.asList(complainRegList.getStrEmployeeList().split(DisasterConstant.COMMA));
		} else {
			deptlist.add(
					StringUtils.substringBefore(complainRegList.getStrDepartmentList(), DisasterConstant.COMMA));
			emplist.add(StringUtils.substringBefore(complainRegList.getStrEmployeeList(), DisasterConstant.COMMA));
		}
		for (int i = 0; i < deptlist.size(); i++) {
			ComplainScrutiny compScr1 = new ComplainScrutiny();
			compScr1.setDeptId(Long.valueOf(deptlist.get(i).toString()));
			compScr1.setUserId(Long.valueOf(emplist.get(i).toString()));
			compScr1.setComplainNo(complainRegisterDTO.getComplainNo());
			compScr1.setComplainScrutinyStatus(DisasterConstant.PENDING);
			compScr1.setCreatedDate(new Date());
			compScr1.setOrgid(complainRegisterDTO.getOrgid());
			compScr1.setComplainId(complainRegisterDTO.getComplainId());
			complainScrutinyRepository.save(compScr1);
		}
	}

	
}
