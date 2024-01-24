package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.domain.TbComparamMasEntity;
import com.abm.mainet.common.domain.ViewPrefixDetails;
import com.abm.mainet.common.master.dto.TbComparamDet;

/**
 * Repository : TbComparamMas.
 */
@Repository
public interface TbComparamMasJpaRepository extends PagingAndSortingRepository<TbComparamMasEntity, Long> {

    @Query("select tbComparamMasEntity from TbComparamMasEntity tbComparamMasEntity "
            + "where tbComparamMasEntity.cpmPrefix =:cpmPrefix "
            + "order by tbComparamMasEntity.cpmPrefix") // and tbComparamMasEntity.cpmStatus='A'
    TbComparamMasEntity findComparamDetDataByCpmId(@Param("cpmPrefix") String cpmPrefix);

    @Query("select prefixDetails from ViewPrefixDetails prefixDetails "
            + " where prefixDetails.cpmPrefix =:cpmPrefix and prefixDetails.orgid=:orgId")
    List<ViewPrefixDetails> findPrefixData(@Param("cpmPrefix") String cpmPrefix, @Param("orgId") Long orgId);

    @Query("select comparamDetEntity from TbComparamDetEntity comparamDetEntity, TbComparamMasEntity comparamMasEntity "
            + " where comparamDetEntity.tbComparamMas.cpmId =comparamMasEntity.cpmId"
            + " and comparamDetEntity.cpdStatus='A' and comparamMasEntity.cpmStatus='A'"
            + " and (comparamMasEntity.cpmPrefix like '%TX%' or comparamMasEntity.cpmPrefix= :prefixVal) "
            + " and comparamDetEntity.orgid=:orgId order by comparamDetEntity.cpdDesc")
    List<TbComparamDet> findTaxPrefixData(@Param("prefixVal") String prefixVal, @Param("orgId") Long orgId);

    @Query("SELECT comparamDetEntity FROM TbComparamDetEntity comparamDetEntity WHERE comparamDetEntity.tbComparamMas.cpmId="
            + " (SELECT comparamMasEntity.cpmId FROM TbComparamMasEntity comparamMasEntity WHERE comparamMasEntity.cpmPrefix='SCD')"
            + " AND comparamDetEntity.cpdStatus = 'A' AND comparamDetEntity.orgid = :orgId ORDER BY comparamDetEntity.cpdDesc")
    List<TbComparamDetEntity> getDependentSlab(@Param("orgId") Long orgId);

    @Query("select count(tbComparamMasEntity.cpmId) from TbComparamMasEntity tbComparamMasEntity "
            + " where tbComparamMasEntity.cpmPrefix =:cpmPrefix and tbComparamMasEntity.cpmStatus='A'")
    Long validatePrefix(@Param("cpmPrefix") String cpmPrefix);

    @Query("select tbComparamMasEntity from TbComparamMasEntity tbComparamMasEntity "
            + "where tbComparamMasEntity.cpmModuleName =:department "
            + "order by tbComparamMasEntity.cpmPrefix")
    List<TbComparamMasEntity> findAllByDepartment(@Param("department") String department);

    @Query("select tbComparamMasEntity from TbComparamMasEntity tbComparamMasEntity "
            + "where tbComparamMasEntity.cpmModuleName=:department and tbComparamMasEntity.cpmPrefix=:cpmPrefix "
            + "order by tbComparamMasEntity.cpmPrefix")
    List<TbComparamMasEntity> findAllByDepartmentPrefix(@Param("department") String department,
            @Param("cpmPrefix") String cpmPrefix);

    @Query("select tbComparamMasEntity from TbComparamMasEntity tbComparamMasEntity "
            + " where tbComparamMasEntity.cpmPrefix =:prefixVal and tbComparamMasEntity.cpmModuleName=:deptCode and tbComparamMasEntity.cpmStatus='A'")
    TbComparamMasEntity findComparamDataByModule(@Param("prefixVal") String prefixVal, @Param("deptCode") String deptCode);

    @Query("select prefixDetails from ViewPrefixDetails prefixDetails "
            + " where prefixDetails.cpmPrefix =:cpmPrefix and prefixDetails.orgid=:orgId order by prefixDetails.codCpdValue")
    List<ViewPrefixDetails> findMonPrefixData(@Param("cpmPrefix") String cpmPrefix, @Param("orgId") Long orgId);

    @Query("select tbComparamMasEntity from TbComparamMasEntity tbComparamMasEntity "
            + " where tbComparamMasEntity.cpmReplicateFlag =:cpmRepFlag and"
            + " COALESCE(tbComparamMasEntity.cpmType,:cpmType)=:cpmType")
    List<TbComparamMasEntity> findAllByCpmReplicateFlag(@Param("cpmRepFlag") String cpmRepFlag, @Param("cpmType") String cpmType);

    @Query("select tbComparamMasEntity from TbComparamMasEntity tbComparamMasEntity "
            + " where tbComparamMasEntity.cpmReplicateFlag =:cpmRepFlag and tbComparamMasEntity.cpmType=:cpmType")
    List<TbComparamMasEntity> findHierarchicalPrefixes(@Param("cpmRepFlag") String cpmRepFlag, @Param("cpmType") String cpmType);

    @Query("select tbComparamMasEntity from TbComparamMasEntity tbComparamMasEntity "
            + "where tbComparamMasEntity.cpmPrefix =:cpmPrefix "
            + "order by tbComparamMasEntity.cpmPrefix")
    TbComparamMasEntity findComparamDetDataByReplicateFlag(@Param("cpmPrefix") String cpmPrefix);

}
