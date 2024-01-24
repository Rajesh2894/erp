package com.abm.mainet.cfc.checklist.service;

import java.util.List;

import com.abm.mainet.cfc.checklist.domain.CheckListReportEntity;
import com.abm.mainet.cfc.checklist.dto.CheckListVerificationReportParentDTO;
import com.abm.mainet.cfc.checklist.ui.model.ChecklistVerificationSearchModel;
import com.abm.mainet.common.domain.Organisation;

public interface ICheckListVerificationReportService {

    /**
     * Gets the rej letter list.
     *
     * @param applicationId the application id
     * @param statusVariable the status variable
     * @param orgId the org id
     * @return the rej letter list
     */
    List<CheckListReportEntity> getRejLetterList(Long applicationId,
            String statusVariable, Organisation orgId);

    /**
     * Gets the rejected app list.
     *
     * @param model the model
     * @param checkListReportEntity the check list report entity
     * @param orgId the org id
     * @return the rejected app list
     */
    List<CheckListVerificationReportParentDTO> getRejectedAppList(
            ChecklistVerificationSearchModel model,
            CheckListReportEntity checkListReportEntity, Organisation orgId);
    
    long updateApplicationMastrRejection(Long apmApplicationId, Organisation  org);

}
