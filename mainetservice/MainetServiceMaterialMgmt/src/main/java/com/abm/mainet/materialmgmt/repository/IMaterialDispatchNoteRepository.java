package com.abm.mainet.materialmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.MaterialDispatchNote;

@Repository
public interface IMaterialDispatchNoteRepository extends JpaRepository<MaterialDispatchNote, Long> {

	@Query(value = "SELECT distinct si.siid, si.sino, sDet.quantity, IFNULL((SELECT SUM(IFNULL(mdnItem.issuedQty,0)) "
			+ "	FROM mm_mdn_items mdnItem INNER JOIN mm_mdn mdn ON mdn.mdnid=mdnItem.mdnid WHERE mdnItem.ORGID=sDet.ORGID "
			+ " AND mdnItem.itemid = sDet.itemid AND mdn.siid=sDet.siid GROUP BY mdn.siid, mdnItem.itemid, mdnItem.ORGID),0) "
			+ " AS prev_received_quantity FROM mm_storeindent_item sDet INNER JOIN mm_storeindent si ON si.siid=sDet.siid "
			+ " WHERE sDet.ORGID=:orgId and si.Status!=:flagN having (sDet.quantity - prev_received_quantity)>0;", nativeQuery = true)
	List<Object[]> getStoreIndentListForMDN(@Param("orgId")Long orgId, @Param("flagN") String flagN);

	
	@Query(value = "SELECT si.siid, si.requeststore, si.issuestore, si.sidate, sDet.siitemid, sDet.itemid, "
			+ " im.name, im.uom, im.management, sDet.quantity, IFNULL((SELECT SUM(IFNULL(mdnItem.issuedQty, 0)) "
			+ " FROM mm_mdn_items mdnItem INNER JOIN mm_mdn mdn ON mdn.mdnid = mdnItem.mdnid WHERE "
			+ " mdnItem.ORGID = sDet.ORGID AND mdnItem.itemid = sDet.itemid AND mdn.siid = sDet.siid GROUP BY "
			+ " mdn.siid, mdnItem.itemid, mdnItem.ORGID), 0) AS prev_received_quantity FROM mm_storeindent_item sDet "
			+ " INNER JOIN mm_storeindent si ON si.siid = sDet.siid LEFT JOIN MM_ITEMMASTER im ON im.itemId = sDet.itemid "
			+ " WHERE sDet.siid=:siId AND sDet.ORGID=:orgId", nativeQuery = true)
	List<Object[]> getDataByStoreIndentId(@Param("siId")Long siId, @Param("orgId")Long orgId);
	
	
	MaterialDispatchNote findByMdnNumberAndOrgId(String mdnNumber, Long orgId);

	MaterialDispatchNote findByMdnIdAndOrgId(Long mdnId, Long orgId);
	
	@Query("Select mdn from MaterialDispatchNote mdn where mdn.mdnNumber=:mdnNumber and mdn.orgId=:orgId")
	MaterialDispatchNote findByMdnNumber(@Param("mdnNumber") String mdnNumber, @Param("orgId") Long orgId);
	
	@Query("SELECT mdn.mdnId, mdn.mdnNumber, mdn.mdnDate, mdn.siId, si.storeIndentNo, mdn.requestStoreId, smr.storeName, "
			+ " mdn.issueStoreId, smi.storeName, mdn.status FROM MaterialDispatchNote mdn, StoreIndentEntity si, "
			+ " StoreMaster smr, StoreMaster smi WHERE mdn.requestStoreId = smr.storeId and mdn.issueStoreId=smi.storeId "
			+ " and mdn.siId = si.storeIndentId and mdn.orgId = :orgId AND (:mdnId IS NULL OR mdn.mdnId = :mdnId) "
			+ " AND (:requestStoreId IS NULL OR mdn.requestStoreId = :requestStoreId) "
			+ " AND (:issueStoreId IS NULL OR mdn.issueStoreId = :issueStoreId) "
			+ " AND (:status IS NULL OR mdn.status = :status) AND (:siId IS NULL OR mdn.siId = :siId)")
	List<Object[]> mdnSummaryDataObjectList(@Param("orgId") Long orgId, @Param("mdnId") Long mdnId,
			@Param("requestStoreId") Long requestStoreId, @Param("issueStoreId") Long issueStoreId,
			@Param("status") String status, @Param("siId") Long siId);
	
}
