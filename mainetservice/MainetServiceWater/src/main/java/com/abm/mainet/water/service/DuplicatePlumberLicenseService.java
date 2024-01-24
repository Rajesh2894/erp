/**
 * 
 */
package com.abm.mainet.water.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface DuplicatePlumberLicenseService {

	Long getPlumberMasterPlumId(Long applicationId, Long orgid);

	public List<PlumberLicenseRequestDTO> getPlumberDetailsByPlumId(final Long plumberId, final Long orgId);

	PlumberLicenseResponseDTO saveDuplicatePlumberData(PlumberLicenseRequestDTO plumDto);

	public void updateApprovalStatus(Long applicationNumber, String appovalStatus, Date date);

	// public TbDuplicatePlumberLicenseEntity
	// findLatestDuplicateLicenseByPlumId(Long plumId, Long orgid);

	Object[] getPlumberApplicationDetail(String licNo, PlumberLicenseRequestDTO plumDto);

	public PlumberLicenseRequestDTO saveDuplicatePlumberLicenseAndAttachFile(PlumberLicenseRequestDTO requestDTO);

	/*
	 * public byte[] generateJasperReportPDF(PlumberLicenseRequestDTO plumDto,
	 * ByteArrayOutputStream outputStream, Map oParms, String fileName, String
	 * menuURL, String type);
	 */

	void updateAuthStatus(String authStatus, Long authBy, Date authDate, Long applicationId, Long orgId);

	public boolean getchecklistStatusInApplicationMaster(Long plumId, Long orgid);

	WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);
}
