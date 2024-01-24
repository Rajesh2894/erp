package com.abm.mainet.sfac.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.domain.BlockAllocationDetailEntity;
import com.abm.mainet.sfac.domain.CBBOFiledStaffDetailsEntity;
import com.abm.mainet.sfac.dto.BlockAllocationDetailDto;
import com.abm.mainet.sfac.dto.CBBOFiledStaffDetailsDto;
import com.abm.mainet.sfac.repository.BlockAllocationDetailRepository;
import com.abm.mainet.sfac.repository.CBBOFiledStaffDetailsRepository;

@Service
public class CBBOFiledStaffDetailEntryServiceImpl implements CBBOFiledStaffDetailEntryService{

	@Autowired BlockAllocationDetailRepository blockAllocationDetailRepository;

	@Autowired CBBOFiledStaffDetailsRepository cbboFiledStaffDetailsRepository;

	private static final Logger logger = Logger.getLogger(CBBOFiledStaffDetailEntryServiceImpl.class);

	@Override
	public List<LookUp> getBlockDetails(Long masId) {
		// TODO Auto-generated method stub
		List<LookUp> blockList = new ArrayList<LookUp>();
		List<LookUp> blckList = CommonMasterUtility.getLevelData("SDB", 3,
				UserSession.getCurrent().getOrganisation());
		List<BlockAllocationDetailEntity> blockAllocationDetailEntities = blockAllocationDetailRepository.findByCbboId(masId);
		blockList = blckList.stream().filter(lookUp -> blockAllocationDetailEntities.stream().anyMatch(block -> block.getSdb3() == lookUp.getLookUpId()))
				.collect(Collectors.toList());
		return blockList;

	}

	@Override
	public List<CBBOFiledStaffDetailsDto> getCbboFieldStaffDetails(String name, Long block) {
		// TODO Auto-generated method stub
		List<CBBOFiledStaffDetailsDto> cbboFiledStaffDetailsDtos = new ArrayList<>();
		List<CBBOFiledStaffDetailsEntity> cbboFiledStaffDetailsEntities = new ArrayList<>();
		if(name!=null && !name.isEmpty() && block!=null && block!=0) {
			cbboFiledStaffDetailsEntities = cbboFiledStaffDetailsRepository.findBySdb3AndCbboExpertName(block, name);
		}
		else if( block!=null && block!=0) {
			cbboFiledStaffDetailsEntities = cbboFiledStaffDetailsRepository.findBySdb3(block);	
		}else if(name!=null && !name.isEmpty() ) {
			cbboFiledStaffDetailsEntities = cbboFiledStaffDetailsRepository.findByCbboExpertName( name);
		}

		for(CBBOFiledStaffDetailsEntity cbboFiledStaffDetailsEntity : cbboFiledStaffDetailsEntities) {
			CBBOFiledStaffDetailsDto cbboFiledStaffDetailsDto = new CBBOFiledStaffDetailsDto();
			BeanUtils.copyProperties(cbboFiledStaffDetailsEntity, cbboFiledStaffDetailsDto);;
			cbboFiledStaffDetailsDtos.add(cbboFiledStaffDetailsDto);
		}


		return cbboFiledStaffDetailsDtos;
	}

	@Override
	public CBBOFiledStaffDetailsDto saveAndUpdateApplication(CBBOFiledStaffDetailsDto mastDto) {
		try {
			logger.info("saveAndUpdateApplication started");
			CBBOFiledStaffDetailsEntity masEntity = mapDtoToEntity(mastDto);
			masEntity = cbboFiledStaffDetailsRepository.save(masEntity);

		} catch (Exception e) {
			logger.error("error occured while saving CBBO Field Staff Details" + e);
			throw new FrameworkException("error occured while saving CBBO Field Staff Details master  details" + e);
		}
		logger.info("saveAndUpdateApplication End");
		return mastDto;
	}

	private CBBOFiledStaffDetailsEntity mapDtoToEntity(CBBOFiledStaffDetailsDto mastDto) {
		// TODO Auto-generated method stub

		CBBOFiledStaffDetailsEntity cbboFiledStaffDetailsEntity = new CBBOFiledStaffDetailsEntity();

		BeanUtils.copyProperties(mastDto, cbboFiledStaffDetailsEntity);

		return cbboFiledStaffDetailsEntity;
	}

	@Override
	public CBBOFiledStaffDetailsDto getDetailById(Long fsdId) {
		// TODO Auto-generated method stub
		CBBOFiledStaffDetailsDto cbboFiledStaffDetailsDto = new CBBOFiledStaffDetailsDto();
		CBBOFiledStaffDetailsEntity cbboFiledStaffDetailsEntity = cbboFiledStaffDetailsRepository.findOne(fsdId);
		BeanUtils.copyProperties(cbboFiledStaffDetailsEntity, cbboFiledStaffDetailsDto);
		return cbboFiledStaffDetailsDto;
	}

	@Override
	public BlockAllocationDetailDto getSD(Long block) {
		BlockAllocationDetailEntity blockAllocationDetailEntity =  blockAllocationDetailRepository.findBySdb3(block);
		BlockAllocationDetailDto blockAllocationDetailDto = new BlockAllocationDetailDto();
		BeanUtils.copyProperties(blockAllocationDetailEntity, blockAllocationDetailDto);
		return blockAllocationDetailDto;
	}
}
