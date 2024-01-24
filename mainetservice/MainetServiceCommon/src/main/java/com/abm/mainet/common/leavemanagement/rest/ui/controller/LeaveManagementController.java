package com.abm.mainet.common.leavemanagement.rest.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementApplyRequestDTO;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementApproveDTO;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementApproverDetailDTO;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementResponseDTO;
import com.abm.mainet.common.leavemanagement.service.LeaveManagementService;
import com.abm.mainet.common.utility.Utility;

import io.swagger.annotations.Api;
@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/leaveManagementController")
@Produces("application/json")
@WebService
@Api(value = "/leaveManagementController")
@Path("/leaveManagementController")
public class LeaveManagementController {
	private static final Logger logger = Logger.getLogger(LeaveManagementController.class);
	
	@Autowired
	private LeaveManagementService leaveManagementService;
	
	
	@RequestMapping(value = "/applyLeave", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@POST
	@Path("/applyLeave")
	public LeaveManagementResponseDTO applyLeave(@RequestBody LeaveManagementApplyRequestDTO leaveReqDto ) {
		LeaveManagementResponseDTO resDTO = new LeaveManagementResponseDTO();		
		try {
			List<String> errList = leaveManagementService.applyLeave(leaveReqDto);
			if (errList.size() <= 0) {
				resDTO.setResponseCode(MainetConstants.FlagS);
				resDTO.setResponseMessage(MainetConstants.LEAVEMANAGEMENT.APPLY_LEAVE_SUCCESS);
			} else {
				resDTO.setResponseCode(MainetConstants.FlagF);
				resDTO.setResponseMessage(Utility.getMapper().writeValueAsString(errList));
			}
		} catch (final Exception ex) {
			resDTO.setResponseCode(MainetConstants.FlagF);
			resDTO.setResponseMessage(MainetConstants.LEAVEMANAGEMENT.APPLY_LEAVE_EXCEPTION);
			logger.error("problem occurred while request for LeaveManagementController::applyLeave", ex);
		}

		return resDTO;

	}
	
	
	@RequestMapping(value = "/leaveBalance/empId/{empid}/orgId/{orgid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GET
	@Path("/leaveBalance/empId/{empid}/orgId/{orgid}")
	public Map<String,Object> getLeaveBalance( @PathVariable("empid") @PathParam("empid") Long empid,@PathVariable("orgid") @PathParam("orgid") Long orgid) {		
 		try {
 			 return	leaveManagementService.getLeaveBalanceBasedOnType(orgid,empid);
		} catch (final Exception ex) { 
			Map<String,Object> res = new LinkedHashMap<String,Object>();
			res.put(MainetConstants.ResponseCode, MainetConstants.FlagF);
			res.put(MainetConstants.ResponseMessage, MainetConstants.LEAVEMANAGEMENT.LEAVE_BALANCE_EXCEPTION);		
			logger.error("problem occurred while request for LeaveManagementController::getLeaveBalance", ex);
			return res;
		}

	}
	
	@RequestMapping(value = "/fetchApproverData/approverId/{approverId}/orgId/{orgId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.ALL_VALUE)
	@ResponseBody
	@Path("/fetchApproverData/approverId/{approverId}/orgId/{orgId}")
	@ResponseStatus(HttpStatus.OK)
	@GET
	public List<LeaveManagementApproverDetailDTO> fetchApproverData(@PathVariable("approverId") @PathParam("approverId") Long approverId,@PathVariable("orgId") @PathParam("orgId") Long orgId) {
		logger.info("Enter the fetchApproverData  Method in LeaveManagementController");
		List<LeaveManagementApproverDetailDTO>  dtoList = new ArrayList<LeaveManagementApproverDetailDTO>();
		
		try {
			dtoList = leaveManagementService.fetchApproverData(approverId,orgId);
		} catch (Exception exp) {
			logger.error("Exception in fetchApproverData" + exp);
		}
		
		return dtoList;
	}
	
	
	@RequestMapping(value = "/updateLeaveApprovalData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@POST
	@Path("/updateLeaveApprovalData")
	public ResponseEntity<?> updateLeaveApprovalData(@RequestBody LeaveManagementApproveDTO approveDto) {
		logger.info("Enter the updateLeaveApprovalData  Method in LeaveManagementController");
		ResponseEntity<?> responseEntity = null;
		LeaveManagementResponseDTO responseDto = new LeaveManagementResponseDTO();
		try {
			responseDto=leaveManagementService.updateLeaveApprovalData(approveDto);
		
		responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseDto);
		}catch (Exception exp) {
			logger.error("Exception in updateLeaveApprovalData" + exp);
			responseDto.setResponseCode(MainetConstants.FlagF);
			responseDto.setResponseMessage(MainetConstants.FAILURE_MSG);
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
		}
		
		return responseEntity;
	}

	
	
}
