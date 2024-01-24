package com.abm.mainet.workManagement.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.CommonProposalDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.ui.validator.WorkDefinitionValidator;

/**
 * Object: this model is used for work definition master.
 * 
 * @author hiren.poriya
 * @Since 08-Feb-2018
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class WorkDefinitionModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    private WorkDefinitionDto wmsDto;
    private String formMode;
    private List<WorkDefinitionDto> defDtoList = new ArrayList<>();
    private String removeAssetIds;
    private String removeYearIds;
    private String removeFileById;
    private String removeSancDetId;
    private String removeWardZoneDetId;
    private List<TbDepartment> departmentsList; 
    private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();
    private List<TbLocationMas> locList = new ArrayList<>();
    private List<Long> fileCountUpload;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private String cpdMode;
    private List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList = new ArrayList<>();
    private List<TbFinancialyear> faYears = new ArrayList<>();
    private List<TbServicesMst> serviceMasterList = new ArrayList<>();
    private List<TbDepartment> sanctionDeptsList = new ArrayList<>();
    private WorkDefinitionDto workDefinitionDto = new WorkDefinitionDto();
    private List<CommonProposalDTO> commonproposalList = new ArrayList<>();
    private WorkDefinitionDto definitionDto = new WorkDefinitionDto();
    private String gisValue;
    private String gISUri;
    private String workStatus;
    private String workBackHandle;

    @Override
    public boolean saveForm() {
        wmsDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
      
        validateBean(wmsDto, WorkDefinitionValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        List<DocumentDetailsVO> dto = getAttachments();
        setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
                .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            getAttachments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }

        if (formMode.equals(MainetConstants.MODE_CREATE)) {
            wmsDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            wmsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            WorkDefinitionDto savedDto = ApplicationContextProvider.getApplicationContext().getBean(WorkDefinitionService.class)
                    .createWorkDefinition(wmsDto, getAttachments(), requestDTO);
            setWorkDefinitionDto(savedDto);

            setSuccessMessage(
                    getAppSession().getMessage("work.Def.create.success") + " " + getAppSession().getMessage("work.def.code")
                            + " " + savedDto.getWorkcode());
        } else {
            List<Long> removeAssetIdList = getRemovedAssetIdAsList();
            List<Long> removeYearIdList = getRemovedYearIdAsList();
            List<Long> removeDocIdList = getRemovedDocIdAsList();
            List<Long> removeScanIdList = getRemoveSanctionDetails();
            List<Long> removeWarZoneIdList = getRemoveWardZoneDetails();
            wmsDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            wmsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            wmsDto.setWorkId(wmsDto.getWorkId());

            ApplicationContextProvider.getApplicationContext().getBean(WorkDefinitionService.class).updateWorkDefinition(wmsDto,
                    getAttachments(), requestDTO, removeAssetIdList, removeYearIdList, removeDocIdList, removeScanIdList,
                    removeWarZoneIdList);
            setWorkDefinitionDto(wmsDto);

            setSuccessMessage(getAppSession().getMessage("work.Def.update.success"));
        }
        return true;
    }

    // deleted documents id as list
    private List<Long> getRemovedDocIdAsList() {
        List<Long> removeDocIdList = null;
        String docIds = getRemoveFileById();
        if (docIds != null && !docIds.isEmpty()) {
            removeDocIdList = new ArrayList<>();
            String docArray[] = docIds.split(MainetConstants.operator.COMMA);
            for (String docId : docArray) {
                removeDocIdList.add(Long.valueOf(docId));
            }
        }
        return removeDocIdList;
    }

    // deleted documents financial year id as list
    private List<Long> getRemovedYearIdAsList() {
        List<Long> removeYearIdList = null;
        String yearIds = getRemoveYearIds();
        if (yearIds != null && !yearIds.isEmpty()) {
            removeYearIdList = new ArrayList<>();
            String yearArray[] = yearIds.split(MainetConstants.operator.COMMA);
            for (String yearId : yearArray) {
                removeYearIdList.add(Long.valueOf(yearId));
            }
        }
        return removeYearIdList;
    }

    // deleted documents asset id as list
    private List<Long> getRemovedAssetIdAsList() {
        List<Long> removeAssetIdList = null;
        String assetIds = getRemoveAssetIds();
        if (assetIds != null && !assetIds.isEmpty()) {
            removeAssetIdList = new ArrayList<>();
            String assetArray[] = assetIds.split(MainetConstants.operator.COMMA);
            for (String assetId : assetArray) {
                removeAssetIdList.add(Long.valueOf(assetId));
            }
        }
        return removeAssetIdList;
    }

    private List<Long> getRemoveSanctionDetails() {
        List<Long> removeSancDetIdList = null;
        String sancIds = getRemoveSancDetId();
        if (sancIds != null && !sancIds.isEmpty()) {
            removeSancDetIdList = new ArrayList<>();
            String sancArray[] = sancIds.split(MainetConstants.operator.COMMA);
            for (String scanId : sancArray) {
                removeSancDetIdList.add(Long.valueOf(scanId));
            }
        }
        return removeSancDetIdList;

    }

    private List<Long> getRemoveWardZoneDetails() {
        List<Long> removeWardZoneDetIdList = null;
        String wardId = getRemoveWardZoneDetId();
        if (wardId != null && !wardId.isEmpty()) {
            removeWardZoneDetIdList = new ArrayList<>();
            String sancArray[] = wardId.split(MainetConstants.operator.COMMA);
            for (String wardZoneId : sancArray) {
                removeWardZoneDetIdList.add(Long.valueOf(wardZoneId));
            }
        }
        return removeWardZoneDetIdList;

    }

    public WorkDefinitionDto getWmsDto() {
        return wmsDto;
    }

    public void setWmsDto(WorkDefinitionDto wmsDto) {
        this.wmsDto = wmsDto;
    }

    public List<TbDepartment> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<TbDepartment> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public List<Long> getFileCountUpload() {
        return fileCountUpload;
    }

    public void setFileCountUpload(List<Long> fileCountUpload) {
        this.fileCountUpload = fileCountUpload;
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

    public String getRemoveFileById() {
        return removeFileById;
    }

    public void setRemoveFileById(String removeFileById) {
        this.removeFileById = removeFileById;
    }

    public List<WmsProjectMasterDto> getProjectMasterList() {
        return projectMasterList;
    }

    public void setProjectMasterList(List<WmsProjectMasterDto> projectMasterList) {
        this.projectMasterList = projectMasterList;
    }

    public List<TbLocationMas> getLocList() {
        return locList;
    }

    public void setLocList(List<TbLocationMas> locList) {
        this.locList = locList;
    }

    public String getCpdMode() {
        return cpdMode;
    }

    public void setCpdMode(String cpdMode) {
        this.cpdMode = cpdMode;
    }

    public List<AccountHeadSecondaryAccountCodeMasterEntity> getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList) {
        this.budgetList = budgetList;
    }

    public String getFormMode() {
        return formMode;
    }

    public void setFormMode(String formMode) {
        this.formMode = formMode;
    }

    public List<TbFinancialyear> getFaYears() {
        return faYears;
    }

    public void setFaYears(List<TbFinancialyear> faYears) {
        this.faYears = faYears;
    }

    public List<WorkDefinitionDto> getDefDtoList() {
        return defDtoList;
    }

    public void setDefDtoList(List<WorkDefinitionDto> defDtoList) {
        this.defDtoList = defDtoList;
    }

    public String getRemoveAssetIds() {
        return removeAssetIds;
    }

    public void setRemoveAssetIds(String removeAssetIds) {
        this.removeAssetIds = removeAssetIds;
    }

    public String getRemoveYearIds() {
        return removeYearIds;
    }

    public void setRemoveYearIds(String removeYearIds) {
        this.removeYearIds = removeYearIds;
    }

    public String getRemoveSancDetId() {
        return removeSancDetId;
    }

    public void setRemoveSancDetId(String removeSancDetId) {
        this.removeSancDetId = removeSancDetId;
    }

    public List<TbServicesMst> getServiceMasterList() {
        return serviceMasterList;
    }

    public void setServiceMasterList(List<TbServicesMst> serviceMasterList) {
        this.serviceMasterList = serviceMasterList;
    }

    public List<TbDepartment> getSanctionDeptsList() {
        return sanctionDeptsList;
    }

    public void setSanctionDeptsList(List<TbDepartment> sanctionDeptsList) {
        this.sanctionDeptsList = sanctionDeptsList;
    }

    public WorkDefinitionDto getWorkDefinitionDto() {
        return workDefinitionDto;
    }

    public void setWorkDefinitionDto(WorkDefinitionDto workDefinitionDto) {
        this.workDefinitionDto = workDefinitionDto;
    }

    public String getRemoveWardZoneDetId() {
        return removeWardZoneDetId;
    }

    public void setRemoveWardZoneDetId(String removeWardZoneDetId) {
        this.removeWardZoneDetId = removeWardZoneDetId;
    }

    public List<CommonProposalDTO> getCommonproposalList() {
        return commonproposalList;
    }

    public void setCommonproposalList(List<CommonProposalDTO> commonproposalList) {
        this.commonproposalList = commonproposalList;
    }

    public WorkDefinitionDto getDefinitionDto() {
        return definitionDto;
    }

    public void setDefinitionDto(WorkDefinitionDto definitionDto) {
        this.definitionDto = definitionDto;
    }

	public String getGisValue() {
		return gisValue;
	}

	public void setGisValue(String gisValue) {
		this.gisValue = gisValue;
	}

	public String getgISUri() {
		return gISUri;
	}

	public void setgISUri(String gISUri) {
		this.gISUri = gISUri;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getWorkBackHandle() {
		return workBackHandle;
	}

	public void setWorkBackHandle(String workBackHandle) {
		this.workBackHandle = workBackHandle;
	}
	
	

}
