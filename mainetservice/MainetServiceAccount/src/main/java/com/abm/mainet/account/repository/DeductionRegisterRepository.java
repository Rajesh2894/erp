
package com.abm.mainet.account.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.TbAcVendormasterEntity;

/**
 * @author satish.rathore
 *
 */
@Repository
public interface DeductionRegisterRepository extends PagingAndSortingRepository<TbAcVendormasterEntity, Long> {

    @Query("select  tb.secondaryAccountCodeMaster.sacHeadId from TbAcPayToBankEntity tb  where tb.orgid=:orgId and tb.ptbTdsType=:tdsTypeId and tb.ptbStatus='A'")
    Long getsacHeadIdbyTDSTypeId(@Param("orgId") Long orgId, @Param("tdsTypeId") Long tdsTypeId);

}
