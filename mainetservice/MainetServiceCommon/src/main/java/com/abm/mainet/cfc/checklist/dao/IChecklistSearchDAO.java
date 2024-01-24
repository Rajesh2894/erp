package com.abm.mainet.cfc.checklist.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.cfc.checklist.domain.ChecklistStatusView;

public interface IChecklistSearchDAO {

    List<ChecklistStatusView> queryChecklist(long orgId, Long applicationId, Long serviceId, String name, Date fromDate,
            Date toDate, String applicationStatus);

    ChecklistStatusView queryChecklistByApplication(long orgId, long applicationId);

    boolean updateChecklistFlag(long applicationId, long orgId, String checklistFlag);

}
