package com.abm.mainet.bnd.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.abm.mainet.bnd.domain.HospitalMaster;

@Repository
public interface HospitalMasterRepository extends JpaRepository<HospitalMaster, Long> {


	List<HospitalMaster> findByOrgid(Long orgId);
	
	public List<HospitalMaster> findByOrgidAndHiNameLikeAndCpdTypeId(Long orgid,String hiName,Long cpdTypeId);
	
	public List<HospitalMaster> findByOrgidAndCpdTypeId(Long orgid, Long cpdTypeId);
	
	@Query("select h from HospitalMaster h where h.orgid =:orgid and h.hiName like %:hiName%")
	public List<HospitalMaster> findByOrgidAndHiNameLike(@Param("hiName") String hiName,@Param("orgid") Long orgid);
	
	@Query("select h from HospitalMaster h where h.hiCode=:hiCode and h.orgid =:orgid")
	List<HospitalMaster> getHospitalCode(@Param("hiCode") String hiCode, @Param("orgid") Long orgid);

	@Query("select h from HospitalMaster h where  h.orgid =:orgid and h.hiStatus =:Status")
	List<HospitalMaster> getHospitalByStatus(@Param("Status") String Status, @Param("orgid") Long orgid);
}


/*
 * @Query("select h from HospitalMaster h where h.orgid =:orgid and lower(h.hiName) like lower('%"
 * + hiName +"%')")
 */



