package com.abm.mainet.workManagement.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.workManagement.dto.MbOverHeadDetDto;
import com.abm.mainet.workManagement.dto.MeasurementBookCheckListDto;
import com.abm.mainet.workManagement.dto.MeasurementBookDetailsDto;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;

public interface MeasurementBookService {

	/**
	 * use to save ,update and delete measurement book
	 * 
	 * @param masterDto
	 * @param deletedMbId
	 */
	void saveAndUpdateMB(MeasurementBookMasterDto masterDto, List<Long> deletedMbdId);

	/**
	 * used to get measuerment Book With Details
	 * 
	 * @param MbId
	 * @return measuerment Book With Details
	 */
	MeasurementBookMasterDto getMBById(Long mbId);

	/**
	 * used to get measuerment Book With Details
	 * 
	 * @param workId
	 * @return measuerment Book With Details
	 */
	MeasurementBookMasterDto getMBByWorkOrderId(Long workId, String flag);

	/**
	 * used to get List of Measurement Book Record based on Organization Id
	 * 
	 * @param orgId
	 * @return List of Measurement Book
	 */
	List<MeasurementBookMasterDto> getAllMBListByOrgId(Long orgId);

	/**
	 * used to get all Measurement Book Details by MB ID
	 * 
	 * @param mbId
	 * @return
	 */
	List<MeasurementBookDetailsDto> getMBDetailsListByMBId(Long mbId);

	/**
	 * used to get MB DEtails By MB details ID
	 * 
	 * @param mbDId
	 * @return MB Details
	 */
	MeasurementBookDetailsDto getMBDetailsByDetailsId(Long mbDId);

	/**
	 * used to save MB rate analysis List
	 * 
	 * @param bookDetailsDto
	 */
	void saveMbRateDetails(List<MeasurementBookDetailsDto> bookDetailsDto, List<Long> deletedRatedId);

	/**
	 * used to generate and update MB number
	 * 
	 * @param workOrderNo
	 * @param orgId
	 * @param deptId
	 * @return
	 */
	String generateAndUpdateMbNumber(Long workOrderNo, Long orgId, Long deptId);

	/**
	 * used to get measuerment Book With Details
	 * 
	 * @param mbPId
	 * @return measuerment Book With Details
	 */
	List<MeasurementBookDetailsDto> getMBByMbdetParentId(Long mbPId);

	/**
	 * used to get measuerment Book With Details if present
	 * 
	 * @param workId
	 * @return measuerment Book With Details
	 */
	MeasurementBookMasterDto checkMBByWorkOrderId(Long workId);

	void deleteEnclosureFileById(List<Long> enclosureRemoveById, Long empId);

	/**
	 * used to get MB DEtails By MB Estimate Id
	 * 
	 * @param estimateId
	 * @param flag
	 * @return MB Details
	 */
	MeasurementBookDetailsDto getMBDetailsByEstimateId(Long estimateId, Long mbDetId, String flag);

	/**
	 * used to get MB Master by MB Number and OrgId
	 * 
	 * @param workMBCode
	 * @param currentOrgId
	 * @return
	 */
	MeasurementBookMasterDto getWorkMbByMBCode(String workMBCode, Long currentOrgId);

	/**
	 * used to find Abstract Sheet Report
	 * 
	 * @param workMbId
	 * @param orgId
	 * @return
	 */
	List<MeasurementBookMasterDto> findAbstractSheetReport(Long workMbId);

	/**
	 * used to get Log Details by mb detail Parent Id
	 * 
	 * @param mbParentId
	 * @return
	 */
	List<MeasurementBookDetailsDto> getAuditLogDetails(Long mbParentId);

	/**
	 * used to update total lbh quanity into mb master table
	 * 
	 * @param quantity
	 * @param workMbId
	 */
	void updateLbhQuantityByMbId(BigDecimal quantity, Long workMbDId);

	/**
	 * update total Mb amount
	 * 
	 * @param amount
	 * @param workMbId
	 */
	void updateTotalMbAmountByMbId(BigDecimal amount, Long workMbId);

	/**
	 * update Bill Number
	 * 
	 * @param billNumber
	 * @param workMbId
	 */
	void updateBillNumberByMbId(String billNumber, Long workMbId);

	/**
	 * get Work Id With MbNumber
	 * 
	 * @param workMbNo
	 * @param orgId
	 * @return workId
	 */
	Long getWorkIdWithMbNumber(String workMbNo, Long orgId);

	/**
	 * Use to update MeasureMent Mode
	 * 
	 * @param workMbId
	 * @param flag
	 */
	void updateMeasureMentMode(Long workMbId, String flag);

	/**
	 * used to filter Measurement Book Data
	 * 
	 * @param workOrderId
	 * @return List<MeasurementBookMasterDto>
	 */
	List<MeasurementBookMasterDto> filterMeasurementBookData(Long workOrderId, String flag, Long orgId);

