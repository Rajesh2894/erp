/**
 * 
 */
package com.abm.mainet.property.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.property.domain.BillingScheduleDetailEntity;
import com.abm.mainet.property.domain.BillingScheduleMstEntity;

/**
 * @author hiren.poriya
 * @since 21-Nov-2017
 */
@Repository
public interface BillingScheduleRepository extends JpaRepository<BillingScheduleMstEntity, Long> {

    /**
     * find all active billing schedule details.
     * @param orgId
     * @return
     */
    @Query("select bs from BillingScheduleMstEntity bs where bs.orgId=:orgId and bs.asBillStatus='A' order by bs.asBillScheid desc ")
    List<BillingScheduleMstEntity> findAllRecords(@Param("orgId") Long orgId);

    @Query("select bs from BillingScheduleMstEntity bs where bs.orgId=:orgId and bs.asBillScheid=:schId ")
    BillingScheduleMstEntity findBillSchById(@Param("orgId") Long orgId, @Param("schId") Long schId);

    @Query("select bs from BillingScheduleDetailEntity bs where bs.billScheduleMas.asBillScheid=:schId and bs.status='A' and bs.orgid=:orgId")
    List<BillingScheduleDetailEntity> getBillScheduleDetBySchId(@Param("schId") Long schId, @Param("orgId") Long orgId);

    /**
     * Inactive Current Bill Schedule
     * @param billId
     */
    @Transactional
    @Modifying
    @Query(" UPDATE BillingScheduleMstEntity SET asBillStatus = 'I' WHERE asBillScheid=:asBillScheid and orgId=:orgId")
    void deleteBillScheduleMas(@Param("asBillScheid") Long asBillScheid, @Param("orgId") Long orgId);

    @Transactional
    @Modifying
    @Query(" update  BillingScheduleDetailEntity set status='I' where billScheduleMas.asBillScheid=:asBillScheid and orgid=:orgId and "
            +
            "billScheduleMas in ( select asBillScheid from BillingScheduleMstEntity where asBillStatus='A')")
    void deleteBillScheduleDet(@Param("asBillScheid") Long asBillScheid, @Param("orgId") Long orgId);

    @Transactional
    @Modifying
    @Query(" UPDATE BillingScheduleDetailEntity SET status = 'I' WHERE billScheduleMas.asBillScheid=:asBillScheid and orgid=:orgId")
    void updateOldDetailsRecord(@Param("asBillScheid") Long asBillScheid, @Param("orgId") Long orgId);

    @Query("select om.tbFinancialyear from TbFincialyearorgMapEntity om where om.orgid=:orgId and om.tbFinancialyear "
            + "not in(select tbFinancialyear from BillingScheduleMstEntity bs where bs.orgId=:orgId and bs.asBillStatus='A' )"
            + " and om.orgid=:orgId order by om.faFromYear ")
    List<FinancialYear> findAllFinYearNotMapInBillSchByOrgId(@Param("orgId") Long orgId);

    @Query("select d from BillingScheduleDetailEntity d where " +
            "d.billScheduleMas in (select m.asBillScheid from BillingScheduleMstEntity m where m.asBillStatus='A' and m.orgId=:orgId)"
            +
            "and  d.status='A' and :schFromDate between d.billFromDate and d.billToDate and d.orgid=:orgId")
    BillingScheduleDetailEntity getScheduleBySchFromDate(@Param("orgId") Long orgId, @Param("schFromDate") Date schFromDate);

    @Query("select d from BillingScheduleDetailEntity d " +
            " where d.schDetId=:schDetId and d.orgid=:orgId and d.status='A' ")
    BillingScheduleDetailEntity getSchDetailByScheduleId(@Param("schDetId") Long schDetId, @Param("orgId") Long orgId);

    @Query("select a from BillingScheduleDetailEntity a where  a.status='A' and  a.orgid=:orgId and  a.billFromDate > "
            + "(select b.billFromDate from BillingScheduleDetailEntity b where  b.schDetId=:schDetId and  b.status='A')"
            + " order by a.billFromDate asc ")
    public List<BillingScheduleDetailEntity> getFinanceYearListFromLastPayment(@Param("schDetId") Long schDetId,
            @Param("orgId") Long orgId);

    @Query("select d.schDetId,d.billFromDate,d.billToDate" +
            " from BillingScheduleDetailEntity d " +
            " where (d.billFromDate >= :fromdate or d.billToDate>= :fromdate) and (d.billFromDate<= :currDate or d.billToDate <= :currDate) "
            + " and d.status='A' and d.orgid=:orgid ")
    List<Object[]> getAllBillScheduleFromDate(@Param("fromdate") Date startDate, @Param("currDate") Date currDate,
            @Param("orgid") Long orgid);

    @Query("select d.schDetId, s.asBillFrequency, f.faFromDate, f.faToDate,d.billFromDate,d.billToDate" +
            " from BillingScheduleMstEntity s,BillingScheduleDetailEntity d,FinancialYear f " +
            " where s.asBillScheid=d.billScheduleMas.asBillScheid" +
            " and s.tbFinancialyear.faYear =f.faYear and s.orgId =:orgid and s.asBillStatus='A' " +
            " and d.status='A' order by  f.faFromDate,s.asBillFrequency,d.schDetId ")
    List<Object[]> getBillscheduleByOrgid(@Param("orgid") Long orgid);

    @Query("select d.billScheduleMas.tbFinancialyear.faYear from BillingScheduleDetailEntity d " +
            " where d.schDetId=:schDetId and d.orgid=:orgId and d.status='A' ")
    Long getfinYearIdBySchId(@Param("schDetId") Long schDetId, @Param("orgId") Long orgId);

    @Query("select d from BillingScheduleDetailEntity d where orgid=:orgId and billFromDate <=(select faFromDate from FinancialYear where :currentDate between faFromDate and faToDate) "
            +
            "and status='A' and billFromDate >= ( " +
            "select billFromDate from BillingScheduleDetailEntity where orgid=:orgId and schDetId=:schDetId and status='A') " +
            "order by billFromDate")
    List<BillingScheduleDetailEntity> getSchListFromschIdTillCurDate(@Param("schDetId") Long schDetId,
            @Param("orgId") Long orgId, @Param("currentDate") Date currentDate);

    @Query("select d from BillingScheduleDetailEntity d where orgid=:orgId and billFromDate >=(select faFromDate from FinancialYear where :scheDate between faFromDate and faToDate) "
            +
            "and billFromDate<=(select faFromDate from FinancialYear where :currentDate between faFromDate and faToDate) and status='A' "
            +
            "order by billFromDate desc")
    List<BillingScheduleDetailEntity> getSchListFromGivenDateTillCurDate(@Param("scheDate") Date scheDate,
            @Param("orgId") Long orgId, @Param("currentDate") Date currentDate);

    @Query("select d from BillingScheduleDetailEntity d where " +
            "d.billScheduleMas in (select m.asBillScheid from BillingScheduleMstEntity m where m.asBillStatus='A' and m.orgId=:orgId)"
            +
            "and  d.status='A' and d.orgid=:orgId")
    List<BillingScheduleDetailEntity> getSchedulebyOrgId(@Param("orgId") Long orgId);

}
