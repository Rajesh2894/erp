package com.abm.mainet.rnl.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.TbChargeMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.dao.IDemandNoticeDAO;
import com.abm.mainet.rnl.dao.IReportDAO;
import com.abm.mainet.rnl.domain.RLBillMaster;
import com.abm.mainet.rnl.dto.EstatePropGrid;
import com.abm.mainet.rnl.dto.ReportDTO;
import com.abm.mainet.rnl.repository.EstateContractMappingRepository;
import com.abm.mainet.rnl.repository.ReportSummaryRepository;
import com.abm.mainet.rnl.ui.model.ReportModel;

@Service
public class ReportServiceImpl implements IReportService {

	private static final Logger LOGGER = Logger.getLogger(ReportServiceImpl.class);

	@Autowired
	ReportSummaryRepository reportSummaryRepository;

	@Autowired
	EstateContractMappingRepository estateContractMappingRepository;

	@Autowired
	IReportDAO reportDAO;

	@Autowired
	IDemandNoticeDAO iNoticeBillDAO;
	@Autowired
	TbChargeMasterService tbChargeMasterService;

	@Override
	public void fetchRevenueReport(ReportModel model, String occupancyType, String fromDate, String toDate,
			Long serviceId, Long orgId) {
		BigDecimal totalBankAmount = new BigDecimal(0.00);
		BigDecimal totalCashAmount = new BigDecimal(0.00);
		List<ReportDTO> collectionDetailList = findRevenueReportByDateAndServiceIdAndOrgId(toDate, fromDate, orgId,
				serviceId);

		ReportDTO finalReportDTO = new ReportDTO();
		List<ReportDTO> listOfCollectionDetail = new ArrayList<>();
		finalReportDTO.setFromDateString(fromDate);
		finalReportDTO.setToDateString(toDate);
		if (collectionDetailList == null || collectionDetailList.isEmpty()) {
			LOGGER.error("No Records found for report from  input[fromDate=" + fromDate + " todate=" + toDate
					+ " orgid=" + orgId);
		} else {
			for (ReportDTO data : collectionDetailList) {
				final ReportDTO reportDTO = new ReportDTO();
				String collectionMode = CommonMasterUtility.findLookUpDesc(AccountPrefix.PAY.name(), orgId,
						data.getCpdFeemode());
				Map<String, BigDecimal> map=tbChargeMasterService.getChargeDescByChgIdByAppld(data.getApmApplicationId(), orgId);
				if(map!=null) {
					reportDTO.setBookinFee(map.get("Booking Fees"));
					reportDTO.setSecurityDeposit(map.get("Security Deposit"));
				}
				reportDTO.setReceiptMode(collectionMode);
				reportDTO.setReceiptNumber(data.getRmRcptno());
				reportDTO.setReceiptDate(Utility.dateToString(data.getRmDate()));
				reportDTO.setCashAmountIndianCurrency(
						CommonMasterUtility.getAmountInIndianCurrency(data.getRfFeeamount()));
				totalCashAmount = totalCashAmount.add(data.getRfFeeamount());
				reportDTO.setChequeNumber(data.getRdChequeddno());
				reportDTO.setNameOfDepositer(data.getRmReceivedfrom());
				reportDTO.setRemarks(data.getRmNarration());
				listOfCollectionDetail.add(reportDTO);
			}
			finalReportDTO.setBankTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalBankAmount));

