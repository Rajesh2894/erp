package com.abm.mainet.account.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.RevenueStampChargesEntity;

@Repository
public interface AccountRevenueStampRepository extends PagingAndSortingRepository<RevenueStampChargesEntity, Long> {

}
