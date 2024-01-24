package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.property.domain.MainBillDetEntity;
import com.abm.mainet.property.domain.MainBillMasEntity;

public interface PropertyMainBillDetRepository extends JpaRepository<MainBillDetEntity, Long> {

	@Query("select a from MainBillDetEntity a where a.bmIdNo=:findOne")
    List<MainBillDetEntity> getBillDetailsByBmIdno(@Param("findOne") MainBillMasEntity findOne);
}
