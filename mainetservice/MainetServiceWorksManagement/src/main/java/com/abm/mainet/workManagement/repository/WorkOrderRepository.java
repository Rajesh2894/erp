package com.abm.mainet.workManagement.repository;

import java.lang.annotation.Repeatable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.WorkOrder;
import com.abm.mainet.workManagement.dto.WorkOrderDto;

@Repository
public interface WorkOrderRepository extends CrudRepository<WorkOrder, Long>, WorkOrderRepositoryCustoms {

	/**
	 * used to in activate work order terms
	 * 
	 * @param inactiveTermsIds
	 */
	@Modifying
	@Query("update WorkOrderTerms ta set ta.active ='N' where ta.termsId in (:termsId)")
	void deleteMBDetailsById(@Param("termsId") List<Long> inactiveTermsIds);

	/**
	 * use to update work order number
	 * 
	 * @param workOrderNo
	 * @param mbNumber
	 */
	@Query("Update WorkOrder wo set wo.workOrderNo =:workOrderNo where wo.workId =:workId")
	void updateWorkOrderNumber(@Param("workId") Long workOrderNo, @Param("workOrderNo") String mbNumber);

	/**
	 * get All work order list based on orgId.
	 * 
	 * @param orgId
	 * @return List<WorkOrder>
	 */
	List<WorkOrder> findByOrgId(Long orgId);

	/**
	 * find All Work Order Id And CNo
	 * 
	 * @param orgId
	 * @return
	 */
	@Query("select wo.workId,wo.workOrderNo from WorkOrder wo where wo.orgId=:orgId")
	List<Object[]> findAllWorkOrderIdAndCNo(@Param("orgId") Long orgId);

	/**
	 * used to get Work Order By contract number
	 * 
	 * @param contId
	 * @param orgId
	 * @return
	 */
	@Query("select wo from WorkOrder wo where wo.contractMastEntity.contId=:contId and wo.orgId=:orgId ")
	List<WorkOrder> getWorkOrderByOrderNumber(@Param("contId") Long contId, @Param("orgId") Long orgId);

	/**
	 * Select All contract Details By OrgId
	 * 
	 * @param orgId
	 * @return
	 */

	@Query("Select a.contId,a.contNo,a.contDate , a.contDept,b.contFromDate,b.contToDate, c.contp1Name, "
			+ "d.contp2Name,b.contAmount,b.contToPeriod,g.vmVendoradd,g.emailId, g.mobileNo ,f.tenderNo,f.tenderDate,b.contToPeriodUnit,g.vmVendorname  from "
			+ "ContractMastEntity a ,ContractDetailEntity b ,ContractPart1DetailEntity c, "
			+ "ContractPart2DetailEntity d ,TenderWorkEntity e , TenderMasterEntity f , TbAcVendormasterEntity g "
			+ "WHERE a.contId=b.contId.contId  and a.contId=c.contId.contId and a.contId=d.contId.contId "
			+ "and d.contId.contId=e.contractId and e.tenderMasEntity.tndId=f.tndId "
			+ "and d.vmVendorid=g.vmVendorid and b.contdActive='Y' and c.contp1Type='U' "
			+ "and d.contp2Type='V' and d.contp2Primary='Y' and d.contvActive='Y' and a.orgId= :orgId "
			+ "and a.contId not in (select p.contractMastEntity.contId from WorkOrder p where p.orgId= :orgId) ")
	List<Object[]> findAllContractDetailsByOrgId(@Param("orgId") Long orgId);

	/**
	 * get All Contract Details In WorkOrder By OrgId
	 * 
	 * @param orgId
	 * @return
	 */

