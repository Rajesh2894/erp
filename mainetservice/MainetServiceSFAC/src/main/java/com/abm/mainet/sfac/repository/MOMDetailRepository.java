/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.sfac.domain.MOMDetEntity;
import com.abm.mainet.sfac.domain.MeetingMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface MOMDetailRepository extends  JpaRepository<MOMDetEntity, Long>{

	/**
	 * @param entity
	 * @return
	 */
	@Query("Select m from MOMDetEntity m where m.meetingMasterEntity=:entity")
	List<MOMDetEntity> getMomDet(@Param("entity") MeetingMasterEntity entity);

	/**
	 * @param removedMomDetIdsList
	 * @param updatedBy
	 */
	@Transactional
	@Modifying
	@Query("UPDATE MOMDetEntity y SET y.status ='I',y.updatedDate=CURRENT_DATE, y.updatedBy=:updatedBy where y.momId in (:removedMomDetIdsList)")
	void inActiveMomDetByIds(@Param("removedMomDetIdsList") List<Long> removedMomDetIdsList,@Param("updatedBy") Long updatedBy);
	


}
