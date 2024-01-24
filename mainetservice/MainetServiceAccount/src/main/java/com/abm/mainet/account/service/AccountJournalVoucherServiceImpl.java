
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountJournalVoucherEntryDao;
import com.abm.mainet.account.domain.AccountVoucherEntryDetailsEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryDetailsHistEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.domain.AccountVoucherEntryHistEntity;
import com.abm.mainet.account.dto.AccountJournalVoucherEntryBean;
import com.abm.mainet.account.dto.AccountJournalVoucherEntryDetailsBean;
import com.abm.mainet.account.dto.AccountVoucherDetailsUploadDTO;
import com.abm.mainet.account.dto.AccountVoucherMasterUploadDTO;
import com.abm.mainet.account.mapper.AccountJournalVoucherEntryMapper;
import com.abm.mainet.account.repository.AccountDepositRepository;
import com.abm.mainet.account.repository.AccountVoucherEntryRepository;
import com.abm.mainet.account.repository.BudgetHeadRepository;
import com.abm.mainet.account.repository.ContraEntryVoucherRepository;
import com.abm.mainet.account.repository.VoucherTemplateRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountJournalVoucherEntry;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.AbstractService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

/**
 * @author deepika.pimpale
 *
 */
@Service
public class AccountJournalVoucherServiceImpl extends AbstractService implements AccountJournalVoucherService {

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountJournalVoucherService# getSearchVoucherData(java.util.List)
     */

    @Resource
    AccountJournalVoucherEntryDao journalVoucherDao;

    @Resource
    AccountJournalVoucherEntryMapper voucherMapper;

    @Resource
    private EmployeeJpaRepository employeeJpaRepository;

    @Resource
    private VoucherTemplateRepository voucherTemplateRepository;

    @Resource
    ContraEntryVoucherRepository contraEntryVoucherJpaRepository;

    @Resource
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Resource
    private BudgetHeadRepository budgetHeadRepository;

    @Resource
    private AccountDepositRepository accountDepositJparepository;
    @Resource
    private TbFinancialyearService tbFinancialyearService;
    @Resource
    private AuditService auditService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private AccountVoucherEntryRepository accountVoucherEntryRepository;

    @Resource
    private ILocationMasService locationMasService;

    private static final String SEQUENCE_NO_ERROR = "unable to generate sequenceNo for orgId=";
    private static final String SEQUENCE_NO = "0000000000";

    private static final String TEMPLATE_ID_NOT_FOUND_VOU = "templateTypeId not found for provided parameter[voucherType=";
    private static final String ORG_ID = ", orgId=";
    private static final String PREFIX_VOT = ",prefix=VOT]";

    private static final String FI_YEAR_DATE_MAP = "Financial year Status is missing in the given financial year Date : ";
    private static final String FI_MONTH_DATE_MAP = "Financial month Status is missing in the given financial year Date : ";
    private static final String ORGID_IS = " and orgid is : ";
    private static final Logger LOGGER = Logger.getLogger(AccountJournalVoucherServiceImpl.class);
    private static final String GENERATE_VOUCHER_NO_FIN_YEAR_ID = "Voucher sequence number generation, The financial year id is getting null value";
    private static final String GENERATE_VOUCHER_NO_VOU_TYPE_ID = "Voucher sequence number generation, The voucher type id is getting null value";

