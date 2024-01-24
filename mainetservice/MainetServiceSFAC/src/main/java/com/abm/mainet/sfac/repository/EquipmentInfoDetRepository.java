package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.EquipmentInfoDetailEntity;
import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;

@Repository
public interface EquipmentInfoDetRepository extends JpaRepository<EquipmentInfoDetailEntity, Long>{

	List<EquipmentInfoDetailEntity> findByFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileManagemntMaster);

}
