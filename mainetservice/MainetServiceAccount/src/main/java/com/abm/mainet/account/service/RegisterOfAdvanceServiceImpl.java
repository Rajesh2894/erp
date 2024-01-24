/**
 * 
 */
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AdvanceEntryDTO;
import com.abm.mainet.account.repository.RegisterOfAdvanceRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.utility.Utility;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author satish.rathore
 *
 */
@Service
public class RegisterOfAdvanceServiceImpl implements RegisterOfAdvanceService {

    private static final Logger LOGGER = Logger.getLogger(RegisterOfAdvanceServiceImpl.class);
    @Resource
    RegisterOfAdvanceRepository registerOfAdvanceRepository;
    @Resource
    private TbAcVendormasterService tbAcVendormasterService;

    @Override
    @Transactional(readOnly = true)
    public AdvanceEntryDTO findAdvanceLadgerReport(String asOnDate, Long orgId) {
        BigDecimal sumAdvAmt = BigDecimal.ZERO;
        BigDecimal sumblncAmt = BigDecimal.ZERO;
        AdvanceEntryDTO advanceEntryDTO = new AdvanceEntryDTO();
        if (StringUtils.isNotEmpty(asOnDate) && orgId != null) {
            Date asOnDates = Utility.stringToDate(asOnDate);
            List<AdvanceEntryDTO> advanceEntryEntityListfinal = new ArrayList<>();
            List<AdvanceEntryEntity> advanceEntryEntityList = registerOfAdvanceRepository.findAdvanceLadgerReport(asOnDates,
                    orgId);
            if (!advanceEntryEntityList.isEmpty() && advanceEntryEntityList != null) {

                for (AdvanceEntryEntity AdvanceEntryEntitys : advanceEntryEntityList) {
                    AdvanceEntryDTO AdvanceEntryDTO1 = new AdvanceEntryDTO();
                    List<TbServiceReceiptMasEntity> tbServiceReceiptMasEntity = registerOfAdvanceRepository
                            .findReceiptForAdvanceLedger(AdvanceEntryEntitys.getPrAdvEntryId(), orgId);
                    
                   if(CollectionUtils.isNotEmpty(tbServiceReceiptMasEntity)) {
                    for (TbServiceReceiptMasEntity receipt : tbServiceReceiptMasEntity) {
                    	 if (receipt != null)
                             if (receipt.getRmRcptno() != null && receipt.getRmDate() != null) {
                            	 TbServiceReceiptMasBean receiptDto = new TbServiceReceiptMasBean();
                            	 String balanceAmount = null;
                            	 if(CollectionUtils.isNotEmpty(AdvanceEntryDTO1.getReceiptList())) {
                            		  balanceAmount = AdvanceEntryDTO1.getReceiptList().get(AdvanceEntryDTO1.getReceiptList().size()-1).getBalanceAmount();
                            	 }else {
                            		 balanceAmount = AdvanceEntryEntitys.getAdvanceAmount().toString();
                            	 }
                            	 
                                 receiptDto.setRmRcptno(receipt.getRmRcptno());
                                 receiptDto.setTransactionDate( new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(receipt.getRmDate()));
                                 receiptDto.setRmAmount(receipt.getRmAmount().toString());
                                 receiptDto.setBalanceAmount((new BigDecimal(balanceAmount).subtract(receipt.getRmAmount())).toString());
                                 sumblncAmt = sumblncAmt.add(new BigDecimal(balanceAmount).subtract(receipt.getRmAmount()));
                                 AdvanceEntryDTO1.getReceiptList().add(receiptDto);
                             }
					}
                    }else {
                    	 TbServiceReceiptMasBean receiptDto = new TbServiceReceiptMasBean();
                    	 receiptDto.setBalanceAmount(AdvanceEntryEntitys.getAdvanceAmount().toString());
                         sumblncAmt = sumblncAmt.add(AdvanceEntryEntitys.getAdvanceAmount());
                         AdvanceEntryDTO1.getReceiptList().add(receiptDto);
                    }
                    	
                   
                    List<AccountBillEntryMasterEnitity> accountBillEntryMasterEnitityList = registerOfAdvanceRepository
                            .findbillNoAndDateForAdvanceLadger(AdvanceEntryEntitys.getPrAdvEntryId(), orgId);
                    if (accountBillEntryMasterEnitityList != null && !accountBillEntryMasterEnitityList.isEmpty()) {
                        for (AccountBillEntryMasterEnitity accountBillEntryMasterEnitityLists : accountBillEntryMasterEnitityList) {
                        	AccountBillEntryMasterBean billDto = new AccountBillEntryMasterBean();
                        	BigDecimal balanceAmnt = new BigDecimal(0);
                        	
							if (CollectionUtils.isNotEmpty(AdvanceEntryDTO1.getBillList())) {
								balanceAmnt = AdvanceEntryDTO1.getBillList()
										.get(AdvanceEntryDTO1.getBillList().size() - 1).getBalanceAmount();
							} else if (CollectionUtils.isNotEmpty(AdvanceEntryDTO1.getReceiptList())) {
								balanceAmnt = new BigDecimal(AdvanceEntryDTO1.getReceiptList()
										.get(AdvanceEntryDTO1.getReceiptList().size() - 1).getBalanceAmount());
							}else {
								balanceAmnt = new BigDecimal(AdvanceEntryDTO1.getAdvanceAmount());
							}
                        	billDto.setBillNo(accountBillEntryMasterEnitityLists.getBillNo());
                        	billDto.setBillDate(Utility.dateToString(accountBillEntryMasterEnitityLists.getBillEntryDate()));
                        	billDto.setInvoiceValue(accountBillEntryMasterEnitityLists.getInvoiceValue());
                        	billDto.setBalanceAmount(balanceAmnt.subtract(accountBillEntryMasterEnitityLists.getInvoiceValue()));
                            sumblncAmt = sumblncAmt.add(balanceAmnt.subtract(accountBillEntryMasterEnitityLists.getInvoiceValue()));
                        	AdvanceEntryDTO1.getBillList().add(billDto);
                            /*AdvanceEntryDTO1.setVoucherNo(Long.parseLong(accountBillEntryMasterEnitityLists.getBillNo()));
                            AdvanceEntryDTO1
                                    .setVoucherDate(Utility.dateToString(accountBillEntryMasterEnitityLists.getBillEntryDate()));*/

                        }
                    }/*else {
                    	AccountBillEntryMasterBean billDto = new AccountBillEntryMasterBean();
                    	billDto.setBalanceAmount(new BigDecimal(AdvanceEntryEntitys.getAdvanceAmount().toString()));
                    	AdvanceEntryDTO1.getBillList().add(billDto);
                    }*/

                    BeanUtils.copyProperties(AdvanceEntryEntitys, AdvanceEntryDTO1);

                    if (AdvanceEntryEntitys.getAdvanceAmount() != null) {
                        AdvanceEntryDTO1.setAdvanceAmount(AdvanceEntryEntitys.getAdvanceAmount().toString());
                        sumAdvAmt = sumAdvAmt.add(AdvanceEntryEntitys.getAdvanceAmount());
                    }
                    /*if (AdvanceEntryEntitys.getBalanceAmount() != null) {
                        AdvanceEntryDTO1.setBalanceAmount(AdvanceEntryEntitys.getBalanceAmount().toString());
                        sumblncAmt = sumblncAmt.add(AdvanceEntryEntitys.getBalanceAmount());
                    }*/
                    if (AdvanceEntryEntitys.getPrAdvEntryDate() != null) {
                        AdvanceEntryDTO1.setAdvanceDate(Utility.dateToString(AdvanceEntryEntitys.getPrAdvEntryDate()));
                    }
                    if (AdvanceEntryEntitys.getPaymentDate() != null) {
                        AdvanceEntryDTO1.setAdvPaymentDate(Utility.dateToString(AdvanceEntryEntitys.getPaymentDate()));
                    }
                    if (AdvanceEntryEntitys.getPaymentOrderDate() != null) {
                        AdvanceEntryDTO1.setAdvPaymentOrderDate(Utility.dateToString(AdvanceEntryEntitys.getPaymentOrderDate()));

                    }
                    if (AdvanceEntryEntitys.getPaymentNumber() != null) {
                        AdvanceEntryDTO1.setPaymentNumber(AdvanceEntryEntitys.getPaymentNumber().toString());
                    }
                    if (AdvanceEntryEntitys.getVendorId() != null) {
                        final TbAcVendormaster vendorList = tbAcVendormasterService.findById(AdvanceEntryEntitys.getVendorId(),
                                orgId);
                        if ((vendorList != null)
                                && ((vendorList.getVmVendorname() != null) && !vendorList.getVmVendorname().isEmpty())) {
                            AdvanceEntryDTO1.setVendorName(vendorList.getVmVendorname());
                        }
                    }
                    advanceEntryEntityListfinal.add(AdvanceEntryDTO1);
                }
                advanceEntryDTO.setAdvanceLedgerList(advanceEntryEntityListfinal);
            }
            advanceEntryDTO.setSumAdvAmt(sumAdvAmt);
            advanceEntryDTO.setSumblncAmt(sumblncAmt);
            return advanceEntryDTO;
        } else {
            LOGGER.error("As On Date Or OrgId is empty asOnDate" + asOnDate + "and orgId" + orgId);
        }
        return advanceEntryDTO;

    }

}
