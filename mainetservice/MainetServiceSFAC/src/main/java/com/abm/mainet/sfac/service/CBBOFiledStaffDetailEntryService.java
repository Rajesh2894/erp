package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.sfac.dto.BlockAllocationDetailDto;
import com.abm.mainet.sfac.dto.CBBOFiledStaffDetailsDto;

public interface CBBOFiledStaffDetailEntryService {

	List<LookUp> getBlockDetails(Long masId);

	List<CBBOFiledStaffDetailsDto> getCbboFieldStaffDetails(String name, Long block);

	CBBOFiledStaffDetailsDto saveAndUpdateApplication(CBBOFiledStaffDetailsDto mastDto);

	CBBOFiledStaffDetailsDto getDetailById(Long fsdId);

	BlockAllocationDetailDto getSD(Long block);

}
