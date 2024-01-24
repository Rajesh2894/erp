package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.AreaWiseDto;
import com.abm.mainet.swm.dto.SurveyReportDTO;
import com.abm.mainet.swm.service.ISurveyFormService;

@Component
@Scope("session")
public class SurveyReportModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ISurveyFormService surveyFormService;

    private List<TbLocationMas> locationList = new ArrayList<>();
    
    private List<SurveyReportDTO> surveyReportDTOList = new ArrayList<>();

    SurveyReportDTO surveyReportDTO = new SurveyReportDTO();

    private AreaWiseDto areaWiseDto = new AreaWiseDto();

    private String saveMode;

    @Override
    public boolean saveForm() {

        if (surveyReportDTO.getSuId() == null) {
            surveyReportDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            surveyReportDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            surveyReportDTO.setCreatedDate(new Date());
            surveyReportDTO.setLgIpMac(Utility.getMacAddress());
            surveyFormService.saveSurveyForm(surveyReportDTO);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("swm.survey.added"));
        } else {
            surveyReportDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            surveyReportDTO.setUpdatedDate(new Date());
            surveyReportDTO.setLgIpMacUpd(Utility.getMacAddress());
            surveyFormService.updateSurveyForm(surveyReportDTO);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("swm.survey.edited"));
        }

        return true;

    }

    public List<TbLocationMas> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<TbLocationMas> locationList) {
        this.locationList = locationList;
    }

    public SurveyReportDTO getSurveyReportDTO() {
        return surveyReportDTO;
    }

    public void setSurveyReportDTO(SurveyReportDTO surveyReportDTO) {
        this.surveyReportDTO = surveyReportDTO;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public AreaWiseDto getAreaWiseDto() {
        return areaWiseDto;
    }

    public void setAreaWiseDto(AreaWiseDto areaWiseDto) {
        this.areaWiseDto = areaWiseDto;
    }

    public List<SurveyReportDTO> getSurveyReportDTOList() {
        return surveyReportDTOList;
    }

    public void setSurveyReportDTOList(List<SurveyReportDTO> surveyReportDTOList) {
        this.surveyReportDTOList = surveyReportDTOList;
    }
    
}
