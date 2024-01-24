/**
 * 
 */
package com.abm.mainet.asset.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetInformation;
import com.abm.mainet.asset.domain.AssetRetire;
import com.abm.mainet.asset.domain.AssetRetireHistory;
import com.abm.mainet.asset.mapper.RetireMapper;
import com.abm.mainet.asset.repository.AssetInformationRepo;
import com.abm.mainet.asset.repository.RetireRepo;
import com.abm.mainet.asset.ui.dto.AssetValuationDetailsDTO;
import com.abm.mainet.asset.ui.dto.CalculationDTO;
import com.abm.mainet.asset.ui.dto.ChartOfDepreciationMasterDTO;
import com.abm.mainet.asset.ui.dto.RetirementDTO;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailExternalDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetInformationDTO;
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
import com.abm.mainet.common.service.IReceiptEntryService;
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
public class RetireServiceImpl implements IRetireService {

    @Autowired
    private RetireRepo retire;
    @Autowired
    private AuditService auditService;
    @Autowired
    private IMaintenanceService mainService;
    @Autowired
    private IAssetInformationService infoService;
    @Autowired
    private IInformationService infoUpdateService;
    @Autowired
    private IAssetValuationService valuationService;
    @Autowired
    private TbTaxMasService taxMasService;
    @Autowired
    private IReceiptEntryService receiptService;
    @Autowired
    private ServiceMasterService serviceMasterService;
    @Autowired
    private AssetInformationRepo infoRepo;
    @Autowired
    private TbDepartmentService deptService;
    @Autowired
    private TbFinancialyearService finYearService;
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
    private IOrganisationService iOrganisationService;
    @Autowired
    private IDepreciationService depreciationService;
    @Autowired
    private IChartOfDepreciationMasterService chartService;
    @Autowired
    private SecondaryheadMasterService secondaryheadMasterService;

    private static final Logger LOGGER = Logger.getLogger(RetireServiceImpl.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.asset.service.RetireService#retire(com.abm.mainet.asset.ui.dto .RetirementDTO,
     * com.abm.mainet.asset.ui.dto.AuditDetailsDTO, java.lang.String)
     */
    @Override
    @Transactional
    public String retire(final RetirementDTO dto, final AuditDetailsDTO audit, final String workFlowFlag, String moduleDeptCode,
            String serviceCodeDeptWise) {
        final AssetRetireHistory entityHistory = new AssetRetireHistory();
        dto.setCreatedBy(audit.getEmpId());
        dto.setLgIpMac(audit.getEmpIpMac());
        dto.setCreationDate(new Date());
        AssetRetire entity = RetireMapper.mapToEntity(dto);
        try {
            // D#85182
            entity = retire.save(entity);
        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving asset retirement request ", exception);
            throw new FrameworkException("Exception occur while saving asset retirement request ", exception);
        }
        String astIdStr = dto.getSerialNo() + "/" + MainetConstants.AssetManagement.WF_TXN_IDEN_RETIRE + "/"
                + entity.getRetireId();
        WorkflowTaskActionResponse response = initiateWorkFlow(dto.getOrgId(), astIdStr, audit, moduleDeptCode,
                serviceCodeDeptWise);
        if (response != null) {
            // pass astIdStr for astAppNo
            infoUpdateService.updateAppStatusFlag(dto.getAssetId(), dto.getOrgId(),
                    MainetConstants.AssetManagement.APPROVAL_STATUS_PENDING, astIdStr);
        }
        try {
            entityHistory.setHistoryFlag(MainetConstants.InsertMode.ADD.getStatus());
            auditService.createHistory(entity, entityHistory);
        } catch (Exception ex) {
            LOGGER.error("Error occured while recording history: ", ex);
        }
        return astIdStr;

    }

