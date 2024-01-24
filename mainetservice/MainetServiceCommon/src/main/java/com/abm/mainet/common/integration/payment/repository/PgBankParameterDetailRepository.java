/**
 * 
 */
package com.abm.mainet.common.integration.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.integration.payment.entity.PGBankDetail;

/**
 * @author cherupelli.srikanth
 *@since 29 september 2020
 */
@Repository
public interface PgBankParameterDetailRepository extends JpaRepository<PGBankDetail, Long> {

}
