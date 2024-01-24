package com.abm.mainet.common.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;

@Repository
public interface ReceiptRepository extends CrudRepository<TbServiceReceiptMasEntity, Long> {

    TbServiceReceiptMasEntity findByRmRcptidAndOrgId(Long refId, long orgid);

    @Query("select sum(rmAmount) from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.apmApplicationId =:applicationNo and r.receiptTypeFlag='R'")
    BigDecimal getPaidAmountByAppNo(@Param("orgId") Long orgId, @Param("applicationNo") Long applicationNo,
            @Param("deptId") Long deptId);

    @Query("select r from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.apmApplicationId =:applicationNo and r.receiptTypeFlag='RB' ")
    List<TbServiceReceiptMasEntity> getAllActiveRebetReceiptByAppNo(@Param("orgId") Long orgId,
            @Param("applicationNo") Long applicationNo,
            @Param("deptId") Long deptId);

    @Query("select sum(rmAmount) from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.apmApplicationId =:applicationNo and r.receiptTypeFlag='AR'")
    BigDecimal getAjustedAdvancedAmountByAppNo(@Param("orgId") Long orgId, @Param("applicationNo") Long applicationNo,
            @Param("deptId") Long deptId);

    @Query("select sum(rmAmount) from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.additionalRefNo =:additionalRefNo")
    BigDecimal getPaidAmountByAdditionalRefNo(@Param("orgId") Long orgId, @Param("additionalRefNo") String additionalRefNo,
            @Param("deptId") Long deptId);

    @Query("select r from TbServiceReceiptMasEntity r where r.receiptDelFlag is null and r.rmRcptid = (select max(rec.rmRcptid) from TbServiceReceiptMasEntity rec where"
            +
            " rec.orgId =:orgId  and rec.additionalRefNo =:additionalRefNo and receiptTypeFlag='R') ")
    TbServiceReceiptMasEntity getLatestReceiptDetailByAddRefNo(@Param("orgId") Long orgId,
            @Param("additionalRefNo") String additionalRefNo);

    @Query("select r from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.apmApplicationId =:applicationNo and r.receiptTypeFlag='RB' and r.receiptDelFlag is null")
    List<TbServiceReceiptMasEntity> getRebateByAppNo(@Param("orgId") Long orgId, @Param("applicationNo") Long applicationNo,
            @Param("deptId") Long deptId);

    @Query("select r from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.additionalRefNo =:additionalRefNo and r.receiptDelFlag is null")
    List<TbServiceReceiptMasEntity> getCollectionDetails(@Param("additionalRefNo") String additionalRefNo,
            @Param("deptId") Long deptId, @Param("orgId") long orgid);
    
    @Query("select r from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.additionalRefNo =:additionalRefNo and r.receiptDelFlag='Y' ")
    List<TbServiceReceiptMasEntity> getCollectionDetailsInactive(@Param("additionalRefNo") String additionalRefNo,
            @Param("deptId") Long deptId, @Param("orgId") long orgid);

    @Query("select distinct rm.rmReceivedfrom from TbServiceReceiptMasEntity rm where rm.orgId =:orgId and rm.dpDeptId =:deptId ")
    List<String> getPayeeNames(@Param("orgId") Long orgId, @Param("deptId") Long deptId);

    @Query("select rm from TbServiceReceiptMasEntity rm where rm.dpDeptId =:deptId and  rm.orgId =:orgId and rm.apmApplicationId is null and rm.smServiceId is null ORDER BY rmRcptno DESC")
    List<TbServiceReceiptMasEntity> getAllReceiptsByOrgId(@Param("deptId") Long deptId, @Param("orgId") Long orgId);
    
    @Query("select rm from TbServiceReceiptMasEntity rm where rm.dpDeptId =:deptId and  rm.orgId =:orgId and rm.apmApplicationId is null ORDER BY rmDate DESC")
    List<TbServiceReceiptMasEntity> getAllReceiptsByOrgIdAndDep(@Param("deptId") Long deptId, @Param("orgId") Long orgId);

    @Query("select r from TbServiceReceiptMasEntity r where " +
            "r.rmRcptno =:rcptNo and r.orgId =:orgId")
    TbServiceReceiptMasEntity getReceiptDetailByRcptNoAndOrgId(@Param("rcptNo") long rcptNo, @Param("orgId") Long orgId);

