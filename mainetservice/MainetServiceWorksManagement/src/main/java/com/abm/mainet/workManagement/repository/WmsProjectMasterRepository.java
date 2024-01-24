package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.TbWmsProjectMaster;

@Repository
public interface WmsProjectMasterRepository extends CrudRepository<TbWmsProjectMaster, Long> {

    @Modifying
    @Query("update TbWmsProjectMaster set projActive = 'N'  where projId = :projId")
    void deleteProjectByProjId(@Param("projId") Long projId);

    @Query("SELECT pm FROM TbWmsProjectMaster pm  where pm.orgId = :orgId and projActive= 'Y'")
    List<TbWmsProjectMaster> getActiveProjectMasterListByOrgId(@Param("orgId") long orgId);

    @Query("SELECT pm FROM TbWmsProjectMaster pm  where pm.orgId=:orgId and pm.projId  in :projId and pm.projActive is 'Y' ORDER BY pm.projId DESC")
    List<TbWmsProjectMaster> getAllProjectMaster(@Param("orgId") Long orgId, @Param("projId") List<Long> projId);

    @Query("select a.projId from TbWmsProjectMaster a where a.projId not in "
            + "(select distinct ms.projectMaster.projId from MileStone ms where ms.mastDetailsEntity.workId is null "
            + "and ms.mileStoneType='P' and ms.orgId=:orgId) and a.orgId=:orgId union "
            + "select a.projId from TbWmsProjectMaster a where a.projId not in "
            + "(select distinct ms.projectMaster.projId from MileStone ms where ms.mastDetailsEntity.workId is not null "
            + "and ms.mileStoneType='P' and ms.orgid=:orgId) and a.orgid=:orgId ")
    List<Long> getAllProjectAssociatedMileStone(@Param("orgId") Long orgId);

    @Query("SELECT pm FROM TbWmsProjectMaster pm  where pm.orgId=:orgId and pm.dpDeptId=:deptId and pm.projActive is 'Y' ORDER BY pm.projId DESC")
    List<TbWmsProjectMaster> getAllActiveProjectsByDeptIdAndOrgId(@Param("deptId") Long deptId,
            @Param("orgId") Long orgId);

    @Query("SELECT pm FROM TbWmsProjectMaster pm  where pm.orgId = :orgId and projActive= 'Y' and schId.wmSchId is null ")
    List<TbWmsProjectMaster> getActiveProjectMasterListWithoutSchemeByOrgId(@Param("orgId") long orgId);

    @Query("SELECT pm FROM TbWmsProjectMaster pm  where pm.schId.wmSchId= :schId ")
    List<TbWmsProjectMaster> getActiveProjectMasterListBySchId(@Param("schId") long schId);

    @Query("select distinct a.dpDeptId from TbWmsProjectMaster a where a.orgId=:orgId ")
    List<Long> getAllProjectAssociatedDeptList(@Param("orgId") Long orgId);
    
    TbWmsProjectMaster findByProjCode(String dept);
    

}
