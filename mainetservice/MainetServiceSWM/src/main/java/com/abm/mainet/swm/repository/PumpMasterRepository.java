package com.abm.mainet.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.PumpMaster;

/**
 * The Interface PumpMasterRepository.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */

@Repository
public interface PumpMasterRepository extends JpaRepository<PumpMaster, Long> {
    /**
     * find All By pump Type
     * @param puPutype
     * @param orgId
     * @return
     */
    List<PumpMaster> findAllBypuPutype(Long puPutype, Long orgId);
}
