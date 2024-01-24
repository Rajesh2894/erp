package com.abm.mainet.landEstate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.landEstate.domain.LandAcquisition;

@Repository
public interface LandAcquisitionRepository extends JpaRepository<LandAcquisition, Long> {

    @Query("select laq from LandAcquisition laq where laq.apmApplicationId = :apmApplicationId AND laq.orgId=:orgId")
    LandAcquisition getLAQDataByRefNoAndOrgId(@Param("apmApplicationId") Long apmApplicationId, @Param("orgId") Long orgId);

    @Query("select count(laq) from LandAcquisition laq where laq.lnServno=:lnServno AND laq.lnArea=:lnArea AND laq.locId=:locId AND laq.payTo=:payTo")
    int getLandAcquisitiondata(@Param("lnServno") String lnServno, @Param("lnArea") String lnArea, @Param("locId") Long locId,
            @Param("payTo") String payTo);

    @Query("select laq.apmApplicationId from LandAcquisition laq")
    List<String> fetchProposalNoList();

    @Modifying
    @Query("UPDATE LandAcquisition  lq SET lq.acqStatus =:acqStatus, lq.updatedBy =:updatedBy, lq.lgIpMacUpd =:lgIpMacUpd, lq.updatedDate = CURRENT_TIMESTAMP "
            + "WHERE lq.apmApplicationId =:apmApplicationId ")
    void updateLandProposalAcqStatus(@Param("acqStatus") String acqStatus, @Param("updatedBy") Long updatedBy,
            @Param("lgIpMacUpd") String lgIpMacUpd,
            @Param("apmApplicationId") Long apmApplicationId);

    @Query("select laq from LandAcquisition laq where laq.apmApplicationId = :apmApplicationId")
    LandAcquisition getLAQProposal(@Param("apmApplicationId") Long apmApplicationId);

}
