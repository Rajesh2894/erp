package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.workManagement.dto.ApprovalTermsConditionDto;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;

@Component
@Scope("session")
public class ApprovalLetterPrintModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();
    private List<LookUp> reportTypeLookUp = new ArrayList<>();
    private WorkEstimateMasterDto workEstimateMasterDto;
    private List<WorkDefinitionDto> workDefinitionDto = new ArrayList<WorkDefinitionDto>();
    private WmsProjectMasterDto projectMasterDto;
    private WorkDefinitionDto definitionDto = new WorkDefinitionDto();
    private ScheduleOfRateMstDto scheduleOfRateMstDto;
    private List<ApprovalTermsConditionDto> approvalTermsConditionDto = new ArrayList<>();
    private Long projId;
    private Long orgId;
    private String deptName;
    private String workEstimAmt;
    private String saveMode;
    private Long parentOrgId;
    private String orgRegionalName;

    public List<WmsProjectMasterDto> getProjectMasterList() {
        return projectMasterList;
    }

    public void setProjectMasterList(List<WmsProjectMasterDto> projectMasterList) {
        this.projectMasterList = projectMasterList;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public WorkEstimateMasterDto getWorkEstimateMasterDto() {
        return workEstimateMasterDto;
    }

    public void setWorkEstimateMasterDto(WorkEstimateMasterDto workEstimateMasterDto) {
        this.workEstimateMasterDto = workEstimateMasterDto;
    }

    public List<LookUp> getReportTypeLookUp() {
        return reportTypeLookUp;
    }

    public void setReportTypeLookUp(List<LookUp> reportTypeLookUp) {
        this.reportTypeLookUp = reportTypeLookUp;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<WorkDefinitionDto> getWorkDefinitionDto() {
        return workDefinitionDto;
    }

    public void setWorkDefinitionDto(List<WorkDefinitionDto> workDefinitionDto) {
        this.workDefinitionDto = workDefinitionDto;
    }

    public WmsProjectMasterDto getProjectMasterDto() {
        return projectMasterDto;
    }

    public void setProjectMasterDto(WmsProjectMasterDto projectMasterDto) {
        this.projectMasterDto = projectMasterDto;
    }

    public WorkDefinitionDto getDefinitionDto() {
        return definitionDto;
    }

    public void setDefinitionDto(WorkDefinitionDto definitionDto) {
        this.definitionDto = definitionDto;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getWorkEstimAmt() {
        return workEstimAmt;
    }

    public void setWorkEstimAmt(String workEstimAmt) {
        this.workEstimAmt = workEstimAmt;
    }

    public ScheduleOfRateMstDto getScheduleOfRateMstDto() {
        return scheduleOfRateMstDto;
    }

    public void setScheduleOfRateMstDto(ScheduleOfRateMstDto scheduleOfRateMstDto) {
        this.scheduleOfRateMstDto = scheduleOfRateMstDto;
    }

    public List<ApprovalTermsConditionDto> getApprovalTermsConditionDto() {
        return approvalTermsConditionDto;
    }

    public void setApprovalTermsConditionDto(List<ApprovalTermsConditionDto> approvalTermsConditionDto) {
        this.approvalTermsConditionDto = approvalTermsConditionDto;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public String getOrgRegionalName() {
        return orgRegionalName;
    }

    public void setOrgRegionalName(String orgRegionalName) {
        this.orgRegionalName = orgRegionalName;
    }

}
