package com.abm.mainet.adh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.ADHBillMasEntity;

/**
 * @author cherupelli.srikanth
 * @since 11 November 2019
 */
@Repository
public interface ADHBillMasRepository extends JpaRepository<ADHBillMasEntity, Long> {

    @Query("select case when count(adh)>0 THEN true ELSE false END from ADHBillMasEntity adh where  adh.contractId =:contractId AND adh.paidFlag='Y' AND adh.orgId=:orgId")
    Boolean checkInstallmentPaidOrNot(@Param("contractId") Long conitId, @Param("orgId") Long orgId);

    List<ADHBillMasEntity> findByContractIdAndOrgIdAndPaidFlagAndBillTypeOrderByBillMasNoAsc(Long contractId,
	    Long orgId, String paidFlag, String type);
}
