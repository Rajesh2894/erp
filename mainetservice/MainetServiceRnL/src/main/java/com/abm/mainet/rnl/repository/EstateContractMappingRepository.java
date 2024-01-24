package com.abm.mainet.rnl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.rnl.domain.EstateContractMapping;

public interface EstateContractMappingRepository extends CrudRepository<EstateContractMapping, Long> {
	
    List<EstateContractMapping> findByContractMastEntityContId(Long contId);

    @Query("select count(c) from EstateContractMapping c where c.orgId =?1 and c.estatePropertyEntity.propId = ?2 )")
    Long findCountForProperty(Long orgId, Long propId);

    @Query("from ContractMastEntity c where c.orgId =?1 and c.contDept = ?2 and not exists (select m.contractMastEntity.contId from EstateContractMapping m  where m.contractMastEntity.contId = c.contId)")
    List<ContractMastEntity> findContractDeptWiseNotExist(Long orgId, Long deptId);

    @Query("from ContractMastEntity c where c.orgId =?1 and c.contDept = ?2 and exists (select m.contractMastEntity.contId from EstateContractMapping m  where m.contractMastEntity.contId = c.contId)")
    List<ContractMastEntity> findContractDeptWiseExist(Long orgId, Long deptId);

    @Query("from ContractMastEntity c where c.orgId =?1 and c.contDept = ?2 and (c.contNo = ?3 or c.contDate =?4 ) and exists (select m.contractMastEntity.contId from EstateContractMapping m  where m.contractMastEntity.contId = c.contId)")
    List<ContractMastEntity> findContractsExist(Long orgId, Long deptId, String contNo, Date contdate);

    @Query("select count(c)from ContractMastEntity c where c.orgId =?1 and c.contDept = ?2 and not exists (select m.contractMastEntity.contId from EstateContractMapping m  where m.contractMastEntity.contId = c.contId)")
    Long getCountContractDeptWiseNotExist(Long orgId, Long deptId);

    @Modifying
    @Query("update ContractMastEntity c set c.contCloseFlag = :contCloseFlag, c.updatedBy = :empId,c.updatedDate = CURRENT_TIMESTAMP where c.contId = :contId ")
    void updateContractCloseFlag(@Param("contCloseFlag") String contCloseFlag, @Param("contId") Long contId,
            @Param("empId") Long empId);

    List<EstateContractMapping> findByEstatePropertyEntityPropId(Long propId);

    @Query("from EstateContractMapping ec where ec.mappingId = (select max(mappingId) from EstateContractMapping where estatePropertyEntity.propId=ec.estatePropertyEntity.propId AND ec.orgId = ?1 AND ec.active= ?2)")
    List<EstateContractMapping> fetchPropertiesForBillPay(Long orgId, String status); 
   
    @Query("select c.contractMastEntity from EstateContractMapping c where c.orgId =?1 and c.estatePropertyEntity.propId = ?2 ")
    ContractMastEntity findByEstatePropertyPropId(Long orgId, Long propId);
    
    @Query("select c.contractMastEntity from EstateContractMapping c where c.orgId =?1 and c.estatePropertyEntity.propId = ?2 ")
    List<ContractMastEntity> findAllMappedProperty(Long orgId, Long propId);
    
    @Query("Select e from EstateContractMapping e where e.wfRefno=:wfRefno and e.orgId=:orgId")
    EstateContractMapping getPropIdByAppId(@Param("wfRefno") String wfRefno,  @Param("orgId") Long orgId);
    
    @Modifying
    @Query("update TbCfcApplicationMstEntity am set am.apmAppRejFlag =:approvalStatus ,am.apmApprovedBy =:approvedBy where am.apmApplicationId =:applicationId")
    void contactMappingApprovalWorkflow(@Param("approvalStatus") String approvalStatus,
            @Param("approvedBy") Long approvedBy, @Param("applicationId") Long applicationId);

    @Query("Select e from ContractMastEntity e where e.orgId=:orgId and e.contNo=:contNo")
    ContractMastEntity findContractsByContractNo(@Param("orgId")Long orgId, @Param("contNo") String contNo);
    
    @Query("Select e from EstateContractMapping e where e.orgId=:orgId and e.contractMastEntity.contId=:contId")
    EstateContractMapping findContractsByContractId(@Param("orgId") Long orgId, @Param("contId") Long contId);

    @Query("from EstateContractMapping ec where ec.mappingId = (select max(mappingId) from EstateContractMapping where estateEntity.esId=ec.estateEntity.esId AND ec.orgId = ?1 AND ec.active= ?2)")
	List<EstateContractMapping> fetchEstateDetailsList(Long orgId, String status);

    @Query("from EstateContractMapping ec where ec.mappingId = (select max(mappingId) from EstateContractMapping where estateEntity.esId=ec.estateEntity.esId AND ec.orgId = ?1 AND ec.active= ?2 AND ec.contractMastEntity.contId =?3)")
   	List<EstateContractMapping> fetchEstateDetailsByContId(Long orgId, String status, Long contId);
    
    @Query("from EstateContractMapping ec where ec.mappingId = (select max(mappingId) from EstateContractMapping where estateEntity.esId=ec.estateEntity.esId AND estatePropertyEntity.propId=ec.estatePropertyEntity.propId AND ec.estateEntity.code = ?1 AND ec.orgId = ?2 ) order by ec.estatePropertyEntity.code ASC")
    List<EstateContractMapping> fetchPropertyByEstateName(String estateName, Long orgId);
    
    @Query("select em.estateEntity.code, em.estateEntity.nameEng, em.estateEntity.nameReg, em.estatePropertyEntity.code, em.estatePropertyEntity.name,c.contNo from ContractMastEntity c , EstateContractMapping em where em.contractMastEntity.contId=c.contId and c.contActive!='N' and em.contractMastEntity.contNo=:contNo  and c.orgId=:orgId")
    List<Object[]> fetchEstatePropertyDetails (@Param("contNo") String contNo, @Param("orgId") Long orgId);
    
    @Query(value = "SELECT COUNT(em.RM_RCPTNO) FROM TB_DUP_RECEIPT em " +
            "WHERE em.APM_APPLICATION_ID = :contId", nativeQuery = true)
    Long countBySmShortdescAndAdditionalRefNo(@Param("contId") Long contId);

    
    

    
    
    
}
