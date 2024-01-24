/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOAdministrativeDetailEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FPOAdministrativeDetailRpository extends JpaRepository<FPOAdministrativeDetailEntity, Long>{

	/**
	 * @param entity
	 * @return
	 */
	@Query("Select m from FPOAdministrativeDetailEntity m where m.masterEntity=:entity")
	List<FPOAdministrativeDetailEntity> getAdminDetails(@Param("entity") FPOMasterEntity entity);

}
