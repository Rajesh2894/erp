package com.abm.mainet.materialmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.materialmgmt.domain.StoreMaster;


@Repository
public interface StoreMasterRepository extends JpaRepository<StoreMaster, Long> {

	
    @Query("select r from StoreMaster r where  r.storeId =:storeId order by r.storeId desc")
    public StoreMaster findAllStoreByStoreid(@Param("storeId") Long storeId);
    
    
    @Query("select r from StoreMaster r where  r.orgId =:orgId order by r.storeId desc")
    public List<StoreMaster> getAllStoreDataByOrgId(@Param("orgId") Long orgId);
    
    
    @Query("select r from StoreMaster r where  r.location =:locationId and r.storeName=:storeName "
    		+ "and r.orgId =:orgId order by r.storeId desc")
	public List<StoreMaster> findByLocIdAndStoreName(@Param("locationId") Long locationId,
			@Param("storeName") String storeName, @Param("orgId") Long orgId);    
    
    @Modifying
    @Query("DELETE from  StoreMaster a where a.storeId=:storeId and a.orgId=:orgId")
    void deleteAllRecords(@Param("storeId") Long storeId, @Param("orgId") Long orgId);


    @Query("select r.storeName from StoreMaster r where  r.storeId =:storeId")
    public String findStorenameByStoreid(@Param("storeId") Long storeId);

    @Query("select s.storeId, s.storeName, s.status from StoreMaster s where s.orgId=:orgId order by s.storeId")
	public List<Object[]> getStoreIdAndNameList(@Param("orgId") Long orgId);
  
    @Query(value = "select s.location, loc.LOC_NAME_ENG, s.storename, s.storeincharge, e.EMPNAME, e.EMPLNAME from MM_STOREMASTER s join "
    		+ "TB_LOCATION_MAS loc on s.location=loc.LOC_ID join Employee e on s.storeincharge=e.EMPID where s.storeid=:storeId "
    		+ "and s.ORGID=:orgId", nativeQuery = true)
	public Object[] getStoreDetailsByStore(@Param("storeId") Long storeId, @Param("orgId") Long orgId);

    
    @Query(value = "select s.storeId, s.storename, s.location, loc.LOC_NAME_ENG, s.storeincharge, e.EMPNAME, e.EMPLNAME from MM_STOREMASTER s join "
    		+ "TB_LOCATION_MAS loc on s.location=loc.LOC_ID join Employee e on s.storeincharge=e.EMPID where s.storeid in (:storeIdList) "
    		+ "and s.ORGID=:orgId", nativeQuery = true)
	List<Object[]> getStoreDetailListByStore(@Param("storeIdList") List<Long> storeIdList, @Param("orgId") Long orgId);


	// This list must be used only in Add forms
    @Query("select s.storeId, s.storeName, s.status from StoreMaster s where s.orgId=:orgId and s.status=:status order by s.storeId")
	public List<Object[]> getActiveStoreObjectListForAdd(@Param("orgId") Long orgId, @Param("status") Character status);
	
	
    @Query(value = "select loc.locNameEng, s.address from StoreMaster s, LocationMasEntity loc "
    		+ " where s.location=loc.locId and s.storeId =:storeId and s.orgId =:orgId ")
	public Object[] getStoreLocationAddress(@Param("storeId") Long storeId, @Param("orgId") Long orgId);
}
