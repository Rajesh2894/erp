package com.abm.mainet.rnl.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.abm.mainet.bill.service.BillDetailsService;
import com.abm.mainet.bill.service.BillGenerationService;
import com.abm.mainet.bill.service.BillMasterCommonService;
import com.abm.mainet.bill.service.BillPaymentService;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.IFinancialYearDAO;
import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.common.domain.ContractPart2DetailEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.BillReceiptPostingDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractInstalmentDetailDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.OrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rnl.datamodel.RNLRateMaster;
import com.abm.mainet.rnl.domain.EstateContractMapping;
import com.abm.mainet.rnl.domain.RLBillMaster;
import com.abm.mainet.rnl.repository.EstateContractMappingRepository;
import com.abm.mainet.rnl.repository.RLBillMasterRepository;
import com.abm.mainet.rnl.repository.ReportSummaryRepository;

@Service(value = "RNLBillService")
@Repository
public class RLBILLMasterServiceImpl
		implements IRLBILLMasterService, BillPaymentService, BillGenerationService, BillDetailsService {

	private static Logger logger = Logger.getLogger(RLBILLMasterServiceImpl.class);

	@Autowired
	private RLBillMasterRepository rLBillMasterRepository;

	@Autowired
	private BillMasterCommonService billMasterService;

	@Autowired
	private ContractAgreementRepository contractAgreementRepository;

	@Autowired
	ReportSummaryRepository reportSummaryRepository;

	@Autowired
	IFinancialYearService iFinancialYearService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;

	@Autowired
	private BRMSRNLService brmsRNLService;

	@Autowired
	private EstateContractMappingRepository estateContractMappingRepository;

	@Autowired
	private IContractAgreementService iContractAgreementService;
	
	@Autowired
    private IFinancialYearDAO financialYearDAO;


	/*
	 * @Autowired private IRNLChecklistAndChargeServiceiRNLChecklistAndChargeService;
	 */

	/*
	 * @Autowired private ServiceMasterService serviceMasterService;
	 */

	@Override
	public List<RLBillMaster> finByContractId(final Long contId, final Long orgId, String paidFlag, String bmType) {
		Organisation  organisation= new Organisation();
		organisation.setOrgid(orgId);
		
			return rLBillMasterRepository.findByContIdAndOrgIdAndPaidFlagAndBmTypeOrderByBillIdAsc(contId, orgId, paidFlag, bmType);
		

	}

	@Override
	@Transactional
	public List<BillReceiptPostingDTO> updateBillMasterAmountPaid(final String contNo, final Double amount,
			final Long orgid, Long userId, String ipAddress, Date manualReceptDate, String flatNo) {
		List<BillReceiptPostingDTO> result = new ArrayList<>();
		final Organisation org = new Organisation();
		org.setOrgid(orgid);
		ContractAgreementSummaryDTO contractAgreementSummaryDTO = null;
		contractAgreementSummaryDTO = iContractAgreementService.findByContractNo(orgid, contNo);
		Long contId = contractAgreementSummaryDTO.getContId();
		final List<ContractInstalmentDetailEntity> contractInstalmentDetailEntities = contractAgreementRepository
				.finAllRecord(contId, MainetConstants.RnLCommon.Y_FLAG);
		Map<Long, Double> details = new HashMap<>(0);
		Map<Long, Long> sacHeadIdDetails = new HashMap<>(0);
		final Long currDemandId = CommonMasterUtility
				.getValueFromPrefixLookUp(MainetConstants.ReceivableDemandEntry.CURRENT_YEAR,
						MainetConstants.ReceivableDemandEntry.DEMAND_CLASSIFICATION, org)
				.getLookUpId();
		final Long sacHeadId = tbTaxMasService.fetchSacHeadIdForReceiptDetByDemandClass(orgid,
				contractInstalmentDetailEntities.get(0).getTaxId(), MainetConstants.STATUS.ACTIVE, currDemandId);
		sacHeadIdDetails.put(contractInstalmentDetailEntities.get(0).getTaxId(), sacHeadId);
		details.put(contractInstalmentDetailEntities.get(0).getTaxId(), amount);
		contractAgreementSummaryDTO = calculate(amount, contId, org);

		if (contractAgreementSummaryDTO.getChargeList() != null
				&& !contractAgreementSummaryDTO.getChargeList().isEmpty()) {
			contractAgreementSummaryDTO.getChargeList().forEach(charge -> {
				details.put(charge.getChargeCode(), charge.getChargeAmount());
				final Long sacHeadIdChargeCode = tbTaxMasService.fetchSacHeadIdForReceiptDetByDemandClass(orgid,
						charge.getChargeCode(), MainetConstants.STATUS.ACTIVE, currDemandId);
				sacHeadIdDetails.put(charge.getChargeCode(), sacHeadIdChargeCode);
			});
		}
		List<AccountHeadSecondaryAccountCodeMasterEntity> secondaryHeadCodesList = new ArrayList<>();
		final List<AccountHeadSecondaryAccountCodeMasterEntity> secondaryHeadCodes = secondaryheadMasterService
				.getSecondaryHeadcodesForTax(org.getOrgid());

		for (AccountHeadSecondaryAccountCodeMasterEntity sacHead : secondaryHeadCodes) {
			if (sacHeadIdDetails.containsValue(sacHead.getSacHeadId())) {
				secondaryHeadCodesList.add(sacHead);
			}
		}
		List<RLBillMaster> rlBillMasterList = finByContractId(contId, orgid, MainetConstants.CommonConstants.N,
				MainetConstants.CommonConstants.B);
		// update installments data in TB_RL_BILL_MAST
		RLBillMaster rlBillMaster = null;
		Double paidInstallmentAmt = 0d;
		Double payAmount = contractAgreementSummaryDTO.getArrearReceivedAmount();
		ContractAgreementSummaryDTO penlatychargeDTO = new ContractAgreementSummaryDTO();
		Double penlatycharge = 0.0;
		Long penaltyTaxCode = 0L;
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
			penlatychargeDTO = findPenaltyChargeList(rlBillMasterList, org);
			for (final ChargeDetailDTO charge : penlatychargeDTO.getChargeList()) {
				penaltyTaxCode = Long.valueOf(charge.getChargeCode());
				penlatycharge = charge.getChargeAmount();
			}

		}
		Double currentamt = null;
		Double arrearamt = null;
		Double totalArrearAmount = 0.0;
		Double arreargst = 0.0;
		Double currentgst = 0.0;
		if (penlatycharge > 0) {
			Long penaltycurrentYear = financialYearDAO.getFinanceYearId(new Date());
			if (payAmount.equals(penlatycharge)) {
				setBillReceiptPostingDTO(result, arrearamt, penlatycharge, penlatycharge, null, penaltyTaxCode, orgid,penaltycurrentYear);
				penlatycharge = 0d;
				payAmount = 0d;

			} else if (payAmount > 0 && payAmount > penlatycharge) {
				payAmount = payAmount - penlatycharge;
				setBillReceiptPostingDTO(result, arrearamt, penlatycharge, penlatycharge, null, penaltyTaxCode, orgid,penaltycurrentYear);
				penlatycharge = 0d;

			}
		}
		if (penlatycharge <= 0 && payAmount > 0) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMATS);
			Calendar calCurrent = Calendar.getInstance();
			Date currentDate = calCurrent.getTime();
			Date endDate = DateUtils.addMonths(new Date(), 1);
			for (int i = 0; i < rlBillMasterList.size(); i++) {
				if (payAmount > 0) {
					rlBillMaster = rlBillMasterList.get(i);
					paidInstallmentAmt = rlBillMaster.getBalanceAmount();
					Date dueDate = rlBillMaster.getDueDate();
					Long billYear = 0L;
					try {
						currentDate = dateFormat.parse(dateFormat.format(currentDate));
						endDate = dateFormat.parse(dateFormat.format(endDate));
						dueDate = dateFormat.parse(dateFormat.format(dueDate));
					} catch (ParseException e) {
						e.printStackTrace();

					}
					Long currentYear = financialYearDAO.getFinanceYearId(new Date());
					try {
						billYear = financialYearDAO.getFinanceYearId(rlBillMaster.getDueDate());
					} catch (Exception e) {

					}

					boolean conditionArrear;
					boolean conditionCurrent;
					if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
						conditionArrear = (paidInstallmentAmt > 0 && (currentYear > billYear) && (billYear != 0));
						conditionCurrent = (paidInstallmentAmt > 0 && (currentYear <= billYear));
					} else {
						conditionArrear = (currentDate.compareTo(dueDate) > 0);
						conditionCurrent = (currentDate.compareTo(dueDate) <= 0);
					}
					if (conditionArrear) {
						if (payAmount > 0) {
							rlBillMaster.setUpdatedBy(userId);
							rlBillMaster.setUpdatedDate(new Date());
							rlBillMaster.setLgIpMacUp(ipAddress);
							if (payAmount.equals(paidInstallmentAmt)) {
								rlBillMaster.setPaidAmount(rlBillMaster.getAmount());
								rlBillMaster.setBalanceAmount(0d);
								updateRLBillMas(rlBillMaster);

								setBillReceiptPostingDTO(result, payAmount, currentamt, payAmount,
										rlBillMaster.getBillId(), rlBillMaster.getTaxId(), orgid,billYear);
								if (contractAgreementSummaryDTO.getChargeList() != null) {
									for (ChargeDetailDTO charge : contractAgreementSummaryDTO.getChargeList()) {
										setBillReceiptPostingDTO(result, charge.getChargeAmount(), currentamt,
												charge.getChargeAmount(), null, charge.getChargeCode(), orgid,billYear);
									}
								}
								payAmount = 0d;

							} else if (payAmount > 0 && payAmount > paidInstallmentAmt) {
								rlBillMaster.setPaidAmount(rlBillMaster.getAmount());
								BigDecimal b1 = new BigDecimal(Double.toString(rlBillMaster.getBalanceAmount()));
								BigDecimal b2 = new BigDecimal(Double.toString(payAmount));
								rlBillMaster.setBalanceAmount(0d);
								updateRLBillMas(rlBillMaster);
								totalArrearAmount += paidInstallmentAmt;
								payAmount = b2.subtract(b1).doubleValue();
								setBillReceiptPostingDTO(result, b1.doubleValue(), currentamt, b1.doubleValue(),
										rlBillMaster.getBillId(), rlBillMaster.getTaxId(), orgid,billYear);

							} else if (payAmount > 0 && payAmount < paidInstallmentAmt) {
								BigDecimal b1 = new BigDecimal(Double.toString(rlBillMaster.getBalanceAmount()));
								BigDecimal b2 = new BigDecimal(Double.toString(payAmount));
								Double balanceAmount = b1.subtract(b2).doubleValue();
								rlBillMaster.setBalanceAmount(balanceAmount);
								if (rlBillMaster.getPaidAmount() != null) {
									rlBillMaster.setPaidAmount(payAmount + rlBillMaster.getPaidAmount());
								} else {
									rlBillMaster.setPaidAmount(payAmount);
								}
								updateRLPartialBillMas(rlBillMaster);
								setBillReceiptPostingDTO(result, payAmount, currentamt, payAmount,
										rlBillMaster.getBillId(), rlBillMaster.getTaxId(), orgid,billYear);
								if (contractAgreementSummaryDTO.getChargeList() != null) {
									for (ChargeDetailDTO charge : contractAgreementSummaryDTO.getChargeList()) {
										setBillReceiptPostingDTO(result, charge.getChargeAmount(), currentamt,
												charge.getChargeAmount(), null, charge.getChargeCode(), orgid,billYear);
									}
								}
								payAmount = 0d;
							}
						}

					} else if (conditionCurrent) {
						if (payAmount > 0) {
							rlBillMaster.setUpdatedBy(userId);
							rlBillMaster.setUpdatedDate(new Date());
							rlBillMaster.setLgIpMacUp(ipAddress);
							if (payAmount.equals(paidInstallmentAmt)) {
								rlBillMaster.setPaidAmount(rlBillMaster.getAmount());
								rlBillMaster.setBalanceAmount(0d);
								updateRLBillMas(rlBillMaster);
								setBillReceiptPostingDTO(result, arrearamt, payAmount, payAmount,
										rlBillMaster.getBillId(), rlBillMaster.getTaxId(), orgid,billYear);
								if (contractAgreementSummaryDTO.getChargeList() != null) {
									for (ChargeDetailDTO charge : contractAgreementSummaryDTO.getChargeList()) {
										if (totalArrearAmount > 0) {
											arreargst = totalArrearAmount * charge.getPercentageRate() / 100;
											BigDecimal arreargstamt = new BigDecimal(arreargst);
											arreargst = arreargstamt.setScale(0, RoundingMode.HALF_UP).doubleValue();
											setBillReceiptPostingDTO(result, arreargst, currentamt, arreargst, null,
													charge.getChargeCode(), orgid,billYear);
										}
									}
								}
								if (contractAgreementSummaryDTO.getChargeList() != null) {
									for (ChargeDetailDTO charge : contractAgreementSummaryDTO.getChargeList()) {
										currentgst = charge.getChargeAmount() - arreargst;
										setBillReceiptPostingDTO(result, arrearamt, currentgst, currentgst, null,
												charge.getChargeCode(), orgid,billYear);
									}
								}
								payAmount = 0d;

							} else if (payAmount > 0 && payAmount > paidInstallmentAmt) {
								rlBillMaster.setPaidAmount(rlBillMaster.getAmount());
								BigDecimal b1 = new BigDecimal(Double.toString(rlBillMaster.getBalanceAmount()));
								BigDecimal b2 = new BigDecimal(Double.toString(payAmount));
								rlBillMaster.setBalanceAmount(0d);
								updateRLBillMas(rlBillMaster);
								payAmount = b2.subtract(b1).doubleValue();
								setBillReceiptPostingDTO(result, arrearamt, b1.doubleValue(), b1.doubleValue(),
										rlBillMaster.getBillId(), rlBillMaster.getTaxId(), orgid,billYear);

							} else if (payAmount > 0 && payAmount < paidInstallmentAmt) {
								BigDecimal b1 = new BigDecimal(Double.toString(rlBillMaster.getBalanceAmount()));
								BigDecimal b2 = new BigDecimal(Double.toString(payAmount));
								Double balanceAmount = b1.subtract(b2).doubleValue();
								rlBillMaster.setBalanceAmount(balanceAmount);
								if (rlBillMaster.getPaidAmount() != null) {
									rlBillMaster.setPaidAmount(payAmount + rlBillMaster.getPaidAmount());
								} else {
									rlBillMaster.setPaidAmount(payAmount);
								}
								updateRLPartialBillMas(rlBillMaster);
								setBillReceiptPostingDTO(result, arrearamt, payAmount, payAmount,
										rlBillMaster.getBillId(), rlBillMaster.getTaxId(), orgid,billYear);
								if (contractAgreementSummaryDTO.getChargeList() != null) {
									for (ChargeDetailDTO charge : contractAgreementSummaryDTO.getChargeList()) {
										if (totalArrearAmount > 0) {
											arreargst = totalArrearAmount * charge.getPercentageRate() / 100;
											BigDecimal arreargstamt = new BigDecimal(arreargst);
											arreargst = arreargstamt.setScale(0, RoundingMode.HALF_UP).doubleValue();
											setBillReceiptPostingDTO(result, arreargst, currentamt, arreargst, null,
													charge.getChargeCode(), orgid,billYear);
										}
									}

								}
								if (contractAgreementSummaryDTO.getChargeList() != null) {
									for (ChargeDetailDTO charge : contractAgreementSummaryDTO.getChargeList()) {
										currentgst = charge.getChargeAmount() - arreargst;
										setBillReceiptPostingDTO(result, arrearamt, currentgst, currentgst, null,
												charge.getChargeCode(), orgid,billYear);
									}
								}

								payAmount = 0d;
							}
						}
					}
				}
			}
		}
		return result;
	}

	private void setBillReceiptPostingDTO(List<BillReceiptPostingDTO> result, Double arrear, Double current,
			Double taxAmt, Long bmId, Long taxId, Long orgid,Long billYear) {
		BillReceiptPostingDTO billUpdate = new BillReceiptPostingDTO();
		if (current != null) {
			billUpdate.setPayableAmount(current);
			billUpdate.setTotalDetAmount(current);
		} else if (arrear != null) {
			billUpdate.setArrearAmount(arrear);
			billUpdate.setTotalDetAmount(arrear);
		}
		billUpdate.setTaxAmount(taxAmt);
		if (bmId != null) {
			billUpdate.setBmIdNo(bmId.toString());
			billUpdate.setBillMasId(bmId);
		}
		billUpdate.setTaxId(taxId);
		TbTaxMas taxMaster = tbTaxMasService.findById(taxId, orgid);
		billUpdate.setDisplaySeq(taxMaster.getTaxDisplaySeq());
		billUpdate.setTaxCategory(taxMaster.getTaxCategory1());
		billUpdate.setTaxCategoryCode(taxMaster.getTaxCode());
		billUpdate.setYearId(billYear);
		result.add(billUpdate);
	}

	private WSResponseDTO initializeModelForTax() {
		final WSRequestDTO dto = new WSRequestDTO();
		dto.setModelName(MainetConstants.RnLCommon.RNLRATEMASTER);
		final WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
		return response;
	}

	private String getSubCategoryDesc(final String taxsubCategory, final Organisation org) {
		String subCategoryDesc = "";
		final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.TAC_PREFIX,
				MainetConstants.EstateBooking.LEVEL, org);
		for (final LookUp lookup : subCategryLookup) {
			if (lookup.getLookUpCode().equals(taxsubCategory)) {
				subCategoryDesc = lookup.getDescLangFirst();
				break;
			}
		}
		return subCategoryDesc;
	}

	private ContractAgreementSummaryDTO calculate(Double amount, Long contId, Organisation orgnisation) {
		final List<EstateContractMapping> estateContractMappingList = estateContractMappingRepository
				.findByContractMastEntityContId(contId);
		final ContractAgreementSummaryDTO contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
		// final Organisation orgnisation = UserSession.getCurrent().getOrganisation();
		Long taxMasLookUpId = null;
		String Usagelookupcode = null;
		String Usage = estateContractMappingList.get(0).getEstatePropertyEntity().getUsage().toString();
		List<LookUp> lookUp2 = CommonMasterUtility.getLevelData("USA", 1, orgnisation);
		for (LookUp lookUp : lookUp2) {
			if (Usage != null && !Usage.isEmpty()) {
				if (lookUp.getLookUpId() == Long.parseLong(Usage)) {
					taxMasLookUpId = lookUp.getLookUpId();
					Usagelookupcode = lookUp.getLookUpCode();
				}
			}
		}
		if (Usagelookupcode != null && !Usagelookupcode.equals("RESNG")) {
			WSResponseDTO response = initializeModelForTax();
			final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp("APL",
					PrefixConstants.NewWaterServiceConstants.CAA, orgnisation);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> rnlRateMasterList = RestClient.castResponse(response, RNLRateMaster.class, 0);
				final RNLRateMaster rnlRateMaster = (RNLRateMaster) rnlRateMasterList.get(0);
				rnlRateMaster.setOrgId(orgnisation.getOrgid());

				rnlRateMaster.setDeptCode(MainetConstants.RnLCommon.RL_SHORT_CODE);
				WSRequestDTO taxReqDTO = new WSRequestDTO();
				rnlRateMaster.setOrgId(orgnisation.getOrgid());
				rnlRateMaster.setServiceCode(MainetConstants.EstateContract.CBP);
				rnlRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
				taxReqDTO.setDataModel(rnlRateMaster);

				final WSResponseDTO taxResponseDTO = brmsRNLService.getApplicableTaxes(taxReqDTO);

				if (taxResponseDTO.getWsStatus() != null
						&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {

					if (!taxResponseDTO.isFree()) {
						final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
						final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
						for (final Object rate : rates) {
							final RNLRateMaster master1 = (RNLRateMaster) rate;
							master1.setOrgId(orgnisation.getOrgid());
							master1.setServiceCode(MainetConstants.EstateContract.CBP);
							master1.setDeptCode(MainetConstants.RNL_DEPT_CODE);
							master1.setChargeApplicableAt(
									CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.CAA,
											orgnisation.getOrgid(),
									Long.parseLong(rnlRateMaster.getChargeApplicableAt())));

							master1.setTaxSubCategory(getSubCategoryDesc(master1.getTaxSubCategory(),
									orgnisation));
							// set payable Amt from user input
							master1.setTotalAmount(amount);
							requiredCHarges.add(master1);
						}
						WSRequestDTO chargeReqDTO = new WSRequestDTO();
						chargeReqDTO.setDataModel(requiredCHarges);
						WSResponseDTO applicableCharges = brmsRNLService.getApplicableCharges(chargeReqDTO);
						final List<ChargeDetailDTO> charges = (List<ChargeDetailDTO>) applicableCharges
								.getResponseObj();
						if (charges == null) {
							throw new FrameworkException("Charges not Found in brms Sheet");
						} else {

							LookUp lookUp1 = CommonMasterUtility.getValueFromPrefixLookUp("DC", "GCC", orgnisation);
							if (lookUp1 != null && lookUp1.getOtherField().equals("Y")) {
								List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
								Double finalAmt = amount;
								for (final ChargeDetailDTO charge : charges) {
									BigDecimal amounts = new BigDecimal(charge.getChargeAmount());
									charge.setChargeAmount(amounts.setScale(0, RoundingMode.HALF_UP).doubleValue());
									// charge.setChargeAmount(amount.doubleValue());
									chargeList.add(charge);
									finalAmt -= charge.getChargeAmount();
								}
								contractAgreementSummaryDTO.setChargeList(chargeList);
								contractAgreementSummaryDTO.setArrearReceivedAmount(finalAmt);

							} else {
								List<ChargeDetailDTO> chargeList = new ArrayList<>(0);
								// payment AMT +total Taxable AMT
								Double finalAmt = amount;
								for (final ChargeDetailDTO charge : charges) {
									BigDecimal amounts = new BigDecimal(charge.getChargeAmount());
									charge.setChargeAmount(amounts.doubleValue());
									chargeList.add(charge);
									finalAmt += charge.getChargeAmount();
								}
								contractAgreementSummaryDTO.setChargeList(chargeList);
								contractAgreementSummaryDTO.setArrearReceivedAmount(finalAmt);
							}
						}
					} else {
						contractAgreementSummaryDTO.setArrearReceivedAmount(amount);
					}
				}
			}
		} else {
			contractAgreementSummaryDTO.setArrearReceivedAmount(amount);
		}
		return contractAgreementSummaryDTO;
	}


	@Transactional
	public List<BillReceiptPostingDTO> updateBillMasterAmountPaidOldCode(final String contNo, final Double amount,
			final Long orgid, Long userId, String ipAddress, Date manualReceptDate, String flatNo) {
		List<BillReceiptPostingDTO> result = new ArrayList<>();
		final Organisation org = new Organisation();
		org.setOrgid(orgid);
		final List<RLBillMaster> rlbillMasters = rLBillMasterRepository.findByContIdAndOrgIdAndPaidFlagAndBmTypeOrderByBillIdAsc(
				Long.valueOf(contNo),
 				orgid,MainetConstants.CommonConstants.N, MainetConstants.CommonConstants.B);
		final List<TbBillMas> billMasData = new ArrayList<>();
		TbBillMas tbWtBillMas;
		final double interest = 2d;
		for (final RLBillMaster rlBillMaster : rlbillMasters) {
			tbWtBillMas = new TbBillMas();
			tbWtBillMas.setBmDuedate(rlBillMaster.getDueDate());

			tbWtBillMas.setBmTotalAmount(rlBillMaster.getAmount());
			tbWtBillMas.setBmTotalBalAmount(rlBillMaster.getBalanceAmount());
			tbWtBillMas.setBmIdno(rlBillMaster.getBillId());
			tbWtBillMas.setBmPaidFlag(MainetConstants.CommonConstants.N);
			tbWtBillMas.setOrgid(rlBillMaster.getOrgId());
			tbWtBillMas.setBmBilldt(rlBillMaster.getStartDate());

			tbWtBillMas.setBmIntValue(interest / 100);

			billMasData.add(tbWtBillMas);
		}
		if ((billMasData != null) && !billMasData.isEmpty()) {
			final Map<Long, Double> details = new HashMap<>(0);
			final Map<Long, Long> billDetails = new HashMap<>(0);
			int count = 0;
			final Calendar currentDate = Calendar.getInstance();
			final Calendar billDate = Calendar.getInstance();
			currentDate.setTime(new Date());
			// update here
			// currentDate.add(Calendar.DATE, 15);

			for (final TbBillMas tbWtBillMas1 : billMasData) {
				billDate.setTime(tbWtBillMas1.getBmDuedate());
				if (billDate.before(currentDate)) {
					count++;
				}
			}
			final List<TbBillMas> newList = new ArrayList<>(billMasData.subList(0, count));
			billMasterService.processBillNewData(newList, null);
			final int newListSize = newList.size();
			billMasData.size();

			final TbBillMas resultBill = newList.get(newListSize - 1);
			final TbBillMas updatetax = newList.get(newListSize - 2);
			RLBillMaster newEntry = null;
			for (final RLBillMaster rlBillMaster : rlbillMasters) {
				if (updatetax.getBmIdno() == rlBillMaster.getBillId()) {
					final Date date = new Date();
					try {
						newEntry = (RLBillMaster) rlBillMaster.clone();
					} catch (final CloneNotSupportedException e) {

					}
					newEntry.setBillId(null);
					newEntry.setBillDate(date);
					newEntry.setAmount(resultBill.getBmTotalCumIntArrears());

					newEntry.setBalanceAmount(resultBill.getBmTotalCumIntArrears());
					newEntry.setCreatedDate(date);

					newEntry.setPaymentDate(date);
					newEntry.setRemarks(updatetax.getBmNo() + MainetConstants.RnLService.REMARKS);
					newEntry.setBmType(MainetConstants.STATUS.INACTIVE);
					break;
				}
			}
			rLBillMasterRepository.save(newEntry);

			rlbillMasters.add(newEntry);
			final TbBillMas newEntryForUpadte = new TbBillMas();
			newEntryForUpadte.setBmDuedate(newEntry.getDueDate());

			newEntryForUpadte.setBmTotalAmount(resultBill.getBmTotalCumIntArrears());
			newEntryForUpadte.setBmTotalBalAmount(resultBill.getBmTotalCumIntArrears());
			newEntryForUpadte.setBmIdno(newEntry.getBillId());
			newEntryForUpadte.setBmPaidFlag(MainetConstants.MENU.N);
			newEntryForUpadte.setOrgid(newEntry.getOrgId());
			newEntryForUpadte.setBmBilldt(newEntry.getStartDate());

			newEntryForUpadte.setBmIntValue(interest / 100);

			final List<TbBillMas> updateBillMasters = new ArrayList<>();
			updateBillMasters.add(newEntryForUpadte);
			updateBillMasters.addAll(billMasData);

			result = billMasterService.updateBillData(updateBillMasters, amount.doubleValue(), details, billDetails, org, null,
					 manualReceptDate);

			final List<RLBillMaster> newRlBill = rLBillMasterRepository.findByAllContIdAndOrgId(Long.valueOf(contNo), orgid);
			Collections.sort(newRlBill, RLBillMaster.sortByIntrest);
			for (final RLBillMaster rlBillMaster : newRlBill) {

				for (final TbBillMas tbWtBillMas2 : updateBillMasters) {
					if (tbWtBillMas2.getBmIdno() == rlBillMaster.getBillId()) {
						rlBillMaster.setBalanceAmount(tbWtBillMas2.getBmTotalBalAmount());
						rlBillMaster.setPaidAmount(rlBillMaster.getAmount() - tbWtBillMas2.getBmTotalBalAmount());
						rlBillMaster.setPaidFlag(tbWtBillMas2.getBmPaidFlag());
						rlBillMaster.setUpdatedDate(new Date());

						break;
					}
				}
			}
		}
		return result;
	}

	@Override
	public void save(final List<RLBillMaster> billMasters) {
		rLBillMasterRepository.save(billMasters);
	}

	private String getJsonString(final Object responseObject) {

		@SuppressWarnings("unchecked")
		final LinkedHashMap<Long, Object> outPutObject = (LinkedHashMap<Long, Object>) responseObject;
		final String jsonString = new JSONObject(outPutObject).toString();
		return jsonString;

	}

	private WSResponseDTO getTaxPercentage(final RNLRateMaster rate, final long orgid) {
		final WSRequestDTO dto = new WSRequestDTO();
		dto.setDataModel(rate);
		final WSResponseDTO applicableTaxes = RestClient.callBRMS(dto,ServiceEndpoints.BRMSMappingURL.RNL_TAX_PERCENTAGE_URI);
		return applicableTaxes;
	}

	@Override
	public boolean saveAdvancePayment(Long orgId, Double amount, String uniqueId, Long userId, Long receiptId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean revertBills(TbServiceReceiptMasBean feedetailDto, Long userId, String ipAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TbBillMas> fetchListOfBillsByPrimaryKey(List<Long> uniquePrimaryKey, Long orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateAccountPostingFlag(List<Long> bmIdNo, String flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TbBillMas> fetchCurrentBill(String uniqueSearchNumber, Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbBillMas> updateAdjustedCurrentBill(List<TbBillMas> bill) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getApplicantUserNameModuleWise(long orgId, String uniqueKey) {
		// here uniqueKey is contract Id
		List<ContractPart2DetailEntity> contractPart2List = reportSummaryRepository
				.getContractPart2DataByIds(Long.valueOf(uniqueKey), orgId);
		String applicantName = "";
		for (ContractPart2DetailEntity data : contractPart2List) {
			if (data.getContp2Type().equals("V") && data.getContp2Primary().equals("Y")) {
				applicantName = ApplicationContextProvider.getApplicationContext().getBean(TbAcVendormasterService.class)
						.getVendorNameById(data.getVmVendorid(), orgId);
				break;
			}
		}
		return applicantName;
	}

	@Override
	public VoucherPostDTO reverseBill(TbServiceReceiptMasBean feedetailDto, Long orgId, Long userId, String ipAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonChallanDTO getDataRequiredforRevenueReceipt(CommonChallanDTO offlineMaster, Organisation orgnisation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void updateRLBillMas(RLBillMaster rlBillMaster) {
		rlBillMaster.setPaymentDate(new Date());
		rlBillMaster.setPaidFlag(MainetConstants.Common_Constant.YES);
		rlBillMaster.setRemarks("Bill Payment");
		rlBillMaster.setPartialPaidFlag(MainetConstants.CommonConstants.N);
		rlBillMaster.setBmType(MainetConstants.STATUS.INACTIVE);
		rLBillMasterRepository.save(rlBillMaster);
	}

	@Transactional
	public void updateRLPartialBillMas(RLBillMaster rlBillMaster) {
		rlBillMaster.setPaymentDate(new Date());
		rlBillMaster.setPartialPaidFlag(MainetConstants.CommonConstants.P);
		// rlBillMaster.setPaidFlag("P");
		rlBillMaster.setRemarks("Partial Bill Payment");
		// rlBillMaster.setBmType(MainetConstants.STATUS.INACTIVE);
		rLBillMasterRepository.save(rlBillMaster);
	}

//	@Transactional
//	public void updateRLBillMasPenalty(EstateContractMapping estcontmapping) {
//		estateContractMappingRepository.save(estcontmapping);
//	}

	@Override
	public ContractAgreementSummaryDTO getReceiptAmountDetailsForBillPayment(Long contId,
			ContractAgreementSummaryDTO contractAgreementSummaryDTO, Long orgId) {

		final Organisation orgnisation = new Organisation();
		orgnisation.setOrgid(orgId);
		List<RLBillMaster> rlBillMasters;
		if(Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_TSCL) ) {
			 rlBillMasters = rLBillMasterRepository.findUnpaidBills(
	                contractAgreementSummaryDTO.getContId(),orgId, MainetConstants.CommonConstants.N, MainetConstants.CommonConstants.B);
		}
		else {
			 rlBillMasters = rLBillMasterRepository.findByContIdAndOrgIdAndPaidFlagAndBmTypeOrderByBillIdAsc(
	                contractAgreementSummaryDTO.getContId(),orgId, MainetConstants.CommonConstants.N, MainetConstants.CommonConstants.B);
		}

		

		BigDecimal balanceAmount = new BigDecimal(0.00);
		// U#39694
		BigDecimal sumOfInstallmentAmt = new BigDecimal(0.00);
		BigDecimal sumOfCurrentAmt = new BigDecimal(0.00);
		BigDecimal sumOfArrearsAmt = new BigDecimal(0.00);
		BigDecimal arrearsAmt = new BigDecimal(0.00);
		BigDecimal arrearsAndCurrentAmt = new BigDecimal(0.00);
		BigDecimal arrearsCurrentAndPenaltyAmt = new BigDecimal(0.00);
		BigDecimal arrearsAlreadyPaidAmount = new BigDecimal(0.00);
		BigDecimal currentAmtAlreadyPaid = new BigDecimal(0.00);
		BigDecimal penlatycharge = new BigDecimal(0.00);

		Double overdueAmount = 0d;
		if(Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_PSCL)){
			ContractAgreementSummaryDTO penlatychargeDTO = findPenaltyChargeList(rlBillMasters, orgnisation);
			for (final ChargeDetailDTO charge : penlatychargeDTO.getChargeList()) {
				 penlatycharge = BigDecimal.valueOf(charge.getChargeAmount());
			 } 
		}
		if (Utility.isEnvPrefixAvailable(orgnisation, MainetConstants.ENV_TSCL)) {
			for (final RLBillMaster rlBillMastersDetail : rlBillMasters) {
				sumOfInstallmentAmt = sumOfInstallmentAmt.add(BigDecimal.valueOf(rlBillMastersDetail.getAmount()));
				SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMATS);
				Date dueDate = rlBillMastersDetail.getDueDate();
				try {

					dueDate = dateFormat.parse(dateFormat.format(dueDate));
				} catch (ParseException e) {
					e.printStackTrace();

				}

				Long currentYear = iFinancialYearService.getFinanceYearId(new Date());
				Long billYear = 0L;
				if (dueDate.before(new Date())) {
					logger.info("Due Date -----> " + rlBillMastersDetail.getDueDate());
					billYear = iFinancialYearService.getFinanceYearId(rlBillMastersDetail.getDueDate());
					logger.info("billYear ------> " + billYear);

				}
				if ((billYear.equals(currentYear)) && (dueDate.before(new Date()))) {
					sumOfCurrentAmt = sumOfCurrentAmt.add(BigDecimal.valueOf(rlBillMastersDetail.getBalanceAmount()));

					Boolean paid = rLBillMasterRepository.checkInstallmentPaidOrNot(rlBillMastersDetail.getConitId(),
							rlBillMastersDetail.getOrgId());
					if (paid) {
						// arrears bill Amt Already paid add
						if(rlBillMastersDetail.getPaidAmount()!=null) {
							currentAmtAlreadyPaid  =currentAmtAlreadyPaid.add(BigDecimal.valueOf(rlBillMastersDetail.getPaidAmount()));
						}					
					}
				}
				if ((currentYear > billYear) && (billYear != 0)) {
					arrearsAmt = arrearsAmt.add(BigDecimal.valueOf(rlBillMastersDetail.getBalanceAmount()));
					Boolean paid = rLBillMasterRepository.checkInstallmentPaidOrNot(rlBillMastersDetail.getConitId(),
							rlBillMastersDetail.getOrgId());
					if (paid) {
						// arrears bill Amt Already paid add
						if(rlBillMastersDetail.getPaidAmount()!=null) {
							arrearsAlreadyPaidAmount  =arrearsAlreadyPaidAmount.add(BigDecimal.valueOf(rlBillMastersDetail.getPaidAmount()));
						}
					}
				}

			}
		} else {
			for (final RLBillMaster rlBillMastersDetail : rlBillMasters) {
				sumOfInstallmentAmt = sumOfInstallmentAmt.add(BigDecimal.valueOf(rlBillMastersDetail.getAmount()));
				Date dueDate = rlBillMastersDetail.getDueDate();
				Calendar calCurrent = Calendar.getInstance();
				// calCurrent.add(Calendar.DATE, 15);
				SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMATS);
				Date currentDate = calCurrent.getTime();
				Date endDate = DateUtils.addMonths(new Date(), 1);


				try {
					currentDate = dateFormat.parse(dateFormat.format(currentDate));
					endDate = dateFormat.parse(dateFormat.format(endDate));
					dueDate = dateFormat.parse(dateFormat.format(dueDate));
				} catch (ParseException e) {
					e.printStackTrace();

				}
				if ((currentDate.compareTo(dueDate) <= 0 && endDate.compareTo(dueDate) > 0)) {
					sumOfCurrentAmt = sumOfCurrentAmt.add(BigDecimal.valueOf(rlBillMastersDetail.getBalanceAmount()));

					Boolean paid = rLBillMasterRepository.checkInstallmentPaidOrNot(rlBillMastersDetail.getConitId(),
							rlBillMastersDetail.getOrgId());
					if (paid) {
						// arrears bill Amt Already paid add
						if(rlBillMastersDetail.getPaidAmount()!=null) {
							currentAmtAlreadyPaid  =currentAmtAlreadyPaid.add(BigDecimal.valueOf(rlBillMastersDetail.getPaidAmount()));
						}
					}
				}
				if ((currentDate.compareTo(dueDate) > 0)) {
					arrearsAmt = arrearsAmt.add(BigDecimal.valueOf(rlBillMastersDetail.getBalanceAmount()));
					Boolean paid = rLBillMasterRepository.checkInstallmentPaidOrNot(rlBillMastersDetail.getConitId(),
							rlBillMastersDetail.getOrgId());
					if (paid) {
						// arrears bill Amt Already paid add
						if(rlBillMastersDetail.getPaidAmount()!=null) {
							arrearsAlreadyPaidAmount  =arrearsAlreadyPaidAmount.add(BigDecimal.valueOf(rlBillMastersDetail.getPaidAmount()));
						}
						
					}
				}
				if (currentDate.before(dueDate)) {
					// before date
				} else if (currentDate.equals(dueDate)) {
					// equal date
				} else {
					// after date
					// due date is bigger so add amount in here
					Boolean paid = rLBillMasterRepository.checkInstallmentPaidOrNot(rlBillMastersDetail.getConitId(),
							rlBillMastersDetail.getOrgId());
					if (!paid) {
						// overdue AMT add
						overdueAmount += rlBillMastersDetail.getBalanceAmount();
					}
				}

			}
		}

		/*
		 * for (final ContractInstalmentDetailEntity contractInstalmentDetailEntity :
		 * contractInstalmentDetailEntities) { sumOfInstallmentAmt =
		 * sumOfInstallmentAmt.add(BigDecimal.valueOf(contractInstalmentDetailEntity.
		 * getConitAmount())); Date dueDate =
		 * contractInstalmentDetailEntity.getConitDueDate(); Calendar calCurrent =
		 * Calendar.getInstance(); // calCurrent.add(Calendar.DATE, 15);
		 * SimpleDateFormat dateFormat = new
		 * SimpleDateFormat(MainetConstants.DATE_FORMATS); Date currentDate =
		 * calCurrent.getTime(); Date endDate = DateUtils.addMonths(new Date(), 1);
		 * 
		 * 
		 * try { currentDate = dateFormat.parse(dateFormat.format(currentDate)); endDate
		 * = dateFormat.parse(dateFormat.format(endDate)); dueDate =
		 * dateFormat.parse(dateFormat.format(dueDate)); } catch (ParseException e) {
		 * e.printStackTrace();
		 * 
		 * } if((currentDate.compareTo(dueDate)<=0 && endDate.compareTo(dueDate)>0)) {
		 * sumOfCurrentAmt =
		 * sumOfCurrentAmt.add(BigDecimal.valueOf(contractInstalmentDetailEntity.
		 * getConitAmount()));
		 * 
		 * Boolean paid = rLBillMasterRepository.checkInstallmentPaidOrNot(
		 * contractInstalmentDetailEntity.getConitId(),
		 * contractInstalmentDetailEntity.getOrgId()); if (paid) { // arrears bill Amt
		 * Already paid add currentAmtAlreadyPaid
		 * =currentAmtAlreadyPaid.add(BigDecimal.valueOf(contractInstalmentDetailEntity.
		 * getConitAmount())); } } if((currentDate.compareTo(dueDate)>0)) { arrearsAmt =
		 * arrearsAmt.add(BigDecimal.valueOf(contractInstalmentDetailEntity.
		 * getConitAmount())); Boolean paid =
		 * rLBillMasterRepository.checkInstallmentPaidOrNot(
		 * contractInstalmentDetailEntity.getConitId(),
		 * contractInstalmentDetailEntity.getOrgId()); if (paid) { // arrears bill Amt
		 * Already paid add arrearsAlreadyPaidAmount
		 * =arrearsAlreadyPaidAmount.add(BigDecimal.valueOf(
		 * contractInstalmentDetailEntity.getConitAmount())); } } if
		 * (currentDate.before(dueDate)) { // before date } else if
		 * (currentDate.equals(dueDate)) { // equal date } else { // after date // due
		 * date is bigger so add amount in here Boolean paid =
		 * rLBillMasterRepository.checkInstallmentPaidOrNot(
		 * contractInstalmentDetailEntity.getConitId(),
		 * contractInstalmentDetailEntity.getOrgId()); if (!paid) { // overdue AMT add
		 * overdueAmount += contractInstalmentDetailEntity.getConitAmount(); } }
		 * 
		 * }
		 */

		// end for loop

		// make balance AMT by how much already paid
		final List<RLBillMaster> billMasters = finByContractId(contractAgreementSummaryDTO.getContId(),
				 orgId,MainetConstants.CommonConstants.Y, MainetConstants.FlagI);
		// paid amount iterate
		Double alreadyPaid = 0d;
		for (RLBillMaster rlBill : billMasters) {
			alreadyPaid += rlBill.getPaidAmount();
		}
		// U#39694
		// balanceAmount = contractAgreementSummaryDTO.getContAmount().subtract(BigDecimal.valueOf(alreadyPaid));
		balanceAmount = sumOfInstallmentAmt.subtract(BigDecimal.valueOf(alreadyPaid));
		contractAgreementSummaryDTO.setSumOfCurrentAmt(sumOfCurrentAmt.subtract(currentAmtAlreadyPaid));
		arrearsAmt = arrearsAmt.subtract(arrearsAlreadyPaidAmount);
		arrearsAndCurrentAmt = arrearsAmt.add(sumOfCurrentAmt);	
		arrearsCurrentAndPenaltyAmt = arrearsAndCurrentAmt.add(penlatycharge);
		// arrearsAmt=balanceAmount.subtract(sumOfCurrentAmt);
		contractAgreementSummaryDTO.setPenalty(penlatycharge);
		contractAgreementSummaryDTO.setArrearsCurrentAndPenaltyAmt(arrearsCurrentAndPenaltyAmt);
		contractAgreementSummaryDTO.setArrearsAndCurrentAmt(arrearsAndCurrentAmt);
		contractAgreementSummaryDTO.setBalanceAmount(balanceAmount);
		contractAgreementSummaryDTO.setOverdueAmount(BigDecimal.valueOf(overdueAmount));
		contractAgreementSummaryDTO.setArrearsAmt(arrearsAmt);
		return contractAgreementSummaryDTO;
	}

	private ContractAgreementSummaryDTO findPenaltyChargeList(List<RLBillMaster> rlBillMasters, Organisation orgnisation) {
		ContractAgreementSummaryDTO contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
		WSResponseDTO response = initializeModelForTax();
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.ChargeApplicableAt.RECEIPT,
				PrefixConstants.NewWaterServiceConstants.CAA, orgnisation);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> rnlRateMasterList = RestClient.castResponse(response, RNLRateMaster.class, 0);
			final RNLRateMaster rnlRateMaster = (RNLRateMaster) rnlRateMasterList.get(0);
			rnlRateMaster.setOrgId(orgnisation.getOrgid());
			rnlRateMaster.setDeptCode(MainetConstants.RnLCommon.RL_SHORT_CODE);
			rnlRateMaster.setOrgId(orgnisation.getOrgid());
			rnlRateMaster.setServiceCode(MainetConstants.EstateContract.CBP);
			rnlRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
			WSRequestDTO taxReqDTO = new WSRequestDTO();
			taxReqDTO.setDataModel(rnlRateMaster);
			final WSResponseDTO taxResponseDTO = brmsRNLService.getApplicableTaxes(taxReqDTO);
			if (taxResponseDTO.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {
				if (!taxResponseDTO.isFree()) {
					final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
					final List<RNLRateMaster> requiredCHarges = new ArrayList<>();
					for (final Object rate : rates) {
						final RNLRateMaster master1 = (RNLRateMaster) rate;
						master1.setOrgId(orgnisation.getOrgid());
						master1.setServiceCode(MainetConstants.EstateContract.CBP);
						master1.setDeptCode(MainetConstants.RNL_DEPT_CODE);
						master1.setChargeApplicableAt(
								CommonMasterUtility.findLookUpDesc(PrefixConstants.LookUpPrefix.CAA,
										orgnisation.getOrgid(), Long.parseLong(rnlRateMaster.getChargeApplicableAt())));
						master1.setTaxSubCategory(getSubCategoryDesc(master1.getTaxSubCategory(), orgnisation));
						requiredCHarges.add(master1);
					}
					WSRequestDTO chargeReqDTO = new WSRequestDTO();
					chargeReqDTO.setDataModel(requiredCHarges);
					WSResponseDTO applicableCharges = brmsRNLService.getPenaltyCharges(chargeReqDTO);
					final List<RNLRateMaster> charges = (List<RNLRateMaster>) applicableCharges.getResponseObj();
					if (charges == null) {
						throw new FrameworkException("Charges not Found in brms Sheet");
					} else {
						Double sums = 0.0;
						int noOfMonths=1;
						List<ChargeDetailDTO> penaltychargelist = new ArrayList<>(0);
						for (final RLBillMaster rlBillMastersDetail : rlBillMasters) {
							try {
								Date dueDate = new SimpleDateFormat(MainetConstants.DATE_FORMATS)
										.parse(rlBillMastersDetail.getDueDate().toString());
								LocalDate frmDateLoc = dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
								Date currentDate = new Date();
								LocalDate toDateLoc = currentDate.toInstant().atZone(ZoneId.systemDefault())
										.toLocalDate();
								if (frmDateLoc.isBefore(toDateLoc)) {
									//Long noOfMonths = ChronoUnit.MONTHS.between(frmDateLoc, toDateLoc) + 1;
									Double sum = 0.0;
									for (final RNLRateMaster charge : charges) {
										if (noOfMonths == charge.getSlab1()) {
											sum += charge.getSlabRate1();
										} else if (noOfMonths == charge.getSlab2()) {
											sum += charge.getSlabRate2();
										} else if ((noOfMonths > charge.getSlab2())
												&& (noOfMonths <= charge.getSlab3())) {
											sum += charge.getSlabRate3();
										} else if ((noOfMonths > charge.getSlab3())) {
											sum += charge.getSlabRate4();
										}
									}
									
									sums += sum;
									noOfMonths++;	

								}

							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						for (final RNLRateMaster charge : charges) {
							ChargeDetailDTO penaltycharge = new ChargeDetailDTO();
							penaltycharge.setChargeCode(charge.getTaxId());
							penaltycharge.setChargeAmount(sums);
							penaltychargelist.add(penaltycharge);
						}
						contractAgreementSummaryDTO.setChargeList(penaltychargelist);
					}
				}
			}
		}
		return contractAgreementSummaryDTO;

	}
	
	@Override
	public List<ContractInstalmentDetailDTO> findAdjRecords(Long contId, String yFlag, String string) {
		List<ContractInstalmentDetailEntity> contractInstalmentDetailList = rLBillMasterRepository.findAllAdjRecords(contId, MainetConstants.RnLCommon.Y_FLAG,"A");
		List<ContractInstalmentDetailDTO> AdjustmentDetaiList= new ArrayList<>();
		ContractInstalmentDetailDTO AdjustmentDetail;
		for(ContractInstalmentDetailEntity contractInstalmentDetail: contractInstalmentDetailList) {
			AdjustmentDetail = new ContractInstalmentDetailDTO();
			BeanUtils.copyProperties(contractInstalmentDetail, AdjustmentDetail);
			AdjustmentDetail.setContAmt(contractInstalmentDetail.getConitAmount());
			AdjustmentDetail.setActive(contractInstalmentDetail.getConttActive());
			AdjustmentDetaiList.add(AdjustmentDetail);
		}
		return AdjustmentDetaiList;
	}

	@Override
	public TbSrcptModesDetBean getDishonorCharges(Long deptId, Organisation org, TbSrcptModesDetBean chequeModeDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean revertBills(TbSrcptModesDetBean feedetailDto, Long userId, String ipAdress) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CommonChallanDTO getBillDetails(CommonChallanDTO commonChallanDTO) {
		// TOLD by MAYANK
		return commonChallanDTO;
	}

	@Override
	public List<TbBillMas> fetchBillsListByBmId(List<Long> uniquePrimaryKey, Long orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPropNoByOldPropNo(String oldPropNo, Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getBalanceAmountByContractId(Long contId, Long orgId, String flag) {

		Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_TSCL)) {
			return rLBillMasterRepository.getBalanceAmountByContractIdDate(contId, orgId, flag);

		} else {
			return rLBillMasterRepository.getBalanceAmountByContractId(contId, orgId, flag);
		}

	}

	@Override
	public Double getTotalAmountByContractId(Long contId, Long orgId, String flag) {
		return rLBillMasterRepository.getTotalAmountByContractId(contId, orgId,flag);
	}

	@Override
	@Transactional
	public void updateRLBillMaster(Long contId,String status, Date suspendDate, Long empId, Long orgId) {
		rLBillMasterRepository.updateSuspendDate(contId, status, suspendDate, empId, orgId);
	}
	
	@Override
	public List<RLBillMaster> finByContractIdPaidFlag(final Long contId, final Long orgId, String paidFlag, String bmType) {
		return rLBillMasterRepository.findUnpaidBills(contId, orgId, paidFlag, bmType);
	}
	
	
}
