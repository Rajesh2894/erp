/**
 * 
 */
package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.MeasurementBookTaxDetails;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Repository
public interface MeasurementBookTaxDetailRepository extends CrudRepository<MeasurementBookTaxDetails, Long>{

	
	/**
	 * used to delete MeasurementBookLbh  by MB Tax details ID
	 * 
	 * @param deleted MB_TAXID
	 */
	@Modifying
	@Query("DELETE from  MeasurementBookTaxDetails a where a.mbTaxId in (:mbTaxId)")
	void deleteMbTaxDetByMbTaxId(@Param("mbTaxId") List<Long> mbTaxId);
	
	/**
	 * used to get MB_Tax_Details by mbTaxID
	 * 
	 * @param mbId
	 * @return
	 */
	@Query("select mbTax from MeasurementBookTaxDetails mbTax where mbTax.mbMaster.workMbId = :mbId and mbTax.orgId =:orgId")
	List<MeasurementBookTaxDetails> getMBTaxDetByMbId(@Param("mbId") Long mbId,@Param("orgId") Long orgId);
	
	
}
