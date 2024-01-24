package com.abm.mainet.rnl.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadClass;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author ritesh.patil
 *
 */
public class CommonUtilityRL {

    private static Logger logger = Logger.getLogger(CommonUtilityRL.class);

    public static void clearFileSession() {
        FileUploadUtility.getCurrent().getFileMap().clear();
        FileUploadUtility.getCurrent().getFileUploadSet().clear();
        FileUploadUtility.getCurrent().setFolderCreated(false);
    }

    public static List<AttachDocs> uploadFile(final List<AttachDocs> docs) {

        final List<AttachDocs> attachDocsList = new ArrayList<>(0);
        AttachDocs attachDocs = null;
        final Date date = new Date();
        List<File> list = null;
        File file = null;
        String tempDirPath = "";
        Iterator<File> setFilesItr = null;

        final List<String> str = new ArrayList<>();
        if (docs != null) {
            for (final AttachDocs attaches : docs) {
                str.add(attaches.getAttFname());
            }
        }
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            list = new ArrayList<>(entry.getValue());
            setFilesItr = entry.getValue().iterator();

            while (setFilesItr.hasNext()) {
                file = setFilesItr.next();
                if (!str.contains(file.getName())) {
                    tempDirPath = MainetConstants.operator.EMPTY;
                    attachDocs = new AttachDocs();
                    attachDocs.setLgIpMac(Utility.getMacAddress());
                    attachDocs.setLmodate(date);
                    attachDocs.setAttDate(new Date());
                    attachDocs.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                   // attachDocs.setLangId(UserSession.getCurrent().getLanguageId());
                    attachDocs.setStatus(MainetConstants.RnLCommon.Flag_A);
                    attachDocs.setAttFname(file.getName());
                    attachDocs.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                    for (final FileUploadClass fileUploadClass : FileUploadUtility.getCurrent().getFileUploadSet()) {
                        if (entry.getKey().toString().equals(fileUploadClass.getCurrentCount().toString())) {
                            boolean pathStatus = true;
                            if (pathStatus) {
                                tempDirPath = getESTMasDirectry() + MainetConstants.FILE_PATH_SEPARATOR
                                        + fileUploadClass.getFolderName();
                                attachDocs.setAttPath(tempDirPath);
                                attachDocs.setSerialNo(Integer.valueOf(fileUploadClass.getFolderName()));
                                pathStatus = false;
                            }
                            attachDocsList.add(attachDocs);
                        }
                    }

                    try {
                        FileNetApplicationClient.getInstance().uploadFileList(list, tempDirPath);
                    } catch (final Exception e) {
                        logger.error("", e);
                        return null;
                    }
                }
            }
        }

        try {
            final String path = FileUploadUtility.getCurrent().getExistingFolderPath();
            if (path != null) {
                final File cacheFolderStructure = new File(FileUploadUtility.getCurrent().getExistingFolderPath());
                FileUtils.deleteDirectory(cacheFolderStructure);
            }
        } catch (final Exception e) {
            logger.error("", e);
        }

        return attachDocsList;
    }

    private static String getESTMasDirectry() {
        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator
                + MainetConstants.RL_Identifier.NameEstateMaster + File.separator + Utility.getTimestamp();
    }

}
