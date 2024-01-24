package com.abm.mainet.materialmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.IndentIssueEntity;
import com.abm.mainet.materialmgmt.domain.IndentProcessEntity;

@Repository
public interface IndentProcessRepository extends JpaRepository<IndentProcessEntity, Long> {

	@Query("select b from IndentProcessEntity b where b.indentno=:indentno and b.orgid=:orgid")
	public IndentProcessEntity findByIndentNo(@Param("indentno") String indentno, @Param("orgid") Long orgid);

	/**
	 * update Indent Status and wfFlag
	 * @param orgId
	 * @param indentNo
	 * @param status 
	 * @param wfFlag
	 */
	@Modifying
	@Query("UPDATE IndentProcessEntity tm SET tm.status =:status , tm.wfFlag=:wfFlag WHERE tm.orgid =:orgId and tm.indentno =:indentNo")
	void updateIndentStatus(@Param("orgId") Long orgId, @Param("indentNo") String indentNo, @Param("status") String status, 
			@Param("wfFlag") String wfFlag);

	@Query("select b from IndentProcessEntity b where  b.orgid=:orgid order by b.indentid desc")
	public List<IndentProcessEntity> findIndentByorgId(@Param("orgid") Long orgid);


	@Query(value = "select BINLOCID binLocId, BINLOCATION binLocation from MM_BIN_LOCATION_MAS b where b.ORGID=:orgId and b.BINLOCID  "
			+ " in(select grn.binlocation from mm_grn_items_entry grn where grn.storeid=:storeId and grn.itemid=:itemId)  " 
			+ " union  " 
			+ "	select BINLOCID binLocId, BINLOCATION binLocation from MM_BIN_LOCATION_MAS b where b.ORGID=:orgId and b.BINLOCID  "
			+ " in(select iob.binlocation from mm_itemopeningbalance_det iob inner join mm_itemopeningbalance ob  "
			+ " on ob.openbalid=iob.openbalid where ob.storeid=:storeId and iob.itemid=:itemId)", nativeQuery = true)
	public List<Object[]> findByOrgIdAndItemId(@Param("orgId") Long orgId, @Param("storeId") Long storeId, @Param("itemId") Long itemId);


