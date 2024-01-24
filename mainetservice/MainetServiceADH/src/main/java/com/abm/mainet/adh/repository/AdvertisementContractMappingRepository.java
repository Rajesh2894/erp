package com.abm.mainet.adh.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.adh.domain.ADHContractMappingEntity;
import com.abm.mainet.common.domain.ContractMastEntity;

/**
 * @author cherupelli.srikanth
 * @since 04 November 2019
 */
@Repository
public interface AdvertisementContractMappingRepository extends JpaRepository<ADHContractMappingEntity, Long> {

    List<ADHContractMappingEntity> findByContractMastEntityContIdAndOrgId(Long contId, Long orgId);

    @Query("SELECT cm.contId,cm.contNo FROM ContractMastEntity cm WHERE cm.contDept=:contDeptId AND cm.orgId=:orgId AND cm.contNo IS NOT NULL")
    List<String[]> findByContDeptAndOrgId(@Param("contDeptId") Long contDeptId, @Param("orgId") Long orgId);

    @Query("from ContractMastEntity c where c.orgId =?1 and c.contDept = ?2 and exists (select m.contractMastEntity.contId from ADHContractMappingEntity m  where m.contractMastEntity.contId = c.contId)")
    List<ContractMastEntity> findContractDeptWiseExist(Long orgId, Long deptId);

    @Query("from ContractMastEntity c where c.orgId =?1 and c.contDept = ?2 and (c.contNo = ?3 or c.contDate =?4 ) and exists (select m.contractMastEntity.contId from ADHContractMappingEntity m  where m.contractMastEntity.contId = c.contId)")
    List<ContractMastEntity> findContractsExist(Long orgId, Long deptId, String contNo, Date contdate);

    @Query("from ContractMastEntity c where c.orgId =?1 and c.contId = ?2 ")
    List<ContractMastEntity> findContractsView(Long orgId, Long contId);

    @Query("SELECT c from ADHContractMappingEntity c where  c.contractMastEntity.contId =:contId and c.orgId =:orgId ")
    ADHContractMappingEntity findContractByContId(@Param("contId") Long contId, @Param("orgId") Long orgId);

    @Query("SELECT count(c) from ADHContractMappingEntity c where  c.contractMastEntity.contId =:contId and c.orgId =:orgId ")
    Long findCountByContId(@Param("contId") Long contId, @Param("orgId") Long orgId);

    @Query("SELECT c.contractMastEntity.contId FROM ADHContractMappingEntity c WHERE c.hoardingEntity.hoardingId=:hoardingId AND c.orgId=:orgId")
    Long findHoardingExistByHoardIdAndOrgId(@Param("hoardingId") Long hoardingId, @Param("orgId") Long orgId);
}
