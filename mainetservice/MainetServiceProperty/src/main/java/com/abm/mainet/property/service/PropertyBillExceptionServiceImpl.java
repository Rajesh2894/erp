/**
 * 
 */
package com.abm.mainet.property.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.property.domain.PropertyBillExceptionEntity;
import com.abm.mainet.property.dto.PropertyBillExceptionDto;
import com.abm.mainet.property.repository.PropertyBillExceptionRepository;

/**
 * @author cherupelli.srikanth
 *@since 29 april 2021
 */
@Service
public class PropertyBillExceptionServiceImpl implements PropertyBillExceptionService{

	@Autowired
	private PropertyBillExceptionRepository propertyBillExceptionRepository;
	
	@Override
	@Transactional
	public void saveExceptionDetails(PropertyBillExceptionDto exceptionDto) {
		PropertyBillExceptionEntity exceptionEntity = new PropertyBillExceptionEntity();
		BeanUtils.copyProperties(exceptionDto, exceptionEntity);
		propertyBillExceptionRepository.save(exceptionEntity);
	}

	@Override
	@Transactional
	public List<String> getAllPendingPropNoForBill(Long orgId, String billType, String status, Long userId) {
		return propertyBillExceptionRepository.getAllPendingBillPropNos(orgId, billType, status,userId);
	}

	@Override
	@Transactional
	public void updateBillExceptionDataByPropNo(String propNo, String status, String exceptionReason,String ipAddress) {
		List<PropertyBillExceptionEntity> propBillEntityList = getPropExceptionDetailByPropNo(propNo);
		propBillEntityList.forEach(propBillEntity->{
			if(propBillEntity != null) {
				propBillEntity.setStatus(status);
				propBillEntity.setUpdatedDate(new Date());
				propBillEntity.setLgIpMacUpd(ipAddress);
				if(StringUtils.equals(status, "A") && StringUtils.isNotBlank(exceptionReason)) {
					propBillEntity.setExceptionReason(exceptionReason);
				}
				propertyBillExceptionRepository.save(propBillEntity);
			}
		});
		
	}

	@Override
	public List<PropertyBillExceptionEntity> getPropExceptionDetailByPropNo(String propNo) {
		return propertyBillExceptionRepository.getPropBillExceptionByPropNo(propNo);
	}

	@Override
	@Transactional
	public void deleteEceptionDetailsByOrgIdAndUserId(Long userId, Long orgId) {
		propertyBillExceptionRepository.deleteRecordFromException(userId, orgId);
		
	}

}
