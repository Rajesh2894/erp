package com.abm.mainet.materialmgmt.service;

import java.util.List;

import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;


public interface IStoreMasterService {
	
	StoreMasterDTO saveStore(StoreMasterDTO storeMasterDTO);
	
	List<StoreMasterDTO> serchStoreMasterDataByOrgid(Long orgId);
	
	List<StoreMasterDTO> serchStoreByLocAndStoreName(Long location, String storeName, Long orgId);

	StoreMasterDTO getStoreMasterByStoreId(Long storeId);

	void saveStoreExcelData(List<StoreMasterDTO> storeMasterDTO);

	String getStorenameByStoreId(Long storeId);

	List<Object[]> getStoreIdAndNameList(Long orgId);

	Object[] getStoreDetailsByStore(Long storeId, Long orgId);

	List<Object[]> getActiveStoreObjectListForAdd(Long orgId);

	Object[] getStoreLocationAddress(Long storeId, Long orgId);
	
	
}
