package com.abm.mainet.materialmgmt.service;

import java.util.List;

import com.abm.mainet.materialmgmt.dto.BinDefMasDto;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;

public interface IBinMasService {

	void save(BinDefMasDto binDefMasDto);
	
	void saveBinLocation(BinLocMasDto binLocMasDto);
	
	List<BinDefMasDto> findAllBinDefintion(Long orgId);
	
	BinDefMasDto findByBinDefID(Long bindefId);
	
    List<BinLocMasDto> findAllBinLocation(Long orgId);
	
    BinLocMasDto findByBinLocID(Long binlocId);
    
    void deleteEmptyLocId();

	List<Object[]> getbinLocIdAndNameListByOrgId(Long orgId);

	List<Object[]> getbinLocIdAndNameListByStore(Long storeId, Long orgId);

}
