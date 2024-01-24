package com.abm.mainet.common.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicationStatusDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Arun.Chavda
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface ApplicationService {

	/*
	 * Before passing requestDto set all field whatever required in create
	 * applicationNo
	 */
	public Long createApplication(RequestDTO requestDto);

	public void updateApplication(TbCfcApplicationMstEntity applicationMaster);

	@POST
	@ApiOperation(value = "Fetch application status by applicationId", notes = "Fetch application status by applicationId", response = ApplicationStatusDTO.class)
	@Path("/status/applicationId/{applicationId}/lang/{langId}")
	ApplicationStatusDTO getApplicationStatus(
			@ApiParam(value = "Application Id", required = true) @PathParam("applicationId") long applicationId,
			@ApiParam(value = "Language Id", required = true) @PathParam("langId") short langId);

	public void updateApplicationForSupplementaryBill(RequestDTO requestDto);

	String getPaymentStatusFlagByApplNo(Long applNo, Long orgid);

	String getLicenseNoByAppId(Long applicationId, Long orgId);

	@POST
	@ApiOperation(value = "Fetch Organisation Id by applicationId", notes = "Fetch Organisation Id by applicationId", response = Long.class)
	@Path("/getOrgId/applicationId/{applicationId}")
	Long getOrgId(@ApiParam(value = "Application Id", required = true) @PathParam("applicationId") Long applicationId);
	
	@POST
	@ApiOperation(value = "Fetch applicant name by applicationId", notes = "Fetch applicant name by applicationId", response = String.class)
	@Path("/getApplicantName/applicationId/{applicationId}/orgId/{orgId}")
	String getApplicantName(@ApiParam(value = "Application Id", required = true) @PathParam("applicationId") Long applicationId,
			@ApiParam(value = "orgId", required = true) @PathParam("orgId") long orgId);

}
