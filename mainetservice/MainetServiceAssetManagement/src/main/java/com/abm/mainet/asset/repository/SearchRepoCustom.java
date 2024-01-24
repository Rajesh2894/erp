/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.List;

import com.abm.mainet.asset.ui.dto.SearchDTO;

/**
 * @author satish.rathore
 *
 */
public interface SearchRepoCustom {
	public List<Object[]> search(SearchDTO searchDTO);
}
