package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.workManagement.domain.WmsLeadLiftMasterEntity;

@Repository
public interface LeadLiftMasterRepository extends CrudRepository<WmsLeadLiftMasterEntity, Long> {

	/**
	 * get All Active Lead-Lift Master details by organization id.
	 * 
	 * @param orgId
	 * @return List<Object>
	 */
	@Query("select DISTINCT a.sorMaster.sorId , a.leLiFlag  from WmsLeadLiftMasterEntity a where a.orgId=:orgId and a.leLiActive is 'Y' ")
	List<Object> getAllActiveLeadLiftRecords(@Param("orgId") Long orgId);

	/**
	 * get All Active Lead-Lift Master details by sorId,leLiFlag & organization id.
	 * 
	 * @param sorId
	 * @param leLiFlag
	 * @param orgId
	 * @return List<Object>
	 */
	@Query("select DISTINCT a.sorMaster.sorId , a.leLiFlag  from WmsLeadLiftMasterEntity a where a.leLiFlag=:leLiFlag and a.sorMaster.sorId=:sorId and a.orgId=:orgId and a.leLiActive is 'Y' ")
	List<Object> toCheckLeadLiftEntry(@Param("sorId") Long sorId, @Param("leLiFlag") String leLiFlag,
			@Param("orgId") Long orgId);

	/**
	 * To get Lead-Lift Master Active Records .
	 * 
	 * @param sorId
	 * @param leLiFlag
	 * @param orgid
	 * @return List<WmsLeadLiftMasterEntity>
	 */
	@Query("select a from WmsLeadLiftMasterEntity a where a.leLiFlag=:leLiFlag and a.sorMaster.sorId=:sorId and a.orgId=:orgId and a.leLiActive is 'Y'")
	List<WmsLeadLiftMasterEntity> editLeadLiftData(@Param("sorId") Long sorId, @Param("leLiFlag") String leLiFlag,
			@Param("orgId") Long orgId);

	/**
	 * used to inactive Lead-Lift Master details by sor id
	 * 
	 * @param sorId
	 * @param empId
	 * @param leLiFlag
	 */
	@Modifying
	@Transactional
	@Query("UPDATE WmsLeadLiftMasterEntity a set a.leLiActive = 'N', a.updatedBy=:empId, a.updatedDate = CURRENT_DATE WHERE a.sorMaster.sorId=:sorId and a.leLiFlag=:leLiFlag ")
	void inactiveLeadLiftMas(@Param("sorId") Long sorId, @Param("empId") Long empId,
			@Param("leLiFlag") String leLiFlag);

	/**
	 * used to inactive Lead-Lift Master Particular Entries by LeadLift Master
	 * Primary Key
	 * 
	 * @param empId
	 * @param removeChildIds
	 */
	@Modifying
	@Transactional
	@Query("UPDATE WmsLeadLiftMasterEntity a set a.leLiActive = 'N',a.updatedBy=?1, a.updatedDate = CURRENT_DATE WHERE a.leLiId in (?2)")
	void inactiveEntityRecords(Long empId, List<Long> removeChildIds);

}
