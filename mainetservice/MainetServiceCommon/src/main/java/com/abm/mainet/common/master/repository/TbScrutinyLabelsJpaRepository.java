package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cfc.scrutiny.domain.TbScrutinyLabelsEntity;
import com.abm.mainet.cfc.scrutiny.domain.TbScrutinyLabelsEntityKey;

/**
 * Repository : TbScrutinyLabels.
 */
@Repository
public interface TbScrutinyLabelsJpaRepository
		extends PagingAndSortingRepository<TbScrutinyLabelsEntity, TbScrutinyLabelsEntityKey> {

	@Query("SELECT serviceMaster.smServiceId, serviceMaster.smServiceName, serviceMaster.smServiceNameMar "
			+ " FROM  ServiceMaster serviceMaster, Department department " + " WHERE  serviceMaster.orgid =:orgId "
			+ "  AND serviceMaster.tbDepartment.dpDeptid = department.dpDeptid "
			+ " AND  ( department.dpDeptcode='ML'  OR NOT EXISTS (SELECT 'x' FROM TbScrutinyLabelsEntity scrutinyLabels "
			+ " WHERE scrutinyLabels.tbServiceMst.smServiceId = serviceMaster.smServiceId "
			+ " AND scrutinyLabels.compositePrimaryKey.orgid =:orgId))  ORDER BY department.dpDeptid ")
	List<Object> getAllSKDCLServices(@Param("orgId") Long orgId);

	@Query("SELECT serviceMaster.smServiceId, serviceMaster.smServiceName, serviceMaster.smServiceNameMar "
			+ " FROM  ServiceMaster serviceMaster, Department department " + " WHERE  serviceMaster.orgid =:orgId "
			+ "  AND serviceMaster.tbDepartment.dpDeptid = department.dpDeptid "
			+ " AND NOT EXISTS (SELECT 'x' FROM TbScrutinyLabelsEntity scrutinyLabels "
			+ " WHERE scrutinyLabels.tbServiceMst.smServiceId = serviceMaster.smServiceId "
			+ " AND scrutinyLabels.compositePrimaryKey.orgid =:orgId) ORDER BY department.dpDeptid ")
	List<Object> getAllServices(@Param("orgId") Long orgId);

	@Query("select scrutinyLabelsEntity from TbScrutinyLabelsEntity scrutinyLabelsEntity"
			+ " where scrutinyLabelsEntity.slActiveStatus='A'"
			+ " and scrutinyLabelsEntity.compositePrimaryKey.orgid=:orgId and scrutinyLabelsEntity.tbServiceMst.smServiceId=:smServiceId"
			+ " order by scrutinyLabelsEntity.compositePrimaryKey.slLabelId asc")
	List<TbScrutinyLabelsEntity> findAllScrutinyLabelData(@Param("smServiceId") Long smServiceId,
			@Param("orgId") Long orgId);

	@Query("SELECT serviceMaster.smServiceName, " + "serviceMaster.smServiceNameMar, serviceMaster.smServiceId "
			+ " FROM  ServiceMaster serviceMaster, Department department " + " WHERE serviceMaster.orgid =:orgId "
			+ " AND serviceMaster.tbDepartment.dpDeptid = department.dpDeptid " + " ORDER BY  department.dpDeptid ")
	List<Object> getScrutinyServices(@Param("orgId") Long orgId);

	@Query("select scrutinyLabelsEntity from TbScrutinyLabelsEntity scrutinyLabelsEntity"
			+ " where scrutinyLabelsEntity.slActiveStatus='A'"
			+ " and scrutinyLabelsEntity.compositePrimaryKey.orgid=:orgId and scrutinyLabelsEntity.tbServiceMst.smServiceId=:smServiceId and scrutinyLabelsEntity.levels=:levelId"
			+ " order by scrutinyLabelsEntity.compositePrimaryKey.slLabelId asc")
	List<TbScrutinyLabelsEntity> findAllScrutinyLabelsByServiceAndLevelId(@Param("smServiceId") Long smServiceId,
			@Param("levelId") Long levelId, @Param("orgId") Long orgId);

	@Query("select scrutinyLabelsEntity from TbScrutinyLabelsEntity scrutinyLabelsEntity" + " where "
			+ "scrutinyLabelsEntity.slActiveStatus='A' and "
			+ " scrutinyLabelsEntity.compositePrimaryKey.orgid=:orgId and scrutinyLabelsEntity.tbServiceMst.smServiceId=:smServiceId and scrutinyLabelsEntity.triCod1=:triCod1")
	List<TbScrutinyLabelsEntity> checkScrutinyLabelExistOrNot(@Param("smServiceId") Long smServiceId,
			@Param("orgId") Long orgId, @Param("triCod1") Long triCod1);

	@Query("select scrutinyLabelsEntity from TbScrutinyLabelsEntity scrutinyLabelsEntity"
			+ " where scrutinyLabelsEntity.slActiveStatus='A'"
			+ " and scrutinyLabelsEntity.compositePrimaryKey.orgid=:orgId and scrutinyLabelsEntity.tbServiceMst.smServiceId=:smServiceId and scrutinyLabelsEntity.triCod1=:triCod1"
			+ " order by scrutinyLabelsEntity.compositePrimaryKey.slLabelId asc")
	List<TbScrutinyLabelsEntity> findAllScrutinyLabelDataForSKDCL(@Param("smServiceId") Long smServiceId,
			@Param("orgId") Long orgId, @Param("triCod1") Long triCod1);

	@Query("select scrutinyLabelsEntity from TbScrutinyLabelsEntity scrutinyLabelsEntity"
			+ " where scrutinyLabelsEntity.slActiveStatus='A'"
			+ " and scrutinyLabelsEntity.compositePrimaryKey.orgid=:orgId and scrutinyLabelsEntity.tbServiceMst.smServiceId=:smServiceId"
			+ " and scrutinyLabelsEntity.slFormName='LoiGeneration.html' and scrutinyLabelsEntity.gmId=:gmId")
	TbScrutinyLabelsEntity checkLoiGenerationExistOrNot(@Param("smServiceId") Long smServiceId,
			@Param("orgId") Long orgId, @Param("gmId") Long gmId);
	
	@Query("select scrutinyLabelsEntity from TbScrutinyLabelsEntity scrutinyLabelsEntity"
			+ " where scrutinyLabelsEntity.slActiveStatus='A'"
			+ " and scrutinyLabelsEntity.compositePrimaryKey.orgid=:orgId and scrutinyLabelsEntity.tbServiceMst.smServiceId=:smServiceId"
			+ " and scrutinyLabelsEntity.slFormName='MeterDetailsConnectionForm.html' and scrutinyLabelsEntity.gmId=:gmId")
	TbScrutinyLabelsEntity checkMeteredExistOrNot(@Param("smServiceId") Long smServiceId,
			@Param("orgId") Long orgId, @Param("gmId") Long gmId);

	@Query("select scrutinyLabelsEntity from TbScrutinyLabelsEntity scrutinyLabelsEntity"
			+ " where scrutinyLabelsEntity.slActiveStatus='A'"
			+ " and scrutinyLabelsEntity.compositePrimaryKey.orgid=:orgId and scrutinyLabelsEntity.tbServiceMst.smServiceId=:smServiceId"
			+ " and scrutinyLabelsEntity.slFormName='LoiGeneration.html' and scrutinyLabelsEntity.gmId=:gmId and scrutinyLabelsEntity.triCod1=:triCod1")
	TbScrutinyLabelsEntity checkLoiGenerationExistOrNotForSkdcl(@Param("smServiceId") Long smServiceId,
			@Param("orgId") Long orgId, @Param("gmId") Long gmId, @Param("triCod1") Long triCod1);
	
	@Query("SELECT serviceMaster.smServiceId, serviceMaster.smServiceName, serviceMaster.smServiceNameMar ,serviceMaster.tbDepartment.dpDeptid "
			+ " FROM  ServiceMaster serviceMaster" + " WHERE  serviceMaster.orgid =:orgId "
			)
	List<Object> getAllActiveServices(@Param("orgId") Long orgId);
	
	@Query("select s.slQuery from TbScrutinyLabelsEntity s where s.levels=:levels and s.compositePrimaryKey.slLabelId=:slLabelId and s.tbServiceMst.smServiceId=:smServiceId and s.compositePrimaryKey.orgid=:orgId")
	String getGroupValue(@Param("levels") Long levels,@Param("slLabelId") Long slLabelId,@Param("smServiceId") Long smServiceId,@Param("orgId") Long orgId);

}
