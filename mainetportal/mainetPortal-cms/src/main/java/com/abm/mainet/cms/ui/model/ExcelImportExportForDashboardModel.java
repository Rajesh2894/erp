package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.dto.ReadExcelData;
import com.abm.mainet.cms.service.IExcelImportExportForDashboardService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.AttachDocs;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * @author Jugnu Pandey
 *
 *
 */
@Component
@Scope("session")
public class ExcelImportExportForDashboardModel extends AbstractEntryFormModel<BaseEntity> implements Serializable {
    private static final long serialVersionUID = 4223761944031666461L;
    private static final Logger logger = Logger.getLogger(ExcelImportExportForDashboardModel.class);

    @Autowired
    private IExcelImportExportForDashboardService iExcelImportExportForDashboardService;

    private AttachDocs attachDocs = new AttachDocs();

    private String docName;
    private Date attDate;

    List<Long> list = new ArrayList<>();

    public AttachDocs getAttachDocs() {
        return attachDocs;
    }

    public void setAttachDocs(AttachDocs attachDocs) {
        this.attachDocs = attachDocs;
    }

    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
    String date = formatter.format(new Date());
    private String directoryTree = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + date
            + File.separator + UserSession.getCurrent().getEmployee().getEmpId() + Utility.getTimestamp();

    private String uploadedFile = MainetConstants.BLANK;

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
            Class className = getClassName(attachDocs.getIdfId());

            if (className != null) {
                ReadExcelData data = new ReadExcelData<>(filePath, className, 1);
                data.parseExcelList();
                if (data.getParseData().isEmpty()) {
                    addValidationError("Data cannot be read");
                } else {
                    status = iExcelImportExportForDashboardService.saveExcelData(className, data.getParseData(),
                            UserSession.getCurrent().getEmployee().getEmpId(), getAttachDocs().getAttDate(), this.getList());
                }

            }
            if (status) {
                AttachDocs attachment = new AttachDocs();
                if ((uploadedFile != null) && !uploadedFile.equals(MainetConstants.BLANK)) {
                    attachment.setAttFname(uploadedFile);
                    uploadedFile = directoryTree + File.separator + uploadedFile;
                    attachment.setAttPath(uploadedFile);
                    attachment.setLmodDate(new Date());
                    attachment.setLgIpMac(getUserSession().getEmployee().getEmppiservername());
                    attachment.setOrgid(getUserSession().getOrganisation().getOrgid());
                    attachment.setUserid(getUserSession().getEmployee().getEmpId());
                    attachment.setStatus("A");
                    attachment.setAttDate(getAttachDocs().getAttDate());
                    attachment.setIdfId(getAttachDocs().getIdfId());
                    iExcelImportExportForDashboardService.saveAttachment(attachment);
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

    public Class getClassName(String idfId) {
        String cpdValue = CommonMasterUtility
                .getNonHierarchicalLookUpObject(Long.valueOf(idfId), UserSession.getCurrent().getOrganisation()).getLookUpCode();
        Class className = null;
        if (!cpdValue.isEmpty() && !cpdValue.equalsIgnoreCase("")) {
            String name = ApplicationSession.getInstance().getMessage("SCM." + cpdValue);
            try {
                className = Class.forName("com.abm.mainet.cms.domain." + name);
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

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

}
