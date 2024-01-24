package com.abm.mainet.legal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.CaseHearing;

/**
 * JPA specific extension of {@link org.springframework.data.jpa.repository.JpaRepository}.
 * 
 * @author Lalit.Prusti
 *
 */

@Repository
public interface CaseHearingRepository extends JpaRepository<CaseHearing, Long> {
    List<CaseHearing> findByOrgidAndCseId(Long orgid, Long cseId);
    
    List<CaseHearing> findByCseId(Long cseId);


    @Query("SELECT DISTINCT cseId FROM CaseHearing WHERE hrStatus =:hrStatus AND orgid =:orgid ")
    List<Long> findCaseIdsByStatus(@Param("hrStatus") Long hrStatus, @Param("orgid") Long orgid);
    
    @Query("SELECT DISTINCT cseId FROM CaseHearing WHERE hrStatus =:hrStatus AND cseId in(select c.cseId from CaseEntry c where c.concernedUlb is not null)")
    List<Long> findCaseIdByStatus(@Param("hrStatus") Long hrStatus);
    
    @Query("SELECT ch FROM CaseHearing ch WHERE  orgid =:orgid AND ch.hrDate >= :currDate ")
    List<CaseHearing> findHearingDetByOrgid(@Param("orgid") Long orgid,@Param("currDate")  Date currDate);

}
