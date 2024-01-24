package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.ContraVoucherEntryDao;
import com.abm.mainet.account.domain.AccountBankDepositeSlipDenomEntity;
import com.abm.mainet.account.domain.AccountBankDepositeSlipMasterEntity;
import com.abm.mainet.account.domain.AccountPaymentDetEntity;
import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.domain.VoucherTemplateDetailEntity;
import com.abm.mainet.account.domain.VoucherTemplateMasterEntity;
import com.abm.mainet.account.dto.AccountContraVoucherCashDepBean;
import com.abm.mainet.account.dto.AccountContraVoucherEntryBean;
import com.abm.mainet.account.repository.AccountBankDepositeSlipMasterJpaRepository;
import com.abm.mainet.account.repository.AccountPaymentMasterJpaRepository;
import com.abm.mainet.account.repository.AccountReceiptEntryJpaRepository;
import com.abm.mainet.account.repository.ContraEntryVoucherRepository;
import com.abm.mainet.account.repository.TbAcChequebookleafMasJpaRepository;
import com.abm.mainet.account.repository.VoucherTemplateRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountContraVoucherEntry;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

/** @author tejas.kotekar */
@Service
public class AccountContraVoucherEntryServiceImpl implements AccountContraVoucherEntryService {

    @Resource
    ContraEntryVoucherRepository contraEntryVoucherJpaRepository;

    @Resource
    ContraVoucherEntryDao contraVoucherEntryDao;

    @Resource
    AccountPaymentMasterJpaRepository paymentMasterJpaRepository;

    @Resource
    private TbDepartmentJpaRepository departmentRepository;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private TbFinancialyearJpaRepository financialYearJpaRepository;

    @Resource
    private AccountBankDepositeSlipMasterJpaRepository bankDepositeSlipMasterJpaRepository;

    @Resource
    private AccountReceiptEntryJpaRepository receiptEntryJpaRepository;

    @Resource
    private AccountVoucherPostService accountVoucherPostService;

    @Resource
    private VoucherTemplateRepository voucherTemplateRepository;

    @Resource
    TbAcChequebookleafMasJpaRepository chequeBookLeafRepository;

    /*
     * @Resource private JdbcStoredProcedure jdbcStoredProcedure;
     */

    @Resource
    private TbDepartmentService deparmentService;

    private static final String GENERATE_PAYMENT_NO_FIN_YEAR_ID = "Payment sequence number generation, The financial year id is getting null value";
    private static final String GENERATE_RECEIPT_NO_FIN_YEAR_ID = "Receipt sequence number generation, The financial year id is getting null value";
    private static final String GENERATE_VOUCHER_NO_FIN_YEAR_ID = "Voucher sequence number generation, The financial year id is getting null value";
    private static final String GETTING_BANK_BAL_AMT_FIN_YEAR_ID = "Getting Bank Balance Amount, The financial year id is getting null value";

