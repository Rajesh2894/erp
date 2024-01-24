/**
 * 
 */
package com.abm.mainet.sfac.dao;

import java.util.List;

import com.abm.mainet.sfac.domain.FPOMasterEntity;

/**
 * @author pooja.maske
 *
 */
public interface FPOMasterDao {

	/**
	 * @param fpoId
	 * @param fpoRegNo
	 * @return
	 */
	List<FPOMasterEntity> getfpoByIdAndRegNo(Long fpoId, String fpoRegNo,Long iaId,Long masId,String orgShortNm,String uniqueId);

	/**
	 * @param masId
	 * @param orgShortNm
	 * @return
	 */
	List<FPOMasterEntity> findFpoByMasId(Long masId, String orgShortNm,String uniqueId);

}
