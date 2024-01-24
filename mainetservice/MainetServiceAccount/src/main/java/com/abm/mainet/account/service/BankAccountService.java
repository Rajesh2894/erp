package com.abm.mainet.account.service;

import java.util.List;

import com.abm.mainet.account.dto.BankAccountMaster;
import com.abm.mainet.account.dto.BankAccountMasterUploadDTO;
import com.abm.mainet.account.dto.BankMasterDto;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;

/**
 * Business Service Interface for entity TbBankmaster.
 */
public interface BankAccountService {

    /**
     * get All bank name from bank master
     * @param entity
     * @return
     */

    List<String> getBankNameList();

    List<String> getBankNameStatusList(String status);

    /**
     * Loads an entity from the database using its Primary Key
     * @param bmBankid
     * @param orgid
     * @return entity
     */
    List<BankAccountMasterDto> findByAccNo(String baAccountId);

    /**
     * get All bank branch name by bank name from bank master
     * @param entity
     * @return
     */
    List<Object[]> getBranchNamesByBank(String bankName);

    /**
     * get All bank branch name by bank name from bank master
     * @param entity
     * @return
     */

    List<BankAccountMasterDto> findAll(Long orgId);

    boolean findByAccountNoAndFund(String baAccountNo, Long fundId);

    /**
     * Creates the given entity in the database
     * @param entity
     * @return
     */
    BankAccountMaster create(BankAccountMaster accountMaster, Organisation org, Organisation org1, Long userId, int langId,
            Long accountStatus);

    /**
     * Updates the given entity in the database
     * @param entity
     * @return
     */
    BankAccountMaster update(BankAccountMaster entity, Organisation org, int langId);

    /**
     * single account details from the database using its Primary Key
     * @param accountId
     * @return entity
     */
    BankAccountMasterDto findAccountByAccountId(Long accountId);

    /**
     * single BANK details from the database using its Primary Key
     * @param branchId
     * @return entity
     */

    BankMasterDto getBankbyBranchId(Long branchId);

    /**
     * validate duplicate combination
     * @return
     */

    boolean validateDuplicateCombination(String baAccountNo, Long functionId, Long pacHeadId, Long fieldId, Long fundId);

    /**
     * get All bank branch name by bank id from bank master
     * @param entity
     * @return
     */

    BankAccountMaster viewBankAccountForm(Long bankAccountId, Long orgId);

    boolean isCombinationExists(String bankName, String baAccountNo, Long orgid);

    List<BankAccountMasterDto> findByAllGridSearchData(String accountNo, Long accountNameId, Long bankId, Long orgId);

    List<BankAccountMasterEntity> getBankAccountMasterDet(long orgid);

    public void saveBankAccountMasterExcelData(BankAccountMasterUploadDTO bankAccountMasterUploadDto, Organisation defaultOrg,
            Organisation org, Long userId, int langId, Long accountStatus);

    List<Object[]> findBankDeatilsInBankAccount(long orgid);

}
