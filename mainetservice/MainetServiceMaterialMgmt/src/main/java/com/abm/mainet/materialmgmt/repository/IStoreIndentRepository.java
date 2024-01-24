package com.abm.mainet.materialmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.StoreIndentEntity;

@Repository
public interface IStoreIndentRepository extends JpaRepository<StoreIndentEntity, Long> {

	@Modifying
	@Query("Update StoreIndentEntity si set si.status =:status, si.wfFlag=:wfFlag where si.orgId =:orgId "
			+ "and si.storeIndentNo =:storeIndentNo")
	void updateStoreIndentStatus(@Param("orgId") Long orgId, @Param("storeIndentNo") String storeIndentNo,
			@Param("status") String status, @Param("wfFlag") String wfFlag);

	StoreIndentEntity findByStoreIndentIdAndOrgId(Long storeIndentId, Long orgId);

	StoreIndentEntity findByStoreIndentNoAndOrgId(String storeIndentNo, Long orgId);

	@Query("Select s.storeIndentId, s.storeIndentNo from StoreIndentEntity s where s.orgId =:orgId And s.status=:status")
	List<Object[]> getStoreIndentIdNumberList(@Param("orgId") Long orgId, @Param("status") String status);

	@Query("Select s.storeIndentNo, s.storeIndentdate from StoreIndentEntity s where s.storeIndentId=:storeIndentId and s.orgId =:orgId ")
	Object[] getStoreIndentNumberAndDate(@Param("storeIndentId") Long storeIndentId, @Param("orgId") Long orgId);
	
}
