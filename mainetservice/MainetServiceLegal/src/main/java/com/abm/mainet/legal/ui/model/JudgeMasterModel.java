package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.JudgeDetailMasterDTO;
import com.abm.mainet.legal.dto.JudgeMasterDTO;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.service.IJudgeMasterService;
import com.abm.mainet.legal.ui.validator.JudgeMasterValidator;

@Component
@Scope("session")
public class JudgeMasterModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JudgeMasterDTO judgeMasterDto;
    private List<LookUp> crtTypes;
    private List<LookUp> courts = new ArrayList<>();
    private String saveMode;
    private List<JudgeDetailMasterDTO> judgeDetails = new ArrayList<>();
    private List<JudgeMasterDTO> judgeMasterDtos = new ArrayList<>();
    private Long caseId;
    private List<CourtMasterDTO> courtNameList;

    @Autowired
    private ICourtMasterService courtMasterService;

    @Autowired
    private IJudgeMasterService judgeMasterService;

    @Override
    protected void initializeModel() {
        crtTypes = CommonMasterUtility.getLookUps("CTP", UserSession.getCurrent().getOrganisation());
        List<CourtMasterDTO> courtMasterDtos = courtMasterService
                .getAllActiveCourtMaster(UserSession.getCurrent().getOrganisation().getOrgid());
        courtMasterDtos.stream()
        .filter(k ->  k.getCrtStatus().equals("Y")).forEach(c -> {
            LookUp court = new LookUp();
            court.setDescLangFirst(c.getCrtName());
            court.setDescLangSecond(c.getCrtNameReg());
            court.setLookUpId(c.getId());
            courts.add(court);
        });
    }

    @Override
    public boolean saveForm() {

        validateBean(judgeMasterDto, JudgeMasterValidator.class);
        if (hasValidationErrors())
            return false;

        judgeMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        Employee employee = UserSession.getCurrent().getEmployee();
        if (getSaveMode().equals(MainetConstants.Legal.SaveMode.EDIT)) {
            judgeMasterDto.setUpdatedBy(employee.getEmpId());
            judgeMasterDto.setUpdatedDate(new Date());
            judgeMasterDto.setLgIpMacUpd(employee.getEmppiservername());
            this.setSuccessMessage(getAppSession().getMessage("lgl.saveJudgeMaster.edit"));
            judgeMasterService.updateJudgeMaster(judgeMasterDto);
        } else {
            judgeMasterDto.setCreatedBy(employee.getEmpId());
            judgeMasterDto.setCreateDate(new Date());
            judgeMasterDto.setLgIpMac(employee.getEmppiservername());
            this.setSuccessMessage(getAppSession().getMessage("lgl.saveJudgeMaster"));
            judgeMasterService.saveJudgeMaster(judgeMasterDto);
        }
        return true;
    }

    public JudgeMasterDTO getJudgeMasterDto() {
        return judgeMasterDto;
    }

    public void setJudgeMasterDto(JudgeMasterDTO judgeMasterDto) {
        this.judgeMasterDto = judgeMasterDto;
    }

    public List<LookUp> getCrtTypes() {
        return crtTypes;
    }

    public void setCrtTypes(List<LookUp> crtTypes) {
        this.crtTypes = crtTypes;
    }

    public List<LookUp> getCourts() {
        return courts;
    }

    public void setCourts(List<LookUp> courts) {
        this.courts = courts;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<JudgeDetailMasterDTO> getJudgeDetails() {
        return judgeDetails;
    }

    public void setJudgeDetails(List<JudgeDetailMasterDTO> judgeDetails) {
        this.judgeDetails = judgeDetails;
    }

    public List<JudgeMasterDTO> getJudgeMasterDtos() {
        return judgeMasterDtos;
    }

    public void setJudgeMasterDtos(List<JudgeMasterDTO> judgeMasterDtos) {
        this.judgeMasterDtos = judgeMasterDtos;
    }

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public List<CourtMasterDTO> getCourtNameList() {
		return courtNameList;
	}

	public void setCourtNameList(List<CourtMasterDTO> courtNameList) {
		this.courtNameList = courtNameList;
	}
    
    

}
