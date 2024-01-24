package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.SchemeMasterDTO;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.service.SchemeMasterService;
import com.abm.mainet.workManagement.ui.validator.SchemeMasterValidator;

/**
 * @author vishwajeet.kumar
 * @since 5 dec 2017
 */
@Component
@Scope("session")
public class SchemeMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 6743540000965355560L;

    @Autowired
    private SchemeMasterService schemeMasterService;

    @Autowired
    IFileUploadService fileUpload;

    private List<SchemeMasterDTO> schemeMasterList;
    private SchemeMasterDTO schemeMasterDTO;
    private List<LookUp> lookUps = new ArrayList<LookUp>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<WmsProjectMasterDto> projectMasterDtos;
    private Map<Long, String> fundList;
    private List<AttachDocs> attachDocsList;
    private String saveMode;
    private String removeFileById;
    private String removeChildIds;
    private String cpdMode;

    private List<LookUp> sourceLookUps = new ArrayList<LookUp>();
    private List<LookUp> schemeLookUps = new ArrayList<LookUp>();
    private String UADstatusForScheme;

    @Override
    public boolean saveForm() {
        boolean status = true;
        if (getSaveMode().equals(MainetConstants.FlagA)) {
            getSchemeMasterDTO().setWmSchCode(
                    schemeMasterService.generateSchemeCode(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        validateBean(schemeMasterDTO, SchemeMasterValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setIdfId(getSchemeMasterDTO().getWmSchCode());
        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

        setAttachments(fileUpload.setFileUploadMethod(getAttachments()));
        fileUpload.doMasterFileUpload(getAttachments(), requestDTO);
        prepareSchemeMasterEntity(getSchemeMasterDTO());
        return status;
    }

    public SchemeMasterDTO prepareSchemeMasterEntity(SchemeMasterDTO schemeMaster) {

        getSchemeMasterDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

        if (getSaveMode().equals(MainetConstants.WorksManagement.EDIT)) {

            List<Long> removeIds = null;
            String ids = getRemoveChildIds();
            if (ids != null && !ids.isEmpty()) {
                removeIds = new ArrayList<>();
                String array[] = ids.split(MainetConstants.operator.COMMA);
                for (String id : array) {
                    removeIds.add(Long.valueOf(id));
                }
            }
            List<Long> removeFileById = null;
            String fileId = getRemoveFileById();
            if (fileId != null && !fileId.isEmpty()) {
                removeFileById = new ArrayList<>();
                String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
                for (String fields : fileArray) {
                    removeFileById.add(Long.valueOf(fields));
                }
            }
            schemeMaster.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            schemeMaster.setUpdatedDate(new Date());
            schemeMaster.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            schemeMasterService.updateSchemeMaster(schemeMaster, removeIds, removeFileById);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("scheme.master.updation.success"));

        } else {
            schemeMaster.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            schemeMaster.setCreatedDate(new Date());
            schemeMaster.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            schemeMasterService.saveSchemeMaster(schemeMaster);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("scheme.master.creation.success"));

        }
        return schemeMasterDTO;
    }

    /**
     * Integrate with account fund master to get fund list
     */

    public void fundList() throws Exception {

        TbOrganisationService organisationService = ApplicationContextProvider.getApplicationContext()
                .getBean(TbOrganisationService.class);
        final boolean isDafaultOrgExist = organisationService.defaultexist(MainetConstants.MASTER.Y);
        Long orgId = null;
        if (isDafaultOrgExist) {
            orgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        } else {
            orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        }
        AccountFundMasterService accountFundMasterService = ApplicationContextProvider.getApplicationContext()
                .getBean(AccountFundMasterService.class);
        setFundList(accountFundMasterService.getFundMasterLastLevels(orgId));
    }

    public Map<Long, String> getFundList() {
        return fundList;
    }

    public void setFundList(Map<Long, String> fundList) {
        this.fundList = fundList;
    }

    public SchemeMasterDTO getSchemeMasterDTO() {
        return schemeMasterDTO;
    }

    public void setSchemeMasterDTO(SchemeMasterDTO schemeMasterDTO) {
        this.schemeMasterDTO = schemeMasterDTO;
    }

    public List<SchemeMasterDTO> getSchemeMasterList() {
        return schemeMasterList;
    }

    public void setSchemeMasterList(List<SchemeMasterDTO> schemeMasterList) {
        this.schemeMasterList = schemeMasterList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<LookUp> getLookUps() {
        return lookUps;
    }

    public void setLookUps(List<LookUp> lookUps) {
        this.lookUps = lookUps;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public String getRemoveChildIds() {
        return removeChildIds;
    }

    public void setRemoveChildIds(String removeChildIds) {
        this.removeChildIds = removeChildIds;
    }

    public List<WmsProjectMasterDto> getProjectMasterDtos() {
        return projectMasterDtos;
    }

    public void setProjectMasterDtos(List<WmsProjectMasterDto> projectMasterDtos) {
        this.projectMasterDtos = projectMasterDtos;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public String getRemoveFileById() {
        return removeFileById;
    }

    public void setRemoveFileById(String removeFileById) {
        this.removeFileById = removeFileById;
    }

    public String getCpdMode() {
        return cpdMode;
    }

    public void setCpdMode(String cpdMode) {
        this.cpdMode = cpdMode;
    }

    public List<LookUp> getSourceLookUps() {
        return sourceLookUps;
    }

    public void setSourceLookUps(List<LookUp> sourceLookUps) {
        this.sourceLookUps = sourceLookUps;
    }

    public List<LookUp> getSchemeLookUps() {
        return schemeLookUps;
    }

    public void setSchemeLookUps(List<LookUp> schemeLookUps) {
        this.schemeLookUps = schemeLookUps;
    }

    public String getUADstatusForScheme() {
        return UADstatusForScheme;
    }

    public void setUADstatusForScheme(String uADstatusForScheme) {
        UADstatusForScheme = uADstatusForScheme;
    }

}
