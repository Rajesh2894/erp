/**
 * 
 */
package com.abm.mainet.property.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.cfc.objection.dto.HearingInspectionDto;
import com.abm.mainet.property.domain.AssesmentDetailHistEntity;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.domain.AssesmentMastHistEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlEntity;
import com.abm.mainet.property.domain.AssesmentOwnerDtlHistEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;

public interface AssesmentMastService {

    ProvisionalAssesmentMstDto getPropDetailByAssNoOrOldPropNo(long orgId, String assNo, String assOldpropno,
            String status, String logicalPropNo);

    ProvisionalAssesmentMstDto getAssessmentDetailsAssNoOrOldpropno(long orgId, String assNo, String assOldpropno, String status);

    ProvisionalAssesmentMstDto mapAssessmentMasterToProvisionalDto(AssesmentMastEntity assessmentMaster);

    ProvisionalAssesmentMstDto fetchAssessmentMasterByPropNo(long orgId, String parentPropNo);

    List<ProvisionalAssesmentMstDto> fetchAssessmentMasterByPropNo(long orgId, List<String> parentPropNo);

    List<ProvisionalAssesmentMstDto> fetchAssessmentMasterForAmalgamation(long orgId, String parentPropNo,
            List<String> propNoList, String parentOldPropNo, List<String> oldPropNoList);

    List<AssesmentMastEntity> getAssessmentMstByPropNo(long orgId, String assNo);

    Long getApplicationNoByPropNoForObjection(Long orgId, String assNo, Long serviceId);

    void saveAssesmentMastByDto(List<ProvisionalAssesmentMstDto> provAssDtoList, Long orgId, Long empId, String authFlag,
            String ipAddress);

    void saveAssesmentMastByEntity(List<ProvisionalAssesmentMstEntity> provAssDtoList, Long orgId, Long empId, String authFlag,
            String ipAddress);

    List<ProvisionalAssesmentMstDto> getPropDetailFromMainAssByPropNoOrOldPropNo(Long orgId, String propertyNo,
            String oldPropertyNo);

    ProvisionalAssesmentMstDto fetchLatestAssessmentByPropNo(Long orgId, String propertyNo);

    List<Long> fetchApplicationAgainstProperty(long orgId, String proertyNo);

    List<ProvisionalAssesmentMstDto> getPropDetailByPropNoOnly(String propertyNo);

    ProvisionalAssesmentMstDto fetchAssessmentMasterByOldPropNo(Long orgId, String OldPropNo);

    boolean checkForAssesmentFieldForCurrYear(Long orgId, String assNo, String assOldpropno, String status, Long finYearId,
            Long serviceId);

    List<ProvisionalAssesmentMstDto> getPropDetailFromAssByPropNo(Long orgId, String propNo);

    ProvisionalAssesmentMstDto mapAssessmentMasterToProvisionalDtoWithPriKey(AssesmentMastEntity assessmentMaster);

    List<AssesmentOwnerDtlEntity> getPrimaryOwnerDetailByPropertyNo(Long orgId, String propertryNo);

    void saveAndUpdateAssessmentMastForOnlyForDES(ProvisionalAssesmentMstDto provAssDto, Long orgId, Long empId,
            String ipAddress);

    Map<Long, Long> saveAndUpdateAssessmentMast(ProvisionalAssesmentMstDto provAssMstDto,
            List<ProvisionalAssesmentMstDto> provAssMstDtoList, Long orgId, Long empId, String authFlag);
    
    void deleteDetailInDESEdit(ProvisionalAssesmentMstDto provAssDto, Long orgId, Long empId, String ipAddress);

    List<ProvisionalAssesmentMstDto> getAssesmentMstDtoListByAppId(Long applicationId, Long orgId);

    void saveAndUpdateAssessmentMastInMutation(ProvisionalAssesmentMstDto provAssDto, Long orgId, Long empId, String ipAddress);

    ProvisionalAssesmentMstDto fetchAssessmentMasterByPropNoWithKey(long orgId, String propNo);

    List<AssesmentMastEntity> getAssesmentMstEntListByAppId(Long applicationId, Long orgId);

    List<ProvisionalAssesmentMstDto> getAssessmentMstListByAssIds(List<Long> assIdList, Long orgId);

    List<ProvisionalAssesmentMstDto> getAssMstListForProvisionalDemand(Long orgId);

    boolean checkForAssesmentFieldForCurrYear(Long orgId, String assNo, String assOldpropno, String status, Long finYearId);

    List<String> checkActiveFlag(String propNo, Long orgId);

    List<String> checkActiveFlagByOldPropNo(String oldPropNo, Long orgId);

    Long getAssMasterIdbyPropNo(String propNo, Long orgId);

    Long getAssDetailIdByAssIdAndUnitNo(Long assId, Long unitNo);

    Long getAssOwnerIdByAssId(Long assId);

    Long getAssFactorIdByAssId(Long assId);

    List<Double> getPlotAreaByPropNo(Long orgId, List<String> propId);

    ProvisionalAssesmentMstDto getDataEntryByPropNoOrOldPropNo(long orgId, String assNo, String assOldpropno);

