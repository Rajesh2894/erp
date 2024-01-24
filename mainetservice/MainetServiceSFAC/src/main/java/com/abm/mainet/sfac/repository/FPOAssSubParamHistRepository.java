/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOAssessmentSubParamHistory;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FPOAssSubParamHistRepository extends JpaRepository<FPOAssessmentSubParamHistory, Long>{

	/**
	 * @param assSId
	 * @return
	 */
	FPOAssessmentSubParamHistory findByAssSId(Long assSId);

}
