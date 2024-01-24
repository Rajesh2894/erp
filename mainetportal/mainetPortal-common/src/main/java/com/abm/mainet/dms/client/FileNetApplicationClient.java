package com.abm.mainet.dms.client;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.dms.utility.UtilitySupportService;

public class FileNetApplicationClient implements Serializable {

    private static final long serialVersionUID = 3558597713531428446L;
    private static final Logger LOG = Logger.getLogger(FileNetApplicationClient.class);
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

    public String[] uploadFileList(final List<File> fileList, final String directoryTree) throws Exception {
        LOG.info(" directoryTree Path Inside uploadFileList of FileNetApplicationClient :"+directoryTree);        
        return utilityService.upload(fileList, directoryTree);
    }

    public byte[] getFileByte(final String fileName, final String dirTree) {
        return utilityService.getFile(fileName, dirTree);
    }

}
