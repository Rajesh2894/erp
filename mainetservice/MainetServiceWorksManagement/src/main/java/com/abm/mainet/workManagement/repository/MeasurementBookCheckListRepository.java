/**
 * 
 */
package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.workManagement.domain.MbCheckListMast;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public interface MeasurementBookCheckListRepository extends CrudRepository<MbCheckListMast, Long> {

	/**
	 * used to get all measurement book CheckList by org Id
	 * 
	 * @param orgId
	 * @return MbCheckListMast
	 */
	@Query("select mb from MbCheckListMast mb where mb.mbMaster.workMbId= :mbId and mb.orgId = :orgId")
	MbCheckListMast getMBCheckListByMBAndOrgId(@Param("mbId") Long mbId, @Param("orgId") Long orgId);

	/**
	 * used to delete MB CheckList details by ID
	 * 
	 * @param deleted
	 *            mbcdId
	 */
	@Modifying
	@Query("DELETE from  MbCheckListDetail a where a.mbcdId in (:mbcdId)")
	void deleteMBChkListDetailsById(@Param("mbcdId") List<Long> mbcdId);
}
