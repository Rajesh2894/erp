package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.NonUniqueResultException;
import org.apache.commons.lang.StringUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountJournalVoucherEntryDao;
import com.abm.mainet.account.dao.PaymentEntryDao;
import com.abm.mainet.account.domain.AccountBillEntryDeductionDetEntity;
import com.abm.mainet.account.domain.AccountBillEntryExpenditureDetEntity;
import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.AccountPaymentDetEntity;
import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryDetailsEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;
import com.abm.mainet.account.domain.TdsAcknowledgementPaymentEntity;
import com.abm.mainet.account.domain.VoucherTemplateDetailEntity;
import com.abm.mainet.account.domain.VoucherTemplateMasterEntity;
import com.abm.mainet.account.dto.AccountBillEntryExpenditureDetBean;
import com.abm.mainet.account.dto.AdvanceEntryDTO;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.dto.RTGSPaymentEntryDTO;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.mapper.AdvanceEntryServiceMapper;
import com.abm.mainet.account.repository.AccountDepositRepository;
import com.abm.mainet.account.repository.AccountPaymentMasterJpaRepository;
import com.abm.mainet.account.repository.AccountVoucherEntryRepository;
import com.abm.mainet.account.repository.AdvanceEntryRepository;
import com.abm.mainet.account.repository.BillEntryRepository;
import com.abm.mainet.account.repository.ContraEntryVoucherRepository;
import com.abm.mainet.account.repository.TbAcChequebookleafMasJpaRepository;
import com.abm.mainet.account.repository.TdsAcknowledgementPaymentMasterJpaRepository;
import com.abm.mainet.account.repository.VoucherTemplateRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountJournalVoucherEntry;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.constant.PrefixConstants.DirectPaymentEntry;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.integration.acccount.dto.PaymentDetailsDto;
import com.abm.mainet.common.integration.acccount.dto.RTGSPaymentDetailsDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.acccount.repository.SecondaryheadMasterJpaRepository;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.dms.dao.IAttachDocsDao;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.repository.TbAcVendormasterJpaRepository;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

@Service
public class PaymentEntryServiceImpl implements PaymentEntrySrevice {

    @Resource
    private BillEntryRepository billEntryRepository;
    @Resource
    private AccountPaymentMasterJpaRepository paymentEntryRepository;
    @Resource
    private AccountContraVoucherEntryService contraVoucherEntryService;
    @Resource
    ContraEntryVoucherRepository contraEntryVoucherJpaRepository;
    @Resource
    private AccountVoucherPostService accountVoucherPostService;
    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private AdvanceEntryServiceMapper advanceEntryServiceMapper;
    @Resource
    private AdvanceEntryRepository advanceEntryRepository;
    @Resource
    private AdvanceEntryService advanceEntryService;

    @Resource
    private PaymentEntryDao paymentEntryDao;
    @Resource
    private TbAcVendormasterJpaRepository vendorRepository;
    @Resource
    TbAcChequebookleafMasJpaRepository chequeBookLeafRepository;
    @Resource
    AccountJournalVoucherEntryDao journalVoucherDao;
    @Resource
    private VoucherTemplateRepository voucherTemplateRepository;

    @Resource
    private AccountVoucherEntryRepository accountVoucherEntryRepository;

    @Resource
    private DepartmentService departmentService;
    @Resource
    private AccountDepositRepository accountDepositJparepository;
    @Resource
    private SecondaryheadMasterJpaRepository tbAcSecondaryheadMasterJpaRepository;
    @Resource
    private TdsAcknowledgementPaymentMasterJpaRepository tdsAcknowledgementPaymentMasterJpaRepository;
    @Autowired
    private IWorkflowExecutionService workflowExecutionService;
    @Autowired
    private ServiceMasterService serviceMasterService;
    @Autowired
    private IWorkflowTyepResolverService workflowTyepResolverService;
    @Resource
    private TbFinancialyearService tbFinancialyearService;
    @Resource
	private AccountFieldMasterService accountFieldMasterService;
    @Autowired
   	private IAttachDocsDao iAttachDocsDao;
    @Resource
    private TbAcVendormasterService vendorMasterService;

    private static final String SEQUENCE_NO_ERROR = "unable to generate sequenceNo for orgId=";
    private static final String SEQUENCE_NO = "0000000000";
    private static final String TEMPLATE_ID_NOT_FOUND_VOU = "templateTypeId not found for provided parameter[voucherType=";
    private static final String ORG_ID = ", orgId=";
    private static final String PREFIX_VOT = ",prefix=VOT]";

    private static final Logger LOGGER = Logger.getLogger(PaymentEntryServiceImpl.class);

    private static final String TB_AC_PAYMENT_MAS = "TB_AC_PAYMENT_MAS";
    private static final String PAYMENT_NO = "PAYMENT_NO";

    private final String TB_AC_ADVANCE = "TB_AC_ADVANCE";
    private final String PAY_ADVANCE_NO = "PAY_ADVANCE_NO";
    private final String PAYMODE = "pay mode is not mapped against payment entry";
    private final String ACHEADCODE = "account head is not mapped against payment entry - pay mode";

    private static final String GENERATE_PAYMENT_NO_FIN_YEAR_ID = "Payment sequence number generation, The financial year id is getting null value";
    private static final String GENERATE_VOUCHER_NO_FIN_YEAR_ID = "Voucher sequence number generation, The financial year id is getting null value";
    private static final String GENERATE_ADVANCE_NO_FIN_YEAR_ID = "Advance sequence number generation, The financial year id is getting null value";
    private static final String GENERATE_VOUCHER_NO_VOU_TYPE_ID = "Voucher sequence number generation, The voucher type id is getting null value";

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.PaymentEntrySrevice#createPaymentEntry(com.abm .mainet.account.dto.PaymentEntryDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentEntryDto createPaymentEntry(final PaymentEntryDto paymentEntryDto) throws ParseException {

        AccountPaymentMasterEntity paymentEntity = new AccountPaymentMasterEntity();
        TbComparamDetEntity comparamDetId = null;
        BankAccountMasterEntity bankAccount = null;
        TbAcVendormasterEntity vendorId = null;
        Long lookUpIdCash = null;
        Long lookUpIdAdj = null;
        Long lookUpIdBank = null;
        Long lookUpIdpaytCash = null;
        paymentEntity.setPaymentDate(paymentEntryDto.getPaymentDate());

        // Work flow
        /*
         * ServiceMaster service = serviceMasterService.getServiceByShortName("PE", paymentEntryDto.getOrgId()); if(service ==
         * null) return null; WorkflowMas workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
         * service.getTbDepartment().getDpDeptid(), service.getSmServiceId(), null, null, null, null, null); if (workflowMas !=
         * null) { try { WorkflowProcessParameter processParameter = AccountWorkflowUtility
         * .prepareInitAccountPaymentEntryProcessParameter(paymentEntryDto, workflowMas, paymentEntity.getPaymentNo());
         * workflowExecutionService.initiateWorkflow(processParameter); } catch (Exception e) { LOGGER.error(
         * "Unsuccessful initiation of task for application : " + paymentEntity.getPaymentNo()); try { throw new Exception(
         * "Unsuccessful initiation of task for application : " + paymentEntity.getPaymentNo()); } catch (Exception e1) { // TODO
         * Auto-generated catch block e1.printStackTrace(); } } }
         */
        /* End Of Work Flow */

        // payment type id
        if (paymentEntryDto.getPaymentTypeId() != null) {
            paymentEntity.setPmdId(paymentEntryDto.getPaymentTypeId());
        }
        // Bill type id
        comparamDetId = new TbComparamDetEntity();
        comparamDetId.setCpdId(paymentEntryDto.getBillTypeId());
        paymentEntity.setBillTypeId(comparamDetId);

        // Vendor id
        vendorId = new TbAcVendormasterEntity();
        vendorId.setVmVendorid(paymentEntryDto.getVendorId());
        paymentEntity.setVmVendorId(vendorId);

        // Pay mode
        comparamDetId = new TbComparamDetEntity();
        comparamDetId.setCpdId(paymentEntryDto.getPaymentMode());
        paymentEntity.setPaymentModeId(comparamDetId);

