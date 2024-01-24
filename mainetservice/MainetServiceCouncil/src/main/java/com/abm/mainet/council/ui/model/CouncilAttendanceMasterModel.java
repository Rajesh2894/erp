package com.abm.mainet.council.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;
import com.abm.mainet.council.service.ICouncilMeetingMasterService;

@Component
@Scope("session")
public class CouncilAttendanceMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 1784946896216971314L;
    private Long orgId;
    private String saveMode;
    private CouncilMeetingMasterDto couMeetingMasterDto = null;
    private List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = null;
    private List<CouncilMemberCommitteeMasterDto> memberList = new ArrayList<>();
  ;
    private Boolean disableSelect = false;
    // for document upload
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private Long deleteFileId;

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

    public CouncilMeetingMasterDto getCouMeetingMasterDto() {
        return couMeetingMasterDto;
    }

    public void setCouMeetingMasterDto(CouncilMeetingMasterDto couMeetingMasterDto) {
        this.couMeetingMasterDto = couMeetingMasterDto;
    }

    public List<CouncilMeetingMasterDto> getCouncilMeetingMasterDtoList() {
        return councilMeetingMasterDtoList;
    }

    public void setCouncilMeetingMasterDtoList(List<CouncilMeetingMasterDto> councilMeetingMasterDtoList) {
        this.councilMeetingMasterDtoList = councilMeetingMasterDtoList;
    }

    public List<CouncilMemberCommitteeMasterDto> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<CouncilMemberCommitteeMasterDto> memberList) {
        this.memberList = memberList;
    }

    public Boolean getDisableSelect() {
        return disableSelect;
    }

    public void setDisableSelect(Boolean disableSelect) {
        this.disableSelect = disableSelect;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public Long getDeleteFileId() {
        return deleteFileId;
    }

    public void setDeleteFileId(Long deleteFileId) {
        this.deleteFileId = deleteFileId;
    }
   
   


	@Autowired
    ICouncilMeetingMasterService councilMeetingMasterService;

    @Override
    public boolean saveForm() {
        FileUploadDTO uploadDTO = new FileUploadDTO();
        uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        uploadDTO.setStatus(MainetConstants.FlagA);
        uploadDTO.setDepartmentName(MainetConstants.Council.COUNCIL_MANAGEMENT);
        uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

        setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
                .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

        Long meetingId = this.couMeetingMasterDto.getMeetingId();
        String memberIdByCommitteeType = this.couMeetingMasterDto.getMemberIdByCommitteeType();
        String splitIds[] = memberIdByCommitteeType.split(MainetConstants.operator.COMMA);
        // make List of memberId long array
        List<Long> memberIds = new ArrayList<>();
        for (int i = 0; i < splitIds.length; i++) {
            memberIds.add(Long.valueOf(splitIds[i]));
        }

        // update in TB_CMT_COUNCIL_MEETING_MEMBER table
        councilMeetingMasterService.updateAttendanceStatusInMeetingMember(memberIds, meetingId, getAttachments(),
                uploadDTO, deleteFileId, couMeetingMasterDto.getOrgId(), couMeetingMasterDto.getLgIpMac());
        
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
        	councilMeetingMasterService.updateMeetingStatusWithMeetingID(meetingId, MainetConstants.FlagA);
        	councilMeetingMasterService.updateStatus(this.memberList);
        }
        
        if (saveMode.equalsIgnoreCase(MainetConstants.CommonConstants.ADD)) {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("council.attendance.savesuccessmsg"));
        } else {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("council.attendance.updatesuccessmsg"));
        }

        return true;

    }

}
