/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOAssessmentSubParamDetail;
import com.abm.mainet.sfac.domain.FPOAssessmentSubParamEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FPOAssSubParamDetRepository extends JpaRepository<FPOAssessmentSubParamDetail, Long>{

	/**
	 * @param sub
	 * @return
	 */
	List<FPOAssessmentSubParamDetail> findByFpoSubParamEntity(FPOAssessmentSubParamEntity sub);

	

}
