/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.StateInformationEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface StateInformationRepository extends JpaRepository<StateInformationEntity, Long> {

	/**
	 * @param state
	 * @param district
	 * @param orgId
	 * @return
	 */
	@Query("Select s from StateInformationEntity s where s.state=:state and s.district=:district and s.orgId=:orgId")
	List<StateInformationEntity> getStateInfoDetailsByIds(@Param("state") Long state, @Param("district") Long district,
			@Param("orgId") Long orgId);

	/**
	 * @param sdb2
	 * @param orgId
	 * @return
	 */
	@Query("Select s from StateInformationEntity s where s.district=:sdb2 ")
	StateInformationEntity getStateInfoByDistId(@Param("sdb2") Long sdb2);

	/**
	 * @param district
	 * @return
	 */
	@Query("Select case when count(f)>0 THEN true ELSE false END from StateInformationEntity f where f.district=:district ")
	Boolean checkSpecialCateExist(@Param("district") Long district);

	
}
