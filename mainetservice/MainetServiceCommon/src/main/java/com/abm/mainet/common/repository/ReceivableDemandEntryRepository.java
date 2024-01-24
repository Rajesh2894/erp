package com.abm.mainet.common.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.ReceivableDemandEntry;

@Repository
public interface ReceivableDemandEntryRepository extends JpaRepository<ReceivableDemandEntry, Long> {

    @Query("select v from ReceivableDemandEntry v where v.orgid =:orgid and v.billNo =:billNo")
    ReceivableDemandEntry getBillInfoByBillNo(@Param("orgid") Long orgId, @Param("billNo") String billNo);

    @Query(value = "select * from tb_bill_mas as bill where (CM_REFNO IN (select CS_ID from tb_wt_csmr_info as csmr where CS_CCN=:refNo) OR \r\n" +
            "APM_APPLICATION_ID IN (select APM_APPLICATION_ID from tb_cfc_application_mst as ap where REF_NO=:refNo) OR \r\n" +
            "bill.BM_BILLNO=:billNo) and ORGID=:orgid and RECEIPT_ID is null \r\n" +
            "and now() between CREATED_DATE and date_add(CREATED_DATE, INTERVAL :dueDate DAY);", nativeQuery = true)
    List<ReceivableDemandEntry> getBillInfoBybillNoOrRefNo(@Param("orgid") Long orgId, @Param("billNo") String billNo, @Param("refNo") String refNo, @Param("dueDate") int dueDate);

    @Query(value = "select bill.BM_ID,bill.CM_REFNO,csmr.CS_ID,bill.APM_APPLICATION_ID,bill.BM_BILLNO,bill.CREATED_DATE,bill.BM_AMOUNT,rp.RM_RCPTNO,rp.CREATED_DATE \r\n" +
            "from tb_bill_mas bill left join tb_receipt_mas rp on bill.RECEIPT_ID=rp.RM_RCPTID\r\n" +
            "left join tb_wt_csmr_info csmr on bill.CM_REFNO=csmr.CS_ID \r\n" +
            "left join tb_cfc_application_mst ap on bill.APM_APPLICATION_ID=ap.APM_APPLICATION_ID \r\n" +
            "where csmr.CS_CCN=:refNo and bill.ORGID=:orgid\r\n" +
            "union \r\n" +
            "select bill.BM_ID,bill.CM_REFNO,csmr.CS_ID,bill.APM_APPLICATION_ID,bill.BM_BILLNO,bill.CREATED_DATE,bill.BM_AMOUNT,rp.RM_RCPTNO,rp.CREATED_DATE \r\n" +
            "from tb_bill_mas bill left join tb_receipt_mas rp on bill.RECEIPT_ID=rp.RM_RCPTID\r\n" +
            "left join tb_wt_csmr_info csmr on bill.CM_REFNO=csmr.CS_ID \r\n" +
            "left join tb_cfc_application_mst ap on bill.APM_APPLICATION_ID=ap.APM_APPLICATION_ID \r\n" +
            "where ap.REF_NO=:refNo and bill.ORGID=:orgid\r\n" +
            "union \r\n" +
            "select bill.BM_ID,bill.CM_REFNO,csmr.CS_ID,bill.APM_APPLICATION_ID,bill.BM_BILLNO,bill.CREATED_DATE,bill.BM_AMOUNT,rp.RM_RCPTNO,rp.CREATED_DATE \r\n" +
            "from tb_bill_mas bill left join tb_receipt_mas rp on bill.RECEIPT_ID=rp.RM_RCPTID\r\n" +
            "left join tb_wt_csmr_info csmr on bill.CM_REFNO=csmr.CS_ID \r\n" +
            "left join tb_cfc_application_mst ap on bill.APM_APPLICATION_ID=ap.APM_APPLICATION_ID \r\n" +
            "where bill.BM_BILLNO=:billNo and bill.ORGID=:orgid", nativeQuery = true)
    List<Object[]> getListOfBillInfoBybillNoOrRefNo(@Param("orgid") Long orgId, @Param("billNo") String billNo, @Param("refNo") String refNo);    
    
    @Query(value = "select bill.BM_ID,bill.CM_REFNO,csmr.CS_ID,bill.APM_APPLICATION_ID,bill.BM_BILLNO,bill.CREATED_DATE billcreatedDate,bill.BM_AMOUNT,rp.RM_RCPTNO,rp.CREATED_DATE rpCreatedDate\r\n" + 
            "from tb_bill_mas bill left join tb_receipt_mas rp on bill.RECEIPT_ID=rp.RM_RCPTID\r\n" + 
            "left join tb_wt_csmr_info csmr on bill.CM_REFNO=csmr.CS_ID \r\n" + 
            "left join tb_cfc_application_mst ap on bill.APM_APPLICATION_ID=ap.APM_APPLICATION_ID where bill.ORGID=:orgid\r\n" + 
            "and csmr.LOC_ID=:locId OR ap.REF_NO like :wardCode", nativeQuery = true)
    List<Object[]> getWardWiseSupplementryBillList(@Param("orgid") Long orgId, @Param("locId") Long locId, @Param("wardCode") String wardCode);
    
    @Query(value = "select bill.BM_ID,csmr.CS_CCN,ap.REF_NO,bill.BM_BILLNO,bill.CREATED_DATE billcreatedDate,bill.BM_AMOUNT,bill.BM_AMOUNT Total_paidAmount,'S' bill_type\r\n" + 
            "from tb_bill_mas bill left join tb_receipt_mas rp on bill.RECEIPT_ID=rp.RM_RCPTID\r\n" + 
            "left join tb_wt_csmr_info csmr on bill.CM_REFNO=csmr.CS_ID \r\n" + 
            "left join tb_cfc_application_mst ap on bill.APM_APPLICATION_ID=ap.APM_APPLICATION_ID \r\n" + 
            "where bill.ORGID=:orgid and bill.BM_ID=:billId  and bill.RECEIPT_ID=:receiptId ORDER BY 1 DESC;", nativeQuery = true)
    Object[] getSupplimentryBillDetailsByBillandReceiptId(@Param("orgid") Long orgId, @Param("receiptId") Long receiptId, @Param("billId") Long billId);
    
    @Query(value = "select RECEIPT_ID from tb_bill_mas a where a.BM_ID in (:billIds) and RECEIPT_ID is not null", nativeQuery = true)
    List<BigInteger> getReceiptIdsOfPaidSupplementaryBill(@Param(value = "billIds") List<Long> billIds); 
    
    @Modifying
    @Query("UPDATE ReceivableDemandEntry t set t.isAdjust=:AdjustmentFlag, t.isRefund=:refundFlag WHERE t.billId in (:billIds)")
    void updateDepositRefundAdjustmentFlag(@Param(value = "billIds") List<Long> billIds,@Param("refundFlag")String refundFlag,@Param("AdjustmentFlag") String AdjustmentFlag);
    

}
