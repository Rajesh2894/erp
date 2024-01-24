package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.ServiceMaster;

/**
 * Repository : TbServicesMst.
 */
public interface TbServicesMstJpaRepository extends PagingAndSortingRepository<ServiceMaster, Long> {

    @Query("select tbServicesMstEntity from ServiceMaster tbServicesMstEntity , Department tbDepartmentEntity,"
            + "DeptOrgMap tbDeporgMapEntity"
            + " where tbServicesMstEntity.tbDepartment.dpDeptid = tbDepartmentEntity.dpDeptid"
            + " and tbDepartmentEntity.dpDeptid = tbDeporgMapEntity.department.dpDeptid"
            + " and tbServicesMstEntity.orgid = tbDeporgMapEntity.orgid"
            + " and tbDeporgMapEntity.orgid = :orgId"
            + " and tbServicesMstEntity.smChklstVerify = :smChklstVerify"
            + " and NOT EXISTS (SELECT tbCfcChecklistMstEntity FROM TbCfcChecklistMstEntity tbCfcChecklistMstEntity"
            + " WHERE tbCfcChecklistMstEntity.tbServicesMst.smServiceId = tbServicesMstEntity.smServiceId "
            + " AND tbCfcChecklistMstEntity.tbOrganisation.orgid = :orgId)"
            + " order by tbServicesMstEntity.smServiceName")
    List<ServiceMaster> findAllServiceList(@Param("orgId") Long orgId, @Param("smChklstVerify") Long smChklstVerify);

    @Query("select tbServicesMstEntity from ServiceMaster tbServicesMstEntity "
            + " where tbServicesMstEntity.orgid = :orgId")
    List<ServiceMaster> findAllServiceListByOrgId(@Param("orgId") Long orgId);

    @Query("select tbServicesMstEntity from ServiceMaster tbServicesMstEntity where "
            + "tbServicesMstEntity.tbDepartment.dpDeptid= :deptId "
            + "and tbServicesMstEntity.smServiceId= :serviceId and tbServicesMstEntity.orgid = :organisation")
    List<ServiceMaster> findByDeptServiceId(@Param("deptId") Long deptId, @Param("serviceId") Long serviceId,
            @Param("organisation") Long organisation);

    @Query("select tbServicesMstEntity from ServiceMaster tbServicesMstEntity where "
            + "tbServicesMstEntity.tbDepartment.dpDeptid= :deptId "
            + "and tbServicesMstEntity.orgid = :organisation")
    List<ServiceMaster> findByDeptServiceId(@Param("deptId") Long deptId, @Param("organisation") Long organisation);

    @Query("select tbServicesMstEntity from ServiceMaster tbServicesMstEntity "
            + " where tbServicesMstEntity.tbDepartment.dpDeptid= :deptId and tbServicesMstEntity.orgid = :orgId order by smServiceName asc")
    List<ServiceMaster> findByDeptId(@Param("deptId") Long deptId, @Param("orgId") Long orgId);
    
    @Query("SELECT tbServicesMstEntity FROM ServiceMaster tbServicesMstEntity "
            + " WHERE tbServicesMstEntity.tbDepartment.dpDeptid= :deptId AND tbServicesMstEntity.orgid = :orgId and tbServicesMstEntity.comV1='N' AND tbServicesMstEntity.smServActive = :servActive ORDER BY smServiceName ASC")
    List<ServiceMaster> findActiveServiceByDeptIdAndNotActualSer(@Param("deptId") Long deptId, @Param("orgId") Long orgId,
            @Param("servActive") Long servActive);

    @Query("SELECT tbServicesMstEntity FROM ServiceMaster tbServicesMstEntity "
            + " WHERE tbServicesMstEntity.tbDepartment.dpDeptid= :deptId AND tbServicesMstEntity.orgid = :orgId AND tbServicesMstEntity.smServActive = :servActive ORDER BY smServiceName ASC")
    List<ServiceMaster> findActiveServiceByDeptId(@Param("deptId") Long deptId, @Param("orgId") Long orgId,
            @Param("servActive") Long servActive);

    @Query("select tbServicesMstEntity.smServiceId from ServiceMaster tbServicesMstEntity"
            + " where tbServicesMstEntity.smServiceName= :serviceName and tbServicesMstEntity.orgid = :orgId ")
    Long checkForDuplicateService(@Param("serviceName") String serviceName, @Param("orgId") Long orgId);

