
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountBudgetProjectedExpenditureDao;
import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;
import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureHistEntity;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureDto;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureUploadDto;
import com.abm.mainet.account.dto.AccountIncomeAndExpenditureDto;
import com.abm.mainet.account.mapper.AccountBudgetProjectedExpenditureServiceMapper;
import com.abm.mainet.account.repository.BillEntryRepository;
import com.abm.mainet.account.repository.BudgetHeadRepository;
import com.abm.mainet.account.repository.BudgetProjectedExpenditureRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.dto.VendorBillExpDetailDTO;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author prasad.kancharla
 *
 */
@Component
public class AccountBudgetProjectedExpenditureServiceImpl implements AccountBudgetProjectedExpenditureService {

    @Resource
    private BudgetProjectedExpenditureRepository accountBudgetProjectedExpenditureJpaRepository;

    @Resource
    private AccountBudgetProjectedExpenditureServiceMapper accountBudgetProjectedExpenditureServiceMapper;

    @Resource
    private AccountBudgetProjectedExpenditureDao accountBudgetProjectedExpenditureDao;

    @Resource
    private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;

    @Resource
    private TbDepartmentJpaRepository departmentRepository;

    @Resource
    private BillEntryRepository billEntryRepository;

    @Resource
    private BudgetHeadRepository budgetHeadRepository;

    @Resource
    private TbFinancialyearJpaRepository financialYearJpaRepository;

    @Autowired
    private TbFinancialyearService financialyearService;

    @Resource
    private BudgetCodeService budgetCodeService;

    @Resource
    private BillEntryRepository billEntryServiceJpaRepository;
    @Resource
    private AuditService auditService;
    @Resource
    private AccountFieldMasterService accountFieldMasterService;
    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;

