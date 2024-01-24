package com.abm.mainet.securitymanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.HolidayMaster;
import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;
import com.abm.mainet.securitymanagement.domain.EmployeeScheduling;
import com.abm.mainet.securitymanagement.domain.EmployeeSchedulingDet;
import com.abm.mainet.securitymanagement.domain.ShiftMaster;

@Repository
public interface EmployeeSchedulingRepository extends JpaRepository<EmployeeScheduling, Long>{
	
	@Query("select sm from ContractualStaffMaster sm ")
	List<ContractualStaffMaster> findContractualEmpNameById();

	@Query("select sm.emplScdlId from EmployeeScheduling sm where sm.deplId.deplId=:deplId and sm.orgid=:orgid ")
	Long getBydeplId(@Param("deplId")Long deplId,@Param("orgid") Long orgid);
	
	@Query("select cm from ContractualStaffMaster cm where cm.vendorId=:vendorId and cm.orgid=:orgid ")
	List<ContractualStaffMaster> getStaffNameByVendorId(@Param("vendorId")Long vendorId,@Param("orgid")Long orgid);
	
	@Query("select sm from ShiftMaster sm where sm.shiftId=:cpdShiftId and sm.orgid=:orgid and sm.status='A' ")
	ShiftMaster findByShiftId(@Param("cpdShiftId")Long cpdShiftId, @Param("orgid") Long orgid);
	
	@Query("select cm from ContractualStaffMaster cm where cm.contStaffIdNo=:contStaffIdNo and cm.vendorId=:vendorId and cm.orgid=:orgid and cm.status='A'")
	ContractualStaffMaster findByEmpIdAndVendor(@Param("contStaffIdNo")String contStaffIdNo,@Param("vendorId")Long vendorId,@Param("orgid")Long orgid);
	
	@Query("select ds from EmployeeScheduling ds where ds.contStaffIdNo =:contStaffIdNo and ds.vendorId=:vendorId  and ds.orgid=:orgid ")
	List<EmployeeScheduling> findStaffListByEmpId(@Param("contStaffIdNo")String contStaffIdNo,@Param("vendorId")Long vendorId,@Param("orgid") Long orgId);
	
	@Query("select hm from HolidayMaster hm where hm.hoDate between :fromDate and :toDate and hm.orgId=:orgid order by 1 desc ")
	List<HolidayMaster> findHolidaysByYear(@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,@Param("orgid")Long orgid);
	
	@Query("select esd from EmployeeSchedulingDet esd where esd.employeeScheduling.emplScdlId=:emplScdlId and esd.orgid=:orgid")
	List<EmployeeSchedulingDet> findDetailsById(@Param("emplScdlId")Long emplScdlId,@Param("orgid")Long orgid);
	
	@Query("select DISTINCT cm.contStaffName from ContractualStaffMaster cm where cm.contStaffIdNo=:contStaffIdNo and cm.vendorId=:vendorId and cm.orgid=:orgid")
	Object[] findByEmpIdAndVendorId(@Param("contStaffIdNo")String contStaffIdNo,@Param("vendorId")Long vendorId,@Param("orgid")Long orgid);
	
}
