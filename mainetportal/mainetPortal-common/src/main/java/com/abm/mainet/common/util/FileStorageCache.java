package com.abm.mainet.common.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.model.Filepaths;

/**
 * @author Rajendra.Bhujbal
 */
public class FileStorageCache implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(FileStorageCache.class);
    private long recordId;

    private List<MultipartFile> multipartFiles = new ArrayList<>(0);
    private MultipleFile multipleFiles = new MultipleFile();
    private File profileImage;

    public FileStorageCache() {
        super();
    }

    static public FileStorageCache getInstance() {
        return new FileStorageCache();
    }

    /**
     * @return the recordId
     */
    public long getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(final long recordId) {
        this.recordId = recordId;
    }

    /**
     * @return the multipartFiles
     */
    public List<MultipartFile> getMultipartFiles() {
        return multipartFiles;
    }

    /**
     * @param multipartFiles the multipartFiles to set
     */
    public void setMultipartFiles(final List<MultipartFile> multipartFiles) {
        this.multipartFiles = multipartFiles;
    }

    /**
     * @return the multipleFiles
     */
    public MultipleFile getMultipleFiles() {
        return multipleFiles;
    }

    /**
     * @param multipleFiles the multipleFiles to set
     */
    public void setMultipleFiles(final MultipleFile multipleFiles) {
        this.multipleFiles = multipleFiles;
    }

    public List<File> getFileList() {
        final ListIterator<File> listIterator = multipleFiles.getFileList().listIterator();
        while (listIterator.hasNext()) {
            final File file = listIterator.next();

            if (!file.exists()) {
                listIterator.remove();
            }

        }

        return multipleFiles.getFileList();
    }

    public void removeFiles() {
        final ListIterator<File> listIterator = multipleFiles.getFileList().listIterator();
        while (listIterator.hasNext()) {
            listIterator.remove();
        }

    }

    public void flush() {
        multipleFiles.flush();

        if ((getProfileImage() != null) && getProfileImage().exists()) {
            final File profileDir = getProfileImage().getParentFile();
            getProfileImage().delete();
            if (profileDir.exists() && (profileDir.listFiles().length == 0)) {
                profileDir.delete();
            }

        }
    }

    /**
     * @return the profileImage
     */
    public File getProfileImage() {
        return profileImage;
    }

    /**
     * @param profileImage the profileImage to set
     */
    private void setProfileImage(final File profileImage) {
        this.profileImage = profileImage;
    }

    public final void setProfile(final MultipartFile profileImage) {
        final boolean prevImage = hasPreviousrofileImage();

        String existingPath = null;

        // Check whether previously profile image is uploaded or not?

        if (prevImage) {
            existingPath = this.profileImage.getParent() + MainetConstants.FILE_PATH_SEPARATOR;
        } else {
            existingPath = Filepaths.getfilepath() + MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                    + MainetConstants.FILE_PATH_SEPARATOR + Utility.getGUIDNumber() + MainetConstants.FILE_PATH_SEPARATOR;
        }

        // To create nested directory if not exists.
        Utility.createDirectory(existingPath);

        final File tempFile = new File(existingPath + profileImage.getOriginalFilename());

        try {

            final File oldProfileImage = new File(
                    this.profileImage != null ? this.profileImage.getAbsolutePath() : MainetConstants.BLANK);

            // Upload new profile imahe.
            profileImage.transferTo(tempFile);

            // Delete previously uploaded profile images.
            if (prevImage) {
                oldProfileImage.delete();
            }

            setProfileImage(tempFile);
        } catch (IllegalStateException | IOException ex) {
            LOG.error(MainetConstants.ERROR_OCCURED, ex);
        }
    }

    private boolean hasPreviousrofileImage() {
        if (profileImage != null) {
            final String path = profileImage.getAbsolutePath();

            return ((path != null) && !MainetConstants.BLANK.equals(path));
        }

        return false;
    }
}
