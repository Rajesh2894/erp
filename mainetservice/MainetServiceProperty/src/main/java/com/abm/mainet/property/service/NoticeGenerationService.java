package com.abm.mainet.property.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.PrintBillMaster;
import com.abm.mainet.property.dto.SpecialNoticeReportDto;

public interface NoticeGenerationService extends Serializable {

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

    List<PrintBillMaster> getNoticePrintingData(List<NoticeGenSearchDto> notGenSearchDtoList, Organisation organisation);

}
