package com.abm.mainet.common.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbVisitorScheduleEntity;

/**
 * Repository : TbVisitorSchedule.
 */
public interface TbVisitorScheduleJpaRepository extends PagingAndSortingRepository<TbVisitorScheduleEntity, Long> {

	/**
	 * @param appId
	 * @return
	 */

	@Query("select tbVisitorScheduleEntity from TbVisitorScheduleEntity tbVisitorScheduleEntity "
			+ " where tbVisitorScheduleEntity.visApplicationId = :visApplicationId")
	TbVisitorScheduleEntity findAppId(@Param("visApplicationId") Long appId);
    //D#142467
	@Query("select tbVisitorScheduleEntity from TbVisitorScheduleEntity tbVisitorScheduleEntity "
			+ " where tbVisitorScheduleEntity.visDate = :visDate and orgid=:orgId")
	List<TbVisitorScheduleEntity> findAllByInspectionDate(@Param("visDate") Date visDate, @Param("orgId") Long orgId);

	@Modifying
	@Query("UPDATE TbVisitorScheduleEntity ap SET ap.visDate = :appointmentDate,ap.visTime = :appointmentTime, ap.updatedBy =:updatedBy, ap.updatedDate = CURRENT_TIMESTAMP where ap.orgid =:orgId AND ap.visApplicationId in (:appointmentIds)")
	void updateAppointmentRescByIds(@Param("appointmentDate") Date appointmentDate,
			@Param("appointmentTime") String appointmentTime, @Param("appointmentIds") List<Long> appointmentIds,
			@Param("orgId") Long orgId, @Param("updatedBy") Long updatedBy);

}