      //Defect #85537->modified 
	@Query("Select a.contId,a.contNo,a.contDate , c.dpDeptid,b.contFromDate,b.contToDate, c.contp1Name, "
			+ "d.contp2Name,b.contAmount,b.contToPeriod,g.vmVendoradd,g.emailId, g.mobileNo, f.tenderNo,f.tenderDate,b.contToPeriodUnit,g.vmVendorname  from "
			+ "ContractMastEntity a ,ContractDetailEntity b ,ContractPart1DetailEntity c, "
			+ "ContractPart2DetailEntity d ,TenderWorkEntity e , TenderMasterEntity f , TbAcVendormasterEntity g "
			+ "WHERE a.contId=b.contId.contId  and a.contId=c.contId.contId and a.contId=d.contId.contId "
			+ "and d.contId.contId=e.contractId and e.tenderMasEntity.tndId=f.tndId "
			+ "and d.vmVendorid=g.vmVendorid and b.contdActive='Y' and c.contp1Type='U' "
			+ "and d.contp2Type='V' and d.contp2Primary='Y' and d.contvActive='Y' and e.workDefinationEntity.workStatus <> 'C' and a.orgId= :orgId "
			+ "and a.contId in (select p.contractMastEntity.contId from WorkOrder p where p.orgId= :orgId) ")
	List<Object[]> getAllContractDetailsInWorkOrderByOrgId(@Param("orgId") Long orgId);

	/**
	 * get All Summary Contract Details
	 * 
	 * @param orgId
	 * @return
	 */
	@Query("Select a.contId,a.contNo,a.contDate , c.dpDeptid,b.contFromDate,b.contToDate, c.contp1Name, "
			+ "g.vmVendorname,b.contAmount,g.vmVendoradd,g.emailId, g.mobileNo ,wo.workOrderStatus  from "
			+ "ContractMastEntity a ,ContractDetailEntity b ,ContractPart1DetailEntity c, "
			+ "ContractPart2DetailEntity d ,TenderWorkEntity e , TenderMasterEntity f , TbAcVendormasterEntity g ,WorkOrder wo "
			+ "WHERE a.contId=b.contId.contId  and a.contId=c.contId.contId and a.contId=d.contId.contId "
			+ "and d.contId.contId=e.contractId and e.tenderMasEntity.tndId=f.tndId "
			+ "and d.vmVendorid=g.vmVendorid and b.contdActive='Y' and c.contp1Type='U' "
			+ "and d.contp2Type='V' and d.contp2Primary='Y' and d.contvActive='Y' and a.orgId= :orgId "
			+ "and a.contId = wo.contractMastEntity.contId "
			+ "and a.contId in (select w.contractId from WorkEstimateMaster w where w.orgId= :orgId "
			+ "and w.workeReviseFlag in ('N','E'))")
	List<Object[]> getAllSummaryContractDetails(@Param("orgId") Long orgId);

	/**
	 * update Contract Variation Status
	 * 
	 * @param contId
	 * @param flag
	 */
	@Modifying
	@Query("UPDATE WorkOrder wo SET wo.workOrderStatus =:flag  where wo.contractMastEntity.contId =:contId")
	void updateContractVariationStatus(@Param("contId") Long contId, @Param("flag") String flag);

	/**
	 * used to get Work Order By contract Id
	 * 
	 * @param contId
	 * @param orgId
	 * @return
	 */
	@Query("SELECT wo from WorkOrder wo where wo.contractMastEntity.contId=:contId and wo.orgId=:orgId ")
	WorkOrder fetchWorkOrderByContId(@Param("contId") Long contId, @Param("orgId") Long orgId);

	/**
	 * get Vendor Details Against Which Work order Generated
	 * 
	 * @param orgId
	 * @return
	 */
	@Query("select distinct c.vmVendorid,c.vmVendorname from WorkOrder a,TenderWorkEntity b,TbAcVendormasterEntity c where a.contractMastEntity.contId=b.contractId and b.vendorMaster.vmVendorid=c.vmVendorid and a.orgId=:orgId")
	List<Object[]> findAllWorkOrderGeneratedVendorDetail(@Param("orgId") Long orgId);

	/**
	 * Find All Legacy Work Order
	 * 
	 * @param legacyId
	 * @param orgId
	 * @return
	 */
	@Query("SELECT a FROM WorkOrder a,TenderWorkEntity b,WorkDefinationEntity c where a.contractMastEntity.contId=b.contractId and b.workDefinationEntity.workId=c.workId and c.orgId = :orgId and c.workType= :legacyId")
	List<WorkOrder> findAllLegacyWorkOrder(@Param("legacyId") Long legacyId, @Param("orgId") Long orgId);

