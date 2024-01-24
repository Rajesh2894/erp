package com.abm.mainet.firemanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.firemanagement.domain.FireCallRegister;

/**
 * Spring Data repository for the ComplainRegister entity.
 */
@Repository
public interface FireCallRegisterRepository extends JpaRepository<FireCallRegister, Long> {

	// FireCallRegister findByCmplntNoAndOrgid(String cmplntNo, Long orgid);

	List<FireCallRegister> findByOrgid(Long orgid);

	FireCallRegister findByCmplntNoAndOrgidAndComplaintStatus(String cmplntNo, Long orgid, String complaintStatus);

	List<FireCallRegister> findByOrgidAndComplaintStatus(Long orgid, String complaintStatus);

	@Modifying
	@Query("UPDATE FireCallRegister a SET a.complaintStatus =:status where a.cmplntNo =:cmplntNo")
	void updateComplainStatus(@Param("cmplntNo") String cmplntNo, @Param("status") String status);

	/*
	 * @Modifying
	 * // @Query("SELECT e FROM FireCallRegister e WHERE e.occdate BETWEEN e:date=:date AND e.callClosedDate=:callClosedDate"
	 * )
	 * 
	 * @Query("select m.date,m.callClosedDate from FireCallRegister m where m.cmplntNo=:cmplntNo"
	 * ) public List<FireCallRegister> findAllEvents(@Param("cmplntNo") String
	 * cmplntNo);
	 */
	@Query("select count(*) from FireCallRegister m where m.orgid=:orgid and m.cmplntId=:cmplntId and :inputDate between m.date and sysdate()")
	public Long ValidateDate(@Param("orgid") Long orgid, @Param("inputDate") Date inputDate,
			@Param("cmplntId") Long cmplntId);

	@Query("select count(*) from FireCallRegister m where m.orgid=:orgid and m.cmplntId=:cmplntId and :inputTime between m.time and sysdate()")
	public Long validateTime(@Param("orgid") Long orgid, @Param("inputTime") String inputTime,
			@Param("cmplntId") Long cmplntId);

	@Modifying
	@Query("update FireCallRegister d set d.FireWFStatus=:wfStatus where d.cmplntId=:cmplntId and d.orgid=:orgid")
	void updateWorkFlowStatus(@Param("cmplntId") Long cmplntId, @Param("orgid") Long orgId,
			@Param("wfStatus") String wfStatus);

	@Query("select d from FireCallRegister d where d.cpdFireStation=:fireStation and d.cmplntNo=:cmplntNo and d.orgid=:orgid")
	FireCallRegister findByCmplntNoAndOrgid(@Param("fireStation") String fireStation,
			@Param("cmplntNo") String cmplntNo, @Param("orgid") Long orgid);

	@Modifying
	@Query("update FireCallRegister d set d.complaintStatus=:complaintStatus where d.cmplntId=:cmplntId and d.orgid=:orgid")
	void updateCallRegisterInProgress(@Param("cmplntId") Long cmplntId, @Param("orgid") Long orgId,
			@Param("complaintStatus") String complaintStatus);

	@Query("select m.date,m.time from FireCallRegister m where m.orgid=:orgid and m.cmplntId=:cmplntId ")
	public String validateDateTime(@Param("orgid") Long orgid, @Param("cmplntId") Long cmplntId);

	@Query("select e.empId from Employee e where e.tbDepartment.dpDeptid=:depId and e.organisation.orgid=:orgid")
	List<Long> getEmpId(@Param("depId") Long depId,@Param("orgid") Long orgid);
	//and e.designation.dsgid in(2,6) where e.tbDepartment.dpDeptid=:depId

	@Modifying
	@Query("update FireCallRegister d set d.hodRemarks=:hodRemarks  where d.cmplntNo =:cmplntNo and d.orgid=:orgid")
	void updateFinalCallClosureComment(@Param("cmplntNo")String cmplntNo,@Param("hodRemarks") String hodRemarks,@Param("orgid") Long orgid);

}

// "select count(*) from User"+" where userName=:userName and
// passWord=:passWord" 
