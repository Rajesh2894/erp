
package com.abm.mainet.mobile.controller;

import java.util.List;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.domain.OpinionPollOptionResponse;
import com.abm.mainet.cms.dto.OpinionDTO;
import com.abm.mainet.cms.dto.OpinionPollDTO;
import com.abm.mainet.mobile.dto.CommonAppResponseDTO;
import com.abm.mainet.mobile.service.IOpinionPollService;

@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/opinionpoll")
public class OpinionPollController {

	private static final Logger LOG = Logger.getLogger(OpinionPollController.class);

	@Autowired
	private IOpinionPollService iOpinionPollService;
	

	@RequestMapping(value = "/ActivePollRsult/poll/{id} ", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<OpinionPollDTO> getOpinionPollResponse(@PathVariable long id) {
		try {
			
			final List<OpinionPollDTO> organisationsList = iOpinionPollService.getOpinionPollResponse(id);
			return organisationsList;
		} catch (final Exception exception) {
			LOG.error("Exception occure during fetching Organisations List");
			return null;
		}
		
	}
	
	@RequestMapping(value = "/ActivePoll/org/{orgid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public OpinionDTO getOpinionPoll(@PathVariable long orgid) {
		try {
			return iOpinionPollService.getCitizenOpinion(orgid);
			
		} catch (final Exception exception) {
			LOG.error("Exception occure during fetching Organisations List");
			return null;
		}
		
	}

	@RequestMapping(value = "/response/org/{orgid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public boolean saveOpinionPoll(@PathVariable long orgid,@RequestBody @Valid final OpinionPollOptionResponse opinionPollOptionResponse) {
		try {
			return iOpinionPollService.saveOpinionPoll(orgid,opinionPollOptionResponse);
			
		} catch (final Exception exception) {
			LOG.error("Exception occure during fetching Organisations List");
			return false;
		}
		
	}
	
	@RequestMapping(value = "/innovativeIdea/{orgid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object saveInnovativeIdea(@PathVariable long orgid, @RequestBody @Valid final Feedback feedback) {
		CommonAppResponseDTO responseDTO = new CommonAppResponseDTO();
		String errorMsg = "";
		boolean status = false;
		try {
			errorMsg += ((feedback.getFdUserName() != null && feedback.getFdUserName() != "") ? ""
					: ",User Name cannot be empty");
			errorMsg += ((feedback.getMobileNo() != null && feedback.getMobileNo() != "") ? ""
					: ",Mobile Number cannot be empty");
			errorMsg += ((feedback.getEmailId() != null && feedback.getEmailId() != "") ? ""
					: ",Email cannot be empty");
			errorMsg += ((feedback.getFeedBackDetails() != null && feedback.getFeedBackDetails() != "") ? ""
					: ",Comments cannot be empty");
			errorMsg += ((feedback.getFeedBackSubject() != null && feedback.getFeedBackSubject() != "") ? ""
					: ",User Name cannot be empty");
			if (errorMsg.isEmpty()) {
				status = iOpinionPollService.saveInnovativeIdea(orgid, feedback);
				if (status == true) {
					responseDTO.setResponseMsg("Thank You For Your Valuable Idea.");
					responseDTO.setHttpstatus(HttpStatus.OK);
				} else {
					responseDTO.setErrorMsg("Please check the details, something went wrong");
					responseDTO.setHttpstatus(HttpStatus.BAD_REQUEST);
				}

			} else {
				responseDTO.setErrorMsg(errorMsg.substring(1, errorMsg.length()));
				responseDTO.setHttpstatus(HttpStatus.BAD_REQUEST);

			}
			return responseDTO;
		} catch (final Exception exception) {
			LOG.error("Exception occure during fetching Organisations List");
			responseDTO.setErrorMsg("Internal server error");
			responseDTO.setHttpstatus(HttpStatus.BAD_REQUEST);
			return responseDTO;
		}

	}

}