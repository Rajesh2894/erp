package com.abm.mainet.bnd.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.abm.mainet.bnd.domain.BirthRegdraftEntity;

@Repository
public interface BirthRegDraftRepository extends JpaRepository<BirthRegdraftEntity, Long>{
	
	List<BirthRegdraftEntity> findByOrgId(Long orgId);
	
	@Query("select count(*) from BirthRegdraftEntity b where b.brRegNo=:brRegno and b.orgId=:orgId")
	Long checkDuplicateRegno(@Param("brRegno")String brRegno, @Param("orgId")Long orgId);

	@Query("select count(*) from BirthRegdraftEntity b where b.brRegNo=:brRegno and b.orgId=:orgId and b.brDraftId <> :brDraftId")
	Long checkduplregnobyRegnoanddraftId(@Param("brRegno")String brRegno, @Param("orgId")Long orgId, @Param("brDraftId")Long brDraftId);
	

}
