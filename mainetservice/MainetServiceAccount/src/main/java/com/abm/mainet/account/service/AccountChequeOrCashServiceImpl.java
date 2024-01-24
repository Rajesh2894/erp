package com.abm.mainet.account.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountChequeOrCashDepositeDao;
import com.abm.mainet.account.dao.AccountJournalVoucherEntryDao;
import com.abm.mainet.account.dao.BankReconciliationDao;
import com.abm.mainet.account.domain.AccountBankDepositeSlipDenomEntity;
import com.abm.mainet.account.domain.AccountBankDepositeSlipMasterEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryDetailsEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.domain.VoucherTemplateMasterEntity;
import com.abm.mainet.account.dto.AccountCashDepositeBean;
import com.abm.mainet.account.dto.AccountChequeOrCashDepositSlipDTO;
import com.abm.mainet.account.dto.AccountChequeOrCashDepositeBean;
import com.abm.mainet.account.dto.AccountLedgerMasBean;
import com.abm.mainet.account.dto.DepositSlipReversalViewDTO;
import com.abm.mainet.account.dto.DraweeBankDetailsBean;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.repository.AccountBankDepositeSlipMasterJpaRepository;
import com.abm.mainet.account.repository.AccountReceiptEntryJpaRepository;
import com.abm.mainet.account.repository.AccountReceiptHeadPostingJpaRepository;
import com.abm.mainet.account.repository.AccountVoucherEntryRepository;
import com.abm.mainet.account.repository.VoucherTemplateRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountChequeOrCash;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountJournalVoucherEntry;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.dao.IDepartmentDAO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.dto.TbBankaccount;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.TbBankaccountService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.DepartmentLookUp;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Implementation of TbAcFundMasterService
 */
@Service
@Transactional
public class AccountChequeOrCashServiceImpl implements AccountChequeOrCashDepositeService {

    @Resource
    private AccountChequeOrCashDepositeDao accountChequeOrCashDepositeDao;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    AccountReceiptHeadPostingJpaRepository accountReceiptHeadJpaRepository;

    @Resource
    private AccountVoucherPostService voucherPostingService;

    @Resource
    BudgetCodeService budgetCodeService;

    @Resource
    private AccountContraVoucherEntryService contraVoucherEntryService;

    @Resource
    VoucherTemplateRepository voucherTemplateRepository;

    @Resource
    private TbBankaccountService tbBankaccountService;
    @Resource
    private TbDepartmentService deparmentService;

    @Resource
    private AccountBankDepositeSlipMasterJpaRepository depositSlipRepository;

    @Resource
    AccountJournalVoucherEntryDao journalVoucherDao;
    @Resource
    private AccountReceiptEntryJpaRepository accountReceiptEntryJpaRepository;
    @Resource
    private AccountVoucherEntryRepository accountVoucherEntryRepository;
    @Autowired
    private IDepartmentDAO departmentDAO;
    @Resource
    private TbFinancialyearJpaRepository financialYearJpaRepository;
    @Resource
	private DepartmentService departmentService;
    @Resource
	private AccountFieldMasterService accountFieldMasterService;
    @Resource
    private BankReconciliationDao bankReconciliationDao;

    private static final String SEQUENCE_NO_ERROR = "unable to generate sequenceNo for orgId=";
    private static final String SEQUENCE_NO = "0000000000";

    private static final String TEMPLATE_ID_NOT_FOUND_VOU = "templateTypeId not found for provided parameter[voucherType=";
    private static final String ORG_ID = ", orgId=";
    private static final String PREFIX_VOT = ",prefix=VOT]";

    private static final Logger LOGGER = Logger.getLogger(AccountChequeOrCashServiceImpl.class);

