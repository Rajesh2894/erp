package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbOrgDesignationEntity;

/**
 * Repository : TbOrgDesignation.
 */
public interface TbOrgDesignationJpaRepository extends PagingAndSortingRepository<TbOrgDesignationEntity, Long> {

    @Query("select designation, orgDesignationEntity "
            + " from TbOrgDesignationEntity orgDesignationEntity, Designation designation"
            + " where designation.dsgid = orgDesignationEntity.designation.dsgid"
            + " and orgDesignationEntity.tbOrganisation.orgid=:orgId"
            + " and orgDesignationEntity.mapStatus='A'")
    List<Object> findAllByOrigId(@Param("orgId") Long orgId);
}