	/**
	 * Find All Work Order other Than Legacy
	 * 
	 * @param legacyId
	 * @param orgId
	 * @return
	 */
	@Query("SELECT a FROM WorkOrder a,TenderWorkEntity b,WorkDefinationEntity c where a.contractMastEntity.contId=b.contractId and b.workDefinationEntity.workId=c.workId and c.orgId = :orgId and c.workType !=:legacyId")
	List<WorkOrder> findAllNonLegacyWorkOrder(@Param("legacyId") Long legacyId, @Param("orgId") Long orgId);
	
	//added by sadik
	@Query("SELECT wo from WorkOrder wo  where wo.workOrderNo =:workOrderNo")
	WorkOrder findWorkOrderbyOrderNo(@Param("workOrderNo") String mbNumber);
	
	 @Query(value ="SELECT DISTINCT wo.WORKOR_ID, wo.WORKOR_NO, wo.WORKOR_DATE, wo.CONT_ID, wo.TND_ID, wo.WORKOR_AGFROMDATE,\r\n" +
	            "wo.WORKOR_AGTODATE, wo.WORKOR_STARTDATE, wo.WORKOR_DEFECTLIABILITYPER, wo.WORK_ASSIGNEE,\r\n" +
	            "wo.WORK_ASSIGNEEDATE, wo.WORKOR_STATUS, wo.ORGID  , DP.DP_DEPTDESC , CM.CONT_NO \r\n" + 
				"FROM tb_wms_workdefination A\r\n" +
				"INNER JOIN TB_WMS_WORKDEFINATION_WARDZONE_DET B ON  a.WORK_ID = B.WORK_ID\r\n" +
				"INNER JOIN tb_wms_tender_work TW ON  TW.WORK_ID = A.WORK_ID\r\n" +
				"INNER JOIN tb_contract_mast CM ON  CM.CONT_ID=TW.CONT_ID\r\n" +
				"INNER JOIN tb_wms_workeorder WO ON WO.CONT_ID=CM.CONT_ID\r\n" +
				"INNER JOIN tb_department DP ON DP.DP_DEPTID = A.DP_DEPTID\r\n" +
				"WHERE A.ORGID=:orgId \r\n" +
				"AND COALESCE(b.COD_ID1 ,0)=(case when COALESCE(:codId1,0)=0 then COALESCE(b.COD_ID1  ,0) else COALESCE(:codId1,0) end)\r\n" +
				"AND COALESCE(b.COD_ID2 ,0)=(case when COALESCE(:codId2,0)=0 then COALESCE(b.COD_ID2  ,0) else COALESCE(:codId2,0) end)\r\n" +
				"AND COALESCE(b.COD_ID3 ,0)=(case when COALESCE(:codId3,0)=0 then COALESCE(b.COD_ID3  ,0) else COALESCE(:codId3,0) end)\r\n" +
				"AND COALESCE(A.DP_DEPTID ,0)=(case when COALESCE(:dpDeptId,0)=0 then COALESCE(A.DP_DEPTID  ,0) else COALESCE(:dpDeptId,0) end)\r\n" + 
				"AND COALESCE(wo.WORKOR_NO ,'X')=(case when COALESCE(:workOrderNo,'X')='X' then COALESCE(wo.WORKOR_NO  ,'X') else COALESCE(:workOrderNo,'X') end)\r\n" + 
				"AND COALESCE(date_format(wo.WORKOR_STARTDATE,'%y %m %d') ,'X')=(case when COALESCE(:workStipulatedDate,'X')='X' then COALESCE(date_format(wo.WORKOR_STARTDATE,'%y %m %d')  ,'X') else COALESCE(date_format(:workStipulatedDate,'%y %m %d'),'X') end)\r\n" + 
		 		"\r\n" + 
		 		"", nativeQuery = true)
		 
