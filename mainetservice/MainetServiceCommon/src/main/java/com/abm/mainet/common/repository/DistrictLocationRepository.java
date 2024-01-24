package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.DistrictLocationEntity;

@Repository
public interface DistrictLocationRepository extends JpaRepository<DistrictLocationEntity, Long>{
	
	@Query(value= "SELECT A.COD_ID as STATE_ID,A.COD_dESC as STATE_NAME,B.COD_ID as DISTRICT_ID,B.COD_DESC as DISTRICT_NAME,C.COD_ID as BLOCK_ID,C.COD_DESC as BLOCK_NAME ,IFNULL((SELECT  DISTINCT LAT FROM district_location D  WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND  D.BLOCK_ID=C.COD_ID),0) as DISTRICT_LAT,IFNULL((SELECT  DISTINCT LON  FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as DISTRICT_LON  ,IFNULL((SELECT  DISTINCT BLOCK_LAT  FROM district_location  D   WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LAT ,IFNULL((SELECT  DISTINCT BLOCK_LON FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LON FROM  TB_COMPARENT_DET A,TB_COMPARENT_DET b,TB_COMPARENT_DET c WHERE     a.com_id = 4715 AND a.cod_id = b.parent_id AND b.cod_id = c.parent_id AND (A.COD_ID, B.COD_ID, C.COD_ID) NOT IN (SELECT sdb1, sdb2, sdb3 FROM tb_sfac_block_allocation_detail)", nativeQuery=true)
	List<Object[]> getVacantBlockData();
	
	
	@Query(value ="SELECT A.COD_ID as STATE_ID,A.COD_dESC as STATE_NAME,B.COD_ID as DISTRICT_ID,B.COD_DESC as DISTRICT_NAME,C.COD_ID as BLOCK_ID,C.COD_DESC as BLOCK_NAME ,IFNULL((SELECT  DISTINCT LAT FROM district_location D  WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND  D.BLOCK_ID=C.COD_ID),0) as DISTRICT_LAT,IFNULL((SELECT  DISTINCT LON  FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as DISTRICT_LON  ,IFNULL((SELECT  DISTINCT BLOCK_LAT  FROM district_location  D   WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LAT ,IFNULL((SELECT  DISTINCT BLOCK_LON FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LON FROM  TB_COMPARENT_DET A,TB_COMPARENT_DET b,TB_COMPARENT_DET c WHERE  a.com_id = 4715 AND a.cod_id = b.parent_id AND b.cod_id = c.parent_id AND (A.COD_ID, B.COD_ID, C.COD_ID)  IN (SELECT sdb1, sdb2, sdb3 FROM tb_sfac_block_allocation_detail)", nativeQuery = true)
	List<Object[]> getAllocatedBlockData();


	@Query(value= "select distinct(IA_NAME) from tb_sfac_fpo_master where SDB2 =:distId", nativeQuery=true)
	List<String> getIaList(@Param("distId") Long distId);

	@Query(value= "select distinct(CBBO_NAME) from tb_sfac_fpo_master where SDB2 =:distId", nativeQuery=true)
	List<String> getCbboList(@Param("distId") Long distId);

	@Query(value= "select distinct(FPO_NAME) from tb_sfac_fpo_master where SDB2 =:distId", nativeQuery=true)
	List<String> getFPOList(@Param("distId") Long distId);
	
	@Query(value= "select count(*) from TB_COMPARENT_DET A ,TB_COMPARENT_DET b , TB_COMPARENT_DET c where a.com_id='4715' and a.cod_id= b.parent_id and b.cod_id= c.parent_id and B.COD_ID =:distId group by B.COD_ID", nativeQuery=true)
	Integer getTotalBlock(@Param("distId") Long distId);
	
	@Query(value= "SELECT A.COD_ID as STATE_ID,A.COD_dESC as STATE_NAME,B.COD_ID as DISTRICT_ID,B.COD_DESC as DISTRICT_NAME,C.COD_ID as BLOCK_ID,C.COD_DESC as BLOCK_NAME ,IFNULL((SELECT  DISTINCT LAT FROM district_location D  WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND  D.BLOCK_ID=C.COD_ID),0) as DISTRICT_LAT,IFNULL((SELECT  DISTINCT LON  FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as DISTRICT_LON  ,IFNULL((SELECT  DISTINCT BLOCK_LAT  FROM district_location  D   WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LAT ,IFNULL((SELECT  DISTINCT BLOCK_LON FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LON FROM  TB_COMPARENT_DET A,TB_COMPARENT_DET b,TB_COMPARENT_DET c WHERE     a.com_id = 4715 AND a.cod_id = b.parent_id AND b.cod_id = c.parent_id AND B.COD_ID=:distId  AND (A.COD_ID, B.COD_ID, C.COD_ID) IN (SELECT sdb1, sdb2, sdb3 FROM tb_sfac_block_allocation_detail)", nativeQuery=true)
	List<Object[]> getAllocatedList(@Param("distId") Long distId);
	
