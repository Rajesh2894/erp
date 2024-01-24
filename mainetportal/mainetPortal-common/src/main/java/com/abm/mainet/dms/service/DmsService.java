package com.abm.mainet.dms.service;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface DmsService {
    public Map<String, String> createDocument(String folderPath, String filePath, Boolean isFolderExist);

    public String createFolder(String folderPath);

    public byte[] getDocumentById(String docId) throws JsonParseException, JsonMappingException, IOException;
}
