package com.abm.mainet.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.CFCApplicationAddressEntity;

@Repository
public interface CFCApplicationAddressRepository extends JpaRepository<CFCApplicationAddressEntity, Long> {

    Optional<CFCApplicationAddressEntity> findTopByApaMobilnoOrderByApmApplicationIdDesc(String apamobilno);
    
    Optional<CFCApplicationAddressEntity> findTopByApaMobilnoOrderByLmodDateDesc(String apamobilno);
}
