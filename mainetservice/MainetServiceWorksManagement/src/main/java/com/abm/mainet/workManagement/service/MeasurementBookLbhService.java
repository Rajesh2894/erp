package com.abm.mainet.workManagement.service;

import java.util.List;
import com.abm.mainet.workManagement.dto.MeasurementBookLbhDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;

public interface MeasurementBookLbhService {

	/**
	 * get All LBH Details List By Measurement Id
	 * 
	 * @param MeasurementId
	 * @return list Of MeasurementBookLbhDto
	 */
	List<MeasurementBookLbhDto> getAllLbhDetailsByMeasurementId(Long MeasurementId);

	/**
	 * get LBH Details By Measurement Book LBH Id
	 * 
	 * @param lbhId
	 * @return MeasurementBookLbhDto
	 */
	MeasurementBookLbhDto getMbLbhByLbhId(Long lbhId);

	/**
	 * save and update Measurement Book LBH List
	 * 
	 * @param lbhDtosList
	 * @param parentEstimatedId 
	 */
	void saveUpdateMbLbhList(List<MeasurementBookLbhDto> lbhDtosList,List<Long> deletedLbhId, Long parentEstimatedId);

	/**
	 * save and update Measurement Book LBH
	 * 
	 * @param lbhDtosList
	 */
	void saveUpdateMbLbh(MeasurementBookLbhDto lbhDto);
	
	/**
	 * get All LBH Details List By Measurement Id
	 * 
	 * @param MeasurementId
	 * @return list Of MeasurementBookLbhDto
	 */
	List<MeasurementBookLbhDto> getLbhDetailsByMeasurementId(List<Long> measurementId,Long mbDetId,String mode);
	
	/**
	 * used to delete MeasurementBookLbh  by MB details ID
	 * 
	 * @param  MB details ID
	 */
	void deleteMbLbhByMbDetailsId(List<Long> mbdId);
	
	/**
	 * get log details 
	 * @param mbDetId
	 * @return
	 */
	List<MeasurementBookLbhDto> getAuditDetailsByMeasurementId(Long mbDetId);
	
	/**
	 * MeasurementBookLbhDto on basis of MB detId
	 * @param mbDetId
	 * @return List<MeasurementBookLbhDto>
	 */
	List<MeasurementBookLbhDto> getLbhDetailsByMbDetailId(Long orgId,Long mbDetId);
	
	List<WorkEstimateMeasureDetailsDto> getWorkEstimateByWorkEId(Long WorkEId);


}
