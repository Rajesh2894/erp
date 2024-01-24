package com.abm.mainet.materialmgmt.service;

import java.util.List;

import com.abm.mainet.materialmgmt.dto.ItemOpeningBalanceDto;

public interface ItemOpeningBalanceService {

	void save(ItemOpeningBalanceDto itemOpeningBalanceDto, List<Long> removeIds);

	List<ItemOpeningBalanceDto> findByOrgId(Long orgId);

	void updateStatus(Long updatedBy, Long openBalId);

	ItemOpeningBalanceDto findById(Long openBalId);

	List<ItemOpeningBalanceDto> serchByItemIdAndStoreId(Long storeId, Long itemId, Long orgId);

	List<String> checkDuplicateItemNos(Long itemId, List<String> itemNumberList, Long orgId);

}
