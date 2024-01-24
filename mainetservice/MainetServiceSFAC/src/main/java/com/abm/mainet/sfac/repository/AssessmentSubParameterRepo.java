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
import com.abm.mainet.sfac.domain.AssessmentSubParameterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface AssessmentSubParameterRepo extends JpaRepository<AssessmentSubParameterEntity, Long>{

	/**
	 * @param assKId
	 * @return
	 */
	@Query("Select s from AssessmentSubParameterEntity s where s.keyMasterEntity.assKId=:assKId")
	List<AssessmentSubParameterEntity> findByAsskId(@Param("assKId") Long assKId);

	/**
	 * @param key
	 * @return
	 */
	List<AssessmentSubParameterEntity> findByKeyMasterEntity(AssessmentKeyParameterEntity key);

}
