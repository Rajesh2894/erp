/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.AssessmentSubParameterEntity;
import com.abm.mainet.sfac.domain.AssessmentSubParameterHist;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface AssessmentSubParameterHistRepository extends JpaRepository<AssessmentSubParameterHist, Long>{

}
