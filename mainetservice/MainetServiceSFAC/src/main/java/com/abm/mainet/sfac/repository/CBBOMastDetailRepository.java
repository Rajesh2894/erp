/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CBBOMastDetailEntity;
import com.abm.mainet.sfac.domain.CBBOMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface CBBOMastDetailRepository extends JpaRepository<CBBOMastDetailEntity, Long>{
	
	/**
	 * @param entity
	 * @return
	 */
	List<CBBOMastDetailEntity> findByMasterEntity(CBBOMasterEntity entity);

}
