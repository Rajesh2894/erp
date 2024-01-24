package com.abm.mainet.materialmgmt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.materialmgmt.domain.BinLocMasEntity;
import com.abm.mainet.materialmgmt.domain.GoodsReceivedNotesEntity;
import com.abm.mainet.materialmgmt.domain.GoodsreceivedNotesitemEntity;

@Repository
public interface GoodsReceivedNotesRepository extends JpaRepository<GoodsReceivedNotesEntity, Long> {

	List<GoodsReceivedNotesEntity> findAllByOrgIdOrderByGrnidDesc(Long orgId);

	@Query("select p from GoodsreceivedNotesitemEntity p where  p.grnitemid =:grnitemid AND p.orgId =:orgid order by 1 desc")
	List<GoodsreceivedNotesitemEntity> getGrnDataByGrno(@Param("grnitemid") Long grnitemid, @Param("orgid") Long orgid);

	GoodsReceivedNotesEntity findByGrnidAndOrgIdOrderByGrnid(@Param("grnid") Long grnid, @Param("orgid") Long orgid);

	@Modifying
	@Query("update GoodsreceivedNotesitemEntity it set it.acceptqty =:acceptqty, it.rejectqty =:rejectqty where it.orgId =:orgId")
	void updateStatusAcceptedRejectedQty(@Param("acceptqty") Double acceptqty, @Param("rejectqty") Double rejectqty,
			@Param("orgId") Long orgId);

	@Query("select p from GoodsReceivedNotesEntity p where  p.orgId =:orgId AND p.poid =:poid")
	List<GoodsReceivedNotesEntity> getGrnDataByOrgIdAndPoidOrderByGrnidDesc(@Param("orgId") Long orgId,
			@Param("poid") Long poid);

	@Query("select grn.grnid, grn.grnno, grn.inspectiondate, grn.poid, grn.storeid, grn.Status from GoodsReceivedNotesEntity grn "
			+ " where grn.orgId=:orgId and grn.Status=:draftStatus or grn.Status=:inspectStatus order by 1 desc")
	List<Object[]> findByOrgIdAndStatusForInspectionSummary(@Param("orgId") Long orgId, @Param("draftStatus") String draftStatus,
			@Param("inspectStatus") String inspectStatus);

	@Query("select grn from GoodsReceivedNotesEntity grn where grn.orgId=:orgId and grn.Status=:Status order by 1 asc")
	List<GoodsReceivedNotesEntity> findByOrgIdAndStatusOrderByGrnid(@Param("orgId") Long orgId,
			@Param("Status") String Status);

	List<GoodsReceivedNotesEntity> findByOrgIdOrderByGrnid(@Param("orgId") Long orgId);

	@Query("select grn from GoodsReceivedNotesEntity grn where grn.grnno=:grnno and grn.orgId=:orgId order by 1 desc")
	GoodsReceivedNotesEntity getGrnDataByGrno(@Param("grnno") String grnno, @Param("orgId") Long orgId);

	@Transactional
	@Modifying
	@Query("update GrnInspectionItemDetEntity gdet set gdet.status = 'N', gdet.updatedBy = ?1, gdet.updatedDate = CURRENT_DATE where gdet.grnitementryid in ?2 ")
	void removeInBatchsById(Long userId, List<Long> removeInBatchIdList);

	@Transactional
	@Modifying
	@Query("update GrnInspectionItemDetEntity gdet set gdet.status = 'N', gdet.updatedBy = ?1, gdet.updatedDate = CURRENT_DATE where gdet.grnitementryid in ?2 ")
	void removeSerialsById(Long userId, List<Long> removeSerialIdList);

	@Transactional
	@Modifying
	@Query("update GrnInspectionItemDetEntity gdet set gdet.status = 'N', gdet.updatedBy = ?1, gdet.updatedDate = CURRENT_DATE where gdet.grnitementryid in ?2 ")
	void removeNotInBatchByIds(Long userId, List<Long> removeNotInBatchList);

	@Query("select b from BinLocMasEntity b where b.orgId=:orgId and b.binLocId in(select grn.binLocation from GrnInspectionItemDetEntity grn where grn.storeId=:storeId and grn.itemMasterEntity.itemId=:itemId)")
	List<BinLocMasEntity> findByOrgIdAndItemId(@Param("orgId") Long orgId, @Param("storeId") Long storeId,
			@Param("itemId") Long itemId);

	@Query("select gdet.itemNo from GrnInspectionItemDetEntity gdet where gdet.itemNo in ?1 and gdet.storeId = ?2 and gdet.orgId = ?3")
	List<String> getDuplicateList(List<String> itemNumberList, Long storeid, Long orgId);

