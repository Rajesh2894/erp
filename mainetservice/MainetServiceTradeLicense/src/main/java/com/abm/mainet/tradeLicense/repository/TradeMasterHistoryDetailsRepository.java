package com.abm.mainet.tradeLicense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.tradeLicense.domain.TbMlTradeMastHist;

/**
 * Created Date:03/02/2021
 * 
 * @author Rajesh das
 *
 */
@Repository
public interface TradeMasterHistoryDetailsRepository extends CrudRepository<TbMlTradeMastHist, Long> {

	@Query("select h from TbMlTradeMastHist h where h.trdLicno =:licNo")
	List<TbMlTradeMastHist> getTradeDetailsHistByLicNo(@Param("licNo") String licNo);

	@Query("select distinct(h.apmApplicationId) from TbMlTradeMastHist h where   h.trdLicno=:licNo")
	List<Long> getTradeDetailsHistoryByLicNoAndAppId(@Param("licNo") String licNo);

	// for saving history data D#125619
	@Query("select distinct(h.trdLicno) from TbMlTradeMastHist h where   h.apmApplicationId=:apmApplicationId")
	String getTradeLicNo(@Param("apmApplicationId") Long apmApplicationId);
//Defect #133557
	@Query("select distinct(trd.apmApplicationId) from TbMlTradeMastHist trd,TbCfcApplicationMstEntity cfc ,ServiceMaster ser where trd.apmApplicationId=cfc.apmApplicationId and (cfc.tbServicesMst.smServiceId=ser.smServiceId and ser.smShortdesc =:servShortCode) and trd.trdLicno =:trdLicno order by 1 desc")
	List<Long> getTradeRenewalAppHist(@Param("trdLicno") String licNo,@Param("servShortCode") String servShortCode);

}
