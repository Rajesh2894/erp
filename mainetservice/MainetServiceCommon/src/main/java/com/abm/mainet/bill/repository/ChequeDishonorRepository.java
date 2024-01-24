package com.abm.mainet.bill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;

public interface ChequeDishonorRepository extends JpaRepository<TbSrcptModesDetEntity, Long> {

}
