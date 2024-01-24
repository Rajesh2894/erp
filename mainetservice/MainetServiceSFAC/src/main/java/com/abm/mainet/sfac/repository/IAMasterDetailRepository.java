/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.IAMasterDetailEntity;
import com.abm.mainet.sfac.domain.IAMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface IAMasterDetailRepository extends  JpaRepository<IAMasterDetailEntity, Long>{

	/**
	 * @param entity
	 * @return
	 */
	@Query("Select m from IAMasterDetailEntity m where m.masterEntity=:entity")
	List<IAMasterDetailEntity> getIaDetail(@Param("entity") IAMasterEntity entity);

}
