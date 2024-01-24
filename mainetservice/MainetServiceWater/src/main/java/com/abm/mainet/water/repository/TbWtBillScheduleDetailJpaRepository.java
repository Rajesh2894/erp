package com.abm.mainet.water.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.water.domain.TbWtBillScheduleDetailEntity;

/**
 * Repository : TbWtBillScheduleDetail.
 */
@Repository
public interface TbWtBillScheduleDetailJpaRepository
		extends PagingAndSortingRepository<TbWtBillScheduleDetailEntity, Long> {

	@Query("select billScheduleDetailEntity from TbWtBillScheduleDetailEntity billScheduleDetailEntity"
			+ " where billScheduleDetailEntity.tbWtBillSchedule.cnsId=:cnsId and billScheduleDetailEntity.orgid=:orgId"
			+ " and billScheduleDetailEntity.status='A'")
	List<TbWtBillScheduleDetailEntity> findAllByParentId(@Param("cnsId") Long cnsId, @Param("orgId") Long orgId);
}