    @Query("select count(r) from TbServiceReceiptMasEntity r where " +
            "r.additionalRefNo =:additionalRefNo and r.orgId =:orgId")
    int getReceiptDetailByRcptNoAndOrgId(@Param("additionalRefNo") String additionalRefNo,
            @Param("orgId") Long orgId);

    @Query("FROM TbServiceReceiptMasEntity r WHERE r.rmLoiNo =:rmLoiNo AND r.orgId =:orgId")
    TbServiceReceiptMasEntity getReceiptNoByLoiNoAndOrgId(@Param("rmLoiNo") String rmLoiNo, @Param("orgId") Long orgId);

    @Query("select r from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.rmRcptno =:receiptNo and r.rmDate=:receiptDate and (r.receiptDelFlag <> 'Y' or r.receiptDelFlag is null)")
    List<TbServiceReceiptMasEntity> getReceiptByDeptAndDate(@Param("receiptNo") Long receiptNo, @Param("orgId") Long orgId,
            @Param("receiptDate") Date receiptDate,
            @Param("deptId") Long deptId);

    @Query("select  count(1) from TbServiceReceiptMasEntity a where a.additionalRefNo=:additionalRefNo and  a.orgId=:orgId and a.dpDeptId =:deptId and a.receiptTypeFlag='R' and (a.receiptDelFlag <> 'Y' or a.receiptDelFlag is null) and rm_date >(select r.rmDate from TbServiceReceiptMasEntity r where r.rmRcptid =:rmRcptid and  (a.receiptDelFlag <> 'Y' or a.receiptDelFlag is null))")
    int getCountAllNextGeneratedReceipt(@Param("additionalRefNo") String additionalRefNo,
            @Param("orgId") long orgId, @Param("rmRcptid") long rmRcptid, @Param("deptId") Long deptId);

    @Modifying
    @Query("update TbServiceReceiptMasEntity u set u.receiptDelFlag =:status,u.receiptDelRemark =:receiptDelRemark,u.updatedBy=:userId,u.receiptDelDate=:deleteDate,u.updatedDate=:deleteDate,u.lgIpMacUpd=:updatedIPAddress,u.receipt_del_auth_by=:userId where u.rmRcptid =:rmRcptid and u.orgId=:orgId and u.dpDeptId =:deptId")
    int updateSetForReceiptDelFlag(@Param("status") String status,
            @Param("rmRcptid") long rmRcptid, @Param("orgId") long orgId, @Param("receiptDelRemark") String receiptDelRemark,
            @Param("userId") long userId, @Param("deptId") Long deptId, @Param("updatedIPAddress") String updatedIPAddress,
            @Param("deleteDate") Date deleteDate);

    @Query("select count(d) from TbSrcptFeesDetEntity d where d.taxId=:taxId and rmRcptid in(select r.rmRcptid  from TbServiceReceiptMasEntity r where "
            +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.apmApplicationId =:applicationNo and r.receiptTypeFlag='R') ")
    int getcountOfTaxExistAgainstAppId(@Param("orgId") Long orgId, @Param("applicationNo") Long applicationNo,
            @Param("deptId") Long deptId, @Param("taxId") Long taxId);
    
    
    @Query("select r from TbServiceReceiptMasEntity r where r.apmApplicationId=:applicationNo  and r.orgId=:orgId")
    TbServiceReceiptMasEntity getReceiptDetailsByAppId(@Param("applicationNo") Long applicationNo,@Param("orgId") Long orgId);

    @Query("SELECT SUM(rcp.rmAmount) FROM TbServiceReceiptMasEntity rcp WHERE rcp.refId=:refId AND rcp.receiptTypeFlag=:receiptType AND rcp.orgId=:orgId AND rcp.receiptDelFlag is null")
    Long getTotalReceiptAmountByRefIdAndReceiptType(@Param("refId") Long refId, @Param("receiptType") String receiptType, @Param("orgId") Long orgId);
    