		 public List<Object[]> getFilterWorkOrderGeneration(@Param("workOrderNo") String workOrderNo, @Param("workStipulatedDate") Date workStipulatedDate,
		            @Param("orgId") Long orgId, @Param("codId1") Long codId1, @Param("codId2") Long codId2, @Param("codId3") Long codId3, @Param("dpDeptId") Long dpDeptId);
		 
		
		 @Query(value ="SELECT DISTINCT wo.WORKOR_ID, wo.WORKOR_NO, wo.WORKOR_DATE, wo.CONT_ID, wo.TND_ID, wo.WORKOR_AGFROMDATE,\r\n" +
		            "wo.WORKOR_AGTODATE, wo.WORKOR_STARTDATE, wo.WORKOR_DEFECTLIABILITYPER, wo.WORK_ASSIGNEE,\r\n" +
		            "wo.WORK_ASSIGNEEDATE, wo.WORKOR_STATUS, wo.ORGID  , DP.DP_DEPTDESC , CM.CONT_NO \r\n" + 
					"FROM tb_wms_workdefination A\r\n" +
					"INNER JOIN TB_WMS_WORKDEFINATION_WARDZONE_DET B ON  a.WORK_ID = B.WORK_ID\r\n" +
					"INNER JOIN tb_wms_tender_work TW ON  TW.WORK_ID = A.WORK_ID\r\n" +
					"INNER JOIN tb_contract_mast CM ON  CM.CONT_ID=TW.CONT_ID\r\n" +
					"INNER JOIN tb_wms_workeorder WO ON WO.CONT_ID=CM.CONT_ID\r\n" +
					"INNER JOIN tb_department DP ON DP.DP_DEPTID = A.DP_DEPTID\r\n" +
					"WHERE A.ORGID=:orgId \r\n" +
					"AND COALESCE(b.COD_ID1 ,0)=(case when COALESCE(:codId1,0)=0 then COALESCE(b.COD_ID1  ,0) else COALESCE(:codId1,0) end)\r\n" +
					"AND COALESCE(b.COD_ID2 ,0)=(case when COALESCE(:codId2,0)=0 then COALESCE(b.COD_ID2  ,0) else COALESCE(:codId2,0) end)\r\n" +
					"AND COALESCE(b.COD_ID3 ,0)=(case when COALESCE(:codId3,0)=0 then COALESCE(b.COD_ID3  ,0) else COALESCE(:codId3,0) end)\r\n" +
					"AND COALESCE(A.DP_DEPTID ,0)=(case when COALESCE(:dpDeptId,0)=0 then COALESCE(A.DP_DEPTID  ,0) else COALESCE(:dpDeptId,0) end)\r\n" + 
			 		"\r\n" + 
			 		"", nativeQuery = true)
		 
		 public List<Object[]> getFilterWorkOrderGeneration1(@Param("orgId") Long orgId, @Param("codId1") Long codId1,
				 @Param("codId2") Long codId2, @Param("codId3") Long codId3, @Param("dpDeptId") Long dpDeptId);
		 
		 @Query(value ="SELECT DISTINCT wo.WORKOR_ID, wo.WORKOR_NO, wo.WORKOR_DATE, wo.CONT_ID, wo.TND_ID, wo.WORKOR_AGFROMDATE,\r\n" +
		            "wo.WORKOR_AGTODATE, wo.WORKOR_STARTDATE, wo.WORKOR_DEFECTLIABILITYPER, wo.WORK_ASSIGNEE,\r\n" +
		            "wo.WORK_ASSIGNEEDATE, wo.WORKOR_STATUS, wo.ORGID  , DP.DP_DEPTDESC , CM.CONT_NO \r\n" + 
					"FROM tb_wms_workdefination A\r\n" +
					"INNER JOIN TB_WMS_WORKDEFINATION_WARDZONE_DET B ON  a.WORK_ID = B.WORK_ID\r\n" +
					"INNER JOIN tb_wms_tender_work TW ON  TW.WORK_ID = A.WORK_ID\r\n" +
					"INNER JOIN tb_contract_mast CM ON  CM.CONT_ID=TW.CONT_ID\r\n" +
					"INNER JOIN tb_wms_workeorder WO ON WO.CONT_ID=CM.CONT_ID\r\n" +
					"INNER JOIN tb_department DP ON DP.DP_DEPTID = A.DP_DEPTID\r\n" +
					"WHERE A.ORGID=:orgId \r\n" +
					"AND COALESCE(b.COD_ID1 ,0)=(case when COALESCE(:codId1,0)=0 then COALESCE(b.COD_ID1  ,0) else COALESCE(:codId1,0) end)\r\n" +
					"AND COALESCE(b.COD_ID2 ,0)=(case when COALESCE(:codId2,0)=0 then COALESCE(b.COD_ID2  ,0) else COALESCE(:codId2,0) end)\r\n" +
					"AND COALESCE(b.COD_ID3 ,0)=(case when COALESCE(:codId3,0)=0 then COALESCE(b.COD_ID3  ,0) else COALESCE(:codId3,0) end)\r\n" +
					"AND COALESCE(A.DP_DEPTID ,0)=(case when COALESCE(:dpDeptId,0)=0 then COALESCE(A.DP_DEPTID  ,0) else COALESCE(:dpDeptId,0) end)\r\n" + 
					"AND COALESCE(wo.WORKOR_NO ,'X')=(case when COALESCE(:workOrderNo,'X')='X' then COALESCE(wo.WORKOR_NO  ,'X') else COALESCE(:workOrderNo,'X') end)\r\n" + 
			 		"\r\n" + 
			 		"", nativeQuery = true)
		 
