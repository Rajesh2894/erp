/**
 *
 */
package com.abm.mainet.common.integration.acccount.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;

/**
 * @author alpesh.mehta
 *
 */
public interface TbServiceReceiptJpaRepository extends PagingAndSortingRepository<TbSrcptFeesDetEntity, Long> {

    @Query("select tbSrcptFeesDetEntity from TbSrcptFeesDetEntity tbSrcptFeesDetEntity "
            + " where tbSrcptFeesDetEntity.rmRcptid.rmRcptid in (select tbServiceReceiptMasEntity.rmRcptid  from  TbServiceReceiptMasEntity tbServiceReceiptMasEntity  where tbServiceReceiptMasEntity.apmApplicationId= :applicationId  and tbServiceReceiptMasEntity.rmLoiNo is not null ) and tbSrcptFeesDetEntity.orgId=:orgId")
    List<TbSrcptFeesDetEntity> finndTaxidFeeamount(@Param("applicationId") Long applicationId, @Param("orgId") Long orgId);
    
    @Query("select tbSrcptFeesDetEntity from TbSrcptFeesDetEntity tbSrcptFeesDetEntity "
            + " where tbSrcptFeesDetEntity.rmRcptid.rmRcptid in (select tbServiceReceiptMasEntity.rmRcptid  from  TbServiceReceiptMasEntity tbServiceReceiptMasEntity  where tbServiceReceiptMasEntity.apmApplicationId= :applicationId ) and tbSrcptFeesDetEntity.orgId=:orgId")
    List<TbSrcptFeesDetEntity> finndTaxidFeeamountByAppId(@Param("applicationId") Long applicationId, @Param("orgId") Long orgId);

    @Query(value="select TAX_DESC,sum(RF_FEEAMOUNT) -- ,count(1) \r\n" + 
    		"   from tb_receipt_det a,tb_receipt_mas b,tb_tax_mas x where a.rm_rcptid=b.rm_rcptid and b.dp_deptid=10 \r\n" + 
    		"   and coalesce(RECEIPT_DEL_FLAG,'N')='N' and a.orgid=:orgId and x.tax_id=a.tax_id\r\n" + 
    		"   and date(rm_date)=:dateN \r\n" + 
    		"   group by b.orgid,TAX_DESC", nativeQuery=true)
   List<Object[]> countTaxCollection(@Param("orgId") Long orgId, @Param("dateN") String date1);
   
}
