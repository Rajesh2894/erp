/**
 * 
 */
package com.abm.mainet.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.ContractPart2DetailHistEntity;

/**
 * @author divya.marshettiwar
 *
 */
@Repository
public interface ContractPart2DetailHistRepository extends JpaRepository<ContractPart2DetailHistEntity, Long>{

}