    /**
     * This method performs save action for all the three(transfer,withdraw,deposit) contra voucher entries.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountContraVoucherEntryBean createContraEntry(final AccountContraVoucherEntryBean contraVoucherBean,
            final Long payModLookupId, final Organisation org) {

        final AccountBankDepositeSlipMasterEntity bankDepositSlipEntity = new AccountBankDepositeSlipMasterEntity();
        AccountBankDepositeSlipMasterEntity bankDepositSlipEntitySaved = new AccountBankDepositeSlipMasterEntity();
        AccountPaymentMasterEntity paymentMasterEntity = new AccountPaymentMasterEntity();
        TbServiceReceiptMasEntity receiptMasterEntity = new TbServiceReceiptMasEntity();
        final TbSrcptFeesDetEntity receiptDetEntity = new TbSrcptFeesDetEntity();
        final TbSrcptModesDetEntity receiptModesEntity = new TbSrcptModesDetEntity();
        final int langId = Integer.valueOf(contraVoucherBean.getLangId().toString()).intValue();

        if ((contraVoucherBean.getCoTranId() != null) && (contraVoucherBean.getCoTranId() > 0l)) {
            bankDepositSlipEntity.setDepositeSlipNumber(contraVoucherBean.getCoTranId().toString());
        }

        final Department dept = new Department();
        dept.setDpDeptid(deparmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
        bankDepositSlipEntity.setDeptId(dept);

        final Long contraVoucherNo = generateVoucherNumber(contraVoucherBean.getOrgId(), contraVoucherBean.getCoEntryDate());
        String coVouchetNumber = contraVoucherNo.toString();
        bankDepositSlipEntity.setDepositeSlipNumber(contraVoucherNo.toString());
        bankDepositSlipEntity.setDepositeSlipDate(contraVoucherBean.getCoEntryDate());
        // bankDepositSlipEntity.setDepositeBAAccountId(contraVoucherBean.getBaAccountidPay());
        bankDepositSlipEntity.setDepositeRemark(contraVoucherBean.getCoRemarkPay());
        if (null != contraVoucherBean.getCoAmountPay()) {
            bankDepositSlipEntity.setDepositeAmount(contraVoucherBean.getCoAmountPay());
        }
        bankDepositSlipEntity.setDepositTypeFlag(MainetConstants.CommonConstants.CHAR_C);// D-Deposit slip , C-Contra
        bankDepositSlipEntity.setOrgid(contraVoucherBean.getOrgId());

        final AccountFieldMasterEntity field = new AccountFieldMasterEntity();
        field.setFieldId(contraVoucherBean.getFieldId());
        bankDepositSlipEntity.setFieldId(field);
        bankDepositSlipEntity.setCreatedBy(contraVoucherBean.getCreatedBy());
        bankDepositSlipEntity.setCreatedDate(contraVoucherBean.getCreatedDate());
        Objects.requireNonNull(contraVoucherBean.getTransactionDate(),
                ApplicationSession.getInstance().getMessage("account.contra.voucher.transaction") + contraVoucherBean);
        // bankDepositSlipEntity.setDepositeDate(Utility.stringToDate(contraVoucherBean.getTransactionDate()));
        bankDepositSlipEntity.setLgIpMac(contraVoucherBean.getLgIpMac());

        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.T)) {
            bankDepositSlipEntity.setDepositeBAAccountId(contraVoucherBean.getBaAccountidRec());
            bankDepositSlipEntity.setCoTypeFlag(MainetConstants.AccountContraVoucherEntry.T);
            final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(MainetConstants.CHEQUE_DISHONOUR.PAY, org);
            for (final LookUp looUp : paymentModeList) {
                if (looUp.getLookUpId() == payModLookupId) {
                    bankDepositSlipEntity.setDepositeTypeFlag(looUp.getLookUpCode());
                }
            }

        } else if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W)) {
            bankDepositSlipEntity.setCoTypeFlag(MainetConstants.AccountContraVoucherEntry.W);
            bankDepositSlipEntity.setDepositeTypeFlag(MainetConstants.PAYMODE.WEB);
        } else {
            bankDepositSlipEntity.setCoTypeFlag(MainetConstants.AccountContraVoucherEntry.D);
            bankDepositSlipEntity.setDepositeTypeFlag(MainetConstants.WORKFLOWTYPE.Flag_D);
        }

        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D)) {
            bankDepositSlipEntity.setDepositeBAAccountId(contraVoucherBean.getBaAccountidRec());
            bankDepositSlipEntity.setDepositeAmount(contraVoucherBean.getCoAmountRec());
            bankDepositSlipEntity.setDepositeRemark(contraVoucherBean.getCoRemarkRec());
            saveCashDepositDenom(contraVoucherBean, bankDepositSlipEntity);
        }

        if (!contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W)) {
            bankDepositSlipEntitySaved = bankDepositeSlipMasterJpaRepository.save(bankDepositSlipEntity);
            final String depositSlipNo = bankDepositSlipEntitySaved.getDepositeSlipNumber().toString();
            contraVoucherBean.setCoVouchernumber(depositSlipNo);
        }

        final Long departmentId = deparmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());
        contraVoucherBean.setDepartmentId(departmentId);
        receiptMasterEntity.setFieldId(contraVoucherBean.getFieldId());
        contraVoucherBean.setFieldId(contraVoucherBean.getFieldId());

        if ((contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W))
                || (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D))) {
            paymentMasterEntity = savePaymentDetails(bankDepositSlipEntity, paymentMasterEntity, contraVoucherBean,
                    payModLookupId);
            paymentMasterEntity = paymentMasterJpaRepository.save(paymentMasterEntity);
            receiptMasterEntity = saveReceiptMasDetails(receiptMasterEntity, contraVoucherBean);
            saveReceiptDetDetails(bankDepositSlipEntity, receiptMasterEntity, receiptDetEntity, contraVoucherBean);
            saveReceiptModeDetDetails(bankDepositSlipEntity, receiptMasterEntity, receiptModesEntity, contraVoucherBean,
                    payModLookupId);
            receiptEntryJpaRepository.save(receiptMasterEntity);
            contraVoucherBean.setCoVouchernumber(paymentMasterEntity.getPaymentNo());
        }
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.T)) {
            paymentMasterEntity = savePaymentDetails(bankDepositSlipEntity, paymentMasterEntity, contraVoucherBean,
                    payModLookupId);
            paymentMasterEntity = paymentMasterJpaRepository.save(paymentMasterEntity);
            receiptMasterEntity = saveReceiptMasDetails(receiptMasterEntity, contraVoucherBean);
            saveReceiptDetDetails(bankDepositSlipEntity, receiptMasterEntity, receiptDetEntity, contraVoucherBean);
            saveReceiptModeDetDetails(bankDepositSlipEntity, receiptMasterEntity, receiptModesEntity, contraVoucherBean,
                    payModLookupId);
            receiptEntryJpaRepository.save(receiptMasterEntity);
        }
        String vouNo = postContraEntry(contraVoucherBean, langId, org);
        contraVoucherBean.setVoucherNo(vouNo);
        if ((contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.T))
                || (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W))) {
            updateChequeNumberStatus(contraVoucherBean.getChequebookDetid(), contraVoucherBean.getOrgId(), langId, org,
                    contraVoucherBean.getCoTypeFlag().toString(), paymentMasterEntity.getPaymentDate(),
                    paymentMasterEntity.getPaymentId());
        }
        if (coVouchetNumber != null && !coVouchetNumber.isEmpty()) {
            contraVoucherBean.setCoVouchernumber(coVouchetNumber);
        }
        return contraVoucherBean;
    }

    /* Save payment side details */
    private AccountPaymentMasterEntity savePaymentDetails(
            final AccountBankDepositeSlipMasterEntity bankDepositSlipEntity,
            final AccountPaymentMasterEntity paymentMasterEntity, final AccountContraVoucherEntryBean contraVoucherBean,
            final Long payModLookupId) {

        // Generate payment no//
        final String paymentNo = generatePaymentNumber(contraVoucherBean.getOrgId(), contraVoucherBean.getCoEntryDate());
        paymentMasterEntity.setPaymentNo(paymentNo);
        TbComparamDetEntity cpdIdPaymentMode = null;
        BankAccountMasterEntity baBankAccountId = null;
        baBankAccountId = new BankAccountMasterEntity();
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D)) {
            baBankAccountId.setBaAccountId(contraVoucherBean.getBaAccountidRec());
            paymentMasterEntity.setNarration(contraVoucherBean.getCoRemarkRec());
        } else {
            baBankAccountId.setBaAccountId(contraVoucherBean.getBaAccountidPay());
            paymentMasterEntity.setNarration(contraVoucherBean.getCoRemarkPay());
        }
        paymentMasterEntity.setBaBankAccountId(baBankAccountId);
        paymentMasterEntity.setPaymentDate(contraVoucherBean.getCoEntryDate());
        paymentMasterEntity.setPaymentAmount(contraVoucherBean.getCoAmountPay());
        cpdIdPaymentMode = new TbComparamDetEntity();

        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D)) {
            Long cpdIdPayMode = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                    AccountPrefix.PAY.toString(), contraVoucherBean.getOrgId());
            cpdIdPaymentMode.setCpdId(cpdIdPayMode);
        } else {
            cpdIdPaymentMode.setCpdId(contraVoucherBean.getCpdModePay());
        }
        paymentMasterEntity.setPaymentModeId(cpdIdPaymentMode);
        if ((contraVoucherBean.getChequebookDetid() != null)
                && contraVoucherBean.getCpdModePay().equals(payModLookupId)) {
            paymentMasterEntity.setInstrumentNumber(contraVoucherBean.getChequebookDetid());
            paymentMasterEntity.setInstrumentDate(
                    UtilityService.convertStringDateToDateFormat(contraVoucherBean.getCoChequedateStr()));
        }
        paymentMasterEntity.setOrgId(contraVoucherBean.getOrgId());

        AccountPaymentDetEntity paymentDetailsEntity = null;
        final List<AccountPaymentDetEntity> paymentDetailsList = new ArrayList<>();
        paymentDetailsEntity = new AccountPaymentDetEntity();
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D)) {
            final Long sacHeadId = getSacHeadId(contraVoucherBean.getBaAccountidRec(), contraVoucherBean.getOrgId());
            paymentDetailsEntity.setBudgetCodeId(sacHeadId);
            paymentDetailsEntity.setPaymentAmt(contraVoucherBean.getCoAmountRec());
        } else {
            final Long sacHeadId = getSacHeadId(contraVoucherBean.getBaAccountidPay(), contraVoucherBean.getOrgId());
            paymentDetailsEntity.setBudgetCodeId(sacHeadId);
            paymentDetailsEntity.setPaymentAmt(contraVoucherBean.getCoAmountPay());
        }
        paymentDetailsEntity.setOrgId(contraVoucherBean.getOrgId());
        paymentDetailsEntity.setCreatedBy(contraVoucherBean.getCreatedBy());
        paymentDetailsEntity.setCreatedDate(contraVoucherBean.getCreatedDate());
        paymentDetailsEntity.setLangId(contraVoucherBean.getLangId());
        paymentDetailsEntity.setLgIpMac(contraVoucherBean.getLgIpMac());
        paymentDetailsEntity.setPaymentMasterId(paymentMasterEntity);
        paymentDetailsList.add(paymentDetailsEntity);
        paymentMasterEntity.setPaymentDetailList(paymentDetailsList);

        /*
         * Organisation org = new Organisation(); org.setOrgid(contraVoucherBean.getOrgId()); final LookUp PdmTypeLookUp =
         * CommonMasterUtility.getLookUpFromPrefixLookUpValue("P", AccountPrefix.PDM.toString(),
         * Integer.valueOf(contraVoucherBean.getLangId().toString()), org); Long pdmTypeId = PdmTypeLookUp.getLookUpId(); //
         * payment type id if (pdmTypeId != null) { paymentMasterEntity.setPmdId(pdmTypeId); }
         */
        // paymentMasterEntity.setLangId(contraVoucherBean.getLangId());
        paymentMasterEntity.setCreatedBy(contraVoucherBean.getCreatedBy());
        paymentMasterEntity.setCreatedDate(contraVoucherBean.getCreatedDate());
        paymentMasterEntity.setLgIpMac(contraVoucherBean.getLgIpMac());

        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W)) {
            paymentMasterEntity.setPaymentTypeFlag(2L);
        }

        if (!contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W)) {
            paymentMasterEntity.setDepositeSlipId(bankDepositSlipEntity.getDepositeSlipId());
        }
        return paymentMasterEntity;
    }

    /* Save receipt side details */
    private TbServiceReceiptMasEntity saveReceiptMasDetails(final TbServiceReceiptMasEntity receiptMasterEntity,
            final AccountContraVoucherEntryBean contraVoucherBean) {

        // Generate receipt no//
        final Long receiptNo = generateReceiptNumber(contraVoucherBean.getOrgId(), contraVoucherBean.getCoEntryDate());
        receiptMasterEntity.setRmRcptno(receiptNo);
        receiptMasterEntity.setRmDate(contraVoucherBean.getCoEntryDate());
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.T)) {
            receiptMasterEntity.setRmAmount(contraVoucherBean.getCoAmountRec());
        }
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W)) {
            receiptMasterEntity.setRmAmount(contraVoucherBean.getCoAmountPay());
        }
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D)) {
            final long payModeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    PrefixConstants.ContraVoucherEntry.PCA, AccountPrefix.PAY.toString(), contraVoucherBean.getOrgId());
            receiptMasterEntity.setRmReceivedfrom(CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.toString(),
                    contraVoucherBean.getOrgId(), payModeId));
            receiptMasterEntity.setRmAmount(contraVoucherBean.getCoAmountRec());
        } else {
            receiptMasterEntity.setRmReceivedfrom(contraVoucherBean.getPayee());// Need to set Pay to
        }
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W)) {
            receiptMasterEntity.setRmReceivedfrom(contraVoucherBean.getPayTo());
        }
        if (!(contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D))) {
            receiptMasterEntity.setRmNarration(contraVoucherBean.getCoRemarkPay());
        } else {
            receiptMasterEntity.setRmNarration(contraVoucherBean.getCoRemarkRec());
        }
        receiptMasterEntity.setReceiptTypeFlag(AccountConstants.C.toString());
        receiptMasterEntity.setOrgId(contraVoucherBean.getOrgId());
        receiptMasterEntity.setOrgId(contraVoucherBean.getOrgId());
        receiptMasterEntity.setCreatedBy(contraVoucherBean.getCreatedBy());
        receiptMasterEntity.setCreatedDate(contraVoucherBean.getCreatedDate());
        receiptMasterEntity.setLgIpMac(contraVoucherBean.getLgIpMac());
        receiptMasterEntity.setDpDeptId(contraVoucherBean.getDepartmentId());
        return receiptMasterEntity;
    }

    private void saveReceiptDetDetails(final AccountBankDepositeSlipMasterEntity bankDepositSlipEntity,
            final TbServiceReceiptMasEntity receiptMasterEntity, final TbSrcptFeesDetEntity receiptDetEntity,
            final AccountContraVoucherEntryBean contraVoucherBean) {

        final List<TbSrcptFeesDetEntity> recptDetEntityList = new ArrayList<>();
        receiptDetEntity.setDepositeSlipId(bankDepositSlipEntity.getDepositeSlipId());
        receiptDetEntity.setRmRcptid(receiptMasterEntity);
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.T)) {
            final Long sacHeadId = getSacHeadId(contraVoucherBean.getBaAccountidRec(), contraVoucherBean.getOrgId());
            receiptDetEntity.setSacHeadId(sacHeadId);
            receiptDetEntity.setRfFeeamount(contraVoucherBean.getCoAmountRec());
        }
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D)) {
            final Long sacHeadId = getSacHeadId(contraVoucherBean.getBaAccountidRec(), contraVoucherBean.getOrgId());
            receiptDetEntity.setSacHeadId(sacHeadId);
            receiptDetEntity.setRfFeeamount(contraVoucherBean.getCoAmountRec());
        }
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W)) {
            Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CWE.toString(),
                    AccountPrefix.TDP.toString(), contraVoucherBean.getOrgId());
            Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.CommonConstants.ACTIVE, AccountPrefix.ACN.toString(), contraVoucherBean.getOrgId());
            final VoucherTemplateMasterEntity template = voucherTemplateRepository
                    .queryDefinedTemplate(voucherSubTypeId, contraVoucherBean.getDepartmentId(), contraVoucherBean.getOrgId(),
                            status, null);
            Long sacHeadId = null;
            for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : template.getTemplateDetailEntities()) {
                sacHeadId = voucherTemplateDetailEntity.getSacHeadId();
                break;
            }
            receiptDetEntity.setSacHeadId(sacHeadId);
            receiptDetEntity.setRfFeeamount(contraVoucherBean.getCoAmountPay());
        }
        receiptDetEntity.setOrgId(contraVoucherBean.getOrgId());
        receiptDetEntity.setCreatedBy(contraVoucherBean.getCreatedBy());
        receiptDetEntity.setCreatedDate(contraVoucherBean.getCreatedDate());
        receiptDetEntity.setLgIpMac(contraVoucherBean.getLgIpMac());
        recptDetEntityList.add(receiptDetEntity);
        receiptMasterEntity.setReceiptFeeDetail(recptDetEntityList);
    }

    private void saveReceiptModeDetDetails(final AccountBankDepositeSlipMasterEntity bankDepositSlipEntity,
            final TbServiceReceiptMasEntity receiptMasterEntity, final TbSrcptModesDetEntity receiptModesEntity,
            final AccountContraVoucherEntryBean contraVoucherBean, final Long payModLookupId) {
        final ApplicationSession session = ApplicationSession.getInstance();
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D)) {
            long payModeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    PrefixConstants.ContraVoucherEntry.PCA, AccountPrefix.PAY.toString(), contraVoucherBean.getOrgId());
            receiptModesEntity.setCpdFeemode(payModeId);
        } else {
            receiptModesEntity.setCpdFeemode(contraVoucherBean.getCpdModePay());
        }
        receiptModesEntity.setRdAmount(contraVoucherBean.getCoAmountRec());
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D)) {
            receiptModesEntity.setBaAccountid(contraVoucherBean.getBaAccountidRec());
        } else {
            receiptModesEntity.setBaAccountid(contraVoucherBean.getBaAccountidPay());
        }
        Long budgetCodeId;
        if (contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D)) {
            budgetCodeId = getSacHeadId(contraVoucherBean.getBaAccountidRec(), contraVoucherBean.getOrgId());
        } else {
            budgetCodeId = getSacHeadId(contraVoucherBean.getBaAccountidPay(), contraVoucherBean.getOrgId());
        }

        Objects.requireNonNull(budgetCodeId,
                session.getMessage("account.contra.voucher.budgetcode") + contraVoucherBean.getBaAccountidPay()
                        + MainetConstants.AccountContraVoucherEntry.ORG_ID + contraVoucherBean.getOrgId()
                        + MainetConstants.WHITE_SPACE + contraVoucherBean);
        if (!(contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D))) {
            if ((contraVoucherBean.getChequebookDetid() != null)
                    && contraVoucherBean.getCpdModePay().equals(payModLookupId)) {
                String chequeNo = chequeBookLeafRepository.getChequeNoById(contraVoucherBean.getChequebookDetid());
                if (chequeNo != null && !chequeNo.isEmpty()) {
                    receiptModesEntity.setRdChequeddno(Long.valueOf(chequeNo));
                }
                receiptModesEntity.setRdChequedddate(
                        UtilityService.convertStringDateToDateFormat(contraVoucherBean.getCoChequedateStr()));
            }
        }
        
        Organisation org = new Organisation();
        org.setOrgid(contraVoucherBean.getOrgId());
        
        LookUp depositNtIsuedLookUp = CommonMasterUtility.getValueFromPrefixLookUp("DNC", "CLR", org);
        receiptModesEntity.setRmRcptid(receiptMasterEntity);
        receiptModesEntity.setOrgId(contraVoucherBean.getOrgId());
        receiptModesEntity.setCreatedBy(contraVoucherBean.getCreatedBy());
        receiptModesEntity.setCreatedDate(contraVoucherBean.getCreatedDate());
        receiptModesEntity.setLgIpMac(contraVoucherBean.getLgIpMac());
        if(depositNtIsuedLookUp != null && StringUtils.isNotBlank(depositNtIsuedLookUp.getOtherField()))
        {
        	 receiptModesEntity.setRdSrChkDis(String.valueOf(depositNtIsuedLookUp.getOtherField()));
        	 receiptModesEntity.setCheckStatus(depositNtIsuedLookUp.getLookUpId());
        }else {
        	throw new FrameworkException("Please define other value under deposit issued but not present in CLR prefix");
        }
       
        List<TbSrcptModesDetEntity> receiptModeList = new ArrayList<TbSrcptModesDetEntity>();
        receiptModeList.add(receiptModesEntity);
        receiptMasterEntity.setReceiptModeDetail(receiptModeList);
    }

    private Long generateVoucherNumber(final Long orgId, Date coEntryDate) {
        Long finYearId = financialYearJpaRepository.getFinanciaYearIdByFromDate(coEntryDate);
        if (finYearId == null) {
            throw new NullPointerException(GENERATE_VOUCHER_NO_FIN_YEAR_ID);
        }
        final Long cVoucherNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                MainetConstants.TB_AC_BANK_DEPOSITSLIP_MASTER, MainetConstants.DPS_SLIPNO, orgId,
                MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, finYearId);
        return cVoucherNumber;
    }

    /*
     * private String generatePaymentNumber(final Long orgId) { final Object[] ipValues = new Object[] {
     * MainetConstants.RECEIPT_MASTER.Module, AccountContraVoucherEntry.TB_AC_PAYMENT_MAS2, AccountContraVoucherEntry.PAYMENT_NO2,
     * orgId, MainetConstants.RECEIPT_MASTER.Reset_Type, null, }; final Object seq =
     * voucherTemplateRepository.generateSeqNo(ipValues, orgId); return seq.toString(); }
     */

    private String generatePaymentNumber(final Long orgId, Date coEntryDate) {
        Long finYearId = financialYearJpaRepository.getFinanciaYearIdByFromDate(coEntryDate);
        if (finYearId == null) {
            throw new NullPointerException(GENERATE_PAYMENT_NO_FIN_YEAR_ID);
        }
        final Long paymentNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                AccountContraVoucherEntry.TB_AC_PAYMENT_MAS2, AccountContraVoucherEntry.PAYMENT_NO2, orgId,
                MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE,
                finYearId);
        return paymentNumber.toString();
    }

    private Long generateReceiptNumber(final Long orgId, Date coEntryDate) {
        Long finYearId = financialYearJpaRepository.getFinanciaYearIdByFromDate(coEntryDate);
        if (finYearId == null) {
            throw new NullPointerException(GENERATE_RECEIPT_NO_FIN_YEAR_ID);
        }
        final Long receiptNumber = seqGenFunctionUtility.generateSequenceNo(AccountConstants.AC.toString(),
                MainetConstants.TB_RECEIPT_MAS, MainetConstants.RM_RCPTNO, orgId,
                MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, finYearId);
        return receiptNumber;
    }

    private void saveCashDepositDenom(final AccountContraVoucherEntryBean contraVoucherBean,
            final AccountBankDepositeSlipMasterEntity bankDepositSlipEntity) {

        AccountBankDepositeSlipDenomEntity cashDepEntity = null;
        final List<AccountBankDepositeSlipDenomEntity> cashDepList = new ArrayList<>(0);
        if ((contraVoucherBean.getCashDep() != null) && !contraVoucherBean.getCashDep().isEmpty()) {
            for (final AccountContraVoucherCashDepBean cashDep : contraVoucherBean.getCashDep()) {
                cashDepEntity = new AccountBankDepositeSlipDenomEntity();
                if ((cashDep.getCashdepDetId() != null) && (cashDep.getCashdepDetId() > 0L)) {
                    cashDepEntity.setCashdepDetId(cashDep.getCashdepDetId());
                }
                cashDepEntity.setCpdDenomId(cashDep.getTbComparamDet());
                cashDepEntity.setDenominationCount(cashDep.getDenomination());
                cashDepEntity.setOrgid(contraVoucherBean.getOrgId());
                cashDepEntity.setLangId(Integer.parseInt(contraVoucherBean.getLangId().toString()));
                cashDepEntity.setLgIpMac(contraVoucherBean.getLgIpMac());
                cashDepEntity.setCreatedDate(contraVoucherBean.getCreatedDate());
                cashDepEntity.setCreatedBy(contraVoucherBean.getCreatedBy());
                cashDepEntity.setDepositeSlipId(bankDepositSlipEntity);
                if (cashDep.getDenomination() != null) {
                    cashDepList.add(cashDepEntity);
                }
            }
        }
        bankDepositSlipEntity.setDenominationEntityList(cashDepList);
    }

    private String postContraEntry(final AccountContraVoucherEntryBean contraVoucherBean, final int langId,
            final Organisation org) {

        final VoucherPostDTO postDto = new VoucherPostDTO();
        final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
        VoucherPostDetailDTO postDetailDtoCr = null;
        VoucherPostDetailDTO postDetailDtoDr = null;
        Long voucherSubTypeId = null;
        postDto.setVoucherDate(Utility.stringToDate(contraVoucherBean.getTransactionDate()));
        if ((contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.T))) {
            voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.BTE.toString(),
                    AccountPrefix.TDP.toString(), contraVoucherBean.getOrgId());
        }
        if ((contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W))) {
            voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CWE.toString(),
                    AccountPrefix.TDP.toString(), contraVoucherBean.getOrgId());
        }
        if ((contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D))) {
            voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCD.toString(),
                    AccountPrefix.TDP.toString(), contraVoucherBean.getOrgId());
        }
        postDto.setVoucherSubTypeId(voucherSubTypeId);
        postDto.setDepartmentId(contraVoucherBean.getDepartmentId());
        postDto.setVoucherReferenceNo(contraVoucherBean.getCoVouchernumber());
        if (!(contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D))) {
            postDto.setNarration(contraVoucherBean.getCoRemarkPay());
            postDto.setPayerOrPayee(contraVoucherBean.getPayee());
            if ((contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W))) {
                postDto.setPayerOrPayee(contraVoucherBean.getPayTo());
            }
        } else {
            postDto.setNarration(contraVoucherBean.getCoRemarkRec());
            postDto.setPayerOrPayee(contraVoucherBean.getPayTo());
        }
        postDto.setFieldId(contraVoucherBean.getFieldId());
        postDto.setOrgId(contraVoucherBean.getOrgId());
        postDto.setCreatedBy(contraVoucherBean.getCreatedBy());
        postDto.setCreatedDate(contraVoucherBean.getCreatedDate());
        postDto.setLgIpMac(contraVoucherBean.getLgIpMac());
        postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.MAN_ENTRY_TYPE);
        // Autho Flag
        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ContraVoucherEntry.CV, AccountPrefix.AUT.toString(), langId, org);
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            // dto.setVoucherDate(tbReceiptMasEntity.getRmDate());
            postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
        } else {
            // dto.setVoucherDate(tbReceiptMasEntity.getCreatedDate());
        }

        if ((contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.T))) {
            /* Credit side */
            postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(contraVoucherBean.getCoAmountPay());
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                    PrefixConstants.DCR, contraVoucherBean.getOrgId());
            postDetailDtoCr.setDrCrId(crId);
            final Long sacHeadIdCr = getSacHeadId(contraVoucherBean.getBaAccountidPay(), contraVoucherBean.getOrgId());
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);
            postDetailDtoCr.setPayModeId(contraVoucherBean.getCpdModePay());

            /* Debit side */
            postDetailDtoDr = new VoucherPostDetailDTO();
            postDetailDtoDr.setVoucherAmount(contraVoucherBean.getCoAmountRec());
            final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                    PrefixConstants.DCR, contraVoucherBean.getOrgId());
            postDetailDtoDr.setDrCrId(drId);
            final Long sacHeadIdDr = getSacHeadId(contraVoucherBean.getBaAccountidRec(), contraVoucherBean.getOrgId());
            postDetailDtoDr.setSacHeadId(sacHeadIdDr);
            postDetailDtoDr.setPayModeId(contraVoucherBean.getCpdModePay());
        }
        if ((contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.W))) {

            /* Credit side */
            postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(contraVoucherBean.getCoAmountPay());
            final Long sacHeadIdCr = getSacHeadId(contraVoucherBean.getBaAccountidPay(), contraVoucherBean.getOrgId());
            postDetailDtoCr.setSacHeadId(sacHeadIdCr);

            /* Debit side */
            postDetailDtoDr = new VoucherPostDetailDTO();
            postDetailDtoDr.setVoucherAmount(contraVoucherBean.getCoAmountPay());// Rec

            // Get budget code id for petty cash
            final Long cpdIdPayMode = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                    AccountPrefix.PAY.toString(), org.getOrgid());
            postDetailDtoDr.setPayModeId(cpdIdPayMode);
        }
        if ((contraVoucherBean.getCoTypeFlag().equals(MainetConstants.AccountContraVoucherEntry.D))) {

            /* Credit side */
            postDetailDtoCr = new VoucherPostDetailDTO();
            postDetailDtoCr.setVoucherAmount(contraVoucherBean.getCoAmountRec());

            final Long cpdIdPayMode = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.PCA.toString(),
                    AccountPrefix.PAY.toString(), org.getOrgid());
            postDetailDtoCr.setPayModeId(cpdIdPayMode);

            /* Debit side */
            postDetailDtoDr = new VoucherPostDetailDTO();
            postDetailDtoDr.setVoucherAmount(contraVoucherBean.getCoAmountRec());// Pay
            final Long sacHeadIdDr = getSacHeadId(contraVoucherBean.getBaAccountidRec(), contraVoucherBean.getOrgId());
            postDetailDtoDr.setSacHeadId(sacHeadIdDr);
        }
        voucherDetails.add(postDetailDtoCr);
        voucherDetails.add(postDetailDtoDr);
        postDto.setVoucherDetails(voucherDetails);

        String response = accountVoucherPostService.contraVoucherPosting(postDto);
        return response;

        /*
         * if (!response.getStatusCode().is2xxSuccessful()) { throw new IllegalArgumentException("Voucher Posting failed {" +
         * response.getBody()); }
         */

    }

    @Override
    @Transactional(readOnly = true)
    public Long getSacHeadId(final Long bankAccountId, final Long orgId) {
        return contraEntryVoucherJpaRepository.getSacHeadIdByBankAccountId(bankAccountId, orgId);
    }

    public void updateBankBalance(final AccountContraVoucherEntryBean contraVoucherBean, final int langId,
            final Organisation org) {

        if (contraVoucherBean.getCoTypeFlag().equals(AccountConstants.CONTRA_TRANSFER.toString())) {

        }
        if (contraVoucherBean.getCoTypeFlag().equals(AccountConstants.CONTRA_WITHDRAW.toString())) {
        }
        if (contraVoucherBean.getCoTypeFlag().equals(AccountConstants.CONTRA_DEPOSIT.toString())) {

        }
    }

    public void updateChequeNumberStatus(final Long chequeBookDetId, final Long orgId, final int langId,
            final Organisation org, final String paymentType, final Date issuanceDate, final Long paymentId) {

        final LookUp lkpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ISSUED.getValue(),
                AccountPrefix.CLR.toString(), langId, org);
        final Long cpdIdStatus = lkpStatus.getLookUpId();
        // chequeBookLeafRepository.updateChequeNumberStatus(cpdIdStatus,
        // chequeBookDetId, orgId, paymentType, paymentId);
        chequeBookLeafRepository.updateChequeDetailsForPayments(cpdIdStatus, chequeBookDetId, orgId, paymentId,
                issuanceDate, paymentType);

    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBankBalance(final Long bankAcId, Date tranDate, Long orgid) {
        Long finYearId = financialYearJpaRepository.getFinanciaYearIdByFromDate(tranDate);
        Date finYearStartDate = null;
        if (finYearId == null) {
            throw new NullPointerException(GETTING_BANK_BAL_AMT_FIN_YEAR_ID);
        } else {
            List<Object[]> finYearStartDateList = financialYearJpaRepository.getFinanceYearFrmDate(finYearId);
            for (Object[] objects : finYearStartDateList) {
                if (objects[0] != null) {
                    finYearStartDate = (Date) objects[0];
                }
            }
        }
        List<Object[]> getBalance = contraEntryVoucherJpaRepository.getBankAcBalance(bankAcId, tranDate, orgid, finYearId,
                finYearStartDate);
        BigDecimal finalBankBal = null;
        if (getBalance == null || getBalance.isEmpty()) {
            finalBankBal = BigDecimal.ZERO;
        } else {
            final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, orgid);
            BigDecimal openbalance = null;
            Long cpdIdCrDr = null;
            BigDecimal crAmount = null;
            BigDecimal drAmount = null;
            for (Object[] objects : getBalance) {
                if (objects[0] != null && !objects[0].equals(BigDecimal.ZERO)) {
                    openbalance = new BigDecimal(objects[0].toString());//845902.47
                } else {
                    openbalance = new BigDecimal(0.00);
                }
                if (objects[2] != null) {
                    cpdIdCrDr = (Long.valueOf(objects[2].toString()));//DR
                }
                if (objects[3] != null) {
                    crAmount = new BigDecimal(objects[3].toString());//15112639.45
                } else {
                    crAmount = new BigDecimal(0.00);
                }
                if (objects[4] != null) {
                    drAmount = new BigDecimal(objects[4].toString());//17442860
                } else {
                    drAmount = new BigDecimal(0.00);
                }
            }
            BigDecimal bankBalance = null;
            if (cpdIdCrDr != null) {
                if (cpdIdCrDr.equals(drId)) {
                    bankBalance = openbalance.add(drAmount).subtract(crAmount);//(50180+4259615)-46138885
                } else {
                    bankBalance = drAmount.subtract(openbalance.add(crAmount));//17442860-(15112639.45+845902.47)
                }
            } else {
                bankBalance = drAmount.subtract(crAmount);
            }
            /*
             * if(bankBalance.compareTo(drAmount) >= 0){ throw new NullPointerException("bankBalance is greater than drAmount"); }
             */
            if (bankBalance != null && bankBalance.compareTo(BigDecimal.ZERO) > 0) {
                finalBankBal = bankBalance;
			} 
			else {
				finalBankBal = bankBalance;
			}
				 
        }
        return finalBankBal;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountContraVoucherEntryService# getAllContraEntryData(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public Set<Object[]> getAllContraEntryData(final Long orgId) {
        return contraVoucherEntryDao.getAllContraEntryData(orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountContraVoucherEntryService# getTransactionNo(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Long> getTransactionNo(final Long orgId) {
        return contraEntryVoucherJpaRepository.getTransactionNo(orgId);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountContraVoucherEntryService# getContraEntryDetails(java.lang.Long,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public Set<Object[]> getContraEntryDetails(final Long orgId, final String fromDate, final String toDate,
            final Long transactionNo, final Character transactionType) {

        return contraVoucherEntryDao.getContraEntryDetails(orgId, fromDate, toDate, transactionNo, transactionType);
    }

    @Override
    @Transactional(readOnly = true)
    public Object[] getContraEntryDataById(final Long transactionId, final Long orgId) {
        return contraVoucherEntryDao.getContraEntryDataById(transactionId, orgId);
    }

    @Override
    @Transactional
    public boolean checkTemplateType(final VoucherPostDTO dto, final Long templateTypeId, final Long voucherTypeId) {
        final List<VoucherTemplateMasterEntity> templateList = voucherTemplateRepository.searchTemplateData(
                templateTypeId, dto.getFinancialYearId(), voucherTypeId, dto.getVoucherSubTypeId(), dto.getOrgId());
        if ((templateList == null) || templateList.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getDepositDetailsForChequeUtilization(final Long paymentId, final Long orgId) {
        final List<Object[]> details = contraEntryVoucherJpaRepository.getDepositDetailsForChequeUtilization(paymentId,
                orgId);
        return details;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getBudgetCodeIdForPettyChash(final Long cpdIdPayMode, final Long orgId) {
        final Long budgetCodeId = contraEntryVoucherJpaRepository.getBudgetCodeIdForPettyChash(cpdIdPayMode, orgId);
        return budgetCodeId;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getPettyCashAmount(Date date, Long orgId, Long cpdIdPayMode, Long voucherSubTypeId,
            Long voucherSubTypeId1, Long voucherSubTypeId2, Long voucherSubTypeId3, Long status, Long deptId) {
        // TODO Auto-generated method stub
        final VoucherTemplateMasterEntity template = voucherTemplateRepository.queryDefinedTemplate(voucherSubTypeId,
                deptId, orgId, status, null);
        if (template == null) {
            throw new NullPointerException("Voucher template is not exist " + "voucherTemplateFor " + voucherSubTypeId);
        }
        Long sacHeadId = null;
        List<VoucherTemplateDetailEntity> detList = template.getTemplateDetailEntities();
        for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : detList) {
            if (voucherTemplateDetailEntity.getCpdIdPayMode().equals(cpdIdPayMode)) {
                sacHeadId = voucherTemplateDetailEntity.getSacHeadId();
                break;
            }
        }
        if ((sacHeadId == null) || (sacHeadId == 0l)) {
            throw new IllegalArgumentException(
                    ApplicationSession.getInstance().getMessage("account.voucher.service.cpdid.paymode")
                            + cpdIdPayMode);
        }
        List<Long> vouSubTypes = new ArrayList<>(0);
        vouSubTypes.add(voucherSubTypeId1);
        vouSubTypes.add(voucherSubTypeId2);
        vouSubTypes.add(voucherSubTypeId3);
        BigDecimal finalPettyCash = null;
        List<Object[]> pettyCashAmt = contraEntryVoucherJpaRepository.getPettyCashAmount(date, orgId, sacHeadId,
                vouSubTypes);
        for (Object[] objects : pettyCashAmt) {
            if (objects[0] != null && objects[1] != null) {
                BigDecimal crAmt = new BigDecimal(objects[0].toString());
                BigDecimal drAmt = new BigDecimal(objects[1].toString());
                finalPettyCash = drAmt.subtract(crAmt);
            }
        }
        return finalPettyCash;
    }

    @Override
    @Transactional
    public BigDecimal getCashAmount(Date date, Long orgId, Long cpdIdPayMode, Long voucherSubTypeId, Long status,
            Long deptId) {
        // TODO Auto-generated method stub
        final VoucherTemplateMasterEntity template = voucherTemplateRepository.queryDefinedTemplate(voucherSubTypeId,
                deptId, orgId, status, null);
        if (template == null) {
            throw new NullPointerException("Voucher template is not exist " + "voucherTemplateFor " + voucherSubTypeId);
        }
        Long sacHeadId = null;
        List<VoucherTemplateDetailEntity> detList = template.getTemplateDetailEntities();
        for (VoucherTemplateDetailEntity voucherTemplateDetailEntity : detList) {
            if (voucherTemplateDetailEntity.getCpdIdPayMode().equals(cpdIdPayMode)) {
                sacHeadId = voucherTemplateDetailEntity.getSacHeadId();
                break;
            }
        }
        if ((sacHeadId == null) || (sacHeadId == 0l)) {
            throw new IllegalArgumentException(
                    ApplicationSession.getInstance().getMessage("account.voucher.service.cpdid.paymode")
                            + cpdIdPayMode);
        }
        BigDecimal finalPettyCash = null;
        List<Object[]> pettyCashAmt = contraEntryVoucherJpaRepository.getCashAmount(date, orgId, sacHeadId);
        for (Object[] objects : pettyCashAmt) {
            if (objects[0] != null && objects[1] != null) {
                BigDecimal crAmt = new BigDecimal(objects[0].toString());
                BigDecimal drAmt = new BigDecimal(objects[1].toString());
                finalPettyCash = drAmt.subtract(crAmt);
            }
        }
        return finalPettyCash;
    }

}