    private static final String GENERATE_VOUCHER_NO_FIN_YEAR_ID = "Voucher sequence number generation, The financial year id is getting null value";
    private static final String GENERATE_DEPOSIT_SLIP_NO_FIN_YEAR_ID = "Deposit Slip sequence number generation, The financial year id is getting null value";
    private static final String GENERATE_VOUCHER_NO_VOU_TYPE_ID = "Voucher sequence number generation, The voucher type id is getting null value";

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountChequeOrCashDepositeService# getReceiptDetails(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<TbServiceReceiptMasBean> getReceiptDetails(final AccountChequeOrCashDepositeBean bean,
            final Organisation org) throws ParseException {
        final List<Object[]> listOfObjArray = accountChequeOrCashDepositeDao.getReceiptDetails(bean);
        List<TbServiceReceiptMasBean> listOfBean = new ArrayList<>();
        listOfBean = getReceiptDetailsObjectToBean(listOfObjArray, org);
        return listOfBean;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbServiceReceiptMasBean> getReceiptDetailsView(final AccountChequeOrCashDepositeBean bean,
            final Organisation org) {
        final List<Object[]> listOfObjArray = accountChequeOrCashDepositeDao.getReceiptDetailsView(bean);
        List<TbServiceReceiptMasBean> listOfBean = new ArrayList<>();
        listOfBean = getReceiptDetailsObjectToBean(listOfObjArray, org);
        return listOfBean;
    }

    /**
     * @param listOfObjArray
     * @param org
     * @return
     */
    private List<TbServiceReceiptMasBean> getReceiptDetailsObjectToBean(final List<Object[]> listOfObjArray,
            final Organisation org) {
        final List<TbServiceReceiptMasBean> listOfBean = new ArrayList<>();
        TbServiceReceiptMasBean bean = null;
        final List<DepartmentLookUp> deparmentList = CommonMasterUtility.getDepartmentForWS(org);
        for (final Object[] objArr : listOfObjArray) {
            bean = new TbServiceReceiptMasBean();
            bean.setRmRcptno((Long) objArr[0]);
            bean.setRmDate((Date) objArr[1]);
            bean.setDpDeptId((Long) objArr[2]);
            for (final DepartmentLookUp dept : deparmentList) {
                if (dept.getLookUpId() == bean.getDpDeptId()) {
                    bean.setDeptName(dept.getLookUpDesc());
                }
            }

            bean.setRmAmount((String) objArr[3]);
            bean.setRmRcptid((Long) objArr[4]);
            listOfBean.add(bean);
        }
        return listOfBean;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountChequeOrCashDepositeService# LedgerDetails(java.util.Date,
     * java.util.Date, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountLedgerMasBean> LedgerDetails(final Date fromDate, final Date toDate, final Long depositeType,
            final Long fundId, final Long fieldId, final Long department, Long orgId, String depSlipType,Long functionId) {

        List<AccountLedgerMasBean> listOfBean = new ArrayList<>();
        if (depSlipType.equals("LWD")) {
            final List<Object[]> listOfObjArray = accountChequeOrCashDepositeDao.getLedgertDetails(fromDate, toDate,
                    depositeType, fundId, fieldId, department, orgId,functionId);
            listOfBean = getLedgerDetailsObjectToBean(listOfObjArray);
        } else if (depSlipType.equals("RWD")) {
            final List<Object[]> listOfObjArray = accountChequeOrCashDepositeDao.getReceiptLedgertDetails(fromDate,
                    toDate, depositeType, fundId, fieldId, department, orgId,functionId);
            listOfBean = getReceiptLedgerDetailsObjectToBean(listOfObjArray);
        }
        return listOfBean;
    }

    private List<AccountLedgerMasBean> getReceiptLedgerDetailsObjectToBean(List<Object[]> listOfObjArray) {
        // TODO Auto-generated method stub

        final List<AccountLedgerMasBean> listOfBean = new ArrayList<>();
        AccountLedgerMasBean bean = null;
        for (final Object[] objArr : listOfObjArray) {
            bean = new AccountLedgerMasBean();
            bean.setReceiptIds(objArr[0].toString());
            bean.setRmDate(Utility.dateToString((Date) objArr[1]));
            bean.setRmRcptno(Long.valueOf(objArr[2].toString()));
            if (objArr[3] != null) {
                bean.setManualReceiptNo(objArr[3].toString());
            }
            if (objArr[4] != null) {
                BigDecimal feeAmount = new BigDecimal(objArr[4].toString());
                feeAmount = feeAmount.setScale(2, RoundingMode.CEILING);
                bean.setFeeAmount(feeAmount.toString());
            }
            
            if(objArr.length>5 && objArr[5]!=null) {
            	bean.setDeptId(Long.valueOf(objArr[5].toString()));
            }
            listOfBean.add(bean);
        }
        return listOfBean;
    }

    /**
     * @param listOfObjArray
     * @return
     */
    private List<AccountLedgerMasBean> getLedgerDetailsObjectToBean(final List<Object[]> listOfObjArray) {
        final List<AccountLedgerMasBean> listOfBean = new ArrayList<>();
        AccountLedgerMasBean bean = null;
        for (final Object[] objArr : listOfObjArray) {
            final AccountHeadSecondaryAccountCodeMasterEntity budget = (AccountHeadSecondaryAccountCodeMasterEntity) objArr[2];
            if (budget != null) {
                bean = new AccountLedgerMasBean();
                final AccountHeadPrimaryAccountCodeMasterEntity primaryCode = budget.getTbAcPrimaryheadMaster();
                bean.setPrimary(primaryCode.getPrimaryAcHeadCompcode() + MainetConstants.AccountChequeOrCash.ARROW
                        + primaryCode.getPrimaryAcHeadDesc());

                bean.getSecondaryMap().put(budget.getSacHeadId(), budget.getAcHeadCode());

                bean.setSecondary(
                        budget.getSacHeadCode() + MainetConstants.AccountChequeOrCash.ARROW + budget.getSacHeadDesc());
                BigDecimal amount = new BigDecimal(objArr[0].toString()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                bean.setFeeAmount(amount.toString());
                bean.setReceiptIds(objArr[1].toString());
                bean.getSecondaryMap().put(budget.getSacHeadId(), budget.getAcHeadCode());
                listOfBean.add(bean);
            }
        }
        return listOfBean;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountLedgerMasBean> LedgerDetailsView(final AccountChequeOrCashDepositeBean bean) {
        List<AccountLedgerMasBean> listOfBean = new ArrayList<>();

        if (bean.getDepositTypeFlag().equals("R")) {
            final List<Object[]> listOfObjArray = accountChequeOrCashDepositeDao.getReceiptLedgertDetailsView(bean);
            listOfBean = getReceiptLedgerDetailsObjectToBean(listOfObjArray);
        } else {
            final List<Object[]> listOfObjArray = accountChequeOrCashDepositeDao.getLedgertDetailsView(bean);
            listOfBean = getLedgerDetailsObjectToBean(listOfObjArray);
        }
        return listOfBean;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountChequeOrCashDepositeService# getSavedReceiptDetails(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountChequeOrCashDepositeBean> getSavedReceiptDetails(final String fromDate, final String toDate,
            final String feeMode, final Long orgId, final String account, final String slipNumber)
            throws ParseException {
        final List<AccountBankDepositeSlipMasterEntity> listOfDeositeSlipMasEntity = accountChequeOrCashDepositeDao
                .getSavedReceiptDetails(fromDate, toDate, feeMode, orgId, account, slipNumber);
        final List<AccountChequeOrCashDepositeBean> listOfBean = new ArrayList<>();
        AccountChequeOrCashDepositeBean bean = null;
        for (final AccountBankDepositeSlipMasterEntity entity : listOfDeositeSlipMasEntity) {
            bean = new AccountChequeOrCashDepositeBean();
            bean.setDepositeSlipId(entity.getDepositeSlipId());
            bean.setDepositeSlipNo(entity.getDepositeSlipNumber().toString());
            bean.setDepositeSlipDate(CommonUtility.dateToString(entity.getDepositeSlipDate()));
            bean.setAmount(entity.getDepositeAmount());
            if ((entity.getFieldId() != null) && (entity.getFieldId().getFieldCompcode() != null)
                    && ((entity.getFieldId().getFieldDesc() != null)
                            && !entity.getFieldId().getFieldDesc().isEmpty())) {
                bean.setFieldIdWithDesc(entity.getFieldId().getFieldCompcode() + MainetConstants.CommonConstants.BLANK
                        + entity.getFieldId().getFieldDesc());
            }
            if (entity.getDepositeTypeFlag() != null) {
                bean.setDepositeType(CommonMasterUtility.findLookUpCodeDesc(MainetConstants.CHEQUE_DISHONOUR.PAY,
                        entity.getOrgid(), entity.getDepositeTypeFlag()));
            }
            if (entity.getDepositeBAAccountId() != null) {
                bean.setBaAccountid(entity.getDepositeBAAccountId());
                final Long banKAcId = entity.getDepositeBAAccountId();
                final TbBankaccount bankName = tbBankaccountService.findByBankId(banKAcId);
                if (((bankName.getBaAccountname() != null) && !bankName.getBaAccountname().isEmpty())) {
                    bean.setBaAccountName(bankName.getBaAccountname());
                }
            }
            listOfBean.add(bean);
        }
        return listOfBean;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountChequeOrCashDepositeService#
     * saveRecords(com.abm.mainetservice.account.bean. AccountChequeOrCashDepositeBean)
     */
    @Override
    @Transactional
    public AccountChequeOrCashDepositeBean saveRecords(final AccountChequeOrCashDepositeBean bean, final Long orgId,
            final int langId, final Long empId, final String ipMacAddress) throws Exception {
        AccountBankDepositeSlipDenomEntity depositeSlipDenomEntity = null;
        final List<AccountBankDepositeSlipDenomEntity> listOfDenomEntity = new ArrayList<>();
        if ((bean.getRecptIntgFlagId() != null) && !bean.getRecptIntgFlagId().isEmpty()) {
            bean.setFieldId(bean.getFieldId());
        } else {
            bean.setFieldId(bean.getFieldId());
        }
        AccountBankDepositeSlipMasterEntity depositeSlipMasterEntity = new AccountBankDepositeSlipMasterEntity();

        if (bean.getCashDep() != null) {
            for (final AccountCashDepositeBean cashDepBean : bean.getCashDep()) {

                if (cashDepBean.getDenomination() != null) {
                    depositeSlipDenomEntity = new AccountBankDepositeSlipDenomEntity();
                    depositeSlipDenomEntity.setCpdDenomId(cashDepBean.getTbComparamDet());
                    depositeSlipDenomEntity.setDenominationCount(cashDepBean.getDenomination());
                    depositeSlipDenomEntity.setDepositeSlipId(depositeSlipMasterEntity);
                    depositeSlipDenomEntity = (AccountBankDepositeSlipDenomEntity) setCommonParametersForEntities(
                            depositeSlipDenomEntity, orgId, langId, empId, ipMacAddress);
                    listOfDenomEntity.add(depositeSlipDenomEntity);
                }
            }
        }
        final SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        if ((bean.getDepositeSlipDate() != null) && !bean.getDepositeSlipDate().isEmpty()) {
            
            depositeSlipMasterEntity.setDepositeSlipDate(formatter.parse(bean.getDepositeSlipDate()));
        }
        // depositeSlipMasterEntity.setDepositeDate(new Date());
        depositeSlipMasterEntity.setDepositeFromDate(bean.getFromDate());
        depositeSlipMasterEntity.setDepositeToDate(bean.getToDate());
        depositeSlipMasterEntity.setDepositeTypeFlag(bean.getDepositeType());
        if( bean.getRmDate()!=null)
        	depositeSlipMasterEntity.setDpsDepositDate(Utility.stringToDate(bean.getRmDate()));
        else
        	depositeSlipMasterEntity.setDpsDepositDate(bean.getFromDate());	
        if ((bean.getDepositSlipRemark() != null) && !bean.getDepositSlipRemark().isEmpty()) {
            depositeSlipMasterEntity.setDepositeRemark(bean.getDepositSlipRemark());
        }
        depositeSlipMasterEntity.setDepositeBAAccountId(bean.getBaAccountid());
        final Department dept = new Department();
        dept.setDpDeptid(deparmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString()));
        depositeSlipMasterEntity.setDeptId(dept);
        if ((bean.getFundId() != null) && (bean.getFundId() > 0)) {
            final AccountFundMasterEntity fundMaster = new AccountFundMasterEntity();
            fundMaster.setFundId(bean.getFundId());
            depositeSlipMasterEntity.setFundId(fundMaster);
        }
        if ((bean.getFieldId() != null) && (bean.getFieldId() > 0)) {
            final AccountFieldMasterEntity fieldMaster = new AccountFieldMasterEntity();
            fieldMaster.setFieldId(bean.getFieldId());
            depositeSlipMasterEntity.setFieldId(fieldMaster);
        }
        if (bean.getTotal() == null) {
            bean.setTotal(bean.getDepositSlipAmount());
        }
        depositeSlipMasterEntity.setDepositeAmount(bean.getTotal());

        depositeSlipMasterEntity.setDepositeAuthFlag(MainetConstants.CommonConstants.BLANK);
        depositeSlipMasterEntity = (AccountBankDepositeSlipMasterEntity) setCommonParametersForEntities(
                depositeSlipMasterEntity, orgId, langId, empId, ipMacAddress);
        depositeSlipMasterEntity.setDenominationEntityList(listOfDenomEntity);

        String depSlipType = CommonMasterUtility.findLookUpCode(PrefixConstants.AccountPrefix.CFD.toString(), orgId,
                bean.getDepositSlipType());
        char newDepSlipType = depSlipType.charAt(0);
        depositeSlipMasterEntity.setDepositTypeFlag(newDepSlipType);
        String depDate=null;
       if(bean.getDepositeSlipDate()!=null) {
    	   depDate=bean.getDepositeSlipDate();
       }
       else {
    	   depDate= bean.getDepositDateCheque();
    	   bean.setDepositeSlipDate(depDate);
    	   depositeSlipMasterEntity.setDepositeSlipDate(formatter.parse(depDate));
       }
        Long finYearId = financialYearJpaRepository
                .getFinanciaYearIdByFromDate(Utility.stringToDate(depDate));
        if (finYearId == null) {
            throw new NullPointerException(GENERATE_DEPOSIT_SLIP_NO_FIN_YEAR_ID);
        }

        // bank deposit slip voucher posting validate to given VoucherPostDTO
        bankDepositSlipVoucherPostingValidation(bean, orgId, empId, langId, ipMacAddress);
        String getSequenceAsString=null;
        SequenceConfigMasterDTO configMasterDTO = null;
        Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.AccountConstants.AC.getValue(),
                PrefixConstants.STATUS_ACTIVE_PREFIX);
        configMasterDTO = seqGenFunctionUtility.loadSequenceData(orgId, deptId,
        		MainetConstants.AccountChequeOrCash.TB_ACC_DEPOSITSLIP, MainetConstants.AccountChequeOrCash.DPS_SLIP_NO);
        if (configMasterDTO.getSeqConfigId() == null) {
        	  final Long getSequence = seqGenFunctionUtility.generateSequenceNo(MainetConstants.RECEIPT_MASTER.Module,
                      MainetConstants.AccountChequeOrCash.TB_ACC_DEPOSITSLIP, MainetConstants.AccountChequeOrCash.DPS_SLIP_NO,
                      orgId, MainetConstants.SECONDARY_MASTER.SECONDARY_SEQ_CONTINUE_VALUE, finYearId);
        	  getSequenceAsString=getSequence.toString();
        }else {
       	 CommonSequenceConfigDto commonDto = new CommonSequenceConfigDto();
       	 AccountFieldMasterBean fieldMaster = accountFieldMasterService.findById(bean.getFieldId());
       	 if(fieldMaster!=null) {
       		 commonDto.setCustomField(fieldMaster.getFieldDesc());
       	 }
       	getSequenceAsString=seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonDto);
       }
        depositeSlipMasterEntity.setDepositeSlipNumber(getSequenceAsString.toString());
        bean.setDepositeSlipNo(getSequenceAsString.toString());
        final AccountBankDepositeSlipMasterEntity depositeSlipMasEntity = accountChequeOrCashDepositeDao
                .saveDepositeSlipMasterEntity(depositeSlipMasterEntity);
        if ((bean.getRecptIntgFlagId() != null) && !bean.getRecptIntgFlagId().isEmpty()) {
            bean.setDepositeSlipId(depositeSlipMasEntity.getDepositeSlipId());
        }
        LOGGER.info("postContraEntry - postContraEntry :" + bean + orgId + empId + langId);
        postContraEntry(bean, orgId, empId, langId, ipMacAddress);
        updateReceiptDetails(depositeSlipMasEntity, bean);
        updateReceiptMode(depositeSlipMasEntity, bean);
        bean.setDepositeSlipNo(depositeSlipMasEntity.getDepositeSlipNumber().toString());
		
        return bean;

    }

   

	private void bankDepositSlipVoucherPostingValidation(AccountChequeOrCashDepositeBean bean, Long orgId, Long empId,
            int langId, String ipMacAddress) {
        LOGGER.info("Process for bank deposit slip account voucher posting validation:" + bean + orgId + empId
                + ipMacAddress);

        final VoucherPostDTO postDto = new VoucherPostDTO();
        List<VoucherPostDTO> postDtoList = new ArrayList<>();
        final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
        VoucherPostDetailDTO postDetailDtoCr = null;
        VoucherPostDetailDTO postDetailDtoDr = null;

        Organisation org = new Organisation();
        org.setOrgid(orgId);
        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ContraVoucherEntry.CV, AccountPrefix.AUT.toString(), langId, org);
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            // postDto.setVoucherDate(Utility.stringToDate(bean.getDepositeSlipDate()));
            postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
        } else {
            // postDto.setVoucherDate(bean.getClearingDate());
        }

        // postDto.setVoucherDate(new Date());
        postDto.setVoucherDate(Utility.stringToDate(bean.getDepositeSlipDate()));

        final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                PrefixConstants.ChequeDishonour.DS, PrefixConstants.REV_TYPE_CPD_VALUE, orgId);
        postDto.setVoucherSubTypeId(voucherSubTypeId);

        postDto.setDepartmentId(departmentDAO.getDepartmentIdByDeptCode(MainetConstants.RECEIPT_MASTER.Module,
                PrefixConstants.STATUS_ACTIVE_PREFIX));

        postDto.setNarration(MainetConstants.AccountChequeOrCash.DEPOSIT_SLIP_ENTRY);
        postDto.setFieldId(bean.getFieldId());
        postDto.setOrgId(orgId);
        postDto.setCreatedBy(empId);
        postDto.setCreatedDate(new Date());
        postDto.setLangId(langId);
        postDto.setLgIpMac(ipMacAddress);
        postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

        // Credit side
        postDetailDtoCr = new VoucherPostDetailDTO();
        postDetailDtoCr.setVoucherAmount(bean.getTotal());
        final Long depositTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(bean.getDepositeType(),
                MainetConstants.CHEQUE_DISHONOUR.PAY, orgId);
        LOGGER.info("getting PaymentMode Data -:" + depositTypeId + orgId);
        postDetailDtoCr.setPayModeId(depositTypeId); // paymentMode
        voucherDetails.add(postDetailDtoCr);

        // Debit side
        postDetailDtoDr = new VoucherPostDetailDTO();
        postDetailDtoDr.setVoucherAmount(bean.getTotal());
        LOGGER.info("getting budgetCodeIdDr Data - budgetCodeService getBankBudgetCodeIdByAccountId :"
                + bean.getBaAccountid() + orgId);
        final Long budgetCodeIdDr = budgetCodeService.getBankBudgetCodeIdByAccountId(bean.getBaAccountid(), orgId);
        postDetailDtoDr.setSacHeadId(budgetCodeIdDr); // Budget Head

        voucherDetails.add(postDetailDtoDr);
        postDto.setVoucherDetails(voucherDetails);
        postDtoList.add(postDto);

        LOGGER.info("voucherPostingService - voucherPosting validation :" + postDto);
        List<String> responseValidation = voucherPostingService.checkVoucherPostValidateInput(postDtoList);
        if (responseValidation.size() > 0) {
            throw new NullPointerException(
                    "improper input parameter for VoucherPostDTO in bank depost slip entry -> " + responseValidation);
        }
    }

    public Object setCommonParametersForEntities(final Object entityObject, final Long orgId, final int langId,
            final Long empId, final String ipMacAddress) throws Exception {
        final Method methodForOrgId = entityObject.getClass().getMethod(MainetConstants.AccountChequeOrCash.SET_ORG_ID,
                new Class[] { Long.class });
        final Method methodForCreatedBy = entityObject.getClass().getMethod(AccountChequeOrCash.SET_CREATED_BY,
                new Class[] { Long.class });
        final Method methodForCreatedDate = entityObject.getClass().getMethod(AccountChequeOrCash.SET_CREATED_DATE,
                new Class[] { Date.class });
        final Method methodForUpdatedBy = entityObject.getClass().getMethod(AccountChequeOrCash.SET_UPDATED_BY,
                new Class[] { Long.class });
        final Method methodForUpdatedDate = entityObject.getClass().getMethod(AccountChequeOrCash.SET_UPDATED_DATE,
                new Class[] { Date.class });
        final Method methodLGIPMac = entityObject.getClass().getMethod(AccountChequeOrCash.SET_LG_IP_MAC,
                new Class[] { String.class });

        methodForOrgId.invoke(entityObject, new Object[] { orgId });
        methodForCreatedBy.invoke(entityObject, new Object[] { empId });
        methodForCreatedDate.invoke(entityObject, new Object[] { new Date() });
        methodForUpdatedBy.invoke(entityObject, new Object[] { empId });
        methodForUpdatedDate.invoke(entityObject, new Object[] { new Date() });
        methodLGIPMac.invoke(entityObject, new Object[] { ipMacAddress });

        return entityObject;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountChequeOrCashDepositeService# getSlipDetailsUsingDepSlipId(java.lang.Long)
     */
    @Override
    @Transactional
    public AccountChequeOrCashDepositeBean getSlipDetailsUsingDepSlipId(final Long depositeSlipId) {
        final AccountBankDepositeSlipMasterEntity entity = accountChequeOrCashDepositeDao
                .saveDenominationEntity(depositeSlipId);
        final AccountChequeOrCashDepositeBean bean = new AccountChequeOrCashDepositeBean();
        bean.setDepositeSlipId(entity.getDepositeSlipId());
        bean.setDepositeSlipNo(entity.getDepositeSlipNumber().toString());
        bean.setDepositeSlipDate(CommonUtility.dateToString(entity.getDepositeSlipDate()));
        bean.setDepositeAmount(entity.getDepositeAmount());
        bean.setCoTypeFlag(entity.getCoTypeFlag());
        if (entity.getDepositeTypeFlag() != null) {
            bean.setDepositeType(entity.getDepositeTypeFlag());
        }
        bean.setBaAccountid(entity.getDepositeBAAccountId());
        bean.setBankId(entity.getDepositeBAAccountId());
        bean.setDepositSlipRemark(entity.getDepositeRemark());
        if (entity.getFieldId() != null) {
            bean.setFieldIdWithDesc(entity.getFieldId().getFieldCompcode() + MainetConstants.AccountChequeOrCash.ARROW
                    + entity.getFieldId().getFieldDesc());
            bean.setFieldId(entity.getFieldId().getFieldId());
        }
        if (entity.getFundId() != null) {
            bean.setFundIdWithDesc(entity.getFundId().getFundCompositecode() + MainetConstants.AccountChequeOrCash.ARROW
                    + entity.getFundId().getFundDesc());
            bean.setFundId(entity.getFundId().getFundId());
        }
        bean.setSfromDate(CommonUtility.dateToString(entity.getDepositeFromDate()));
        bean.setStoDate(CommonUtility.dateToString(entity.getDepositeToDate()));
        if (entity.getDepositeTypeFlag() != null) {
            bean.setSfeeMode(String.valueOf(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    entity.getDepositeTypeFlag(), MainetConstants.CHEQUE_DISHONOUR.PAY,
                    UserSession.getCurrent().getOrganisation().getOrgid())));
        }
        if (entity.getDeptId() != null) {
            bean.setDepartment(entity.getDeptId().getDpDeptid());
        }
        if (entity.getDepositTypeFlag() != null) {
            bean.setDepositTypeFlag(Character.toString(entity.getDepositTypeFlag()));
        }
        AccountCashDepositeBean cashBean = null;
        final List<AccountCashDepositeBean> listOfCash = new ArrayList<>();
        for (final AccountBankDepositeSlipDenomEntity denomination : entity.getDenominationEntityList()) {
            cashBean = new AccountCashDepositeBean();
            cashBean.setDenomination(denomination.getDenominationCount().longValue());
            cashBean.setDenomDesc(
                    CommonMasterUtility.getNonHierarchicalLookUpObject(denomination.getCpdDenomId()).getLookUpCode());
            listOfCash.add(cashBean);
        }
        bean.setCashDep(listOfCash);
        return bean;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountChequeOrCashDepositeService# getDraweeBankDetails(java.util.Date,
     * java.util.Date, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<DraweeBankDetailsBean> getDraweeBankDetails(final Date fromDate, final Date toDate, final Long orgId,
            final Long fundId, final Long fieldId) {
        final List<DraweeBankDetailsBean> listOfDraweeBank = new ArrayList<>();
        DraweeBankDetailsBean draweeBank = null;
        final List<Object[]> listOfObjArr = accountChequeOrCashDepositeDao.getDraweeBankDetails(fromDate, toDate, orgId,
                fundId, fieldId);
        for (final Object[] objArr : listOfObjArr) {
            draweeBank = new DraweeBankDetailsBean();
            draweeBank.setBankId((Long) objArr[0]);
            draweeBank.setBankName((String) objArr[1]);
            listOfDraweeBank.add(draweeBank);
        }
        return listOfDraweeBank;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TbServiceReceiptMasBean> getDraweeBankDetailsView(final Long depositSlipId, final Long orgId,
            final Character typeFlag, final String depositeType) {

        final List<Object[]> listOfObjArr = accountChequeOrCashDepositeDao.getDraweeBankDetailsView(depositSlipId,
                orgId, typeFlag, depositeType);
        final List<TbServiceReceiptMasBean> listOfServiceMasBean = new ArrayList<>();
        TbServiceReceiptMasBean bean = null;
        for (final Object[] objArr : listOfObjArr) {
            bean = new TbServiceReceiptMasBean();
            if (typeFlag == null) {
                bean.setRmRcptno((Long) objArr[0]);
                if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)){
                bean.setRmReceiptNo(objArr[0].toString());
                }
                bean.setRmDate((Date) objArr[1]);
                if (objArr[2] != null) {
                    bean.setDeptName((String) objArr[2]);
                }

                bean.setInstrumentType((String) objArr[3]);
                if (depositeType.equals(MainetConstants.AccountReceiptEntry.RT)
                        || depositeType.equals(MainetConstants.MENU.N) || depositeType.equals(MainetConstants.FlagB)) {
                    if (objArr[4] != null) {
                        bean.setPayOrderNo(objArr[4].toString());
                    }
                } else {
                    if (objArr[4] != null) {
                        bean.setPayOrderNo((objArr[4].toString()));
                    }
                }
                bean.setPayOrderDt((Date) objArr[5]);
                bean.setDrawnOnBank((String) objArr[6]);
                bean.setClearingDt((Date) objArr[7]);
                bean.setRmAmount((String) objArr[8].toString());
                bean.setModeId((Long) objArr[9]);
                bean.setRmRcptid((Long) objArr[10]);
                bean.setBranch((String) objArr[11]);
            } else if (!typeFlag.equals(MainetConstants.AccountContraVoucherEntry.T)
                    && !typeFlag.equals(MainetConstants.AccountContraVoucherEntry.W)
                    && !typeFlag.equals(MainetConstants.AccountContraVoucherEntry.D)) {
                bean.setRmRcptno((Long) objArr[0]);
                bean.setRmDate((Date) objArr[1]);
                if (objArr[2] != null) {
                    bean.setDeptName((String) objArr[2]);
                }

                bean.setInstrumentType((String) objArr[3]);
                if (depositeType.equals(MainetConstants.AccountReceiptEntry.RT)
                        || depositeType.equals(MainetConstants.MENU.N) || depositeType.equals(MainetConstants.FlagB)) {
                    if (objArr[4] != null) {
                        bean.setPayOrderNo(objArr[4].toString());
                    }
                } else {
                    if (objArr[4] != null) {
                        bean.setPayOrderNo((objArr[4].toString()));
                    }
                }
                bean.setPayOrderDt((Date) objArr[5]);
                bean.setDrawnOnBank((String) objArr[6]);
                bean.setClearingDt((Date) objArr[7]);
                bean.setRmAmount((String) objArr[8].toString());
                bean.setModeId((Long) objArr[9]);
                bean.setRmRcptid((Long) objArr[10]);
                bean.setBranch((String) objArr[11]);
            } else {
                bean.setPayOrderNo(objArr[0].toString());
                bean.setPayOrderDt((Date) objArr[2]);
                bean.setRmAmount((String) objArr[1].toString());
                bean.setRmRcptno((Long.valueOf(objArr[3].toString())));
                bean.setRmDate((Date) objArr[4]);
                Department deptData = deparmentService.findDepartmentByCode(MainetConstants.RECEIPT_MASTER.Module);
                bean.setDeptName(deptData.getDpDeptdesc());

            }
            listOfServiceMasBean.add(bean);
        }
        return listOfServiceMasBean;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountChequeOrCashDepositeService# getChequeOrDDDetails(java.util.Date,
     * java.util.Date, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<TbServiceReceiptMasBean> getChequeOrDDDetails(Date fromDate, Date toDate, final Long mode,
            final Long fieldId, Long orgId,Date rmDate,Long deptId,Long functionId) {
        if (fromDate == null && toDate == null) {
            Date date = new Date();
            //fromDate = date;
            //toDate = date;
        }
        final List<Object[]> listOfObjArr = accountChequeOrCashDepositeDao.getChequeOrDDDetails(fromDate, toDate, mode,
                fieldId, orgId,rmDate,deptId,functionId);
        final List<TbServiceReceiptMasBean> listOfServiceMasBean = new ArrayList<>();
        BigDecimal rmAmt = null;
        TbServiceReceiptMasBean bean = null;
        for (final Object[] objArr : listOfObjArr) {
            bean = new TbServiceReceiptMasBean();
            bean.setRmRcptno((Long) objArr[0]);
            bean.setRmDate((Date) objArr[1]);
            if (objArr[2] != null) {
                bean.setDeptName((String) objArr[2]);
            }
            //D#154713
            if(objArr[3]!=null)
            bean.setInstrumentType((String) objArr[3]);
            if(objArr[4]!=null)
            bean.setPayOrderNo(objArr[4].toString());
            if(objArr[5]!=null)
            bean.setPayOrderDt((Date) objArr[5]);
            if(objArr[6]!=null)
            bean.setDrawnOnBank((String) objArr[6]);
            if(objArr[7]!=null)
            bean.setClearingDt((Date) objArr[7]);
            if(objArr[8]!=null) {
            rmAmt = new BigDecimal(objArr[8].toString());
            rmAmt = rmAmt.setScale(2, RoundingMode.CEILING);
            bean.setRmAmount(rmAmt.toString());
            }
            if(objArr[9]!=null)
            bean.setModeId((Long) objArr[9]);
            if(objArr[10]!=null)
            bean.setRmRcptid((Long) objArr[10]);
            if(objArr[11]!=null)
            bean.setBranch((String) objArr[11]);

            listOfServiceMasBean.add(bean);
        }
        return listOfServiceMasBean;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountChequeOrCashDepositeService#
     * updateReceiptDetails(com.abm.mainetservice.account .entity.AccountBankDepositeSlipMasterEntity,
     * com.abm.mainetservice.account.bean.AccountChequeOrCashDepositeBean)
     */
    @Override
    @Transactional
    public void updateReceiptDetails(final AccountBankDepositeSlipMasterEntity master,
            final AccountChequeOrCashDepositeBean bean) {
        if (bean.getDepositeType().equals(MainetConstants.Complaint.MODE_CREATE)) {

            for (final AccountLedgerMasBean receiptBean : bean.getListOfLedgerDetails()) {
                if (receiptBean.getSelectDs() != null) {
                    final String[] receiptId = receiptBean.getReceiptIds().split(MainetConstants.operator.COMMA);
                    for (final String feeId : receiptId) {
                        if (!feeId.equals(MainetConstants.CommonConstants.BLANK)) {

                            String depSlipType = CommonMasterUtility.findLookUpCode(
                                    PrefixConstants.AccountPrefix.CFD.toString(), master.getOrgid(),
                                    bean.getDepositSlipType());
                            if (depSlipType.equals("RWD")) {
                                TbServiceReceiptMasEntity masEntiryLsit = accountReceiptHeadJpaRepository
                                        .getSrcptReciptMasDeatilsById(Long.valueOf(feeId));
                                if (masEntiryLsit != null) {
                                    List<TbSrcptFeesDetEntity> detDetailsList = masEntiryLsit.getReceiptFeeDetail();
                                    for (TbSrcptFeesDetEntity tbSrcptFeesDetEntity : detDetailsList) {
                                        accountReceiptHeadJpaRepository.updateLedgerWiseDepositId(
                                                tbSrcptFeesDetEntity.getRfFeeid(), master.getDepositeSlipId());
                                    }
                                }
                            } else if (depSlipType.equals("LWD")) {
                                accountReceiptHeadJpaRepository.updateLedgerWiseDepositId(Long.valueOf(feeId),
                                        master.getDepositeSlipId());
                            }
                        }
                    }

                }
            }

        }

        else {
            for (final TbServiceReceiptMasBean receiptBean : bean.getListOfChequeDDPoDetails()) {
                if (receiptBean.getSelectDs() != null) {
                    accountReceiptHeadJpaRepository.updateReceiptWiseDepositId(receiptBean.getRmRcptid(),
                            master.getDepositeSlipId());
                }

            }

        }
    }

    private void postContraEntry(final AccountChequeOrCashDepositeBean bean, final Long orgId, final Long empId,
            final int langId, final String ipMacAddress) {

        final VoucherPostDTO postDto = new VoucherPostDTO();
        List<VoucherPostDTO> postDtoList = new ArrayList<>();
        final List<VoucherPostDetailDTO> voucherDetails = new ArrayList<>();
        VoucherPostDetailDTO postDetailDtoCr = null;
        VoucherPostDetailDTO postDetailDtoDr = null;

        Organisation org = new Organisation();
        org.setOrgid(orgId);
        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ContraVoucherEntry.CV, AccountPrefix.AUT.toString(), langId, org);
        if (payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty()) {
            // postDto.setVoucherDate(Utility.stringToDate(bean.getDepositeSlipDate()));
            postDto.setPayEntryMakerFlag(MainetConstants.Y_FLAG);
        } else {
            // postDto.setVoucherDate(bean.getClearingDate());
        }

        // postDto.setVoucherDate(new Date());
        postDto.setVoucherDate(Utility.stringToDate(bean.getDepositeSlipDate()));

        final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                PrefixConstants.ChequeDishonour.DS, PrefixConstants.REV_TYPE_CPD_VALUE, orgId);
        postDto.setVoucherSubTypeId(voucherSubTypeId);

        postDto.setDepartmentId(departmentDAO.getDepartmentIdByDeptCode(MainetConstants.RECEIPT_MASTER.Module,
                PrefixConstants.STATUS_ACTIVE_PREFIX));
        /*
         * postDto.setDepartmentId( CommonMasterUtility.getDeptIdFromDeptCode(MainetConstants.RECEIPT_MASTER.
         * Module).getLookUpId());
         */
        postDto.setVoucherReferenceNo(bean.getDepositeSlipNo());

        postDto.setNarration(MainetConstants.AccountChequeOrCash.DEPOSIT_SLIP_ENTRY);
        postDto.setFieldId(bean.getFieldId());
        postDto.setOrgId(orgId);
        postDto.setCreatedBy(empId);
        postDto.setCreatedDate(new Date());
        postDto.setLangId(langId);
        postDto.setLgIpMac(ipMacAddress);
        postDto.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);

        // Credit side
        postDetailDtoCr = new VoucherPostDetailDTO();
        postDetailDtoCr.setVoucherAmount(bean.getTotal());
        final Long depositTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(bean.getDepositeType(),
                MainetConstants.CHEQUE_DISHONOUR.PAY, orgId);
        LOGGER.info("getting PaymentMode Data -:" + depositTypeId + orgId);
        postDetailDtoCr.setPayModeId(depositTypeId); // paymentMode
        voucherDetails.add(postDetailDtoCr);

        // Debit side
        postDetailDtoDr = new VoucherPostDetailDTO();
        postDetailDtoDr.setVoucherAmount(bean.getTotal());
        LOGGER.info("getting budgetCodeIdDr Data - budgetCodeService getBankBudgetCodeIdByAccountId :"
                + bean.getBaAccountid() + orgId);
        final Long budgetCodeIdDr = budgetCodeService.getBankBudgetCodeIdByAccountId(bean.getBaAccountid(), orgId);
        postDetailDtoDr.setSacHeadId(budgetCodeIdDr); // Budget Head

        voucherDetails.add(postDetailDtoDr);
        postDto.setVoucherDetails(voucherDetails);

        LOGGER.info("voucherPostingService - voucherPosting :" + postDto);
        ApplicationSession session = ApplicationSession.getInstance();
        postDtoList.add(postDto);
        String responseValidation = voucherPostingService.validateInput(postDtoList);
        AccountVoucherEntryEntity response = null;
        if (responseValidation.isEmpty()) {
            response = voucherPostingService.voucherPosting(postDtoList);
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
    public DepositSlipReversalViewDTO viewData(final String transactionType, final long primaryKey, final long orgId) {

        final AccountChequeOrCashDepositeBean depositeBean = getSlipDetailsUsingDepSlipId(primaryKey);

        final DepositSlipReversalViewDTO viewData = new DepositSlipReversalViewDTO();
        viewData.setSlipNo(Long.parseLong(depositeBean.getDepositeSlipNo()));
        viewData.setSlipDate(depositeBean.getDepositeSlipDate());
        viewData.setDepositMode(depositeBean.getSfeeMode());

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reverseDepositSlip(final List<String[]> transactionIds, final VoucherReversalDTO dto,
            final long fieldId, final long orgId, final String ipMacAddress) {
    	Long checkStatus=null;
    	try {
			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.RND,
					PrefixConstants.LookUpPrefix.CLR, new Organisation(orgId));
			checkStatus=lookUp.getLookUpId();
			}catch(Exception ex) {
				LOGGER.error("Error while getting prefix value", ex);
			}
        // do reversal for each
        for (final String[] array : transactionIds) {
            final long depositSlipId = Long.parseLong(array[0]);
            accountReceiptEntryJpaRepository.updateCheckStatusInReversal(checkStatus, depositSlipId);
            accountChequeOrCashDepositeDao.reverseDepositSlip(dto, depositSlipId, orgId, ipMacAddress);
            dto.setPayModeType(array[1]);
            voucherPostingForDepositSlipReversal(dto,
                    depositSlipRepository.findAllByDepositSlipId(depositSlipId, orgId), fieldId, orgId, ipMacAddress);
        }
    }

    private void voucherPostingForDepositSlipReversal(final VoucherReversalDTO reversalDTO,
            final AccountBankDepositeSlipMasterEntity entity, final long fieldId, final long orgId,
            final String ipMacAddress) {

        final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                PrefixConstants.ContraVoucherEntry.CV, AccountPrefix.VOT.toString(), orgId);
        // final Long voucherSubTypeId =
        // CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ChequeDishonour.DS,
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
                    "entryType id not found for for lookUpCode[MAN] from VET Prefix in Deposit Slip Reversal reverse voucher posting.");
        }
        final List<AccountVoucherEntryEntity> acVouEntryEntity = journalVoucherDao.getReceiptReversalVoucherDetails(
                entity.getDepositeSlipNumber().toString(), entity.getDepositeSlipDate(), voucherTypeId, orgId,
                entity.getDeptId().getDpDeptid(), vouSubTypes, entryTypeId);

        if ((acVouEntryEntity == null) || acVouEntryEntity.isEmpty()) {
            throw new NullPointerException("No records found from TB_AC_VOUCHER [voReferenceNo="
                    + entity.getDepositeSlipNumber() + ",vouDate=" + entity.getDepositeSlipDate() + ",voucherTypeId="
                    + voucherTypeId + ",voucherSubTypeId=" + voucherSubTypeId + ",orgId=" + orgId + "]");
        }

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

            if (voucherType.equals(PrefixConstants.ContraVoucherEntry.CV)) {

                bean.setVouSubtypeCpdId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.Prefix.DSR,
                        PrefixConstants.REV_TYPE_CPD_VALUE, orgId));
                bean.setVouReferenceNo(entity.getDepositeSlipNumber().toString());// rcptno
                bean.setVouDate(entity.getDepositeSlipDate());
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

                String vouNo = generateVoucherNo(PrefixConstants.ContraVoucherEntry.CV, org.getOrgid(),
                        entity.getDepositeSlipDate());
                bean.setVouNo(vouNo);
                bean.setVouTypeCpdId(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                        PrefixConstants.ContraVoucherEntry.CV, AccountPrefix.VOT.toString(), orgId));
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
                LOGGER.info("voucherPostingService - voucherPosting -CV voucherPostingForDepositSlipReversal:" + bean);
                accountVoucherEntryRepository.save(bean);
            }
        }
    }

    @Override
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
            if (oldData != null) {
                for (final VoucherReversalDTO old : oldData) {
                    if (old.getPrimaryKey() == Long.parseLong(checkedId)) {
                        if (MainetConstants.YES.equals(old.getReversedOrNot())) {
                            joiner.add(checkedId);
                            count++;
                        } else {
                            toReverse.add(checkedId);
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
                            + session.getMessage("account.cheque.cash.reverse"));
        }

        return response;
    }

    @Override
    public ResponseEntity<?> validateDataForDepositSlipReversal(final List<VoucherReversalDTO> oldData,
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
        final List<String[]> toReverse = new ArrayList<>();

        for (final String checkedId : splitArr) {
            for (final VoucherReversalDTO old : oldData) {
                if (old.getPrimaryKey() == Long.parseLong(checkedId)) {
                    if (MainetConstants.YES.equals(old.getReversedOrNot())) {
                        joiner.add(checkedId);
                        count++;
                    } else {
                        final String[] checkedIdArr = new String[2];
                        checkedIdArr[0] = checkedId;
                        checkedIdArr[1] = old.getPayModeType();
                        toReverse.add(checkedIdArr);
                        break;
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
                            + session.getMessage("account.cheque.cash.reverse"));
        }

        return response;

    }

    private String generateVoucherNo(String voucherType, final long orgId, Date depositeSlipDate) {
        Long finYearId = financialYearJpaRepository.getFinanciaYearIdByFromDate(depositeSlipDate);
        if (finYearId == null) {
            throw new NullPointerException(GENERATE_VOUCHER_NO_FIN_YEAR_ID);
        }
        Long voucherTypeId = voucherTypeId(voucherType, orgId);
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

    private void updateReceiptMode(AccountBankDepositeSlipMasterEntity depositeSlipMasEntity,
            AccountChequeOrCashDepositeBean bean) {
    	LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.DNC,
                PrefixConstants.LookUpPrefix.CLR, new Organisation(depositeSlipMasEntity.getOrgid()));

        if (bean.getDepositeType().equals(MainetConstants.FlagD)
                || bean.getDepositeType().equals(MainetConstants.CommonConstants.Q)|| bean.getDepositeType().equals(MainetConstants.FlagB)||bean.getDepositeType().equals(MainetConstants.CommonConstants.P)) {

            if (lookUp != null)
                if (lookUp.getOtherField() != null) {
                    for (final TbServiceReceiptMasBean receiptBean : bean.getListOfChequeDDPoDetails()) {
                        if (receiptBean.getSelectDs() != null) {
                            accountReceiptHeadJpaRepository.updateSrcptModeChequeFlagUpdate(receiptBean.getRmRcptid(),
                                    lookUp.getLookUpId());

                        }
                    }
                }

        }
		else if (bean.getDepositeType().equals(MainetConstants.CommonConstants.C)
				|| bean.getDepositeType().equals(MainetConstants.FlagW)
				|| bean.getDepositeType().equals("POS")) {
			if (lookUp != null)
		          if (lookUp.getOtherField() != null) {
                     for (final AccountLedgerMasBean receiptBean : bean.getListOfLedgerDetails()) {
                         if (receiptBean.getSelectDs() != null) {
                        	
                        		 if(receiptBean.getReceiptIds()!=null ) {
                        			 accountReceiptHeadJpaRepository.updateSrcptModeChequeFlagUpdate(receiptBean.getRmRcptid(),
                                             lookUp.getLookUpId()); 
                        		 }
                        	 }

                         
                     }
                 }
        }

    }
    private void updateBankClearanceData(AccountBankDepositeSlipMasterEntity depositeSlipMasEntity,
			AccountChequeOrCashDepositeBean beanfinal ,Long orgId,
            final int langId, final Long empId, final String ipMacAddress) {
    	 final Long lkpStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                 AccountConstants.CLEARED.getValue(), AccountPrefix.CLR.toString(),
                 depositeSlipMasEntity.getOrgid());
    	 String lookStatus=null;
    	 if(lkpStatus!=null)
    	 lookStatus=String.valueOf(lkpStatus);
    	Date date= Utility.stringToDate(beanfinal.getDepositDateCheque());
    	 for (final TbServiceReceiptMasBean receiptBean : beanfinal.getListOfChequeDDPoDetails()) {
    		 try {
			if (receiptBean.getSelectDs() != null) {
				Long id=accountReceiptHeadJpaRepository.getSrcptReciptModeId(receiptBean.getRmRcptid());
				if(id!=null) {
				bankReconciliationDao.saveOrUpdateBankReconciliationFormReceiptData(id,
						date, lookStatus, depositeSlipMasEntity.getOrgid(), depositeSlipMasEntity.getUpdatedBy(), new Date(), ipMacAddress);
				}
			}}
    	 catch (Exception e) {
    		 LOGGER.error("Error occuered at the time of updateBankClearanceData "+e);
		}
         }
		
    	
	}
    @Override
    @Transactional(readOnly = true)
    public List<AccountChequeOrCashDepositSlipDTO> getBankDetails(Long depositeSlipId, Long orgid) {
        List<Long> receiptDetList = accountReceiptHeadJpaRepository.findRmcpIdBySlipId(depositeSlipId, orgid);

        final List<AccountChequeOrCashDepositSlipDTO> listOfBean = new ArrayList<>();

        for (Long rmRcptid : receiptDetList) {

            List<Object[]> listOfObjArr = null;
            BigDecimal rmAmt = null;

            if (rmRcptid != null) {
                listOfObjArr = accountReceiptHeadJpaRepository.findChequeNoByRm_RcpId(rmRcptid, orgid);
            }
            final AccountChequeOrCashDepositSlipDTO depositSlip = new AccountChequeOrCashDepositSlipDTO();
            Long BankId = null;
            Long chequeNo = null;
            Long modeId=null;

            if (listOfObjArr != null && !listOfObjArr.isEmpty()) {
                for (Object[] objArr : listOfObjArr) {
                	if(objArr[0]!=null)
                    chequeNo = (Long) objArr[0];
                	if(objArr[1]!=null)
                    BankId = (Long) objArr[1];
                	if(objArr[2]!=null) {
                    rmAmt = new BigDecimal(objArr[2].toString());
                    depositSlip.setAmount(rmAmt);
                	}
                	if(objArr[5]!=null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
                    String rmDate = dateFormat.format((Date)objArr[5]);
                    depositSlip.setChequeDate(rmDate);
                	}
                	if(objArr[6]!=null) {
                    modeId=(Long) objArr[6];
                    depositSlip.setModeId(modeId);
                	}
                }
                List<Object[]> listOfBankObj = accountReceiptHeadJpaRepository.findBranchNameByBId(BankId);
                for (Object[] objBankArr : listOfBankObj) {
                    depositSlip.setBranch(objBankArr[0].toString());
                    depositSlip.setBank(objBankArr[1].toString());
                }
                if(chequeNo!=null)
                depositSlip.setChequeNo(chequeNo.toString());
                listOfBean.add(depositSlip);
            }
        }
        return listOfBean.stream().sorted(Comparator.comparing(AccountChequeOrCashDepositSlipDTO::getBank))
				.collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountChequeOrCashDepositSlipDTO> getBankAccountDetails(Long depositeSlipId, Long orgid) {
        List<Long> receiptDetList = accountReceiptHeadJpaRepository.findRmcpIdBySlipId(depositeSlipId, orgid);

        final List<AccountChequeOrCashDepositSlipDTO> listOfBean = new ArrayList<>();

        for (Long rmRcptid : receiptDetList) {

            List<Object[]> listOfObjArr = null;
            BigDecimal rmAmt = null;

            if (rmRcptid != null) {
                listOfObjArr = accountReceiptHeadJpaRepository.findChequeNoByRm_RcpId(rmRcptid, orgid);
            }
            final AccountChequeOrCashDepositSlipDTO depositSlip = new AccountChequeOrCashDepositSlipDTO();
            Long bankId = null;
            String chequeNo = null;
            Long modeId=null;

            if (listOfObjArr != null && !listOfObjArr.isEmpty()) {
                for (Object[] objArr : listOfObjArr) {
                	if(objArr[0]!=null)
                    chequeNo = ((Long) objArr[0]).toString();
                	if(objArr[1]!=null)
                    bankId = (Long) objArr[1];
                	if(objArr[2]!=null) {
                    rmAmt = new BigDecimal(objArr[2].toString());
                    depositSlip.setAmount(rmAmt);
                	}
                	if(objArr[5]!=null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
                    String rmDate = dateFormat.format((Date)objArr[5]);
                    depositSlip.setChequeDate(rmDate);
                	}
                	if(objArr[6]!=null)
                    modeId=(Long) objArr[6];
                    depositSlip.setModeId(modeId);
                    depositSlip.setChequeNo(chequeNo);
                    if(bankId!=null)
                    depositSlip.setBank(bankId.toString());
                    
                }
                Long bankMasterId =null;
                if(bankId!=null)
                	bankMasterId = accountReceiptHeadJpaRepository.findBankAccountBranchNameByBId(bankId, orgid);
                if (bankMasterId != null) {
                    List<Object[]> listOfBankObj = accountReceiptHeadJpaRepository.findBranchNameByBId(bankMasterId);
                    for (Object[] objBankArr : listOfBankObj) {
                        depositSlip.setBranch(objBankArr[0].toString());
                        depositSlip.setBank(objBankArr[1].toString());
                    }
                }
                listOfBean.add(depositSlip);
            }
        }
		return listOfBean.stream().sorted(Comparator.comparing(AccountChequeOrCashDepositSlipDTO::getBank))
				.collect(Collectors.toList());
         

    }
   

    @Override
    @Transactional(readOnly = true)
    public List<AccountChequeOrCashDepositSlipDTO> getDenominationCashDetails(Long depositeSlipId, Long orgid) {
        // TODO Auto-generated method stub

        final List<AccountChequeOrCashDepositSlipDTO> listOfBean = new ArrayList<>();

        List<AccountBankDepositeSlipMasterEntity> denominationCashDetail = depositSlipRepository
                .getDenominationCashDetails(depositeSlipId, orgid);
        for (AccountBankDepositeSlipMasterEntity accountBankDepositeSlipMasterEntity : denominationCashDetail) {
            List<AccountBankDepositeSlipDenomEntity> denList = accountBankDepositeSlipMasterEntity
                    .getDenominationEntityList();
            for (AccountBankDepositeSlipDenomEntity accountBankDepositeSlipDenomEntity : denList) {
                final AccountChequeOrCashDepositSlipDTO depositSlip = new AccountChequeOrCashDepositSlipDTO();
                String denCodeValue = CommonMasterUtility.findLookUpCode("DEN", orgid,
                        accountBankDepositeSlipDenomEntity.getCpdDenomId());
                if (accountBankDepositeSlipDenomEntity.getDenominationCount() != null && denCodeValue != null
                        && denCodeValue != "") {
                    depositSlip.setNumberOfRupes(accountBankDepositeSlipDenomEntity.getDenominationCount());
                    depositSlip.setRupesTpye(denCodeValue);
                    listOfBean.add(depositSlip);
                }
            }
        }
        return listOfBean;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getCheckDepositSlipAmountExists(Date receiptDate, long orgid) {

        BigDecimal sumOfReceiptAmount = depositSlipRepository.getSumOfReceiptAmountExist(receiptDate, orgid);
        BigDecimal sumOfDepositAmount = depositSlipRepository.getSumOfDepositAmountExist(receiptDate, orgid);
        BigDecimal sumOfReceiptAmt = BigDecimal.ZERO;
        BigDecimal sumOfDepositAmt = BigDecimal.ZERO;
        if (sumOfReceiptAmount != null) {
            sumOfReceiptAmt = sumOfReceiptAmount;
        }
        if (sumOfDepositAmount != null) {
            sumOfDepositAmt = sumOfDepositAmount;
        }
        BigDecimal finalSumAmount = BigDecimal.ZERO;
        if (sumOfReceiptAmt != null && sumOfDepositAmt != null) {
            finalSumAmount = sumOfReceiptAmt.subtract(sumOfDepositAmt);
        }
        return finalSumAmount;
    }

}