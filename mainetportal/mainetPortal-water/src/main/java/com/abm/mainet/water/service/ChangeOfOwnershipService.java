package com.abm.mainet.water.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.water.dto.ChangeOfOwnerRequestDTO;
import com.abm.mainet.water.dto.ChangeOfOwnerResponseDTO;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChangeOfOwnershipService implements IChangeOfOwnershipService {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOfOwnershipService.class);

    @Override
    public ChangeOfOwnerRequestDTO mapCheckList(final ChangeOfOwnerRequestDTO requestObject, final List<DocumentDetailsVO> docs,
            final BindingResult bindingResult) {

        new HashMap<Long, String>();
        new HashMap<Long, String>();

        final List<DocumentDetailsVO> docList = prepareFileUpload(docs);

        if ((docList != null) && !docList.isEmpty()) {
            for (final DocumentDetailsVO doc : docList) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.YES)) {
                    if ((doc.getDocumentByteCode() == null) || doc.getDocumentByteCode().isEmpty()) {
                        bindingResult.addError(new ObjectError(MainetConstants.BLANK,ApplicationSession.getInstance().getMessage("upload.doc")));

                        break;
                    }
                }
            }
            requestObject.setUploadedDocList(docList);
        }

        return requestObject;

    }

    private List<DocumentDetailsVO> prepareFileUpload(final List<DocumentDetailsVO> docs) {
        final Map<Long, String> listOfString = new HashMap<>();
        final Map<Long, String> fileName = new HashMap<>();
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    try {
                        final Base64 base64 = new Base64();
                        final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                        fileName.put(entry.getKey(), file.getName());
                        listOfString.put(entry.getKey(), bytestring);
                    } catch (final IOException e) {
                        LOGGER.error("Exception has been occurred in file byte to string conversions", e);
                    }
                }
            }
        }
        if (!docs.isEmpty() && !listOfString.isEmpty()) {
            for (final DocumentDetailsVO d : docs) {
                final long count = d.getDocumentSerialNo() - 1;
                if (listOfString.containsKey(count) && fileName.containsKey(count)) {
                    d.setDocumentByteCode(listOfString.get(count));
                    d.setDocumentName(fileName.get(count));
                }
            }
        }
        return docs;
    }

    @Override
    public ChangeOfOwnerResponseDTO fetchOldConnectionData(final ChangeOfOwnerRequestDTO requestVo)
            throws JsonParseException, JsonMappingException, IOException {
        @SuppressWarnings("unchecked")
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
                .callRestTemplateClient(requestVo, ServiceEndpoints.ServiceCallURI.CHANGE_OWNER_OLD_CONNECTION);
        final String d = new JSONObject(responseVo).toString();
        return new ObjectMapper().readValue(d, ChangeOfOwnerResponseDTO.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ChangeOfOwnerResponseDTO saveOrUpdateChangeOfOwnerForm(final ChangeOfOwnerRequestDTO changeOwnerMaster)
            throws JsonParseException, JsonMappingException, IOException {
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
                .callRestTemplateClient(changeOwnerMaster, ServiceEndpoints.ServiceCallURI.CHANGE_OWNER_SAVE);
        final String response = new JSONObject(responseVo).toString();
        return new ObjectMapper().readValue(response,
                ChangeOfOwnerResponseDTO.class);

    }

    @Override
	 @SuppressWarnings("unchecked")
	public ChangeOfOwnerRequestDTO getAppicationDetails(Long applicationId, Long orgId) {
		
    	ChangeOfOwnerRequestDTO requestDto = null;
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("applicationId", String.valueOf(applicationId));
		requestParam.put("orgId", String.valueOf(orgId));
		URI uri = dd.expand(ServiceEndpoints.WebServiceUrl.GET_CHANGEOFOWNER_DETAILS_BY_APPID, requestParam);
		final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
				.callRestTemplateClient(orgId, uri.toString());
		final String dto = new JSONObject(responseVo).toString();
		try {
			requestDto = new ObjectMapper().readValue(dto, ChangeOfOwnerRequestDTO.class);
		} catch (final IOException e) {
			throw new FrameworkException("Error while casting response to ChangeOfUsageRequestDTO", e);
		}
		LOGGER.info("ChangeOfUsageRequestDTO formed is " + requestDto.toString());
		return requestDto;
	
	}
}
