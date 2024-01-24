package com.abm.mainet.swm.service;


import java.util.List;

import com.abm.mainet.swm.dto.SurveyReportDTO;

public interface ISurveyFormService {
    
    void saveSurveyForm(SurveyReportDTO surveyDetails);
    
    void updateSurveyForm(SurveyReportDTO surveyDetails);
    
    SurveyReportDTO searchSurveyDetails(Long locId, Long orgId);
    
    List<Long[]> getDetailsbyOrgIdandLocId(Long locId,Long orgId);
    
    SurveyReportDTO addWardZoneDetails(SurveyReportDTO surveyReportDTO, Long locId, Long orgId);

}