    @Query("select sum(rmAmount) from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.apmApplicationId =:applicationNo and r.receiptTypeFlag='R' and r.receiptDelFlag is null")
    BigDecimal getActivePaidAmountByAppNo(@Param("orgId") Long orgId, @Param("applicationNo") Long applicationNo,
            @Param("deptId") Long deptId);
    
    @Query("select r from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.apmApplicationId =:applicationNo and r.receiptTypeFlag='R' ")
    List<TbServiceReceiptMasEntity> getAllActiveDemandReceiptByAppNo(@Param("orgId") Long orgId,
            @Param("applicationNo") Long applicationNo,
            @Param("deptId") Long deptId);
    
    @Query("select r from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.refId =:refId and r.rmDate=:receiptDate and r.receiptTypeFlag=:receiptType and r.receiptDelFlag is null ")
    List<TbServiceReceiptMasEntity> getReceiptByDeptAndDateAndReceiptType(@Param("refId") Long refId, @Param("orgId") Long orgId,
            @Param("receiptDate") Date receiptDate,
            @Param("deptId") Long deptId, @Param("receiptType") String receiptType);
    
    @Query("select r from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.additionalRefNo =:additionalRefNo and r.rmDate=:receiptDate and r.receiptDelFlag is null ")
    List<TbServiceReceiptMasEntity> getReceiptByDeptAndDateAddRefNo(@Param("additionalRefNo") String additionalRefNo, @Param("orgId") Long orgId,
            @Param("receiptDate") Date receiptDate,
            @Param("deptId") Long deptId);
    
	@Query("select  count(a) from TbServiceReceiptMasEntity a where a.additionalRefNo =:propNo and a.receiptTypeFlag='R' and  a.orgId=:orgId and (a.rmDate between :fromDate and :toDate) and a.receiptDelFlag is null")
	Long getCountOfbillPaidBetweenCurFinYear(@Param("propNo") String propNo, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate ,@Param("orgId") Long orgId);
	
	@Query("select a from TbServiceReceiptMasEntity a where a.rmRcptid =:rmRcptId and a.additionalRefNo =:propNo and (a.receiptTypeFlag='A' or a.receiptTypeFlag='R') and  a.orgId=:orgId and a.manualReceiptNo is not null and a.receiptDelFlag is null")
	TbServiceReceiptMasEntity getmanualReceiptAdvanceAmountByAddRefNo(@Param("rmRcptId") Long receiptId,@Param("propNo") String propNo,@Param("orgId") Long orgId);
	
	@Query("select  count(a) from TbServiceReceiptMasEntity a where a.additionalRefNo =:propNo and (a.receiptTypeFlag='R' or a.receiptTypeFlag='A') and a.manualReceiptNo is not null and a.orgId=:orgId and (a.rmDate between :fromDate and :toDate) and a.receiptDelFlag is null")
	Long getCountOfbillPaidBetweenCurFinYearAdvance(@Param("propNo") String propNo, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate ,@Param("orgId") Long orgId);
	
	@Query("select  a from TbServiceReceiptMasEntity a where a.additionalRefNo =:addRefNo and  a.orgId=:orgId and (a.rmDate between :fromDate and :toDate) and a.receiptDelFlag is null")
	 List<TbServiceReceiptMasEntity> getbillPaidBetweenCurFinYear(@Param("addRefNo") String addRefNo, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate ,@Param("orgId") Long orgId);

	@Query("select sum(rmAmount) from TbServiceReceiptMasEntity r where "
			+ "r.dpDeptId =:deptId and r.orgId =:orgId  and r.additionalRefNo =:additionalRefNo and r.receiptDelFlag is null")
	BigDecimal getPaidAmountByAdditionalRefNoIncRebate(@Param("orgId") Long orgId,
			@Param("additionalRefNo") String additionalRefNo, @Param("deptId") Long deptId);
	
	@Query("select r from TbServiceReceiptMasEntity r where "
			+ " r.orgId =:orgId  and r.additionalRefNo =:propNo and r.receiptTypeFlag='RB'  and r.receiptDelFlag is null")
	List<TbServiceReceiptMasEntity> getRebateByAdditionalRefNo(@Param("orgId") Long orgId,
			@Param("propNo") String propNo);
	
