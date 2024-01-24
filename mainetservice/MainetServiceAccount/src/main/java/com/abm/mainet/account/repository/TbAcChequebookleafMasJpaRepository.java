package com.abm.mainet.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;
import com.abm.mainet.account.domain.TbAcChequebookleafMasEntity;

/**
 * Repository : TbAcChequebookleafMas.
 */
@Repository
public interface TbAcChequebookleafMasJpaRepository extends PagingAndSortingRepository<TbAcChequebookleafMasEntity, Long> {

    @Query("select e from TbAcChequebookleafMasEntity e  where e.orgid=:orgid and e.baAccountid=:bmBankid")
    List<TbAcChequebookleafMasEntity> getChequeData(@Param("bmBankid") Long bmBankid, @Param("orgid") Long orgid);

    /**
     * CAST function is used here to read string value as a number because 'between' criteria works on numbers
     *
     * @param bmBankid
     * @param baAccountid
     * @param orgid
     * @param fromChequeNo
     * @param toChequeNo
     * @return
     */
    @Query("select count(a.baAccountid) from TbAcChequebookleafMasEntity a where a.baAccountid =:bmBankid and a.orgid =:orgId and a.fromChequeNo=:fromChequeNo and a.toChequeNo=:toChequeNo")
    Long getChequeBookCount(@Param("bmBankid") Long bmBankid, @Param("orgId") Long orgid,
            @Param("fromChequeNo") String fromChequeNo, @Param("toChequeNo") String toChequeNo);

    /**
     * @param receiptModePaymentId
     * @param statusFlag
     * @param orgId
     * @param cancelledStatusFlag
     * @param userId
     * @param lmoddate
     * @param lgIpMac 22/02/2019 updated By Ajay Kumar
     * @param userId, @param lmoddate, @param lgIpMac here updated is not there in previous query.
     */
    @Modifying
    @Query("UPDATE TbAcChequebookleafDetEntity cb SET cb.cpdIdstatus =:statusFlag, cb.updatedBy=:userId,cb.updatedDate=:lmoddate, cb.lgIpMacUpd=:lgIpMac  WHERE cb.paymentId =:paymentId and cb.orgid =:orgId and cb.cpdIdstatus <> :cancelledStatusFlag")
    public void updateBankReconciliationFormPaymentCheckBookData(@Param("paymentId") Long receiptModePaymentId,
            @Param("statusFlag") Long statusFlag, @Param("orgId") Long orgId,
            @Param("cancelledStatusFlag") Long cancelledStatusFlag, @Param("userId") Long userId,
            @Param("lmoddate") Date lmoddate, @Param("lgIpMac") String lgIpMac);

    /**
     * @param chequeBookDetId
     * @param orgId
     */
    @Modifying
    @Query("update TbAcChequebookleafDetEntity det set det.cpdIdstatus =:cpdIdStatus, det.paymentType =:paymentType, det.paymentId =:paymentId where det.chequebookDetid =:chequeBookDetId and det.orgid =:orgId")
    void updateChequeNumberStatus(@Param("cpdIdStatus") Long cpdIdStatus, @Param("chequeBookDetId") Long chequeBookDetId,
            @Param("orgId") Long orgId, @Param("paymentType") String paymentType, @Param("paymentId") Long paymentId);

    @Modifying
    @Query("update TbAcChequebookleafDetEntity det set det.cpdIdstatus =:cpdIdStatus, det.paymentId =:paymentId, det.issuanceDate =:issuanceDate,det.paymentType =:paymentType "
            + "where det.chequebookDetid =:chequeBookDetId and det.orgid =:orgId")
    void updateChequeDetailsForPayments(@Param("cpdIdStatus") Long cpdIdStatus, @Param("chequeBookDetId") Long chequeBookDetId,
            @Param("orgId") Long orgId,
            @Param("paymentId") Long paymentId, @Param("issuanceDate") Date issuanceDate,
            @Param("paymentType") String paymentType);

    @Modifying
    @Query("update TbAcChequebookleafDetEntity det set det.cancellationDate =:cancellationDate, det.cpdIdstatus =:cancellationStatus, "
            + "det.cancellationReason =:cancellationReason, det.newIssueCheqeuBookDetId =:newChequeDetId where det.chequebookDetid =:chequeBookDetId and det.orgid =:orgId")
    void updateChequeForCancellation(@Param("chequeBookDetId") Long chequeBookDetId,
            @Param("cancellationDate") Date cancellationDate, @Param("cancellationStatus") Long cancellationStatus,
            @Param("cancellationReason") String cancellationReason, @Param("newChequeDetId") Long newChequeDetId,
            @Param("orgId") Long orgId);