	@Query(value = "SELECT pdet.poid, po.pono, pdet.quantity, IFNULL((SELECT SUM(IFNULL(gdet.receivedqty, 0))FROM "
			+ " mm_grn_items gdet INNER JOIN mm_grn grn ON gdet.grnid=grn.grnid WHERE gdet.ORGID=pdet.ORGID AND "
			+ " gdet.itemid=pdet.itemid AND grn.poid=pdet.poid GROUP BY grn.poid, gdet.itemid, gdet.ORGID),0) prevRecQty "
			+ " FROM MM_PURCHASEORDER_DET pdet inner join MM_PURCHASEORDER po on po.poid=pdet.poid WHERE pdet.ORGID=:orgId "
			+ " And po.Status=:flagY having (pdet.quantity - prevRecQty)>0 ;", nativeQuery = true)
	List<Object[]> getPurchaseOrderListForGRN(@Param("orgId") Long orgId, @Param("flagY") String flagY);

	@Query(value = "SELECT pdet.poid, pdet.podet, pdet.itemid, pdet.quantity, IFNULL((SELECT SUM(IFNULL(gdet.receivedqty, 0)) "
			+ "FROM mm_grn_items gdet INNER JOIN mm_grn grn ON gdet.grnid=grn.grnid WHERE gdet.ORGID=pdet.ORGID AND gdet.itemid=pdet.itemid "
			+ "AND grn.poid=pdet.poid GROUP BY grn.poid, gdet.itemid, gdet.ORGID),0) prev_received_quantity FROM MM_PURCHASEORDER_DET  pdet inner join "
			+ "MM_PURCHASEORDER po on po.poid=pdet.poid WHERE pdet.poid=:poId and  pdet.ORGID=:orgId ", nativeQuery = true)
	List<Object[]> getDataByPoId(@Param("poId") Long poId, @Param("orgId") Long orgId);

	@Query("select distinct grn.lmoDate from GoodsReceivedNotesEntity grn where  grn.orgId =:orgId and grn.grnid=:grnid")
	Date findGRNDateByOrgIdAndGRNId(@Param("orgId") Long orgId, @Param("grnid") Long grnid);

	@Query("select distinct grn.grnno from GoodsReceivedNotesEntity grn where  grn.orgId =:orgId and grn.grnid=:grnid")
	String findGRNNumberByOrgIdAndGRNId(@Param("orgId") Long orgId, @Param("grnid") Long grnid);

	@Query("select g.rejectionReason from GrnInspectionItemDetEntity g where  g.orgId =:orgId and g.grnitementryid=:grnitementryid")
	Long getRejectionReasonByOrgIdAndGrnitementryid(@Param("orgId") Long orgId,
			@Param("grnitementryid") Long grnitementryid);

	@Query("Select grn.grnid, grn.grnno, grn.receiveddate, grn.poid, grn.storeid, grn.Status from "
			+ " GoodsReceivedNotesEntity grn where grn.orgId = :orgId and (grn.storeid = COALESCE(:storeId, grn.storeid)) "
			+ " and (grn.grnid = COALESCE(:grnid, grn.grnid)) and (grn.receiveddate >= COALESCE(:fromDate, grn.receiveddate)) "
			+ " and (grn.receiveddate <= COALESCE(:toDate, grn.receiveddate)) and (grn.poid = COALESCE(:poid, grn.poid)) "
			+ " order by grn.grnid desc ")
	List<Object[]> searchGoodsReceivedNotes(@Param("storeId") Long storeId, @Param("grnid") Long grnid,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("poid") Long poid,
			@Param("orgId") Long orgId);

	@Query("Select grn.grnid, grn.grnno, grn.inspectiondate, grn.poid, grn.storeid, grn.Status from GoodsReceivedNotesEntity grn "
			+ " where grn.orgId=:orgId And (:storeId IS NULL OR grn.storeid=:storeId) And (:grnid IS NULL OR grn.grnid=:grnid) "
			+ " And (:poid IS NULL OR grn.poid=:poid) And (:Status IS NULL OR grn.Status=:Status) "
			+ " And (:fromDate IS NULL OR grn.inspectiondate>=:fromDate) AND (:toDate IS NULL OR grn.inspectiondate<=:toDate) "
			+ " order by grn.grnid desc ")
	List<Object[]> searchGRNInspectionSearchData(@Param("storeId") Long storeId, @Param("grnid") Long grnid,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("poid") Long poid,
			@Param("orgId") Long orgId, @Param("Status") String Status);

	@Query("Select grn.grnid, grn.grnno from GoodsReceivedNotesEntity grn where grn.orgId=:orgId and grn.Status=:Status"
			+ " order by grn.grnid")
	List<Object[]> grnIdNoListByStatus(@Param("orgId") Long orgId, @Param("Status") String Status);

	
}
