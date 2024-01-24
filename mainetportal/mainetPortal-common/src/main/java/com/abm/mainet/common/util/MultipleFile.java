package com.abm.mainet.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.model.Filepaths;

public final class MultipleFile {

    private static final String PROBLEM_OCCURRED_WHILE_DETETING_DIRECTORY = "Problem occurred while deteting directory.";
    private static final String PROBLEM_OCCURRED_WHILE_DETETING_FILE = "Problem occurred while deteting file :";
    private static final String IO_EXCEPTION_OCCRRED_WHILE_UPLOADING = "IO exception occrred while uploading ";
    private List<File> fileList = new ArrayList<>(0);

    public void addRemove(final Object file, final String operationType) throws FrameworkException {

        File tempFile = null;

        try {
            String existingPath = getExistingPath();

            if (existingPath == null) {
                existingPath = Filepaths.getfilepath() + MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                        + MainetConstants.FILE_PATH_SEPARATOR + Utility.getGUIDNumber() + MainetConstants.FILE_PATH_SEPARATOR;
            }

            // To create nested directory if not exists.
            Utility.createDirectory(existingPath);

            if (operationType.equals(MainetConstants.Transaction.Mode.ADD)) {
                tempFile = new File(existingPath + ((MultipartFile) file).getOriginalFilename());
                ((MultipartFile) file).transferTo(tempFile);

                fileList.add(tempFile);
            } else {
                tempFile = new File(existingPath + file);
                fileList.remove(tempFile);
                tempFile.delete();

            }

        } catch (final IOException ex) {
            throw new FrameworkException(IO_EXCEPTION_OCCRRED_WHILE_UPLOADING, ex);
        } finally {

        }
    }

    private String getExistingPath() {
        if (fileList.size() > 0) {
            return StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR, fileList.get(0).getAbsolutePath())
                    + MainetConstants.FILE_PATH_SEPARATOR;
        }

        return null;
    }

    public int getSize() {
        return getFileList().size();
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(final List<File> fileList) {
        this.fileList = fileList;
    }

    public void flush() throws FrameworkException {
        for (final File file : getFileList()) {
            if (file.exists() && !file.delete()) {
                throw new FrameworkException(PROBLEM_OCCURRED_WHILE_DETETING_FILE + file.getName());
            }
        }

        final String path = getExistingPath();

        final File dir = (path != null) ? new File(path) : null;

        if ((dir != null) && dir.isDirectory() && (dir.list().length == 0)) {
            if (!dir.delete()) {
                throw new FrameworkException(PROBLEM_OCCURRED_WHILE_DETETING_DIRECTORY);
            }
        }

    }

    public void add(final String file, final String operationType) throws FrameworkException {

        File tempFile = null;

        String existingPath = getExistingPath();

        if (existingPath == null) {
            existingPath = Filepaths.getfilepath() + MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                    + MainetConstants.FILE_PATH_SEPARATOR + Utility.getGUIDNumber() + MainetConstants.FILE_PATH_SEPARATOR;
        }

        // To create nested directory if not exists.
        Utility.createDirectory(existingPath);

        tempFile = new File(existingPath + file);

        fileList.add(tempFile);

    }

}
