/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOAssessmentKeyParamEntity;
import com.abm.mainet.sfac.domain.FPOAssessmentMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FPOAssKeyParamRepository extends JpaRepository<FPOAssessmentKeyParamEntity, Long>{


	/**
	 * @param masEntity
	 * @return
	 */
	List<FPOAssessmentKeyParamEntity> findByFpoMasterEntity(FPOAssessmentMasterEntity masEntity);


}
