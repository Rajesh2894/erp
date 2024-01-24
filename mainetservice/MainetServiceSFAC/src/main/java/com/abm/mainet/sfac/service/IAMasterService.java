/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.sfac.dto.IAMasterDto;

/**
 * @author pooja.maske
 *
 */
public interface IAMasterService {

	/**
	 * @param mastDto
	 * @return
	 */
	IAMasterDto saveAndUpdateApplication(IAMasterDto mastDto);

	/**
	 * @param iaName
	 * @param allocationYear
	 */
	List<IAMasterDto> getIaDetailsByIds(Long IAName, Long allocationYear,Long orgId);

	/**
	 * @param orgid
	 * @return
	 */
	List<IAMasterDto> getIAListByOrgId(Long orgId);

	/**
	 * @param org
	 * @return
	 */
	List<TbFinancialyear> getfinancialYearList(Organisation org);

	/**
	 * @param iAId
	 * @return
	 */
	IAMasterDto findByIaId(Long iAId);

	/**
	 * @param iaMasterDto
	 * @param removedContDetIdsList
	 */
	void inactiveRemovedContactDetails(IAMasterDto iaMasterDto, List<Long> removedContDetIdsList);


	/**
	 * @return
	 */
	List<IAMasterDto> findAllIA();

	/**
	 * @param orgid
	 * @return
	 */
	List<CommonMasterDto> getMasterDetail(Long orgid);

	/**
	 * @param orgTypeId
	 * @param organizationNameId
	 * @return
	 */
	String fetchNameById(Long orgTypeId, Long organizationNameId);

	/**
	 * @param empId
	 * @return
	 */
	List<IAMasterDto> findAllIaAssociatedWithCbbo(Long empId);

	/**
	 * @param mastDto
	 * @return
	 */
	IAMasterDto updateIaMasterDetail(IAMasterDto mastDto);

	/**
	 * @param iaId
	 * @return
	 */
	Long getIaALlocationYear(Long iaId);

	/**
	 * @param iAName
	 * @return
	 */
	boolean checkIANameExist(String iAName);

	/**
	 * @param iaShortName
	 * @return
	 */
	boolean checkIaShortNmExist(String iaShortName);

}