    @Override
    @Transactional
    public List<AccountJournalVoucherEntryBean> getSearchVoucherData(final Long voucherType, final Date fromDate,
            final Date toDate, final String dateType, final String authStatus, final BigDecimal amount,
            final Long orgId, final String refNo, String urlIdentifyFlag) {

        final List<Object[]> searchList = journalVoucherDao.getSearchVoucherData(voucherType, fromDate, toDate,
                dateType, authStatus, amount, orgId, refNo);
        final List<AccountJournalVoucherEntryBean> beanList = new ArrayList<>();
        AccountJournalVoucherEntryBean bean = null;
        for (final Object[] entity : searchList) {
            bean = new AccountJournalVoucherEntryBean();
            if (entity[0] != null) {
                bean.setVouId(Long.valueOf(entity[0].toString()));
            }
            if (entity[1] != null) {
                bean.setVouNo(entity[1].toString());
            }
            if (entity[2] != null) {
                bean.setVouDate((Date) entity[2]);
            }
            if (entity[3] != null) {
                bean.setVouPostingDate((Date) entity[3]);
            }
            if (entity[4] != null) {
                bean.setVouTypeCpdId(Long.valueOf(entity[4].toString()));
            }
            if (entity[5] != null) {
                bean.setVouReferenceNo(entity[5].toString());
            }
            if (entity[6] != null) {
                bean.setNarration(entity[6].toString());
            }
            if (entity[7] != null) {
                bean.setAuthoFlg(entity[7].toString());
            }
            if (entity[8] != null) {
                 	try {
						bean.setEntryFrom(CommonMasterUtility.getCPDDescription(Long.valueOf(entity[8].toString()),MainetConstants.FlagE));
					} catch (Exception e) {
						e.printStackTrace();
					}
            }
            if (urlIdentifyFlag != null && !urlIdentifyFlag.isEmpty()) {
                bean.setUrlIdentifyFlag(MainetConstants.Y_FLAG);
            } else {
                bean.setUrlIdentifyFlag(MainetConstants.N_FLAG);
            }
            if (bean != null) {
                beanList.add(bean);
            }
        }
        return beanList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountJournalVoucherService#
     * saveAccountJournalVoucherEntry(com.abm.mainet.account.dto. AccountJournalVoucherEntryBean)
     */
    @Override
    @Transactional
    public boolean saveAccountJournalVoucherEntry(final AccountJournalVoucherEntryBean dto) {

        boolean fiYearHeadClosed = false;
        if (dto.getTransactionDate() != null && !dto.getTransactionDate().isEmpty()) {
            Date hardClosedFiYearDate = Utility.stringToDate(dto.getTransactionDate());
            Long finYeadStatus = tbFinancialyearService.checkHardClosedFinYearDateExists(hardClosedFiYearDate,
                    dto.getOrg());
            if (finYeadStatus == null) {
                throw new NullPointerException(FI_YEAR_DATE_MAP + hardClosedFiYearDate + ORGID_IS + dto.getOrgId());
            } else {
                Long finYeadMonthStatus = tbFinancialyearService.checkSoftClosedFinYearDateExists(hardClosedFiYearDate,
                        dto.getOrg());
                String fiYearStatusCode = CommonMasterUtility.findLookUpCode(MainetConstants.FIN_YEAR_YOC, dto.getOrg(),
                        finYeadStatus);
                String fiYearMonthStatusCode = "";
                if (finYeadMonthStatus != null) {
                    fiYearMonthStatusCode = CommonMasterUtility.findLookUpCode(MainetConstants.FIN_YEAR_YOC,
                            dto.getOrg(), finYeadMonthStatus);
                }

                if ((fiYearStatusCode != null && !fiYearStatusCode.isEmpty())
                        && (fiYearMonthStatusCode !=null && !fiYearMonthStatusCode.isEmpty())) {
                    if (fiYearStatusCode.equals(PrefixConstants.LookUp.OPN) || fiYearMonthStatusCode.equals(PrefixConstants.LookUp.OPN)) {
                        fiYearHeadClosed = true;
                    }
                }
            }
        }
        if (fiYearHeadClosed == false) {
            return false;
            // throw new FrameworkException(FI_YEAR_STATUS_CLOSED);
        }

        AccountVoucherEntryHistEntity accountVoucherEntryHistEntity = null;
        AccountVoucherEntryDetailsHistEntity accountVoucherEntryDetailsHistEntity = null;

        final AccountVoucherEntryEntity entity = voucherMapper.mapDtoToEntity(dto);
        Objects.requireNonNull(dto.getTransactionDate(),
                ApplicationSession.getInstance().getMessage("account.journal.voucher.voucherdate") + dto);

        entity.setVouDate(Utility.stringToDate(dto.getTransactionDate()));
        final long entryTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountJournalVoucherEntry.MAN,
                AccountJournalVoucherEntry.VET, entity.getOrg());
        if (entryTypeId == 0l) {
            throw new NullPointerException("entryType id not found for for lookUpCode[MAN] from VET Prefix");
        }
        entity.setEntryType(entryTypeId);

        Organisation org = new Organisation();
        org.setOrgid(dto.getOrg());
        // Autho Flag
        final long entryTypeUp = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountJournalVoucherEntry.UPL,
                AccountJournalVoucherEntry.VET, org.getOrgid());
        final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.AccountJournalVoucherEntry.VOUCHER_AUTH_TYPE, AccountPrefix.AUT.toString(),
                dto.getLangId(), org);
        if ((payEntryMakerChecker.getLookUpCode() == null || payEntryMakerChecker.getLookUpCode().isEmpty())||((dto.getEntryType()!=null&&entryTypeUp==dto.getEntryType())&&StringUtils.equalsIgnoreCase(payEntryMakerChecker.getOtherField(), MainetConstants.FlagY))) {
            entity.setAuthoFlg(AccountConstants.Y.getValue());
            entity.setAuthoId(dto.getCreatedBy());
            entity.setAuthoDate(entity.getVouDate());
            entity.setVouNo(dto.getVouNo());
            entity.setVouPostingDate(entity.getVouDate());
            dto.setAuthoFlg(AccountConstants.Y.getValue());
        } else {
            entity.setAuthoFlg(AccountConstants.N.getValue());
            entity.setVouPostingDate(null);
            dto.setAuthoFlg(AccountConstants.N.getValue());
        }

        AccountVoucherEntryEntity finalEntity = journalVoucherDao.saveAccountJournalVoucherEntry(entity);

        accountVoucherEntryHistEntity = new AccountVoucherEntryHistEntity();
        accountVoucherEntryHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
        try {
            auditService.createHistory(finalEntity, accountVoucherEntryHistEntity);
        } catch (Exception ex) {
            LOGGER.error("Could not make audit entry for " + finalEntity, ex);
        }

        for (AccountVoucherEntryDetailsEntity accountVoucherEntryDetailsEntity : finalEntity.getDetails()) {
            accountVoucherEntryDetailsHistEntity = new AccountVoucherEntryDetailsHistEntity();
            accountVoucherEntryDetailsHistEntity.setMaster(finalEntity.getVouId());
            if (accountVoucherEntryDetailsEntity.getBudgetCode() != null
                    && accountVoucherEntryDetailsEntity.getBudgetCode().getPrBudgetCodeid() != null) {
                accountVoucherEntryDetailsHistEntity
                        .setBudgetCode(accountVoucherEntryDetailsEntity.getBudgetCode().getprBudgetCodeid());
            }
            accountVoucherEntryDetailsHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
            try {
                auditService.createHistory(accountVoucherEntryDetailsEntity, accountVoucherEntryDetailsHistEntity);
            } catch (Exception ex) {
                LOGGER.error("Could not make audit entry for " + accountVoucherEntryDetailsEntity, ex);
            }
        }

        if ((dto.getDepositFlag() != null) && !dto.getDepositFlag().isEmpty()) {
            if (dto.getDepositFlag().equals(MainetConstants.MENU.Y)) {
                final Long depId = dto.getDepId();
                dto.setVouId(finalEntity.getVouId());
                final Long statusDepOpen = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                        PrefixConstants.AccountBillEntry.DO, PrefixConstants.NewWaterServiceConstants.RDC,
                        dto.getOrg());
                final Long statusDepRefund = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                        PrefixConstants.AccountBillEntry.DR, PrefixConstants.NewWaterServiceConstants.RDC,
                        dto.getOrg());

                // Check MAcker Checker flag value VT in prefix AUT if value is null inactive
                // else active
                if (payEntryMakerChecker.getLookUpCode() == null) {
                    BigDecimal depBalAmt = dto.getBalAmount().subtract(dto.getDepBalAmount());
                    if ((depBalAmt.toString()).equals("0") || (depBalAmt.toString()).equals("0.00")) {
                        accountDepositJparepository.updateDepBalAmountInTransferDeposit(depId, statusDepRefund,
                                depBalAmt, dto.getOrg());
                    } else {
                        accountDepositJparepository.updateDepBalAmountInTransferDeposit(depId, statusDepOpen, depBalAmt,
                                dto.getOrg());
                    }

                }

            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateAccountJournalVoucherEntry(final AccountJournalVoucherEntryBean dto) {

        AccountVoucherEntryHistEntity accountVoucherEntryHistEntity = null;
        AccountVoucherEntryDetailsHistEntity accountVoucherEntryDetailsHistEntity = null;

        if (MainetConstants.MENU.N.equals(dto.getAuthoFlg())) {
            final AccountVoucherEntryEntity entity = voucherMapper.mapDtoToEntity(dto);
            entity.setUpdatedby(dto.getUpdatedby());
            if(StringUtils.isNotBlank(dto.getTransactionDate()))
            entity.setVouDate(Utility.stringToDate(dto.getTransactionDate()));
            AccountVoucherEntryEntity finalEntity = journalVoucherDao.updateAccountJournalVoucherEntry(entity);

            accountVoucherEntryHistEntity = new AccountVoucherEntryHistEntity();
            accountVoucherEntryHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
            try {
                auditService.createHistory(finalEntity, accountVoucherEntryHistEntity);
            } catch (Exception ex) {
                LOGGER.error("Could not make audit entry for " + finalEntity, ex);
            }
            for (AccountVoucherEntryDetailsEntity accountVoucherEntryDetailsEntity : finalEntity.getDetails()) {
                accountVoucherEntryDetailsHistEntity = new AccountVoucherEntryDetailsHistEntity();
                accountVoucherEntryDetailsHistEntity.setMaster(finalEntity.getVouId());
                if (accountVoucherEntryDetailsEntity.getBudgetCode() != null
                        && accountVoucherEntryDetailsEntity.getBudgetCode().getPrBudgetCodeid() != null) {
                    accountVoucherEntryDetailsHistEntity
                            .setBudgetCode(accountVoucherEntryDetailsEntity.getBudgetCode().getprBudgetCodeid());
                }
                accountVoucherEntryDetailsHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
                try {
                    auditService.createHistory(accountVoucherEntryDetailsEntity, accountVoucherEntryDetailsHistEntity);
                } catch (Exception ex) {
                    LOGGER.error("Could not make audit entry for " + accountVoucherEntryDetailsEntity, ex);
                }
            }

        } else if (MainetConstants.MENU.D.equals(dto.getAuthoFlg())) {
            final AccountVoucherEntryEntity entity = voucherMapper.mapDtoToEntity(dto);
            entity.setAuthoId(dto.getUpdatedby());
            entity.setAuthoDate(Utility.stringToDate(dto.getTransactionAuthDate()));
            AccountVoucherEntryEntity finalEntity = journalVoucherDao.updateAccountJournalVoucherEntry(entity);

            accountVoucherEntryHistEntity = new AccountVoucherEntryHistEntity();
            accountVoucherEntryHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
            try {
                auditService.createHistory(finalEntity, accountVoucherEntryHistEntity);
            } catch (Exception ex) {
                LOGGER.error("Could not make audit entry for " + finalEntity, ex);
            }

            for (AccountVoucherEntryDetailsEntity accountVoucherEntryDetailsEntity : finalEntity.getDetails()) {
                accountVoucherEntryDetailsHistEntity = new AccountVoucherEntryDetailsHistEntity();
                accountVoucherEntryDetailsHistEntity.setMaster(finalEntity.getVouId());
                if (accountVoucherEntryDetailsEntity.getBudgetCode() != null
                        && accountVoucherEntryDetailsEntity.getBudgetCode().getPrBudgetCodeid() != null) {
                    accountVoucherEntryDetailsHistEntity
                            .setBudgetCode(accountVoucherEntryDetailsEntity.getBudgetCode().getprBudgetCodeid());
                }
                accountVoucherEntryDetailsHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
                try {
                    auditService.createHistory(accountVoucherEntryDetailsEntity, accountVoucherEntryDetailsHistEntity);
                } catch (Exception ex) {
                    LOGGER.error("Could not make audit entry for " + accountVoucherEntryDetailsEntity, ex);
                }
            }

        } else {
            final AccountVoucherEntryEntity entity = voucherMapper.mapDtoToEntity(dto);
            Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    MainetConstants.AccountJournalVoucherEntry.VOU_SUB_TYPE_DFA, AccountPrefix.TDP.toString(),
                    entity.getOrg());
            // in case of deposit it will work
            if (entity.getVouSubtypeCpdId().equals(voucherSubTypeId)) {
                final Long statusDepOpen = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                        PrefixConstants.AccountBillEntry.DO, PrefixConstants.NewWaterServiceConstants.RDC,
                        dto.getOrg());
                final Long statusDepRefund = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                        PrefixConstants.AccountBillEntry.DR, PrefixConstants.NewWaterServiceConstants.RDC,
                        dto.getOrg());

                final Long drType = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                        PrefixConstants.AccountBillEntry.DR, PrefixConstants.DCR, dto.getOrg());

                BigDecimal depositAmount = null;
                for (AccountVoucherEntryDetailsEntity accountVoucherEntryDetailsEntity : entity.getDetails()) {

                    if (accountVoucherEntryDetailsEntity.getDrcrCpdId().equals(drType)) {
                        depositAmount = accountVoucherEntryDetailsEntity.getVoudetAmt();
                        break;
                    }
                }

                BigDecimal depositRefundAmount = BigDecimal.ZERO;
                if (entity.getVouReferenceNo() != null && !entity.getVouReferenceNo().isEmpty()) {
                    depositRefundAmount = accountDepositJparepository
                            .getDepRefundAmount(Long.valueOf(entity.getVouReferenceNo()), entity.getOrg());
                }
                BigDecimal depBalAmt = depositRefundAmount.subtract(depositAmount);

                if (depBalAmt.signum() != -1) {
                    if ((depBalAmt.toString()).equals("0") || (depBalAmt.toString()).equals("0.00")) {
                        accountDepositJparepository.updateDepBalAmountInTransferDepositAuth(
                                Long.valueOf(entity.getVouReferenceNo()), statusDepRefund, depBalAmt, dto.getOrg());
                    } else {
                        accountDepositJparepository.updateDepBalAmountInTransferDepositAuth(
                                Long.valueOf(entity.getVouReferenceNo()), statusDepOpen, depBalAmt, dto.getOrg());
                    }

                    Objects.requireNonNull(dto.getTransactionAuthDate(),
                            ApplicationSession.getInstance().getMessage("account.bill.entry.service.authorizationdate")
                                    + dto);
                    entity.setAuthoDate(Utility.stringToDate(dto.getTransactionAuthDate()));
                    entity.setAuthoId(dto.getUpdatedby());
                    entity.setVouPostingDate(Utility.stringToDate(dto.getTransactionAuthDate()));
                    AccountVoucherEntryEntity finalEntity = journalVoucherDao.updateAccountJournalVoucherEntry(entity);

                    accountVoucherEntryHistEntity = new AccountVoucherEntryHistEntity();
                    accountVoucherEntryHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
                    try {
                        auditService.createHistory(finalEntity, accountVoucherEntryHistEntity);
                    } catch (Exception ex) {
                        LOGGER.error("Could not make audit entry for " + finalEntity, ex);
                    }

                    // List<AccountVoucherEntryDetailsEntity> expList = finalEntity.getDetails();
                    for (AccountVoucherEntryDetailsEntity accountVoucherEntryDetailsEntity : finalEntity.getDetails()) {
                        accountVoucherEntryDetailsHistEntity = new AccountVoucherEntryDetailsHistEntity();
                        accountVoucherEntryDetailsHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
                        try {
                            auditService.createHistory(accountVoucherEntryDetailsEntity,
                                    accountVoucherEntryDetailsHistEntity);
                        } catch (Exception ex) {
                            LOGGER.error("Could not make audit entry for " + accountVoucherEntryDetailsEntity, ex);
                        }
                    }

                }

                else {
                    return false;
                }
            }

            else {
                Objects.requireNonNull(dto.getTransactionAuthDate(),
                        ApplicationSession.getInstance().getMessage("account.bill.entry.service.authorizationdate")
                                + dto);
                entity.setAuthoDate(Utility.stringToDate(dto.getTransactionAuthDate()));
                entity.setAuthoId(dto.getUpdatedby());
                entity.setVouPostingDate(Utility.stringToDate(dto.getTransactionAuthDate()));
                AccountVoucherEntryEntity finalEntity = journalVoucherDao.updateAccountJournalVoucherEntry(entity);

                accountVoucherEntryHistEntity = new AccountVoucherEntryHistEntity();
                accountVoucherEntryHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
                try {
                    auditService.createHistory(finalEntity, accountVoucherEntryHistEntity);
                } catch (Exception ex) {
                    LOGGER.error("Could not make audit entry for " + finalEntity, ex);
                }

                for (AccountVoucherEntryDetailsEntity accountVoucherEntryDetailsEntity : finalEntity.getDetails()) {
                    accountVoucherEntryDetailsHistEntity = new AccountVoucherEntryDetailsHistEntity();
                    accountVoucherEntryDetailsHistEntity.setMaster(finalEntity.getVouId());
                    if (accountVoucherEntryDetailsEntity.getBudgetCode() != null
                            && accountVoucherEntryDetailsEntity.getBudgetCode().getPrBudgetCodeid() != null) {
                        accountVoucherEntryDetailsHistEntity
                                .setBudgetCode(accountVoucherEntryDetailsEntity.getBudgetCode().getprBudgetCodeid());
                    }
                    accountVoucherEntryDetailsHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
                    try {
                        auditService.createHistory(accountVoucherEntryDetailsEntity,
                                accountVoucherEntryDetailsHistEntity);
                    } catch (Exception ex) {
                        LOGGER.error("Could not make audit entry for " + accountVoucherEntryDetailsEntity, ex);
                    }
                }

            }

        }

        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountJournalVoucherService# getAccountVoucherDataBeanById(long)
     */
    @Override
    @Transactional(readOnly = true)
    public AccountJournalVoucherEntryBean getAccountVoucherDataBeanById(final long rowId) {
        final AccountVoucherEntryEntity entity = journalVoucherDao.getAccountVoucherDataBeanById(rowId);
        final AccountJournalVoucherEntryBean dto = voucherMapper.mapEntityToDto(entity);
        if ((entity.getAuthoFlg() != null) && entity.getAuthoFlg().equals(MainetConstants.MENU.Y)) {
            final Employee employeeEntity = employeeJpaRepository.findOne(entity.getAuthoId());
            if(employeeEntity != null) {
            if (employeeEntity.getEmpmname() == null) {
                employeeEntity.setEmpmname(StringUtils.EMPTY);
            }
            if (employeeEntity.getEmplname() == null) {
                employeeEntity.setEmplname(StringUtils.EMPTY);
            }
            dto.setAuthorityName(employeeEntity.getEmpname() + MainetConstants.WHITE_SPACE
                    + employeeEntity.getEmpmname() + MainetConstants.WHITE_SPACE + employeeEntity.getEmplname());
            }
        }
        if (entity.getVouDate() != null) {
            dto.setVouDateDup(UtilityService.convertDateToDDMMYYYY(entity.getVouDate()));
        }
        if (entity.getAuthoDate() != null) {
            dto.setAuthoDateDup(UtilityService.convertDateToDDMMYYYY(entity.getAuthoDate()));
            dto.setTransactionAuthDate(UtilityService.convertDateToDDMMYYYY(entity.getAuthoDate()));
        }
        dto.setTransactionDate(Utility.dateToString(entity.getVouDate()));
        dto.setVouTypeCpdId(entity.getVouTypeCpdId());
        dto.setVoucherSubType(entity.getVouSubtypeCpdId());
        return dto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountJournalVoucherService#deleteRow(long)
     */
    @Override
    @Transactional
    public void deleteRow(final long voudetId) {
        journalVoucherDao.deleteRow(voudetId);

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountJournalVoucherService#delete(long)
     */
    @Override
    @Transactional
    public void delete(final long rowId) {
        journalVoucherDao.delete(rowId);

    }

    @Override
    @Transactional
    public String generateVoucherNumber(final String voucherType, final Long org, String vocherDate) {
        final String vouNo = generateVoucherNo(voucherType, org, vocherDate);
        return vouNo;
    }

    private String generateVoucherNo(final String voucherType, final long orgId, String vocherDate) {

        Long finYearId = tbFinancialyearService.getFinanciaYearIdByFromDate(Utility.stringToDate(vocherDate));
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

    @Override
    @Transactional(readOnly = true)
    public List<LookUp> findVoucherSubTypeList(final Long voucherTypeId, final Long deptId, final Long orgId) {
        final List<Long> subTypeList = voucherTemplateRepository.queryVoucherSubTypesForVoucher(voucherTypeId, deptId,
                orgId, CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.IsLookUp.ACTIVE,
                        PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX, orgId));
        // jpa return empty List if no records found
        if (subTypeList.isEmpty()) {
            throw new NullPointerException("No voucherSubType found from TB_AC_VOUCHERTEMPLATE_MAS");
        }
        final List<LookUp> list = CommonMasterUtility.lookUpListByPrefix(PrefixConstants.REV_TYPE_CPD_VALUE, orgId);
        final List<LookUp> subtypeList = new ArrayList<>();
        for (final Long subTypeId : subTypeList) {
            for (final LookUp lookUp : list) {
                if (subTypeId.longValue() == lookUp.getLookUpId()) {
                    subtypeList.add(lookUp);
                    break;
                }
            }
        }

        return subtypeList;
    }

    @Override
    @Transactional
    public boolean isCombinationCheckTransactions(Long sacHeadid, Long orgId) {
        // TODO Auto-generated method stub
        return journalVoucherDao.isCombinationCheckTransactions(sacHeadid, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkFinancialYearStatusHardCloseOrNot(String transactionAuthDate, Long orgid) {

        boolean fiYearHeadClosed = false;
        if (transactionAuthDate != null && !transactionAuthDate.isEmpty()) {
            Date hardClosedFiYearDate = Utility.stringToDate(transactionAuthDate);
            Long finYeadStatus = tbFinancialyearService.checkHardClosedFinYearDateExists(hardClosedFiYearDate, orgid);
            if (finYeadStatus == null) {
                throw new NullPointerException(FI_YEAR_DATE_MAP + hardClosedFiYearDate + ORGID_IS + orgid);
            } else {
                Long finYeadMonthStatus = tbFinancialyearService.checkSoftClosedFinYearDateExists(hardClosedFiYearDate,
                        orgid);
                String fiYearStatusCode = CommonMasterUtility.findLookUpCode(MainetConstants.FIN_YEAR_YOC, orgid,
                        finYeadStatus);
                String fiYearMonthStatusCode = "";
                if (finYeadMonthStatus != null) {
                    fiYearMonthStatusCode = CommonMasterUtility.findLookUpCode(MainetConstants.FIN_YEAR_YOC, orgid,
                            finYeadMonthStatus);
                }
                // String fiYearStatusCode =
                // CommonMasterUtility.getNonHierarchicalLookUpObject(finYeadStatus,
                // dto.getOrgId()).getLookUpCode();
               /* if ((fiYearStatusCode != null && !fiYearStatusCode.isEmpty())
                        && (fiYearMonthStatusCode == null || fiYearMonthStatusCode.isEmpty())) {
                    if (fiYearStatusCode.equals(PrefixConstants.LookUp.OPN)) {
                        fiYearHeadClosed = true;
                    }
                }*/
                if ((fiYearStatusCode != null && !fiYearStatusCode.isEmpty())
                        && (fiYearMonthStatusCode !=null && !fiYearMonthStatusCode.isEmpty())) {
                    if (fiYearStatusCode.equals(PrefixConstants.LookUp.OPN) || fiYearMonthStatusCode.equals(PrefixConstants.LookUp.OPN) ) {
                        fiYearHeadClosed = true;
                    }
                }
            }
        }
        if (fiYearHeadClosed == false) {
            return false;
            // throw new FrameworkException(FI_YEAR_STATUS_CLOSED);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean saveVoucherEntryExcelData(AccountVoucherMasterUploadDTO voucherEntryUploadDto, Long orgId, int langId,
            Employee emp) {

        final long entryTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountJournalVoucherEntry.UPL,
                AccountJournalVoucherEntry.VET, orgId);
        final Long departmentId = departmentService.getDepartmentIdByDeptCode(AccountConstants.AC.toString());

        long fieldId = 0;
        if (emp.getTbLocationMas() != null) {
            fieldId = locationMasService.getcodIdRevLevel1ByLocId(emp.getTbLocationMas().getLocId(), orgId);
        }
        AccountJournalVoucherEntryBean voucherEntryBean = new AccountJournalVoucherEntryBean();

        List<AccountJournalVoucherEntryDetailsBean> entryDetailsList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.REQUIRED_PG_PARAM.DATEFORMAT_DDMMYYYY);
        //Parsing the given String to Date object 
        Date dateFromParsing = null;
        try {
           dateFromParsing = formatter.parse(voucherEntryUploadDto.getTranDate());
        } catch (ParseException e) {
        	  LOGGER.error("Error while parsing Date :", e);
        }
        String tranDate = Utility.dateToString(dateFromParsing);
        int count = 0;
        // voucherEntryBean.setVouPostingDate(voucherEntryUploadDto.getTranDate());
        voucherEntryBean.setVouDate(Utility.stringToDate(tranDate));
        voucherEntryBean.setTransactionDate(tranDate);
        voucherEntryBean.setVouTypeCpdId(Long.valueOf(voucherEntryUploadDto.getVoucherType()));
        voucherEntryBean.setVouSubtypeCpdId(Long.valueOf(voucherEntryUploadDto.getVoucherSubType()));
        voucherEntryBean.setDpDeptid(departmentId);
        voucherEntryBean.setVouReferenceNo(voucherEntryUploadDto.getTranRefNo());
        voucherEntryBean.setNarration(voucherEntryUploadDto.getNarration());
        voucherEntryBean.setFieldId(fieldId);
        voucherEntryBean.setOrg(voucherEntryUploadDto.getOrgid());
        voucherEntryBean.setCreatedBy(voucherEntryUploadDto.getUserId());
        voucherEntryBean.setLmodDate(voucherEntryUploadDto.getLmoddate());
        voucherEntryBean.setLangId(langId);
        voucherEntryBean.setLgIpMac(voucherEntryUploadDto.getLgIpMac());
        voucherEntryBean.setEntryType(entryTypeId);

        Organisation org = new Organisation();
        org.setOrgid(orgId);
        final String voucherType = CommonMasterUtility
                .getNonHierarchicalLookUpObject(voucherEntryBean.getVouTypeCpdId(), org)
                .getLookUpCode();
        final LookUp payEntryMakerChecker = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.AccountJournalVoucherEntry.VT,
                PrefixConstants.AccountJournalVoucherEntry.AUT, org);
        // final LookUp payEntryMakerChecker = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
        // MainetConstants.AccountJournalVoucherEntry.VOUCHER_AUTH_TYPE, AccountPrefix.AUT.toString(),
        // langId, org);
        if (payEntryMakerChecker != null && StringUtils.equalsIgnoreCase(payEntryMakerChecker.getOtherField(), MainetConstants.FlagY)) {
            String generateVoucherNumber = generateVoucherNumber(voucherType, orgId, voucherEntryBean.getTransactionDate());
            voucherEntryBean.setVouNo(generateVoucherNumber);
        }
        List<AccountVoucherDetailsUploadDTO> detailsList = voucherEntryUploadDto.getDetails();
        for (AccountVoucherDetailsUploadDTO voucherDetails : detailsList) {
            AccountJournalVoucherEntryDetailsBean entryDetailsBean = new AccountJournalVoucherEntryDetailsBean();
            entryDetailsBean.setLgIpMac(voucherEntryUploadDto.getLgIpMac());
            entryDetailsBean.setOrgId(voucherEntryUploadDto.getOrgid());
            entryDetailsBean.setCreatedBy(voucherEntryUploadDto.getUserId());
            entryDetailsBean.setLmodDate(voucherEntryUploadDto.getLmoddate());
            entryDetailsBean.setSacHeadId(Long.valueOf(voucherDetails.getAccHead()));
            entryDetailsBean.setVoudetAmt(voucherDetails.getAmount());
            entryDetailsBean.setDrcrCpdId(Long.valueOf(voucherDetails.getDrOrCr()));
            entryDetailsBean.setMaster(voucherEntryBean);
            entryDetailsList.add(entryDetailsBean);
            voucherEntryBean.setDetails(entryDetailsList);

        }
       
        return saveAccountJournalVoucherEntry(voucherEntryBean);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountVoucherEntryEntity> getVoucherMasterDetails(Long orgId) {

        return journalVoucherDao.getVoucherDetails(orgId);

    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountJournalVoucherEntryBean> getGridVoucherData(Long orgId) {

        List<AccountJournalVoucherEntryBean> list = new ArrayList<AccountJournalVoucherEntryBean>();
        List<AccountVoucherEntryEntity> entityList = journalVoucherDao.getGridVoucherData(orgId);
        if (entityList != null && !entityList.isEmpty()) {
            int count = 0;
            for (AccountVoucherEntryEntity accountVoucherEntryEntity : entityList) {
                if (count < 5) {
                    AccountJournalVoucherEntryBean bean = new AccountJournalVoucherEntryBean();
                    BeanUtils.copyProperties(accountVoucherEntryEntity, bean);
                    if(accountVoucherEntryEntity.getEntryType()!=null) {
                    	try {
							bean.setEntryFrom(CommonMasterUtility.getCPDDescription(accountVoucherEntryEntity.getEntryType(),MainetConstants.FlagE));
						} catch (Exception e) {
							e.printStackTrace();
						}
                    }
                    list.add(bean);
                    count++;
                }
            }
        }
        return list;
    }

    @Override
    @Transactional
    public void updateVoucherIdInDeposit(Long refNo, Long voucherId, Long orgId) {
        if (refNo != null && voucherId != null && orgId != null) {
            accountDepositJparepository.updateVoucherIdInDeposit(refNo, voucherId, orgId);
        }
        LOGGER.error("voucherId update in deposit table and inputs are refNo=" + refNo + "and voucherId=" + voucherId
                + "and orgId=" + orgId + "if these are not present then it will not update");

    }

}

// journalVoucherDao.saveVoucherEntryExcelData(accountVoucherEntryEntity);
