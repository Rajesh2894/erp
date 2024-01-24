package com.abm.mainet.disastermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.disastermanagement.domain.ComplainScrutiny;

/**
 * Spring Data repository for the ComplainRegister entity.
 */
@Repository
public interface ComplainScrutinyRepository extends JpaRepository <ComplainScrutiny, Long> {



}
