package com.abm.mainet.rnl.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author ritesh.patil
 *
 */
// @Component
public abstract class RLUtility {

    protected static Logger logger = Logger.getLogger(RLUtility.class);

    public static String getUploadedMessgeString(final String message, final List<AttachDocs> documentList) {

        final Map<Long, String> fileNames = new LinkedHashMap<>();
        final List<String> existDocs = new ArrayList<>();
        if (documentList != null) {
            for (final AttachDocs attachDocs : documentList) {
                fileNames.put(attachDocs.getAttId(), attachDocs.getAttFname());
                existDocs.add(FileUploadUtility.getCurrent().generateValidString(attachDocs.getAttFname()));
            }
        }

        File file = null;
        Iterator<File> setFilesItr = null;
        final List<String> allDocs = new ArrayList<>();
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            new ArrayList<>(entry.getValue());
            setFilesItr = entry.getValue().iterator();
            while (setFilesItr.hasNext()) {
                file = setFilesItr.next();
                System.out.println("total file " + file.getName());
                allDocs.add(FileUploadUtility.getCurrent().generateValidString(file.getName()));
            }
        }

        final StringBuilder b = new StringBuilder("<ul>");
        System.out.println(message);
        String text = message;
        text = text.replaceAll("</li>", "").replaceAll("</ul>", "");
        final String[] results = text.split("<li>");
        boolean flag = true;
        for (final String string : allDocs) {
            final Set<Entry<Long, String>> ent = fileNames.entrySet();
            for (final Entry<Long, String> entry : ent) {
                // The first one is empty, remove it
                for (int i = 1; i < results.length; i++) {
                    if (string.equals(entry.getValue())) {
                        if (flag) {
                            b.append("<li>" + results[i] + "<li>");
                            flag = false;
                        }
                        if (results[i].contains(string)) {
                            b.append("<li>" + results[i] + "<input type='hidden' value='" + entry.getKey() + "'></li>");
                        }
                    }
                }
            }
        }

        for (final String string : existDocs) {
            final Set<Entry<Long, String>> ent = fileNames.entrySet();
            for (final Entry<Long, String> entry : ent) {
                // The first one is empty, remove it
                for (int i = 1; i < results.length; i++) {
                    if (string.equals(entry.getValue())) {
                        if (flag) {
                            b.append("<li>" + results[i] + "<li>");
                            flag = false;
                        }
                        if (results[i].contains(string)) {
                            b.append("<li>" + results[i] + "<input type='hidden' value='" + entry.getKey() + "'></li>");
                        }
                    }
                }
            }
        }
        allDocs.removeAll(existDocs);

        for (final String string : allDocs) {
            for (int i = 1; i < results.length; i++) {
                if (results[i].contains(string)) {
                    b.append("<li>" + results[i] + "<input type='hidden' value=''></li>");
                }
            }
        }
        b.append("</ul>");

        return null;

    }

    public static void clearFileSession() {
        FileUploadUtility.getCurrent().getFileMap().clear();
        FileUploadUtility.getCurrent().getFileUploadSet().clear();
        FileUploadUtility.getCurrent().setFolderCreated(false);
    }

    public static String getDirectry(final String name) {
        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + name + File.separator
                + Utility.getTimestamp();
    }

    public static String getNumberBasedOnFunctionValue(final long no) {
        final int length = (int) Math.log10(no) + 1;
        switch (length) {
        case 1:
            return MainetConstants.RnLCommon.Tripe_Zero;
        case 2:
            return MainetConstants.RnLCommon.Double_Zero;
        case 3:
            return MainetConstants.RnLCommon.Single_Zero;
        }
        return "";
    }

    public static String getNumberBasedOnOrg(final long no) {
        final int length = (int) Math.log10(no) + 1;
        switch (length) {
        case 1:
            return MainetConstants.RnLCommon.Double_Zero;
        case 2:
            return MainetConstants.RnLCommon.Single_Zero;
        }
        return "";
    }

}
