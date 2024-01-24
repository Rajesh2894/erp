package com.abm.mainet.swm.ui.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.dto.ReadExcelData;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.service.ILogBookReportService;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class LogBookReportUploadModel extends AbstractEntryFormModel<BaseEntity> implements Serializable {
    private static final long serialVersionUID = 6989416652627811979L;
    @Autowired
    private ILogBookReportService iLogBookReportService;
    private String uploadedFile = MainetConstants.BLANK;
    private AttachDocs attachDocs = new AttachDocs();
    List<Long> list = new ArrayList<>();
    private String docName;
    private Date attDate;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
    String date = formatter.format(new Date());
    private String directoryTree = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date
            + File.separator + UserSession.getCurrent().getEmployee().getEmpId() + Utility.getTimestamp();

    @SuppressWarnings("unchecked")
    @Override
    public boolean saveOrUpdateForm() {
        boolean status = false;
        String filePath = null;
        try {
            uploadedFile = uploadDocAndVerifyDoc(getFileNetClient(), directoryTree);
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                Set<File> list = entry.getValue();
                for (final File file : list) {
                    filePath = file.toString();
                    break;
                }
            }
            @SuppressWarnings("rawtypes")
            Class className = getClassName(attachDocs.getIdfId());
            if (className != null) {
                @SuppressWarnings("rawtypes")
                ReadExcelData data = new ReadExcelData<>(filePath, className);
                data.parseExcelList();
                if (data.getParseData().isEmpty()) {
                    addValidationError("Data cannot be read");
                } else {
                    status = iLogBookReportService.saveExcelData(className, data.getParseData(),
                            UserSession.getCurrent().getEmployee().getEmpId(), getAttachDocs().getAttDate(),
                            UserSession.getCurrent().getOrganisation().getOrgid(), this.getList());
                }
            }
            if (status) {
                AttachDocs attachment = new AttachDocs();
                if ((uploadedFile != null) && !uploadedFile.equals(MainetConstants.BLANK)) {
                    attachment.setAttFname(uploadedFile);
                    uploadedFile = directoryTree + File.separator + uploadedFile;
                    attachment.setAttPath(directoryTree);
                    attachment.setLgIpMac(getUserSession().getEmployee().getEmppiservername());
                    attachment.setOrgid(getUserSession().getOrganisation().getOrgid());
                    attachment.setStatus("A");
                    attachment.setAttDate(getAttachDocs().getAttDate());
                    attachment.setIdfId(getAttachDocs().getIdfId());
                    attachment.setAttDate(new Date());
                    attachment.setLmodate(new Date());
                    attachment.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                    iLogBookReportService.saveAttachment(attachment);
                }
            }
        } catch (final Exception e) {
            addValidationError(e.toString());
            logger.error(e);
        }
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("rawtypes")
    public Class getClassName(String idfId) {
        String cpdValue = CommonMasterUtility
                .getNonHierarchicalLookUpObject(Long.valueOf(idfId), UserSession.getCurrent().getOrganisation()).getLookUpCode();
        Class className = null;
        if (!cpdValue.isEmpty() && !cpdValue.equalsIgnoreCase("")) {
            String name = ApplicationSession.getInstance().getMessage("LBR." + cpdValue);
            try {
                className = Class.forName("com.abm.mainet.swm.domain." + name);
            } catch (ClassNotFoundException e) {
                logger.error(e);
            }
        }
        return className;

    }

    private String uploadDocAndVerifyDoc(final FileNetApplicationClient fileNetApplicationClient, final String directoryTree)
            throws Exception {
        String uploadFileName = StringUtils.EMPTY;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            final Iterator<File> setFilesItr = entry.getValue().iterator();
            while (setFilesItr.hasNext()) {
                final File file = setFilesItr.next();
                uploadFileName = uploadFileName + file.getName();
                final List<File> fileList = new ArrayList<>();
                fileList.add(file);
                fileNetApplicationClient.uploadFileList(fileList, directoryTree);
            }
        }
        return uploadFileName;
    }

    public String getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(String uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public AttachDocs getAttachDocs() {
        return attachDocs;
    }

    public void setAttachDocs(AttachDocs attachDocs) {
        this.attachDocs = attachDocs;
    }

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

}
