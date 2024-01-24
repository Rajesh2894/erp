/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.sfac.dto.BlockAllocationDetailDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.ui.model.FpoMasterApprovalModel;

/**
 * @author pooja.maske
 *
 */
public interface FPOMasterService {

	/**
	 * @param mastDto
	 * @return
	 */
	FPOMasterDto saveAndUpdateApplication(FPOMasterDto mastDto);
	
	String getPrefixOtherValue(Long lookUpId, Long orgId);

	/**
	 * @param sdb3
	 * @return
	 */
	boolean checkSpecialCateExist(Long sdb3,Long orgId);

	/**
	 * @return
	 */
	List<FPOMasterDto> findAllFpo();

	/**
	 * @param fpoId
	 * @return
	 */
	FPOMasterDto getDetailById(Long fpoId);

	/**
	 * @param masId
	 * @return
	 */
	List<FPOMasterDto> findFpoByMasId(Long masId,String orgShortNm,String uniqueId);

	/**
	 * @param fpoId
	 * @param fpoRegNo
	 * @return
	 */
	List<FPOMasterDto> getfpoByIdAndRegNo(Long fpoId, String fpoRegNo,Long iaId,Long masId,String orgShortNm,String uniqueId);

	/**
	 * @param frmFPORegNo
	 * @return
	 */
	String getFpoName(String frmFPORegNo);

	/**
	 * @param masId
	 * @param emploginname
	 * @return
	 */
	List<FPOMasterDto> findFPOByIds(Long masId);

	/**
	 * @param mastDto
	 * @return
	 */
	FPOMasterDto updateFpoDetails(FPOMasterDto mastDto);

	/**
	 * @param valueOf
	 * @return
	 */
	FPOMasterDto getFpoDetByAppId(Long applicationId);

	/**
	 * @param dto
	 * @param fpoMasterApprovalModel
	 */
	void updateApprovalStatusAndRemark(FPOMasterDto dto, FpoMasterApprovalModel fpoMasterApprovalModel);

	Long getFPOCount(Long cbboId);

	/**
	 * @param masId
	 * @return
	 */
	List<FPOMasterDto> findByIaId(Long masId);
	
	List<FPOMasterDto> findByCbboId(Long cbboId);

	/**
	 * @param iaAlcYear
	 * @param masId
	 * @return
	 */
	List<BlockAllocationDetailDto> findBlockDetailsByMasIdAndYr(Long iaId, Long masId);

	/**
	 * @param companyRegNo
	 * @return
	 */
	boolean checkComRegNoExist(String companyRegNo);

	/**
	 * @param fpoName
	 * @return
	 */
	boolean checkFpoNameExist(String fpoName);

}
