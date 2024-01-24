package com.abm.mainet.workManagement.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.MeasurementBookLbh;
import com.abm.mainet.workManagement.domain.MeasurementBookLbhHistory;

@Repository
public interface MeasurementBookLbhRepository extends CrudRepository<MeasurementBookLbh, Long> {

	@Query("select mb from MeasurementBookLbh mb where mb.details.workEstemateId =:measurementId")
	List<MeasurementBookLbh> getAllLbhDetailsByMeasurementId(@Param("measurementId") Long measurementId);

	@Query("select mb from MeasurementBookLbh mb where mb.mbLbhId =:lbhId")
	MeasurementBookLbh getLbhDetailsByLbhId(@Param("lbhId") Long lbhId);

	@Query("select mb from MeasurementBookLbh mb where mb.details.meMentId =:measurementId and mb.mbdId=:mbdId")
	MeasurementBookLbh getLbhDetailsByMeasurementId(@Param("measurementId") Long measurementId,
			@Param("mbdId") Long mbdId);

	/**
	 * used to delete MeasurementBookLbh by MB details ID
	 * 
	 * @param deleted
	 *            MB_LBH
	 */
	@Modifying
	@Query("DELETE from  MeasurementBookLbh a where a.mbLbhId in (:mbLbhId)")
	void deleteMbLbhByMbDetailsId(@Param("mbLbhId") List<Long> deletedMbdId);

	/**
	 * used to get change Log details
	 * 
	 * @param mbDetId
	 * @return
	 */
	@Query("select mb from MeasurementBookLbhHistory mb where mb.mbdId=:mbdId")
	List<MeasurementBookLbhHistory> getAuditDetailsByMeasurementId(@Param("mbdId") Long mbdId);

	@Query("select Sum(m.mbTotal) from MeasurementBookLbh m where m.mbdId=:mbdId")
	BigDecimal getAllLbhTotal(@Param("mbdId") Long mbdId);

	@Query("select mb from MeasurementBookLbh mb where mb.orgId =:orgId and mb.mbdId=:mbdId and mb.details.meMentId is null")
	List<MeasurementBookLbh> getLbhDetailsByMbDetailId(@Param("orgId") Long orgId, @Param("mbdId") Long mbdId);
	
	@Query("select mb from MeasurementBookLbh mb where mb.details.meMentId =:measurementId and mb.mbdId=:mbdId")
	List<MeasurementBookLbh> getLbhDetailsByMeasurement(@Param("measurementId") Long measurementId,
			@Param("mbdId") Long mbdId);

}
