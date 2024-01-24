package com.abm.mainet.workManagement.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.WorkEstimOverHeadDetails;
import com.abm.mainet.workManagement.domain.WorkEstimateMaster;
import com.abm.mainet.workManagement.domain.WorkEstimateMasterHistory;

@Repository
public interface WorkEstimateDao {

	/**
	 * used to save Work Estimate List
	 * 
	 * @param workEstimateList
	 * @param removeWorkIdList
	 */
	WorkEstimateMaster saveWorkEstimateList(WorkEstimateMaster entity);

	/**
	 * used to get Work Estimate By Work Id
	 * 
	 * @param workId
	 * @param orgid
	 * @return
	 */
	List<WorkEstimateMaster> getWorkEstimateByWorkId(Long workId, long orgid);

	/**
	 * used to set InActive Work Estimate By Work Id
	 * 
	 * @param workIDList
	 */
	void setInActiveWorkEstimateByWorkId(List<Long> workIDList);

	/**
	 * used to get All Active Distinct Work Id
	 * 
	 * @param orgid
	 * @return
	 */
	List<Long> getAllActiveDistinctWorkId(long orgid);

	/**
	 * used to get Estimation By Work Id And Type
	 * 
	 * @param workId
	 * @param workType
	 * @return
	 */
	List<WorkEstimateMaster> getEstimationByWorkIdAndType(Long workId, String workType);

	/**
	 * used to calculate Total Estimated Amount By Work Id
	 * 
	 * @param workEstemateId
	 * @return
	 */
	BigDecimal calculateTotalEstimatedAmountByWorkId(Long workEstemateId);

	/**
	 * use to get Rate Analysis List By Estimate Id
	 * 
	 * @param workEstemateId
	 * @param flag
	 * @return
	 */
	List<WorkEstimateMaster> getRateAnalysisListByEstimateId(Long workEstemateId, String flag);

	/**
	 * used to get Child Rate Analysis List By Material Id
	 * 
	 * @param materialId
	 * @param workId
	 * @return
	 */
	List<WorkEstimateMaster> getChildRateAnalysisListByMaterialId(Long materialId, Long workId);

	/**
	 * used to save OverHead List
	 * 
	 * @param entity
	 */
	void saveOverHeadList(WorkEstimOverHeadDetails entity);

	/**
	 * used to get Estimate OverHead Details By Work Id
	 * 
	 * @param workId
	 * @return
	 */
	List<WorkEstimOverHeadDetails> getEstimateOverHeadDetByWorkId(Long workId);

	/**
	 * used to update Deleted Flag
	 * 
	 * @param removeWorkIdList
	 */
	void updateDeletedFlag(List<Long> removeWorkIdList);

	/**
	 * used to update Work Esimate Lbh Qunatity
	 * 
	 * @param measurementWorkId
	 * @param totalAmount
	 */
	void updateWorkEsimateLbhQunatity(Long measurementWorkId, BigDecimal totalAmount);

	/**
	 * used to update Deleted Flag For OverHeads
	 * 
	 * @param overHeadRemoveById
	 */
	void updateDeletedFlagForOverHeads(List<Long> overHeadRemoveById);

	/**
	 * used to get all estimates which are used in tendering process
	 * 
	 * @param workId
	 * @return
	 */
	List<WorkEstimateMaster> getAllTenderEstimates(List<Long> workId);

	/**
	 * used to get all rate Type for measurement book by estimate measurement id
	 * 
	 * @param estmiateId
	 * @return
	 */
	List<WorkEstimateMaster> getAllRateTypeMBByEstimateNo(Long estmiateId);

	/**
	 * used to update work estimation number by work Id
	 * 
	 * @param workId
	 * @param mbNumber
	 */
	void updateWorkEstimateNo(Long workId, String mbNumber);

	/**
	 * used to update actual amount and quantity in work estimation table
	 * 
	 * @param workEstemateId
	 * @param workActualAmt
	 * @param workActualQty
	 */
	void updateRateValues(Long workEstemateId, BigDecimal workActualAmt, BigDecimal workActualQty);

	/**
	 * used to get change log data
	 * 
	 * @param workEstemateId
	 * @param flag
	 * @return
	 */
	List<WorkEstimateMasterHistory> getAuditRateByEstimateId(Long workEstemateId);

	/**
	 * used to update work estimation number by work Id
	 * 
	 * @param workId
	 * @param mbNumber
	 */
	void updateContractId(Long workId, Long contId, Long empId);

	/**
	 * get All Revised Contarct Estimate Details By Contrcat Id (workEstimateType
	 * -SOR->'S',NON-SOR->'N','B'->Bill of Quantity :for all set workEstimateType A
	 * )
	 * 
	 * @param contractId
	 * @param orgId
	 * @param workEstimateType
	 * @return
	 */
	List<WorkEstimateMaster> getAllRevisedContarctEstimateDetailsByContrcatId(Long contractId, Long orgId,
			String workEstimateType);

	/**
	 * used to get Previous Estimate By Contract Id
	 * 
	 * @param contractId
	 * @param orgId
	 * @param workEstimateType
	 * @return
	 */
	List<WorkEstimateMaster> getPreviousEstimateByContractId(Long contractId, Long orgId, String workEstimateType);

	/**
	 * used to update Parent Work Estimation Amount
	 * 
	 * @param workEId
	 * @param amount
	 */
	void updateParentWorkEstimationAmount(Long workEId, BigDecimal amount);

	/**
	 * used to get So rAnd NonSor Amount by work id
	 * 
	 * @param workId
	 * @return
	 */
	BigDecimal getSorAndNonSorAmount(Long workId);

	/**
	 * used to calculate Total Estimated Amount By Contract Id
	 * 
	 * @param contId
	 * @param orgId
	 * @return
	 */
	BigDecimal calculateTotalEstimatedAmountByContId(Long contId, Long orgId);

	/**
	 * used to find Work Estimate No By Work Id
	 * 
	 * @param workId
	 * @param orgId
	 * @return
	 */
	String findWorkEstimateNoByWorkId(Long workId, Long orgId);

	/**
	 * used to get overhead amount by work Id
	 * 
	 * @param workId
	 * @return
	 */
	BigDecimal getOverheadAmount(Long workId);
	
	/**
	 * used to get WorkEstimateMaster by workEstimate Id
	 * @param orgId
	 * @param workEstimateId
	 * @return WorkEstimateMasterDto
	 */
	WorkEstimateMaster findById(Long orgId,Long workEstimateId);

	/**
	 * used to update commulative Quantity
	 * @param measurementWorkId
	 * @param utlQuantity
	 */
	void updateWorkEsimateLbhUtlQunatity(Long measurementWorkId, BigDecimal utlQuantity);

}
