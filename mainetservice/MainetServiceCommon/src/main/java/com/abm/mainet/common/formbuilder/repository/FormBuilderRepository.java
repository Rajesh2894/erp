package com.abm.mainet.common.formbuilder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.formbuilder.domain.FormBuilderEntity;

/**
 * Repository : TbScrutinyLabels.
 */
@Repository
public interface FormBuilderRepository
        extends PagingAndSortingRepository<FormBuilderEntity, Long> {

    @Query("SELECT serviceMaster.smServiceId, serviceMaster.smServiceName, serviceMaster.smServiceNameMar,serviceMaster.smShortdesc "
            + " FROM  ServiceMaster serviceMaster, Department department "
            + " WHERE  serviceMaster.orgid =:orgId "
            + "  AND serviceMaster.tbDepartment.dpDeptid = department.dpDeptid "
            + "  AND department.dpDeptcode =:dpDeptcode AND serviceMaster.smServActive =:servActive"
            + " AND NOT EXISTS (SELECT 'x' FROM FormBuilderEntity scrutinyLabels "
            + " WHERE scrutinyLabels.smShortDesc = serviceMaster.smShortdesc "
            + " AND scrutinyLabels.orgid =:orgId) ORDER BY department.dpDeptid ")
    List<Object> getAllServices(@Param("orgId") Long orgId, @Param("dpDeptcode") String dpDeptcode,@Param("servActive") Long servActive);

    @Query("select scrutinyLabelsEntity from FormBuilderEntity scrutinyLabelsEntity"
            + " where scrutinyLabelsEntity.slActiveStatus='A'"
            + " and scrutinyLabelsEntity.orgid=:orgId and scrutinyLabelsEntity.smShortDesc=:smShortdesc"
            + " order by scrutinyLabelsEntity.slPosition asc")
    List<FormBuilderEntity> findAllFormLabelData(@Param("smShortdesc") String smShortdesc, @Param("orgId") Long orgId);

    @Query("SELECT serviceMaster.smServiceName, "
            + "serviceMaster.smServiceNameMar, serviceMaster.smShortdesc "
            + " FROM  ServiceMaster serviceMaster, Department department "
            + " WHERE serviceMaster.orgid =:orgId "
            + " AND serviceMaster.tbDepartment.dpDeptid = department.dpDeptid AND department.dpDeptcode='SWM'"
            + " ORDER BY  department.dpDeptid ")
    List<Object> getFormServices(@Param("orgId") Long orgId);

}
