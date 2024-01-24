package com.abm.mainet.council.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;
import com.abm.mainet.council.dto.CouncilMemberMasterDto;
import com.abm.mainet.council.service.ICouncilMemberCommitteeMasterService;

/**
 * @author israt.ali
 *
 */
@Component
@Scope("session")
public class CouncilMemberCommitteeMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = -6270033092206912011L;
    private String saveMode;
    private Long orgId;
    private CouncilMemberCommitteeMasterDto councilMemberCommitteeMasterDto = new CouncilMemberCommitteeMasterDto();
    private List<CouncilMemberCommitteeMasterDto> councilMemberCommitteeMasterDtoList = new ArrayList<>();
    private List<CouncilMemberMasterDto> couMemMasterDtoList = new ArrayList<>();
    private List<DesignationBean> designationList = new ArrayList<>();
    private String removedIds; // only purpose when edit member committee
    private List<CouncilMemberCommitteeMasterDto> committeeActiveMemberList = new ArrayList<>();

    @Autowired
    private ICouncilMemberCommitteeMasterService councilMemberCommitteeService;

    @Override
    public boolean saveForm() {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        boolean committeeTypePresent = false;
        // validation for member count based on other CPT prefix other value
        this.getCouncilMemberCommitteeMasterDtoList().forEach(memberList -> {
            if (memberList.getMemberStatus() == null) {
                committeeActiveMemberList.add(memberList);
            } else if (memberList.getMemberStatus().equals(MainetConstants.STATUS.ACTIVE)) {
                committeeActiveMemberList.add(memberList);
            }
        });
        int memberCount = this.getCouncilMemberCommitteeMasterDtoList().size();
        LookUp lookupCPT = CommonMasterUtility.getNonHierarchicalLookUpObject(
                councilMemberCommitteeMasterDto.getCommitteeTypeId(), UserSession.getCurrent().getOrganisation());
        if (lookupCPT.getOtherField().isEmpty()) {
            addValidationError(
                    getAppSession().getMessage("council.validation.memberCount"));
            logger.error("OtherField can not be null");
            return false;
        }
        int memberCPTCount = Integer.parseInt(lookupCPT.getOtherField());
        if (memberCount > memberCPTCount) {
            addValidationError(
                    getAppSession().getMessage("council.committee.member.count.validate"));
        }
        if (hasValidationErrors()) {
            return false;
        }

        List<Long> removeIds = new ArrayList<>();
        if (removedIds != null && !removedIds.isEmpty()) {
            String array[] = removedIds.split(MainetConstants.operator.COMMA);
            for (String id : array) {
                removeIds.add(Long.valueOf(id));
            }
        }

        getCouncilMemberCommitteeMasterDto().setOrgId(orgId);
        Long memberCommmitteeId = this.councilMemberCommitteeMasterDto.getMemberCommmitteeId();

        if (memberCommmitteeId != null) {
            councilMemberCommitteeMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            councilMemberCommitteeMasterDto.setUpdatedDate(new Date());
            councilMemberCommitteeMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            if (councilMemberCommitteeMasterDto.getExpiryDate() != null
                    && councilMemberCommitteeMasterDto.getExpiryReason() != null) {
                councilMemberCommitteeMasterDto.setMemberStatus(MainetConstants.STATUS.INACTIVE);
            }
            councilMemberCommitteeService.saveCouncilMemberCommitteeMaster(councilMemberCommitteeMasterDto,
                    this.councilMemberCommitteeMasterDtoList, removeIds);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("council.committeeMapping.updatesuccessmsg"));

        } else {
            // 1st check this committeeType already exist in case of save only because at edit time committeeType disable
            // CouncilMemberCommitteeMasterDto committeeMember = councilMemberCommitteeService.getCommitteeMemberData();
            Boolean committeeAlreadyExist = councilMemberCommitteeService.checkCommitteeTypeInDissolveDateByOrg(
                    councilMemberCommitteeMasterDto.getCommitteeTypeId(), orgId, MainetConstants.Council.ACTIVE_STATUS);
            if (committeeAlreadyExist) {
                this.setCouncilMemberCommitteeMasterDtoList(null);
                committeeTypePresent = true;
                addValidationError(getAppSession().getMessage("council.committeeMapping.validation.alreadyPresent"));
            }

            if (committeeTypePresent || hasValidationErrors()) {
                return false;
            }

            // insertion
            councilMemberCommitteeMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            councilMemberCommitteeMasterDto.setCreatedDate(new Date());
            councilMemberCommitteeMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            councilMemberCommitteeMasterDto.setStatus(MainetConstants.Council.ACTIVE_STATUS);
            councilMemberCommitteeMasterDto.setMemberStatus(MainetConstants.STATUS.ACTIVE);
            councilMemberCommitteeService.saveCouncilMemberCommitteeMaster(councilMemberCommitteeMasterDto,
                    this.councilMemberCommitteeMasterDtoList, null);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("council.committeeMapping.savesuccessmsg"));
        }
        return true;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<CouncilMemberCommitteeMasterDto> getCouncilMemberCommitteeMasterDtoList() {
        return councilMemberCommitteeMasterDtoList;
    }

    public void setCouncilMemberCommitteeMasterDtoList(
            List<CouncilMemberCommitteeMasterDto> councilMemberCommitteeMasterDtoList) {
        this.councilMemberCommitteeMasterDtoList = councilMemberCommitteeMasterDtoList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public CouncilMemberCommitteeMasterDto getCouncilMemberCommitteeMasterDto() {
        return councilMemberCommitteeMasterDto;
    }

    public void setCouncilMemberCommitteeMasterDto(CouncilMemberCommitteeMasterDto councilMemberCommitteeMasterDto) {
        this.councilMemberCommitteeMasterDto = councilMemberCommitteeMasterDto;
    }

    public List<CouncilMemberMasterDto> getCouMemMasterDtoList() {
        return couMemMasterDtoList;
    }

    public void setCouMemMasterDtoList(List<CouncilMemberMasterDto> couMemMasterDtoList) {
        this.couMemMasterDtoList = couMemMasterDtoList;
    }

    public List<DesignationBean> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<DesignationBean> designationList) {
        this.designationList = designationList;
    }

    public String getRemovedIds() {
        return removedIds;
    }

    public void setRemovedIds(String removedIds) {
        this.removedIds = removedIds;
    }

    public List<CouncilMemberCommitteeMasterDto> getCommitteeActiveMemberList() {
        return committeeActiveMemberList;
    }

    public void setCommitteeActiveMemberList(List<CouncilMemberCommitteeMasterDto> committeeActiveMemberList) {
        this.committeeActiveMemberList = committeeActiveMemberList;
    }

}
