package com.abm.mainet.common.leavemanagement.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.jws.WebMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IOrganisationDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.leavemanagement.dao.LeaveManagementDao;
import com.abm.mainet.common.leavemanagement.domain.TbLeaveGrantMaster;
import com.abm.mainet.common.leavemanagement.domain.TbLeaveRequest;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementApplyRequestDTO;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementApproveDTO;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementApproverDetailDTO;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementResponseDTO;
import com.abm.mainet.common.leavemanagement.repository.LeaveGrantMasterRepository;
import com.abm.mainet.common.leavemanagement.repository.LeaveRequestRepository;
import com.abm.mainet.common.master.dao.IDesignationDAO;
import com.abm.mainet.common.master.dao.IEmployeeDAO;
import com.abm.mainet.common.utility.Utility;

@Service
public class LeaveManagementServiceImpl implements LeaveManagementService{
	
	private static final Logger log = LoggerFactory.getLogger(LeaveManagementServiceImpl.class);

	@Autowired
	private LeaveGrantMasterRepository leaveGrantMasterRepository;
	
	@Autowired
	private LeaveRequestRepository  leaveRequestRepository;
	
	@Autowired
	IOrganisationDAO organisationDAO;
	@Autowired
	IEmployeeDAO employeeDAO;
	@Autowired
	IDesignationDAO iDesignationDAO;
	
	@Autowired
	private LeaveManagementDao leaveDao;
	

	
	@Override
	@WebMethod(exclude = true)
    @Transactional
	public List<String> applyLeave(LeaveManagementApplyRequestDTO leaveReqDTO) {
		List<String> errList = new ArrayList<String>();
	    TbLeaveRequest leaveRqstEntity = new TbLeaveRequest();		
	    BigDecimal calculatedLeaveCount = new BigDecimal("0");
		errList = this.validateApplyLeaveApplication(leaveReqDTO);
		if(errList.size() > 0) {
			return errList;
		}
		Map<String,Object> countOrErrorMap = this.getLeaveCountBasedOnFromAndToDate(leaveReqDTO);
		if(countOrErrorMap.containsKey(MainetConstants.LEAVEMANAGEMENT.LEAVE_COUNT)){
			calculatedLeaveCount =(BigDecimal)	countOrErrorMap.get(MainetConstants.LEAVEMANAGEMENT.LEAVE_COUNT);
		}else if (countOrErrorMap.containsKey(MainetConstants.LEAVEMANAGEMENT.LEAVE_ERR_LIST)) {
			errList.addAll(	(List<String>) countOrErrorMap.get(MainetConstants.LEAVEMANAGEMENT.LEAVE_ERR_LIST));
		}else {
			errList.add("Unable to Calculate the Leave Count");
		}
		
	   
	    if (calculatedLeaveCount.compareTo(BigDecimal.ZERO) <= 0) {
		errList.add("Applied leave count must be greater than 0");
	    }else {
	    	leaveRqstEntity.setNoOfDays(calculatedLeaveCount);
	    }
		Long leaveGrantastId = this.getLatestTbGrantMastId(leaveReqDTO.getOrgId(), leaveReqDTO.getEmpId());
		if(leaveGrantastId!=null) {
			leaveRqstEntity.setLeaveGrantId(leaveGrantastId);			
		}else {
			errList.add("Unable to fetch Leave Grant Id");
		}
		Organisation org =	organisationDAO.getOrganisationById(leaveReqDTO.getOrgId(), MainetConstants.STATUS.ACTIVE);
		Employee emp = employeeDAO.getEmployeeById(leaveReqDTO.getEmpId(), org, MainetConstants.IsDeleted.ZERO);
		if(emp == null) {
			errList.add("Please Enter correct EmpId wrto Organisation");
		}else {
			if(!(emp.getReportingManager().equals(leaveReqDTO.getApproverId()))) {
			errList.add("Please Enter correct ApproverId wrto Organisation");
			}
		}
		if(errList.size() > 0) {
			return errList;
		}
		leaveRqstEntity.setLeaveType(leaveReqDTO.getLeaveType());
		leaveRqstEntity.setLeaveApplyDate(new Date());
		leaveRqstEntity.setLeaveFromdt( Utility.stringToDate(leaveReqDTO.getLeaveFromdt()));
		leaveRqstEntity.setLeaveFromdtSession(leaveReqDTO.getLeaveFromdtSession());
		leaveRqstEntity.setLeaveTodt( Utility.stringToDate(leaveReqDTO.getLeaveTodt()));
		leaveRqstEntity.setLeaveTodtSession(leaveReqDTO.getLeaveTodtSession());
		leaveRqstEntity.setReason(leaveReqDTO.getReason());
		
		leaveRqstEntity.setEmpId(leaveReqDTO.getEmpId());		
		leaveRqstEntity.setOrgId(leaveReqDTO.getOrgId());
		leaveRqstEntity.setCreatedBy(leaveReqDTO.getEmpId());
		leaveRqstEntity.setCreatedDate(new Date());
		leaveRqstEntity.setLgIpMac(leaveReqDTO.getLgIpMac());
		leaveRqstEntity.setApproveFlag(MainetConstants.FlagN);
		leaveRqstEntity.setApproverId(leaveReqDTO.getApproverId());
	    try {
	    	if(errList.size() <= 0) {
	    	leaveRequestRepository.save(leaveRqstEntity);
	    	}
	    }catch (Exception e) {
	    	errList.add(MainetConstants.LEAVEMANAGEMENT.APPLY_LEAVE_EXCEPTION);
	    	
		}
	    return errList;
	}


	
	public Long getLatestTbGrantMastId(Long orgId, Long empId) {
		List<TbLeaveGrantMaster>  mast =	leaveGrantMasterRepository.getAllLeaveMasterBasedOnEmpAndOrgId(orgId, empId);
		 if (mast!=null && mast.size()>0) {
			return  mast.get(0).getLeaveGrantId();
		 }
		return null;
		 
	
	}




