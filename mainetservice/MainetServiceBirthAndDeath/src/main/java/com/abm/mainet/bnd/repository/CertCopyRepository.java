package com.abm.mainet.bnd.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.BirthDeathCFCInterface;
import com.abm.mainet.bnd.domain.TbBdCertCopy;

@Repository
public interface CertCopyRepository extends JpaRepository<TbBdCertCopy, Long>{
	
	
	  @Modifying
	  @Query("update TbBdCertCopy d set d.status=:status where d.bdId=:bdId and d.orgid=:orgId")
	  void updateStatus(@Param("bdId")Long bdId,@Param("orgId")Long orgId,@Param("status")String status);
	  
	  
		@Query("select bd from TbBdCertCopy bd where bd.brId=:brId")
		public List<TbBdCertCopy>  findData(@Param("brId") Long brId);

		@Query("select bd from TbBdCertCopy bd where bd.drId=:drId")
		List<TbBdCertCopy> findDeathCertData(@Param("drId") Long drId);

}
