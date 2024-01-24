
package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountBudgetCodeDao;
import com.abm.mainet.account.dto.AccountBudgetCodeUploadDto;
import com.abm.mainet.account.dto.BudgetHeadDTO;
import com.abm.mainet.account.mapper.AccountBudgetCodeServiceMapper;
import com.abm.mainet.account.repository.BudgetHeadRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountBudgetCodeBean;
import com.abm.mainet.common.integration.acccount.dto.AccountBudgetCodeDto;
import com.abm.mainet.common.integration.acccount.dto.SecondaryheadMaster;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.mapper.TbAcSecondaryheadMasterServiceMapper;
import com.abm.mainet.common.integration.acccount.repository.SecondaryheadMasterJpaRepository;
import com.abm.mainet.common.master.repository.DepartmentJpaRepository;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author prasad.kancharla
 *
 */
@Service
public class BudgetCodeServiceImpl implements BudgetCodeService, BudgetHeadProvider {

    // @Resource
    // private BudgetHeadRepository budgetHeadRepository;

    @Resource
    private AccountBudgetCodeServiceMapper accountBudgetCodeServiceMapper;

    @Resource
    private AccountBudgetCodeDao accountBudgetCodeDao;

    @Resource
    private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;
    @Resource
    private TbDepartmentJpaRepository departmentRepository;
    @Resource
    private DepartmentJpaRepository departmentJpaRepository;
    @Resource
    private BudgetHeadRepository budgetHeadRepository;
    @Resource
    private SecondaryheadMasterJpaRepository tbAcSecondaryheadMasterJpaRepository;
    @Resource
    private TbAcSecondaryheadMasterServiceMapper tbAcSecondaryheadMasterServiceMapper;

    @Override
    @Transactional
    public Boolean isCombinationExists(final Long dpDeptid, final Long fundId, final Long functionId, final Long fieldId,
            final Long sacId, final Long orgId,
            final String objectHeadType) {

        return accountBudgetCodeDao.isCombinationExists(dpDeptid, fundId, functionId, fieldId, sacId, orgId, objectHeadType);
    }

    @Override
    @Transactional
    public List<AccountBudgetCodeBean> findByAllGridSearchData(final Long dpDeptid, final Long fundId, final Long fieldId,
            final Long functionId,
            final Long sacHeadId, final String cpdIdStatusFlag, final Long orgId, final String objectHeadType) {

        final List<AccountBudgetCodeEntity> entities = accountBudgetCodeDao.findByAllGridSearchData(dpDeptid, fundId, fieldId,
                functionId, sacHeadId, cpdIdStatusFlag, orgId, objectHeadType);

        final List<AccountBudgetCodeBean> bean = new ArrayList<>();

        for (final AccountBudgetCodeEntity accountBudgetCodeEntity : entities) {

            bean.add(accountBudgetCodeServiceMapper.mapAccountBudgetCodeEntityToAccountBudgetCodeBean(accountBudgetCodeEntity));
        }
        return bean;
    }

    @Override
    @Transactional
    public List<SecondaryheadMaster> findByAllGridData(final Long orgId) {

        final Iterable<AccountHeadSecondaryAccountCodeMasterEntity> entities = tbAcSecondaryheadMasterJpaRepository
                .findAll(orgId);
        final List<SecondaryheadMaster> beans = new ArrayList<>();
        for (final AccountHeadSecondaryAccountCodeMasterEntity tbAcSecondaryheadMasterEntity : entities) {
            beans.add(tbAcSecondaryheadMasterServiceMapper
                    .mapTbAcSecondaryheadMasterEntityToTbAcSecondaryheadMaster(tbAcSecondaryheadMasterEntity));
        }
        return beans;
    }

