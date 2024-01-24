
package com.abm.mainet.account.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.account.domain.AccountBudgetAdditionalSupplementalEntity;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetAdditionalSupplementalDao {

    List<AccountBudgetAdditionalSupplementalEntity> findByGridAllData(Long faYearid, Long cpdBugtypeId, Long dpDeptid,
            Long prBudgetCodeid, String budgIdentifyFlag, Long orgId);

    List<AccountBudgetAdditionalSupplementalEntity> findByAuthorizationGridData(Date frmDate, Date todate, Long cpdBugtypeId,
            String status, String budgIdentifyFlag, Long orgId);
}
