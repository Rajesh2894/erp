/**
 * 
 */
package com.abm.mainet.workManagement.ui.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.service.MeasurementBookService;
import com.abm.mainet.workManagement.service.TenderInitiationService;

/**
 * @author Saiprasad.Vengurekar
 *
 */

@Component
@Scope("session")
public class LegacyMeasurementBookModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 8221490178861216015L;

    @Autowired
    MeasurementBookService mbService;

    @Resource
    IFileUploadService fileUpload;

    @Autowired
    private TenderInitiationService tenderService;

    String saveMode;
    private List<WorkOrderDto> workOrderDtoList = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<MeasurementBookMasterDto> mbList = new ArrayList<>();
    private WorkOrderDto workOrderDto = new WorkOrderDto();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private MeasurementBookMasterDto mbMasDto = new MeasurementBookMasterDto();
    private Long length;

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public List<WorkOrderDto> getWorkOrderDtoList() {
        return workOrderDtoList;
    }

    public void setWorkOrderDtoList(List<WorkOrderDto> workOrderDtoList) {
        this.workOrderDtoList = workOrderDtoList;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<MeasurementBookMasterDto> getMbList() {
        return mbList;
    }

    public void setMbList(List<MeasurementBookMasterDto> mbList) {
        this.mbList = mbList;
    }

    public WorkOrderDto getWorkOrderDto() {
        return workOrderDto;
    }

    public void setWorkOrderDto(WorkOrderDto workOrderDto) {
        this.workOrderDto = workOrderDto;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Override
    public boolean saveForm() {
        Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        Date todayDate = new Date();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        TenderMasterDto tenderMasterDto = null;
        if (!validateList(mbList)) {
            return false;
        }
        if (getSaveMode().equals(MainetConstants.FlagC)) {
            Long contractId = getWorkOrderDto().getContractMastDTO().getContId();
            TenderWorkDto tenderWorkDto = tenderService.findWorkByWorkId(contractId);
            tenderMasterDto = tenderService.getPreparedTenderDetails(tenderWorkDto.getTndId());
        }
        for (MeasurementBookMasterDto dto : mbList) {
            if (dto.getCreatedBy() == null) {
                dto.setOrgId(orgId);
                dto.setCreatedBy(empId);
                dto.setCreatedDate(todayDate);
                dto.setWorkOrId(getWorkOrderDto().getWorkId());
                dto.setMbStatus(MainetConstants.FlagD);
                if (tenderMasterDto != null)
                    dto.setWorkMbNo(mbService.generateAndUpdateMbNumber(getWorkOrderDto().getWorkId(),
                            UserSession.getCurrent().getOrganisation().getOrgid(), tenderMasterDto.getDeptId()));
                dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            } else {
                dto.setUpdatedBy(empId);
                dto.setWorkOrId(getWorkOrderDto().getWorkId());
                dto.setUpdatedDate(todayDate);
                dto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            }
        }
        setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
        if (getSaveMode().equals(MainetConstants.FlagC)) {
            ApplicationContextProvider.getApplicationContext().getBean(MeasurementBookService.class).saveLegacyData(mbList,
                    getAttachments());
            setSuccessMessage(ApplicationSession.getInstance().getMessage("leadlift.master.save"));
        } else {
            ApplicationContextProvider.getApplicationContext().getBean(MeasurementBookService.class).updateLegacyData(mbList,
                    getAttachments(),
                    getAttachDocsList());
            setSuccessMessage(ApplicationSession.getInstance().getMessage("leadlift.master.update"));
        }
        return true;
    }

    public boolean validateList(List<MeasurementBookMasterDto> detDtos) {
        boolean isCheck = true;
        for (int value = 0; value <= detDtos.size() - 1; value++) {
            if (detDtos.get(value).getOldMbNo() == null) {
                addValidationError(ApplicationSession.getInstance()
                        .getMessage("Please Enter Old Measurement Book No. For Entry No. ") + (value + 1));
                isCheck = false;
            }
            if (detDtos.get(value).getWorkMbTakenDate() == null) {
                addValidationError(ApplicationSession.getInstance()
                        .getMessage("Please Enter Actual Measurement Taken Date For Entry No. ") + (value + 1));
                isCheck = false;
            }
            if (detDtos.get(value).getMbTotalAmt() == null) {
                addValidationError(ApplicationSession.getInstance()
                        .getMessage("Please Enter Old Measurement Book Amount For Entry No. ") + (value + 1));
                isCheck = false;
            }

        }
        if (isCheck)
            isCheck = checkDocumentList();
        return isCheck;

    }

    public boolean checkDocumentList() {
        boolean flag = true;
        final List<DocumentDetailsVO> docList = fileUpload.prepareFileUpload(getAttachments());
        if ((docList != null) && !docList.isEmpty()) {
            for (final DocumentDetailsVO doc : docList) {
                if ((doc.getDocumentByteCode() == null) || doc.getDocumentByteCode().isEmpty()) {
                    addValidationError(
                            ApplicationSession.getInstance().getMessage("Please Upload Measurement Book Documents"));
                    flag = false;
                }
            }
        }
        return flag;
    }

    public Map<Long, Set<File>> getUploadedFileList(List<AttachDocs> attachDocs,
            FileNetApplicationClient fileNetApplicationClient) {
        Set<File> fileList = null;
        Long x = 0L;
        Map<Long, Set<File>> fileMap = new HashMap<>();
        for (AttachDocs doc : attachDocs) {
            fileList = new HashSet<>();
            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                    + MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
            String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();
            final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
                    existingPath);

            String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
                    existingPath);

            directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMMA);
            FileOutputStream fos = null;
            File file = null;
            try {
                final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

                Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);

                file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

                fos = new FileOutputStream(file);

                fos.write(image);

                fos.close();

            } catch (final Exception e) {
                throw new FrameworkException("Exception in getting getUploadedFileList", e);
            } finally {
                try {

                    if (fos != null) {
                        fos.close();
                    }

                } catch (final IOException e) {
                    throw new FrameworkException("Exception in getting getUploadedFileList", e);
                }
            }
            fileList.add(file);

        }
        fileMap.put(x, fileList);
        return fileMap;
    }

    public MeasurementBookMasterDto getMbMasDto() {
        return mbMasDto;
    }

    public void setMbMasDto(MeasurementBookMasterDto mbMasDto) {
        this.mbMasDto = mbMasDto;
    }

}
