package com.abm.mainet.intranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.intranet.domain.Poll;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

	public Poll findById(Long pollId);
	
	@Modifying
    @Query("UPDATE Poll a SET a.pollStatus =:pollStatus where a.pollid =:pollid and a.orgid=:orgid")
    void updatePollStatus(@Param("pollStatus") String pollStatus, @Param("pollid") Long pollid, @Param("orgid") Long orgid);
	
	
    
}
