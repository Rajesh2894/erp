package com.abm.mainet.cfc.objection.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.cfc.objection.dto.HearingInspectionDto;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.domain.Employee;

public interface HearingAndInspectionService {

    void saveInspectionMaster(HearingInspectionDto hearingInspectionDto, Long orgId, Long empId, String macAddress);

    void saveHearingMaster(HearingInspectionDto hearingInspectionDto, Long orgId, Long empId, String macAddress,
            ObjectionDetailsDto objDto);

    HearingInspectionDto getHearingDetailByObjId(Long orgId, Long objId);

    HearingInspectionDto getInspectionByObjNo(Long orgId, String objNo);

    HearingInspectionDto getInspectionByObjId(Long orgId, Long objId);

    void callWorkflow(Employee emp, ObjectionDetailsDto objDto, String decision);

	void callWorkflow(Employee emp, ObjectionDetailsDto objDto, String approved, String decisionInFavorOf);

	List<String> getSelectedOwnerInfoByApplId(Long apmApplicationId, Long orgid);

}
