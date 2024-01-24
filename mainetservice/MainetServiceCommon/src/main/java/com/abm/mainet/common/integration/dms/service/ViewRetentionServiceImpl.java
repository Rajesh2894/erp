package com.abm.mainet.common.integration.dms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.dao.IViewRetentionDao;
import com.abm.mainet.common.integration.dms.domain.DocRetentionEntity;
import com.abm.mainet.common.integration.dms.dto.DmsRetentionDetDto;
import com.abm.mainet.common.integration.dms.dto.DmsRetentionDto;
import com.abm.mainet.common.integration.dms.ui.model.ViewRetentionModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

@Service
public class ViewRetentionServiceImpl implements IViewRetentionService {

	private static final Logger LOGGER = Logger.getLogger(ViewRetentionServiceImpl.class);

	@Autowired
	private IViewRetentionDao dao;

	@Override
	public List<DmsRetentionDto> getMetadataDetails(String deptId, String metadataId, String metadataValue,
			Long roleId, Long orgid, Long zone, Long ward, Long mohalla, String docRefNo, String callType,
			ViewRetentionModel model, Long docType, String complainNo, String status) {
		List<DocRetentionEntity> entityList = new ArrayList<>();
		if (StringUtils.isEmpty(complainNo)) {
			if (StringUtils.isEmpty(metadataId) || metadataId.equals("0")) {
				List<LookUp> metadataList = CommonMasterUtility.getChildLookUpsFromParentId(Long.valueOf(deptId));
				if (!metadataList.isEmpty()) {
					metadataId = String.valueOf(metadataList.get(0).getLookUpId());
					metadataValue = MainetConstants.CommonConstants.BLANK;
				}
			}
			entityList = dao.getMetadataDetails(deptId, metadataId, metadataValue, roleId, orgid, zone, ward, mohalla,
					docRefNo, docType);
		} else {
			entityList = dao.getApprovalMetadataDetails(complainNo, status, orgid);
		}

		List<DocRetentionEntity> newEntityList = new ArrayList<>();

		entityList.forEach(entity -> {
			if (entity.getStorageType() != null && entity.getStorageType().equals(MainetConstants.Dms.DMS)) {
				newEntityList.add(entity);
			}
		});
		List<DmsRetentionDto> dtoList = new ArrayList<DmsRetentionDto>();
		newEntityList.forEach(data -> {
			DmsRetentionDto dto = new DmsRetentionDto();
			BeanUtils.copyProperties(data, dto);
			List<DmsRetentionDetDto> detDtoList = new ArrayList<>();
			data.getDocRetentionDetEntity().forEach(childData -> {
				LookUp lookup = CommonMasterUtility.getHierarchicalLookUp(Long.valueOf(childData.getMtKey()),
						dto.getOrgId().getOrgid());
				childData.setMtKey(lookup.getLookUpDesc());
				DmsRetentionDetDto detDto = new DmsRetentionDetDto();
				BeanUtils.copyProperties(childData, detDto);
				detDtoList.add(detDto);
			});
			dto.setDmsRetentionDetDtoList(detDtoList);
			dtoList.add(dto);
		});
		Map<String, Object> argumentsMap = new HashMap<>();
		if (zone != null && !zone.toString().equals("0")) {
			argumentsMap.put("zone", zone);
		}
		if (ward != null && !ward.toString().equals("0")) {
			argumentsMap.put("ward", ward);
		}
		if (mohalla != null && !mohalla.toString().equals("0")) {
			argumentsMap.put("mohalla", mohalla);
		}
		return dtoList;
	}

}