    /**
     * @param provisionalAssesmentMstDto
     * @param orgId
     * @param empId
     * @param clientIpAddress
     */
    boolean updateDataEntry(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId, Long empId,
            String clientIpAddress);

    String getReceiptDelFlag(String propNo, Long orgId);

    void updateActiveFlagOfProperty(Long orgId, String propNo, String assActive);

    List<ProvisionalAssesmentMstDto> getPropDetailByPropNoWithoutActiveCon(String propertyNo);

    List<Long> getObjectionIdListByPropNo(Long orgId, String propNo);

    HearingInspectionDto getHearingIdListByPropNo(Long orgId, Long objId);

    List<String> getUpdateDataEntryFlag(String propNo, Long orgId);

    List<ProvisionalAssesmentMstDto> getPropDetailFromMainAssByPropNoOrOldPropNoByFlatNo(Long orgId, String propertyNo,
            String oldPropertyNo, String logicalPropNo);

    void updateMobileNoAndEmailInOwnerDti(String mobileNo, String emailId, Long ownerDtlId);

    void updateMobileNoAndEmailInDetail(String mobileNo, String emailId, Long detailId);

    void saveAndUpdateAssessmentMastForOnlyForDESIndividualBill(ProvisionalAssesmentMstDto provAssDto, Long orgId, Long empId,
            String ipAddress);

    List<ProvisionalAssesmentMstDto> getPropDetailFromAssByPropNoFlatNo(long orgId, String propertyNo, String flatNo);

    ProvisionalAssesmentMstDto fetchLatestAssessmentByPropNoAndFlatNo(long orgId, String proertyNo, String flatNo);

    List<Long> fetchApplicationAgainstPropertyWithFlatNo(long orgId, String proertyNo, String flatNo);

    List<ProvisionalAssesmentMstDto> getAssMstListForProvisionalDemandbyPropNoList(Long orgId, List<String> propNoList);

    ProvisionalAssesmentOwnerDtlDto fetchPropertyOwnerByMobileNo(String mobileNo);

    ProvisionalAssesmentOwnerDtlDto getOTPServiceForAssessment(String mobileNo);

    List<AssesmentOwnerDtlEntity> getOwnerDetailsByPropertyNo(Long orgId, String propertryNo);

    List<ProvisionalAssesmentMstDto> fetchAssessmentMasterByOldPropNoNFlatNo(long orgId, String oldPropNo, String flatNo);

    List<String> checkActiveFlagByOldPropNoNFlatNo(String OldPropNo, String flatNo, Long orgId);

    List<String> checkActiveFlagByPropnonFlatNo(String propNo, String flatNo, Long orgId);

    void updateAssessmentMstStatus(String propNo, String status, Long orgId);

    ProvisionalAssesmentMstDto getPropDetailByAssNoOrOldPropNoForBillRevise(long orgId, String assNo, String assOldpropno,
            String status, String logicalPropNo);

    ProvisionalAssesmentMstDto mapAssessmentMasterToProvisionalDtoForBillRevise(AssesmentMastEntity assessmentMaster);

    String getPropNoByOldPropNo(String oldPropNo, Long orgId);

    AssesmentMastHistEntity fetchAssessmentHistMasterByPropNoOrFlat(Long orgId, String propNo, String flatNo);

    List<PropertyTransferOwnerDto> fetchAssOwnerHistByPropNoNApplId(Long orgId, Long apmApplicationId);

    List<PropertyTransferOwnerDto> fetchAssDetailHistByApplicationId(Long orgId, String flatNo, Long apmApplicationId);

    int updateMobileAndEmailForMobile(String mobileNo, String email, Long ownerDtlId, Date updatedDate);

    int updateMobileAndEmailForMobileMainTable(String mobileNo, String email, Long ownerDtlId, Date updatedDate);

	List<AssesmentOwnerDtlEntity> fetchOwnerDetailListByProAssId(AssesmentMastEntity assesmentMastEntity, Long orgId);
	
	List<ProvisionalAssesmentMstDto> fetchAssessmentMasterByParentPropNo(String parentPropNo);
	
	List<Long> getAssdIdListbyPropNo(String propNo, Long orgId);
	
	void saveAndUpdateAssessmentMastAfterObjection(ProvisionalAssesmentMstDto provAssMstDto,
            List<ProvisionalAssesmentMstDto> provAssMstDtoList, Long orgId, Long empId, String ipAddress, String authFlag);
	
	ProvisionalAssesmentMstDto getCurrentYearAssesment(Long finYearId, String propNo, Long applicationId);
	
	List<ProvisionalAssesmentMstDto> getAllYearAssesment(Long finYearId, String propNo);

	List<ProvisionalAssesmentMstDto> getPropDetailFromHouseNo(Long orgId, String houseNo);

	List<Object[]> getAssessedProperties(Long orgId, String dateSet);

	List<Object[]> getPropertiesRegisteredList(Long orgId);

	List<Object[]> getTransactionsProp(Long orgId, Long deptId, String dateSet);

	List<Object[]> getPaymentModeWiseColln(Long orgId, Long deptId, String dateSet);

}