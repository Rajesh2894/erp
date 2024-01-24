/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.sfac.domain.CBBOMasterEntity;

/**
 * @author pooja.maske
 *
 */
public interface CBBOMasterDao {

	

	/**
	 * @param cbboId
	 * @param alcYearToCBBO
	 * @return
	 */
	List<CBBOMasterEntity> getCBBODetailsByIds(Long cbboId, Date alcYearToCBBO,Long iaId);

}
