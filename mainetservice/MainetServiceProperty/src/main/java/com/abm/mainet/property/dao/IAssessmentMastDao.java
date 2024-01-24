package com.abm.mainet.property.dao;

import java.util.List;

import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.property.domain.AssesmentMastEntity;
import com.abm.mainet.property.dto.NoticeGenSearchDto;
import com.abm.mainet.property.dto.ProperySearchDto;

public interface IAssessmentMastDao {

    AssesmentMastEntity fetchAssDetailAssNoOrOldpropno(long orgId, String assNo, String assOldpropno, String status,String logicalPropNo);

    AssesmentMastEntity fetchAssessmentDetailsAssNoOrOldpropno(long orgId, String assNo, String assOldpropno, String status);

    List<AssesmentMastEntity> fetchAssDetailBySearchCriteriaForBill(long orgId, NoticeGenSearchDto specNotSearchDto,
            List<String> propNos, Long finId);

    List<AssesmentMastEntity> fetchAssDetailBySearchCriteria(long orgId, NoticeGenSearchDto notGenDto);

    List<AssesmentMastEntity> fetchAllSpecialNoticePropBySearchCriteria(long orgId, NoticeGenSearchDto notGenDto);

    List<Object[]> getAllPropBillGeneByPropNo(Long finId, Long orgId);

    int getCountWhetherMaxBmIdExistInMainBill(String propNo, Long orgId);

    List<Object[]> getAllPropBillForPrint(NoticeGenSearchDto specNotSearchDto, long orgid, List<String> propNoList);

    List<Object[]> getAllPropBillForPrintByOldPropNo(NoticeGenSearchDto specNotSearchDto, long orgid,
            List<String> oldNoList);

    List<Object[]> fetchBillPrintDetailBySearchCriteria(NoticeGenSearchDto specialNotGenSearchDto, long orgid);

    List<AssesmentMastEntity> getAllPropertyAfterDueDate(long orgid, List<String> propNoList, List<String> oldNoList,
            NoticeGenSearchDto specNotSearchDto);

    List<AssesmentMastEntity> getNoticesAfterDueDate(long orgid, List<String> propNoList, List<String> oldNoList,
            NoticeGenSearchDto specNotSearchDto, Long noticeType);

    List<AssesmentMastEntity> fetchPropertyDemandNoticePrint(long orgid, List<String> propNoList, List<String> oldNoList,
            NoticeGenSearchDto specNotSearchDto);

    List<AssesmentMastEntity> fetchAllObjNotPropBySearchCriteria(long orgId, NoticeGenSearchDto notGenDto);

    boolean checkForAssesmentFieldForCurrYear(Long orgId, String assNo, String assOldpropno, String status, Long finYearId,
            Long serviceId);

    List<AssesmentMastEntity> searchPropetyForView(ProperySearchDto searchDto, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO,
            Long serviceId);

    boolean checkForAssesmentFieldForCurrYear(Long orgId, String assNo, String assOldpropno, String status, Long finYearId);

    AssesmentMastEntity fetchDataEntryByAssNoOrOldPropNo(long orgId, String assNo, String assOldpropno);
    
    AssesmentMastEntity fetchAssessmentDetailsAssNoOrOldpropnoAndFlatNo(long orgId, String assNo, String assOldpropno,
            String status,String logicalPropNo);
    
    
    List<Object[]> getAllMaxBmYearByPropNo(List<String> propNoList, Long orgId);
    
    List<Object[]> getAllBillsForBillDistribution(List<String> propNoList, Long orgId);
    
    List<Object[]> getAllBillsForBillDistributionForupdation(List<String> propNoList, Long orgId);
    
    public List<Object[]> getAllPropBillGeneByPropNoList(Long finId, Long orgId,List<String> propNoList);

	List<String> fetchAssessmentByGroupPropNo(Long orgId, String groupPropName, String parentPropName, String status);

	List<Object[]> fetchPropNoWhoseBillNotPresent(Long orgId, NoticeGenSearchDto NoticeGenSearchDto);

	List<String> fetParentBillNoOfBillGeneratedProps(Long finId, Long orgId, List<String> propNoList);

	List<Object[]> getConsolidatedBillsForGrpProperty(Long finId, Long orgId, String parentpropNo);
	
	List<Object[]> getAllMaxBmYearByPropNoForWhole(List<String> propNoList, Long orgId);
}
