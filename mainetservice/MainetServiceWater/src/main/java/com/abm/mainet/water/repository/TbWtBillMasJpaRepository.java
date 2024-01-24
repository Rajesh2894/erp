package com.abm.mainet.water.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.water.constant.QueryConstants;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBillMasEntity;

/**
 * Repository : TbWtBillMas.
 */
public interface TbWtBillMasJpaRepository
        extends
        PagingAndSortingRepository<TbWtBillMasEntity, Long> {

    /**
     * @param billPrintIds
     * @param orgid
     * @return
     */
    @Query(QueryConstants.WATER_MODULE_QUERY.BILL_MAS_QUERY.BILL_MAS_PRINT)
    List<Object[]> getBillPrintingData(
            @Param("billIds") List<Long> billPrintIds,
            @Param("orgId") long orgid);

    /**
     * @param csCcn
     * @param orgid
     * @return
     */
    @Query("select b from TbKCsmrInfoMH b where b.csCcn=:csCcn and b.orgId=:orgId and b.conActive is null")
    TbKCsmrInfoMH getCsIdnByConnectionNumber(@Param("csCcn") String csCcn,@Param("orgId") Long orgId);

    /**
     * @param csIdn
     * @param orgid
     * @param billFrom
     * @param billTo
     * @return
     */
    @Query("select b from TbWtBillMasEntity b where b.csIdn=:csIdn and b.orgid=:orgid and b.bmFromdt=:billFrom and b.bmTodt=:billTo  order by  b.bmIdno desc ")
    List<TbWtBillMasEntity> getBillMasterbyCsidnFromDateToDAte(@Param("csIdn") Long csIdn,
            @Param("orgid") long orgid, @Param("billFrom") Date billFrom, @Param("billTo") Date billTo);

    @Query("select b.csMeteredccn,max(bm.bmIdno) from TbKCsmrInfoMH b,TbWtBillMasEntity bm where bm.csIdn=b.csIdn and bm.orgid=b.orgId "
            + " and b.csIdn=:csIdn and b.orgId=:orgId ")
    List<Object[]> fetchConnectionDataAndBillId(@Param("csIdn") Long csIdn, @Param("orgId") Long orgid);

    @Query("select b from TbWtBillMasEntity b where b.csIdn=:csIdn order by b.bmIdno asc")
    List<TbWtBillMasEntity> getBillMasByConnectionId(@Param("csIdn") Long csIdn);
    
    @Query("select b from TbWtBillMasEntity b where b.csIdn=:csIdn and b.bmYear=:finYearId")
    List<TbWtBillMasEntity> getArrearsDeletionBills(@Param("csIdn") Long csIdn,@Param("finYearId") Long finYearId);
    
    
    @Query("select b from TbWtBillMasEntity b where b.csIdn=:csIdn and b.bmYear not in (:finYearId)")
    List<TbWtBillMasEntity> getArrearsDeletionBillsNotinCurrFinYear(@Param("csIdn") Long csIdn,@Param("finYearId") Long finYearId);
    
    @Modifying
    @Query("delete  from TbWtBillMasEntity b where b.bmIdno=:bmIdno")
    void deleteArrearByBmIdFromMas(@Param("bmIdno") Long bmIdno);
    
    /**
     * @param CS_OLDCCN
     * @param orgid
     * @return
     */
    @Query("select b from TbKCsmrInfoMH b where b.csOldccn=:csOldccn and b.orgId=:orgId")
    TbKCsmrInfoMH getCsIdnByOldConnectionNumber(@Param("csOldccn") String csOldccn,@Param("orgId") Long orgId);
    
    @Query("SELECT count(*) FROM  TbKCsmrInfoMH b  WHERE b.csCcn=:csCcn  and b.orgId=:orgId")
    Long getCountByCcnNo(@Param("csCcn") String csCcn, @Param("orgId") Long orgId);
    
    @Query("SELECT count(*) FROM  TbKCsmrInfoMH b  WHERE b.csOldccn=:csOldccn and b.orgId=:orgId")
    Long getCountByoldConnNo(@Param("csOldccn") String csOldccn,@Param("orgId") Long orgId);
 
    
	@Query("select count(*) from TbWtBillMasEntity b where b.csIdn=:csIdn and b.bmYear=:finYearId and b.orgid=:orgId and (:currentDate between b.bmFromdt and b.bmTodt)")
	Long getArrearsDeletionCurrentBillCount(@Param("csIdn") Long csIdn, @Param("finYearId") Long finYearId,
			@Param("orgId") Long orgId, @Param("currentDate") Date currentDate);
	
	@Query("select b from TbWtBillMasEntity b where b.csIdn=:csIdn and b.orgid=:orgId")
    List<TbWtBillMasEntity> getArrearsDeletionBillsForMonthly(@Param("csIdn") Long csIdn,@Param("orgId") Long orgId);
	
	@Query("select b from TbWtBillMasEntity b where b.csIdn=:csIdn and b.orgid=:orgId and b.bmNo >=:billNo")
    List<TbWtBillMasEntity> getArrearsDeletionBillsByCsIdnAndBillNo(@Param("csIdn") Long csIdn,@Param("orgId") Long orgId, @Param("billNo") String billNo);
	
	@Query("select b from TbWtBillMasEntity b where b.csIdn=:csIdn and b.orgid=:orgId and b.bmYear >=:finId")
    List<TbWtBillMasEntity> getArrearsDeletionBillsByGreaterAndEqualfinId(@Param("csIdn") Long csIdn,@Param("orgId") Long orgId, @Param("finId") Long finId);

    @Query(QueryConstants.WATER_MODULE_QUERY.BILL_MAS_QUERY.BILL_PRINT_SKDCL)
    List<Object[]> getBillPrintingDataForSkdcl(@Param("billIds") List<Long> billPrintIds, @Param("orgId") long orgid);

    @Modifying
    @Query("update TbWtBillMasEntity as mas set mas.bmTotalArrears =:bmTotalArrears, mas.bmTotalCumIntArrears =:bmTotalCumIntArrears, mas.bmToatlInt =:bmToatlInt, mas.bmTotalOutstanding =:bmTotalOutstanding where mas.bmIdno =:bmIdno and mas.orgid =:orgid")
	void updatePrevBillMasInterestValue(@Param("bmIdno") Long bmIdno, @Param("bmTotalArrears") Double bmTotalArrears,
			@Param("bmTotalCumIntArrears") Double bmTotalCumIntArrears, @Param("bmToatlInt") Double bmToatlInt,
			@Param("bmTotalOutstanding") Double bmTotalOutstanding, @Param("orgid") Long orgid);

    @Query("select b from TbWtBillMasEntity b where b.csIdn=:csIdn and b.orgid=:orgid order by b.bmIdno ")
    List<TbWtBillMasEntity> fetchBillMasData(@Param("csIdn") Long csIdn, @Param("orgid") Long orgid);

    @Query(QueryConstants.WATER_MODULE_QUERY.BILL_MAS_QUERY.DUPLICATE_BILL_PRINT_SKDCL)
	List<Object[]> getDuplicateBillPrintingData(@Param("orgid")Long orgid, @Param("bmNo") String bmNo);
	
	@Query("select b from TbKCsmrInfoMH b where b.applicationNo=:applicationNo")
    TbKCsmrInfoMH getDataWithApplicationNo(@Param("applicationNo") Long applicationNo);
}
