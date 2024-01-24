package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dto.BankAccountMaster;
import com.abm.mainet.account.dto.BankAccountMasterUploadDTO;
import com.abm.mainet.account.dto.BankMasterDto;
import com.abm.mainet.account.mapper.AccountBudgetCodeServiceMapper;
import com.abm.mainet.account.repository.BudgetHeadRepository;
import com.abm.mainet.account.repository.UlbBankMasterJpaRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;
import com.abm.mainet.common.integration.acccount.dto.SecondaryheadMaster;
import com.abm.mainet.common.integration.acccount.hrms.bankaccount.soap.jaxws.client.BankAccount;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.BankAccountSoapWSProvisionService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.repository.BankAccountJpaRepository;
import com.abm.mainet.common.repository.BankMasterJpaRepository;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * Implementation of BankAccountService
 */
@Component
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    @Resource
    private BankAccountJpaRepository bankAccountJpaRepository;

    @Resource
    private BankMasterJpaRepository bankMasterJpaRepository;

    @Resource
    private UlbBankMasterJpaRepository ulbBankJpaRepository;

    @Autowired
    private SecondaryheadMasterService secondaryheadMasterService;
    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;
    @Resource
    private AccountFundMasterService tbAcFundMasterService;
    @Resource
    private SecondaryheadMasterService tbAcSecondaryheadMasterService;
    @Resource
    private BudgetHeadRepository budgetCodeRepository;
    @Resource
    private AccountBudgetCodeServiceMapper accountBudgetCodeServiceMapper;
    @Resource
    private BankAccountSoapWSProvisionService bankAccountSoapWSProvisionService;

    private static final String DATE_COVERT_EXCEPTION = "Exception while converting date to XMLGregorianCalendar :";

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.TbBankmasterService#getBankNameList(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getBankNameList() {
        final List<String> bankNames = bankMasterJpaRepository.getAllBankName();
        return bankNames;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.TbBankmasterService#getBranchNamesByBank(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getBranchNamesByBank(final String bank) {
        final List<Object[]> branchNames = bankMasterJpaRepository.getAllBranchNames(bank);
        return branchNames;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.BankAccountService#findAll()
     */
    @Override
    @Transactional(readOnly = true)
    public List<BankAccountMasterDto> findAll(final Long orgId) {
        final Iterable<BankAccountMasterEntity> entities = bankAccountJpaRepository.findBankAccountsByOrgId(orgId);
        final List<BankAccountMasterDto> beans = new ArrayList<>();
        BankAccountMasterDto dto = null;
        for (final BankAccountMasterEntity tbBankaccountEntity : entities) {
            dto = new BankAccountMasterDto();
            BeanUtils.copyProperties(tbBankaccountEntity, dto);
            beans.add(dto);
        }
        return beans;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.BankAccountService#findByAccountCode(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean findByAccountNoAndFund(final String baAccountNo, final Long fundId) {
        final Long count = bankAccountJpaRepository.findAccounNoAndFund(baAccountNo, fundId);
        if (count != 0) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.BankAccountService#create(com.abm.mainetservice.account.bean.BankAccountMaster)
     */
    @Override
    @Transactional
    public BankAccountMaster create(final BankAccountMaster accountMaster, final Organisation org, final Organisation org1,
            final Long userId, final int langId,
            final Long accountStatus) {

        BankAccountMasterEntity bankAccountEntity = null;
        final Long bankCpdId = getBankCpdId(org);
        final List<String> desc = new ArrayList<>(0);
        SecondaryheadMaster tbAcSecondaryheadMaster = null;
        final List<BankAccountMasterEntity> accountList = new ArrayList<>();
        for (final BankAccountMasterDto dto : accountMaster.getListOfTbBankaccount()) {

            bankAccountEntity = new BankAccountMasterEntity();
            BeanUtils.copyProperties(dto, bankAccountEntity);

            bankAccountEntity.setAcCpdIdStatus(accountStatus);
            bankAccountEntity.setOrgId(accountMaster.getOrgId());
            bankAccountEntity.setCreatedBy(userId);
            bankAccountEntity.setLgIpMac(accountMaster.getIpMacAddress());
            // bankAccountEntity.setLangId((long) langId);
            bankAccountEntity.setCreatedDate(new Date());
            bankAccountEntity.setFunctionId(dto.getFunctionId());
            BankMasterEntity bankEntity = new BankMasterEntity();
            bankEntity.setBankId(Long.valueOf(accountMaster.getBankName()));
            dto.setBankId(Long.valueOf(accountMaster.getBankName()));
            bankAccountEntity.setBankId(bankEntity);
            bankAccountEntity.setBankType(accountMaster.getBankType());
            BankAccountMasterEntity finalEntity = bankAccountJpaRepository.save(bankAccountEntity);
            dto.setBaAccountId(finalEntity.getBaAccountId());
            insertBankAccountDataIntoPropertyTaxTableByUsingSoapWS(finalEntity);
            accountList.add(bankAccountEntity);
            if (dto.getPacHeadId() != null) {
                desc.add(dto.getSacHeadDesc());
            }
        }
        int i = 0;
        for (final BankAccountMasterDto accountEntity : accountMaster.getListOfTbBankaccount()) {
            if (accountEntity.getPacHeadId() != null) {
                tbAcSecondaryheadMaster = new SecondaryheadMaster();

                final List<LookUp> accountTypeLevel = CommonMasterUtility.getListLookup(PrefixConstants.CMD_PREFIX, org1);
                for (final LookUp lookUp : accountTypeLevel) {
                    if (lookUp != null) {
                        if (PrefixConstants.SECONDARY_CPD_VALUE.equalsIgnoreCase(lookUp.getLookUpCode())) {
                            if ((lookUp.getOtherField() != null) && !lookUp.getOtherField().isEmpty()) {
                                if (lookUp.getOtherField().equals(MainetConstants.MASTER.Y)) {
                                    tbAcSecondaryheadMaster.setSecondaryStatus(MainetConstants.MASTER.Y);
                                }
                            }
                        }
                    }
                }

                tbAcSecondaryheadMaster.setBaAccountid(accountEntity.getBaAccountId());
                if ((accountEntity.getFunctionId() != null) && (accountEntity.getFunctionId() != 0)) {
                    tbAcSecondaryheadMaster.setFunctionId(accountEntity.getFunctionId());
                }
                tbAcSecondaryheadMaster.setPacHeadId(accountEntity.getPacHeadId());
                if ((accountEntity.getFundId() != null) && (accountEntity.getFundId() != 0)) {
                    tbAcSecondaryheadMaster.setFundId(accountEntity.getFundId());
                }
                if ((accountEntity.getFieldId() != null) && (accountEntity.getFieldId() != 0)) {
                    tbAcSecondaryheadMaster.setFieldId(accountEntity.getFieldId());
                }
                tbAcSecondaryheadMaster.setSacLeddgerTypeCpdId(bankCpdId);
                tbAcSecondaryheadMaster.setOrgid(org1.getOrgid());
                tbAcSecondaryheadMaster.setUserId(userId);
                tbAcSecondaryheadMaster.setLmoddate(new Date());
                tbAcSecondaryheadMaster.setLgIpMac(accountMaster.getIpMacAddress());
                String bank = bankMasterJpaRepository.fetchBankNameDescById(accountEntity.getBankId());
                if (bank != null && !bank.isEmpty()) {
                    tbAcSecondaryheadMaster
                            .setSacHeadDesc(bank + MainetConstants.SEPARATOR
                                    + accountEntity.getBaAccountNo() + MainetConstants.SEPARATOR
                                    + accountEntity.getBaAccountName());
                }
               tbAcSecondaryheadMaster.setOldSacHeadCode(accountEntity.getAccOldHeadCode());
                secondaryheadMasterService.saveSecondaryHeadData(tbAcSecondaryheadMaster, org, langId);
                i++;
            }
        }

        return accountMaster;
    }

    private void insertBankAccountDataIntoPropertyTaxTableByUsingSoapWS(BankAccountMasterEntity finalEntity) {

        try {
            BankAccount bankAccount = new BankAccount();
            // bankAccount.setStatus("Status900842037");
            bankAccount.setBAAccountid(finalEntity.getBaAccountId());
            // bankAccount.setModifiedIn(finalEntity.getCreatedBy().toString());
            // bankAccount.setCreatedBy(finalEntity.getCreatedBy().toString());
            bankAccount.setBAAccountNo(finalEntity.getBaAccountNo());
            // bankAccount.setSheetId("SheetId661268621");
            if (finalEntity.getCreatedDate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(finalEntity.getCreatedDate());
                bankAccount.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            bankAccount.setACCPDIDSTATUS(finalEntity.getAcCpdIdStatus());
            bankAccount.setCPDACCOUNTTYPE(finalEntity.getCpdAccountType());
            // bankAccount.setCreatedAt(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-05-15T11:17:26.990+05:30"));
            // bankAccount.setMetadata(finalEntity.getLgIpMac());
            bankAccount.setSheetName(finalEntity.getBaAccountId().toString());
            bankAccount.setBAAccountname(finalEntity.getBaAccountName());
            if (finalEntity.getUpdatedBy() != null) {
                bankAccount.setModifiedBy(finalEntity.getUpdatedBy().toString());
            }
            // bankAccount.setCaption("Caption-333477853");
            if (finalEntity.getBankId() != null && finalEntity.getBankId().getBankId() != null) {
                bankAccount.setBANKID(finalEntity.getBankId().getBankId());
            }
            // bankAccount.setAssignedTo("AssignedTo-1507879371");
            if (finalEntity.getUpdatedDate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(finalEntity.getUpdatedDate());
                bankAccount.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            bankAccount.setOrgId(finalEntity.getOrgId());
            // bankAccount.setTenant("Tenant-731033452");
            // bankAccount.setProcessInstance("ProcessInstance676192517");
            // bankAccount.setSheetMetadataName("SheetMetadataName13659033");
            bankAccountSoapWSProvisionService.createBankAccountHead(bankAccount);

        } catch (DatatypeConfigurationException ex) {
            throw new FrameworkException(DATE_COVERT_EXCEPTION + ex);
        }
    }

    public Long getBankCpdId(final Organisation org) {
        final List<LookUp> list = CommonMasterUtility.getLookUps(MainetConstants.BANK_MASTER.FTY, org);
        Long bankCpdId = null;
        for (final LookUp bankObj : list) {
            if (bankObj.getLookUpCode().equalsIgnoreCase(MainetConstants.BANK_MASTER.BK)) {
                bankCpdId = bankObj.getLookUpId();
                break;
            }
        }
        return bankCpdId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankAccountMasterDto> findByAccNo(final String accountNo) {

        final List<BankAccountMasterEntity> entitiesList = bankAccountJpaRepository.findByAccountNo(accountNo);
        final List<BankAccountMasterDto> beansList = new ArrayList<>();
        BankAccountMasterDto dto = null;
        for (final BankAccountMasterEntity bankAcccountEntity : entitiesList) {
            dto = new BankAccountMasterDto();
            BeanUtils.copyProperties(bankAcccountEntity, dto);
            beansList.add(dto);
        }
        return beansList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.BankAccountService#findByAccountId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public BankAccountMasterDto findAccountByAccountId(final Long accountId) {
        final BankAccountMasterDto accountDto = new BankAccountMasterDto();
        if (accountId != null) {
            final BankAccountMasterEntity entity = bankAccountJpaRepository.findOne(accountId);
            Objects.requireNonNull(entity,
                    ApplicationSession.getInstance().getMessage("bank.account.service.record") + accountId);

            if (entity.getBankId() != null && entity.getBankId().getBankId() != null) {
                accountDto.setBankId(entity.getBankId().getBankId());
            }
            accountDto.setOrgId(entity.getOrgId());
            if (entity.getBankType() != null) {
                accountDto.setBankTypeDesc(entity.getBankType().toString());
            }
            accountDto.setFunctionId(entity.getFunctionId());
            BeanUtils.copyProperties(entity, accountDto);

        } else {
            throw new IllegalArgumentException(
                    "baAccountId cannot be null or zero to query from TB_BANK_ACCOUNT [accountId=" + accountId + "]");
        }
        return accountDto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.BankAccountService#getBankbyBranchId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public BankMasterDto getBankbyBranchId(final Long branchId) {

        final BankMasterDto bankDto = new BankMasterDto();
        if ((branchId != null) && (branchId > 0l)) {
            final BankMasterEntity entity = bankMasterJpaRepository.findOne(branchId);
            BeanUtils.copyProperties(entity, bankDto);
        } else {
            throw new IllegalArgumentException(
                    "bankId cannot be null or zero to query from TB_BANK_MASTER [branchId=" + branchId + "]");
        }
        return bankDto;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.BankAccountService#update(com.abm.mainetservice.account.bean.BankAccountMaster)
     */
    @Override
    @Transactional
    public BankAccountMaster update(final BankAccountMaster bankAccountMas, final Organisation org, final int langId) {
        SecondaryheadMaster secondaryheadMaster = null;
        BankAccountMasterEntity accountEntity = null;
        for (final BankAccountMasterDto accountDto : bankAccountMas.getListOfTbBankaccount()) {
            accountEntity = bankAccountJpaRepository.findOne(bankAccountMas.getAccountId());
            accountEntity.setBaAccountNo(accountDto.getBaAccountNo());
            accountEntity.setBaAccountName(accountDto.getBaAccountName());
            accountEntity.setCpdAccountType(accountDto.getCpdAccountType());
            accountEntity.setFunctionId(accountDto.getFunctionId());
            accountEntity.setFieldId(accountDto.getFieldId());
            accountEntity.setFundId(accountDto.getFundId());
            accountEntity.setAcCpdIdStatus(accountDto.getAcCpdIdStatus());
            accountEntity.setOrgId(bankAccountMas.getOrgId());
            BankMasterEntity bankEntity = new BankMasterEntity();
            bankEntity.setBankId(Long.valueOf(bankAccountMas.getBankName()));
            accountEntity.setBankId(bankEntity);
            accountEntity.setBankType(bankAccountMas.getBankType());
            accountEntity = bankAccountJpaRepository.save(accountEntity);
            // updateBankAccountDataIntoPropertyTaxTableByUsingSoapWS(accountEntity);
            if (accountDto.getPacHeadId() != null) {
                secondaryheadMaster = new SecondaryheadMaster();
                final Long secHeadId = getSeconaryIdByAccountId(bankAccountMas.getAccountId());
                if ((secHeadId != null)&& (secHeadId != 0l) ) {
                    secondaryheadMaster.setSacHeadId(secHeadId);
                }
                secondaryheadMaster.setBaAccountid(accountEntity.getBaAccountId());
                String bank = bankMasterJpaRepository.fetchBankNameDescById(accountEntity.getBankId().getBankId());
                if (bank != null && !bank.isEmpty()) {
                    secondaryheadMaster
                            .setSacHeadDesc(bank + MainetConstants.SEPARATOR
                                    + accountEntity.getBaAccountNo() + MainetConstants.SEPARATOR
                                    + accountEntity.getBaAccountName());
                }
                // secondaryheadMaster.setSacHeadDesc(accountDto.getSacHeadDesc());
                secondaryheadMasterService.saveSecondaryHeadData(secondaryheadMaster, org, langId);
            }
        }
        return bankAccountMas;
    }

    private void updateBankAccountDataIntoPropertyTaxTableByUsingSoapWS(BankAccountMasterEntity accountEntity) {

        try {
            BankAccount bankAccount = new BankAccount();
            // bankAccount.setStatus("Status900842037");
            bankAccount.setBAAccountid(accountEntity.getBaAccountId());
            // bankAccount.setModifiedIn(accountEntity.getCreatedBy().toString());
            // bankAccount.setCreatedBy(accountEntity.getCreatedBy().toString());
            bankAccount.setBAAccountNo(accountEntity.getBaAccountNo());
            // bankAccount.setSheetId("SheetId661268621");
            if (accountEntity.getCreatedDate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(accountEntity.getCreatedDate());
                bankAccount.setCreatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            bankAccount.setACCPDIDSTATUS(accountEntity.getAcCpdIdStatus());
            bankAccount.setCPDACCOUNTTYPE(accountEntity.getCpdAccountType());
            // bankAccount.setCreatedAt(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-05-15T11:17:26.990+05:30"));
            // bankAccount.setMetadata(accountEntity.getLgIpMac());
            bankAccount.setSheetName(accountEntity.getBaAccountId().toString());
            bankAccount.setBAAccountname(accountEntity.getBaAccountName());
            if (accountEntity.getUpdatedBy() != null) {
                bankAccount.setModifiedBy(accountEntity.getUpdatedBy().toString());
            }
            // bankAccount.setCaption("Caption-333477853");
            if (accountEntity.getBankId() != null && accountEntity.getBankId().getBankId() != null) {
                bankAccount.setBANKID(accountEntity.getBankId().getBankId());
            }
            // bankAccount.setAssignedTo("AssignedTo-1507879371");
            if (accountEntity.getUpdatedDate() != null) {
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(accountEntity.getUpdatedDate());
                bankAccount.setModifiedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            }
            bankAccount.setOrgId(accountEntity.getOrgId());
            // bankAccount.setTenant("Tenant-731033452");
            // bankAccount.setProcessInstance("ProcessInstance676192517");
            // bankAccount.setSheetMetadataName("SheetMetadataName13659033");
            bankAccountSoapWSProvisionService.updateBankAccountHead(bankAccount);

        } catch (DatatypeConfigurationException ex) {
            throw new FrameworkException(DATE_COVERT_EXCEPTION + ex);
        }

    }

    private Long getSeconaryIdByAccountId(final Long accountId) {
        Long secHeadId = 0l;
        if (accountId != null) {
            secHeadId = bankAccountJpaRepository.getSecHeadIdbyAccountId(accountId);
        }
        return secHeadId;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.BankAccountService#findDuplicateCombination(java.lang.String, java.lang.Long,
     * java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean validateDuplicateCombination(final String baAccountNo, final Long functionId, final Long pacHeadId,
            final Long fieldId, final Long fundId) {

        final int count = bankAccountJpaRepository.findDuplicateCombination(baAccountNo, functionId, pacHeadId, fieldId, fundId);
        if (count != 0) {
            return true;
        }
        return false;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.BankAccountService#getAllBankAccountByUlbBankId(java.lang.Long)
     */

    @Override
    public BankAccountMaster viewBankAccountForm(final Long bankAccountId, final Long orgId) {

        final BankAccountMaster viewAccount = new BankAccountMaster();

        final BankAccountMasterDto account = findAccountByAccountId(bankAccountId);
        viewAccount.setViewAccountNo(account.getBaAccountNo());
        viewAccount.setViewAccountName(account.getBaAccountName());
        viewAccount
                .setViewAccountType(CommonMasterUtility.findLookUpDesc(PrefixConstants.ACT, orgId, account.getCpdAccountType()));
        viewAccount
                .setViewBankTypeDesc(
                        CommonMasterUtility.findLookUpDesc(PrefixConstants.BAT, orgId, Long.valueOf(account.getBankTypeDesc())));
        viewAccount
                .setViewAccountStatus(CommonMasterUtility.findLookUpDesc(PrefixConstants.BAS, orgId, account.getAcCpdIdStatus()));

        if (account.getFunctionId() != null) {
            viewAccount.setFunctionId(account.getFunctionId());
        }
        viewAccount.setPacHeadId(account.getPacHeadId());
        viewAccount.setFieldId(account.getFieldId());
        viewAccount.setFundId(account.getFundId());

        final BankMasterDto bank = getBankbyBranchId(account.getBankId());
        viewAccount.setBankName(
                bank.getBank() + MainetConstants.WHITE_SPACE + bank.getBranch() + MainetConstants.WHITE_SPACE + bank.getIfsc());
        return viewAccount;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCombinationExists(final String bankName, final String baAccountNo, final Long orgid) {

        final int count = bankAccountJpaRepository.isCombinationExists(bankName, baAccountNo, orgid);
        if (count != 0) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.service.TbBankmasterService#getBankNameList(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<String> getBankNameStatusList(final String status) {
        final List<String> bankNames = bankMasterJpaRepository.getAllBankNameStatus(status);
        return bankNames;
    }

    /*
     * @Override
     * @Transactional public List<UlbBankMasterEntity> getBankList(final Long bankId, final Long orgId, final String status) {
     * return ulbBankJpaRepository.getBankList(bankId, orgId, status); }
     */

    @Override
    @Transactional
    public List<BankAccountMasterDto> findByAllGridSearchData(String accountNo, Long accountNameId, Long bankId, Long orgId) {

        final List<BankAccountMasterEntity> entities = bankAccountJpaRepository.findByAllGridSearchData(accountNo, accountNameId,
                bankId, orgId);
        final List<BankAccountMasterDto> beans = new ArrayList<>();
        BankAccountMasterDto dto = null;
        if (entities != null && !entities.isEmpty()) {
            for (final BankAccountMasterEntity tbBankaccountEntity : entities) {
                dto = new BankAccountMasterDto();
                // dto.setUlbBankId(tbBankaccountEntity.getUlbBankId().getUlbBankId());
                BeanUtils.copyProperties(tbBankaccountEntity, dto);
                beans.add(dto);
            }
        }
        return beans;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankAccountMasterEntity> getBankAccountMasterDet(long orgid) {

        return bankAccountJpaRepository.getBankAccountMasterDet(orgid);
    }

    @Override
    public void saveBankAccountMasterExcelData(BankAccountMasterUploadDTO bankAccountMasterUploadDto,
            Organisation defaultOrg, Organisation org, Long userId, int langId, Long accountStatus) {
        BankAccountMaster accountMaster = new BankAccountMaster();
        accountMaster.setOrgId(bankAccountMasterUploadDto.getOrgid());
        accountMaster.setIpMacAddress(bankAccountMasterUploadDto.getLgIpMac());
        accountMaster.setBankName(bankAccountMasterUploadDto.getBankName());
        accountMaster.setBankType(Long.valueOf(bankAccountMasterUploadDto.getBanktype()));
        List<BankAccountMasterDto> list = new ArrayList<BankAccountMasterDto>();
        BankAccountMasterDto dto = new BankAccountMasterDto();
        dto.setBaAccountNo(bankAccountMasterUploadDto.getAccNum().toString());
        dto.setFunctionId(Long.valueOf(bankAccountMasterUploadDto.getFunction()));
        dto.setBaAccountName(bankAccountMasterUploadDto.getAccName());
        dto.setCpdAccountType(Long.valueOf(bankAccountMasterUploadDto.getType()));
        dto.setPacHeadId(Long.valueOf(bankAccountMasterUploadDto.getPrimaryHead()));
        dto.setAccOldHeadCode(bankAccountMasterUploadDto.getAccOldHeadCode());
        list.add(dto);
        accountMaster.setListOfTbBankaccount(list);
        create(accountMaster, defaultOrg, org, userId, langId, accountStatus);
        // bankAccountJpaRepository.save(accountMasterEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findBankDeatilsInBankAccount(long orgid) {
        // TODO Auto-generated method stub
        return bankAccountJpaRepository.findBankDeatilsInBankAccount(orgid);
    }
}
