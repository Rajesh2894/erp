package com.abm.mainet.materialmgmt.dao;

import java.util.List;

import com.abm.mainet.materialmgmt.domain.StoreMaster;

public interface StoreMasterDao {

	List<StoreMaster> searhStoreMasterData(Long locationId,String storeName,Long orgId);
}
