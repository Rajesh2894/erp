package com.abm.mainet.account.ui.validator;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;

import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.dto.AccountVoucherDetailsUploadDTO;
import com.abm.mainet.account.dto.AccountVoucherMasterUploadDTO;
import com.abm.mainet.account.ui.model.AccountJournalVoucherEntryModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;

public class VoucherEntryValidator {

    int count = 0;

	private static final Logger LOGGER = Logger.getLogger(VoucherEntryValidator.class);

    public List<AccountVoucherMasterUploadDTO> excelValidation(List<AccountVoucherMasterUploadDTO> masterList,
            BindingResult bindingResult, Map<Long, String> voucherTypeMap, List<LookUp> voucherSubTypeList,
            List<LookUp> drCrTypes, Map<String, String> voucherCodeMap, AccountJournalVoucherEntryModel voucherModel,
            Map<Long, String> secondaryHeadDescMap, Map<Long, String> oldSecondaryHeadDescMap) {
        int rowNo = 1;
        final List<AccountVoucherMasterUploadDTO> voucherEntryUploadDtoList = new ArrayList<>();

        List<AccountVoucherDetailsUploadDTO> detailList = new ArrayList<>();

        final ApplicationSession session = ApplicationSession.getInstance();
        for (AccountVoucherMasterUploadDTO voucherEntryUploadDto : masterList) {
            AccountVoucherMasterUploadDTO dto = new AccountVoucherMasterUploadDTO();
            rowNo++;
            int tranDate = 0;
            if (voucherEntryUploadDto.getTranDate() == null || voucherEntryUploadDto.getTranDate().isEmpty()) {
                tranDate++;
                Calendar instance = Calendar.getInstance();

            } else {
                dto.setTranDate(voucherEntryUploadDto.getTranDate());
            }

            if (tranDate != 0) {
                count++;
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null, false,
                        new String[] { MainetConstants.ERRORS }, null,
                        session.getMessage("account.journal.voucher.tranDate") + rowNo));
                break;
            }

            int voucherType = 0;
            if (voucherEntryUploadDto.getVoucherType() == null) {
                voucherType++;
            }

