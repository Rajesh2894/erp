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
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.MileStoneDTO;
import com.abm.mainet.workManagement.dto.MilestoneDetDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.service.MileStoneService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Component
@Scope("session")
public class MilestoneProgressModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3958139561718326796L;

    @Resource
    IFileUploadService fileUpload;

    private MileStoneDTO mileStoneDTO;
    private List<MileStoneDTO> milestoneList = new ArrayList<>();

    private List<MilestoneDetDto> progressList = new ArrayList<>();
    private Map<Long, List<String>> fileNames = new HashMap<>();
    private List<WmsProjectMasterDto> projectMasterList = new ArrayList<>();
    private Map<Integer, List<AttachDocs>> docMap = new HashMap<>();
    private List<Long> fileCountUpload;
    private Long length;
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private String removeFileById;

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

    public MileStoneDTO getMileStoneDTO() {
        return mileStoneDTO;
    }

    public void setMileStoneDTO(MileStoneDTO mileStoneDTO) {
        this.mileStoneDTO = mileStoneDTO;
    }

    public Map<Integer, List<AttachDocs>> getDocMap() {
        return docMap;
    }

    public void setDocMap(Map<Integer, List<AttachDocs>> docMap) {
        this.docMap = docMap;
    }

    public List<MileStoneDTO> getMilestoneList() {
        return milestoneList;
    }

    public void setMilestoneList(List<MileStoneDTO> milestoneList) {
        this.milestoneList = milestoneList;
    }

    public List<MilestoneDetDto> getProgressList() {
        return progressList;
    }

    public void setProgressList(List<MilestoneDetDto> progressList) {
        this.progressList = progressList;
    }

    public Map<Long, List<String>> getFileNames() {
        return fileNames;
    }

    public void setFileNames(Map<Long, List<String>> fileNames) {
        this.fileNames = fileNames;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Override
    public boolean saveForm() {
        Long projId = mileStoneDTO.getProjId();
        Long workId = mileStoneDTO.getWorkId();
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        Date todayDate = new Date();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        for (MilestoneDetDto dto : progressList) {
            if (dto.getCreatedBy() == null) {
                dto.setOrgId(orgId);
                dto.setCreatedBy(empId);
                dto.setCreatedDate(todayDate);
                dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
                dto.setMileId(mileStoneDTO.getMileId());
            } else {
                dto.setUpdatedBy(empId);
                dto.setMileId(mileStoneDTO.getMileId());
                dto.setUpdatedDate(todayDate);
                dto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }
        }
        if (!validateList(progressList)) {
            return false;
        }
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setStatus(MainetConstants.FlagA);
        if (workId != null) {
            requestDTO
                    .setIdfId(ApplicationContextProvider.getApplicationContext().getBean(WorkDefinitionService.class)
                            .findAllWorkDefinitionById(workId)
                            .getWorkcode() + MainetConstants.WINDOWS_SLASH + mileStoneDTO.getMileId());
        } else {
            requestDTO
                    .setIdfId(ApplicationContextProvider.getApplicationContext().getBean(WmsProjectMasterService.class)
                            .getProjectMasterByProjId(projId)
                            .getProjCode() + MainetConstants.WINDOWS_SLASH + mileStoneDTO.getMileId());
        }
        requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            for (int value = 0; value <= entry.getValue().size() - 1; value++) {
                getAttachments().get(i)
                        .setDocumentSerialNo((progressList.get(entry.getKey().intValue()).getPhyPercent().longValue()));
                i++;
            }
        }
        ApplicationContextProvider.getApplicationContext().getBean(MileStoneService.class).saveAndUpdateMilestoneProgress(
                progressList,
                getAttachments(), requestDTO, getAttachDocsList(), orgId);
        setSuccessMessage(ApplicationSession.getInstance().getMessage("leadlift.master.save"));
        return true;
    }

    public boolean validateList(List<MilestoneDetDto> detDtos) {
        boolean isCheck = true;
        for (int value = 0; value <= detDtos.size() - 1; value++) {
            if (detDtos.get(value).getPhyPercent() == null) {
                addValidationError(
                        ApplicationSession.getInstance().getMessage("milestone.progress.phyPercent") + (value + 1));
                isCheck = false;
            }

            if (detDtos.get(value).getProUpdateDate() == null) {
                addValidationError(
                        ApplicationSession.getInstance().getMessage("milestone.progress.progressDate") + (value + 1));
                isCheck = false;
            }
            if (value > 0)
                if (detDtos.get(value).getPhyPercent().compareTo(detDtos.get(value - 1).getPhyPercent()) <= 0) {

                    addValidationError(
                            ApplicationSession.getInstance().getMessage("milestone.progress.presentProgressDate")
                                    + (value + 1));

                    isCheck = false;
                }

        }
        return isCheck;

    }

    public void getdataOfUploadedImage() {
        getFileNames().clear();
        List<String> fileNameList = null;
        Long count = 0L;
        Map<Long, List<String>> fileNames = new HashMap<>();
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                fileNameList = new ArrayList<>();
                for (final File file : entry.getValue()) {
                    String fileName = null;

                    try {
                        final String path = file.getPath().replace(MainetConstants.DOUBLE_BACK_SLACE,
                                MainetConstants.operator.FORWARD_SLACE);
                        fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }

                    fileNameList.add(fileName);
                }
                fileNames.put(count, fileNameList);
                count++;
            }
        }
        setFileNames(fileNames);
    }
}