	@Override
	public Map<String, Object> getLeaveBalanceBasedOnType(Long orgId, Long empId) {
		Map<String, Object> countMap = new LinkedHashMap<String, Object>();
		Map<String, Object> categeoryWiseLeaveBalance = new LinkedHashMap<String, Object>();
		BigDecimal noLeave = new BigDecimal("0");
		Organisation org =	organisationDAO.getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);
		Employee emp = employeeDAO.getEmployeeById(empId, org, MainetConstants.IsDeleted.ZERO);
		List<TbLeaveGrantMaster>  mast =	leaveGrantMasterRepository.getAllLeaveMasterBasedOnEmpAndOrgId(orgId, empId);
		countMap.put(MainetConstants.LEAVEMANAGEMENT.EMPNAME, emp.getEmpname());
		countMap.put(MainetConstants.LEAVEMANAGEMENT.EMPDESIGNATION, emp.getDesignation().getDsgname());
		countMap.put(MainetConstants.LEAVEMANAGEMENT.EMPREPORTINGTO,emp.getReportingManager());
		countMap.put(MainetConstants.LEAVEMANAGEMENT.TOTALLEAVEBALANCE, mast.get(0).getNoOfLeavesGranted());
		categeoryWiseLeaveBalance.put(MainetConstants.LEAVEMANAGEMENT.CL,
				((mast != null && mast.size() > 0)) ? mast.get(0).getClLeaveBalance() : noLeave);
		categeoryWiseLeaveBalance.put(MainetConstants.LEAVEMANAGEMENT.PL,
				((mast != null && mast.size() > 0)) ? mast.get(0).getPlLeaveBalance() : noLeave);
		categeoryWiseLeaveBalance.put(MainetConstants.LEAVEMANAGEMENT.SL,
				((mast != null && mast.size() > 0)) ? mast.get(0).getSlLeaveBalance() : noLeave);
		categeoryWiseLeaveBalance.put(MainetConstants.LEAVEMANAGEMENT.ML,
				((mast != null && mast.size() > 0)) ? mast.get(0).getMlLeaveBalance() : noLeave);
		categeoryWiseLeaveBalance.put(MainetConstants.LEAVEMANAGEMENT.PIL,
				((mast != null && mast.size() > 0)) ? mast.get(0).getPlLeaveBalance() : noLeave);
		countMap.put(MainetConstants.LEAVEMANAGEMENT.CATEGEORYWISELEAVEBALANCE, categeoryWiseLeaveBalance);

