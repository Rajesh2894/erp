package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.util.List;

import com.abm.mainet.workManagement.dto.WmsRaBillTaxDetailsDto;
import com.abm.mainet.workManagement.dto.WorksRABillDto;

public interface WorksRABillService {

	/**
	 * used to get ra Bill Details by ra Id
	 * 
	 * @param raId
	 * @return
	 */
	WorksRABillDto getRaBillByRaId(Long raId);

	/**
	 * used to get Ra Bill List By Project number And Work Number
	 * 
	 * @param projId
	 * @param workId
	 * @param orgId
	 * @return
	 */
	List<WorksRABillDto> getRaBillListByProjAndWorkNumber(Long projId, Long workId, Long orgId);

	/**
	 * save And Update Ra Bill
	 * 
	 * @param worksRABillDto
	 * @param raTaxIds
	 * @param billTaxDetailsDtos 
	 */
	WorksRABillDto saveAndUpdateRaBill(WorksRABillDto worksRABillDto, List<Long> raTaxIds, Long deptId, List<WmsRaBillTaxDetailsDto> billTaxDetailsDtos);

	/**
	 * update Bill Details
	 * 
	 * @param raNumber
	 * @param billNumber
	 */
	void updateBillDetails(Long raNumber, String billNumber);

	/**
	 * get RA Bill Details By RaCode
	 * 
	 * @param raCode
	 * @return
	 */
	WorksRABillDto getRABillDetailsByRaCode(String raCode, Long orgId);

	/**
	 * update Status of Ra Bill By RaId
	 * 
	 * @param raId
	 * @param flag
	 */
	void updateStatusByRaId(Long raId, String flag);

	/**
	 * used get Previous Ra Bill Details
	 * 
	 * @param workId
	 * @param orgId
	 * @param currentRaId
	 * @return
	 */
	WorksRABillDto getPreviousRaBillDetails(Long workId, Long orgId, Long currentRaId);
	
	/**
	 * used to get sum of all previous WithHeld Amount 
	 * @param workId
	 * @param orgId
	 * @param serialNo
	 * @return
	 */
	BigDecimal getAllPreviousWithHeldAmount(Long workId, Long orgId, Long serialNo);
	
	
	public List<WorksRABillDto> getRaBillByRaCode(Long orgId);
	
	List<WorksRABillDto> getAllRaDetailforSchedular(Long orgId);

	void updateRaBillTypeById(Long raId, String flag);
	}
