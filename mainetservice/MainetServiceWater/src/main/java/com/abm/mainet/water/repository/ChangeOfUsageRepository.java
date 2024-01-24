package com.abm.mainet.water.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.water.constant.QueryConstants;
import com.abm.mainet.water.domain.ChangeOfUsage;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;

/**
 * Repository : ChangeOfUses.
 */
@Repository
public interface ChangeOfUsageRepository extends CrudRepository<ChangeOfUsage, Long> {

    /**
     * Gets the connection details.
     *
     * @param orgId the org id
     * @param connectionNumber the connection number
     * @return the connection details
     */
    @Query(QueryConstants.ChangeOfUsageQuery.QUERY_CONNECTION_BY_CONNECTION_NO)
    public TbKCsmrInfoMH getConnectionDetails(@Param("orgId") long orgId, @Param("csCcn") String connectionNumber);

    /**
     * Gets the connection details.
     *
     * @param orgId the org id
     * 
     * @param connectionId the connection id
     * 
     * @return the connection details
     */
    @Query(QueryConstants.ChangeOfUsageQuery.QUERY_CONNECTION_BY_CONNECTION_ID)
    public TbKCsmrInfoMH getConnectionDetails(@Param("orgId") long orgId, @Param("csIdn") long connectionId);

    /**
     * Gets the change of usaes.
     *
     * @param orgId the org id
     * 
     * @param applicationId the application id
     * 
     * @return the change of usaes
     */
    @Query(QueryConstants.ChangeOfUsageQuery.QUERY_CHANGE_OF_USES)
    public ChangeOfUsage getChangeOfUsaes(@Param("orgId") long orgId, @Param("applicationId") long applicationId);

    /**
     * @param orgId
     * @param csIdn
     * @param newTrmGroup1
     * @param newTrmGroup2
     * @param newTrmGroup3
     * @param newTrmGroup4
     * @param newTrmGroup5
     */
    @Modifying
    // (clearAutomatically = true)
    @Query(QueryConstants.ChangeOfUsageQuery.UPDATE_CHANGE_OF_USES)
    public void updateChangeOfUsaes(@Param("orgId") Long orgId, @Param("connectionId") Long connectionId,
            @Param("trmGroup1") Long trmGroup1, @Param("trmGroup2") Long trmGroup2, @Param("trmGroup3") Long trmGroup3,
            @Param("trmGroup4") Long trmGroup4, @Param("trmGroup5") Long trmGroup5);

    @Query("SELECT cu.apmApplicationId FROM ChangeOfUsage cu  WHERE cu.orgId = :orgId AND  cu.csIdn = :csIdn ")
    public List<Long> getChangeOfUsageApplicationId(@Param("orgId") Long orgId, @Param("csIdn") Long csIdn);


    /**
     * Gets the connection details.
     *
     * @param orgId the org id
     * 
     * @param couGrantFlag the couGrantFlag
     * 
     * @return the Change Of Usage details
     */
    @Query(QueryConstants.ChangeOfUsageQuery.QUERY_CONNECTION_BY_ORGID_AND_GRANT_FLAG)
    public List<ChangeOfUsage> getChangedUsageDetails(@Param("orgId") Long orgId, @Param("chanGrantFlag") String chanGrantFlag);

    @Modifying(clearAutomatically = true)
    @Query(QueryConstants.ChangeOfUsageQuery.UPDATE_CHANGE_OF_USAGE)
	public void updateChangeOfUsageEntity(@Param("apmApplicationId")Long apmApplicationId, @Param("chanGrantFlag")String chanGrantFlag, @Param("orgId")Long orgId);

}
