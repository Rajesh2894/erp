/**
 * 
 */
package com.abm.mainet.common.master.dao;

import java.util.List;

import com.abm.mainet.common.integration.acccount.domain.TaxDefinationEntity;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public interface TaxDefinationDao {

	/**
	 * To Get TaxDefination Data On Basis of Pan apply, Tax Id
	 * 
	 * @param taxId
	 * @param panApp
	 * @param orgId
	 * @return List<TaxDefinationEntity>
	 */
	List<TaxDefinationEntity> findByAllGridSearchData(Long taxId, String panApp, Long orgId);
}
