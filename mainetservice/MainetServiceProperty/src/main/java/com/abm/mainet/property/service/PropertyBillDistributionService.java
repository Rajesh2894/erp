package com.abm.mainet.property.service;

import java.util.List;

import com.abm.mainet.property.dto.NoticeGenSearchDto;

/**
 * @author cherupelli.srikanth
 * @since 07 May 2021
 */
public interface PropertyBillDistributionService {

	List<NoticeGenSearchDto> getAllBillsForDistributionDateUpdation(NoticeGenSearchDto searchDto);
	
}
