package com.abm.mainet.bnd.repository;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.TbDeathRegdraft;
@Repository
public interface DeathRegDraftRepository extends JpaRepository<TbDeathRegdraft, Long>{
	
	List<TbDeathRegdraft> findByOrgId(Long orgId);

	@Query("select count(*) from TbDeathRegdraft b where b.drRegno=:drRegno and b.orgId=:orgId")
	Long checkDuplicateRegno(@Param("drRegno")String drRegno, @Param("orgId")Long orgId);

	@Query("select count(*) from TbDeathRegdraft b where b.drRegno=:drRegno and b.orgId=:orgId and b.drDraftId <> :drDraftId")
	Long checkduplregnobyRegnoanddraftId(@Param("drRegno")String drRegno, @Param("orgId")Long orgId, @Param("drDraftId")Long drDraftId);
	
}
