/**
 * 
 */
package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.property.domain.PropertyBillExceptionEntity;

/**
 * @author cherupelli.srikanth
 * @since 29 april 2021
 */
@Repository
public interface PropertyBillExceptionRepository extends JpaRepository<PropertyBillExceptionEntity, Long>{

	 @Query("select pb.propNo from PropertyBillExceptionEntity pb where pb.orgId=:orgId and pb.billType=:billType and pb.status=:status and pb.createdBy=:userId")
	List<String> getAllPendingBillPropNos(@Param("orgId") Long orgId, @Param("billType") String billType, @Param("status") String status, @Param("userId") Long userId);
	 
	 @Query("select pb from PropertyBillExceptionEntity pb where pb.propNo=:propNo and pb.status='A'")
	 List<PropertyBillExceptionEntity> getPropBillExceptionByPropNo(@Param("propNo") String propNo);
	 
	 @Modifying
	    @Query("DELETE from PropertyBillExceptionEntity a where a.createdBy=:empId and a.orgId=:orgId")
	    void deleteRecordFromException(@Param("empId") Long empId, @Param("orgId") Long orgId);
}
