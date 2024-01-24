/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.AssessmentKeyParameterEntity;
import com.abm.mainet.sfac.domain.AssessmentMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface AssessmentKeyParameterRepo extends JpaRepository<AssessmentKeyParameterEntity, Long>{

	/**
	 * @param assId
	 * @return
	 */
	@Query("SELECT k from  AssessmentKeyParameterEntity k WHERE k.masterEntity.assId=:assId")
	List<AssessmentKeyParameterEntity> findByAssId(@Param("assId") Long assId);

	/**
	 * @param masEntity
	 * @return
	 */
	
	List<AssessmentKeyParameterEntity> findByMasterEntity(AssessmentMasterEntity masEntity);

}
