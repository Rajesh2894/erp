package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.repository.DeductionRegisterRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.PaymentDetailsDto;
import com.abm.mainet.common.repository.TbAcVendormasterJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Utility;

@Service
public class DeductionRegisterServiceImpl implements DeductionRegisterService {
    @Resource
    private TbAcVendormasterJpaRepository tbAcVendormasterJpaRepository;
    @Resource
    private DeductionRegisterRepository deductionRegisterRepository;

    private static final Logger LOGGER = Logger.getLogger(DeductionRegisterServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public PaymentEntryDto getDeductionRegisterData(String frmDate, String toDate, Long orgId, Long tdsTypeId) {
        // TODO Auto-generated method stub

        PaymentEntryDto deductionRegisterList = new PaymentEntryDto();
        List<PaymentEntryDto> dedductionRegisterList = new ArrayList<>();
        BigDecimal totalBillAmt = new BigDecimal(0.00);
        BigDecimal totalTdsAmt = new BigDecimal(0.00);
        BigDecimal totalTdsPaidAmt = new BigDecimal(0.00);
        BigDecimal totalBalanceAmt = new BigDecimal(0.00);

        Long sacHeadid = deductionRegisterRepository.getsacHeadIdbyTDSTypeId(orgId, tdsTypeId);
        if (frmDate != null && orgId != null && toDate != null && tdsTypeId != null) {
            List<Object[]> deductionRegister = tbAcVendormasterJpaRepository.queryForDeductionRegisterData(orgId,
                    Utility.stringToDate(frmDate), Utility.stringToDate(toDate), sacHeadid);
            if (!deductionRegister.isEmpty() && deductionRegister != null) {

                for (Object[] deductionRegisterData : deductionRegister) {
                    PaymentEntryDto PaymentEntryDto = new PaymentEntryDto();
                    if (deductionRegisterData[1] != null) {
                        PaymentEntryDto.setVendorName(deductionRegisterData[1].toString());
                    }

                    if (deductionRegisterData[2] != null) {
                        PaymentEntryDto.setBillAmountStr(deductionRegisterData[2].toString());
                        totalBillAmt = totalBillAmt.add(new BigDecimal(deductionRegisterData[2].toString()));
                    } else {
                        PaymentEntryDto.setBillAmountStr(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

                    }

                    if (deductionRegisterData[3] != null) {
                        PaymentEntryDto.setTdsAmt(deductionRegisterData[3].toString());
                        totalTdsAmt = totalTdsAmt.add(new BigDecimal(deductionRegisterData[3].toString()));
                    } else {
                        PaymentEntryDto.setTdsAmt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

                    }

                    if (deductionRegisterData[4] != null) {
                        PaymentEntryDto.setTdsPaidAmt(deductionRegisterData[4].toString());
                        totalTdsPaidAmt = totalTdsPaidAmt.add(new BigDecimal(deductionRegisterData[4].toString()));
                    } else {
                        PaymentEntryDto.setTdsPaidAmt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

                    }

                    if (deductionRegisterData[5] != null) {
                        PaymentEntryDto.setPaymentNo(deductionRegisterData[5].toString());
                    }

                    if (deductionRegisterData[6] != null) {
                        PaymentEntryDto.setPaymentEntryDate(Utility.dateToString((Date) deductionRegisterData[6]));
                    }

                    if (deductionRegisterData[3] != null && deductionRegisterData[4] != null) {
                        PaymentEntryDto.setBalanceAmt(CommonMasterUtility
                                .getAmountInIndianCurrency(new BigDecimal(deductionRegisterData[3].toString())
                                        .subtract(new BigDecimal(deductionRegisterData[4].toString()))));
                        //totalBalanceAmt = totalBalanceAmt.add(new BigDecimal(PaymentEntryDto.getBalanceAmt()));
                        totalBalanceAmt = totalBalanceAmt.add(new BigDecimal(deductionRegisterData[3].toString())
                                .subtract(new BigDecimal(deductionRegisterData[4].toString())));
                    } else if (deductionRegisterData[4] == null) {
                        PaymentEntryDto.setBalanceAmt(CommonMasterUtility
                                .getAmountInIndianCurrency(new BigDecimal(deductionRegisterData[3].toString())));
                        totalBalanceAmt = totalBalanceAmt.add(new BigDecimal(deductionRegisterData[3].toString()));
                    }
                    dedductionRegisterList.add(PaymentEntryDto);
                }
                deductionRegisterList
                        .setTotalBillAmountStr(CommonMasterUtility.getAmountInIndianCurrency(totalBillAmt));
                deductionRegisterList.setTotalTdsAmt(CommonMasterUtility.getAmountInIndianCurrency(totalTdsAmt));
                deductionRegisterList
                        .setTotalTdsPaidAmt(CommonMasterUtility.getAmountInIndianCurrency(totalTdsPaidAmt));
                deductionRegisterList.setTotalBalAmt(CommonMasterUtility.getAmountInIndianCurrency(totalBalanceAmt));
                deductionRegisterList.setListOfdedRegister(dedductionRegisterList);
            }
            return deductionRegisterList;
        } else {
            LOGGER.error("As From Date Or To Date or tdsTypeId or  OrgId is empty " + frmDate + "and To Date" + toDate
                    + "and tdsTypeId " + tdsTypeId + "and OrgId " + orgId);
        }
        return null;

    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetailsDto> getActiveDetails(long orgId, Date fromDate, Date toDate, Long tdsTypeId) {
        // TODO Auto-generated method stub

        Long sacHeadid = deductionRegisterRepository.getsacHeadIdbyTDSTypeId(orgId, tdsTypeId);

        final List<PaymentDetailsDto> vendorList = new ArrayList<>();

        if (sacHeadid != null) {
            final Iterable<Object[]> vendorEntities = tbAcVendormasterJpaRepository.getActiveVendorsDetails(orgId,
                    fromDate, toDate, sacHeadid);

            if (vendorEntities != null) {
                PaymentDetailsDto vendorDto = null;
                for (Object[] tbAcVendormasterEntity : vendorEntities) {
                     vendorDto = new PaymentDetailsDto();
                    if(tbAcVendormasterEntity.length>=1 &&  tbAcVendormasterEntity[1]!=null)
                      vendorDto.setVendorName(tbAcVendormasterEntity[1].toString());
                    if(tbAcVendormasterEntity.length>=3 &&  tbAcVendormasterEntity[1]!=null)
                       vendorDto.setDeductions(tbAcVendormasterEntity[3].toString());
                    vendorDto.setId(sacHeadid);
                    vendorDto.setBdhId(Long.valueOf(tbAcVendormasterEntity[4].toString()));
                    vendorDto.setBmId(Long.valueOf(tbAcVendormasterEntity[5].toString()));
                    vendorDto.setBillNumber(tbAcVendormasterEntity[6].toString());
                    vendorDto.setBillDate(Utility.dateToString((Date) tbAcVendormasterEntity[7]));
                    if(tbAcVendormasterEntity.length>=8 &&  tbAcVendormasterEntity[8]!=null)
                    	vendorDto.setBillTypeId(Long.valueOf(tbAcVendormasterEntity[8].toString()));
                    vendorList.add(vendorDto);
                }
            }
        }
        return vendorList;
    }

}
