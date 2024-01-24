package com.abm.mainet.account.ui.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.abm.mainet.account.dto.BankAccountMasterUploadDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;

public class BankAccountMasterValidator {

    public int count = 0;

    public List<BankAccountMasterUploadDTO> excelValidation(List<BankAccountMasterUploadDTO> bankAccountMasterUploadDtos,
            BindingResult bindingResult, Map<Long, String> bankMap, final List<LookUp> bankType, final List<LookUp> accountType,
            final Map<Long, String> functionHead, final Map<Long, String> primaryHead) {
        int rowNo = 0;
        final List<BankAccountMasterUploadDTO> bankAccountMasterUploadDtoList = new ArrayList<>();

        final ApplicationSession session = ApplicationSession.getInstance();

        Set<BankAccountMasterUploadDTO> bankAccountMasterExport = bankAccountMasterUploadDtos.stream()
                .filter(dto -> Collections.frequency(bankAccountMasterUploadDtos, dto) > 1)
                .collect(Collectors.toSet());
        if (bankAccountMasterExport.isEmpty()) {

            for (BankAccountMasterUploadDTO bankAccountMasterUploadDto : bankAccountMasterUploadDtos) {

                BankAccountMasterUploadDTO dto = new BankAccountMasterUploadDTO();
                rowNo++;
                int bankName = 0;
                if (bankAccountMasterUploadDto.getBankName() == null || bankAccountMasterUploadDto.getBankName().isEmpty()) {
                    bankName++;
                }

                if (bankName != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("accounts.bankAccount.excel.bankName") + rowNo));
                    break;
                }

                int bankTypes = 0;
                if (bankAccountMasterUploadDto.getBanktype() == null || bankAccountMasterUploadDto.getBanktype().isEmpty()) {
                    bankTypes++;
                }

                if (bankTypes != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("Bank Type Should not be empty at Row") + rowNo));
                    break;
                }

                int accNum = 0;
                if (bankAccountMasterUploadDto.getAccNum() == null) {
                    accNum++;
                } else {
                    dto.setAccNum(bankAccountMasterUploadDto.getAccNum());
                }

                if (accNum != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("accounts.bankAccount.excel.accountNum") + rowNo));
                    break;
                }

                int accName = 0;
                if (bankAccountMasterUploadDto.getAccName() == null || bankAccountMasterUploadDto.getAccName().isEmpty()) {
                    accName++;
                } else {
                    dto.setAccName(bankAccountMasterUploadDto.getAccName());
                }
                if (accName != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("accounts.bankAccount.excel.accName") + rowNo));
                    break;
                }

                int accType = 0;
                if (bankAccountMasterUploadDto.getType() == null || bankAccountMasterUploadDto.getType().isEmpty()) {
                    accType++;
                }

                if (accType != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("accounts.bankAccount.excel.accType") + rowNo));
                    break;
                }

                int function = 0;
                if (bankAccountMasterUploadDto.getFunction() == null || bankAccountMasterUploadDto.getFunction().isEmpty()) {
                    function++;
                }

                if (function != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("accounts.bankAccount.excel.function") + rowNo));
                    break;
                }

                int primary = 0;
                if (bankAccountMasterUploadDto.getPrimaryHead() == null
                        || bankAccountMasterUploadDto.getPrimaryHead().isEmpty()) {
                    primary++;
                }

                if (primary != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("accounts.bankAccount.excel.primaryHead") + rowNo));
                    break;
                }

                /*
                 * int accountHead = 0; if (bankAccountMasterUploadDto.getAccountHead() == null ||
                 * bankAccountMasterUploadDto.getAccountHead().isEmpty()) { accountHead++; } if (accountHead != 0) { count++;
                 * bindingResult.addError(new org.springframework.validation.FieldError(
                 * MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK, null, false, new String[] {
                 * MainetConstants.ERRORS }, null, session.getMessage("accounts.bankAccount.excel.accountHead") + rowNo)); break;
                 * }
                 */

                int bankNameExist = 0;
                for (Map.Entry<Long, String> entry : bankMap.entrySet()) {

                    if (bankAccountMasterUploadDto.getBankName().equalsIgnoreCase(entry.getValue())) {
                        dto.setBankName(entry.getKey().toString());
                        bankNameExist++;
                    }
                }

                if (bankNameExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null, rowNo +
                                    session.getMessage("accounts.bankAccount.excel.bankNameExist")));
                    break;
                }

                int bankTypeExist = 0;
                for (LookUp list : bankType) {

                    if (bankAccountMasterUploadDto.getBanktype().trim().equalsIgnoreCase(list.getDescLangFirst())) {
                        dto.setBanktype(String.valueOf(list.getLookUpId()));
                        bankTypeExist++;
                    }
                }

                if (bankTypeExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null, rowNo +
                                    session.getMessage("accounts.bankAccount.excel.bankTypeExist")));
                    break;
                }

                int accTypeExist = 0;
                for (LookUp list : accountType) {

                    if (bankAccountMasterUploadDto.getType().equalsIgnoreCase(list.getLookUpDesc())) {
                        dto.setType(String.valueOf(list.getLookUpId()));
                        accTypeExist++;
                    }
                }

                if (accTypeExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null, rowNo +
                                    session.getMessage("accounts.bankAccount.excel.accTypeExist")));
                    break;
                }

                int functionExist = 0;
                for (Map.Entry<Long, String> entry : functionHead.entrySet()) {

                    if (bankAccountMasterUploadDto.getFunction().equalsIgnoreCase(entry.getValue())) {
                        dto.setFunction(entry.getKey().toString());
                        functionExist++;
                    }
                }

                if (functionExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null, rowNo +
                                    session.getMessage("accounts.bankAccount.excel.functionExist")));
                    break;
                }

                int primaryExist = 0;
                for (Map.Entry<Long, String> entry : primaryHead.entrySet()) {

                    if (bankAccountMasterUploadDto.getPrimaryHead().equalsIgnoreCase(entry.getValue())) {
                        dto.setPrimaryHead(entry.getKey().toString());
                        primaryExist++;
                    }
                }

                if (primaryExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null, rowNo +
                                    session.getMessage("accounts.bankAccount.excel.primaryHeadExist")));
                    break;
                }
