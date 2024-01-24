package com.abm.mainet.property.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalBillMasDto;

public interface IProvisionalBillService {

    List<TbBillMas> getProvisionalBillMasByPropertyNo(String propertyNo, Long orgId);

    List<TbBillMas> getProvisionalBillDtoByProvisionalBillEntity(
            List<ProvisionalBillMasEntity> provisionalBillMasEntityList);

    boolean checkBillPresentFromProvBillWithEntity(List<ProvisionalBillMasEntity> provisionalBillMasEntityList);

    boolean checkBillPresentFromProvBillWithDto(List<ProvisionalBillMasDto> provisionalBillMasDtoList);

    ProvisionalBillMasEntity getLatestProvisionalBillByPropNo(Long propertyId, Long orgId);

    void copyDataFromProvisionalBillDetailToHistory(List<TbBillMas> oldBillMasList,String updateFlag);

    List<TbBillMas> fetchListOfBillsByPrimaryKey(List<Long> bmIDno, Long orgid);

    void updateAccountPostingFlag(List<Long> bmIdNo, String flag);

    void deleteDetailsTaxes(List<Long> deleteTaxes);

    List<TbBillMas> fetchNotPaidBillForProAssessment(String propNo, Long orgId);

    List<Long> saveAndUpdateProvisionalBill(List<TbBillMas> billMasList, Long orgId, Long empId, String propNo,
            Map<Long, Long> assIdMap, List<ProvisionalBillMasEntity> provBillList, String ipAddress);

    void deleteProvisionalBillsById(List<TbBillMas> billMasList);

    void deleteProvisionalBillsWithEntityById(List<ProvisionalBillMasEntity> provBillEntList);

    TbBillMas fetchBillFromBmIdNo(Long bmIdno, Long orgId);

    int getCountOfBillWithoutDESByPropNo(String propNo, Long orgId);

    void saveAndUpdateTemporaryProvisionalBill(List<TbBillMas> billMasList, Long orgId, Long empId, String ipAddress);

    void deleteTemporaryProvisionalBillsWithEntityById(Long empId, Long orgId);

    List<String> fetchProvisionalNotPaidBillByPropNo(String propNo, Long orgId);

	List<TbBillMas> getProvisionalBillMasByPropertyNoAndFlatNo(String assNo, String flatNo, long orgId);

	List<TbBillMas> fetchNotPaidBillForProAssessmentWithFlatNo(String propNo, String flatNo, Long orgId);
	
	List<TbBillMas> getProvisionalBillMasByPropertyNoAndFinId(String propertyNo, Long orgId, Long finId);
	
	List<Long> saveAndUpdateProvisionalBillForReviseBill(List<TbBillMas> billMasList, Long orgId, Long empId, String propNo,
            Map<Long, Long> assIdMap, List<ProvisionalBillMasEntity> provBillList, String ipAddress);
	
	List<TbBillMas> fetchNotPaidBillForProAssessmentByParentPropNo(String parentPropNo, Long orgId);

	void saveAndUpdateProvBillForBillChange(List<TbBillMas> billMasList, Long orgId, Long empId,
			String ipAddress);

	List<TbBillMas> getProvisionalBillMasterList(ProvisionalAssesmentMstDto provDto, String flatNo,
			Map<Long, String> taxMap);
}
