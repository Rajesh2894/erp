package com.abm.mainet.account.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.abm.mainet.account.dao.AccountJournalVoucherEntryDao;
import com.abm.mainet.account.domain.AccountInvestmentMasterEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryDetailsEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.dto.AccountInvestmentMasterDto;
import com.abm.mainet.account.dto.ReceiptReversalViewDTO;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.repository.AccountBankDepositeSlipMasterJpaRepository;
import com.abm.mainet.account.repository.AccountDepositRepository;
import com.abm.mainet.account.repository.AccountReceiptEntryJpaRepository;
import com.abm.mainet.account.repository.AccountVoucherEntryRepository;
import com.abm.mainet.account.repository.AccountVoucherReversalRepository;
import com.abm.mainet.account.repository.BudgetHeadRepository;
import com.abm.mainet.account.rest.dto.VoucherReversalExtDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountJournalVoucherEntry;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 *
 * @author Vivek.Kumar
 * @since 19 May 2017
 */
@Service
public class AccountVoucherReversalServiceImpl implements AccountVoucherReversalService {

    @Resource
    private AccountVoucherReversalRepository accountVoucherReversalRepository;
    @Resource
    private IEmployeeService iEmployeeService;
    @Resource
    private BudgetHeadRepository budgetHeadRepository;
    @Resource
    private TbBankmasterService banksMasterService;

    @Resource
    private AccountReceiptEntryJpaRepository accountReceiptEntryJpaRepository;

    @Resource
    private AccountVoucherPostService accountVoucherPostService;
    @Resource
    AccountJournalVoucherEntryDao journalVoucherDao;

    @Resource
    private AccountVoucherEntryRepository accountVoucherEntryRepository;
    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;
    @Resource
    private AccountDepositRepository accountDepositRepository;
    @Resource
    private AccountReceiptEntryJpaRepository accountReceiptEntrRrepository;
    @Resource
    private TbDepartmentService deparmentService;
    @Resource
    private TbFinancialyearService tbFinancialyearService;
    @Resource
    private ReceiptReversalProvisionService receiptReversalProvisionService;
    
    @Resource
    AccountInvestmentService accountInvestmentService;
    
    @Resource
	private AccountBankDepositeSlipMasterJpaRepository accountBankDepositeSlipMasterJpaRepository;
    
    @Resource
	private AccountChequeOrCashDepositeService chequeOrCashService;

    private static final String SEQUENCE_NO_ERROR = "unable to generate sequenceNo for orgId=";
    private static final String SEQUENCE_NO = "0000000000";
    private static final String TEMPLATE_ID_NOT_FOUND_VOU = "templateTypeId not found for provided parameter[voucherType=";
    private static final String ORG_ID = ", orgId=";
    private static final String PREFIX_VOT = ",prefix=VOT]";

    private static final Logger LOGGER = Logger.getLogger(AccountVoucherReversalServiceImpl.class);
    private static final String ORGID = " orgId=";

