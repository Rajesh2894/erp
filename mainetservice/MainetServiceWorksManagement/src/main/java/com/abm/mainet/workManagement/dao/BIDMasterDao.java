package com.abm.mainet.workManagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.BIDMasterEntity;
import com.abm.mainet.workManagement.domain.TenderMasterEntity;
import com.abm.mainet.workManagement.domain.TenderWorkEntity;

public interface BIDMasterDao {
	public List<TenderWorkEntity> searchTenderByTendernoAndDate(Long orgid, String tenderNo, Date tenderDate);
	
	public BIDMasterEntity searchBIdDetByVendorIdandBidId(Long orgId,String bidId,Long vendorId, Long tndId);
}
