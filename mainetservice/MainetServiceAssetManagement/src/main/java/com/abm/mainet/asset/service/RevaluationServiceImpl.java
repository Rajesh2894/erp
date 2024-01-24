/**
 * 
 */
package com.abm.mainet.asset.service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetRevaluation;
import com.abm.mainet.asset.mapper.RevaluationMapper;
import com.abm.mainet.asset.repository.RevaluationRepo;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.RevaluationDTO;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailExternalDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.integration.asset.dto.AuditDetailsDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

/**
 * @author sarojkumar.yadav
 *
 */
@Service
public class RevaluationServiceImpl implements IRevaluationService {

    @Autowired
    private RevaluationRepo revaluation;
    @Autowired
    private IAssetValuationService valuationService;
    @Autowired
    private TbFinancialyearService finYearService;
    @Autowired
    private IAssetInformationService infoService;
    @Autowired
    private IWorkflowTyepResolverService iWorkflowTyepResolverService;
    @Autowired
    private ServiceMasterService iServiceMasterService;
    @Autowired
    private TbDepartmentService iTbDepartmentService;
    @Autowired
    public IFileUploadService fileUpload;
    @Resource
    private IAssetWorkflowService assetWorkFlowService;
    @Resource
    private IChecklistVerificationService checkListService;
    @Autowired
    private IEmployeeService empService;
    @Autowired
    private IInformationService infoUpdateService;
    @Autowired
    private IMaintenanceService maintainService;
    @Autowired
    private IOrganisationService iOrganisationService;
    @Autowired
    private SecondaryheadMasterService secondaryheadMasterService;
    @Autowired
    private TbTaxMasService taxMasService;

    private static final Logger LOGGER = Logger.getLogger(RevaluationServiceImpl.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.asset.service.RevaluationService#revaluate(com.abm.mainet. asset.ui.dto.RevaluationDTO,
     * com.abm.mainet.asset.ui.dto.AuditDetailsDTO, java.lang.String)
     */
    @Override
    @Transactional
    public String revaluate(final RevaluationDTO dto, final AuditDetailsDTO audit, final String workFlowFlag) {
        // final AssetRevaluationHistory entityHistory = new AssetRevaluationHistory();
        dto.setCreatedBy(audit.getEmpId());
        dto.setLgIpMac(audit.getEmpIpMac());
        dto.setCreationDate(new Date());
        AssetRevaluation entity = RevaluationMapper.mapToEntity(dto);
        try {
            entity = revaluation.save(entity);
            // entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
            // auditService.createHistory(entity, entityHistory);
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving asset Revaluation request ", exception);
            throw new FrameworkException("Exception occur while saving asset Revaluation request ", exception);
        }
        String referenceNo = dto.getSerialNo() + "/" + MainetConstants.AssetManagement.WF_TXN_IDEN_REVAL + "/"
                + entity.getRevaluationId();
        WorkflowTaskActionResponse response = initiateWorkFlow(dto.getOrgId(), dto.getSerialNo(),
                entity.getRevaluationId(), audit, referenceNo);
        if (response != null) {
            // pass referenceNo for astAppNo
            infoUpdateService.updateAppStatusFlag(dto.getAssetId(), dto.getOrgId(),
                    MainetConstants.AssetManagement.APPROVAL_STATUS_PENDING, referenceNo);
        }
        return referenceNo;

    }

