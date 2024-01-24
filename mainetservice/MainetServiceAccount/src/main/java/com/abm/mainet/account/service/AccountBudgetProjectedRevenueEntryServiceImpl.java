
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountBudgetProjectedRevenueEntryDao;
import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryEntity;
import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryHistEntity;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryDto;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryUploadDto;
import com.abm.mainet.account.mapper.AccountBudgetProjectedRevenueEntryServiceMapper;
import com.abm.mainet.account.repository.AccountReceiptEntryJpaRepository;
import com.abm.mainet.account.repository.BudgetProjectedRevenueRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author prasad.kancharla
 *
 */
@Component
public class AccountBudgetProjectedRevenueEntryServiceImpl implements AccountBudgetProjectedRevenueEntryService {

    @Resource
    private BudgetProjectedRevenueRepository accountBudgetProjectedRevenueEntryJpaRepository;

    @Resource
    private AccountBudgetProjectedRevenueEntryServiceMapper accountBudgetProjectedRevenueEntryServiceMapper;

    @Resource
    private AccountBudgetProjectedRevenueEntryDao accountBudgetProjectedRevenueEntryDao;

    @Resource
    private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;
    @Resource
    private TbDepartmentJpaRepository departmentRepository;
    @Resource
    private AccountReceiptEntryJpaRepository accountReceiptEntryJpaRepository;
    @Resource
    private AuditService auditService;
    @Resource
    private BudgetCodeService accountBudgetCodeService;
    @Resource
    private AccountFieldMasterService accountFieldMasterService;

