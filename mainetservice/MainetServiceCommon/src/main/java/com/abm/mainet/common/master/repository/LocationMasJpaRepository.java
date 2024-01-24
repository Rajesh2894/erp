package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.LocationOperationWZMapping;

/**
 * Repository : TbLocationMas.
 */

public interface LocationMasJpaRepository extends PagingAndSortingRepository<LocationMasEntity, Long> {

    @Query(" SELECT l.locId,l.locNameEng,l.locNameReg,l.locArea,l.locAreaReg,l.organisation.orgid,l.userId,l.langId,l.landmark,l.pincode,l.locActive"
            + " FROM LocationMasEntity l  where  "
            + " l.organisation.orgid=:orgId order by 1 desc")// and l.locActive = 'Y'
    public List<Object[]> findAllLocationMasterListWithoutCriateria(@Param("orgId") Long orgId);

    @Query("select l from LocationMasEntity l,DeptLocationEntity lm where l.locId=lm.locId "
            + " and l.organisation.orgid=lm.tbOrganisation.orgid and lm.tbDepartment.dpDeptid=:deptId and lm.tbOrganisation.orgid=:orgid and lm.isdeleted='0' "
            + " and l.locActive='Y'")
    List<LocationMasEntity> findAllLocationByDeptid(@Param("deptId") Long deptId,
            @Param("orgid") Long orgid);

    @Query("SELECT locmas.locId, locmas.locNameEng,locmas.locNameReg FROM LocationMasEntity locmas "
            + "where locmas.organisation.orgid=:orgid and locmas.locActive = 'Y'")
    public List<Object[]> getLocationNameByOrgId(@Param("orgid") Long orgid);

    @Transactional
    @Modifying
    @Query("update LocationMasEntity e set e.locActive = ?1,e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.locId = ?3")
    void deleteRecord(String flag, Long empId, Long id);

    @Query("select l from LocationMasEntity l where l.pincode =:pinCode")
    List<LocationMasEntity> getlocationByPinCode(@Param("pinCode") Long pinCode);

    @Query("select l from LocationMasEntity l where l.organisation.orgid =:orgid")
    List<LocationMasEntity> getLocationNameMasterByOrgId(@Param("orgid") Long orgid);

    @Query("select l from LocationOperationWZMapping l where l.codIdOperLevel1 =:wardID or l.codIdOperLevel2 =:zoneID or l.codIdOperLevel3 =:blockId and l.dpDeptId =:deptId")
    List<LocationOperationWZMapping> findByDepartmentWardZoneAndBlock(@Param("deptId") Long deptId, @Param("wardID") Long wardID,
            @Param("zoneID") Long zoneID, @Param("blockId") Long blockId);

    @Query("select e.tbLocationMas.locId from Employee e where e.empId=:empId and e.organisation.orgid=:orgid")
    public Long fetchEmployeeLocation(@Param("empId") Long empId, @Param("orgid") Long orgId);

    @Query("SELECT locmas FROM LocationMasEntity locmas "
            + "where locmas.organisation.orgid=:orgid and locmas.locActive = 'Y' and locmas.deptLoc='Y' order by locmas.locNameEng asc")
    public List<LocationMasEntity> getLocationByOrgId(@Param("orgid") Long orgid);

    
    @Query("SELECT locmas.locId, e.tbLocationMas.locId,locmas.locNameEng , locmas.locNameReg FROM LocationMasEntity locmas, Employee e  "
            + "where locmas.organisation.orgid=:orgid and locmas.locActive = 'Y' and locmas.deptLoc='Y' and e.emploginname=:empName and e.organisation.orgid=:orgid order by locmas.locNameEng asc")
    public List<Object[]> getdefaultLocWithOtherLocByOrgId(@Param("empName") String empName, @Param("orgid") Long orgid);
    
    @Query("SELECT locmas FROM LocationMasEntity locmas where locmas.locId=:locId")
    public LocationMasEntity findbyLocationId(@Param("locId") Long locId);

    @Query("SELECT l.locNameEng FROM LocationMasEntity l where l.locId=:locId and l.organisation.orgid =:orgId")
    public String getLocationNameById(@Param("locId") Long locId, @Param("orgId") Long orgId);

    @Query("select l from LocationOperationWZMapping l where l.locationMasEntity.locId =:locId and l.dpDeptId =:dpDeptId")
    public LocationOperationWZMapping findbyLocationAndDepartment(@Param("locId") Long locId, @Param("dpDeptId") Long dpDeptId);

