/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.ChangeofBlockEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface ChangeofBlockRepository extends JpaRepository<ChangeofBlockEntity, Long>{

}
