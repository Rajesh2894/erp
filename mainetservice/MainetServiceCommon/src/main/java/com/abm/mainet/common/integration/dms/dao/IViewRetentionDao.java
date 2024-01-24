package com.abm.mainet.common.integration.dms.dao;

import java.util.List;

import com.abm.mainet.common.integration.dms.domain.DocRetentionDetEntity;
import com.abm.mainet.common.integration.dms.domain.DocRetentionEntity;

public interface IViewRetentionDao {

	public List<DocRetentionEntity> getMetadataDetails(String deptId, String metadataId, String metadataValue, Long roleId,
			long orgid, Long zone, Long ward, Long mohalla, String docRefNo,Long docType);

	public List<DocRetentionDetEntity> getMetadataDetDetails(List<Long> dmsId, long orgid);
	
	public List<DocRetentionEntity> getApprovalMetadataDetails(String complainNo,String status,Long orgid);
}