	@Query(value= "SELECT A.COD_ID as STATE_ID,A.COD_dESC as STATE_NAME,B.COD_ID as DISTRICT_ID,B.COD_DESC as DISTRICT_NAME,C.COD_ID as BLOCK_ID,C.COD_DESC as BLOCK_NAME ,IFNULL((SELECT  DISTINCT LAT FROM district_location D  WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND  D.BLOCK_ID=C.COD_ID),0) as DISTRICT_LAT,IFNULL((SELECT  DISTINCT LON  FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as DISTRICT_LON  ,IFNULL((SELECT  DISTINCT BLOCK_LAT  FROM district_location  D   WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LAT ,IFNULL((SELECT  DISTINCT BLOCK_LON FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LON FROM  TB_COMPARENT_DET A,TB_COMPARENT_DET b,TB_COMPARENT_DET c WHERE     a.com_id = 4715 AND a.cod_id = b.parent_id AND b.cod_id = c.parent_id AND B.COD_ID=:distId  AND (A.COD_ID, B.COD_ID, C.COD_ID) NOT IN (SELECT sdb1, sdb2, sdb3 FROM tb_sfac_block_allocation_detail)", nativeQuery=true)
	List<Object[]> getVacantList(@Param("distId") Long distId);


	@Query(value= "select distinct(IA_NAME) from tb_sfac_fpo_master where SDB3 =:blockId", nativeQuery=true)
	List<String> getIaListByBlock(@Param("blockId") Long blockId);
	
	@Query(value= "select distinct(CBBO_NAME) from tb_sfac_fpo_master where SDB3 =:blockId", nativeQuery=true)
	List<String> getCbboListByBlock(@Param("blockId") Long blockId);

	@Query(value= "select distinct(FPO_NAME) from tb_sfac_fpo_master where SDB3 =:blockId", nativeQuery=true)
	List<String> getFPOListByBlock(@Param("blockId") Long blockId);
	
	@Query(value= "select count(*) from TB_COMPARENT_DET A ,TB_COMPARENT_DET b , TB_COMPARENT_DET c where a.com_id='4715' and a.cod_id= b.parent_id and b.cod_id= c.parent_id and C.COD_ID =:blockId group by B.COD_ID", nativeQuery=true)
	Integer getTotalBlockByBlock(@Param("blockId") Long blockId);

	@Query(value= "SELECT A.COD_ID as STATE_ID,A.COD_dESC as STATE_NAME,B.COD_ID as DISTRICT_ID,B.COD_DESC as DISTRICT_NAME,C.COD_ID as BLOCK_ID,C.COD_DESC as BLOCK_NAME ,IFNULL((SELECT  DISTINCT LAT FROM district_location D  WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND  D.BLOCK_ID=C.COD_ID),0) as DISTRICT_LAT,IFNULL((SELECT  DISTINCT LON  FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as DISTRICT_LON  ,IFNULL((SELECT  DISTINCT BLOCK_LAT  FROM district_location  D   WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LAT ,IFNULL((SELECT  DISTINCT BLOCK_LON FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LON FROM  TB_COMPARENT_DET A,TB_COMPARENT_DET b,TB_COMPARENT_DET c WHERE     a.com_id = 4715 AND a.cod_id = b.parent_id AND b.cod_id = c.parent_id AND C.COD_ID=:blockId  AND (A.COD_ID, B.COD_ID, C.COD_ID)  IN (SELECT sdb1, sdb2, sdb3 FROM tb_sfac_block_allocation_detail)", nativeQuery=true)
	List<Object[]> getAllocatedListByBlock(@Param("blockId") Long blockId);
	
	@Query(value= "SELECT A.COD_ID as STATE_ID,A.COD_dESC as STATE_NAME,B.COD_ID as DISTRICT_ID,B.COD_DESC as DISTRICT_NAME,C.COD_ID as BLOCK_ID,C.COD_DESC as BLOCK_NAME ,IFNULL((SELECT  DISTINCT LAT FROM district_location D  WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND  D.BLOCK_ID=C.COD_ID),0) as DISTRICT_LAT,IFNULL((SELECT  DISTINCT LON  FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as DISTRICT_LON  ,IFNULL((SELECT  DISTINCT BLOCK_LAT  FROM district_location  D   WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LAT ,IFNULL((SELECT  DISTINCT BLOCK_LON FROM district_location D WHERE D.STATE_ID=a.cod_id AND D.DISTRICT_ID=b.cod_id AND D.BLOCK_ID=C.COD_ID ),0) as BLOCK_LON FROM  TB_COMPARENT_DET A,TB_COMPARENT_DET b,TB_COMPARENT_DET c WHERE     a.com_id = 4715 AND a.cod_id = b.parent_id AND b.cod_id = c.parent_id AND C.COD_ID=:blockId  AND (A.COD_ID, B.COD_ID, C.COD_ID) NOT IN (SELECT sdb1, sdb2, sdb3 FROM tb_sfac_block_allocation_detail)", nativeQuery=true)
	List<Object[]> getVacantListByBlock(@Param("blockId") Long blockId);
	
	
}



