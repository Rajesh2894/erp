/**
 * 
 */
package com.abm.mainet.rnl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.rnl.domain.RLBillMasterHist;


/**
 * @author divya.marshettiwar
 *
 */
@Repository
public interface RLBillMasterHistRepository extends JpaRepository<RLBillMasterHist, Long>{

}
