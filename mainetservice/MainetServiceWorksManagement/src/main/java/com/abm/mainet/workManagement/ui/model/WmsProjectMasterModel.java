package com.abm.mainet.workManagement.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.SchemeMasterDTO;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;

@Component
@Scope("session")
public class WmsProjectMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 5978055501693341230L;

    @Resource
    IFileUploadService fileUpload;

    private WmsProjectMasterDto wmsProjectMasterDto;
    private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();
    private List<TbDepartment> departmentsList;
    private Map<String, Long> depeartmentList;
    private String saveMode;
    private List<SchemeMasterDTO> schemeMasterDtoList;
    private List<Long> fileCountUpload;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private String removeFileById;
    private List<Long> removeFileByIdVal = null;
    private List<WorkDefinitionDto> workDefDtoList = new ArrayList<>();
    private List<LookUp> sourceLookUps = new ArrayList<LookUp>();
    private List<LookUp> schemeLookUps = new ArrayList<LookUp>();
    private String UADstatusForProject;
    private List<TbFinancialyear> faYears = new ArrayList<>();
    private String cpdMode;
    private List<AccountHeadSecondaryAccountCodeMasterEntity> budgetList = new ArrayList<>();
    private Map<Long, String> budgetMap = new HashMap<>();
    private String removeYearIds;

    @Override
    public boolean saveForm() {
        boolean status = true;
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        requestDTO.setIdfId(getWmsProjectMasterDto().getProjCode());
        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        List<DocumentDetailsVO> dto = getAttachments();
        List<Long> removeYearIdList = getRemovedYearIdAsList();
        setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
        
        if(StringUtils.isBlank(wmsProjectMasterDto.getProjNameEng())) {
        	this.addValidationError(getAppSession()
		                    .getMessage(ApplicationSession.getInstance().getMessage("project.master.vldn.projectname")));
        }
        
        if(StringUtils.isBlank(wmsProjectMasterDto.getProjNameReg())) {   
        	this.addValidationError(getAppSession()
	                    .getMessage(ApplicationSession.getInstance().getMessage("project.master.vldn.projectname.reg")));
        }
        
        if(hasValidationErrors()) {
        	return false;
        }

        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            getAttachments().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }

        prepareProjectMasterEntity(getWmsProjectMasterDto());
        WmsProjectMasterDto dtoEntity = ApplicationContextProvider.getApplicationContext().getBean(WmsProjectMasterService.class)
                .saveUpdateProjectMaster(getWmsProjectMasterDto(), getRemoveFileByIdVal());
        if(removeYearIdList!=null && !removeYearIdList.isEmpty())
        {
        	ApplicationContextProvider.getApplicationContext().getBean(WmsProjectMasterService.class)
            .inactiveRemovedYearDetails(getWmsProjectMasterDto(), removeYearIdList);
        }
        requestDTO.setIdfId(dtoEntity.getProjCode());
        fileUpload.doMasterFileUpload(getAttachments(), requestDTO);

        setSuccessMessage(ApplicationSession.getInstance().getMessage("project.master.vldn.savesuccessmsg")
                + MainetConstants.operator.COMA + ApplicationSession.getInstance().getMessage("project.master.projcode")
                + MainetConstants.operator.COLON + dtoEntity.getProjCode());

        return status;

    }

    // used to set all others required database field
    public WmsProjectMasterDto prepareProjectMasterEntity(WmsProjectMasterDto projectMasterDto) {

        Date todayDate = new Date();

        getWmsProjectMasterDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        if (projectMasterDto.getCreatedBy() == null) {
            projectMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            projectMasterDto.setCreatedDate(todayDate);
            projectMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            projectMasterDto.setProjActive(MainetConstants.IsDeleted.DELETE);

        } else {
            projectMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            projectMasterDto.setUpdatedDate(todayDate);
            projectMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
        }

        String fileId = getRemoveFileById();
        if (fileId != null && !fileId.isEmpty()) {
            removeFileByIdVal = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                removeFileByIdVal.add(Long.valueOf(fields));
            }
        }

        return wmsProjectMasterDto;

    }

    public void prepareProjectMasterGridData(List<WmsProjectMasterDto> projectMasterList) {

        for (WmsProjectMasterDto wmsProjectMaster : projectMasterList) {
            for (TbDepartment tbDepartment : getDepartmentsList()) {
                if (tbDepartment.getDpDeptid().longValue() == wmsProjectMaster.getDpDeptId().longValue()) {
                    wmsProjectMaster.setDepartmentName(tbDepartment.getDpDeptdesc());
                }
            }
        }

    }
    
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

    public WmsProjectMasterDto getWmsProjectMasterDto() {
        return wmsProjectMasterDto;
    }

    public void setWmsProjectMasterDto(WmsProjectMasterDto wmsProjectMasterDto) {
        this.wmsProjectMasterDto = wmsProjectMasterDto;
    }

    public List<WmsProjectMasterDto> getProjectMasterList() {
        return projectMasterList;
    }

    public void setProjectMasterList(List<WmsProjectMasterDto> projectMasterList) {
        this.projectMasterList = projectMasterList;
    }

    public List<TbDepartment> getDepartmentsList() {
        return departmentsList;
    }

    public void setDepartmentsList(List<TbDepartment> departmentsList) {
        this.departmentsList = departmentsList;
    }

    public Map<String, Long> getDepeartmentList() {
        return depeartmentList;
    }

    public void setDepeartmentList(Map<String, Long> depeartmentList) {
        this.depeartmentList = depeartmentList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<SchemeMasterDTO> getSchemeMasterDtoList() {
        return schemeMasterDtoList;
    }

    public void setSchemeMasterDtoList(List<SchemeMasterDTO> schemeMasterDtoList) {
        this.schemeMasterDtoList = schemeMasterDtoList;
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

    public List<Long> getRemoveFileByIdVal() {
        return removeFileByIdVal;
    }

    public void setRemoveFileByIdVal(List<Long> removeFileByIdVal) {
        this.removeFileByIdVal = removeFileByIdVal;
    }

    public List<WorkDefinitionDto> getWorkDefDtoList() {
        return workDefDtoList;
    }

    public void setWorkDefDtoList(List<WorkDefinitionDto> workDefDtoList) {
        this.workDefDtoList = workDefDtoList;
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

    public String getUADstatusForProject() {
        return UADstatusForProject;
    }

    public void setUADstatusForProject(String uADstatusForProject) {
        UADstatusForProject = uADstatusForProject;
    }

	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
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

	public String getRemoveYearIds() {
		return removeYearIds;
	}

	public void setRemoveYearIds(String removeYearIds) {
		this.removeYearIds = removeYearIds;
	}

}
