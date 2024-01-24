package com.abm.mainet.common.integration.dms.service;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadata;
import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadataDet;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.dto.DocumentDetailsDto;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.repository.IMetadataRepository;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class DmsServiceImpl implements IDmsService {
	
	   @Autowired
	    IMetadataRepository metadataRepository;

    @Override
    public Map<String, String> createDocument(String folderPath, final String byteString, String fileName,
            final Boolean isFolderExist,final FileUploadDTO fileUploadDTOs,final DocumentDetailsVO docdetailVO) {

        Map<String, String> fileMap = new HashMap<>();
        String docId = null;
        byte[] byteArray = null;
        String mimeType = null;

        if (!isFolderExist) {
            folderPath = createFolder(folderPath);
            if (folderPath.equals(MainetConstants.Dms.E104)) {
            	fileMap.put(MainetConstants.Dms.ERROR_MSG, MainetConstants.Dms.SITE_NOT_PRESENT);
            	return fileMap;
			}
        }
        final Base64 base64 = new Base64();
        byteArray = base64.decode(byteString);
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        mimeType = fileNameMap.getContentTypeFor(fileName);

        /*
         * docId = (String) RestClient.dmsCreateDoc(new DocumentDetailsDto(null, MainetConstants.DmsClientType.DMS_CLIENT_SOAP,
         * folderPath, fileName, byteArray, mimeType));
         */
           
        //#106603  By Arun
		DocumentDetailsDto dto = new DocumentDetailsDto(null, folderPath, fileName, byteArray, mimeType);
		Organisation org =new Organisation();
		org.setOrgid(fileUploadDTOs.getOrgId());
		if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_ASCL) && MainetConstants.DeptCode.WATER.equalsIgnoreCase(fileUploadDTOs.getDepartmentName())) 
			 dto.setDepartmentCode(MainetConstants.DeptCode.WTD);
		else 
			dto.setDepartmentCode(fileUploadDTOs.getDepartmentName());
		dto.setDocTypeDescription(docdetailVO.getDescriptionType() != null ? docdetailVO.getDescriptionType() : null);
		dto.setDepartmentName(
				fileUploadDTOs.getDepartmentFullName() != null ? fileUploadDTOs.getDepartmentFullName() : null);
		if (fileUploadDTOs.getDmsDocsDto() != null) {
			dto.setDmsServiceMap(fileUploadDTOs.getDmsDocsDto().getDmsServiceMap() != null
					? fileUploadDTOs.getDmsDocsDto().getDmsServiceMap()
					: null);
		}
		
        docId = (String) RestClient.dmsCreateDoc(dto);
		if (docId != null) {
			if (docId.equals(MainetConstants.Dms.E101)) {
				fileMap.put(MainetConstants.Dms.ERROR_MSG, MainetConstants.Dms.DOCUMENT_EXISTS);
			} else if (docId.equals(MainetConstants.Dms.E102)) {
				fileMap.put(MainetConstants.Dms.ERROR_MSG, MainetConstants.Dms.METADATA_NOT_MAP);
			} else if (docId.equals(MainetConstants.Dms.E103)) {
				fileMap.put(MainetConstants.Dms.ERROR_MSG,  MainetConstants.Dms.METADATA_NOT_SAVED);
			} else {			
				fileMap.put("FOLDER_PATH", folderPath);
				fileMap.put("DOC_ID", docId);
				fileMap.put("FILE_NAME", fileName);
				fileMap.put(MainetConstants.Dms.ERROR_MSG, MainetConstants.BLANK);
			}
		}
        return fileMap;
    }

    @Override
    public String createFolder(final String folderPath) {
        return RestClient.callDmsCreateFolder(folderPath);
    }

    @Override
    public byte[] getDocumentById(final String docId) throws JsonParseException, JsonMappingException, IOException {
        byte[] byteArray = null;
        String responseVo = RestClient.dmsGetDoc(docId);

        if ((responseVo != null) && !responseVo.isEmpty()) {
        	Base64 base64 = new Base64();
            byteArray = base64.decode(responseVo);
        }

        return byteArray;
    }

    public static byte[] FileToArrayOfBytes(final String filePath) throws IOException {

        final byte[] bFile = Files.readAllBytes(Paths.get(filePath));
        return bFile;
    }

	@Override
	public DmsDocsMetadataDto saveDms(DmsDocsMetadataDto dmsMetadaDto) {
		DmsDocsMetadata entity = new DmsDocsMetadata();
		List<DmsDocsMetadataDet> detEntityList = new ArrayList<DmsDocsMetadataDet>();
		BeanUtils.copyProperties(dmsMetadaDto, entity);
		entity.setDmsDocsMetadataDetList(detEntityList);
		metadataRepository.save(entity);
		BeanUtils.copyProperties(entity, dmsMetadaDto);
		return dmsMetadaDto;
	}

}
