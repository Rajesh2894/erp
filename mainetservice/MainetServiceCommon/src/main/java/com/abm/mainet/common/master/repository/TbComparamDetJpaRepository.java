package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.TbComparamDetEntity;

/**
 * Repository : TbComparamDet.
 */
@Repository
public interface TbComparamDetJpaRepository extends PagingAndSortingRepository<TbComparamDetEntity, Long> {

    @Query("select tbComparamDetEntity from TbComparamMasEntity tbComparamMasEntity, TbComparamDetEntity tbComparamDetEntity "
            + " where tbComparamMasEntity.cpmStatus=tbComparamDetEntity.cpdStatus "
            + " and tbComparamMasEntity.cpmPrefix='DCG' and tbComparamMasEntity.cpmId=tbComparamDetEntity.tbComparamMas.cpmId"
            + " and tbComparamDetEntity.orgid=:orgId")
    List<TbComparamDetEntity> findAllComparamDetData(@Param("orgId") Long orgId);

    @Query("select tbComparamDetEntity from TbComparamDetEntity tbComparamDetEntity "
            + " where tbComparamDetEntity.tbComparamMas.cpmId =:cpmId and tbComparamDetEntity.orgid=:orgId")
    List<TbComparamDetEntity> findComparamDetDataByCpmId(@Param("cpmId") Long cpmId, @Param("orgId") Long orgId);

    @Query("select tbComparamDetEntity from TbComparamDetEntity tbComparamDetEntity "
            + " where tbComparamDetEntity.tbComparamMas.cpmId =:cpmId and tbComparamDetEntity.cpdValue = 'A' and tbComparamDetEntity.orgid=:orgId")
    TbComparamDetEntity findComparamDetByCpmId(@Param("cpmId") Long cpmId, @Param("orgId") Long orgId);

    /**
     * @param orgid
     * @return
     */
    @Query("select det.cpdId,det.cpdDesc from TbComparamDetEntity det where det.tbComparamMas.cpmId IN (select mas.cpmId from TbComparamMasEntity mas "
            + " where mas.cpmPrefix IN ('JV', 'RV', 'PVA', 'CV', 'PAY')"
            + "  and det.cpdValue IN ('COC','CON','PBLT','WBC','WNA','APP','SPD'))"
            + "and det.orgid = :orgId")
    List<Object[]> getPrefixDataForPermenent(@Param("orgId") Long orgId);

    /**
     * @param orgid
     * @return
     */
    @Query("SELECT t from TbComparamDetEntity t where t.tbComparamMas.cpmId IN "
            + " (select m.cpmId from TbComparamMasEntity m"
            + " where m.cpmPrefix IN ('JV', 'RV', 'PVA', 'CV', 'PAY')"
            + " and t.cpdValue in ( 'PB','WB')) and t.orgid = :orgid ")
    List<TbComparamDetEntity> getPrefixDataForFinancialWise(@Param("orgid") Long orgid);

    @Query("select detEntity.cpdId,detEntity.cpdDesc from TbComparamDetEntity detEntity"
            + " where detEntity.tbComparamMas.cpmId IN (select masEntity.cpmId from TbComparamMasEntity masEntity"
            + " where masEntity.cpmPrefix IN ('PAY')) and detEntity.orgid = :orgId ")
    List<Object[]> getDataForPermenentPay(@Param("orgId") Long orgId);

    @Query("select tbComparamDetEntity from TbComparamDetEntity tbComparamDetEntity "
            + " where tbComparamDetEntity.tbComparamMas.cpmId =:cpmId and tbComparamDetEntity.orgid IN (select orgid from Organisation org where org.defaultStatus='Y')")
    List<TbComparamDetEntity> findCmprmDetByCpmId(@Param("cpmId") Long cpmId);
}
