package com.abm.mainet.materialmgmt.dao;

import java.util.List;

public interface IndentProcessDao {

	List<Object[]> getIndentSummaryList(Long storeid, String indentno, Long deptId, Long indenter, String status,
			Long orgid);

}
