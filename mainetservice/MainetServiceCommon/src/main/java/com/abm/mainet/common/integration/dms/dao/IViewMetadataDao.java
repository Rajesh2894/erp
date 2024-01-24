package com.abm.mainet.common.integration.dms.dao;

import java.util.List;

import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadata;
import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadataDet;

public interface IViewMetadataDao {

	public List<DmsDocsMetadata> getMetadataDetails(String deptId, String metadataId, String metadataValue, Long roleId,
			long orgid, Long zone, Long ward, Long mohalla, String docRefNo,Long docType);

	List<DmsDocsMetadataDet> getMetadataDetDetails(List<Long> dmsId, long orgid);
	
	public List<DmsDocsMetadata> getApprovalMetadataDetails(String complainNo,String status,Long orgid);
}
