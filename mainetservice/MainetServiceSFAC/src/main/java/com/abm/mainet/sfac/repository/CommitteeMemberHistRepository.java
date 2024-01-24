/**
 * 
 */
package com.abm.mainet.sfac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abm.mainet.sfac.domain.CommitteeMemberMasterHistory;

/**
 * @author pooja.maske
 *
 */
public interface CommitteeMemberHistRepository extends JpaRepository<CommitteeMemberMasterHistory, Long>{

}
