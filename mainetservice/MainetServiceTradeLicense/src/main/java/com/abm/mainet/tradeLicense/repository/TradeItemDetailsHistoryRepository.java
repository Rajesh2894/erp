package com.abm.mainet.tradeLicense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.tradeLicense.domain.TbMlItemDetailHist;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMastHist;

/**
 * Created Date:22/01/2021
 * 
 * @author Rajesh das
 *
 */
@Repository
public interface TradeItemDetailsHistoryRepository extends CrudRepository<TbMlItemDetailHist, Long> {

	/**
	 * used to get Trade License Item Details History With All Details By Trd Id
	 * 
	 * @param trdId
	 */
	@Query("select h.triId,h.triCod1,h.triCod2,h.triCod3,h.triCod4,h.triStatus,h.trdUnit from TbMlItemDetailHist h where h.triId =:triId")
	List<Object[]> getItemDetailsHistByTrdId(@Param("triId") Long triId);

}
