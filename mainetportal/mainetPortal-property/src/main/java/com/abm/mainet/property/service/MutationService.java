package com.abm.mainet.property.service;

import java.util.List;

import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

public interface MutationService {

	 ProvisionalAssesmentMstDto fetchDetailForMutataion(String propNo, String oldPropNo, Long orgId);

	List<DocumentDetailsVO> fetchCheckList(PropertyTransferMasterDto tranDto);

	PropertyTransferMasterDto fetchChargesForMuatation(PropertyTransferMasterDto tranDto);

	PropertyTransferMasterDto saveMutation(PropertyTransferMasterDto propTransferDto);

	PropertyTransferMasterDto callWorkFlowForFreeService(PropertyTransferMasterDto propTransferDto);

	List<LookUp> getLocationList(Long orgId, Long deptId);
	
	String getPropertyBillingMethod(String propNo, Long orgId);
	
	List<String> getPropertyFlatList(String propNo, String orgId);
	
	ProvisionalAssesmentMstDto fetchMutationDetails(String propNo, String oldPropNo, String flatNo, Long orgId);
}
