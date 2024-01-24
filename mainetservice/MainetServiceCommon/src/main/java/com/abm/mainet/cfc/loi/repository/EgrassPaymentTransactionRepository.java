package com.abm.mainet.cfc.loi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.integration.payment.entity.EgrassPaymentENtity;

@Repository
public interface EgrassPaymentTransactionRepository extends JpaRepository<EgrassPaymentENtity, Long> {

	@Query("SELECT a FROM EgrassPaymentENtity a WHERE a.referenceId =:referenceId and a.orgId=:orgId order by 1 desc  ")
	List<EgrassPaymentENtity> fetchPaymentStatus(@Param("referenceId") String referenceId, @Param("orgId") Long orgId);

	@Query(value = "SELECT case_no,APPLICATION_NO_E_SERVICE FROM TB_BPMS_LIC_MSTR   WHERE APPLICATION_NO=:referenceId and ORGID=:orgId", nativeQuery = true)
	List<Object[]> getInformationFromTbBPMSLICMaster(@Param("referenceId") String recvMode, @Param("orgId") Long orgId);

}
