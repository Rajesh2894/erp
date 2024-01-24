package com.abm.mainet.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;
/**
 * 
 * @author vishwanath.s
 *
 */

@Repository
public interface AccountChequeIssueanceRepository extends CrudRepository<TbAcChequebookleafDetEntity, Long> {

	@Query("select pm.paymentId ,pm.paymentDate ,pm.paymentNo,pm.paymentAmount,cd.chequeNo,cd.issuanceDate,pm.instrumentDate,pm.chequeClearanceDate ,(SELECT cpdValue FROM TbComparamDetEntity x WHERE x.cpdId = pm.paymentModeId.cpdId), cd.paymentType,cd.chequebookDetid, cd.stoppayDate,cd.stopPayRemark from AccountPaymentMasterEntity pm,TbAcChequebookleafDetEntity cd where "
			+ "pm.orgId=cd.orgid and pm.paymentId=cd.paymentId and pm.paymentDeletionFlag is null and pm.baBankAccountId.baAccountId=:BaAccountId "
			+ "and cd.cpdIdstatus=:chequeStatus and pm.orgId=:orgId and pm.paymentDate between :fromDate and :toDate")
	List<Object[]> getAllNotIssuedPaymentCheque(@Param("orgId")Long orgId,@Param("chequeStatus")Long chequeStatus,@Param("BaAccountId")Long BaAccountId,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate);	

	@Query("select pm.paymentId ,pm.paymentDate ,pm.paymentNo,pm.paymentAmount,cd.chequeNo,cd.issuanceDate,pm.instrumentDate,pm.chequeClearanceDate ,(SELECT cpdValue FROM TbComparamDetEntity x WHERE x.cpdId = pm.paymentModeId.cpdId), cd.paymentType,cd.chequebookDetid, cd.stoppayDate,cd.stopPayRemark from AccountPaymentMasterEntity pm,TbAcChequebookleafDetEntity cd where "
			+ "pm.orgId=cd.orgid and pm.paymentId=cd.paymentId and pm.paymentDeletionFlag is null "
			+ "and cd.cpdIdstatus=:chequeStatus and pm.orgId=:orgId and pm.paymentDate between :fromDate and :toDate")
	List<Object[]> getAllNotIssuedPaymentChequewithoutBankId(@Param("orgId")Long orgId,@Param("chequeStatus")Long chequeStatus,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate);
	
	
	@Modifying
    @Query("update TbAcChequebookleafDetEntity det set det.cpdIdstatus =:cpdIdStatus, det.issuanceDate =:issuanceDate"
            + " where det.chequebookDetid =:chequeBookDetId and det.orgid =:orgId")
    void updateChequeDetail(@Param("cpdIdStatus") Long cpdIdStatus, @Param("chequeBookDetId") Long chequeBookDetId,@Param("orgId") Long orgId,
            @Param("issuanceDate") Date issuanceDate);
	
	@Query("select pm.paymentId ,pm.paymentDate ,pm.paymentNo,pm.paymentAmount,cd.chequeNo,cd.issuanceDate,pm.instrumentDate,pm.chequeClearanceDate ,(SELECT cpdValue FROM TbComparamDetEntity x WHERE x.cpdId = pm.paymentModeId.cpdId), cd.paymentType,cd.chequebookDetid from AccountPaymentMasterEntity pm,TbAcChequebookleafDetEntity cd where "
			+ "pm.orgId=cd.orgid and pm.paymentId=cd.paymentId and pm.paymentDeletionFlag is null and pm.baBankAccountId.baAccountId=:BaAccountId "
			+ "and cd.cpdIdstatus in (:chequeIssued,:chequeRedayForIssue) and pm.orgId=:orgId and pm.paymentDate between :fromDate and :toDate")
	List<Object[]> getIssuedAndReadyForIssueChequeForStopPayment(@Param("orgId")Long orgId,@Param("chequeIssued")Long chequeIssued,@Param("chequeRedayForIssue")Long chequeRedayForIssue,@Param("BaAccountId")Long BaAccountId,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate);	

	
	@Modifying
    @Query("update TbAcChequebookleafDetEntity det set det.cpdIdstatus =:cpdIdStatus, det.stoppayDate =:stoppayDate ,det.stopPayRemark=:stoppaymentRemark "
            + " where det.chequebookDetid =:chequeBookDetId and det.orgid =:orgId")
    void updateChequeDetailforStopPayment(@Param("cpdIdStatus") Long cpdIdStatus, @Param("chequeBookDetId") Long chequeBookDetId,@Param("orgId") Long orgId,
            @Param("stoppayDate") Date stoppayDate,@Param("stoppaymentRemark") String stoppaymentRemark);
	
	
}
