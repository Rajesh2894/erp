/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.IAMasterHistEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface IAMasterHistRepository extends JpaRepository<IAMasterHistEntity, Long>{

}
