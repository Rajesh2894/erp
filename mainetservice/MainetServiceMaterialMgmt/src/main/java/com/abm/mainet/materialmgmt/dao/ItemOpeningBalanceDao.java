package com.abm.mainet.materialmgmt.dao;

import java.util.List;

import com.abm.mainet.materialmgmt.domain.ItemOpeningBalanceEntity;

public interface ItemOpeningBalanceDao {
	
	
	List<ItemOpeningBalanceEntity> searchBalaneData(Long storeId, Long itemid,
			Long orgId);

}
