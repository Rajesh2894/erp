/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.AssessmentSubParameterDetailHistory;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface AssessmentSubParaDetHistRepository extends JpaRepository<AssessmentSubParameterDetailHistory, Long>{

}
