
package com.abm.mainet.account.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.account.domain.AccountBudgetReappropriationMasterEntity;

/**
 * @author prasad.kancharla
 *
 */
public interface AccountBudgetReappropriationMasterDao {

    List<AccountBudgetReappropriationMasterEntity> findByGridAllData(Long faYearid, Long cpdBugtypeId, Long dpDeptid,
            Long prBudgetCodeid, String budgIdentifyFlag,Long fieldId, Long orgId);

    List<AccountBudgetReappropriationMasterEntity> findByAuthorizationGridData(Date frmDate, Date todate, Long cpdBugtypeId,
            String status, String budgIdentifyFlag, Long orgId);

}