		return countMap;
	}
	  

	@Override
	@Transactional(readOnly = true)
	public List<LeaveManagementApproverDetailDTO> fetchApproverData(Long approverId, Long orgId) {
		List<TbLeaveRequest> leaveList = leaveRequestRepository.fetchApproverDataByApproverId(approverId,orgId);
		Organisation org = organisationDAO.getOrganisationById(orgId, MainetConstants.STATUS.ACTIVE);
		Employee approver = employeeDAO.getEmployeeById(approverId, org, MainetConstants.IsDeleted.ZERO);
		
		List<Employee> empList = employeeDAO.getAllEmployeeByReportingManager(approverId, orgId, MainetConstants.IsDeleted.ZERO);
		List<LeaveManagementApproverDetailDTO> listDto = new ArrayList<LeaveManagementApproverDetailDTO>();
		if(!leaveList.isEmpty()) {
		leaveList.forEach(entity -> {
			LeaveManagementApproverDetailDTO dto = new LeaveManagementApproverDetailDTO();
			BeanUtils.copyProperties(entity, dto);
			for(Employee emp : empList) {
				if(emp.getEmpId()==entity.getEmpId()) {
					dto.setEmpName(emp.getEmpname());
					dto.setEmpDesignation(emp.getDesignation().getDsgname());
					dto.setReportingTo(approver.getEmpname());
				}
			}
			listDto.add(dto);
		});
		}
		return listDto;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public LeaveManagementResponseDTO updateLeaveApprovalData(LeaveManagementApproveDTO approveDto) {
		LeaveManagementResponseDTO responseDto = new LeaveManagementResponseDTO();
		TbLeaveRequest leaveRequestEntity = leaveRequestRepository.fetchDataByLeaveReqId(approveDto.getLeaveReqId(), approveDto.getOrgId());
				
		if (leaveRequestEntity != null && leaveRequestEntity.getApproveFlag().equals(MainetConstants.LEAVEMANAGEMENT.DEFAULT_FLAG)) {
			if (approveDto.getApproveFlag().equals(MainetConstants.LEAVEMANAGEMENT.APPROVED)) {
				TbLeaveGrantMaster leaveMasterEntity = leaveGrantMasterRepository.findOne(leaveRequestEntity.getLeaveGrantId());
				BigDecimal noOflLeavedays = getLeaves(leaveMasterEntity, leaveRequestEntity);
				BigDecimal noOfLeavesGranted = leaveMasterEntity.getNoOfLeavesGranted();
				BigDecimal LeavesGranted = noOfLeavesGranted.subtract(leaveRequestEntity.getNoOfDays());
				
				leaveMasterEntity.setNoOfLeavesGranted(LeavesGranted);
				leaveMasterEntity.setUpdatedBy(approveDto.getApproverId());
				leaveMasterEntity.setUpdatedDate(new Date());
				leaveMasterEntity.setLgIpMacUpd(approveDto.getLgIpMacUpd());
				leaveDao.updateLeave(noOflLeavedays, leaveMasterEntity, approveDto, leaveRequestEntity.getLeaveType());
				leaveRequestRepository.updateTbLeaveRequest(approveDto.getApproveFlag(), new Date(),
						approveDto.getApproverRemarks(), approveDto.getLgIpMacUpd(), approveDto.getApproverId(), new Date(),
						approveDto.getLeaveReqId(), approveDto.getOrgId());
				responseDto.setResponseCode(MainetConstants.FlagS);
				responseDto.setResponseMessage(MainetConstants.LEAVEMANAGEMENT.APPROVE_MSG);
			}else if(approveDto.getApproveFlag().equals(MainetConstants.LEAVEMANAGEMENT.REJECTED)) {
				leaveRequestRepository.updateTbLeaveRequest(approveDto.getApproveFlag(), new Date(),
						approveDto.getApproverRemarks(), approveDto.getLgIpMacUpd(), approveDto.getApproverId(), new Date(),
						approveDto.getLeaveReqId(), approveDto.getOrgId());
				responseDto.setResponseCode(MainetConstants.FlagS);
				responseDto.setResponseMessage(MainetConstants.LEAVEMANAGEMENT.REJECTED_MSG);
			}else {
				responseDto.setResponseCode(MainetConstants.FlagF);
				responseDto.setResponseMessage(MainetConstants.FAILURE_MSG);
			}
			
		}else {
			responseDto.setResponseCode(MainetConstants.FlagF);
			responseDto.setResponseMessage(MainetConstants.LEAVEMANAGEMENT.ALREADY_APPROVE);
		}
		
		return  responseDto;
	}
	  


 private BigDecimal getLeaves(TbLeaveGrantMaster leaveMasterEntity,TbLeaveRequest leaveRequestEntity) {
	 
	 BigDecimal leavedays = new BigDecimal(MainetConstants.Property.BIG_DEC_ZERO);
		switch (leaveRequestEntity.getLeaveType()) {
		case MainetConstants.LEAVEMANAGEMENT.ML:
			leavedays = leaveMasterEntity.getMlLeaveBalance();
			break;
		case MainetConstants.LEAVEMANAGEMENT.CL:
			leavedays = leaveMasterEntity.getClLeaveBalance();
			break;
		case MainetConstants.LEAVEMANAGEMENT.PL:
			leavedays = leaveMasterEntity.getPlLeaveBalance();
			break;
		case MainetConstants.LEAVEMANAGEMENT.PIL:
			leavedays = leaveMasterEntity.getPtLeaveBalance();
			break;
		case MainetConstants.LEAVEMANAGEMENT.SL:
			leavedays = leaveMasterEntity.getSlLeaveBalance();
			break;
		}
		BigDecimal noOflLeavedays = leavedays.subtract(leaveRequestEntity.getNoOfDays());
		return noOflLeavedays;
 }
	
public List<String> validateApplyLeaveApplication(LeaveManagementApplyRequestDTO leaveReqDto){
	List<String> validLevCounerrorList = new ArrayList<String>();
	
	if(leaveReqDto.getApproverId()== null  ) {
		validLevCounerrorList.add("Approver Id cannot be empty");
	}
	if (leaveReqDto.getEmpId() == null) {
		validLevCounerrorList.add("Emp Id cannot be empty");
	}
	if (leaveReqDto.getOrgId() == null) {
		validLevCounerrorList.add("Org Id cannot be empty");  
	}
	if (leaveReqDto.getLeaveType() == null || leaveReqDto.getLeaveType().equals("")) {
		validLevCounerrorList.add("Leave Type cannot be empty");
	}
	if (leaveReqDto.getLeaveFromdt() == null || leaveReqDto.getLeaveFromdt().equals("")) {
		validLevCounerrorList.add("Leave From Date cannot be empty");
	}
	if (leaveReqDto.getLeaveTodt() == null || leaveReqDto.getLeaveTodt().equals("")) {
		validLevCounerrorList.add("Leave To Date cannot be empty");
	}
	
	if (leaveReqDto.getLeaveFromdtSession() == null || leaveReqDto.getLeaveFromdtSession().equals("")) {
		validLevCounerrorList.add("Plese Enter From Date Leave Session");
	}else  {
        if(!(leaveReqDto.getLeaveFromdtSession().contentEquals(MainetConstants.LEAVEMANAGEMENT.LEAVE_TYPE_FH) || leaveReqDto.getLeaveFromdtSession().equals(MainetConstants.LEAVEMANAGEMENT.LEAVE_TYPE_SH))) {
        	validLevCounerrorList.add("Plese Enter valid From Date Leave Session");
		}
	}
	if (leaveReqDto.getLeaveTodtSession() == null || leaveReqDto.getLeaveTodtSession().equals("")) {
		validLevCounerrorList.add("Plese Enter  To Date Leave Session");
	}else {
		
		if(!(leaveReqDto.getLeaveTodtSession().contentEquals(MainetConstants.LEAVEMANAGEMENT.LEAVE_TYPE_FH) || leaveReqDto.getLeaveTodtSession().equals(MainetConstants.LEAVEMANAGEMENT.LEAVE_TYPE_SH))) {
        	validLevCounerrorList.add("Plese Enter valid To Date Leave Session");
		}
	}
	
	
	
	return validLevCounerrorList;
 }
public Map<String,Object> getLeaveCountBasedOnFromAndToDate(LeaveManagementApplyRequestDTO leaveReqDto ) {
	Date fmDate = null;
	Date tDate =null;
	List<String> validLevCounerrorList = new ArrayList<String>();
	Map<String,Object>  outputMap =new HashMap<String,Object>();
	try {
		fmDate = new SimpleDateFormat("dd/MM/yyyy").parse(leaveReqDto.getLeaveFromdt());
		tDate = new SimpleDateFormat("dd/MM/yyyy").parse(leaveReqDto.getLeaveTodt());
	} catch (ParseException e) {
		e.printStackTrace();
		validLevCounerrorList.add("Error occured while parsing dates please enter the correct formate");
	}
	if(fmDate.after(tDate)) {
		validLevCounerrorList.add("Please Enter Leave From Date grater than Leave To Date");
	}
		Calendar fromDate = Calendar.getInstance();
		fromDate.setTime(fmDate);
		Calendar toDate = Calendar.getInstance();
		toDate.setTime(tDate);
	    Long diffDays= TimeUnit.MILLISECONDS.toDays(Math.abs(fromDate.getTimeInMillis() - toDate.getTimeInMillis()));
	    BigDecimal count= new BigDecimal(diffDays);
	    if(diffDays == 0) {
	    	if(leaveReqDto.getLeaveFromdtSession().contentEquals(leaveReqDto.getLeaveTodtSession())) {
	    		count = count.add(new BigDecimal(0.5));
	    	}else if(leaveReqDto.getLeaveFromdtSession().contentEquals(MainetConstants.LEAVEMANAGEMENT.LEAVE_TYPE_SH) &&
	    			leaveReqDto.getLeaveTodtSession().contentEquals(MainetConstants.LEAVEMANAGEMENT.LEAVE_TYPE_FH) ) {
	    		validLevCounerrorList.add("Invalid Date, Leaves cannot be aplied from afternoon to morning");//invalid Date
	    	}else {
	    		count = count.add(new BigDecimal(1));
	    	}
	    } else if(diffDays >0) {	    	
        	if(leaveReqDto.getLeaveFromdtSession().contentEquals(MainetConstants.LEAVEMANAGEMENT.LEAVE_TYPE_FH) &&
	    			leaveReqDto.getLeaveTodtSession().contentEquals(MainetConstants.LEAVEMANAGEMENT.LEAVE_TYPE_SH)) {
        		count = count.add(new BigDecimal(1));
	    	}
        	else if(leaveReqDto.getLeaveFromdtSession().contentEquals(leaveReqDto.getLeaveTodtSession())) {
	    		count = count.add(new BigDecimal(0.5));
	    	}
	    }	
	   
	   
	    if(validLevCounerrorList.size()<=0) {
	    	 outputMap.put(MainetConstants.LEAVEMANAGEMENT.LEAVE_COUNT, count);
	    }else {
	    	 outputMap.put(MainetConstants.LEAVEMANAGEMENT.LEAVE_ERR_LIST, validLevCounerrorList);
	    }
	    return outputMap;
	}


}


		
