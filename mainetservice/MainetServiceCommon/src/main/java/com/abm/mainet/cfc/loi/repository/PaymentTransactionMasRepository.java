package com.abm.mainet.cfc.loi.repository;

import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTransactionMasRepository extends JpaRepository<PaymentTransactionMas, Long> {

	@Modifying(clearAutomatically = true)
	@Query("update PaymentTransactionMas a set a.recvStatus=:status, a.updatedDate = CURRENT_DATE  where  a.recvBankRefNum =:recvBankRefNum and a.orgId=:orgId ")
	void updateTransactionByBankRefId(@Param("recvBankRefNum") String recvBankRefNum, @Param("status") String status,
			@Param("orgId") Long orgId);
	
	@Query("SELECT a.recvStatus FROM PaymentTransactionMas a WHERE a.recvBankRefNum =:recvBankRefNum ")
    String fetchPaymentStatus(@Param("recvBankRefNum") String recvBankRefNum);
	
	@Query("SELECT a FROM PaymentTransactionMas a WHERE a.recvMode =:recvMode and a.recvStatus <> 'RECONCILE' and  a.recvMihpayid is not null and orgId=:orgId")
	  List<PaymentTransactionMas> getAlloffLineChallanDataForReconcile(@Param("recvMode") String recvMode,@Param("orgId") Long orgId);

}
