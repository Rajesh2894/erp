/**
 * @author  : Harshit kumar
 * @since   : 20 Feb 2018
 * @comment : Interface for RTI Application Service        
 */

package com.abm.mainet.rti.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.cfc.objection.domain.TbObjectionEntity;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.rti.domain.TbRtiApplicationDetails;
import com.abm.mainet.rti.domain.TbRtiMediaDetails;
import com.abm.mainet.rti.dto.FormEReportDTO;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.dto.RtiFrdEmployeeDetails;
import com.abm.mainet.rti.dto.RtiMediaListDTO;
import com.abm.mainet.rti.dto.RtiRemarksHistDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javaxt.utils.string;

@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface IRtiApplicationDetailService extends Serializable {

	@POST
	@Path("/saveRTIApplication")
	@ApiOperation(value = "Save RTI Application", notes = "Save RTI Application", response = RtiApplicationFormDetailsReqDTO.class)
	RtiApplicationFormDetailsReqDTO saveApplication(
			@ApiParam(value = "RtiApplicationFormDetailsReqDTO", required = true) @RequestBody RtiApplicationFormDetailsReqDTO requestDTO);

	String generateRtiApplicationNumber(Long applicationId, Long serviceId, Long orgId);

	// D#77334
	RtiApplicationFormDetailsReqDTO saveServiceApplication(RtiApplicationFormDetailsReqDTO rtiDto);

	List<LookUp> getActiveDepartment(long orgId);

	@POST
	@ApiOperation(value = "get department location", notes = "get department location")
	@Path("/getDeptLocation/orgId/{orgId}/deptId/{deptId}")
	Set<LookUp> getDeptLocation(@ApiParam(value = "Organization Id", required = true) @PathParam("orgId") Long orgId,
			@ApiParam(value = "Department Id", required = true) @PathParam("deptId") Long deptId);

	Boolean saveRtiApplication(RtiApplicationFormDetailsReqDTO requestDTO);

	RtiApplicationFormDetailsReqDTO fetchRtiApplicationInformationById(Long applicationId, Long orgId);

	String getlocationNameById(Long locId, Long orgid);

	String getdepartmentNameById(Long deptId);

	Boolean saveRtiMediaList(TbRtiMediaDetails tbRtiMediaDetails);

	List<RtiMediaListDTO> getMediaList(Long long1, Long orgId);

	/**
	 * this service is used for get applicable tax for RTI service
	 * 
	 * @param requestDTO
	 * @return WSResponseDTO with model applicable tax details
	 */
	WSResponseDTO getApplicableTaxes(@RequestBody WSRequestDTO requestDTO);

	/**
	 * this service is used for get Service charges from BRMS.
	 * 
	 * @param requestDTO
	 * @return WSResponseDTO with ChargeDetailDTO for RTI service
	 */
	WSResponseDTO getApplicableCharges(@RequestBody WSRequestDTO requestDTO);

	List<FormEReportDTO> getRTIApplicationDetail(long apmApplicationId, Organisation organisation);

	Map<String, Long> getApplicationNumberByRefNo(String rtiNo, Long serviceId, Long orgId, Long empId);

	CommonChallanDTO getCharges(Long serviceId, Long orgId, String refNo);

	String getBplType(String rtiNo, Long orgId);

	List<RtiApplicationFormDetailsReqDTO> getRTIApplicationDetailBy(Long OrgId);

	List<RtiApplicationFormDetailsReqDTO> getRtiDetails(Long applicationId, Long orgId);

	public RtiApplicationFormDetailsReqDTO saveSecondApplication(
			@RequestBody RtiApplicationFormDetailsReqDTO requestDTO);

	List<RtiApplicationFormDetailsReqDTO> getForwardDeptList(Long ForwardDeptId, Long orgId);

	RtiApplicationFormDetailsReqDTO saveAppealHistory(RtiApplicationFormDetailsReqDTO dto);

	public TbRtiApplicationDetails getLoiIdByLoiNumber(Long Loinumber, Long orgId);

	int inactiveMedia(Long rtiId, Long orgId);

	RtiApplicationFormDetailsReqDTO getCurentTime(RtiApplicationFormDetailsReqDTO saveDto);

	// RtiApplicationFormDetailsReqDTO getServerTime(RtiApplicationFormDetailsReqDTO
	// saveDto);

	int updateLoiMasterAmount(BigDecimal loiAmount, Long loiApplicationId);

	int updateLoiDetailsAmount(BigDecimal loiDetailsAmt, BigDecimal loiPrevAmt, String loiRemarks, Long loiId);

	TbLoiMasEntity getLoiIdbyAppno(Long applicationId);

	ObjectionDetailsDto fetchRtiAppDetailByRefNo(String objectionReferenceNumber, Long orgId);

	Boolean checkWorkflowIsNotDefine(Organisation org, Long orgId, String serviceCode);

	public TbObjectionEntity getObjectionDetailByApplicationId(Long orgId, Long appId, String rtifirstappeal);

	public void setSecondAppealDataByRtiNo(RtiApplicationFormDetailsReqDTO reqDto);

	public List<DocumentDetailsVO> getCheckListData(Long appId, FileNetApplicationClient fClient, UserSession session);

	RequestDTO getApplicationDetailsByMobile(String mobileNumber);

	String generateDispatchNo(Date estimateDate, Long orgId, Long deptId);



	String generateTokenNo(Long applicationId, Long orgId);

	Boolean checkWoflowDefinedOrNot(RtiApplicationFormDetailsReqDTO mastDto);


	String resolveWorkflowTypeDefinition(Long orgId, Long compTypeId);

	WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

	Boolean saveRtiApplicationHistory(RtiApplicationFormDetailsReqDTO requestDTO);

	Boolean setWorkflowData(WorkflowTaskAction workflowAction, WorkflowProcessParameter workflowdto,
			RtiApplicationFormDetailsReqDTO reqDto);


	List<RtiRemarksHistDto> getRtiActionLogByApplicationId(Long applicationId, Long orgId, long langId);

	void initiateWorkFlowForFrdEmploye(RtiApplicationFormDetailsReqDTO reqDTO, Long deptId, String remList);

	List<RtiMediaListDTO> setLoiDetails(Long applicationId, long orgid);

	List<RtiFrdEmployeeDetails> setRtiFwdEmployeeDetails(Long applicationId);


}
