package com.abm.mainet.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.property.domain.PropertyPenltyEntity;
import com.abm.mainet.property.dto.PropertyPenltyDto;
import com.abm.mainet.property.repository.PropertyPenltyRepository;

@Service
public class PropertyPenltyServiceImpl implements PropertyPenltyService {

    @Autowired
    private PropertyPenltyRepository propertyPenltyRepository;

    @Override
    @Transactional
    public PropertyPenltyDto calculateExistingSurcharge(String propNo, Long finYearId, Long orgId) {
        PropertyPenltyDto propertyPenltyDto = null;

        PropertyPenltyEntity penEnt = propertyPenltyRepository.getPropertyPenltyByPropNoAndFinYearId(propNo, finYearId, orgId);
        if (penEnt != null) {
            propertyPenltyDto = new PropertyPenltyDto();
            BeanUtils.copyProperties(penEnt, propertyPenltyDto);

        }
        return propertyPenltyDto;

    }

    @Override
    @Transactional
    public void savePropertyPenlty(String propNo, Long finYearId, Long orgId, Long userId, String ipAddress,
            double actualAmt, double pendingAmt,String parentPropNo) {
        PropertyPenltyEntity propertyPenltyEntity = new PropertyPenltyEntity();
        propertyPenltyEntity.setPropNo(propNo);
        propertyPenltyEntity.setPendingAmount(pendingAmt);
        propertyPenltyEntity.setActualAmount(actualAmt);
        propertyPenltyEntity.setFinYearId(finYearId);
        propertyPenltyEntity.setParentPropNo(parentPropNo);
        propertyPenltyEntity.setCreatedBy(userId);
        propertyPenltyEntity.setOrgId(orgId);
        propertyPenltyEntity.setLgIpMac(ipAddress);
        propertyPenltyEntity.setCreatedDate(new Date());
        propertyPenltyRepository.save(propertyPenltyEntity);
    }

    @Override
    public void inActiveSurchargeByPropNoAndFinYear(String activeFlag, Long finYearId, Long orgId, String propNo) {
    	try {
    		propertyPenltyRepository.inActiveSurchargeByPropNoAndFinYear(activeFlag, finYearId, orgId, propNo);
    	}catch (Exception e) {
		}

    }

	@Override
	@Transactional
	public void updatePropertyPenalty(PropertyPenltyDto penaltyDto, String ipAddress, Long userId) {
		PropertyPenltyEntity penEnt = propertyPenltyRepository.getPropertyPenltyByPropNoAndFinYearId(
				penaltyDto.getPropNo(), penaltyDto.getFinYearId(), penaltyDto.getOrgId());
		
		if (penEnt != null) {
			penEnt.setActualAmount(penaltyDto.getActualAmount());
			penEnt.setPendingAmount(penaltyDto.getPendingAmount());
			penEnt.setUpdatedDate(new Date());
			penEnt.setUpdatedBy(userId);
			penEnt.setLgIpMacUpd(ipAddress);
			penEnt.setActiveFlag(penaltyDto.getActiveFlag());
			propertyPenltyRepository.save(penEnt);
		}

	}

	@Override
	public PropertyPenltyDto getLastClaculatedSurcharge(String propNo, Long orgId) {
		PropertyPenltyDto propertyPenltyDto = null;

		List<PropertyPenltyEntity> penEntList = propertyPenltyRepository.getPropertyPenltyByPropNo(propNo, orgId);
		if (CollectionUtils.isNotEmpty(penEntList)) {
			propertyPenltyDto = new PropertyPenltyDto();
			BeanUtils.copyProperties(penEntList.get(penEntList.size() - 1), propertyPenltyDto);

		}
		return propertyPenltyDto;

	}
    @Override
    @Transactional
    public List<PropertyPenltyDto> calculateExistingSurchargeByParentPropNo(String parentPropNo, Long finYearId, Long orgId) {
    	List<PropertyPenltyDto> propertyPenltyDtoList = new ArrayList<PropertyPenltyDto>();;

        List<PropertyPenltyEntity> penEntList = propertyPenltyRepository.getPropertyPenltyByParentPropNoAndFinYearId(parentPropNo, finYearId, orgId);
        if (CollectionUtils.isNotEmpty(penEntList)) {
        	penEntList.forEach(penEnt ->{
        		PropertyPenltyDto propertyPenltyDto = new PropertyPenltyDto();
                BeanUtils.copyProperties(penEnt, propertyPenltyDto);
                propertyPenltyDtoList.add(propertyPenltyDto);
        	});

        }
        return propertyPenltyDtoList;

    }
    
    
    @Override
	public Double getPropertyPenltyByGroupPropNos(List<String> propNoList, Long finYearId, Long orgId) {
		return propertyPenltyRepository.getPropertyPenltyByGroupPropNos(propNoList, finYearId, orgId);
	}
	
	
}
