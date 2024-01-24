package com.abm.mainet.common.integration.dms.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.Utility;

public class FileUploadClientService implements IFileUploadDownloadService {

    private final FileNetPathService utilityService = new FileNetPathService();

    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Utility.class);

    @Override
    public String uploadFile(final MultipartFile file, final String dirTree) {

	final String folderTree = utilityService.getDirectoryTree(dirTree);

	String readFileFrom = MainetConstants.BLANK;
	readFileFrom = folderTree + file.getOriginalFilename();
	final String uploadFileAt = utilityService.getRootDirectory() + readFileFrom;

	final File uploadFile = new File(uploadFileAt);
	try {
	    FileUtils.writeByteArrayToFile(uploadFile, file.getBytes());
	} catch (final IOException e) {
	    LOGGER.warn(e.getMessage());
	}

	return readFileFrom;
    }

    @Override
    public String uploadFile(final File file, final String relativeTempDirectoryTree) {

	// below line commented as it does not seems to be correct or required
	// final String folderTree = utilityService.getDirectoryTree(relativeTempDirectoryTree);
	final String folderTree = MainetConstants.operator.FORWARD_SLACE + relativeTempDirectoryTree;

	String readFileFrom = MainetConstants.BLANK;
	readFileFrom = folderTree + file.getName();
	final String uploadFileAt = utilityService.getRootDirectory() + folderTree;

	try {
	    FileUtils.copyFileToDirectory(file, new File(uploadFileAt));
	} catch (final IOException e) {
	    LOGGER.warn(e.getMessage(), e);
	}

	return readFileFrom;
    }

    @Override
    public byte[] downloadFile(final String fileName, final String commaSeperatordirectoryPath)
	    throws FrameworkException {
	return downloadFile(fileName, commaSeperatordirectoryPath, Boolean.FALSE);
    }
    
    
    @Override
    public byte[] downloadFile(final String fileName, final String commaSeperatordirectoryPath,
	    final boolean previewMode) throws FrameworkException {
	String fullPath = MainetConstants.BLANK;
	String folderTree = MainetConstants.BLANK;
	String appPath = MainetConstants.BLANK;

	if (previewMode) {
	    folderTree = utilityService.getPreviewDirectoryTree(commaSeperatordirectoryPath);

	    // construct the complete absolute path of the file
	    fullPath = folderTree + fileName;

	} else {
	    folderTree = utilityService.getDirectoryTree(commaSeperatordirectoryPath);

	    // get absolute path of the application
	    appPath = utilityService.getRootDirectory();
	    
	  
	    // construct the complete absolute path of the file
	    fullPath = appPath + folderTree + fileName;
	    
	   
	}
	
	 return downloadFile(fullPath);

    }

    public byte[] downloadFile(String fullPath) throws FrameworkException {
	try {
	    File downloadFile = new File(fullPath);
	    if (downloadFile.exists()) {
		try (FileInputStream inputStream = new FileInputStream(downloadFile)) {

		    final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    final byte[] buf = new byte[1024];
		    for (int readNum; (readNum = inputStream.read(buf)) != -1;) {
			bos.write(buf, 0, readNum); // no doubt here is 0
		    }

		    return bos.toByteArray();
		} catch (IOException e1) {
		    LOGGER.error("downloadFile-> " , e1);
		    throw new FrameworkException("File Read Exception on downloadFile", e1);
		}
	    } else {
		 LOGGER.error("File Does Not Exist.");
		throw new FrameworkException("File Does Not Exist.");
	    }
	} catch (final FrameworkException e) {
	    LOGGER.error("downloadFile-> " , e);
	    throw e;
	}
    }

}
