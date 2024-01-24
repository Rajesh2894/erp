/**
 * 
 */
package com.abm.mainet.rnl.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.rnl.domain.TankerBookingDetailsEntity;


/**
 * @author arif.shaikh
 *
 */
@Repository
public interface WaterTankerBookingRepository extends CrudRepository<TankerBookingDetailsEntity, Long> {

	@Modifying
	@Transactional
    @Query("update TankerBookingDetailsEntity tbd set tbd.tankerReturnDate =:returndate ,tbd.returnRemark =:returnRemark,tbd.updatedBy =:updatedBy,tbd.updatedDate =:updatedDate where tbd.estateBooking.id =:ebk_id")
	void update(@Param("returndate") Date returndate, @Param("returnRemark") String returnRemark,
			@Param("updatedBy") Long updatedBy, @Param("updatedDate")Date updatedDate, @Param("ebk_id")Long id);
	
	@Query("select distinct tbd from TankerBookingDetailsEntity tbd where tbd.estateBooking.id =:ebk_id")
	TankerBookingDetailsEntity findByEbkId( @Param("ebk_id")Long id);

}
