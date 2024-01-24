package com.abm.mainet.bnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.BirthRegistrationEntity;
import com.abm.mainet.bnd.domain.TbBdCertCopy;

@Repository
public interface BirthRegRepository extends JpaRepository<BirthRegistrationEntity, Long> {

	List<BirthRegistrationEntity> findByOrgId(Long orgId);
	
   @Modifying
   @Query(value="update BirthRegistrationEntity as br set br.birthWFStatus=:birthWFStatus where br.brId=:brId and br.orgId=:orgId")
   void updateNoOfIssuedCopy(@Param("brId")Long brId,@Param("orgId") Long orgId,@Param("birthWFStatus") String birthWFStatus);
   
   @Modifying
   @Query("update BirthRegistrationEntity d set d.birthWFStatus=:wfStatus,d.brStatus=:brStatus where d.brId=:brId and d.orgId=:orgId")
   void updateWorkFlowStatus(@Param("brId")Long brId,@Param("orgId")Long orgId,@Param("wfStatus")String wfStatus,@Param("brStatus")String brStatus);
   
   
   @Modifying
   @Query("update BirthRegistrationEntity d set d.noOfCopies=d.noOfCopies + :noOfCopies   where d.brId=:brId and d.orgId=:orgId")
   void updatNoOfcopyStatus(@Param("brId")Long brId,@Param("orgId")Long orgId,@Param("noOfCopies")Long noOfCopies);
   
   @Modifying
   @Query("update BirthRegistrationEntity d set d.birthWFStatus=:wfStatus where d.brId=:brId and d.orgId=:orgId")
   void updateWorkFlowStatusforIssuance(@Param("brId")Long brId,@Param("orgId")Long orgId,@Param("wfStatus")String wfStatus);
   
	@Query("select b from BirthRegistrationEntity b where b.brId=:brId")
	public List<BirthRegistrationEntity>  findData(@Param("brId") Long brId);
   
	@Query("select count(*) from BirthRegistrationEntity b where b.brRegNo=:brRegno and b.orgId=:orgId")
	Long checkRegno1(@Param("brRegno")String brRegno, @Param("orgId")Long orgId);
	  
	@Modifying
	@Query(value="update BirthRegistrationEntity as br set br.brCertNo=:brCertNo where br.brId=:brId and br.orgId=:orgId")
	void updateBrCertNo(@Param("brId")Long brId,@Param("orgId") Long orgId,@Param("brCertNo") String brCertNo );
	
	@Modifying
	@Query("update BirthRegistrationEntity d set d.authRemark=:birthRegremark where d.brId=:brId and d.orgId=:orgId")
	void updateBirthRemark(@Param("brId") Long brId, @Param("orgId") Long orgId,@Param("birthRegremark") String birthRegremark);
}
