package com.abm.mainet.rti.service;

import java.util.List;
import java.util.Set;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.rti.datamodel.RtiRateMaster;
import com.abm.mainet.rti.dto.MediaChargeAmountDTO;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;

public interface IRtiApplicationDetailService {

	WSResponseDTO getApplicableTaxes(RtiRateMaster rtiRateMaster, WSRequestDTO wsRequestDto,
			String chargeApplicablePrefixCode);

	List<MediaChargeAmountDTO> getApplicableCharges(List<RtiRateMaster> requiredCharges);

	Set<LookUp> getDeptLocation(Long orgid, Long deptId);

	RtiApplicationFormDetailsReqDTO saveorUpdateRtiApplication(RtiApplicationFormDetailsReqDTO requestDTO);

	RtiApplicationFormDetailsReqDTO initiateFreeServiceWorkFlow(RtiApplicationFormDetailsReqDTO requestDTO);

	Set<LookUp> getRtiWorkflowMasterDefinedDepartmentListByOrgId(Long orgid);
	
	RtiApplicationFormDetailsReqDTO fetchRtiApplicationInformationById(Long applicationId, Long orgId);

	Boolean resolveServiceWorkflowType(RtiApplicationFormDetailsReqDTO tradeDTO);

}