    private static Logger LOGGER = Logger.getLogger(AccountBudgetProjectedRevenueEntryServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public Boolean isCombinationExists(final Long faYearid, final Long prBudgetCodeid, final Long orgId,Long deptId,Long fieldId) {

        return accountBudgetProjectedRevenueEntryDao.isCombinationExists(faYearid, prBudgetCodeid, orgId,deptId,fieldId);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean isCombinationCheckTransactions(final Long prProjectionId, final Long faYearId, final Long orgId) {

        return accountBudgetProjectedRevenueEntryDao.isCombinationCheckTransactions(prProjectionId, faYearId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedRevenueEntryBean> findByFinancialId(final Long faYearid, final Long orgId) {
        final List<AccountBudgetProjectedRevenueEntryEntity> entities = accountBudgetProjectedRevenueEntryJpaRepository
                .findByFinancialId(faYearid, orgId);
        final List<AccountBudgetProjectedRevenueEntryBean> bean = new ArrayList<>();

        for (final AccountBudgetProjectedRevenueEntryEntity accountBudgetProjectedRevenueEntryEntity : entities) {
            bean.add(accountBudgetProjectedRevenueEntryServiceMapper
                    .mapAccountBudgetProjectedRevenueEntryBeanEntityToAccountBudgetProjectedRevenueEntryBean(
                            accountBudgetProjectedRevenueEntryEntity));
        }
        final List<AccountBudgetProjectedRevenueEntryBean> bean1 = new ArrayList<>();
		for (AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : bean) {
			AccountBudgetProjectedRevenueEntryBean dto = new AccountBudgetProjectedRevenueEntryBean();
			BeanUtils.copyProperties(accountBudgetProjectedRevenueEntryBean, dto);
			String revAmount = "";
			//revAmount = getAllCollectedAmount(faYearid, accountBudgetProjectedRevenueEntryBean.getPrBudgetCodeid(),
			//		orgId);
			revAmount = getAllCollectedAmountByBasedOnDeptField(faYearid,
					accountBudgetProjectedRevenueEntryBean.getPrBudgetCodeid(), orgId,
					accountBudgetProjectedRevenueEntryBean.getDpDeptid(),accountBudgetProjectedRevenueEntryBean.getFieldId());
			dto.setPrCollected(revAmount);
			bean1.add(dto);
		}
        return bean1;
    }

    @Override
    @Transactional
    public AccountBudgetProjectedRevenueEntryBean saveBudgetProjectedRevenueEntryFormData(
            final AccountBudgetProjectedRevenueEntryBean tbAcBudgetProjectedRevenueEntry, final Organisation orgid,
            final int langId) {

        final AccountBudgetProjectedRevenueEntryBean budgetProjectedRevenueEntry = tbAcBudgetProjectedRevenueEntry;
        AccountBudgetProjectedRevenueEntryEntity accountBudgetProjectedRevenueEntryEntity = null;
        AccountBudgetProjectedRevenueEntryHistEntity accountBudgetProjectedRevenueEntryHistEntity = null;

        final List<AccountBudgetProjectedRevenueEntryDto> budgetProjectedRevenueEntryDto = budgetProjectedRevenueEntry
                .getBugRevenueMasterDtoList();
        if ((budgetProjectedRevenueEntryDto != null) && !budgetProjectedRevenueEntryDto.isEmpty()) {
            for (final AccountBudgetProjectedRevenueEntryDto accountBudgetProjectedRevenueEntryDto : budgetProjectedRevenueEntryDto) {

                budgetProjectedRevenueEntry
                        .setPrBudgetCodeid(accountBudgetProjectedRevenueEntryDto.getPrBudgetCodeid());

                if ((accountBudgetProjectedRevenueEntryDto.getOrginalEstamt() != null)
                        && !accountBudgetProjectedRevenueEntryDto.getOrginalEstamt().isEmpty()) {
                    budgetProjectedRevenueEntry
                            .setOrginalEstamt(accountBudgetProjectedRevenueEntryDto.getOrginalEstamt()
                                    .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK));
                    budgetProjectedRevenueEntry.setPrProjected(accountBudgetProjectedRevenueEntryDto.getOrginalEstamt()
                            .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK));
                }
                if ((accountBudgetProjectedRevenueEntryDto.getRevisedEstamt() != null)
                        && !accountBudgetProjectedRevenueEntryDto.getRevisedEstamt().isEmpty()) {
                    budgetProjectedRevenueEntry
                            .setRevisedEstamt(accountBudgetProjectedRevenueEntryDto.getRevisedEstamt()
                                    .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK));
                }

                accountBudgetProjectedRevenueEntryEntity = new AccountBudgetProjectedRevenueEntryEntity();
                accountBudgetProjectedRevenueEntryServiceMapper
                        .mapAccountBudgetProjectedRevenueEntryBeanToAccountBudgetProjectedRevenueEntryBeanEntity(
                                budgetProjectedRevenueEntry, accountBudgetProjectedRevenueEntryEntity);
                AccountBudgetProjectedRevenueEntryEntity finalEntity = accountBudgetProjectedRevenueEntryJpaRepository
                        .save(accountBudgetProjectedRevenueEntryEntity);

                if ((tbAcBudgetProjectedRevenueEntry.getPrProjectionid() != null)
                        && (tbAcBudgetProjectedRevenueEntry.getPrProjectionid() > 0L)) {
                    accountBudgetProjectedRevenueEntryHistEntity = new AccountBudgetProjectedRevenueEntryHistEntity();
                    accountBudgetProjectedRevenueEntryHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
                    try {
                        auditService.createHistory(finalEntity, accountBudgetProjectedRevenueEntryHistEntity);
                    } catch (Exception ex) {
                        LOGGER.error("Could not make audit entry for " + finalEntity, ex);
                    }
                } else {
                    accountBudgetProjectedRevenueEntryHistEntity = new AccountBudgetProjectedRevenueEntryHistEntity();
                    accountBudgetProjectedRevenueEntryHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
                    try {
                        auditService.createHistory(finalEntity, accountBudgetProjectedRevenueEntryHistEntity);
                    } catch (Exception ex) {
                        LOGGER.error("Could not make audit entry for " + finalEntity, ex);
                    }
                }
            }
        }
        return tbAcBudgetProjectedRevenueEntry;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountBudgetProjectedRevenueEntryBean getDetailsUsingProjectionId(
            final AccountBudgetProjectedRevenueEntryBean tbAcBudgetProjectedRevenueEntry, final Long orgId) {

        final Long PrProjectionid = tbAcBudgetProjectedRevenueEntry.getPrProjectionid();
        final AccountBudgetProjectedRevenueEntryEntity accountBudgetProjectedRevenueEntryEntity = accountBudgetProjectedRevenueEntryJpaRepository
                .findOne(PrProjectionid);

        if ((accountBudgetProjectedRevenueEntryEntity.getTbDepartment() != null)
                && (accountBudgetProjectedRevenueEntryEntity.getTbDepartment().getDpDeptid() != null)) {
            tbAcBudgetProjectedRevenueEntry
                    .setDpDeptid(accountBudgetProjectedRevenueEntryEntity.getTbDepartment().getDpDeptid());
            tbAcBudgetProjectedRevenueEntry.setDepartmentDesc(departmentRepository
                    .fetchDepartmentDescById(accountBudgetProjectedRevenueEntryEntity.getTbDepartment().getDpDeptid()));
        }
        if (accountBudgetProjectedRevenueEntryEntity.getCpdBugsubtypeId() != null) {
            tbAcBudgetProjectedRevenueEntry
                    .setCpdBugsubtypeId(accountBudgetProjectedRevenueEntryEntity.getCpdBugsubtypeId());
            tbAcBudgetProjectedRevenueEntry.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.FTP,
                    accountBudgetProjectedRevenueEntryEntity.getOrgid(),
                    accountBudgetProjectedRevenueEntryEntity.getCpdBugsubtypeId()));
        }
        tbAcBudgetProjectedRevenueEntry.setFaYearid(accountBudgetProjectedRevenueEntryEntity.getFaYearid());
        tbAcBudgetProjectedRevenueEntry.setFieldId(accountBudgetProjectedRevenueEntryEntity.getFieldId());
        
        if(accountBudgetProjectedRevenueEntryEntity.getTbAcBudgetCodeMaster().getTbAcFunctionMaster()!=null)
        tbAcBudgetProjectedRevenueEntry.setFunctionId(accountBudgetProjectedRevenueEntryEntity.getTbAcBudgetCodeMaster().getTbAcFunctionMaster().getFunctionId());
       
        AccountBudgetProjectedRevenueEntryDto accountBudgetProjectedRevenueEntryDto = null;
        final List<AccountBudgetProjectedRevenueEntryDto> bugRevenueMasterDtoList = new ArrayList<>();
        accountBudgetProjectedRevenueEntryDto = new AccountBudgetProjectedRevenueEntryDto();

        if (accountBudgetProjectedRevenueEntryEntity.getOrginalEstamt() != null) {
            final String amount = CommonMasterUtility
                    .getAmountInIndianCurrency(accountBudgetProjectedRevenueEntryEntity.getOrginalEstamt());
            accountBudgetProjectedRevenueEntryDto.setOrginalEstamt(amount);
        }
        if ((accountBudgetProjectedRevenueEntryEntity.getRevisedEstamt() != null)
                && !accountBudgetProjectedRevenueEntryEntity.getRevisedEstamt().isEmpty()) {
            final BigDecimal bd = new BigDecimal(accountBudgetProjectedRevenueEntryEntity.getRevisedEstamt());
            final String amount1 = CommonMasterUtility.getAmountInIndianCurrency(bd);
            accountBudgetProjectedRevenueEntryDto.setRevisedEstamt(amount1);
        }

        if ((accountBudgetProjectedRevenueEntryEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetProjectedRevenueEntryEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid() != null)) {
            accountBudgetProjectedRevenueEntryDto.setPrBudgetCodeid(
                    accountBudgetProjectedRevenueEntryEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid());
            String collectedAmount = MainetConstants.CommonConstants.BLANK;
            List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository
                    .getFinanceYearFrmDate(accountBudgetProjectedRevenueEntryEntity.getFaYearid());
            Date fromDate = null;
            Date toDate = null;
            for (Object[] objects : finYearFormToDate) {
                if (objects[0] != null && objects[1] != null) {
                    fromDate = (Date) objects[0];
                    toDate = (Date) objects[1];
                }
            }
            /*final BigDecimal collectedAmountValue = accountReceiptEntryJpaRepository.getAllCollectedAmount(
                    accountBudgetProjectedRevenueEntryEntity.getFaYearid(),
                    accountBudgetProjectedRevenueEntryEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid(), orgId,
                    fromDate, toDate);*/
            
            final BigDecimal collectedAmountValue=accountReceiptEntryJpaRepository.getAllCollectedAmountByBasedOnDeptIdFieldId(accountBudgetProjectedRevenueEntryEntity.getFaYearid(),
                    accountBudgetProjectedRevenueEntryEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid(), orgId,
                    fromDate, toDate,
                    accountBudgetProjectedRevenueEntryEntity.getTbDepartment().getDpDeptid(),accountBudgetProjectedRevenueEntryEntity.getFieldId());
            
            if (collectedAmountValue != null) {
                collectedAmount = CommonMasterUtility.getAmountInIndianCurrency(collectedAmountValue);
                accountBudgetProjectedRevenueEntryDto.setPrCollected(collectedAmount);
            }
        }
        bugRevenueMasterDtoList.add(accountBudgetProjectedRevenueEntryDto);
        tbAcBudgetProjectedRevenueEntry.setBugRevenueMasterDtoList(bugRevenueMasterDtoList);
        return tbAcBudgetProjectedRevenueEntry;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findByRenueOrgAmount(final Long faYearid, final Long budgCodeid, final Long orgId) {
        final List<Object[]> entities = accountBudgetProjectedRevenueEntryJpaRepository.findByRenueOrgAmount(faYearid,
                budgCodeid, orgId);
        return entities;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedRevenueEntryBean> getListOfAllFinacialYearDates(final Long orgId,
            final Long faYearid) throws ParseException {
        final List<AccountBudgetProjectedRevenueEntryBean> orgEstimationAmmountsCopy = new ArrayList<>();
        final List<AccountBudgetProjectedRevenueEntryBean> orgEstimationAmmounts = new ArrayList<>();
        final List<AccountBudgetProjectedRevenueEntryBean> list = new ArrayList<>();
        final List<Object[]> faYearFrmDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        final DateFormat yearFormater = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        String fromdateYear;
        String todateYear;
        String date;
        String dateForNextYear;
        AccountBudgetProjectedRevenueEntryBean bean = null;
        if ((faYearFrmDate != null) && !faYearFrmDate.isEmpty()) {
            bean = new AccountBudgetProjectedRevenueEntryBean();
            Long fromDate = null;
            Long toDate = null;
            Long fromCalYearDate = null;
            Long toCalYearDate = null;
            for (final Object[] obj : faYearFrmDate) {
                fromdateYear = yearFormater.format(obj[0]);
                todateYear = yearFormater.format(obj[1]);
                AccountBudgetProjectedRevenueEntryBean beanOrg = null;
                for (int count = 1; 3 >= count; count++) {
                    beanOrg = new AccountBudgetProjectedRevenueEntryBean();
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
            for (final AccountBudgetProjectedRevenueEntryBean orgEstimationAmmount : orgEstimationAmmountsCopy) {
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
    public List<Object[]> findBudgetCodeIdFromBudgetProjectedRevenueEntry(final Long faYearid,
            final Long cpdBugsubtypeId, final Long dpDeptid, final Long orgid) {

        final List<Object[]> budgetCodeList = accountBudgetProjectedRevenueEntryJpaRepository
                .findBudgetCodeIdFromBudgetProjectedRevenueEntry(faYearid, cpdBugsubtypeId, dpDeptid, orgid);

        return budgetCodeList;
    }

    /*
     * @Override
     * @Transactional public List<AccountBudgetProjectedRevenueEntryBean> findByGridAllData(final Long faYearid, final Long
     * cpdBugsubtypeId, final Long dpDeptid, final Long prBudgetCodeid, final Long orgId) { final
     * List<AccountBudgetProjectedRevenueEntryEntity> entities = accountBudgetProjectedRevenueEntryDao
     * .findByGridAllData(faYearid, cpdBugsubtypeId, dpDeptid, prBudgetCodeid, orgId); final
     * List<AccountBudgetProjectedRevenueEntryBean> bean = new ArrayList<>(); for (final AccountBudgetProjectedRevenueEntryEntity
     * accountBudgetProjectedRevenueEntryEntity : entities) { bean.add(accountBudgetProjectedRevenueEntryServiceMapper
     * .mapAccountBudgetProjectedRevenueEntryBeanEntityToAccountBudgetProjectedRevenueEntryBean(
     * accountBudgetProjectedRevenueEntryEntity)); } return bean; }
     */

    /*
     * @Override
     * @Transactional public Map<String, String> findByBudgetIds(final Long faYearid, final Long fundId, final Long functionId,
     * final Long cpdBugtypeid, final Long cpdBugsubtypeId, final Long prBudgetCodeid, final Long dpDeptid, final Long orgId) {
     * final List<Object[]> findById = accountBudgetProjectedRevenueEntryDao.findByAllBudgetCodeId(faYearid, fundId, functionId,
     * cpdBugtypeid, cpdBugsubtypeId, prBudgetCodeid, dpDeptid, orgId); final Map<String, String> map = new LinkedHashMap<>(); if
     * ((findById != null) && !findById.isEmpty()) { for (final Object[] objects : findById) { map.put(objects[0].toString(),
     * objects[1].toString()); } } return map; }
     */

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service. AccountBudgetProjectedRevenueEntryService#getBudgetCodeInRevenue(java.lang.
     * Long, long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getBudgetCodeInRevenue(final Long finYearId, final long orgid) {
        final List<Object[]> budgetList = accountBudgetProjectedRevenueEntryJpaRepository
                .getBudgetCodeInRevenue(finYearId, orgid);
        return budgetList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getAllDepartmentIdsData(final Long orgid) {

        final List<Object[]> deptList = accountBudgetProjectedRevenueEntryJpaRepository.getAllDepartmentIdsData(orgid);
        return deptList;
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllCollectedAmount(Long faYearid, final Long prBudgetCodeId, final Long orgId) {

        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }
        BigDecimal collectedAmountValue = accountReceiptEntryJpaRepository.getAllCollectedAmount(faYearid,
                prBudgetCodeId, orgId, fromDate, toDate);
        String collectedAmount = "0.00";
        if (collectedAmountValue != null) {
            collectedAmount = collectedAmountValue.toString();
        }
        return collectedAmount;
    }
    
    @Override
    @Transactional(readOnly = true)
    public String getAllCollectedAmountByBasedOnDept(Long faYearid, final Long prBudgetCodeId, final Long orgId,final Long deptId) {
        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }
        BigDecimal collectedAmountValue = accountReceiptEntryJpaRepository.getAllCollectedAmountByBasedOnDeptId(faYearid,
                prBudgetCodeId, orgId, fromDate, toDate,deptId);
        String collectedAmount = "0.00";
        if (collectedAmountValue != null) {
            collectedAmount = collectedAmountValue.toString();
        }
        return collectedAmount;
    }
    
    @Override
    @Transactional(readOnly = true)
    public String getAllCollectedAmountByBasedOnDeptField(Long faYearid, final Long prBudgetCodeId, final Long orgId,final Long deptId,final Long fieldId) {
        List<Object[]> finYearFormToDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        Date fromDate = null;
        Date toDate = null;
        for (Object[] objects : finYearFormToDate) {
            if (objects[0] != null && objects[1] != null) {
                fromDate = (Date) objects[0];
                toDate = (Date) objects[1];
            }
        }
        BigDecimal collectedAmountValue = accountReceiptEntryJpaRepository.getAllCollectedAmountByBasedOnDeptIdFieldId(faYearid,
                prBudgetCodeId, orgId, fromDate, toDate,deptId,fieldId);
        String collectedAmount = "0.00";
        if (collectedAmountValue != null) {
            collectedAmount = collectedAmountValue.toString();
        }
        return collectedAmount;
    }
    

	@Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllRevBudgetHeads(final Long orgId) {
        final Map<Long, String> map = new LinkedHashMap<>();
         List<Object[]> revBudgetHeadList = accountBudgetProjectedRevenueEntryJpaRepository
                .findByAllRevBudgetHeads(orgId);
        if ((revBudgetHeadList != null) && !revBudgetHeadList.isEmpty()) {
            for (final Object[] objects : revBudgetHeadList) {
                map.put((Long) objects[0], objects[1].toString());
            }
        }
        return map;
    }
	
	@Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllRevBudgetHeadsFieldId(final Long orgId,final Long fieldId) {
        final Map<Long, String> map = new LinkedHashMap<>();
         List<Object[]> revBudgetHeadList = accountBudgetProjectedRevenueEntryJpaRepository
                .findByAllRevBudgetHeadsWithFieldId(orgId,fieldId);
        if ((revBudgetHeadList != null) && !revBudgetHeadList.isEmpty()) {
            for (final Object[] objects : revBudgetHeadList) {
                map.put((Long) objects[0], objects[1].toString());
            }
        }
        return map;
    }
    
    
   	  @Override
      @Transactional(readOnly = true)
      public Map<Long, String> findByAllRevBudgetHeads(final Long orgId,final Long BudgetCodeId) {
           final Map<Long, String> map = new LinkedHashMap<>();
            List<Object[]> revBudgetHeadList = accountBudgetProjectedRevenueEntryJpaRepository
                   .findByAllRevBudgetHeads(orgId);
           if(BudgetCodeId!=null) {
           	 Long accType = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,BudgetCodeId);
           	 revBudgetHeadList = revBudgetHeadList.stream().filter(s->s[2].toString().equals(accType.toString())).collect(Collectors.toList());
           }
           if ((revBudgetHeadList != null) && !revBudgetHeadList.isEmpty()) {
               for (final Object[] objects : revBudgetHeadList) {
                   map.put((Long) objects[0], objects[1].toString());
               }
           }
           return map;
       }
   	  
   	 @Override
     @Transactional(readOnly = true)
     public Map<Long, String> findByAllRevBudgetHeadsWithFieldId(final Long orgId,final Long BudgetCodeId,final Long fieldId) {
          final Map<Long, String> map = new LinkedHashMap<>();
           List<Object[]> revBudgetHeadList = accountBudgetProjectedRevenueEntryJpaRepository
                  .findByAllRevBudgetHeadsWithFieldId(orgId,fieldId);
          if(BudgetCodeId!=null) {
          	 Long accType = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,BudgetCodeId);
          	 revBudgetHeadList = revBudgetHeadList.stream().filter(s->s[2].toString().equals(accType.toString())).collect(Collectors.toList());
          }
          if ((revBudgetHeadList != null) && !revBudgetHeadList.isEmpty()) {
              for (final Object[] objects : revBudgetHeadList) {
                  map.put((Long) objects[0], objects[1].toString());
              }
          }
          return map;
      }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedRevenueEntryBean> findBudgetProjectedRevenueEntrysByOrgId(final Long orgId) {
        final List<AccountBudgetProjectedRevenueEntryEntity> entities = accountBudgetProjectedRevenueEntryJpaRepository
                .findBudgetProjectedRevenueEntrysByOrgId(orgId);
        final List<AccountBudgetProjectedRevenueEntryBean> bean = new ArrayList<>();

        for (final AccountBudgetProjectedRevenueEntryEntity accountBudgetProjectedRevenueEntryEntity : entities) {

            bean.add(accountBudgetProjectedRevenueEntryServiceMapper
                    .mapAccountBudgetProjectedRevenueEntryBeanEntityToAccountBudgetProjectedRevenueEntryBean(
                            accountBudgetProjectedRevenueEntryEntity));
        }
        return bean;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getAccountHeadCodeInRevenue(final long orgid,Long activeStatus) {
        final List<Object[]> budgetList = accountBudgetProjectedRevenueEntryJpaRepository
                .getAccountHeadCodeInRevenue(orgid,activeStatus);
        return budgetList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedRevenueEntryBean> findByGridAllData(Long faYearid, Long fundId, Long functionId,
            Long cpdBugsubtypeId, Long dpDeptid, Long prBudgetCodeid,Long fieldId, Long orgId) {
    	List<Object[]> entities=null;
   	         final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
   	     for (final LookUp lookUp : fundTypeLevel) {
            if (MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
            	entities = accountBudgetProjectedRevenueEntryDao.findByGridAllData(faYearid, fundId, functionId,
                        cpdBugsubtypeId, dpDeptid, prBudgetCodeid, fieldId,orgId);
            }
            if (MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
            	entities = accountBudgetProjectedRevenueEntryDao.findByGridAllDatas(faYearid, fundId, functionId,
                        cpdBugsubtypeId, dpDeptid, prBudgetCodeid, fieldId,orgId);
            }
   	     }
        List<AccountBudgetProjectedRevenueEntryBean> chList = new ArrayList<>();
        if (entities != null) {
            for (Object[] object : entities) {
                AccountBudgetProjectedRevenueEntryBean bean = new AccountBudgetProjectedRevenueEntryBean();
                bean.setPrProjectionid((Long) (object[0]));
                if (object[1] != null) {
                    String revAmount = "";
                    //revAmount = getAllCollectedAmount(faYearid, (Long) object[1], orgId);
                    Department deparment=(Department) object[6];
					if ((Long) object[5] != null) {
						revAmount = getAllCollectedAmountByBasedOnDeptField(faYearid, (Long) object[1], orgId,deparment.getDpDeptid(), (Long) object[5]);
					} else {
						revAmount = getAllCollectedAmountByBasedOnDept(faYearid, (Long) object[1], orgId,deparment.getDpDeptid());
					}
                    bean.setPrCollected(revAmount);
                }
                bean.setPrRevBudgetCode(object[2].toString());
                bean.setOrginalEstamt(object[3].toString());
                if (object[4] != null) {
                    bean.setRevisedEstamt(object[4].toString());
                }
                if(object[6] != null){
                	Department deparment=(Department) object[6];
                	bean.setDpDeptid(deparment.getDpDeptid());
                	bean.setDpDeptName(deparment.getDpDeptdesc());	/*deparment.getDpDeptcode() + MainetConstants.HYPHEN + */
                }
                bean.setFaYearid(faYearid);
                chList.add(bean);
            }
        }
        return chList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetProjectedRevenueEntryEntity> getBudgetProjectedRevenueEntry(long orgId) {

        return accountBudgetProjectedRevenueEntryJpaRepository.getBudgetProjectedRevenueEntry(orgId);
    }

    @Override
    @Transactional
    public void saveBudgetProjectedRevenueEntryExportData(
            AccountBudgetProjectedRevenueEntryUploadDto accountBudgetProjectedRevenueEntryUploadDto, long orgId,
            int langId) {

        AccountBudgetProjectedRevenueEntryEntity revenueEntryEntity = new AccountBudgetProjectedRevenueEntryEntity();
        revenueEntryEntity.setOrgid(accountBudgetProjectedRevenueEntryUploadDto.getOrgid());
        revenueEntryEntity.setUserId(accountBudgetProjectedRevenueEntryUploadDto.getUserId());
        revenueEntryEntity.setLangId(accountBudgetProjectedRevenueEntryUploadDto.getLangId().intValue());
        revenueEntryEntity.setLgIpMac(accountBudgetProjectedRevenueEntryUploadDto.getLgIpMac());
        revenueEntryEntity.setLmoddate(accountBudgetProjectedRevenueEntryUploadDto.getLmoddate());
        revenueEntryEntity.setFaYearid(Long.valueOf(accountBudgetProjectedRevenueEntryUploadDto.getBudgetYear()));
        final Department dept = new Department();
        dept.setDpDeptid(Long.valueOf(accountBudgetProjectedRevenueEntryUploadDto.getDepartment()));
        revenueEntryEntity.setTbDepartment(dept);
        final AccountBudgetCodeEntity budgetCodeEntity = new AccountBudgetCodeEntity();
        budgetCodeEntity.setprBudgetCodeid(Long.valueOf(accountBudgetProjectedRevenueEntryUploadDto.getBudgetHead()));
        if (accountBudgetProjectedRevenueEntryUploadDto.getBudgetSubType() != null
                && !accountBudgetProjectedRevenueEntryUploadDto.getBudgetSubType().isEmpty()) {
            revenueEntryEntity.setCpdBugsubtypeId(Long.valueOf(accountBudgetProjectedRevenueEntryUploadDto.getBudgetSubType()));
        }
        revenueEntryEntity.setTbAcBudgetCodeMaster(budgetCodeEntity);
        revenueEntryEntity
                .setOrginalEstamt(new BigDecimal(accountBudgetProjectedRevenueEntryUploadDto.getOriginalBudget()));
        revenueEntryEntity
                .setPrProjected(new BigDecimal(accountBudgetProjectedRevenueEntryUploadDto.getOriginalBudget()));
        if (accountBudgetProjectedRevenueEntryUploadDto.getBudgetSubType() != null
                && !accountBudgetProjectedRevenueEntryUploadDto.getBudgetSubType().isEmpty()) {
            revenueEntryEntity.setCpdBugsubtypeId(Long.valueOf(accountBudgetProjectedRevenueEntryUploadDto.getBudgetSubType()));
        }
        Long filedId = accountFieldMasterService.getFieldIdByFieldCompositCode(accountBudgetProjectedRevenueEntryUploadDto.getField(), orgId);
        revenueEntryEntity.setFieldId(filedId);
        
        accountBudgetProjectedRevenueEntryJpaRepository.save(revenueEntryEntity);

    }

	
}
