package com.abm.mainet.asset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetAnnualPlan;

@Repository
public interface AssetAnnualPlanRepo extends JpaRepository<AssetAnnualPlan, Long> {

}
