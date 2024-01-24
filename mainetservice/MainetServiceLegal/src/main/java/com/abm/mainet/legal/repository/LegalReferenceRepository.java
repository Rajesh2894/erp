package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.LegalReference;

/**
 * JPA specific extension of {@link org.springframework.data.jpa.repository.JpaRepository}.
 * 
 * @author Lalit.Prusti
 *
 */

@Repository
public interface LegalReferenceRepository extends JpaRepository<LegalReference, Long> {
    List<LegalReference> findByOrgidAndCseId(Long orgid, Long cseId);
}