    @Query("select tbServicesMstEntity.smServiceId, tbServicesMstEntity.smServiceName, tbServicesMstEntity.smServiceNameMar,"
            + " tbCfcChecklistMstEntity.docGroup from TbCfcChecklistMstEntity tbCfcChecklistMstEntity,"
            + " ServiceMaster tbServicesMstEntity where tbCfcChecklistMstEntity.tbServicesMst=tbServicesMstEntity.smServiceId"
            + " and tbCfcChecklistMstEntity.tbOrganisation.orgid=tbServicesMstEntity.orgid"
            + " and tbCfcChecklistMstEntity.clmStatus='A'"
            + " and tbCfcChecklistMstEntity.tbOrganisation.orgid=:orgId and tbServicesMstEntity.orgid=:orgId"
            + " group by tbServicesMstEntity.smServiceId, tbServicesMstEntity.smServiceName, tbServicesMstEntity.smServiceNameMar,"
            + " tbCfcChecklistMstEntity.docGroup")
    List<Object> findAllServiceListData(@Param("orgId") Long orgId);

    @Query("select tbServicesMstEntity.tbDepartment.dpDeptid from ServiceMaster tbServicesMstEntity "
            + " where   tbServicesMstEntity.smServiceId =:smServiceId and tbServicesMstEntity.orgid = :orgid")
    long findDepartmentIdByserviceid(@Param("smServiceId") Long smServiceId, @Param("orgid") Long orgid);

    @Query("SELECT sm.smServiceName, am.apmApplicationDate, am.apmTitle, am.apmFname, am.apmMname, am.apmLname FROM ServiceMaster sm, TbCfcApplicationMstEntity am WHERE am.apmApplicationId=:apmApplicationId AND sm.smServiceId=am.tbServicesMst.smServiceId")
    List<Object> findServiceAndApplicantNameByApplicationId(@Param("apmApplicationId") Long applicationId);

    /**
     * @param serviceId
     * @param orgId
     * @return
     */
    @Query("SELECT sm.smServiceName FROM ServiceMaster sm WHERE sm.smServiceId=:serviceId AND sm.orgid=:orgId ")
    String findServiceNameById(@Param("serviceId") Long serviceId, @Param("orgId") Long orgId);

    @Query("SELECT servicemaster FROM ServiceMaster servicemaster WHERE servicemaster.smShortdesc=:smShortdesc AND servicemaster.orgid=:orgId")
    ServiceMaster findShortCodeByOrgId(@Param("smShortdesc") String smShortdesc, @Param("orgId") Long orgId);

    @Query("SELECT DISTINCT sm.tbDepartment FROM ServiceMaster sm "
            + "WHERE sm.orgid=:orgId AND sm.smScrutinyApplicableFlag='Y'")
    List<Department> findDepartmentwithScrutiny(@Param("orgId") Long orgId);

    @Query("SELECT sm FROM ServiceMaster sm "
            + "WHERE sm.tbDepartment.dpDeptid=:deptId AND "
            + "sm.orgid=:orgId AND sm.smScrutinyApplicableFlag='Y' AND "
            + "sm.smServActive=:servActive ORDER BY smServiceName ASC")
    List<ServiceMaster> findServiceByDeptScrutiny(@Param("deptId") Long deptId, @Param("orgId") Long orgId,
            @Param("servActive") Long servActive);

    @Query("SELECT sm.smServiceId, sm.smServiceName,sm.smServiceNameMar FROM ServiceMaster sm "
            + "WHERE sm.smServiceId=?1 and sm.orgid=?2")
    public List<Object[]> findService(Long serviceId, Long orgId);

    /*
     * @Query("SELECT TbDocumentGroup.docName,TbDocumentGroup.docType,TbDocumentGroup.docSize,"
     * +
     * "TbDocumentGroup.ccmValueset,TbDocumentGroup.docSrNo,TbDocumentGroup.dgId FROM TbDocumentGroupEntity TbDocumentGroup WHERE TbDocumentGroup.groupCpdId=:groupId"
     * + " AND TbDocumentGroup.docStatus='A' " + "AND TbDocumentGroup.tbOrganisation.orgid=:orgId"
     * + " order by TbDocumentGroup.docSrNo ASC")
     * List<Object> getDocumentDetails(@Param("orgId") Long orgId, @Param("groupId") Long groupId);
     */
}
