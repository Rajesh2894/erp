package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.materialmgmt.domain.DeptReturnEntity;
import com.abm.mainet.materialmgmt.domain.GoodsReceivedNotesEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseOrderEntity;


public interface DepartmentalReturnDao {

	List<DeptReturnEntity> searchindentReturnData(String dreturnno, Long indentid, Date fromDate, Date toDate,
			Long storeid, Character status, Long orgid);
	
	
	
}