    private void updateValuation(final RevaluationDTO revalDto, final AuditDetailsDTO auditDto) {
        AssetValuationDetailsDTO dto = new AssetValuationDetailsDTO();
        AssetValuationDetailsDTO depAssetDTO = valuationService.findLatestAssetId(revalDto.getOrgId(),
                revalDto.getAssetId());
        final AssetPurchaseInformationDTO purchaseDTO = infoService.getPurchaseInfo(revalDto.getAssetId());
        if (purchaseDTO == null) {
            LOGGER.error("Acquisition details not available for asset : " + revalDto.getSerialNo());
            return;
        }
        dto.setAssetId(revalDto.getAssetId());
        if (purchaseDTO.getCostOfAcquisition() != null) {
            dto.setInitialBookValue(purchaseDTO.getCostOfAcquisition());
        } else {
            if (purchaseDTO.getInitialBookValue() != null) {
                dto.setInitialBookValue(purchaseDTO.getInitialBookValue());
            }
        }
        if (purchaseDTO.getDateOfAcquisition() != null) {
            dto.setInitialBookDate(purchaseDTO.getDateOfAcquisition());
        }
        final AssetDepreciationChartDTO depDTO = infoService.getDepreciationInfo(revalDto.getAssetId());
        if(depDTO !=null && depDTO.getOriUseYear()!=null) {
            dto.setLife(depDTO.getOriUseYear());
        }
        if (depAssetDTO != null) {
            dto.setPreviousBookDate(depAssetDTO.getCurrentBookDate());
            dto.setPreviousBookValue(depAssetDTO.getCurrentBookValue());
        } else {
            dto.setLife(depDTO.getOriUseYear());
            dto.setPreviousBookValue(purchaseDTO.getInitialBookValue());
            dto.setPreviousBookDate(purchaseDTO.getDateOfAcquisition());
        }
        Date curDate = new Date();
        FinancialYear financiaYear = finYearService.getFinanciaYearByDate(curDate);
        dto.setBookFinYear(financiaYear.getFaYear());
        dto.setCurrentBookDate(curDate);
        dto.setCurrentBookValue(revalDto.getNewAmount());
        dto.setOrgId(revalDto.getOrgId());
        Long impId = revalDto.getImpType();
        Organisation org = new Organisation();
        org.setOrgid(revalDto.getOrgId());
        LookUp impLookup = CommonMasterUtility.getValueFromPrefixLookUp(
                MainetConstants.AssetManagement.IMP_TYPE_CAPITAL, MainetConstants.AssetManagement.REVAL_IMP_TYPE_PRF,
                org);
        if (impId != null && impId == impLookup.getLookUpId()) {
            dto.setChangetype(MainetConstants.AssetManagement.ValuationType.IMPROVEMENT.getValue());
            dto.setAddCost(revalDto.getImpCost());
        } else if (impId != null && impId != impLookup.getLookUpId() && impId != 0) {
            // skip valuation table entry because this won't impact valuation of asset
            return;
        } else if (impId == null || impId == 0) {
            dto.setChangetype(MainetConstants.AssetManagement.ValuationType.REVAL.getValue());
        }
        dto.setCreatedBy(auditDto.getEmpId());
        dto.setLgIpMac(auditDto.getEmpIpMac());
        dto.setCreationDate(curDate);
        Long id = valuationService.addEntry(dto);
        if (depAssetDTO != null && depAssetDTO.getCurrentBookValue() != null) {
            revalDto.setCurrentValue(depAssetDTO.getCurrentBookValue());
        } else if (purchaseDTO != null) {
            revalDto.setCurrentValue(purchaseDTO.getCostOfAcquisition());
        }
        if (dto.getChangetype() != null) {
            LookUp defaultVal = CommonMasterUtility.getDefaultValue(PrefixConstants.SLI);
            if (defaultVal != null && defaultVal.getLookUpCode().equals(MainetConstants.FlagL)) {
                revaluationAccountPosting(revalDto, curDate, org, auditDto);
            } else {
                LOGGER.info("Default Lookup value could not be found for prefix " + PrefixConstants.SLI
                        + ", Hence Account Posting will not be called");
            }
        }
        LOGGER.info("asset is added successfully in asset valuation table with id " + id + " and details " + dto);
    }

