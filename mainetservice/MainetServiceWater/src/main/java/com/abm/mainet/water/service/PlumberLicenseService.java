package com.abm.mainet.water.service;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.PlumberRenewalRegisterMaster;
import com.abm.mainet.water.domain.TbPlumberExperience;
import com.abm.mainet.water.domain.TbPlumberQualification;
import com.abm.mainet.water.dto.PlumLicenseRenewalSchDTO;
import com.abm.mainet.water.dto.PlumberExperienceDTO;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;
import com.abm.mainet.water.dto.PlumberQualificationDTO;

/**
 * @author Arun.Chavda
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface PlumberLicenseService {

	PlumberLicenseResponseDTO savePlumberLicenseDetails(PlumberLicenseRequestDTO requestDTO);

	PlumberMaster getPlumberDetailsByAppId(long applicationId, Long orgId);

	List<PlumberQualificationDTO> getPlumberQualificationDetails(Long plumberId, Long orgId);

	List<PlumberExperienceDTO> getPlumberExperienceDetails(Long plumberId, Long orgId);

	WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

	Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId);

	void updatedPlumberLicenseDetailsOnScrutiny(PlumberMaster plumberMaster,
			List<TbPlumberQualification> qualificationDTO, List<TbPlumberExperience> experienceDTO);

	void updatedQualAndExpIsDeletedFlag(String[] qualRowId, String[] expRowId);

	PlumLicenseRenewalSchDTO updatedUserTaskAndPlumberLicenseExecutionDetails(PlumberMaster master,
			WorkflowProcessParameter taskDefDto);

	void initiateWorkFlowForFreeService(PlumberLicenseRequestDTO requestDTO);

	// void savePlumberDataEntrySuite(PlumberMastDto masDto);

	boolean executeWfAction(WorkflowTaskAction wfAction, String eventName, Long serviceId);

	PlumberLicenseRequestDTO getPlumberDetailsByLicenseNumber(Long orgId, String licenseNumber);

	PlumberLicenseResponseDTO savePlumberRenewalData(PlumberLicenseRequestDTO plumDto);

	PlumberLicenseRequestDTO savePlumberLicenseRenewDataAndAttachFile(PlumberLicenseRequestDTO requestDTO);

	boolean loiCalculation(Long applicationId, Long serviceId, Long orgId, Long userUId, String serviceName,
			Date licTodate);

	Map<Long, Double> getLoiCharges(Long applicationId, Long serviceId, Long orgId, String serviceName, Date licTodate);

	PlumberLicenseRequestDTO getValidDates(Long applicationId);

	Map<Long, Double> getRenewalLoiCharges(final Long applicationId, final Long serviceId, final Long orgId,
			String serviceCode, Date licTodate);

	boolean updateValiDates(PlumberLicenseRequestDTO plumDto);

	List<Object[]> getReceiptDetails(PlumberLicenseRequestDTO plumDto);

	boolean updateLicNumber(PlumberLicenseRequestDTO plumDto);

	String getplumLicenseNo(Long applicationId);

	Long getPlumMasId(Long applicationId);

	Long getApplicationNumByPlumId(Long plumId);

	boolean updatPlumberRenewalDates(PlumberLicenseRequestDTO plumDto);

	PlumberLicenseRequestDTO getPlumberApplicationDashBoardDetails(Long applicationId, Long orgId);

	/**
	 * To get If Plumber Renewal already applied for current year
	 * 
	 * @param plumberId
	 * @return boolean
	 */
	String getRenewalValidateMsg(Long plumberId);

	PlumberLicenseResponseDTO saveDuplicatePlumberData(PlumberLicenseRequestDTO plumDto);

	/**
	 * To generate Jasper Report PDF
	 * 
	 * @param plumDto
	 * @param outputStream
	 * @param oParms
	 * @param fileName
	 * @param menuURL
	 * @param type
	 * @return
	 */
	public String generateJasperReportPDF(PlumberLicenseRequestDTO plumDto, ByteArrayOutputStream outputStream,
			Map oParms, String fileName, String menuURL, String type);
	
	public PlumberRenewalRegisterMaster getPlumberLicenceRenewalDetailsByAppId(Long applicationId, Long orgId);
	
	public PlumberMaster getPlumberDetailsByPlumId(Long plumId, Long orgId);

	Long getPlumberId(long applicationId, Long orgId);

}
