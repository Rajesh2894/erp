package com.abm.mainet.tradeLicense.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;

/**
 * 
 * @author Gayatri.kokane
 *
 */
@Repository
public interface TradeLicenseItemDetailRepository extends CrudRepository<TbMlItemDetail, Long> {

}
