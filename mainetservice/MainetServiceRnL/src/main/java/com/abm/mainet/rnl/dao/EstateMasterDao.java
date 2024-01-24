/**
 * 
 */
package com.abm.mainet.rnl.dao;

import java.util.List;

import com.abm.mainet.rnl.domain.EstateEntity;

/**
 * @author divya.marshettiwar
 *
 */
@SuppressWarnings("unused")
public interface EstateMasterDao {

	List<Object[]> searchData(Long orgId, Long locationId, Long estateId, Long purpose,Integer type1,Integer type2, Long acqType);
}
