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

import com.abm.mainet.sfac.domain.MeetingDetailEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface MeetingDetailRepository extends JpaRepository<MeetingDetailEntity, Long> {

	/**
	 * @param removedMemtDetIdsList
	 * @param updatedBy
	 */
	@Transactional
	@Modifying
	@Query("UPDATE MeetingDetailEntity  y SET y.status ='I',y.updatedDate=CURRENT_DATE, y.updatedBy=:updatedBy where y.memId in (:removedMemtDetIdsList)")
	void inActiveMemDetByIds(@Param("removedMemtDetIdsList") List<Long> removedMemtDetIdsList,
			@Param("updatedBy") Long updatedBy);

}
