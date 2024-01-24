/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOMasterDetailEntity;
import com.abm.mainet.sfac.domain.FPOMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FPOMasterDetailRepository extends JpaRepository<FPOMasterDetailEntity, Long>{

	/**
	 * @param entity
	 * @return
	 */
	@Query("Select d From FPOMasterDetailEntity d where d.masterEntity=:entity")
	List<FPOMasterDetailEntity> getDetails(@Param("entity") FPOMasterEntity entity);

}
