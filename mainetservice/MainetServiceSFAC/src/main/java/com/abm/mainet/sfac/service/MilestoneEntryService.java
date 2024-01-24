package com.abm.mainet.sfac.service;

import java.util.List;

import com.abm.mainet.sfac.dto.MilestoneMasterDto;

public interface MilestoneEntryService {

	List<MilestoneMasterDto> getMilestoneDetails(Long iaId, String milestoneId);

	MilestoneMasterDto saveAndUpdateApplication(MilestoneMasterDto mastDto);

	MilestoneMasterDto getDetailById(Long msId);

}
