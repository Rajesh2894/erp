/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOAssessmentKeyParamEntity;
import com.abm.mainet.sfac.domain.FPOAssessmentSubParamEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FPOAssSubParamRepository extends JpaRepository<FPOAssessmentSubParamEntity, Long>{

	/**
	 * @param key
	 * @return
	 */
	List<FPOAssessmentSubParamEntity> findByFpoKeyMasterEntity(FPOAssessmentKeyParamEntity key);


}