    private static final String GENERATE_RECEIPT_NO_FIN_YEAR_ID = "Receipt sequence number generation, The financial year id is getting null value";
    private static final String GENERATE_VOUCHER_NO_VOU_TYPE_ID = "Voucher sequence number generation, The voucher type id is getting null value";

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findRecordsForReversal(final VoucherReversalDTO dto, final long orgId, final long deptId) {
        ResponseEntity<?> response = null;
        final List<Object[]> resultList = accountVoucherReversalRepository.findRerodsForVoucherReversal(dto, deptId,
                orgId);
        if (Objects.nonNull(resultList) && !resultList.isEmpty()) {
            final String[] urlArray = identifyActionURL(dto.getTransactionType());
            final List<VoucherReversalDTO> list = new ArrayList<>();
            for (final Object[] objArray : resultList) {
                final VoucherReversalDTO resultDTO = new VoucherReversalDTO();
                resultDTO.setPrimaryKey((long) objArray[0]);
                resultDTO.setId((long) objArray[0]);
                if (objArray[1] instanceof String) {
                    resultDTO.setTransactionNo(objArray[1].toString());
                } else if (objArray[1] != null) {
                    resultDTO.setTransactionNo(objArray[1].toString());
                }
                resultDTO.setTransactionDate(Utility.dateToString((Date) objArray[2]));
                if (objArray[3] instanceof BigDecimal) {
                    resultDTO.setTransactionAmount(CommonMasterUtility
                            .getAmountInIndianCurrency(((BigDecimal) objArray[3]).setScale(2, RoundingMode.CEILING)));
                    // resultDTO.setTransactionAmount(((BigDecimal) objArray[3]).setScale(2,
                    // RoundingMode.CEILING));
                } else if (objArray[3] != null) {
                    resultDTO.setTransactionAmount(CommonMasterUtility.getAmountInIndianCurrency(
                            BigDecimal.valueOf((Double) objArray[3]).setScale(2, RoundingMode.CEILING)));
                    // resultDTO.setTransactionAmount(BigDecimal.valueOf((Double)
                    // objArray[3]).setScale(2, RoundingMode.CEILING));
                }
                resultDTO.setNarration((String) objArray[4]);
                final boolean status = objArray[5] == null ? false : MainetConstants.MENU.Y.equals(objArray[5]);
                resultDTO.setReversedOrNot(status ? MainetConstants.YESL : MainetConstants.NOL);
                resultDTO.setViewURL(urlArray[0]);
                if (MainetConstants.DSE.equals(dto.getTransactionType()) && (objArray[6] != null)) {
                    resultDTO.setPayModeType((String) objArray[6]);
                }
                list.add(resultDTO);
            }
            final Object[] responseArray = new Object[2];
            // actionUrl on reverse button
            responseArray[0] = urlArray[1];
            responseArray[1] = list;
            response = ResponseEntity.status(HttpStatus.OK).body(responseArray);
        } else {
            switch (dto.getTransactionType()) {
            case "RP":
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(MainetConstants.MENU.R);
                break;
            case "BP":
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(MainetConstants.MENU.B);
                break;
            case "DSE":
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(MainetConstants.MENU.S);
                break;
            case "BPE":
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(MainetConstants.MENU.P);
                break;
            case "DPE":
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(MainetConstants.MENU.D);
                break;
            }
        }
        return response;
    }

