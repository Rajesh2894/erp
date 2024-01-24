package com.abm.mainet.cfc.checklist.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.cfc.checklist.domain.ChecklistStatusView;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public interface IChecklistSearchService {
    List<ChecklistStatusView> serachChecklist(long orgId, Long applicationId, Long serviceId, String name,
            Date fromDate, Date toDate, String applicationStatus);

    ChecklistStatusView getCheckListDataByApplication(long orgId, long applicationId);

    boolean updateApplicationChecklistStatus(long applicationId, long orgId, String checklistFlag);

     CommonChallanDTO getApplicantName(Long applicationId,Long orgId);
     
    public List<DocumentDetailsVO> getCheckListDocument(String orgId,String application, String checkListFlag);
}
