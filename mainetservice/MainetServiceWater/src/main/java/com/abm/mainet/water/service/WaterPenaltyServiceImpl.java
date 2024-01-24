/**
 * 
 */
package com.abm.mainet.water.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.water.domain.WaterPenaltyEntity;
import com.abm.mainet.water.domain.WaterPenaltyHistoryEntity;
import com.abm.mainet.water.dto.WaterPenaltyDto;
import com.abm.mainet.water.repository.WaterPenaltyRepository;

/**
 * @author cherupelli.srikanth
 *
 */
@Service
public class WaterPenaltyServiceImpl implements IWaterPenaltyService{

    @Autowired
    WaterPenaltyRepository waterPenaltyRepository;

    @Autowired
    private AuditService auditService;
    
    @Override
    public WaterPenaltyDto getWaterPenaltyByCCNOByFinId(String ccnNo, Long finYearId, Long orgId) {
	
	WaterPenaltyDto penaltyDto =null;
	List<WaterPenaltyEntity> entity = waterPenaltyRepository.getWaterPenaltyByCCnNo(ccnNo, orgId);
	if(CollectionUtils.isNotEmpty(entity) && entity.get(entity.size() - 1) != null) {
		if(StringUtils.isBlank(String.valueOf(entity.get(entity.size() - 1).getActiveFlag()).trim())) {
			 penaltyDto = new WaterPenaltyDto();
			    BeanUtils.copyProperties(entity.get(entity.size() - 1), penaltyDto);
		}
	}
	return penaltyDto;
    }

    @Override
    @Transactional
	public void saveWaterPenalty(WaterPenaltyDto waterPenaltyDto) {
		try {
			WaterPenaltyEntity entity = new WaterPenaltyEntity();
			BeanUtils.copyProperties(waterPenaltyDto, entity);
			entity.setPendingAmount(Math.round(waterPenaltyDto.getPendingAmount()));
			entity.setActualAmount(Math.round(waterPenaltyDto.getActualAmount()));
			waterPenaltyRepository.save(entity);
			WaterPenaltyHistoryEntity historyEntity = new WaterPenaltyHistoryEntity();
			historyEntity.sethStatus(MainetConstants.FlagA);
			auditService.createHistory(entity, historyEntity);
		} catch (Exception exception) {
			throw new FrameworkException(exception, "Problem while saving surcharge");
		}
	}

    @Override
    public List<WaterPenaltyDto> getWaterPenaltyByconIds(List<Long> conIds, Long orgId) {
	
	List<String> conList = new ArrayList<String>(conIds.size()); 
		for (Long conNo : conIds) { 
		    conList.add(String.valueOf(conNo)); 
		}
	List<WaterPenaltyEntity> entityList = waterPenaltyRepository.getWaterPenaltyByConIds(conList, orgId);
	List<WaterPenaltyDto> penaltyDtoList = new ArrayList<>();
	
	if(CollectionUtils.isNotEmpty(entityList)) {
	    entityList.forEach(entity ->{
	    	if(StringUtils.isBlank(String.valueOf(entity.getActiveFlag()).trim())) {
	    		 WaterPenaltyDto penaltyDto = new WaterPenaltyDto();
	    			BeanUtils.copyProperties(entity, penaltyDto);
	    			penaltyDtoList.add(penaltyDto);
	    	}
	    });
	}
	return penaltyDtoList;
    }

    @Override
    @Transactional
    public void updateWaterPenalty(WaterPenaltyDto waterPenaltyDto) {
	
	WaterPenaltyEntity entity = new WaterPenaltyEntity();
	waterPenaltyDto.setUpdatedDate(new Date());
	BeanUtils.copyProperties(waterPenaltyDto, entity);
	entity.setPendingAmount(Math.round(waterPenaltyDto.getPendingAmount()));
	entity.setActualAmount(Math.round(waterPenaltyDto.getActualAmount()));
	waterPenaltyRepository.save(entity);
	WaterPenaltyHistoryEntity historyEntity = new WaterPenaltyHistoryEntity();
	historyEntity.sethStatus(MainetConstants.FlagU);
		auditService.createHistory(entity, historyEntity);
    }

	@Override
	public WaterPenaltyDto getWaterPenaltyByBmIdNoAndOrgId(Long bmIdNo, Long orgId) {
		WaterPenaltyDto penaltyDto =null;
		WaterPenaltyEntity entity = waterPenaltyRepository.findByBmIdNoAndOrgId(bmIdNo, orgId);
		if(entity != null) {
			if(StringUtils.isBlank(String.valueOf(entity.getActiveFlag()).trim())) {
				 penaltyDto = new WaterPenaltyDto();
				    BeanUtils.copyProperties(entity, penaltyDto);
			}
		}
		return penaltyDto;
	}
	
