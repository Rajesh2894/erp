package com.abm.mainet.account.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.account.domain.AccountGrantMasterEntity;

public interface AccountGrantMasterDao {

	List<AccountGrantMasterEntity> findByNameAndNature(String grtType, String grtName,String grtNo,Long fundId,Date fromDate, Date toDate,Long orgId);
}
