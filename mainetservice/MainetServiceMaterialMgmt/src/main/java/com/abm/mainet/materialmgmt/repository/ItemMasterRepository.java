
package com.abm.mainet.materialmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.materialmgmt.domain.ItemMasterEntity;

public interface ItemMasterRepository extends PagingAndSortingRepository<ItemMasterEntity, Long> {

	@Query("select DISTINCT e from ItemMasterEntity e JOIN FETCH e.itemMasterConversionEntity where e.orgId=:orgId order by 1 desc")
	List<ItemMasterEntity> findItemMasterDetailsByOrgId(@Param("orgId") Long orgId);

	@Query("select DISTINCT e from ItemMasterEntity e  JOIN FETCH e.itemMasterConversionEntity where e.itemId=:itemId order by 1 desc")
	ItemMasterEntity findItemMasterDetailsByItemId(@Param("itemId") Long itemId);

	@Query("select e.uom from ItemMasterEntity e  where e.orgId=:orgId and e.itemId=:itemId order by 1 desc")
	Long getUomByitemCode(@Param("orgId") Long orgId, @Param("itemId") Long itemId);

	@Query("select count(e.name) from ItemMasterEntity e where e.name=:name and e.orgId=:orgId")
	Long ItemNameCount(@Param("orgId") Long orgId, @Param("name") String name);

	@Query("select e.name from ItemMasterEntity e  where e.orgId=:orgId and e.itemId=:itemId order by 1 desc")
	String getItemInfoByItemIdDesc(@Param("orgId") Long orgId, @Param("itemId") Long itemId);

	@Query("select item.itemId, item.name, item.status from ItemMasterEntity item where item.orgId=:orgId order by 1")
	List<Object[]> getItemIdNameListByOrgId(@Param("orgId") Long orgId);

	@Query("select item.itemId, item.name, item.uom, item.management from ItemMasterEntity item where item.itemId=:itemId")
	Object[] getItemDetailObjectByItemId(@Param("itemId") Long itemId);

	@Query("select e.name, e.isExpiry from ItemMasterEntity e  where e.orgId=:orgId and e.itemId=:itemId order by 1 desc")
	Object[] getItemNameAndExpiryFlagByItemId(@Param("orgId") Long orgId, @Param("itemId") Long itemId);
	
	@Query("select item.itemId, item.name from ItemMasterEntity item where item.itemId in (:itemIdList) and item.orgId=:orgId order by 1")
	List<Object[]> getItemIdNameListByItemIdsOrgId(@Param("itemIdList") List<Long> itemIdList, @Param("orgId") Long orgId);

	// This list must be used only in Add forms
	@Query("select item.itemId, item.name, item.status from ItemMasterEntity item where item.orgId=:orgId and item.status=:status order by 1")
	List<Object[]> getActiveItemIdNameListByOrgId(@Param("orgId") Long orgId, @Param("status") String status);
}
