package com.abm.mainet.asset.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.ui.dto.SearchDTO;
import com.abm.mainet.common.dao.AbstractDAO;


public interface SearchRepo extends CrudRepository<AssetInformation, Serializable>, SearchRepoCustom{

	/**
	 * It is used to search assets
	 * 
	 * @return list of Object as join is applied on it
	 */
	//public List<Object[]> search(SearchDTO searchDTO);
	//extends AbstractDAO<Long>
}
