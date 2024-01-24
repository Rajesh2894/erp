package com.abm.mainet.materialmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.PurchaseReturnEntity;

@Repository
public interface IPurchaseReturnRepository extends JpaRepository<PurchaseReturnEntity, Long> {
	
	@Query("select distinct grn.grnid, grn.grnno from GoodsReceivedNotesEntity grn join grn.inspectionDetEntityList gitem"
			+ " where gitem.decision=:decision  and grn.Status!=:status and grn.grnid not in (select r.grnId from PurchaseReturnEntity r) "
			+ " and grn.orgId=:orgId order by 1")
	public List<Object[]> fetchGrnNumberListForPurchaseReturn(@Param("decision") Character decision, @Param("status") String status, @Param("orgId") Long orgId);

	
	@Query("select distinct grn.grnid, grn.grnno, grn.storeid, grn.lmoDate, grn.poid, po.poNo, po.vendorId FROM GoodsReceivedNotesEntity grn, "
			+" PurchaseOrderEntity po WHERE grn.poid=po.poId and grn.grnid=:grnid and grn.Status!=:status and grn.orgId=:orgId")
	public Object[] getGRNDataByGRNId(@Param("grnid")Long grnid, @Param("orgId")Long orgId, @Param("status") String status);
	
	
	@Query("SELECT gitem.goodsReceivedNote.grnid, gitem.grnitementryid, gitem.itemMasterEntity.itemId, gitem.itemNo, gitem.quantity,"
			+ " gitem.rejectionReason FROM GrnInspectionItemDetEntity gitem WHERE gitem.goodsReceivedNote.grnid=:grnid and "
			+ " gitem.decision=:decision and gitem.status=:status and gitem.orgId=:orgId")
	public List<Object[]> getItemDataByGRNId(@Param("grnid")Long grnid, @Param("orgId")Long orgId, @Param("decision") Character decision, 
												@Param("status") Character status);

	public PurchaseReturnEntity findByReturnIdAndOrgId(@Param("returnId")Long returnId, @Param("orgId")Long orgId);

	
}
