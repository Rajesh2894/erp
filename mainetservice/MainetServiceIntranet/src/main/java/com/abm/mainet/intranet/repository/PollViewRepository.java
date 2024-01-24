package com.abm.mainet.intranet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.intranet.domain.PollView;
import com.abm.mainet.intranet.dto.report.IntranetPollDTO;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface PollViewRepository extends JpaRepository<PollView, Long> {

	
	@Query(value = "select x.QUESTION, x.text, count(choice_val) as cnt \r\n" + 
			"from \r\n" + 
			"(SELECT P.ID, P.QUESTION, C.TEXT,v.choice_val \r\n" + 
			"   FROM TB_POLLS P, TB_CHOICES C left outer join tb_poll_view v on c.id = v.choice_id \r\n" + 
			"  WHERE P.ID = C.POLL_ID \r\n" +
			"    AND P.ORG_ID = :orgId) x \r\n" + 
			"  group by QUESTION, text order by 1 desc ", nativeQuery=true)
	List<Object> getPollViewCount(@Param("orgId") Long orgId);
	
	@Query(value = 	
			"SELECT  QUESTION \r\n" + 
			" FROM TB_POLLS P, TB_CHOICES C left outer join tb_poll_view v on c.id = v.choice_id \r\n" + 
			" WHERE P.ID = C.POLL_ID x\r\n" + 
			" order by Question ", nativeQuery=true)
	List<Object> getPollViewCountQue();
		
	@Query(value = "select x.QUESTION, x.text, count(choice_val) as cnt\r\n" + 
			"from\r\n" + 
			"(SELECT P.ID, P.QUESTION, C.TEXT,v.choice_val\r\n" + 
			"   FROM TB_POLLS P, TB_CHOICES C left outer join tb_poll_view v on c.id = v.choice_id\r\n" + 
			"  WHERE P.ID = C.POLL_ID ) x\r\n" + 
			"  group by QUESTION, text", nativeQuery=true)
	List<IntranetPollDTO> getPollViewCountDto();
	
	
}
