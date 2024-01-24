

package com.abm.mainet.materialmgmt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.ItemMasterConversionEntity;


@Repository
public interface ItemMasterConversionRepository
        extends PagingAndSortingRepository<ItemMasterConversionEntity, Long> {
	
	  @Query("select e from ItemMasterConversionEntity e  where e.convId=:convId order by 1 desc")
	 ItemMasterConversionEntity findItemMasterDetailsByConvId(@Param("convId") Long convId);
	
	    

}
