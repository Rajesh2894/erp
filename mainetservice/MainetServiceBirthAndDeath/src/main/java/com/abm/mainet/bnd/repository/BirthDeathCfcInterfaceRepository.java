package com.abm.mainet.bnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.BirthDeathCFCInterface;

@Repository
public interface BirthDeathCfcInterfaceRepository extends JpaRepository<BirthDeathCFCInterface, Long>{

	List<BirthDeathCFCInterface> findByBdRequestId(Long bdRequestId);
	
	@Query("select bd from BirthDeathCFCInterface bd where bd.apmApplicationId=:aplId")
	public List<BirthDeathCFCInterface>  findData(@Param("aplId") Long aplId);

	
}
