package com.abm.mainet.intranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.intranet.domain.PollDetails;

@Repository
public interface PollDetailsRepository extends JpaRepository<PollDetails, Long> {

	public PollDetails findById(Long pollId);

	
	
	
    
}
