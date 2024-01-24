/**
 * 
 */
package com.abm.mainet.property.service;

import java.util.List;

import com.abm.mainet.property.domain.PropertyBillExceptionEntity;
import com.abm.mainet.property.dto.PropertyBillExceptionDto;

/**
 * @author cherupelli.srikanth
 *@since 29 april 2021
 */
public interface PropertyBillExceptionService {

	void saveExceptionDetails(PropertyBillExceptionDto exceptionDto);
	
	List<String> getAllPendingPropNoForBill(Long orgId, String billType, String status, Long userId);
	
	void updateBillExceptionDataByPropNo(String propNo,String status,String exceptionReason,String ipAddress);
	
	List<PropertyBillExceptionEntity> getPropExceptionDetailByPropNo(String propNo);
	
	void deleteEceptionDetailsByOrgIdAndUserId(Long userId, Long orgId);
}
