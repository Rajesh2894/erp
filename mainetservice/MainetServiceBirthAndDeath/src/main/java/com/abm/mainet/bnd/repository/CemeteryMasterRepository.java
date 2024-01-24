package com.abm.mainet.bnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.CemeteryMaster;

@Repository
public interface CemeteryMasterRepository extends JpaRepository<CemeteryMaster, Long> {

	
	List<CemeteryMaster> findByOrgid(Long orgId);
	
	
	List<CemeteryMaster> findByOrgidAndCeNameLikeAndCpdTypeId(Long orgid, String ceName, Long cpdTypeId);
	
	List<CemeteryMaster> findByOrgidAndCpdTypeId(Long orgid, Long cpdTypeId);
	
	@Query("select h from CemeteryMaster h where h.orgid =:orgid and h.ceName like %:ceName%")
	List<CemeteryMaster> findByOrgidAndCeNameLike(@Param("orgid") Long orgid,@Param("ceName") String ceName);
	
	
}