            if (voucherType != 0) {
                count++;
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null, false,
                        new String[] { MainetConstants.ERRORS }, null,
                        session.getMessage("account.journal.voucher.voucherType") + rowNo));
                break;
            }

            int voucherSubType = 0;
            if (voucherEntryUploadDto.getVoucherSubType() == null
                    || voucherEntryUploadDto.getVoucherSubType().isEmpty()) {
                voucherSubType++;
            }

            if (voucherSubType != 0) {
                count++;
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null, false,
                        new String[] { MainetConstants.ERRORS }, null,
                        session.getMessage("account.journal.voucher.voucherSubType") + rowNo));
                break;
            }

            int narration = 0;
            if (voucherEntryUploadDto.getNarration() == null || voucherEntryUploadDto.getNarration().isEmpty()) {
                narration++;
            } else {
                dto.setNarration(voucherEntryUploadDto.getNarration());
            }

            if (narration != 0) {
                count++;
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null, false,
                        new String[] { MainetConstants.ERRORS }, null,
                        session.getMessage("account.journal.voucher.narration") + rowNo));
                break;
            }

            int voucherTypeExist = 0;
            for (Map.Entry<Long, String> entry : voucherTypeMap.entrySet()) {

                if (voucherEntryUploadDto.getVoucherType().trim().equalsIgnoreCase(entry.getValue().trim())) {
                    dto.setVoucherType(entry.getKey().toString());
                    voucherTypeExist++;
                }
            }

            if (voucherTypeExist == 0) {
                count++;
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null, false,
                        new String[] { MainetConstants.ERRORS }, null,
                        rowNo + session.getMessage("account.journal.voucher.voucherTypeExist")));
                break;
            }

            int voucherSubTypeExist = 0;
            for (LookUp list : voucherSubTypeList) {

                if (voucherEntryUploadDto.getVoucherSubType().trim().equalsIgnoreCase(list.getLookUpDesc().trim())) {
                    dto.setVoucherSubType(String.valueOf(list.getLookUpId()));
                    voucherSubTypeExist++;
                }
            }

            if (voucherSubTypeExist == 0) {
                count++;
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null, false,
                        new String[] { MainetConstants.ERRORS }, null,
                        rowNo + session.getMessage("account.journal.voucher.voucherSubTypeExist")));
                break;
            }

			/*
			 * if (voucherEntryUploadDto.getTranRefNo() == null ||
			 * voucherEntryUploadDto.getTranRefNo().isEmpty()) { bindingResult.addError(new
			 * org.springframework.validation.FieldError(
			 * MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE,
			 * MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
			 * null, session.getMessage("Tran Ref No should not be empty at Row") + rowNo));
			 * break; }
			 */

            String tranRefNo = null;
            String tranRef = voucherEntryUploadDto.getTranRefNo();
            if (tranRef != null && !tranRef.isEmpty() && tranRef.contains(".")) {
                int cnt = tranRef.indexOf(".");
                tranRefNo = voucherEntryUploadDto.getTranRefNo().substring(0, cnt);
                dto.setTranRefNo(voucherEntryUploadDto.getTranRefNo().substring(0, cnt));
            } else {
                dto.setTranRefNo(voucherEntryUploadDto.getTranRefNo());
            }

            detailList = voucherEntryUploadDto.getDetails();
            if (detailList != null) {
                List<AccountVoucherDetailsUploadDTO> voucherDetailList = new ArrayList<>();
                List<String> accHeadList = new ArrayList<>();
                int accHeadExist = 0;
                BigDecimal debitAmount = new BigDecimal(0);
                BigDecimal creditAmount = new BigDecimal(0);
                for (AccountVoucherDetailsUploadDTO details : detailList) {
                    AccountVoucherDetailsUploadDTO detailDto = new AccountVoucherDetailsUploadDTO();

                    int drOrCr = 0;
                    if (details.getDrOrCr() == null) {
                        drOrCr++;
                    }
                    if (drOrCr != 0) {
                        count++;
                        bindingResult.addError(new org.springframework.validation.FieldError(
                                MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null,
                                false, new String[] { MainetConstants.ERRORS }, null,
                                session.getMessage("account.journal.voucher.DR/CR") + rowNo));
                        break;
                    }

                    int accHead = 0;
                    if (details.getAccHead() == null) {
                        accHead++;
                    }

                    if (accHead != 0) {
                        count++;
                        bindingResult.addError(new org.springframework.validation.FieldError(
                                MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null,
                                false, new String[] { MainetConstants.ERRORS }, null,
                                session.getMessage("account.journal.voucher.accountHead") + rowNo));
                        break;
                    }

                    int amount = 0;
                    if (details.getAmount() == null) {
                        amount++;
                    } else {
                        detailDto.setAmount(details.getAmount());
                    }
                    if (amount != 0) {
                        count++;
                        bindingResult.addError(new org.springframework.validation.FieldError(
                                MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null,
                                false, new String[] { MainetConstants.ERRORS }, null,
                                session.getMessage("account.journal.voucher.amount") + rowNo));
                        break;
                    }

                    int drCrExist = 0;
                    for (LookUp list : drCrTypes) {

                        if (details.getDrOrCr().equalsIgnoreCase(list.getLookUpCode())) {
                            detailDto.setDrOrCr(String.valueOf(list.getLookUpId()));
                            drCrExist++;
                        }
                    }

                    if (drCrExist == 0) {
                        count++;
                        bindingResult.addError(new org.springframework.validation.FieldError(
                                MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null,
                                false, new String[] { MainetConstants.ERRORS }, null,
                                rowNo + session.getMessage("account.journal.voucher.DR/CRExist")));
                        break;
                    }

                    int accountHeadExist = 0;
                    for (Map.Entry<Long, String> entry : secondaryHeadDescMap.entrySet()) {
                        String value = entry.getValue().replaceAll("(\\r|\\n)", "");
                        String value1 = value.replaceAll("\\s", "");
                        String accountHead = details.getAccHead().trim();
                        String acHeadCode = accountHead.replaceAll("\\s", "");
                        if (acHeadCode.trim().equalsIgnoreCase(value1.trim())) {
                            detailDto.setAccHead(entry.getKey().toString());
                            accountHeadExist++;
                        }
                    }
                    if (accountHeadExist == 0) {
                    	   for (Map.Entry<Long, String> entry : oldSecondaryHeadDescMap.entrySet()) {
                    		   if(entry!=null&&entry.getValue()!=null) {
                               String value = entry.getValue().replaceAll("(\\r|\\n)", "");
                               String value1 = value.replaceAll("\\s", "");
                               String accountHead = details.getAccHead().trim();
                               String acHeadCode = accountHead.replaceAll("\\s", "");
                               if (acHeadCode.trim().equalsIgnoreCase(value1.trim())) {
                                   detailDto.setAccHead(entry.getKey().toString());
                                   accountHeadExist++;
                               }
                           }	}
                    }
                    if (accountHeadExist == 0) {
                        count++;
                        bindingResult.addError(new org.springframework.validation.FieldError(
                                MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null,
                                false, new String[] { MainetConstants.ERRORS }, null,
                                rowNo + session.getMessage("account.journal.voucher.accountHeadExist")));
                        break;
                    }

                    if (details.getDrOrCr().equalsIgnoreCase(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_LIABILITY)) {
                        debitAmount = debitAmount.add(details.getAmount()) ;
                    }
                    if (details.getDrOrCr().equalsIgnoreCase(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS)) {
                        creditAmount = creditAmount.add(details.getAmount()) ;
                    }
                    accHeadList.add(details.getAccHead());
                    accHeadExist = accHeadExist(accHeadList);
                    voucherDetailList.add(detailDto);
                }

                if (!((debitAmount!=null&&creditAmount!=null)&&debitAmount.intValue()==creditAmount.intValue())) {
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null, false,
                            new String[] { MainetConstants.ERRORS }, null,
                            "Row no"+MainetConstants.WHITE_SPACE +rowNo +MainetConstants.WHITE_SPACE + session.getMessage("account.journal.voucher.sumOfDR/CR")));

                }

				/*
				 * if (accHeadExist > 1) { bindingResult.addError(new
				 * org.springframework.validation.FieldError(
				 * MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE,
				 * MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS },
				 * null, "Row no"+MainetConstants.WHITE_SPACE +rowNo
				 * +MainetConstants.WHITE_SPACE +
				 * session.getMessage("account.journal.voucher.dupAccountHead")));
				 * 
				 * }
				 */
                dto.setDetails(voucherDetailList);
                voucherEntryUploadDtoList.add(dto);
            }

            else {

                if (tranRefNo != null || !tranRefNo.isEmpty()) {
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("account.journal.voucher.voucherDetnotExist") + tranRefNo));
                    break;
                }
            }
        }
        return voucherEntryUploadDtoList;

    }

    public int accHeadExist(List<String> accHeadList) {
        int frequency = 0;
        Set<String> unique = new HashSet<String>(accHeadList);
        for (String set : unique) {
            frequency = Collections.frequency(accHeadList, set);
        }

        return frequency;
    }

    public void checkDuprecords(List<AccountVoucherMasterUploadDTO> voucherEntryUploadDtosUploadList,
            BindingResult bindingResult, List<AccountVoucherEntryEntity> voucherMasterDetails) {
        final ApplicationSession session = ApplicationSession.getInstance();
        for (AccountVoucherMasterUploadDTO masterDet : voucherEntryUploadDtosUploadList) {
if(masterDet.getTranRefNo()!=null && !masterDet.getTranRefNo().isEmpty()) {
            for (AccountVoucherEntryEntity masterEntity : voucherMasterDetails) {
                String vouDate = masterEntity.getVouDate().toString();
                String voutranRefNo = masterEntity.getVouReferenceNo();
                Long vouType = masterEntity.getVouTypeCpdId();
                String tranRefNo = masterDet.getTranRefNo();
                String tranDate = null;
                final DateFormat dateFormater = new SimpleDateFormat(MainetConstants.DATE_FORMAT_UPLOAD);
                Date dateFromParsing = null;
                String sampleDate = masterDet.getTranDate();
                try {
                    dateFromParsing = dateFormater.parse(sampleDate);
                } catch (ParseException e) {
                    //e.printStackTrace();
                	LOGGER.error("Error while parsing ", e);
                }
                DateFormat formatter;
                formatter = new SimpleDateFormat(MainetConstants.DATE_FORMATS);
                if (dateFromParsing != null) {
                    tranDate = formatter.format(dateFromParsing);
                }
                String voucherType = masterDet.getVoucherType();
                if (vouDate.equals(tranDate) && voutranRefNo.equals(tranRefNo) && vouType.equals(Long.valueOf(voucherType))) {
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null, false,
                            new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("account.journal.voucher.db.duplicate")));
                    break;
                }
            }}
        }
    }

}