	/**
	 * used to save MB rate analysis List
	 * 
	 * @param bookDetailsDto
	 */
	void saveMbNonSorDetails(List<MeasurementBookDetailsDto> bookDetailsDto);

	/**
	 * used to get Cummulative Qunatity Details By Work Order Id
	 * 
	 * @param workOrderId
	 * @param runningMbId
	 * @return
	 */
	List<Object[]> getCummulativeDetailsByWorkOrderId(Long workOrderId, Long runningMbId);

	List<EmployeeBean> getAssignedEmpDetailByEmpIdList(List<Long> empId, Long orgId);

	/* *//**
			 * update List Measure Ment Mode
			 * 
			 * @param workMbId
			 * @param flag
			 *//*
				 * void updateListMeasureMentMode(List<Long> workMbId, String flag);
				 */

	/**
	 * get All Mb Deatils By List Of MBId
	 * 
	 * @param workMbId
	 * @param orgId
	 * @return
	 */
	List<MeasurementBookMasterDto> getAllMbDeatilsByListOfMBId(List<Long> workMbId, Long orgId);

	/**
	 * Use to get Previous MeasurementBook Utilized Quantity
	 * 
	 * @param mbId
	 * @param WorkOrderId
	 * @returnMap<Long,Long>
	 */
	Map<Long, Long> getPreviousMbUtilzedQuantity(Long mbId, Long workOrderId);

	/**
	 * Use to get Previous Non-Sor Utilized Quantity
	 * 
	 * @param mbId
	 * @param workOrderId
	 * @return Map<Long,Long>
	 */
	Map<Long, BigDecimal> getPreviousNonSorUtilzedQuantity(Long mbId, Long workOrderId);

	/**
	 * Use to get Previous Direct Utilized Quantity
	 * 
	 * @param mbId
	 * @param workOrderId
	 * @return Map<Long,Long>
	 */
	Map<Long, Long> getPreviousDirectUtilzedQuantity(Long mbId, Long workOrderId);

	/**
	 * Use to get Previous Direct Utilized Quantity
	 * 
	 * @param mbId
	 * @param workOrderId
	 * @return Map<Long,Long>
	 */
	Set<File> getUploadedFileList(List<AttachDocs> attachDocs, FileNetApplicationClient fileNetApplicationClient);

	void deleteMbDocuments(List<AttachDocs> attachDocs, Long empId);

	List<MeasurementBookMasterDto> getAllMbDeatilsBySearch(Long workOrderId, String flag, String mbNo, Long workId,
			Long vendorId, Long orgId);

	/**
	 * use to save ,update and delete measurement book CheckList Data
	 * 
	 * @param masterDto
	 * @param deletedMbcdId
	 */
	void saveAndUpdateMBCheckList(MeasurementBookCheckListDto masterDto, List<Long> deletedMbcdId);

	/**
	 * used to get List of Measurement Book CheckList Data based on Organization Id
	 * 
	 * @param orgId
	 * @return MeasurementBookCheckListDto
	 */
	MeasurementBookCheckListDto getMBCheckListByMBAndOrgId(Long mbId, Long orgId);

	BigDecimal getTotalMbAmountByWorkId(Long workId, Long orgId);

	/**
	 * To save and Update Legacy Measurement Book Data
	 * 
	 * @param bookMas
	 */
	void saveLegacyData(List<MeasurementBookMasterDto> bookMas, List<DocumentDetailsVO> attachments);

	/**
	 * To save and Update Legacy Measurement Book Data
	 * 
	 * @param bookMas
	 */
	void updateLegacyData(List<MeasurementBookMasterDto> bookMas, List<DocumentDetailsVO> attachments,
			List<AttachDocs> attachDocs);

	BigDecimal getTotalMbAmtByWorkOrderId(Long workId, Long orgId);


	void saveOverHeadList(List<MbOverHeadDetDto> mbeOverHeadDetDtoList, List<Long> overHeadRemoveById, BigDecimal totalMbAmount, Long long1);
	
	BigDecimal getTotalMbAmtByMbId(Long mbId,Long orgId);
	
	List<MbOverHeadDetDto> getmbOverDetByMbMasId(Long mbId,Long orgId);
	
	List<MbOverHeadDetDto> getmbOverDetByEstimationId(Long ovId,Long orgId);

	void updateTotalEstimateAmounts(BigDecimal amt, Long mbId);

	void updateTotalMbAmountByMbIdDet(BigDecimal amount, Long workMbId);

	void updateAmountNonSor(List<MeasurementBookDetailsDto> bookDetailsDto,List<Long> mbdId);

	void updateTotalAmountByMbId(BigDecimal workActualAmt, Long mbDetId);

	List<MeasurementBookMasterDto> getAllMBListSummaryByOrgId(Long orgId);
	
}
