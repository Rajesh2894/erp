package com.abm.mainet.common.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.TbDepositEntity;

/**
 * @author Saiprasad.Vengurekar
 *
 */

@Repository
public interface TbDepositReceiptRepository extends JpaRepository<TbDepositEntity, Long>, TbDepositReceiptRepositoryCustom {

}
