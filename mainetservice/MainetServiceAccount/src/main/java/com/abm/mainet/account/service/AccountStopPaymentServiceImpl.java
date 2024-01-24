package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dto.StopPaymentDTO;
import com.abm.mainet.account.repository.AccountChequeIssueanceRepository;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Utility;

@Service
public class AccountStopPaymentServiceImpl implements AccountStopPaymentService {

	@Autowired
	private AccountChequeIssueanceRepository accountChequeIssueanceRepository;
	
	
	@Override
	@Transactional
	public List<StopPaymentDTO> findByAllGridstopPaymentData(Long bankAccount, Date fromDte, Date toDte,
			Long orgId, Long chequeStatus) {
		BigDecimal totalReceiptAmount = new BigDecimal(0.00);
        BigDecimal totalPaymentAmount = new BigDecimal(0.00);
        StopPaymentDTO dto = null;
        final List<StopPaymentDTO> list = new ArrayList<>();
        List<Object[]> entities=null;
        
        if(bankAccount!=null) 
        entities=accountChequeIssueanceRepository.getAllNotIssuedPaymentCheque(orgId, chequeStatus, bankAccount,fromDte,toDte);
        else
        entities=accountChequeIssueanceRepository.getAllNotIssuedPaymentChequewithoutBankId(orgId, chequeStatus, fromDte, toDte);
        

        if (!entities.isEmpty() && entities != null) {
            for (final Object[] objects : entities) {
                dto = new StopPaymentDTO();
                BigDecimal bd = new BigDecimal(0.00);
                // @id
                if(objects[0]!=null) {
                dto.setId(Long.valueOf(objects[0].toString()));
                }
                
                // @payment Date
                if(objects[1]!=null) {
                final Date date = (Date) objects[1];
                final String tranDate = Utility.dateToString(date);
                dto.setPaymentDate(tranDate);
                }
                // @payment No
                if(objects[2]!=null) {
                dto.setPaymentNo(objects[2].toString());
                }
                 
                // @Amount
                if (objects[3] != null) {
                    bd = new BigDecimal(objects[3].toString());
                    dto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
                } else {
                    dto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
                }
                
                // @InstrumentNo/ChequeNo
                if (objects[4] != null) {
                    dto.setChequeddno((String) objects[4].toString());
                }
                 
                // @InstrumentDate/ChequeDate
                if (objects[6] != null) {
                    final Date chequedddate = (Date) objects[6];
                    final String cheqDate = Utility.dateToString(chequedddate);
                    dto.setChequedddate(cheqDate);
                }

                // @ChequeClearanceDate
                if (objects[7] != null) {
                    dto.setDate(Utility.dateToString((Date) objects[7]));
                }

                // @transactionmode
               if (objects[8] != null) {
            	   if (objects[8].toString().equals("Q")) {
                        dto.setPaymentMode("C");
                        dto.setTransMode("Cheque");
                    } else if (objects[8].toString().equals("B")) {
                        dto.setPaymentMode("B");
                        dto.setTransMode("Bank");
                    } else if (objects[8].toString().equals("C")) {
                        dto.setPaymentMode("C");
                        dto.setTransMode("Cash");
                    } else if (objects[8].toString().equals("RT")) {
                        dto.setPaymentMode("RT");
                        dto.setTransMode("RTGS");
                    } else if (objects[8].toString().equals("D")) {
                        dto.setPaymentMode("D");
                        dto.setTransMode("Deposite");
                    }
                }
              
                //@chequebookdetId
                if (objects[10] != null) {
                    dto.setChequebookDetid(Long.valueOf(objects[10].toString()));
                }
                
                //@stop Payment Date
                if (objects[11] != null) {
                	 final Date stopPayDate = (Date) objects[11];
                     final String cheqDate = Utility.dateToString(stopPayDate);
                    dto.setStopPaymentDate(cheqDate);
                }
                
                //@stop payment reason
                if (objects[12] != null) {
                   dto.setStopPaymentReason(objects[12].toString());;
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
	@Transactional(rollbackFor=Exception.class)
	public StopPaymentDTO savestopPaymentFormData(StopPaymentDTO stopPaymentDTO) {
		final Long stopPaymentStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
        		AccountConstants.STOP_PAYMENT.getValue(), AccountPrefix.CLR.toString(),
        		stopPaymentDTO.getOrgid());
	  if(CollectionUtils.isNotEmpty(stopPaymentDTO.getStopPaymentDto())) {
		for(StopPaymentDTO dto:stopPaymentDTO.getStopPaymentDto()) {
			if(StringUtils.isNotEmpty(dto.getStopPaymentDate())) {
		   accountChequeIssueanceRepository.updateChequeDetailforStopPayment(stopPaymentStatus, dto.getChequebookDetid(), stopPaymentDTO.getOrgid(), Utility.stringToDate(dto.getStopPaymentDate()), dto.getStopPaymentReason());
		  }
		}
		}
		 return stopPaymentDTO;
		 
	}




	@Override
	@Transactional
	public List<StopPaymentDTO> findRecordsForStopPayment(Long bankAccount, Date fromDte, Date toDte, Long orgId) {
		StopPaymentDTO dto = null;
		BigDecimal totalReceiptAmount = new BigDecimal(0.00);
        BigDecimal totalPaymentAmount = new BigDecimal(0.00);
        final List<StopPaymentDTO> dtoList = new ArrayList<>();
		final Long issuedStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
        		AccountConstants.ISSUED.getValue(), AccountPrefix.CLR.toString(),
        		orgId);
		final Long readyForIssueStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
        		AccountConstants.READY_FOR_ISSUE.getValue(), AccountPrefix.CLR.toString(),
        		orgId);
		 List<Object[]> entityList = accountChequeIssueanceRepository.getIssuedAndReadyForIssueChequeForStopPayment(orgId, issuedStatus, readyForIssueStatus, bankAccount, fromDte, toDte);
		
		 if (!entityList.isEmpty() && entityList != null) {
	            for (final Object[] objects : entityList) {
	            	 dto = new StopPaymentDTO();
	                 BigDecimal bd = new BigDecimal(0.00);
	                 // @id
	                 if(objects[0]!=null) {
	                 dto.setId(Long.valueOf(objects[0].toString()));
	                 }
	                 
	                 // @payment Date
	                 if(objects[1]!=null) {
	                 final Date date = (Date) objects[1];
	                 final String tranDate = Utility.dateToString(date);
	                 dto.setPaymentDate(tranDate);
	                 }
	                 // @payment No
	                 if(objects[2]!=null) {
	                 dto.setPaymentNo(objects[2].toString());
	                 }
	                  
	                 // @Amount
	                 if (objects[3] != null) {
	                     bd = new BigDecimal(objects[3].toString());
	                     dto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
	                 } else {
	                     dto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
	                 }
	                 
	                 // @InstrumentNo/ChequeNo
	                 if (objects[4] != null) {
	                     dto.setChequeddno((String) objects[4].toString());
	                 }
	                  
	                 // @InstrumentDate/ChequeDate
	                 if (objects[6] != null) {
	                     final Date chequedddate = (Date) objects[6];
	                     final String cheqDate = Utility.dateToString(chequedddate);
	                     dto.setChequedddate(cheqDate);
	                 }

	                 // @ChequeClearanceDate
	                 if (objects[7] != null) {
	                     dto.setDate(Utility.dateToString((Date) objects[7]));
	                 }

	                 // @transactionmode
	                if (objects[8] != null) {
	             	   if (objects[8].toString().equals("Q")) {
	                         dto.setPaymentMode("C");
	                         dto.setTransMode("Cheque");
	                     } else if (objects[8].toString().equals("B")) {
	                         dto.setPaymentMode("B");
	                         dto.setTransMode("Bank");
	                     } else if (objects[8].toString().equals("C")) {
	                         dto.setPaymentMode("C");
	                         dto.setTransMode("Cash");
	                     } else if (objects[8].toString().equals("RT")) {
	                         dto.setPaymentMode("RT");
	                         dto.setTransMode("RTGS");
	                     } else if (objects[8].toString().equals("D")) {
	                         dto.setPaymentMode("D");
	                         dto.setTransMode("Deposite");
	                     }
	                 }
	               
	                 //@chequebookdetId
	                 if (objects[10] != null) {
	                     dto.setChequebookDetid(Long.valueOf(objects[10].toString()));
	                 }
	                 
	                 /*//@stop Payment Date
	                 if (objects[11] != null) {
	                 	 final Date stopPayDate = (Date) objects[11];
	                      final String cheqDate = Utility.dateToString(stopPayDate);
	                     dto.setStopPaymentDate(cheqDate);
	                 }
	                 
	                 //@stop payment reason
	                 if (objects[12] != null) {
	                    dto.setStopPaymentReason(objects[12].toString());;
	                }*/
	                 
	                 dtoList.add(dto);
	             }
	             if (totalReceiptAmount.compareTo(BigDecimal.ZERO) != 0) {
	                 dto.setTotalReceiptAmount(totalReceiptAmount);
	             }
	             if (totalPaymentAmount.compareTo(BigDecimal.ZERO) != 0) {
	                 dto.setTotalPaymentAmount(totalPaymentAmount);
	             }
	         }
	         return dtoList;
	}

}
