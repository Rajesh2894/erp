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
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.ui.validator.CourtMasterValidator;

@Component
@Scope("session")
public class CourtMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    private CourtMasterDTO courtMasterDto;
    private String saveMode;
    private String removeChildIds;
    private List<CourtMasterDTO> courtMasterDtos = new ArrayList<>();
    private String envFlag;
    private List<CourtMasterDTO> courtNameList;

    @Autowired
    private ICourtMasterService courtMasterService;

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getRemoveChildIds() {
        return removeChildIds;
    }

    public void setRemoveChildIds(String removeChildIds) {
        this.removeChildIds = removeChildIds;
    }

    public CourtMasterDTO getCourtMasterDto() {
        return courtMasterDto;
    }

    public void setCourtMasterDto(CourtMasterDTO courtMasterDto) {
        this.courtMasterDto = courtMasterDto;
    }

    public List<CourtMasterDTO> getCourtMasterDtos() {
        return courtMasterDtos;
    }

    public void setCourtMasterDtos(List<CourtMasterDTO> courtMasterDtos) {
        this.courtMasterDtos = courtMasterDtos;
    }
 
    public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}

	public List<CourtMasterDTO> getCourtNameList() {
		return courtNameList;
	}

	public void setCourtNameList(List<CourtMasterDTO> courtNameList) {
		this.courtNameList = courtNameList;
	}

	@Override
    public boolean saveForm() {

    	courtMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        validateBean(courtMasterDto, CourtMasterValidator.class);
        if (hasValidationErrors())
            return false;
        Employee employee = UserSession.getCurrent().getEmployee();
        if (getSaveMode().equals(MainetConstants.Legal.SaveMode.EDIT)) {
            courtMasterDto.setUpdatedBy(employee.getEmpId());
            courtMasterDto.setUpdatedDate(new Date());
            courtMasterDto.setLgIpMacUpd(employee.getEmppiservername());
            courtMasterService.updateCourtMaster(courtMasterDto);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.editCourtMaster"));
        } else {
            courtMasterDto.setCrtStatus(MainetConstants.CommonConstants.Y);
            courtMasterDto.setCreatedBy(employee.getEmpId());
            courtMasterDto.setCreateDate(new Date());
            courtMasterDto.setLgIpMac(employee.getEmppiservername());
            courtMasterService.saveCourtMaster(courtMasterDto);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.saveCourtMaster"));
        }

        return true;
    }

}
