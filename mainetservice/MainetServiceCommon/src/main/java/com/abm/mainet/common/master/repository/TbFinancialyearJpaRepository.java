package com.abm.mainet.common.master.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.FinancialYear;

/**
 * Repository : TbFinancialyear.
 */
public interface TbFinancialyearJpaRepository extends
        PagingAndSortingRepository<FinancialYear, Long> {

    @Query("select fnYear from FinancialYear fnYear where fnYear.faFromDate=:faFromdate")
    FinancialYear finanacialYearAlreadyExist(@Param("faFromdate") Date fromDate);

    @Query("select fnYear from FinancialYear fnYear order by fnYear.faFromDate asc")
    List<FinancialYear> findAllFinYear();
    
    @Query("select fnYear from FinancialYear fnYear Where fnYear.faFromDate <= CURRENT_DATE order by fnYear.faFromDate desc")
    List<FinancialYear> findAllFinYearInBudget();
    
    @Query("SELECT fy.faYear,fy.faFromDate,fy.faToDate FROM FinancialYear fy Where fy.faFromDate <= CURRENT_DATE order by fy.faFromDate desc")
    List<Object[]> getAllFinincialYear();

    @Query("select fnYear from FinancialYear fnYear where fnYear.faFromDate=:faFromdate")
    FinancialYear finanacialYearAlreadyExistCount(@Param("faFromdate") Date fromDate);

    @Query("select f from FinancialYear f where :inputDate between f.faFromDate and f.faToDate")
    FinancialYear getFinanciaYearId(@Param("inputDate") Date tenderEntryDate);

    @Query("SELECT fy.faFromDate,fy.faToDate FROM FinancialYear fy Where fy.faYear= :faYearid")
    List<Object[]> getFinanceYearFrmDate(@Param("faYearid") Long faYearid);

    @Query("SELECT fy.faYear FROM FinancialYear fy Where fy.faFromDate= :dateFrm")
    Long getFinanceYearIds(@Param("dateFrm") Date dateFrm);

    @Query("select sum(fd.rfFeeamount) from TbServiceReceiptMasEntity rm,TbSrcptFeesDetEntity fd where rm.rmRcptid = fd.rmRcptid.rmRcptid and fd.budgetCode.prBudgetCodeid =:prBudgetCodeid and rm.rmDate between :dateFrom and :dateTo and rm.orgId = fd.orgId and rm.orgId= :orgId")
    BigDecimal getSumFeeAmount(@Param("prBudgetCodeid") Long prBudgetCodeid, @Param("dateFrom") Date dateFrom,
            @Param("dateTo") Date dateTo, @Param("orgId") Long orgId);

    @Query("select f from FinancialYear f where f.faFromDate >= :inputDate order by f.faFromDate")
    List<FinancialYear> getFinanciaYearsFromDate(@Param("inputDate") Date fromDate);

    @Query("select f.faYear from FinancialYear f where :inputDate between f.faFromDate and f.faToDate")
    Long getFinanciaYearIds(@Param("inputDate") Date receiptEntryDate);

    @Query("SELECT count(finYear) FROM FinancialYear finYear WHERE "
            + "extract(YEAR from finYear.faFromDate)=:fromYear and "
            + "extract(YEAR from finYear.faToDate)=:toYear")
    Long getPreviousFinancialYear(@Param("fromYear") int fromYear, @Param("toYear") int toYear);

    @Query("SELECT fYear.faToDate FROM FinancialYear fYear WHERE "
            + "fYear.faYear IN (SELECT MAX(fYear1.faYear) FROM FinancialYear fYear1)")
    Date getValidFInancialYearDate();

    @Query("SELECT fy.faYear,fy.faFromDate,fy.faToDate FROM FinancialYear fy Where fy.faFromDate =:sliDate order by fy.faFromDate desc")
    List<Object[]> getAllSLIPrefixDateFinincialYear(@Param("sliDate") Date sliDate);

    @Query("SELECT count(finYear) FROM FinancialYear finYear")
    int getFinancialYearTotalCount();

    @Query("SELECT MIN(fYear.faFromDate) FROM FinancialYear fYear")
    Date getMinFinancialYear();

    @Query("select m.yaTypeCpdId from FinancialYear f,TbFincialyearorgMapEntity m where f.faYear=m.tbFinancialyear.faYear and :inputDate between f.faFromDate and f.faToDate and m.orgid =:orgid")
    Long getFinancialYearTotalCount(@Param("inputDate") Date date, @Param("orgid") Long orgid);

    @Query("select fnYear from FinancialYear fnYear where fnYear.faYear in (:finYearIdList) ")
    List<FinancialYear> findAllFinancialYearById(@Param("finYearIdList") List<Long> finYearIdList);

    @Query("SELECT DISTINCT fy.tbFinancialyear.faYear,fy.tbFinancialyear.faFromDate,fy.tbFinancialyear.faToDate FROM TbFincialyearorgMapEntity fy Where fy.yaTypeCpdId =:finYarStatusId and fy.orgid =:orgId and fy.tbFinancialyear.faFromDate <= CURRENT_DATE order by fy.tbFinancialyear.faFromDate desc")
    List<Object[]> getAllFinincialYearStatusOpen(@Param("finYarStatusId") Long finYarStatusId, @Param("orgId") Long orgId);

    @Query("SELECT  f.faYear FROM FinancialYear f where :fromDate  between f.faFromDate and f.faToDate")
    Long getFinanciaYearIdByFromDate(@Param("fromDate") Date fromDate);

    @Query("select fa.faFromDate from FinancialYear fa where :dateBefore between fa.faFromDate and fa.faToDate")
    Date getFromDateFromFinancialYearIdByPassingDate(@Param("dateBefore") Date dateBefore);

    @Query("select m.faMonthStatus from FinancialYear f,TbFincialyearorgMapEntity m where f.faYear=m.tbFinancialyear.faYear and :inputDate between f.faFromDate and f.faToDate and m.orgid =:orgid")
    Long getFinancialYearMonthTotalCount(@Param("inputDate") Date date, @Param("orgid") Long orgid);
    
    @Query("select count(1) from FinancialYear f where f.faFromDate between :fromDate and :toDate")
    int getCountOfFinYearBetwDates(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
