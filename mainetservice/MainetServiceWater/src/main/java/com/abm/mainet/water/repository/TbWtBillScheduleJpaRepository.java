package com.abm.mainet.water.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.water.domain.TbWtBillScheduleEntity;

/**
 * Repository : TbWtBillSchedule.
 */
public interface TbWtBillScheduleJpaRepository extends JpaRepository<TbWtBillScheduleEntity, Long>{

    /**
     * @param finYearId
     * @param orgid
     * @param meteredtype
     * @return
     */
    @Query("select s from TbWtBillScheduleEntity s where s.cnsYearid=:finYearId and s.orgid=:orgid and s.cnsMn=:cnsMn")
    List<TbWtBillScheduleEntity> getBillScheduleByFinYearId(@Param("finYearId") Long finYearId, @Param("orgid") long orgid,
            @Param("cnsMn") String meteredtype);

    /**
     * @param finYearId
     * @param billfrequency
     * @param orgId
     * @param meterType
     * @return
     */
    @Query("select s from TbWtBillScheduleEntity s where s.cnsYearid=:finYearId and s.orgid=:orgid and s.tbComparamDet.cpdId=:billFrequency and "
            + " s.cnsMn=:meterType")
    List<TbWtBillScheduleEntity> getBillScheduleByFinYearIdAndBillFrequency(@Param("finYearId") Long finYearId,
            @Param("billFrequency") Long billfrequency, @Param("orgid") Long orgId, @Param("meterType") String meterType);

    @Query("select s from TbWtBillScheduleEntity s where s.orgid=:orgid  and s.cnsMn=:meterType "
            + " and s.cnsYearid in(select f.faYear from FinancialYear f where f.faFromDate>=(select f.faFromDate from FinancialYear f where :fromDate between f.faFromDate and f.faToDate)"
            + " and f.faFromDate<=(select f.faFromDate from FinancialYear f where :toDate between f.faFromDate and f.faToDate)) ")
    List<TbWtBillScheduleEntity> getBillScheduleFromToYear(@Param("fromDate") Date fromdate, @Param("orgid") long orgid,
            @Param("meterType") String meterType, @Param("toDate") Date todate);
    
    
    @Query("select s from TbWtBillScheduleEntity s where s.orgid=:orgid and s.cnsMn=:cnsMn")
    List<TbWtBillScheduleEntity> getBillScheduleByOrgId(@Param("orgid") long orgid,
            @Param("cnsMn") String meteredtype);
}