    /**
     * @param bankId
     * @param bankAcId
     * @param statusId
     * @param orgId
     * @return
     */
    @Query("select d from TbAcChequebookleafMasEntity m,TbAcChequebookleafDetEntity d where m.chequebookId = d.tbAcChequebookleafMas.chequebookId and m.orgid = d.orgid and m.baAccountid =:bankAcId and d.cpdIdstatus =:statusId")
    List<TbAcChequebookleafDetEntity> getChequeNumbers(@Param("bankAcId") Long bankAcId, @Param("statusId") Long statusId);

    @Query("select m.chequebookId,m.fromChequeNo,m.toChequeNo from TbAcChequebookleafMasEntity m where m.baAccountid =:bankAccountId and m.orgid =:orgId")
    List<Object[]> getChequeRangeByBankAccountId(@Param("bankAccountId") Long bankAccountId, @Param("orgId") Long orgId);

    /**
     * @param chequeBookId
     * @param orgId
     * @return
     */
    @Query("select d.chequeNo,d.cpdIdstatus,d.paymentId,d.paymentType,d.chequebookDetid from TbAcChequebookleafDetEntity d where d.tbAcChequebookleafMas.chequebookId =:chequeBookId and d.orgid=:orgId")
    List<Object[]> getChequeUtilizationDetails(@Param("chequeBookId") Long chequeBookId, @Param("orgId") Long orgId);

    @Query("select d.chequeNo from TbAcChequebookleafDetEntity d where d.chequebookDetid =:chequebookDetid ")
    String getCheque(@Param("chequebookDetid") Long chequebookDetid);

    /**
     * @param bankId
     * @param bankAcId
     * @param statusId
     * @param orgId
     * @return
     */
    @Query("select d from TbAcChequebookleafMasEntity m,TbAcChequebookleafDetEntity d where m.chequebookId = d.tbAcChequebookleafMas.chequebookId and m.orgid = d.orgid and m.baAccountid =:bankAcId")
    List<TbAcChequebookleafDetEntity> getChequeNumbers(@Param("bankAcId") Long bankAcId);

    @Query("select d.chequeNo from TbAcChequebookleafDetEntity d where d.chequebookDetid =:chequebookDetid ")
    String getChequeNoById(@Param("chequebookDetid") Long chequebookDetid);

    @Query("select d.paymentId from TbAcChequebookleafDetEntity d  where d.chequebookDetid=:chequebookdetid")
    long getpaymentId(@Param("chequebookdetid") Long chequebookdetid);

    @Query("select d.paymentId,d.paymentType from TbAcChequebookleafDetEntity d  where d.chequebookDetid=:chequebookdetid and d.orgid =:orgId")
    List<Object[]> getpaymentIdAndTypeByChequeBookDetId(@Param("chequebookdetid") Long chequebookdetid,
            @Param("orgId") Long orgId);

    @Query("select d.issuanceDate from TbAcChequebookleafDetEntity d  where d.chequebookDetid=:issuedChequeNo and d.orgid =:orgId")
    Date getDateByInstrumentNo(@Param("issuedChequeNo") Long issuedChequeNo,
            @Param("orgId") Long orgId);

    @Query("select d.chequeNo from TbAcChequebookleafDetEntity d where d.cpdIdstatus=:cpdIdstatus and d.chequebookDetid =:chequebookDetid ")
    String getInstrumentChequeNo(@Param("cpdIdstatus") Long cpdIdstatus, @Param("chequebookDetid") Long chequebookDetid);

    @Modifying
    @Query("update TbAcChequebookleafDetEntity det set det.cpdIdstatus =:cpdIdStatus "
            + "where det.chequebookDetid =:chequeBookDetId and det.orgid =:orgId")
    void updateChequeDetailsForPaymentsReversal(@Param("cpdIdStatus") Long cpdIdStatus,
            @Param("chequeBookDetId") Long chequeBookDetId,
            @Param("orgId") Long orgId);
    
    @Modifying
    @Query("update AccountPaymentMasterEntity pm set pm.instrumentNumber=:newInstrumentNumber where pm.paymentId=:paymentId and pm.orgId=:orgId")
    void updateInstrumentnumberByNewInstrumentNumber(@Param("orgId") Long orgId,  @Param("newInstrumentNumber") Long newInstrumentNumber,@Param("paymentId") Long paymentId);

}
