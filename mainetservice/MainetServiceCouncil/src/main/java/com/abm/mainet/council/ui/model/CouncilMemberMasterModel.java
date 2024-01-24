package com.abm.mainet.council.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.council.domain.CouncilMemberMasterEntity;
import com.abm.mainet.council.dto.CouncilMemberMasterDto;
import com.abm.mainet.council.dto.CouncilRestResponseDto;
import com.abm.mainet.council.repository.CouncilMemberMasterRepository;
import com.abm.mainet.council.service.ICouncilMemberMasterService;
import com.abm.mainet.council.ui.validator.MemberValidator;

/**
 * @author aarti.paan
 *
 */
@Component
@Scope("session")
public class CouncilMemberMasterModel extends AbstractFormModel {
    private static final long serialVersionUID = -1250602655307212504L;
    private CouncilMemberMasterDto couMemMasterDto = new CouncilMemberMasterDto();
    private List<DesignationBean> designationList = new ArrayList<>();
    private List<CouncilMemberMasterDto> couMemMasterDtoList = new ArrayList<>();
    private CouncilRestResponseDto response = new CouncilRestResponseDto();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private Long orgId;
    private String saveMode;
    private Long deleteFileId;
    private String parentOrgFlag;
    @Autowired
    private ICouncilMemberMasterService councilMemberMasterService;

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    CouncilMemberMasterRepository councilMemberMasterRepository;

    @Override
    public boolean saveForm() {

        validateBean(this, MemberValidator.class);

        if (hasValidationErrors()) {
            return false;
        }

        FileUploadDTO uploadDTO = new FileUploadDTO();
        uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        uploadDTO.setStatus(MainetConstants.FlagA);
        uploadDTO.setDepartmentName(MainetConstants.Council.COUNCIL_MANAGEMENT);
        uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

        setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
                .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

        // Duplicate Member Master Validation based on DOB and Mob No.
        Boolean memberPresent = false;
        if (getCouMemMasterDto().getCreatedBy() == null) {
            List<CouncilMemberMasterEntity> members = councilMemberMasterRepository.validateMemberDoB(
                    couMemMasterDto.getCouMemName(),
                    couMemMasterDto.getCouDOB(), couMemMasterDto.getCouMobNo());
            if (!members.isEmpty()) {
                memberPresent = true;
                addValidationError(getAppSession().getMessage("council.member.duplication.validation"));
            }
        } else {
            // update memebrs
            List<CouncilMemberMasterEntity> members = councilMemberMasterRepository.checkMemebrExist(
                    couMemMasterDto.getCouMemName(),
                    couMemMasterDto.getCouDOB(), couMemMasterDto.getCouMobNo(), couMemMasterDto.getCouId());
            if (!members.isEmpty()) {
                memberPresent = true;
                addValidationError(getAppSession().getMessage("council.member.duplication.validation"));
            }
        }

        if (memberPresent || hasValidationErrors()) {
            return false;
        }

        // Calling Member Master Save Service
        getCouMemMasterDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        if (getCouMemMasterDto().getCreatedBy() == null) {
            couMemMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            couMemMasterDto.setCreatedDate(new Date());
            couMemMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            councilMemberMasterService.saveCouncil(couMemMasterDto, getAttachments(), uploadDTO, null);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("council.member.savesuccessmsg"));

        } else {
            couMemMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            couMemMasterDto.setUpdatedDate(new Date());
            couMemMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            councilMemberMasterService.saveCouncil(couMemMasterDto, getAttachments(), uploadDTO, deleteFileId);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("council.member.updatesuccessmsg"));
        }
        return true;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public CouncilMemberMasterDto getCouMemMasterDto() {
        return couMemMasterDto;
    }

    public void setCouMemMasterDto(CouncilMemberMasterDto couMemMasterDto) {
        this.couMemMasterDto = couMemMasterDto;
    }

    public List<DesignationBean> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<DesignationBean> designationList) {
        this.designationList = designationList;
    }

    public List<CouncilMemberMasterDto> getCouMemMasterDtoList() {
        return couMemMasterDtoList;
    }

    public void setCouMemMasterDtoList(List<CouncilMemberMasterDto> couMemMasterDtoList) {
        this.couMemMasterDtoList = couMemMasterDtoList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public CouncilRestResponseDto getResponse() {
        return response;
    }

    public void setResponse(CouncilRestResponseDto response) {
        this.response = response;
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

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

	public String getParentOrgFlag() {
		return parentOrgFlag;
	}

	public void setParentOrgFlag(String parentOrgFlag) {
		this.parentOrgFlag = parentOrgFlag;
	}

  
}