        // Bank acc id
        bankAccount = new BankAccountMasterEntity();
        if (paymentEntryDto.getBankAcId() != null) {
            bankAccount.setBaAccountId(paymentEntryDto.getBankAcId());
            paymentEntity.setBaBankAccountId(bankAccount);
        }
        final LookUp lookUpCash = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.CASH.getValue(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getLanguageId().intValue(),
                paymentEntryDto.getOrganisation());
        final LookUp lookUpPaytCash = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.PCA.getValue(), AccountPrefix.PAY.toString(),
                paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation());
        final LookUp lookUpAdj = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.A.getValue(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getLanguageId().intValue(),
                paymentEntryDto.getOrganisation());
        final LookUp lookUpBank = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MENU.B,
                AccountPrefix.PAY.toString(), paymentEntryDto.getLanguageId().intValue(),
                paymentEntryDto.getOrganisation());
        lookUpIdCash = lookUpCash.getLookUpId();
        lookUpIdAdj = lookUpAdj.getLookUpId();
        lookUpIdBank = lookUpBank.getLookUpId();
        lookUpIdpaytCash = lookUpPaytCash.getLookUpId();
        if ((paymentEntryDto.getPaymentMode() != null) && !paymentEntryDto.getPaymentMode().equals(lookUpIdCash)
                && !paymentEntryDto.getPaymentMode().equals(lookUpIdAdj)
                && !paymentEntryDto.getPaymentMode().equals(lookUpIdBank)
                && !paymentEntryDto.getPaymentMode().equals(lookUpIdpaytCash)) {
            paymentEntity.setInstrumentNumber(paymentEntryDto.getInstrumentNo());
            paymentEntity.setInstrumentDate(
                    UtilityService.convertStringDateToDateFormat(paymentEntryDto.getInstrumentDate()));
        }
        if (paymentEntryDto.getPaymentMode().equals(lookUpIdBank)) {
            paymentEntity.setUtrNo(paymentEntryDto.getUtrNo());
        }
        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            paymentEntity.setAuthoDate(paymentEntryDto.getPaymentDate());
        } else {
            paymentEntity.setAuthoDate(paymentEntryDto.getCreatedDate());
        }
        paymentEntity.setAuthoFlg(AccountConstants.Y.toString());
        paymentEntity.setAuthoId(paymentEntryDto.getCreatedBy());
        paymentEntity.setPaymentTypeFlag(0L);

        // If selected bill type is advance
        final Organisation organisation = new Organisation();
        organisation.setOrgid(paymentEntryDto.getOrgId());
        final int langId = Integer.parseInt(paymentEntryDto.getLanguageId().toString());

        saveCommonFields(paymentEntity, paymentEntryDto);
        savePaymentDetails(paymentEntryDto, paymentEntity, langId, organisation);
        Objects.requireNonNull(paymentEntryDto.getTransactionDate(),
                ApplicationSession.getInstance().getMessage("payment.entry.service.transactiondate") + paymentEntryDto);
        paymentEntity.setPaymentDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));

        BigDecimal finalPaymentAmt = BigDecimal.ZERO;
        BigDecimal paymentDetTotalAmt = BigDecimal.ZERO;
        finalPaymentAmt = paymentEntity.getPaymentAmount().setScale(2, RoundingMode.CEILING);
        List<AccountPaymentDetEntity> paymentDetList = paymentEntity.getPaymentDetailList();
        for (AccountPaymentDetEntity accountPaymentDetEntity : paymentDetList) {
            if (accountPaymentDetEntity.getPaymentAmt() != null) {
                paymentDetTotalAmt = paymentDetTotalAmt
                        .add(accountPaymentDetEntity.getPaymentAmt().setScale(2, RoundingMode.CEILING));
                // paymentDetTotalAmt = paymentDetTotalAmt.setScale(2, RoundingMode.CEILING);
            }
        }
        if (!finalPaymentAmt.equals(paymentDetTotalAmt)) {
            throw new IllegalArgumentException("Discrepancy found in payment amount.");
        }

        // payment voucher posting validate to given VoucherPostDTO
        paymentVoucherPostingValidation(paymentEntryDto);
       
       //payment no coustomization start
       //coustom sequence generation need to configure
   	   String paymentNo=null;
       SequenceConfigMasterDTO configMasterDTO = null;
       Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.AccountConstants.AC.getValue(),
               PrefixConstants.STATUS_ACTIVE_PREFIX);
       configMasterDTO = seqGenFunctionUtility.loadSequenceData(paymentEntryDto.getOrgId(), deptId,
               MainetConstants.AccountContraVoucherEntry.TB_AC_PAYMENT_MAS2, MainetConstants.AccountContraVoucherEntry.PAYMENT_NO2);
       if (configMasterDTO.getSeqConfigId() == null) {
             paymentNo = generatePaymentNumber(paymentEntryDto.getOrgId(), paymentEntryDto.getPaymentDate());
        }else {
       	 CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
       	 AccountFieldMasterBean fieldMaster = accountFieldMasterService.findById(paymentEntryDto.getFieldId());
    	 if(fieldMaster!=null)
         commonDto.setCustomField(fieldMaster.getFieldDesc());
       	 paymentNo=seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
         }
        //payment no coustomization end
        paymentEntryDto.setPaymentNo(paymentNo);
        paymentEntity.setPaymentNo(paymentNo);
        paymentEntity.setFieldId(paymentEntryDto.getFieldId());
        paymentEntity = paymentEntryRepository.save(paymentEntity);

        String paymentType = CommonMasterUtility.findLookUpCode(AccountPrefix.PDM.toString(), organisation.getOrgid(),
                paymentEntryDto.getPaymentTypeId());

        final LookUp advanceLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.AD.toString(),
                AccountPrefix.ABT.toString(), langId, organisation);
        final Long advLookUpId = advanceLookup.getLookUpId();
        if (paymentEntryDto.getBillTypeId().equals(advLookUpId)) {
            if (paymentType.equals("W")) {
                postAdvanceEntryBillSumaryWise(paymentEntryDto);
            } else {
                postAdvanceEntry(paymentEntryDto);
            }
        }
        postPaymentEntry(paymentEntryDto);
        updatePaymentStatusForBill(paymentEntity);
        if (paymentEntryDto.getInstrumentNo() != null) {
            updateChequeNoStatus(paymentEntryDto.getInstrumentNo(), paymentEntryDto.getOrgId(),
                    paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation(),
                    paymentEntity.getPaymentId(), paymentEntity.getPaymentDate(), AccountConstants.P.toString());
        }
        // Task #7144 for this task i need to set paymentId
        paymentEntryDto.setPaymentId(paymentEntity.getPaymentId());
        return paymentEntryDto;
    }

    private void paymentVoucherPostingValidation(PaymentEntryDto paymentEntryDto) {
        LOGGER.info("Process for account payment voucher posting validation:" + paymentEntryDto);

        final VoucherPostDTO postDto = new VoucherPostDTO();
        List<VoucherPostDTO> postDtoList = new ArrayList<>();
        final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
        final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
        postDto.setVoucherSubTypeId(voucherSubTypeId);

        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
            postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
        } else {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        }
        postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
        postDto.setDepartmentId(departmentId);
        // postDto.setVoucherReferenceNo(paymentEntryDto.getPaymentNo());

        postDto.setNarration(paymentEntryDto.getNarration());
        postDto.setPayerOrPayee(paymentEntryDto.getVendorDesc());// Vendor name

        postDto.setFieldId(paymentEntryDto.getFieldId());
        postDto.setOrgId(paymentEntryDto.getOrgId());
        postDto.setCreatedBy(paymentEntryDto.getCreatedBy());
        postDto.setCreatedDate(paymentEntryDto.getCreatedDate());
        postDto.setLgIpMac(paymentEntryDto.getLgIpMacAddress());
        postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

        Long lookUpIdCash = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.C.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdAdj = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.A.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdPetty = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        if (paymentEntryDto.getPaymentMode().equals(lookUpIdCash)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdAdj)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdPetty)) {
            // Cash Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long templateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                    AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
            final Long activeStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.IsLookUp.ACTIVE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, paymentEntryDto.getOrgId());
            VoucherTemplateMasterEntity entity = voucherTemplateRepository
                    .findAcHeadCodeInTemplateForCashMode(templateFor, paymentEntryDto.getOrgId(), activeStatus);
            Long sacHeadIdCr = null;
            List<VoucherTemplateDetailEntity> detList = entity.getTemplateDetailEntities();
            for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : detList) {
                if (voucherTemplateDetailEntity.getCpdIdPayMode().equals(paymentEntryDto.getPaymentMode())) {
                    if (voucherTemplateDetailEntity.getSacHeadId() != null) {
                        sacHeadIdCr = voucherTemplateDetailEntity.getSacHeadId();
                        break;
                    }
                }
            }
            if (sacHeadIdCr == null) {
                throw new NullPointerException(PAYMODE + " templateFor is : " + templateFor + " pay mode is : "
                        + paymentEntryDto.getPaymentMode() + " orgid is : " + paymentEntryDto.getOrgId()
                        + " status is active : " + activeStatus);
            }
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        } else {
            // Bank Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            if (paymentEntryDto.getTotalPaymentAmount() != null) {
                postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            }
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long sacHeadIdCr = getSacHeadIdByBankAccountId(paymentEntryDto.getBankAcId(),
                    paymentEntryDto.getOrgId());
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        }

        // Vendor Debit side
        final VoucherPostDetailDTO postDetailDtoDr = new VoucherPostDetailDTO();
        final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                PrefixConstants.DCR, paymentEntryDto.getOrgId());
        postDetailDtoDr.setDrCrId(drId);
        postDetailDtoDr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
        Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), paymentEntryDto.getOrgId());
        final Long vendorSacHeadIdDr = billEntryRepository.getVendorSacHeadIdByVendorId(paymentEntryDto.getVendorId(),
                paymentEntryDto.getOrgId(), status);
        postDetailDtoDr.setSacHeadId(vendorSacHeadIdDr);
        voucherDetails.add(postDetailDtoDr);

        postDto.setVoucherDetails(voucherDetails);
        postDtoList.add(postDto);
        List<String> responseValidation = accountVoucherPostService.checkVoucherPostValidateInput(postDtoList);
        if (responseValidation.size() > 0) {
            throw new NullPointerException(
                    "improper input parameter for VoucherPostDTO in payment entry -> " + responseValidation);
        }
    }

    // Generate payment sequence number
    private String generatePaymentNumber(final Long orgId, Date paymentDate) {
        Long finYearId = tbFinancialyearService.getFinanciaYearIdByFromDate(paymentDate);
        if (finYearId == null) {
            throw new NullPointerException(GENERATE_PAYMENT_NO_FIN_YEAR_ID);
        }
        final Long paymentNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                TB_AC_PAYMENT_MAS, PAYMENT_NO, orgId, MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE,
                finYearId);
        return paymentNumber.toString();
    }

    // Save common entity fields
    private void saveCommonFields(final AccountPaymentMasterEntity paymentEntity,
            final PaymentEntryDto paymentEntryDto) {
        paymentEntity.setPaymentAmount(paymentEntryDto.getTotalPaymentAmount());
        paymentEntity.setOrgId(paymentEntryDto.getOrgId());
        paymentEntity.setNarration(paymentEntryDto.getNarration());
        paymentEntity.setCreatedBy(paymentEntryDto.getCreatedBy());
        paymentEntity.setCreatedDate(paymentEntryDto.getCreatedDate());
        // paymentEntity.setLangId(paymentEntryDto.getLanguageId());
        paymentEntity.setLgIpMac(paymentEntryDto.getLgIpMacAddress());
    }

    private void savePaymentDetails(final PaymentEntryDto paymentEntryDto,
            final AccountPaymentMasterEntity paymentEntity, int langId, Organisation organisation) {
        BigDecimal netPayableAmount = new BigDecimal(0.00);
        BigDecimal netAmount = new BigDecimal(0.00);
        Integer count = 1;
        int countFound = 0;
        AccountPaymentDetEntity paymentDetailsEntity = null;
        final List<AccountPaymentDetEntity> paymentDetailsList = new ArrayList<>();

        final List<PaymentDetailsDto> billDetails = paymentEntryDto.getPaymentDetailsDto();

        String paymentType = CommonMasterUtility.findLookUpCode(AccountPrefix.PDM.toString(), organisation.getOrgid(),
                paymentEntryDto.getPaymentTypeId());

        String billType = CommonMasterUtility.findLookUpCode(AccountPrefix.ABT.toString(), organisation.getOrgid(),
                paymentEntryDto.getBillTypeId());

        if ((billDetails != null) && !billDetails.isEmpty()) {
            for (final PaymentDetailsDto billDetail : billDetails) {

                if (paymentType.equals("W")) {

                    AccountBillEntryMasterEnitity billEnryEntity = billEntryRepository
                            .findBillEntryById(paymentEntryDto.getOrgId(), billDetail.getId());

                    if (billType.equals("ESB")) {

                        BigDecimal ratio = BigDecimal.ZERO;
                        BigDecimal sumOfBillBalanceAmount = BigDecimal.ZERO;
                        BigDecimal sumOfBillDeductionAmount = BigDecimal.ZERO;

                        List<AccountBillEntryExpenditureDetEntity> expList = billEnryEntity.getExpenditureDetailList();
                        for (AccountBillEntryExpenditureDetEntity expBillEntryDetails : expList) {
                            sumOfBillBalanceAmount = sumOfBillBalanceAmount
                                    .add(expBillEntryDetails.getBillChargesAmount());
                        }
                        List<AccountBillEntryDeductionDetEntity> dedList = billEnryEntity.getDeductionDetailList();
                        if (dedList != null && !dedList.isEmpty()) {
                            for (AccountBillEntryDeductionDetEntity dedBillEntryDetails : dedList) {
                                sumOfBillDeductionAmount = sumOfBillDeductionAmount
                                        .add(dedBillEntryDetails.getDeductionAmount());
                            }
                        }

                        BigDecimal sumOfBillProRataBalanceAmount = sumOfBillBalanceAmount.setScale(0,
                                BigDecimal.ROUND_HALF_EVEN);
                        BigDecimal sumOfBillProRataDeductionAmount = sumOfBillDeductionAmount.setScale(0,
                                BigDecimal.ROUND_HALF_EVEN);
                        netPayableAmount = sumOfBillProRataBalanceAmount.subtract(sumOfBillProRataDeductionAmount);
                        ratio = sumOfBillProRataDeductionAmount.divide(sumOfBillProRataBalanceAmount,
                                MathContext.DECIMAL128);
                        // BigDecimal proRataRatio = ratio.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                        List<AccountBillEntryExpenditureDetEntity> expListProRata = billEnryEntity
                                .getExpenditureDetailList();
                        Integer listSize = expListProRata.size();
                        BigDecimal newNetBalPayableAmt = BigDecimal.ZERO;
                        for (AccountBillEntryExpenditureDetEntity expBillEntryProRataDetails : expListProRata) {

                            BigDecimal proRataDeductionAmt = BigDecimal.ZERO;
                            BigDecimal proRataPayableAmt = BigDecimal.ZERO;
                            // BigDecimal proRataPaymentAmt = BigDecimal.ZERO;

                            paymentDetailsEntity = new AccountPaymentDetEntity();
                            paymentDetailsEntity.setBchIdExpenditure(expBillEntryProRataDetails.getId());
                            paymentDetailsEntity.setBudgetCodeId(expBillEntryProRataDetails.getSacHeadId());

                            proRataDeductionAmt = expBillEntryProRataDetails.getBillChargesAmount().multiply(ratio)
                                    .setScale(2, BigDecimal.ROUND_HALF_EVEN);
                            proRataPayableAmt = expBillEntryProRataDetails.getBillChargesAmount()
                                    .subtract(proRataDeductionAmt);
                            BigDecimal amount = proRataPayableAmt.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                            if (expBillEntryProRataDetails.getFi04V1() != null
                                    && !expBillEntryProRataDetails.getFi04V1().isEmpty()
                                    && !expBillEntryProRataDetails.getFi04V1().equals("0.00")) {
                                newNetBalPayableAmt = newNetBalPayableAmt
                                        .add(new BigDecimal(expBillEntryProRataDetails.getFi04V1()));
                            }

                            if (newNetBalPayableAmt != null && newNetBalPayableAmt != BigDecimal.ZERO) {
                                // netAmount = netAmount.add(newNetBalPayableAmt);
                                BigDecimal balPaymentAmount = new BigDecimal(expBillEntryProRataDetails.getFi04V1());
                                if (count.equals(listSize)) {
                                    BigDecimal sumPaymentAmount = billEntryRepository
                                            .findPaymentAmount(billEnryEntity.getId(), billEnryEntity.getOrgId());
                                    if (sumPaymentAmount != null) {
                                        newNetBalPayableAmt = newNetBalPayableAmt.add(sumPaymentAmount);
                                    }
                                    if (newNetBalPayableAmt.compareTo(netPayableAmount) == 1) {
                                        BigDecimal finalBalRatioAmount = newNetBalPayableAmt.subtract(netPayableAmount);
                                        balPaymentAmount = balPaymentAmount.subtract(finalBalRatioAmount);
                                    } else if (newNetBalPayableAmt.compareTo(netPayableAmount) == -1) {
                                        BigDecimal finalBalRatioAmount = netPayableAmount.subtract(newNetBalPayableAmt);
                                        balPaymentAmount = balPaymentAmount.add(finalBalRatioAmount);
                                    }
                                    // newNetBalPayableAmt = newNetBalPayableAmt.subtract(sumPaymentAmount);
                                }
                                count++;
                                paymentDetailsEntity.setPaymentAmt(balPaymentAmount); // set amount for different
                            } else {
                                netAmount = netAmount.add(amount);
                                if (count.equals(listSize)) {
                                    if (netAmount.compareTo(netPayableAmount) == 1) {
                                        BigDecimal finalBalRatioAmount = netAmount.subtract(netPayableAmount);
                                        amount = amount.subtract(finalBalRatioAmount);
                                    } else if (netAmount.compareTo(netPayableAmount) == -1) {
                                        BigDecimal finalBalRatioAmount = netPayableAmount.subtract(netAmount);
                                        amount = amount.add(finalBalRatioAmount);
                                    }
                                }
                                count++;
                                paymentDetailsEntity.setPaymentAmt(amount); // set amount for different
                            }
                            // expenditure
                            paymentDetailsEntity.setBillId(billDetail.getId());// Column name To be renamed
                            paymentDetailsEntity.setOrgId(paymentEntity.getOrgId());
                            paymentDetailsEntity.setCreatedBy(paymentEntity.getCreatedBy());
                            paymentDetailsEntity.setCreatedDate(paymentEntity.getCreatedDate());
                            paymentDetailsEntity.setLangId(Long.valueOf(langId));
                            paymentDetailsEntity.setLgIpMac(paymentEntity.getLgIpMac());
                            paymentDetailsEntity.setPaymentMasterId(paymentEntity);
                            // Deposit payable amount updation
                            final LookUp depLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                                    MainetConstants.AccountBillEntry.DE, AccountPrefix.ABT.toString(), langId,
                                    organisation);
                            Long depLookUpId = depLookup.getLookUpId();
                            if (depLookUpId != null) {
                                if (depLookUpId.equals(paymentEntryDto.getBillTypeId())) {
                                    BigDecimal oldDepDefundAmt = accountDepositJparepository
                                            .getDepDefundAmountDetails(billDetail.getId(), paymentEntryDto.getOrgId());
                                    if (oldDepDefundAmt != null) {
                                        BigDecimal newDepDefundAmt = oldDepDefundAmt
                                                .subtract(billDetail.getPaymentAmount());
                                        accountDepositJparepository.updateDepDelRefundAmount(billDetail.getId(),
                                                paymentEntryDto.getOrgId(), newDepDefundAmt);
                                        if (newDepDefundAmt.compareTo(BigDecimal.ZERO) == 0
                                                || (newDepDefundAmt.compareTo(new BigDecimal(0.00)) == 0)) {
                                            final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                                                    PrefixConstants.AccountBillEntry.DR,
                                                    PrefixConstants.NewWaterServiceConstants.RDC,
                                                    paymentEntryDto.getOrgId());
                                            accountDepositJparepository.updateDepositBalanceAmountInDepositTableBybillMasterId(
                                                    billDetail.getId(), statusId,
                                                    paymentEntryDto.getOrgId());
                                        }
                                    }
                                }
                            }
                            paymentDetailsList.add(paymentDetailsEntity);

                        }

                    } else {
                        final List<Object[]> expenditures = getExpenditureDetails(billDetail.getId(),
                                paymentEntryDto.getOrgId());
                        // BigDecimal newNetBalPayableAmt = BigDecimal.ZERO;
                        if ((expenditures != null) && !expenditures.isEmpty()) {
                            for (final Object[] exp : expenditures) {
                                paymentDetailsEntity = new AccountPaymentDetEntity();
                                paymentDetailsEntity.setBchIdExpenditure((Long) exp[0]);
                                paymentDetailsEntity.setBudgetCodeId((Long) exp[2]);
                                // remaining payment amount in getting bill expenditure table column on fi04V1
                                BigDecimal newNetBalPayableAmt = BigDecimal.ZERO;
                                if (exp[3] != null) {
                                    newNetBalPayableAmt = newNetBalPayableAmt
                                            .add(new BigDecimal(String.valueOf(exp[3])));
                                }
                                if (newNetBalPayableAmt != null && newNetBalPayableAmt != BigDecimal.ZERO) {
                                    paymentDetailsEntity.setPaymentAmt(newNetBalPayableAmt); // set amount for different
                                } else {
                                    BigDecimal totalDeductionAmount = BigDecimal.ZERO;
                                    final List<AccountBillEntryDeductionDetEntity> dedDetList = billEnryEntity
                                            .getDeductionDetailList();
                                    if ((dedDetList != null) && !dedDetList.isEmpty()) {
                                        for (final AccountBillEntryDeductionDetEntity dedDetEntity : dedDetList) {
                                            if (dedDetEntity.getBchId() != null) {
                                                if (dedDetEntity.getBchId()
                                                        .equals(paymentDetailsEntity.getBudgetCodeId())) {
                                                    if (dedDetEntity.getDeductionAmount() != null) {
                                                        totalDeductionAmount = totalDeductionAmount
                                                                .add(dedDetEntity.getDeductionAmount());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    paymentDetailsEntity.setPaymentAmt(
                                            new BigDecimal(exp[1].toString()).subtract(totalDeductionAmount)); // set
                                                                                                               // amount
                                                                                                               // for
                                                                                                               // different
                                }
                                // expenditure
                                paymentDetailsEntity.setBillId(billDetail.getId());// Column name To be renamed
                                paymentDetailsEntity.setOrgId(paymentEntity.getOrgId());
                                paymentDetailsEntity.setCreatedBy(paymentEntity.getCreatedBy());
                                paymentDetailsEntity.setCreatedDate(paymentEntity.getCreatedDate());
                                paymentDetailsEntity.setLangId(Long.valueOf(langId));
                                paymentDetailsEntity.setLgIpMac(paymentEntity.getLgIpMac());
                                paymentDetailsEntity.setPaymentMasterId(paymentEntity);
                                // Deposit payable amount updation
                                final LookUp depLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                                        MainetConstants.AccountBillEntry.DE, AccountPrefix.ABT.toString(), langId,
                                        organisation);
                                Long depLookUpId = depLookup.getLookUpId();
                                if (depLookUpId != null) {
                                    if (depLookUpId.equals(paymentEntryDto.getBillTypeId())) {
                                        BigDecimal oldDepDefundAmt = accountDepositJparepository
                                                .getDepDefundAmountDetails(billDetail.getId(),
                                                        paymentEntryDto.getOrgId());
                                        if (oldDepDefundAmt != null) {
                                            BigDecimal newDepDefundAmt = oldDepDefundAmt
                                                    .subtract(billDetail.getPaymentAmount());
                                            accountDepositJparepository.updateDepDelRefundAmount(billDetail.getId(),
                                                    paymentEntryDto.getOrgId(), newDepDefundAmt);
                                            if (newDepDefundAmt.compareTo(BigDecimal.ZERO) == 0
                                                    || (newDepDefundAmt.compareTo(new BigDecimal(0.00)) == 0)) {
                                                final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                                                        PrefixConstants.AccountBillEntry.DR,
                                                        PrefixConstants.NewWaterServiceConstants.RDC,
                                                        paymentEntryDto.getOrgId());
                                                accountDepositJparepository
                                                        .updateDepositBalanceAmountInDepositTableBybillMasterId(
                                                                billDetail.getId(), statusId,
                                                                paymentEntryDto.getOrgId());
                                            }
                                        }
                                    }
                                }
                                paymentDetailsList.add(paymentDetailsEntity);
                            }
                        }
                    }
                } else {

                    AccountBillEntryMasterEnitity billEnryEntity = billEntryRepository
                            .findBillEntryById(paymentEntryDto.getOrgId(), billDetail.getId());
                    BigDecimal sumOfBillBalanceAmount = BigDecimal.ZERO;
                    BigDecimal sumOfBillDeductionAmount = BigDecimal.ZERO;
                    List<AccountBillEntryExpenditureDetEntity> expList = billEnryEntity.getExpenditureDetailList();
                    for (AccountBillEntryExpenditureDetEntity expBillEntryDetails : expList) {
                        sumOfBillBalanceAmount = sumOfBillBalanceAmount.add(expBillEntryDetails.getBillChargesAmount());
                    }
                    List<AccountBillEntryDeductionDetEntity> dedList = billEnryEntity.getDeductionDetailList();
                    if (dedList != null && !dedList.isEmpty()) {
                        for (AccountBillEntryDeductionDetEntity dedBillEntryDetails : dedList) {
                            sumOfBillDeductionAmount = sumOfBillDeductionAmount
                                    .add(dedBillEntryDetails.getDeductionAmount());
                        }
                    }

                    BigDecimal sumOfBillProRataBalanceAmount = sumOfBillBalanceAmount.setScale(2,
                            BigDecimal.ROUND_HALF_EVEN);
                    BigDecimal sumOfBillProRataDeductionAmount = sumOfBillDeductionAmount.setScale(2,
                            BigDecimal.ROUND_HALF_EVEN);
                    netPayableAmount = sumOfBillProRataBalanceAmount.subtract(sumOfBillProRataDeductionAmount);

                    boolean bmIdIsExistOrNot = false;
                    try {
                        int bmIdIsExistOrNotInt = billEntryRepository.findbmIdIsExistOrNot(billDetail.getId(),
                                billDetail.getBchId(),
                                paymentEntryDto.getOrgId());
                        if (bmIdIsExistOrNotInt == 0) {
                            bmIdIsExistOrNot = false;
                        } else {
                            bmIdIsExistOrNot = true;
                        }
                    } catch (Exception e) {
                        bmIdIsExistOrNot = false;
                        // TODO: handle exception
                    }
                    BigDecimal sumOfNetAmount = BigDecimal.ZERO;
                    if (countFound == 0) {
                        if (bmIdIsExistOrNot) {
                            countFound++;
                            sumOfNetAmount = billEntryRepository.findPaymentAmount(billDetail.getId(),
                                    paymentEntryDto.getOrgId());
                        }
                    }
                    sumOfNetAmount = billDetail.getPaymentAmount().add(sumOfNetAmount);
                    if (sumOfNetAmount.compareTo(netPayableAmount) == 1) {
                        billDetail.setPaymentAmount(billDetail.getPaymentAmount().subtract(new BigDecimal(1)));
                    } else if (sumOfNetAmount.compareTo(netPayableAmount) == -1) {
                        BigDecimal subPaymentAmt = netPayableAmount.subtract(sumOfNetAmount);
                        if (subPaymentAmt.compareTo(new BigDecimal(1)) == 0) {
                            billDetail.setPaymentAmount(billDetail.getPaymentAmount().add(new BigDecimal(1)));
                        }
                    }

                    paymentDetailsEntity = new AccountPaymentDetEntity();
                    paymentDetailsEntity.setBchIdExpenditure(
                            getExpenditureBchId(billDetail.getBchId(), billDetail.getId(), paymentEntryDto.getOrgId()));
                    paymentDetailsEntity.setBudgetCodeId(billDetail.getBchId());
                    paymentDetailsEntity.setPaymentAmt(billDetail.getPaymentAmount()); // set amount for different
                                                                                       // expenditure
                    paymentDetailsEntity.setBillId(billDetail.getId());// Column name To be renamed
                    paymentDetailsEntity.setOrgId(paymentEntity.getOrgId());
                    paymentDetailsEntity.setCreatedBy(paymentEntity.getCreatedBy());
                    paymentDetailsEntity.setCreatedDate(paymentEntity.getCreatedDate());
                    paymentDetailsEntity.setLangId(Long.valueOf(langId));
                    paymentDetailsEntity.setLgIpMac(paymentEntity.getLgIpMac());

                    BigDecimal newBalAmt = new BigDecimal(billDetail.getNetPayable().replaceAll(",", ""))
                            .subtract(billDetail.getPaymentAmount());
                    billEntryRepository.updateExpenditureBalanceAmt(paymentDetailsEntity.getBchIdExpenditure(),
                            newBalAmt.toString());

                    paymentDetailsEntity.setPaymentMasterId(paymentEntity);
                    // Deposit payable amount updation
                    final LookUp depLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            MainetConstants.AccountBillEntry.DE, AccountPrefix.ABT.toString(), langId, organisation);
                    Long depLookUpId = depLookup.getLookUpId();
                    if (depLookUpId != null) {
                        if (depLookUpId.equals(paymentEntryDto.getBillTypeId())) {
                            BigDecimal oldDepDefundAmt = accountDepositJparepository
                                    .getDepDefundAmountDetails(billDetail.getId(), paymentEntryDto.getOrgId());
                            if (oldDepDefundAmt != null && !oldDepDefundAmt.toString().equals("0.00")
                                    && !oldDepDefundAmt.toString().equals("0")) {
                            	
                            	Long checkPayExists = paymentEntryRepository.checkPaymentExistsByBillId(billDetail.getId(), paymentEntryDto.getOrgId());
                            	BigDecimal newDepDefundAmt = new BigDecimal(0);
                            	if(checkPayExists == null || checkPayExists == 0l) {
                            		newDepDefundAmt = oldDepDefundAmt.subtract(
                                            billDetail.getPaymentAmount()
                                                    .add(new BigDecimal(billDetail.getDeductions().replaceAll(",", ""))));
                            	}else {
                            		newDepDefundAmt = oldDepDefundAmt.subtract(
                                            billDetail.getPaymentAmount());
                            	}
                                 
                                if (newDepDefundAmt.signum() != -1) {
                                    accountDepositJparepository.updateDepDelRefundAmount(billDetail.getId(),
                                            paymentEntryDto.getOrgId(), newDepDefundAmt);
                                    
                                    Long statusId = null;
                                    if (newDepDefundAmt.compareTo(BigDecimal.ZERO) == 0
                                            || (newDepDefundAmt.compareTo(new BigDecimal(0.00)) == 0)) {
                                        statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                                                PrefixConstants.AccountBillEntry.DR, PrefixConstants.NewWaterServiceConstants.RDC,
                                                paymentEntryDto.getOrgId());
                                    }else {
                                    	statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                                                "PRD", PrefixConstants.NewWaterServiceConstants.RDC,
                                                paymentEntryDto.getOrgId());
                                    }
                                        accountDepositJparepository.updateDepositBalanceAmountInDepositTableBybillMasterId(
                                                billDetail.getId(),
                                                statusId,
                                                paymentEntryDto.getOrgId());
                                    
                                } else {
                                    LOGGER.info("deposit balance not available for payment for this deposit Id"
                                            + paymentEntryDto.getBillTypeId());
                                }
                            } else {
                                LOGGER.info("deposit balance not available for payment for this deposit Id="
                                        + paymentEntryDto.getBillTypeId());
                            }

                        }
                    }
                    paymentDetailsList.add(paymentDetailsEntity);
                    /*
                     * final List<Object[]> deductions = getDeductionDetails(billDetail.getId(), paymentEntryDto.getOrgId()); if
                     * ((deductions != null) && !deductions.isEmpty()) { for (final Object[] ded : deductions) {
                     * paymentDetailsEntity = new AccountPaymentDetEntity(); paymentDetailsEntity.setBdhIdDeduction((Long)
                     * ded[0]); paymentDetailsEntity.setBudgetCodeId((Long) ded[2]);
                     * paymentDetailsEntity.setPaymentDeductionAmt(Double.valueOf(ded[1].toString()) );
                     * paymentDetailsEntity.setBillId(billDetail.getId());// Column name To be renamed
                     * paymentDetailsEntity.setCreatedBy(paymentEntity.getCreatedBy());
                     * paymentDetailsEntity.setCreatedDate(paymentEntity.getCreatedDate());
                     * paymentDetailsEntity.setLangId(paymentEntity.getLangId());
                     * paymentDetailsEntity.setLgIpMac(paymentEntity.getLgIpMac());
                     * paymentDetailsEntity.setPaymentMasterId(paymentEntity); paymentDetailsList.add(paymentDetailsEntity); } }
                     */
                }
            }
            paymentEntity.setPaymentDetailList(paymentDetailsList);
        } else

        {
            throw new NullPointerException("Bill Details cannot be empty.");
        }

    }

    private Long getExpenditureBchId(final Long bchId, Long billId, final Long orgId) {
        return billEntryRepository.getExpenditureBchId(bchId, billId, orgId);
    }

    private List<Object[]> getExpenditureDetails(final Long billId, final Long orgId) {
        return billEntryRepository.getExpenditureDetails(billId, orgId);
    }

    /*
     * private List<Object[]> getDeductionDetails(final Long billId, final Long orgId) { return
     * billEntryRepository.getDeductionDetails(billId, orgId); }
     */

    // Update the flag of payment status in bill master table
    @Transactional(rollbackFor=Exception.class)
    private void updatePaymentStatusForBill(final AccountPaymentMasterEntity accountPaymentMasterEntity) {

        Map<Long, BigDecimal> billIdAndPayAmtMap = new HashMap<Long, BigDecimal>();
        final List<AccountPaymentDetEntity> paymentDetailList = accountPaymentMasterEntity.getPaymentDetailList();
        if ((paymentDetailList != null) && !paymentDetailList.isEmpty()) {
            for (final AccountPaymentDetEntity payDet : paymentDetailList) {
                BigDecimal mapPaymentAmount = billIdAndPayAmtMap.get(payDet.getBillId());
                if (mapPaymentAmount == null) {
                    billIdAndPayAmtMap.put(payDet.getBillId(), payDet.getPaymentAmt());
                } else {
                    mapPaymentAmount = mapPaymentAmount.add(payDet.getPaymentAmt());
                    billIdAndPayAmtMap.put(payDet.getBillId(), mapPaymentAmount);
                }
            }
        }
        for (Entry<Long, BigDecimal> acPaymentDetMapEntries : billIdAndPayAmtMap.entrySet()) {
            AccountBillEntryMasterEnitity billEntryEntity = billEntryRepository
                    .findBillEntryById(accountPaymentMasterEntity.getOrgId(), acPaymentDetMapEntries.getKey());
            BigDecimal balanceAmount = billEntryEntity.getBalanceAmount().subtract(acPaymentDetMapEntries.getValue());
            String y = "Y";
            String n = "N";
            if ((balanceAmount.toString()).equals("0") || (balanceAmount.toString()).equals("0.00")
                    || (balanceAmount.compareTo(BigDecimal.ZERO) < 0)) {
                billEntryRepository.updateFullPaymentStatus(acPaymentDetMapEntries.getKey(), y.charAt(0), balanceAmount);
            } else {
                billEntryRepository.updateFullPaymentStatus(acPaymentDetMapEntries.getKey(), n.charAt(0), balanceAmount);
            }
        }
    }

    // Update the cheque number status
    public void updateChequeNoStatus(final Long chequeBookDetId, final Long orgId, final int langId,
            final Organisation org, final Long paymentId, final Date issuanceDate, final String paymentType) {
    	   LookUp lkpStatus=null;
    	   LookUp isChequeIssanceRequired=null;
    	    try { 
    	    lkpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ISSUED.getValue(),
    	                AccountPrefix.CLR.toString(), langId, org);		
    	    isChequeIssanceRequired = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.CIR.getValue(),
                  AccountPrefix.AIC.toString(), langId, org);
    	    
    	    }catch(Exception e) {
                LOGGER.info("Prefix AIC not found ",e);
                isChequeIssanceRequired=new LookUp();
    	    }
    	 if(isChequeIssanceRequired!=null && StringUtils.isNotBlank(isChequeIssanceRequired.getOtherField()) && isChequeIssanceRequired.getOtherField().equals(MainetConstants.Y_FLAG)) {
    		 lkpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.READY_FOR_ISSUE.getValue(),
                     AccountPrefix.CLR.toString(), langId, org);
    		 chequeBookLeafRepository.updateChequeDetailsForPayments(lkpStatus.getLookUpId(), chequeBookDetId, orgId, paymentId,
    	                null, paymentType);
    	  }else {
          final Long cpdIdStatus = lkpStatus.getLookUpId();
          chequeBookLeafRepository.updateChequeDetailsForPayments(cpdIdStatus, chequeBookDetId, orgId, paymentId,
                issuanceDate, paymentType);
    	  }
    }

    // Posting the payment entry
    private void postPaymentEntry(final PaymentEntryDto paymentEntryDto) {

        final VoucherPostDTO postDto = new VoucherPostDTO();
        List<VoucherPostDTO> postDtoList = new ArrayList<>();
        final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
        final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
        postDto.setVoucherSubTypeId(voucherSubTypeId);

        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
            postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
        } else {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        }
        postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
        postDto.setDepartmentId(departmentId);
        postDto.setVoucherReferenceNo(paymentEntryDto.getPaymentNo());

        postDto.setNarration(paymentEntryDto.getNarration());
        postDto.setPayerOrPayee(paymentEntryDto.getVendorDesc());// Vendor name

        postDto.setFieldId(paymentEntryDto.getFieldId());
        postDto.setOrgId(paymentEntryDto.getOrgId());
        postDto.setCreatedBy(paymentEntryDto.getCreatedBy());
        postDto.setCreatedDate(paymentEntryDto.getCreatedDate());
        postDto.setLgIpMac(paymentEntryDto.getLgIpMacAddress());
        postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

        Long lookUpIdCash = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.C.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdAdj = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.A.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdPetty = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        if (paymentEntryDto.getPaymentMode().equals(lookUpIdCash)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdAdj)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdPetty)) {
            // Cash Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long templateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                    AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
            final Long activeStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.IsLookUp.ACTIVE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, paymentEntryDto.getOrgId());
            VoucherTemplateMasterEntity entity = voucherTemplateRepository
                    .findAcHeadCodeInTemplateForCashMode(templateFor, paymentEntryDto.getOrgId(), activeStatus);
            Long sacHeadIdCr = null;
            List<VoucherTemplateDetailEntity> detList = entity.getTemplateDetailEntities();
            for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : detList) {
                if (voucherTemplateDetailEntity.getCpdIdPayMode().equals(paymentEntryDto.getPaymentMode())) {
                    if (voucherTemplateDetailEntity.getSacHeadId() != null) {
                        sacHeadIdCr = voucherTemplateDetailEntity.getSacHeadId();
                        break;
                    }
                }
            }
            if (sacHeadIdCr == null) {
                throw new NullPointerException(PAYMODE + " templateFor is : " + templateFor + " pay mode is : "
                        + paymentEntryDto.getPaymentMode() + " orgid is : " + paymentEntryDto.getOrgId()
                        + " status is active : " + activeStatus);
            }
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        } else {
            // Bank Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            if (paymentEntryDto.getTotalPaymentAmount() != null) {
                postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            }
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long sacHeadIdCr = getSacHeadIdByBankAccountId(paymentEntryDto.getBankAcId(),
                    paymentEntryDto.getOrgId());
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        }

        // Vendor Debit side
        final VoucherPostDetailDTO postDetailDtoDr = new VoucherPostDetailDTO();
        final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                PrefixConstants.DCR, paymentEntryDto.getOrgId());
        postDetailDtoDr.setDrCrId(drId);
        postDetailDtoDr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
        Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), paymentEntryDto.getOrgId());
        final Long vendorSacHeadIdDr = billEntryRepository.getVendorSacHeadIdByVendorId(paymentEntryDto.getVendorId(),
                paymentEntryDto.getOrgId(), status);
        postDetailDtoDr.setSacHeadId(vendorSacHeadIdDr);
        voucherDetails.add(postDetailDtoDr);

        postDto.setVoucherDetails(voucherDetails);
        postDtoList.add(postDto);
        ApplicationSession session = ApplicationSession.getInstance();
        String responseValidation = accountVoucherPostService.validateInput(postDtoList);
        AccountVoucherEntryEntity response = null;
        if (responseValidation.isEmpty()) {
            response = accountVoucherPostService.voucherPosting(postDtoList);
            if (response == null) {
                throw new IllegalArgumentException("Voucher Posting failed");
            }
        } else {
            LOGGER.error(session.getMessage("account.voucher.service.posting")
                    + session.getMessage("account.voucher.posting.improper.input") + responseValidation);
            throw new IllegalArgumentException(
                    "Voucher Posting failed : proper data is not exist" + responseValidation);
        }
    }

    // Posting the direct payment entry
    private void postDirectPaymentEntry(final PaymentEntryDto paymentEntryDto) {

        final VoucherPostDTO postDto = new VoucherPostDTO();
        List<VoucherPostDTO> postDtoList = new ArrayList<>();
        final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
        final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
        postDto.setVoucherSubTypeId(voucherSubTypeId);

        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
            postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
        } else {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        }
        postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
        postDto.setDepartmentId(departmentId);
        postDto.setVoucherReferenceNo(paymentEntryDto.getPaymentNo());

        postDto.setNarration(paymentEntryDto.getNarration());
        postDto.setPayerOrPayee(paymentEntryDto.getVendorDesc());// Vendor name

        postDto.setFieldId(paymentEntryDto.getFieldId());
        postDto.setOrgId(paymentEntryDto.getOrgId());
        postDto.setCreatedBy(paymentEntryDto.getCreatedBy());
        postDto.setCreatedDate(paymentEntryDto.getCreatedDate());
        postDto.setLgIpMac(paymentEntryDto.getLgIpMacAddress());
        postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

        Long lookUpIdCash = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.C.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdAdj = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.A.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdPetty = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        if (paymentEntryDto.getPaymentMode().equals(lookUpIdCash)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdAdj)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdPetty)) {
            // Cash Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long templateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                    AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
            final Long activeStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.IsLookUp.ACTIVE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, paymentEntryDto.getOrgId());
            VoucherTemplateMasterEntity entity = voucherTemplateRepository
                    .findAcHeadCodeInTemplateForCashMode(templateFor, paymentEntryDto.getOrgId(), activeStatus);
            Long sacHeadIdCr = null;
            List<VoucherTemplateDetailEntity> detList = entity.getTemplateDetailEntities();
            for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : detList) {
                if (voucherTemplateDetailEntity.getCpdIdPayMode().equals(paymentEntryDto.getPaymentMode())) {
                    if (voucherTemplateDetailEntity.getSacHeadId() != null) {
                        sacHeadIdCr = voucherTemplateDetailEntity.getSacHeadId();
                        break;
                    }
                }
            }
            if (sacHeadIdCr == null) {
                throw new NullPointerException(PAYMODE + " templateFor is : " + templateFor + " pay mode is : "
                        + paymentEntryDto.getPaymentMode() + " orgid is : " + paymentEntryDto.getOrgId()
                        + " status is active : " + activeStatus);
            }
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        } else {
            // Bank Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long sacHeadIdCr = getSacHeadIdByBankAccountId(paymentEntryDto.getBankAcId(),
                    paymentEntryDto.getOrgId());
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        }
        // Account Head Debit side
        for (final PaymentDetailsDto paymentDetailsDtoList : paymentEntryDto.getPaymentDetailsDto()) {
            final VoucherPostDetailDTO postDetailDtoDr = new VoucherPostDetailDTO();
            final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoDr.setDrCrId(drId);
            postDetailDtoDr.setSacHeadId(paymentDetailsDtoList.getId());
            postDetailDtoDr.setVoucherAmount(paymentDetailsDtoList.getPaymentAmount());
            voucherDetails.add(postDetailDtoDr);
        }
        postDto.setVoucherDetails(voucherDetails);
        postDtoList.add(postDto);
        ApplicationSession session = ApplicationSession.getInstance();
        String responseValidation = accountVoucherPostService.validateInput(postDtoList);
        AccountVoucherEntryEntity response = null;
        if (responseValidation.isEmpty()) {
            response = accountVoucherPostService.voucherPosting(postDtoList);
            if (response == null) {
                throw new IllegalArgumentException("Voucher Posting failed");
            }
        } else {
            LOGGER.error(session.getMessage("account.voucher.service.posting")
                    + session.getMessage("account.voucher.posting.improper.input") + responseValidation);
            throw new IllegalArgumentException(
                    "Voucher Posting failed : proper data is not exist" + responseValidation);
        }
    }

    private Long getSacHeadIdByBankAccountId(final Long bankAccountId, final Long orgId) {

        Long sacHeadId = null;
        try {
            sacHeadId = contraEntryVoucherJpaRepository.getSacHeadIdByBankAccountId(bankAccountId, orgId);
        } catch (final NonUniqueResultException ex) {
            LOGGER.error("duplicate Account Head mapped against Bank Account[bankAccountId=" + bankAccountId + ",orgId="
                    + orgId + "]", ex);
            throw new IllegalArgumentException("duplicate Account Head mapped against Bank Account[bankAccountId="
                    + bankAccountId + ",orgId=" + orgId + "]", ex);
        }
        return sacHeadId;
    }

    private void postAdvanceEntry(final PaymentEntryDto paymentEntryDto) throws ParseException {
    	Organisation  orgId=new Organisation();
    	orgId.setOrgid(paymentEntryDto.getOrgId());
    	final LookUp advanceLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.AD.toString(),
                AccountPrefix.ABT.toString(), Integer.parseInt(paymentEntryDto.getLanguageId().toString()), orgId);
        final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
        final List<PaymentDetailsDto> paymentDetailsList = paymentEntryDto.getPaymentDetailsDto();
        for (final PaymentDetailsDto paymentDetails : paymentDetailsList) {

            /*
             * final List<Object[]> expenditures = getExpenditureDetails(billDetail.getId(), paymentEntryDto.getOrgId()); if
             * ((expenditures != null) && !expenditures.isEmpty()) { for (final Object[] exp : expenditures) {
             */
        	//paymentDetails.getBillTypeId()
             if(paymentEntryDto.getBillTypeId().equals(advanceLookup.getLookUpId())) {
            final AdvanceEntryDTO advanceEntryDTO = new AdvanceEntryDTO();
            final AdvanceEntryEntity advanceEntryEntity = new AdvanceEntryEntity();

            advanceEntryDTO.setOrgid(paymentEntryDto.getOrgId());
            final int langId = Integer.parseInt(paymentEntryDto.getLanguageId().toString());
            advanceEntryDTO.setLangId(langId);
            advanceEntryDTO.setCreatedBy(paymentEntryDto.getCreatedBy());
            advanceEntryDTO.setCreatedDate(paymentEntryDto.getCreatedDate());
            // advanceEntryDTO.setCreatedBy(paymentEntryDto.getUpdatedBy());
            advanceEntryDTO.setUpdatedDate(paymentEntryDto.getUpdatedDate());
            advanceEntryDTO.setLgIpMac(paymentEntryDto.getLgIpMacAddress());
            advanceEntryDTO.setLgIpMacUpd(paymentEntryDto.getLgIpMacAddress());
            advanceEntryDTO.setPrAdvEntryDate(new Date());
            if (paymentEntryDto.getPaymentDate() != null) {
                advanceEntryDTO.setPrAdvEntryDate(paymentEntryDto.getPaymentDate());
            }
            Long finYearId = tbFinancialyearService.getFinanciaYearIdByFromDate(paymentEntryDto.getPaymentDate());
            if (finYearId == null) {
                throw new NullPointerException(GENERATE_ADVANCE_NO_FIN_YEAR_ID);
            }
            // remove financial Id as input because require a same both the side direct payment and advance payment
            final Long advNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                    TB_AC_ADVANCE, PAY_ADVANCE_NO, paymentEntryDto.getOrgId(),
                    MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, null);
            advanceEntryDTO.setPrAdvEntryNo(advNumber);
            advanceEntryDTO.setVendorId(paymentEntryDto.getVendorId());

            // final Long accountHeadId = paymentDetails.getBchId();
            /*
             * Task #6674 1. Advance payment entry not save- needs to resolve. for that i need to change paymentDetails.getBchId()
             * into paymentDetails.getId() because i did not get bchId line number 1156
             */
            advanceEntryDTO.setPacHeadId(paymentDetails.getId());

            // final Long superOrgId =
            // ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
            /*
             * final Long advLookUpTypeId = advanceEntryService.getAdvAccountTypeId(billDetail.getId(), accountHeadId, superOrgId,
             * paymentEntryDto.getOrgId());
             */
            /*
             * Task #6674 1. Advance payment entry not save- needs to resolve. for that i need to change paymentDetails.getBchId()
             * into paymentDetails.getId() because i did not get bchId line number 1168
             */
            final Long advLookUpTypeId = advanceEntryService.getAdvAccountTypeIdByOrgIdAndId(paymentDetails.getBchId(),
                    paymentEntryDto.getOrgId());
            if (advLookUpTypeId == null) {
                throw new NullPointerException("Advance type value is mandatory in advance bill type " + " accountHeadId : "
                        + paymentDetails.getId() + " orgid : " + paymentEntryDto.getOrgId());
            }
            advanceEntryDTO.setAdvanceTypeId(advLookUpTypeId);

            advanceEntryDTO.setPaymentNumber(paymentEntryDto.getPaymentNo());
            advanceEntryDTO.setPaymentDate(paymentEntryDto.getPaymentDate());
            advanceEntryDTO.setAdvanceAmount(paymentEntryDto.getTotalPaymentAmount().toString());
            advanceEntryDTO.setBalanceAmount(paymentEntryDto.getTotalPaymentAmount().toString());
            advanceEntryDTO.setPayAdvParticulars(paymentEntryDto.getNarration());
            advanceEntryDTO.setDeptId(departmentId);
            final Organisation organisation = new Organisation();
            organisation.setOrgid(paymentEntryDto.getOrgId());
            final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), langId, organisation);
            advanceEntryDTO.setCpdIdStatus(statusLookup.getLookUpCode());

            advanceEntryDTO.setAdv_Flg(AccountConstants.S.toString());
            advanceEntryDTO.setPaymentReferenceId(paymentEntryDto.getPaymentId());
            advanceEntryServiceMapper.mapAdvanceEntryDTOToAdvanceEntryEntity(advanceEntryDTO, advanceEntryEntity);
            advanceEntryRepository.save(advanceEntryEntity);
        }
       }       
    }
    
    private void postAdvanceEntryFromTDS(final PaymentEntryDto paymentEntryDto) throws ParseException {
    	Organisation  orgId=new Organisation();
    	orgId.setOrgid(paymentEntryDto.getOrgId());
    	final LookUp advanceLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.AD.toString(),
                AccountPrefix.ABT.toString(), Integer.parseInt(paymentEntryDto.getLanguageId().toString()), orgId);
        final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
        final List<PaymentDetailsDto> paymentDetailsList = paymentEntryDto.getPaymentDetailsDto();
        for (final PaymentDetailsDto paymentDetails : paymentDetailsList) {
          if(paymentDetails.getBillTypeId().equals(advanceLookup.getLookUpId())) {
            final AdvanceEntryDTO advanceEntryDTO = new AdvanceEntryDTO();
            final AdvanceEntryEntity advanceEntryEntity = new AdvanceEntryEntity();

            advanceEntryDTO.setOrgid(paymentEntryDto.getOrgId());
            final int langId = Integer.parseInt(paymentEntryDto.getLanguageId().toString());
            advanceEntryDTO.setLangId(langId);
            advanceEntryDTO.setCreatedBy(paymentEntryDto.getCreatedBy());
            advanceEntryDTO.setCreatedDate(paymentEntryDto.getCreatedDate());
            // advanceEntryDTO.setCreatedBy(paymentEntryDto.getUpdatedBy());
            advanceEntryDTO.setUpdatedDate(paymentEntryDto.getUpdatedDate());
            advanceEntryDTO.setLgIpMac(paymentEntryDto.getLgIpMacAddress());
            advanceEntryDTO.setLgIpMacUpd(paymentEntryDto.getLgIpMacAddress());
            advanceEntryDTO.setPrAdvEntryDate(new Date());
            if (paymentEntryDto.getPaymentDate() != null) {
                advanceEntryDTO.setPrAdvEntryDate(paymentEntryDto.getPaymentDate());
            }
            Long finYearId = tbFinancialyearService.getFinanciaYearIdByFromDate(paymentEntryDto.getPaymentDate());
            if (finYearId == null) {
                throw new NullPointerException(GENERATE_ADVANCE_NO_FIN_YEAR_ID);
            }
            // remove financial Id as input because require a same both the side direct payment and advance payment
            final Long advNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                    TB_AC_ADVANCE, PAY_ADVANCE_NO, paymentEntryDto.getOrgId(),
                    MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, null);
            advanceEntryDTO.setPrAdvEntryNo(advNumber);
            advanceEntryDTO.setVendorId(paymentEntryDto.getVendorId());

            // final Long accountHeadId = paymentDetails.getBchId();
            /*
             * Task #6674 1. Advance payment entry not save- needs to resolve. for that i need to change paymentDetails.getBchId()
             * into paymentDetails.getId() because i did not get bchId line number 1156
             */
            advanceEntryDTO.setPacHeadId(paymentDetails.getId());

            // final Long superOrgId =
            // ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
            /*
             * final Long advLookUpTypeId = advanceEntryService.getAdvAccountTypeId(billDetail.getId(), accountHeadId, superOrgId,
             * paymentEntryDto.getOrgId());
             */
            /*
             * Task #6674 1. Advance payment entry not save- needs to resolve. for that i need to change paymentDetails.getBchId()
             * into paymentDetails.getId() because i did not get bchId line number 1168
             */
            final Long advLookUpTypeId = advanceEntryService.getAdvAccountTypeIdByOrgIdAndId(paymentDetails.getBchId(),
                    paymentEntryDto.getOrgId());
            if (advLookUpTypeId == null) {
                throw new NullPointerException("Advance type value is mandatory in advance bill type " + " accountHeadId : "
                        + paymentDetails.getId() + " orgid : " + paymentEntryDto.getOrgId());
            }
            advanceEntryDTO.setAdvanceTypeId(advLookUpTypeId);

            advanceEntryDTO.setPaymentNumber(paymentEntryDto.getPaymentNo());
            advanceEntryDTO.setPaymentDate(paymentEntryDto.getPaymentDate());
            advanceEntryDTO.setAdvanceAmount(paymentEntryDto.getTotalPaymentAmount().toString());
            advanceEntryDTO.setBalanceAmount(paymentEntryDto.getTotalPaymentAmount().toString());
            advanceEntryDTO.setPayAdvParticulars(paymentEntryDto.getNarration());
            advanceEntryDTO.setDeptId(departmentId);
            final Organisation organisation = new Organisation();
            organisation.setOrgid(paymentEntryDto.getOrgId());
            final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), langId, organisation);
            advanceEntryDTO.setCpdIdStatus(statusLookup.getLookUpCode());

            advanceEntryDTO.setAdv_Flg(AccountConstants.S.toString());
            advanceEntryDTO.setPaymentReferenceId(paymentEntryDto.getPaymentId());
            advanceEntryServiceMapper.mapAdvanceEntryDTOToAdvanceEntryEntity(advanceEntryDTO, advanceEntryEntity);
            advanceEntryRepository.save(advanceEntryEntity);
        }
       }       
    }

    private void postAdvanceEntryBillSumaryWise(final PaymentEntryDto paymentEntryDto) throws ParseException {

        final List<PaymentDetailsDto> billDetails = paymentEntryDto.getPaymentDetailsDto();
        for (final PaymentDetailsDto billDetail : billDetails) {

            final List<Object[]> expenditures = getExpenditureDetails(billDetail.getId(), paymentEntryDto.getOrgId());
            if ((expenditures != null) && !expenditures.isEmpty()) {
                for (final Object[] exp : expenditures) {

                    final AdvanceEntryDTO advanceEntryDTO = new AdvanceEntryDTO();
                    final AdvanceEntryEntity advanceEntryEntity = new AdvanceEntryEntity();

                    advanceEntryDTO.setOrgid(paymentEntryDto.getOrgId());
                    final int langId = Integer.parseInt(paymentEntryDto.getLanguageId().toString());
                    advanceEntryDTO.setLangId(langId);
                    advanceEntryDTO.setCreatedBy(paymentEntryDto.getCreatedBy());
                    advanceEntryDTO.setCreatedDate(paymentEntryDto.getCreatedDate());
                    advanceEntryDTO.setCreatedBy(paymentEntryDto.getUpdatedBy());
                    advanceEntryDTO.setUpdatedDate(paymentEntryDto.getUpdatedDate());
                    advanceEntryDTO.setLgIpMac(paymentEntryDto.getLgIpMacAddress());
                    advanceEntryDTO.setLgIpMacUpd(paymentEntryDto.getLgIpMacAddress());

                    advanceEntryDTO.setPrAdvEntryDate(new Date());
                    Long finYearId = tbFinancialyearService
                            .getFinanciaYearIdByFromDate(paymentEntryDto.getPaymentDate());
                    if (finYearId == null) {
                        throw new NullPointerException(GENERATE_ADVANCE_NO_FIN_YEAR_ID);
                    }
                    final Long advNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                            TB_AC_ADVANCE, PAY_ADVANCE_NO, paymentEntryDto.getOrgId(),
                            MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, finYearId);
                    advanceEntryDTO.setPrAdvEntryNo(advNumber);
                    advanceEntryDTO.setVendorId(paymentEntryDto.getVendorId());

                    final Long accountHeadId = (Long) exp[2];
                    advanceEntryDTO.setPacHeadId(accountHeadId);
                    final Long superOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
                    final Long advLookUpTypeId = advanceEntryService.getAdvAccountTypeId(
                            paymentEntryDto.getBillTypeId(), accountHeadId, superOrgId, paymentEntryDto.getOrgId());
                    if (advLookUpTypeId == null) {
                        throw new NullPointerException("Advance type value is mandatory in advance bill type " + " billTypeId :"
                                + paymentEntryDto.getBillTypeId() + "accountHeadId : " + accountHeadId + "superOrgid : "
                                + superOrgId + " orgid : " + paymentEntryDto.getOrgId());
                    }
                    advanceEntryDTO.setAdvanceTypeId(advLookUpTypeId);

                    advanceEntryDTO.setPaymentNumber(paymentEntryDto.getPaymentNo());
                    advanceEntryDTO.setPaymentDate(paymentEntryDto.getPaymentDate());
                    advanceEntryDTO.setAdvanceAmount(paymentEntryDto.getTotalPaymentAmount().toString());
                    advanceEntryDTO.setBalanceAmount(paymentEntryDto.getTotalPaymentAmount().toString());
                    advanceEntryDTO.setPayAdvParticulars(paymentEntryDto.getNarration());

                    final Organisation organisation = new Organisation();
                    organisation.setOrgid(paymentEntryDto.getOrgId());
                    final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), langId, organisation);
                    advanceEntryDTO.setCpdIdStatus(statusLookup.getLookUpCode());

                    advanceEntryDTO.setAdv_Flg(AccountConstants.S.toString());
                    advanceEntryServiceMapper.mapAdvanceEntryDTOToAdvanceEntryEntity(advanceEntryDTO,
                            advanceEntryEntity);
                    advanceEntryRepository.save(advanceEntryEntity);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.PaymentEntrySrevice#createDirectPaymentEntry(
     * com.abm.mainet.account.dto.PaymentEntryDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountPaymentMasterEntity createDirectPaymentEntry(final PaymentEntryDto paymentEntryDto)
            throws ParseException {

        AccountPaymentMasterEntity paymentEntity = new AccountPaymentMasterEntity();
        TbComparamDetEntity comparamDetId = null;
        BankAccountMasterEntity bankAccount = null;
        TbAcVendormasterEntity vendorId = null;
        Long lookUpIdCash = null;
        Long lookUpIdpaytCash = null;
        Long lookUpIdAdj = null;
        Long lookUpIdBank = null;

        paymentEntity.setPaymentDate(paymentEntryDto.getPaymentDate());

        // Bill type id
        comparamDetId = new TbComparamDetEntity();
        comparamDetId.setCpdId(paymentEntryDto.getBillTypeId());
        paymentEntity.setBillTypeId(comparamDetId);

        // Vendor id
        vendorId = new TbAcVendormasterEntity();
        if (paymentEntryDto.getVendorId() != null) {
            vendorId.setVmVendorid(paymentEntryDto.getVendorId());
            paymentEntity.setVmVendorId(vendorId);
        }

        // Pay mode
        comparamDetId = new TbComparamDetEntity();
        comparamDetId.setCpdId(paymentEntryDto.getPaymentMode());
        paymentEntity.setPaymentModeId(comparamDetId);

        // Bank acc id
        bankAccount = new BankAccountMasterEntity();
        if (paymentEntryDto.getBankAcId() != null) {
            bankAccount.setBaAccountId(paymentEntryDto.getBankAcId());
            paymentEntity.setBaBankAccountId(bankAccount);
        }
        final LookUp lookUpCash = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.CASH.getValue(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getLanguageId().intValue(),
                paymentEntryDto.getOrganisation());
        final LookUp lookUpPaytCash = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.PCA.getValue(), AccountPrefix.PAY.toString(),
                paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation());
        final LookUp lookUpAdj = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.A.getValue(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getLanguageId().intValue(),
                paymentEntryDto.getOrganisation());
        final LookUp lookUpBank = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MENU.B,
                AccountPrefix.PAY.toString(), paymentEntryDto.getLanguageId().intValue(),
                paymentEntryDto.getOrganisation());
        lookUpIdCash = lookUpCash.getLookUpId();
        lookUpIdAdj = lookUpAdj.getLookUpId();
        lookUpIdBank = lookUpBank.getLookUpId();
        lookUpIdpaytCash = lookUpPaytCash.getLookUpId();
        if ((paymentEntryDto.getPaymentMode() != null) && !paymentEntryDto.getPaymentMode().equals(lookUpIdCash)
                && !paymentEntryDto.getPaymentMode().equals(lookUpIdAdj)
                && !paymentEntryDto.getPaymentMode().equals(lookUpIdBank)
                && !paymentEntryDto.getPaymentMode().equals(lookUpIdpaytCash)) {
            paymentEntity.setInstrumentNumber(paymentEntryDto.getInstrumentNo());
            paymentEntity.setInstrumentDate(
                    UtilityService.convertStringDateToDateFormat(paymentEntryDto.getInstrumentDate()));
        }
        if (paymentEntryDto.getPaymentMode().equals(lookUpIdBank)) {
            paymentEntity.setUtrNo(paymentEntryDto.getUtrNo());
        }
        paymentEntity.setAuthoId(paymentEntryDto.getCreatedBy());

        LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            paymentEntity.setAuthoDate(paymentEntryDto.getPaymentDate());
        } else {
            paymentEntity.setAuthoDate(paymentEntryDto.getCreatedDate());
        }

        paymentEntity.setAuthoFlg(AccountConstants.Y.toString());
        paymentEntity.setPaymentTypeFlag(1L);

        saveCommonFields(paymentEntity, paymentEntryDto);
        saveDirectPaymentDetails(paymentEntryDto, paymentEntity);
        Objects.requireNonNull(paymentEntryDto.getTransactionDate(),
                ApplicationSession.getInstance().getMessage("payment.entry.service.paymentdate") + paymentEntryDto);
        paymentEntity.setPaymentDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        // Task #6674 paymentEntryDto.getPayeeName() + "$" + 7. Set correct narration.
        paymentEntity.setNarration(paymentEntryDto.getNarration());

        // direct payment voucher posting validate to given VoucherPostDTO
        directPaymentVoucherPostingValidation(paymentEntryDto);

        final String paymentNo = generatePaymentNumber(paymentEntryDto.getOrgId(), paymentEntryDto.getPaymentDate());
        paymentEntryDto.setPaymentNo(paymentNo);
        paymentEntity.setPaymentNo(paymentNo);

        if (paymentEntryDto.getBillRefNo() != null && !paymentEntryDto.getBillRefNo().isEmpty()) {
            paymentEntity.setBillRefNo(paymentEntryDto.getBillRefNo());
        }
        paymentEntity.setFieldId(paymentEntryDto.getFieldId());
        paymentEntity = paymentEntryRepository.save(paymentEntity);
        paymentEntryDto.setPaymentId(paymentEntity.getPaymentId());
        final Organisation organisation = new Organisation();
        organisation.setOrgid(paymentEntryDto.getOrgId());
        final int langId = Integer.parseInt(paymentEntryDto.getLanguageId().toString());
        final LookUp advanceLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.AD.toString(),
                AccountPrefix.ABT.toString(), langId, organisation);
        final Long advLookUpId = advanceLookup.getLookUpId();
        if (paymentEntryDto.getBillTypeId().equals(advLookUpId)) {
            postAdvanceEntry(paymentEntryDto);
        }
        postDirectPaymentEntry(paymentEntryDto);
        if (paymentEntryDto.getInstrumentNo() != null) {
            updateChequeNoStatus(paymentEntryDto.getInstrumentNo(), paymentEntryDto.getOrgId(),
                    paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation(),
                    paymentEntity.getPaymentId(), paymentEntity.getPaymentDate(), AccountConstants.D.toString());
        }
        return paymentEntity;
    }

    private void directPaymentVoucherPostingValidation(PaymentEntryDto paymentEntryDto) {
        LOGGER.info("Process for account direct payment voucher posting validation:" + paymentEntryDto);

        final VoucherPostDTO postDto = new VoucherPostDTO();
        List<VoucherPostDTO> postDtoList = new ArrayList<>();
        final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
        final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
        postDto.setVoucherSubTypeId(voucherSubTypeId);

        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
            postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
        } else {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        }
        postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
        postDto.setDepartmentId(departmentId);
        // postDto.setVoucherReferenceNo(paymentEntryDto.getPaymentNo());
        postDto.setNarration(paymentEntryDto.getNarration());
        postDto.setPayerOrPayee(paymentEntryDto.getVendorDesc());// Vendor name
        postDto.setFieldId(paymentEntryDto.getFieldId());
        postDto.setOrgId(paymentEntryDto.getOrgId());
        postDto.setCreatedBy(paymentEntryDto.getCreatedBy());
        postDto.setCreatedDate(paymentEntryDto.getCreatedDate());
        postDto.setLgIpMac(paymentEntryDto.getLgIpMacAddress());
        postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

        Long lookUpIdCash = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.C.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdAdj = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.A.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdPetty = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        if (paymentEntryDto.getPaymentMode().equals(lookUpIdCash)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdAdj)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdPetty)) {
            // Cash Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long templateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                    AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
            final Long activeStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.IsLookUp.ACTIVE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, paymentEntryDto.getOrgId());
            VoucherTemplateMasterEntity entity = voucherTemplateRepository
                    .findAcHeadCodeInTemplateForCashMode(templateFor, paymentEntryDto.getOrgId(), activeStatus);
            Long sacHeadIdCr = null;
            List<VoucherTemplateDetailEntity> detList = entity.getTemplateDetailEntities();
            for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : detList) {
                if (voucherTemplateDetailEntity.getCpdIdPayMode().equals(paymentEntryDto.getPaymentMode())) {
                    if (voucherTemplateDetailEntity.getSacHeadId() != null) {
                        sacHeadIdCr = voucherTemplateDetailEntity.getSacHeadId();
                        break;
                    }
                }
            }
            if (sacHeadIdCr == null) {
                throw new NullPointerException(PAYMODE + " templateFor is : " + templateFor + " pay mode is : "
                        + paymentEntryDto.getPaymentMode() + " orgid is : " + paymentEntryDto.getOrgId()
                        + " status is active : " + activeStatus);
            }
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        } else {
            // Bank Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long sacHeadIdCr = getSacHeadIdByBankAccountId(paymentEntryDto.getBankAcId(),
                    paymentEntryDto.getOrgId());
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        }
        // Account Head Debit side
        for (final PaymentDetailsDto paymentDetailsDtoList : paymentEntryDto.getPaymentDetailsDto()) {
            final VoucherPostDetailDTO postDetailDtoDr = new VoucherPostDetailDTO();
            final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoDr.setDrCrId(drId);
            postDetailDtoDr.setSacHeadId(paymentDetailsDtoList.getId());
            postDetailDtoDr.setVoucherAmount(paymentDetailsDtoList.getPaymentAmount());
            voucherDetails.add(postDetailDtoDr);
        }
        postDto.setVoucherDetails(voucherDetails);
        postDtoList.add(postDto);
        List<String> responseValidation = accountVoucherPostService.checkVoucherPostValidateInput(postDtoList);
        if (responseValidation.size() > 0) {
            throw new NullPointerException(
                    "improper input parameter for VoucherPostDTO in direct payment entry -> " + responseValidation);
        }
    }

    /**
     * @param paymentEntryDto
     * @param paymentEntity
     */
    private void saveDirectPaymentDetails(final PaymentEntryDto paymentEntryDto,
            final AccountPaymentMasterEntity paymentEntity) {
        AccountPaymentDetEntity paymentDetailsEntity = null;
        final List<AccountPaymentDetEntity> paymentDetailsList = new ArrayList<>();

        final List<PaymentDetailsDto> billDetails = paymentEntryDto.getPaymentDetailsDto();
        if ((billDetails != null) && !billDetails.isEmpty()) {
            for (final PaymentDetailsDto billDetail : billDetails) {
                paymentDetailsEntity = new AccountPaymentDetEntity();
                paymentDetailsEntity.setBudgetCodeId(billDetail.getId());
                paymentDetailsEntity.setPaymentAmt(new BigDecimal(billDetail.getPaymentAmount().toString()));
                paymentDetailsEntity.setOrgId(paymentEntity.getOrgId());
                paymentDetailsEntity.setCreatedBy(paymentEntity.getCreatedBy());
                paymentDetailsEntity.setCreatedDate(paymentEntity.getCreatedDate());
                paymentDetailsEntity.setLangId(paymentEntryDto.getLanguageId());
                paymentDetailsEntity.setLgIpMac(paymentEntity.getLgIpMac());
                paymentDetailsEntity.setPaymentMasterId(paymentEntity);
                paymentDetailsList.add(paymentDetailsEntity);
            }
            paymentEntity.setPaymentDetailList(paymentDetailsList);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.PaymentEntrySrevice#getPaymentDetails(java. lang.Long, java.lang.String,
     * java.math.BigDecimal, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountPaymentMasterEntity> getPaymentDetails(final Long orgId, final String paymentEntryDate,
            final BigDecimal paymentAmount, final Long vendorId, final Long budgetCodeId, final String paymentNo,
            final Long baAccountid, final Long paymentTypeFlag) {

        final List<AccountPaymentMasterEntity> paymentDetailsList = paymentEntryDao.getPaymentDetails(orgId,
                paymentEntryDate, paymentAmount, vendorId, budgetCodeId, paymentNo, baAccountid, paymentTypeFlag);
        return paymentDetailsList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.PaymentEntrySrevice#findPaymentEntryDataById( java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public PaymentEntryDto findPaymentEntryDataById(final Long id, final Long orgId, int langId) {

        final PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        final AccountPaymentMasterEntity paymentMasterEntity = paymentEntryRepository.findPaymentEntryDataById(id,
                orgId);
        PaymentDetailsDto paymentDetailsdto = null;
        final List<PaymentDetailsDto> paymentDetailsList = new ArrayList<>();

        if (paymentMasterEntity != null) {
            BigDecimal totalPaymentAmount = BigDecimal.ZERO;
            paymentEntryDto.setId(paymentMasterEntity.getPaymentId());

            if ((paymentMasterEntity.getBaBankAccountId() != null)
                    && (paymentMasterEntity.getBaBankAccountId().getBaAccountId() != null)) {
                paymentEntryDto.setBankAcId(paymentMasterEntity.getBaBankAccountId().getBaAccountId());
            }

            paymentEntryDto.setPaymentNo(paymentMasterEntity.getPaymentNo());

            if (paymentMasterEntity.getInstrumentDate() != null) {
                paymentEntryDto
                        .setInsttDate(UtilityService.convertDateToDDMMYYYY(paymentMasterEntity.getInstrumentDate()));
            }

            paymentEntryDto
                    .setPaymentEntryDate(UtilityService.convertDateToDDMMYYYY(paymentMasterEntity.getPaymentDate()));
            if (paymentMasterEntity.getBillTypeId() != null && paymentMasterEntity.getBillTypeId().getCpdId() != null) {
                final String paymentType = CommonMasterUtility.findLookUpDesc(AccountPrefix.ABT.toString(),
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        paymentMasterEntity.getBillTypeId().getCpdId());
                paymentEntryDto.setBillTypeDesc(paymentType);
            }

            if (paymentMasterEntity.getVmVendorId() != null
                    && paymentMasterEntity.getVmVendorId().getVmVendorid() != null) {
                final String vendorDescription = vendorRepository.getVendorNameById(
                        paymentMasterEntity.getVmVendorId().getVmVendorid(), paymentMasterEntity.getOrgId());
                paymentEntryDto.setVendorDesc(vendorDescription);
            }

            if (paymentMasterEntity.getNarration() != null) {
                paymentEntryDto.setNarration(paymentMasterEntity.getNarration());
            }

            /*
             * Organisation org = new Organisation(); org.setOrgid(orgId); final LookUp lkpStatus =
             * CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ISSUED. getValue(),
             * AccountPrefix.CLR.toString(), langId, org); final Long cpdIdStatus = lkpStatus.getLookUpId();
             */
            if (paymentMasterEntity.getInstrumentNumber() != null) {
                final String chequno = getCheque(paymentMasterEntity.getInstrumentNumber());
                if (chequno != null && !chequno.isEmpty()) {
                    paymentEntryDto.setInstrumentNo(Long.valueOf(chequno));
                }
            }
            if (paymentMasterEntity.getUtrNo() != null && !paymentMasterEntity.getUtrNo().isEmpty()) {
                paymentEntryDto.setUtrNo(paymentMasterEntity.getUtrNo());
            }

            final List<Object[]> payementDetails = paymentEntryRepository.getVendorDetailList(id, orgId);

            // final List<AccountPaymentDetEntity> payementDetails =
            // paymentMasterEntity.getPaymentDetailList();
            if ((payementDetails != null) && !payementDetails.isEmpty()) {
                for (final Object[] paymentDetail : payementDetails) {
                    paymentDetailsdto = new PaymentDetailsDto();
                    paymentDetailsdto.setVendorName(paymentDetail[0].toString());
                    paymentDetailsdto.setBillNumber(paymentDetail[1].toString());
                    paymentDetailsdto.setBillDate(Utility.dateToString((Date) paymentDetail[2]));
                    if (paymentDetail[3] != null) {
                        BigDecimal amount = ((BigDecimal) paymentDetail[3]).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                        paymentDetailsdto.setPaymentAmount(amount);
                    }
                    paymentDetailsList.add(paymentDetailsdto);
                    totalPaymentAmount = totalPaymentAmount.add(new BigDecimal(paymentDetail[3].toString()));
                }
            }
            paymentEntryDto.setPaymentDetailsDto(paymentDetailsList);
            paymentEntryDto.setTotalPaymentAmount(
                    new BigDecimal(totalPaymentAmount.toString()).setScale(2, RoundingMode.HALF_EVEN));
        }
        paymentEntryDto.setModeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
                paymentMasterEntity.getOrgId(), paymentMasterEntity.getPaymentModeId().getCpdId()));

        paymentEntryDto.setModeCode(CommonMasterUtility.findLookUpCode(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
                paymentMasterEntity.getOrgId(), paymentMasterEntity.getPaymentModeId().getCpdId()));

        return paymentEntryDto;
    }

    /*
     * (non-Javadoc) using direct payment entry data area
     * @see com.abm.mainet.account.service.PaymentEntrySrevice#findPaymentEntryDataById( java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public PaymentEntryDto findDirectPaymentEntryDataById(final Long id, final Long orgId, int langId) {

        final PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        final AccountPaymentMasterEntity paymentMasterEntity = paymentEntryRepository.findPaymentEntryDataById(id,
                orgId);
        PaymentDetailsDto paymentDetailsdto = null;
        final List<PaymentDetailsDto> paymentDetailsList = new ArrayList<>();

        if (paymentMasterEntity != null) {
            BigDecimal totalPaymentAmount = BigDecimal.ZERO;
            paymentEntryDto.setId(paymentMasterEntity.getPaymentId());

            if ((paymentMasterEntity.getBaBankAccountId() != null)
                    && (paymentMasterEntity.getBaBankAccountId().getBaAccountId() != null)) {
                paymentEntryDto.setBankAcId(paymentMasterEntity.getBaBankAccountId().getBaAccountId());
            }

            paymentEntryDto.setPaymentNo(paymentMasterEntity.getPaymentNo());

            if (paymentMasterEntity.getInstrumentDate() != null) {
                paymentEntryDto
                        .setInsttDate(UtilityService.convertDateToDDMMYYYY(paymentMasterEntity.getInstrumentDate()));
            }

            paymentEntryDto
                    .setPaymentEntryDate(UtilityService.convertDateToDDMMYYYY(paymentMasterEntity.getPaymentDate()));
            if (paymentMasterEntity.getBillTypeId() != null && paymentMasterEntity.getBillTypeId().getCpdId() != null) {
                final String paymentType = CommonMasterUtility.findLookUpDesc(AccountPrefix.ABT.toString(),
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        paymentMasterEntity.getBillTypeId().getCpdId());
                paymentEntryDto.setBillTypeDesc(paymentType);
            }

            if (paymentMasterEntity.getVmVendorId() != null
                    && paymentMasterEntity.getVmVendorId().getVmVendorid() != null) {
                final String vendorDescription = vendorRepository.getVendorNameById(
                        paymentMasterEntity.getVmVendorId().getVmVendorid(), paymentMasterEntity.getOrgId());
                paymentEntryDto.setVendorDesc(vendorDescription);
            }

            if (paymentMasterEntity.getNarration() != null) {
                paymentEntryDto.setNarration(paymentMasterEntity.getNarration());
            }

            if (paymentMasterEntity.getInstrumentNumber() != null) {
                final String chequno = getCheque(paymentMasterEntity.getInstrumentNumber());
                if (chequno != null && !chequno.isEmpty()) {
                    paymentEntryDto.setInstrumentNo(Long.valueOf(chequno));
                }
            }
            if (paymentMasterEntity.getUtrNo() != null && !paymentMasterEntity.getUtrNo().isEmpty()) {
                paymentEntryDto.setUtrNo(paymentMasterEntity.getUtrNo());
            }
            if (paymentMasterEntity.getBillRefNo() != null && !paymentMasterEntity.getBillRefNo().isEmpty()) {
                paymentEntryDto.setBillRefNo(paymentMasterEntity.getBillRefNo());
            }
            if (paymentMasterEntity.getFieldId()!= null) {
                paymentEntryDto.setFieldId(paymentMasterEntity.getFieldId());
            }
            List<AccountPaymentDetEntity> payementDetails = paymentMasterEntity.getPaymentDetailList();
            if (payementDetails != null && !payementDetails.isEmpty()) {
                for (AccountPaymentDetEntity paymentDetail : payementDetails) {
                    paymentDetailsdto = new PaymentDetailsDto();
                    paymentDetailsdto.setId(paymentDetail.getBudgetCodeId());
                    paymentDetailsdto
                            .setPaymentAmount(paymentDetail.getPaymentAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                    paymentDetailsList.add(paymentDetailsdto);
                    totalPaymentAmount = totalPaymentAmount
                            .add(paymentDetail.getPaymentAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN));
                }
                paymentEntryDto.setPaymentDetailsDto(paymentDetailsList);
                paymentEntryDto.setTotalPaymentAmount(
                        new BigDecimal(totalPaymentAmount.toString()).setScale(2, RoundingMode.HALF_EVEN));
            }
        }
        paymentEntryDto.setModeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
                paymentMasterEntity.getOrgId(), paymentMasterEntity.getPaymentModeId().getCpdId()));

        paymentEntryDto.setModeCode(CommonMasterUtility.findLookUpCode(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
                paymentMasterEntity.getOrgId(), paymentMasterEntity.getPaymentModeId().getCpdId()));

        return paymentEntryDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getDetailsForChequeUtilization(final Long paymentId, final Long orgId) {
        final List<Object[]> details = paymentEntryRepository.getPaymentDetailsForChequeUtilization(paymentId, orgId);
        return details;
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentEntryDto getRecordForView(final Long paymentId, final Long orgId, int langId) {

        final PaymentEntryDto paymentEntryDto = new PaymentEntryDto();

        final AccountPaymentMasterEntity paymentDetails = paymentEntryRepository.findPaymentEntryDataById(paymentId,
                orgId);
        if (paymentDetails != null) {
            paymentEntryDto.setPaymentNo(paymentDetails.getPaymentNo());
            paymentEntryDto.setPaymentEntryDate(UtilityService.convertDateToDDMMYYYY(paymentDetails.getPaymentDate()));

            if ((paymentDetails.getBaBankAccountId() != null)
                    && (paymentDetails.getBaBankAccountId().getBaAccountId() != null)) {
                paymentEntryDto.setBankAcId(paymentDetails.getBaBankAccountId().getBaAccountId());
            }

            if (paymentDetails.getInstrumentDate() != null) {
                paymentEntryDto.setInsttDate(UtilityService.convertDateToDDMMYYYY(paymentDetails.getInstrumentDate()));
            }

            if (paymentDetails.getNarration() != null) {
                paymentEntryDto.setNarration(paymentDetails.getNarration());
            }

            /*
             * Organisation org = new Organisation(); org.setOrgid(orgId); final LookUp lkpStatus =
             * CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ISSUED. getValue(),
             * AccountPrefix.CLR.toString(), langId, org); final Long cpdIdStatus = lkpStatus.getLookUpId();
             */

            if (paymentDetails.getInstrumentNumber() != null) {
                final String chequno = getCheque(paymentDetails.getInstrumentNumber());
                if (chequno != null && !chequno.isEmpty()) {
                    paymentEntryDto.setInstrumentNo(Long.valueOf(chequno));
                }
            }
            if (paymentDetails.getUtrNo() != null && !paymentDetails.getUtrNo().isEmpty()) {
                paymentEntryDto.setUtrNo(paymentDetails.getUtrNo());
            }

            if (paymentDetails.getFieldId() != null) {
                paymentEntryDto.setFieldId(paymentDetails.getFieldId());
            }
            /*
             * if (paymentDetails.getInstrumentNumber() != null) {
             * paymentEntryDto.setInstrumentNo(paymentDetails.getInstrumentNumber()); }
             */

            if (paymentDetails.getBillTypeId() != null) {
                final String paymentType = CommonMasterUtility.findLookUpDesc(AccountPrefix.ABT.toString(),
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        paymentDetails.getBillTypeId().getCpdId());
                paymentEntryDto.setBillTypeDesc(paymentType);
            }

            if (paymentDetails.getPmdId() != null) {
                final String paymentTypeDesc = CommonMasterUtility.findLookUpDesc(AccountPrefix.PDM.toString(),
                        UserSession.getCurrent().getOrganisation().getOrgid(), paymentDetails.getPmdId());
                paymentEntryDto.setPaymentTypeDesc(paymentTypeDesc);
                final String paymentTypeCode = CommonMasterUtility.findLookUpCode(AccountPrefix.PDM.toString(),
                        UserSession.getCurrent().getOrganisation().getOrgid(), paymentDetails.getPmdId());
                paymentEntryDto.setPaymentTypeCode(paymentTypeCode);
            }

            if (paymentDetails.getVmVendorId() != null) {
                final String vendorDescription = vendorRepository
                        .getVendorNameById(paymentDetails.getVmVendorId().getVmVendorid(), paymentDetails.getOrgId());
                paymentEntryDto.setVendorDesc(vendorDescription);
            }
            final String payAmount = CommonMasterUtility.getAmountInIndianCurrency(paymentDetails.getPaymentAmount());
            paymentEntryDto.setSanctionedAmountStr(payAmount);
            getPaymentDetailsData(paymentEntryDto, paymentDetails);
        }

        paymentEntryDto.setModeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
                paymentDetails.getOrgId(), paymentDetails.getPaymentModeId().getCpdId()));

        paymentEntryDto.setModeCode(CommonMasterUtility.findLookUpCode(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
                paymentDetails.getOrgId(), paymentDetails.getPaymentModeId().getCpdId()));

        return paymentEntryDto;
    }

    /**
     * @param paymentEntryDto
     * @param paymentDetails
     */
    private void getPaymentDetailsData(final PaymentEntryDto paymentEntryDto,
            final AccountPaymentMasterEntity paymentDetails) {

        final List<PaymentDetailsDto> paymentDetailsDtoList = new ArrayList<>();
        String billAmountStr = null;
        String deductionsStr = null;
        String netPayableStr = null;
        Integer count = 1;
        BigDecimal netPayableAmount = new BigDecimal(0.00);
        if (!paymentDetails.getPaymentDetailList().isEmpty()) {
            AccountBillEntryMasterEnitity billMasterEntity = null;
            List<AccountPaymentDetEntity> detlist = paymentDetails.getPaymentDetailList();
            Integer listSize = detlist.size();
            BigDecimal newNetBalPayableAmt = BigDecimal.ZERO;
            for (AccountPaymentDetEntity accountPaymentDetEntity : detlist) {
                billMasterEntity = billEntryRepository.findBillEntryById(paymentDetails.getOrgId(),
                        accountPaymentDetEntity.getBillId());

                if (billMasterEntity != null) {

                    String billType = CommonMasterUtility.findLookUpCode(AccountPrefix.ABT.toString(),
                            paymentDetails.getOrgId(), billMasterEntity.getBillTypeId().getCpdId());

                    if (billType.equals("ESB")) {

                        PaymentDetailsDto paymentDetail = new PaymentDetailsDto();

                        paymentDetail.setId(billMasterEntity.getId());
                        paymentDetail.setBillNumber(billMasterEntity.getBillNo());
                        billAmountStr = CommonMasterUtility
                                .getAmountInIndianCurrency(billMasterEntity.getBillTotalAmount());
                        paymentDetail.setAmount(billMasterEntity.getBillTotalAmount().toString());

                        BigDecimal ratio = BigDecimal.ZERO;
                        BigDecimal sumOfBillBalanceAmount = BigDecimal.ZERO;
                        BigDecimal sumOfBillDeductionAmount = BigDecimal.ZERO;
                        List<AccountBillEntryExpenditureDetEntity> expList = billMasterEntity
                                .getExpenditureDetailList();
                        for (AccountBillEntryExpenditureDetEntity expBillEntryDetails : expList) {
                            sumOfBillBalanceAmount = sumOfBillBalanceAmount
                                    .add(expBillEntryDetails.getBillChargesAmount());
                        }
                        List<AccountBillEntryDeductionDetEntity> dedList = billMasterEntity.getDeductionDetailList();
                        if (dedList != null && !dedList.isEmpty()) {
                            for (AccountBillEntryDeductionDetEntity dedBillEntryDetails : dedList) {
                                sumOfBillDeductionAmount = sumOfBillDeductionAmount
                                        .add(dedBillEntryDetails.getDeductionAmount());
                            }
                        }
                        BigDecimal sumOfBillProRataBalanceAmount = sumOfBillBalanceAmount.setScale(2,
                                BigDecimal.ROUND_HALF_EVEN);
                        BigDecimal sumOfBillProRataDeductionAmount = sumOfBillDeductionAmount.setScale(2,
                                BigDecimal.ROUND_HALF_EVEN);
                        ratio = sumOfBillProRataDeductionAmount.divide(sumOfBillProRataBalanceAmount,
                                MathContext.DECIMAL128);
                        List<AccountBillEntryExpenditureDetEntity> expListProRata = billMasterEntity
                                .getExpenditureDetailList();
                        for (AccountBillEntryExpenditureDetEntity expBillEntryProRataDetails : expListProRata) {

                            if (expBillEntryProRataDetails.getSacHeadId()
                                    .equals(accountPaymentDetEntity.getBudgetCodeId())) {
                                BigDecimal proRataDeductionAmt = BigDecimal.ZERO;
                                BigDecimal proRataPayableAmt = BigDecimal.ZERO;
                                proRataDeductionAmt = expBillEntryProRataDetails.getBillChargesAmount().multiply(ratio)
                                        .setScale(0, BigDecimal.ROUND_HALF_EVEN);
                                proRataPayableAmt = expBillEntryProRataDetails.getBillChargesAmount()
                                        .subtract(proRataDeductionAmt);
                                paymentDetail.setAmount(CommonMasterUtility
                                        .getAmountInIndianCurrency(expBillEntryProRataDetails.getBillChargesAmount()));
                                paymentDetail.setDeductions(
                                        CommonMasterUtility.getAmountInIndianCurrency(proRataDeductionAmt));

                                if (expBillEntryProRataDetails.getFi04V1() != null
                                        && !expBillEntryProRataDetails.getFi04V1().isEmpty()
                                        && !expBillEntryProRataDetails.getFi04V1().equals("0.00")) {
                                    newNetBalPayableAmt = newNetBalPayableAmt
                                            .add(new BigDecimal(expBillEntryProRataDetails.getFi04V1()));
                                }
                                if (newNetBalPayableAmt != null && newNetBalPayableAmt != BigDecimal.ZERO) {
                                    // netAmount = netAmount.add(newNetBalPayableAmt);
                                    BigDecimal balPaymentAmount = new BigDecimal(
                                            expBillEntryProRataDetails.getFi04V1());
                                    if (count.equals(listSize)) {
                                        BigDecimal sumPaymentAmount = billEntryRepository
                                                .findPaymentAmount(billMasterEntity.getId(), paymentDetails.getOrgId());
                                        if (sumPaymentAmount != null) {
                                            newNetBalPayableAmt = newNetBalPayableAmt.add(sumPaymentAmount);
                                        }
                                        if (newNetBalPayableAmt.compareTo(netPayableAmount) == 1) {
                                            balPaymentAmount = balPaymentAmount.subtract(new BigDecimal(1));
                                        } else if (newNetBalPayableAmt.compareTo(netPayableAmount) == -1) {
                                            BigDecimal subPaymentAmt = netPayableAmount.subtract(newNetBalPayableAmt);
                                            if (subPaymentAmt.compareTo(new BigDecimal(1)) == 0) {
                                                balPaymentAmount = balPaymentAmount.add(new BigDecimal(1));
                                            }
                                        }
                                        // newNetBalPayableAmt = newNetBalPayableAmt.subtract(sumPaymentAmount);
                                    }
                                    count++;
                                    paymentDetail.setNetPayable(CommonMasterUtility.getAmountInIndianCurrency(
                                            balPaymentAmount.setScale(0, BigDecimal.ROUND_HALF_EVEN))); // set amount
                                                                                                        // for different
                                } else {
                                    paymentDetail.setNetPayable(CommonMasterUtility.getAmountInIndianCurrency(
                                            proRataPayableAmt.setScale(0, BigDecimal.ROUND_HALF_EVEN)));
                                }
                                paymentDetail.setPaymentAmountDesc(CommonMasterUtility
                                        .getAmountInIndianCurrency(accountPaymentDetEntity.getPaymentAmt()));
                                paymentDetail.setBchId(accountPaymentDetEntity.getBudgetCodeId());
                                String acHeadCode = tbAcSecondaryheadMasterJpaRepository
                                        .findByAccountHead(accountPaymentDetEntity.getBudgetCodeId());
                                paymentDetail.setAccountCode(acHeadCode);
                                paymentDetailsDtoList.add(paymentDetail);
                            }
                        }
                    } else {

                        PaymentDetailsDto paymentDetail = new PaymentDetailsDto();
                        BigDecimal totalDeductionAmount = BigDecimal.ZERO;
                        paymentDetail.setId(billMasterEntity.getId());
                        paymentDetail.setBillNumber(billMasterEntity.getBillNo());
                        billAmountStr = CommonMasterUtility
                                .getAmountInIndianCurrency(billMasterEntity.getBillTotalAmount());
                        paymentDetail.setAmount(billMasterEntity.getBillTotalAmount().toString());
                        final List<AccountBillEntryExpenditureDetEntity> expDetList = billMasterEntity
                                .getExpenditureDetailList();
                        BigDecimal balPaymentAmount = BigDecimal.ZERO;
                        if ((expDetList != null) && !expDetList.isEmpty()) {
                            for (final AccountBillEntryExpenditureDetEntity expDetEntity : expDetList) {
                                if (expDetEntity.getSacHeadId().equals(accountPaymentDetEntity.getBudgetCodeId())) {
                                    if (expDetEntity.getFi04V1() != null && !expDetEntity.getFi04V1().isEmpty()
                                            && !expDetEntity.getFi04V1().equals("0.00")) {
                                        balPaymentAmount = new BigDecimal(expDetEntity.getFi04V1());
                                    }
                                    if (expDetEntity.getBillChargesAmount() != null) {
                                        billAmountStr = CommonMasterUtility
                                                .getAmountInIndianCurrency(expDetEntity.getBillChargesAmount());
                                    }
                                    paymentDetail.setAmount(billAmountStr);
                                }
                            }
                        } else {
                            LOGGER.error("Expenditure data not found");
                        }
                        final List<AccountBillEntryDeductionDetEntity> dedDetList = billMasterEntity
                                .getDeductionDetailList();
                        if ((dedDetList != null) && !dedDetList.isEmpty()) {
                            for (final AccountBillEntryDeductionDetEntity dedDetEntity : dedDetList) {
                            	// Defect #150832
                                //if (dedDetEntity.getBchId() != null) {
                                  // if (dedDetEntity.getBchId().equals(accountPaymentDetEntity.getBudgetCodeId())) {
                                        if (dedDetEntity.getDeductionAmount() != null) {
                                            totalDeductionAmount = totalDeductionAmount
                                                    .add(dedDetEntity.getDeductionAmount());
                                        }
                                   // }
                              //  }
                            }
                        }
                        deductionsStr = CommonMasterUtility.getAmountInIndianCurrency(totalDeductionAmount);
                        paymentDetail.setDeductions(deductionsStr);
                        netPayableStr = CommonMasterUtility
                                .getAmountInIndianCurrency(new BigDecimal(billAmountStr.replaceAll(",", ""))
                                        .subtract(totalDeductionAmount).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                        if (balPaymentAmount != null && balPaymentAmount != BigDecimal.ZERO) {
                            BigDecimal newPaymentAmount = accountPaymentDetEntity.getPaymentAmt();
                            if (balPaymentAmount.compareTo(newPaymentAmount) == 0) {
                                paymentDetail
                                        .setNetPayable(CommonMasterUtility.getAmountInIndianCurrency(balPaymentAmount));
                            } else {
                                paymentDetail.setNetPayable(CommonMasterUtility
                                        .getAmountInIndianCurrency(balPaymentAmount.add(newPaymentAmount)));
                            }
                        } else {
                            paymentDetail.setNetPayable(netPayableStr);
                        }
                        /*
                         * if (paymentEntryDto.getPaymentTypeCode().equals("W")) {
                         * paymentDetail.setPaymentAmountDesc(netPayableStr); } else {
                         */
                        paymentDetail.setPaymentAmountDesc(
                                CommonMasterUtility.getAmountInIndianCurrency(accountPaymentDetEntity.getPaymentAmt()));
                        paymentDetail.setBchId(accountPaymentDetEntity.getBudgetCodeId());
                        String acHeadCode = tbAcSecondaryheadMasterJpaRepository
                                .findByAccountHead(accountPaymentDetEntity.getBudgetCodeId());
                        paymentDetail.setAccountCode(acHeadCode);

                        paymentDetailsDtoList.add(paymentDetail);
                    }
                }
            }
        }
        paymentEntryDto.setPaymentDetailsDto(paymentDetailsDtoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reversePaymentEntry(final List<String> transactionIds, final VoucherReversalDTO dto, final long fieldId,
            final long orgId, String ipMacAddress) {
        // do reversal for each
        for (final String primaryKey : transactionIds) {
            final long paymentId = Long.parseLong(primaryKey);
            voucherPostingAgainstBillReversal(dto, paymentEntryRepository.findPaymentEntryDataById(paymentId, orgId),
                    fieldId, orgId, ipMacAddress);
            paymentEntryDao.reversePaymentEntry(dto, paymentId, orgId, ipMacAddress);
            paymentEntryDao.updateBillPaymentStatus(paymentId, orgId);
            Long instrumentNoId = paymentEntryRepository.findInstrumentNumberIdById(paymentId, orgId);
            if (instrumentNoId != null) {
                updateChequeNoStatusForReversal(instrumentNoId, orgId);
            }
        }
    }

    private void updateChequeNoStatusForReversal(Long instrumentNoId, long orgId) {
        // TODO Auto-generated method stub
        Long cpdIdStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.NOT_ISSUED.getValue(),
                AccountPrefix.CLR.toString(), orgId);
        chequeBookLeafRepository.updateChequeDetailsForPaymentsReversal(cpdIdStatus, instrumentNoId, orgId);
    }

    private void voucherPostingAgainstBillReversal(final VoucherReversalDTO reversalDTO,
            final AccountPaymentMasterEntity entity, final long fieldId, final long orgId, final String ipMacAddress) {

        final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.VOT.toString(), orgId);
        // final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(DirectPaymentEntry.PVE,
        // AccountPrefix.TDP.toString(), orgId);
        Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.RRE.toString(),
                AccountPrefix.TDP.toString(), orgId);
        Long voucherSubTypeId1 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DSR.toString(),
                AccountPrefix.TDP.toString(), orgId);
        Long voucherSubTypeId2 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.RBI.toString(),
                AccountPrefix.TDP.toString(), orgId);
        Long voucherSubTypeId3 = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.RBP.toString(),
                AccountPrefix.TDP.toString(), orgId);
        List<Long> vouSubTypes = new ArrayList<>(0);
        vouSubTypes.add(voucherSubTypeId);
        vouSubTypes.add(voucherSubTypeId1);
        vouSubTypes.add(voucherSubTypeId2);
        vouSubTypes.add(voucherSubTypeId3);
        long entryTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountJournalVoucherEntry.MAN,
                AccountJournalVoucherEntry.VET, orgId);
        if (entryTypeId == 0l) {
            throw new NullPointerException(
                    "entryType id not found for for lookUpCode[MAN] from VET Prefix in Bill/Payment Reversal voucher posting.");
        }
        final List<AccountVoucherEntryEntity> acVouEntryEntity = journalVoucherDao.getReceiptReversalVoucherDetails(
                entity.getPaymentNo(), entity.getPaymentDate(), voucherTypeId, orgId,
                departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()), vouSubTypes, entryTypeId);
        Objects.requireNonNull(acVouEntryEntity,
                ApplicationSession.getInstance().getMessage("payment.entry.service.voucher.posting"));

        for (final AccountVoucherEntryEntity acVoucherEntryEntity : acVouEntryEntity) {

            AccountVoucherEntryEntity bean = new AccountVoucherEntryEntity();
            final List<AccountVoucherEntryDetailsEntity> detailsEntity = new ArrayList<>();
            AccountVoucherEntryDetailsEntity postDetailEntityCr = null;
            AccountVoucherEntryDetailsEntity postDetailEntityDr = null;

            // postDto.setVoucherDate(new Date());
            final String voucherType = CommonMasterUtility.findLookUpCode(AccountPrefix.VOT.toString(), orgId,
                    acVoucherEntryEntity.getVouTypeCpdId());

            final Organisation org = new Organisation();
            org.setOrgid(orgId);

            if (voucherType.equals(PrefixConstants.DirectPaymentEntry.PV)) {

                bean.setVouSubtypeCpdId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(DirectPaymentEntry.RBP,
                        PrefixConstants.REV_TYPE_CPD_VALUE, orgId));
                bean.setVouReferenceNo(entity.getPaymentNo().toString());// rcptno
                bean.setVouDate(entity.getPaymentDate());
                bean.setNarration(reversalDTO.getNarration());
                bean.setOrg(orgId);
                bean.setCreatedBy(reversalDTO.getApprovedBy());
                bean.setLmodDate(new Date());
                bean.setLgIpMac(ipMacAddress);
                // bean.setTemplateType(AccountConstants.PN.toString());
                bean.setEntryType(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                        MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE, AccountPrefix.VET.toString(), orgId));
                bean.setAuthoDate(acVoucherEntryEntity.getAuthoDate());
                bean.setLmodDate(acVoucherEntryEntity.getLmodDate());
                bean.setUpdatedDate(acVoucherEntryEntity.getUpdatedDate());
                bean.setVouPostingDate(acVoucherEntryEntity.getVouPostingDate());
                bean.setVouReferenceNoDate(acVoucherEntryEntity.getVouReferenceNoDate());
                bean.setAuthoFlg(MainetConstants.MENU.Y);
                bean.setAuthoId(acVoucherEntryEntity.getAuthoId());
                bean.setAuthRemark(acVoucherEntryEntity.getAuthRemark());
                bean.setPayerPayee(acVoucherEntryEntity.getPayerPayee());
                bean.setUpdatedby(acVoucherEntryEntity.getUpdatedby());

                String vouNo = generateVoucherNo(MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, org.getOrgid(),
                        entity.getPaymentDate());
                bean.setVouNo(vouNo);
                bean.setVouTypeCpdId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                        MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, AccountPrefix.VOT.toString(), orgId));
                bean.setDpDeptid(acVoucherEntryEntity.getDpDeptid());
                bean.setFieldId(acVoucherEntryEntity.getFieldId());

                for (final AccountVoucherEntryDetailsEntity detEntity : acVoucherEntryEntity.getDetails()) {
                    final String drcrCpdId = CommonMasterUtility.findLookUpCode(PrefixConstants.DCR, orgId,
                            detEntity.getDrcrCpdId());
                    if (drcrCpdId.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS)) {
                        // Debit side
                        postDetailEntityDr = new AccountVoucherEntryDetailsEntity(); // BeanUtils.copyProperties(postDetailEntityDr,
                                                                                     // detEntity);
                        // postDetailEntityDr.setVoudetId(detEntity.getVoudetId());
                        long drId = CommonMasterUtility.getValueFromPrefixLookUp(
                                PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, org).getLookUpId();
                        postDetailEntityDr.setCreatedBy(detEntity.getCreatedBy());
                        postDetailEntityDr.setDrcrCpdId(drId);
                        postDetailEntityDr.setOrgId(detEntity.getOrgId());
                        postDetailEntityDr.setSacHeadId(detEntity.getSacHeadId());
                        postDetailEntityDr.setUpdatedBy(detEntity.getUpdatedBy());
                        postDetailEntityDr.setBudgetCode(detEntity.getBudgetCode());
                        postDetailEntityDr.setFi04D1(detEntity.getFi04D1());
                        postDetailEntityDr.setFieldCode(detEntity.getFieldCode());
                        postDetailEntityDr.setFunctionCode(detEntity.getFunctionCode());
                        postDetailEntityDr.setFundCode(detEntity.getFundCode());
                        postDetailEntityDr.setLgIpMac(detEntity.getLgIpMac());
                        postDetailEntityDr.setLgIpMacUpd(detEntity.getLgIpMacUpd());
                        postDetailEntityDr.setLmodDate(detEntity.getLmodDate());
                        postDetailEntityDr.setMaster(bean);
                        postDetailEntityDr.setPrimaryHeadCode(detEntity.getPrimaryHeadCode());
                        postDetailEntityDr.setSecondaryHeadCode(detEntity.getSecondaryHeadCode());
                        postDetailEntityDr.setUpdatedDate(detEntity.getUpdatedDate());
                        postDetailEntityDr.setVoudetAmt(detEntity.getVoudetAmt());
                        detailsEntity.add(postDetailEntityDr);
                    } else if (drcrCpdId.equals(PrefixConstants.AccountJournalVoucherEntry.DR)) {

                        long crId = CommonMasterUtility.getValueFromPrefixLookUp(
                                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS,
                                PrefixConstants.DCR, org).getLookUpId();
                        // Credit side
                        postDetailEntityCr = new AccountVoucherEntryDetailsEntity();
                        // postDetailEntityCr.setVoudetId(detEntity.getVoudetId());
                        postDetailEntityCr.setCreatedBy(detEntity.getCreatedBy());
                        postDetailEntityCr.setDrcrCpdId(crId);
                        postDetailEntityCr.setOrgId(detEntity.getOrgId());
                        postDetailEntityCr.setSacHeadId(detEntity.getSacHeadId());
                        postDetailEntityCr.setUpdatedBy(detEntity.getUpdatedBy());
                        postDetailEntityCr.setBudgetCode(detEntity.getBudgetCode());
                        postDetailEntityCr.setFi04D1(detEntity.getFi04D1());
                        postDetailEntityCr.setFieldCode(detEntity.getFieldCode());
                        postDetailEntityCr.setFunctionCode(detEntity.getFunctionCode());
                        postDetailEntityCr.setFundCode(detEntity.getFundCode());
                        postDetailEntityCr.setLgIpMac(detEntity.getLgIpMac());
                        postDetailEntityCr.setLgIpMacUpd(detEntity.getLgIpMacUpd());
                        postDetailEntityCr.setLmodDate(detEntity.getLmodDate());
                        postDetailEntityCr.setMaster(bean);
                        postDetailEntityCr.setPrimaryHeadCode(detEntity.getPrimaryHeadCode());
                        postDetailEntityCr.setSecondaryHeadCode(detEntity.getSecondaryHeadCode());
                        postDetailEntityCr.setUpdatedDate(detEntity.getUpdatedDate());
                        postDetailEntityCr.setVoudetAmt(detEntity.getVoudetAmt());
                        // BeanUtils.copyProperties(postDetailEntityCr, detEntity);
                        detailsEntity.add(postDetailEntityCr);
                    }
                }
                bean.setDetails(detailsEntity);
                LOGGER.info("voucherPostingService - voucherPosting -RV voucherPostingAgainstBillReversal:" + bean);
                accountVoucherEntryRepository.save(bean);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentEntryDto getRecordReportForm(final Long paymentId, final Long orgId) {

        final PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
        final AccountPaymentMasterEntity paymentDetails = paymentEntryRepository.findPaymentEntryDataById(paymentId,
                orgId);
        if (paymentDetails != null) {

            final String paymentType = CommonMasterUtility.findLookUpDesc(AccountPrefix.ABT.toString(),
                    UserSession.getCurrent().getOrganisation().getOrgid(), paymentDetails.getBillTypeId().getCpdId());
            paymentEntryDto.setBillTypeDesc(paymentType);

            final String payAmount = CommonMasterUtility.getAmountInIndianCurrency(paymentDetails.getPaymentAmount());
            paymentEntryDto.setSanctionedAmountStr(payAmount);

        }
        return paymentEntryDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getDetailsCheque(final Long bankAccountId, final Long orgId) {

        final List<Object[]> ChequeNumbers = chequeBookLeafRepository.getChequeRangeByBankAccountId(bankAccountId,
                orgId);
        return ChequeNumbers;
    }

    @Override
    @Transactional(readOnly = true)
    public String getCheque(final Long chequebookDetid) {
        final String ChequeNumber = chequeBookLeafRepository.getCheque(chequebookDetid);
        return ChequeNumber;

    }

    @Override
    @Transactional(readOnly = true)
    public String getInstrumentChequeNo(final Long cpdIdstatus, final Long chequebookDetid) {
        final String ChequeNumber = chequeBookLeafRepository.getInstrumentChequeNo(cpdIdstatus, chequebookDetid);
        return ChequeNumber;
    }

    @Override
    public List<TbAcChequebookleafDetEntity> getChequeNumbers(final Long chequeId, final Long orgId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> searchRecordsForPaymentEntry(final PaymentEntryDto dto, final Long orgId) {
        ResponseEntity<?> response = null;
        final List<Object[]> records = paymentEntryRepository.queryRecordsForPaymentEntry(dto, orgId);
        if ((records != null) && !records.isEmpty()) {
            final List<PaymentEntryDto> recordList = records.stream().map(objects -> {
                final PaymentEntryDto entryDto = new PaymentEntryDto();
                mapQueryRecordsToPaymentEntryDto(objects, entryDto);
                return entryDto;
            }).collect(Collectors.toList());
            response = ResponseEntity.status(HttpStatus.FOUND).body(recordList);
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(MainetConstants.MENU.N);
        }
        return response;
    }

    private void mapQueryRecordsToPaymentEntryDto(final Object[] objects, final PaymentEntryDto entryDto) {
        entryDto.setPaymentId((Long) objects[0]);
        if (objects[1] != null) {
            entryDto.setPaymentNo((String) objects[1]);
        }
        if (objects[2] != null) {
            entryDto.setTransactionDate(Utility.dateToString((Date) objects[2]));
        }
        if (objects[3] != null) {
            entryDto.setVendorName((String) objects[3]);
        }
        if (objects[4] != null) {
            entryDto.setPaymentAmount(((BigDecimal) objects[4]).setScale(2));
        }
        entryDto.setViewURL("PaymentEntry.html?view");

    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getExpenditureDetDetails(Long billId, Long orgId) {
        // TODO Auto-generated method stub
        return billEntryRepository.getExpenditureDetDetails(billId, orgId);
    }

    @Override
    @Transactional
    public AccountPaymentMasterEntity findById(Long paymentId, Long orgId) {
        // TODO Auto-generated method stub
        AccountPaymentMasterEntity paymentDetails = paymentEntryRepository.findPaymentEntryDataById(paymentId, orgId);
        return paymentDetails;
    }

    private String generateVoucherNo(final String voucherType, final long orgId, Date paymentDate) {
        Long finYearId = tbFinancialyearService.getFinanciaYearIdByFromDate(paymentDate);
        if (finYearId == null) {
            throw new NullPointerException(GENERATE_VOUCHER_NO_FIN_YEAR_ID);
        }
        final Long voucherTypeId = voucherTypeId(voucherType, orgId);
        if (voucherTypeId == null) {
            throw new NullPointerException(GENERATE_VOUCHER_NO_VOU_TYPE_ID);
        }
        String resetIdValue = voucherTypeId.toString() + finYearId.toString();
        Long resetId = Long.valueOf(resetIdValue);
        final Object sequenceNo = seqGenFunctionUtility.generateSequenceNo(MainetConstants.RECEIPT_MASTER.Module,
                com.abm.mainet.common.constant.MainetConstants.AccountJournalVoucherEntry.TB_AC_VOUCHER,
                com.abm.mainet.common.constant.MainetConstants.AccountJournalVoucherEntry.VOU_NO, orgId,
                MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, resetId);
        if (sequenceNo == null) {
            throw new NullPointerException(SEQUENCE_NO_ERROR + orgId);
        }
        return voucherType.substring(0, 2) + SEQUENCE_NO.substring(sequenceNo.toString().length())
                + sequenceNo.toString();
    }

    private Long voucherTypeId(final String voucherType, final long orgId) {
        final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(voucherType,
                AccountPrefix.VOT.toString(), orgId);
        if (voucherTypeId == 0l) {
            throw new IllegalArgumentException(TEMPLATE_ID_NOT_FOUND_VOU + voucherType + ORG_ID + orgId + PREFIX_VOT);
        }
        return voucherTypeId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getExpenditureDetailHead(Long billId, Long orgId) {
        // TODO Auto-generated method stub
        return billEntryRepository.getExpenditureDetailHead(billId, orgId);

    }

    @Override
    @Transactional(readOnly = true)
    public AccountPaymentMasterEntity findByPaymentNumber(String valueOf, long orgid, Date paymentDate) {
        AccountPaymentMasterEntity paymentDetails = paymentEntryRepository.findPaymentEntryDataByPaymentNumber(valueOf,
                orgid, paymentDate);
        return paymentDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public Date checkClearanceDateExists(Long chequeBookId, Long orgId) {
        // TODO Auto-generated method stub
        Date paymentClearanceDate = paymentEntryRepository.checkClearanceDateExists(chequeBookId, orgId);
        return paymentClearanceDate;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getDepDefundAmountDetailsCheck(Long id, Long orgId) {

        // TODO Auto-generated method stub
        if (id != null && orgId != null)
            return accountDepositJparepository.getDepDefundAmountDetails(id, orgId);
        else
            return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AccountPaymentMasterEntity createTDSPaymentEntry(PaymentEntryDto tdsPaymentEntrydto) throws ParseException {

        if (tdsPaymentEntrydto.getTransactionDate() == null || tdsPaymentEntrydto.getTransactionDate().isEmpty()) {
            tdsPaymentEntrydto.setTransactionDate(tdsPaymentEntrydto.getDupTransactionDate());
        }
        AccountPaymentMasterEntity paymentEntity = new AccountPaymentMasterEntity();
        TbComparamDetEntity comparamDetId = null;
        BankAccountMasterEntity bankAccount = null;
        TbAcVendormasterEntity vendorId = null;
        Long lookUpIdCash = null;
        Long lookUpIdpaytCash = null;
        Long lookUpIdAdj = null;
        Long lookUpIdBank = null;
        if (tdsPaymentEntrydto.getPaymentDate() == null) {
            tdsPaymentEntrydto.setPaymentDate(Utility.stringToDate(tdsPaymentEntrydto.getTransactionDate()));
        }

        if (tdsPaymentEntrydto.getPaymentDate() != null) {
            paymentEntity.setPaymentDate(tdsPaymentEntrydto.getPaymentDate());
        }
        // Bill type id
        //comparamDetId = new TbComparamDetEntity();
        //comparamDetId.setCpdId(tdsPaymentEntrydto.getBillTypeId());
        //paymentEntity.setBillTypeId(comparamDetId);

        // Vendor id
        vendorId = new TbAcVendormasterEntity();
        if (tdsPaymentEntrydto.getVendorId() != null) {
            vendorId.setVmVendorid(tdsPaymentEntrydto.getVendorId());
            paymentEntity.setVmVendorId(vendorId);
        }
        if(tdsPaymentEntrydto.getVendorId() != null) {

            final String vendorDescription = vendorMasterService
                    .getVendorNameById(tdsPaymentEntrydto.getVendorId(), tdsPaymentEntrydto.getOrgId());
            tdsPaymentEntrydto.setVendorDesc(vendorDescription);
        
        }
        // Pay mode
        comparamDetId = new TbComparamDetEntity();
        comparamDetId.setCpdId(tdsPaymentEntrydto.getPaymentMode());
        paymentEntity.setPaymentModeId(comparamDetId);

        // Bank acc id
        bankAccount = new BankAccountMasterEntity();
        if (tdsPaymentEntrydto.getBankAcId() != null) {
            bankAccount.setBaAccountId(tdsPaymentEntrydto.getBankAcId());
            paymentEntity.setBaBankAccountId(bankAccount);
        }
        final LookUp lookUpCash = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.CASH.getValue(),
                AccountPrefix.PAY.toString(), tdsPaymentEntrydto.getLanguageId().intValue(),
                tdsPaymentEntrydto.getOrganisation());
        final LookUp lookUpPaytCash = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.PCA.getValue(), AccountPrefix.PAY.toString(),
                tdsPaymentEntrydto.getLanguageId().intValue(), tdsPaymentEntrydto.getOrganisation());
        final LookUp lookUpAdj = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.A.getValue(),
                AccountPrefix.PAY.toString(), tdsPaymentEntrydto.getLanguageId().intValue(),
                tdsPaymentEntrydto.getOrganisation());
        final LookUp lookUpBank = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MENU.B,
                AccountPrefix.PAY.toString(), tdsPaymentEntrydto.getLanguageId().intValue(),
                tdsPaymentEntrydto.getOrganisation());
        lookUpIdCash = lookUpCash.getLookUpId();
        lookUpIdAdj = lookUpAdj.getLookUpId();
        lookUpIdBank = lookUpBank.getLookUpId();
        lookUpIdpaytCash = lookUpPaytCash.getLookUpId();
        if ((tdsPaymentEntrydto.getPaymentMode() != null) && !tdsPaymentEntrydto.getPaymentMode().equals(lookUpIdCash)
                && !tdsPaymentEntrydto.getPaymentMode().equals(lookUpIdAdj)
                && !tdsPaymentEntrydto.getPaymentMode().equals(lookUpIdBank)
                && !tdsPaymentEntrydto.getPaymentMode().equals(lookUpIdpaytCash)) {
            paymentEntity.setInstrumentNumber(tdsPaymentEntrydto.getInstrumentNo());
            paymentEntity.setInstrumentDate(
                    UtilityService.convertStringDateToDateFormat(tdsPaymentEntrydto.getInstrumentDate()));
        }
        if (tdsPaymentEntrydto.getPaymentMode().equals(lookUpIdBank)) {
            paymentEntity.setUtrNo(tdsPaymentEntrydto.getUtrNo());
        }
        paymentEntity.setAuthoId(tdsPaymentEntrydto.getCreatedBy());

        LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                tdsPaymentEntrydto.getLanguageId().intValue(), tdsPaymentEntrydto.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            paymentEntity.setAuthoDate(tdsPaymentEntrydto.getPaymentDate());
        } else {
            paymentEntity.setAuthoDate(tdsPaymentEntrydto.getCreatedDate());
        }

        paymentEntity.setAuthoFlg(AccountConstants.Y.toString());
        paymentEntity.setPaymentTypeFlag(2L);

        saveCommonFields(paymentEntity, tdsPaymentEntrydto);
        saveTDSPaymentDetails(tdsPaymentEntrydto, paymentEntity);

        Objects.requireNonNull(tdsPaymentEntrydto.getTransactionDate(),
                ApplicationSession.getInstance().getMessage("payment.entry.service.paymentdate") + tdsPaymentEntrydto);
        paymentEntity.setPaymentDate(Utility.stringToDate(tdsPaymentEntrydto.getTransactionDate()));

        paymentEntity.setNarration(tdsPaymentEntrydto.getNarration());

        // tds payment entry voucher posting validate to given VoucherPostDTO
        tdsPaymentEntryVoucherPostingValidation(tdsPaymentEntrydto);

        //payment no coustomization start
        //coustom sequence generation need to configure
    	 String paymentNo=null;
         SequenceConfigMasterDTO configMasterDTO = null;
         Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.AccountConstants.AC.getValue(),
                PrefixConstants.STATUS_ACTIVE_PREFIX);
         configMasterDTO = seqGenFunctionUtility.loadSequenceData(tdsPaymentEntrydto.getOrgId(), deptId,
                MainetConstants.AccountContraVoucherEntry.TB_AC_PAYMENT_MAS2, MainetConstants.AccountContraVoucherEntry.PAYMENT_NO2);
        if (configMasterDTO.getSeqConfigId() == null) {
            paymentNo = generatePaymentNumber(tdsPaymentEntrydto.getOrgId(),
                      tdsPaymentEntrydto.getPaymentDate());
         }else {
        	 CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
        	 AccountFieldMasterBean fieldMaster = accountFieldMasterService.findById(tdsPaymentEntrydto.getFieldId());
     	 if(fieldMaster!=null)
          commonDto.setCustomField(fieldMaster.getFieldDesc());
        	 paymentNo=seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
          }
         //payment no coustomization end
        
        tdsPaymentEntrydto.setPaymentNo(paymentNo);
        paymentEntity.setPaymentNo(paymentNo);

        paymentEntity = paymentEntryRepository.save(paymentEntity);

        final Organisation organisation = new Organisation();
        organisation.setOrgid(tdsPaymentEntrydto.getOrgId());
        final int langId = Integer.parseInt(tdsPaymentEntrydto.getLanguageId().toString());
        final LookUp advanceLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.AD.toString(),
                AccountPrefix.ABT.toString(), langId, organisation);
        final Long advLookUpId = advanceLookup.getLookUpId();

        //if (tdsPaymentEntrydto.getBillTypeId().equals(advLookUpId)) {
         postAdvanceEntryFromTDS(tdsPaymentEntrydto);
        //}

        postTDSPaymentEntry(tdsPaymentEntrydto);
        if (tdsPaymentEntrydto.getInstrumentNo() != null) {
            updateChequeNoStatus(tdsPaymentEntrydto.getInstrumentNo(), tdsPaymentEntrydto.getOrgId(),
                    tdsPaymentEntrydto.getLanguageId().intValue(), tdsPaymentEntrydto.getOrganisation(),
                    paymentEntity.getPaymentId(), paymentEntity.getPaymentDate(), AccountConstants.D.toString());
        }
        return paymentEntity;
    }

    private void tdsPaymentEntryVoucherPostingValidation(PaymentEntryDto paymentEntryDto) {
        LOGGER.info("Process for account tds payment voucher posting validation:" + paymentEntryDto);

        final VoucherPostDTO postDto = new VoucherPostDTO();
        List<VoucherPostDTO> postDtoList = new ArrayList<>();
        final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
        final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
        postDto.setVoucherSubTypeId(voucherSubTypeId);

        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
            postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
        } else {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        }
        postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
        postDto.setDepartmentId(departmentId);
        // postDto.setVoucherReferenceNo(paymentEntryDto.getPaymentNo());

        postDto.setNarration(paymentEntryDto.getNarration());
        postDto.setPayerOrPayee(paymentEntryDto.getVendorDesc());// Vendor name

        postDto.setFieldId(paymentEntryDto.getFieldId());
        postDto.setOrgId(paymentEntryDto.getOrgId());
        postDto.setCreatedBy(paymentEntryDto.getCreatedBy());
        postDto.setCreatedDate(paymentEntryDto.getCreatedDate());
        postDto.setLgIpMac(paymentEntryDto.getLgIpMacAddress());
        postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

        Long lookUpIdCash = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.C.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdAdj = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.A.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdPetty = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        if (paymentEntryDto.getPaymentMode().equals(lookUpIdCash)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdAdj)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdPetty)) {
            // Cash Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long templateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                    AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
            final Long activeStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.IsLookUp.ACTIVE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, paymentEntryDto.getOrgId());
            VoucherTemplateMasterEntity entity = voucherTemplateRepository
                    .findAcHeadCodeInTemplateForCashMode(templateFor, paymentEntryDto.getOrgId(), activeStatus);
            Long sacHeadIdCr = null;
            List<VoucherTemplateDetailEntity> detList = entity.getTemplateDetailEntities();
            for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : detList) {
                if (voucherTemplateDetailEntity.getCpdIdPayMode().equals(paymentEntryDto.getPaymentMode())) {
                    if (voucherTemplateDetailEntity.getSacHeadId() != null) {
                        sacHeadIdCr = voucherTemplateDetailEntity.getSacHeadId();
                        break;
                    }
                }
            }
            if (sacHeadIdCr == null) {
                throw new NullPointerException(PAYMODE + " templateFor is : " + templateFor + " pay mode is : "
                        + paymentEntryDto.getPaymentMode() + " orgid is : " + paymentEntryDto.getOrgId()
                        + " status is active : " + activeStatus);
            }
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        } else {
            // Bank Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long sacHeadIdCr = getSacHeadIdByBankAccountId(paymentEntryDto.getBankAcId(),
                    paymentEntryDto.getOrgId());
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        }
        // Account Head Debit side
        for (final PaymentDetailsDto paymentDetailsDtoList : paymentEntryDto.getPaymentDetailsDto()) {
            final VoucherPostDetailDTO postDetailDtoDr = new VoucherPostDetailDTO();
            final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoDr.setDrCrId(drId);
            postDetailDtoDr.setSacHeadId(paymentDetailsDtoList.getId());
            postDetailDtoDr.setVoucherAmount(new BigDecimal(paymentEntryDto.getTotal()));
            voucherDetails.add(postDetailDtoDr);
            break;
        }
        postDto.setVoucherDetails(voucherDetails);
        postDtoList.add(postDto);
        List<String> responseValidation = accountVoucherPostService.checkVoucherPostValidateInput(postDtoList);
        if (responseValidation.size() > 0) {
            throw new NullPointerException(
                    "improper input parameter for VoucherPostDTO in tds payment entry -> " + responseValidation);
        }
    }

    // Posting the direct payment entry
    private void postTDSPaymentEntry(final PaymentEntryDto paymentEntryDto) {

        final VoucherPostDTO postDto = new VoucherPostDTO();
        List<VoucherPostDTO> postDtoList = new ArrayList<>();
        final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
        final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
        postDto.setVoucherSubTypeId(voucherSubTypeId);

        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                paymentEntryDto.getLanguageId().intValue(), paymentEntryDto.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
            postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
        } else {
            // postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        }
        postDto.setVoucherDate(Utility.stringToDate(paymentEntryDto.getTransactionDate()));
        final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
        postDto.setDepartmentId(departmentId);
        postDto.setVoucherReferenceNo(paymentEntryDto.getPaymentNo());

        postDto.setNarration(paymentEntryDto.getNarration());
        postDto.setPayerOrPayee(paymentEntryDto.getVendorDesc());// Vendor name

        postDto.setFieldId(paymentEntryDto.getFieldId());
        postDto.setOrgId(paymentEntryDto.getOrgId());
        postDto.setCreatedBy(paymentEntryDto.getCreatedBy());
        postDto.setCreatedDate(paymentEntryDto.getCreatedDate());
        postDto.setLgIpMac(paymentEntryDto.getLgIpMacAddress());
        postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

        Long lookUpIdCash = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.C.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdAdj = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.A.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        Long lookUpIdPetty = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                AccountPrefix.PAY.toString(), paymentEntryDto.getOrgId());
        if (paymentEntryDto.getPaymentMode().equals(lookUpIdCash)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdAdj)
                || paymentEntryDto.getPaymentMode().equals(lookUpIdPetty)) {
            // Cash Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long templateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                    AccountPrefix.TDP.toString(), paymentEntryDto.getOrgId());
            final Long activeStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.IsLookUp.ACTIVE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, paymentEntryDto.getOrgId());
            VoucherTemplateMasterEntity entity = voucherTemplateRepository
                    .findAcHeadCodeInTemplateForCashMode(templateFor, paymentEntryDto.getOrgId(), activeStatus);
            Long sacHeadIdCr = null;
            List<VoucherTemplateDetailEntity> detList = entity.getTemplateDetailEntities();
            for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : detList) {
                if (voucherTemplateDetailEntity.getCpdIdPayMode().equals(paymentEntryDto.getPaymentMode())) {
                    if (voucherTemplateDetailEntity.getSacHeadId() != null) {
                        sacHeadIdCr = voucherTemplateDetailEntity.getSacHeadId();
                        break;
                    }
                }
            }
            if (sacHeadIdCr == null) {
                throw new NullPointerException(PAYMODE + " templateFor is : " + templateFor + " pay mode is : "
                        + paymentEntryDto.getPaymentMode() + " orgid is : " + paymentEntryDto.getOrgId()
                        + " status is active : " + activeStatus);
            }
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        } else {
            // Bank Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(paymentEntryDto.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long sacHeadIdCr = getSacHeadIdByBankAccountId(paymentEntryDto.getBankAcId(),
                    paymentEntryDto.getOrgId());
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(paymentEntryDto.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        }
        // Account Head Debit side
        for (final PaymentDetailsDto paymentDetailsDtoList : paymentEntryDto.getPaymentDetailsDto()) {
            final VoucherPostDetailDTO postDetailDtoDr = new VoucherPostDetailDTO();
            final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                    PrefixConstants.DCR, paymentEntryDto.getOrgId());
            postDetailDtoDr.setDrCrId(drId);
            postDetailDtoDr.setSacHeadId(paymentDetailsDtoList.getId());
            postDetailDtoDr.setVoucherAmount(new BigDecimal(paymentEntryDto.getTotal()));
            voucherDetails.add(postDetailDtoDr);
            break;
        }
        postDto.setVoucherDetails(voucherDetails);
        postDtoList.add(postDto);
        ApplicationSession session = ApplicationSession.getInstance();
        String responseValidation = accountVoucherPostService.validateInput(postDtoList);
        AccountVoucherEntryEntity response = null;
        if (responseValidation.isEmpty()) {
            response = accountVoucherPostService.voucherPosting(postDtoList);
            if (response == null) {
                throw new IllegalArgumentException("Voucher Posting failed");
            }
        } else {
            LOGGER.error(session.getMessage("account.voucher.service.posting")
                    + session.getMessage("account.voucher.posting.improper.input") + responseValidation);
            throw new IllegalArgumentException(
                    "Voucher Posting failed : proper data is not exist" + responseValidation);
        }
    }

    /**
     * @param paymentEntryDto
     * @param paymentEntity
     */
    private void saveTDSPaymentDetails(final PaymentEntryDto paymentEntryDto,
            final AccountPaymentMasterEntity paymentEntity) {
        AccountPaymentDetEntity paymentDetailsEntity = null;
        final List<AccountPaymentDetEntity> paymentDetailsList = new ArrayList<>();

        final List<PaymentDetailsDto> billDetails = paymentEntryDto.getPaymentDetailsDto();

        if ((billDetails != null) && !billDetails.isEmpty()) {
            for (final PaymentDetailsDto billDetail : billDetails) {
                if (billDetail.isVendorDetCheckFlag()) {
                    paymentDetailsEntity = new AccountPaymentDetEntity();
                    paymentDetailsEntity.setBudgetCodeId(billDetail.getId());
                    paymentDetailsEntity.setPaymentAmt(new BigDecimal(billDetail.getDeductions()));
                    /* paymentDetailsEntity.setPaymentDeductionAmt(); */
                    paymentDetailsEntity.setBdhIdDeduction(billDetail.getBdhId());
                    paymentDetailsEntity.setBillId(billDetail.getBmId());
                    if (billDetail.getBdhId() != null) {
                        billEntryRepository.updateDeductionBalanceAmount(billDetail.getBdhId(), BigDecimal.ZERO);
                    }
                    paymentDetailsEntity.setOrgId(paymentEntity.getOrgId());
                    paymentDetailsEntity.setCreatedBy(paymentEntity.getCreatedBy());
                    paymentDetailsEntity.setCreatedDate(paymentEntity.getCreatedDate());
                    paymentDetailsEntity.setLangId(paymentEntryDto.getLanguageId());
                    paymentDetailsEntity.setLgIpMac(paymentEntity.getLgIpMac());
                    paymentDetailsEntity.setPaymentMasterId(paymentEntity);
                    paymentDetailsList.add(paymentDetailsEntity);
                }
            }
            paymentEntity.setPaymentDetailList(paymentDetailsList);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountPaymentMasterEntity> getTdsPaymentDetails(Long orgId, String paymentEntryDate,
            BigDecimal paymentAmount, Long vendorId, Long budgetCodeId, String paymentNo, Long baAccountid,
            Long paymentTypeFlag) {

        final List<AccountPaymentMasterEntity> paymentDetailsList = paymentEntryDao.getTdsPaymentDetails(orgId,
                paymentEntryDate, paymentAmount, vendorId, budgetCodeId, paymentNo, baAccountid, paymentTypeFlag);
        return paymentDetailsList;
        // TODO Auto-generated method stub

    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetailsDto> getPaymentDetails(Long orgId, Date fromDate, Date toDate, Long paymentTypeFlag) {

        final Iterable<Object[]> tdsPaymentDetailsList = paymentEntryRepository.getTdsPaymenDetails(orgId, fromDate,
                toDate, paymentTypeFlag);
        final List<PaymentDetailsDto> tdsPaymentList = new ArrayList<>();

        if (tdsPaymentDetailsList != null) {
            PaymentDetailsDto tdsPaymentDto = null;
            for (Object[] tdsPaymentEntity : tdsPaymentDetailsList) {
                tdsPaymentDto = new PaymentDetailsDto();
                tdsPaymentDto.setPaymentDate(Utility.dateToString((Date) tdsPaymentEntity[0]));
                tdsPaymentDto.setPaymentNo(tdsPaymentEntity[1].toString());
                if (tdsPaymentEntity[2] != null) {
                    tdsPaymentDto.setTdsPaymentAmt(CommonMasterUtility
                            .getAmountInIndianCurrency(new BigDecimal(tdsPaymentEntity[2].toString())));
                }
                if (tdsPaymentEntity[3] != null) {
                    tdsPaymentDto.setPaymentId((Long) tdsPaymentEntity[3]);
                    List<TdsAcknowledgementPaymentEntity> list = tdsAcknowledgementPaymentMasterJpaRepository
                            .checkPayIdExists((Long) tdsPaymentEntity[3], orgId);
                    if (list == null || list.isEmpty()) {
                        tdsPaymentDto.setPaymentIdFlagExist(MainetConstants.N_FLAG);
                    } else {
                        tdsPaymentDto.setPaymentIdFlagExist(MainetConstants.Y_FLAG);
                    }
                }
                tdsPaymentList.add(tdsPaymentDto);
            }
        }
        return tdsPaymentList;
    }

    @Override
    @Transactional
    public void createTdsAckPaymentEntry(PaymentEntryDto paymentEntrydto) throws ParseException {
        // TODO Auto-generated method stub

        TdsAcknowledgementPaymentEntity tdsAckpaymentEntity = new TdsAcknowledgementPaymentEntity();
        saveCommonFields(tdsAckpaymentEntity, paymentEntrydto);
        AccountPaymentMasterEntity paymetEntity = new AccountPaymentMasterEntity();
        paymetEntity.setPaymentId(paymentEntrydto.getPaymentId());
        tdsAckpaymentEntity.setPaymentId(paymetEntity);
        tdsAckpaymentEntity.setChallanNo(paymentEntrydto.getChallanNo());
        tdsAckpaymentEntity.setAckDate(Utility.stringToDate(paymentEntrydto.getAckDate()));
        tdsAckpaymentEntity.setChallanDate(Utility.stringToDate(paymentEntrydto.getChallanDate()));
        tdsAckpaymentEntity.setAckNo(paymentEntrydto.getAckNo());
        tdsAckpaymentEntity.setCpIdQtr(paymentEntrydto.getQtrId());
        tdsAcknowledgementPaymentMasterJpaRepository.save(tdsAckpaymentEntity);

    }

    // Save common TdsAckPaymentEntry entity fields
    private void saveCommonFields(final TdsAcknowledgementPaymentEntity paymentEntity,
            final PaymentEntryDto paymentEntryDto) {
        paymentEntity.setOrgId(paymentEntryDto.getOrgId());
        paymentEntity.setCreatedBy(paymentEntryDto.getCreatedBy());
        paymentEntity.setCreatedDate(paymentEntryDto.getCreatedDate());
        paymentEntity.setLgIpMac(paymentEntryDto.getLgIpMacAddress());
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentEntryDto findTdsAckPaymentDetailsById(long gridId, Long orgId) {
        // TODO Auto-generated method stub
        final List<TdsAcknowledgementPaymentEntity> tdsPaymentDetailsList = tdsAcknowledgementPaymentMasterJpaRepository
                .TdsAckPaymentDetailsById(gridId, orgId);
        PaymentEntryDto paymentEntryDto = null;
        if (tdsPaymentDetailsList != null) {
            for (TdsAcknowledgementPaymentEntity tdsPaymentEntity : tdsPaymentDetailsList) {
                paymentEntryDto = new PaymentEntryDto();
                paymentEntryDto.setAckDate(Utility.dateToString(tdsPaymentEntity.getAckDate()));
                paymentEntryDto.setAckNo(tdsPaymentEntity.getAckNo());
                paymentEntryDto.setChallanNo(tdsPaymentEntity.getChallanNo());
                paymentEntryDto.setChallanDate(Utility.dateToString(tdsPaymentEntity.getChallanDate()));
                paymentEntryDto.setCpIdQtr(tdsPaymentEntity.getCpIdQtr().toString());

            }
        }
        return paymentEntryDto;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RTGSPaymentEntryDTO createRTGSPaymentEntryFormData(RTGSPaymentEntryDTO rtgsPaymentEntryDTO) throws ParseException {

        AccountPaymentMasterEntity paymentEntity = new AccountPaymentMasterEntity();
        TbComparamDetEntity comparamDetId = null;
        BankAccountMasterEntity bankAccount = null;
        TbAcVendormasterEntity vendorId = null;
        Long lookUpIdCash = null;
        Long lookUpIdAdj = null;
        Long lookUpIdBank = null;
        Long lookUpIdpaytCash = null;
        paymentEntity.setPaymentDate(rtgsPaymentEntryDTO.getPaymentDate());

        // Work flow
        /*
         * ServiceMaster service = serviceMasterService.getServiceByShortName("PE", rtgsPaymentEntryDTO.getOrgId()); if(service ==
         * null) return null; WorkflowMas workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(service.getOrgid(),
         * service.getTbDepartment().getDpDeptid(), service.getSmServiceId(), null, null, null, null, null); if (workflowMas !=
         * null) { try { WorkflowProcessParameter processParameter = AccountWorkflowUtility
         * .prepareInitAccountPaymentEntryProcessParameter(rtgsPaymentEntryDTO, workflowMas, paymentEntity.getPaymentNo());
         * workflowExecutionService.initiateWorkflow(processParameter); } catch (Exception e) { LOGGER.error(
         * "Unsuccessful initiation of task for application : " + paymentEntity.getPaymentNo()); try { throw new Exception(
         * "Unsuccessful initiation of task for application : " + paymentEntity.getPaymentNo()); } catch (Exception e1) { // TODO
         * Auto-generated catch block e1.printStackTrace(); } } }
         */
        /* End Of Work Flow */

        // payment type id
        if (rtgsPaymentEntryDTO.getPaymentTypeId() != null) {
            paymentEntity.setPmdId(rtgsPaymentEntryDTO.getPaymentTypeId());
        }
        // Bill type id
        
        //comparamDetId = new TbComparamDetEntity();
        //comparamDetId.setCpdId(rtgsPaymentEntryDTO.getBillTypeId());
        //paymentEntity.setBillTypeId(comparamDetId);

        // Vendor id
        vendorId = new TbAcVendormasterEntity();
        if (rtgsPaymentEntryDTO.getVendorId() != null) {
            vendorId.setVmVendorid(rtgsPaymentEntryDTO.getVendorId());
            paymentEntity.setVmVendorId(vendorId);
        }

        // Pay mode
        comparamDetId = new TbComparamDetEntity();
        comparamDetId.setCpdId(rtgsPaymentEntryDTO.getPaymentMode());
        paymentEntity.setPaymentModeId(comparamDetId);

        // Bank acc id
        bankAccount = new BankAccountMasterEntity();
        if (rtgsPaymentEntryDTO.getBankAcId() != null) {
            bankAccount.setBaAccountId(rtgsPaymentEntryDTO.getBankAcId());
            paymentEntity.setBaBankAccountId(bankAccount);
        }
        final LookUp lookUpCash = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.CASH.getValue(),
                AccountPrefix.PAY.toString(), rtgsPaymentEntryDTO.getLanguageId().intValue(),
                rtgsPaymentEntryDTO.getOrganisation());
        final LookUp lookUpPaytCash = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.PCA.getValue(), AccountPrefix.PAY.toString(),
                rtgsPaymentEntryDTO.getLanguageId().intValue(), rtgsPaymentEntryDTO.getOrganisation());
        final LookUp lookUpAdj = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.A.getValue(),
                AccountPrefix.PAY.toString(), rtgsPaymentEntryDTO.getLanguageId().intValue(),
                rtgsPaymentEntryDTO.getOrganisation());
        final LookUp lookUpBank = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MENU.B,
                AccountPrefix.PAY.toString(), rtgsPaymentEntryDTO.getLanguageId().intValue(),
                rtgsPaymentEntryDTO.getOrganisation());
        lookUpIdCash = lookUpCash.getLookUpId();
        lookUpIdAdj = lookUpAdj.getLookUpId();
        lookUpIdBank = lookUpBank.getLookUpId();
        lookUpIdpaytCash = lookUpPaytCash.getLookUpId();
        if ((rtgsPaymentEntryDTO.getPaymentMode() != null) && !rtgsPaymentEntryDTO.getPaymentMode().equals(lookUpIdCash)
                && !rtgsPaymentEntryDTO.getPaymentMode().equals(lookUpIdAdj)
                && !rtgsPaymentEntryDTO.getPaymentMode().equals(lookUpIdBank)
                && !rtgsPaymentEntryDTO.getPaymentMode().equals(lookUpIdpaytCash)) {
            paymentEntity.setInstrumentNumber(rtgsPaymentEntryDTO.getInstrumentNo());
            paymentEntity.setInstrumentDate(
                    UtilityService.convertStringDateToDateFormat(rtgsPaymentEntryDTO.getInstrumentDate()));
        }
        if (rtgsPaymentEntryDTO.getPaymentMode().equals(lookUpIdBank)) {
            paymentEntity.setUtrNo(rtgsPaymentEntryDTO.getUtrNo());
        }
        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                rtgsPaymentEntryDTO.getLanguageId().intValue(), rtgsPaymentEntryDTO.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            paymentEntity.setAuthoDate(rtgsPaymentEntryDTO.getPaymentDate());
        } else {
            paymentEntity.setAuthoDate(rtgsPaymentEntryDTO.getCreatedDate());
        }
        paymentEntity.setAuthoFlg(AccountConstants.Y.toString());
        paymentEntity.setAuthoId(rtgsPaymentEntryDTO.getCreatedBy());
        paymentEntity.setPaymentTypeFlag(3L);

        // If selected bill type is advance
        final Organisation organisation = new Organisation();
        organisation.setOrgid(rtgsPaymentEntryDTO.getOrgId());
        final int langId = Integer.parseInt(rtgsPaymentEntryDTO.getLanguageId().toString());
        LOGGER.info("Created date "+rtgsPaymentEntryDTO.getCreatedBy());
		if (rtgsPaymentEntryDTO.getCreatedBy() == null
				&& (UserSession.getCurrent() != null && UserSession.getCurrent().getEmployee() != null)) {
			rtgsPaymentEntryDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());}
		
        saveRTGSPaymentCommonFields(paymentEntity, rtgsPaymentEntryDTO);
        LOGGER.info("paymentEntity Created date "+paymentEntity.getCreatedBy());
        saveRTGSPaymentDetails(rtgsPaymentEntryDTO, paymentEntity, langId, organisation);
        Objects.requireNonNull(rtgsPaymentEntryDTO.getTransactionDate(),
                ApplicationSession.getInstance().getMessage("payment.entry.service.transactiondate") + rtgsPaymentEntryDTO);
        paymentEntity.setPaymentDate(Utility.stringToDate(rtgsPaymentEntryDTO.getTransactionDate()));

        BigDecimal finalPaymentAmt = BigDecimal.ZERO;
        BigDecimal paymentDetTotalAmt = BigDecimal.ZERO;
        finalPaymentAmt = paymentEntity.getPaymentAmount().setScale(2, RoundingMode.CEILING);
        List<AccountPaymentDetEntity> paymentDetList = paymentEntity.getPaymentDetailList();
        for (AccountPaymentDetEntity accountPaymentDetEntity : paymentDetList) {
            if (accountPaymentDetEntity.getPaymentAmt() != null) {
                paymentDetTotalAmt = paymentDetTotalAmt
                        .add(accountPaymentDetEntity.getPaymentAmt().setScale(2, RoundingMode.CEILING));
                // paymentDetTotalAmt = paymentDetTotalAmt.setScale(2, RoundingMode.CEILING);
            }
        }
        if (!finalPaymentAmt.equals(paymentDetTotalAmt)) {
            throw new IllegalArgumentException("Discrepancy found in payment amount.");
        }

        // RTGS payment voucher posting validate to given VoucherPostDTO
        RTGSPaymentVoucherPostingValidation(rtgsPaymentEntryDTO);

        //payment no coustomization start
        //coustom sequence generation need to configure
    	String paymentNo=null;
        SequenceConfigMasterDTO configMasterDTO = null;
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.AccountConstants.AC.getValue(),
                PrefixConstants.STATUS_ACTIVE_PREFIX);
        configMasterDTO = seqGenFunctionUtility.loadSequenceData(rtgsPaymentEntryDTO.getOrgId(), deptId,
                MainetConstants.AccountContraVoucherEntry.TB_AC_PAYMENT_MAS2, MainetConstants.AccountContraVoucherEntry.PAYMENT_NO2);
        if (configMasterDTO.getSeqConfigId() == null) {
        	paymentNo = generatePaymentNumber(rtgsPaymentEntryDTO.getOrgId(), rtgsPaymentEntryDTO.getPaymentDate());
         }else {
        	 CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
        	 AccountFieldMasterBean fieldMaster = accountFieldMasterService.findById(rtgsPaymentEntryDTO.getFieldId());
     	 if(fieldMaster!=null)
          commonDto.setCustomField(fieldMaster.getFieldDesc());
        	 paymentNo=seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
          }
         //payment no coustomization end
        rtgsPaymentEntryDTO.setPaymentNo(paymentNo);
        paymentEntity.setPaymentNo(paymentNo);

        paymentEntity = paymentEntryRepository.save(paymentEntity);
        rtgsPaymentEntryDTO.setPaymentId(paymentEntity.getPaymentId());
      // final LookUp advanceLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.AD.toString(),
        //     AccountPrefix.ABT.toString(), langId, organisation);
        // Long advLookUpId = advanceLookup.getLookUpId();
        //if (rtgsPaymentEntryDTO.getBillTypeId().equals(advLookUpId)) {
            postAdvanceEntryRTGSBillSumaryWise(rtgsPaymentEntryDTO);
       // }
        postRTGSPaymentEntry(rtgsPaymentEntryDTO);
        updatePaymentStatusForBill(paymentEntity);
        if (rtgsPaymentEntryDTO.getInstrumentNo() != null) {
            updateChequeNoStatus(rtgsPaymentEntryDTO.getInstrumentNo(), rtgsPaymentEntryDTO.getOrgId(),
                    rtgsPaymentEntryDTO.getLanguageId().intValue(), rtgsPaymentEntryDTO.getOrganisation(),
                    paymentEntity.getPaymentId(), paymentEntity.getPaymentDate(), AccountConstants.P.toString());
        }
        return rtgsPaymentEntryDTO;
    }

    // Save common entity fields
    private void saveRTGSPaymentCommonFields(final AccountPaymentMasterEntity paymentEntity,
            final RTGSPaymentEntryDTO rtgsPaymentEntryDTO) {
        paymentEntity.setPaymentAmount(rtgsPaymentEntryDTO.getTotalPaymentAmount());
        paymentEntity.setOrgId(rtgsPaymentEntryDTO.getOrgId());
        paymentEntity.setNarration(rtgsPaymentEntryDTO.getNarration());
        paymentEntity.setCreatedBy(rtgsPaymentEntryDTO.getCreatedBy());
        paymentEntity.setCreatedDate(rtgsPaymentEntryDTO.getCreatedDate());
        // paymentEntity.setLangId(paymentEntryDto.getLanguageId());
        paymentEntity.setLgIpMac(rtgsPaymentEntryDTO.getLgIpMacAddress());
    }

    private void saveRTGSPaymentDetails(final RTGSPaymentEntryDTO rtgsPaymentEntryDTO,
            final AccountPaymentMasterEntity paymentEntity, int langId, Organisation organisation) {

        // int balCount = 0;
        AccountPaymentDetEntity paymentDetailsEntity = null;
        final List<AccountPaymentDetEntity> paymentDetailsList = new ArrayList<>();
        final List<RTGSPaymentDetailsDTO> rtgsPaymentDetailsList = rtgsPaymentEntryDTO.getRtgsPaymentDetailsDto();
        //String billType = CommonMasterUtility.findLookUpCode(AccountPrefix.ABT.toString(), organisation.getOrgid(),
          //      rtgsPaymentEntryDTO.getBillTypeId());

        if ((rtgsPaymentDetailsList != null) && !rtgsPaymentDetailsList.isEmpty()) {
            for (final RTGSPaymentDetailsDTO rtgsPaymentDetailsDTO : rtgsPaymentDetailsList) {

                AccountBillEntryMasterEnitity billEnryEntity = billEntryRepository
                        .findBillEntryById(rtgsPaymentEntryDTO.getOrgId(), rtgsPaymentDetailsDTO.getId());

                int balCount = 0;
                BigDecimal totalNetPayableAmount = new BigDecimal(0.00);
                BigDecimal totalPayableAmount = new BigDecimal(0.00);
                BigDecimal sumOfBillBalanceLatestAmount = BigDecimal.ZERO;
                BigDecimal paymentLatestAmount = rtgsPaymentDetailsDTO.getPaymentAmount();
                BigDecimal latestRatio = BigDecimal.ZERO;
                BigDecimal latestNewRatio = BigDecimal.ZERO;
                BigDecimal ratio = BigDecimal.ZERO;
                BigDecimal sumOfBillBalanceAmount = BigDecimal.ZERO;
                BigDecimal sumOfBillDeductionAmount = BigDecimal.ZERO;
                List<AccountBillEntryExpenditureDetEntity> expLatestList = billEnryEntity.getExpenditureDetailList();
                for (AccountBillEntryExpenditureDetEntity expBillEntryDetails : expLatestList) {
                    if (expBillEntryDetails.getFi04V1() != null
                            && !expBillEntryDetails.getFi04V1().isEmpty() && !expBillEntryDetails.getFi04V1().equals("0.00")) {
                        sumOfBillBalanceLatestAmount = sumOfBillBalanceLatestAmount
                                .add(new BigDecimal(expBillEntryDetails.getFi04V1()));
                        balCount++;
                    } else {
                        sumOfBillBalanceAmount = sumOfBillBalanceAmount.add(expBillEntryDetails.getBillChargesAmount());
                    }
                }
                List<AccountBillEntryDeductionDetEntity> dedList = billEnryEntity.getDeductionDetailList();
                if (dedList != null && !dedList.isEmpty()) {
                    for (AccountBillEntryDeductionDetEntity dedBillEntryDetails : dedList) {
                        sumOfBillDeductionAmount = sumOfBillDeductionAmount
                                .add(dedBillEntryDetails.getDeductionAmount());
                    }
                }
                if (balCount != 0) {
                    BigDecimal sumOfBillProRataBalLatestAmount = sumOfBillBalanceLatestAmount.setScale(2,
                            BigDecimal.ROUND_HALF_EVEN);
                    BigDecimal paymentLatestAmt = paymentLatestAmount.setScale(2,
                            BigDecimal.ROUND_HALF_EVEN);
                    latestRatio = paymentLatestAmt.divide(sumOfBillProRataBalLatestAmount,
                            MathContext.DECIMAL128);
                } else {
                    BigDecimal sumOfBillProRataBalanceAmount = sumOfBillBalanceAmount.setScale(2,
                            BigDecimal.ROUND_HALF_EVEN);
                    BigDecimal sumOfBillProRataDeductionAmount = sumOfBillDeductionAmount.setScale(2,
                            BigDecimal.ROUND_HALF_EVEN);
                    // netPayableAmount = sumOfBillProRataBalanceAmount.subtract(sumOfBillProRataDeductionAmount);
                    ratio = sumOfBillProRataDeductionAmount.divide(sumOfBillProRataBalanceAmount,
                            MathContext.DECIMAL128);
                }
                List<AccountBillEntryExpenditureDetBean> expenditureDetailList = new ArrayList<>();
                List<AccountBillEntryExpenditureDetEntity> latestExpList = billEnryEntity.getExpenditureDetailList();
                for (AccountBillEntryExpenditureDetEntity accountBillEntryExpenditureDetEntity : latestExpList) {
                    AccountBillEntryExpenditureDetBean expDetailsDTO = new AccountBillEntryExpenditureDetBean();
                    BigDecimal deductionAmount = BigDecimal.ZERO;
                    deductionAmount = accountBillEntryExpenditureDetEntity.getBillChargesAmount().multiply(ratio)
                            .setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    BigDecimal netPayableAmt = BigDecimal.ZERO;
                    netPayableAmt = accountBillEntryExpenditureDetEntity.getBillChargesAmount().subtract(deductionAmount);
                    totalNetPayableAmount = totalNetPayableAmount.add(netPayableAmt);
                    expDetailsDTO.setId(accountBillEntryExpenditureDetEntity.getId());
                    expDetailsDTO.setSacHeadId(accountBillEntryExpenditureDetEntity.getSacHeadId());
                    expDetailsDTO.setBillChargesAmount(accountBillEntryExpenditureDetEntity.getBillChargesAmount());
                    expDetailsDTO.setNewBalanceAmount(netPayableAmt);
                    expenditureDetailList.add(expDetailsDTO);
                }
                // List<AccountBillEntryExpenditureDetBean> paymentDetailList = new ArrayList<>();
                if (balCount != 0) {
                    // List<AccountBillEntryExpenditureDetBean> expDetailList = new ArrayList<>();
                    for (AccountBillEntryExpenditureDetEntity accountBillEntryExpenditureDetEntity : latestExpList) {
                        BigDecimal paymentAmount = BigDecimal.ZERO;
                        paymentAmount = new BigDecimal(accountBillEntryExpenditureDetEntity.getFi04V1()).multiply(latestRatio);
                        paymentDetailsEntity = new AccountPaymentDetEntity();
                        paymentDetailsEntity.setBchIdExpenditure(accountBillEntryExpenditureDetEntity.getId());
                        paymentDetailsEntity.setBudgetCodeId(accountBillEntryExpenditureDetEntity.getSacHeadId());
                        paymentDetailsEntity.setPaymentAmt(paymentAmount.setScale(2,
                                BigDecimal.ROUND_HALF_EVEN));
                        totalPayableAmount = totalPayableAmount.add(paymentAmount.setScale(2,
                                BigDecimal.ROUND_HALF_EVEN));

                        BigDecimal newBalAmt = new BigDecimal(accountBillEntryExpenditureDetEntity.getFi04V1())
                                .subtract(paymentAmount);
                        BigDecimal latestNewBalAmt = newBalAmt.setScale(2,
                                BigDecimal.ROUND_HALF_EVEN);
                        billEntryRepository.updateExpenditureBalanceAmt(paymentDetailsEntity.getBchIdExpenditure(),
                                latestNewBalAmt.toString());

                        // expenditure
                        paymentDetailsEntity.setBillId(rtgsPaymentDetailsDTO.getId());// Column name To be renamed
                        paymentDetailsEntity.setOrgId(paymentEntity.getOrgId());
                        paymentDetailsEntity.setCreatedBy(paymentEntity.getCreatedBy());
                        paymentDetailsEntity.setCreatedDate(paymentEntity.getCreatedDate());
                        paymentDetailsEntity.setLangId(Long.valueOf(langId));
                        paymentDetailsEntity.setLgIpMac(paymentEntity.getLgIpMac());
                        paymentDetailsEntity.setFi04V2(rtgsPaymentDetailsDTO.getVendorId().toString());
                        paymentDetailsEntity.setPaymentMasterId(paymentEntity);
                        // Deposit payable amount updation
                        final LookUp depLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                                MainetConstants.AccountBillEntry.DE, AccountPrefix.ABT.toString(), langId,
                                organisation);
                        Long depLookUpId = depLookup.getLookUpId();
                        if (depLookUpId != null) {
                            if (depLookUpId.equals(rtgsPaymentEntryDTO.getBillTypeId())) {
                                BigDecimal oldDepDefundAmt = accountDepositJparepository
                                        .getDepDefundAmountDetails(rtgsPaymentDetailsDTO.getId(), rtgsPaymentEntryDTO.getOrgId());
                                if (oldDepDefundAmt != null) {
                                    BigDecimal newDepDefundAmt = oldDepDefundAmt
                                            .subtract(rtgsPaymentDetailsDTO.getPaymentAmount());
                                    accountDepositJparepository.updateDepDelRefundAmount(rtgsPaymentDetailsDTO.getId(),
                                            rtgsPaymentEntryDTO.getOrgId(), newDepDefundAmt);
                                    if (newDepDefundAmt.compareTo(BigDecimal.ZERO) == 0
                                            || (newDepDefundAmt.compareTo(new BigDecimal(0.00)) == 0)) {
                                        final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                                                PrefixConstants.AccountBillEntry.DR, PrefixConstants.NewWaterServiceConstants.RDC,
                                                rtgsPaymentEntryDTO.getOrgId());
                                        accountDepositJparepository.updateDepositBalanceAmountInDepositTable(
                                                rtgsPaymentDetailsDTO.getId(), statusId,
                                                rtgsPaymentEntryDTO.getOrgId());
                                    }
                                }
                            }
                        }
                        paymentDetailsList.add(paymentDetailsEntity);
                    }
                } else {
                    BigDecimal totalLatestNetPayableAmount = totalNetPayableAmount.setScale(2,
                            BigDecimal.ROUND_HALF_EVEN);
                    latestNewRatio = paymentLatestAmount.divide(totalLatestNetPayableAmount,
                            MathContext.DECIMAL128);
                    // List<AccountBillEntryExpenditureDetBean> expDetailList = new ArrayList<>();
                    for (AccountBillEntryExpenditureDetBean accountBillEntryExpenditureDetBean : expenditureDetailList) {
                        BigDecimal paymentAmount = BigDecimal.ZERO;
                        paymentAmount = accountBillEntryExpenditureDetBean.getNewBalanceAmount().multiply(latestNewRatio);
                        paymentDetailsEntity = new AccountPaymentDetEntity();
                        paymentDetailsEntity.setBchIdExpenditure(accountBillEntryExpenditureDetBean.getId());
                        paymentDetailsEntity.setBudgetCodeId(accountBillEntryExpenditureDetBean.getSacHeadId());
                        paymentDetailsEntity.setPaymentAmt(paymentAmount.setScale(2,
                                BigDecimal.ROUND_HALF_EVEN));
                        totalPayableAmount = totalPayableAmount.add(paymentAmount.setScale(2,
                                BigDecimal.ROUND_HALF_EVEN));

                        BigDecimal newBalAmt = accountBillEntryExpenditureDetBean.getNewBalanceAmount().subtract(paymentAmount);
                        BigDecimal latestNewBalAmt = newBalAmt.setScale(2,
                                BigDecimal.ROUND_HALF_EVEN);
                        billEntryRepository.updateExpenditureBalanceAmt(paymentDetailsEntity.getBchIdExpenditure(),
                                latestNewBalAmt.toString());

                        // expenditure
                        paymentDetailsEntity.setBillId(rtgsPaymentDetailsDTO.getId());// Column name To be renamed
                        paymentDetailsEntity.setOrgId(paymentEntity.getOrgId());
                        paymentDetailsEntity.setCreatedBy(paymentEntity.getCreatedBy());
                        paymentDetailsEntity.setCreatedDate(paymentEntity.getCreatedDate());
                        paymentDetailsEntity.setLangId(Long.valueOf(langId));
                        paymentDetailsEntity.setLgIpMac(paymentEntity.getLgIpMac());
                        paymentDetailsEntity.setFi04V2(rtgsPaymentDetailsDTO.getVendorId().toString());
                        paymentDetailsEntity.setPaymentMasterId(paymentEntity);
                        // Deposit payable amount updation
                        final LookUp depLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                                MainetConstants.AccountBillEntry.DE, AccountPrefix.ABT.toString(), langId,
                                organisation);
                        Long depLookUpId = depLookup.getLookUpId();
                        if (depLookUpId != null) {
                            if (depLookUpId.equals(rtgsPaymentEntryDTO.getBillTypeId())) {
                                BigDecimal oldDepDefundAmt = accountDepositJparepository
                                        .getDepDefundAmountDetails(rtgsPaymentDetailsDTO.getId(), rtgsPaymentEntryDTO.getOrgId());
                                if (oldDepDefundAmt != null) {
                                    BigDecimal newDepDefundAmt = oldDepDefundAmt
                                            .subtract(rtgsPaymentDetailsDTO.getPaymentAmount());
                                    accountDepositJparepository.updateDepDelRefundAmount(rtgsPaymentDetailsDTO.getId(),
                                            rtgsPaymentEntryDTO.getOrgId(), newDepDefundAmt);
                                    if (newDepDefundAmt.compareTo(BigDecimal.ZERO) == 0
                                            || (newDepDefundAmt.compareTo(new BigDecimal(0.00)) == 0)) {
                                        final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                                                PrefixConstants.AccountBillEntry.DR, PrefixConstants.NewWaterServiceConstants.RDC,
                                                rtgsPaymentEntryDTO.getOrgId());
                                        accountDepositJparepository.updateDepositBalanceAmountInDepositTable(
                                                rtgsPaymentDetailsDTO.getId(), statusId,
                                                rtgsPaymentEntryDTO.getOrgId());
                                    }
                                }
                            }
                        }
                        paymentDetailsList.add(paymentDetailsEntity);
                    }
                }
            }
            paymentEntity.setPaymentDetailList(paymentDetailsList);
        } else {
            throw new NullPointerException("Bill Details cannot be empty.");
        }
    }

    private void postAdvanceEntryRTGSBillSumaryWise(final RTGSPaymentEntryDTO rtgsPaymentEntryDTO) throws ParseException {
    	  final Organisation orgID = new Organisation();
    	  orgID.setOrgid(rtgsPaymentEntryDTO.getOrgId());
    	  final LookUp advanceLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.AD.toString(),
                 AccountPrefix.ABT.toString(), Integer.parseInt(rtgsPaymentEntryDTO.getLanguageId().toString()), orgID);
    	 
        final List<RTGSPaymentDetailsDTO> rtgsPaymentDetailsList = rtgsPaymentEntryDTO.getRtgsPaymentDetailsDto();
        for (final RTGSPaymentDetailsDTO rtgsPaymentDetails : rtgsPaymentDetailsList) {
            if(rtgsPaymentDetails.getBillTypeId().equals(advanceLookup.getLookUpId())) {
            final List<Object[]> expenditures = getExpenditureDetails(rtgsPaymentDetails.getId(), rtgsPaymentEntryDTO.getOrgId());
            if ((expenditures != null) && !expenditures.isEmpty()) {
                for (final Object[] exp : expenditures) {

                    final AdvanceEntryDTO advanceEntryDTO = new AdvanceEntryDTO();
                    final AdvanceEntryEntity advanceEntryEntity = new AdvanceEntryEntity();

                    advanceEntryDTO.setOrgid(rtgsPaymentEntryDTO.getOrgId());
                    final int langId = Integer.parseInt(rtgsPaymentEntryDTO.getLanguageId().toString());
                    advanceEntryDTO.setLangId(langId);
                    LOGGER.info("Created By : "+rtgsPaymentEntryDTO.getCreatedBy());
                    advanceEntryDTO.setCreatedBy(rtgsPaymentEntryDTO.getCreatedBy());
                    advanceEntryDTO.setCreatedDate(rtgsPaymentEntryDTO.getCreatedDate());
                  //  advanceEntryDTO.setCreatedBy(rtgsPaymentEntryDTO.getUpdatedBy());
                    advanceEntryDTO.setUpdatedDate(new Date());
                    advanceEntryDTO.setLgIpMac(rtgsPaymentEntryDTO.getLgIpMacAddress());
                    advanceEntryDTO.setLgIpMacUpd(rtgsPaymentEntryDTO.getLgIpMacAddress());

                    advanceEntryDTO.setPrAdvEntryDate(new Date());
                    Long finYearId = tbFinancialyearService
                            .getFinanciaYearIdByFromDate(rtgsPaymentEntryDTO.getPaymentDate());
                    if (finYearId == null) {
                        throw new NullPointerException(GENERATE_ADVANCE_NO_FIN_YEAR_ID);
                    }
                    final Long advNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                            TB_AC_ADVANCE, PAY_ADVANCE_NO, rtgsPaymentEntryDTO.getOrgId(),
                            MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, finYearId);
                    advanceEntryDTO.setPrAdvEntryNo(advNumber);
                    advanceEntryDTO.setVendorId(rtgsPaymentDetails.getVendorId());

                    final Long accountHeadId = (Long) exp[2];
                    advanceEntryDTO.setPacHeadId(accountHeadId);
                    final Long superOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
                    final Long advLookUpTypeId = advanceEntryService.getAdvAccountTypeId(
                            rtgsPaymentEntryDTO.getBillTypeId(), accountHeadId, superOrgId, rtgsPaymentEntryDTO.getOrgId());
                    advanceEntryDTO.setAdvanceTypeId(advLookUpTypeId);

                    advanceEntryDTO.setPaymentNumber(rtgsPaymentEntryDTO.getPaymentNo());
                    advanceEntryDTO.setPaymentDate(rtgsPaymentEntryDTO.getPaymentDate());
                    advanceEntryDTO.setAdvanceAmount(rtgsPaymentEntryDTO.getTotalPaymentAmount().toString());
                    advanceEntryDTO.setBalanceAmount(rtgsPaymentEntryDTO.getTotalPaymentAmount().toString());
                    advanceEntryDTO.setPayAdvParticulars(rtgsPaymentEntryDTO.getNarration());

                    final Organisation organisation = new Organisation();
                    organisation.setOrgid(rtgsPaymentEntryDTO.getOrgId());
                    final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                            MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), langId, organisation);
                    advanceEntryDTO.setCpdIdStatus(statusLookup.getLookUpCode());

                    advanceEntryDTO.setAdv_Flg(AccountConstants.S.toString());
                    advanceEntryServiceMapper.mapAdvanceEntryDTOToAdvanceEntryEntity(advanceEntryDTO,
                            advanceEntryEntity);
                    advanceEntryRepository.save(advanceEntryEntity);
                }
              }
            }
        }
    }

    private void RTGSPaymentVoucherPostingValidation(RTGSPaymentEntryDTO rtgsPaymentEntryDTO) {
        LOGGER.info("Process for account payment voucher posting validation:" + rtgsPaymentEntryDTO);

        final VoucherPostDTO postDto = new VoucherPostDTO();
        List<VoucherPostDTO> postDtoList = new ArrayList<>();
        final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
        final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                AccountPrefix.TDP.toString(), rtgsPaymentEntryDTO.getOrgId());
        postDto.setVoucherSubTypeId(voucherSubTypeId);

        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                rtgsPaymentEntryDTO.getLanguageId().intValue(), rtgsPaymentEntryDTO.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            // postDto.setVoucherDate(Utility.stringToDate(rtgsPaymentEntryDTO.getTransactionDate()));
            postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
        } else {
            // postDto.setVoucherDate(Utility.stringToDate(rtgsPaymentEntryDTO.getTransactionDate()));
        }
        postDto.setVoucherDate(Utility.stringToDate(rtgsPaymentEntryDTO.getTransactionDate()));
        final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
        postDto.setDepartmentId(departmentId);
        // postDto.setVoucherReferenceNo(rtgsPaymentEntryDTO.getPaymentNo());

        postDto.setNarration(rtgsPaymentEntryDTO.getNarration());
        postDto.setPayerOrPayee(rtgsPaymentEntryDTO.getVendorDesc());// Vendor name

        postDto.setFieldId(rtgsPaymentEntryDTO.getFieldId());
        postDto.setOrgId(rtgsPaymentEntryDTO.getOrgId());
        postDto.setCreatedBy(rtgsPaymentEntryDTO.getCreatedBy());
        postDto.setCreatedDate(rtgsPaymentEntryDTO.getCreatedDate());
        postDto.setLgIpMac(rtgsPaymentEntryDTO.getLgIpMacAddress());
        postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

        Long lookUpIdCash = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.C.toString(),
                AccountPrefix.PAY.toString(), rtgsPaymentEntryDTO.getOrgId());
        Long lookUpIdAdj = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.A.toString(),
                AccountPrefix.PAY.toString(), rtgsPaymentEntryDTO.getOrgId());
        Long lookUpIdPetty = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                AccountPrefix.PAY.toString(), rtgsPaymentEntryDTO.getOrgId());
        if (rtgsPaymentEntryDTO.getPaymentMode().equals(lookUpIdCash)
                || rtgsPaymentEntryDTO.getPaymentMode().equals(lookUpIdAdj)
                || rtgsPaymentEntryDTO.getPaymentMode().equals(lookUpIdPetty)) {
            // Cash Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(rtgsPaymentEntryDTO.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, rtgsPaymentEntryDTO.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long templateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                    AccountPrefix.TDP.toString(), rtgsPaymentEntryDTO.getOrgId());
            final Long activeStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.IsLookUp.ACTIVE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, rtgsPaymentEntryDTO.getOrgId());
            VoucherTemplateMasterEntity entity = voucherTemplateRepository
                    .findAcHeadCodeInTemplateForCashMode(templateFor, rtgsPaymentEntryDTO.getOrgId(), activeStatus);
            Long sacHeadIdCr = null;
            List<VoucherTemplateDetailEntity> detList = entity.getTemplateDetailEntities();
            for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : detList) {
                if (voucherTemplateDetailEntity.getCpdIdPayMode().equals(rtgsPaymentEntryDTO.getPaymentMode())) {
                    if (voucherTemplateDetailEntity.getSacHeadId() != null) {
                        sacHeadIdCr = voucherTemplateDetailEntity.getSacHeadId();
                        break;
                    }
                }
            }
            if (sacHeadIdCr == null) {
                throw new NullPointerException(PAYMODE + " templateFor is : " + templateFor + " pay mode is : "
                        + rtgsPaymentEntryDTO.getPaymentMode() + " orgid is : " + rtgsPaymentEntryDTO.getOrgId()
                        + " status is active : " + activeStatus);
            }
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(rtgsPaymentEntryDTO.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        } else {
            // Bank Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            if (rtgsPaymentEntryDTO.getTotalPaymentAmount() != null) {
                postDetailDtoCr.setVoucherAmount(rtgsPaymentEntryDTO.getTotalPaymentAmount());
            }
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, rtgsPaymentEntryDTO.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long sacHeadIdCr = getSacHeadIdByBankAccountId(rtgsPaymentEntryDTO.getBankAcId(),
                    rtgsPaymentEntryDTO.getOrgId());
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(rtgsPaymentEntryDTO.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        }

        // Vendor Debit side
        /*
         * final VoucherPostDetailDTO postDetailDtoDr = new VoucherPostDetailDTO(); final Long drId =
         * CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(), PrefixConstants.DCR,
         * rtgsPaymentEntryDTO.getOrgId()); postDetailDtoDr.setDrCrId(drId);
         * postDetailDtoDr.setVoucherAmount(rtgsPaymentEntryDTO.getTotalPaymentAmount()); Long status =
         * CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix( MainetConstants.CommonConstants.ACTIVE,
         * AccountPrefix.ACN.toString(),rtgsPaymentEntryDTO.getOrgId()); final Long vendorSacHeadIdDr =
         * billEntryRepository.getVendorSacHeadIdByVendorId(rtgsPaymentEntryDTO.getVendorId(), rtgsPaymentEntryDTO.getOrgId(),
         * status); postDetailDtoDr.setSacHeadId(vendorSacHeadIdDr); voucherDetails.add(postDetailDtoDr);
         */

        Map<Long, BigDecimal> vendorHeadIdMap = new HashMap<Long, BigDecimal>();
        final List<RTGSPaymentDetailsDTO> rtgsPaymentDetailsDTOList = rtgsPaymentEntryDTO.getRtgsPaymentDetailsDto();
        if ((rtgsPaymentDetailsDTOList != null) && !rtgsPaymentDetailsDTOList.isEmpty()) {
            for (final RTGSPaymentDetailsDTO rtgsPaymentDetailsDTO : rtgsPaymentEntryDTO.getRtgsPaymentDetailsDto()) {
                BigDecimal mapVendorPaymentAmount = vendorHeadIdMap.get(rtgsPaymentDetailsDTO.getVendorId());
                if (mapVendorPaymentAmount == null) {
                    vendorHeadIdMap.put(rtgsPaymentDetailsDTO.getVendorId(), rtgsPaymentDetailsDTO.getPaymentAmount());
                } else {
                    mapVendorPaymentAmount = mapVendorPaymentAmount.add(rtgsPaymentDetailsDTO.getPaymentAmount());
                    vendorHeadIdMap.put(rtgsPaymentDetailsDTO.getVendorId(), mapVendorPaymentAmount);
                }
            }
        }
        for (Entry<Long, BigDecimal> rtgsPaymentDetailsDTO : vendorHeadIdMap.entrySet()) {
            final VoucherPostDetailDTO dtoListFee = new VoucherPostDetailDTO();
            final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                    PrefixConstants.DCR, rtgsPaymentEntryDTO.getOrgId());
            dtoListFee.setDrCrId(drId);
            dtoListFee.setVoucherAmount(rtgsPaymentDetailsDTO.getValue());
            Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), rtgsPaymentEntryDTO.getOrgId());
            final Long vendorSacHeadIdDr = billEntryRepository.getVendorSacHeadIdByVendorId(rtgsPaymentDetailsDTO.getKey(),
                    rtgsPaymentEntryDTO.getOrgId(), status);
            dtoListFee.setSacHeadId(vendorSacHeadIdDr);
            // dtoListFee.setVoucherAmount(tbServiceReceiptMasEntityList.getRfFeeamount());
            voucherDetails.add(dtoListFee);
        }
        postDto.setVoucherDetails(voucherDetails);
        postDtoList.add(postDto);
        List<String> responseValidation = accountVoucherPostService.checkVoucherPostValidateInput(postDtoList);
        if (responseValidation.size() > 0) {
            throw new NullPointerException(
                    "improper input parameter for VoucherPostDTO in payment entry -> " + responseValidation);
        }
    }

    // Posting the RTGS payment entry
    private void postRTGSPaymentEntry(final RTGSPaymentEntryDTO rtgsPaymentEntryDTO) {

        final VoucherPostDTO postDto = new VoucherPostDTO();
        List<VoucherPostDTO> postDtoList = new ArrayList<>();
        final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
        final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                AccountPrefix.TDP.toString(), rtgsPaymentEntryDTO.getOrgId());
        postDto.setVoucherSubTypeId(voucherSubTypeId);

        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.AUT.toString(),
                rtgsPaymentEntryDTO.getLanguageId().intValue(), rtgsPaymentEntryDTO.getOrganisation());
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            // postDto.setVoucherDate(Utility.stringToDate(rtgsPaymentEntryDTO.getTransactionDate()));
            postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
        } else {
            // postDto.setVoucherDate(Utility.stringToDate(rtgsPaymentEntryDTO.getTransactionDate()));
        }
        postDto.setVoucherDate(Utility.stringToDate(rtgsPaymentEntryDTO.getTransactionDate()));
        final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
        postDto.setDepartmentId(departmentId);
        postDto.setVoucherReferenceNo(rtgsPaymentEntryDTO.getPaymentNo());

        postDto.setNarration(rtgsPaymentEntryDTO.getNarration());
        postDto.setPayerOrPayee(rtgsPaymentEntryDTO.getVendorDesc());// Vendor name

        postDto.setFieldId(rtgsPaymentEntryDTO.getFieldId());
        postDto.setOrgId(rtgsPaymentEntryDTO.getOrgId());
        postDto.setCreatedBy(rtgsPaymentEntryDTO.getCreatedBy());
        postDto.setCreatedDate(rtgsPaymentEntryDTO.getCreatedDate());
        postDto.setLgIpMac(rtgsPaymentEntryDTO.getLgIpMacAddress());
        postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

        Long lookUpIdCash = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.C.toString(),
                AccountPrefix.PAY.toString(), rtgsPaymentEntryDTO.getOrgId());
        Long lookUpIdAdj = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.A.toString(),
                AccountPrefix.PAY.toString(), rtgsPaymentEntryDTO.getOrgId());
        Long lookUpIdPetty = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                AccountPrefix.PAY.toString(), rtgsPaymentEntryDTO.getOrgId());
        if (rtgsPaymentEntryDTO.getPaymentMode().equals(lookUpIdCash)
                || rtgsPaymentEntryDTO.getPaymentMode().equals(lookUpIdAdj)
                || rtgsPaymentEntryDTO.getPaymentMode().equals(lookUpIdPetty)) {
            // Cash Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(rtgsPaymentEntryDTO.getTotalPaymentAmount());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, rtgsPaymentEntryDTO.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long templateFor = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PVE.toString(),
                    AccountPrefix.TDP.toString(), rtgsPaymentEntryDTO.getOrgId());
            final Long activeStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.IsLookUp.ACTIVE,
                    PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, rtgsPaymentEntryDTO.getOrgId());
            VoucherTemplateMasterEntity entity = voucherTemplateRepository
                    .findAcHeadCodeInTemplateForCashMode(templateFor, rtgsPaymentEntryDTO.getOrgId(), activeStatus);
            Long sacHeadIdCr = null;
            List<VoucherTemplateDetailEntity> detList = entity.getTemplateDetailEntities();
            for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : detList) {
                if (voucherTemplateDetailEntity.getCpdIdPayMode().equals(rtgsPaymentEntryDTO.getPaymentMode())) {
                    if (voucherTemplateDetailEntity.getSacHeadId() != null) {
                        sacHeadIdCr = voucherTemplateDetailEntity.getSacHeadId();
                        break;
                    }
                }
            }
            if (sacHeadIdCr == null) {
                throw new NullPointerException(PAYMODE + " templateFor is : " + templateFor + " pay mode is : "
                        + rtgsPaymentEntryDTO.getPaymentMode() + " orgid is : " + rtgsPaymentEntryDTO.getOrgId()
                        + " status is active : " + activeStatus);
            }
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(rtgsPaymentEntryDTO.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        } else {
            // Bank Credit side
            final VoucherPostDetailDTO postDetailDtoCr = new VoucherPostDetailDTO();
            if (rtgsPaymentEntryDTO.getTotalPaymentAmount() != null) {
                postDetailDtoCr.setVoucherAmount(rtgsPaymentEntryDTO.getTotalPaymentAmount());
            }
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, rtgsPaymentEntryDTO.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long sacHeadIdCr = getSacHeadIdByBankAccountId(rtgsPaymentEntryDTO.getBankAcId(),
                    rtgsPaymentEntryDTO.getOrgId());
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(rtgsPaymentEntryDTO.getPaymentMode());
            voucherDetails.add(postDetailDtoCr);
        }

        // Vendor Debit side
        /*
         * final VoucherPostDetailDTO postDetailDtoDr = new VoucherPostDetailDTO(); final Long drId =
         * CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(), PrefixConstants.DCR,
         * rtgsPaymentEntryDTO.getOrgId()); postDetailDtoDr.setDrCrId(drId);
         * postDetailDtoDr.setVoucherAmount(rtgsPaymentEntryDTO.getTotalPaymentAmount()); Long status =
         * CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix( MainetConstants.CommonConstants.ACTIVE,
         * AccountPrefix.ACN.toString(),rtgsPaymentEntryDTO.getOrgId()); final Long vendorSacHeadIdDr =
         * billEntryRepository.getVendorSacHeadIdByVendorId(rtgsPaymentEntryDTO.getVendorId(), rtgsPaymentEntryDTO.getOrgId(),
         * status); postDetailDtoDr.setSacHeadId(vendorSacHeadIdDr); voucherDetails.add(postDetailDtoDr);
         */

        Map<Long, BigDecimal> vendorHeadIdMap = new HashMap<Long, BigDecimal>();
        final List<RTGSPaymentDetailsDTO> rtgsPaymentDetailsDTOList = rtgsPaymentEntryDTO.getRtgsPaymentDetailsDto();
        if ((rtgsPaymentDetailsDTOList != null) && !rtgsPaymentDetailsDTOList.isEmpty()) {
            for (final RTGSPaymentDetailsDTO rtgsPaymentDetailsDTO : rtgsPaymentEntryDTO.getRtgsPaymentDetailsDto()) {
                BigDecimal mapVendorPaymentAmount = vendorHeadIdMap.get(rtgsPaymentDetailsDTO.getVendorId());
                if (mapVendorPaymentAmount == null) {
                    vendorHeadIdMap.put(rtgsPaymentDetailsDTO.getVendorId(), rtgsPaymentDetailsDTO.getPaymentAmount());
                } else {
                    mapVendorPaymentAmount = mapVendorPaymentAmount.add(rtgsPaymentDetailsDTO.getPaymentAmount());
                    vendorHeadIdMap.put(rtgsPaymentDetailsDTO.getVendorId(), mapVendorPaymentAmount);
                }
            }
        }
        /*
         * Map<Long,String> vendorHeadIdMap = new LinkedHashMap<Long,String>(); for (final RTGSPaymentDetailsDTO
         * rtgsPaymentDetailsDTO : rtgsPaymentEntryDTO.getRtgsPaymentDetailsDto()) {
         * vendorHeadIdMap.put(rtgsPaymentDetailsDTO.getVendorId(), rtgsPaymentDetailsDTO.getPaymentAmount().toString()); }
         */
        for (Entry<Long, BigDecimal> rtgsPaymentDetailsDTO : vendorHeadIdMap.entrySet()) {
            final VoucherPostDetailDTO dtoListFee = new VoucherPostDetailDTO();
            final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                    PrefixConstants.DCR, rtgsPaymentEntryDTO.getOrgId());
            dtoListFee.setDrCrId(drId);
            dtoListFee.setVoucherAmount(rtgsPaymentDetailsDTO.getValue());
            Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), rtgsPaymentEntryDTO.getOrgId());
            final Long vendorSacHeadIdDr = billEntryRepository.getVendorSacHeadIdByVendorId(rtgsPaymentDetailsDTO.getKey(),
                    rtgsPaymentEntryDTO.getOrgId(), status);
            dtoListFee.setSacHeadId(vendorSacHeadIdDr);
            // dtoListFee.setVoucherAmount(tbServiceReceiptMasEntityList.getRfFeeamount());
            voucherDetails.add(dtoListFee);
        }
        postDto.setVoucherDetails(voucherDetails);
        postDtoList.add(postDto);
        ApplicationSession session = ApplicationSession.getInstance();
        String responseValidation = accountVoucherPostService.validateInput(postDtoList);
        AccountVoucherEntryEntity response = null;
        if (responseValidation.isEmpty()) {
            response = accountVoucherPostService.voucherPosting(postDtoList);
            if (response == null) {
                throw new IllegalArgumentException("Voucher Posting failed");
            }
        } else {
            LOGGER.error(session.getMessage("account.voucher.service.posting")
                    + session.getMessage("account.voucher.posting.improper.input") + responseValidation);
            throw new IllegalArgumentException(
                    "Voucher Posting failed : proper data is not exist" + responseValidation);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> searchRecordsForRTGSPaymentEntry(final RTGSPaymentEntryDTO dto, final Long orgId) {
        ResponseEntity<?> response = null;
        final List<Object[]> records = paymentEntryRepository.queryRecordsForRTGSPaymentEntry(dto, orgId);
        if ((records != null) && !records.isEmpty()) {
            final List<RTGSPaymentEntryDTO> recordList = records.stream().map(objects -> {
                final RTGSPaymentEntryDTO entryDto = new RTGSPaymentEntryDTO();
                mapQueryRecordsToRTGSPaymentEntryDto(objects, entryDto);
                return entryDto;
            }).collect(Collectors.toList());
            response = ResponseEntity.status(HttpStatus.FOUND).body(recordList);
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(MainetConstants.MENU.N);
        }
        return response;
    }

    private void mapQueryRecordsToRTGSPaymentEntryDto(Object[] objects, RTGSPaymentEntryDTO entryDto) {
        entryDto.setPaymentId((Long) objects[0]);
        if (objects[1] != null) {
            entryDto.setPaymentNo((String) objects[1]);
        }
        if (objects[2] != null) {
            entryDto.setTransactionDate(Utility.dateToString((Date) objects[2]));
        }
        if (objects[3] != null && objects[4] != null) {
            entryDto.setBankacname((String) objects[3] + " - " + (String) objects[4]);
        }
        if (objects[5] != null) {
            entryDto.setPaymentAmount(((BigDecimal) objects[5]).setScale(2));
        }
        entryDto.setViewURL("RTGSPaymentEntry.html?view");

    }

    @Override
    @Transactional(readOnly = true)
    public RTGSPaymentEntryDTO getRTGSRecordForView(final Long paymentId, final Long orgId, int langId) {

        final RTGSPaymentEntryDTO rtgsPaymentEntryDto = new RTGSPaymentEntryDTO();

        final AccountPaymentMasterEntity paymentDetails = paymentEntryRepository.findPaymentEntryDataById(paymentId,
                orgId);
        if (paymentDetails != null) {
            rtgsPaymentEntryDto.setPaymentId(paymentDetails.getPaymentId());
            rtgsPaymentEntryDto.setPaymentNo(paymentDetails.getPaymentNo());
            rtgsPaymentEntryDto.setPaymentEntryDate(UtilityService.convertDateToDDMMYYYY(paymentDetails.getPaymentDate()));

            if ((paymentDetails.getBaBankAccountId() != null)
                    && (paymentDetails.getBaBankAccountId().getBaAccountId() != null)) {
                rtgsPaymentEntryDto.setBankAcId(paymentDetails.getBaBankAccountId().getBaAccountId());
            }

            if (paymentDetails.getInstrumentDate() != null) {
                rtgsPaymentEntryDto.setInsttDate(UtilityService.convertDateToDDMMYYYY(paymentDetails.getInstrumentDate()));
            }

            if (paymentDetails.getNarration() != null) {
                rtgsPaymentEntryDto.setNarration(paymentDetails.getNarration());
            }

            if (paymentDetails.getInstrumentNumber() != null) {
                final String chequno = getCheque(paymentDetails.getInstrumentNumber());
                if (chequno != null && !chequno.isEmpty()) {
                    rtgsPaymentEntryDto.setInstrumentNo(Long.valueOf(chequno));
                }
            }
            if (paymentDetails.getUtrNo() != null && !paymentDetails.getUtrNo().isEmpty()) {
                rtgsPaymentEntryDto.setUtrNo(paymentDetails.getUtrNo());
            }

            if (paymentDetails.getBillTypeId() != null) {
                final String paymentType = CommonMasterUtility.findLookUpDesc(AccountPrefix.ABT.toString(),
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        paymentDetails.getBillTypeId().getCpdId());
                rtgsPaymentEntryDto.setBillTypeDesc(paymentType);
            }

            if (paymentDetails.getPmdId() != null) {
                final String paymentTypeDesc = CommonMasterUtility.findLookUpDesc(AccountPrefix.PDM.toString(),
                        UserSession.getCurrent().getOrganisation().getOrgid(), paymentDetails.getPmdId());
                rtgsPaymentEntryDto.setPaymentTypeDesc(paymentTypeDesc);
                final String paymentTypeCode = CommonMasterUtility.findLookUpCode(AccountPrefix.PDM.toString(),
                        UserSession.getCurrent().getOrganisation().getOrgid(), paymentDetails.getPmdId());
                rtgsPaymentEntryDto.setPaymentTypeCode(paymentTypeCode);
            }

            /*
             * if (paymentDetails.getVmVendorId() != null) { final String vendorDescription = vendorRepository
             * .getVendorNameById(paymentDetails.getVmVendorId().getVmVendorid(), paymentDetails.getOrgId());
             * rtgsPaymentEntryDto.setVendorDesc(vendorDescription); }
             */
            final String payAmount = CommonMasterUtility.getAmountInIndianCurrency(paymentDetails.getPaymentAmount());
            rtgsPaymentEntryDto.setSanctionedAmountStr(payAmount);
            // getRTGSPaymentDetailsData(rtgsPaymentEntryDto, paymentDetails);
            getRTGSPaymentDetailsDataById(rtgsPaymentEntryDto, paymentDetails);
        }

        rtgsPaymentEntryDto.setModeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
                paymentDetails.getOrgId(), paymentDetails.getPaymentModeId().getCpdId()));

        rtgsPaymentEntryDto.setModeCode(CommonMasterUtility.findLookUpCode(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
                paymentDetails.getOrgId(), paymentDetails.getPaymentModeId().getCpdId()));

        return rtgsPaymentEntryDto;
    }

    private void getRTGSPaymentDetailsDataById(RTGSPaymentEntryDTO rtgsPaymentEntryDto,
            AccountPaymentMasterEntity paymentDetails) {

        final List<RTGSPaymentDetailsDTO> rtgsPaymentDetailsDtoList = new ArrayList<>();
        List<Object[]> paymentDetailsList = paymentEntryRepository.getPaymentDetailsListById(paymentDetails.getPaymentId(),
                paymentDetails.getOrgId());
        for (Object[] objects : paymentDetailsList) {
            RTGSPaymentDetailsDTO rtgsPaymentDetail = new RTGSPaymentDetailsDTO();
            if (objects[0] != null) {
                rtgsPaymentDetail.setId(Long.valueOf(objects[0].toString()));
            }
            if (objects[1] != null) {
                rtgsPaymentDetail.setVendorDesc((String) objects[1]);
            }
            BigDecimal paymentAmt = BigDecimal.ZERO;
            if (objects[3] != null) {
                paymentAmt = (BigDecimal) objects[3];
                String paymentAmount = CommonMasterUtility
                        .getAmountInIndianCurrency(paymentAmt.setScale(0, BigDecimal.ROUND_HALF_EVEN));
                rtgsPaymentDetail.setPaymentAmountDesc(paymentAmount);
            }
            if (objects[4] != null) {
                rtgsPaymentDetail.setBillNumber((String) objects[4]);
            }
            if (objects[5] != null) {
                BigDecimal netPayableAmt = (BigDecimal) objects[5];
                netPayableAmt = netPayableAmt.add(paymentAmt);
                String netPayableAmount = CommonMasterUtility
                        .getAmountInIndianCurrency(netPayableAmt.setScale(0, BigDecimal.ROUND_HALF_EVEN));
                rtgsPaymentDetail.setNetPayable(netPayableAmount);
            }
            rtgsPaymentDetailsDtoList.add(rtgsPaymentDetail);
        }
        rtgsPaymentEntryDto.setRtgsPaymentDetailsDto(rtgsPaymentDetailsDtoList);
    }

    @Override
    @Transactional
    public void updateDepositsByPaymentId(Long depId, Long paymentId, Long orgId) {
        if (depId != null && paymentId != null && orgId != null) {
            accountDepositJparepository.updateDepositsByPaymentId(depId,
                    paymentId, orgId);
        }
        LOGGER.info("Deposit update call againest that ids billmasterId=" + depId + "and paymentId=" + paymentId + "and orgId="
                + orgId + "if that ids are null then that table will not update");

    }

	@Override
	@Transactional
	public List<PaymentEntryDto> getPaymentDetailByBillId(Long billId, Long orgId) {
		
		List<PaymentEntryDto> dtoList=new ArrayList<>();
		 List<AccountPaymentDetEntity> payDetListEntity = paymentEntryRepository.findMultipleBillEntryDetailsByBillId(billId, orgId);
		 if(CollectionUtils.isNotEmpty(payDetListEntity)) {
			 payDetListEntity.forEach(payDetEntity->{
				   PaymentEntryDto dto=new PaymentEntryDto();
					dto.setPaymentNo(payDetEntity.getPaymentMasterId().getPaymentNo());
					dto.setPaymentEntryDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
			                .format(payDetEntity.getPaymentMasterId().getPaymentDate().getTime()));
					if(payDetEntity!=null && payDetEntity.getPaymentMasterId()!=null && payDetEntity.getPaymentMasterId().getVmVendorId()!=null && payDetEntity.getPaymentMasterId().getVmVendorId().getVmVendorname()!=null)
					dto.setVendorName(payDetEntity.getPaymentMasterId().getVmVendorId().getVmVendorname());
					dto.setPaymentAmount(payDetEntity.getPaymentAmt());
					dto.setNarration(payDetEntity.getPaymentMasterId().getNarration());
					dtoList.add(dto);
			 });
			    
		 }
		
		return dtoList;
	}

	@Override
	@Transactional
	public void updateUploadPaymentDeletedRecords(List<Long> removeFileById, Long updatedBy) {
		iAttachDocsDao.updateRecord(removeFileById, updatedBy, MainetConstants.RnLCommon.Flag_D);
	}

}
