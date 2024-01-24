package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.CourtMaster;

/**
 * JPA specific extension of {@link org.springframework.data.jpa.repository.JpaRepository}.
 * 
 * @author sanket.joshi
 *
 */
@Repository
public interface CourtMasterRepository extends JpaRepository<CourtMaster, Long> {

    // doing this because list need by combination of login ORG or parentORG
	List<CourtMaster> findByCrtStatusAndOrgIdOrOrgId(String crtstatus, Long orgid, Long orgId);

    List<CourtMaster> findByOrgId(Long orgid);
    
    CourtMaster findById(Long id);
    
    @Modifying
    @Query("update CourtMaster  c set c.crtStatus ='N' where c.id=:id")
    void setCrtinactive(@Param("id") Long id);

    List<CourtMaster> findByOrgIdAndCrtStatus(Long orgId, String crtstatus);
    
    @Query("select count(c) from  CourtMaster c where c.crtName =:crtName and c.crtType=:crtType and c.orgId=:orgId")
    int findbyCourtNameAndCourtType(@Param("crtName") String crtName,@Param("crtType") Long crtType,@Param("orgId") Long orgId);

    
    @Query("select c.crtName from CourtMaster c  where c.id=:id")
     String  findCrtNameById(@Param("id") Long id);
    
    @Query("SELECT cm.cseTypId from CourtMaster cm WHERE cm.id =:crtId and cm.orgId =:orgId")
	String getCaseTypeId(@Param("crtId") Long crtId, @Param("orgId")Long orgId);

}
