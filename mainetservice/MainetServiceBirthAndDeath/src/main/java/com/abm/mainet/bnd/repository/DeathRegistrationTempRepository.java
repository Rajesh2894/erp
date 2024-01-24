package com.abm.mainet.bnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.TbDeathregTemp;

@Repository
public interface DeathRegistrationTempRepository extends JpaRepository<TbDeathregTemp, Long>{

}