    @Query("select count(l.locId) from LocationMasEntity l where l.locNameEng=:locName and l.locArea=:locArea and l.locActive = 'Y' and l.organisation.orgid=:orgId")
    public int findLocationNameAndAre(@Param("locName") String locName, @Param("locArea") String locArea,
            @Param("orgId") Long orgId);

    @Query("select l from LocationMasEntity l,LocationOperationWZMapping o,DeptLocationEntity d where l.locId=d.locId and l.locId=o.locationMasEntity.locId "
            + "and d.tbDepartment.dpDeptid=o.dpDeptId and l.organisation.orgid=o.orgId and l.locActive='Y' and l.organisation.orgid=:orgid and d.tbDepartment.dpDeptid=:deptId ")
    List<LocationMasEntity> findAllLocationWithOperationWZMapping(@Param("orgid") Long orgid, @Param("deptId") Long deptId);

    @Query("select l from LocationMasEntity l,LocationOperationWZMapping o where  l.locId=o.locationMasEntity.locId "
            + " and l.organisation.orgid=o.orgId and l.locActive='Y' and l.organisation.orgid=:orgid and o.dpDeptId=:deptId ")
    List<LocationMasEntity> findAllLocationWithWZMappingByDeptId(@Param("orgid") Long orgid, @Param("deptId") Long deptId);

    /**
     * Add Service for fetch All active location master DTO list based on organization Id.
     * @param orgId
     * @return List<TbLocationMas>
     */
    @Query("select l from LocationMasEntity l where l.locActive='Y' and l.organisation.orgid =:orgId order by l.locNameEng asc")
    public List<LocationMasEntity> fillAllActiveLocationByOrgId(@Param("orgId") Long orgId);

    @Query("select l from LocationMasEntity l where l.locActive='Y' and l.organisation.orgid =:orgId and l.locNameEng=:locNameEng")
    public LocationMasEntity findByLocationName(@Param("locNameEng") String locName, @Param("orgId") Long orgId);

    @Query("select l from LocationMasEntity l where l.locActive='Y' and l.locCategory=:locCategory and l.organisation.orgid =:orgId order by l.locNameEng asc")
    public List<LocationMasEntity> findlAllLocationByLocationCategoryAndOrgId(@Param("locCategory") Long locCategory,
            @Param("orgId") Long orgId);

	@Query("select l from LocationMasEntity l,Employee e where l.locActive='Y' and l.organisation.orgid =:orgId and e.tbLocationMas.locId=l.locId order by l.locNameEng asc")
    public List<LocationMasEntity> getAllLocationByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT l FROM LocationMasEntity l where l.organisation.orgid=:orgId order by 1 desc")
    public List<LocationMasEntity> findAllLocations(@Param("orgId") Long orgId); 
    
    @Query("select a.codIdRevLevel1 from LocationRevenueWZMapping a,LocationOperationWZMapping b where b.codIdOperLevel1 =:wardID and b.dpDeptId =:deptId and a.locationMasEntity.locId=b.locationMasEntity.locId and a.orgId=:orgId")
    public Long findFieldIdWithWardAndDeptId(@Param("deptId") Long deptId, @Param("wardID") Long wardID,@Param("orgId") Long orgId);
    
    @Modifying
    @Query("UPDATE LocationYearDetEntity y SET y.yeActive ='N',y.updatedDate=CURRENT_DATE, y.updatedBy=:updatedBy where y.pbId in (:removeYearIdList)")
    void iactiveYearsByIds(@Param("removeYearIdList") List<Long> removeYearIdList, @Param("updatedBy") Long updatedBy);
    
    @Query("select a.codIdRevLevel1 from LocationRevenueWZMapping a where a.locationMasEntity.locId=:locId and a.orgId=:orgId")
    public Long findFieldIdByLocationId(@Param("locId") Long locId,@Param("orgId") Long orgId);

    @Query("SELECT locmas.locId, locmas.locNameEng,locmas.locNameReg FROM LocationMasEntity locmas "
            + "where locmas.organisation.orgid=:orgid and locmas.locActive = 'Y' and locmas.locCode IS NOT NULL and locmas.locCode <> '' ")
	public List<Object[]> getLocationNameAndLocCodeByOrgId(@Param("orgid") Long orgid);
    
}
