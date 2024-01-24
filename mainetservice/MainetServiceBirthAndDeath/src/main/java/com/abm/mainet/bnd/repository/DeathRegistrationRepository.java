package com.abm.mainet.bnd.repository;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.BirthRegistrationEntity;
import com.abm.mainet.bnd.domain.TbDeathreg;

  @Repository 
  public interface DeathRegistrationRepository extends JpaRepository<TbDeathreg, Long>{
	  
	  List<TbDeathreg> findByorgId(Long orgId);
	 
	  @Modifying
	  @Query("update TbDeathreg d set d.DeathWFStatus=:wfStatus,d.drStatus=:drStatus  where d.drId=:drID and d.orgId=:orgId")
	  void updateWorkFlowStatus(@Param("drID")Long drID,@Param("orgId")Long orgId,@Param("wfStatus")String wfStatus,@Param("drStatus")String drStatus);
	  
	  
	  @Modifying
	  @Query("update TbDeathreg d set d.certNoCopies=d.certNoCopies + :certNoCopies where d.drId=:drId and d.orgId=:orgId")
	  void updatNoOfcopyStatus(@Param("drId")Long drId,@Param("orgId")Long orgId,@Param("certNoCopies")Long certNoCopies);
	  
	  
	  @Modifying
	   @Query(value="update TbDeathreg as dr set dr.DeathWFStatus=:DeathWFStatus where dr.drId=:drId and dr.orgId=:orgId")
	   void updateNoOfIssuedCopy(@Param("drId")Long drId,@Param("orgId") Long orgId,@Param("DeathWFStatus") String DeathWFStatus);
		
	  
	  
	  @Modifying
	  @Query("update TbDeathreg d set d.DeathWFStatus=:wfStatus  where d.drId=:drID and d.orgId=:orgId")
	  void updateWorkFlowStatusForissuance(@Param("drID")Long drID,@Param("orgId")Long orgId,@Param("wfStatus")String wfStatus);

	
	
	@Query("select d from TbDeathreg d where d.drId=:drId")
	public List<TbDeathreg>  findData(@Param("drId") Long drId);

	
	@Query("select count(*) from TbDeathreg b where b.drRegno=:drRegno and b.orgId=:orgId")
	Long checkDuplicateRegno(@Param("drRegno")String drRegno, @Param("orgId")Long orgId);
	  
	@Modifying
	@Query("update TbDeathreg d set d.drCertNo=:drCertNo where d.drId=:drId and d.orgId=:orgId")
	void updateCertNo(@Param("drId")Long drId,@Param("orgId")Long orgId,@Param("drCertNo")String drCertNo);
	
	@Modifying
	@Query("update TbDeathreg d set d.authRemark=:deathRegremark where d.drId=:drId and d.orgId=:orgId")
	void updateDeathRemark(@Param("drId") Long drId, @Param("orgId") Long orgId,@Param("deathRegremark") String deathRegremark);
  }
