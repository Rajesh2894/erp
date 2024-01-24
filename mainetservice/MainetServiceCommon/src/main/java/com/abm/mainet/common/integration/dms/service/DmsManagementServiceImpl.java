package com.abm.mainet.common.integration.dms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.integration.dms.domain.DocManagementDetEntity;
import com.abm.mainet.common.integration.dms.domain.DocManagementEntity;
import com.abm.mainet.common.integration.dms.dto.DmsManagementDto;
import com.abm.mainet.common.integration.dms.repository.IDocManagementRepository;

@Service
public class DmsManagementServiceImpl implements IDmsManagementService {
	
	   @Autowired
	   IDocManagementRepository docManagementRepository;
	   
	@Override
	@Transactional
	public DmsManagementDto saveDms(DmsManagementDto dmsMetadaDto) {
		DocManagementEntity entity = new DocManagementEntity();
		List<DocManagementDetEntity> detEntityList = new ArrayList<DocManagementDetEntity>();
		BeanUtils.copyProperties(dmsMetadaDto, entity);
		
		dmsMetadaDto.getDmsDocsMetadataDetList().forEach(data -> {
			DocManagementDetEntity detEntity = new DocManagementDetEntity();
			BeanUtils.copyProperties(data, detEntity);
			detEntity.setDocManagementEntity(entity);
			detEntityList.add(detEntity);
		});
		
		entity.setDocManagementDetEntity(detEntityList);
		docManagementRepository.save(entity);
		BeanUtils.copyProperties(entity, dmsMetadaDto);
		return dmsMetadaDto;
	}

	@Override
	public void updateManagementRecord(Long orgId) {
		docManagementRepository.updateRecord(orgId);
	}
}