dto.setAccOldHeadCode(bankAccountMasterUploadDto.getAccOldHeadCode());
                bankAccountMasterUploadDtoList.add(dto);
            }
        } else {
            count++;
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK, null,
                    false, new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("accounts.Secondaryhead.empty.excel.duplicate")));
        }
        return bankAccountMasterUploadDtoList;
    }

    public void isAccNumExists(List<BankAccountMasterUploadDTO> bankAccountMasterUploadDtosUploadList,
            BindingResult bindingResult, List<BankAccountMasterEntity> mainList) {
        final ApplicationSession session = ApplicationSession.getInstance();
        // BankAccountMasterUploadDTO dto = new BankAccountMasterUploadDTO();
        for (BankAccountMasterUploadDTO accountMasterUploadDTO : bankAccountMasterUploadDtosUploadList) {
            int isAccNumExists = 0;
            for (BankAccountMasterEntity accountMasterEntity : mainList) {
                Long bankId = accountMasterEntity.getBankId().getBankId();
                String AccNum = accountMasterEntity.getBaAccountNo();
                if (accountMasterUploadDTO.getBankName().equals(bankId.toString())
                        && accountMasterUploadDTO.getAccNum().equals(AccNum)) {
                    isAccNumExists++;
                }
            }
            if (isAccNumExists != 0) {
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.BankAccountMaster.BANK_ACC_MASTER, MainetConstants.BLANK, null,
                        false, new String[] { MainetConstants.ERRORS }, null,
                        session.getMessage("accounts.bankAccount.excel.dupaccNum") + "-" + accountMasterUploadDTO.getAccNum()));
                break;
            }
        }

    }
}
