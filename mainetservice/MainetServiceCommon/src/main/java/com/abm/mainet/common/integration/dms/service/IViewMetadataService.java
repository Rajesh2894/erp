package com.abm.mainet.common.integration.dms.service;

import java.util.List;

import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.ui.model.ViewMetadataModel;

public interface IViewMetadataService {

	public List<DmsDocsMetadataDto> getMetadataDetails(String deptId, String metadataId, String metadataValue,
			Long roleId, Long orgid, Long zone, Long ward, Long mohalla, String docRefNo, String callType,ViewMetadataModel model,Long docType,
			String complainNo,String status);
}
