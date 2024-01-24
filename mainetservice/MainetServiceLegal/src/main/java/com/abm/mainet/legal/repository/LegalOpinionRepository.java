package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.LegalOpinion;

/**
 * JPA specific extension of {@link org.springframework.data.jpa.repository.JpaRepository}.
 * 
 * @author Lalit.Prusti
 *
 */

@Repository
public interface LegalOpinionRepository extends JpaRepository<LegalOpinion, Long> {
    List<LegalOpinion> findByOrgidAndCseId(Long orgid, Long cseId);
}
