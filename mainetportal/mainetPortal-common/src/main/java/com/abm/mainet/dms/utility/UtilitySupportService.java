package com.abm.mainet.dms.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;

import com.abm.mainet.common.constant.MainetConstants;

public class UtilitySupportService {

    private static final Logger LOG = Logger.getLogger(UtilitySupportService.class);
    private static String filenetAddress;

    private static String filenetPort;

    private final FileUploadUtility fileUploadUtility = new FileUploadUtility();

    public String getDownloadURL() {
        return MainetConstants.HTTP + getFilenetAddress() + MainetConstants.operator.COLON + getFilenetPort()
                + MainetConstants.applicationName + MainetConstants.downloadURL;
    }

    public String getUploadMultipartURL() {
        return MainetConstants.HTTP + getFilenetAddress() + MainetConstants.operator.COLON + getFilenetPort()
                + MainetConstants.applicationName
                + MainetConstants.uploadURL;
    }

    public String getListFilesFromDirURL() {
        return MainetConstants.HTTP + getFilenetAddress() + MainetConstants.operator.COLON + getFilenetPort()
                + MainetConstants.applicationName
                + MainetConstants.fileNameListURL;
    }

    /**
     * @return the filenetAddress
     */
    public static String getFilenetAddress() {
        return filenetAddress;
    }

    /**
     * @param filenetAddress the filenetAddress to set
     */
    public static void setFilenetAddress(final String filenetAddress) {
        UtilitySupportService.filenetAddress = filenetAddress;
    }

    /**
     * @return the filenetPort
     */
    public static String getFilenetPort() {
        return filenetPort;
    }

    /**
     * @param filenetPort the filenetPort to set
     */
    public static void setFilenetPort(final String filenetPort) {
        UtilitySupportService.filenetPort = filenetPort;
    }

    /**
     * @return the uploadURL
     */
    public static String getUploadURL() {
        return MainetConstants.uploadURL;
    }

    /**
     * @return the fileNameListURL
     */
    public static String getFileNameListURL() {
        return MainetConstants.fileNameListURL;
    }

    private static HttpHeaders headersHttpHeaders = new HttpHeaders();
    static {
        headersHttpHeaders.set("Content-Type", "multipart/form-data ; UTF-8");
        headersHttpHeaders.set("ACCEPT_CHARSET", "UTF-8");
    }

    public byte[] getFile(final String fileName, final String dirTree) {

        byte[] response = null;
        try {
            response = fileUploadUtility.downloadFile(fileName, dirTree);
        } catch (final FileNotFoundException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
        }

        return response;
    }

    public String[] upload(final List<File> fileList, final String directoryTree) throws Exception {
        String response = "File Upload Failed.";
        LOG.info("Inside upload of UtilitySupportService with Path"+directoryTree);
        final List<String> filename = new ArrayList<>();
        for (final File file : fileList) {
            if (file.isFile()) {
                filename.add(file.getName());
            }
        }

        response = exchangeDataFile(fileList, directoryTree);
        String[] fileNameList = null;
        if ((response != null) && !response.equalsIgnoreCase(MainetConstants.BLANK)
                && response.contains(MainetConstants.operator.COMA)) {
            fileNameList = response.split(MainetConstants.operator.COMA);
        } else {
            fileNameList = new String[] { response };
        }

        return fileNameList;
    }

    private String exchangeDataFile(final List<File> fileList, final String directoryTree) {
        final List<String> fileNames = new ArrayList<>();
        LOG.info("Inside exchangeDataFile of UtilitySupportService with Path: "+directoryTree);
        for (final File eachFile : fileList) {
            fileUploadUtility.uploadFile(eachFile, directoryTree);
            fileNames.add(eachFile.getName());

        }

        return fileNames.toString();
    }

}