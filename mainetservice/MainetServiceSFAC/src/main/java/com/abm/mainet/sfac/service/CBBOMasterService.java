/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.StateAreaZoneCategoryDto;
import com.abm.mainet.sfac.ui.model.CBBOMasterApprovalModel;

/**
 * @author pooja.maske
 *
 */
public interface CBBOMasterService {

	/**
	 * @param masterDto
	 * @return
	 */
	CBBOMasterDto saveCbboMasterDetails(CBBOMasterDto masterDto);



	/**
	 * @param cbboName
	 * @param alcYearToCBBO
	 * @param orgId
	 * @return
	 */
	List<CBBOMasterDto> getCBBODetailsByIds(Long cbboId, Date alcYearToCBBO, Long orgId,Long iaId);



	/**
	 * @return
	 */
	//List<StateAreaZoneCategoryDto> fetchAllAreaAndZone();



	/**
	 * @param stateCode
	 * @return
	 */
	StateAreaZoneCategoryDto fetchAreaAndZoneByStateCode(String stateCode);



	/**
	 * @param sdb3
	 */
	boolean checkIsAispirationalDist(Long sdb3);



	/**
	 * @param sdb3
	 * @return
	 */
	boolean checkIsTribalDist(Long sdb3);



	/**
	 * @param sdb2
	 * @return
	 */
	String getOdopByDist(Long sdb2);



	/**
	 * @param orgId
	 * @return
	 */
	List<CBBOMasterDto> getCBBODetailsByorgId(Long orgId);



	/**
	 * @param iaId
	 * @return
	 */
	CBBOMasterDto getCbboDetailsByIAId(Long iaId);



	/**
	 * @param cbboId
	 * @return
	 */
	CBBOMasterDto findBycbboId(Long cbboId);



	/**
	 * @param masterDto
	 * @param removedContDetIdsList
	 */
	void inactiveRemovedContactDetails(CBBOMasterDto masterDto, List<Long> removedContDetIdsList);



	/**
	 * @param cbboId
	 * @return
	 */
	Long fetchPromotionAgnByCbboId(Long cbboId);



	/**
	 * @return
	 */
	List<CBBOMasterDto> findAllCBBO();



	/**
	 * @param panNo
	 * @return
	 */
	CBBOMasterDto getDetailsByPanNo(String panNo);



	/**
	 * @param panNo
	 * @return
	 */
	boolean checkPanNoExist(String panNo,Long iaId,Long empId);



	/**
	 * @param loginName
	 * @return
	 */
	List<CBBOMasterDto> findAllIaAssociatedWithCbbo(String loginName);



	/**
	 * @param mastDto
	 * @return
	 */
	CBBOMasterDto updateCbboMasterDetails(CBBOMasterDto mastDto);



	/**
	 * @param masId
	 * @return
	 */
	List<CBBOMasterDto> findCbboById(Long masId);



	/**
	 * @param iaId
	 * @return
	 */
	CBBOMasterDto findById(Long cbboId);



	List<CBBOMasterDto> getCBBOList(Long masId);



	List<CBBOMasterDto> findIAList(String cbboUniqueId);



	/**
	 * @param valueOf
	 * @return
	 */
	CBBOMasterDto getCbboByAppId(Long applicationId);



	/**
	 * @param dto
	 * @param cbboMasterApprovalModel
	 */
	void updateApprovalStatusAndRemark(CBBOMasterDto dto, CBBOMasterApprovalModel cbboMasterApprovalModel);

}
