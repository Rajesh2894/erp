package com.abm.mainet.cfc.checklist.dao;

import java.util.List;

import com.abm.mainet.cfc.checklist.domain.CheckListReportEntity;
import com.abm.mainet.common.domain.Organisation;

public interface ICheckListVerificationReportDAO {

    List<CheckListReportEntity> getRejLetterList(Long applicationId,
            String statusVariable, Organisation orgId);

    long updateApplicationMastrRejection(long apmApplicationId, Organisation orgId);

}
