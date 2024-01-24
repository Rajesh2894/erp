package com.abm.mainet.intranet.dao;

import java.util.List;

import com.abm.mainet.intranet.domain.IntranetMaster;

public interface IntranetDao {

	List<IntranetMaster> getIntranetData(Long orgId, Long docCateType);
	
	IntranetMaster getIntranetDataByInId(Long inId, Long orgId);

	List<IntranetMaster> getAllIntranetData(Long orgId);


}
