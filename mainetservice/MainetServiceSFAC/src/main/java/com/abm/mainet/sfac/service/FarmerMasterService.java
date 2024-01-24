/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.sfac.dto.FarmerMasterDto;
import com.abm.mainet.sfac.ui.model.FarmerMasterModel;

/**
 * @author pooja.maske
 *
 */
public interface FarmerMasterService {

	/**
	 * @param farmerMasterDto
	 * @param farmerMasterModel
	 */
	public FarmerMasterDto saveFarmerDetails(FarmerMasterDto farmerMasterDto, FarmerMasterModel farmerMasterModel,List<DocumentDetailsVO> attachments,
			RequestDTO requestDTO);

	/**
	 * @param userId
	 * @return
	 */
	boolean checkWomenCentric(Long femaleId, Long maleId,Long userId);

	/**
	 * @param frmId
	 * @return
	 */
	public FarmerMasterDto getDetailById(Long frmId);

	/**
	 * @param frmId
	 * @param frmFPORegNo
	 * @return
	 */
	public List<FarmerMasterDto> getFarmerDetailsByIds(Long frmId, String frmFPORegNo);

	/**
	 * @param masId
	 * @return
	 */
	public List<FarmerMasterDto> getAllDetailsById(Long frmId);

	/**
	 * @return
	 */
	public List<FarmerMasterDto> findAll();

	/**
	 * @param attachDocs
	 * @param fileNetApplicationClient
	 * @return
	 */
	Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs,
			FileNetApplicationClient fileNetApplicationClient);

}
