package com.abm.mainet.rnl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDetDto;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.service.DepartmentalMetadetaService;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dao.IRnlMetadataDao;
import com.abm.mainet.rnl.domain.EstatePropertyEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("RnlMetaService")
public class RnlMetadataServiceImpl implements DepartmentalMetadetaService {

	@Autowired
	private IRnlMetadataDao rnlDao;

	@Autowired
	private IAttachDocsService attachDocsService;

	@Override
	@Transactional
	public List<DmsDocsMetadataDto> getDepartmentalMetadeta(String colname, String Colvalue, Long orgId, Long deptId,
			Map<String, Object> argumentsMap) {
		List<DmsDocsMetadataDto> list = new ArrayList<DmsDocsMetadataDto>();
		List<EstatePropertyEntity> rnlList = rnlDao.getRnlDetails(colname, Colvalue, orgId, deptId, argumentsMap);
		if (CollectionUtils.isNotEmpty(rnlList)) {
			rnlList.forEach(entity -> {
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

				List<AttachDocs> docs = attachDocsService.findByCode(orgId, entity.getCode());
				if (CollectionUtils.isNotEmpty(docs)) {
					docs.forEach(data -> {
						if (StringUtils.isNotEmpty(data.getDmsDocName())) {
							if (StringUtils.isEmpty(docNames.toString())) {
								docNames.append(data.getDmsDocName());
								docIds.append(data.getDmsDocId());
							} else {
								docNames.append(MainetConstants.operator.COMMA + data.getDmsDocName());
								docIds.append(MainetConstants.operator.COMMA + data.getDmsDocId());
							}
						}
					});
				}
				if (StringUtils.isNotEmpty(docIds.toString())) {
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
