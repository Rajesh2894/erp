/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.List;

import com.abm.mainet.sfac.domain.FPOAssessmentMasterEntity;

/**
 * @author pooja.maske
 *
 */
public interface FpoAssessmentMasterDao {

	/**
	 * @param fpoId
	 * @param assStatus
	 * @return
	 */
	List<FPOAssessmentMasterEntity> findByFpoIdAndAssStatus(Long fpoId, String assStatus);

}
