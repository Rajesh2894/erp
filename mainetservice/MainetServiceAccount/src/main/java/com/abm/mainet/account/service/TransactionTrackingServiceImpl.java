
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.TransactionTrackingDao;
import com.abm.mainet.account.dto.TransactionTrackingDto;
import com.abm.mainet.account.repository.TransactionTrackingRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author tejas.kotekar
 *
 */
@Service
public class TransactionTrackingServiceImpl implements TransactionTrackingService {

	@Resource
	private TransactionTrackingRepository trackingRepository;
	@Resource
	private TransactionTrackingDao transactionTrackingDao;
	@Resource
	private TbFinancialyearJpaRepository financialYearReoistory;
	@Resource
	private BudgetCodeService budgetCodeService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.account.service.TransactionTrackingService#
	 * getTransactedAccountHeads(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getTransactedAccountHeads(final Long orgId) {

		return trackingRepository.getTransactedAccountHeads(orgId);
	}

	@Override
	@Transactional(readOnly = true)
	public TransactionTrackingDto getTransactionDetails(final Long budgetCodeId, final Long orgId) {

		TransactionTrackingDto transactionDto = null;
		final List<Object[]> result = transactionTrackingDao.getTransactionDetails(budgetCodeId, orgId);
		if ((result != null) && !result.isEmpty()) {
			for (final Object[] resultObj : result) {
				transactionDto = new TransactionTrackingDto();
				transactionDto.setBudgetCodeId(budgetCodeId);
				transactionDto.setAccountCode((String) resultObj[0]);
				transactionDto.setAccountHead((String) resultObj[1]);
				String drCr = null;
				if (null != resultObj[2]) {
					drCr = CommonMasterUtility.findLookUpDesc(PrefixConstants.DCR,
							UserSession.getCurrent().getOrganisation().getOrgid(), (long) resultObj[4]);
					transactionDto
							.setOpeningBalance(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) resultObj[2])
									+ MainetConstants.FUND_MASTER.OPEN_BRACKET + drCr
									+ MainetConstants.FUND_MASTER.CLOSE_BRACKET);
				} else {
					transactionDto.setOpeningBalance(CommonMasterUtility.getAmountInIndianCurrency(BigDecimal.ZERO));
				}
				if (null != resultObj[3]) {
					transactionDto
							.setDebitAmount(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) resultObj[3]));
				} else {
					transactionDto.setDebitAmount(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal.ZERO)));
				}
				if (null != resultObj[5]) {
					transactionDto
							.setCreditAmount(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) resultObj[5]));
				} else {
					transactionDto.setCreditAmount(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal.ZERO)));
				}
				if (null != resultObj[7]) {
					transactionDto.setClosingBalance(
							CommonMasterUtility.getAmountInIndianCurrency((BigDecimal) resultObj[7]));
				} else {
					transactionDto.setClosingBalance(CommonMasterUtility.getAmountInIndianCurrency((BigDecimal.ZERO)));
				}
			}
		}
		return transactionDto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.account.service.TransactionTrackingService#getMonthWiseDetails
	 * (java.lang.Long, java.lang.Long, java.lang.Long,
	 * com.abm.mainet.common.domain.Organisation)
	 */
	@Override
	@Transactional(readOnly = true)
	public TransactionTrackingDto getMonthWiseDetails(final Long budgetCodeId, final Long orgId, final Long finYearId,
			final Organisation organisation) throws ParseException {

		final List<Object[]> finYear = financialYearReoistory.getFinanceYearFrmDate(finYearId);
		Date fromDate = null;
		Date toDate = null;
		if ((finYear != null) && !finYear.isEmpty()) {
			for (final Object[] finyearObj : finYear) {
				fromDate = (Date) finyearObj[0];
				toDate = (Date) finyearObj[1];
			}
		}
		transactionTrackingDao.getMonthWiseTransactionDetails(budgetCodeId, orgId, fromDate, toDate, finYearId,
				organisation);
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public TransactionTrackingDto getTransactionTrackingTrialBalance(Long orgId, Date fromDate, Date toDate,
			Long faYearid) {

		List<Object[]> transactionList = trackingRepository.getTransactionTrackingTrialBalance(orgId, fromDate, toDate,
				faYearid);

		BigDecimal sumTransactionDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumTransactionCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumOpeningCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumOpeningDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumClosingCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumClosingDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		final TransactionTrackingDto bean = new TransactionTrackingDto();
		final List<TransactionTrackingDto> list = new ArrayList<>();
		for (final Object[] transactionLists : transactionList) {
			final TransactionTrackingDto dto = new TransactionTrackingDto();
			if (transactionLists[1] != null) {
				dto.setAccountHead(transactionLists[1].toString());
				dto.setAccountCode(budgetCodeService
						.findAccountHeadCodeBySacHeadId(Long.valueOf(transactionLists[1].toString()), orgId));
			}
			Long cpdIdCrDr = null;

			final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
					PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, orgId);
			final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
					PrefixConstants.AccountJournalVoucherEntry.CR, PrefixConstants.DCR, orgId);
			if (transactionLists[2] != null) {
				cpdIdCrDr = (Long.valueOf(transactionLists[2].toString()));

				if (cpdIdCrDr != null) {
					if (cpdIdCrDr.equals(drId)) {
						dto.setOpeningDrAmount(((BigDecimal) transactionLists[0]).setScale(2, RoundingMode.HALF_EVEN));
						sumOpeningDR = sumOpeningDR.add(dto.getOpeningDrAmount().setScale(2, RoundingMode.HALF_EVEN));

					} else if (cpdIdCrDr.equals(crId)) {
						dto.setOpeningCrAmount(((BigDecimal) transactionLists[0]).setScale(2, RoundingMode.HALF_EVEN));
						sumOpeningCR = sumOpeningCR.add(dto.getOpeningCrAmount().setScale(2, RoundingMode.HALF_EVEN));

					}
				}
			}
			if (transactionLists[3] != null && !(transactionLists[3]
					.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
				dto.setTransactionCrAmount(((BigDecimal) transactionLists[3]).setScale(2, RoundingMode.HALF_EVEN));
				sumTransactionCR = sumTransactionCR.add((BigDecimal) transactionLists[3]);

			} else {
				dto.setTransactionCrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
			}
			if (transactionLists[4] != null && !(transactionLists[4]
					.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
				dto.setTransactionDrAmount(((BigDecimal) transactionLists[4]).setScale(2, RoundingMode.HALF_EVEN));
				sumTransactionDR = sumTransactionDR.add((BigDecimal) transactionLists[4]);

			} else {
				dto.setTransactionDrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
			}
			if (dto.getOpeningCrAmount() != null) {
				dto.setClosingCrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningCrAmount(),
						dto.getTransactionCrAmount(), dto.getTransactionDrAmount()));
				if (dto.getClosingCrAmount() != null && !dto.getClosingCrAmount()
						.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
					sumClosingCR = sumClosingCR.add(dto.getClosingCrAmount());
				}
			} else if (dto.getOpeningDrAmount() != null) {
				dto.setClosingDrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningDrAmount(),
						dto.getTransactionDrAmount(), dto.getTransactionCrAmount()));
				if (dto.getClosingDrAmount() != null && !dto.getClosingDrAmount()
						.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
					sumClosingDR = sumClosingDR.add(dto.getClosingDrAmount());
				}
			} else {

				if (dto.getTransactionCrAmount() != null && !dto.getTransactionCrAmount().equals(new BigDecimal("0.00"))
						&& dto.getTransactionDrAmount() != null
						&& !dto.getTransactionDrAmount().equals(new BigDecimal("0.00"))) {
					BigDecimal closingBlance = calculateClosingBalAsOnDateTrailBalance(dto.getOpeningCrAmount(),
							dto.getTransactionDrAmount(), dto.getTransactionCrAmount());
					if (closingBlance.signum() == -1) {
						dto.setClosingCrAmount(closingBlance.abs());
					} else {
						dto.setClosingDrAmount(closingBlance);
					}

				}

				else if (dto.getTransactionCrAmount() != null
						&& !dto.getTransactionCrAmount().equals(new BigDecimal("0.00"))) {
					dto.setClosingCrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningCrAmount(),
							dto.getTransactionCrAmount(), dto.getTransactionDrAmount()));
					sumClosingCR = sumClosingCR.add(dto.getClosingCrAmount());
				} else if (dto.getTransactionDrAmount() != null
						&& !dto.getTransactionDrAmount().equals(new BigDecimal("0.00"))) {
					dto.setClosingDrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningDrAmount(),
							dto.getTransactionDrAmount(), dto.getTransactionCrAmount()));
					sumClosingDR = sumClosingDR.add(dto.getClosingDrAmount());
				}
			}
			if (dto.getClosingCrAmount() == null) {
				dto.setClosingCrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
			}
			if (dto.getClosingDrAmount() == null) {
				dto.setClosingDrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
			}
			if (dto.getOpeningCrAmount() == null) {
				dto.setOpeningCrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
			}
			if (dto.getOpeningDrAmount() == null) {
				dto.setOpeningDrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
			}

			list.add(dto);
		}

		bean.setListOfSum(list);
		bean.setSumClosingCR(sumClosingCR);
		bean.setSumClosingDR(sumClosingDR);
		bean.setSumOpeningCR(sumOpeningCR);
		bean.setSumOpeningDR(sumOpeningDR);
		bean.setSumTransactionCR(sumTransactionCR);
		bean.setSumTransactionDR(sumTransactionDR);
		bean.setFaYearid(faYearid);
		return bean;

	}

	private BigDecimal calculateClosingBalAsOnDateTrailBalance(final BigDecimal openBalance, final BigDecimal drAmount,
			final BigDecimal crAmount) {
		BigDecimal closingBalance = BigDecimal.ZERO.setScale(2);
		if (openBalance != null) {
			closingBalance = closingBalance.add(openBalance);
		}
		if (drAmount != null
				&& !drAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
			closingBalance = closingBalance.add(drAmount);
		}
		if (crAmount != null
				&& !crAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
			closingBalance = closingBalance.subtract(crAmount);
		}

		/*
		 * if (closingBalance.signum() == -1 || closingBalance.equals(new
		 * BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
		 * closingBalance = null;
		 */// }

		return closingBalance;

	}

	@Override
	@Transactional(readOnly = true)
	public TransactionTrackingDto findHeadWiseBalance(Long orgId, String accountHead, Long faYearid,
			BigDecimal openingDr, BigDecimal openingCr) {
		BigDecimal sumTransactionDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumTransactionCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

		final TransactionTrackingDto bean = new TransactionTrackingDto();
		final List<Object[]> finYear = financialYearReoistory.getFinanceYearFrmDate(faYearid);
		Date fromDate = null;
		Date toDate = null;
		String fromDates;
		String toDates;
		if ((finYear != null) && !finYear.isEmpty()) {
			for (final Object[] finyearObj : finYear) {
				fromDate = (Date) finyearObj[0];
				toDate = (Date) finyearObj[1];
				break;
			}
		}

		int days, month, year;
		final List<TransactionTrackingDto> list = new ArrayList<>();
		for (int i = 0; i <= 11; i++) {

			Calendar cal = new GregorianCalendar();
			cal.setTime(fromDate);
			year = cal.get(Calendar.YEAR);
			days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			month = cal.get(Calendar.MONTH) + 1;

			fromDates = "1/" + (month) + "/" + year;
			toDates = days + "/" + (month) + "/" + year;

			list.add(getTransactionTrackingHeadWise(orgId, Utility.stringToDate(fromDates),
					Utility.stringToDate(toDates), faYearid, accountHead));

			fromDates = "1/" + (month + 1) + "/" + year;
			fromDate = Utility.stringToDate(fromDates);
			if (fromDate.after(new Date()))
				break;
			if (month == 12) {
				cal.setTime(toDate);
				year = cal.get(Calendar.YEAR);
				fromDates = "1/" + "1/" + year;
				fromDate = Utility.stringToDate(fromDates);
			}

		}

		if (list != null && !list.isEmpty()) {

			final List<TransactionTrackingDto> list1 = new ArrayList<>();
			for (TransactionTrackingDto lists : list) {

				final TransactionTrackingDto dto = new TransactionTrackingDto();

				if (lists.getTransactionCrAmount() != null) {
					dto.setTransactionCrAmount(lists.getTransactionCrAmount());
					sumTransactionCR = sumTransactionCR.add(dto.getTransactionCrAmount());
				}
				if (lists.getTransactionDrAmount() != null) {
					dto.setTransactionDrAmount(lists.getTransactionDrAmount());
					sumTransactionDR = sumTransactionDR.add(dto.getTransactionDrAmount());
				}

				if (dto.getTransactionCrAmount() == null) {
					dto.setTransactionCrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
				}
				if (dto.getTransactionDrAmount() == null) {
					dto.setTransactionDrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
				}
				if (openingDr != null) {
					dto.setOpeningDrAmount(openingDr.setScale(2, RoundingMode.HALF_EVEN));

				}
				if (openingCr != null) {
					dto.setOpeningCrAmount(openingCr.setScale(2, RoundingMode.HALF_EVEN));

				}
				if (dto.getOpeningCrAmount() != null && !dto.getOpeningCrAmount().equals(new BigDecimal("0.00"))) {
					dto.setClosingCrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningCrAmount(),
							dto.getTransactionCrAmount(), dto.getTransactionDrAmount()).abs());

				} else if (dto.getOpeningDrAmount() != null
						&& !dto.getOpeningDrAmount().equals(new BigDecimal("0.00"))) {
					dto.setClosingDrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningDrAmount(),
							dto.getTransactionDrAmount(), dto.getTransactionCrAmount()));

				} else {
					if (dto.getTransactionCrAmount() != null
							&& !dto.getTransactionCrAmount().equals(new BigDecimal("0.00"))
							&& dto.getTransactionDrAmount() != null
							&& !dto.getTransactionDrAmount().equals(new BigDecimal("0.00"))) {
						BigDecimal closingBlance = calculateClosingBalAsOnDateTrailBalance(dto.getOpeningCrAmount(),
								dto.getTransactionDrAmount(), dto.getTransactionCrAmount());
						if (closingBlance.signum() == -1) {
							dto.setClosingCrAmount(closingBlance.abs());
						} else {
							dto.setClosingDrAmount(closingBlance);
						}

					}

					else if (dto.getTransactionCrAmount() != null
							&& !dto.getTransactionCrAmount().equals(new BigDecimal("0.00"))) {
						dto.setClosingCrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningCrAmount(),
								dto.getTransactionCrAmount(), dto.getTransactionDrAmount()).abs());
					} else if (dto.getTransactionDrAmount() != null
							&& !dto.getTransactionDrAmount().equals(new BigDecimal("0.00"))) {
						dto.setClosingDrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningDrAmount(),
								dto.getTransactionDrAmount(), dto.getTransactionCrAmount()));
					}
				}
				if (dto.getClosingDrAmount() != null) {
					openingDr = dto.getClosingDrAmount();
				}
				if (dto.getClosingCrAmount() != null) {
					openingCr = dto.getClosingCrAmount();
				}
				if (dto.getClosingCrAmount() == null) {
					dto.setClosingCrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
				}
				if (dto.getClosingDrAmount() == null) {
					dto.setClosingDrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
				}
				if (dto.getOpeningCrAmount() == null) {
					dto.setOpeningCrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
				}
				if (dto.getOpeningDrAmount() == null) {
					dto.setOpeningDrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
				}

				dto.setFromDate(lists.getFromDate());
				dto.setToDate(lists.getToDate());
				dto.setMonth(lists.getMonth());

				list1.add(dto);

			}
			bean.setListOfSum(list1);
			bean.setSumTransactionCR(sumTransactionCR);
			bean.setSumTransactionDR(sumTransactionDR);
			bean.setAccountHead(accountHead);
			bean.setFaYearid(faYearid);

			bean.setAccountCode(
					budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(accountHead.toString()), orgId));
		}

		return bean;
	}

	@Override
	@Transactional(readOnly = true)
	public TransactionTrackingDto getTransactionTrackingHeadWise(Long orgId, Date fromDate, Date toDate, Long faYearid,
			String sacHeadId) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(fromDate);
		int month = cal.get(Calendar.MONTH);
		String months = theMonth(month);
		List<Object[]> transactionList = trackingRepository.getTransactionTrackingHeadWise(orgId, fromDate, toDate,
				faYearid, sacHeadId);

		final TransactionTrackingDto dto = new TransactionTrackingDto();
		// final List<TransactionTrackingDto> list = new ArrayList<>();
		for (final Object[] transactionLists : transactionList) {
			// final TransactionTrackingDto dto = new TransactionTrackingDto();
			/*
			 * if (transactionLists[1] != null) {
			 * dto.setAccountHead(transactionLists[1].toString()); dto.setAccountCode(
			 * budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(
			 * transactionLists[1].toString()), orgId)); }
			 */
			Long cpdIdCrDr = null;

			final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
					PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, orgId);
			final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
					PrefixConstants.AccountJournalVoucherEntry.CR, PrefixConstants.DCR, orgId);
			if (transactionLists[2] != null) {
				cpdIdCrDr = (Long.valueOf(transactionLists[2].toString()));

				if (cpdIdCrDr != null) {
					if (cpdIdCrDr.equals(drId)) {
						dto.setOpeningDrAmount(((BigDecimal) transactionLists[0]).setScale(2, RoundingMode.HALF_EVEN));

					} else if (cpdIdCrDr.equals(crId)) {
						dto.setOpeningCrAmount(((BigDecimal) transactionLists[0]).setScale(2, RoundingMode.HALF_EVEN));

					}
				}
			}
			if (transactionLists[3] != null && !(transactionLists[3]
					.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
				dto.setTransactionCrAmount(((BigDecimal) transactionLists[3]).setScale(2, RoundingMode.HALF_EVEN));

			}
			if (transactionLists[4] != null && !(transactionLists[4]
					.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))) {
				dto.setTransactionDrAmount(((BigDecimal) transactionLists[4]).setScale(2, RoundingMode.HALF_EVEN));

			}
			if (dto.getOpeningCrAmount() != null) {
				dto.setClosingCrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningCrAmount(),
						dto.getTransactionCrAmount(), dto.getTransactionDrAmount()));

			} else if (dto.getOpeningDrAmount() != null) {
				dto.setClosingDrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningDrAmount(),
						dto.getTransactionDrAmount(), dto.getTransactionCrAmount()));

			} else {
				dto.setClosingCrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningCrAmount(),
						dto.getTransactionCrAmount(), dto.getTransactionDrAmount()));

				dto.setClosingDrAmount(calculateClosingBalAsOnDateTrailBalance(dto.getOpeningDrAmount(),
						dto.getTransactionDrAmount(), dto.getTransactionCrAmount()));

			}

		}
		dto.setFromDate(Utility.dateToString(fromDate));
		dto.setToDate(Utility.dateToString(toDate));
		dto.setMonth(months);
		return dto;

	}

	public static String theMonth(int month) {
		String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		return monthNames[month];
	}

	@Override
	public TransactionTrackingDto findDayWiseBalance(Long orgId, String accountHead, Long faYearid, String fromDate,
			String toDate) {
		BigDecimal sumTransactionDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumTransactionCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

		final TransactionTrackingDto bean = new TransactionTrackingDto();
		final List<TransactionTrackingDto> list = new ArrayList<>();
		List<Object> postingDate = findvoucherPostingDatesInMonth(orgId, fromDate, toDate, accountHead);
		if (postingDate != null && !postingDate.isEmpty()) {

			for (final Object postingDates : postingDate) {

				list.add(getTransactionTrackingHeadWise(orgId, (Date) postingDates, (Date) postingDates, faYearid,
						accountHead));

			}

		}

		if (list != null && !list.isEmpty()) {

			final List<TransactionTrackingDto> list1 = new ArrayList<>();
			for (TransactionTrackingDto lists : list) {

				final TransactionTrackingDto dto = new TransactionTrackingDto();

				if (lists.getTransactionCrAmount() != null) {
					dto.setTransactionCrAmount(lists.getTransactionCrAmount());
					sumTransactionCR = sumTransactionCR.add(lists.getTransactionCrAmount());
				}
				if (lists.getTransactionDrAmount() != null) {
					dto.setTransactionDrAmount(lists.getTransactionDrAmount());
					sumTransactionDR = sumTransactionDR.add(lists.getTransactionDrAmount());
				}

				if (dto.getTransactionCrAmount() == null)
					dto.setTransactionCrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));

				if (dto.getTransactionDrAmount() == null)
					dto.setTransactionDrAmount(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_EVEN));
				dto.setFromDate(lists.getFromDate());
				dto.setToDate(lists.getToDate());
				dto.setMonth(lists.getMonth());

				list1.add(dto);

			}

			bean.setListOfSum(list1);
			bean.setAccountCode(
					budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(accountHead.toString()), orgId));
			bean.setSumTransactionCR(sumTransactionCR);
			bean.setSumTransactionDR(sumTransactionDR);
			bean.setAccountHead(accountHead);
			bean.setFaYearid(faYearid);
			bean.setFromDate(list1.get(0).getFromDate());
			bean.setToDate(toDate);
		} else {
			bean.setSuccessfulFlag(MainetConstants.Y_FLAG);
		}

		return bean;
	}

	private List<Object> findvoucherPostingDatesInMonth(Long orgId, String fromDate, String toDate,
			String accountHead) {
		if (orgId != null && fromDate != null && toDate != null) {
			Date fromDates = Utility.stringToDate(fromDate);
			Date toDates = Utility.stringToDate(toDate);
			Long sacHeadId = Long.valueOf(accountHead);
			return trackingRepository.findvoucherPostingDatesInMonth(orgId, fromDates, toDates, sacHeadId);
		}
		return null;
	}

	@Override
	public TransactionTrackingDto findVoucherWiseTransactions(Long orgId, String accountHead, String fromDate) {
		BigDecimal sumTransactionDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		BigDecimal sumTransactionCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
		TransactionTrackingDto transactionTrackingDto = new TransactionTrackingDto();
		List<TransactionTrackingDto> voucherList = new ArrayList<>();
		List<Object[]> voucherDetail = findVoucherWiseTransactionsRepo(orgId, accountHead, fromDate);
		if (voucherDetail != null && !voucherDetail.isEmpty()) {
			for (Object[] voucherDetails : voucherDetail) {
				TransactionTrackingDto transactionTrackingDto1 = new TransactionTrackingDto();

				if (voucherDetails[0] != null) {
					transactionTrackingDto1.setVoucherNumber((String) voucherDetails[0]);

				}
				if (voucherDetails[1] != null) {
					transactionTrackingDto1.setFromDate(Utility.dateToString((Date) voucherDetails[1]));

				}
				if (voucherDetails[2] != null) {
					transactionTrackingDto1.setNarration((String) voucherDetails[2]);

				}
				if (voucherDetails[3] != null) {
					transactionTrackingDto1.setTransactionCrAmount(
							new BigDecimal(voucherDetails[3].toString()).setScale(2, RoundingMode.HALF_EVEN));
					sumTransactionCR = sumTransactionCR.add(transactionTrackingDto1.getTransactionCrAmount());
				}
				if (voucherDetails[4] != null) {
					transactionTrackingDto1.setTransactionDrAmount(
							new BigDecimal(voucherDetails[4].toString()).setScale(2, RoundingMode.HALF_EVEN));
					sumTransactionDR = sumTransactionDR.add(transactionTrackingDto1.getTransactionDrAmount());

				}
				if (voucherDetails[5] != null) {
					transactionTrackingDto1.setVoucherId((Long) voucherDetails[5]);
				}
				voucherList.add(transactionTrackingDto1);

			}
			transactionTrackingDto.setSumTransactionCR(sumTransactionCR);
			transactionTrackingDto.setSumTransactionDR(sumTransactionDR);
			transactionTrackingDto.setListOfSum(voucherList);
			transactionTrackingDto.setAccountCode(
					budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(accountHead.toString()), orgId));

		}

		return transactionTrackingDto;
	}

	private List<Object[]> findVoucherWiseTransactionsRepo(Long orgId, String accountHead, String fromDate) {

		if (orgId != null && accountHead != null && fromDate != null) {
			Date fromDates = Utility.stringToDate(fromDate);
			Long sacHeadId = Long.valueOf(accountHead);
			return trackingRepository.findVoucherWiseTransactionsRepo(orgId, sacHeadId, fromDates);
		}

		return null;
	}

}