		 public List<Object[]> getFilterWorkOrderGeneration2(@Param("workOrderNo") String workOrderNo,
		            @Param("orgId") Long orgId, @Param("codId1") Long codId1, @Param("codId2") Long codId2, @Param("codId3") Long codId3, @Param("dpDeptId") Long dpDeptId);
		 
		 @Query(value ="SELECT DISTINCT wo.WORKOR_ID, wo.WORKOR_NO, wo.WORKOR_DATE, wo.CONT_ID, wo.TND_ID, wo.WORKOR_AGFROMDATE,\r\n" +
		            "wo.WORKOR_AGTODATE, wo.WORKOR_STARTDATE, wo.WORKOR_DEFECTLIABILITYPER, wo.WORK_ASSIGNEE,\r\n" +
		            "wo.WORK_ASSIGNEEDATE, wo.WORKOR_STATUS, wo.ORGID  , DP.DP_DEPTDESC , CM.CONT_NO \r\n" + 
					"FROM tb_wms_workdefination A\r\n" +
					"INNER JOIN TB_WMS_WORKDEFINATION_WARDZONE_DET B ON  a.WORK_ID = B.WORK_ID\r\n" +
					"INNER JOIN tb_wms_tender_work TW ON  TW.WORK_ID = A.WORK_ID\r\n" +
					"INNER JOIN tb_contract_mast CM ON  CM.CONT_ID=TW.CONT_ID\r\n" +
					"INNER JOIN tb_wms_workeorder WO ON WO.CONT_ID=CM.CONT_ID\r\n" +
					"INNER JOIN tb_department DP ON DP.DP_DEPTID = A.DP_DEPTID\r\n" +
					"WHERE A.ORGID=:orgId \r\n" +
					"AND COALESCE(b.COD_ID1 ,0)=(case when COALESCE(:codId1,0)=0 then COALESCE(b.COD_ID1  ,0) else COALESCE(:codId1,0) end)\r\n" +
					"AND COALESCE(b.COD_ID2 ,0)=(case when COALESCE(:codId2,0)=0 then COALESCE(b.COD_ID2  ,0) else COALESCE(:codId2,0) end)\r\n" +
					"AND COALESCE(b.COD_ID3 ,0)=(case when COALESCE(:codId3,0)=0 then COALESCE(b.COD_ID3  ,0) else COALESCE(:codId3,0) end)\r\n" +
					"AND COALESCE(A.DP_DEPTID ,0)=(case when COALESCE(:dpDeptId,0)=0 then COALESCE(A.DP_DEPTID  ,0) else COALESCE(:dpDeptId,0) end)\r\n" + 
					"AND COALESCE(date_format(wo.WORKOR_STARTDATE,'%y %m %d') ,'X')=(case when COALESCE(:workStipulatedDate,'X')='X' then COALESCE(date_format(wo.WORKOR_STARTDATE,'%y %m %d')  ,'X') else COALESCE(date_format(:workStipulatedDate,'%y %m %d'),'X') end)\r\n" + 
			 		"\r\n" + 
			 		"", nativeQuery = true)

		    public List<Object[]> getFilterWorkOrderGeneration3(@Param("workStipulatedDate") Date workStipulatedDate,
		            @Param("orgId") Long orgId, @Param("codId1") Long codId1, @Param("codId2") Long codId2, @Param("codId3") Long codId3, @Param("dpDeptId") Long dpDeptId);
		    

		    @Query("select wo from WorkOrder wo where wo.orgId=:orgId")
			List<WorkOrder> findAllWorkOrder(@Param("orgId") Long orgId);
		    	
}