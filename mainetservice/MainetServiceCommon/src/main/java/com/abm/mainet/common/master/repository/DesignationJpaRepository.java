package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.TbOrgDesignationEntity;

/**
 * Repository : Designation.
 */
public interface DesignationJpaRepository extends PagingAndSortingRepository<Designation, Long> {

    @Query("select designation from Designation designation, TbOrgDesignationEntity orgDesignationEntity "
            + " where designation.dsgid=orgDesignationEntity.designation.dsgid"
            + " and orgDesignationEntity.tbOrganisation.orgid=:orgId and orgDesignationEntity.mapStatus = 'A' and designation.isdeleted='0'")
    List<Designation> findByDeptid(@Param("orgId") Long orgId);

    @Query("select d from Designation d where d.dsgname=:dsgname")
    Designation findByName(@Param("dsgname") String dsgname);

    /**
     * @param dsgid
     * @param orgId
     * @return
     */
    @Query("select dsgOrg from TbOrgDesignationEntity dsgOrg "
            + " where dsgOrg.designation.dsgid=:dsgid"
            + " and dsgOrg.tbOrganisation.orgid=:orgid")
    List<TbOrgDesignationEntity> queryDataFrmMasterTable(@Param("dsgid") Long dsgid, @Param("orgid") Long orgid);

    @Query("select d from Designation d where d.dsgshortname=:dsgShrtNme")
    Designation findByShortName(@Param("dsgShrtNme") String dsgShrtNme);

    @Query("select count(*) from TbOrgDesignationEntity dsgOrg"
            + " where dsgOrg.tbOrganisation.orgid=:orgId"
            + " and dsgOrg.designation.dsgid=:dsgId")
    int findByDestId(@Param("orgId") Long orgId, @Param("dsgId") Long dsgid);

    @Query("select designation from Designation designation, Employee e,TbOrgDesignationEntity orgDesignationEntity where  designation.dsgid=e.designation.dsgid and designation.dsgid=orgDesignationEntity.designation.dsgid and orgDesignationEntity.tbOrganisation.orgid=:orgId and orgDesignationEntity.mapStatus = 'A' and designation.isdeleted='0'")
	List<Designation> findDeptByOrgId(@Param("orgId") Long orgId);
    
    @Query("select designation from Designation designation,TbOrgDesignationEntity orgDesignationEntity where   designation.dsgid=orgDesignationEntity.designation.dsgid and orgDesignationEntity.tbOrganisation.orgid=:orgId and orgDesignationEntity.mapStatus = 'A' and designation.isdeleted='0'")
   	List<Designation> findDesgByOrgId(@Param("orgId") Long orgId);

}
