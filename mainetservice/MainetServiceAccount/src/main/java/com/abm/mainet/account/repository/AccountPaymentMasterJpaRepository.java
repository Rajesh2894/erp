package com.abm.mainet.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountPaymentDetEntity;
import com.abm.mainet.account.domain.AccountPaymentMasterEntity;

/**
 * @author tejas.kotekar
 *
 */
public interface AccountPaymentMasterJpaRepository
        extends PagingAndSortingRepository<AccountPaymentMasterEntity, Long>, AccountPaymentEntryRepositoryCustom {

    /**
     * @param receiptModePaymentId
     * @param tranDate
     * @param orgId Date:22/02/2019 Updated By Ajay Kumar
     * @param userId
     * @param lmoddate
     * @param lgIpMac
     */
    @Modifying
    @Query("UPDATE AccountPaymentMasterEntity pm SET pm.chequeClearanceDate =:tranDate, pm.updatedBy=:userId ,pm.updatedDate=:lmoddate, pm.lgIpMacUpd=:lgIpMac WHERE pm.paymentId =:paymentId and pm.orgId =:orgId")
    public void updateBankReconciliationFormPaymentData(@Param("paymentId") Long receiptModePaymentId,
            @Param("tranDate") Date tranDate, @Param("orgId") Long orgId, @Param("userId") Long userId,
            @Param("lmoddate") Date lmoddate, @Param("lgIpMac") String lgIpMac);

    /**
     * @param id
     * @param orgId
     * @return
     */
    @Query("select m from AccountPaymentMasterEntity m where m.paymentId =:id and m.orgId =:orgId")
    AccountPaymentMasterEntity findPaymentEntryDataById(@Param("id") Long id, @Param("orgId") Long orgId);

    @Query("select m.paymentNo,m.paymentDate,m.paymentAmount,m.paymentId,m.billTypeId.cpdId, m.vmVendorId.vmVendorid from AccountPaymentMasterEntity m where m.paymentId =:paymentId and m.orgId =:orgId")
    List<Object[]> getPaymentDetailsForChequeUtilization(@Param("paymentId") Long paymentId, @Param("orgId") Long orgId);

    @Query("select d from AccountPaymentDetEntity d where d.billId =:billId and d.orgId =:orgId and d.paymentMasterId.paymentDeletionFlag is NULL")
    AccountPaymentDetEntity findBillEntryDetailsByBillId(@Param("billId") Long billId, @Param("orgId") Long orgId);
    
    @Query("select d from AccountPaymentDetEntity d where d.billId =:billId and d.orgId =:orgId and d.paymentMasterId.paymentDeletionFlag is NULL")
    List<AccountPaymentDetEntity> findMultipleBillEntryDetailsByBillId(@Param("billId") Long billId, @Param("orgId") Long orgId);
    

    @Query("select m from AccountPaymentMasterEntity m where m.paymentNo =:valueOf and m.orgId =:orgid and m.paymentDate =:paymentDate")
    public AccountPaymentMasterEntity findPaymentEntryDataByPaymentNumber(@Param("valueOf") String valueOf,
            @Param("orgid") long orgid, @Param("paymentDate") Date paymentDate);

    @Query("select m.chequeClearanceDate from AccountPaymentMasterEntity m,TbAcChequebookleafDetEntity d where d.chequebookDetid =:chequeBookId and d.orgid =:orgId and m.paymentId=d.paymentId and m.orgId=d.orgid and d.cancellationDate IS NULL")
    public Date checkClearanceDateExists(@Param("chequeBookId") Long chequeBookId, @Param("orgId") Long orgId);

    @Query("select pm.paymentDate, pm.paymentNo, pm.paymentAmount, pm.paymentId from AccountPaymentMasterEntity pm where pm.paymentDate between :fromDate and :toDate and pm.orgId =:orgId and pm.paymentTypeFlag=:paymentTypeFlag")
    public Iterable<Object[]> getTdsPaymenDetails(@Param("orgId") Long orgId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate, @Param("paymentTypeFlag") Long paymentTypeFlag);

    @Query("select distinct bm.vendorName,bm.billNo,bm.billEntryDate,pd.paymentAmt FROM TbAcVendormasterEntity vm, AccountBillEntryMasterEnitity bm,AccountBillEntryDeductionDetEntity bd,\r\n"
            +
            "AccountPaymentMasterEntity pm,AccountPaymentDetEntity pd WHERE vm.vmVendorid = bm.vendorId.vmVendorid and bm.id = bd.billMasterId.id and bd.billMasterId.id = pd.billId and pm.paymentId = pd.paymentMasterId.paymentId and bd.sacHeadId = pd.budgetCodeId and bd.orgid = bm.orgId and pm.paymentId =:id and pm.orgId =:orgId and pm.paymentDeletionFlag IS NULL  order by bm.billEntryDate")
    public List<Object[]> getVendorDetailList(@Param("id") Long id, @Param("orgId") Long orgId);

    /**
     * @param id
     * @param orgId
     * @return
     */
    @Query("select m.instrumentNumber from AccountPaymentMasterEntity m where m.paymentId =:id and m.orgId =:orgId")
    Long findInstrumentNumberIdById(@Param("id") Long id, @Param("orgId") Long orgId);

    @Query(value = "select kd.*,md.BM_BILLNO,BM_BAL_AMT from (SELECT pd.BM_ID,(select vm_vendorname from tb_vendormaster where vm_vendorid=FI04_V2) as vendor_name,\r\n"
            +
            "orgid,sum(PAYMENT_AMT)as PAYMENT_AMT \r\n" +
            "FROM tb_ac_payment_DET pd WHERE pd.PAYMENT_ID=:paymentId and pd.ORGID =:orgId group by pd.BM_ID,FI04_V2,orgid) kd,tb_ac_bill_mas md  \r\n"
            +
            "where kd.bm_id=md.bm_id and kd.orgid=md.orgid", nativeQuery = true)
    public List<Object[]> getPaymentDetailsListById(@Param("paymentId") Long paymentId, @Param("orgId") Long orgId);

    @Query(value = "SELECT ID,\r\n" + 
    		"       ORGID,\r\n" + 
    		"       TRN_DATE,\r\n" + 
    		"       NO,\r\n" + 
    		"       BA_ACCOUNTID,\r\n" + 
    		"       CHEQUE_NO,\r\n" + 
    		"       INSTRUMENT_DATE,\r\n" + 
    		"       AMOUNT,\r\n" + 
    		"       CHEQUE_CLEARANCE_DATE,\r\n" + 
    		"       TRANSACTION_MODE,\r\n" + 
    		"       IDNTFY,\r\n" + 
    		"       DPS_SLIPID\r\n" + 
    		"  FROM (SELECT PM.ORGID,\r\n" + 
    		"               PM.BA_ACCOUNTID BA_ACCOUNTID,\r\n" + 
    		"               PM.PAYMENT_ID ID,\r\n" + 
    		"               PM.PAYMENT_DATE TRN_DATE,\r\n" + 
    		"               PM.PAYMENT_NO NO,\r\n" + 
    		"               CD.CHEQUE_NO CHEQUE_NO,\r\n" + 
    		"               PM.INSTRUMENT_DATE INSTRUMENT_DATE,\r\n" + 
    		"               PM.PAYMENT_AMOUNT AMOUNT,\r\n" + 
    		"               PM.CHEQUE_CLEARANCE_DATE CHEQUE_CLEARANCE_DATE,\r\n" + 
    		"               (SELECT CPD_VALUE\r\n" + 
    		"                  FROM TB_COMPARAM_DET x\r\n" + 
    		"                 WHERE x.CPD_ID = PM.CPD_ID_PAYMENT_MODE)\r\n" + 
    		"                  TRANSACTION_MODE,\r\n" + 
    		"               'P' IDNTFY,\r\n" + 
    		"               '0' DPS_SLIPID\r\n" + 
    		"          FROM TB_AC_PAYMENT_MAS PM LEFT JOIN TB_AC_CHEQUEBOOKLEAF_DET CD\r\n" + 
    		"         ON     PM.PAYMENT_ID = CD.PAYMENT_ID\r\n" + 
    		"               where  \r\n" + 
    		"               coalesce(PAYMENT_DEL_FLAG,'N')='N'\r\n" + 
    		"               and BA_ACCOUNTID=:bankAccount\r\n" + 
    		"        UNION\r\n" + 
    		"        SELECT DSM.ORGID,\r\n" + 
    		"               DSM.BA_ACCOUNTID,\r\n" + 
    		"               RMD.RD_MODESID ID,\r\n" + 
    		"               DSM.DPS_SLIPDATE,\r\n" + 
    		"               DSM.DPS_SLIPNO,\r\n" + 
    		"               RMD.RD_CHEQUEDDNO,\r\n" + 
    		"               RMD.RD_CHEQUEDDDATE,\r\n" + 
    		"               RMD.RD_AMOUNT,\r\n" + 
    		"               RMD.RD_SR_CHK_DATE,\r\n" + 
    		"               DSM.DPS_TYPE,\r\n" + 
    		"               'R' IDNTFY,\r\n" + 
    		"               DSM.DPS_SLIPID\r\n" + 
    		"          FROM TB_AC_BANK_DEPOSITSLIP_MASTER DSM\r\n" + 
    		"               JOIN TB_RECEIPT_MODE RMD JOIN TB_RECEIPT_DET RD\r\n" + 
    		"         WHERE     DSM.ORGID = RD.ORGID\r\n" + 
    		"               AND DSM.DPS_TYPE <> 'C'\r\n" + 
    		"               AND DSM.DPS_SLIPID = RD.DPS_SLIPID\r\n" + 
    		"               AND RD.RM_RCPTID = RMD.RM_RCPTID\r\n" + 
    		"               AND RD.ORGID = RMD.ORGID\r\n" + 
    		"               and DSM.BA_ACCOUNTID in (:bankAccount)\r\n" + 
    		"       \r\n" + ") Z\r\n" + 
    		" WHERE  TRN_DATE BETWEEN :fromDte AND :toDte  AND ORGID =:orgId\r\n" + 
    		"        AND BA_ACCOUNTID in (:bankAccount) \r\n" + 
    		"ORDER BY TRN_DATE DESC\r\n" + 
    		"", nativeQuery = true)
    public List<Object[]> getPaymentAndReceiptDetailsList(@Param("bankAccount") Long bankAccount, @Param("fromDte") Date fromDte,
            @Param("toDte") Date toDte, @Param("orgId") Long orgId);

    @Query("select DISTINCT cb.cpdIdstatus FROM  TbAcChequebookleafDetEntity cb WHERE  cb.orgid =:orgId")
    public List<Long> getPaymentStatusId(@Param("orgId") Long orgId);

    @Query(value = "SELECT ID,\r\n" +
            "       ORGID,\r\n" +
            "       TRN_DATE,\r\n" +
            "       NO,\r\n" +
            "       BA_ACCOUNTID,\r\n" +
            "       CHEQUE_NO,\r\n" +
            "       INSTRUMENT_DATE,\r\n" +
            "       AMOUNT,\r\n" +
            "       CHEQUE_CLEARANCE_DATE,\r\n" +
            "       TRANSACTION_MODE,\r\n" +
            "       IDNTFY,\r\n" +
            "       DPS_SLIPID\r\n" +
            "  FROM (SELECT PM.ORGID,\r\n" +
            "               PM.BA_ACCOUNTID BA_ACCOUNTID,\r\n" +
            "               PM.PAYMENT_ID ID,\r\n" +
            "               PM.PAYMENT_DATE TRN_DATE,\r\n" +
            "               PM.PAYMENT_NO NO,\r\n" +
            "               CD.CHEQUE_NO CHEQUE_NO,\r\n" +
            "               PM.INSTRUMENT_DATE INSTRUMENT_DATE,\r\n" +
            "               PM.PAYMENT_AMOUNT AMOUNT,\r\n" +
            "               PM.CHEQUE_CLEARANCE_DATE CHEQUE_CLEARANCE_DATE,\r\n" +
            "               (SELECT CPD_VALUE\r\n" +
            "                  FROM TB_COMPARAM_DET x\r\n" +
            "                 WHERE x.CPD_ID = PM.CPD_ID_PAYMENT_MODE)\r\n" +
            "                  TRANSACTION_MODE,\r\n" +
            "               'P' IDNTFY,\r\n" +
            "               '0' DPS_SLIPID\r\n" +
            "          FROM TB_AC_PAYMENT_MAS PM LEFT JOIN TB_AC_CHEQUEBOOKLEAF_DET CD\r\n" +
            "         ON     PM.PAYMENT_ID = CD.PAYMENT_ID\r\n" +
            "               AND PM.PAYMENT_ID = CD.PAYMENT_ID\r\n" +
            "               AND PM.ORGID = CD.ORGID\r\n" +
            "        UNION\r\n" +
            "        SELECT DSM.ORGID,\r\n" +
            "               DSM.BA_ACCOUNTID,\r\n" +
            "               RMD.RD_MODESID ID,\r\n" +
            "               DSM.DPS_SLIPDATE,\r\n" +
            "               DSM.DPS_SLIPNO,\r\n" +
            "               RMD.RD_CHEQUEDDNO,\r\n" +
            "               RMD.RD_CHEQUEDDDATE,\r\n" +
            "               RMD.RD_AMOUNT,\r\n" +
            "               RMD.RD_SR_CHK_DATE,\r\n" +
            "               DSM.DPS_TYPE,\r\n" +
            "               'R' IDNTFY,\r\n" +
            "               DSM.DPS_SLIPID\r\n" +
            "          FROM TB_AC_BANK_DEPOSITSLIP_MASTER DSM\r\n" +
            "               JOIN TB_RECEIPT_MODE RMD JOIN TB_RECEIPT_DET RD\r\n" +
            "         WHERE     DSM.ORGID = RD.ORGID\r\n" +
            "               AND DSM.DPS_TYPE <> 'C'\r\n" +
            "               AND DSM.DPS_SLIPID = RD.DPS_SLIPID\r\n" +
            "               AND RD.RM_RCPTID = RMD.RM_RCPTID\r\n" +
            "               AND RD.ORGID = RMD.ORGID\r\n" +
            "        UNION\r\n" +
            "        SELECT DSM.ORGID,\r\n" +
            "               DSM.BA_ACCOUNTID,\r\n" +
            "               DSM.DPS_SLIPID,\r\n" +
            "               DSM.DPS_SLIPDATE,\r\n" +
            "               DSM.DPS_SLIPNO,\r\n" +
            "               NULL RD_CHEQUEDDNO,\r\n" +
            "               NULL RD_CHEQUEDDDATE,\r\n" +
            "               DSM.DPS_AMOUNT,\r\n" +
            "               DSM.AUTH_DATE,\r\n" +
            "               DSM.DPS_TYPE,\r\n" +
            "               'R' IDNTFY,\r\n" +
            "               DSM.DPS_SLIPID\r\n" +
            "          FROM TB_AC_BANK_DEPOSITSLIP_MASTER DSM\r\n" +
            "         WHERE DSM.DPS_TYPE = 'C') Z\r\n" +
            " WHERE ORGID =:orgId\r\n" +
            "ORDER BY TRN_DATE", nativeQuery = true)
    public List<Object[]> getDefaultSummaryData(@Param("orgId") Long orgId);

    @Query(value = "SELECT ID,\r\n" + 
    		"       ORGID,\r\n" + 
    		"       TRN_DATE,\r\n" + 
    		"       NO,\r\n" + 
    		"       BA_ACCOUNTID,\r\n" + 
    		"       CHEQUE_NO,\r\n" + 
    		"       INSTRUMENT_DATE,\r\n" + 
    		"       AMOUNT,\r\n" + 
    		"       CHEQUE_CLEARANCE_DATE,\r\n" + 
    		"       TRANSACTION_MODE,\r\n" + 
    		"       IDNTFY,\r\n" + 
    		"       DPS_SLIPID\r\n" + 
    		"  FROM (SELECT PM.ORGID,\r\n" + 
    		"               PM.BA_ACCOUNTID BA_ACCOUNTID,\r\n" + 
    		"               PM.PAYMENT_ID ID,\r\n" + 
    		"               PM.PAYMENT_DATE TRN_DATE,\r\n" + 
    		"               PM.PAYMENT_NO NO,\r\n" + 
    		"               CD.CHEQUE_NO CHEQUE_NO,\r\n" + 
    		"               PM.INSTRUMENT_DATE INSTRUMENT_DATE,\r\n" + 
    		"               PM.PAYMENT_AMOUNT AMOUNT,\r\n" + 
    		"               PM.CHEQUE_CLEARANCE_DATE CHEQUE_CLEARANCE_DATE,\r\n" + 
    		"               (SELECT CPD_VALUE\r\n" + 
    		"                  FROM TB_COMPARAM_DET x\r\n" + 
    		"                 WHERE x.CPD_ID = PM.CPD_ID_PAYMENT_MODE)\r\n" + 
    		"                  TRANSACTION_MODE,\r\n" + 
    		"               'P' IDNTFY,\r\n" + 
    		"               '0' DPS_SLIPID\r\n" + 
    		"          FROM TB_AC_PAYMENT_MAS PM LEFT JOIN TB_AC_CHEQUEBOOKLEAF_DET CD\r\n" + 
    		"         \r\n" + 
    		"         on     PM.PAYMENT_ID = CD.PAYMENT_ID\r\n" + 
    		"               AND PM.PAYMENT_ID = CD.PAYMENT_ID\r\n" + 
    		"               AND PM.ORGID = CD.ORGID\r\n" + 
    		"                where PM.CHEQUE_CLEARANCE_DATE is not null\r\n" + 
    		"               \r\n" + 
    		"        UNION\r\n" + 
    		"        SELECT DSM.ORGID,\r\n" + 
    		"               DSM.BA_ACCOUNTID,\r\n" + 
    		"               RMD.RD_MODESID ID,\r\n" + 
    		"               DSM.DPS_SLIPDATE,\r\n" + 
    		"               DSM.DPS_SLIPNO,\r\n" + 
    		"               RMD.RD_CHEQUEDDNO,\r\n" + 
    		"               RMD.RD_CHEQUEDDDATE,\r\n" + 
    		"               RMD.RD_AMOUNT,\r\n" + 
    		"               RMD.RD_SR_CHK_DATE,\r\n" + 
    		"               DSM.DPS_TYPE,\r\n" + 
    		"               'R' IDNTFY,\r\n" + 
    		"               DSM.DPS_SLIPID\r\n" + 
    		"          FROM TB_AC_BANK_DEPOSITSLIP_MASTER DSM\r\n" + 
    		"               JOIN TB_RECEIPT_MODE RMD JOIN TB_RECEIPT_DET RD\r\n" + 
    		"         WHERE     DSM.ORGID = RD.ORGID\r\n" + 
    		"               AND DSM.DPS_TYPE <> 'C'\r\n" + 
    		"               AND DSM.DPS_SLIPID = RD.DPS_SLIPID\r\n" + 
    		"               AND RD.RM_RCPTID = RMD.RM_RCPTID\r\n" + 
    		"               AND RD.ORGID = RMD.ORGID\r\n" + 
    		"               AND RMD.RD_SR_CHK_DATE is not null) Z\r\n" + 
    		" WHERE     TRN_DATE BETWEEN :fromDte AND :toDte \r\n" + 
    		"       AND ORGID =:orgId\r\n" + 
    		"       AND BA_ACCOUNTID =:bankAccountId\r\n" + 
    		"ORDER BY TRN_DATE;", nativeQuery = true)
    public List<Object[]> findByAllSummarySearchData(@Param("bankAccountId") Long bankAccountId, @Param("fromDte") Date fromDte,
            @Param("toDte") Date toDte, @Param("orgId") Long orgId);
			
			 @Query("select count(d) from AccountPaymentDetEntity d where d.billId =:billId and d.orgId =:orgId and d.paymentMasterId.paymentDeletionFlag is NULL")
    Long checkPaymentExistsByBillId(@Param("billId") Long billId, @Param("orgId") Long orgId);
			 @Query(value = "SELECT ID,\r\n" + 
			 		"       TRN_DATE,\r\n" + 
			 		"       NO,\r\n" + 
			 		"       CHEQUE_NO,\r\n" + 
			 		"       INSTRUMENT_DATE,\r\n" + 
			 		"       AMOUNT,\r\n" + 
			 		"       PAYMENT_DEL_FLAG,\r\n" + 
			 		"       CHEQUE_CLEARANCE_DATE,\r\n" + 
			 		"       CPD_IDSTATUS,\r\n" + 
			 		"       TRANSACTION_MODE\r\n" + 
			 		"  FROM (SELECT PM.ORGID,\r\n" + 
			 		"               PM.BA_ACCOUNTID BA_ACCOUNTID,\r\n" + 
			 		"               PM.PAYMENT_ID ID,\r\n" + 
			 		"               PM.PAYMENT_DATE TRN_DATE,\r\n" + 
			 		"               PM.PAYMENT_NO NO,\r\n" + 
			 		"               CD.CHEQUE_NO CHEQUE_NO,\r\n" + 
			 		"               PM.INSTRUMENT_DATE INSTRUMENT_DATE,\r\n" + 
			 		"               PM.PAYMENT_AMOUNT AMOUNT,\r\n" + 
			 		"               PM.PAYMENT_DEL_FLAG PAYMENT_DEL_FLAG,\r\n" + 
			 		"               PM.CHEQUE_CLEARANCE_DATE CHEQUE_CLEARANCE_DATE,\r\n" + 
			 		"               CD.CPD_IDSTATUS CPD_IDSTATUS,\r\n" + 
			 		"               (SELECT CPD_VALUE\r\n" + 
			 		"                  FROM TB_COMPARAM_DET x\r\n" + 
			 		"                 WHERE x.CPD_ID = PM.CPD_ID_PAYMENT_MODE)\r\n" + 
			 		"                  TRANSACTION_MODE\r\n" + 
			 		"          FROM TB_AC_PAYMENT_MAS PM LEFT JOIN TB_AC_CHEQUEBOOKLEAF_DET CD\r\n" + 
			 		"         ON     PM.PAYMENT_ID = CD.PAYMENT_ID and CD.CPD_IDSTATUS=:categoryId\r\n" + 
			 		"               where  \r\n" + 
			 		"               coalesce(PAYMENT_DEL_FLAG,'N')='N'\r\n" + 
			 		"               and BA_ACCOUNTID=:bankAccountId \r\n" + 
			 		"       ) Z\r\n" + 
			 		" WHERE  date(TRN_DATE) BETWEEN :fromDte AND :toDte  AND ORGID =:orgId\r\n" + 
			 		"        AND BA_ACCOUNTID in (:bankAccountId) \r\n" + 
			 		"ORDER BY TRN_DATE DESC;\r\n" + 
			 		"" + 
			 		""
			    		, nativeQuery = true)
			public List<Object[]> findByAllGridPaymentEntrySearchData(@Param("bankAccountId")Long bankAccount, @Param("fromDte") Date fromDte,
					  @Param("toDte")	Date toDte, @Param("orgId")  Long orgId,  @Param("categoryId") Long categoryId);

}
