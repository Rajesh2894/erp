/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.MeetingMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface MeetingMasterRepository extends JpaRepository<MeetingMasterEntity, Long>{

}
