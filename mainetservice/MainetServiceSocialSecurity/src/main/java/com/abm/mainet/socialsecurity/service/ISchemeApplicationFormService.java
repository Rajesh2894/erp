package com.abm.mainet.socialsecurity.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeEligibilty;
import com.abm.mainet.socialsecurity.domain.SocialSecuritySchemeMaster;
import com.abm.mainet.socialsecurity.ui.dto.ApplicationFormDto;
import com.abm.mainet.socialsecurity.ui.dto.CriteriaDto;
import com.abm.mainet.socialsecurity.ui.dto.SchemeAppFamilyDetailsDto;

@WebService
public interface ISchemeApplicationFormService {

	public List<LookUp> FindSecondLevelPrefixByFirstLevelPxCode(Long orgId, String parentPx, Long ParentpxId,
			Long level);

	public ApplicationFormDto saveApplicationDetails(final ApplicationFormDto dto);

	public ApplicationFormDto prepareAndSaveApplicationMaster(ApplicationFormDto dto);

	public void initiateWorkFlowForFreeService(ApplicationFormDto dto);

	public ApplicationFormDto findApplicationdetails(Long applicationId, Long orgId);

	public boolean saveDecision(ApplicationFormDto applicationformdto, Long orgId, Employee emp,
			WorkflowTaskAction workFlowActionDto, RequestDTO requestDto);

	// newly added for data legacy form save

	public ApplicationFormDto saveDataLegacyFormDetails(final ApplicationFormDto dto);

	String getbeneficiarynumber(String ulbName, String serviceSortCode, Long orgId, String wardCode);

	Long getDeptIdByServiceShortName(Long orgId);

	List<Object[]> getSchemeName(ApplicationFormDto dto);

	Long getCriteriaGridId(CriteriaDto dto);

	String isSchemeActive(Long serviceId, Long orgId);

	// newly added for showing only active scheme and validating scheme eligibility
	// criteria
	public List<SocialSecuritySchemeMaster> getActiveScheme(Long orgid);

	public Long getAllSchemeData(Long serviceId, Long orgId);

	/* public List<Object[]> getScemeEligibilityData(Long scmMstId, Long orgId); */

	// added for getting active service in portal Side
	List<Object[]> findAllActiveServicesWhichIsNotActual(Long orgId, Long depId, Long activeStatusId,
			String notActualFlag);

	// adding for Defect #79855
	List<SocialSecuritySchemeEligibilty> getLatestScemeEligbleData(Long scmMstId);

	List<BankMasterEntity> getBankList();

	String checkFamilyDetailsReqOrNot(Long schemeId, Long orgId);

	public List<ApplicationFormDto> getExistingHolderByUID(String aadharNo, long orgid);

	boolean checkAppPresentAgainstScheme(Long serviceId, Long orgId);

	public List<SchemeAppFamilyDetailsDto> getFamilyDetById(Long applicationId, Long orgId);

	public String checkLifeCertificateDateReqOrNot(Long selectSchemeName, Long orgid);

	String annualIncome(Long schemeId, Long orgId, Long id);

	List<ApplicationFormDto> getAppliDetail(Long selectSchemeName, Long subSchemeName, String swdward1, String swdward2,
			String swdward3, String swdward4, String swdward5, String aadharCard, String status, Long orgId);

	ApplicationFormDto findApplicationData(Long applicationId);

	WSResponseDTO getApplicableTaxes(WSRequestDTO requestDTO);

	WSResponseDTO getApplicableCharges(WSRequestDTO requestDTO);

}