    @Transactional
    public void doRetirement(final RetirementDTO dto, final AuditDetailsDTO audit, String moduleDeptCode,
            String serviceCodeDeptWise) {
        Boolean prolosOrWriteOf = CommonMasterUtility
                .lookUpByLookUpIdAndPrefix(dto.getDispositionMethod(),
                        dto.getDeptCode().equals(MainetConstants.AssetManagement.ASSETCODE)
                                ? MainetConstants.AssetManagement.ASSET_DISPOSAL_METHOD_CODE
                                : MainetConstants.ITAssetManagement.IT_ASSET_DISPOSAL_METHOD_CODE,
                        dto.getOrgId())
                .getLookUpCode().equals(MainetConstants.AssetManagement.DISPOSAL_SALE_CODE);
        try {
            Long assetStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.AssetManagement.ASSET_DISPOSAL_CODE,
                    MainetConstants.AssetManagement.ASSET_STATUS_PREFIX, dto.getOrgId());

            infoRepo.updateStatus(dto.getOrgId(), assetStatus, dto.getAssetId());
            if (prolosOrWriteOf) {
                /*
                 * Defect #7239 For Asset Write off without revenue Skip Receipt Entry and Depreciation and Valuation process The
                 * system should post only write off voucher entry.
                 */
                updateDepreciation(dto, audit);
                updateValuation(dto);
                generateReceipt(dto, moduleDeptCode, serviceCodeDeptWise); // no need to do any change in the receipt posting as
                                                                           // per discussion to rajesh and samadhan
            }

        } catch (Exception exception) {
            LOGGER.error("Exception occur while saving asset Retire request ", exception);
            throw new FrameworkException("Exception occur while saving asset Retire request ", exception);
        }
        try {
            LookUp defaultVal = CommonMasterUtility.getDefaultValue(PrefixConstants.SLI);
            if (defaultVal != null && defaultVal.getLookUpCode().equals(MainetConstants.FlagL)) {
                if (prolosOrWriteOf) {
                    profitAndLossPosting(dto, audit);
                    writeOffPosting(dto, audit);// disposal entries
                } else {
                    writeOffPosting(dto, audit);
                }
            } else {
                LOGGER.info("Default Lookup value could not be found for prefix " + PrefixConstants.SLI
                        + ", Hence Account Posting will not be called");
            }
        } catch (Exception ex) {
            LOGGER.error("Exception occured while posting receipt for asset retirement", ex);
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void writeOffPosting(final RetirementDTO dto, final AuditDetailsDTO audit) {
        try {
            final Organisation organisation = iOrganisationService.getOrganisationById(dto.getOrgId());
            final Department dept = deptService.findDepartmentByCode(dto.getDeptCode());
            final AssetValuationDetailsDTO valuationDTO = valuationService.findLatestAssetId(dto.getOrgId(),
                    dto.getAssetId());
            AssetClassificationDTO astclassdto = infoService.getClassification(dto.getAssetId());
            AssetPurchaseInformationDTO assetAcqu = infoService.getPurchaseInfo(dto.getAssetId());
            if (astclassdto == null || astclassdto.getCostCenter() == null) {
                LOGGER.error("cost center is not set in the asset acqusition please set first that on or acquision is null");
                throw new FrameworkException("cost center is not set in the asset acqusition please set first that on ");

            }
            Long taxDescPrefixId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.AssetManagement.ASSET_WRITE_OFF, MainetConstants.AssetManagement.TAX_PREFIX,
                    dto.getOrgId());

            List<TbTaxMas> txMasList = taxMasService.findAllByDescId(taxDescPrefixId, dto.getOrgId(),
                    dept.getDpDeptid());
            String accountCodeTAX = secondaryheadMasterService.findSacHeadCodeBySacHeadId(
                    taxMasService.fetchSacHeadIdForReceiptDet(dto.getOrgId(), txMasList.get(0).getTaxId(), "A"));
            List<VoucherPostDetailExternalDTO> detailExtenalDtoList = new ArrayList<>();
            final VoucherPostExternalDTO vouPostDto = new VoucherPostExternalDTO();
            vouPostDto.setVoucherDate(Utility.dateToString(dto.getDispositionDate()));
            vouPostDto.setVousubTypeCode(MainetConstants.AssetManagement.ASSET_WRITE_OFF);
            vouPostDto.setDepartmentCode(dto.getDeptCode());
            vouPostDto.setUlbCode(organisation.getOrgShortNm());
            vouPostDto.setVoucherReferenceNo(dto.getSerialNo());
            vouPostDto.setVoucherReferenceDate(Utility.dateToString(dto.getDispositionDate()));
            vouPostDto.setNarration("ASSET DEPRECIATION FOR " + dto.getSerialNo());
            vouPostDto.setLocationDescription("1-1");
            vouPostDto.setPayerOrPayee(MainetConstants.AssetManagement.SYSTEM);
            vouPostDto.setCreatedBy(audit.getEmpId().toString());
            vouPostDto.setEntryCode(MainetConstants.AssetManagement.ASSET_ENTRY_CODE_OF_ACCOUNT_EXTERNAL);
            vouPostDto.setPayModeCode(MainetConstants.AssetManagement.PAY_MODE_TRANSFER);
            vouPostDto.setEntryFlag(MainetConstants.AssetManagement.CHECK_TRANSACTION_YES);

            VoucherPostDetailExternalDTO vouPostDetExteDto = new VoucherPostDetailExternalDTO();
            vouPostDetExteDto.setVoucherAmount(valuationDTO.getPreviousBookValue());// net book value /end book value
            vouPostDetExteDto.setAcHeadCode(accountCodeTAX);
            vouPostDetExteDto.setAccountHeadFlag(MainetConstants.AssetManagement.PAYMENT);
            if (valuationDTO.getAccumDeprValue() != null) {
                VoucherPostDetailExternalDTO vouPostDetExteDto1 = new VoucherPostDetailExternalDTO();
                vouPostDetExteDto1.setVoucherAmount(valuationDTO.getAccumDeprValue());
                vouPostDetExteDto1.setAcHeadCode(depreciationService.findDeprtChartByAssetId(dto.getAssetId()).getAccumuDepAc());
                vouPostDetExteDto1.setAccountHeadFlag(MainetConstants.AssetManagement.PAYMENT);
                detailExtenalDtoList.add(vouPostDetExteDto1);
            }

            VoucherPostDetailExternalDTO vouPostDetExteDto2 = new VoucherPostDetailExternalDTO();
            vouPostDetExteDto2.setVoucherAmount(assetAcqu.getCostOfAcquisition());
            vouPostDetExteDto2.setAcHeadCode(astclassdto.getCostCenter());
            vouPostDetExteDto2.setAccountHeadFlag(MainetConstants.AssetManagement.RECEIPT);

            detailExtenalDtoList.add(vouPostDetExteDto);
            detailExtenalDtoList.add(vouPostDetExteDto2);
            vouPostDto.setVoucherExtDetails(detailExtenalDtoList);
            try {
                if (mainService.checkAccountActiveOrNot()) {
                    mainService.externalVoucherPostingInAccount(organisation.getOrgid(), vouPostDto);
                    LOGGER.info("Account posting service called successfully for Asset id " + dto.getAssetId() + " and details "
                            + dto);
                }
            } catch (Exception exc) {
                throw new FrameworkException("Exception occured while posting depreciation voucher to accounts module: ", exc);
            }
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
    }

    /**
     * It is used to initiate workflow
     * 
     * @param orgId
     * @param serialNo
     * @param asstId
     */
    private WorkflowTaskActionResponse initiateWorkFlow(final Long orgId, final String astIdStr,
            final AuditDetailsDTO auditDto, String moduleDeptCode,
            String serviceCodeDeptWise) {
        WorkflowTaskActionResponse response = null;
        try {
            TbDepartment deptObj = iTbDepartmentService.findDeptByCode(orgId, MainetConstants.FlagA,
                    moduleDeptCode);
            ServiceMaster sm = iServiceMasterService
                    .getServiceMasterByShortCode(serviceCodeDeptWise, orgId);
            // Code related to work flow
            WorkflowMas workFlowMas = iWorkflowTyepResolverService.resolveServiceWorkflowType(orgId,
                    deptObj.getDpDeptid(), sm.getSmServiceId(), null, null, null, null, null);

            response = assetWorkFlowService.initiateWorkFlowAssetService(
                    prepareWorkFlowTaskActionCreate(astIdStr, auditDto, orgId), workFlowMas.getWfId(),
                    MainetConstants.AssetManagement.ASSET_RETIRE_URL, MainetConstants.FlagA,
                    serviceCodeDeptWise);
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

    @Transactional
    private void updateDepreciation(final RetirementDTO retireDto, final AuditDetailsDTO audit) {

        final Date curDate = new Date();
        Integer months;
        final Organisation organisation = iOrganisationService.getOrganisationById(retireDto.getOrgId());
        List<VoucherPostDetailDTO> voucherList = new ArrayList<>(0);
        CalculationDTO dto = new CalculationDTO();
        AssetValuationDetailsDTO depAssetDTO = valuationService.findLatestAssetId(retireDto.getOrgId(),
                retireDto.getAssetId());
        final AssetPurchaseInformationDTO purchaseDTO = infoService.getPurchaseInfo(retireDto.getAssetId());
        final AssetInformationDTO astinfodto = infoService.getInfo(retireDto.getAssetId());
        if (purchaseDTO == null) {
            LOGGER.error("Acquisition details not available for asset : " + retireDto.getSerialNo());
            return;
        }
        dto.setAssetId(retireDto.getAssetId());
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
        final AssetDepreciationChartDTO depDTO = infoService.getDepreciationInfo(retireDto.getAssetId());
        dto.setLife(depDTO.getOriUseYear());
        if (depAssetDTO != null) {
            dto.setPreviousBookDate(depAssetDTO.getCurrentBookDate());
            dto.setPreviousBookValue(depAssetDTO.getCurrentBookValue());
            dto.setAccumDeprValue(depAssetDTO.getAccumDeprValue());
            months = Utility.getMonthsBetweenDates(depAssetDTO.getCurrentBookDate(), retireDto.getDispositionDate());
            if (months > 6) {
                months = 12;
            } else {
                months = 6;
            }
        } else {
            dto.setLife(depDTO.getOriUseYear());
            dto.setPreviousBookValue(purchaseDTO.getInitialBookValue());
            dto.setPreviousBookDate(purchaseDTO.getDateOfAcquisition());
            months = null;
        }
        ChartOfDepreciationMasterDTO cdmDTO = chartService.getAllByAssetClass(retireDto.getOrgId(), astinfodto.getAssetClass2());
        dto.setOrgId(retireDto.getOrgId());
        dto.setAccountCode(cdmDTO.getAccountCode());
        dto.setChangetype(MainetConstants.AssetManagement.ValuationType.DPR.getValue());

        depAssetDTO = mainService.calculateDepreciation(dto, months);
        depAssetDTO.setAssetCode(astinfodto.getAstCode());
        retireDto.setAssetCode(astinfodto.getAstCode());// this is for reference
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(PrefixConstants.SLI);// if account is live then only account
                                                                                     // posting will done
        if (defaultVal != null && defaultVal.getLookUpCode().equals(MainetConstants.FlagL)) {
            voucherPostingAfterDepreciation(audit, curDate, organisation, voucherList, dto, depAssetDTO, retireDto);
        }
    }

    private void voucherPostingAfterDepreciation(final AuditDetailsDTO audit, final Date curDate,
            final Organisation organisation, List<VoucherPostDetailDTO> voucherList, CalculationDTO dto,
            AssetValuationDetailsDTO depAssetDTO, RetirementDTO retireDto) {
        List<VoucherPostDetailExternalDTO> detailExtenalDtoList = new ArrayList<>();
        final VoucherPostExternalDTO vouPostDto = new VoucherPostExternalDTO();
        vouPostDto.setVoucherDate(Utility.dateToString(retireDto.getDispositionDate()));
        vouPostDto.setVousubTypeCode(MainetConstants.AssetManagement.DEPR_ACCT_TMPLT_PRF);
        vouPostDto.setDepartmentCode(retireDto.getDeptCode());
        vouPostDto.setUlbCode(organisation.getOrgShortNm());
        vouPostDto.setVoucherReferenceNo(depAssetDTO.getAssetCode());
        vouPostDto.setVoucherReferenceDate(Utility.dateToString(retireDto.getDisOrderDate()));
        vouPostDto.setNarration("ASSET DEPRECIATION FOR " + depAssetDTO.getAssetCode());
        vouPostDto.setLocationDescription("1-1");
        vouPostDto.setPayerOrPayee(MainetConstants.AssetManagement.SYSTEM);
        vouPostDto.setCreatedBy(audit.getEmpId().toString());
        vouPostDto.setEntryCode(MainetConstants.AssetManagement.ASSET_ENTRY_CODE_OF_ACCOUNT_EXTERNAL);
        vouPostDto.setPayModeCode(MainetConstants.AssetManagement.PAY_MODE_TRANSFER);
        vouPostDto.setEntryFlag(MainetConstants.AssetManagement.CHECK_TRANSACTION_YES);

        VoucherPostDetailExternalDTO vouPostDetExteDto = new VoucherPostDetailExternalDTO();
        vouPostDetExteDto.setVoucherAmount(depAssetDTO.getDeprValue());
        vouPostDetExteDto.setAcHeadCode(dto.getAccountCode());
        vouPostDetExteDto.setAccountHeadFlag(MainetConstants.AssetManagement.PAYMENT);

        VoucherPostDetailExternalDTO vouPostDetExteDto1 = new VoucherPostDetailExternalDTO();
        vouPostDetExteDto1.setVoucherAmount(depAssetDTO.getDeprValue());
        vouPostDetExteDto1.setAcHeadCode(depreciationService.findDeprtChartByAssetId(depAssetDTO.getAssetId()).getAccumuDepAc());
        vouPostDetExteDto1.setAccountHeadFlag(MainetConstants.AssetManagement.RECEIPT);
        detailExtenalDtoList.add(vouPostDetExteDto);
        detailExtenalDtoList.add(vouPostDetExteDto1);
        vouPostDto.setVoucherExtDetails(detailExtenalDtoList);
        try {
            /* check account module is live or not */
            if (mainService.checkAccountActiveOrNot()) {
                mainService.externalVoucherPostingInAccount(organisation.getOrgid(), vouPostDto);
                LOGGER.info("Account posting service called successfully for Asset id " + dto.getAssetId() + " and details "
                        + dto);
            }
        } catch (Exception exc) {
            throw new FrameworkException("Exception occured while posting depreciation voucher to accounts module: ", exc);
        }
    }

    @Transactional
    private void updateValuation(final RetirementDTO retireDto) {
        AssetValuationDetailsDTO dto = new AssetValuationDetailsDTO();
        AssetValuationDetailsDTO depAssetDTO = valuationService.findLatestAssetId(retireDto.getOrgId(),
                retireDto.getAssetId());
        final AssetPurchaseInformationDTO purchaseDTO = infoService.getPurchaseInfo(retireDto.getAssetId());
        if (purchaseDTO == null) {
            LOGGER.error("Acquisition details not available for asset : " + retireDto.getSerialNo());
            return;
        }
        dto.setAssetId(retireDto.getAssetId());
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
        final AssetDepreciationChartDTO depDTO = infoService.getDepreciationInfo(retireDto.getAssetId());
        dto.setLife(depDTO.getOriUseYear());
        if (depAssetDTO != null) {
            dto.setPreviousBookDate(depAssetDTO.getCurrentBookDate());
            dto.setPreviousBookValue(depAssetDTO.getCurrentBookValue());
            dto.setAccumDeprValue(depAssetDTO.getAccumDeprValue());
        } else {
            dto.setLife(depDTO.getOriUseYear());
            dto.setPreviousBookValue(purchaseDTO.getInitialBookValue());
            dto.setPreviousBookDate(purchaseDTO.getDateOfAcquisition());
        }
        Date curDate = new Date();
        FinancialYear financiaYear = finYearService.getFinanciaYearByDate(curDate);
        dto.setBookFinYear(financiaYear.getFaYear());
        dto.setCurrentBookDate(curDate);
        dto.setCurrentBookValue(BigDecimal.ZERO);
        dto.setOrgId(retireDto.getOrgId());
        dto.setCreationDate(new Date());
        dto.setChangetype(MainetConstants.AssetManagement.ValuationType.RETIRE.getValue());
        Long id = valuationService.addEntry(dto);
        LOGGER.info("asset is added successfully in asset valuation table with id " + id + " and details " + dto);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void generateReceipt(final RetirementDTO retireDto, String moduleDeptCode, String serviceCodeDeptWise) {
        try {
            Department dept = deptService.findDepartmentByCode(moduleDeptCode);
            if (dept == null) {
                throw new FrameworkException("AST for asset management department not found");
            }
            Organisation org = new Organisation();
            org.setOrgid(retireDto.getOrgId());
            LookUp tdpPrefix = CommonMasterUtility.getValueFromPrefixLookUp(
                    MainetConstants.AssetManagement.AST_SALE_ACCT_TMPLT_PRF,
                    MainetConstants.BILL_MASTER_COMMON.ACCOUNT_TEMPLATE_FOR_PREFIX, org);
            if (tdpPrefix == null) {
                throw new FrameworkException("ADE common parameter for TDP prefix not defined");
            }
            CommonChallanDTO requestDTO = new CommonChallanDTO();
            requestDTO.setTdpPrefixId(tdpPrefix.getLookUpId());
            requestDTO.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
            requestDTO.setOrgId(retireDto.getOrgId());
            requestDTO.setDeptId(dept.getDpDeptid());
            requestDTO.setLoggedLocId(retireDto.getLocation());
            requestDTO.setApplicantName(retireDto.getSoldToName());
            requestDTO.setUserId(retireDto.getCreatedBy());
            requestDTO.setLgIpMac(retireDto.getLgIpMac());
            requestDTO.setUniquePrimaryId(retireDto.getAssetId().toString());

            requestDTO.setAmountToPay(retireDto.getAmount().toPlainString());
            requestDTO.setPayModeIn(retireDto.getPayMode());
            ServiceMaster serviceMaster = serviceMasterService
                    .getServiceByShortName(serviceCodeDeptWise, retireDto.getOrgId());
            requestDTO.setServiceId(serviceMaster.getSmServiceId());
            requestDTO.setPaymentCategory("M");
            requestDTO.setApplNo(0L);
            Long taxDescPrefixId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.AssetManagement.SALE_PROCEEDS_FROM_ASSETS, MainetConstants.AssetManagement.TAX_PREFIX,
                    retireDto.getOrgId());
            if (taxDescPrefixId == null || taxDescPrefixId.longValue() == 0) {
                throw new FrameworkException("Tax (TXN,SPA) not defined for Asset Department (AST)");
            }
            List<TbTaxMas> txMasList = taxMasService.findAllByDescId(taxDescPrefixId, retireDto.getOrgId(),
                    dept.getDpDeptid());
            Map<Long, Double> feeIds = new HashMap<>();
            feeIds.put(txMasList.get(0).getTaxId(), retireDto.getAmount().doubleValue());
            requestDTO.setFeeIds(feeIds);
            // requestDTO.
            receiptService.insertInReceiptMaster(requestDTO, null);
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void profitAndLossPosting(final RetirementDTO retireDto, final AuditDetailsDTO audit) {
        try {
            final AssetValuationDetailsDTO valuationDTO = valuationService.findLatestAssetId(retireDto.getOrgId(),
                    retireDto.getAssetId());
            if (valuationDTO == null || valuationDTO.getPreviousBookValue() == null) {
                LOGGER.error("vualuation is null or privious book value is null");
                throw new FrameworkException("vualuation is null or privious book value is null");
            }
            final Organisation organisation = iOrganisationService.getOrganisationById(retireDto.getOrgId());
            BigDecimal ProfitAndLossAmt;
            final VoucherPostExternalDTO vouPostDto = new VoucherPostExternalDTO();
            VoucherPostDetailExternalDTO vouPostDetExteDtoSPA = new VoucherPostDetailExternalDTO();
            VoucherPostDetailExternalDTO vouPostDetExteDtoALE = new VoucherPostDetailExternalDTO();
            VoucherPostDetailExternalDTO vouPostDetExteDtoAD = new VoucherPostDetailExternalDTO();
            final Department dept = deptService.findDepartmentByCode(retireDto.getDeptCode());
            // this case is for loss and SPA Sale Proceeds from Assets (Tax Master)
            Long tdpPrefixIdSPA = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.AssetManagement.SALE_PROCEEDS_FROM_ASSETS, MainetConstants.AssetManagement.TAX_PREFIX,
                    retireDto.getOrgId());
            List<TbTaxMas> txMasListSPA = taxMasService.findAllByDescId(tdpPrefixIdSPA, retireDto.getOrgId(),
                    dept.getDpDeptid());
            String accountCodeSPA = secondaryheadMasterService.findSacHeadCodeBySacHeadId(
                    taxMasService.fetchSacHeadIdForReceiptDet(retireDto.getOrgId(), txMasListSPA.get(0).getTaxId(), "A"));
            // this case is for loss and ALE Loss on disposal of fixed asset(Tax Master)
            Long tdpPrefixIdALE = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.AssetManagement.LOSS_ON_DISPOSAL_OF_FIXED_ASSET,
                    MainetConstants.AssetManagement.TAX_PREFIX,
                    retireDto.getOrgId());
            List<TbTaxMas> txMasListALE = taxMasService.findAllByDescId(tdpPrefixIdALE, retireDto.getOrgId(),
                    dept.getDpDeptid());
            String accountCodeALE = secondaryheadMasterService.findSacHeadCodeBySacHeadId(
                    taxMasService.fetchSacHeadIdForReceiptDet(retireDto.getOrgId(), txMasListALE.get(0).getTaxId(), "A"));
            // this case is for loss and AD Fixed Assets -Assets under Disposal (Tax Master)
            Long tdpPrefixIdAD = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.AssetManagement.AST_DISPOSAL_ACCT_TMPLT_PRF, MainetConstants.AssetManagement.TAX_PREFIX,
                    retireDto.getOrgId());
            List<TbTaxMas> txMasListAD = taxMasService.findAllByDescId(tdpPrefixIdAD, retireDto.getOrgId(),
                    dept.getDpDeptid());
            String accountCodeAD = secondaryheadMasterService.findSacHeadCodeBySacHeadId(
                    taxMasService.fetchSacHeadIdForReceiptDet(retireDto.getOrgId(), txMasListAD.get(0).getTaxId(), "A"));

            // this case is for profit and APE profit on disposal of fixed asset(Tax Master)
            Long tdpPrefixIdAPE = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.AssetManagement.AST_PROFIT_ACCT_TMPLT_PRF, MainetConstants.AssetManagement.TAX_PREFIX,
                    retireDto.getOrgId());
            List<TbTaxMas> txMasListAPE = taxMasService.findAllByDescId(tdpPrefixIdAPE, retireDto.getOrgId(),
                    dept.getDpDeptid());
            String accountCodeAPE = secondaryheadMasterService.findSacHeadCodeBySacHeadId(
                    taxMasService.fetchSacHeadIdForReceiptDet(retireDto.getOrgId(), txMasListAPE.get(0).getTaxId(), "A"));
            // this is only for loss case
            if (valuationDTO.getPreviousBookValue().compareTo(retireDto.getAmount()) > 0) {
                vouPostDto.setVousubTypeCode(MainetConstants.AssetManagement.LOSS_ON_DISPOSAL_OF_FIXED_ASSET);
                ProfitAndLossAmt = valuationDTO.getPreviousBookValue().subtract(retireDto.getAmount());
                vouPostDetExteDtoSPA.setAcHeadCode(accountCodeSPA);
                vouPostDetExteDtoSPA.setVoucherAmount(retireDto.getAmount()); // sold amount
                vouPostDetExteDtoSPA.setAccountHeadFlag(MainetConstants.AssetManagement.PAYMENT);
                vouPostDetExteDtoALE.setAcHeadCode(accountCodeALE);
                vouPostDetExteDtoALE.setVoucherAmount(ProfitAndLossAmt);// loss amount
                vouPostDetExteDtoALE.setAccountHeadFlag(MainetConstants.AssetManagement.PAYMENT);
                vouPostDetExteDtoAD.setAcHeadCode(accountCodeAD);
                vouPostDetExteDtoAD.setVoucherAmount(valuationDTO.getPreviousBookValue());// current book value
                vouPostDetExteDtoAD.setAccountHeadFlag(MainetConstants.AssetManagement.RECEIPT);
            } // this case is for profit only
            else if (valuationDTO.getPreviousBookValue().compareTo(retireDto.getAmount()) < 0) {
                vouPostDto.setVousubTypeCode(MainetConstants.AssetManagement.AST_PROFIT_ACCT_TMPLT_PRF);
                ProfitAndLossAmt = retireDto.getAmount().subtract(valuationDTO.getPreviousBookValue());
                vouPostDetExteDtoSPA.setAcHeadCode(accountCodeSPA);
                vouPostDetExteDtoSPA.setVoucherAmount(retireDto.getAmount());// sold amount
                vouPostDetExteDtoSPA.setAccountHeadFlag(MainetConstants.AssetManagement.PAYMENT);
                vouPostDetExteDtoALE.setAcHeadCode(accountCodeAPE);
                vouPostDetExteDtoALE.setVoucherAmount(ProfitAndLossAmt);// profit amount
                vouPostDetExteDtoALE.setAccountHeadFlag(MainetConstants.AssetManagement.RECEIPT);
                vouPostDetExteDtoAD.setAcHeadCode(accountCodeAD);
                vouPostDetExteDtoAD.setVoucherAmount(valuationDTO.getPreviousBookValue());// current book value
                vouPostDetExteDtoAD.setAccountHeadFlag(MainetConstants.AssetManagement.RECEIPT);

            } else {
                throw new FrameworkException("Asset book value and retire amount are same OR Action item are not defined");
            }
            // this VoucherPostExternalDTO is common for but child dto is different
            List<VoucherPostDetailExternalDTO> detailExtenalDtoList = new ArrayList<>();
            vouPostDto.setVoucherDate(Utility.dateToString(retireDto.getDispositionDate()));
            vouPostDto.setDepartmentCode(retireDto.getDeptCode());
            vouPostDto.setUlbCode(organisation.getOrgShortNm());
            vouPostDto.setVoucherReferenceNo(retireDto.getAssetCode());
            vouPostDto.setVoucherReferenceDate(Utility.dateToString(retireDto.getDisOrderDate()));
            vouPostDto.setNarration("ASSET DEPRECIATION FOR " + retireDto.getAssetCode());
            vouPostDto.setLocationDescription("1-1");
            vouPostDto.setPayerOrPayee(MainetConstants.AssetManagement.SYSTEM);
            vouPostDto.setCreatedBy(audit.getEmpId().toString());
            vouPostDto.setEntryCode(MainetConstants.AssetManagement.ASSET_ENTRY_CODE_OF_ACCOUNT_EXTERNAL);
            vouPostDto.setPayModeCode(MainetConstants.AssetManagement.PAY_MODE_TRANSFER);
            vouPostDto.setEntryFlag(MainetConstants.AssetManagement.CHECK_TRANSACTION_YES);
            detailExtenalDtoList.add(vouPostDetExteDtoSPA);
            detailExtenalDtoList.add(vouPostDetExteDtoALE);
            detailExtenalDtoList.add(vouPostDetExteDtoAD);
            vouPostDto.setVoucherExtDetails(detailExtenalDtoList);
            /* check account module is live or not */
            if (mainService.checkAccountActiveOrNot()) {
                mainService.externalVoucherPostingInAccount(retireDto.getOrgId(), vouPostDto);
            }
        } catch (Exception e) {
            throw new FrameworkException(e);
        }

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.asset.service.RetireService#initiateWorkFlow(com.abm.mainet. asset.ui.dto.RetirementDTO,
     * com.abm.mainet.asset.ui.dto.AuditDetailsDTO, java.lang.String)
     */
    @Override
    public void initiateWorkFlow(final RetirementDTO dto, final AuditDetailsDTO audit, final String workFlowFlag) {
    }

    @Override
    @Transactional
    public RetirementDTO getDetails(Long retireId) {
        AssetRetire entity = retire.findOne(retireId);
        if (entity == null) {
            throw new FrameworkException("Could not find retirement details for ID: " + retireId);
        }
        // initalize AssetInformation by calling getter
        entity.getAssetId().getAssetId();
        AssetInformation info = entity.getAssetId();
        RetirementDTO dto = RetireMapper.mapToDTO(entity);
        dto.setCurrentValue(getCurrentValue(info.getOrgId(), info.getAssetId()));

        return dto;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private WorkflowTaskActionResponse executeWorkflowAction(final String refId, final RetirementDTO rtrDto,
            final AuditDetailsDTO auditDto, final WorkflowTaskAction wfAction, String moduleDeptCode,
            String serviceCodeDeptWise) {
        WorkflowTaskActionResponse response = null;
        ServiceMaster serviceMast = iServiceMasterService.getServiceMasterByShortCode(
                serviceCodeDeptWise, rtrDto.getOrgId());
        // Prepare file upload
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(refId);
        requestDTO.setOrgId(rtrDto.getOrgId());
        requestDTO.setDepartmentName(moduleDeptCode);
        requestDTO.setServiceId(serviceMast.getSmServiceId());
        requestDTO.setUserId(auditDto.getEmpId());
        requestDTO.setApplicationId(rtrDto.getAssetId());
        requestDTO.setDeptId(serviceMast.getTbDepartment().getDpDeptid());
        // process and upload
        List<DocumentDetailsVO> attachments = prepareWfAttachments();
        attachments = fileUpload.prepareFileUpload(attachments);
        fileUpload.doFileUpload(attachments, requestDTO);
        List<Long> attIds = checkListService.fetchAllAttachIdByReferenceId(refId, rtrDto.getOrgId());
        // execute workflow task action
        WorkflowTaskAction workflowActionDtoTmp = prepareWorkFlowTaskActionUpdate(refId, wfAction.getTaskId(), attIds,
                auditDto, rtrDto.getOrgId());
        workflowActionDtoTmp.setDecision(wfAction.getDecision());
        response = assetWorkFlowService.initiateWorkFlowAssetService(workflowActionDtoTmp, wfAction.getTaskId(),
                MainetConstants.AssetManagement.ASSET_RETIRE_URL, MainetConstants.FlagU,
                serviceCodeDeptWise);

        return response;
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
            final WorkflowTaskAction wfAction, String moduleDeptCode,
            String serviceCodeDeptWise) {
        String rtrId = wfReferenceId
                .substring(wfReferenceId.lastIndexOf(MainetConstants.AssetManagement.WF_TXN_IDEN_RETIRE) + 4);
        RetirementDTO rtrDto = getDetails(Long.valueOf(rtrId));
        boolean retVal = false;
        WorkflowTaskActionResponse response = null;
        try {
            response = executeWorkflowAction(wfReferenceId, rtrDto, auditDto, wfAction, moduleDeptCode, serviceCodeDeptWise);
            retVal = true;
        } catch (Exception ex) {
            LOGGER.error("Exception occurred during workflow action execution ", ex);
            retVal = false;
        }
        if (response != null && !response.getIsProcessAlive()) {
            if (wfAction.getDecision().equals(MainetConstants.WorkFlow.Decision.APPROVED)) {
                doRetirement(rtrDto, auditDto, moduleDeptCode, serviceCodeDeptWise);
                retire.updateAuthStatus("A", auditDto.getEmpId(), new Date(), rtrDto.getAssetId(), rtrDto.getRetireId(),
                        rtrDto.getOrgId());
                // pass wfReferenceId for astAppNo
                retVal = infoUpdateService.updateAppStatusFlag(rtrDto.getAssetId(), rtrDto.getOrgId(),
                        MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED, wfReferenceId);
            } else {
                retire.updateAuthStatus("R", auditDto.getEmpId(), new Date(), rtrDto.getAssetId(), rtrDto.getRetireId(),
                        rtrDto.getOrgId());
                // pass wfReferenceId for astAppNo
                retVal = infoUpdateService.updateAppStatusFlag(rtrDto.getAssetId(), rtrDto.getOrgId(),
                        MainetConstants.AssetManagement.APPROVAL_STATUS_APPROVED, wfReferenceId);
            }
        }
        return retVal;
    }

    /*
     * @param orgId
     * @param assetId
     * @return this method is use for finding the current book value
     */
    private BigDecimal getCurrentValue(Long orgId, Long assetId) {
        BigDecimal currentValue = null;
        AssetValuationDetailsDTO valuationDTO = valuationService.findLatestAssetId(orgId, assetId);
        if (valuationDTO == null) {
            AssetPurchaseInformationDTO purDTO = infoService.getPurchaseInfo(assetId);
            if (purDTO != null && purDTO.getInitialBookValue() != null) {
                currentValue = purDTO.getInitialBookValue();
            } else if (purDTO != null && purDTO.getCostOfAcquisition() != null) {
                currentValue = purDTO.getCostOfAcquisition();
            }
        } else {
            currentValue = valuationDTO.getCurrentBookValue();
        }
        return currentValue;

    }
}
