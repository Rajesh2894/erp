package com.abm.mainet.water.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.water.constant.QueryConstants;
import com.abm.mainet.water.domain.TbWtBillDetEntity;

/**
 * Repository : TbBillDet.
 */
@Repository
public interface TbWtBillDetJpaRepository extends
        PagingAndSortingRepository<TbWtBillDetEntity, Long> {

    @Query(QueryConstants.WATER_MODULE_QUERY.BILL_DET_QUERY.BILLDET_COLLSEQ_LIST)
    Iterable<TbWtBillDetEntity> getBillDetailEntity(
            @Param("billIds") List<Long> billPrintIds,
            @Param("orgId") long orgid);
    
    @Modifying
    @Query("delete  from TbWtBillDetEntity b where b.bmIdNo.bmIdno =:bmIdNo ")
    void deleteArrearByBmIdFromDet(@Param("bmIdNo") Long bmIdNo);

    @Modifying
    @Query("update TbWtBillDetEntity as det set det.bdCurBalTaxamt =:bdCurBalTaxamt where det.bdBilldetid =:bdBilldetid and det.orgid =:orgid")
    void updatePrevBillDetails(@Param("bdBilldetid")Long bdBilldetid, @Param("bdCurBalTaxamt") Double bdCurBalTaxamt, @Param("orgid") Long orgid);
}
