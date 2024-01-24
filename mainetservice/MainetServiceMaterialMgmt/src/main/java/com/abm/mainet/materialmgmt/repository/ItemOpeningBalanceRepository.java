package com.abm.mainet.materialmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.ItemOpeningBalanceEntity;

@Repository
public interface ItemOpeningBalanceRepository extends JpaRepository<ItemOpeningBalanceEntity, Long> {

	public List<ItemOpeningBalanceEntity> findByOrgId(@Param("orgId") Long orgId);

	@Modifying
	@Query("update ItemOpeningBalanceEntity it set it.status = false, it.updatedBy = ?1,it.updatedDate = CURRENT_DATE where it.openBalId = ?2")
	public void updateStatus(Long empId, Long openBalId);

	@Modifying
	@Query("update ItemOpeningBalanceDetEntity de set de.active = false, de.updatedBy = ?1,de.updatedDate = CURRENT_DATE where de.openBalDetId in ?2")
	public void updateStatusForDetails(Long empId, List<Long> openBalDetIds);

	@Query("Select d.itemNo from ItemOpeningBalanceDetEntity d where d.itemId=:itemId and d.itemNo in (:itemNumberList) and d.orgId=:orgId")
	public List<String> checkDuplicateItemNos(@Param("itemId") Long itemId, @Param("itemNumberList") List<String> itemNumberList,
			@Param("orgId") Long orgId);

}