    private String[] identifyActionURL(final String transactionType) {

        final String[] urlArray = new String[2];
        switch (transactionType) {
        case MainetConstants.RP:
            urlArray[0] = MainetConstants.VOUCHER_REVERSAL_URLS.RECEIPT_DETAIL_VIEW_URL;
            urlArray[1] = MainetConstants.VOUCHER_REVERSAL_URLS.RECEIPT_REVERSAL_URL;
            break;
        case MainetConstants.BP:
            urlArray[0] = MainetConstants.VOUCHER_REVERSAL_URLS.BILL_INVOICE_DETAIL_VIEW_URL;
            urlArray[1] = MainetConstants.VOUCHER_REVERSAL_URLS.BILL_INVOICE_REVERSAL_URL;
            break;
        case MainetConstants.DSE:
            urlArray[0] = MainetConstants.VOUCHER_REVERSAL_URLS.DEPOSITSLIP_DETAIL_VIEW_URL;
            urlArray[1] = MainetConstants.VOUCHER_REVERSAL_URLS.DEPOSITSLIP_REVERSAL_URL;
            break;
        case MainetConstants.BPE:
            urlArray[0] = MainetConstants.VOUCHER_REVERSAL_URLS.PAYMENT_DETAIL_VIEW_URL;
            urlArray[1] = MainetConstants.VOUCHER_REVERSAL_URLS.PAYMENT_ENTRY_REVERSAL_URL;
            break;

        case MainetConstants.DPE:
            urlArray[0] = MainetConstants.VOUCHER_REVERSAL_URLS.DIRECT_PAYMENT_DETAIL_VIEW_URL;
            urlArray[1] = MainetConstants.VOUCHER_REVERSAL_URLS.PAYMENT_ENTRY_REVERSAL_URL;
            break;

        default:
            break;
        }

        return urlArray;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookUp> findApprovalAuthority(final long deptId, final long orgId) {
        final List<Object[]> list = iEmployeeService.findActiveEmployeeByDeptId(deptId, orgId);
        Objects.requireNonNull(list,
                ApplicationSession.getInstance().getMessage("account.voucher.reversal.service.approval") + deptId
                        + ORGID + orgId);
        final List<LookUp> approvalAuthorities = new ArrayList<>();
        for (final Object[] objArr : list) {
            final LookUp lookUp = new LookUp();
            lookUp.setLookUpId((long) objArr[0]);
            lookUp.setDescLangFirst(empFullName(objArr));
            approvalAuthorities.add(lookUp);
        }

        return approvalAuthorities;
    }

    private String empFullName(final Object[] emp) {

        final StringJoiner join = new StringJoiner(MainetConstants.WHITE_SPACE);
        join.add((String) emp[1]).add(emp[2] != null ? (String) emp[2] : MainetConstants.operator.EMPTY)
                .add(emp[3] != null ? (String) emp[3] : MainetConstants.operator.EMPTY);

        return join.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public ReceiptReversalViewDTO veiwData(final long primaryKey, final long orgId) {

        return findReceiptViewData(primaryKey, orgId);
    }

    private ReceiptReversalViewDTO findReceiptViewData(final long primaryKey, final long orgId) {

        final ApplicationSession session = ApplicationSession.getInstance();
        final TbServiceReceiptMasEntity entity = accountReceiptEntryJpaRepository.findAllByReceiptId(primaryKey, orgId);
        Objects.requireNonNull(entity,
                session.getMessage("account.voucher.reversal.service.record") + primaryKey + ORGID + orgId);
        final ReceiptReversalViewDTO viewData = new ReceiptReversalViewDTO();
        viewData.setReceiptNo(entity.getRmRcptno());
        viewData.setReceiptDate(Utility.dateToString(entity.getRmDate()));
        viewData.setReceivedFrom(entity.getRmReceivedfrom());
        viewData.setPayeeName(entity.getRmReceivedfrom());
        viewData.setMobileNo(entity.getMobileNumber());
        viewData.setEmailId(entity.getEmailId());
        viewData.setManualReceiptNo(entity.getManualReceiptNo());
        viewData.setNarration(entity.getRmNarration());
        viewData.setDeptId(entity.getDpDeptId());
        Objects.requireNonNull(entity.getReceiptFeeDetail(),
                session.getMessage("account.voucher.reversal.service.receiptno") + primaryKey + ORGID + orgId);
        final List<ReceiptReversalViewDTO> collectionDetails = new ArrayList<>();
        // setting Collection details
        for (final TbSrcptFeesDetEntity det : entity.getReceiptFeeDetail()) {
            final ReceiptReversalViewDTO collectionDetail = new ReceiptReversalViewDTO();
            if ((det.getSacHeadId() != null) && (det.getSacHeadId() != 0)) {
                collectionDetail
                        .setReceiptHead(budgetHeadRepository.findAccountHeadCodeBySacHeadId(det.getSacHeadId(), orgId));
            } else {
                LOGGER.error("No Account Head entry found from TB_RECEIPT_DET [rfFeeid=" + det.getRfFeeid() + "]");
            }

            BigDecimal rfFeeamount = det.getRfFeeamount();
            rfFeeamount = rfFeeamount.setScale(2, RoundingMode.CEILING);
            collectionDetail.setReceiptAmount(rfFeeamount.toString());
            collectionDetails.add(collectionDetail);
        }
        viewData.setCollectionDetails(collectionDetails);
        // setting Collection Modes
        final String modeCode = CommonMasterUtility.findLookUpCode(AccountPrefix.PAY.name(), orgId,
                entity.getReceiptModeDetail().get(0).getCpdFeemode());
        viewData.setModeShortCode(modeCode);
        viewData.setMode(CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.name(), orgId,
                entity.getReceiptModeDetail().get(0).getCpdFeemode()));
        // RT-RTGS, N-NEFT,B-Bank
        if (!MainetConstants.MODE_CREATE.equals(modeCode)) {
            if (MainetConstants.AccountReceiptEntry.RT.equals(modeCode) || MainetConstants.MENU.N.equals(modeCode)
                    || MainetConstants.FlagB.equals(modeCode)) {
                final List<Object[]> bankNamesList = banksMasterService
                        .getBankAccountNames(entity.getReceiptModeDetail().get(0).getBaAccountid());
                Objects.requireNonNull(bankNamesList, session.getMessage("account.voucher.reversal.service.bank.record")
                        + modeCode + MainetConstants.BA_ACCOUNT_ID + entity.getReceiptModeDetail().get(0).getBaAccountid());
                for (final Object[] objects : bankNamesList) {
                    viewData.setBankName(objects[1].toString() + MainetConstants.SEPARATOR + objects[2].toString());
                }
                viewData.setTrnNo(entity.getReceiptModeDetail().get(0).getTranRefNumber());
                viewData.setTrnDate(Utility.dateToString(entity.getReceiptModeDetail().get(0).getTranRefDate()));
            } else {
                viewData.setBankName(
                        banksMasterService.getBankNameByBankId(entity.getReceiptModeDetail().get(0).getCbBankid()));
                viewData.setTrnNo(entity.getReceiptModeDetail().get(0).getRdChequeddno().toString());
                viewData.setTrnDate(Utility.dateToString(entity.getReceiptModeDetail().get(0).getRdChequedddate()));
            }
        }
        viewData.setTotalAmount(entity.getRmAmount().toString());

        return viewData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateVoucherReversal(final List<String> transactionIds, final VoucherReversalDTO dto,
            final long fieldId, final long orgId, int langId, final String ipMacAddress) {
    	AccountInvestmentMasterDto accountInvestmentMasterdto = new AccountInvestmentMasterDto();
    	AccountInvestmentMasterEntity accountInvestmentMasterEntity = new AccountInvestmentMasterEntity();
    	for (final String string : transactionIds) {
            final Long transactionId = Long.parseLong(string);
            accountVoucherReversalRepository.saveOrUpdateReceipt(transactionId, dto, orgId, ipMacAddress);
            if (transactionId != null) {
                Long dpDeptid = deparmentService.getDepartmentIdByDeptCode(AccountConstants.AS.toString());
                Long     recRefId = accountReceiptEntrRrepository.gettingRecRefIdInPropertyTax(transactionId, dpDeptid, orgId);
                if (recRefId != null) {
                    receiptReversalProvisionService.updateReceiptReversalDelFlag(recRefId);
                    // accountReceiptEntrRrepository.updateRecRefIdInPropertyTax(recRefId, orgId);
                }
            }
            if (dto.getTransactionNo() != null) {
            	DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/uuuu" );
            	LocalDate ld = LocalDate.parse( dto.getTransactionDate() , f );
            	Date transactionDate = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
				Long depositId = accountDepositRepository.findAccountDepositEntityByDepReceiptnoAndDepDate(
						dto.getTransactionNo().toString(), orgId, transactionDate);
                if (depositId != null) {
                    String dep_del_flag = MainetConstants.MENU.Y;
                    final Long ReceiptDeleted = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                            PrefixConstants.AccountBillEntry.RD, PrefixConstants.NewWaterServiceConstants.RDC, orgId);
                    accountDepositRepository.updateDep_del_flagOfAccountDepositEntity(depositId, orgId, dep_del_flag,
                            ReceiptDeleted);
            }
            }
            voucherPostingForReceiptReversal(dto,
                    accountReceiptEntryJpaRepository.findAllByReceiptId(transactionId, orgId), fieldId, orgId, langId);
            
        }
    	//#32480
    	//Code modified by rahul.chaubey. Receipt reversal for investment.Changing the status From Closed "C" to Active "A"
        Long receipt_ref_id = accountReceiptEntrRrepository.gettingRecRefIdInPropertyTax(Long.valueOf(dto.getCheckedIds()),Long.valueOf(dto.getDpDeptid()), orgId);
        if(receipt_ref_id != null) {
        	 accountInvestmentMasterEntity =  accountReceiptEntryJpaRepository.findInvestmentbyIdEntity(receipt_ref_id, orgId);
        	 if(accountInvestmentMasterEntity != null) {
        		 BeanUtils.copyProperties(accountInvestmentMasterEntity, accountInvestmentMasterdto);
                 accountInvestmentMasterdto.setStatus(MainetConstants.FlagA);
                 accountInvestmentService.saveInvestMentMaster(accountInvestmentMasterdto);
        	 }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> validateDataForReversal(final List<VoucherReversalDTO> oldData,
            final VoucherReversalDTO requestData, final long orgId) {

        final ApplicationSession session = ApplicationSession.getInstance();
        Objects.requireNonNull(requestData.getCheckedIds(), session.getMessage("account.cheque.cash.record"));
        if (requestData.getCheckedIds().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(session.getMessage("account.cheque.cash.recordgrid"));
        }
        final String[] splitArr = requestData.getCheckedIds().split(MainetConstants.operator.COMMA);
        final StringJoiner joiner = new StringJoiner(MainetConstants.operator.COMMA);
        int count = 0;
        ResponseEntity<?> response = null;
        final List<String> toReverse = new ArrayList<>();
        for (final String checkedId : splitArr) {
            for (final VoucherReversalDTO old : oldData) {
                if (old.getPrimaryKey() == Long.parseLong(checkedId)) {
                    if (MainetConstants.YESL.equals(old.getReversedOrNot())) {
                        joiner.add(checkedId);
                        count++;
                    } else {
                        final Long generatedCount = accountReceiptEntryJpaRepository
                                .countDepositSlipAlreadyGenerated(old.getPrimaryKey(), orgId);
                        if (Objects.isNull(generatedCount) || (generatedCount.longValue() == 0l)) {
                            toReverse.add(checkedId);
                            break;
                        } else {
                            response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body(session.getMessage("account.voucher.reversal.service.depositslip")
                                            + old.getTransactionNo());
                            break;
                        }
                    }
                }
            }
        }

        if (count == 0) {
            // applicable for reverse
            if (!toReverse.isEmpty()) {
                response = ResponseEntity.status(HttpStatus.OK).body(toReverse);
            }
        } else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(session.getMessage("account.cheque.cash.transaction") + joiner.toString()
                            + session.getMessage("account.voucher.reversal.service.reverse"));
        }

        return response;
    }

    private void voucherPostingForReceiptReversal(final VoucherReversalDTO reversalDTO,
            final TbServiceReceiptMasEntity entity, final long fieldId, final long orgId, int langId) {

        final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.REV_SUB_CPD_VALUE,
                AccountPrefix.VOT.toString(), orgId);
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
                    "entryType id not found for for lookUpCode[MAN] from VET Prefix in Receipt Reversal voucher posting.");
        }
        /*
         * final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants. REV_SUB_CPD_VALUE,
         * AccountPrefix.TDP.toString(), orgId);
         */
        final List<AccountVoucherEntryEntity> tbAcVoucherEntryEntity = journalVoucherDao
                .getReceiptReversalVoucherDetails(entity.getRmRcptno().toString(), entity.getRmDate(), voucherTypeId,
                        orgId, entity.getDpDeptId(), vouSubTypes, entryTypeId);