    @Override
    @Transactional
    public AccountBudgetCodeBean saveBudgetCodeFormData(final AccountBudgetCodeBean tbAcBudgetCode, final Organisation orgid,
            final int langId) {

        final AccountBudgetCodeBean acTbbudgetCode = tbAcBudgetCode;
        AccountBudgetCodeEntity accountBudgetCodeEntity = null;

        final List<AccountBudgetCodeDto> budgetCodeDto = acTbbudgetCode.getBudgCodeMasterDtoList();
        if ((budgetCodeDto != null) && !budgetCodeDto.isEmpty()) {
            for (final AccountBudgetCodeDto accountBudgetCodeDto : budgetCodeDto) {

                acTbbudgetCode.setFunctionId(accountBudgetCodeDto.getFunctionId());
                if (acTbbudgetCode.getObjectHeadType() != null && !acTbbudgetCode.getObjectHeadType().isEmpty()) {
                    if (acTbbudgetCode.getObjectHeadType().equals(MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS)) {
                        acTbbudgetCode.setSacHeadId(accountBudgetCodeDto.getSacHeadId());
                        Long primaryHeadId = tbAcSecondaryheadMasterJpaRepository
                                .getPrimaryHeadIdByPassSacHeadId(accountBudgetCodeDto.getSacHeadId(), tbAcBudgetCode.getOrgid());
                        if (primaryHeadId != null) {
                            acTbbudgetCode.setPacHeadId(primaryHeadId);
                        }
                    }
                    if (acTbbudgetCode.getObjectHeadType().equals(MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS)) {
                        acTbbudgetCode.setPacHeadId(accountBudgetCodeDto.getSacHeadId());
                    }
                }
                acTbbudgetCode.setPrBudgetCode(accountBudgetCodeDto.getPrBudgetCode());
                if (accountBudgetCodeDto.getCpdIdStatusFlag() != null) {
                    acTbbudgetCode.setCpdIdStatusFlag(accountBudgetCodeDto.getCpdIdStatusFlag());
                } else {
                    acTbbudgetCode.setCpdIdStatusFlag(
                            CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.BUDGET_CODE.ACTIVE_VALUE,
                                    MainetConstants.BUDGET_CODE.STATUS_FLAG, orgid).getLookUpCode());
                }
                accountBudgetCodeEntity = new AccountBudgetCodeEntity();

                accountBudgetCodeServiceMapper.mapAccountBudgetCodeBeanToAccountBudgetCodeBeanEntity(acTbbudgetCode,
                        accountBudgetCodeEntity);
                budgetHeadRepository.save(accountBudgetCodeEntity);
            }
            return tbAcBudgetCode;
        }
        return acTbbudgetCode;
    }

    @Override
    @Transactional
    public AccountBudgetCodeBean getDetailsUsingPrBudgetCodeId(final AccountBudgetCodeBean tbAcBudgetCode, final Long orgId,
            final String primaryStatusLookUpCode) {

        final Long PrProjectionid = tbAcBudgetCode.getPrBudgetCodeid();
        final AccountBudgetCodeEntity accountBudgetCodeEntity = budgetHeadRepository.findOne(PrProjectionid);

        if ((accountBudgetCodeEntity.getTbAcFundMaster() != null)
                && (accountBudgetCodeEntity.getTbAcFundMaster().getFundId() != null)) {
            tbAcBudgetCode.setFundId(accountBudgetCodeEntity.getTbAcFundMaster().getFundId());
        }
        if ((accountBudgetCodeEntity.getTbAcFieldMaster() != null)
                && (accountBudgetCodeEntity.getTbAcFieldMaster().getFieldId() != null)) {
            tbAcBudgetCode.setFieldId(accountBudgetCodeEntity.getTbAcFieldMaster().getFieldId());
        }
        if ((accountBudgetCodeEntity.getTbDepartment() != null)
                && (accountBudgetCodeEntity.getTbDepartment().getDpDeptid() != null)) {
            tbAcBudgetCode.setDpDeptid(accountBudgetCodeEntity.getTbDepartment().getDpDeptid());
            tbAcBudgetCode.setDepartmentDesc(
                    departmentRepository.fetchDepartmentDescById(accountBudgetCodeEntity.getTbDepartment().getDpDeptid()));
        }
        if (accountBudgetCodeEntity.getCpdBugtypeId() != null) {
            tbAcBudgetCode.setCpdBugtypeId(accountBudgetCodeEntity.getCpdBugtypeId());
            tbAcBudgetCode.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
                    accountBudgetCodeEntity.getOrgid(), accountBudgetCodeEntity.getCpdBugtypeId()));
        }
        if (accountBudgetCodeEntity.getCpdBugsubtypeId() != null) {
            tbAcBudgetCode.setCpdBugsubtypeId(accountBudgetCodeEntity.getCpdBugsubtypeId());
            tbAcBudgetCode.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.BUG_SUB_PREFIX,
                    accountBudgetCodeEntity.getOrgid(), accountBudgetCodeEntity.getCpdBugsubtypeId()));
        }

        AccountBudgetCodeDto accountBudgetCodeDto = null;
        final List<AccountBudgetCodeDto> bugRevenueMasterDtoList = new ArrayList<>();
        accountBudgetCodeDto = new AccountBudgetCodeDto();
        if ((accountBudgetCodeEntity.getTbAcFunctionMaster() != null)
                && (accountBudgetCodeEntity.getTbAcFunctionMaster().getFunctionId() != null)) {
            accountBudgetCodeDto.setFunctionId(accountBudgetCodeEntity.getTbAcFunctionMaster().getFunctionId());
        }
        accountBudgetCodeDto.setPrBudgetCode(accountBudgetCodeEntity.getPrBudgetCode());
        if (primaryStatusLookUpCode != null) {
            if (primaryStatusLookUpCode.equals(MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS)) {
                accountBudgetCodeDto.setSacHeadId(accountBudgetCodeEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadId());
            } else {
                accountBudgetCodeDto.setSacHeadId(accountBudgetCodeEntity.getTbAcSecondaryheadMaster().getSacHeadId());
            }
        } else {
            accountBudgetCodeDto.setSacHeadId(accountBudgetCodeEntity.getTbAcSecondaryheadMaster().getSacHeadId());
        }

        accountBudgetCodeDto.setCpdIdStatusFlag(accountBudgetCodeEntity.getCpdIdStatusFlag());
        accountBudgetCodeDto
                .setStatusDesc(CommonMasterUtility.findLookUpCodeDesc(PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                        accountBudgetCodeEntity.getOrgid(), accountBudgetCodeEntity.getCpdIdStatusFlag()));
        tbAcBudgetCode.setCpdIdStatusFlag(accountBudgetCodeEntity.getCpdIdStatusFlag());
        bugRevenueMasterDtoList.add(accountBudgetCodeDto);
        tbAcBudgetCode.setBudgCodeMasterDtoList(bugRevenueMasterDtoList);

        return tbAcBudgetCode;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountBudgetCodeService#getBudgetHeads(java.lang.Long, java.lang.Long,
     * java.lang.Long)
     */
    @Override
    @Transactional
    public List<Object[]> getBudgetHeads(final Long superOrgId, final Long orgId, final Long cpdIdHeadType) {

        return budgetHeadRepository.getBudgetHeads(superOrgId, orgId, cpdIdHeadType);
    }

    @Override
    @Transactional
    public List<Object[]> getTDSDeductionBudgetHeads(final Long superOrgId, final Long orgId) {

        return budgetHeadRepository.getTDSDeductionBudgetHeads(superOrgId, orgId);
    }

    @Override
    @Transactional
    public List<Object[]> getExpenditutreBudgetHeads(final Long orgId, final Long budgetCodeId) {

        return budgetHeadRepository.getExpenditutreBudgetHeads(orgId, budgetCodeId);
    }

    @Override
    @Transactional
    public Map<Long, String> finAllBudgetCodeByOrg(Long orgid) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();

        final List<Object[]> budgetCodeList = budgetHeadRepository.findAllBudgetCodeId(orgid);

        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
            }
        }
        return mapBudgetCode;
    }

    @Override
    @Transactional
    public Map<Long, String> findByAllRevBudgetHeads(final Long faYearid, final Long cpdBugtypeid, final Long cpdBugsubtypeId,
            final Long dpDeptid,
            final Organisation organisation, final int langId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        /*
         * if based on the function and primary head master was configure in configuration ULB to generate budget head code.
         */
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllPrimaryBudgetHeadIds(status, orgid);

        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                int strPacCodeValue;
                strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                if ((Integer.parseInt(MainetConstants.Common_Constant.NUMBER.ONE) == strPacCodeValue)
                        || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.THREE) == strPacCodeValue)
                        || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.FOUR) == strPacCodeValue)) {
                    if ((objects[0] != null) && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                        mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                    }
                }
            }
        }
        return mapBudgetCode;
    }

    @Override
    @Transactional
    public Map<Long, String> findByAllRevObjectTypeBudgetHeads(final Long faYearid, final Long cpdBugtypeid,
            final Long cpdBugsubtypeId,
            final Long dpDeptid, final Organisation organisation, final int langId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        /*
         * if based on the function and Object head master was configure in configuration ULB to generate budget head code.
         */
        // final Long status = statusLookup.getLookUpId();
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllObjectBudgetHeadIds(status, orgid);
        final LookUp oTLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_OTHER_CPD_VALUE, PrefixConstants.FTY, langId,
                organisation);
        final Long lookUpOTId = oTLookup.getLookUpId();
        Long subLedgerTypeId = null;
        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                subLedgerTypeId = Long.valueOf(objects[2].toString());
                if (lookUpOTId.equals(subLedgerTypeId)) {
                    if (objects[3] != null) {
                        String acHeadTypeCode = CommonMasterUtility.findLookUpCode(MainetConstants.COMMON_PREFIX.COA, orgid,
                                Long.valueOf(objects[3].toString()));
                        if (!acHeadTypeCode
                                .equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.EXPENDITURE_OTHER_FIELD)) {
                            mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                        }
                    }
                }
            }
        }
        return mapBudgetCode;
    }

    @Override
    @Transactional
    public Map<Long, String> findByAllExpBudgetHeads(final Long faYearid, final Long cpdBugtypeid, final Long cpdBugsubtypeId,
            final Long dpDeptid,
            final Organisation organisation, final int langId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        /*
         * if based on the function and primary head master was configure in configuration ULB to generate budget head code.
         */
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllPrimaryBudgetHeadIds(status, orgid);

        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                int strPacCodeValue;
                strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                if ((Integer.parseInt(MainetConstants.Common_Constant.NUMBER.TWO) == strPacCodeValue)
                        || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.THREE) == strPacCodeValue)
                        || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.FOUR) == strPacCodeValue)) {
                    if ((objects[0] != null) && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                        mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                    }
                }
            }
        }

        return mapBudgetCode;
    }

    @Override
    @Transactional
    public Map<Long, String> findByAllExpObjectTypeBudgetHeads(final Long faYearid, final Long cpdBugtypeid,
            final Long cpdBugsubtypeId,
            final Long dpDeptid, final Organisation organisation, final int langId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        /*
         * if based on the function and Object head master was configure in configuration ULB to generate budget head code.
         */
        // final Long status = statusLookup.getLookUpId();
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllObjectBudgetHeadIds(status, orgid);
        final LookUp oTLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_OTHER_CPD_VALUE, PrefixConstants.FTY, langId,
                organisation);
        final Long lookUpOTId = oTLookup.getLookUpId();
        Long subLedgerTypeId = null;
        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                subLedgerTypeId = Long.valueOf(objects[2].toString());
                if (lookUpOTId.equals(subLedgerTypeId)) {
                    if (objects[3] != null) {
                        String acHeadTypeCode = CommonMasterUtility.findLookUpCode(MainetConstants.COMMON_PREFIX.COA, orgid,
                                Long.valueOf(objects[3].toString()));
                        if (!acHeadTypeCode.equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.INCOME_OTHER_FIELD)) {
                            mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                        }
                    }
                }
            }
        }
        return mapBudgetCode;
    }

    @Override
    @Transactional
    public String getBudgetCode(final Long prBudgetCodeid, final Long orgId) {
        final String budgetCodeList = budgetHeadRepository.findByBudgetCode(prBudgetCodeid, orgId);
        return budgetCodeList;
    }
    
    @Override
    @Transactional
    public String getBudgetCodes(final Long prBudgetCodeid, final Long orgId) {
        final String budgetCodeList = budgetHeadRepository.findByBudgetCodes(prBudgetCodeid, orgId);
        return budgetCodeList;
    }

    @Override
    @Transactional
    public List<BudgetHeadDTO> findBudgetHeadsByMode(final long payMode, final long orgId) {

        final List<Object[]> objects = budgetHeadRepository.fetchBudgetHeads(payMode, null, null, null, orgId);

        return mapToBudgetCodeDTO(objects);
    }

    private List<BudgetHeadDTO> mapToBudgetCodeDTO(final List<Object[]> list) {

        if ((list == null) || list.isEmpty()) {
            throw new IllegalArgumentException("No record found for provided input parameters");
        }
        final List<BudgetHeadDTO> budgetCodeList = new ArrayList<>();
        for (final Object[] objects : list) {
            final BudgetHeadDTO dto = new BudgetHeadDTO();
            dto.setBudgetCodeId((long) objects[0]);
            dto.setBudgetCode((String) objects[1]);
            dto.setPrimaryHeadCompositCode((String) objects[2]);
            dto.setSecondaryHeadCode((String) objects[3]);
            dto.setSecondaryHeadDesc((String) objects[4]);
            dto.setCombinedBudgetCodeDesc(combineBudgetCode(dto));

            budgetCodeList.add(dto);
        }

        return budgetCodeList;
    }

    private String combineBudgetCode(final BudgetHeadDTO dto) {

        final StringBuilder builder = new StringBuilder();
        builder.append(dto.getBudgetCode())
                .append(MainetConstants.SEPARATOR)
                .append(dto.getPrimaryHeadCompositCode())
                .append(MainetConstants.SEPARATOR)
                .append(dto.getSecondaryHeadCode())
                .append(MainetConstants.SEPARATOR)
                .append(dto.getSecondaryHeadDesc());

        return builder.toString();
    }

    @Override
    @Transactional
    public List<BudgetHeadDTO> findAllBudgetHeadsById(final long budgetCodeId, final long orgId) {

        return mapToBudgetCodeDTO(budgetHeadRepository.findByAllBudgetCode(budgetCodeId, orgId));
    }

    @Override
    @Transactional
    public List<BudgetHeadDTO> findAllBudgetHeadsByHeadType(final long superOrgId, final long orgId, final long headTypeId) {

        return mapToBudgetCodeDTO(budgetHeadRepository.fetchBudgetHeads(null, headTypeId, null, superOrgId, orgId));
    }

    @Override
    @Transactional
    public List<Object[]> getFundCode(final Long prBudgetCodeId, final Long orgId) {

        return budgetHeadRepository.getFundCode(prBudgetCodeId, orgId);
    }

    @Override
    @Transactional
    public List<Object[]> getFunctionCode(final Long prBudgetCodeId, final Long orgId) {

        return budgetHeadRepository.getFunctionCode(prBudgetCodeId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getAllBudgetCodes(final Long prBudgetCodeid, final Long orgId) {
        final Map<String, String> mapBudgetCode = new LinkedHashMap<>();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllBudgetCode(prBudgetCodeid, orgId);
        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                mapBudgetCode.put(objects[0].toString(), objects[1].toString());
            }
        }
        return mapBudgetCode;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.AccountBudgetCodeService#getJournalBudgetCode(long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getJournalBudgetCode(final long orgid) {
        final List<Object[]> budgetCode = budgetHeadRepository.getJournalBudgetCode(orgid);
        return budgetCode;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getVoucherAccountHead(final Long orgid,
            final Long cpdId1, final Long cpdId2,Long activeStatus) {
        final List<Object[]> budgetCode = budgetHeadRepository.getVoucherAccountHead(orgid, cpdId1, cpdId2,activeStatus);
        return budgetCode;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getBudgetCodeIdForPayMode(final Long cpdIdPayMode, final Long orgId) {

        return budgetHeadRepository.getBudgetCodeIdForPayMode(cpdIdPayMode, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getBankBudgetCodeIdByAccountId(final Long bankAccountId, final Long orgId) {

        return budgetHeadRepository.getBankBudgetCodeIdByAccountId(bankAccountId, orgId);
    }

    @Override
    @Transactional
    public List<Object[]> findSacBudgetHeadIdDescAllData(final long orgId) {
        final List<Object[]> budgetCode = budgetHeadRepository.findSacBudgetHeadIdDescAllData(orgId);
        return budgetCode;
    }

    @Override
    @Transactional
    public List<String> findBudgetCodeDetails(final Long prBudgetCodeid, final Long orgId) {
        final List<String> budgdetCodeDetails = budgetHeadRepository.findBudgetCodeDetails(prBudgetCodeid, orgId);
        return budgdetCodeDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findBudgetCode(final Long budgetCodeId, final Long orgId) {
        final List<Object[]> budgdetCode = budgetHeadRepository.findBudgetCode(budgetCodeId, orgId);
        return budgdetCode;
    }

    @Override
    @Transactional(readOnly = true)
    public String findAccountHeadCodeBySacHeadId(final Long sacHeadId, final Long orgId) {
        return budgetHeadRepository.findAccountHeadCodeBySacHeadId(sacHeadId, orgId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findAccountHeadCode(final Long budgetCodeId, final Long orgId) {
        final List<Object[]> budgdetCode = budgetHeadRepository.findAccountHeadCode(budgetCodeId, orgId);
        return budgdetCode;
    }

    @Override
    public VendorBillApprovalDTO geDeductionDetails(final Long orgId, final Long superOrgId) {

        final VendorBillApprovalDTO vendorBillApprovalDto = new VendorBillApprovalDTO();
        final List<LookUp> deductionLookUpList = new ArrayList<>();
        final List<Object[]> deductionDetList = getTDSDeductionBudgetHeads(superOrgId, orgId);
        LookUp lookUp = null;
        for (final Object[] deductionDet : deductionDetList) {
            lookUp = new LookUp();
            lookUp.setLookUpId((Long) deductionDet[0]);
            lookUp.setDescLangFirst(deductionDet[1].toString());
            deductionLookUpList.add(lookUp);
        }
        vendorBillApprovalDto.setLookUpList(deductionLookUpList);
        return vendorBillApprovalDto;
    }

    @Override
    public void saveBudgetHeadExportData(AccountBudgetCodeUploadDto accountBudgetCodeUploadDto, Organisation defaultOrg,
            int langId, String primaryHeadFlag, String objectHeadFlag) {
        AccountBudgetCodeBean tbAcBudgetCode = new AccountBudgetCodeBean();
        tbAcBudgetCode.setOrgid(accountBudgetCodeUploadDto.getOrgid());
        tbAcBudgetCode.setUserId(accountBudgetCodeUploadDto.getUserId());
        tbAcBudgetCode.setLmoddate(accountBudgetCodeUploadDto.getLmoddate());
        tbAcBudgetCode.setLgIpMac(accountBudgetCodeUploadDto.getLgIpMac());
        AccountBudgetCodeDto codeDto = new AccountBudgetCodeDto();
        List<AccountBudgetCodeDto> dtoList = new ArrayList<>();
        codeDto.setOrgid(accountBudgetCodeUploadDto.getOrgid());
        codeDto.setUserId(accountBudgetCodeUploadDto.getUserId());
        codeDto.setLmoddate(accountBudgetCodeUploadDto.getLmoddate());
        codeDto.setLgIpMac(accountBudgetCodeUploadDto.getLgIpMac());
        codeDto.setCpdIdStatusFlag(accountBudgetCodeUploadDto.getCpdIdStatusFlag());
        codeDto.setFunctionId(Long.valueOf(accountBudgetCodeUploadDto.getFunction()));
        if (primaryHeadFlag != null && !primaryHeadFlag.isEmpty()) {
            if (primaryHeadFlag.equals(MainetConstants.Y_FLAG)) {
                codeDto.setSacHeadId(Long.valueOf(accountBudgetCodeUploadDto.getPrimaryHead()));
                tbAcBudgetCode.setObjectHeadType(MainetConstants.BUDGET_CODE.PRIMARY_WISE_STATUS);
            }
        }
        if (objectHeadFlag != null && !objectHeadFlag.isEmpty()) {
            if (objectHeadFlag.equals(MainetConstants.Y_FLAG)) {
                codeDto.setSacHeadId(Long.valueOf(accountBudgetCodeUploadDto.getPrimaryHead()));
                tbAcBudgetCode.setObjectHeadType(MainetConstants.BUDGET_CODE.OBJECT_WISE_STATUS);
            }
        }
        codeDto.setPrBudgetCode(accountBudgetCodeUploadDto.getBudgetHeadCodeDescription());
        dtoList.add(codeDto);
        tbAcBudgetCode.setBudgCodeMasterDtoList(dtoList);
        saveBudgetCodeFormData(tbAcBudgetCode, defaultOrg, langId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetCodeEntity> getBudgetHeadCodes(long orgid, String cpdIdStatusFlag) {
        return budgetHeadRepository.getBudgetHeadCodes(orgid, cpdIdStatusFlag);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> finAllBudgetCodeByOrganization(Long orgid) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();

        final List<Object[]> budgetCodeList = budgetHeadRepository.findAllBudgetCodeId(orgid);

        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
            }
        }
        return mapBudgetCode;

    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllExpFieldBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId,
            Long dpDeptid, Organisation organisation, int langId, Long fieldId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        /*
         * if based on the function and primary head master was configure in configuration ULB to generate budget head code.
         */
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllPrimaryBudgetHeadIds(status, orgid);

        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                if (objects[4] != null) {
                    if (Long.valueOf(objects[4].toString()) == fieldId) {
                        int strPacCodeValue;
                        strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                        if ((Integer.parseInt(MainetConstants.Common_Constant.NUMBER.TWO) == strPacCodeValue)
                                || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.THREE) == strPacCodeValue)
                                || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.FOUR) == strPacCodeValue)) {
                            if ((objects[0] != null) && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                            }
                        }
                    }
                }
            }
        }

        return mapBudgetCode;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllExpObjectTypeFieldBudgetHeads(Long faYearid, Long cpdBugtypeid,
            Long cpdBugsubtypeId, Long dpDeptid, Organisation organisation, int langId, Long fieldId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        /*
         * if based on the function and Object head master was configure in configuration ULB to generate budget head code.
         */
        // final Long status = statusLookup.getLookUpId();
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllObjectBudgetHeadIds(status, orgid);
        final LookUp oTLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_OTHER_CPD_VALUE, PrefixConstants.FTY, langId,
                organisation);
        final Long lookUpOTId = oTLookup.getLookUpId();
        Long subLedgerTypeId = null;
        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                if (objects[4] != null) {
                    if (Long.valueOf(objects[4].toString()).equals(fieldId)) {
                        subLedgerTypeId = Long.valueOf(objects[2].toString());
                        if (lookUpOTId.equals(subLedgerTypeId)) {
                            if (objects[3] != null) {
                                String acHeadTypeCode = CommonMasterUtility.findLookUpCode(MainetConstants.COMMON_PREFIX.COA,
                                        orgid, Long.valueOf(objects[3].toString()));
                                if (!acHeadTypeCode
                                        .equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.INCOME_OTHER_FIELD)) {
                                    mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        return mapBudgetCode;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllExpFunctionBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId,
            Long dpDeptid, Organisation organisation, int langId, Long functionId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        /*
         * if based on the function and primary head master was configure in configuration ULB to generate budget head code.
         */
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllPrimaryBudgetHeadIds(status, orgid);

        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                if (objects[5] != null) {
                    if (Long.valueOf(objects[5].toString()) == functionId) {
                        int strPacCodeValue;
                        strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                        if ((Integer.parseInt(MainetConstants.Common_Constant.NUMBER.TWO) == strPacCodeValue)
                                || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.THREE) == strPacCodeValue)
                                || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.FOUR) == strPacCodeValue)) {
                            if ((objects[0] != null) && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                            }
                        }
                    }
                }
            }
        }

        return mapBudgetCode;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllExpObjectTypeFunctionBudgetHeads(Long faYearid, Long cpdBugtypeid,
            Long cpdBugsubtypeId, Long dpDeptid, Organisation organisation, int langId, Long functionId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        final String budgetSubType = CommonMasterUtility.findLookUpCode(PrefixConstants.BUG_SUB_PREFIX,orgid, cpdBugsubtypeId);

        /*
         * if based on the function and Object head master was configure in configuration ULB to generate budget head code.
         */
        // final Long status = statusLookup.getLookUpId();
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllObjectBudgetHeadIds(status, orgid);
        final LookUp oTLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_OTHER_CPD_VALUE, PrefixConstants.FTY, langId,
                organisation);
        final Long lookUpOTId = oTLookup.getLookUpId();
        Long subLedgerTypeId = null;
        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                if (objects[5] != null) {
                    if (Long.valueOf(objects[5].toString()).equals(functionId)) {
                        subLedgerTypeId = Long.valueOf(objects[2].toString());
                        if (lookUpOTId.equals(subLedgerTypeId)) {
                            if (objects[3] != null) {
                                String acHeadTypeCode = CommonMasterUtility.findLookUpCode(MainetConstants.COMMON_PREFIX.COA,
                                        orgid, Long.valueOf(objects[3].toString()));
                                if(budgetSubType.equals(PrefixConstants.REV_SUB_CPD_VALUE)) {
                                if (acHeadTypeCode
                                        .equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.EXPENDITURE_OTHER_FIELD)) {
                                    mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                                 }
                                }else if(budgetSubType.equals(PrefixConstants.EXP_SUB_CPD_VALUE)){
                                	 if (acHeadTypeCode
                                             .equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ASSET_OTHER_FIELD)||
                                        acHeadTypeCode
                                             .equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.LIABILITY_OTHER_FIELD)) {
                                         mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                                      }
                                }
                            }
                        }
                    }
                }
            }
        }
        return mapBudgetCode;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllRevFieldBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId,
            Long dpDeptid, Organisation organisation, int langId, Long fieldId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        /*
         * if based on the function and primary head master was configure in configuration ULB to generate budget head code.
         */
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllPrimaryBudgetHeadIds(status, orgid);

        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                if (objects[4] != null) {
                    if (Long.valueOf(objects[4].toString()) == fieldId) {
                        int strPacCodeValue;
                        strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                        if ((Integer.parseInt(MainetConstants.Common_Constant.NUMBER.ONE) == strPacCodeValue)
                                || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.THREE) == strPacCodeValue)
                                || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.FOUR) == strPacCodeValue)) {
                            if ((objects[0] != null) && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                            }
                        }
                    }
                }
            }
        }
        return mapBudgetCode;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllRevObjectTypeFieldBudgetHeads(Long faYearid, Long cpdBugtypeid,
            Long cpdBugsubtypeId, Long dpDeptid, Organisation organisation, int langId, Long fieldId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        /*
         * if based on the function and Object head master was configure in configuration ULB to generate budget head code.
         */
        // final Long status = statusLookup.getLookUpId();
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllObjectBudgetHeadIds(status, orgid);
        final LookUp oTLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_OTHER_CPD_VALUE, PrefixConstants.FTY, langId,
                organisation);
        final Long lookUpOTId = oTLookup.getLookUpId();
        Long subLedgerTypeId = null;
        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                if (objects[4] != null) {
                    if (Long.valueOf(objects[4].toString()) == fieldId) {
                        subLedgerTypeId = Long.valueOf(objects[2].toString());
                        if (lookUpOTId.equals(subLedgerTypeId)) {
                            if (objects[3] != null) {
                                String acHeadTypeCode = CommonMasterUtility.findLookUpCode(MainetConstants.COMMON_PREFIX.COA,
                                        orgid, Long.valueOf(objects[3].toString()));
                                if (!acHeadTypeCode
                                        .equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.EXPENDITURE_OTHER_FIELD)) {
                                    mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        return mapBudgetCode;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllRevFunctionBudgetHeads(Long faYearid, Long cpdBugtypeid, Long cpdBugsubtypeId,
            Long dpDeptid, Organisation organisation, int langId, Long functionId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        /*
         * if based on the function and primary head master was configure in configuration ULB to generate budget head code.
         */
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllPrimaryBudgetHeadIds(status, orgid);

        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                if (objects[5] != null) {
                    if (Long.valueOf(objects[5].toString()) == functionId) {
                        int strPacCodeValue;
                        strPacCodeValue = Integer.parseInt(String.valueOf(objects[2].toString().charAt(0)));
                        if ((Integer.parseInt(MainetConstants.Common_Constant.NUMBER.ONE) == strPacCodeValue)
                                || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.THREE) == strPacCodeValue)
                                || (Integer.parseInt(MainetConstants.Common_Constant.NUMBER.FOUR) == strPacCodeValue)) {
                            if ((objects[0] != null) && ((objects[1] != null) && !objects[1].toString().isEmpty())) {
                                mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                            }
                        }
                    }
                }
            }
        }
        return mapBudgetCode;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findByAllRevObjectTypeFunctionBudgetHeads(Long faYearid, Long cpdBugtypeid,
            Long cpdBugsubtypeId, Long dpDeptid, Organisation organisation, int langId, Long functionId) {
        final Map<Long, String> mapBudgetCode = new LinkedHashMap<>();
        final Long orgid = organisation.getOrgid();
        final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.ACTIVE_STATUS_CPD_VALUE, PrefixConstants.ACCOUNT_MASTERS.ACTIVE_INACTIVE_PREFIX,
                langId, organisation);
        final String budgetSubType = CommonMasterUtility.findLookUpCode(PrefixConstants.BUG_SUB_PREFIX,orgid, cpdBugsubtypeId);
        /*
         * if based on the function and Object head master was configure in configuration ULB to generate budget head code.
         */
        // final Long status = statusLookup.getLookUpId();
        final String status = statusLookup.getLookUpCode();
        final List<Object[]> budgetCodeList = budgetHeadRepository.findByAllObjectBudgetHeadIds(status, orgid);
        final LookUp oTLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ACCOUNT_MASTERS.SECONDARY_MASTER.SECONDARY_OTHER_CPD_VALUE, PrefixConstants.FTY, langId,
                organisation);
        final Long lookUpOTId = oTLookup.getLookUpId();
        Long subLedgerTypeId = null;
        if ((budgetCodeList != null) && !budgetCodeList.isEmpty()) {
            for (final Object[] objects : budgetCodeList) {
                if (objects[5] != null) {
                    if (Long.valueOf(objects[5].toString()) == functionId) {
                        subLedgerTypeId = Long.valueOf(objects[2].toString());
                        if (lookUpOTId.equals(subLedgerTypeId)) {
                            if (objects[3] != null) {
                                String acHeadTypeCode = CommonMasterUtility.findLookUpCode(MainetConstants.COMMON_PREFIX.COA,
                                        orgid, Long.valueOf(objects[3].toString()));
                                if(budgetSubType.equals(PrefixConstants.REV_SUB_CPD_VALUE)) {
                                if (acHeadTypeCode
                                        .equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.INCOME_OTHER_FIELD)) {
                                    mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                                  }
                                }else if(budgetSubType.equals(PrefixConstants.EXP_SUB_CPD_VALUE)) {
                                	if (acHeadTypeCode
                                            .equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.ASSET_OTHER_FIELD) ||
                                            		acHeadTypeCode
                                                    .equals(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.LIABILITY_OTHER_FIELD)) {
                                        mapBudgetCode.put(Long.valueOf(objects[0].toString()), objects[1].toString());
                                      }
                                }
                            }
                        }
                    }
                }
            }
        }
        return mapBudgetCode;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountBudgetCodeEntity findBudgetHeadIdBySacHeadId(Long sacHeadId, Long orgId) {
        return budgetHeadRepository.findBudgetHeadIdBySacHeadId(sacHeadId, orgId);
    }

	@Override
    @Transactional(readOnly = true)
	public Long getBudgetAccTypeByBudgetCodeId(Long orgId, Long BudgetCodeId) {
		// TODO Auto-generated method stub
		return budgetHeadRepository.getCPDAccTypeByBudgetCodeId(orgId, BudgetCodeId);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Object[]> findByAllObjectBudgetHeadId(Long orgId, Long finYearId,Long dpDeptid,Long fieldId) {
		return budgetHeadRepository.findByAllObjectBudgetHeadId(finYearId,orgId,dpDeptid,fieldId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getSecondaryHeadcodesWithField(final Long orgId, final Long fieldId) {
		return budgetHeadRepository.getSecondaryHeadcodesWithField(orgId, fieldId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccountHeadSecondaryAccountCodeMasterEntity> getSecondaryHeadcodesWithDeptField(final Long orgId,
			final Long dpDeptId, final Long fieldId) {
		return budgetHeadRepository.getSecondaryHeadcodesWithDeptField(orgId, dpDeptId, fieldId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccountHeadSecondaryAccountCodeMasterEntity> getSecondaryHeadcodesField(final Long orgId,
			final Long fieldId) {
		return budgetHeadRepository.getSecondaryHeadcodesField(orgId, fieldId);
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<Object[]> getSecondaryHeadcodesDeptField(final Long orgId ,final Long dpDeptId,final Long fieldId) {
        return budgetHeadRepository.getSecondaryHeadcodesDeptField(orgId,dpDeptId,fieldId);
    }

}
