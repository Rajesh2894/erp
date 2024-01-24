package com.abm.mainet.workManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.workManagement.domain.ScheduleOfRateDetEntity;
import com.abm.mainet.workManagement.domain.ScheduleOfRateMstEntity;

@Repository
public interface ScheduleOfRateRepository extends CrudRepository<ScheduleOfRateMstEntity, Long>, ScheduleOfRateRepositoryCustom {

    /**
     * get All Active Schedule of rate Master details by organization id.
     * @param orgId
     * @return List<ScheduleOfRateMstEntity>
     */
    @Query("select sor from ScheduleOfRateMstEntity sor where sor.orgId=:orgId and sor.sorActive is 'Y' order by sor.sorId desc ")
    List<ScheduleOfRateMstEntity> findAllActiveScheduleByOrgId(@Param("orgId") Long orgId);

    /**
     * used to inactive SOR master details by sor id
     * @param flag ="N"
     * @param empId
     * @param sorId
     */

    @Modifying
    @Transactional
    @Query("UPDATE ScheduleOfRateMstEntity sor set sor.sorActive = 'N', sor.updatedBy=:empId, sor.updatedDate = CURRENT_DATE WHERE sor.sorId=:sorId")
    void inactiveSorMas(@Param("sorId") Long sorId, @Param("empId") Long empId);

    /**
     * used to inactive All SOR child by SOR Master primary key SorId.
     * @param sorId
     * @param empId
     */
    @Modifying
    @Transactional
    @Query("UPDATE ScheduleOfRateDetEntity sor set sor.sorDActive = 'N', sor.updatedBy=:empId, sor.updatedDate = CURRENT_DATE WHERE sor.scheduleOfRateMst.sorId=:sorId")
    void inactiveAllChildByMasId(@Param("sorId") Long sorId, @Param("empId") Long empId);

    /**
     * used to inactive particular list of child by SOR Master primary key SorId.
     * @param removeChildIds
     * @param updatedBy
     */
    @Modifying
    @Transactional
    @Query("UPDATE ScheduleOfRateDetEntity sord set sord.sorDActive = 'N', sord.updatedBy=?1, sord.updatedDate = CURRENT_DATE WHERE sord.sordId in (?2)")
    void inactiveSorChildRecords(Long updatedBy, List<Long> removeChildIds);

    /**
     * used to get All SOR name by organization id
     * @param orgId
     * @return SOR Name Object List
     */
    @Query("select sor.sorId,sor.sorName from ScheduleOfRateMstEntity sor where sor.orgId=:orgId")
    List<Object[]> findAllSorNamesByOrgId(@Param("orgId") Long orgId);

    /**
     * get All Active and Inactive Schedule of rate Master details by organization id.
     * @param orgId
     * @return List<ScheduleOfRateMstEntity>
     */
    @Query("select sor from ScheduleOfRateMstEntity sor where sor.orgId=:orgId order by sor.sorId desc")
    List<ScheduleOfRateMstEntity> findAllScheduleByOrgId(@Param("orgId") Long orgId);

    /**
     * used to get Existing active SOR type by SOR Name Id
     * @param sorCpdId
     * @return
     */
    @Query("select sor from ScheduleOfRateMstEntity sor where sor.sorCpdId=:sorCpdId and sor.orgId=:orgId and sor.sorActive is 'Y'")
    ScheduleOfRateMstEntity findExistingActiveSorType(@Param("sorCpdId") Long sorCpdId, @Param("orgId") Long orgId);

    /**
     * it is used to get all active Schedule of Rate Item Details List
     * @param orgid
     * @return List<ScheduleOfRateDetDto> if record found else return empty list
     */
    @Query("select sor from ScheduleOfRateDetEntity sor where sor.orgId=:orgId and sor.sorDActive is 'Y' order by sor.sordId desc")
    List<ScheduleOfRateDetEntity> findAllScheduleDetailsByOrgId(@Param("orgId") Long orgId);

    /**
     * this service is used to find item details by using SOR details primary key sordId
     * @param sordId
     * @return ScheduleOfRateDetEntity
     */
    @Query("select sor from ScheduleOfRateDetEntity sor where sor.sordId=:sordId ")
    ScheduleOfRateDetEntity findSorItemDetailsBySordId(@Param("sordId") long sordId);

    @Query("select sor from ScheduleOfRateDetEntity sor where sor.sorDIteamNo=:itemCode and sor.orgId=:orgId and sor.scheduleOfRateMst.sorId=:sorId")
    ScheduleOfRateDetEntity getSorDetailsByItemCode(@Param("orgId") Long orgId, @Param("itemCode") String itemCode,
            @Param("sorId") Long sorId);

    @Query("select sor from ScheduleOfRateDetEntity sor where sor.sordCategory=:sordCategory and sor.orgId=:orgId and sor.scheduleOfRateMst.sorId=:sorId")
    List<ScheduleOfRateDetEntity> getAllItemsListByChapterId(@Param("sordCategory") Long chapterValue,
            @Param("sorId") Long sorId, @Param("orgId") long orgId);
}