        if ((tbAcVoucherEntryEntity == null) || tbAcVoucherEntryEntity.isEmpty()) {
            throw new NullPointerException("No records found from TB_AC_VOUCHER [receiptNo="
                    + entity.getRmRcptno().toString() + ",receiptEntryDate=" + entity.getRmDate() + ",voucherTypeId="
                    + voucherTypeId + ",orgId=" + orgId + "]");
        }

        for (final AccountVoucherEntryEntity acVoucherEntryEntity : tbAcVoucherEntryEntity) {

            AccountVoucherEntryEntity bean = new AccountVoucherEntryEntity();
            final List<AccountVoucherEntryDetailsEntity> detailsEntity = new ArrayList<>();
            AccountVoucherEntryDetailsEntity postDetailEntityCr = null;
            AccountVoucherEntryDetailsEntity postDetailEntityDr = null;

            // postDto.setVoucherDate(new Date());
            final String voucherType = CommonMasterUtility.findLookUpCode(AccountPrefix.VOT.toString(), orgId,
                    acVoucherEntryEntity.getVouTypeCpdId());

            final Organisation org = new Organisation();
            org.setOrgid(orgId);

            if (voucherType.equals(PrefixConstants.REV_SUB_CPD_VALUE)) {

                final LookUp voucherSubType = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                        PrefixConstants.ChequeDishonour.RRE, PrefixConstants.REV_TYPE_CPD_VALUE, langId, org);
                final Long voucherSubTypeid = voucherSubType.getLookUpId();
                bean.setVouSubtypeCpdId(voucherSubTypeid);
                bean.setVouReferenceNo(entity.getRmRcptno().toString());// rcptno
                bean.setVouDate(entity.getRmDate());
                bean.setNarration(reversalDTO.getNarration());
                bean.setOrg(orgId);
                bean.setCreatedBy(reversalDTO.getApprovedBy());
                bean.setLmodDate(new Date());
                bean.setLgIpMac(Utility.getMacAddress());
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

                String vouNo = generateVoucherNo(PrefixConstants.DirectPaymentEntry.PV, org.getOrgid(), entity.getRmDate());
                bean.setVouNo(vouNo);
                bean.setVouTypeCpdId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                        PrefixConstants.DirectPaymentEntry.PV, AccountPrefix.VOT.toString(), orgId));
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
                LOGGER.info("voucherPostingService - voucherPosting -RV voucherPostingForReceiptReversal:" + bean);
                accountVoucherEntryRepository.save(bean);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean countDepositSlipAlreadyGenerated(final long receiptId, final long orgId) {
        final Long count = accountReceiptEntryJpaRepository.countDepositSlipAlreadyGenerated(receiptId, orgId);
        final boolean status = Objects.isNull(count) ? false : count.longValue() == 0;
        return status ? false : true;
    }

    private String generateVoucherNo(final String voucherType, final long orgId, Date rmDate) {
        Long finYearId = tbFinancialyearService.getFinanciaYearIdByFromDate(rmDate);
        if (finYearId == null) {
            throw new NullPointerException(GENERATE_RECEIPT_NO_FIN_YEAR_ID);
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
	public boolean checkBillReversalApplicable(VoucherReversalDTO voucherReversalDto, String transactionType, Long orgId) {
		
		boolean reverseAppFlag = false;
		TbComparamDetEntity comparamDetEntity = null;
		
		if(StringUtils.equals("RP", transactionType)) {
			
			 comparamDetEntity = accountVoucherReversalRepository.getDepositCpdStatusByRecptId(voucherReversalDto.getPrimaryKey(), orgId);
		}else if(StringUtils.equals("BP", transactionType)) {
			comparamDetEntity = accountVoucherReversalRepository.getDepositCpdStatusByDepRefNo(voucherReversalDto.getTransactionNo(),orgId);
		}
		
		if(comparamDetEntity != null) {
			if(!StringUtils.equals(comparamDetEntity.getCpdValue(), "DO")) {
				reverseAppFlag = true;
			}
		}
		return reverseAppFlag;
	}
	
	@Override
	@Transactional
	public List<String> validateExternalReversalRequest(VoucherReversalExtDTO dto){
		
		 LOGGER.info("Provided input for External System Voucher Posting validation:" + dto);
		  StringBuilder builder = new StringBuilder();
		  List<String> errorMessageList = new ArrayList<>();      
		  Long deptId=null;
     	 Organisation organisation = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class).getActiveOrgByUlbShortCode(dto.getUlbCode());
     	
     	if(StringUtils.isBlank(dto.getUlbCode())) {
        	builder.append("ULB Code, ");
        }else {
 	        if(organisation == null) {
 	        	builder.append("We cant find ULB based on your ULB Short Code. Invalid ULB Code, ");
 	        }else {
 	        	 if(StringUtils.isBlank(dto.getFieldCode())) {
 		        	builder.append("Field Code, ");
 		        }else {
 		        	Long fieldIdByFieldCompositCode = ApplicationContextProvider.getApplicationContext().getBean(AccountFieldMasterService.class).getFieldIdByFieldCompositCode(dto.getFieldCode(), organisation.getOrgid());
 		        	if(fieldIdByFieldCompositCode == null || fieldIdByFieldCompositCode <0) {
 		        		builder.append("We cant find Field id based on your Field Short Code. Invalid Field Code, ");
 		        	}
 		        }
 	        	
 	        }
        }
     	 
     	if(StringUtils.isBlank(dto.getTransactionDate())) {
     		builder.append("Transaction date, ");
     	}
     	
     	if(StringUtils.isBlank(dto.getTransactionType())) {
     		builder.append("Transaction type, ");
     	}
     	 
     	if(StringUtils.isBlank(dto.getCheckSum())) {
     		builder.append("checksum , ");
     	}
     	
     	if(StringUtils.isBlank(dto.getApprovalOrderNo())) {
     		builder.append("approval order no , ");
     	}
     	
     	if(StringUtils.isBlank(dto.getApprovedBy())) {
     		builder.append("ApprovedBy , ");
     	}
     	
     	if(StringUtils.isBlank(dto.getNarration())) {
     		builder.append("Narration , ");
     	}
     	
     	if(StringUtils.isBlank(dto.getDepartmentName())) {
     		builder.append("deparment name , ");
     	}else {
     		 deptId = deparmentService.getDepartmentIdByDeptCode(dto.getDepartmentName());
     		if(deptId==null) {
     			builder.append("deparment name not found, ");
     		}
     	}
     	if(StringUtils.isBlank(dto.getTransactionNo())) {
     		builder.append("Transaction number, ");
     	}else {
     		Long entryTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.EEC.getValue(),
					AccountPrefix.VET.toString(), organisation.getOrgid());
     		Date rmDate = null;
    		try {
    			rmDate = Utility.stringToDate(dto.getTransactionDate());
    		} catch (Exception e) {
    			LOGGER.info("Cannot convert String to date" + e);
    		}
    	    TbServiceReceiptMasEntity receiptEntity = accountReceiptEntrRrepository.getReceiptNoByUniquereferenceNumber(deptId, organisation.getOrgid(), Long.valueOf(dto.getTransactionNo()),entryTypeId,rmDate);
    		  if (receiptEntity != null) {
    	          List<TbSrcptFeesDetEntity> detEntity = receiptEntity.getReceiptFeeDetail();
    	          for (TbSrcptFeesDetEntity detDTO : detEntity) {
    	              if (detDTO.getDepositeSlipId() != null) {
    	            	  if(dto.getTransactionType().equalsIgnoreCase("DSE")) {
    	            		  List<Object[]> depositeSlipRecord = accountBankDepositeSlipMasterJpaRepository.findReceiptEntryDetailsByDepositSlipId(detDTO.getDepositeSlipId(), organisation.getOrgid());
        	            	  if(CollectionUtils.isEmpty(depositeSlipRecord)) {
            	            	  builder.append("Invalid Detail for Deposite Slip Reversal ");
        	            	  } 
    	            	  }else {
        	            	  builder.append("Please Reverse The Depoiste Slip Entry First, To Reverse The Deposite Slip Entry pass transactionType='DSE'  ");
    	            	  }
    	            	 
    	              }
    	          }
    	      }else {
    	    	  builder.append(" Transaction Number, ");  
    	      }
    	    
    	    
     	}
     	
     	if (!builder.toString().isEmpty()) {
            builder.append(" } cannot be null, empty or zero.");
        }
        if (!builder.toString().isEmpty()) {
            errorMessageList.add(builder.toString());
        }
     	
     	return errorMessageList; 
	}
	@Override
	@Transactional
	public VoucherReversalDTO convertExtDtoToInternalDto(VoucherReversalExtDTO extDto) {
	   VoucherReversalDTO dto=new VoucherReversalDTO();
       Organisation organisation = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class).getActiveOrgByUlbShortCode(extDto.getUlbCode());
	   dto.setNarration(extDto.getNarration());
	   dto.setTransactionNo(extDto.getTransactionNo());
	   dto.setTransactionDate(extDto.getTransactionDate());
	   dto.setTransactionType(extDto.getTransactionType());
	   dto.setApprovedBy(Long.valueOf(extDto.getApprovedBy()));
	   dto.setFiledId(ApplicationContextProvider.getApplicationContext().getBean(AccountFieldMasterService.class).getFieldIdByFieldCompositCode(extDto.getFieldCode(), organisation.getOrgid()));
	   dto.setOrgId(organisation.getOrgid());
	   dto.setEntryType(MainetConstants.AccountConstants.EEC.getValue());
	   dto.setApprovalOrderNo(extDto.getApprovalOrderNo());
	   List<LookUp> lookupList = CommonMasterUtility
       .getListLookup(PrefixConstants.AccountPrefix.TOS.toString(), organisation);
	   for(LookUp lookup:lookupList) {
		   if(lookup.getLookUpCode().trim().equalsIgnoreCase(extDto.getTransactionType().trim())) {
			   dto.setTransactionTypeId(lookup.getLookUpId());  
		   }
	   }
	   dto.setDpDeptid(deparmentService.getDepartmentIdByDeptCode(extDto.getDepartmentName()));
	   Long entryTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.EEC.getValue(),
				AccountPrefix.VET.toString(), organisation.getOrgid());
		Date rmDate = null;
		try {
			rmDate = Utility.stringToDate(extDto.getTransactionDate());
		} catch (Exception e) {
			LOGGER.info("Cannot convert String to date" + e);
		}
	  TbServiceReceiptMasEntity receiptEntity = accountReceiptEntrRrepository.getReceiptNoByUniquereferenceNumber(dto.getDpDeptid(), organisation.getOrgid(), Long.valueOf(extDto.getTransactionNo()),entryTypeId,rmDate);
	  if (receiptEntity != null) {
          List<TbSrcptFeesDetEntity> detEntity = receiptEntity.getReceiptFeeDetail();
          for (TbSrcptFeesDetEntity detDTO : detEntity) {
              if (detDTO.getDepositeSlipId() != null) {
            	  dto.setTransactionNo(accountBankDepositeSlipMasterJpaRepository.findOne(detDTO.getDepositeSlipId()).getDepositeSlipNumber());
              }else {
            	  dto.setTransactionNo(receiptEntity.getRmRcptno().toString());
              }
          }
      }
	  
	  return dto;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseEntity<?> saveReverseTransaction(VoucherReversalDTO dto) {
		   ResponseEntity<?> record = findRecordsForReversal(dto, dto.getOrgId(), dto.getDpDeptid());
		   List<VoucherReversalDTO> list=null;
		   
		   if (record.getStatusCode() == HttpStatus.OK) {
               final Object[] responseArray = (Object[]) record.getBody();
               list=(List<VoucherReversalDTO>) responseArray[1];
               StringBuilder checkIds=new StringBuilder();
               list.forEach(primary->{
            	   checkIds.append(primary.getPrimaryKey());
               });
               dto.setCheckedIds(checkIds.toString());
             if(dto.getTransactionType().equalsIgnoreCase("RP")) {
               ResponseEntity<?> responseVlidation = validateDataForReversal(list, dto, dto.getOrgId());
                if(responseVlidation.getStatusCode()==HttpStatus.OK) {
                	 Long billdepositeStatus = getDepositeStatusAgainstBillType(dto.getTransactionNo(),Utility.stringToDate(dto.getTransactionDate()), dto.getOrgId());
                     final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
     						PrefixConstants.AccountBillEntry.DO, PrefixConstants.NewWaterServiceConstants.RDC,
     						dto.getOrgId());
                     if(checkFianacialYearCloseOrNot(dto.getTransactionDate(), dto.getOrgId())) {
                     	 return ResponseEntity.status(HttpStatus.OK).body("Fianacial already year is closed");	
                     }
                    if(billdepositeStatus!=null && !billdepositeStatus.equals(statusId)) {
                 	   return ResponseEntity.status(HttpStatus.OK).body("Invalid Detail For Transaction Reversal");
                 	}
                	saveOrUpdateVoucherReversal((List<String>)responseVlidation.getBody(), dto, dto.getFiledId(),  dto.getOrgId(), 1, "192.168.100.230");
             	   return ResponseEntity.status(HttpStatus.OK).body("Reversal process done successfully");
               }else {
            	   return responseVlidation;
               }
		   }
		   else if(dto.getTransactionType().equalsIgnoreCase("DSE")) {
			   ResponseEntity<?> response = chequeOrCashService.validateDataForDepositSlipReversal(list, dto, dto.getOrgId());
			   if(response.getStatusCode()==HttpStatus.OK) {
				   chequeOrCashService.reverseDepositSlip((List<String[]>)response.getBody(), dto, dto.getFiledId(), dto.getOrgId(),"192.169.100.230");
				   return ResponseEntity.status(HttpStatus.OK).body("Reversal process done successfully");
			   }else {
				   return response;
			   }
		   }
		   }else {
			 return ResponseEntity.status(HttpStatus.OK).body("Invalid Detail For Transaction Reversal");
		   }
		return record;
	}
	
	private Boolean checkFianacialYearCloseOrNot(String date,Long orgId) {
		boolean fiYearHeadClosed = false;
		boolean closeOrNot=false;
			Long finYeadStatus = tbFinancialyearService.checkHardClosedFinYearDateExists(Utility.stringToDate(date),
					orgId);
			if (finYeadStatus == null) {
				//throw new NullPointerException("Fiancial year is not defined");
				closeOrNot=true;
			} else {
				Long finYeadMonthStatus = tbFinancialyearService.checkSoftClosedFinYearDateExists(Utility.stringToDate(date),
						orgId);
				Organisation org = new Organisation();
				org.setOrgid(orgId);
				String fiYearStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadStatus, org)
						.getLookUpCode();
				String fiYearMonthStatusCode = "";
				if (finYeadMonthStatus != null) {
					fiYearMonthStatusCode = CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadMonthStatus, org)
							.getLookUpCode();
				}
				if ((fiYearStatusCode != null && !fiYearStatusCode.isEmpty())) {
					if (fiYearStatusCode.equals(PrefixConstants.LookUp.OPN)) {
						fiYearHeadClosed = true;
					}
				}
			}
		
		if (fiYearHeadClosed == false) {
			//throw new NullPointerException("Fiancial year already closed");
			closeOrNot=true;
		}
		return closeOrNot;
	}
	
	@Override
	public Long getDepositeStatusAgainstBillType(String receiptRefNo,Date ReceiptDate, long orgId) {
		// TODO Auto-generated method stub
		
		return accountDepositRepository.findbillDepositeStatusInDepositeEntity(receiptRefNo,ReceiptDate, orgId);
	}

}
