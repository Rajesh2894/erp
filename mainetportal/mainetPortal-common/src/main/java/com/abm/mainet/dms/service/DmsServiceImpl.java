package com.abm.mainet.dms.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.DocumentDetailsDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DmsServiceImpl implements DmsService {

    @Override
    public Map<String, String> createDocument(String folderPath, final String filePath, final Boolean isFolderExist) {

        Map<String, String> fileMap = null;
        String docId = null;
        byte[] byteArray = null;
        ;
        String fileName = null;
        String mimeType = null;

        if (!isFolderExist) {
            folderPath = createFolder(folderPath);
        }
        try {
            final Path path = Paths.get(filePath);
            byteArray = Files.readAllBytes(path);
            fileName = path.getFileName().toString();
            mimeType = Files.probeContentType(path);
        } catch (final IOException e) {

            throw new RuntimeException(e);
        }

        docId = (String) JersyCall.dmsCreateDoc(new DocumentDetailsDto(null, MainetConstants.DmsClientType.DMS_CLIENT_SOAP,
                folderPath, fileName, byteArray, mimeType));

        if (docId != null) {
            if (!docId.equals(MainetConstants.DOC_EXIST)) {
                fileMap = new HashMap<>(3);
                fileMap.put(MainetConstants.FOLDER_PATH, folderPath);
                fileMap.put(MainetConstants.DOC_ID, docId);
                fileMap.put(MainetConstants.FILE_NAME, fileName);

            }
        }
        return fileMap;
    }

    @Override
    public String createFolder(final String folderPath) {
        return JersyCall.callDmsCreateFolder(folderPath);
    }

    @Override
    public byte[] getDocumentById(final String docId) throws JsonParseException, JsonMappingException, IOException {
        byte[] byteArray = null;
        final DocumentDetailsDto dto = new DocumentDetailsDto();
        dto.setDocumentId(docId);
        dto.setProtocol(MainetConstants.DmsClientType.DMS_CLIENT_SOAP);
        @SuppressWarnings("unchecked")
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.dmsGetDoc(dto);
        DocumentDetailsDto documentDto = null;
        if ((responseVo != null) && !responseVo.isEmpty()) {
            final String doc = new JSONObject(responseVo).toString();
            documentDto = new ObjectMapper().readValue(doc, DocumentDetailsDto.class);
        }

        if (documentDto != null) {
            byteArray = documentDto.getFileContent();

        }
        return byteArray;
    }

    public static byte[] FileToArrayOfBytes(final String filePath) throws IOException {

        final byte[] bFile = Files.readAllBytes(Paths.get(filePath));
        return bFile;
    }

}
