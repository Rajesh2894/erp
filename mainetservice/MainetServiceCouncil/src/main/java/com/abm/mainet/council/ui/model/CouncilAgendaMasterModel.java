package com.abm.mainet.council.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.council.dto.CouncilAgendaMasterDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.dto.CouncilRestResponseDto;
import com.abm.mainet.council.service.ICouncilAgendaMasterService;

@Component
@Scope("session")
public class CouncilAgendaMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 2570068561584498439L;
    private CouncilAgendaMasterDto couAgendaMasterDto = new CouncilAgendaMasterDto();
    private List<CouncilAgendaMasterDto> couAgendaMasterDtoList = new ArrayList<>();
    private CouncilRestResponseDto response = new CouncilRestResponseDto();
    private List<CouncilProposalMasterDto> proposalDtoList = new ArrayList<>();
    private List<CouncilProposalMasterDto> agendaProposalDtoList = new ArrayList<>();
    private List<LookUp> committeeTypeFilterList = new ArrayList<>();
    private boolean proposalPresent = false;
    private Long orgId;
    private String saveMode;
    private List<TbDepartment> departmentsList;
    private Long deptId;
    
    @Autowired
    private ICouncilAgendaMasterService agendaMasterService;

    public CouncilAgendaMasterDto getCouAgendaMasterDto() {
        return couAgendaMasterDto;
    }

    public void setCouAgendaMasterDto(CouncilAgendaMasterDto couAgendaMasterDto) {
        this.couAgendaMasterDto = couAgendaMasterDto;
    }

    public List<CouncilAgendaMasterDto> getCouAgendaMasterDtoList() {
        return couAgendaMasterDtoList;
    }

    public void setCouAgendaMasterDtoList(List<CouncilAgendaMasterDto> couAgendaMasterDtoList) {
        this.couAgendaMasterDtoList = couAgendaMasterDtoList;
    }

    public CouncilRestResponseDto getResponse() {
        return response;
    }

    public void setResponse(CouncilRestResponseDto response) {
        this.response = response;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }
    
    

    public List<LookUp> getCommitteeTypeFilterList() {
		return committeeTypeFilterList;
	}

	public void setCommitteeTypeFilterList(List<LookUp> committeeTypeFilterList) {
		this.committeeTypeFilterList = committeeTypeFilterList;
	}

	public List<CouncilProposalMasterDto> getProposalDtoList() {
        return proposalDtoList;
    }

    public void setProposalDtoList(List<CouncilProposalMasterDto> proposalDtoList) {
        this.proposalDtoList = proposalDtoList;
    }

    public boolean isProposalPresent() {
        return proposalPresent;
    }

    public void setProposalPresent(boolean proposalPresent) {
        this.proposalPresent = proposalPresent;
    }

    public List<CouncilProposalMasterDto> getAgendaProposalDtoList() {
        return agendaProposalDtoList;
    }

    public void setAgendaProposalDtoList(List<CouncilProposalMasterDto> agendaProposalDtoList) {
        this.agendaProposalDtoList = agendaProposalDtoList;
    }

	public List<TbDepartment> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<TbDepartment> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public boolean saveForm() {
        long committeeTypeId = this.couAgendaMasterDto.getCommitteeTypeId();
        Long agendaId = this.couAgendaMasterDto.getAgendaId();
        if (agendaId != null) {
            // update agenda
            couAgendaMasterDto.setCommitteeTypeId(committeeTypeId);
            // couAgendaMasterDto.setAgendaStatus("Y");
            couAgendaMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            couAgendaMasterDto.setUpdatedDate(new Date());
            couAgendaMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            agendaMasterService.saveCouncilAgenda(couAgendaMasterDto);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("council.agenda.updatesuccessmsg"));
        } else {
            // saveCouncilAgenda
            // set committeeType Id here
            couAgendaMasterDto.setCommitteeTypeId(committeeTypeId);
            couAgendaMasterDto.setAgendaStatus(MainetConstants.Council.Agenda.AGENDA_STATUS_YES);
            couAgendaMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            couAgendaMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            couAgendaMasterDto.setCreatedDate(new Date());
            couAgendaMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            agendaMasterService.saveCouncilAgenda(couAgendaMasterDto);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("council.agenda.savesuccessmsg"));
        }
        // update Subject No
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL))
        agendaMasterService.updateSubjectNo(this.agendaProposalDtoList);
        return true;

    }
}
