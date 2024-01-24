/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.BlockAllocationEntity;
import com.abm.mainet.sfac.domain.BlockTargetDetEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface BlockTargetDetRepository extends JpaRepository<BlockTargetDetEntity, Long>{

	/**
	 * @param entity
	 * @return
	 */
	List<BlockTargetDetEntity> findByBlockMasterEntity(BlockAllocationEntity entity);

}