	@Query(value = "SELECT itemNo, bal_qty FROM ( SELECT grn.Itemno AS itemNo, grn.quantity - IFNULL((SELECT SUM(IFNULL(issueqty,0))  " + 
			" FROM mm_indent_issue ind INNER JOIN mm_indent imas ON ind.indentid = imas.indentid WHERE ind.binLocation = grn.binlocation  " + 
			" AND ind.itemId = grn.itemId AND ind.itemNo = grn.itemNo AND ind.orgId = grn.ORGID AND imas.storeid = grn.storeid   " + 
			" GROUP BY ind.binLocation, imas.storeId, ind.itemId, ind.itemNo, ind.orgId),0) - IFNULL((SELECT SUM(IFNULL(ex.quantity,0))  " + 
			" FROM MM_EXPIRED_DET ex WHERE ex.binLocation = grn.binlocation AND ex.storeid = grn.storeid AND ex.itemid = grn.itemId  " + 
			" AND ex.itemno = grn.itemNo AND ex.ORGID = grn.ORGID GROUP BY ex.binLocation, ex.storeId, ex.itemId, ex.itemNo, ex.orgId),0) AS bal_qty  " + 
			" FROM mm_grn_items_entry grn WHERE grn.binlocation = :binLocation AND grn.storeid = :storeId AND grn.itemId = :itemId  " + 
			" AND grn.ORGID = :orgId AND (IFNULL(grn.expirydate, '') = '' OR grn.expirydate > CURRENT_DATE()) " + 
			" UNION  " + 
			" SELECT iob.itemNo AS itemNo, iob.quantity - IFNULL((SELECT SUM(IFNULL(issueqty,0)) FROM mm_indent_issue ind  " + 
			" INNER JOIN mm_indent imas ON ind.indentid = imas.indentid WHERE ind.binLocation = iob.binlocation AND ind.itemId = iob.itemId  " + 
			" AND ind.itemNo = iob.itemNo AND ind.orgId = iob.ORGID AND imas.storeid = ob.storeid  " + 
			" GROUP BY ind.binLocation, imas.storeId, ind.itemId, ind.itemNo, ind.orgId),0) - IFNULL((SELECT SUM(IFNULL(ex.quantity,0))  " + 
			" FROM MM_EXPIRED_DET ex WHERE ex.binLocation = iob.binlocation AND ex.storeid = ob.storeid AND ex.itemid = iob.itemId  " + 
			" AND ex.itemno = iob.itemNo AND ex.ORGID = iob.ORGID GROUP BY ex.binLocation, ex.storeId, ex.itemId, ex.itemNo, ex.orgId),0) AS bal_qty  " + 
			" FROM mm_itemopeningbalance_det iob INNER JOIN mm_itemopeningbalance ob ON ob.openbalid = iob.openbalid  " + 
			" WHERE iob.binLocation = :binLocation AND ob.storeId = :storeId AND iob.itemId = :itemId AND iob.ORGID = :orgId  " + 
			" and (ifnull(iob.expirydate,'') = '' or iob.expirydate > current_date()) ) AS subquery WHERE bal_qty != 0;" , nativeQuery = true)
	public List<Object[]> fetchItemNoListByBinAndItemId(@Param("binLocation") Long binLocation, @Param("itemId") Long itemId, 
			@Param("storeId") Long storeId, @Param("orgId") Long orgId);	
	
	
	@Query(value = "select grn.quantity - ifnull((select sum(ifnull(issueqty,0)) from mm_indent_issue ind  "
			+ "inner join mm_indent imas on ind.indentid = imas.indentid where ind.binLocation = grn.binlocation  "
			+ "and ind.itemId = grn.itemId and ind.itemNo = grn.itemNo and ind.orgId = grn.ORGID and  imas.storeid = grn.storeid  "
			+ "group by ind.binLocation, imas.storeId, ind.itemId, ind.itemNo, ind.orgId ),0) bal_qty  "
			+ "from mm_grn_items_entry grn where grn.binlocation=:binLocation and grn.storeid=:storeId and grn.itemId=:itemId  "
			+ "and grn.itemNo=:itemNo and  grn.ORGID=:orgId and (ifnull(grn.expirydate,'') = '' or grn.expirydate > current_date())  "
			+ "UNION  "
			+ "select iob.quantity - ifnull((select sum(ifnull(issueqty,0)) from mm_indent_issue ind  "
			+ "inner join mm_indent imas on ind.indentid = imas.indentid where ind.binLocation = iob.binlocation  "
			+ "and ind.itemId = iob.itemId and ind.itemNo = iob.itemNo and ind.orgId = iob.ORGID and imas.storeid = ob.storeid  "
			+ "group by ind.binLocation, imas.storeId, ind.itemId, ind.itemNo, ind.orgId ),0) bal_qty  "
			+ "from mm_itemopeningbalance_det iob inner join mm_itemopeningbalance ob on ob.openbalid=iob.openbalid  "
			+ "where iob.binLocation=:binLocation and ob.storeId=:storeId and iob.itemId=:itemId and iob.itemNo=:itemNo  "
			+ "and iob.ORGID=:orgId and (ifnull(iob.expirydate,'') = '' or iob.expirydate > current_date()) ", nativeQuery = true)
	public Double getSumOfQuantityByItemNoAndBinLocation(@Param("binLocation") Long binLocation, @Param("itemId") Long itemId,
			@Param("itemNo") String itemNo, @Param("storeId") Long storeId, @Param("orgId") Long orgId);
	
	
	@Query(value = "SELECT IFNULL(SUM(bal_qty), 0) AS total_sum FROM (SELECT grn.quantity - IFNULL((SELECT "
			+ " SUM(IFNULL(issueqty, 0)) FROM mm_indent_issue ind INNER JOIN mm_indent imas ON ind.indentid = imas.indentid "
			+ " WHERE ind.binLocation = grn.binlocation AND ind.itemId = grn.itemId AND ind.orgId = grn.ORGID "
			+ " AND imas.storeid = grn.storeid GROUP BY ind.binLocation, imas.storeId, ind.itemId, ind.orgId ), 0) AS bal_qty "
			+ " FROM mm_grn_items_entry grn WHERE grn.binlocation=:binLocation AND grn.storeid=:storeId AND grn.itemId=:itemId "
			+ " AND grn.ORGID=:orgId AND (IFNULL(grn.expirydate, '') = '' OR grn.expirydate > CURRENT_DATE()) "
			+ " UNION "
			+ " SELECT iob.quantity - IFNULL((SELECT SUM(IFNULL(issueqty, 0)) FROM mm_indent_issue ind INNER JOIN "
			+ " mm_indent imas ON ind.indentid = imas.indentid WHERE ind.binLocation = iob.binlocation AND "
			+ " ind.itemId = iob.itemId AND ind.orgId = iob.ORGID AND imas.storeid = ob.storeid GROUP BY ind.binLocation, "
			+ " imas.storeId, ind.itemId, ind.orgId ), 0) AS bal_qty FROM mm_itemopeningbalance_det iob INNER JOIN "
			+ " mm_itemopeningbalance ob ON ob.openbalid = iob.openbalid WHERE iob.binLocation=:binLocation AND "
			+ " ob.storeId=:storeId AND iob.itemId=:itemId AND iob.ORGID=:orgId AND (IFNULL(iob.expirydate, '') = '' OR "
			+ " iob.expirydate > CURRENT_DATE())) AS subquery;", nativeQuery = true)
	public Double getSumOfNotInBatchQtyByBin(@Param("binLocation") Long binLocation, @Param("itemId") Long itemId, @Param("storeId") Long storeId, 
			@Param("orgId") Long orgId);

	public IndentProcessEntity findByIndentidAndOrgid(Long indentid, Long orgid);
	
	@Query("select b from IndentProcessEntity b where b.status=:status and b.orgid=:orgid")
	public List<IndentProcessEntity> findindentBystatus(@Param("status") String status, @Param("orgid") Long orgid);
	
	@Query("select b from IndentIssueEntity b where b.indentid.indentid=:indentid and b.ORGID=:ORGID")
	public List<IndentIssueEntity> findItemInfoByIndent(@Param("indentid") Long indentid, @Param("ORGID") Long ORGID);
	
	
	

}
