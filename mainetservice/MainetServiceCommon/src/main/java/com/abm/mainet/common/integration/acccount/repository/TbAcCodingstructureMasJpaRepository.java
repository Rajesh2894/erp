package com.abm.mainet.common.integration.acccount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureMasEntity;

/**
 * Repository : TbAcCodingstructureMas.
 */
public interface TbAcCodingstructureMasJpaRepository extends PagingAndSortingRepository<TbAcCodingstructureMasEntity, Long> {

    @Query("select am from TbAcCodingstructureMasEntity am join FETCH am.tbAcCodingstructureDetEntity det WHERE am.orgid=:orgid and am.comCpdId=:comparamDet and am.comAppflag=:activeStatusCode")
    TbAcCodingstructureMasEntity findConfigurationMasterEntiry(@Param("comparamDet") Long comparamDet, @Param("orgid") Long orgId,
            @Param("activeStatusCode") String activeStatusCode);

    @Query("select fundMas from TbAcCodingstructureMasEntity configMas,TbAcCodingstructureDetEntity configDet,AccountFundMasterEntity fundMas where configMas.codcofId=configDet.tbAcCodingstructureMasEntity and configDet.codcofdetId=fundMas.tbAcCodingstructureDet.codcofdetId and configMas.codNoLevel=configDet.codLevel and configMas.comCpdId=:cpdFundId")
    List<AccountFundMasterEntity> getFundMasterForMapping(@Param("cpdFundId") Long cpdFundId);

    @Query("select fundMas from TbAcCodingstructureMasEntity configMas,TbAcCodingstructureDetEntity configDet,AccountFundMasterEntity fundMas where configMas.codcofId=configDet.tbAcCodingstructureMasEntity and configDet.codcofdetId=fundMas.tbAcCodingstructureDet.codcofdetId and configMas.codNoLevel=configDet.codLevel and configMas.comCpdId=:cpdFundId and fundMas.fundStatusCpdId=:fundStatusCpdId")
    List<AccountFundMasterEntity> getActiveFundMasterForMapping(@Param("cpdFundId") Long cpdFundId,
            @Param("fundStatusCpdId") Long fundStatusCpdId);

    @Query("select DISTINCT d from TbAcCodingstructureMasEntity d where d.orgid=:orgid order by 1 desc")
    Iterable<TbAcCodingstructureMasEntity> findAllUsingOrgId(@Param("orgid") Long orgid);

    @Query("select cd from TbAcCodingstructureMasEntity cd,TbComparamDetEntity cp where cd.orgid=:orgid and cd.defineOnflag=:defaultFlag and cp.cpdValue=:comCpdIdCode and cp.cpdId=cd.comCpdId and cp.orgid=cd.orgid")
    List<TbAcCodingstructureMasEntity> checkDefaultFlagIsExists(@Param("comCpdIdCode") String comCpdIdCode,
            @Param("orgid") Long orgId, @Param("defaultFlag") String defaultFlag);

    @Query("select fundMas from TbAcCodingstructureMasEntity configMas,TbAcCodingstructureDetEntity configDet,AccountFundMasterEntity fundMas where configMas.codcofId=configDet.tbAcCodingstructureMasEntity and configDet.codcofdetId=fundMas.tbAcCodingstructureDet.codcofdetId and configMas.codNoLevel=configDet.codLevel and configMas.comCpdId=:cpdFundId and fundMas.orgid=:orgid")
    List<AccountFundMasterEntity> getFundMasterForDefaultorgMapping(@Param("cpdFundId") Long cpdFundId,
            @Param("orgid") Long orgid);

    @Query("SELECT csd.codcofdetId, csd.codDescription FROM TbAcCodingstructureDetEntity csd WHERE csd.tbAcCodingstructureMasEntity IN (SELECT csm.codcofId FROM TbAcCodingstructureMasEntity csm WHERE csm.comCpdId"
            + " IN (SELECT m.comCpdId FROM TbAcCodingstructureMasEntity m WHERE m.comCpdId IN(:param1,:param2))) AND csd.orgId=:orgId ORDER BY csd.codDescription ASC")
    List<Object[]> queryAccountHeadByChartOfAccount(@Param("param1") Long param1, @Param("param2") Long param2,
            @Param("orgId") Long orgId);
    
    @Query("SELECT d.codcofdetId, d.codLevel,d.codDescription,m.comCpdId FROM TbAcCodingstructureDetEntity d,TbAcCodingstructureMasEntity m WHERE d.tbAcCodingstructureMasEntity=m.codcofId and d.codcofdetId=:codcofdetId and d.orgId=:orgId")
    Object[] queryAccountHeadById (@Param("codcofdetId") Long codcofdetId,@Param("orgId") Long orgId);

}
