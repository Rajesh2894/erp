package com.abm.mainet.care.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.care.dao.ComplaintRequestDAO;
import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDetDto;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.service.DepartmentalMetadetaService;
import com.abm.mainet.common.utility.Utility;

@Service("CareMetadata")
public class DepartmentalMetadataServiceImpl implements DepartmentalMetadetaService {

	@Autowired
	private ComplaintRequestDAO complaintRequestDAO;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Override
	public List<DmsDocsMetadataDto> getDepartmentalMetadeta(String colname, String Colvalue, Long orgId, Long deptId,Map<String, Object> argumentsMap) {
		List<DmsDocsMetadataDto> list = new ArrayList<DmsDocsMetadataDto>();
		//List<DmsDocsMetadataDetDto> childList = new ArrayList<DmsDocsMetadataDetDto>();
		List<CareRequest> DetailList = complaintRequestDAO.getComplaintRegisterDetail(colname, Colvalue, orgId, deptId, argumentsMap);
		if (CollectionUtils.isNotEmpty(DetailList)) {
			DetailList.forEach(entity -> {
				List<DmsDocsMetadataDetDto> childList = new ArrayList<DmsDocsMetadataDetDto>();
				DmsDocsMetadataDto dto = new DmsDocsMetadataDto();

				String jsonData = null;
				Map<?, ?> map = null;
				try {
					jsonData = Utility.getMapper().writeValueAsString(entity);
					map = Utility.getMapper().readValue(jsonData, Map.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				map.forEach((key, value) -> {
					DmsDocsMetadataDetDto childdto = new DmsDocsMetadataDetDto();
					childdto.setMtKey(String.valueOf(key));
					childdto.setMtVal(String.valueOf(value));
					childList.add(childdto);
				});

				StringBuilder docNames = new StringBuilder();
				StringBuilder docIds = new StringBuilder();
				List<CFCAttachment> doc = iChecklistVerificationService
						.getDocumentUploadedForAppId(entity.getApplicationId(), entity.getOrgId());
				if (CollectionUtils.isNotEmpty(doc)) {
					doc.forEach(data -> {
						if (StringUtils.isEmpty(docNames.toString())) {
							docNames.append(data.getDmsDocName());
							docIds.append(data.getDmsDocId());
						} else {
							docNames.append(MainetConstants.operator.COMMA + data.getDmsDocName());
							docIds.append(MainetConstants.operator.COMMA + data.getDmsDocId());
						}
					});
				}
				dto.setDmsDocId(docIds.toString());
				dto.setDmsDocName(docNames.toString());
				dto.setDmsDocsMetadataDetList(childList);
				list.add(dto);
			});
		}

		return list;
	}

}
