package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;

public interface GrnInspectionDao {

	List<Object[]> searchGRNInspectionData(Long storeId, Long grnid, Date fromDate, Date toDate, Long poid, Long orgId);

}
