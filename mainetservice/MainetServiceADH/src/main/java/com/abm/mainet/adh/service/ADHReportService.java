package com.abm.mainet.adh.service;

import java.util.List;

import com.abm.mainet.adh.dto.AdvertiserMasterDto;

public interface ADHReportService {
	AdvertiserMasterDto findAdvertiserRegister(String fromDate, String toDate, Long orgId);

}
