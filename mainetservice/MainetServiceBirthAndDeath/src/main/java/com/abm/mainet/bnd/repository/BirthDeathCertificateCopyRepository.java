package com.abm.mainet.bnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.TbBdCertCopy;

@Repository
public interface BirthDeathCertificateCopyRepository  extends JpaRepository<TbBdCertCopy, Long>{

	
	@Query("from TbBdCertCopy c where c.APMApplicationId=:ApplicationId and c.orgid=:orgId")
	List<TbBdCertCopy> getprintCertificateDetail(@Param("ApplicationId")Long ApplicationId,@Param("orgId")Long orgId);
	
	
	
	//void updatePrintStatus(Long orgId,String status);
}
