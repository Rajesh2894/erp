package com.abm.mainet.property.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.property.domain.PropertyTransferMasterEntity;
import com.abm.mainet.property.domain.PropertyTransferOwnerEntity;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.repository.PropertyTransferRepository;

@Service
public class PropertyTransferServiceImpl implements PropertyTransferService {

    @Autowired
    private PropertyTransferRepository propertyTransferRepository;

    @Override
    public void saveAndUpadPropTransferMast(PropertyTransferMasterDto propTranMstDto) {
        final String ipAddress = propTranMstDto.getLgIpMac();
        PropertyTransferMasterEntity provAssetMst = new PropertyTransferMasterEntity();
        if (propTranMstDto.getTransferMstId() == 0) {
            propTranMstDto.setCreatedBy(propTranMstDto.getEmpId());
            propTranMstDto.setLgIpMac(ipAddress);
            propTranMstDto.setCreatedDate(new Date());
            propTranMstDto.setOrgId(propTranMstDto.getOrgId());
            propTranMstDto.setStatus(MainetConstants.STATUS.ACTIVE);
        } else {
            propTranMstDto.setUpdatedBy(propTranMstDto.getEmpId());
            propTranMstDto.setLgIpMacUpd(ipAddress);
            propTranMstDto.setUpdatedDate(new Date());
        }
        BeanUtils.copyProperties(propTranMstDto, provAssetMst);

        final List<PropertyTransferOwnerEntity> provAsseOwnEntList = new ArrayList<>();
        for (PropertyTransferOwnerDto provOwndto : propTranMstDto.getPropTransferOwnerList()) {
            PropertyTransferOwnerEntity provAsseOwnerEnt = new PropertyTransferOwnerEntity();
            BeanUtils.copyProperties(provOwndto, provAsseOwnerEnt);
            provAsseOwnerEnt.setTbAsTransferrMast(provAssetMst);
            if (provOwndto.getOwnerDtlId() == 0) {
                provAsseOwnerEnt.setCreatedBy(propTranMstDto.getEmpId());
                provAsseOwnerEnt.setLgIpMac(ipAddress);
                provAsseOwnerEnt.setAssNo(provAssetMst.getProAssNo());
                provAsseOwnerEnt.setCreatedDate(new Date());
                provAsseOwnerEnt.setActive(MainetConstants.STATUS.ACTIVE);
                provAsseOwnerEnt.setStartDate(new Date());
                provAsseOwnerEnt.setOrgId(propTranMstDto.getOrgId());
                provAsseOwnerEnt.setApmApplicationId(provAssetMst.getApmApplicationId());
                provAsseOwnerEnt.setSmServiceId(provAssetMst.getSmServiceId());
                provAsseOwnerEnt.setType("O");// Owner Type
            } else {
                provAsseOwnerEnt.setUpdatedBy(propTranMstDto.getEmpId());
                provAsseOwnerEnt.setLgIpMacUpd(ipAddress);
                provAsseOwnerEnt.setUpdatedDate(new Date());
            }
            provAsseOwnEntList.add(provAsseOwnerEnt);
        }
        provAssetMst.setPropTransferOwnerList(provAsseOwnEntList);
        propertyTransferRepository.save(provAssetMst);
    }

    @Override
    @Transactional
    public PropertyTransferMasterDto getPropTransferMstByAppId(Long orgId, Long applicationId) {
        List<PropertyTransferOwnerDto> propTranOwnList = new ArrayList<>();
        PropertyTransferMasterEntity propTransEnt = propertyTransferRepository.getPropTransferMstByAppId(orgId, applicationId);
        PropertyTransferMasterDto propTransDto = new PropertyTransferMasterDto();
        BeanUtils.copyProperties(propTransEnt, propTransDto);
        propTransEnt.getPropTransferOwnerList().forEach(ownerEnt -> {
            PropertyTransferOwnerDto ownerDto = new PropertyTransferOwnerDto();
            BeanUtils.copyProperties(ownerEnt, ownerDto);
            propTranOwnList.add(ownerDto);
        });
        propTransDto.setPropTransferOwnerList(propTranOwnList);
        return propTransDto;
    }

    @Override
    public Long getApplicationIdByPropNo(String propNo, Long orgId) {
        return propertyTransferRepository.getApplicationIdByPropNo(propNo, orgId);
    }

    @Override
    public String getPropertyNoByAppId(Long orgId, Long applicationId, Long serviceId) {
        return propertyTransferRepository.getPropertyNoByAppId(orgId, applicationId,
                serviceId);
    }

    @Override
    public List<Date> getActualTransferDateByPropNo(Long orgId, String propNo) {
        return propertyTransferRepository.getActualTransferDateByPropNo(orgId, propNo);
    }
    
	@Override
	@Transactional
	public List<Long> getAllApplicationIdsByPropNo(String propNo, Long orgId) {
		return propertyTransferRepository.getAllApplicationIdsByPropNo(propNo, orgId);
	}

	@Override
	@Transactional
	public List<Long> getAllApplicationIdsByPropNoNFlat(String propNo, String flatNo, Long orgId) {
		return propertyTransferRepository.getAllApplicationIdsByPropNoNFlat(propNo, flatNo, orgId);
	}
    
}
