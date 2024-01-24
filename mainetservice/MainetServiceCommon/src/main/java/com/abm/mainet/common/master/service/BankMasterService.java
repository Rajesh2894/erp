package com.abm.mainet.common.master.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.master.dto.BankMasterDTO;
import com.abm.mainet.common.master.dto.BankMasterUploadDTO;
@WebService
public interface BankMasterService {

    public BankMasterEntity isCombinationIfscCodeExists(String ifsc);

    public BankMasterEntity isCombinationBranchNameExists(String bank, String branch);

    public BankMasterDTO saveBankMasterFormData(BankMasterDTO tbBankMaster);

    public List<BankMasterDTO> findByAllGridData();

    public List<BankMasterDTO> findByAllGridSearchData(String bank, String branch, String ifsc, String micr, String city);

    public BankMasterDTO getDetailsUsingBankId(BankMasterDTO bankMasterDTO);

    public List<BankMasterEntity> getBankList();

    public List<Object[]> getChequeDDNoBankAccountNames(Long bankAccountId);

    public BankMasterEntity isDuplicateMICR(String micr);

public void saveBankMasterExcelData(BankMasterUploadDTO bankMasterUploadDto, Long orgId, int langId);

public List<BankMasterDTO> getBankListDto();

public List<BankMasterEntity> getBankListByName(String bankName);

public List<Object[]> getBankListGroupBy();

public String getBankBranchName(Long bankId);

/**
 * @param ifscCode
 * @return
 */
public String getBankById(Long bankId);

/**
 * @param valueOf
 * @return
 */
public String getIfscCodeById(Long bankId);
public List<String> getBankDetails();

}
