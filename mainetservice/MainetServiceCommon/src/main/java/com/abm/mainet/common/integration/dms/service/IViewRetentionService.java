package com.abm.mainet.common.integration.dms.service;

import java.util.List;

import com.abm.mainet.common.integration.dms.dto.DmsRetentionDto;
import com.abm.mainet.common.integration.dms.ui.model.ViewRetentionModel;

public interface IViewRetentionService {

	public List<DmsRetentionDto> getMetadataDetails(String deptId, String metadataId, String metadataValue,
			Long roleId, Long orgid, Long zone, Long ward, Long mohalla, String docRefNo, String callType,ViewRetentionModel model,Long docType,
			String complainNo,String status);
}
