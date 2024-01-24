package com.abm.mainet.water.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDetDto;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.service.DepartmentalMetadetaService;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.IWaterMetadataDao;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("WaterMetaService")
public class WaterMetadataServiceImpl implements DepartmentalMetadetaService {

	@Autowired
	private IWaterMetadataDao waterDao;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private IAttachDocsService attachDocsService;

	@Override
	@Transactional
	public List<DmsDocsMetadataDto> getDepartmentalMetadeta(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap) {
		List<DmsDocsMetadataDto> list = new ArrayList<DmsDocsMetadataDto>();
		List<TbKCsmrInfoMH> DetailList = waterDao.getWaterDetails(colname, Colvalue, orgId, deptId, argumentsMap);
		if (CollectionUtils.isNotEmpty(DetailList)) {
			DetailList.forEach(entity -> {
				List<DmsDocsMetadataDetDto> childList = new ArrayList<DmsDocsMetadataDetDto>();
				DmsDocsMetadataDto dto = new DmsDocsMetadataDto();
				ObjectMapper mapper = Utility.getMapper();
				String jsonData = null;
				Map<?, ?> map = null;

				try {
					jsonData = mapper.writeValueAsString(entity);
					map = mapper.readValue(jsonData, Map.class);

				} catch (Exception e) {
					throw new FrameworkException(e);
				}

				map.forEach((key, value) -> {
					DmsDocsMetadataDetDto childdto = new DmsDocsMetadataDetDto();
					childdto.setMtKey(String.valueOf(key));
					childdto.setMtVal(String.valueOf(value));
					childList.add(childdto);
				});

				StringBuilder docNames = new StringBuilder();
				StringBuilder docIds = new StringBuilder();
				if (entity.getCsCcn() != null) {
					List<AttachDocs> docs = attachDocsService.findByCode(orgId,
							MainetConstants.DMS_LIST + MainetConstants.FILE_PATH_SEPARATOR + entity.getCsCcn()); // water bill payment documents
																													
					if (CollectionUtils.isNotEmpty(docs)) {
						for (AttachDocs data : docs) {
							if (StringUtils.isEmpty(docNames.toString())) {
								docNames.append(data.getDmsDocName());
								docIds.append(data.getDmsDocId());
							} else {
								docNames.append(MainetConstants.operator.COMMA + data.getDmsDocName());
								docIds.append(MainetConstants.operator.COMMA + data.getDmsDocId());
							}
						}
					}
				}

				if (entity.getApplicationNo() != null) {
					List<AttachDocs> docss = attachDocsService.findByCode(orgId,
							MainetConstants.DMS_LIST + MainetConstants.FILE_PATH_SEPARATOR + entity.getApplicationNo()); // water LOI documents																															

					if (CollectionUtils.isNotEmpty(docss)) {
						for (AttachDocs data : docss) {
							if (StringUtils.isEmpty(docNames.toString())) {
								docNames.append(data.getDmsDocName());
								docIds.append(data.getDmsDocId());
							} else {
								docNames.append(MainetConstants.operator.COMMA + data.getDmsDocName());
								docIds.append(MainetConstants.operator.COMMA + data.getDmsDocId());
							}
						}
					}

					List<CFCAttachment> doc = iChecklistVerificationService
							.getDocumentUploadedForAppId(entity.getApplicationNo(), entity.getOrgId()); // water checklist documents
																										
					if (CollectionUtils.isNotEmpty(doc)) {
						for (CFCAttachment data : doc) {
							if (StringUtils.isEmpty(docNames.toString())) {
								docNames.append(data.getDmsDocName());
								docIds.append(data.getDmsDocId());
							} else {
								docNames.append(MainetConstants.operator.COMMA + data.getDmsDocName());
								docIds.append(MainetConstants.operator.COMMA + data.getDmsDocId());
							}
						}
					}
				}
				if (StringUtils.isNotEmpty(docNames.toString())) {
					dto.setDmsDocId(docIds.toString());
					dto.setDmsDocName(docNames.toString());
				}
				dto.setDmsDocsMetadataDetList(childList);
				list.add(dto);

			});
		}
		return list;
	}

}
