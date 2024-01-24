package com.abm.mainet.common.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.Organisation;

@Repository
public interface TbOrganisationJpaRepository extends PagingAndSortingRepository<Organisation, Long> {

    @Query("select org from Organisation org where org.orgStatus='A' order by org.orgid asc")
    Set<Organisation> findActiveOrgList();

    @Query("select tbOrganisationEntity from Organisation tbOrganisationEntity where tbOrganisationEntity.defaultStatus= :defaultStatus")
    List<Organisation> defaultexist(@Param("defaultStatus") String defaultStatus);

    @Query("select org from Organisation org where org.ulbOrgID= :ulbOrgID")
    Organisation findOrgById(@Param("ulbOrgID") Long ulbOrgID);

    @Query("select org from Organisation org where org.ONlsOrgname= :ONlsOrgname")
    Organisation findData(@Param("ONlsOrgname") String ONlsOrgname);

    @Query("select org from Organisation org where org.ONlsOrgname= :ONlsOrgname")
    Organisation findDataByNameReg(@Param("ONlsOrgname") String oNlsOrgnameMar);

    @Query("select orgEntity from Organisation orgEntity where orgEntity.orgShortNm=:orgShortNm")
    Organisation findByShortCode(@Param("orgShortNm") String orgShortNm);
}