	@Query("select r from TbServiceReceiptMasEntity r where r.receiptDelFlag is null and r.rmRcptid = (select max(rec.rmRcptid) from TbServiceReceiptMasEntity rec where"
            +
            " rec.orgId =:orgId  and rec.additionalRefNo =:additionalRefNo and rec.flatNo=:flatNo and receiptTypeFlag='R') ")
    TbServiceReceiptMasEntity getLatestReceiptDetailByAddRefNoAndFlatNo(@Param("orgId") Long orgId,
            @Param("additionalRefNo") String additionalRefNo,@Param("flatNo") String flatNo);
	 
	 @Query("select r from TbServiceReceiptMasEntity r where r.apmApplicationId=:applicationId and r.smServiceId=:smServiceId and r.orgId=:orgId")
		TbServiceReceiptMasEntity getReceiptDetByAppIdAndServiceId(@Param("applicationId") Long applicationId, @Param("smServiceId") Long smServiceId, @Param("orgId") Long orgId);

	 @Query("select  count(a) from TbServiceReceiptMasEntity a where a.additionalRefNo =:propNo and a.receiptTypeFlag='RB' and  a.orgId=:orgId and a.rmNarration=:narration and (a.rmDate between :fromDate and :toDate) and a.receiptDelFlag is null")
		Long getCountOfbillPaidBetweenSelectedPeriod(@Param("propNo") String propNo, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate ,@Param("orgId") Long orgId,@Param("narration") String narration);
	 
	 @Query("select  count(a) from TbServiceReceiptMasEntity a where a.additionalRefNo =:propNo and a.receiptTypeFlag='RB' and  a.orgId=:orgId and a.rmNarration=:narration and a.flatNo=:flatNo and (a.rmDate between :fromDate and :toDate) and a.receiptDelFlag is null")
		Long getCountOfbillPaidBetweenSelectedPeriodByFlatNo(@Param("propNo") String propNo, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate ,@Param("orgId") Long orgId,@Param("narration") String narration,@Param("flatNo") String flatNo);
	 
	 @Query("select sum(rmAmount) from TbServiceReceiptMasEntity r where "
				+ "r.dpDeptId =:deptId and r.orgId =:orgId  and r.additionalRefNo =:additionalRefNo and r.receiptTypeFlag in('R','RB') and r.receiptDelFlag is null")
		BigDecimal getPaidAmountByAdditionalRefNoExcludRebate(@Param("orgId") Long orgId,
				@Param("additionalRefNo") String additionalRefNo, @Param("deptId") Long deptId);
	 @Query("select sum(rmAmount) from TbServiceReceiptMasEntity r where "
				+ "r.dpDeptId =:deptId and r.orgId =:orgId  and r.additionalRefNo =:additionalRefNo and r.receiptTypeFlag in('R','RB') and r.flatNo=:flatNo and r.receiptDelFlag is null")
		BigDecimal getPaidAmountByAdditionalRefNoAndFlatNoIncludeRebate(@Param("orgId") Long orgId,
				@Param("additionalRefNo") String additionalRefNo, @Param("deptId") Long deptId, @Param("flatNo") String flatNo);
	 @Query("select  count(a) from TbSrcptModesDetEntity a where a.cbBankid =:bankId and a.rdChequeddno=:chequeNo")
	 Long getDuplicateChequeNoCount(@Param("bankId") Long bankId, @Param("chequeNo") Long chequeNo);

	 @Query("select r from TbServiceReceiptMasEntity r where r.rmRcptno =:rcptNo and r.orgId =:orgId and r.dpDeptId =:deptId ")
	TbServiceReceiptMasEntity getReceiptDetailByRcptNoAndDeptId(@Param("rcptNo") Long rcptNo, @Param("orgId") Long orgId,@Param("deptId")  Long deptId);
	 
	 @Query("select r from TbServiceReceiptMasEntity r where r.rmRcptno =:rcptNo and r.orgId =:orgId and r.dpDeptId =:deptId and r.rmDate =:rmDate")
	 TbServiceReceiptMasEntity getReceiptDetailByRcptNoAndDeptIdAndRmDate(@Param("rcptNo") Long rcptNo, @Param("orgId") Long orgId,@Param("deptId")  Long deptId, @Param("rmDate") Date rmDate);
	 
