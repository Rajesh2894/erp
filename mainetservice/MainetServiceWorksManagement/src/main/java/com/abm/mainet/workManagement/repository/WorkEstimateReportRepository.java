package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.WorkEstimateMaster;

@Repository
public interface WorkEstimateReportRepository extends CrudRepository<WorkEstimateMaster, Long> {

    @Query("select a.sordCategory,a.sordSubCategory,a.sorDIteamNo,a.sorDDescription,a.sorIteamUnit,a.workEstimQuantity, "
            + "(select sum(c.sorBasicRate) from WorkEstimateMaster c "
            + " where (c.workEstimPId=a.workEstemateId) or (c.workEstemateId=a.workEstemateId)  group by COALESCE(c.workEstimPId,c.workEstemateId)) AS Rate "
            + "from WorkEstimateMaster a where a.workId =:workId  and a.orgId =:orgId and a.workEstimActive = 'Y' ")
    List<Object[]> findAbstractReportSheet(@Param("workId") Long workId,  @Param("orgId") Long orgId);
    
    @Query("select  a.sordCategory,a.sordSubCategory,a.sorDIteamNo,a.sorDDescription,a.sorIteamUnit,a.workEstimQuantity, "
            + "(select sum(c.sorBasicRate)   from WorkEstimateMaster c "
            + " where (c.workEstimPId=a.workEstemateId) or (c.workEstemateId=a.workEstemateId)  group by COALESCE(c.workEstimPId,c.workEstemateId)) AS Rate "
            + "from WorkEstimateMaster a, WorkDefinationEntity b, TbWmsProjectMaster d where b.workId=a.workId and d.projId=b.projMasEntity.projId and "
            + " a.workId=:workId and b.workType=:workType and d.dpDeptId=:deptId and a.orgId =:orgId and a.workEstimActive = 'Y'")
    List<Object[]> findAbstractReportSheet(@Param("workId") Long workId, @Param("deptId")  Long deptId, @Param("workType")  Long workType,  @Param("orgId") Long orgId);

    @Query("Select c.workEstemateId, c.sorDIteamNo ,c.sorDDescription ,d.meMentValType,"
            + "d.meMentNumber,d.meMentLength , d.meMentBreadth,d.meMentHeight, "
            + "d.meValue,d.meMentFormula,d.meMentToltal from WorkEstimateMaster c,WorkEstimateMeasureDetails d "
            + "where c.workEstemateId=d.workEstemateId and c.orgId=d.orgId and "
            + "c.workId=:workId AND d.orgId=:orgId")
    List<Object[]> findMeasurementReportSheet(@Param("workId") Long workId, @Param("orgId") Long orgId);

}
