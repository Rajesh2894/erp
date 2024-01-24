package com.abm.mainet.workManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abm.mainet.workManagement.domain.WorkDelayReasonEntity;

/**
 * @author sadik.shaikh
 *
 */
public interface WorkDelayReasonRepo extends JpaRepository<WorkDelayReasonEntity, Long> {

}
