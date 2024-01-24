package com.abm.mainet.materialmgmt.dao;

import java.util.List;

import com.abm.mainet.materialmgmt.domain.ItemMasterEntity;

public interface ItemMasterDao {

	List<ItemMasterEntity> findByAllGridSearchData(Long category, Long type, Long itemgroup, Long itemsubgroup,
			String name, Long orgId);
}
