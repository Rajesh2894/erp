package com.abm.mainet.materialmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.BinLocMasEntity;

@Repository
public interface BinLocMasRepository extends JpaRepository<BinLocMasEntity, Long> {
	List<BinLocMasEntity> findByOrgId(Long orgId);

	@Query("select b.binLocation from BinLocMasEntity b where b.binLocId=:binLocId and b.orgId=:orgId")
	public String getDefNameByDefIdAndOrgId(@Param("binLocId") Long binLocId, @Param("orgId") Long orgId);

	@Query("select b.binLocId, b.binLocation from BinLocMasEntity b where b.orgId=:orgId")
	public List<Object[]> getbinLocIdAndNameListByOrgId(@Param("orgId") Long orgId);
 
	@Query("select b from BinLocMasEntity b where b.orgId=:orgId")
	public List<BinLocMasEntity> getbinLocInfoListByOrgId(@Param("orgId") Long orgId);
	
	@Query("select b.binLocId, b.binLocation from BinLocMasEntity b where b.storeId=:storeId and b.orgId=:orgId")
	List<Object[]> getbinLocIdAndNameListByStore(@Param("storeId") Long storeId, @Param("orgId") Long orgId);
	
}
