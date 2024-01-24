package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.BankReconciliationDao;
import com.abm.mainet.account.dto.BankReconciliationDTO;
import com.abm.mainet.account.repository.AccountPaymentMasterJpaRepository;
import com.abm.mainet.account.repository.TbAcChequebookleafMasJpaRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;

@Service
public class BankReconciliationServiceImpl implements BankReconciliationService {

    @Resource
    private BankReconciliationDao bankReconciliationDao;
    @Resource
    private AccountPaymentMasterJpaRepository paymentEntryRepository;
    @Resource
    private TbAcChequebookleafMasJpaRepository tbAcChequebookleafMasJpaRepository;

    @Override
    @Transactional
    public List<BankReconciliationDTO> findByAllGridReceiptSearchData(String categoryId, final Long bankAccount,
            final String transactionMode, final Date fromDte, final Date toDte, final Long orgId,Long catagoryStatus) {
        final List<Object[]> entities = bankReconciliationDao.findByAllGridReceiptSearchData(bankAccount,
                transactionMode, fromDte, toDte, orgId,catagoryStatus);
        final List<BankReconciliationDTO> list = new ArrayList<>();
        for (final Object[] objects : entities) {
            final BankReconciliationDTO dto = new BankReconciliationDTO();
            if ((objects[0] != null) && (objects[1] != null) && (objects[2] != null) && (objects[3] != null)
                    && (objects[4] != null) && (objects[5] != null)) {
          
                  //  if (objects[7] == null) {
                        dto.setId((Long) objects[0]);
                        final Date date = (Date) objects[1];
                        final String tranDate = Utility.dateToString(date);
                        dto.setTransactionDate(tranDate);
                        dto.setTransactionNo(String.valueOf(objects[2]));
                        if (objects[3] != null) {
                            dto.setChequeddno((String) objects[3].toString());
                        }
                        final Date chequedddate = (Date) objects[4];
                        final String cheqDate = Utility.dateToString(chequedddate);
                        dto.setChequedddate(cheqDate);
                        final BigDecimal bd = new BigDecimal(objects[5].toString());
                        dto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
                        if (objects[8] != null) {
                            dto.setDate(Utility.dateToString((Date) objects[8]));
                        }
                        if (objects[8] != null) {
                            dto.setPreviousDate(Utility.dateToString((Date) objects[8]));
                        }
                        if (objects[9] != null) {
                            if (objects[9].toString().equals("Q")) {
                            	dto.setTransactionMode("C");
                                dto.setTransMode("Cheque");
                            } else if (objects[9].toString().equals("B")) {
                                dto.setTransactionMode("B");
                                dto.setTransMode("Bank");
                            } else if (objects[9].toString().equals("C")) {
                                dto.setTransactionMode("C");
                                dto.setTransMode("Cash");
                            } else if (objects[9].toString().equals("RT")) {
                                dto.setTransactionMode("RT");
                                dto.setTransMode("RTGS");
                            }else if (objects[9].toString().equals("D")) {
                                dto.setTransactionMode("D");
                                dto.setTransMode("Demand Draft");
                            } 
                        }
                        dto.setSerchType("R");
                        dto.setTransType("Receipt");
                        list.add(dto);
                  //  }
                
            }
        }
        return list;
    }

