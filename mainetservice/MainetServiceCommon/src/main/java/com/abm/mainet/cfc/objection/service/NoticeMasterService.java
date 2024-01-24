package com.abm.mainet.cfc.objection.service;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.cfc.objection.dto.NoticeMasterDto;

public interface NoticeMasterService extends Serializable {

    int getCountOfNotBeforeDueDateByRefNoAndNotNo(Long orgId, String refNo, String notNo);

    int getCountOfNotByRefNoAndNotNo(Long orgId, String refNo, String notNo);

    List<String> saveListOfNoticeMaster(List<NoticeMasterDto> notDtoList, Long orgId, Long empId, String macAddress,
            Long notTypeId, Long finYearId);

    String saveNoticeMaster(NoticeMasterDto notDto, Long orgId, Long empId, String macAddress, Long notTypeId, Long finYearId);

    NoticeMasterDto getNoticeByRefNo(Long orgId, String refNo);

    NoticeMasterDto getMaxNoticeByRefNo(Long orgId, String refNo, Long dpDeptid);

    List<NoticeMasterDto> getAllNoticeByRefNo(Long orgId, String refNo, String flatNo);

    NoticeMasterDto getNoticeByNoticeNo(Long orgId, String noticeNo);

	int getCountOfNotByApplNoAndNotNo(Long orgId, Long apmApplicationId, String noticeNo);

	int getCountOfNotBeforeDueDateByApplNoAndNotNo(Long orgId, Long apmApplicationId, String noticeNo);
	
	Long getNoticeIdByApplicationId(Long apmApplicationId);
}
