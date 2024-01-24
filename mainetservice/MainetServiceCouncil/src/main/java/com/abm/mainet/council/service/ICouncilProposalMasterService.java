package com.abm.mainet.council.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dto.CommonProposalDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;

/**
 * @author aarti.paan
 *
 */
@WebService
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces(MediaType.APPLICATION_JSON)
public interface ICouncilProposalMasterService {

    /**
     * method is used for fetching proposals by status and orgId
     * 
     * @param proposalStatus
     * @param orgId
     * @return List<CouncilProposalMasterDto>
     */
    List<CouncilProposalMasterDto> fetchProposalListByStatus(String proposalStatus, Long orgId);

    /**
     * method is used for fetching Proposals by AgendaId
     * 
     * @param agendaId
     * @return List<CouncilProposalMasterDto>
     */
    List<CouncilProposalMasterDto> findAllProposalByAgendaId(Long agendaId);

    List<CouncilProposalMasterDto> findProposalBasedOnStatusByAgendaId(String proposalStatus, Long agendaId);

    /**
     * method is used for Save Update Council Proposals
     * 
     * @param model
     * @param attachmentList
     * @param uploadDTO
     * @param deleteFileId
     * @param removeYearIdList 
     */
    public void saveCouncil(CouncilProposalMasterDto model, List<DocumentDetailsVO> attachmentList,
            FileUploadDTO uploadDTO, Long deleteFileId, List<Long> removeYearIdList);

    /**
     * method is used for search proposals based on below parameters
     * 
     * @param proposalDepId
     * @param proposalNo
     * @param fromDate
     * @param toDate
     * @param proposalStatus
     * @param orgid
     * @param wardId
     * @return List<CouncilProposalMasterDto>
     */
    public List<CouncilProposalMasterDto> searchProposalMasterData(Long proposalDepId, String proposalNo, Date fromDate,
            Date toDate, String proposalStatus, Long orgid, Long wardId, int langId,String type);

    /**
     * 
     * @param proposalId
     * @return CouncilProposalMasterDto
     */
    CouncilProposalMasterDto getCouncilProposalMasterByproposalId(Long proposalId);

    /**
     * method is used for getting Proposal details by ProposalId
     * 
     * @param proposalNo
     * @param orgId
     * @return CouncilProposalMasterDto
     */
    CouncilProposalMasterDto getCouncilProposalMasterByproposalNo(String proposalNo, Long orgId);

    /**
     * method is used for updating status (workflow status) by ProposalId
     * 
     * @param proposalId
     * @param flag
     */
    void updateProposalStatus(Long proposalId, String flag);

    /**
     * Method is used for getting budget details from account module
     * 
     * @param billApprovalDTO
     * @return vendorBillApprovalDTO
     */
    VendorBillApprovalDTO getBudgetExpenditureDetails(VendorBillApprovalDTO billApprovalDTO);

    /**
     * Method is used for fetching Proposal Number and Proposal detail by DeptId and OrgId
     * 
     * @param proposalDepId
     * @param orgid
     * @return List<CommonProposalDTO>
     */
    public List<CommonProposalDTO> findProposalsByDeptIdAndOrgId(CommonProposalDTO proposalDto);

	Map<Long, String> getBudgetExpenditure(VendorBillApprovalDTO billApprovalDTO);

	VendorBillApprovalDTO getCouncilBudgetExpenditureDetail(VendorBillApprovalDTO billApprovalDTO);

	List<CouncilProposalMasterDto> fetchProposalListbyCommiteeStatus(String proposalStatus, Long orgId);

	CouncilProposalMasterDto generateReport(CouncilProposalMasterDto councilDto);

	void updateUploadedFileDeleteRecords(List<Long> removeFileById, Long updatedBy);
}
