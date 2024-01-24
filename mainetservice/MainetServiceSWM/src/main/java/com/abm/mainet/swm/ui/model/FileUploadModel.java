package com.abm.mainet.swm.ui.model;

import java.io.File;
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
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.service.ILogBookReportService;
import com.ibm.icu.text.SimpleDateFormat;

@Component
@Scope("session")
public class FileUploadModel extends AbstractFormModel {
    private static final long serialVersionUID = 1052731927722825142L;
    @Autowired
    private ILogBookReportService iLogBookReportService;
    private String uploadedFile = MainetConstants.BLANK;
    private AttachDocs attachDocs = new AttachDocs();
    private List<AttachDocs> listAttachDocs = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
    String date = formatter.format(new Date());
    String status = null;
    private String directoryTree = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date
            + File.separator + UserSession.getCurrent().getEmployee().getEmpId() + Utility.getTimestamp();

    @SuppressWarnings("unchecked")
    public boolean saveOrUpdateForm() {
        String filePath = null;
        boolean status = false;
        try {
            uploadedFile = uploadDocAndVerifyDoc(getFileNetClient(), directoryTree);
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                Set<File> list = entry.getValue();
                for (final File file : list) {
                    filePath = file.toString();
                    break;
                }
            }
            attachDocs.getIdfId();
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
                status = true;
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

    @SuppressWarnings("unused")
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

    public ILogBookReportService getiLogBookReportService() {
        return iLogBookReportService;
    }

    public void setiLogBookReportService(ILogBookReportService iLogBookReportService) {
        this.iLogBookReportService = iLogBookReportService;
    }

    public AttachDocs getAttachDocs() {
        return attachDocs;
    }

    public void setAttachDocs(AttachDocs attachDocs) {
        this.attachDocs = attachDocs;
    }

    public SimpleDateFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDirectoryTree() {
        return directoryTree;
    }

    public void setDirectoryTree(String directoryTree) {
        this.directoryTree = directoryTree;
    }

    public List<AttachDocs> getListAttachDocs() {
        return listAttachDocs;
    }

    public void setListAttachDocs(List<AttachDocs> listAttachDocs) {
        this.listAttachDocs = listAttachDocs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

}
