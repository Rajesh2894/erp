package com.abm.mainet.common.master.dao;

import java.util.List;

import com.abm.mainet.common.domain.BankMasterEntity;

public interface BankMasterDao {

    public BankMasterEntity isCombinationIfscCodeExists(String ifsc);

    public BankMasterEntity isCombinationBranchNameExists(String bank, String branch);

    List<BankMasterEntity> findByAllGridSearchData(String bank, String branch, String ifsc, String micr, String city);

    List<BankMasterEntity> getBankList();

    List<Object[]> getChequeDDNoBankAccountNames(Long bankAccountId);

    public BankMasterEntity isDuplicateMICR(String micr);

	public List<BankMasterEntity> getBankListByName(String bankName);

	public List<Object[]> getBankListGroupBy();

	public String getBankBranch(Long bankId);
}
