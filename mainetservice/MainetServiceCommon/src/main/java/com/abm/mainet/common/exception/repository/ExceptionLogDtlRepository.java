package com.abm.mainet.common.exception.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.exception.domain.ExceptionLogDtl;

@Repository
public interface ExceptionLogDtlRepository extends JpaRepository<ExceptionLogDtl, Long> {

}
