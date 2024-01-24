package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dto.AccountCollectionSummaryDTO;
import com.abm.mainet.account.repository.AccountCollectionSummaryReportJpaRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;

@Service
public class AccountCollectionSummaryReportServiceImpl implements AccountCollectionSummaryReportService {

    @Resource
    AccountCollectionSummaryReportJpaRepository accountCollectionSummaryReportJpaRepository;
    @Resource
    private RecieptRegisterService oRecieptRegisterService;

    private static final String CSR = "CSR";
    private static final String CDR = "CDR";
    private static final Logger LOGGER = Logger.getLogger(AccountCollectionSummaryReportServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public AccountCollectionSummaryDTO processReport(String reportTypeCode, String fromDate, String toDate, long orgId,
            Long superOrgId) {
        AccountCollectionSummaryDTO accountCollectionSummaryDTO = new AccountCollectionSummaryDTO();

        switch (reportTypeCode) {
        case CSR:
            return processCollectionSummaryReport(orgId, fromDate, toDate, reportTypeCode);
        case CDR:
            return processCollectionDetailReport(orgId, fromDate, toDate, reportTypeCode, superOrgId);
        default:
            break;

        }
        return accountCollectionSummaryDTO;

    }

    @Override
    @Transactional
    public AccountCollectionSummaryDTO processCollectionSummaryReport(long orgId, String fromDate, String toDate,
            String reportTypeCode) {
        List<Object[]> collectionSummaryReport = findCollectionSummaryReportByTodateAndFromDateAndOrgId(toDate, fromDate, orgId);
        AccountCollectionSummaryDTO finalaccountCollectionSummaryDTO = new AccountCollectionSummaryDTO();
        List<AccountCollectionSummaryDTO> summaryRecordList = new ArrayList<>();
        BigDecimal cashAmountTotal = new BigDecimal(0.00);
        BigDecimal chequeDDTotal = new BigDecimal(0.00);
        BigDecimal bankAmountTotal = new BigDecimal(0.00);
        BigDecimal grandTotal = new BigDecimal(0.00);
        if (collectionSummaryReport == null || collectionSummaryReport.isEmpty()) {
            finalaccountCollectionSummaryDTO.setFromDate(fromDate);
            finalaccountCollectionSummaryDTO.setToDate(toDate);
            LOGGER.error("No Records found for report from  input[fromDate=" + fromDate + " todate=" + toDate
                    + " orgid=" + orgId);
        } else {
            for (final Object obj[] : collectionSummaryReport) {
                final AccountCollectionSummaryDTO accountCollectionSummaryDTO = new AccountCollectionSummaryDTO();
                if (obj[0] != null) {
                    accountCollectionSummaryDTO.setNameOfDepartment((String) obj[0]);
                }
                if (obj[2] != null) {
                    accountCollectionSummaryDTO.setNameOfTheRevenueHead((String) obj[2]);
                }
                if (obj[3] != null) {
                    accountCollectionSummaryDTO
                            .setCashAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[3]));
                    cashAmountTotal = cashAmountTotal.add((BigDecimal) obj[3]);

                }
                if (obj[4] != null) {
                    accountCollectionSummaryDTO
                            .setChequeDDAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[4]));
                    chequeDDTotal = chequeDDTotal.add((BigDecimal) obj[4]);
                }
                if (obj[5] != null) {
                    accountCollectionSummaryDTO
                            .setBankAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[5]));
                    bankAmountTotal = bankAmountTotal.add((BigDecimal) obj[5]);

                }
                summaryRecordList.add(accountCollectionSummaryDTO);
            }

            finalaccountCollectionSummaryDTO.setCollectionSummaryRecordList(summaryRecordList);

            if (!cashAmountTotal.equals(new BigDecimal(0.00))) {
                finalaccountCollectionSummaryDTO
                        .setCashAmountTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(cashAmountTotal));
            }
            if (!chequeDDTotal.equals(new BigDecimal(0.00))) {
                finalaccountCollectionSummaryDTO
                        .setChequeDDTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(chequeDDTotal));
            }
            if (!bankAmountTotal.equals(new BigDecimal(0.00))) {
                finalaccountCollectionSummaryDTO
                        .setBankTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(bankAmountTotal));
            }
            grandTotal = bankAmountTotal.add(chequeDDTotal).add(cashAmountTotal);
            finalaccountCollectionSummaryDTO.setGrandTotal(CommonMasterUtility.getAmountInIndianCurrency(grandTotal));
            final String totalAmountInWords = Utility.convertBigNumberToWord(grandTotal);
            finalaccountCollectionSummaryDTO.setTotalAmountInWords(totalAmountInWords);
            finalaccountCollectionSummaryDTO.setFromDate(fromDate);
            finalaccountCollectionSummaryDTO.setToDate(toDate);

            // model.addAttribute(REPORT_DATA, finalaccountCollectionSummaryDTO);
        }
        return finalaccountCollectionSummaryDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findCollectionSummaryReportByTodateAndFromDateAndOrgId(String toDate, String fromDate, long orgId) {

        Date toDates = null;
        Date fromDates = null;
        if (StringUtils.isNotEmpty(toDate) && StringUtils.isNotEmpty(fromDate)) {
            toDates = Utility.stringToDate(toDate);
            fromDates = Utility.stringToDate(fromDate);
        }

        return accountCollectionSummaryReportJpaRepository.findCollectionSummaryReportByTodateAndFromDateAndOrgId(toDates,
                fromDates,
                orgId);

    }

    private AccountCollectionSummaryDTO processCollectionDetailReport(long orgId, String fromDate, String toDate,
            String reportTypeCode, Long superOrgId) {
        BigDecimal totalBankAmount = new BigDecimal(0.00);
        BigDecimal totalCashAmount = new BigDecimal(0.00);
        List<Object[]> collectionDetailList = findCollectionDetailReportbyByTodateAndFromDateAndOrgIdAndCreatedById(toDate,
                fromDate, orgId, superOrgId);
        LookUp lookUp=null;
        try {
         lookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.AccountConstants.DISHONORED.getValue(),
				PrefixConstants.LookUpPrefix.CLR, new Organisation(orgId));
        }catch(Exception e) {
        	LOGGER.error("Error while getting Prefix value", e);
        }
        AccountCollectionSummaryDTO finalaccountCollectionDetailDTO = new AccountCollectionSummaryDTO();
        List<AccountCollectionSummaryDTO> listOfCollectionDetail = new ArrayList<>();
        if (collectionDetailList == null || collectionDetailList.isEmpty()) {
            // model.addAttribute(VALIDATION_ERROR, AccountConstants.Y.getValue());
            LOGGER.error("No Records found for report from  input[fromDate=" + fromDate + " todate=" + toDate
                    + " orgid=" + orgId);
        } else {
            for (final Object obj[] : collectionDetailList) {
                final AccountCollectionSummaryDTO accountCollectionDetailDTO = new AccountCollectionSummaryDTO();

                if (obj[0] != null) {
                    // Long cpdIdMode =
                    // oRecieptRegisterService.findByRmRcptidTbSrcptModesDetEntity(Long.valueOf(obj[0].toString()));
                    String collectionMode = CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.name(), orgId,
                            Long.valueOf(obj[3].toString()));
                    accountCollectionDetailDTO.setReceiptMode(collectionMode);
                    List<Object[]> bankDetails = findBankDetailByReceiptHeadIdAndOrgId(Long.valueOf(obj[0].toString()), orgId);
                    if (bankDetails != null && !bankDetails.isEmpty()) {
                        for (Object[] bank : bankDetails) {
                            if (bank[1] != null) {
                                accountCollectionDetailDTO.setBankAccountNumber((String) bank[1]);
                            }
                            if (bank[2] != null) {
                                accountCollectionDetailDTO.setDateOfDeposit(Utility.dateToString((Date) bank[2]));
                            }
                            if (bank[3] != null) {
                                accountCollectionDetailDTO.setDateOfRealisation(Utility.dateToString((Date) bank[3]));
                            }

                            String flag = "";
                            Long chequeStatus=null;
                            if (bank[4] != null)
                            	chequeStatus = (Long) bank[4];

                            if (chequeStatus.equals(lookUp.getLookUpId())) //MainetConstants.Common_Constant.NUMBER.FOUR  #120771
                                flag = "Y";
                            else
                                flag = "N";
                            accountCollectionDetailDTO.setWhetherReturned(flag);

                            /*
                             * if (bank[5] != null) { accountCollectionDetailDTO.setRemarks((String) bank[5]); }
                             */
                        }
                    }

                }
                if (obj[1] != null) {
                    accountCollectionDetailDTO.setReceiptNumber(Long.valueOf(obj[1].toString()));
                }
                if (obj[2] != null) {
                    accountCollectionDetailDTO.setReceiptDate(Utility.dateToString((Date) obj[2]));

                }
                if (obj[6] != null) {
                    accountCollectionDetailDTO
                            .setCashAmountIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) obj[6]));
                    totalCashAmount = totalCashAmount.add((BigDecimal) obj[6]);
                }

                if (obj[5] != null) {
                    accountCollectionDetailDTO.setChequeNumber(Long.valueOf(obj[5].toString()));
                }
                if (obj[4] != null) {
                    accountCollectionDetailDTO.setNameOfDepositer((String) obj[4]);
                }
                if (obj[7] != null) {
                    accountCollectionDetailDTO.setRemarks((String) obj[7]);
                }

                listOfCollectionDetail.add(accountCollectionDetailDTO);

            }
            finalaccountCollectionDetailDTO.setFromDate(fromDate);
            finalaccountCollectionDetailDTO.setToDate(toDate);
            finalaccountCollectionDetailDTO
                    .setBankTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalBankAmount));
            finalaccountCollectionDetailDTO
                    .setCashAmountTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalCashAmount));
            finalaccountCollectionDetailDTO.setListOfCollectionDetail(listOfCollectionDetail);

            // model.addAttribute(REPORT_DATA, finalaccountCollectionDetailDTO);
        }
        return finalaccountCollectionDetailDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findCollectionDetailReportbyByTodateAndFromDateAndOrgIdAndCreatedById(String toDate, String fromDate,
            Long orgId, Long superOrgId) {

        Date toDates = null;
        Date fromDates = null;
        if (StringUtils.isNotEmpty(toDate) && StringUtils.isNotEmpty(fromDate)) {
            toDates = Utility.stringToDate(toDate);
            fromDates = Utility.stringToDate(fromDate);
        }

        return accountCollectionSummaryReportJpaRepository
                .findCollectionDetailReportbyByTodateAndFromDateAndOrgIdAndCreatedById(toDates, fromDates, orgId);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findBankDetailByReceiptHeadIdAndOrgId(Long receiptId, Long OrgId) {

        if (receiptId != null && OrgId != null) {
            return accountCollectionSummaryReportJpaRepository.findBankDetailByReceiptHeadIdAndOrgId(receiptId, OrgId);
        }
        return null;

    }
}
