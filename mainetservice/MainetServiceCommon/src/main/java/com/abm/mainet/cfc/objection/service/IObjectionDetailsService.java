package com.abm.mainet.cfc.objection.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.utility.LookUp;

@WebService
public interface IObjectionDetailsService extends Serializable {

    List<ObjectionDetailsDto> getObjectionList(ObjectionDetailsDto objectionDetailsDto);

    List<Department> getActiveDepartment();

    ObjectionDetailsDto getObjectionDetailByAppId(Long orgid, Long serviceId, Long applicationId);

    ObjectionDetailsDto getObjectionDetailByObjNo(Long orgid, String objNo);

    Set<LookUp> getDepartmentList(ObjectionDetailsDto objDto);

    ObjectionDetailsDto saveObjectionWithValidation(ObjectionDetailsDto objDto);

    Map<String, Long> getApplicationNumberByRefNo(ObjectionDetailsDto objDto);

    ObjectionDetailsDto saveObjectionAndCallWorkFlow(ObjectionDetailsDto objectionDetailsDto);

    ObjectionDetailsDto getObjectionDetailByObjId(Long orgid, Long ObjId);

    List<ObjectionDetailsDto> searchObjectionDetails(long orgid, Long objectionDeptId, Long serviceId, String refNo,
            Long objectionOn);

    Set<LookUp> getLocationByDepartmentInLookup(ObjectionDetailsDto objDto);

    Set<LookUp> getservicesByDeptIdWithLookup(ObjectionDetailsDto objDto);

    CommonChallanDTO getCharges(ObjectionDetailsDto objDto);

    void saveAndUpateObjectionMaster(ObjectionDetailsDto objectionDetailsDto, Long empId, String ipAddress);

    List<ObjectionDetailsDto> getAllInactiveObjectionList();

    boolean isValidBillNoForObjection(ObjectionDetailsDto objDto);

    ObjectionDetailsDto fetchRtiAppDetailByRefNo(ObjectionDetailsDto objDto);

    ObjectionDetailsDto getObjectionDetailByIds(Long orgid, Long serviceId, Long applicationId, Long objectionOn);

    List<ObjectionDetailsDto> fetchObjectionsByMobileNoAndIds(String mobileNo, Long orgId, Long serviceId, Long objectionOn);

    List<ObjectionDetailsDto> getObjectionDetailListByAppId(Long orgid, Long serviceId, Long applicationId);

    void updateStatusOfOjection(Long objectionId, Long updatedBy, String lgIpMacUpd, String objectionStatus);

    ObjectionDetailsDto saveRTSAppealInObjection(ObjectionDetailsDto objDto);

	ObjectionDetailsDto saveLicenseObjectionData(ObjectionDetailsDto objectionDto);

	List<String> getFlatListByRefNo(String refNo, Long serviceId, Long objectionDeptId, Long orgId);

	ObjectionDetailsDto getObjectionDetailByRefNo(Long orgId, String objectionReferenceNumber);

}
