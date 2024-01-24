package com.abm.mainet.property.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.property.domain.MainBillDetEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.domain.ProvisionalBillMasEntity;
import com.abm.mainet.property.dto.ProperySearchDto;

public interface PropertyMainBillService extends Serializable {

    List<MainBillMasEntity> saveAndUpdateMainBill(List<TbBillMas> billMasList, Long orgId, Long empId, String authFlag,
            String macAddress);

    List<TbBillMas> fetchNotPaidBillForAssessment(String assNo, long orgId);

    List<TbBillMas> fetchAllBillByPropNo(String assNo, long orgId);

    List<TbBillMas> fetchBillByPropNo(String assNo, long orgId);

    List<MainBillMasEntity> saveAndUpdateMainBillFromProvisionalBill(List<ProvisionalBillMasEntity> provBillList, long orgId,
            Long empId,
            String auth, String ipAddress);

    int getCountOfBillByOldPropNoOrPropNoAndFinId(String propNo, long orgId, Long finYearId);

    void copyDataFromMainBillDetailToHistory(List<TbBillMas> oldBillMasList, String hStatus,Long userId,String lgipAddress);

    void deleteMainBillWithEntById(List<MainBillDetEntity> mainBillDetEntity);

    void deleteMainBillWithDtoById(List<TbBillMas> tbBillMasList);

    List<TbBillMas> fetchBillFromBmIdNos(List<Long> bmIdnos);

    TbBillMas fetchBillFromBmIdNo(Long bmIdno, Long orgId);

    int getCountOfBillWithoutDESByPropNo(String propNo, Long orgId);

    void SaveAndUpdateMainBillOnlyForDES(List<TbBillMas> billMasList, Long orgId, Long empId, String propNo, String macAddress);
    
    void saveAndUpdateMainBillAfterObjection(List<TbBillMas> billMasList, Long orgId, Long empId, String propNo, String macAddress);

    List<MainBillMasEntity> saveAndUpdateMainBillWithKeyGen(List<TbBillMas> billMasList, Long orgId, Long empId, String propNo,
            Map<Long, Long> assIdMap, String ipAddress);

    int getPaidBillCountByPropNoList(List<String> propNoList, Long orgId, Long bmYear);

    List<TbBillMas> fetchAllBillByPropNoAndAssIds(String assNo, List<Long> assIds, long orgId);

    List<TbBillMas> fetchBillByBillNoAndPropertyNo(String propNo, String billNo, Long orgId);

    int getPaidBillCountByPropNo(String propNo, Long orgId, Long bmYear);

    int getPaidBillCountByPropNoAndFlatNo(String propNo, Long orgId, Long bmYear, String flatNo);

    List<TbBillMas> fetchNotPaidBillForAssessmentByFlatNo(String assNo, long orgId, String flatNo);

    List<TbBillMas> fetchAllBillByPropNoAndFlatNo(String assNo, String flatNo, long orgId);

    void updateServeDateAndDueDate(Date serveDate, Date dueDate, Long bmIdNo);

    List<TbBillMas> fetchAllBillByPropNoAndFinId(String assNo, long orgId, Long billNo);

    List<TbBillMas> fetchNotPaidBillForAssessmentByParentPropNo(String parentPropNo, long orgId);

    List<TbBillMas> fetchAllBillByPropNoAndCurrentFinId(String assNo, long orgId, Long finId);

    List<TbBillMas> getBillsForBillMethodChange(ProperySearchDto properySearchDto);

	void deleteMainBillDetById(List<Long> bmIdNos);
	
	int getBillExistByPropNoFlatNoAndYearId(String propNo,Long orgId, Long bmYear, String flatNo);
	
	int getBillExistByPropNoAndYearId(String propNo, Long orgId, Long bmYear);
	
	Long getMaxFinYearIdByPropNo(String propNo, Long orgId);
	
	List<TbBillMas> fetchBillSForViewProperty(String assNo);
	
	void deleteBillDetList(List<Long> billDetList);
	
	String ValidateBillData(List<TbBillMas> billMasList);
}
