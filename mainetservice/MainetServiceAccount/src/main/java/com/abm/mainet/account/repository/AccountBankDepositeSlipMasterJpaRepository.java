
package com.abm.mainet.account.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.account.domain.AccountBankDepositeSlipMasterEntity;

/**
 * @author tejas.kotekar
 *
 */
public interface AccountBankDepositeSlipMasterJpaRepository
        extends PagingAndSortingRepository<AccountBankDepositeSlipMasterEntity, Long> {

    @Query("select distinct e.depositeBAAccountId from AccountBankDepositeSlipMasterEntity e  where e.orgid=:orgid order by 1 desc")
    List<Object[]> getBankAccountData(@Param("orgid") Long orgId);

    @Query("select distinct e.cbBankid from TbSrcptModesDetEntity e  where e.orgId=:orgId order by 1 desc")
    List<Object[]> getChequeDDNoBankAccountData(@Param("orgId") Long orgId);

    @Query("select de.rmRcptid.vmVendorId from AccountBankDepositeSlipMasterEntity bm ,TbSrcptFeesDetEntity de where bm.depositeSlipId=:depositeSlipId and bm.orgid=:orgId and bm.depositeSlipId=de.depositeSlipId and bm.orgid=de.orgId")
    Long getVendorId(@Param("depositeSlipId") Long depositeSlipId, @Param("orgId") Long orgId);

    @Query("SELECT dsm "
            + "FROM AccountBankDepositeSlipMasterEntity dsm "
            + "WHERE dsm.depositeSlipId=:depositeSlipId "
            + "AND dsm.orgid=:orgId ")
    AccountBankDepositeSlipMasterEntity findAllByDepositSlipId(@Param("depositeSlipId") long depositeSlipId,
            @Param("orgId") long orgId);

    // @Query("select rd.rmRcptid from TbSrcptFeesDetEntity rd where rd.depositeSlipId=:depositeSlipId and rd.orgId=:orgId")
    @Query(value = "SELECT DISTINCT (RMD.RD_SR_CHK_DATE)\r\n" +
            "  FROM TB_RECEIPT_MODE               RMD,\r\n" +
            "       TB_RECEIPT_DET                RD,\r\n" +
            "       TB_RECEIPT_MAS                RM,\r\n" +
            "       TB_AC_BANK_DEPOSITSLIP_MASTER DM\r\n" +
            " WHERE RD.RM_RCPTID = RM.RM_RCPTID\r\n" +
            "   AND RD.RM_RCPTID = RMD.RM_RCPTID\r\n" +
            "   AND RM.RM_RCPTID = RMD.RM_RCPTID\r\n" +
            "   AND DM.DPS_SLIPID = RD.DPS_SLIPID\r\n" +
            "   AND DM.ORGID = RD.ORGID\r\n" +
            "   AND DM.DPS_SLIPID = :depositeSlipId\r\n" +
            "   AND DM.ORGID = :orgId\r\n", nativeQuery = true)
    List<Object[]> findReceiptEntryDetailsByDepositSlipId(@Param("depositeSlipId") Long depositeSlipId,
            @Param("orgId") Long orgId);

    @Query("select ds from AccountBankDepositeSlipMasterEntity ds where ds.depositeSlipId=:depositeSlipId and ds.orgid=:orgId")
    List<AccountBankDepositeSlipMasterEntity> getDenominationCashDetails(@Param("depositeSlipId") Long depositeSlipId,
            @Param("orgId") Long orgid);

    @Query(value = "select sum(RM_AMOUNT)  amt from tb_receipt_mode d,tb_comparam_det e,tb_receipt_mas a\r\n" +
            "where cpd_feemode=cpd_id and cpd_value='C' and a.RM_RCPTID = d.RM_RCPTID and a.RM_DATE =:receiptDate and a.orgid=:orgid and RECEIPT_DEL_FLAG IS NULL\r\n"
            +
            "group by RM_DATE", nativeQuery = true)
    BigDecimal getSumOfReceiptAmountExist(@Param("receiptDate") Date receiptDate, @Param("orgid") long orgid);

    @Query(value = "select sum(dps_amount) from tb_ac_bank_depositslip_master where DPS_TYPE='C' and DPS_DEPOSIT_DATE=:depositDate and orgid=:orgid and DPS_DEL_FLAG IS NULL\r\n"
            +
            "group by dps_fromdate", nativeQuery = true)
    BigDecimal getSumOfDepositAmountExist(@Param("depositDate") Date depositDate, @Param("orgid") long orgid);

}
