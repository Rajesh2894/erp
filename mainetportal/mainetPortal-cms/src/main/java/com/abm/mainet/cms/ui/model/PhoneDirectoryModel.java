package com.abm.mainet.cms.ui.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.AbstractModel;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.utility.FileUploadUtility;

@Component
@Scope("session")
public class PhoneDirectoryModel extends AbstractModel {

    private static final long serialVersionUID = -3772636431764660190L;
    private static final Logger LOG = Logger.getLogger(PhoneDirectoryModel.class);
    private String attFname;
    private String attPath;
    private String fileName;
    private String uploadedPath;
    
    SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.FORMAT_DDMMMYY);
    String date = formatter.format(new Date());
    private String eipHomeImagesPath = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator
            + "PHONE_DIRECTORY";

    private String uploadedFile = MainetConstants.BLANK;

    public boolean saveOrUpdateForm() {

        try {
            uploadedFile = uploadDocAndVerifyDoc(getFileNetClient(), eipHomeImagesPath);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        return true;
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

    public String getAttFname() {
        return attFname;
    }

    public void setAttFname(String attFname) {
        this.attFname = attFname;
    }

    public String getAttPath() {
        return attPath;
    }

    public void setAttPath(String attPath) {
        this.attPath = attPath;
    }

    public String getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(String uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadedPath() {
        return uploadedPath;
    }

    public void setUploadedPath(String uploadedPath) {
        this.uploadedPath = uploadedPath;
    }


}
