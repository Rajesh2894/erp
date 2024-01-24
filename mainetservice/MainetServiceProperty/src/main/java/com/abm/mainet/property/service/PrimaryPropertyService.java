package com.abm.mainet.property.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.property.domain.PropertyDetEntity;
import com.abm.mainet.property.domain.PropertyMastEntity;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

public interface PrimaryPropertyService extends Serializable {

    void savePropertyMaster(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId, Long empId);

    void updatePrimayPropertyInactive(long orgId, String assNo, Long empId);

    void updatePrimayProperty(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, long orgId, Long empId);

    PropertyMastEntity getPropertyDetailsByPropNo(String propertyNo, Long orgId);
    
    Long getBillMethodIdByPropNo(String propNo, Long orgId);
    
    List<String> getFlatNoIdByPropNo(String propNo, Long orgId);
    
    void savePropertyMasterForIndividualBill(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId, Long empId);

	PropertyMastEntity getPropertyDetailsByPropNoNFlatNo(String propertyNo, String flatNo, Long orgId);

	void updatePropertyMstStatus(String propNo, String status, Long orgId);

	String getPropertyDetailsByPropNoFlatNoAndOrgId(String propertyNo, String flatNo, Long orgId);

}
