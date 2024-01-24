/**
 * 
 */
package com.abm.mainet.additionalservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.additionalservices.domain.EChallanMasterEntity;

/**
 * @author divya.marshettiwar
 *
 */

@Repository
public interface EChallanEntryRepository extends JpaRepository<EChallanMasterEntity, Long>{
	
	@Query("SELECT am FROM  EChallanMasterEntity am WHERE am.orgid=:orgid AND am.challanId=:challanId ")
	EChallanMasterEntity findByOrgIdAndChallanId(@Param("orgid") Long orgid, @Param("challanId") Long challanId);
	
	
	@Modifying
	@Query("update EChallanMasterEntity d set d.status=:status where d.challanId=:challanId and d.orgid=:orgid")
	void updatePaymentStatus(@Param("challanId")Long challanId,@Param("status")String status, @Param("orgid")Long orgid);
	
	@Transactional
	@Modifying
	@Query("update EChallanItemDetailsEntity pd set pd.status = 'N', pd.updatedBy = ?1, pd.updatedDate = CURRENT_DATE where pd.itemId in ?2 ")
	public void removeItemIds(Long empId, List<Long> removePurDetIdsList);
}
