package com.abm.mainet.property.service;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.cfc.objection.dto.NoticeMasterDto;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.PrintBillMaster;
import com.abm.mainet.property.dto.SpecialNoticeReportDto;

public interface PropertyNoticeService extends Serializable {

    List<NoticeGenSearchDto> getAllPropWithAuthChangeByPropNo(NoticeGenSearchDto specNotSearchDto, Long orgId);

    List<NoticeGenSearchDto> fetchAssDetailBySearchCriteria(NoticeGenSearchDto specNotSearchDto, Long orgId);

    List<NoticeGenSearchDto> getAllSpecialNoticeProperty(NoticeGenSearchDto specNotSearchDto, Long orgId);

    void saveSpecialNoticeGeneration(List<NoticeGenSearchDto> notGenDtoList, Long orgId, Long empId);

    List<String> saveNoticeMaster(List<NoticeGenSearchDto> notGenDtoList, Organisation orgId, Long empId, Long notTypeId,
            Date notDueDate,
            Long finYearId);

    List<SpecialNoticeReportDto> setDtoForSpecialNotPrinting(List<NoticeGenSearchDto> notGenShowList, Organisation org);

    List<NoticeGenSearchDto> fetchAllSpecialNoticePropBySearchCriteria(NoticeGenSearchDto specNotSearchDto, Long orgId);

    List<NoticeGenSearchDto> fetchPropertyAfterDueDate(NoticeGenSearchDto specialNotGenSearchDto, long orgid);

    void saveDemandAndWarrantNoticeGeneration(List<NoticeGenSearchDto> notGenSearchDtoList, long orgid, Long empId,
            Long noticeType);

    List<NoticeGenSearchDto> fetchPropertyDemandNoticePrint(NoticeGenSearchDto specialNotGenSearchDto, long orgid);

    void saveListOfNoticeApplicableForObjection(List<NoticeGenSearchDto> notDtoList, Long orgId, Employee emp, String notType,
            String notDueDatePrefix, Long deptId);

    NoticeMasterDto saveNoticeApplicableForObjection(String propNo, Long appNo, Long serviceId, Long orgId, Employee emp,
            String notType,
            String notDueDatePrefix, Long deptId, String flatNo);

    List<PrintBillMaster> getNoticePrintingData(List<NoticeGenSearchDto> notGenSearchDtoList, Organisation organisation,
            Long notType);
    
	void sendSmsAndMail(Organisation organisation, int langId, LookUp noticeDesc, NoticeGenSearchDto notDto,
			Date notDueDate, Long userId, List<File> filesForAttach);

	ObjectionDetailsDto getObjectionDetailByRefNo(Long orgId, String objectionReferenceNumber, String deptCode);

	List<String> fetchPropertyDemandNoticeofCurrentYear(Long noticeType, Long orgid, Date fromDate, Date toDate);
}
