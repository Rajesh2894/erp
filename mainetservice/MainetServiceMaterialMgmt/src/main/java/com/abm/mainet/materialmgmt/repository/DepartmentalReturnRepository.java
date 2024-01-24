package com.abm.mainet.materialmgmt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.abm.mainet.materialmgmt.domain.DeptReturnEntity;

@Repository
public interface DepartmentalReturnRepository extends JpaRepository<DeptReturnEntity, Long> {
	
	@Query("SELECT e.empname,e.empmname,e.emplname,e.empId"
			+ " FROM Employee e  WHERE e.organisation.orgid=:orgId and e.empId IN ( select distinct indenter from IndentProcessEntity where orgid=:orgId) and   e.isDeleted = '0' order by e.empname asc")
	List<Object[]> findAllEmpIntialInfoByOrg(@Param("orgId") Long orgId);
	
	
	@Query("select b.indentid,b.indentno from IndentProcessEntity b where b.indenter=:indenter  and  b.orgid=:orgid and b.status=:status order by b.indentid desc")
	public  List<Object[]> findIndentByEmpId(@Param("indenter") Long indenter,@Param("orgid") Long orgid,@Param("status") String status );

	
	@Query(value = "SELECT loc.LOC_NAME_ENG, m.storeid, s.storename, m.beneficiary,s.location FROM MM_INDENT m "
		    + "JOIN MM_STOREMASTER s ON  m.storeid=s.storeid "
		    + "JOIN TB_LOCATION_MAS loc ON s.location = loc.LOC_ID "
		    + "WHERE m.indentid = :indentid AND m.ORGID = :orgId", nativeQuery = true)
		public Object[] getStoreDetailsByIndentId(@Param("indentid") Long indentid, @Param("orgId") Long orgId);
	
	@Query("select b from DeptReturnEntity b where b.dreturnno=:dreturnno and b.orgid=:orgid")
	public DeptReturnEntity findByIndentReturnNo(@Param("dreturnno") String dreturnno, @Param("orgid") Long orgid);

	
	 
	@Modifying
	@Query("UPDATE DeptReturnEntity tm SET tm.status =:status , tm.wfFlag=:wfFlag WHERE tm.orgid =:orgId and tm.dreturnno =:dreturnno")
	void updateIndentReturnStatus(@Param("orgId") Long orgId, @Param("dreturnno") String dreturnno, @Param("status") Character status, 
			@Param("wfFlag") String wfFlag);
	
	@Modifying
	@Query("UPDATE DeptReturnEntityDetail tm SET tm.Status =:status WHERE tm.orgid =:orgId and tm.dreturnid.dreturnid =:dreturnno")
	void updateIndentItemReturnStatus(@Param("orgId") Long orgId, @Param("dreturnno") Long dreturnno, @Param("status") Character status);
	
	
	
	
	@Query(value = "select issue.indissuemid, issue.indentid, issue.itemid, issue.itemno, "
			+ " issue.issueqty,im.name, im.uom, IFNULL((SELECT SUM(IFNULL(retDet.returnqty, 0)) "
			+ " FROM mm_deptreturn_det retDet INNER JOIN mm_deptreturn ret ON ret.dreturnid = retDet.dreturnid "
			+ " WHERE retDet.ORGID = issue.ORGID AND retDet.itemid = issue.itemid  AND ret.indentid =issue.indentid "
			+ " and retDet.status =:returnStatus GROUP BY  ret.indentid, retDet.itemid, retDet.ORGID), 0) AS "
			+ " prev_received_quantity FROM MM_INDENT_ISSUE issue LEFT JOIN MM_ITEMMASTER im ON "
			+ " im.itemId = issue.itemid  WHERE issue.indentid=:indentid AND  issue.ORGID=:orgId HAVING prev_received_quantity < issue.issueqty" , nativeQuery = true)
	public List<Object[]> getIndentReturnDataByIndentId(@Param("returnStatus") Character returnStatus,
			 @Param("orgId") Long orgId, @Param("indentid") Long indentid);
	
	
	@Query("select r from DeptReturnEntity r where r.orgid=:orgid  and r.status !='Y' order by 1 desc") 
	public List<DeptReturnEntity> findByOrgIdForIndentReturnSummary( @Param("orgid") Long orgid);
	
	
	@Query("select b from DeptReturnEntity b where b.dreturnid=:dreturnid and b.orgid=:orgid")
	public DeptReturnEntity findByIndentReturnId(@Param("dreturnid") Long dreturnid, @Param("orgid") Long orgid);
	

	
	
	
}


