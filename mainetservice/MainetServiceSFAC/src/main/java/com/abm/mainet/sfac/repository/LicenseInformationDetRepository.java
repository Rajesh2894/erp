package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;
import com.abm.mainet.sfac.domain.LicenseInformationDetEntity;

@Repository
public interface LicenseInformationDetRepository extends JpaRepository<LicenseInformationDetEntity, Long> {

	List<LicenseInformationDetEntity> findByFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileManagemntMaster);

	List<LicenseInformationDetEntity> findByFpoProfileMgmtMasterAndLicenseName(FPOProfileManagementMaster fpoProfileManagemntMaster, String status);



	@Modifying
	@Query("UPDATE LicenseInformationDetEntity Li SET Li.licenseName ='D', Li.updatedBy =:updatedBy, Li.updatedDate = CURRENT_DATE "
			+ "WHERE Li.licId in (:removeIds) ")
	void deActiveLicenseInfo(@Param("removeIds") List<Long> removeIds, @Param("updatedBy") Long updatedBy);

}
