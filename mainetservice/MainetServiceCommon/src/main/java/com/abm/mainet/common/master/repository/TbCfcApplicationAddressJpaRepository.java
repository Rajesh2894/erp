/**
 *
 */
package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.abm.mainet.common.domain.CFCApplicationAddressEntity;

/**
 * @author Arun.Chavda
 *
 */
public interface TbCfcApplicationAddressJpaRepository extends PagingAndSortingRepository<CFCApplicationAddressEntity, Long> {

    @Query("select p.apaAreanm,p.apaCityName,p.apaPincode,p.apaMobilno,p.apaEmail from  CFCApplicationAddressEntity p where p.apmApplicationId=?1 and p.orgId.orgid=?2")
    List<Object[]> findAddressInfo(Long applicationNo, Long orgId);
    
   
    

}
