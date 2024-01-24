package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.ProjectBudgetDetEntity;

@Repository
public interface ProjectBudgetYearDetRepo extends JpaRepository<ProjectBudgetDetEntity, Long> {
	
	@Query("SELECT wd FROM ProjectBudgetDetEntity wd WHERE wd.projectMasEntity.projId=:projId and wd.yeActive <> 'N' ")
	List<ProjectBudgetDetEntity> getYearDetByProjectId(@Param("projId") Long projId);
	

	/**
     * used to inactive Years By Ids
     * 
     * @param removeYearIdList
     * @param updatedBy
     */
    @Modifying
    @Query("UPDATE ProjectBudgetDetEntity y SET y.yeActive ='N',y.updatedDate=CURRENT_DATE, y.updatedBy=:updatedBy where y.pbId in (:removeYearIdList)")
    void iactiveYearsByIds(@Param("removeYearIdList") List<Long> removeYearIdList, @Param("updatedBy") Long updatedBy);

}
