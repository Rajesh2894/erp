
package com.abm.mainet.water.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbWtBIllDetHist;
import com.abm.mainet.water.domain.TbWtBIllMasHist;
import com.abm.mainet.water.domain.TbWtBillDetEntity;
import com.abm.mainet.water.domain.TbWtBillMasEntity;

/**
 * @author Rahul.Yadav
 *
 */
@Repository
public interface BillMasterJpaRepository extends
        PagingAndSortingRepository<TbWtBillMasEntity, Long> {

    /**
     * @param csIdn
     * @param orgid
     * @return
     */
    @Query("select b from TbWtBillMasEntity b where b.csIdn=:csIdn and b.orgid=:orgid and b.bmPaidFlag='N' order by b.bmIdno asc")
    List<TbWtBillMasEntity> getBillMasterListByCsidn(
            @Param("csIdn") Long csIdn, @Param("orgid") long orgid);

    /**
     * @param bmIdNo
     * @param orgid
     * @return
     */
    @Query("select b from TbWtBillMasEntity b where b.bmIdno=:bmIdNo and b.orgid=:orgid ")
    TbWtBillMasEntity getBillPaymentDataByBmno(
            @Param("bmIdNo") Long bmIdNo, @Param("orgid") Long orgid);

    /**
     * @param bmIdNo
     * @param orgid
     * @return
     */
    @Query("select b from TbWtBIllMasHist b where b.bmIdno=:bmIdno and b.orgid=:orgid order by  b.hBmidno desc ")
    List<TbWtBIllMasHist> fetchBillHistoryData(@Param("bmIdno") Long bmIdNo, @Param("orgid") Long orgid);

    /**
     * @param receiptId
     * @param orgid
     * @return
     */
    @Query("select b from TbWtBillDetEntity b where  b.orgid=:orgid and b.bdBilldetid in( "
            + "select d.billdetId from  TbSrcptFeesDetEntity d where  d.rmRcptid.rmRcptid=:rmRcptid and d.orgId=:orgid )")
    TbWtBillDetEntity fetchAdditinalRefNumberByBmIdNo(@Param("rmRcptid") Long receiptId, @Param("orgid") Long orgid);

    /**
     * @param bmIdno
     * @param bdBilldetid
     * @param orgid
     * @return
     */
    @Query("select d from TbWtBIllDetHist d where d.bmIdno=:bmIdno and d.orgid=:orgid and "
            + " d.bdBilldetid=:bdBilldetid order by  d.hBilldetid desc ")
    List<TbWtBIllDetHist> fetchBillDetailByBmidAndBillDetId(@Param("bmIdno") long bmIdno,
            @Param("bdBilldetid") long bdBilldetid, @Param("orgid") Long orgid);

    @Modifying
    @Transactional
    @Query("UPDATE TbWtBillMasEntity e set e.flagJvPost=:flagJvPost where e.bmIdno in (:bmNo) ")
    void updateAccountPostingFlag(@Param("bmNo") List<Long> bmIdNo, @Param("flagJvPost") String string);

    /**
     * @param csIdn
     * @param orgid
     * @return
     */
    @Query("select b from TbWtBillMasEntity b where b.csIdn in (:csIdn) and b.orgid=:orgid and b.bmPaidFlag='N' order by b.bmIdno asc")
    List<TbWtBillMasEntity> getBillMasterListByCsidnListForBilling(@Param("csIdn") List<Long> csIdn, @Param("orgid") long orgid);

    /**
     * @param csIdnBill
     * @param orgid
     * @return
     */
    @Query("select b from TbWtBillMasEntity b where b.bmIdno in ( "
            + " select max(a.bmIdno) from TbWtBillMasEntity a group by a.csIdn) and b.bmIdno in (:bmIdno) and b.orgid=:orgid and b.flagJvPost is null")
    List<TbWtBillMasEntity> fetchListOfBillsByPrimaryKey(@Param("bmIdno") List<Long> bmIdNo, @Param("orgid") long orgid);

    @Query("select c from TbKCsmrInfoMH c where c.csCcn=:csCcn and c.orgId=:orgId")
    TbKCsmrInfoMH fetchCsIdnByConnectionNumber(@Param("csCcn") String connectionNumber, @Param("orgId") Long orgId);

    @Modifying
    @Query("UPDATE TbWtBillMasEntity e set e.bmDuedate=:bmDuedate ,e.distDate=:distDate where e.bmIdno=:bmIdno and e.orgid=:orgid ")
    void updateBillDueDate(@Param("bmDuedate") Date duedate, @Param("distDate") Date disDate, @Param("orgid") long orgid,
            @Param("bmIdno") long bmIdno);

    @Query("select b from TbWtBillMasEntity b where b.bmIdno in ( "
            + " select max(a.bmIdno) from TbWtBillMasEntity a where  a.csIdn in (:csIdn) and a.orgid=:orgid group by a.csIdn)")
    List<TbWtBillMasEntity> checkBillsForNonMeter(@Param("csIdn") List<Long> csIdn, @Param("orgid") long orgid);
    
    @Query("select b from TbWtBillMasEntity b where b.csIdn=:csIdn and b.orgid=:orgid order by  b.bmIdno ")
    List<TbWtBillMasEntity> fetchBillMasData(@Param("csIdn") Long csIdn, @Param("orgid") Long orgid);
    
    @Query("select b from TbWtBillMasEntity b where b.bmIdno in ( "
            + " select max(a.bmIdno) from TbWtBillMasEntity a where  a.csIdn in (:csIdn))")
    List<TbWtBillMasEntity> getBilMasterByCsIdList(@Param("csIdn") List<Long> csIdn);

    @Query("select b from TbWtBillMasEntity b where b.bmIdno in (:bmIdno) and b.orgid=:orgid and b.flagJvPost is null")
    List<TbWtBillMasEntity> fetchBillsListByBmId(@Param("bmIdno") List<Long> bmIdNo, @Param("orgid") long orgid);
    
    @Query("from TbWtBillMasEntity "
            + "  am  WHERE  am.orgid=:orgId and am.csIdn=:csIdno order by bmIdno ")
    List<TbWtBillMasEntity> fetchAllBillByCsIdn(@Param("csIdno") Long csIdno, @Param("orgId") long orgId);

    @Query("select b from TbWtBillMasEntity b where b.csIdn=:csIdn and b.orgid=:orgid and b.bmYear=:bmYear and b.bmPaidFlag='N' order by b.bmIdno desc")
    List<TbWtBillMasEntity> getBillListByCsidnForFinYear(
            @Param("csIdn") Long csIdn, @Param("orgid") long orgid, @Param("bmYear") Long bmYear);
 
    /**
     * Get bill details by csidn, bill no and orgid
     * @param csIdn
     * @param selectedBillNo
     * @param orgid
     * @return
     */
    @Query("select b from TbWtBillMasEntity b where b.csIdn =:csIdn and b.bmNo=:bmNo and b.orgid=:orgid")
    TbWtBillMasEntity getBillDataByBillNo(@Param("csIdn") Long csIdn, @Param("bmNo") String bmNo, @Param("orgid") Long orgid);
    
    @Query("from TbWtBillMasEntity am  WHERE  am.orgid=:orgId and am.csIdn=:csIdno order by bmIdno desc")
    List<TbWtBillMasEntity> fetchAllBillByCsIdnDesc(@Param("csIdno") Long csIdno, @Param("orgId") long orgId);

}
