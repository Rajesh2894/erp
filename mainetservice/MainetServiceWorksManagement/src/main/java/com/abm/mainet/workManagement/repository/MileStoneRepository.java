package com.abm.mainet.workManagement.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.MileStone;

/**
 * @author vishwajeet.kumar
 * @since 22 March 2018
 */
@Repository
public interface MileStoneRepository extends CrudRepository<MileStone, Long> {

	/**
	 * To get Milestone Records By projectId,WorkId,OrganizationId.
	 * 
	 * @param projId
	 * @param workId
	 * @param orgid
	 * @return List<MileStone>
	 */
	@Query("select a from MileStone a where a.projectMaster.projId=:projId and a.mastDetailsEntity.workId=:workId and  a.mileStoneType=:mileStoneType and a.orgId=:orgId")
	List<MileStone> milestoneByProjIdworkId(@Param("projId") Long projId, @Param("workId") Long workId,
			@Param("mileStoneType") String mileStoneType, @Param("orgId") Long orgId);

	/**
	 * To get Milestone Records By projectId,WorkId,OrganizationId.
	 * 
	 * @param projId
	 * @param workId
	 * @param orgid
	 * @return List<MileStone>
	 */
	@Query("select a from MileStone a where a.projectMaster.projId=:projId and a.mileStoneType=:mileStoneType and  a.orgId=:orgId and a.mastDetailsEntity.workId is null")
	List<MileStone> milestoneByProjId(@Param("projId") Long projId, @Param("mileStoneType") String mileStoneType,
			@Param("orgId") Long orgId);

	/**
	 * used to inactive Milestone Entries by Milestone Primary Key
	 * 
	 * @param removeChildIds
	 */
	@Modifying
	@Query("DELETE from  MileStone a where a.mileId in (?1)")
	void deleteEntityRecords(List<Long> removeChildIds);

	/**
	 * Used to get all data with respect to
	 * 
	 * @param projId
	 * @param orgId
	 * @param mileStoneFlag
	 * @return List<MileStone>
	 */
	@Query("select ms from MileStone ms where ms.projectMaster.projId=:projId and ms.mileStoneType=:mileStoneType and ms.orgId=:orgId ")
	List<MileStone> searchAllMileStoneProjectId(@Param("projId") Long projId, @Param("orgId") Long orgId,
			@Param("mileStoneType") String mileStoneType);

	/**
	 * Used to get all Data with respect to
	 * 
	 * @param orgId
	 * @param workId
	 * @param mileStoneFlag
	 * @return List<MileStone>
	 */
	@Query("select ms from MileStone ms where ms.orgId=:orgId and ms.mastDetailsEntity.workId=:workId and ms.mileStoneType=:mileStoneType")
	List<MileStone> searchAllMileStoneWorkId(@Param("orgId") Long orgId, @Param("workId") Long workId,
			@Param("mileStoneType") String mileStoneType);

	@Query("Select DISTINCT ms.projectMaster.projId=:projId ,ms.projectMaster.projNameEng,ms.projectMaster.projStartDate,"
			+ "ms.projectMaster.projEndDate,ms.projectMaster.projCode ,ms.mastDetailsEntity.workName,ms.mastDetailsEntity.workcode,"
			+ " ms.mastDetailsEntity.workStartDate,ms.mastDetailsEntity.workEndDate from MileStone ms where ms.orgId=:orgId")
	List<Object[]> searchAllProjectDetails(@Param("orgId") Long orgId, @Param("projId") Long projId);

	@Query("select a from MileStone a where a.projectMaster.projId=:projId and a.mastDetailsEntity.workId=:workId and  a.mileStoneId=:mileStoneId and a.orgId=:orgId")
	List<MileStone> getMileStoneDtobyMileId(@Param("projId") Long projId, @Param("workId") Long workId,
			@Param("mileStoneId") Long mileStoneId, @Param("orgId") Long orgId);

	@Query("select sum(a.milestonePer) from MileStoneEntryEntity a where a.projId=:projId and a.workId=:workId and a.orgId=:orgId")
	BigDecimal getMilestonePer(@Param("projId") Long projId, @Param("workId") Long workId,@Param("orgId") Long orgId);
	
	//D91022 To update milestone percentage
	@Modifying
    @Query("UPDATE MileStone m set m.msPercent=:msPercent WHERE m.mileId=:mileId")
    void updateMilePer(@Param("msPercent") BigDecimal msPercent, @Param("mileId") Long mileId);
}
