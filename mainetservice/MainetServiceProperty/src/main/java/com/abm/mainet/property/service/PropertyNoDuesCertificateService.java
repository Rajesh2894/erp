package com.abm.mainet.property.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.property.dto.NoDuesCertificateDto;

@WebService
public interface PropertyNoDuesCertificateService {

	/**
	 * used to get Property Details By Property Number
	 * @param dto
	 * @return
	 */
	NoDuesCertificateDto getPropertyDetailsByPropertyNumber(NoDuesCertificateDto dto);
	
	/**
	 * used to fetch CheckList
	 * @param dto
	 * @return List<DocumentDetailsVO>
	 * */	
	List<DocumentDetailsVO> fetchCheckList(NoDuesCertificateDto dto);
	
	/**
	 * used to generate No Dues Certificate
	 * @param dto
	 * @return
	 */
	NoDuesCertificateDto generateNoDuesCertificate(NoDuesCertificateDto noDuesCertificateDto);
	
	/**
	 * used to fetch Charges For NoDues
	 * @param dto
	 * @return
	 */
	NoDuesCertificateDto fetchChargesForNoDues(NoDuesCertificateDto noDuesDto);
	
	boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, String eventName, Long serviceId,
            String serviceShortCode);
	
	Map<Long, Double> getLoiCharges(Organisation org,Long deptId, String serviceCode,String chargeAppAt);

	NoDuesCertificateDto getPropertyDetailsByPropertyNumberNFlatNo(NoDuesCertificateDto dto);
	
	NoDuesCertificateDto getNoDuesDetails(NoDuesCertificateDto dto);

	void updateNoDuesProprtyDetails(NoDuesCertificateDto noDuesCertificateDto);

	boolean getChequeClearanceStatus(NoDuesCertificateDto noDuesCertificateDto);

	NoDuesCertificateDto getPropertyServiceData(NoDuesCertificateDto dto);

	boolean initiateWorkflowForFreeService(NoDuesCertificateDto dto);

	boolean isWorkflowDefined(NoDuesCertificateDto dto);

	Object getPropertyBillingMethod(String propNo, Long orgId);

	List<String> getPropertyFlatList(String propNo, String orgId);

	WardZoneBlockDTO getWordZoneBlockByApplicationId(Long applicationId, Long serviceId, Long orgId);

}
