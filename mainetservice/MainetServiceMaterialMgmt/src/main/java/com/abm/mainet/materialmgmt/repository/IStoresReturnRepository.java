package com.abm.mainet.materialmgmt.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.StoresReturnEntity;

@Repository
public interface IStoresReturnRepository extends JpaRepository<StoresReturnEntity, Long> {

	@Query("select distinct entry.materialDispatchNote.mdnId, entry.materialDispatchNote.mdnNumber from "
			+ " MaterialDispatchNoteItemsEntry entry where entry.rejectQty >:rejectQty and entry.status=:status "
			+ " and entry.materialDispatchNote.mdnId not in (select r.mdnId from StoresReturnEntity r) "
			+ " and entry.orgId=:orgId order by 1")
	public List<Object[]> fetchMDNIDAndNumberListForStoreReturn(@Param("rejectQty") BigDecimal rejectQty,
			@Param("status") String status, @Param("orgId") Long orgId);

	@Query("select mdn.mdnId, mdn.mdnNumber from MaterialDispatchNote mdn where mdn.mdnId in (select "
			+ " sr.mdnId from StoresReturnEntity sr) and mdn.orgId=:orgId order by 1")
	public List<Object[]> getMDNNumbersReturned(@Param("orgId") Long orgId);
	
	@Query("SELECT mdn.mdnId, mdn.mdnNumber, mdn.mdnDate, mdn.siId, si.storeIndentNo, mdn.requestStoreId, smr.storeName, "
			+ " mdn.issueStoreId, smi.storeName FROM MaterialDispatchNote mdn, StoreIndentEntity si, StoreMaster smr, "
			+ " StoreMaster smi WHERE mdn.requestStoreId = smr.storeId and mdn.issueStoreId=smi.storeId and "
			+ " mdn.siId = si.storeIndentId and mdn.mdnId=:mdnId and mdn.orgId=:orgId ")
	Object[] getMDNDetailsByMDNId(@Param("mdnId") Long mdnId, @Param("orgId") Long orgId);

	@Query("Select me.materialDispatchNote.mdnId, me.mdnItemEntryId, me.itemId, im.name, im.uom, me.itemNo, me.rejectQty, "
			+ " me.rejectionReason from MaterialDispatchNoteItemsEntry me, ItemMasterEntity im where me.itemId=im.itemId "
			+ " and me.materialDispatchNote.mdnId=:mdnId and me.rejectQty >:rejectQty and me.orgId=:orgId ")
	List<Object[]> getMDNRejectedItemsByMDNId(@Param("mdnId") Long mdnId, @Param("rejectQty") BigDecimal rejectQty,
			@Param("orgId") Long orgId);
	
	StoresReturnEntity findByStoreReturnIdAndOrgId(Long storeReturnId, Long orgId);

	StoresReturnEntity findByStoreReturnNoAndOrgId(String storeReturnNo, Long orgId);

	
	@Query("SELECT r.storeReturnId, r.storeReturnNo, r.storeReturnDate, r.mdnId, si.storeIndentNo, r.issueStoreId, r.requestStoreId "
			+ " FROM StoresReturnEntity r, StoreIndentEntity si where si.storeIndentId=r.storeIndentId and r.orgId=:orgId AND "
			+ " (:storeReturnId IS NULL OR r.storeReturnId=:storeReturnId) AND (:mdnId IS NULL OR r.mdnId=:mdnId) AND "
			+ " (:fromDate IS NULL OR r.storeReturnDate>=:fromDate) AND (:toDate IS NULL OR r.storeReturnDate>=:toDate) AND "
			+ " (:issueStoreId IS NULL OR r.issueStoreId=:issueStoreId) AND (:requestStoreId IS NULL OR r.requestStoreId=:requestStoreId) ")
	List<Object[]> storeReturnSummaryList(@Param("orgId") Long orgId, @Param("storeReturnId") Long storeReturnId,
			@Param("mdnId") Long mdnId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, 
			@Param("issueStoreId") Long issueStoreId, @Param("requestStoreId") Long requestStoreId);

}
