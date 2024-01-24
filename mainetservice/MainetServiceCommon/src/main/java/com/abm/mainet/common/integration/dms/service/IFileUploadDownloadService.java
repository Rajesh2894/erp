package com.abm.mainet.common.integration.dms.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.abm.mainet.common.exception.FrameworkException;

public interface IFileUploadDownloadService {

    /**
     * To upload file.
     * @param file is {@link MultipartFile}}
     * @param dirTree is directory structure.(COMMA SEPARATED VALUES)
     * @return saved file name.
     */
    public String uploadFile(MultipartFile file, String dirTree);

    public String uploadFile(File file, String dirTree);

    public byte[] downloadFile(String fileName, String dirTree) throws FrameworkException;
    
    public byte[] downloadFile(final String fileName, final String commaSeperatordirectoryPath, boolean previewMode) throws FrameworkException;
}
