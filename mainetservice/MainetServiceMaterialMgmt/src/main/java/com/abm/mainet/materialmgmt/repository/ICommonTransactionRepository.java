package com.abm.mainet.materialmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.materialmgmt.domain.CommonTransactionEntity;

public interface ICommonTransactionRepository extends JpaRepository<CommonTransactionEntity, Long> {

	@Query(value = "SELECT BINLOCID, BINLOCATION from MM_BIN_LOCATION_MAS b where b.ORGID=:orgId and b.BINLOCID in "
			+ " (SELECT distinct a.binlocation FROM (SELECT storeid, itemid, binlocation, itemno, "
			+ " COALESCE(SUM(creditqty), 0) AS crdty,  MAX(CASE WHEN itemno IS NOT NULL THEN expirydate END) AS "
			+ " expirydate_check FROM mm_transactions WHERE transactiontype IN ('P', 'S', 'O', 'R') AND "
			+ " disposalstatus = 'N' AND ORGID=:orgId GROUP BY storeid, itemid, binlocation, itemno) a "
			+ " LEFT JOIN "
			+ " (SELECT storeid, itemid, binlocation, itemno, COALESCE(SUM(debitqty), 0) AS drdty FROM "
			+ " mm_transactions WHERE transactiontype IN ('M', 'I') AND disposalstatus = 'N' AND ORGID=:orgId "
			+ " GROUP BY storeid, itemid, binlocation, itemno) b ON a.storeid = b.storeid AND a.itemid = b.itemid "
			+ " AND a.binlocation = b.binlocation AND a.itemno = b.itemno WHERE a.storeid=:storeId "
			+ " AND a.itemid=:itemId and (ifnull(a.crdty,0) - ifnull(b.drdty,0)) > 0 AND (null IS NULL OR a.itemno=null) "
			+ " AND (a.expirydate_check IS NULL OR if(a.itemno is not null,a.expirydate_check > CURRENT_DATE(),1=1)))", nativeQuery = true)
	public List<Object[]> fetchBinLocationListForIndent(@Param("orgId") Long orgId, @Param("storeId") Long storeId,
			@Param("itemId") Long itemId);

	@Query(value = "SELECT distinct a.itemno FROM (SELECT storeid, itemid, binlocation, itemno, COALESCE(SUM(creditqty), 0) AS crdty, "
			+ " MAX(CASE WHEN itemno IS NOT NULL THEN expirydate END) AS expirydate_check FROM mm_transactions WHERE transactiontype "
			+ " IN ('P', 'S', 'O', 'R') AND disposalstatus = 'N' AND ORGID=:orgId GROUP BY storeid, itemid, binlocation, itemno) a "
			+ " LEFT JOIN "
			+ " (SELECT storeid, itemid, binlocation, itemno, COALESCE(SUM(debitqty), 0) AS drdty FROM mm_transactions WHERE "
			+ " transactiontype IN ('M', 'I') AND disposalstatus = 'N' AND ORGID=:orgId GROUP BY storeid, itemid, binlocation, itemno) b  "
			+ " ON a.storeid = b.storeid AND a.itemid = b.itemid AND a.binlocation = b.binlocation AND a.itemno = b.itemno "
			+ " WHERE a.storeid=:storeId AND a.itemid=:itemId AND a.binlocation=:binLocation and (ifnull(a.crdty,0) - ifnull(b.drdty,0)) > 0  "
			+ " AND (null IS NULL OR a.itemno=null) AND (a.expirydate_check IS NULL OR if(a.itemno is not null,a.expirydate_check > CURRENT_DATE(),1=1));", nativeQuery = true)
	public List<String> fetchItemNoListForIndent(@Param("orgId") Long orgId, @Param("storeId") Long storeId,
			@Param("itemId") Long itemId, @Param("binLocation") Long binLocation);

	@Query(value = "SELECT (ifnull(a.crdty,0) - ifnull(b.drdty,0)) AS difference FROM (SELECT storeid, itemid, binlocation, "
			+ " ifnull(itemno,'') as itemno, COALESCE(SUM(creditqty), 0) AS crdty,  MAX(CASE WHEN itemno IS NOT NULL THEN expirydate END) "
			+ " AS expirydate_check FROM mm_transactions WHERE transactiontype IN ('P', 'S', 'O', 'R') AND disposalstatus = 'N' AND "
			+ " ORGID=:orgId GROUP BY storeid, itemid, binlocation, ifnull(itemno,'')) a "
			+ " LEFT JOIN "
			+ " (SELECT storeid, itemid, binlocation,  ifnull(itemno,'') as itemno, COALESCE(SUM(debitqty), 0) AS drdty FROM "
			+ " mm_transactions WHERE transactiontype IN ('M', 'I') AND disposalstatus = 'N' AND ORGID=:orgId GROUP BY storeid, "
			+ " itemid, binlocation, ifnull(itemno,'')) b "
			+ " ON a.storeid = b.storeid AND a.itemid = b.itemid AND a.binlocation = b.binlocation AND a.itemno = b.itemno "
			+ " WHERE a.storeid=:storeId AND a.itemid=:itemId AND a.binlocation=:binLocation AND (:itemNo IS NULL OR a.itemno=:itemNo) "
			+ " and (ifnull(a.crdty,0) - ifnull(b.drdty,0)) > 0 "
			+ " AND (a.expirydate_check IS NULL OR if(a.itemno is not null,a.expirydate_check > CURRENT_DATE(),1=1));", nativeQuery = true)
	public Double fetchBalanceQuantityForIndent(@Param("orgId") Long orgId, @Param("storeId") Long storeId,
			@Param("itemId") Long itemId, @Param("binLocation") Long binLocation, @Param("itemNo") String itemNo);

}
