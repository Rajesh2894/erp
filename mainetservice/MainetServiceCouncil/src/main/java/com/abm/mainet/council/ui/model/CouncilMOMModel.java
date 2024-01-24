package com.abm.mainet.council.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.dto.MOMResolutionDto;
import com.abm.mainet.council.service.ICouncilMOMService;

@Component
@Scope("session")
public class CouncilMOMModel extends AbstractFormModel {

    private static final long serialVersionUID = 3757594842340215954L;
    private String saveMode;
    private Long orgId;
    private CouncilMeetingMasterDto couMeetingMasterDto = null;
    private List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = null;
    // for create mom need below dtos
    private MOMResolutionDto meetingMOMDto = null;
    private List<MOMResolutionDto> meetingMOMDtos = null;
    private List<TbDepartment> departmentList = null;
    private String removeSumotoIds;
    private Long proposalId; // identify which SUMOTO modify based on proposalId
    private Boolean disableSelect = false;
    // for document setup needed dtos
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private Long deleteFileId;
    private Long printMeetingId; // used for print MOM at CouncilMOMController
    private String resolutionComments;
    private String oMMFlag;
    
    private String removeFileById;

    @Autowired
    ICouncilMOMService momService;
    
    @Autowired
    private TbAcVendormasterService tbAcVendormasterService;

    public Boolean getDisableSelect() {
        return disableSelect;
    }

    public void setDisableSelect(Boolean disableSelect) {
        this.disableSelect = disableSelect;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public MOMResolutionDto getMeetingMOMDto() {
        return meetingMOMDto;
    }

    public void setMeetingMOMDto(MOMResolutionDto meetingMOMDto) {
        this.meetingMOMDto = meetingMOMDto;
    }

    public List<MOMResolutionDto> getMeetingMOMDtos() {
        return meetingMOMDtos;
    }

    public void setMeetingMOMDtos(List<MOMResolutionDto> meetingMOMDtos) {
        this.meetingMOMDtos = meetingMOMDtos;
    }

    public List<TbDepartment> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<TbDepartment> departmentList) {
        this.departmentList = departmentList;
    }

    public String getRemoveSumotoIds() {
        return removeSumotoIds;
    }

    public void setRemoveSumotoIds(String removeSumotoIds) {
        this.removeSumotoIds = removeSumotoIds;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
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

    public ICouncilMOMService getMomService() {
        return momService;
    }

    public void setMomService(ICouncilMOMService momService) {
        this.momService = momService;
    }

    public Long getDeleteFileId() {
        return deleteFileId;
    }

    public void setDeleteFileId(Long deleteFileId) {
        this.deleteFileId = deleteFileId;
    }

    public Long getPrintMeetingId() {
        return printMeetingId;
    }

    public void setPrintMeetingId(Long printMeetingId) {
        this.printMeetingId = printMeetingId;
    }

  
    public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}

	// create MOM
    public boolean saveForm() {
        // save in MOM table
        // document set
        try {
            FileUploadDTO uploadDTO = new FileUploadDTO();
            uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            uploadDTO.setStatus(MainetConstants.FlagA);
            uploadDTO.setDepartmentName(MainetConstants.Council.COUNCIL_MANAGEMENT);
            uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
    
            setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
                    .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
            removeUploadedFiles(this.getRemoveFileById());
            if (saveMode.equalsIgnoreCase(MainetConstants.CommonConstants.ADD)) {
                setSuccessMessage(ApplicationSession.getInstance().getMessage("council.mom.savesuccessmsg"));
            } else {
                setSuccessMessage(ApplicationSession.getInstance().getMessage("council.mom.updatesuccessmsg"));
            }
            if (StringUtils.isNotEmpty(resolutionComments)){
            	if(oMMFlag.equals(MainetConstants.FlagY)) {
            		for (MOMResolutionDto momResolutionDto : meetingMOMDtos) {
            		    momResolutionDto.setResolutionComments(resolutionComments);
            		    momResolutionDto.setStatus(MainetConstants.Council.Proposal.FIN_STATUS);;
            		}
            	}else {
            		this.getMeetingMOMDtos().get(0).setResolutionComments(resolutionComments);
            	}
			}
            printMeetingId = this.getMeetingMOMDtos().get(0).getMeetingId();// for PrintReport Handler purpose value set
            return momService.saveMeetingMOM(this.getMeetingMOMDtos(), getAttachments(), uploadDTO, deleteFileId,
                    UserSession.getCurrent().getEmployee());
        } catch (Exception e) {
            throw new FrameworkException("error when create MOM ", e);
        }
    }
    
    private void removeUploadedFiles(String removeFileByIds) {		
		List<Long> removeFileById = null;
		String fileId =this.getRemoveFileById();
		if (fileId != null && !fileId.isEmpty()) {
			removeFileById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				removeFileById.add(Long.valueOf(fields));
			}
		}
		if (removeFileById != null && !removeFileById.isEmpty()) {				
			tbAcVendormasterService.updateUploadedFileDeleteRecords(removeFileById, UserSession.getCurrent().getEmployee().getEmpId());
		}
	}

	public String getoMMFlag() {
		return oMMFlag;
	}

	public void setoMMFlag(String oMMFlag) {
		this.oMMFlag = oMMFlag;
	}
	
	public String getRemoveFileById() {
		return removeFileById;
	}

	public void setRemoveFileById(String removeFileById) {
		this.removeFileById = removeFileById;
	}
}
