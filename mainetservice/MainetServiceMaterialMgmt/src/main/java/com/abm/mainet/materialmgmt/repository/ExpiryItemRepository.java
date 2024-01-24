package com.abm.mainet.materialmgmt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.ExpiryItemsEntity;

@Repository
public interface ExpiryItemRepository extends CrudRepository<ExpiryItemsEntity, Long> {

	@Query(value = "select\r\n" + 
			"distinctrow\r\n" + 
			"grn.grnitementryid, \r\n" + 
			"grn.grnid,\r\n" + 
			"grn.storeid, \r\n" + 
			"grn.itemid, \r\n" + 
			"grn.grnitemid, \r\n" + 
			"grn.Itemno, \r\n" + 
			"grn.quantity,\r\n" + 
			"grn.decision,  \r\n" + 
			"grn.mfgdate,  \r\n" + 
			"grn.expirydate,\r\n" + 
			"grn.rejectionreason, \r\n" + 
			"det.binlocation,\r\n"+
			"grn.returnid,\r\n" + 
			"grn.Status,\r\n" + 
			"grn.ORGID  from mm_itemopeningbalance_det det,mm_grn_items_entry grn where det.itemid=grn.itemid "
			+ "and grn.ORGID=:orgid and grn.expirydate BETWEEN :fromdate AND :todate", nativeQuery = true)
	public List<Object[]> getExpiryItemDataByDate(@Param("orgid") long orgid, @Param("fromdate") Date fromdate,
			@Param("todate") Date todate);	

	public ExpiryItemsEntity findByApplicationIdAndOrgIdOrderByExpiryIdDesc(Long applicationId, Long orgId);

	
	@Modifying
    @Query("UPDATE ExpiryItemsEntity  ei  SET ei.status =:status WHERE ei.orgId =:orgId and ei.applicationId=:applicationId")
    void updateDispoalStatus(@Param("orgId") Long orgId, @Param("applicationId") Long applicationId, @Param("status") String status);
	

	@Query(value = 
			  "select grn.binlocation loc, grn.itemid itemid, grn.Itemno itemNo, grn.quantity - ifnull((select sum(ifnull(issueqty,0)) \r\n" + 
			  "	   from mm_indent_issue ind inner join mm_indent imas on ind.indentid = imas.indentid where ind.binLocation = grn.binlocation \r\n" + 
			  "	   and ind.itemId = grn.itemId and ind.itemNo = grn.itemNo and ind.orgId = grn.ORGID and  imas.storeid = grn.storeid \r\n" + 
			  "	   group by ind.binLocation, imas.storeId, ind.itemId, ind.itemNo, ind.orgId ),0) - ifnull((select sum(ifnull(ex.quantity,0)) \r\n" + 
			  "	   from MM_EXPIRED_DET ex where ex.binLocation= grn.binlocation and ex.storeid = grn.storeid and ex.itemid = grn.itemId and \r\n" + 
			  "	   ex.itemno = grn.itemNo and ex.ORGID = grn.ORGID and ex.Flag='Y' group by ex.binLocation, ex.storeId, ex.itemId, ex.itemNo, ex.orgId ),0) exp_qty\r\n" + 
			  "	   from mm_grn_items_entry grn where grn.storeid=:storeId and grn.expirydate<=:expiryDate and grn.ORGID=:orgId  " + 
			  "	   and grn.binlocation IS NOT NULL \r\n" + 
			  "	   UNION \r\n" + 
			  "	   select iob.binlocation loc, iob.itemId itemId, iob.itemNo itemNo, iob.quantity - ifnull((select sum(ifnull(issueqty,0)) \r\n" + 
			  "	   from mm_indent_issue ind inner join mm_indent imas on ind.indentid = imas.indentid where ind.binLocation = iob.binlocation \r\n" + 
			  "	   and ind.itemId = iob.itemId and ind.itemNo = iob.itemNo and ind.orgId = iob.ORGID and imas.storeid = ob.storeid \r\n" + 
			  "	   group by ind.binLocation, imas.storeId, ind.itemId, ind.itemNo, ind.orgId ),0) - ifnull((select sum(ifnull(ex.quantity,0)) \r\n" + 
			  "	   from MM_EXPIRED_DET ex where ex.binLocation= iob.binlocation and ex.storeid = ob.storeid and ex.itemid = iob.itemId and\r\n" + 
			  "	   ex.itemno = iob.itemNo and ex.ORGID = iob.ORGID and ex.Flag='Y' group by ex.binLocation, ex.storeId, ex.itemId, ex.itemNo, ex.orgId ),0)  exp_qty\r\n" + 
			  "	   from mm_itemopeningbalance_det iob inner join mm_itemopeningbalance ob on ob.openbalid=iob.openbalid where \r\n" + 
			  "	   ob.storeId=:storeId and iob.expiryDate<=:expiryDate and iob.ORGID=:orgId \r\n" + 
			  "	   and iob.binlocation IS NOT NULL", nativeQuery = true)
	public List<Object[]> getDataByStoreAndExpDate(@Param("storeId") Long storeId, @Param("expiryDate") Date expiryDate,
			@Param("orgId") Long orgId);	
	
	public List<ExpiryItemsEntity> findByDepartmentAndOrgIdAndStatusOrderByExpiryId(@Param("department") Long department,
			  @Param("orgId") Long orgId,@Param("status") String status);

	@Modifying
	@Query("update ExpiryItemsEntity exp set exp.status=:status, exp.updatedBy=:updatedBy, exp.updatedDate=CURRENT_DATE "
			+ "where exp.expiryId in (:expiryIds)")
	public void updateStatusPurchaseReq(@Param("expiryIds") List<Long> expiryIds, @Param("status") String status,
			@Param("updatedBy") Long updatedBy);

	public List<ExpiryItemsEntity> findByOrgIdAndExpiryIdIn(@Param("orgId") Long orgId, @Param("expiryId") List<Long> expiryId);

	
	@Query(value = "select count(c.CONT_ID) from TB_CONTRACT_MAST c where c.LOA_NO=(select TND_LOA_NO from TB_WMS_TENDER_WORK where "
			+ "DISPOSAL_ID=:expiryId and ORGID=:orgId LIMIT 1)", nativeQuery = true)
	public Long checkForContractAggreement(@Param("expiryId") Long expiryId, @Param("orgId") Long orgId);

	
}
