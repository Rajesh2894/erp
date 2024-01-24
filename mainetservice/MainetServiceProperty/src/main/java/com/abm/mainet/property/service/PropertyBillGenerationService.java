package com.abm.mainet.property.service;

import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.property.domain.MainBillMasEntity;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.PrintBillMaster;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

public interface PropertyBillGenerationService {

    List<NoticeGenSearchDto> fetchAssDetailBySearchCriteria(NoticeGenSearchDto specialNotGenSearchDto, long orgid);

    List<NoticeGenSearchDto> getAllPropWithAuthChangeByPropNo(NoticeGenSearchDto specNotSearchDto, Long orgId);

    List<NoticeGenSearchDto> getAllPropForBillPrint(NoticeGenSearchDto specialNotGenSearchDto, long orgid);

    List<NoticeGenSearchDto> fetchBillPrintDetailBySearchCriteria(NoticeGenSearchDto specialNotGenSearchDto, long orgid);

    PrintBillMaster getPrintBillMasterBillGeanertion(Organisation organisation, int langId, TbBillMas mainbill,
            FileNetApplicationClient fileNetApp);

    List<PrintBillMaster> getBillPrintingData(List<NoticeGenSearchDto> notGenSearchDtoList, Organisation organisation,
            int langId);

    PrintBillMaster getPrintBillMasterForDuplicateBill(Organisation organisation, int langId, MainBillMasEntity mainbill,
            ProvisionalAssesmentMstDto assessment, FileNetApplicationClient fileNetApp);

    void saveDuplicateBill(List<MainBillMasEntity> billList, ProvisionalAssesmentMstDto proAssDto, int langId, Long orgId);

    List<NoticeGenSearchDto> fetchAssDetailBySearchCriteriaForSkdcl(NoticeGenSearchDto specNotSearchDto,Long currFinYearId, Long prevFinYearId, long orgId);
    
    List<Object[]> getPropNoListBySearchCriteria(NoticeGenSearchDto noticeSearchDto,String flag,List<String> propNoList);
    
    List<NoticeGenSearchDto> fetchAssDetailBySearchCriteriaForProduct(NoticeGenSearchDto specNotSearchDto, long orgId);
    
}
