package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.abm.mainet.account.dto.ChequeIssueanceDTO;
import com.abm.mainet.account.repository.AccountChequeIssueanceRepository;
import com.abm.mainet.account.service.AccountChequeIssueanceService;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Utility;

/**
 * 
 * @author vishwanath.s
 *
 */

@Service
public class AccountChequeIssueanceServiceImpl implements AccountChequeIssueanceService {

	@Autowired
	private AccountChequeIssueanceRepository accountChequeIssueanceRepository;
	

	@Override
	public List<ChequeIssueanceDTO> findByAllGridChequeNotIssuanceData(Long bankAccount, Date fromDte, Date toDte,
			Long orgId, Long chequeStatus) {
		BigDecimal totalReceiptAmount = new BigDecimal(0.00);
		BigDecimal totalPaymentAmount = new BigDecimal(0.00);
		ChequeIssueanceDTO dto = null;
		final List<ChequeIssueanceDTO> list = new ArrayList<>();
		List<Object[]> entities = null;

		if (bankAccount != null)
			entities = accountChequeIssueanceRepository.getAllNotIssuedPaymentCheque(orgId, chequeStatus, bankAccount,
					fromDte, toDte);
		else
			entities = accountChequeIssueanceRepository.getAllNotIssuedPaymentChequewithoutBankId(orgId, chequeStatus,
					fromDte, toDte);

		if (!entities.isEmpty() && entities != null) {
			for (final Object[] objects : entities) {
				dto = new ChequeIssueanceDTO();
				BigDecimal bd = new BigDecimal(0.00);
				// @id
				dto.setId(Long.valueOf(objects[0].toString()));

				// @Payment Date
				final Date date = (Date) objects[1];
				final String tranDate = Utility.dateToString(date);
				dto.setTransactionDate(tranDate);

				// @Payment No
				dto.setTransactionNo(objects[2].toString());

				// @InstrumentNo/ChequeNo
				if (objects[4] != null) {
					dto.setChequeddno((String) objects[4].toString());
				}

				// @issuance Date
				if (objects[5] != null) {
					final Date date1 = (Date) objects[5];
					final String tranDate1 = Utility.dateToString(date1);
					dto.setIssueanceDate(tranDate1);
				}

				// @InstrumentDate/ChequeDate
				if (objects[6] != null) {
					final Date chequedddate = (Date) objects[6];
					final String cheqDate = Utility.dateToString(chequedddate);
					dto.setChequedddate(cheqDate);
				}

				// @Amount
				if (objects[3] != null) {
					bd = new BigDecimal(objects[3].toString());
					dto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
				} else {
					dto.setAmount(CommonMasterUtility.getAmountInIndianCurrency(bd));
				}

				// @ChequeClearanceDate
				if (objects[7] != null) {
					dto.setDate(Utility.dateToString((Date) objects[7]));
				}

				// @transactionmode
				if (objects[8] != null) {
					if (objects[8].toString().equals("Q")) {
						dto.setTransactionMode("C");
						dto.setTransMode("Cheque");
					} else if (objects[8].toString().equals("B")) {
						dto.setTransactionMode("B");
						dto.setTransMode("Bank");
					} else if (objects[8].toString().equals("C")) {
						dto.setTransactionMode("C");
						dto.setTransMode("Cash");
					} else if (objects[8].toString().equals("RT")) {
						dto.setTransactionMode("RT");
						dto.setTransMode("RTGS");
					} else if (objects[8].toString().equals("D")) {
						dto.setTransactionMode("D");
						dto.setTransMode("Deposite");
					}
				}

				// @SearchType
				if (objects[9] != null) {
					if (objects[9].toString().equals("R")) {
						dto.setSerchType("R");
						dto.setTransType("Receipt");
					} else if (objects[9].toString().equals("P")) {
						dto.setSerchType("P");
						dto.setTransType("Payment");
					}
				}

				// @chequeBookdetId
				if (objects[10] != null) {
					dto.setChequebookDetid(Long.valueOf(objects[10].toString()));
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
	@Transactional(rollbackFor = Exception.class)
	public ChequeIssueanceDTO saveChequeIssueanceFormData(ChequeIssueanceDTO chequeIssueanceDto) {
		List<ChequeIssueanceDTO> list = chequeIssueanceDto.getBankReconciliationDTO();
		for (ChequeIssueanceDTO chequeIssueDto : list) {
			final Long chequebookDetid = chequeIssueDto.getChequebookDetid();
			if (StringUtils.isNotEmpty(chequeIssueDto.getDate())) {
				if (!chequeIssueDto.getDate().equals(chequeIssueDto.getPreviousDate())) {
					final Date issueanceDate = Utility.stringToDate(chequeIssueDto.getDate());
					final Long lkpStatus = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
							AccountConstants.ISSUED.getValue(), AccountPrefix.CLR.toString(),
							chequeIssueanceDto.getOrgid());
					final Long orgId = chequeIssueanceDto.getOrgid();
					accountChequeIssueanceRepository.updateChequeDetail(lkpStatus, chequebookDetid, orgId,
							issueanceDate);
				}
			}
		}
		return chequeIssueanceDto;
	}
}
