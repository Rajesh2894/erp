package com.abm.mainet.common.integration.dms.service;

import java.io.IOException;
import java.util.Map;

import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IDmsService {
    public Map<String, String> createDocument(String folderPath, String byteString, String fileName, Boolean isFolderExist,FileUploadDTO fileDto,DocumentDetailsVO docDetailVo);

    public String createFolder(String folderPath);

    public byte[] getDocumentById(String docId) throws JsonParseException, JsonMappingException, IOException;
    
    public DmsDocsMetadataDto saveDms(DmsDocsMetadataDto dto);

}
