package com.abm.mainet.common.master.repository;

import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.BankMasterEntity;

@Repository
public interface BankMasterRepository extends PagingAndSortingRepository<BankMasterEntity, Long> {

	@Query("select distinct bm from BankMasterEntity bm order by 1 desc")
	List<BankMasterEntity> findByAllGridData();

	/**
	 * @param bankId
	 * @return
	 */
	@Query("select  bm.bank from BankMasterEntity bm where bm.bankId=:bankId")
	String getBankById(@Param("bankId") Long bankId);

	/**
	 * @param bankId
	 * @return
	 */
	@Query("select  bm.ifsc from BankMasterEntity bm where bm.bankId=:bankId")
	String getIfscCodeById(@Param("bankId") Long bankId);
	
	@Query("select  bm.bank from BankMasterEntity bm  Group by bm.bank ")
	List<String> getBankDetails();
	  
}
