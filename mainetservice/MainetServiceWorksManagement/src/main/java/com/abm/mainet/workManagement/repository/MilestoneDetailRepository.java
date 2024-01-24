/**
 * 
 */
package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.MilestoneDetail;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Repository
public interface MilestoneDetailRepository extends CrudRepository<MilestoneDetail, Long> {

	/**
	 * To get MilestoneDetail Active Records .
	 * 
	 * @param mileId
	 * @param orgid
	 * @return List<MilestoneDetail>
	 */

	@Query("select a from MilestoneDetail a where  a.milestoneEntity.mileId=:mileId and a.orgId=:orgId and a.miledActive is 'Y'")
	List<MilestoneDetail> getAllMilestoneDetByMilestoneId(@Param("mileId") Long mileId, @Param("orgId") Long orgId);

	@Modifying
	@Query("update MilestoneDetail a set a.miledActive ='N',a.updatedDate = CURRENT_DATE where a.miledId =:miledId and a.orgId=:orgId")
	void updateMilestoneDetailStatus(@Param("miledId") Long miledId, @Param("orgId") Long orgId);
}
