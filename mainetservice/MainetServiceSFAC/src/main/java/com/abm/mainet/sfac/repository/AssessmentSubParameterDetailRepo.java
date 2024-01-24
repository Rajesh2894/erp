/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.AssessmentSubParameterDetail;
import com.abm.mainet.sfac.domain.AssessmentSubParameterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface AssessmentSubParameterDetailRepo extends JpaRepository<AssessmentSubParameterDetail, Long>{

	/**
	 * @param assSId
	 * @return
	 */
	@Query("Select d from AssessmentSubParameterDetail d  where d.assSubParamEntity.assSId=:assSId")
	List<AssessmentSubParameterDetail> findByAssSId(@Param("assSId") Long assSId);

	/**
	 * @param sub
	 */
	List<AssessmentSubParameterDetail> findByAssSubParamEntity(AssessmentSubParameterEntity sub);

}
