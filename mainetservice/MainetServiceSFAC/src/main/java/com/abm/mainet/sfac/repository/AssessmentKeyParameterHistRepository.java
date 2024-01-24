/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.AssessmentKeyParameterHist;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface AssessmentKeyParameterHistRepository extends JpaRepository<AssessmentKeyParameterHist, Long>{

}
