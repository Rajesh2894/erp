package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.WorkDefinationEntity;
import com.abm.mainet.workManagement.domain.WorkDefinationYearDetEntity;

@Repository
public interface WorkDefinationYearDetRepo extends JpaRepository<WorkDefinationYearDetEntity, Long> {

     //Defect #80491 Modified
	@Query("SELECT wd FROM WorkDefinationYearDetEntity wd WHERE wd.workDefEntity.workId=:workId and wd.yeActive <> 'N'")
	List<WorkDefinationYearDetEntity> getYearDetByWorkId(@Param("workId") Long workId);
	
	@Modifying
	@Query(value = "update WorkDefinationYearDetEntity as br set br.sacHeadId=:sacHeadId where br.yearId=:yearId and br.orgId=:orgId")
	void updateBudgetHead(@Param("yearId") Long yearId, @Param("sacHeadId") Long sacHeadId,@Param("orgId") Long orgId);

}
