
package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.ServiceMaster;

/**
 * @author Lalit.Prusti
 *
 */
@Repository
public interface ServiceMasterRepository extends CrudRepository<ServiceMaster, Long> {

	@Query("SELECT sm.smChklstVerify FROM ServiceMaster sm WHERE sm.smServiceId=:serviceId AND sm.orgid=:orgId")
	List<Object> isCheckListApplicable(@Param("serviceId") long serviceId, @Param("orgId") long orgId);

	@Query("SELECT sm.smFeesSchedule FROM ServiceMaster sm WHERE sm.smServiceId=:serviceId AND sm.orgid=:orgId")
	List<Object> isChargeApplicable(@Param("serviceId") long serviceId, @Param("orgId") long orgId);

	@Query("SELECT sm.smShortdesc FROM ServiceMaster sm WHERE sm.smServiceId=:serviceId AND sm.orgid=:orgId")
	List<String> fetchServiceShortdesc(@Param("serviceId") long serviceId, @Param("orgId") long orgId);

	@Query("SELECT sm  FROM ServiceMaster sm WHERE sm.smServiceId=:serviceId AND sm.orgid=:orgId")
	ServiceMaster getServiceMaster(@Param("serviceId") long serviceId, @Param("orgId") long orgId);

	@Query("SELECT sm  FROM ServiceMaster sm WHERE sm.smShortdesc=:smShortdesc AND sm.orgid=:orgId")
	ServiceMaster getServiceMasterByShrotName(@Param("smShortdesc") String smShortdesc, @Param("orgId") long orgId);

	@Query("SELECT sm.smServiceId, sm.smServiceName, sm.smShortdesc, sm.smServiceNameMar FROM ServiceMaster sm "
			+ "WHERE sm.orgid=:orgId AND sm.tbDepartment.dpDeptid=:depId AND sm.smServActive=:activeStatusId AND sm.smServiceName IS NOT NULL")
	public List<Object[]> findAllActiveServicesByDepartment(@Param("orgId") Long orgId, @Param("depId") Long depId,
			@Param("activeStatusId") Long activeStatusId);

	@Query("SELECT sm.smShortdesc FROM ServiceMaster sm " + "WHERE sm.orgid=:orgId AND sm.smServiceId=:smServiceId")
	String getServiceShortCode(@Param("smServiceId") Long serviceId, @Param("orgId") Long orgId);

	@Query("SELECT sm.smServiceId FROM ServiceMaster sm WHERE sm.smShortdesc=:shortCode AND sm.orgid=:orgId")
	Long findServiceIdByShortCodeAndOrgId(@Param("shortCode") String shortCode, @Param("orgId") Long orgId);

	@Query("SELECT sm.smServiceId, sm.smServiceName, sm.smServiceNameMar ,sm.smShortdesc FROM ServiceMaster sm "
			+ "WHERE sm.orgid=:orgId AND sm.tbDepartment.dpDeptid=:depId AND sm.smServActive=:activeService AND sm.smServiceName IS NOT NULL")
	public List<Object[]> findAllServicesByDepartment(@Param("orgId") Long orgId, @Param("depId") Long depId,
			@Param("activeService") Long activeLookUpId);

	@Query("SELECT sm.smChklstVerify FROM ServiceMaster sm WHERE sm.smShortdesc=:smShortdesc AND sm.orgid=:orgId")
	Long isCheckListApplicable(@Param("smShortdesc") String smShortdesc, @Param("orgId") long orgId);

	@Query("SELECT count(sm) FROM ServiceMaster sm WHERE sm.smServiceId=:serviceId AND sm.orgid=:orgId and sm.smRtsFlag='Y'")
	int getCountOfIsServiceRTS(@Param("serviceId") Long serviceId, @Param("orgId") long orgId);

	@Query("SELECT sm.smServiceId, sm.smServiceName, sm.smShortdesc, sm.smServiceNameMar FROM ServiceMaster sm "
			+ "WHERE sm.orgid=:orgId AND sm.tbDepartment.dpDeptid=:depId AND sm.smServActive=:activeStatusId and sm.comV1=:notActualFlag AND sm.smServiceName IS NOT NULL order by sm.smServiceName asc")
	public List<Object[]> findAllActiveServicesWhichIsNotActual(@Param("orgId") Long orgId, @Param("depId") Long depId,
			@Param("activeStatusId") Long activeStatusId, @Param("notActualFlag") String notActualFlag);

	@Query("SELECT sm.smExternalServiceFlag FROM ServiceMaster sm WHERE sm.smServiceId=:smServiceId AND sm.orgid=:orgId")
	String getServiceExternalFlag(@Param("smServiceId") Long serviceId, @Param("orgId") Long orgId);

	@Query("SELECT sm.smServiceName FROM ServiceMaster sm " + "WHERE sm.orgid=:orgId AND sm.smServiceId=:smServiceId")
	String getServiceNameByServiceId(@Param("smServiceId") Long serviceId, @Param("orgId") Long orgId);


}
