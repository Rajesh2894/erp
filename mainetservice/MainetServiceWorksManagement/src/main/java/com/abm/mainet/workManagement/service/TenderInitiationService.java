package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.workManagement.datamodel.WMSRateMaster;
import com.abm.mainet.workManagement.domain.BIDMasterEntity;
import com.abm.mainet.workManagement.dto.BidMasterDto;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;

/**
 * @author hiren.poriya
 * @Since 10-Apr-2018
 */
public interface TenderInitiationService {


	/**
     * it is used to create tender initiation details
     * 
     * @param initiationDto
     * @param attachments documents
     */
    TenderMasterDto createTenderInitiation(TenderMasterDto initiationDto, List<DocumentDetailsVO> attachments,
            RequestDTO requestDTO);

    /**
     * it is used to update tender initiation details.
     * 
     * @param initiationDto
     * @param attachments documents
     * @param removedWorkIds
     */
    void updateTenderInitiation(TenderMasterDto initiationDto, List<DocumentDetailsVO> attachments,
            RequestDTO requestDTO, List<Long> removedWorkIds, Long deletedFileId);

    /**
     * get prepared tender details by tndId
     * 
     * @param tndId
     * @return TenderMasterDto
     */
    TenderMasterDto getPreparedTenderDetails(Long tndId);

    /**
     * used to get all created tender.
     * 
     * @param orgId
     * @return List<TenderMasterDto>
     */
    List<TenderMasterDto> getAllTenders(Long orgId);

    /**
     * get All active projects object which used for tender initiation
     * 
     * @param orgId
     * @return project id, project description and project code.
     */
    List<Object[]> getAllActiveTenderProjects(Long orgId);

    /**
     * filter tender details based or search criteria
     * 
     * @param orgid
     * @param projId
     * @param initiationNo
     * @param initiationDate
     * @param tenderCategory
     * @param venderClassId
     * @return List<TenderMasterDto>
     */
    List<TenderMasterDto> searchTenderDetails(Long orgid, Long projId, String initiationNo, Date initiationDate,
            Long tenderCategory, String flag);

    /**
     * to delete prepared tender details(Hard Delete)
     * 
     * @param tndId : tender Id
     */
    void deleteTender(Long tndId);

    /**
     * this service is used to initiate tender details and generate initiation number of tender
     * 
     * @param tenderId
     * @param l
     * @return if successfully initiated than return 'Y' else return error message as result.
     */
    String initiateTender(Long tenderId, Long orgId);

    /**
     * save tender update details
     * 
     * @param initiationDto
     */
    void saveTenderUpdateDetails(TenderMasterDto initiationDto, Organisation org);

    /**
     * used to get all tenders which are initiated.
     * 
     * @param orgId
     * @return List<TenderMasterDto>
     */
    List<TenderMasterDto> getAllInitiatedTenders(Long orgId);

    /**
     * update tender work assignee
     * 
     * @param workId
     * @param assignee
     * @param assignedDate
     */
    void updateTenderWorkAssignee(List<Long> workId, Long assigneeId, Date assignedDate,
            List<Long> removedAssignedWorkId);

    /**
     * get tender master details by tender Id
     * 
     * @param tenderId
     * @return TenderMasterDto
     */
    TenderMasterDto getTenderDetailsByTenderId(Long tenderId);

    /**
     * used to generate LOA From tender details Id
     * 
     * @param tndId
     * @param orgid
     * @return
     */
    TenderWorkDto generateLOA(Long tndId, long orgid);

    /**
     * used to get Tender details by Loa number
     * 
     * @param loaNumber
     * @param orgid
     * @return
     */
    TenderWorkDto getTenderDetailsByLoaNumber(String loaNumber, long orgId);

    TenderWorkDto findWorkByWorkId(Long contId);

    void updateContractId(Long contId, Long workId, Long empId);

    TenderWorkDto findContractByWorkId(Long workId);

    List<TenderWorkDto> findTenderByVenderId(Long venderId);

    /**
     * find NIT And Pq Doc Form Details By WorkId and OrgId
     * @param WorkId
     * @param orgId
     * @return TenderWorkDto
     */
    TenderWorkDto findNITAndPqDocFormDetailsByWorkId(Long workId, Long orgId);

    /**
     * used to get EMD amount from BRMS rule sheet
     * @param orgId
     * @param tenderAmount
     * @return
     */
    WMSRateMaster getEmdAmountFromBrmsRule(Long orgId, BigDecimal tenderAmount);

    /**
     * update Tender Status
     * @param orgId
     * @param initiationNo
     * @param updateFlag
     */

    void updateTenderStatus(Long orgId, String initiationNo, String updateFlag);

    /**
     * get Tender Id By InitiationNumber
     * @param tenderInitiationNo
     * @param orgid
     */
    Long getTenderIdByInitiationNumber(String tenderInitiationNo, Long orgId);

    /**
     * find All Tender List
     * @param orgId
     * @return
     */
    List<TenderMasterDto> findAllTenderList(Long orgId);

    /**
     * get All Active Projects For TndServices
     * @param orgId
     * @return
     */
    List<Object[]> getAllActiveProjectsForTndServices(Long orgId);

    /**
     * To get Tender Fess From Brms Rule Sheet
     * @param orgid
     * @param estimatedAmount
     * @return
     */
    WMSRateMaster getTndFessFromBrmsRule(Long orgid, BigDecimal estimatedAmount);
    
    public boolean saveBIDDetail(BidMasterDto bidMasterDto);
    
    List<TenderMasterDto> searchTenderByTendernoAndDate(Long orgid, String tenderNo, Date tenderDate);
    
    List<BidMasterDto> getBidMAsterByTndId(Long orgId,Long tndId);
    
    BidMasterDto getBidMAsterByBIDId(Long orgId,Long bidId);
    
    BidMasterDto searchBIdDetByVendorIdandBidId(Long orgId,String bidId,Long vendorId, Long tndId);
    
    List<TenderMasterDto> getTenderOnProjId(Long projId, Long orgId);

	List<BidMasterDto> createBid(List<BidMasterDto> bidDtoList);

	List<BidMasterDto> getBidMAster(Long orgId, Long projId, String tndNo);

	boolean saveData(BidMasterDto bidDto);

	TenderMasterDto getTenderDetailsByTenderNo(String tenderInitiationNo, Long orgId);

	TenderWorkDto getTenderByWorkId(Long workId);
	
	List<BidMasterDto> getBidMAsterByOrgId(Long orgId);
	
	public Long BidCount(String bidNo,Long orgId);
	
	List<TenderMasterDto> findAllApprovedTender(Long orgId);

	List<BidMasterDto> getBidListByOrgId(Long orgId);

	BidMasterDto viewandeditFinancial(Long orgId, Long bidId, String saveMode);


}
