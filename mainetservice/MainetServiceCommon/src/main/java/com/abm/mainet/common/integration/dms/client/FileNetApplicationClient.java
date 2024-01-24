package com.abm.mainet.common.integration.dms.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.integration.dms.utility.UtilitySupportService;

public class FileNetApplicationClient implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3558597713531428446L;
    private static FileNetApplicationClient client;
    private final UtilitySupportService utilityService = new UtilitySupportService();

    private FileNetApplicationClient() {

    }

    public static FileNetApplicationClient getInstance() {
        if (client == null) {
            client = new FileNetApplicationClient();
        }

        return client;

    }

    public String[] uploadFileList(final List<File> fileList, final String relativeTempDirectoryTree) throws Exception {
        return utilityService.upload(fileList, relativeTempDirectoryTree);
    }

    public byte[] getFileByte(final String fileName, final String commaSeperatordirectoryPath) throws FileNotFoundException {
        return utilityService.getFile(fileName, commaSeperatordirectoryPath);
    }
    
    public byte[] getFileByte(final String fileName, final String commaSeperatordirectoryPath, final Boolean previewMode) throws FileNotFoundException {
        return utilityService.getFile(fileName, commaSeperatordirectoryPath, previewMode);
    }

}