    // this method call only if the SLI is on live mode
    private void revaluationAccountPosting(final RevaluationDTO revalDto, final Date curDate, final Organisation org,
            final AuditDetailsDTO auditDto) {
        final AssetClassificationDTO classDTO = infoService.getClassification(revalDto.getAssetId());
        final Department dept = iTbDepartmentService.findDepartmentByCode(MainetConstants.AssetManagement.ASSET_MANAGEMENT);
        final Organisation organisation = iOrganisationService.getOrganisationById(revalDto.getOrgId());
        if (classDTO == null) {
            LOGGER.error("Could not get secondary account head of an asset details not available for asset : "
                    + revalDto.getSerialNo());
            return;
        }
        BigDecimal postAmount = revalDto.getNewAmount().subtract(revalDto.getCurrentValue());
        if (StringUtils.isBlank(classDTO.getCostCenter())) {
            throw new FrameworkException("Asset Secondary Account Code is null");
        }
        final VoucherPostExternalDTO vouPostDto = new VoucherPostExternalDTO();
        VoucherPostDetailExternalDTO vouPostDetExteDto1 = new VoucherPostDetailExternalDTO();
        VoucherPostDetailExternalDTO vouPostDetExteDto = new VoucherPostDetailExternalDTO();
        if (postAmount.signum() > 0) {
            // RCE means increase value
            Long tdpPrefixIdRCE = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    "RSR", MainetConstants.AssetManagement.TAX_PREFIX,
                    revalDto.getOrgId());
            List<TbTaxMas> txMasListRCE = taxMasService.findAllByDescId(tdpPrefixIdRCE, revalDto.getOrgId(),
                    dept.getDpDeptid());
            String accountCodeRCE = secondaryheadMasterService.findSacHeadCodeBySacHeadId(
                    taxMasService.fetchSacHeadIdForReceiptDet(revalDto.getOrgId(), txMasListRCE.get(0).getTaxId(), "A"));
            vouPostDto.setVousubTypeCode("RCE");
            vouPostDetExteDto1.setAcHeadCode(accountCodeRCE);
            vouPostDetExteDto1.setAccountHeadFlag(MainetConstants.AssetManagement.RECEIPT);// in case of increase the amount
            vouPostDetExteDto.setAccountHeadFlag(MainetConstants.AssetManagement.PAYMENT);

        } else {
            // RDE means decrease value
            Long tdpPrefixIdAWE = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.AssetManagement.ASSET_WRITE_OFF, MainetConstants.AssetManagement.TAX_PREFIX,
                    revalDto.getOrgId());
            List<TbTaxMas> txMasListAWE = taxMasService.findAllByDescId(tdpPrefixIdAWE, revalDto.getOrgId(),
                    dept.getDpDeptid());
            String accountCodeRDE = secondaryheadMasterService.findSacHeadCodeBySacHeadId(
                    taxMasService.fetchSacHeadIdForReceiptDet(revalDto.getOrgId(), txMasListAWE.get(0).getTaxId(), "A"));
            vouPostDto.setVousubTypeCode("RDE");
            vouPostDetExteDto1.setAcHeadCode(accountCodeRDE);
            vouPostDetExteDto1.setAccountHeadFlag(MainetConstants.AssetManagement.PAYMENT);// in case of decrease the amount
            vouPostDetExteDto.setAccountHeadFlag(MainetConstants.AssetManagement.RECEIPT);

        }

        List<VoucherPostDetailExternalDTO> detailExtenalDtoList = new ArrayList<>();
        vouPostDto.setVoucherDate(Utility.dateToString(revalDto.getDocDate()));
        vouPostDto.setDepartmentCode(MainetConstants.AssetManagement.ASSET_MANAGEMENT);
        vouPostDto.setUlbCode(organisation.getOrgShortNm());
        vouPostDto.setVoucherReferenceNo(revalDto.getSerialNo());
        vouPostDto.setVoucherReferenceDate(Utility.dateToString(revalDto.getDocDate()));
        vouPostDto.setNarration(revalDto.getRemarks() + revalDto.getSerialNo());
        vouPostDto.setLocationDescription("1-1");
        vouPostDto.setPayerOrPayee(MainetConstants.AssetManagement.SYSTEM);
        vouPostDto.setCreatedBy(auditDto.getEmpId().toString());
        vouPostDto.setEntryCode(MainetConstants.AssetManagement.ASSET_ENTRY_CODE_OF_ACCOUNT_EXTERNAL);
        vouPostDto.setEntryFlag(MainetConstants.AssetManagement.CHECK_TRANSACTION_YES);
        // Defect #7122 Asset Revaluation : After Final approval of asset revaluation Voucher Template entry goes for full amount.
        // It should be difference amount.
        vouPostDetExteDto.setVoucherAmount(postAmount.abs());// that is the evaluation amount
        vouPostDetExteDto.setAcHeadCode(classDTO.getCostCenter());

        vouPostDetExteDto1.setVoucherAmount(postAmount.abs());// that is the evaluation amount

        detailExtenalDtoList.add(vouPostDetExteDto);
        detailExtenalDtoList.add(vouPostDetExteDto1);
        vouPostDto.setVoucherExtDetails(detailExtenalDtoList);
        try {
            if (maintainService.checkAccountActiveOrNot()) {
                maintainService.externalVoucherPostingInAccount(organisation.getOrgid(), vouPostDto);
            }
            if (vouPostDto.getVousubTypeCode() != null && vouPostDto.getVousubTypeCode() == "RDE") {
                adjustmentOfRevaluationReserve(revalDto, organisation, classDTO, dept, auditDto, postAmount);
            }
            LOGGER.info("Account posting service called successfully for Asset id " + revalDto.getAssetId() + " and details "
                    + revalDto);
        } catch (Exception exc) {
            throw new FrameworkException("Exception occured while posting depreciation voucher to accounts module: ", exc);
        }

    }

    private void adjustmentOfRevaluationReserve(RevaluationDTO revalDto, Organisation organisation,
            AssetClassificationDTO classDTO, Department dept, AuditDetailsDTO auditDto, BigDecimal revaluateddiffAmt) {
        try {
            final AssetDepreciationChartDTO depDto = infoService.getDepreciationInfo(revalDto.getAssetId());
            BigDecimal depreciationAmt = null;
            if (depDto != null && depDto.getLatestAccumDepreAmount() != null) {
                // D#72425
                depreciationAmt = revaluateddiffAmt.divide(depDto.getLatestAccumDepreAmount(), 2, RoundingMode.HALF_UP);
                depreciationAmt = depreciationAmt.multiply(new BigDecimal(100));
            } else if (depDto != null && depDto.getInitialAccumDepreAmount() != null) {
                depreciationAmt = (revaluateddiffAmt.divide(depDto.getInitialAccumDepreAmount(), 2, RoundingMode.HALF_UP))
                        .multiply(new BigDecimal(100));
            }
            Long tdpPrefixIdRCE = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    "RSR", MainetConstants.AssetManagement.TAX_PREFIX,
                    revalDto.getOrgId());// RSR need to send payment type DR
            List<TbTaxMas> txMasListRCE = taxMasService.findAllByDescId(tdpPrefixIdRCE, revalDto.getOrgId(),
                    dept.getDpDeptid());
            String accountCodeRCE = secondaryheadMasterService.findSacHeadCodeBySacHeadId(
                    taxMasService.fetchSacHeadIdForReceiptDet(revalDto.getOrgId(), txMasListRCE.get(0).getTaxId(), "A"));
            if (accountCodeRCE == null) {
                LOGGER.error("exception occur while finding tax account head");
                throw new FrameworkException("exception occur while finding tax account head");
            }

            final VoucherPostExternalDTO vouPostDto = new VoucherPostExternalDTO();
            VoucherPostDetailExternalDTO vouPostDetExteDto1 = new VoucherPostDetailExternalDTO();
            List<VoucherPostDetailExternalDTO> detailExtenalDtoList = new ArrayList<>();
            vouPostDto.setVoucherDate(Utility.dateToString(revalDto.getDocDate()));
            vouPostDto.setDepartmentCode(MainetConstants.AssetManagement.ASSET_MANAGEMENT);
            vouPostDto.setUlbCode(organisation.getOrgShortNm());
            vouPostDto.setVoucherReferenceNo(revalDto.getSerialNo());
            vouPostDto.setVoucherReferenceDate(Utility.dateToString(revalDto.getDocDate()));
            vouPostDto.setNarration("ASSET DEPRECIATION FOR " + revalDto.getSerialNo());
            vouPostDto.setLocationDescription("1-1");
            vouPostDto.setPayerOrPayee(MainetConstants.AssetManagement.SYSTEM);
            vouPostDto.setCreatedBy(auditDto.getEmpId().toString());
            vouPostDto.setEntryCode(MainetConstants.AssetManagement.ASSET_ENTRY_CODE_OF_ACCOUNT_EXTERNAL);
            vouPostDto.setEntryFlag(MainetConstants.AssetManagement.CHECK_TRANSACTION_YES);

            VoucherPostDetailExternalDTO vouPostDetExteDto = new VoucherPostDetailExternalDTO();
            vouPostDetExteDto.setVoucherAmount(depreciationAmt);
            vouPostDetExteDto.setAcHeadCode(depDto.getAccumuDepAc());
            vouPostDetExteDto.setAccountHeadFlag(MainetConstants.AssetManagement.RECEIPT);

            vouPostDetExteDto1.setVoucherAmount(depreciationAmt);
            vouPostDetExteDto1.setAccountHeadFlag(MainetConstants.AssetManagement.PAYMENT);
            vouPostDetExteDto1.setAcHeadCode(accountCodeRCE);

            detailExtenalDtoList.add(vouPostDetExteDto);
            detailExtenalDtoList.add(vouPostDetExteDto1);
            vouPostDto.setVoucherExtDetails(detailExtenalDtoList);

        } catch (Exception exc) {
            throw new FrameworkException("Exception occured while posting depreciation voucher to accounts module: ", exc);
        }

    }

    /**
     * It is used to initiate workflow
     * 
     * @param orgId
     * @param serialNo
     * @param asstId
     */
    private WorkflowTaskActionResponse initiateWorkFlow(final Long orgId, final String assetCode, final Long revId,
            final AuditDetailsDTO auditDto, String refrenceNo) {
        WorkflowTaskActionResponse response = null;
        try {
            TbDepartment deptObj = iTbDepartmentService.findDeptByCode(orgId, MainetConstants.FlagA,
                    MainetConstants.AssetManagement.ASSET_MANAGEMENT);
            ServiceMaster sm = iServiceMasterService
                    .getServiceMasterByShortCode(MainetConstants.AssetManagement.TRF_REVAL_SERV_SHORTCODE, orgId);
            // Code related to work flow
            WorkflowMas workFlowMas = iWorkflowTyepResolverService.resolveServiceWorkflowType(orgId,
                    deptObj.getDpDeptid(), sm.getSmServiceId(), null, null, null, null, null);

            response = assetWorkFlowService.initiateWorkFlowAssetService(
                    prepareWorkFlowTaskActionCreate(refrenceNo, auditDto, orgId), workFlowMas.getWfId(),
                    MainetConstants.AssetManagement.ASSET_REVALUATION_URL, MainetConstants.FlagA,
                    MainetConstants.AssetManagement.TRF_REVAL_SERV_SHORTCODE);
        } catch (Exception ex) {
            LOGGER.error("Error occured while initiating workflow: ", ex);
            response = null;
        }
        return response;
    }

    // set all relevant Work flow Task Action Data For initiating Work Flow -initial
    // request
    private WorkflowTaskAction prepareWorkFlowTaskActionCreate(final String serialNo, final AuditDetailsDTO auditDto,
            final Long orgId) {
        EmployeeBean emp = empService.findById(auditDto.getEmpId());
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(orgId);
        taskAction.setEmpId(emp.getEmpId());
        taskAction.setEmpType(emp.getEmplType());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(emp.getEmpId());
        taskAction.setEmpName(emp.getEmplname());
        taskAction.setEmpEmail(emp.getEmpemail());
        taskAction.setReferenceId(serialNo);
        taskAction.setPaymentMode(MainetConstants.FlagF);
        return taskAction;
    }

    @Override
    @Transactional
    public RevaluationDTO getDetails(final Long revalId) {
        AssetRevaluation astReval = revaluation.findOne(revalId);
        if (astReval == null) {
            throw new FrameworkException("Could not find revaluation details for ID: " + revalId);
        }
        // initalize AssetInformation by calling getter
        astReval.getAssetId().getAssetId();
        return RevaluationMapper.mapToDTO(astReval);
    }

    /**
     * @param workflowActionDto
     */
    private WorkflowTaskAction prepareWorkFlowTaskActionUpdate(final String refId, final Long taskId,
            final List<Long> attIds, final AuditDetailsDTO auditDto, final Long orgId) {
        EmployeeBean emp = empService.findById(auditDto.getEmpId());
        WorkflowTaskAction workflowActionDto = new WorkflowTaskAction();
        workflowActionDto.setOrgId(orgId);
        workflowActionDto.setEmpId(emp.getEmpId());
        workflowActionDto.setEmpType(emp.getEmplType());
        workflowActionDto.setEmpName(emp.getEmplname());
        workflowActionDto.setEmpEmail(emp.getEmpemail());
        workflowActionDto.setAttachementId(attIds);
        workflowActionDto.setDateOfAction(new Date());
        workflowActionDto.setCreatedDate(new Date());
        workflowActionDto.setCreatedBy(emp.getEmpId());
        workflowActionDto.setReferenceId(refId);
        workflowActionDto.setPaymentMode(MainetConstants.FlagF);
        workflowActionDto.setIsFinalApproval(false);
        workflowActionDto.setTaskId(taskId);
        return workflowActionDto;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private WorkflowTaskActionResponse executeWorkflowAction(final String refId, final RevaluationDTO revalDto,
            final AuditDetailsDTO auditDto, final WorkflowTaskAction wfAction) {
        WorkflowTaskActionResponse response = null;
        List<Long> attIds = checkListService.fetchAllAttachIdByReferenceId(refId, revalDto.getOrgId());
        // execute workflow task action
        WorkflowTaskAction workflowActionDtoTmp = prepareWorkFlowTaskActionUpdate(refId, wfAction.getTaskId(), attIds,
                auditDto, revalDto.getOrgId());
        workflowActionDtoTmp.setDecision(wfAction.getDecision());
        response = assetWorkFlowService.initiateWorkFlowAssetService(workflowActionDtoTmp, wfAction.getTaskId(),
                MainetConstants.AssetManagement.ASSET_REVALUATION_URL, MainetConstants.FlagU,
                MainetConstants.AssetManagement.TRF_REVAL_SERV_SHORTCODE);

        return response;
    }

    private List<DocumentDetailsVO> prepareWfAttachments() {
        List<DocumentDetailsVO> docVOList = new ArrayList<DocumentDetailsVO>();

        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    DocumentDetailsVO docVO = new DocumentDetailsVO();
                    docVO.setDoc_DESC_ENGL(file.getName());
                    docVOList.add(docVO);
                }
            }
        }
        return docVOList;
    }

    @Override
    @Transactional
    public boolean executeWfAction(final String wfReferenceId, final AuditDetailsDTO auditDto,
            final WorkflowTaskAction wfAction) {
        String revalId = wfReferenceId
                .substring(wfReferenceId.lastIndexOf(MainetConstants.AssetManagement.WF_TXN_IDEN_REVAL) + 4);
        RevaluationDTO revalDto = getDetails(Long.valueOf(revalId));
        boolean retVal = false;
        WorkflowTaskActionResponse response = null;
        try {
            response = executeWorkflowAction(wfReferenceId, revalDto, auditDto, wfAction);
            retVal = true;
        } catch (Exception ex) {
            LOGGER.error("Exception occurred during workflow action execution ", ex);
            retVal = false;
            response = null;
        }
        if (response != null && !response.getIsProcessAlive()) {
            if (wfAction.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
                updateValuation(revalDto, auditDto);
                revaluation.updateAuthStatus("A", auditDto.getEmpId(), new Date(), revalDto.getAssetId(),
                        revalDto.getRevaluationId(), revalDto.getOrgId());
                // pass wfReferenceId for astAppNo
                retVal = infoUpdateService.updateAppStatusFlag(revalDto.getAssetId(), revalDto.getOrgId(),
                        MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED, wfReferenceId);
            } else {
                revaluation.updateAuthStatus("R", auditDto.getEmpId(), new Date(), revalDto.getAssetId(),
                        revalDto.getRevaluationId(), revalDto.getOrgId());
                // pass wfReferenceId for astAppNo
                retVal = infoUpdateService.updateAppStatusFlag(revalDto.getAssetId(), revalDto.getOrgId(),
                        MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED, wfReferenceId);
            }
        }
        return retVal;
    }

    // Defect #5437 for this i need to do this
    @Override
    @Transactional
    public void docUpload(String wfReferenceId, AuditDetailsDTO auditDto, WorkflowTaskAction wfAction) {
        String revalId = wfReferenceId
                .substring(wfReferenceId.lastIndexOf(MainetConstants.AssetManagement.WF_TXN_IDEN_REVAL) + 4);
        RevaluationDTO revalDto = getDetails(Long.valueOf(revalId));
        ServiceMaster serviceMast = iServiceMasterService.getServiceMasterByShortCode(
                MainetConstants.AssetManagement.TRF_REVAL_SERV_SHORTCODE, revalDto.getOrgId());
        // Prepare file upload
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(wfReferenceId);
        requestDTO.setOrgId(revalDto.getOrgId());
        requestDTO.setDepartmentName(MainetConstants.AssetManagement.ASSETCODE);
        requestDTO.setServiceId(serviceMast.getSmServiceId());
        requestDTO.setUserId(auditDto.getEmpId());
        requestDTO.setApplicationId(revalDto.getAssetId());
        requestDTO.setDeptId(serviceMast.getTbDepartment().getDpDeptid());
        // process and upload
        List<DocumentDetailsVO> attachments = prepareWfAttachments();
        attachments = fileUpload.prepareFileUpload(attachments);
        fileUpload.doFileUpload(attachments, requestDTO);

    }
}
