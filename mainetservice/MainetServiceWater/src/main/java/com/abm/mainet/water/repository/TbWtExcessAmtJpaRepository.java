package com.abm.mainet.water.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.water.domain.TbWtExcessAmt;
import com.abm.mainet.water.domain.TbWtExcessAmtHist;

/**
 * @author Rahul.Yadav
 *
 */
public interface TbWtExcessAmtJpaRepository extends
        PagingAndSortingRepository<TbWtExcessAmt, Long> {

    /**
     * @param csIdn
     * @param orgId
     * @return
     */
    @Query("select e from TbWtExcessAmt e where e.csIdn=:csIdn and e.orgId=:orgId and (e.excessActive='A' or e.excessActive is null)")
    TbWtExcessAmt findExcessAmountByCsIdnAndOrgId(
            @Param("csIdn") long csIdn,
            @Param("orgId") Long orgId);

    /**
     * @param csIdn
     * @param orgid
     */
    @Query("select e from TbWtExcessAmt e where e.csIdn=:csIdn and e.orgId=:orgId and (e.excessActive='A' or e.excessActive is null)")
    TbWtExcessAmt fetchExcessAmountData(@Param("csIdn") Long csIdn, @Param("orgId") Long orgid);

    /**
     * @param excessId
     * @param orgid
     * @param csIdn
     * @return
     */
    @Query("select e from TbWtExcessAmtHist e where e.csIdn=:csIdn and e.orgId=:orgId and e.excessId=:excessId  order by e.hExcessId desc")
    List<TbWtExcessAmtHist> fetchAdvancePaymentHistory(@Param("excessId") long excessId, @Param("orgId") Long orgid,
            @Param("csIdn") Long csIdn);

    /**
     * @param csIdn
     * @param orgid
     */
    @Query("select e.csIdn,e.excAmt,e.adjAmt from TbWtExcessAmt e where e.csIdn in(:csIdn) and e.orgId=:orgId and (e.excessActive='A' or e.excessActive is null)")
    List<Object[]> fetchExcessAmountDataForPrinting(@Param("csIdn") List<Long> csIdn, @Param("orgId") Long orgid);

    /**
     * @param csIdn
     * @param orgid
     */
    @Query("select e from TbWtExcessAmt e where e.csIdn in (:csIdn) and e.orgId=:orgId and (e.excessActive='A' or e.excessActive is null)")
    List<TbWtExcessAmt> fetchBalanceAdvancePaymentData(@Param("csIdn") List<Long> csIdn, @Param("orgId") long orgid);
    
    @Query("select a from TbWtExcessAmt a where a.csIdn=:csIdn and a.orgId=:orgId and a.rmRcptid=:rmRcptid")
    TbWtExcessAmt getAdvanceEntryByRecptId(@Param("rmRcptid") Long rmRcptid, @Param("csIdn") Long csIdn,
            @Param("orgId") Long orgId);
    

	
	@Modifying
	@Transactional
	@Query("update TbWtExcessAmt a set a.excessActive='I'  where a.excessId=:excessId and a.orgId=:orgId ")
	void inactiveAdvPayEnrtyByExcessId(@Param("excessId") long excessId, @Param("orgId") Long orgId);
	 

}