package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.Organisation;

/**
 * Repository : TbOrganisation.
 */
public interface TbOrganisationJpaRepository extends PagingAndSortingRepository<Organisation, Long> {
    @Query("select tbOrganisationEntity from Organisation tbOrganisationEntity where tbOrganisationEntity.defaultStatus= :defaultStatus")
    List<Organisation> defaultexist(@Param("defaultStatus") String defaultStatus);

    @Query("select org from Organisation org where org.ONlsOrgname= :ONlsOrgname")
    Organisation findData(@Param("ONlsOrgname") String ONlsOrgname);

    @Query("select org from Organisation org where org.orgStatus='A' order by org.orgid asc")
    List<Organisation> findActiveOrgList();

    @Query("SELECT org.orgid, org.ONlsOrgname, org.oNlsOrgnameMar "
            + "FROM Organisation org WHERE org.orgStatus=:orgStatus AND org.ONlsOrgname IS NOT NULL")
    public List<Object[]> findAllOrganization(@Param("orgStatus") String orgStatus);

    @Query("select org from Organisation org where org.ulbOrgID= :ulbOrgID")
    Organisation findOrgById(@Param("ulbOrgID") Long ulbOrgID);

    @Query("select tbOrganisationEntity from Organisation tbOrganisationEntity where tbOrganisationEntity.defaultStatus='Y'")
    Organisation defaultOrganisation();

    @Query("select orgEntity from Organisation orgEntity where orgEntity.orgShortNm=:orgShortNm")
    Organisation findByShortCode(@Param("orgShortNm") String orgShortNm);

    @Query("select org from Organisation org where org.oNlsOrgnameMar= :oNlsOrgnameMar")
    Organisation findDataByNameReg(@Param("oNlsOrgnameMar") String oNlsOrgnameMar);

    @Query("select org.ulbOrgID from Organisation org where org.orgid= :orgid")
    Long findOrgShortNameByOrgId(@Param("orgid") Long orgid);
    
    @Query("select org.ONlsOrgname from Organisation org where org.orgid= :orgid")
    String findOrgNameById(@Param("orgid") Long orgid);
}
