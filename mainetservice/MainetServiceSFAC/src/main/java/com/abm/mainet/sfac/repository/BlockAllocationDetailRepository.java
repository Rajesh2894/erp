/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.BlockAllocationDetailEntity;
import com.abm.mainet.sfac.domain.BlockAllocationEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface BlockAllocationDetailRepository extends JpaRepository<BlockAllocationDetailEntity, Long>{

	/**
	 * @param entity
	 * @param cbboId
	 * @return
	 */
	List<BlockAllocationDetailEntity> findByBlockMasterEntityAndCbboId(BlockAllocationEntity entity, Long cbboId);

	/**
	 * @param entity
	 * @return
	 */
	List<BlockAllocationDetailEntity> findByBlockMasterEntity(BlockAllocationEntity entity);

	/**
	 * @param entity
	 * @param applId
	 * @return
	 */
	List<BlockAllocationDetailEntity> findByBlockMasterEntityAndApplicationId(BlockAllocationEntity entity,
			Long applId);

	/**
	 * @param applId
	 * @return
	 */
	List<BlockAllocationDetailEntity> findByApplicationId(Long applId);

	List<BlockAllocationDetailEntity> findByCbboId(Long masId);

	BlockAllocationDetailEntity findBySdb3(Long block);

}
