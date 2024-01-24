package com.abm.mainet.legal.repository;

import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.AdvocateEducationDetails;
import com.abm.mainet.legal.domain.AdvocateMaster;

/**
 * JPA specific extension of {@link org.springframework.data.jpa.repository.JpaRepository}.
 * 
 * @author Lalit.Prusti
 *
 */

@Repository
public interface AdvocateMasterRepository extends JpaRepository<AdvocateMaster, Long> {
    List<AdvocateMaster> findByOrgid(Long orgid);
    List<AdvocateMaster> findByOrgidAndAdvStatus(Long orgid, String advStatus);
    
	AdvocateMaster findByOrgidAndApplicationId(Long orgid, Long applicationId);
	
	@Query("Select aed from AdvocateEducationDetails aed where aed.tbLglAdvMaster.advId=:advId and aed.orgid=:orgid")
	List<AdvocateEducationDetails> findEducationDetById(@Param("advId")  Long advId, @Param("orgid")  Long orgid);
}
