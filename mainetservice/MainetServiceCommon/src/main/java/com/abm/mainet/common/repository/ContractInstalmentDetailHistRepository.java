/**
 * 
 */
package com.abm.mainet.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.ContractInstalmentDetailHistEntity;

/**
 * @author divya.marshettiwar
 *
 */
@Repository
public interface ContractInstalmentDetailHistRepository extends JpaRepository<ContractInstalmentDetailHistEntity, Long>{

}
