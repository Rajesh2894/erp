package com.abm.mainet.property.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.property.dto.NoDuesCertificateDto;
import com.abm.mainet.property.dto.NoDuesPropertyDetailsDto;


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

	NoDuesCertificateDto getPropertyServiceData(NoDuesCertificateDto dto);

	boolean getChequeClearanceStatus(NoDuesCertificateDto dto);

	NoDuesCertificateDto getPropertyDetailsByPropertyNumberNFlatNo(NoDuesCertificateDto noDuesDto);

	boolean initiateWorkflowForPropertyfreeService(NoDuesCertificateDto dto);

	boolean checkWorkflowDefined(NoDuesCertificateDto dto);

	String getPropertyBillingMethod(String propNo, Long orgId);

	List<String> getPropertyFlatList(String propNo, Long orgId);

	NoDuesCertificateDto getNoDuesDetails(NoDuesCertificateDto noDuesCertificateDto);

	int getBillExistByPropNoFlatNoAndYearId(NoDuesPropertyDetailsDto detailsDto);

}
