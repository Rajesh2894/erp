/**
 * 
 */
package com.abm.mainet.sfac.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dao.StateInformationDao;
import com.abm.mainet.sfac.domain.StateInformationEntity;
import com.abm.mainet.sfac.dto.StateInformationDto;
import com.abm.mainet.sfac.repository.StateInformationRepository;

/**
 * @author pooja.maske
 *
 */
@Service
public class StateInformationMasterServiceImpl implements StateInformationMasterService {
	private static final Logger log = Logger.getLogger(StateInformationMasterServiceImpl.class);

	@Autowired
	private FPOMasterService fPOMasterService;

	@Autowired
	private StateInformationRepository stateInfoRepo;

	@Autowired
	private StateInformationDao stateInformationDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.StateInformationMasterService#
	 * saveAndUpdateApplication(com.abm.mainet.sfac.dto.StateInformationDto)
	 */
	@Override
	@Transactional
	public StateInformationDto saveAndUpdateApplication(StateInformationDto mastDto) {
		log.info("saveAndUpdateApplication started");
		try {
			StateInformationEntity entity = new StateInformationEntity();
			LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(mastDto.getState(), mastDto.getOrgId());
			String stateCode = fPOMasterService.getPrefixOtherValue(lookUp.getLookUpId(), mastDto.getOrgId());
			mastDto.setStShortCode(stateCode);
			mastDto.setStateCode(Long.valueOf(lookUp.getLookUpCode()));
			LookUp dist = CommonMasterUtility.getHierarchicalLookUp(mastDto.getDistrict(), mastDto.getOrgId());
			mastDto.setDistCode(Long.valueOf(dist.getLookUpCode()));
			BeanUtils.copyProperties(mastDto, entity);
			stateInfoRepo.save(entity);
		} catch (Exception e) {
			log.error("Error occured while saving state information details " + e);
		}
		log.info("saveAndUpdateApplication ended");
		return mastDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.StateInformationMasterService#
	 * getStateInfoDetailsByIds(java.lang.Long, java.lang.Long, long)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public List<StateInformationDto> getStateInfoDetailsByIds(Long state, Long district, Long orgId) {
		List<StateInformationDto> dtoList = new ArrayList<>();
		List<StateInformationEntity> entity = stateInformationDao.getStateInfoDetailsByIds(state, district, orgId);
		for (StateInformationEntity infoEntity : entity) {
			StateInformationDto dto = new StateInformationDto();
			LookUp lookup = CommonMasterUtility.getHierarchicalLookUp(infoEntity.getState());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
				dto.setStateName(lookup.getDescLangFirst());
			else
				dto.setStateName(lookup.getDescLangSecond());
			LookUp dist = CommonMasterUtility.getHierarchicalLookUp(infoEntity.getDistrict());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
				dto.setDistName(dist.getDescLangFirst());
			else
				dto.setDistName(dist.getDescLangSecond());
			if (infoEntity.getAreaType() != null) {
				LookUp look = CommonMasterUtility.getNonHierarchicalLookUpObject(infoEntity.getAreaType());
				if (look != null) {
					if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
						dto.setAreaTypeDesc(look.getDescLangFirst());
					else
						dto.setAreaTypeDesc(look.getDescLangSecond());
				}
			}
			BeanUtils.copyProperties(infoEntity, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.sfac.service.StateInformationMasterService#findById(java.lang.
	 * Long)
	 */
	@Override
	public StateInformationDto findById(Long stId) {
		StateInformationDto dto = new StateInformationDto();
		StateInformationEntity entity = stateInfoRepo.findOne(stId);
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.sfac.service.StateInformationMasterService#
	 * getStateInfoByDistId(java.lang.Long, long)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public StateInformationDto getStateInfoByDistId(Long sdb2, Long orgId) {
		StateInformationDto dto = new StateInformationDto();
		try {
			log.info("getStateInfoByDistId Started");
			StateInformationEntity entity = stateInfoRepo.getStateInfoByDistId(sdb2);
			if (entity !=null) {
			if (entity.getTribalDist() != null) {
			LookUp tribalDist = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getTribalDist());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
				dto.setTirbalDist(tribalDist.getDescLangFirst());
			else
				dto.setTirbalDist(tribalDist.getDescLangSecond());
				}
			if (entity.getAspirationalDist() != null) {
			LookUp aspirationalDist = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getAspirationalDist());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
				dto.setAspirationallDist(aspirationalDist.getDescLangFirst());
			else
				dto.setAspirationallDist(aspirationalDist.getDescLangSecond());
			}
			if (entity.getAreaType() != null) {
			LookUp areayType = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getAreaType());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
				dto.setAreaTypeValue(areayType.getDescLangFirst());
			else
				dto.setAreaTypeValue(areayType.getDescLangSecond());
			}
			if (entity.getZone() != null) {
			LookUp region = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getZone());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
				dto.setRegion(region.getDescLangFirst());
			else
				dto.setRegion(region.getDescLangSecond());
			}
			if (entity.getOdop() != null) {
			LookUp odop = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getOdop());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID)
				dto.setOdopValue(odop.getDescLangFirst());
			else
				dto.setOdopValue(odop.getDescLangSecond());
			}
			BeanUtils.copyProperties(entity, dto);
			}
		} catch (Exception e) {
			log.error("Error occured while fetching state inforamtion in service " + e);
		}
		log.info("getStateInfoByDistId ended");
		return dto;
	}

	/* (non-Javadoc)
	 * @see com.abm.mainet.sfac.service.StateInformationMasterService#checkSpecialCateExist(java.lang.Long)
	 */
	@Override
	public boolean checkSpecialCateExist(Long district) {
		Boolean result = false;
		result = stateInfoRepo.checkSpecialCateExist(district);
		return result;
	}
}
