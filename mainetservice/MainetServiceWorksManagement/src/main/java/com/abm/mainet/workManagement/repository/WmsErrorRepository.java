package com.abm.mainet.workManagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.TbWmsError;

@Repository
public interface WmsErrorRepository extends CrudRepository<TbWmsError, Long> {

}
