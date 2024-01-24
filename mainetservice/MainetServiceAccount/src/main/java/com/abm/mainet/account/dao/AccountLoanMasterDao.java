package com.abm.mainet.account.dao;

import java.util.List;
import com.abm.mainet.account.domain.AccountLoanMasterEntity;

public interface AccountLoanMasterDao {

	List<AccountLoanMasterEntity> searchByDeptAndPurpose(Long loanId,String lnDeptname, String lnPurpose,Long orgId,String loanCode);
}
