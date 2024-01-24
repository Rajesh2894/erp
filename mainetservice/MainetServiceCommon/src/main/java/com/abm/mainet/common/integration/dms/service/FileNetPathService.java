package com.abm.mainet.common.integration.dms.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;

public class FileNetPathService {

    private String physicalPath = ApplicationSession.getInstance().getMessage("upload.physicalPath");

    private String isPhysicalPathEnable = ApplicationSession.getInstance().getMessage("upload.physicalPathEnable");

    private static String rootPath = MainetConstants.BLANK;

    public String getRootDirectory() {
        if ((isPhysicalPathEnable != null) && isPhysicalPathEnable.equalsIgnoreCase("true")) {
            return getPhysicalPath();
        } else {
            return getRootPath();
        }
    }

    /**
     * List all files from a directory and its subdirectories
     *
     * @param directoryName to be listed
     */
    public void listFilesAndFilesSubDirectories(final String directoryName) {
        final File directory = new File(directoryName);
        // get all the files from a directory
        final File[] fList = directory.listFiles();

        for (final File file : fList) {
            if (file.isFile()) {
            } else if (file.isDirectory()) {
                listFilesAndFilesSubDirectories(file.getAbsolutePath());
            }
        }
    }

    public List<String> getFileNames(final String dirTree) {
        final List<String> fileNames = new ArrayList<>();

        final String folderTree = getDirectoryTree(dirTree);
        final String fullPath = getRootDirectory() + folderTree;
        final File[] files = new File(fullPath).listFiles();

        for (final File file : files) {
            if (file.isFile()) {
                fileNames.add(file.getName());
            }
        }

        return fileNames;
    }

    public String getDirectoryTree(final String commaSeperatordirectoryPath) {
        String folderTree = MainetConstants.FILE_PATH_SEPARATOR;
        for (final String folder : commaSeperatordirectoryPath.split(MainetConstants.operator.COMMA)) {
            folderTree = folderTree + folder + MainetConstants.FILE_PATH_SEPARATOR;
        }
        return folderTree;
    }

    public String getPreviewDirectoryTree(final String commaSeperatordirectoryPath) {
        String folderTree = MainetConstants.BLANK;
        for (final String folder : commaSeperatordirectoryPath.split(MainetConstants.operator.COMMA)) {
            folderTree = folderTree + folder + MainetConstants.FILE_PATH_SEPARATOR;
        }
        return folderTree;
    }

    /**
     * @return the rootPath
     */
    public static String getRootPath() {
        return rootPath;
    }

    /**
     * @return the physicalPath
     */
    public String getPhysicalPath() {
        return physicalPath;
    }

    /**
     * @param physicalPath the physicalPath to set
     */
    public void setPhysicalPath(final String physicalPath) {
        this.physicalPath = physicalPath;
    }

    /**
     * @return the isPhysicalPathEnable
     */
    public String getIsPhysicalPathEnable() {
        return isPhysicalPathEnable;
    }

    /**
     * @param isPhysicalPathEnable the isPhysicalPathEnable to set
     */
    public void setIsPhysicalPathEnable(final String isPhysicalPathEnable) {
        this.isPhysicalPathEnable = isPhysicalPathEnable;
    }
}
