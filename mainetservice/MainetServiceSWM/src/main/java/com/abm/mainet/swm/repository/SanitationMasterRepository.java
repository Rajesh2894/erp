package com.abm.mainet.swm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.SanitationMaster;

/**
 * The Interface SanitationMasterRepository.
 *
 * @author Lalit.Prusti Created Date : 14-May-2018
 * 
 */
@Repository
public interface SanitationMasterRepository extends JpaRepository<SanitationMaster, Long> {

}
