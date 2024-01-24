/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOBankDetailEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FPOBankDetailRepository extends JpaRepository<FPOBankDetailEntity, Long>{

	/**
	 * @param entity
	 * @return
	 */
	@Query("Select m from FPOBankDetailEntity m where m.masterEntity=:entity")
	List<FPOBankDetailEntity> getBankDetails(@Param("entity") FPOMasterEntity entity);

}
