/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.sfac.domain.AssessmentMasterEntity;

/**
 * @author pooja.maske
 *
 */
public interface AssessmentMasterDao {

	/**
	 * @param cbboId
	 * @param assStatus
	 * @param assDate
	 * @return
	 */
	List<AssessmentMasterEntity> findDetByIds(Long cbboId, String assStatus, Date assDate);

}
