package com.abm.mainet.disastermanagement.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.disastermanagement.domain.ComplainRegister;


/**
 * Spring Data repository for the ComplainRegister entity.
 */
@Repository
public interface ComplainRegisterRepository extends JpaRepository<ComplainRegister, Long> {
	
	
	  ComplainRegister findByComplainNoAndOrgid(String complainNo, Long orgid);
	  
	  List<ComplainRegister> findByOrgid(Long orgid);
	  
	  ComplainRegister getComplainByComplainId(Long complainId);
	 
	  //List<ComplainRegister> getComplainStatus(String Status);
	 
	
	@Modifying
	@Query("UPDATE ComplainRegister a SET a.complainStatus =:status where a.complainNo =:complainNo")
	void updateComplainStatus(String complainNo, String status);
 
	
	  @Query(value= "select td.DP_DEPTDESC,e.EMPNAME,cs.complain_scrutiny_status,cs.complaint_remark, cs.complain_id,cs.call_attend_date,cs.call_attend_time,cs.call_attend_employee,cs.reason_for_delay ,e.EMPLNAME from tb_dm_complain_scrutiny cs , " + 
	  		"tb_department td,employee e  " + 
	  		"where cs.DP_DEPTID=td.DP_DEPTID   " + 
	  		"and cs.USER_ID=e.EMPID   " + 
	  		"and  cs.complain_id=:complaintId", nativeQuery = true) 
	  List<Object []> fetchComplaints(@Param("complaintId") Long complaintId);
	 
	
	List<ComplainRegister> findByOrgidAndComplainStatusOrderByComplainId(Long orgId, String complainStatus);
	  
	@Query(value = "SELECT * FROM tb_dm_complain_register WHERE DATE(created_date) >= :fromDate AND DATE(created_date) <= :toDate AND orgid = :orgid "
			+ "AND complain_status !=:complainStatus AND (:callType is null or cod_complaint_type1 =:callType) " + 
			"AND (:callSubType is null or cod_complaint_type2 =:callSubType)ORDER BY created_date DESC", nativeQuery = true) 
	List<ComplainRegister> searchFireCallRegisterwithDate(@Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate, @Param("orgid") Long orgId, @Param("callType") Long callType,
			@Param("callSubType") Long callSubType, @Param("complainStatus") String complainStatus );
	
	
	@Query(value = "SELECT   " + 
			"     c.complain_id,   " + 
			"     c.complain_no,   " + 
			"     c.cod_complaint_type1,   " + 
			"     c.cod_complaint_type2,   " + 
			"     c.complaint_description,   " + 
			"     MAX(cs.complain_scrutiny_status) AS complain_scrutiny_status, " + 
			"     " + 
			"	 if( " + 
			"     (select count(1) from tb_dm_complain_scrutiny where complain_no=c.complain_no and complain_scrutiny_status='Rejected' and orgid=c.orgid) > 0,'Rejected', " + 
			"     if((select count(1) from tb_dm_complain_scrutiny where complain_no=c.complain_no and complain_scrutiny_status='Pending' and orgid=c.orgid) > 0,'Pending', " + 
			"	 if((select count(1) from tb_dm_complain_scrutiny where complain_no=c.complain_no and complain_scrutiny_status='Forward' and orgid=c.orgid) > 0, " + 
			"      " + 
			"     if((select count(1) from tb_dm_complain_scrutiny where complain_no=c.complain_no and complain_scrutiny_status='Approved' and orgid=c.orgid) > 0,'Approved','Pending'), " + 
			"      " + 
			"     if((select count(1) from tb_dm_complain_scrutiny where complain_no=c.complain_no and complain_scrutiny_status='Approved' and orgid=c.orgid) > 0,'Approved',null)) " + 
			"     )) as final_complain_scrutiny_status " + 
			"     " + 
			"     FROM   " + 
			"     tb_dm_complain_register c   " + 
			"     LEFT JOIN   " + 
			"     tb_dm_complain_scrutiny cs ON c.complain_no = cs.complain_no   " + 
			"     WHERE   " + 
			"     c.orgid=:orgid   " + 
			"     AND (:complaintType1 IS NULL OR c.cod_complaint_type1=:complaintType1)   " + 
			"     AND (:complaintType2 IS NULL OR c.cod_complaint_type2=:complaintType2)   " + 
			"     AND (:location IS NULL OR c.loc_id=:location)  " + 
			"     AND (:complainNo IS NULL OR c.complain_no=:complainNo)  " + 
			"     AND (:scrutinyStatus IS NULL OR if( " +
	        "    (select count(1) from tb_dm_complain_scrutiny where complain_no=c.complain_no and complain_scrutiny_status='Rejected' and orgid=c.orgid) > 0,'Rejected', " +
		    "    if((select count(1) from tb_dm_complain_scrutiny where complain_no=c.complain_no and complain_scrutiny_status='Pending' and orgid=c.orgid) > 0,'Pending', " +
			"    if((select count(1) from tb_dm_complain_scrutiny where complain_no=c.complain_no and complain_scrutiny_status='Forward' and orgid=c.orgid) > 0, " +
		     
		    "   if((select count(1) from tb_dm_complain_scrutiny where complain_no=c.complain_no and complain_scrutiny_status='Approved' and orgid=c.orgid) > 0,'Approved','Pending'), " +
		     
		    "   if((select count(1) from tb_dm_complain_scrutiny where complain_no=c.complain_no and complain_scrutiny_status='Approved' and orgid=c.orgid) > 0,'Approved',null)) " +
		    "   ))=:scrutinyStatus)  " + 
			"     GROUP BY   " + 
			"     c.complain_id,   " + 
			"     c.complain_no,   " + 
			"     c.cod_complaint_type1,   " + 
			"     c.cod_complaint_type2,   " + 
			"     c.complaint_description;", nativeQuery = true) 
	List<Object[]> getComplaintClosureSummaryList(@Param("complaintType1") Long complaintType1, @Param("complaintType2") Long complaintType2,
			@Param("location") Long location,
	       @Param("complainNo") String complainNo, @Param("orgid") Long orgId, @Param("scrutinyStatus") String scrutinyStatus  );

	@Query("select m.createdDate from ComplainRegister m where m.orgid=:orgid and m.complainId=:cmplntId ")
	public String validateDateTime(@Param("orgid") Long orgid, @Param("cmplntId") Long cmplntId);




}