package com.abm.mainet.dms.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.util.ApplicationSession;

@Service
public class FileNetPathService {

    private final String physicalPath = ApplicationSession.getInstance().getMessage("upload.physicalPath");

    private String isPhysicalPathEnable = ApplicationSession.getInstance().getMessage("upload.physicalPathEnable");

    private static String rootPath = MainetConstants.BLANK;

    public String getRootDirectory() {
        if ((isPhysicalPathEnable != null) && isPhysicalPathEnable.equalsIgnoreCase(MainetConstants.MENU.TRUE)) {
            return physicalPath;
        } else {
            return getRootPath();
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

    public String getDirectoryTree(final String dirTree) {
        String folderTree = File.separator;
        for (final String folder : dirTree.split(MainetConstants.operator.COMA)) {
            folderTree = folderTree + folder + File.separator;
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
     * @param rootPath the rootPath to set
     */
    public static void setRootPath(final String rootPath) {
        FileNetPathService.rootPath = rootPath;
    }

    /**
     * @return the physicalPath
     */
    public String getPhysicalPath() {
        return physicalPath;
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
