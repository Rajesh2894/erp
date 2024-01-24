package com.abm.mainet.cfc.loi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.cfc.loi.domain.TbLoiMasEntityKey;

/**
 * Repository : TbLoiMas.
 */
public interface TbLoiMasJpaRepository extends PagingAndSortingRepository<TbLoiMasEntity, TbLoiMasEntityKey> {

    /**
     * @param applicationId
     * @param serviceId
     * @param orgid
     * @return List<TbLoiMasEntity>
     */
    @Query("select l from TbLoiMasEntity l where l.loiApplicationId=:applicationId and l.loiServiceId=:serviceId and l.compositePrimaryKey.orgid=:orgid and l.loiStatus='A'")
    List<TbLoiMasEntity> findLoiMasBySearchDTO(@Param("applicationId") Long applicationId, @Param("serviceId") Long serviceId,
            @Param("orgid") long orgid);

    /**
     * @param orgid
     * @return List<TbLoiMasEntity>
     */
    @Query("select l from TbLoiMasEntity l where l.compositePrimaryKey.orgid=:orgid  and l.loiStatus='A'")
    List<TbLoiMasEntity> findLoiMasByorgId(@Param("orgid") long orgid);

    @Query("select l from TbLoiMasEntity l where l.loiApplicationId=:applicationId and  l.compositePrimaryKey.orgid=:orgid and l.loiPaid='N' and l.loiStatus='A' ")
    TbLoiMasEntity findloiByApplicationIdForDeletion(@Param("applicationId") Long applicationId, @Param("orgid") long orgid);

    @Query("select l from TbLoiMasEntity l where l.loiServiceId=:serviceId and l.compositePrimaryKey.orgid=:orgid and l.loiStatus='A'")
    List<TbLoiMasEntity> findLoiMasBySLoiNo(@Param("serviceId") Long serviceId,
            @Param("orgid") long orgid);

    @Query("select l from TbLoiMasEntity l where l.loiNo=:loiNo and l.compositePrimaryKey.orgid=:orgid")
    TbLoiMasEntity findLoiIdbyLoiNumber(@Param("loiNo") String loiNumber, @Param("orgid") Long orgId);

    @Modifying
    @Query("update TbLoiMasEntity l set l.loiStatus='I' where l.loiApplicationId=:applicationId and l.compositePrimaryKey.orgid=:orgid")
    int inactiveLoi(@Param("applicationId") Long applicationId, @Param("orgid") Long orgId);

    @Query("select a.loiApplicationId, b.smServiceName, b.smServiceNameMar,b.smShortdesc, a.loiNo, a.loiRefId, a.loiAmount, a.loiPaid, a.loiServiceId,a.loiDate, c.orgId from TbLoiMasEntity a, ServiceMaster b, CitizenDashboardView c  where a.loiPaid='N' and a.loiStatus='A' and a.loiServiceId = b.smServiceId and b.smServiceName=c.smServiceName  and  a.loiApplicationId = c.apmApplicationId  and c.orgId=:orgId  and (c.empId=:empId  or  c.apmMobileNo=:mobileNo) ")
	List<Object[]> findLoiInformation(@Param("orgId") Long orgId, @Param("mobileNo") String mobileNo, @Param("empId") Long empId);

	@Query("select distinct a.compositePrimaryKey.loiId, a.loiApplicationId, a.loiNo from TbLoiMasEntity a where  a.compositePrimaryKey.orgid=:orgId and a.loiStatus='A'")
	List<Object[]> findLoiDetailsByorgId(@Param("orgId") Long orgId);
	
	 @Query("select l from TbLoiMasEntity l where l.loiApplicationId=:applicationId and  l.compositePrimaryKey.orgid=:orgid ")
	 TbLoiMasEntity findloiByApplicationIdAndOrgId(@Param("applicationId") Long applicationId, @Param("orgid") long orgid);
	
	

}
