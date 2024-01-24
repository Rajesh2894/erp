package com.abm.mainet.common.integration.dms.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.FileUploadClientService;
import com.abm.mainet.common.integration.dms.service.IFileUploadDownloadService;
import com.abm.mainet.common.utility.ApplicationSession;

public class UtilitySupportService implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static String filenetAddress;

    private static String filenetPort;

    private final static String applicationName = "/FileNetApplicationWar";

    private final static String downloadURL = "/Download";

    private final static String uploadURL = "/MultipartUpload";

    private final static String fileNameListURL = "/FileDetails";

    private static String physicalPath = ApplicationSession.getInstance().getMessage("upload.physicalPath");

    private final IFileUploadDownloadService fileUploadDownloadService = new FileUploadClientService();

    private final static Logger LOGGER = org.slf4j.LoggerFactory
            .getLogger(UtilitySupportService.class);
    
    public String getDownloadURL() {
        return "http://" + getFilenetAddress() + MainetConstants.operator.QUOTES + getFilenetPort() + applicationName
                + downloadURL;
    }

    public String getUploadMultipartURL() {
        return "http://" + getFilenetAddress() + MainetConstants.operator.QUOTES + getFilenetPort() + applicationName
                + uploadURL;
    }

    public String getListFilesFromDirURL() {
        return "http://" + getFilenetAddress() + MainetConstants.operator.QUOTES + getFilenetPort() + applicationName
                + fileNameListURL;
    }

    /**
     * @return the filenetAddress
     */
    private String getFilenetAddress() {
        return filenetAddress;
    }

    /**
     * @return the filenetPort
     */
    private String getFilenetPort() {
        return filenetPort;
    }

    /**
     * @return the uploadURL
     */
    public static String getUploadURL() {
        return uploadURL;
    }

    /**
     * @return the fileNameListURL
     */
    public static String getFileNameListURL() {
        return fileNameListURL;
    }

    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders headersHttpHeaders = new HttpHeaders();
    private FileOutputStream fos;

    static {
        headersHttpHeaders.set("Content-Type", "multipart/form-data ; UTF-8");
        headersHttpHeaders.set("ACCEPT_CHARSET", "UTF-8");
    }

    public byte[] getFile(final String fileName, final String commaSeperatordirectoryPath) throws FrameworkException{
        return fileUploadDownloadService.downloadFile(fileName, commaSeperatordirectoryPath);
    }
    
    public byte[] getFile(final String fileName, final String commaSeperatordirectoryPath, final Boolean previewMode) throws FrameworkException{
        return fileUploadDownloadService.downloadFile(fileName, commaSeperatordirectoryPath, previewMode);
    }
    
    // Files from a directory
    public String[] getFileNamesFromDirectotyTree(final String directoryTree) {
        String result[] = null;
        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("dirTree", directoryTree);
        final HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(map,
                new HttpHeaders());
        final String wsURL = getListFilesFromDirURL();
        result = restTemplate.postForObject(wsURL, requestHttpEntity, String[].class);
        return result;
    }

    public List<String> getFileNamesFromDirectotyTreeList(final String directoryTree) {
        List<String> fileNameList = null;
        final File folder = new File(physicalPath + directoryTree);
        final File[] listOfFiles = folder.listFiles();
        if ((listOfFiles != null) && (listOfFiles.length > 0)) {
            for (final File file : listOfFiles) {
                if (file.isFile()) {
                    fileNameList = new ArrayList<>();
                    fileNameList.add(file.getName());
                    return fileNameList;
                }
            }
        }
        return fileNameList;
    }

    public String[] upload(final List<File> fileList, final String relativeTempDirectoryTree) throws Exception {
        String response = "File Upload Failed.";
        final List<String> filename = new ArrayList<>();
        for (final File file : fileList) {
            if (file.isFile()) {
                filename.add(file.getName());
            }
        }

        response = exchangeDataFile(fileList, relativeTempDirectoryTree);
        String[] fileNameList = null;
        if (StringUtils.isNotBlank(response)
                && response.contains(MainetConstants.operator.COMMA)) {
            fileNameList = response.split(MainetConstants.operator.COMMA);
        } else {
            fileNameList = new String[] { response };
        }

        return fileNameList;
    }

    public String[] uploadMultipart(final List<MultipartFile> fileList, final String directoryTree) throws Exception {
        String response = "File Upload Failed !!!";
        response = exchangeData(fileList, directoryTree);
        String[] fileNameList = null;
        if ((response != null) && !response.equalsIgnoreCase(MainetConstants.BLANK)
                && response.contains(MainetConstants.operator.COMMA)) {
            fileNameList = response.split(MainetConstants.operator.COMMA);
        } else {
            fileNameList = new String[] { response };
        }
        return fileNameList;
    }

    private static HttpEntity<MultiValueMap<String, Object>> getHttpEntity(final MultiValueMap<String, Object> mapMultipart) {
        final HttpEntity<MultiValueMap<String, Object>> requestHttpEntity = new HttpEntity<>(
                mapMultipart, headersHttpHeaders);
        return requestHttpEntity;
    }

    public static String getFileURL(final String fileName, String commaSeparatedPath) {
        commaSeparatedPath = commaSeparatedPath.replace(MainetConstants.operator.COMMA,
                MainetConstants.operator.FORWARD_SLACE);

        return commaSeparatedPath + MainetConstants.operator.FORWARD_SLACE + fileName;
    }

    private String exchangeData(final List<MultipartFile> fileList, final String directoryTree) {
        final List<String> fileNames = new ArrayList<>();

        for (final MultipartFile eachFile : fileList) {
            fileUploadDownloadService.uploadFile(eachFile, directoryTree);
            fileNames.add(eachFile.getOriginalFilename());

        }

        return fileNames.toString();
    }

    private String exchangeDataFile(final List<File> fileList, final String relativeTempDirectoryTree) {
        final List<String> fileNames = new ArrayList<>();

        for (final File eachFile : fileList) {
            fileUploadDownloadService.uploadFile(eachFile, relativeTempDirectoryTree);
            fileNames.add(eachFile.getName());

        }

        return fileNames.toString();
    }

}