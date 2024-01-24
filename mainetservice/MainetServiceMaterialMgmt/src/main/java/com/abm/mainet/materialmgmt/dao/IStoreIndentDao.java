package com.abm.mainet.materialmgmt.dao;

import java.util.List;

public interface IStoreIndentDao {

	List<Object[]> getStoreIndentSummaryList(Long requestStore, Long storeIndentId, Long issueStore, String status,
			Long orgId);

}
