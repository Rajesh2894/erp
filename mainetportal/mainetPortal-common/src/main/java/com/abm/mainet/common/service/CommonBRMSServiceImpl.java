package com.abm.mainet.common.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriTemplateHandler;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.FileUploadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;

@Service
public class CommonBRMSServiceImpl implements ICommonBRMSService {
	
	 private static final Logger LOG = LoggerFactory.getLogger(CommonBRMSServiceImpl.class);

	private static final long serialVersionUID = 1131046619328546163L;
	
	@Resource
	IFileUploadService fileUpload;

	@Value("${upload.physicalPath}")
	private String filenetPath;

	@Override
	public WSResponseDTO initializeModel(WSRequestDTO dto) {
		return JersyCall.callServiceBRMSRestClient(dto, ServiceEndpoints.BRMS_URL.INITIALIZE_MODEL_URL);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DocumentDetailsVO> getChecklist(final CheckListModel checklistModel) {
		List<DocumentDetailsVO> checklist = Collections.emptyList();
		final WSRequestDTO dto = new WSRequestDTO();
		dto.setDataModel(checklistModel);
		final WSResponseDTO response = JersyCall.callServiceBRMSRestClient(dto,
				ServiceEndpoints.BRMS_URL.CHECKLIST_URL);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())
				|| MainetConstants.NewWaterServiceConstants.NA.equalsIgnoreCase(response.getWsStatus())) {

			final List<?> docs = JersyCall.castResponse(response, DocumentDetailsVO.class);
			checklist = (List<DocumentDetailsVO>) docs;
			if (!CollectionUtils.isEmpty(checklist)) {
				//Defect #129055
				checklist.forEach(doc -> doc.setDoc_DESC_Mar(doc.getDoc_DESC_Mar()));
			}
		}
		return checklist;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<DocumentDetailsVO> getChecklistDocument(String application , Long orgId,String checkListFlag) {
		List<DocumentDetailsVO> checklist = new ArrayList<>();
		Map<String, String> requestParam = new HashMap<>();
		DefaultUriTemplateHandler dd = new DefaultUriTemplateHandler();
		dd.setParsePath(true);
		requestParam.put("orgId", String.valueOf(orgId));
		requestParam.put("application", application);
		requestParam.put("checkListFlag", checkListFlag);
		URI uri = dd.expand(ServiceEndpoints.JercyCallURL.GET_CHECKLIST_DOCUMENT, requestParam);
		
		 List<LinkedHashMap<Long, Object>> responseObj = (List<LinkedHashMap<Long, Object>>) JersyCall.callRestTemplateClient(
				 null, uri.toString());
	        if (responseObj != null && !responseObj.isEmpty()) {
	            responseObj.forEach(obj -> {
	                String d = new JSONObject(obj).toString();
	                try {
	                	DocumentDetailsVO app = Utility.getMapper().readValue(d, DocumentDetailsVO.class);
	                	checklist.add(app);
	                } catch (Exception ex) {
	                	LOG.error("Exception while casting DocumentDetailsVO to rest response :" + ex);
	                }

	            });
	            
	            for (final DocumentDetailsVO list : checklist) {
					List<DocumentDetailsVO> checklist1 = new ArrayList<>();
					final FileUploadDTO uploadDTO = new FileUploadDTO();
					uploadDTO.setDirPath(list.getUploadedDocumentPath());
					uploadDTO.setFileNetPath(filenetPath);
					checklist1.add(list);
					fileUpload.saveMasterFileToPath(checklist1, uploadDTO);
				}
	            
	        }
		return checklist;
	}

}