    @Override
    @Transactional
    public List<BankReconciliationDTO> findByAllGridPaymentEntrySearchData(Long categoryId, final Long bankAccount,
            final String transactionMode, final Date fromDte, final Date toDte, final Long orgId) {
        Long tranMode = null;
        final Organisation org = new Organisation();
        org.setOrgid(orgId);
        final List<LookUp> levelMap = CommonMasterUtility.getListLookup(AccountPrefix.PAY.toString(), org);
        for (final LookUp lookUp : levelMap) {
            if (lookUp.getLookUpCode().equals(transactionMode)) {
                tranMode = lookUp.getLookUpId();
            }
        }
         List<Object[]> entities1=null;
		if (tranMode == null) {
			 entities1 = paymentEntryRepository.findByAllGridPaymentEntrySearchData(bankAccount,
					fromDte, toDte, orgId, categoryId);
		} else {
			entities1 = bankReconciliationDao.findByAllGridPaymentEntrySearchData(bankAccount,
	                tranMode, fromDte, toDte, orgId,categoryId);
		}
        final List<BankReconciliationDTO> list = new ArrayList<>();
        final Long cancelledId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.CND,
                AccountPrefix.CLR.toString(), orgId);
        for (final Object[] objects : entities1) {
            final BankReconciliationDTO dto = new BankReconciliationDTO();
            if ((objects[0] != null) && (objects[1] != null) && (objects[2] != null) && (objects[5] != null) ) {
                if ((objects[6] == null)) {
                        dto.setId(new BigInteger(objects[0].toString()).longValue());
                        final Date date = (Date) objects[1];
                        final String tranDate = Utility.dateToString(date);
                        dto.setTransactionDate(tranDate);
                        dto.setTransactionNo(objects[2].toString());
                        if (objects[3] != null) {
                            dto.setChequeddno((String) objects[3]);
                        }
                        if(objects[4] != null) {
                        final Date chequedddate = (Date) objects[4];
                        final String cheqDate = Utility.dateToString(chequedddate);
                        dto.setChequedddate(cheqDate);
                        }
                        final BigDecimal bd = new BigDecimal(objects[5].toString());
                        dto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
                        if (objects[7]  != null) {
                            dto.setDate(Utility.dateToString((Date) objects[7]));
                        }
                        if (objects[7]  != null) {
                            dto.setPreviousDate(Utility.dateToString((Date) objects[7]));
                        }
                        if (objects[9] != null) {
                            if (objects[9].toString().equals("Q")) {
                                dto.setTransactionMode("C");
                                dto.setTransMode("Cheque");
                            } else if (objects[9].toString().equals("B")) {
                                dto.setTransactionMode("B");
                                dto.setTransMode("Bank");
                            } else if (objects[9].toString().equals("C")) {
                                dto.setTransactionMode("C");
                                dto.setTransMode("Cash");
                            } else if (objects[9].toString().equals("RT")) {
                                dto.setTransactionMode("RT");
                                dto.setTransMode("RTGS");
                            } else if (objects[9].toString().equals("D")) {
                                dto.setTransactionMode("D");
                                dto.setTransMode("Demand Draft");
                            } 
                        }

                        dto.setSerchType("P");
                        dto.setTransType("Payment");
                        list.add(dto);
                    
                }
            }
        }
        return list;
    }

    @Override
    @Transactional
    public BankReconciliationDTO saveBankReconciliationFormData(final BankReconciliationDTO tbBankReconciliation) {
        List<BankReconciliationDTO> list = tbBankReconciliation.getBankReconciliationDTO();
        for (BankReconciliationDTO bankReconciliationDTO : list) {
        	
            final Long receiptModePaymentId = bankReconciliationDTO.getId();
            boolean reverseReq=false;
        	if(bankReconciliationDTO.getPreviousDate()!=null&&StringUtils.isEmpty(bankReconciliationDTO.getDate())) {
        		reverseReq=true;
        	}
            if (StringUtils.isNotEmpty(bankReconciliationDTO.getDate())||reverseReq) {
            	//code in case of delete reconcilation date and reverse
            	
                if (!StringUtils.equalsIgnoreCase(bankReconciliationDTO.getDate(), bankReconciliationDTO.getPreviousDate())) {
                    final Date tranDate = Utility.stringToDate(bankReconciliationDTO.getDate());
                     String receiptTypeFlag =null;   //"3";  //#120771
                      Long lkpStatus=null;
                      Long cancelledStaFlag=null;
                    lkpStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                            AccountConstants.CLEARED.getValue(), AccountPrefix.CLR.toString(),
                            tbBankReconciliation.getOrgid());
                     cancelledStaFlag = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                            AccountConstants.CANCELLED.getValue(), AccountPrefix.CLR.toString(),
                            tbBankReconciliation.getOrgid());
                     if(reverseReq) {
                    	 lkpStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                                 "DNC", AccountPrefix.CLR.toString(),
                                 tbBankReconciliation.getOrgid());
                          cancelledStaFlag = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                        		  AccountConstants.ISSUED.getValue(), AccountPrefix.CLR.toString(),
                                 tbBankReconciliation.getOrgid()); 
                     }
                    Long statusFlag = null;
                    if (lkpStatus != null) {
                        statusFlag = lkpStatus;
                        receiptTypeFlag=String.valueOf(lkpStatus);
                    }
                    Long cancelledStatusFlag = null;
                    if (cancelledStaFlag != null) {
                        cancelledStatusFlag = cancelledStaFlag; //#120771
                    }
                    final Long orgId = tbBankReconciliation.getOrgid();
                    if (bankReconciliationDTO.getSerchType().equals(MainetConstants.MENU.R)) {
                        bankReconciliationDao.saveOrUpdateBankReconciliationFormReceiptData(receiptModePaymentId,
                                tranDate, receiptTypeFlag, orgId, tbBankReconciliation.getUserId(),
                                tbBankReconciliation.getLmoddate(), tbBankReconciliation.getLgIpMac());
                        if (bankReconciliationDTO.getTransactionMode().equals(MainetConstants.CommonConstants.C)
                                && bankReconciliationDTO.getDate() != null) {
                            bankReconciliationDTO.setOrgid(orgId);
                            bankReconciliationDTO.setLmoddate(tbBankReconciliation.getLmoddate());
                            bankReconciliationDTO.setLgIpMac(tbBankReconciliation.getLgIpMac());
                            bankReconciliationDTO.setUserId(tbBankReconciliation.getUserId());
                            bankReconciliationDTO.setDepositSlipId(bankReconciliationDTO.getDepositSlipId());
                            bankReconciliationDTO.setFlag("Y");
                            bankReconciliationDao.saveOrUpdateBankDepositslipMasterData(bankReconciliationDTO);
                        }
                    }
                    if (bankReconciliationDTO.getSerchType().equals(MainetConstants.MENU.P)) {
                        paymentEntryRepository.updateBankReconciliationFormPaymentData(receiptModePaymentId, tranDate,
                                orgId, tbBankReconciliation.getUserId(), tbBankReconciliation.getLmoddate(),
                                tbBankReconciliation.getLgIpMac());
                        tbAcChequebookleafMasJpaRepository.updateBankReconciliationFormPaymentCheckBookData(
                                receiptModePaymentId, statusFlag, orgId, cancelledStatusFlag, tbBankReconciliation.getUserId(),
                                tbBankReconciliation.getLmoddate(), tbBankReconciliation.getLgIpMac());
                    }
                }
            }
        }
        return tbBankReconciliation;
    }

    @Override
    @Transactional
    public List<BankReconciliationDTO> findByAllGridReceiptAndPaymentEntrySearchData(Long bankAccount, Date fromDte,
            Date toDte, Long orgId) {
        BigDecimal totalReceiptAmount = new BigDecimal(0.00);
        BigDecimal totalPaymentAmount = new BigDecimal(0.00);
        final List<Object[]> entities = paymentEntryRepository.getPaymentAndReceiptDetailsList(bankAccount, fromDte,
                toDte, orgId);
        BankReconciliationDTO dto = null;
        final List<BankReconciliationDTO> list = new ArrayList<>();

        if (!entities.isEmpty() && entities != null) {
            for (final Object[] objects : entities) {
                dto = new BankReconciliationDTO();
                BigDecimal bd = new BigDecimal(0.00);
                // @id
                dto.setId(Long.valueOf(objects[0].toString()));

                // @Transaction Date
                final Date date = (Date) objects[2];
                final String tranDate = Utility.dateToString(date);
                dto.setTransactionDate(tranDate);

                // @Transaction No
                dto.setTransactionNo(objects[3].toString());

                // @InstrumentNo/ChequeNo
                if (objects[5] != null) {
                    dto.setChequeddno((String) objects[5].toString());
                }

                // @InstrumentDate/ChequeDate
                if (objects[6] != null) {
                    final Date chequedddate = (Date) objects[6];
                    final String cheqDate = Utility.dateToString(chequedddate);
                    dto.setChequedddate(cheqDate);
                }

                // @Amount
                if (objects[7] != null) {
                    bd = new BigDecimal(objects[7].toString());
                    dto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
                } else {
                    dto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
                }

                // @ChequeClearanceDate
                if (objects[8] != null) {
                    dto.setDate(Utility.dateToString((Date) objects[8]));
                }

                if (objects[8] != null) {
                    dto.setPreviousDate(Utility.dateToString((Date) objects[8]));
                }
                // @TransactionType
                if (objects[9] != null) {
                    if (objects[9].toString().equals("Q")) {
                        dto.setTransactionMode("C");
                        dto.setTransMode("Cheque");
                    } else if (objects[9].toString().equals("B")) {
                        dto.setTransactionMode("B");
                        dto.setTransMode("Bank");
                    } else if (objects[9].toString().equals("C")) {
                        dto.setTransactionMode("C");
                        dto.setTransMode("Cash");
                    } else if (objects[9].toString().equals("RT")) {
                        dto.setTransactionMode("RT");
                        dto.setTransMode("RTGS");
                    } else if (objects[9].toString().equals("D")) {
                        dto.setTransactionMode("D");
                        dto.setTransMode("Demand Draft");
                    }
                   

                }
                // @SearchType
                if (objects[10] != null) {
                    if (objects[10].toString().equals("R")) {
                        dto.setSerchType("R");
                        dto.setTransType("Receipt");
                    } else if (objects[10].toString().equals("P")) {
                        dto.setSerchType("P");
                        dto.setTransType("Payment");
                    }
                }

                if (objects[11] != null) {
                    dto.setDepositSlipId(Long.valueOf(objects[11].toString()));
                }
                // @Sum of Receipt Amount And Payment Amount
                if (objects[8] == null && objects[10] != null) {
                    if (objects[10].toString().equals("R")) {
                        totalReceiptAmount = totalReceiptAmount.add(new BigDecimal(objects[7].toString()));
                    } else if (objects[10].toString().equals("P")) {
                        totalPaymentAmount = totalPaymentAmount.add(new BigDecimal(objects[7].toString()));
                    }
                }
                list.add(dto);
            }
            if (totalReceiptAmount.compareTo(BigDecimal.ZERO) != 0) {
                dto.setTotalReceiptAmount(totalReceiptAmount);
            }
            if (totalPaymentAmount.compareTo(BigDecimal.ZERO) != 0) {
                dto.setTotalPaymentAmount(totalPaymentAmount);
            }
        }
        return list;
    }

    @Override
    @Transactional
    public List<BankReconciliationDTO> getAllSummaryData(Long bankAccountId, Date fromDte, Date toDte, Long orgId) {
        List<Object[]> entities = null;
        if (fromDte != null && toDte != null && bankAccountId != null) {
            entities = paymentEntryRepository.findByAllSummarySearchData(bankAccountId, fromDte, toDte, orgId);
        } else {
            entities = paymentEntryRepository.getDefaultSummaryData(orgId);
        }
        final List<BankReconciliationDTO> list = new ArrayList<>();
        if (!entities.isEmpty() && entities != null) {
            for (final Object[] objects : entities) {
                final BankReconciliationDTO dto = new BankReconciliationDTO();
                // @Id
                if (objects[0] != null) {
                    dto.setId(Long.valueOf(objects[0].toString()));
                }
                // @TransactionDate
                if (objects[2] != null) {
                    final Date date = (Date) objects[2];
                    final String tranDate = Utility.dateToString(date);
                    dto.setTransactionDate(tranDate);
                }
                // @TransactionNo
                if (objects[3] != null) {
                    dto.setTransactionNo(objects[3].toString());
                }
                // @ChequeNo
                if (objects[5] != null) {
                    dto.setChequeddno(objects[5].toString());
                }
                // @ChequeDate
                if (objects[6] != null) {
                    final Date chequedddate = (Date) objects[6];
                    final String cheqDate = Utility.dateToString(chequedddate);
                    dto.setChequedddate(cheqDate);
                }
                // @Amount
                if (objects[7] != null) {
                    final BigDecimal bd = new BigDecimal(objects[7].toString());
                    dto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
                }
                // @ClearanceDate
                if (objects[8] != null) {
                    dto.setDate(Utility.dateToString((Date) objects[8]));
                }
                // @TransactionMode
                if (objects[9] != null) {
                    if (objects[9].toString().equals("Q")) {
                        dto.setTransactionMode("Cheque");
                    } else if (objects[9].toString().equals("B")) {
                        dto.setTransactionMode("Bank");
                    } else if (objects[9].toString().equals("C")) {
                        dto.setTransactionMode("Cash");
                    } else if (objects[9].toString().equals("RT")) {
                        dto.setTransactionMode("RTGS");
                    } else if (objects[9].toString().equals("D")) {
                        dto.setTransactionMode("Demand Draft");
                    }
					
                }
                // @SearchType
                if (objects[10] != null) {
                    if (objects[10].toString().equals("R")) {
                        dto.setSerchType("Receipt");
                    } else if (objects[10].toString().equals("P")) {
                        dto.setSerchType("Payment");
                    }
                }
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    @Transactional
    public List<BankReconciliationDTO> getAllStatusId(Long orgId) {
        // TODO Auto-generated method stub
        final List<Long> entities = paymentEntryRepository.getPaymentStatusId(orgId);
        final List<BankReconciliationDTO> list = new ArrayList<>();
        if (!entities.isEmpty() && entities != null) {
            for (final Long objects : entities) {
                final BankReconciliationDTO dto = new BankReconciliationDTO();
                dto.setStatusId(Long.valueOf(objects.toString()));
                list.add(dto);
            }
        }
        return list;
    }

}