	@Override
	public WaterPenaltyDto getWaterPenaltyHistoryByBmIdNoAndOrgId(Long bmIdNo, Long orgId) {
		WaterPenaltyDto penaltyDto =null;
		List<WaterPenaltyHistoryEntity> entityList = waterPenaltyRepository.findHistoryByBmIdNoAndOrgId(bmIdNo, orgId);
		if(CollectionUtils.isNotEmpty(entityList)) {
			WaterPenaltyHistoryEntity entity = entityList.get(0);
			if(StringUtils.isBlank(String.valueOf(entity.getActiveFlag()).trim())) {
				 penaltyDto = new WaterPenaltyDto();
				    BeanUtils.copyProperties(entity, penaltyDto);
			}
		}
		return penaltyDto;
	}

	@Override
	public List<WaterPenaltyDto> getWaterPenaltyHistoryByCsIdnoAndOrgId(String csIdNo, Long orgId) {
	List<WaterPenaltyHistoryEntity> entityList = waterPenaltyRepository.findHistoryByCsIdnAndOrgId(csIdNo, orgId);
	List<WaterPenaltyDto> penaltyDtoList = new ArrayList<>();
	if(CollectionUtils.isNotEmpty(entityList)) {
	    entityList.forEach(entity ->{
	    	if(StringUtils.isBlank(String.valueOf(entity.getActiveFlag()).trim())) {
	    		 WaterPenaltyDto penaltyDto = new WaterPenaltyDto();
	    			BeanUtils.copyProperties(entity, penaltyDto);
	    			penaltyDtoList.add(penaltyDto);
	    	}
	    });
	}
	return penaltyDtoList;
	}

	@Override
	public void inactivePenalty(Long bmIdno, Long orgId) {
		waterPenaltyRepository.inactivePenaltyByBmIdno(orgId, bmIdno);
		
	}

	@Override

	public List<WaterPenaltyDto> getWaterPenaltyByCsIdnoAndOrgIdAndBmIdno(String csIdNo, Long orgId,Long bmIdno) {
		List<WaterPenaltyDto> penaltyDtoList =null;
		List<WaterPenaltyEntity> entityList = waterPenaltyRepository.findByBmIdnoAndOrgId(csIdNo, orgId, bmIdno);
		if(CollectionUtils.isNotEmpty(entityList)) {
			penaltyDtoList = new ArrayList<WaterPenaltyDto>();
			for (WaterPenaltyEntity waterPenaltyEntity : entityList) {
				if(StringUtils.isBlank(String.valueOf(waterPenaltyEntity.getActiveFlag()).trim())) {
					WaterPenaltyDto penaltyDto = new WaterPenaltyDto();
				    BeanUtils.copyProperties(waterPenaltyEntity, penaltyDto);
				    penaltyDtoList.add(penaltyDto);
				}
			}
		}
		return penaltyDtoList;
	
	}

	@Override
	public List<WaterPenaltyDto> getWaterPenaltyCurrBillAmountByCsIdnoAndOrgId(String csIdNo, Long orgId) {
		List<WaterPenaltyDto> penaltyDtoList =null;
		List<WaterPenaltyEntity> entityList = waterPenaltyRepository.findByCurrGenAmount(csIdNo, orgId);
		if(CollectionUtils.isNotEmpty(entityList)) {
			penaltyDtoList = new ArrayList<WaterPenaltyDto>();
			for (WaterPenaltyEntity waterPenaltyEntity : entityList) {
				if(StringUtils.isBlank(String.valueOf(waterPenaltyEntity.getActiveFlag()).trim())) {
					WaterPenaltyDto penaltyDto = new WaterPenaltyDto();
				    BeanUtils.copyProperties(waterPenaltyEntity, penaltyDto);
				    penaltyDtoList.add(penaltyDto);
				}
			}
		}
		return penaltyDtoList;
	}

	@Override
	public List<WaterPenaltyDto> getWaterPenaltyByBmNoIds(List<TbBillMas> billMasList, long orgId) {
		 List<Long> bmNoList = new ArrayList<Long>(billMasList.size());
	        for (TbBillMas mas : billMasList) {
	            if(mas!=null) {
	                bmNoList.add(mas.getBmIdno());     
	            }
	        }
	    List<WaterPenaltyEntity> entityList = waterPenaltyRepository.getWaterPenaltyByBmIdNos(bmNoList, orgId);
	    List<WaterPenaltyDto> penaltyDtoList = new ArrayList<>();
	    
	    if(CollectionUtils.isNotEmpty(entityList)) {
	        entityList.forEach(entity ->{
	            /*if(StringUtils.isBlank(String.valueOf(entity.getActiveFlag()).trim())) {*/
	                 WaterPenaltyDto penaltyDto = new WaterPenaltyDto();
	                    BeanUtils.copyProperties(entity, penaltyDto);
	                    penaltyDtoList.add(penaltyDto);
	                /* } */
	        });
	    }
	    return penaltyDtoList;
	}

}
