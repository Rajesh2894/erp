package com.abm.mainet.common.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.SEOKeyWordMaster;

@Repository
public interface SEOMetaDataMasterRepository extends CrudRepository<SEOKeyWordMaster, Long> {

    Optional<SEOKeyWordMaster> findByOrgId(Long orgid);

}