			finalReportDTO
					.setCashAmountTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalCashAmount));

			/* finalReportDTO.setListOfCollectionDetail(listOfCollectionDetail); */

			/*
			 * model.setReportDTO(finalReportDTO);
			 * model.setSummaryReportDetails(listOfCollectionDetail);
			 */
		}
		model.setReportDTO(finalReportDTO);
		model.setSummaryReportDetails(listOfCollectionDetail);
	}

	public List<ReportDTO> findRevenueReportByDateAndServiceIdAndOrgId(String toDate, String fromDate, Long orgId,
			Long serviceId) {
		Date toDates = null;
		Date fromDates = null;
		if (StringUtils.isNotEmpty(toDate) && StringUtils.isNotEmpty(fromDate)) {
			toDates = Utility.stringToDate(toDate);
			fromDates = Utility.stringToDate(fromDate);
		}

		return reportDAO.findRevenueReportByDateAndServiceIdAndOrgId(toDates, fromDates, orgId, serviceId);

		// return
		// reportSummaryRepository.findRevenueReportByDateAndServiceIdAndOrgId(toDates,
		// fromDates, orgId, serviceId);
	}

	@Override
	public void fetchOutstandingReport(ReportModel model, String date, Long orgId) {
		Date filterDate = null;
		BigDecimal totalCashAmount = new BigDecimal(0.00);
		if (StringUtils.isNotEmpty(date)) {
			filterDate = Utility.stringToDate(date);
		}
		List<ReportDTO> summaryList = new ArrayList<>();
		ReportDTO reportDTOData = new ReportDTO();
		List<ReportDTO> results = reportSummaryRepository.fetchOutstandingBillByDate(filterDate, orgId);
		for (ReportDTO obj : results) {
			ReportDTO reportDTO = new ReportDTO();
			long contId = obj.getContractId();
			double outstandingAmt = obj.getTotal();
			totalCashAmount = totalCashAmount.add(new BigDecimal(outstandingAmt));
			// get property name,LESSEE Name,contract no
			/*
			 * List<EstateContractMapping> estateContractMappingList =
			 * estateContractMappingRepository .findByContractMastEntityContId(contId);
			 * String propertyName = ""; for (EstateContractMapping contractMap :
			 * estateContractMappingList) { propertyName +=
			 * contractMap.getEstatePropertyEntity().getName(); }
			 */
			Object[] data = reportSummaryRepository.getContractPropertyData(contId, orgId);// property name[0], contract
																							// No[1]

			if (data != null && data.length > 0) {

				Object[] object = (Object[]) data[0];
				reportDTO.setPropertyName((String) object[0]);
				reportDTO.setContractNo((String) object[1]);
				BigDecimal bigDecimal = new BigDecimal(outstandingAmt);
				reportDTO.setOutstandingAmt(bigDecimal.setScale(2, RoundingMode.HALF_UP));
				List<String> vendorNames = reportSummaryRepository.getContractorNames(contId, orgId);
				String nameOfLessee = "";
				for (String vendor : vendorNames) {
					nameOfLessee += vendor + ",";
				}
				// remove comma from end side
				if (nameOfLessee != null) {
					if (nameOfLessee.endsWith(",")) {
						nameOfLessee = nameOfLessee.substring(0, nameOfLessee.length() - 1);
					}
				}
				reportDTO.setNameOfLessee(nameOfLessee);
				
				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
					String vendormobileNos = reportSummaryRepository.getContractorMobileNo(contId, orgId);
					if(vendormobileNos!=null && !vendormobileNos.isEmpty()) {
						reportDTO.setMobileNoOfLessee(vendormobileNos);
					}	
				}
			}
			summaryList.add(reportDTO);
		}
		reportDTOData.setCashAmountTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalCashAmount));
		reportDTOData.setFromDateString(date);
		model.setReportDTO(reportDTOData);
		model.setSummaryReportDetails(summaryList);
	}

	@Override
	public void fetchDemandRegisterReport(ReportModel model, String financialYear, Long orgId) {
		String temp[] = financialYear.split(MainetConstants.HYPHEN);
		Date[] financialDates = { Utility.stringToDate("31/03/" + temp[0]), Utility.stringToDate("01/04/" + temp[1]) };
		SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMATS);
		Date financeFromDate = financialDates[0], financeToDate = financialDates[1];
		try {
			financeFromDate = dateFormat.parse(dateFormat.format(financeFromDate));
			financeToDate = dateFormat.parse(dateFormat.format(financeToDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// doing this to get data for arrears
		TbDepartment tbDepartment = ApplicationContextProvider.getApplicationContext()
				.getBean(TbDepartmentService.class)
				.findDeptByCode(orgId, MainetConstants.RnLCommon.Flag_A, MainetConstants.RnLCommon.RentLease);
		// flag-> E DEPT wise filter
		final List<ContractMappingDTO> contractMapDet = ApplicationContextProvider.getApplicationContext()
				.getBean(IEstateContractMappingService.class)
				.findContractDeptWise(orgId, tbDepartment, MainetConstants.CommonConstants.E);
		List<ReportDTO> summaryList = new ArrayList<>();
		ReportDTO reportDTOData = new ReportDTO();
		BigDecimal totalAmount = new BigDecimal(0.00);
		BigDecimal totalArrearsAmt = new BigDecimal(0.00);
		BigDecimal totalCurrentAmt = new BigDecimal(0.00);
		for (ContractMappingDTO contract : contractMapDet) {
			BigDecimal arrearsAmount = new BigDecimal(0.00);
			BigDecimal currentAmount = new BigDecimal(0.00);
			BigDecimal total = new BigDecimal(0.00);
					// fetch list of payment based on contractId
					List<RLBillMaster> installments = reportSummaryRepository
							.findContractInstallmentByIds(contract.getContId(), orgId);
					if (installments.isEmpty())
						// ignore don't add in summaryList
						continue;

					for (RLBillMaster rlBillMaster : installments) {
						// check installment paid which financial year
						// below 3 if condition belongs to within financial year and due date is also
						// within F.Y
						if (rlBillMaster.getPaymentDate() != null
								&& (!checkDateIsInFinYear(financeFromDate, financeToDate, rlBillMaster.getDueDate()))) {
							// check if paid before F.Y or after
							// if before than no arrears
							if (!checkDateIsInFinYear(financeFromDate, financeToDate, rlBillMaster.getPaymentDate())) {
								// check payment in future financial year than add in arrears
								if ((rlBillMaster.getPaymentDate() != null
										&& rlBillMaster.getPaymentDate().after(financeFromDate))
										&& rlBillMaster.getPaymentDate().after(financeToDate)) {
									arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
								}
							} else {
								arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
							}
						} else if (rlBillMaster.getPaymentDate() != null
								&& checkDateIsInFinYear(financeFromDate, financeToDate, rlBillMaster.getDueDate())) {
							currentAmount = currentAmount.add(new BigDecimal(rlBillMaster.getAmount()));
						} else if (rlBillMaster.getPaymentDate() == null
								&& checkDateIsInFinYear(financeFromDate, financeToDate, rlBillMaster.getDueDate())) {
							currentAmount = currentAmount.add(new BigDecimal(rlBillMaster.getAmount()));
						} else if (rlBillMaster.getPaymentDate() != null
								&& rlBillMaster.getPaymentDate().before(financeFromDate)) {
							// payment before
							// currentAmt is 0
						} else if ((rlBillMaster.getPaymentDate() != null
								&& rlBillMaster.getPaymentDate().after(financeFromDate))
								&& checkDateIsInFinYear(financeFromDate, financeToDate,
										rlBillMaster.getPaymentDate())) {
							arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
						} else if ((rlBillMaster.getPaymentDate() != null
								&& rlBillMaster.getPaymentDate().after(financeFromDate))
								&& rlBillMaster.getPaymentDate().after(financeToDate)) {
							// payment in future financial year
							arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
						} else if (rlBillMaster.getPaymentDate() == null
								&& rlBillMaster.getDueDate().before(financeToDate)) {
							// not paid payment
							arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
						} else if (rlBillMaster.getPaymentDate() != null
								&& rlBillMaster.getPaymentDate().after(financeToDate)) {
							// payment outside the > financial year EX: 2020-21>2019-20
							arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
						}
					}
					total = arrearsAmount.add(currentAmount);
			// skip if total is zero
			if (total.compareTo(BigDecimal.ZERO) == 0) {
				continue;
			}
			List<String> vendorNames = reportSummaryRepository.getContractorNames(contract.getContId(), orgId);
			String nameOfLessee = "";
			for (String vendor : vendorNames) {
				nameOfLessee += vendor + ",";
			}
			// remove comma from end side
			if (nameOfLessee != null) {
				if (nameOfLessee.endsWith(",")) {
					nameOfLessee = nameOfLessee.substring(0, nameOfLessee.length() - 1);
				}
			}
			ReportDTO reportDTO = new ReportDTO();
			reportDTO.setNameOfLessee(nameOfLessee);
			Object[] data = reportSummaryRepository.getContractPropertyData(contract.getContId(), orgId);// property
																										// name[0],
			// contract No[1]
			Object[] object = (Object[]) data[0];
			reportDTO.setPropertyName((String) object[0]);
			reportDTO.setContractNo((String) object[1]);

			reportDTO.setArrearsAmt(arrearsAmount.setScale(2, RoundingMode.HALF_UP));

			reportDTO.setCurrentAmt(currentAmount.setScale(2, RoundingMode.HALF_UP));
			reportDTO.setTotalAmt(total.setScale(2, RoundingMode.HALF_UP));
			totalAmount = totalAmount.add(reportDTO.getTotalAmt());
			totalArrearsAmt = totalArrearsAmt.add(reportDTO.getArrearsAmt());
			totalCurrentAmt = totalCurrentAmt.add(reportDTO.getCurrentAmt());
			summaryList.add(reportDTO);
		}
		reportDTOData.setTotalArrearsAmt(totalArrearsAmt.setScale(2));
		reportDTOData.setTotalCurrentAmt(totalCurrentAmt.setScale(2));
		reportDTOData.setCashAmountTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalAmount));
		model.setReportDTO(reportDTOData);
		model.setSummaryReportDetails(summaryList);
	}

	// check date is in financial year or not
	public Boolean checkDateIsInFinYear(Date financialStartDate, Date financialToDate, Date checkDate) {
		return checkDate.after(financialStartDate) && checkDate.before(financialToDate);
	}

	@Override
	public void fetchDemandRegisterReportUsingProp(ReportModel model, Long estateId, Long propId, String financialYear,
			Long orgId) {
		String temp[] = financialYear.split(MainetConstants.HYPHEN);
		Date[] financialDates = { Utility.stringToDate("31/03/" + temp[0]), Utility.stringToDate("01/04/" + temp[1]) };
		SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMATS);
		Date financeFromDate = financialDates[0], financeToDate = financialDates[1];
		try {
			financeFromDate = dateFormat.parse(dateFormat.format(financeFromDate));
			financeToDate = dateFormat.parse(dateFormat.format(financeToDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<ReportDTO> summaryList = new ArrayList<>();
		ReportDTO reportDTOData = new ReportDTO();
		BigDecimal totalAmount = new BigDecimal(0.00);
		BigDecimal totalArrearsAmt = new BigDecimal(0.00);
		BigDecimal totalCurrentAmt = new BigDecimal(0.00);
		final ContractMastEntity contracts = estateContractMappingRepository.findByEstatePropertyPropId(orgId, propId);
		if (contracts != null) {
			TbDepartment tbDepartment = ApplicationContextProvider.getApplicationContext()
					.getBean(TbDepartmentService.class)
					.findDeptByCode(orgId, MainetConstants.RnLCommon.Flag_A, MainetConstants.RnLCommon.RentLease);
			// flag-> E DEPT wise filter
			final List<ContractMappingDTO> contractMapDet = ApplicationContextProvider.getApplicationContext()
					.getBean(IEstateContractMappingService.class)
					.findContractsByContractId(orgId, contracts.getContId(), tbDepartment);
			for (ContractMappingDTO contract : contractMapDet) {
				BigDecimal arrearsAmount = new BigDecimal(0.00);
				BigDecimal currentAmount = new BigDecimal(0.00);
				BigDecimal total = new BigDecimal(0.00);
						// fetch list of payment based on contractId
						List<RLBillMaster> installments = reportSummaryRepository
								.findContractInstallmentByIds(contract.getContId(), orgId);
						if (installments.isEmpty())
							// ignore don't add in summaryList
							continue;

						for (RLBillMaster rlBillMaster : installments) {
							// check installment paid which financial year
							// below 3 if condition belongs to within financial year and due date is also
							// within F.Y
							if (rlBillMaster.getPaymentDate() != null && (!checkDateIsInFinYear(financeFromDate,
									financeToDate, rlBillMaster.getDueDate()))) {
								// check if paid before F.Y or after
								// if before than no arrears
								if (!checkDateIsInFinYear(financeFromDate, financeToDate,
										rlBillMaster.getPaymentDate())) {
									// check payment in future financial year than add in arrears
									if ((rlBillMaster.getPaymentDate() != null
											&& rlBillMaster.getPaymentDate().after(financeFromDate))
											&& rlBillMaster.getPaymentDate().after(financeToDate)) {
										arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
									}
								} else {
									arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
								}
							} else if (rlBillMaster.getPaymentDate() != null && checkDateIsInFinYear(financeFromDate,
									financeToDate, rlBillMaster.getDueDate())) {
								currentAmount = currentAmount.add(new BigDecimal(rlBillMaster.getAmount()));
							} else if (rlBillMaster.getPaymentDate() == null && checkDateIsInFinYear(financeFromDate,
									financeToDate, rlBillMaster.getDueDate())) {
								currentAmount = currentAmount.add(new BigDecimal(rlBillMaster.getAmount()));
							} else if (rlBillMaster.getPaymentDate() != null
									&& rlBillMaster.getPaymentDate().before(financeFromDate)) {
								// payment before
								// currentAmt is 0
							} else if ((rlBillMaster.getPaymentDate() != null
									&& rlBillMaster.getPaymentDate().after(financeFromDate))
									&& checkDateIsInFinYear(financeFromDate, financeToDate,
											rlBillMaster.getPaymentDate())) {
								arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
							} else if ((rlBillMaster.getPaymentDate() != null
									&& rlBillMaster.getPaymentDate().after(financeFromDate))
									&& rlBillMaster.getPaymentDate().after(financeToDate)) {
								// payment in future financial year
								arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
							} else if (rlBillMaster.getPaymentDate() == null
									&& rlBillMaster.getDueDate().before(financeToDate)) {
								// not paid payment
								arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
							} else if (rlBillMaster.getPaymentDate() != null
									&& rlBillMaster.getPaymentDate().after(financeToDate)) {
								// payment outside the > financial year EX: 2020-21>2019-20
								arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
							}
						}
						total = arrearsAmount.add(currentAmount);
				// skip if total is zero
				if (total.compareTo(BigDecimal.ZERO) == 0) {
					continue;
				}
				List<String> vendorNames = reportSummaryRepository.getContractorNames(contract.getContId(), orgId);
				String nameOfLessee = "";
				for (String vendor : vendorNames) {
					nameOfLessee += vendor + ",";
				}
				// remove comma from end side
				if (nameOfLessee != null) {
					if (nameOfLessee.endsWith(",")) {
						nameOfLessee = nameOfLessee.substring(0, nameOfLessee.length() - 1);
					}
				}
				ReportDTO reportDTO = new ReportDTO();
				reportDTO.setNameOfLessee(nameOfLessee);
				Object[] data = reportSummaryRepository.getContractPropertyData(contract.getContId(), orgId);// property
																												// name[0],
				// contract No[1]
				Object[] object = (Object[]) data[0];
				reportDTO.setPropertyName((String) object[0]);
				reportDTO.setContractNo((String) object[1]);

				reportDTO.setArrearsAmt(arrearsAmount.setScale(2, RoundingMode.HALF_UP));

				reportDTO.setCurrentAmt(currentAmount.setScale(2, RoundingMode.HALF_UP));
				reportDTO.setTotalAmt(total.setScale(2, RoundingMode.HALF_UP));
				totalAmount = totalAmount.add(reportDTO.getTotalAmt());
				totalArrearsAmt = totalArrearsAmt.add(reportDTO.getArrearsAmt());
				totalCurrentAmt = totalCurrentAmt.add(reportDTO.getCurrentAmt());
				summaryList.add(reportDTO);
			}
			reportDTOData.setTotalArrearsAmt(totalArrearsAmt.setScale(2));
			reportDTOData.setTotalCurrentAmt(totalCurrentAmt.setScale(2));
			reportDTOData.setCashAmountTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalAmount));
			model.setReportDTO(reportDTOData);
			model.setSummaryReportDetails(summaryList);

		}
	}

	@Override
	public void fetchDemandRegisterReportAllProp(ReportModel model, Long estateId, List<EstatePropGrid> proplist,
			String financialYear, Long orgId) {
		String temp[] = financialYear.split(MainetConstants.HYPHEN);
		Date[] financialDates = { Utility.stringToDate("31/03/" + temp[0]), Utility.stringToDate("01/04/" + temp[1]) };
		SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMATS);
		Date financeFromDate = financialDates[0], financeToDate = financialDates[1];
		try {
			financeFromDate = dateFormat.parse(dateFormat.format(financeFromDate));
			financeToDate = dateFormat.parse(dateFormat.format(financeToDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<ReportDTO> summaryList = new ArrayList<>();
		ReportDTO reportDTOData = new ReportDTO();
		BigDecimal totalAmount = new BigDecimal(0.00);
		BigDecimal totalArrearsAmt = new BigDecimal(0.00);
		BigDecimal totalCurrentAmt = new BigDecimal(0.00);
		List<ContractMastEntity> contracts =  new ArrayList<>();
		for(EstatePropGrid prop :proplist) {
			List<ContractMastEntity> contract = estateContractMappingRepository.findAllMappedProperty(orgId, prop.getPropId());
			if(contract != null) {
				contracts.addAll(contract);
			}
		}
		//final ContractMastEntity contracts = estateContractMappingRepository.findByEstatePropertyPropId(orgId, propId);
		if (contracts != null) {
			for(ContractMastEntity cont : contracts ) {
				if(cont.getContId() != 0L) {
					TbDepartment tbDepartment = ApplicationContextProvider.getApplicationContext()
							.getBean(TbDepartmentService.class)
							.findDeptByCode(orgId, MainetConstants.RnLCommon.Flag_A, MainetConstants.RnLCommon.RentLease);
					// flag-> E DEPT wise filter
					final List<ContractMappingDTO> contractMapDet = ApplicationContextProvider.getApplicationContext()
							.getBean(IEstateContractMappingService.class)
							.findContractsByContractId(orgId, cont.getContId(), tbDepartment);
					for (ContractMappingDTO contract : contractMapDet) {
						BigDecimal arrearsAmount = new BigDecimal(0.00);
						BigDecimal currentAmount = new BigDecimal(0.00);
						BigDecimal total = new BigDecimal(0.00);
								// fetch list of payment based on contractId
								List<RLBillMaster> installments = reportSummaryRepository
										.findContractInstallmentByIds(contract.getContId(), orgId);
								if (installments.isEmpty())
									// ignore don't add in summaryList
									continue;

								for (RLBillMaster rlBillMaster : installments) {
									// check installment paid which financial year
									// below 3 if condition belongs to within financial year and due date is also
									// within F.Y
									if (rlBillMaster.getPaymentDate() != null && (!checkDateIsInFinYear(financeFromDate,
											financeToDate, rlBillMaster.getDueDate()))) {
										// check if paid before F.Y or after
										// if before than no arrears
										if (!checkDateIsInFinYear(financeFromDate, financeToDate,
												rlBillMaster.getPaymentDate())) {
											// check payment in future financial year than add in arrears
											if ((rlBillMaster.getPaymentDate() != null
													&& rlBillMaster.getPaymentDate().after(financeFromDate))
													&& rlBillMaster.getPaymentDate().after(financeToDate)) {
												arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
											}
										} else {
											arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
										}
									} else if (rlBillMaster.getPaymentDate() != null && checkDateIsInFinYear(financeFromDate,
											financeToDate, rlBillMaster.getDueDate())) {
										currentAmount = currentAmount.add(new BigDecimal(rlBillMaster.getAmount()));
									} else if (rlBillMaster.getPaymentDate() == null && checkDateIsInFinYear(financeFromDate,
											financeToDate, rlBillMaster.getDueDate())) {
										currentAmount = currentAmount.add(new BigDecimal(rlBillMaster.getAmount()));
									} else if (rlBillMaster.getPaymentDate() != null
											&& rlBillMaster.getPaymentDate().before(financeFromDate)) {
										// payment before
										// currentAmt is 0
									} else if ((rlBillMaster.getPaymentDate() != null
											&& rlBillMaster.getPaymentDate().after(financeFromDate))
											&& checkDateIsInFinYear(financeFromDate, financeToDate,
													rlBillMaster.getPaymentDate())) {
										arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
									} else if ((rlBillMaster.getPaymentDate() != null
											&& rlBillMaster.getPaymentDate().after(financeFromDate))
											&& rlBillMaster.getPaymentDate().after(financeToDate)) {
										// payment in future financial year
										arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
									} else if (rlBillMaster.getPaymentDate() == null
											&& rlBillMaster.getDueDate().before(financeToDate)) {
										// not paid payment
										arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
									} else if (rlBillMaster.getPaymentDate() != null
											&& rlBillMaster.getPaymentDate().after(financeToDate)) {
										// payment outside the > financial year EX: 2020-21>2019-20
										arrearsAmount = arrearsAmount.add(new BigDecimal(rlBillMaster.getAmount()));
									}
								}
								total = arrearsAmount.add(currentAmount);
						// skip if total is zero
						if (total.compareTo(BigDecimal.ZERO) == 0) {
							continue;
						}
						List<String> vendorNames = reportSummaryRepository.getContractorNames(contract.getContId(), orgId);
						String nameOfLessee = "";
						for (String vendor : vendorNames) {
							nameOfLessee += vendor + ",";
						}
						// remove comma from end side
						if (nameOfLessee != null) {
							if (nameOfLessee.endsWith(",")) {
								nameOfLessee = nameOfLessee.substring(0, nameOfLessee.length() - 1);
							}
						}
						ReportDTO reportDTO = new ReportDTO();
						reportDTO.setNameOfLessee(nameOfLessee);
						Object[] data = reportSummaryRepository.getContractPropertyData(contract.getContId(), orgId);// property
																														// name[0],
						// contract No[1]
						Object[] object = (Object[]) data[0];
						reportDTO.setPropertyName((String) object[0]);
						reportDTO.setContractNo((String) object[1]);

						reportDTO.setArrearsAmt(arrearsAmount.setScale(2, RoundingMode.HALF_UP));

						reportDTO.setCurrentAmt(currentAmount.setScale(2, RoundingMode.HALF_UP));
						reportDTO.setTotalAmt(total.setScale(2, RoundingMode.HALF_UP));
						totalAmount = totalAmount.add(reportDTO.getTotalAmt());
						totalArrearsAmt = totalArrearsAmt.add(reportDTO.getArrearsAmt());
						totalCurrentAmt = totalCurrentAmt.add(reportDTO.getCurrentAmt());
						summaryList.add(reportDTO);
					}
				}
			}
			
			reportDTOData.setTotalArrearsAmt(totalArrearsAmt.setScale(2));
			reportDTOData.setTotalCurrentAmt(totalCurrentAmt.setScale(2));
			reportDTOData.setCashAmountTotalIndianCurrency(CommonMasterUtility.getAmountInIndianCurrency(totalAmount));
			model.setReportDTO(reportDTOData);
			model.setSummaryReportDetails(summaryList);

		}
		
	}
}
