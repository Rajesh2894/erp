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

import com.abm.mainet.sfac.domain.MeetingAgendaDetEntity;
import com.abm.mainet.sfac.domain.MeetingMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface MeetingAgendaRepository extends JpaRepository<MeetingAgendaDetEntity, Long>{

	/**
	 * @param removedAgendaDetIdsList
	 * @param updatedBy
	 */
	@Transactional
	@Modifying
	@Query("UPDATE MeetingAgendaDetEntity y SET y.status ='I',y.updatedDate=CURRENT_DATE, y.updatedBy=:updatedBy where y.agendaId in (:removedAgendaDetIdsList)")
	void inActiveAgendaDetByIds(@Param("removedAgendaDetIdsList")List<Long> removedAgendaDetIdsList,@Param("updatedBy") Long updatedBy);

	/**
	 * @param entity
	 * @return
	 */
	@Query("Select m from MeetingAgendaDetEntity m where m.meetingMasterEntity=:entity")
	List<MeetingAgendaDetEntity> getAgendaDet(@Param("entity") MeetingMasterEntity entity);

}
