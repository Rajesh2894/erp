package com.abm.mainet.materialmgmt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.materialmgmt.domain.PurchaseOrderEntity;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, Long> {

	@Query("select p from PurchaseOrderEntity p where  p.orgId =:orgId ")
	List<PurchaseOrderEntity> getAllPurDataByOrgId(@Param("orgId") Long orgId);

	@Modifying
	@Query("update PurchaseOrderEntity p set p.status=:status where p.poId=:poId and p.orgId=:orgId")
	void deletePurchaseOrder(@Param("poId") Long poId, @Param("orgId") Long orgId, @Param("status") char status);
	
    @Transactional
    @Modifying
    @Query("update PurchaseOrderOverheadsEntity oh set oh.status = 'N', oh.updatedBy = ?1, oh.updatedDate = CURRENT_DATE where oh.overHeadId in ?2 ")
	void removeOverheadsById(Long empId, List<Long> removeOverheadIdList);

    @Transactional
    @Modifying
    @Query("update PurchaseorderTncEntity tnc set tnc.status = 'N', tnc.updateBy = ?1, tnc.updatedDate = CURRENT_DATE where tnc.tncId in ?2 ")
	void removeTNCsById(Long empId, List<Long> removeTncIdList);

    @Transactional
    @Modifying
    @Query("update PurchaseorderAttachmentEntity pa set pa.atdStatus = 'N', pa.updatedBy = ?1, pa.updatedDate = CURRENT_DATE where pa.podocId in ?2 ")
	void removeEncsById(Long empId, List<Long> removeEncIdList);

    @Query("select p from PurchaseOrderEntity p where  p.orgId =:orgId AND p.status =:status")
    List<PurchaseOrderEntity> findByOrgIdAndStatusOrder(@Param("orgId") Long orgId,@Param("status") char status); 
    
    @Query("select p from PurchaseOrderEntity p where  p.orgId =:orgId AND p.poId =:poId")
	List<PurchaseOrderEntity> getAllPurchaseOrderData(@Param("orgId")Long orgId, @Param("poId")Long poId );
    
    @Query("select p.poNo from PurchaseOrderEntity p where  p.orgId =:orgId AND p.poId =:poId")
   	String getAllPurchaseOrderNoById(@Param("orgId")Long orgId, @Param("poId")Long poId );
   
	@Query("select p.poDate from PurchaseOrderEntity p  where p.orgId=:orgId and p.poId=:poId order by 1 desc")
	Date getDatebyPOId(@Param("orgId") Long orgId, @Param("poId") Long poId);
	
	@Query("select p.poId, poNo from PurchaseOrderEntity p where p.orgId =:orgId ")
	List<Object[]> getPurcahseOrderIdAndNumbers(@Param("orgId") Long orgId);
	
	@Query("select p.poNo, p.poDate, p.storeId, s.storeName from PurchaseOrderEntity p, StoreMaster s where p.storeId=s.storeId"
			+ " and p.poId =:poId and p.orgId =:orgId")
	Object[] getPurcahseOrderDateAndStore(@Param("poId")Long poId, @Param("orgId") Long orgId);
  
}
