package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.CBBOFiledStaffDetailsEntity;
import com.abm.mainet.sfac.dto.CBBOFiledStaffDetailsDto;

@Repository
public interface CBBOFiledStaffDetailsRepository extends JpaRepository<CBBOFiledStaffDetailsEntity, Long>{



	List<CBBOFiledStaffDetailsEntity> findBySdb3AndCbboExpertName(Long block, String name);

	List<CBBOFiledStaffDetailsEntity> findBySdb3(Long name);

	List<CBBOFiledStaffDetailsEntity> findByCbboExpertName(String name);

}