    private static Logger LOGGER = Logger.getLogger(AccountBudgetProjectedExpenditureServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public Boolean isBudgeExpAlreadyEntered(final Long faYearid, final Long prBudgetCodeid, final Long orgId,Long deptId,Long fieldId) {

        Boolean isBudgeExpAlreadyEntered = false;

        List<AccountBudgetProjectedExpenditureEntity> list = accountBudgetProjectedExpenditureDao
                .getBudgetProjectedExpenditureByFinYearBudgetCode(faYearid, prBudgetCodeid, orgId,deptId,fieldId);

        if (list != null && !list.isEmpty()) {
            isBudgeExpAlreadyEntered = true;
        }
        return isBudgeExpAlreadyEntered;
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean isCombinationCheckTransactions(final Long prExpenditureId, final Long faYearId, final Long orgId) {

        return accountBudgetProjectedExpenditureDao.isCombinationCheckTransactions(prExpenditureId, faYearId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedExpenditureBean> findBudgetProjectedExpenditureByFinId(final Long faYearid,
            final Long orgId) {
        final List<AccountBudgetProjectedExpenditureEntity> entities = accountBudgetProjectedExpenditureJpaRepository
                .findByFinancialIdDeptId(faYearid, orgId,UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid());
        final List<AccountBudgetProjectedExpenditureBean> bean = new ArrayList<>();
        for (final AccountBudgetProjectedExpenditureEntity accountBudgetProjectedExpenditureEntity : entities) {
            bean.add(accountBudgetProjectedExpenditureServiceMapper
                    .mapAccountBudgetProjectedExpenditureEntityToAccountBudgetProjectedExpenditureBean(
                            accountBudgetProjectedExpenditureEntity));
        }
        final List<AccountBudgetProjectedExpenditureBean> bean1 = new ArrayList<>();
        for (AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : bean) {
            AccountBudgetProjectedExpenditureBean dto = new AccountBudgetProjectedExpenditureBean();
            BeanUtils.copyProperties(accountBudgetProjectedExpenditureBean, dto);
            String expAmount = "";
            /*expAmount = getAllExpenditureAmount(faYearid, accountBudgetProjectedExpenditureBean.getPrBudgetCodeid(),
                    orgId);*/
            expAmount=getAllExpenditureAmountByDeptIdFieldId(faYearid, accountBudgetProjectedExpenditureBean.getPrBudgetCodeid(),
                    orgId,accountBudgetProjectedExpenditureBean.getDpDeptid(),accountBudgetProjectedExpenditureBean.getFieldId());
            String amt = getAllAcrualExpenditureAmountFieldId(faYearid, accountBudgetProjectedExpenditureBean.getPrBudgetCodeid(),
                    orgId,accountBudgetProjectedExpenditureBean.getDpDeptid(),accountBudgetProjectedExpenditureBean.getFieldId());
            dto.setExpenditureAmt(expAmount);
            dto.setAccrualAmount(amt);
            AccountFieldMasterBean fieldMaster=null;
			if (accountBudgetProjectedExpenditureBean.getFieldId() != null)
				fieldMaster = tbAcFieldMasterService.findById(accountBudgetProjectedExpenditureBean.getFieldId());
			if (fieldMaster != null) {
				dto.setFieldCode(fieldMaster.getFieldCompcode() + " " + fieldMaster.getFieldDesc());
			}
			if (accountBudgetProjectedExpenditureBean.getCurYrSpamt() != null) {
				dto.setCurYramt(accountBudgetProjectedExpenditureBean.getCurYrSpamt().toString());
            }
			 if (accountBudgetProjectedExpenditureBean.getNxtYrSpamt() != null) {
				 dto.setNxtYramt(accountBudgetProjectedExpenditureBean.getNxtYrSpamt().toString());
            }
            
            bean1.add(dto);
        }
        return bean1;
    }

    @Override
    @Transactional
    public AccountBudgetProjectedExpenditureBean saveBudgetProjectedExpenditureFormData(
            final AccountBudgetProjectedExpenditureBean tbAcBudgetProjectedExpenditure, final Organisation orgid,
            final int langId) {

        final AccountBudgetProjectedExpenditureBean budgetProjectedExpenditure = tbAcBudgetProjectedExpenditure;
        AccountBudgetProjectedExpenditureEntity accountBudgetProjectedExpenditureEntity = null;
        AccountBudgetProjectedExpenditureHistEntity accountBudgetProjectedExpenditureHistEntity = null;

        final List<AccountBudgetProjectedExpenditureDto> budgetProjectedExpenditureDto = budgetProjectedExpenditure
                .getBugExpenditureMasterDtoList();
        if ((budgetProjectedExpenditureDto != null) && !budgetProjectedExpenditureDto.isEmpty()) {
            for (final AccountBudgetProjectedExpenditureDto accountBudgetProjectedExpenditureDto : budgetProjectedExpenditureDto) {
                budgetProjectedExpenditure.setPrBudgetCodeid(accountBudgetProjectedExpenditureDto.getPrBudgetCodeid());
                if ((accountBudgetProjectedExpenditureDto.getOrginalEstamt() != null)
                        && !accountBudgetProjectedExpenditureDto.getOrginalEstamt().isEmpty()) {
                    budgetProjectedExpenditure.setOrginalEstamt(accountBudgetProjectedExpenditureDto.getOrginalEstamt()
                            .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK));
                    budgetProjectedExpenditure.setPrBalanceAmt(accountBudgetProjectedExpenditureDto.getOrginalEstamt()
                            .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK));
                }
                
                if(accountBudgetProjectedExpenditureDto.getCurYrSpamt() != null) {
                	budgetProjectedExpenditure.setCurYrSpamt(accountBudgetProjectedExpenditureDto.getCurYrSpamt());
                }
                
                if(accountBudgetProjectedExpenditureDto.getNxtYrSpamt() != null){
                	budgetProjectedExpenditure.setNxtYrSpamt(accountBudgetProjectedExpenditureDto.getNxtYrSpamt());
                }
                
                if ((accountBudgetProjectedExpenditureDto.getRevisedEstamt() != null)
                        && !accountBudgetProjectedExpenditureDto.getRevisedEstamt().isEmpty()) {
                    budgetProjectedExpenditure.setRevisedEstamt(accountBudgetProjectedExpenditureDto.getRevisedEstamt()
                            .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK));
                }

                accountBudgetProjectedExpenditureEntity = new AccountBudgetProjectedExpenditureEntity();
                accountBudgetProjectedExpenditureServiceMapper
                        .mapAccountBudgetProjectedExpenditureBeanToAccountBudgetProjectedExpenditureEntity(
                                budgetProjectedExpenditure, accountBudgetProjectedExpenditureEntity);
                AccountBudgetProjectedExpenditureEntity finalEntity = accountBudgetProjectedExpenditureJpaRepository
                        .save(accountBudgetProjectedExpenditureEntity);

                if ((tbAcBudgetProjectedExpenditure.getPrExpenditureid() != null)
                        && (tbAcBudgetProjectedExpenditure.getPrExpenditureid() > 0L)) {
                    accountBudgetProjectedExpenditureHistEntity = new AccountBudgetProjectedExpenditureHistEntity();
                    accountBudgetProjectedExpenditureHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
                    try {
                        auditService.createHistory(finalEntity, accountBudgetProjectedExpenditureHistEntity);
                    } catch (Exception ex) {
                        LOGGER.error("Could not make audit entry for " + finalEntity, ex);
                    }
                } else {
                    accountBudgetProjectedExpenditureHistEntity = new AccountBudgetProjectedExpenditureHistEntity();
                    accountBudgetProjectedExpenditureHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
                    try {
                        auditService.createHistory(finalEntity, accountBudgetProjectedExpenditureHistEntity);
                    } catch (Exception ex) {
                        LOGGER.error("Could not make audit entry for " + finalEntity, ex);
                    }
                }
            }
        }

        return tbAcBudgetProjectedExpenditure;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountBudgetProjectedExpenditureBean getDetailsUsingProjectionId(
            final AccountBudgetProjectedExpenditureBean tbAcBudgetProjectedExpenditure, final Long orgId) {

        final Long PrProjectionid = tbAcBudgetProjectedExpenditure.getPrExpenditureid().longValue();
        final AccountBudgetProjectedExpenditureEntity accountBudgetProjectedExpenditureEntity = accountBudgetProjectedExpenditureJpaRepository
                .findOne(PrProjectionid);

        if ((accountBudgetProjectedExpenditureEntity.getTbDepartment() != null)
                && (accountBudgetProjectedExpenditureEntity.getTbDepartment().getDpDeptid() != null)) {
            tbAcBudgetProjectedExpenditure
                    .setDpDeptid(accountBudgetProjectedExpenditureEntity.getTbDepartment().getDpDeptid());
            tbAcBudgetProjectedExpenditure.setDepartmentDesc(departmentRepository
                    .fetchDepartmentDescById(accountBudgetProjectedExpenditureEntity.getTbDepartment().getDpDeptid()));
        }
        if (accountBudgetProjectedExpenditureEntity.getCpdBugsubtypeId() != null) {
            tbAcBudgetProjectedExpenditure
                    .setCpdBugsubtypeId(accountBudgetProjectedExpenditureEntity.getCpdBugsubtypeId());
            tbAcBudgetProjectedExpenditure.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(
                    PrefixConstants.BUG_SUB_PREFIX, accountBudgetProjectedExpenditureEntity.getOrgid(),
                    accountBudgetProjectedExpenditureEntity.getCpdBugsubtypeId()));
        }
        tbAcBudgetProjectedExpenditure.setFaYearid(accountBudgetProjectedExpenditureEntity.getFaYearid());
        tbAcBudgetProjectedExpenditure.setFieldId(accountBudgetProjectedExpenditureEntity.getFieldId());
        
        if(accountBudgetProjectedExpenditureEntity.getTbAcBudgetCodeMaster().getTbAcFunctionMaster()!=null)
        tbAcBudgetProjectedExpenditure.setFunctionId(accountBudgetProjectedExpenditureEntity.getTbAcBudgetCodeMaster().getTbAcFunctionMaster().getFunctionId());
        
        AccountBudgetProjectedExpenditureDto accountBudgetProjectedExpenditureDto = null;
        final List<AccountBudgetProjectedExpenditureDto> budgetProjectedExpenditureDtoList = new ArrayList<>();
        accountBudgetProjectedExpenditureDto = new AccountBudgetProjectedExpenditureDto();

        if (accountBudgetProjectedExpenditureEntity.getOrginalEstamt() != null) {
            final String amount = CommonMasterUtility
                    .getAmountInIndianCurrency(accountBudgetProjectedExpenditureEntity.getOrginalEstamt());
            accountBudgetProjectedExpenditureDto.setOrginalEstamt(amount);
        }
        
        if(accountBudgetProjectedExpenditureEntity.getCurYrSpamt() != null) {
        	 final BigDecimal curYrSpamt = accountBudgetProjectedExpenditureEntity.getCurYrSpamt();
        	 accountBudgetProjectedExpenditureDto.setCurYrSpamt(curYrSpamt);
        }
        
        if(accountBudgetProjectedExpenditureEntity.getNxtYrSpamt() != null) {
        	final BigDecimal nxtYrSpamt = accountBudgetProjectedExpenditureEntity.getNxtYrSpamt();
        	accountBudgetProjectedExpenditureDto.setNxtYrSpamt(nxtYrSpamt);
        }
        
        if ((accountBudgetProjectedExpenditureEntity.getRevisedEstamt() != null)
                && !accountBudgetProjectedExpenditureEntity.getRevisedEstamt().isEmpty()) {
            final BigDecimal bd = new BigDecimal(accountBudgetProjectedExpenditureEntity.getRevisedEstamt());
            final String amount1 = CommonMasterUtility.getAmountInIndianCurrency(bd);
            accountBudgetProjectedExpenditureDto.setRevisedEstamt(amount1);
        }

        if ((accountBudgetProjectedExpenditureEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetProjectedExpenditureEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid() != null)) {
            accountBudgetProjectedExpenditureDto.setPrBudgetCodeid(
                    accountBudgetProjectedExpenditureEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid());
            
            String sacHeadStatus = accountBudgetProjectedExpenditureJpaRepository.checkSacHeadId(
            		accountBudgetProjectedExpenditureEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid(), orgId);
            tbAcBudgetProjectedExpenditure.setSacHeadStatus(sacHeadStatus);
        
            String expenditureAmount = MainetConstants.CommonConstants.BLANK;
            List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository
                    .getFinanceYearFrmDate(accountBudgetProjectedExpenditureEntity.getFaYearid());
            Date fromDate = null;
            Date toDate = null;
            for (Object[] objects : finYearFormToDate) {
                if (objects[0] != null && objects[1] != null) {
                    fromDate = (Date) objects[0];
                    toDate = (Date) objects[1];
                }
            }
           /* final BigDecimal expenditureAmountValue = billEntryRepository.getAllExpenditureAmount(
                    accountBudgetProjectedExpenditureEntity.getFaYearid(),
                    accountBudgetProjectedExpenditureEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid(), orgId,
                    fromDate, toDate);*/
            final BigDecimal expenditureAmountValue =billEntryRepository.getAllExpenditureAmountBasedOnDeptIdFieldId( accountBudgetProjectedExpenditureEntity.getFaYearid(),
                    accountBudgetProjectedExpenditureEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid(), orgId,
                    fromDate, toDate,accountBudgetProjectedExpenditureEntity.getTbDepartment().getDpDeptid(),accountBudgetProjectedExpenditureEntity.getFieldId());
            
            if (expenditureAmountValue != null) {
                expenditureAmount = CommonMasterUtility.getAmountInIndianCurrency(expenditureAmountValue);
                accountBudgetProjectedExpenditureDto.setExpenditureAmt(expenditureAmount);
            }
        }
        budgetProjectedExpenditureDtoList.add(accountBudgetProjectedExpenditureDto);
        tbAcBudgetProjectedExpenditure.setBugExpenditureMasterDtoList(budgetProjectedExpenditureDtoList);
        return tbAcBudgetProjectedExpenditure;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findByExpOrgAmount(final Long faYearid, final Long budgCodeid, final Long orgId) {
        final List<Object[]> entities = accountBudgetProjectedExpenditureJpaRepository.findByExpOrgAmount(faYearid,
                budgCodeid, orgId);
        return entities;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedExpenditureBean> getListOfAllFinacialYearDates(final Long orgId,
            final Long faYearid) throws ParseException {

        final List<AccountBudgetProjectedExpenditureBean> orgEstimationAmmountsCopy = new ArrayList<>();
        final List<AccountBudgetProjectedExpenditureBean> orgEstimationAmmounts = new ArrayList<>();
        final List<AccountBudgetProjectedExpenditureBean> list = new ArrayList<>();
        final List<Object[]> faYearFrmDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        final DateFormat yearFormater = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        String fromdateYear;
        String todateYear;
        String date;
        String dateForNextYear;
        AccountBudgetProjectedExpenditureBean bean = null;
        if ((faYearFrmDate != null) && !faYearFrmDate.isEmpty()) {
            bean = new AccountBudgetProjectedExpenditureBean();
            Long fromDate = null;
            Long toDate = null;
            Long fromCalYearDate = null;
            Long toCalYearDate = null;
            for (final Object[] obj : faYearFrmDate) {
                fromdateYear = yearFormater.format(obj[0]);
                todateYear = yearFormater.format(obj[1]);
                AccountBudgetProjectedExpenditureBean beanOrg = null;
                for (int count = 1; 3 >= count; count++) {
                    beanOrg = new AccountBudgetProjectedExpenditureBean();
                    fromDate = Long.parseLong(fromdateYear) - count;
                    toDate = Long.parseLong(todateYear) - count;
                    beanOrg.setAtualOfLastFaYears(fromDate + MainetConstants.HYPHEN + toDate);
                    fromCalYearDate = Long.parseLong(fromdateYear);
                    toCalYearDate = Long.parseLong(todateYear);
                    date = MainetConstants.WHITE_SPACE + fromdateYear + MainetConstants.HYPHEN + todateYear
                            + MainetConstants.WHITE_SPACE;
                    dateForNextYear = ((fromCalYearDate + 1) + MainetConstants.HYPHEN + (toCalYearDate + 1));
                    beanOrg.setCurNextFinYear(dateForNextYear);
                    beanOrg.setCurFinYear(date);
                    orgEstimationAmmounts.add(beanOrg);
                }
            }
            orgEstimationAmmountsCopy.addAll(orgEstimationAmmounts);
            Collections.reverse(orgEstimationAmmountsCopy);
            for (final AccountBudgetProjectedExpenditureBean orgEstimationAmmount : orgEstimationAmmountsCopy) {
                bean.getAtualOfLastFaYearsList().add(orgEstimationAmmount.getAtualOfLastFaYears());
                bean.setCurFinYear(orgEstimationAmmount.getCurFinYear());
                bean.setCurNextFinYear(orgEstimationAmmount.getCurNextFinYear());
            }
            list.add(bean);
            orgEstimationAmmountsCopy.clear();
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findBudgetProjExpBudgetCodes(final Long faYearid, final Long cpdBugsubtypeId,
            final Long dpDeptid, final Long orgid) {

        final List<Object[]> budgetCodeList = accountBudgetProjectedExpenditureJpaRepository.findBudgetCodeId(faYearid,
                cpdBugsubtypeId, dpDeptid, orgid);

        return budgetCodeList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getBudgetProjExpDeptIds(final Long orgid) {

        final List<Object[]> deptList = accountBudgetProjectedExpenditureJpaRepository.getAllDepartmentIdsData(orgid);
        return deptList;
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllExpenditureAmount(Long faYearid, final Long prBudgetCodeId, final Long orgId) {

        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }
        final BigDecimal collectedAmountValue = billEntryRepository.getAllExpenditureAmount(faYearid, prBudgetCodeId,
                orgId, fromDate, toDate);
        String collectedAmount = "0.00";
        if (collectedAmountValue != null) {
            collectedAmount = String.valueOf(collectedAmountValue);
        }
        return collectedAmount;
    }
    
    @Override
    @Transactional(readOnly = true)
    public String getAllExpenditureAmountByDeptId(Long faYearid, final Long prBudgetCodeId, final Long orgId,final Long deptId) {

        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }
        final BigDecimal collectedAmountValue = billEntryRepository.getAllExpenditureAmountBasedOnDeptId(faYearid, prBudgetCodeId,
                orgId, fromDate, toDate,deptId);
        String collectedAmount = "0.00";
        if (collectedAmountValue != null) {
            collectedAmount = String.valueOf(collectedAmountValue);
        }
        return collectedAmount;
    }
    
    @Override
    @Transactional(readOnly = true)
    public String getAllExpenditureAmountByDeptIdFieldId(Long faYearid, final Long prBudgetCodeId, final Long orgId,final Long deptId,Long fieldId) {

        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }
        final BigDecimal collectedAmountValue = billEntryRepository.getAllExpenditureAmountBasedOnDeptIdFieldId(faYearid, prBudgetCodeId,
                orgId, fromDate, toDate,deptId,fieldId);
        String collectedAmount = "0.00";
        if (collectedAmountValue != null) {
            collectedAmount = String.valueOf(collectedAmountValue);
        }
        return collectedAmount;
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllAcrualExpenditureAmount(Long faYearid, final Long prBudgetCodeId, final Long orgId,final Long deptId) {

        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }
        final BigDecimal collectedAmountValue = billEntryRepository.getAllAccrualExpenditureAmountBasedOnDeptId(faYearid, prBudgetCodeId,
                orgId, fromDate, toDate,deptId);
        String collectedAmount = "0.00";
        if (collectedAmountValue != null) {
            collectedAmount = String.valueOf(collectedAmountValue);
        }
        return collectedAmount;
    }
    
    @Override
    @Transactional(readOnly = true)
    public String getAllAcrualExpenditureAmountFieldId(Long faYearid, final Long prBudgetCodeId, final Long orgId,final Long deptId,Long fieldId ) {

        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }
        final BigDecimal collectedAmountValue = billEntryRepository.getAllAccrualExpenditureAmountBasedOnDeptIdFieldId(faYearid, prBudgetCodeId,
                orgId, fromDate, toDate,deptId,fieldId);
        String collectedAmount = "0.00";
        if (collectedAmountValue != null) {
            collectedAmount = String.valueOf(collectedAmountValue);
        }
        return collectedAmount;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllExpBudgetHeads(final Long dpDeptid, final Long orgId) {
        final Map<Long, String> expBudgetMap = new LinkedHashMap<>();
        final List<Object[]> expBudgetList = accountBudgetProjectedExpenditureJpaRepository
                .findByAllExpBudgetHeads(dpDeptid, orgId);
        for (final Object[] objects : expBudgetList) {
            if ((objects[0] != null) && (objects[1] != null)) {
                expBudgetMap.put(Long.valueOf(objects[0].toString()), objects[1].toString());
            }
        }
        return expBudgetMap;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findBudgetProjectedExpenditureByBudgetCode(final Long budgCodeid, final Long orgId) {

        final List<Object[]> expBudgetList = accountBudgetProjectedExpenditureJpaRepository.findByOrgAmount(budgCodeid,
                orgId);
        return expBudgetList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findBudgetProjectedExpenditureByBudgetCodes(final Long budgCodeid, final Long orgId,
            final Long yearId) {

        final List<Object[]> expBudgetList = accountBudgetProjectedExpenditureJpaRepository.findByOrgAmounts(budgCodeid,
                orgId, yearId);
        return expBudgetList;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllExpBudgetHeads(final Long orgId) {
        final Map<Long, String> map = new LinkedHashMap<>();
        final List<Object[]> expBudgetHeadList = accountBudgetProjectedExpenditureJpaRepository
                .findByAllExpBudgetHeads(orgId);
        if ((expBudgetHeadList != null) && !expBudgetHeadList.isEmpty()) {
            for (final Object[] objects : expBudgetHeadList) {
                map.put((Long) objects[0], objects[1].toString());
            }
        }
        return map;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllExpBudgetHeadsFieldId(final Long orgId,final Long fieldId) {
        final Map<Long, String> map = new LinkedHashMap<>();
        final List<Object[]> expBudgetHeadList = accountBudgetProjectedExpenditureJpaRepository
                .findByAllExpBudgetHeadsFieldId(orgId,fieldId);
        if ((expBudgetHeadList != null) && !expBudgetHeadList.isEmpty()) {
            for (final Object[] objects : expBudgetHeadList) {
                map.put((Long) objects[0], objects[1].toString());
            }
        }
        return map;
    }
        
    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllExpBudgetHeadsByBudgetCodeID(final Long orgId,final Long budgetCodeId) {
        final Map<Long, String> map = new LinkedHashMap<>();
        List<Object[]> expBudgetHeadList = accountBudgetProjectedExpenditureJpaRepository
                .findByAllExpBudgetHeads(orgId);
        if(budgetCodeId!=null) {
          	Long accType = budgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,budgetCodeId);
          	expBudgetHeadList = expBudgetHeadList.stream().filter(s->s[2].toString().equals(accType.toString())).collect(Collectors.toList());
          }
        
        if ((expBudgetHeadList != null) && !expBudgetHeadList.isEmpty()) {
            for (final Object[] objects : expBudgetHeadList) {
                map.put((Long) objects[0], objects[1].toString());
            }
        }
        return map;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllExpBudgetHeadsByBudgetCodeIDFieldId(final Long orgId,final Long budgetCodeId,final Long fieldId) {
        final Map<Long, String> map = new LinkedHashMap<>();
        List<Object[]> expBudgetHeadList = accountBudgetProjectedExpenditureJpaRepository
                .findByAllExpBudgetHeadsFieldId(orgId,fieldId);
        if(budgetCodeId!=null) {
          	Long accType = budgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,budgetCodeId);
          	expBudgetHeadList = expBudgetHeadList.stream().filter(s->s[2].toString().equals(accType.toString())).collect(Collectors.toList());
          }
        
        if ((expBudgetHeadList != null) && !expBudgetHeadList.isEmpty()) {
            for (final Object[] objects : expBudgetHeadList) {
                map.put((Long) objects[0], objects[1].toString());
            }
        }
        return map;
    }
    
    

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedExpenditureBean> findAllBudgetProjectedExpenditureByOrgId(final Long orgId) {
        final List<AccountBudgetProjectedExpenditureEntity> entities = accountBudgetProjectedExpenditureJpaRepository
                .findAllBudgetProjectedExpenditureByOrgId(orgId);
        final List<AccountBudgetProjectedExpenditureBean> bean = new ArrayList<>();

        for (final AccountBudgetProjectedExpenditureEntity accountBudgetProjectedExpenditureEntity : entities) {

            bean.add(accountBudgetProjectedExpenditureServiceMapper
                    .mapAccountBudgetProjectedExpenditureEntityToAccountBudgetProjectedExpenditureBean(
                            accountBudgetProjectedExpenditureEntity));
        }
        return bean;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedExpenditureEntity> getExpenditureDetailsForBillEntryFormView(final Long orgId,
            final Long finYearId, final Long sacHeadId) {

        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }

        final AccountBudgetCodeEntity entity = budgetHeadRepository.findBudgetHeadIdBySacHeadId(sacHeadId, orgId);
        List<AccountBudgetProjectedExpenditureEntity> budProjExp = new ArrayList<AccountBudgetProjectedExpenditureEntity>();
        List<Object[]> listOfObj = null;
        if (entity != null) {
            listOfObj = accountBudgetProjectedExpenditureJpaRepository.getExpenditureDetailsForBillEntryFormView(orgId,
                    finYearId, entity.getprBudgetCodeid(), fromDate, toDate);
            if (listOfObj == null || listOfObj.size() == 0) {
                listOfObj = accountBudgetProjectedExpenditureJpaRepository
                        .getExpenditureDetailsForPaymentEntryTransactionFormView(orgId, finYearId,
                                entity.getprBudgetCodeid());
            }
        }
        if (listOfObj != null) {
            for (Object[] objects : listOfObj) {
                AccountBudgetProjectedExpenditureEntity expEntity = new AccountBudgetProjectedExpenditureEntity();
                if (objects[0] != null) {
                    expEntity.setPrExpenditureid(Long.valueOf(objects[0].toString()));
                }
                if (objects[1] != null) {
                    expEntity.setOrginalEstamt(new BigDecimal(objects[1].toString()));
                }
                if (objects[2] != null) {
                    expEntity.setRevisedEstamt(objects[2].toString());
                }
                if (objects[3] != null) {
                    // expEntity.setExpenditureAmt(null);
                    expEntity.setExpenditureAmt(new BigDecimal(objects[3].toString()));
                }
                if(objects[4]!=null) {
                	Department dept = new Department();
                    dept.setDpDeptid(Long.valueOf(objects[4].toString()));
                	expEntity.setTbDepartment(dept);
                }
                
                budProjExp.add(expEntity);
            }
        }
        return budProjExp;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedExpenditureEntity> getExpenditureDetailsForBillEntryFormViewWithFieldId(final Long orgId,
            final Long finYearId, final Long sacHeadId,final Long fieldId) {

        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }

        final AccountBudgetCodeEntity entity = budgetHeadRepository.findBudgetHeadIdBySacHeadId(sacHeadId, orgId);
        List<AccountBudgetProjectedExpenditureEntity> budProjExp = new ArrayList<AccountBudgetProjectedExpenditureEntity>();
        List<Object[]> listOfObj = null;
        if (entity != null) {
            listOfObj = accountBudgetProjectedExpenditureJpaRepository.getExpenditureDetailsForBillEntryFormViewWithFieldId(orgId,
                    finYearId, entity.getprBudgetCodeid(), fromDate, toDate,fieldId);
            if (listOfObj == null || listOfObj.size() == 0) {
                listOfObj = accountBudgetProjectedExpenditureJpaRepository
                        .getExpenditureDetailsForPaymentEntryTransactionFormViewWithFieldId(orgId, finYearId,
                                entity.getprBudgetCodeid(),fieldId);
            }
        }
        if (listOfObj != null) {
            for (Object[] objects : listOfObj) {
                AccountBudgetProjectedExpenditureEntity expEntity = new AccountBudgetProjectedExpenditureEntity();
                if (objects[0] != null) {
                    expEntity.setPrExpenditureid(Long.valueOf(objects[0].toString()));
                }
                if (objects[1] != null) {
                    expEntity.setOrginalEstamt(new BigDecimal(objects[1].toString()));
                }
                if (objects[2] != null) {
                    expEntity.setRevisedEstamt(objects[2].toString());
                }
                if (objects[3] != null) {
                    // expEntity.setExpenditureAmt(null);
                    expEntity.setExpenditureAmt(new BigDecimal(objects[3].toString()));
                }
                if(objects[4]!=null) {
                	Department dept = new Department();
                    dept.setDpDeptid(Long.valueOf(objects[4].toString()));
                	expEntity.setTbDepartment(dept);
                }
                
                budProjExp.add(expEntity);
            }
        }
        return budProjExp;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedExpenditureEntity> getExpenditureDetailsForPaymentEntryFormView(final Long orgId,
            final Long finYearId, final Long billId,Long bmId) {

    	AccountBillEntryMasterEnitity billMas = billEntryRepository.findOne(bmId);
        Long deptId=billMas.getDepartmentId().getDpDeptid();
    	List<AccountBudgetProjectedExpenditureEntity> budProjExp = new ArrayList<AccountBudgetProjectedExpenditureEntity>();
        // List<Long> sacHeadIdList =
        // billEntryServiceJpaRepository.getExpenditureDetDetails(billId, orgId);
        BigDecimal orginalEstamt = BigDecimal.ZERO;
        BigDecimal revisedEstamt = BigDecimal.ZERO;
        BigDecimal paymentAmt = BigDecimal.ZERO;
        List<Long> budgetCodeIdList = budgetHeadRepository.findBudgetHeadIdBySacHeadIdList(billId, orgId);
        if (budgetCodeIdList == null || budgetCodeIdList.isEmpty()) {

        } else {
            /*List<Object[]> listOfObj = accountBudgetProjectedExpenditureJpaRepository
                    .getExpenditureDetailsForPaymentEntryFinalView(orgId, finYearId, budgetCodeIdList);
            if (listOfObj != null) {
                for (Object[] objects : listOfObj) {
                    if (objects[0] != null) {
                        orginalEstamt = orginalEstamt.add(new BigDecimal(objects[0].toString()));
                    }
                    if (objects[1] != null) {
                        revisedEstamt = revisedEstamt.add(new BigDecimal(objects[1].toString()));
                    }
                }
            }*/
            List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
            Date fromDate = null;
            Date toDate = null;
            for (Object[] objects : finYearFormToDate) {
                if (objects[0] != null && objects[1] != null) {
                    fromDate = (Date) objects[0];
                    toDate = (Date) objects[1];
                }
            }
            //orginal query
           /* List<BigDecimal> listOfPaymentAmt = accountBudgetProjectedExpenditureJpaRepository
                    .getExpenditureDetailsForPaymentEntryFinalPaymentAmt(orgId, billId, fromDate, toDate);
            if ((listOfPaymentAmt != null) && !listOfPaymentAmt.isEmpty()) {
                for (BigDecimal obj : listOfPaymentAmt) {
                    if (obj != null) {
                        // BigDecimal totlAmount = new BigDecimal(obj.toString()).setScale(2,
                        // BigDecimal.ROUND_HALF_EVEN);
                        paymentAmt = paymentAmt.add(obj);
                    }
                }
            }*/
            //new logic start
			LookUp accrualOrCashBaseBudgetSystem = null;
			List<BigDecimal> listOfPaymentAmtDep = null;
			try {
				accrualOrCashBaseBudgetSystem = CommonMasterUtility.getValueFromPrefixLookUp(
						AccountConstants.BMC.getValue(), PrefixConstants.AccountPrefix.AIC.toString(),
						new Organisation(orgId));
			} catch (Exception e) {
				LOGGER.error("Prefix value not found " + AccountConstants.BMC.getValue(), e);
				accrualOrCashBaseBudgetSystem = null;
			}
 		   //modified query
		   if(accrualOrCashBaseBudgetSystem!=null && MainetConstants.STATUS.ACTIVE.equalsIgnoreCase(accrualOrCashBaseBudgetSystem.getOtherField())) {
			   listOfPaymentAmtDep = accountBudgetProjectedExpenditureJpaRepository
						.getAccrualExpenditureDetails(orgId, billId, fromDate,
								toDate,deptId);
		   }else {
			   listOfPaymentAmtDep = accountBudgetProjectedExpenditureJpaRepository
						.getExpenditureDetailsForPaymentEntryFinalPaymentAmtBasedOnDeparment(orgId, billId, fromDate,
								toDate,deptId);
		   }
			
			if(CollectionUtils.isNotEmpty(listOfPaymentAmtDep)) {
				for(BigDecimal obj:listOfPaymentAmtDep) {
					if(obj!=null)
					 paymentAmt = paymentAmt.add(new BigDecimal(obj.toString()));
				
				}
			}
			
		List<Object[]> listOfObjDept = accountBudgetProjectedExpenditureJpaRepository
	                    .getExpenditureDetailsForPaymentEntryFinalViewBasedOnDeptId(orgId, finYearId, budgetCodeIdList,deptId);
	            if (listOfObjDept != null) {
	                for (Object[] objects : listOfObjDept) {
	                    if (objects[0] != null) {
	                        orginalEstamt = orginalEstamt.add(new BigDecimal(objects[0].toString()));
	                    }
	                    if (objects[1] != null) {
	                        revisedEstamt = revisedEstamt.add(new BigDecimal(objects[1].toString()));
	                    }
	                }
	            }
			//new logic end
            
        }

        AccountBudgetProjectedExpenditureEntity expEntity = new AccountBudgetProjectedExpenditureEntity();
        expEntity.setPrExpenditureid(123L); // it is not using 123L value, just for adjustment purpose setting this
                                            // given value only.
        expEntity.setOrginalEstamt(orginalEstamt);
        expEntity.setRevisedEstamt(revisedEstamt.toString());
        expEntity.setExpenditureAmt(paymentAmt.setScale(2, RoundingMode.CEILING));
        budProjExp.add(expEntity);
        return budProjExp;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedExpenditureEntity> getExpenditureDetailsForPaymentEntryFormViewWithFieldId(final Long orgId,
            final Long finYearId, final Long billId,Long bmId,Long fieldId) {

    	AccountBillEntryMasterEnitity billMas = billEntryRepository.findOne(bmId);
        Long deptId=billMas.getDepartmentId().getDpDeptid();
    	List<AccountBudgetProjectedExpenditureEntity> budProjExp = new ArrayList<AccountBudgetProjectedExpenditureEntity>();
        // List<Long> sacHeadIdList =
        // billEntryServiceJpaRepository.getExpenditureDetDetails(billId, orgId);
        BigDecimal orginalEstamt = BigDecimal.ZERO;
        BigDecimal revisedEstamt = BigDecimal.ZERO;
        BigDecimal paymentAmt = BigDecimal.ZERO;
        List<Long> budgetCodeIdList = budgetHeadRepository.findBudgetHeadIdBySacHeadIdList(billId, orgId);
        if (budgetCodeIdList == null || budgetCodeIdList.isEmpty()) {

        } else {
            /*List<Object[]> listOfObj = accountBudgetProjectedExpenditureJpaRepository
                    .getExpenditureDetailsForPaymentEntryFinalView(orgId, finYearId, budgetCodeIdList);
            if (listOfObj != null) {
                for (Object[] objects : listOfObj) {
                    if (objects[0] != null) {
                        orginalEstamt = orginalEstamt.add(new BigDecimal(objects[0].toString()));
                    }
                    if (objects[1] != null) {
                        revisedEstamt = revisedEstamt.add(new BigDecimal(objects[1].toString()));
                    }
                }
            }*/
            List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
            Date fromDate = null;
            Date toDate = null;
            for (Object[] objects : finYearFormToDate) {
                if (objects[0] != null && objects[1] != null) {
                    fromDate = (Date) objects[0];
                    toDate = (Date) objects[1];
                }
            }
            //orginal query
           /* List<BigDecimal> listOfPaymentAmt = accountBudgetProjectedExpenditureJpaRepository
                    .getExpenditureDetailsForPaymentEntryFinalPaymentAmt(orgId, billId, fromDate, toDate);
            if ((listOfPaymentAmt != null) && !listOfPaymentAmt.isEmpty()) {
                for (BigDecimal obj : listOfPaymentAmt) {
                    if (obj != null) {
                        // BigDecimal totlAmount = new BigDecimal(obj.toString()).setScale(2,
                        // BigDecimal.ROUND_HALF_EVEN);
                        paymentAmt = paymentAmt.add(obj);
                    }
                }
            }*/
            //new logic start
			LookUp accrualOrCashBaseBudgetSystem = null;
			List<BigDecimal> listOfPaymentAmtDep = null;
			try {
				accrualOrCashBaseBudgetSystem = CommonMasterUtility.getValueFromPrefixLookUp(
						AccountConstants.BMC.getValue(), PrefixConstants.AccountPrefix.AIC.toString(),
						new Organisation(orgId));
			} catch (Exception e) {
				LOGGER.error("Prefix value not found " + AccountConstants.BMC.getValue(), e);
				accrualOrCashBaseBudgetSystem = null;
			}
 		   //modified query
		   if(accrualOrCashBaseBudgetSystem!=null && MainetConstants.STATUS.ACTIVE.equalsIgnoreCase(accrualOrCashBaseBudgetSystem.getOtherField())) {
			   listOfPaymentAmtDep = accountBudgetProjectedExpenditureJpaRepository
						.getAccrualExpenditureDetailsWithField(orgId, billId, fromDate,
								toDate,deptId,fieldId);
		   }else {
			   listOfPaymentAmtDep = accountBudgetProjectedExpenditureJpaRepository
						.getExpenditureDetailsForPaymentEntryFinalPaymentAmtBasedOnDeparmentWithField(orgId, billId, fromDate,
								toDate,deptId,fieldId);
		   }
			
			if(CollectionUtils.isNotEmpty(listOfPaymentAmtDep)) {
				for(BigDecimal obj:listOfPaymentAmtDep) {
					if(obj!=null)
					 paymentAmt = paymentAmt.add(new BigDecimal(obj.toString()));
				
				}
			}
			
		List<Object[]> listOfObjDept = accountBudgetProjectedExpenditureJpaRepository
	                    .getExpenditureDetailsForPaymentEntryFinalViewBasedOnDeptIdFieldId(orgId, finYearId, budgetCodeIdList,deptId,fieldId);
	            if (listOfObjDept != null) {
	                for (Object[] objects : listOfObjDept) {
	                    if (objects[0] != null) {
	                        orginalEstamt = orginalEstamt.add(new BigDecimal(objects[0].toString()));
	                    }
	                    if (objects[1] != null) {
	                        revisedEstamt = revisedEstamt.add(new BigDecimal(objects[1].toString()));
	                    }
	                }
	            }
			//new logic end
            
        }

        AccountBudgetProjectedExpenditureEntity expEntity = new AccountBudgetProjectedExpenditureEntity();
        expEntity.setPrExpenditureid(123L); // it is not using 123L value, just for adjustment purpose setting this
                                            // given value only.
        expEntity.setOrginalEstamt(orginalEstamt);
        expEntity.setRevisedEstamt(revisedEstamt.toString());
        expEntity.setExpenditureAmt(paymentAmt.setScale(2, RoundingMode.CEILING));
        budProjExp.add(expEntity);
        return budProjExp;
    }

    @SuppressWarnings("null")
    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedExpenditureEntity> getExpenditureDetailsForDirectPaymentEntryFormView(
            final Long orgId, final Long finYearId, final Long budgetCodeId) {

        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }

        AccountBudgetCodeEntity entity = budgetHeadRepository.findBudgetHeadIdBySacHeadId(budgetCodeId, orgId);

        List<AccountBudgetProjectedExpenditureEntity> budProjExp = new ArrayList<AccountBudgetProjectedExpenditureEntity>();
        List<Object[]> listOfObj = null;
        if (entity != null) {
            listOfObj = accountBudgetProjectedExpenditureJpaRepository.getExpenditureDetailsForPaymentEntryFormView(
                    orgId, finYearId, entity.getprBudgetCodeid(), fromDate, toDate);
            if (listOfObj == null || listOfObj.size() == 0) {
                listOfObj = accountBudgetProjectedExpenditureJpaRepository
                        .getExpenditureDetailsForPaymentEntryTransactionFormView(orgId, finYearId,
                                entity.getprBudgetCodeid());
            }
        }
        if (listOfObj != null) {
            for (Object[] objects : listOfObj) {
                AccountBudgetProjectedExpenditureEntity expEntity = new AccountBudgetProjectedExpenditureEntity();
                if (objects[0] != null) {
                    expEntity.setPrExpenditureid(Long.valueOf(objects[0].toString()));
                }
                if (objects[1] != null) {
                    expEntity.setOrginalEstamt(new BigDecimal(objects[1].toString()));
                }
                if (objects[2] != null) {
                    expEntity.setRevisedEstamt(objects[2].toString());
                }
                if (objects[3] != null) {
                    BigDecimal totlAmount = new BigDecimal(objects[3].toString()).setScale(2,
                            BigDecimal.ROUND_HALF_EVEN);
                    expEntity.setExpenditureAmt(totlAmount);
                }
                budProjExp.add(expEntity);
            }
        }
        return budProjExp;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountBillEntryService#viewExpenditureDetails (java.lang.Long, java.lang.Long,
     * java.math.BigDecimal)
     */
    @Override
    @Transactional(readOnly = true)
    public VendorBillApprovalDTO viewExpenditureDetails(final Long orgId, final Long budgetCodeId,
            final BigDecimal sanctionedAmount) {

        final FinancialYear financialYear = financialYearJpaRepository.getFinanciaYearId(new Date());
        final Long finYearId = financialYear.getFaYear();
        final List<AccountBudgetProjectedExpenditureEntity> expenditureList = getExpenditureDetailsForBillEntryFormView(
                orgId, finYearId, budgetCodeId);
        VendorBillApprovalDTO billApprovalDto = new VendorBillApprovalDTO();
        VendorBillExpDetailDTO expDetailDto = null;
        final List<VendorBillExpDetailDTO> expBudgetDetailList = new ArrayList<>();
        BigDecimal balanceProvisionAmt = null;
        BigDecimal newBalanceAmount = null;
        BigDecimal expenditureAmt = null;
        if ((expenditureList != null) && !expenditureList.isEmpty()) {
            for (final AccountBudgetProjectedExpenditureEntity prExpDet : expenditureList) {
                expDetailDto = new VendorBillExpDetailDTO();
                expDetailDto.setProjectedExpenditureId(prExpDet.getPrExpenditureid());
                if (prExpDet.getRevisedEstamt() == null) {
                    expDetailDto.setOriginalEstimate(
                            CommonMasterUtility.getAmountInIndianCurrency(prExpDet.getOrginalEstamt()));// String
                } else {
                    expDetailDto.setOriginalEstimate(
                            CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(prExpDet.getRevisedEstamt())));// String
                }
                if (prExpDet.getExpenditureAmt() == null) {
                    expenditureAmt = BigDecimal.ZERO;
                    prExpDet.setExpenditureAmt(BigDecimal.ZERO);
                    expDetailDto.setBalanceAmount(CommonMasterUtility.getAmountInIndianCurrency(expenditureAmt)); // String
                    if (prExpDet.getRevisedEstamt() == null) {
                        balanceProvisionAmt = prExpDet.getOrginalEstamt()
                                .subtract(expenditureAmt.add(sanctionedAmount));
                    } else {
                        balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
                                .subtract(expenditureAmt.add(sanctionedAmount));
                    }
                    newBalanceAmount = expenditureAmt.add(sanctionedAmount);
                    if (prExpDet.getRevisedEstamt() == null) {
                        prExpDet.getExpenditureAmt().add(sanctionedAmount).compareTo(prExpDet.getOrginalEstamt());
                    } else {
                        prExpDet.getExpenditureAmt().add(sanctionedAmount)
                                .compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
                    }
                } else {
                    expDetailDto.setBalanceAmount(
                            CommonMasterUtility.getAmountInIndianCurrency(prExpDet.getExpenditureAmt()));// String
                    if (prExpDet.getRevisedEstamt() == null) {
                        balanceProvisionAmt = prExpDet.getOrginalEstamt()
                                .subtract((prExpDet.getExpenditureAmt().add(sanctionedAmount)));
                    } else {
                        balanceProvisionAmt = new BigDecimal(prExpDet.getRevisedEstamt())
                                .subtract((prExpDet.getExpenditureAmt().add(sanctionedAmount)));
                    }
                    newBalanceAmount = prExpDet.getExpenditureAmt().add(sanctionedAmount);
                    if (prExpDet.getRevisedEstamt() == null) {
                        prExpDet.getExpenditureAmt().add(sanctionedAmount).compareTo(prExpDet.getOrginalEstamt());
                    } else {
                        prExpDet.getExpenditureAmt().add(sanctionedAmount)
                                .compareTo(new BigDecimal(prExpDet.getRevisedEstamt()));
                    }
                }
                expDetailDto.setNewBalanceAmount(CommonMasterUtility.getAmountInIndianCurrency(newBalanceAmount));// String
                expDetailDto.setBalProvisionAmount(CommonMasterUtility.getAmountInIndianCurrency(balanceProvisionAmt));
                expBudgetDetailList.add(expDetailDto);
            }
            billApprovalDto.setExpDetListDto(expBudgetDetailList);
        } else {
            billApprovalDto = null;
        }
        return billApprovalDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountBudgetProjectedExpenditureBean> findExpenditureDataByFinYearId(Long orgId, Long finYearId) {

        final List<AccountBudgetProjectedExpenditureEntity> expenditureEntitiyList = accountBudgetProjectedExpenditureJpaRepository
                .findExpenditureDataByFinYearId(orgId, finYearId);
        final List<AccountBudgetProjectedExpenditureBean> expenditureBean = new ArrayList<>();
        for (final AccountBudgetProjectedExpenditureEntity accountBudgetProjectedExpenditureEntity : expenditureEntitiyList) {
            expenditureBean.add(accountBudgetProjectedExpenditureServiceMapper
                    .mapAccountBudgetProjectedExpenditureEntityToAccountBudgetProjectedExpenditureBean(
                            accountBudgetProjectedExpenditureEntity));
        }
        return expenditureBean;

    }
    @Override
    @Transactional(readOnly = true)
    public Long getDepartmentFromBudgetProjectedExpenditureByBudgetCode(final Long orgId, final Long budgetCode,
            final Long finYearId) {
        return accountBudgetProjectedExpenditureJpaRepository.getDepartmentByBudget(orgId, budgetCode, finYearId);
    }

    @Override
    @Transactional(readOnly = true)
    public VendorBillApprovalDTO getExpenditureDetails(final Long orgId, final Long languageId) {

        VendorBillApprovalDTO billApprovalDto = new VendorBillApprovalDTO();
        List<AccountBudgetProjectedExpenditureBean> projectedExpList = null;
        List<VendorBillExpDetailDTO> expenditureDetailList = null;
        VendorBillExpDetailDTO expenditureDetailDto = null;

        final FinancialYear financialYear = financialyearService.getFinanciaYearByDate(new Date());
        final Organisation organisation = new Organisation();
        if (financialYear != null) {
            final Long finYearId = financialYear.getFaYear();
            final List<String> budgetCodeList = new ArrayList<>();
            projectedExpList = findExpenditureDataByFinYearId(orgId, finYearId);
            expenditureDetailList = new ArrayList<>();
            final List<LookUp> expenditureLookUpList = new ArrayList<>();
            if ((projectedExpList != null) && !projectedExpList.isEmpty()) {
                for (final AccountBudgetProjectedExpenditureBean expPac : projectedExpList) {
                    expenditureDetailDto = new VendorBillExpDetailDTO();
                    final List<Object[]> expenditureList = budgetCodeService.getExpenditutreBudgetHeads(orgId,
                            expPac.getPrBudgetCodeid());
                    if ((expenditureList != null) && !expenditureList.isEmpty()) {
                        LookUp lookUp = null;
                        for (final Object[] expArray : expenditureList) {
                            lookUp = new LookUp();
                            organisation.setOrgid(orgId);
                            final LookUp transactionHeadBudgetCode = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                                    AccountConstants.BC.getValue(), AccountPrefix.TSH.toString(), languageId.intValue(),
                                    organisation);
                            if (transactionHeadBudgetCode.getDefaultVal().equals(MainetConstants.MENU.Y)) {
                                lookUp.setLookUpId((Long) expArray[0]);
                                lookUp.setDescLangFirst(expArray[1].toString());
                                expenditureLookUpList.add(lookUp);
                            } else {
                                lookUp.setLookUpId((Long) expArray[0]);
                                lookUp.setDescLangFirst(expArray[1].toString());
                                expenditureLookUpList.add(lookUp);
                            }
                        }
                    }
                    budgetCodeList.add(expPac.getPrExpBudgetCode());
                }
            }
            expenditureDetailList.add(expenditureDetailDto);
            billApprovalDto.setLookUpList(expenditureLookUpList);
        } else {
            billApprovalDto.setExpenditureExistsFlag(MainetConstants.MENU.N);
            billApprovalDto = new VendorBillApprovalDTO();
        }
        return billApprovalDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedExpenditureEntity> getBudgetProjectedExpenditure(Long orgId) {

        return accountBudgetProjectedExpenditureJpaRepository.getBudgetProjectedExpenditure(orgId);
    }

    @Override
    @Transactional
    public void saveBudgetProjectedExpenditureExportData(
            AccountBudgetProjectedExpenditureUploadDto accountBudgetProjectedExpenditureUploadDto, long orgId,
            int langId) {
        AccountBudgetProjectedExpenditureEntity accountBudgetProjectedExpenditureEntity = new AccountBudgetProjectedExpenditureEntity();
        accountBudgetProjectedExpenditureEntity.setOrgid(accountBudgetProjectedExpenditureUploadDto.getOrgid());
        accountBudgetProjectedExpenditureEntity.setUserId(accountBudgetProjectedExpenditureUploadDto.getUserId());
        accountBudgetProjectedExpenditureEntity
                .setLangId(accountBudgetProjectedExpenditureUploadDto.getLangId().intValue());
        accountBudgetProjectedExpenditureEntity.setLmoddate(accountBudgetProjectedExpenditureUploadDto.getLmoddate());
        accountBudgetProjectedExpenditureEntity.setLgIpMac(accountBudgetProjectedExpenditureUploadDto.getLgIpMac());
        accountBudgetProjectedExpenditureEntity
                .setFaYearid(Long.valueOf(accountBudgetProjectedExpenditureUploadDto.getBudgetYear()));
        final Department dept = new Department();
        dept.setDpDeptid(Long.valueOf(accountBudgetProjectedExpenditureUploadDto.getDepartment()));
        accountBudgetProjectedExpenditureEntity.setTbDepartment(dept);
        final AccountBudgetCodeEntity budgetCodeEntity = new AccountBudgetCodeEntity();
        budgetCodeEntity.setprBudgetCodeid(Long.valueOf(accountBudgetProjectedExpenditureUploadDto.getBudgetHead()));
        if (accountBudgetProjectedExpenditureUploadDto.getBudgetSubType() != null
                && !accountBudgetProjectedExpenditureUploadDto.getBudgetSubType().isEmpty()) {
            accountBudgetProjectedExpenditureEntity
                    .setCpdBugsubtypeId(Long.valueOf(accountBudgetProjectedExpenditureUploadDto.getBudgetSubType()));
        }
        accountBudgetProjectedExpenditureEntity.setTbAcBudgetCodeMaster(budgetCodeEntity);
        accountBudgetProjectedExpenditureEntity
                .setOrginalEstamt(new BigDecimal(accountBudgetProjectedExpenditureUploadDto.getOriginalBudget()));
        if (accountBudgetProjectedExpenditureUploadDto.getBudgetSubType() != null
                && !accountBudgetProjectedExpenditureUploadDto.getBudgetSubType().isEmpty()) {
            budgetCodeEntity
                    .setcpdBugsubtypeId(Long.valueOf(accountBudgetProjectedExpenditureUploadDto.getBudgetSubType()));
        }
        Long filedId = accountFieldMasterService.getFieldIdByFieldCompositCode(accountBudgetProjectedExpenditureUploadDto.getField(), orgId);
        accountBudgetProjectedExpenditureEntity.setFieldId(filedId);
        accountBudgetProjectedExpenditureJpaRepository.save(accountBudgetProjectedExpenditureEntity);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean getBudgetFlagExists(Long orgId, Long budgetCodeId) {

        final Long status = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.CommonConstants.ACTIVE,
                AccountPrefix.ACN.toString(), orgId);
        Long count = budgetHeadRepository.getBudgetFlagExists(budgetCodeId, orgId, status);
        if (count == null || count == 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<AccountBudgetProjectedExpenditureBean> findByGridAllData(Long faYearid, Long fundId, Long functionId,
            Long cpdBugsubtypeId, Long dpDeptid, Long prBudgetCodeid, Long fieldId,Long orgId) {
    	List<Object[]> entities=null;
    	 final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                 MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                 UserSession.getCurrent().getOrganisation());
    	 for (final LookUp lookUp : fundTypeLevel) {
             if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
            	  entities = accountBudgetProjectedExpenditureDao.findByGridAllData(faYearid, fundId, functionId,
                         cpdBugsubtypeId, dpDeptid, prBudgetCodeid,fieldId, orgId);
             }
             if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
            	 entities = accountBudgetProjectedExpenditureDao.findByGridAllDatas(faYearid, fundId, functionId,
                         cpdBugsubtypeId, dpDeptid, prBudgetCodeid,fieldId, orgId);
             }
    	 }
        List<AccountBudgetProjectedExpenditureBean> chList = new ArrayList<>();
        if (entities != null) {
            for (Object[] object : entities) {
                AccountBudgetProjectedExpenditureBean bean = new AccountBudgetProjectedExpenditureBean();
                bean.setPrExpenditureid((Long) (object[0]));
                if (object[1] != null) {
                    String expAmount = "";
                    //expAmount = getAllExpenditureAmount(faYearid, (Long) object[1], orgId);
                    Department department=(Department) object[6];
                    expAmount=getAllExpenditureAmountByDeptIdFieldId(faYearid, (Long) object[1], orgId,department.getDpDeptid(),(Long) object[5]);
                    String accrualExpAmt = getAllAcrualExpenditureAmountFieldId(faYearid, (Long) object[1], orgId,department.getDpDeptid(),(Long) object[5]);
                    bean.setExpenditureAmt(expAmount);
                    bean.setAccrualAmount(accrualExpAmt);
                }
                bean.setPrExpBudgetCode(object[2].toString());
                bean.setOrginalEstamt(object[3].toString());
                if (object[4] != null) {
                    bean.setRevisedEstamt(object[4].toString());
                }
                
                if(object[6] != null){
                	Department deparment=(Department) object[6];
                	bean.setDpDeptid(deparment.getDpDeptid());
                	bean.setDpDeptName(deparment.getDpDeptdesc());
                }
                bean.setFaYearid(faYearid);
                AccountFieldMasterBean fieldMaster=null;
                if ((Long) object[5] != null)
    				fieldMaster = tbAcFieldMasterService.findById((Long) object[5]);
    			if (fieldMaster != null) {
    				bean.setFieldCode(fieldMaster.getFieldCompcode() + " " + fieldMaster.getFieldDesc());
    			}
    			 if (object[7] != null) {
                     bean.setCurYramt(object[7].toString());
                 }
    			 if (object[8] != null) {
                     bean.setNxtYramt(object[8].toString());
                 }
                chList.add(bean);
            }
        }
        return chList;
    }

    @Override
    @Transactional(readOnly = true)
    public VendorBillApprovalDTO viewInvoiceSalaryBillBudgetDetails(VendorBillApprovalDTO vendorApprovalDto) {

        VendorBillApprovalDTO dto = null;
        final Long orgId = vendorApprovalDto.getOrgId();
        final Long finYearId = vendorApprovalDto.getFaYearid();
        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }
        final AccountBudgetCodeEntity entity = budgetHeadRepository
                .findBudgetHeadIdBySacHeadId(vendorApprovalDto.getBudgetCodeId(), orgId);
        List<Object[]> listOfObj = null;
        if (entity != null) {
        	if(vendorApprovalDto.getFieldId()!=null) {
            listOfObj = accountBudgetProjectedExpenditureJpaRepository.getExpDetailsForInvoiceBillEntryFormBudgetViewWithField(
                    orgId, finYearId, entity.getprBudgetCodeid(), fromDate, toDate,
                    vendorApprovalDto.getDepartmentId(),vendorApprovalDto.getFieldId());
        	}else {
        		listOfObj = accountBudgetProjectedExpenditureJpaRepository.getExpDetailsForInvoiceBillEntryFormBudgetView(
                        orgId, finYearId, entity.getprBudgetCodeid(), fromDate, toDate,
                        vendorApprovalDto.getDepartmentId());
        	}
            if (listOfObj == null || listOfObj.size() == 0) {
                listOfObj = accountBudgetProjectedExpenditureJpaRepository
                        .getExpDetailsForPayEntryTransFormInvoiceBillBudgetView(orgId, finYearId,
                                entity.getprBudgetCodeid(), vendorApprovalDto.getDepartmentId());
            }
        }
        AccountBudgetProjectedExpenditureEntity expEntity = null;
        if (listOfObj != null) {
            for (Object[] objects : listOfObj) {
                expEntity = new AccountBudgetProjectedExpenditureEntity();
                if (objects[0] != null) {
                    expEntity.setPrExpenditureid(Long.valueOf(objects[0].toString()));
                }
                if (objects[1] != null) {
                    expEntity.setOrginalEstamt(new BigDecimal(objects[1].toString()));
                }
                if (objects[2] != null) {
                    expEntity.setRevisedEstamt(objects[2].toString());
                }
                if (objects[3] != null) {
                    // expEntity.setExpenditureAmt(null);
                    expEntity.setExpenditureAmt(new BigDecimal(objects[3].toString()));
                }
            }
        }
        if (expEntity != null) {
            dto = new VendorBillApprovalDTO();
            if (expEntity.getRevisedEstamt() != null && !expEntity.getRevisedEstamt().isEmpty()) {
                dto.setInvoiceAmount(new BigDecimal(expEntity.getRevisedEstamt()).setScale(2, BigDecimal.ROUND_UP));
                BigDecimal expenditureAmt = BigDecimal.ZERO;
                if (expEntity.getExpenditureAmt() != null) {
                    expenditureAmt = expEntity.getExpenditureAmt();
                }
                dto.setSanctionedAmount(expenditureAmt.setScale(2, BigDecimal.ROUND_UP));
                BigDecimal balAmount = new BigDecimal(expEntity.getRevisedEstamt())
                        .subtract(expenditureAmt.add(vendorApprovalDto.getBillAmount()));
                dto.setNetPayables(balAmount.setScale(2, BigDecimal.ROUND_UP));
            } else {
                dto.setInvoiceAmount(expEntity.getOrginalEstamt().setScale(2, BigDecimal.ROUND_UP));
                BigDecimal expenditureAmt = BigDecimal.ZERO;
                if (expEntity.getExpenditureAmt() != null) {
                    expenditureAmt = expEntity.getExpenditureAmt();
                }
                dto.setSanctionedAmount(expenditureAmt.setScale(2, BigDecimal.ROUND_UP));
                BigDecimal balAmount = expEntity.getOrginalEstamt()
                        .subtract(expenditureAmt.add(vendorApprovalDto.getBillAmount()));
                dto.setNetPayables(balAmount.setScale(2, BigDecimal.ROUND_UP));
            }
        }
        return dto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService#
     * getIncomeAndExpenditureReportData(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String)
     */
    @SuppressWarnings({ "unlikely-arg-type", "null" })
    @Override
    public AccountIncomeAndExpenditureDto getIncomeAndExpenditureReportData(Long orgId, Long primaryAcHeadId,
            Long faYearId, String type) {

        // Getting fromDate and toDate By FinancialYearId
        Date currfromDate = null;
        Date currtoDate = null;
        final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearId);
        for (Object[] objects : faYearFromDate) {
            currfromDate = (Date) objects[0];
            currtoDate = (Date) objects[1];
        }

        // Converting Back Date of from date
        int fromDay = getDayFromDate(currfromDate);
        int fromMonth = getMonthFromDate(currfromDate);
        int fromYear = getYearFromDate(currfromDate);
        String fromDates = fromDay + "/" + fromMonth + "/" + (fromYear - 1);
        Date prevFromDate = Utility.stringToDate(fromDates);

        // Converting Back Date of To date
        int toDay = getDayFromDate(currtoDate);
        int toMonth = getMonthFromDate(currtoDate);
        int toYear = getYearFromDate(currtoDate);
        String toDates = toDay + "/" + toMonth + "/" + (toYear - 1);
        Date prevToDate = Utility.stringToDate(toDates);
        List<Object[]> listofcurrentYearIncomeReportData = null;
        List<Object[]> listofpreviousYearIncomeReportData = null;
        Organisation org = new Organisation();
		org.setOrgid(orgId);

        if (type.equals("Income")) {
        	final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL,
    				PrefixConstants.PREFIX_VALUE_IELA, Utility.getDefaultLanguageId(org), org);
            listofcurrentYearIncomeReportData = queryIncomeReportData(currfromDate, currtoDate, orgId, primaryAcHeadId,coaLookup.getLookUpId());
            listofpreviousYearIncomeReportData = queryIncomeReportData(prevFromDate, prevToDate, orgId,
                    primaryAcHeadId ,coaLookup.getLookUpId());
        } else if (type.equals("Expenditures")) {
        	final LookUp coaLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.NEC.EMPLOYEE,
    				PrefixConstants.PREFIX_VALUE_IELA, Utility.getDefaultLanguageId(org), org);
            listofcurrentYearIncomeReportData = queryExpenditureReportData(currfromDate, currtoDate, orgId,
                    primaryAcHeadId,coaLookup.getLookUpId());
            listofpreviousYearIncomeReportData = queryExpenditureReportData(prevFromDate, prevToDate, orgId,
                    primaryAcHeadId,coaLookup.getLookUpId());
        }

        // TODO Auto-generated method stub
        final AccountIncomeAndExpenditureDto setofIEDto = new AccountIncomeAndExpenditureDto();
        List<AccountIncomeAndExpenditureDto> setofFinalIEdto = null;

        // get Data depend upon value are available
        if ((!listofcurrentYearIncomeReportData.isEmpty() || listofcurrentYearIncomeReportData != null)
                && (!listofpreviousYearIncomeReportData.isEmpty() || listofpreviousYearIncomeReportData != null)) {

            setofFinalIEdto = getcurrendAndPreviousYearData(listofcurrentYearIncomeReportData,
                    listofpreviousYearIncomeReportData);

        } else if ((!listofcurrentYearIncomeReportData.isEmpty() || listofcurrentYearIncomeReportData == null)
                && (listofpreviousYearIncomeReportData.isEmpty() || listofpreviousYearIncomeReportData == null)) {

            setofFinalIEdto = getcurrendYearData(listofcurrentYearIncomeReportData);

        } else if ((!listofpreviousYearIncomeReportData.isEmpty() || listofpreviousYearIncomeReportData == null)
                && (listofcurrentYearIncomeReportData.isEmpty() || listofcurrentYearIncomeReportData == null)) {

            setofFinalIEdto = getpreviousYearData(listofpreviousYearIncomeReportData);
        }
        setofIEDto.setListOfIE(setofFinalIEdto);
        return setofIEDto;
    }

    // Get Current And Previous Year Data
    private List<AccountIncomeAndExpenditureDto> getcurrendAndPreviousYearData(
            List<Object[]> listofcurrentYearIncomeReportData, List<Object[]> listofpreviousYearIncomeReportData) {
        // TODO Auto-generated method stub
        // list of current Year data
        List<AccountIncomeAndExpenditureDto> listofcurrentYear = listofcurrentYearIncomeReportData.stream()
                .filter(obj -> obj != null).map(obj -> {
                    final AccountIncomeAndExpenditureDto obj2 = new AccountIncomeAndExpenditureDto();
                    obj2.setPrimaryAcHeadId(obj[0].toString());
                    obj2.setPrimaryAcHeadDesc(obj[1].toString());
                    obj2.setCurrentYearAmount(obj[2].toString());
                    if(obj[3]!=null)
                    obj2.setScheduleNo(obj[3].toString());
                    return obj2;
                }).collect(Collectors.toList());
        // list of previous Year data
        List<AccountIncomeAndExpenditureDto> listofpreviousYear = listofpreviousYearIncomeReportData.stream()
                .filter(obj -> obj != null).map(obj -> {
                    final AccountIncomeAndExpenditureDto obj2 = new AccountIncomeAndExpenditureDto();
                    obj2.setPrimaryAcHeadId(obj[0].toString());
                    obj2.setPrimaryAcHeadDesc(obj[1].toString());
                    obj2.setPreviousYearAmount(obj[2].toString());
                    if(obj[3]!=null)
                        obj2.setScheduleNo(obj[3].toString());
                    return obj2;
                }).collect(Collectors.toList());

        List<AccountIncomeAndExpenditureDto> finalListOfIE = new ArrayList<>();
        List<AccountIncomeAndExpenditureDto> remaningfinalListOfIE = null;

        // Compare current Year data to Previous Year data

        if (listofcurrentYearIncomeReportData.size() >= listofpreviousYearIncomeReportData.size()) {
            listofcurrentYearIncomeReportData.parallelStream().forEach(curr -> {
                listofpreviousYearIncomeReportData.parallelStream().forEach(prev -> {
                    if (prev[0].toString().equals(curr[0].toString())) {
                        final AccountIncomeAndExpenditureDto newdto = new AccountIncomeAndExpenditureDto();
                        newdto.setPrimaryAcHeadId(curr[0].toString());
                        newdto.setPrimaryAcHeadDesc(curr[1].toString());
                        newdto.setCurrentYearAmount(curr[2].toString());
                        newdto.setPreviousYearAmount(prev[2].toString());
                        if(curr[3]!=null)
                        newdto.setScheduleNo(curr[3].toString());
                        if(prev[3]!=null)
                            newdto.setScheduleNo(prev[3].toString());
                        finalListOfIE.add(newdto);
                    }
                });
            });
            remaningfinalListOfIE = listofcurrentYear.stream().filter(x -> !listofpreviousYear.contains(x))
                    .collect(Collectors.toList());

            // Compare Previous Year data to current Year data
        } else if (listofpreviousYearIncomeReportData.size() > listofcurrentYearIncomeReportData.size()) {
            listofpreviousYearIncomeReportData.parallelStream().forEach(prev -> {
                listofcurrentYearIncomeReportData.parallelStream().forEach(curr -> {
                    if (prev[0].toString().equals(curr[0].toString())) {
                        final AccountIncomeAndExpenditureDto newdto = new AccountIncomeAndExpenditureDto();
                        newdto.setPrimaryAcHeadId(prev[0].toString());
                        newdto.setPrimaryAcHeadDesc(prev[1].toString());
                        newdto.setPreviousYearAmount(prev[2].toString());
                        newdto.setCurrentYearAmount(curr[2].toString());
                        if(curr[3]!=null)
                            newdto.setScheduleNo(curr[3].toString());
                            if(prev[3]!=null)
                                newdto.setScheduleNo(prev[3].toString());
                        finalListOfIE.add(newdto);
                    }
                });
            });
            remaningfinalListOfIE = listofpreviousYear.stream().filter(x -> !listofcurrentYear.contains(x))
                    .collect(Collectors.toList());
        }

        // Not Common From current and previous year data
        remaningfinalListOfIE.parallelStream().forEach(obj -> {
            if (obj.getPrimaryAcHeadId() != null) {
                final AccountIncomeAndExpenditureDto newdto1 = new AccountIncomeAndExpenditureDto();
                newdto1.setPrimaryAcHeadId(obj.getPrimaryAcHeadId());
                newdto1.setPrimaryAcHeadDesc(obj.getPrimaryAcHeadDesc());
                newdto1.setCurrentYearAmount(obj.getCurrentYearAmount());
                newdto1.setPreviousYearAmount(obj.getPreviousYearAmount());
                newdto1.setScheduleNo(obj.getScheduleNo());
                finalListOfIE.add(newdto1);
            }
        });
        finalListOfIE.sort((AccountIncomeAndExpenditureDto h1, AccountIncomeAndExpenditureDto h2) -> h1
                .getPrimaryAcHeadId().compareTo(h2.getPrimaryAcHeadId()));
        return finalListOfIE;
    }

    // Get Current Year Data
    private List<AccountIncomeAndExpenditureDto> getcurrendYearData(List<Object[]> listofcurrentYearIncomeReportData) {
        // TODO Auto-generated method stub
        List<AccountIncomeAndExpenditureDto> listofcurrentYear = listofcurrentYearIncomeReportData.stream()
                .filter(obj -> obj != null).map(obj -> {
                    final AccountIncomeAndExpenditureDto obj2 = new AccountIncomeAndExpenditureDto();
                    obj2.setPrimaryAcHeadId(obj[0].toString());
                    obj2.setPrimaryAcHeadDesc(obj[1].toString());
                    obj2.setCurrentYearAmount(obj[2].toString());
                    if(obj[3]!=null)
                    	obj2.setScheduleNo(obj[3].toString());
                    return obj2;
                }).collect(Collectors.toList());
        return listofcurrentYear;
    }

    // Get Previous Year Data
    private List<AccountIncomeAndExpenditureDto> getpreviousYearData(
            List<Object[]> listofpreviousYearIncomeReportData) {
        // TODO Auto-generated method stub
        List<AccountIncomeAndExpenditureDto> listofpreviousYear = listofpreviousYearIncomeReportData.stream()
                .filter(obj -> obj != null).map(obj -> {
                    final AccountIncomeAndExpenditureDto obj2 = new AccountIncomeAndExpenditureDto();
                    obj2.setPrimaryAcHeadId(obj[0].toString());
                    obj2.setPrimaryAcHeadDesc(obj[1].toString());
                    obj2.setPreviousYearAmount(obj[2].toString());
                    if(obj[3]!=null)
                    	obj2.setScheduleNo(obj[3].toString());
                    return obj2;
                }).collect(Collectors.toList());
        return listofpreviousYear;
    }

    private List<Object[]> queryIncomeReportData(Date fromDate, Date toDate, Long orgId, Long primaryAcHeadId, long incExpId) {
        return accountBudgetProjectedExpenditureJpaRepository.queryIncomeReportData(fromDate, toDate, orgId,
                primaryAcHeadId,incExpId);
    }

    private List<Object[]> queryExpenditureReportData(Date fromDate, Date toDate, Long orgId, Long primaryAcHeadId, long incExpId) {
        // TODO Auto-generated method stub
        return accountBudgetProjectedExpenditureJpaRepository.queryExpenditureReportData(fromDate, toDate, orgId,
                primaryAcHeadId,incExpId);
    }

    private static int getDayFromDate(Date date) {

        int result = -1;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            result = cal.get(Calendar.DATE);
        }
        return result;
    }

    private static int getMonthFromDate(Date date) {

        int result = -1;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            result = cal.get(Calendar.MONTH) + 1;
        }
        return result;
    }

    public static int getYearFromDate(Date date) {
        int result = -1;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            result = cal.get(Calendar.YEAR);
        }
        return result;
    }

    @Override
    public List<Object[]> queryforPrimaryHead() {
        // TODO Auto-generated method stub
        return accountBudgetProjectedExpenditureJpaRepository.getPrimaryHeadId();
    }

    public AccountIncomeAndExpenditureDto getAssetsAndLiabilitiesData(Long orgId, Long primaryAcHeadId, Long faYearId,
            String type) {
        // Getting fromDate and toDate By FinancialYearId
        Date currfromDate = null;
        Date currtoDate = null;
        final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearId);
        for (Object[] objects : faYearFromDate) {
            currfromDate = (Date) objects[0];
            currtoDate = (Date) objects[1];
        }
        final AccountIncomeAndExpenditureDto setofIEDto = new AccountIncomeAndExpenditureDto();
        List<Object[]> listofReportData = null;
        List<AccountIncomeAndExpenditureDto> setofFinalIEdto = null;
        if (type.equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ASSET_OTHER_FIELD)) {
            listofReportData = queryAssetsReportData(currfromDate, currtoDate, orgId, primaryAcHeadId, faYearId);
        } else if (type.equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.LIABILITY_OTHER_FIELD)) {
            listofReportData = queryLiabilitiesReportData(currfromDate, currtoDate, orgId, primaryAcHeadId, faYearId);
        }
        if (!listofReportData.isEmpty() || listofReportData != null) {
            setofFinalIEdto = getReportAssetsAndLiabilitiesReportData(listofReportData);
        }
        setofIEDto.setListOfIE(setofFinalIEdto);
        return setofIEDto;
    }

    private List<AccountIncomeAndExpenditureDto> getReportAssetsAndLiabilitiesReportData(
            List<Object[]> listofReportData) {
        // TODO Auto-generated method stub
        List<AccountIncomeAndExpenditureDto> listofpreviousYear = listofReportData.stream().filter(obj -> obj != null)
                .map(obj -> {
                    final AccountIncomeAndExpenditureDto obj2 = new AccountIncomeAndExpenditureDto();
                    obj2.setPrimaryAcHeadId(obj[0].toString());
                    obj2.setPrimaryAcHeadDesc(obj[1].toString());
                    if (obj[2] != null) {
                        obj2.setOpeningBalannce(obj[2].toString());
                    } else {
                        obj2.setOpeningBalannce(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

                    }
                    if (obj[3] != null) {
                        obj2.setTxdramount(obj[3].toString());
                    } else {
                        obj2.setTxdramount(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                    }
                    if (obj[4] != null) {
                        obj2.setTxcramount(obj[4].toString());
                    } else {
                        obj2.setTxcramount(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                    }
                    return obj2;
                }).collect(Collectors.toList());
        return listofpreviousYear;
    }

    private List<Object[]> queryLiabilitiesReportData(Date fromDate, Date toDate, Long orgId, Long primaryAcHeadId,
            Long faYearId) {
        // TODO Auto-generated method stub
        return accountBudgetProjectedExpenditureJpaRepository.queryLiabilitiesReportData(fromDate, toDate, orgId,
                primaryAcHeadId, faYearId);
    }

    private List<Object[]> queryAssetsReportData(Date fromDate, Date toDate, Long orgId, Long primaryAcHeadId,
            Long faYearId) {
        // TODO Auto-generated method stub
        return accountBudgetProjectedExpenditureJpaRepository.queryAssetsReportData(fromDate, toDate, orgId,
                primaryAcHeadId, faYearId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getExpenditureDetails(final Long orgId,
            final Long finYearId, final Long sacHeadId,final Long fieldId,final Long deptId) {

        //List<AccountBudgetProjectedExpenditureEntity> budProjExp = new ArrayList<AccountBudgetProjectedExpenditureEntity>();
        List<Object[]> listOfObj = null;
                listOfObj = accountBudgetProjectedExpenditureJpaRepository .getExpenditureDetails(orgId, finYearId,
                        		sacHeadId,fieldId,deptId);
        return listOfObj;
    }

    
    @Override
    @Transactional(readOnly = true)
    public VendorBillApprovalDTO viewCouncilBillBudgetDetails(VendorBillApprovalDTO vendorApprovalDto) {

        VendorBillApprovalDTO dto = null;
        final Long orgId = vendorApprovalDto.getOrgId();
        final Long finYearId = vendorApprovalDto.getFaYearid();
        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(finYearId);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }
        final AccountBudgetCodeEntity entity = budgetHeadRepository
                .findBudgetHeadIdBySacHeadId(vendorApprovalDto.getBudgetCodeId(), orgId);
        List<Object[]> listOfObj = null;
        if (entity != null) {
        	if(vendorApprovalDto.getFieldId()!=null) {
            listOfObj = accountBudgetProjectedExpenditureJpaRepository.getExpDetailsForCouncilBillEntryFormBudgetViewWithField(
                    orgId, finYearId, vendorApprovalDto.getBudgetCodeId(),vendorApprovalDto.getFieldId());
        	}else {
        		listOfObj = accountBudgetProjectedExpenditureJpaRepository.getExpDetailsForInvoiceBillEntryFormBudgetView(
                        orgId, finYearId, entity.getprBudgetCodeid(), fromDate, toDate,
                        vendorApprovalDto.getDepartmentId());
        	}
            if (listOfObj == null || listOfObj.size() == 0) {
                listOfObj = accountBudgetProjectedExpenditureJpaRepository
                        .getExpDetailsForPayEntryTransFormInvoiceBillBudgetView(orgId, finYearId,
                                entity.getprBudgetCodeid(), vendorApprovalDto.getDepartmentId());
            }
        }
        AccountBudgetProjectedExpenditureEntity expEntity = null;
        if (listOfObj != null) {
            for (Object[] objects : listOfObj) {
                expEntity = new AccountBudgetProjectedExpenditureEntity();
                if (objects[2] != null) {
                    expEntity.setRevisedEstamt(objects[2].toString());
                }
                if (objects[3] != null) {
                    // expEntity.setExpenditureAmt(null);
                    expEntity.setExpenditureAmt(new BigDecimal(objects[3].toString()));
                }
            }
        }
        if (expEntity != null) {
            dto = new VendorBillApprovalDTO();
            if (expEntity.getRevisedEstamt() != null && !expEntity.getRevisedEstamt().isEmpty()) {
                dto.setInvoiceAmount(new BigDecimal(expEntity.getRevisedEstamt()).setScale(2, BigDecimal.ROUND_UP));
                BigDecimal expenditureAmt = BigDecimal.ZERO;
                if (expEntity.getExpenditureAmt() != null) {
                    expenditureAmt = expEntity.getExpenditureAmt();
                }
                dto.setSanctionedAmount(expenditureAmt.setScale(2, BigDecimal.ROUND_UP));
                BigDecimal balAmount = new BigDecimal(expEntity.getRevisedEstamt())
                        .subtract(expenditureAmt.add(vendorApprovalDto.getBillAmount()));
                dto.setNetPayables(balAmount.setScale(2, BigDecimal.ROUND_UP));
            } else {
                dto.setInvoiceAmount(expEntity.getOrginalEstamt().setScale(2, BigDecimal.ROUND_UP));
                BigDecimal expenditureAmt = BigDecimal.ZERO;
                if (expEntity.getExpenditureAmt() != null) {
                    expenditureAmt = expEntity.getExpenditureAmt();
                }
                dto.setSanctionedAmount(expenditureAmt.setScale(2, BigDecimal.ROUND_UP));
                BigDecimal balAmount = expEntity.getOrginalEstamt()
                        .subtract(expenditureAmt.add(vendorApprovalDto.getBillAmount()));
                dto.setNetPayables(balAmount.setScale(2, BigDecimal.ROUND_UP));
            }
        }
        return dto;
    }
    
}
