package com.abm.mainet.property.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.property.domain.ProvisionalAssesmentMstEntity;
import com.abm.mainet.property.domain.ProvisionalAssesmentOwnerDtlEntity;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.PropertyPenltyDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

public interface IProvisionalAssesmentMstService {

    Map<Long, Long> saveProvisionalAssessment(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, Long orgId, Long empId,
            List<Long> finYearList, Long applicationNo);

    List<ProvisionalAssesmentMstEntity> getPropDetailFromProvAssByPropNo(Long orgId, String propertyNo,
            String activeSatus);

    List<ProvisionalAssesmentMstEntity> getProAssMasterByApplicationId(Long orgId, Long applicationId);

    ProvisionalAssesmentMstDto getProvAssDtoByAppId(Long applicationId, Long orgId);

    List<ProvisionalAssesmentMstEntity> getProvisionalAssesmentMstListByAppId(Long applicationId, Long orgId);

    ProvisionalAssesmentMstDto getProvAssesmentMstDtoByEntity(List<ProvisionalAssesmentMstEntity> provAssEntList);

    List<ProvisionalAssesmentMstDto> getAssesmentMstDtoListByAppId(Long applicationId, Long orgId);

    ProvisionalAssesmentMstDto copyProvDtoDataToOtherDto(ProvisionalAssesmentMstDto provAssMstDto);

    void copyDataFromProvisionalPropDetailToHistory(List<ProvisionalAssesmentMstDto> provAssDtoList, Long empId,ProvisionalAssesmentMstDto provAsseMstDto);

    Map<Long, Long> updateProvisionalAssessment(ProvisionalAssesmentMstDto provAssMstDto,
            List<ProvisionalAssesmentMstDto> provAssMstDtoList, Long orgId, Long empId, String authFlag, List<Long> finYearList,
            List<ProvisionalAssesmentMstEntity> provAssEntListNew);

    List<ProvisionalAssesmentMstDto> getPropDetailByPropNoOnly(String propertyNo);

    boolean CheckForAssesmentFieldForCurrYear(long orgId, String assNo, String assOldpropno, String status, Long finYearId,
            Long serviceId);

    void saveAndUpdateProvsionalOwner(ProvisionalAssesmentMstDto provAssMstDto, Long orgId, Long empId);

    List<ProvisionalAssesmentMstEntity> getPropDetailFromProvAssByParentPropNo(long orgId, String assNo);

    List<String> getAmalgamatedChildPropNo(String assNo, long orgId);

    void saveProvisionalAssessmentFromDtoForAmlg(ProvisionalAssesmentMstDto provisionalAssesmentMstDto, long orgId, Long empId,
            String authWithChng);

    List<TbBillMas> getViewData(String propNo);

    List<Long> fetchApplicationAgainstProperty(long orgId, String proertyNo);

    List<ProvisionalAssesmentMstDto> getPropDetailFromProvAssByPropNoOrOldPropNo(Long orgId, String propertyNo,
            String oldPropertyNo);

    List<ProvisionalAssesmentMstDto> getDataEntryPropDetailFromProvAssByPropNo(Long orgId, String propertyNo, String activeSatus);

    void deleteProvisionalAssessWithDtoById(List<ProvisionalAssesmentMstDto> provAssDtoList);

    void deleteProvisionalAssessWithEntById(List<ProvisionalAssesmentMstEntity> provAssEntList);

    ProvisionalAssesmentMstEntity saveProvisionalAssessmentForDataEntry(ProvisionalAssesmentMstDto provisionalAssesmentMstDto,
            Long orgId, Long empId, Long finYear, Long applicationNo);

    ProvisionalAssesmentMstDto fetchPropertyByPropNo(String propNo, Long orgId);

    void updateProvisionalAssessmentForSingleAssessment(ProvisionalAssesmentMstDto provAssDto, Long orgId, Long empId,
            String ipAddress);

    List<ProvisionalAssesmentOwnerDtlEntity> getPrimaryOwnerDetailByPropertyNo(Long orgId, String propertryNo);

    void saveProvisionalAssessmentListForAmalgamation(List<ProvisionalAssesmentMstDto> provAssesmentMstDtoList, Long orgId,
            Long empId, String ipAddress, String parentPropNo, Long serviceId);

    List<ProvisionalAssesmentMstEntity> getListOfPropertyByListOfPropNos(Long orgId, List<String> propNoList);

    int validateProperty(String proertyNo, Long orgId, Long serviceId);

    BigInteger[] validateBill(String proertyNo, Long orgId, Long bmIdno);

    void deleteDetailInDESEdit(ProvisionalAssesmentMstDto provAssDto, Long orgId, Long empId, String ipAddress);

    boolean CheckForAssesmentFiledForCurrYear(long orgId, String assNo, String assOldpropno, String status, Long finYearId);

    List<Long> saveNoOfDaysEditForm(List<ProvisionalAssesmentMstDto> oldProvAssMstDtoList,
            ProvisionalAssesmentMstDto provisionalAssesmentMstDto, List<TbBillMas> oldBillMasList,
            List<TbBillMas> newBillMasList, Employee emp, List<Long> finYearList, BillDisplayDto advanceAmt,
            BillDisplayDto earlyPaymentRebate, List<BillReceiptPostingDTO> rebateRecDto, double advanceAmount,
            PropertyPenltyDto penaltydto, String ipAddress, Long userId, double editableLastAmountPaid, double previousSurcharge);

    void updatePropMasStatus(List<Long> propPrimKeyList, String status);

    ProvisionalAssesmentMstDto fetchProvisionalDetailsByPropNo(String propNo, Long orgId);

    ProvisionalAssesmentMstDto fetchProvisionalDetailsByOldPropNo(String oldPropNo, Long orgId);
	
	int validatePropertyWithFlat(String propNo, String flatNo, long orgid, Long smServiceId);

	List<ProvisionalAssesmentMstDto> getDataEntryPropDetailFromProvAssByPropNoAndFlatNo(long orgId, String rowId,
			String flatNo, String string);

	List<Long> fetchApplicationAgainstPropertyWithFlatNo(long orgId, String proertyNo, String flatNo);

	Map<Long, Long> saveProvisionalAssessmentWithFlatNo(ProvisionalAssesmentMstDto provAsseMstDto, Long orgId,
			Long empId, List<Long> finYearList, Map<String, Long> flatWiseAppIdmap);
	
	List<ProvisionalAssesmentMstDto> getDataEntryPropDetailFromProvAssByPropNoAndOldPropNo(Long orgId, String propertyNo,
            String activeSatus, String oldPropNo);
	
	List<ProvisionalAssesmentMstDto> getPropDetailFromProvAssByOldPropNoAndFlatNo(long orgId, String oldPropNo,
			String flatNo, String activeSatus);

	List<ProvisionalAssesmentMstDto> getPropDetailByOldPropNoAndOrgId(String assOldpropno, Long orgId);

	void updateBillMethodChangeFlag(String proertyNo, String billMethodChngFlag, Long orgId);
	
	String getPropNoByOldPropNo(String oldPropNo, Long orgId);
}