	@Query("SELECT sum(b.rfFeeamount) from TbSrcptFeesDetEntity b  where  b.bmIdNo<=:bmIdNo and b.rmRcptid in (select rmRcptid from TbServiceReceiptMasEntity  where orgId =:orgId  and additionalRefNo =:additionalRefNo and receiptTypeFlag='A' and rmDate=:receiptDate and receiptDelFlag is null)")
		BigDecimal getPaidAmountByAdditionalRefNoDemandAmount(@Param("orgId") Long orgId,
				@Param("additionalRefNo") String additionalRefNo, @Param("bmIdNo") Long bmIdNo, @Param("receiptDate") Date receiptDate);
	
	@Query("select r from TbServiceReceiptMasEntity r where "
			+ "r.dpDeptId =:deptId and r.orgId =:orgId  and r.additionalRefNo =:additionalRefNo and r.flatNo=:flatNo and r.receiptDelFlag is null")
	List<TbServiceReceiptMasEntity> getCollectionDetailsWithFlatNo(@Param("additionalRefNo") String additionalRefNo,
			@Param("flatNo") String flatNo, @Param("deptId") Long deptId, @Param("orgId") Long orgid);
	
	 @Query("select  r from TbServiceReceiptMasEntity r where r.rmRcptid in (select max(rmRcptid) from TbServiceReceiptMasEntity where additionalRefNo=:additionalRefNo and receiptTypeFlag <> 'RB'  and receiptDelFlag is null)")
	 TbServiceReceiptMasEntity getMaxReceiptIdByAdditinalRefNo(@Param("additionalRefNo") String additionalRefNo);
	 
	 @Query("select  r from TbServiceReceiptMasEntity r where r.rmRcptid in (select max(rmRcptid) from TbServiceReceiptMasEntity where additionalRefNo=:additionalRefNo and dpDeptId=:dpDeptId and receiptTypeFlag <> 'RB'  and receiptDelFlag is null)")
	 TbServiceReceiptMasEntity getMaxReceiptIdByAdditinalRefNoAndDeptId(@Param("additionalRefNo") String additionalRefNo, @Param("dpDeptId") Long dpDeptId);
	 
	 @Query("select  r from TbServiceReceiptMasEntity r where r.additionalRefNo =:additionalRefNo and r.orgId =:orgId")
	 List<TbServiceReceiptMasEntity> getPaymentHistoryByAdditinalRefNo(@Param("additionalRefNo") String additionalRefNo, @Param("orgId") Long orgId) ;
	 
	@Modifying
	@Query("delete from TbSrcptFeesDetEntity ad where ad.rfFeeid in(:feeIds)")
	void deleteReceiptdetailByIds(@Param("feeIds") List<Long> feeIdList);

    @Query("select r from TbServiceReceiptMasEntity r where r.rmRcptno =:rcptNo and r.additionalRefNo =:additionalRefNo and r.orgId =:orgId")
    TbServiceReceiptMasEntity getReceiptDetailByCsCcnRcptNoOrgId(@Param("additionalRefNo") String conNo, @Param("rcptNo") Long rcptNo,
            @Param("orgId") Long orgId);
    
    @Query("select r from TbServiceReceiptMasEntity r where r.apmApplicationId=:applicationNo  and r.orgId=:orgId and r.receiptDelFlag is null")
    List<TbServiceReceiptMasEntity> getReceiptDetailsByAppIdList(@Param("applicationNo") Long applicationNo,@Param("orgId") Long orgId);

    @Query("SELECT am FROM  TbServiceReceiptMasEntity am WHERE am.orgId=:orgId AND am.additionalRefNo=:additionalRefNo AND am.dpDeptId=:dpDeptId")
	TbServiceReceiptMasEntity findFromReceiptMaster(@Param("orgId") Long orgId, @Param("additionalRefNo") String additionalRefNo, @Param("dpDeptId") Long dpDeptId);
    
    @Query("SELECT b.cpdFeemode from TbSrcptModesDetEntity b  where   b.rmRcptid in (select rmRcptid from TbServiceReceiptMasEntity  where additionalRefNo =:propNo and receiptDelFlag is null)")
    List<Long> getPayModeIdsByPropertyNo(@Param("propNo") String prpertyNo);
    
