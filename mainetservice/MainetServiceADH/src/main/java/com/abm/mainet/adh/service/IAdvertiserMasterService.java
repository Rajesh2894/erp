package com.abm.mainet.adh.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.adh.dto.ADHRequestDto;
import com.abm.mainet.adh.dto.ADHResponseDTO;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.AgencyRegistrationRequestDto;
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.adh.dto.LicenseLetterDto;
import com.abm.mainet.adh.dto.NewAdvertisementApplicationDto;
import com.abm.mainet.adh.ui.model.AgencyRegistrationModel;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

/**
 * @author cherupelli.srikanth
 * @since 02 august 2019
 */
@WebService
public interface IAdvertiserMasterService {

    /**
     * This method will save form content in the database
     * 
     * @param AdvertiserMasterDto
     */
    void saveAdevertiserMasterData(AdvertiserMasterDto masterDto);

    /**
     * This method will update form content in the database
     * 
     * @param AdvertiserMasterDto
     */
    void updateAdvertiserMasterData(AdvertiserMasterDto masterDto);

    /**
     * This method will get All Advertiser Master By OrgId
     * 
     * @param orgId
     * @return AdvertiserMasterDto list by orgId
     */
    List<AdvertiserMasterDto> getAllAdvertiserMasterByOrgId(Long orgId);

    /**
     * This method will get All Advertiser Master By OrgId and agencyId
     * 
     * @param orgId
     * @param agencyId
     * @return AdvertiserMasterDto by orgId and agencyId
     */
    AdvertiserMasterDto getAdvertiserMasterByOrgidAndAgencyId(Long orgId, Long agencyId);

    /**
     * This method will get All Advertiser Master By OrgId or advertiserNumber or advertiserOldNumber or advertiserName or
     * advertiserStatus
     * 
     * @param orgId
     * @param advertiserNumber
     * @param advertiserOldNumber
     * @param advertiserName
     * @param advertiserStatus
     * @return AdvertiserMasterDto list by passing any of the above parameters
     */
    List<AdvertiserMasterDto> searchAdvertiserMasterData(Long orgId, String advertiserNumber,
            String advertiserOldNumber, String advertiserName, String advertiserStatus);

    /**
     * This method is used to generate application Id, saving the data
     * 
     * @param requestDto
     */
    AgencyRegistrationResponseDto saveAgencyRegistrationData(AgencyRegistrationRequestDto requestDto);

    /**
     * This method is used to initialize the work flow for Agency Registration Service
     * 
     * @param requestDto
     */
    void initializeWorkFlowForFreeService(AgencyRegistrationRequestDto requestDto, boolean loiChargeAppFlag);

    /**
     * This method is used to get AdvertiserMasterDto by applicationId and orgId
     * 
     * @param applicationId
     * @param orgId
     * @return
     */
    List<AdvertiserMasterDto> getAgencyRegistrationByAppIdAndOrgId(Long applicationId, Long orgId);

    /**
     * This method is used to update the work flow if approval levels doesn't ends.
     * 
     * @param taskAction
     * @param eventName
     * @param serviceId
     * @return
     */
    boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, String eventName, Long serviceId,
            String serviceShortCode);

    Map<Long, Double> getLoiCharges(AdvertiserMasterDto masterDto, Long serviceId, String serviceName);

    /**
     * This method is used to generate the agency Licence Number as per the formmat.
     * 
     * @param org
     * @param categoryId
     * @return agency Licence Number
     */
    String generateAgencyLicenceNumber(Organisation org, Long categoryId);

    /**
     * This method is used to get all the license period details(including renewal details) against agencyId
     * 
     * @param agencyId
     * @param orgId
     * @return license period details
     */
    List<AdvertiserMasterDto> getAllLicPeriodDetails(Long agencyId, Long orgId);

    /**
     * This method is used to get advertiser master details by agencyLicNo and orgId
     * 
     * @param agencyLicNo
     * @param orgId
     * @return Advertiser Master Dto
     */
    AgencyRegistrationResponseDto getAgencyDetailByLicnoAndOrgId(String agencyLicNo, Long orgId);

    /**
     * This method is used to get the list of Advertiser Master Dto List based on agency category
     * 
     * @param agencyCategory
     * @param orgId
     * @return AdvertiserMasterDto llist
     */
    List<AdvertiserMasterDto> getAgencyDetailsByAgencyCategoryAndOrgId(Long agencyCategoryId, Long orgId);

    /**
     * This method is used to get the list of agency License numbers by ogId
     * 
     * @param orgId
     * @return List of license numbers
     */
    List<String[]> getAgenLicNoListByOrgId(Long orgId, String agencyStatus);

    /**
     * This method is used to get agency license list sand status list by orgId
     * 
     * @param orgId
     * @return agency Lic No List and status
     */
    List<AdvertiserMasterDto> getLicNoAndAgenNameAndStatusByorgId(Long orgId);

    Map<String, String> getLicMaxTenureByServiceCode(Long orgId, String serviceShortName);

    String getLicNoByApplIdAndOrgId(Long applicationId, Long orgId);

    List<LicenseLetterDto> setLicValidPeriodsToPrint(List<AdvertiserMasterDto> masterDtoList);

    String getAgencyNameByAgnIdAndOrgId(Long agencyId, Long orgId);

    List<String> getAgencyNameByOrgId(Long orgId);

    List<AdvertiserMasterDto> getAgencyDetailsByOrgIdAndStatus(Long orgId, String agencyStatus);

    void processTheAgencyLicenseGenerationTask(Organisation organisation, Long applicationId, Long actualTaskId,
            AgencyRegistrationModel model);

    // D#79968
    ADHResponseDTO getDataByApplicationId(ADHRequestDto adhRequestDto);

    NewAdvertisementApplicationDto getPropertyDetailsByPropertyNumber(NewAdvertisementApplicationDto reqDto);

    // User Story 112154 code changes here
    AgencyRegistrationResponseDto saveCancellationService(AgencyRegistrationRequestDto requestDto);

    // Defect #129856
    Map<String, String> getCalculateYearTpe(Long orgid, String serviceShortName, Long licType);

    // Defect #129856
    Map<String, String> getLicMaxTenureDays(Long orgId, String serviceShortName, Long licType);

    AgencyRegistrationResponseDto viewAdvertiserDetails(AdvertiserMasterDto masterDto);

	ContractAgreementSummaryDTO searchBillPaymentData(String contractNo, Long orgId);

	ContractAgreementSummaryDTO upadteBillDataFromPortal(ContractAgreementSummaryDTO requestDto);

	AdvertiserMasterDto getAgencyRegistrationByAppIdByOrgId(Long applicationId, Long orgId);

}
