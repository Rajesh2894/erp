package com.abm.mainet.workManagement.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.workManagement.dto.MileStoneDTO;
import com.abm.mainet.workManagement.dto.MilestoneDetDto;
import com.abm.mainet.workManagement.dto.MilestoneEntryDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;

/**
 * @author vishwajeet.kumar And SaiPrasad
 * @since 22 March 2018
 */
public interface MileStoneService {

	/**
	 * this service is used for creating Milestone
	 * 
	 * @param dto
	 * @return
	 */
	void addMilestoneEntry(List<MileStoneDTO> mileStoneDtoList);

	/**
	 * this service is used To get Milestone Records for respective project & if any
	 * selected Work Present.
	 * 
	 * @param projId
	 * @param workId
	 * @param orgid
	 * @return List<MileStoneDTO> if record found else return empty list
	 */
	List<MileStoneDTO> editMilestone(Long projId, Long workId, String mileStoneType, Long orgid);

	/**
	 * this service is used to update Milestone form
	 * 
	 * @param List<MileStoneDTO>
	 * @param flag
	 * @param ids
	 * @return
	 */
	void saveAndUpdateMilestone(List<MileStoneDTO> MileStoneDtos, String ids);

	/**
	 * this method is used to get list of data with projectId
	 * 
	 * @param projId
	 * @param orgId
	 * @param mileStoneFlag
	 * @return
	 */
	List<WmsProjectMasterDto> findALLMileStoneList(Long projId, Long orgId, String milestoneFlag);

	/**
	 * This method is used for get data to workId
	 * 
	 * @param projId
	 * @param workId
	 * @param orgId
	 * @param mileStoneFlag
	 * @return
	 */
	List<WmsProjectMasterDto> findALLMileStoneListwithWorkId(Long projId, Long workId, Long orgId,
			String milestoneFlag);

	boolean checkMilestone(Long projId, String mileStoneType, Long orgid);

	MileStoneDTO getMileStoneDetail(Long mileId);

	List<MileStoneDTO> milestoneDtosByProjId(Long projId, Long orgId, String flag);

	List<MileStoneDTO> milestoneDtosWithWorkId(Long projId, Long workId, Long orgId, String flag);

	void updateMilestoneDetailStatus(Long mileDetId, Long orgId);

	void saveAndUpdateMilestoneProgress(List<MilestoneDetDto> detDtoList, List<DocumentDetailsVO> attachments,
			RequestDTO requestDTO, List<AttachDocs> attachDocs, Long orgId);

	List<MilestoneDetDto> getMilestoneDetListByMilestoneId(Long mileId, Long orgId);

	Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs, List<MilestoneDetDto> progressList,
			FileNetApplicationClient fileNetApplicationClient);
	
	void DeleteProgressDocuments(List<AttachDocs> attachDocs);
	
	void saveMilestoneEntry(MilestoneEntryDto milestoneEntryDto);
	
	List<MilestoneEntryDto> getMilestoneInfo(Long projId,Long workId,Long orgId);
	
	List<MileStoneDTO> getMilestoneByMileNm(Long projId, Long workId, Long mileStoneId, Long orgid);
	
	BigDecimal getMileStonePer(Long projId,Long workId,Long orgId);

	boolean checkMilestoneEntry(Long projId, Long workId, Long orgId, String milestoneName);

	void addMilestoneEntryData(List<MilestoneEntryDto> s);
	
}