    @Query("select r from TbServiceReceiptMasEntity r where " +
            "r.dpDeptId =:deptId and r.orgId =:orgId  and r.posTxnId =:transactionId and r.rmDate=:receiptDate and (r.receiptDelFlag <> 'Y' or r.receiptDelFlag is null)")
    List<TbServiceReceiptMasEntity> getReceiptByDeptAndDateAndTransactionId(@Param("transactionId") String transactionId, @Param("orgId") Long orgId,
            @Param("receiptDate") Date receiptDate,
            @Param("deptId") Long deptId);
    
    @Query("SELECT am.rmAmount FROM  TbServiceReceiptMasEntity am WHERE am.additionalRefNo=:additionalRefNo AND am.rmDate=:receiptDate and am.receiptTypeFlag='RB'")
    Double getRebateAmountByPropNo(@Param("additionalRefNo") String propertyNo, @Param("receiptDate") Date rmDate);
    
    @Query("SELECT d FROM  TbSrcptFeesDetEntity d WHERE d.bmIdNo=:bmIdNo AND d.rmRcptid in (select am.rmRcptid from TbServiceReceiptMasEntity am WHERE am.additionalRefNo=:addRefNo AND am.rmNarration='Early Payment Discount for  Property Tax' and am.receiptTypeFlag='RB' and receiptDelFlag is null)")
    List<TbSrcptFeesDetEntity> getEarlyRebateByAdditionalRefNo(@Param("bmIdNo") Long bmIdNo,@Param("addRefNo") String propNo);
    
    @Query(value="select count(1) from tb_receipt_mas where dp_deptid=:deptId and RECEIPT_TYPE_FLAG='R' \r\n" + 
    		"and coalesce(RECEIPT_DEL_FLAG,'N')='N' and date(rm_date)=:dateN and \r\n" + 
    		"orgid=:orgId group by orgid;", nativeQuery=true)
   Long countnoOfPropertyPaidToday(@Param("orgId") Long orgId, @Param("deptId") Long  deptId, @Param("dateN")  String dateSet);
    
    
    @Query(value="select PD_USAGETYPE1,sum(cnt) cnt,sum(rd_amount)  from (\r\n" + 
    		"select b.orgid,sum(RF_FEEAMOUNT) rd_amount,(select group_concat(distinct (select cod_desc from \r\n" + 
    		"tb_comparent_det where cod_id=PD_USAGETYPE1))\r\n" + 
    		"from  tb_as_prop_mas a,tb_as_prop_det b where a.PM_PROPID=b.PM_PROPID \r\n" + 
    		"and a.PM_PROP_NO=b.ADDITIONAL_REF_NO) PD_USAGETYPE1,count(distinct a.rm_rcptid) cnt,count(1) \r\n" + 
    		"from tb_receipt_det a,tb_receipt_mas b,tb_tax_mas c,tb_comparent_det d where a.rm_rcptid=b.rm_rcptid and b.dp_deptid=:deptId and\r\n" + 
    		"a.tax_id=c.tax_id and TAX_CATEGORY1=cod_id and cod_desc=:taxCategory and date(rm_date)=:dateN \r\n" + 
    		"and b.orgid=:orgId\r\n" + 
    		"and coalesce(RECEIPT_DEL_FLAG,'N')='N' group by PD_USAGETYPE1,b.orgid) z\r\n" + 
    		"group by PD_USAGETYPE1,orgid", nativeQuery=true)
   List <Object[]>getTaxWiseAmountCollectnProp(@Param("orgId") Long orgId, @Param("deptId") Long  deptId, @Param("taxCategory")String taxCategory,  @Param("dateN")String dateSet);

	@Query("SELECT CASE WHEN COUNT(am) > 0 THEN true ELSE false END FROM TbServiceReceiptMasEntity am WHERE am.manualReceiptNo = :manualReceiptNo"
			+ " AND am.smServiceId = :smServiceId AND am.dpDeptId = :dpDeptid AND am.orgId = :orgId")
	boolean findDuplicateManualReceiptExist(@Param("manualReceiptNo") String manualReceiptNo,
			@Param("smServiceId") Long smServiceId, @Param("dpDeptid") Long dpDeptid, @Param("orgId") Long orgId);
  
}

