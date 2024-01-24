package com.abm.mainet.sfac.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.sfac.domain.CBBOMasterEntity;
import com.abm.mainet.sfac.domain.MilestoneCBBODetEntity;
import com.abm.mainet.sfac.domain.MilestoneDeliverablesEntity;
import com.abm.mainet.sfac.domain.MilestoneMasterEntity;
import com.abm.mainet.sfac.dto.MilestoneCBBODetDto;
import com.abm.mainet.sfac.dto.MilestoneDeliverablesDto;
import com.abm.mainet.sfac.dto.MilestoneMasterDto;
import com.abm.mainet.sfac.repository.CBBOMasterRepository;
import com.abm.mainet.sfac.repository.MilestoneCBBODetRepository;
import com.abm.mainet.sfac.repository.MilestoneDeliverablesRepository;
import com.abm.mainet.sfac.repository.MilestoneMasterRepository;

@Service
public class MilestoneEntryServiceImpl implements MilestoneEntryService {

	private static final Logger logger = Logger.getLogger(MilestoneEntryServiceImpl.class);

	@Autowired MilestoneMasterRepository milestoneMasterRepository;

	@Autowired MilestoneCBBODetRepository milestoneCBBODetRepository;

	@Autowired MilestoneDeliverablesRepository milestoneDeliverablesRepository;

	@Autowired
	private CBBOMasterRepository cbboMasterRepository;

	@Override
	public List<MilestoneMasterDto> getMilestoneDetails(Long iaId, String milestoneId) {
		// TODO Auto-generated method stub
		List<MilestoneMasterEntity> milestoneMasterEntities = new ArrayList<MilestoneMasterEntity>();
		List<MilestoneMasterDto> milestoneMasterDtos = new ArrayList<>();
		if(milestoneId!=null && milestoneId!="" && !milestoneId.isEmpty()) {
			milestoneMasterEntities = 	milestoneMasterRepository.findByIaIdAndMilestoneId(iaId, milestoneId);
		}
		else {
			milestoneMasterEntities = milestoneMasterRepository.findByIaId(iaId);
		}

		for(MilestoneMasterEntity milestoneMasterEntity : milestoneMasterEntities) {
			MilestoneMasterDto milestoneMasterDto = new MilestoneMasterDto();

			BeanUtils.copyProperties(milestoneMasterEntity, milestoneMasterDto);

			milestoneMasterDtos.add(milestoneMasterDto);
		}

		return milestoneMasterDtos;
	}

	@Override
	public MilestoneMasterDto saveAndUpdateApplication(MilestoneMasterDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			MilestoneMasterEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = milestoneMasterRepository.save(masEntity);

		} catch (Exception e) {
			logger.error("error occured while saving milestone entry master  details" + e);
			throw new FrameworkException("error occured while saving milestone entry master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private MilestoneMasterEntity mapDtoToEntity(MilestoneMasterDto mastDto) {
		// TODO Auto-generated method stub

		MilestoneMasterEntity milestoneMasterEntity = new MilestoneMasterEntity();
		List<MilestoneDeliverablesEntity> milestoneDeliverablesEntities = new ArrayList<>();
		List<MilestoneCBBODetEntity> milestoneCBBODetEntities = new ArrayList<>();
		BeanUtils.copyProperties(mastDto, milestoneMasterEntity);

		mastDto.getMilestoneDeliverablesDtos().forEach(dto -> {

			MilestoneDeliverablesEntity entity = new MilestoneDeliverablesEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setMilestoneMasterEntity(milestoneMasterEntity);
			milestoneDeliverablesEntities.add(entity);

		});
		mastDto.getMilestoneCBBODetDtos().forEach(dto -> {

			MilestoneCBBODetEntity entity = new MilestoneCBBODetEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setMilestoneMasterEntity(milestoneMasterEntity);
			CBBOMasterEntity cbboMasterEntity = 	cbboMasterRepository.findOne(dto.getCbboID());
			entity.setCbboName(cbboMasterEntity.getCbboName());
			milestoneCBBODetEntities.add(entity);

		});
		if(milestoneCBBODetEntities.size()>0)
			milestoneMasterEntity.setMilestoneCBBODetEntities(milestoneCBBODetEntities);
		if(milestoneDeliverablesEntities.size()>0)
			milestoneMasterEntity.setMilestoneDeliverablesEntities(milestoneDeliverablesEntities);

		return milestoneMasterEntity;
	}

	@Override
	public MilestoneMasterDto getDetailById(Long msId) {
		MilestoneMasterDto milestoneMasterDto = new MilestoneMasterDto();
		List<MilestoneDeliverablesDto> milestoneDeliverablesDtos = new ArrayList<>();
		List<MilestoneCBBODetDto> milestoneCBBODetDtos = new ArrayList<>();
		MilestoneMasterEntity milestoneMasterEntity = milestoneMasterRepository.findOne(msId);

		BeanUtils.copyProperties(milestoneMasterEntity, milestoneMasterDto);

		List<MilestoneDeliverablesEntity> milestoneDeliverablesEntities = milestoneDeliverablesRepository.findByMilestoneMasterEntity(milestoneMasterEntity);
		if(milestoneDeliverablesEntities.size()>0) {
			for(MilestoneDeliverablesEntity milestoneDeliverablesEntity : milestoneDeliverablesEntities) {
				MilestoneDeliverablesDto milestoneDeliverablesDto = new MilestoneDeliverablesDto();
				BeanUtils.copyProperties(milestoneDeliverablesEntity,milestoneDeliverablesDto);
				milestoneDeliverablesDtos.add(milestoneDeliverablesDto);
			}

		}
		List<MilestoneCBBODetEntity> milestoneCBBODetEntities = milestoneCBBODetRepository.findByMilestoneMasterEntity(milestoneMasterEntity);
		if(milestoneCBBODetEntities.size()>0) {
			for(MilestoneCBBODetEntity milestoneCBBODetEntity : milestoneCBBODetEntities) {
				MilestoneCBBODetDto milestoneCBBODetDto = new MilestoneCBBODetDto();
				BeanUtils.copyProperties(milestoneCBBODetEntity,milestoneCBBODetDto);
				milestoneCBBODetDtos.add(milestoneCBBODetDto);
			}

		}

		milestoneMasterDto.setMilestoneCBBODetDtos(milestoneCBBODetDtos);
		milestoneMasterDto.setMilestoneDeliverablesDtos(milestoneDeliverablesDtos);

		return milestoneMasterDto;
	}

}
