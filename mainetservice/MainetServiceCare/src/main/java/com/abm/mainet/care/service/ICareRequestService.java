package com.abm.mainet.care.service;

import java.util.List;
import java.util.Set;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.care.domain.CareDepartmentAction;
import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.domain.ComplaintAcknowledgementModel;
import com.abm.mainet.care.domain.EscalationDetailsList;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintSearchDTO;
import com.abm.mainet.care.dto.ComplaintTaskDTO;
import com.abm.mainet.care.dto.DepartmentComplaintDTO;
import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;
import com.abm.mainet.care.dto.DepartmentDTO;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbComparentMas;
import com.abm.mainet.common.workflow.domain.ActionResponse;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskSearchRequest;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface ICareRequestService {

    ActionResponse startCareProces(CareRequest careRequest, WorkflowTaskAction startAction, Long loggedInLocId) throws Exception;

    ActionResponse updateCareProces(CareRequest careRequest, WorkflowTaskAction updateAction) throws Exception;

    ActionResponse restartCareProces(CareRequest careRequest, WorkflowTaskAction reopenAction) throws Exception;

    ActionResponse resubmitCareProces(CareRequest careRequest, WorkflowTaskAction resubmitAction) throws Exception;

    RequestDTO getApplicationDetails(CareRequest careRequest);

    RequestDTO getApplicationDetailsByMobile(String mobileNumber);

    CareRequest findByApplicationId(Long applicationId);

    CareRequest findByComplaintId(String complaintId);

    @GET
    @ApiOperation(value = "Fetch care request by id", notes = "Fetch care request id", response = CareRequestDTO.class)
    @Path("/careRequest/{id}")
    CareRequestDTO findById(@ApiParam(value = "CareRequest Id", required = true) @PathParam("id") Long id);

    @GET
    @ApiOperation(value = "Fetch care request by empId and emplType", notes = "Fetch care request by empId and emplType", response = CareRequestDTO.class)
    @Path("/careRequest/empId/{empId}/emplType/{emplType}")
    List<CareRequestDTO> getCareRequestsByEmpIdAndEmplType(
            @ApiParam(value = "Employee Id", required = true) @PathParam("empId") Long empId,
            @ApiParam(value = "Employee type", required = true) @PathParam("emplType") Long emplType);

    @POST
    @ApiOperation(value = "Search care request by various fields", notes = "Search care request by various fields")
    @Path("/careRequest/search")
    List<CareRequestDTO> findComplaint(
            @ApiParam(value = "Complaint search filters", required = true) @RequestBody ComplaintSearchDTO filter);

    String resolveWorkflowTypeDefinition(@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgId,
            @ApiParam(value = "Complaint Type Id", required = true) @PathParam("compTypeId") Long compTypeId);

    @GET
    @ApiOperation(value = "Fetch DepartmentList by orgId for Care", notes = "Fetch DepartmentList by orgId for Care", response = DepartmentComplaintDTO.class, responseContainer = "List")
    @Path("/getCareDepartmentList/orgId/{orgId}")
    Set<DepartmentComplaintDTO> getDepartmentComplaintsByOrgId(
            @ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgId);

    Set<DepartmentDTO> getCareWorkflowMasterDefinedDepartmentListByOrgId(Long orgId);

    @GET
    @ApiOperation(value = "Fetch ComplaintTypeList by deptId & orgId", notes = "Fetch ComplaintTypeList by deptId & orgId", response = DepartmentComplaintTypeDTO.class, responseContainer = "List")
    @Path("/getComplaintTypeList/deptId/{deptId}/orgId/{orgId}")
    Set<DepartmentComplaintTypeDTO> getDepartmentComplaintTypeByDepartmentId(
            @ApiParam(value = "Department Id", required = true) @PathParam("deptId") Long deptId,
            @ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgId);

    List<EscalationDetailsList> getEscalationDetailsList(WorkflowMas workflowType, WorkflowRequest workflowRequest,
            long langId, Long applicationId);

    List<ComplaintTaskDTO> getOpenComplaintTaskByEmployeeId(TaskSearchRequest requester) throws Exception;

    List<WorkflowTaskActionWithDocs> getCareWorkflowActionLogByApplicationId(Long applicationId, Long orgId,
            long langId);

    List<LocationMasEntity> getDepartmentCompalintLocations(Long orgId, Long deptId);

    ComplaintAcknowledgementModel getComplaintAcknowledgementModel(CareRequest careRequest, long langId);

    List<OrganisationDTO> getOrganisationByDistrict(Long districtCpdId);

    Long getPrefixLevel(String prefix, Long orgId);

    List<TbComparentMas> getPrefixLevelData(String prefix, Long orgId);

    List<CareRequestDTO> findComplaintDetails(ComplaintSearchDTO filter);

    List<CareRequestDTO> findComplaintDetailsForCareOperatorRole(ComplaintSearchDTO filter);

    @GET
    @ApiOperation(value = "Fetch EmployeeLIst by orgId & DeptId", notes = "Fetch EmployeeLIst by orgId & DeptId")
    @Path("/getCareEmpList/orgId/{orgId}/deptId/{deptId}")
    @Produces(MediaType.APPLICATION_JSON)
    Object getAllEmployeeByDept(@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgId,
            @ApiParam(value = "Department Id", required = true) @PathParam("deptId") Long deptId);

    @GET
    @ApiOperation(value = "Get Operational WardZone prefix name by orgId & DeptId", notes = "Get Operational WardZone prefix name by orgId & DeptId")
    @Path("/getCareOprWardZonePrefixName/orgId/{orgId}/deptId/{deptId}")
    @Produces(MediaType.APPLICATION_JSON)
    Object getOperationalWardZonePrefixName(@ApiParam(value = "Department Id", required = true) @PathParam("deptId") Long deptId,
            @ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgId);

    @GET
    @ApiOperation(value = "Search if wardzone wise workflow is defined", notes = "Search if wardzone wise workflow is defined")
    @Path("/getWardZoneRequireDetail/orgId/{orgId}/compTypeId/{compTypeId}")
    @Produces(MediaType.APPLICATION_JSON)
    Object resolveWorkflowAndGetLocType(@ApiParam(value = "Organisation Id", required = true) @PathParam("orgId") Long orgId,
            @ApiParam(value = "Complaint Type Id", required = true) @PathParam("compTypeId") Long compTypeId);
    
    boolean callSWMWorkForceAPI(CareRequest careRequest);

	boolean callPotHoleAPI(CareRequest careRequest);

	String callSwacchBharatAPI(CareRequest careRequest);

	List<CareDepartmentAction> findByCareId(Long id);

}