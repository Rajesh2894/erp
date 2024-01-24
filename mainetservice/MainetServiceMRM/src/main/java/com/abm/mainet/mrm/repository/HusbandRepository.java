package com.abm.mainet.mrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.mrm.domain.Husband;

@Repository
public interface HusbandRepository extends JpaRepository<Husband, Long> {

}
