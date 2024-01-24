/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FarmerMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FarmerMasterRepository extends JpaRepository<FarmerMasterEntity, Long> {

	/**
	 * @param userId
	 * @return
	 */
	@Query("Select f.frmGender, count(f.frmGender) from FarmerMasterEntity f where f.createdBy=:userId group by f.frmGender ")
	List<Object> checkMaxCountOfMaleAndFemale(@Param("userId") Long userId);

	/**
	 * @param femaleId
	 * @param userId
	 * @return
	 */
	@Query("Select count(f.frmGender) from FarmerMasterEntity f where  f.frmGender=:femaleId and f.createdBy=:userId group by f.frmGender")
	Long getFemaleCount(@Param("femaleId") Long femaleId, @Param("userId") Long userId);

	/**
	 * @param maleId
	 * @param userId
	 * @return
	 */
	@Query("Select count(f.frmGender) from FarmerMasterEntity f where  f.frmGender=:maleId and f.createdBy=:userId group by f.frmGender")
	Long getMaleCount(@Param("maleId") Long maleId, @Param("userId") Long userId);

	/**
	 * @param frmId
	 * @return
	 */
	@Query("Select f from FarmerMasterEntity f where f.frmId=:frmId")
	FarmerMasterEntity getDetailById(@Param("frmId") Long frmId);

	/**
	 * @param masId
	 * @return
	 */
	@Query("Select f from FarmerMasterEntity f where f.frmId=:frmId")
	List<FarmerMasterEntity> getAllDetailsById(@Param("frmId")  Long frmId);

	/**
	 * @return
	 */
	@Query("Select f from FarmerMasterEntity f ")
	List<FarmerMasterEntity> findAllFarmerDet();

}
