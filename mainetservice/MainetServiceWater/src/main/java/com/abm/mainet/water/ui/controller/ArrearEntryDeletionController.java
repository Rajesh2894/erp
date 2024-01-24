package com.abm.mainet.water.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.water.dto.BillingScheduleDto;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbWtBillSchedule;
import com.abm.mainet.water.dto.TbWtBillScheduleDetail;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;
import com.abm.mainet.water.service.TbWtBillMasService;
import com.abm.mainet.water.service.TbWtBillScheduleService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.ui.model.ArrearEntryDeletionModel;

@Controller
@RequestMapping("/ArrearEntryDeletion.html")
public class ArrearEntryDeletionController extends AbstractFormController<ArrearEntryDeletionModel> {

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private TbWtBillScheduleService tbWtBillScheduleService;

	@Autowired
	private TbWtBillMasService tbWtBillMasService;

	@Autowired
	private WaterCommonService waterCommonService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private IFinancialYearService iFinancialYearService;

	 @Resource
	private TbFinancialyearService financialyearService;
	 
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		bindModel(httpServletRequest);
		  this.getModel().setFinYearMasterList(iFinancialYearService.getAllFinincialYear());
	        
	        final List<FinancialYear> finYearList = financialyearService
					.findAllFinYear();

			String financialYear = null;
			for (final FinancialYear finYearTemp : finYearList) {
				try {
					financialYear = Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate());
					this.getModel().getFinYearData().put(finYearTemp.getFaYear(), financialYear);
				} catch (final Exception e) {
					//LOGGER.error("error in finYear list", e);
				}
			}
		return index();

	}

	@RequestMapping(params = "search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam(value = "csCcn") String csCcn, @RequestParam(value = "finId") Long finId, @RequestParam(value = "billNo") Long billNo, final HttpServletRequest httpServletRequest)
			throws Exception {
		ArrearEntryDeletionModel model = this.getModel();
		ModelAndView mv = null;
		model.bind(httpServletRequest);
		final Department dept = departmentService.getDepartment(MainetConstants.WATER_DEPARTMENT_CODE,
				MainetConstants.STATUS.ACTIVE);
		getModel().setDeptId(dept.getDpDeptid());
		Organisation org = new Organisation(UserSession.getCurrent().getOrganisation().getOrgid());
		

		List<TbBillMas> billList = null;
		if (csCcn != null && !csCcn.isEmpty()) {
			TbCsmrInfoDTO entity = waterCommonService.fetchConnectionDetailsByConnNo(csCcn, org.getOrgid(), "A");
			if (entity == null) {
				model.addValidationError(
						ApplicationSession.getInstance().getMessage("water.arrear.validmsg"));
				mv = new ModelAndView("ArrearEntryDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

				return mv;

			}
			Long finYearId = 0L;
			if(finId!=null){
				finYearId = finId;
				logger.info("logger fin year is" + finYearId);
			}
			model.setCsmrInfo(entity);
			List<TbWtBillSchedule> currentBillSchedule = null;
			if(entity.getCsMeteredccn() != null && entity.getCsMeteredccn() > 0) {
				final LookUp meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getCsMeteredccn(),
	                    org);
				 currentBillSchedule = ApplicationContextProvider.getApplicationContext().getBean(TbWtBillScheduleService.class)
						.getBillScheduleByFinYearId(finYearId, UserSession.getCurrent().getOrganisation().getOrgid(),
								meterType.getLookUpCode());
			}
			 LookUp billFrequencyLookUp = null;
			if(currentBillSchedule != null && !currentBillSchedule.isEmpty()) {
				Long billFrequencyId = currentBillSchedule.get(0).getCnsCpdid();
				billFrequencyLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billFrequencyId,
	                    org);
			}
			Long arrearBillCountExcludCurrBill = 0L;
			if(billFrequencyLookUp != null && StringUtils.equals(billFrequencyLookUp.getLookUpCode(), String.valueOf(1))) {
				 arrearBillCountExcludCurrBill = ApplicationContextProvider.getApplicationContext().getBean(TbWtBillMasJpaRepository.class)
						.getArrearsDeletionCurrentBillCount(entity.getCsIdn(), finYearId,
								UserSession.getCurrent().getOrganisation().getOrgid(), new Date());
			}else {
				billList = tbWtBillMasService.getArrearsDeletionBills(entity.getCsIdn(), finYearId);
				logger.info("logger curr fin year is" + finYearId);
				logger.info("logger billList:" + billList);
			}
			 LookUp currentBillDelLookUp = null;
		        try {
		        	currentBillDelLookUp = CommonMasterUtility.getValueFromPrefixLookUp("CBD", "WEV", UserSession.getCurrent().getOrganisation());
		        }catch (Exception e) {
		        	
				}
		        if ((currentBillDelLookUp != null && StringUtils.isNotBlank(currentBillDelLookUp.getOtherField())
						&& StringUtils.equals(currentBillDelLookUp.getOtherField(), MainetConstants.FlagY))
						|| (billFrequencyLookUp != null
								&& StringUtils.equals(billFrequencyLookUp.getLookUpCode(), String.valueOf(1))
								&& arrearBillCountExcludCurrBill <= 0)
						|| (billFrequencyLookUp != null && !StringUtils.equals(billFrequencyLookUp.getLookUpCode(), "1")
								&& CollectionUtils.isEmpty(billList))) {
				model.setBillMasList(billList);
				BigDecimal totalAmount = BigDecimal.ZERO;
				final List<LookUp> monthlookup = CommonMasterUtility.getListLookup(
						PrefixConstants.WATERMODULEPREFIX.MONTH, UserSession.getCurrent().getOrganisation());

				for (final LookUp mon : monthlookup) {
				getModel().getMonthprefix().put(Integer.valueOf(mon.getLookUpCode()), mon.getLookUpDesc());
				}
				String billFrequency = null;
				if(billFrequencyLookUp != null) {
					billFrequency = billFrequencyLookUp.getLookUpCode();
					logger.info("logger bill frequency is" + billFrequency);
				}
				if (currentBillDelLookUp != null && StringUtils.isNotBlank(currentBillDelLookUp.getOtherField())
						&& StringUtils.equals(currentBillDelLookUp.getOtherField(), MainetConstants.FlagY)) {
				//	finYearId = 0L;

				}
				logger.info("logger fin year is" + finYearId);
				List<TbBillMas> arrearsDeletionBillsForNonCurrFinYear = tbWtBillMasService
						.getArrearsDeletionBillsForNonCurrFinYear(entity.getCsIdn(), finYearId, billFrequency,
								UserSession.getCurrent().getOrganisation().getOrgid(),billNo);
				Object[] ob = null;
	   			ob = iFinancialYearService.getFinacialYearByDate(new Date());
	   			long currYear = (long)ob[0];
				if((!billList.isEmpty()) && finYearId.equals(currYear)) {
				  arrearsDeletionBillsForNonCurrFinYear.add(billList.get(0));
				  logger.info("logger to add current bill in noncurrent");
				}
			//	  logger.info("logger to add current bill in noncurrent");
				if (CollectionUtils.isNotEmpty(arrearsDeletionBillsForNonCurrFinYear)) {
					List<TbServiceReceiptMasEntity> receiptList = ApplicationContextProvider
							.getApplicationContext().getBean(ReceiptRepository.class)
							.getCollectionDetails(String.valueOf(entity.getCsIdn()), dept.getDpDeptid(),
									UserSession.getCurrent().getOrganisation().getOrgid());
					
					if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SUDA)) {
						List<TbBillMas> completelyUnpaidBills = new ArrayList<>();
						completelyUnpaidBills.addAll(arrearsDeletionBillsForNonCurrFinYear);
						
						Optional<TbBillMas> partiallyPaidBill = null;
						if(CollectionUtils.isNotEmpty(receiptList)) {
							for(TbServiceReceiptMasEntity receipt: receiptList) {
								for(TbSrcptFeesDetEntity receiptDet : receipt.getReceiptFeeDetail()) {
									if(receiptDet.getBmIdNo()!=null){
										partiallyPaidBill = arrearsDeletionBillsForNonCurrFinYear.stream().filter(
												arrear->arrear.getBmIdno()==receiptDet.getBmIdNo()).findAny();
										if(partiallyPaidBill!=null && partiallyPaidBill.isPresent()) {
											completelyUnpaidBills.remove(partiallyPaidBill.get());
										}	
									}
								}
							}
						}
						arrearsDeletionBillsForNonCurrFinYear.clear();
						arrearsDeletionBillsForNonCurrFinYear.addAll(completelyUnpaidBills);
						logger.info("arrearsDeletionBillsForNonCurrFinYear -> "+ (arrearsDeletionBillsForNonCurrFinYear!=null ? 
								arrearsDeletionBillsForNonCurrFinYear.size() : arrearsDeletionBillsForNonCurrFinYear));
					}

					boolean ulbSpecificCheck = Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SUDA) ? true: CollectionUtils.isEmpty(receiptList);
					if(ulbSpecificCheck) {
						for (TbBillMas billMas : arrearsDeletionBillsForNonCurrFinYear) {
							final String faYearString = Utility.getFinancialYearFromDate(billMas.getBmFromdt());
							Date fromDate = billMas.getBmFromdt();
							Calendar c = Calendar.getInstance();
							c.setTime(fromDate);
							int startMonth = c.get(Calendar.MONTH) + 1;
							Date endDate = billMas.getBmFromdt();
							c.setTime(endDate);
							int endMonth = c.get(Calendar.MONTH) + 1;
							String value = faYearString + "->" + getModel().getMonthprefix().get(startMonth) + " - "
									+ getModel().getMonthprefix().get(endMonth);
							billMas.setBmGenDes(value);
							BigDecimal arrearAmount = BigDecimal.valueOf(billMas.getBmLastRcptamt());
							totalAmount = totalAmount.add(arrearAmount);
						}
						String meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getCsMeteredccn(), org)
								.getLookUpCode();
						NewWaterConnectionReqDTO connectionDTO = model.getNewConnectionDto();
						TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
						connectionDTO.setCsmrInfo(infoDTO);

						TbCsmrInfoDTO waterDTO = getModel().getCsmrInfo();
						List<String> years = Utility.getNoOfFinancialYearIncludingCurrent(6);

						String[] year = years.get(5).split("-");
						Calendar c = Calendar.getInstance();
						c.setTime(new Date());
						c.set(Calendar.YEAR, Integer.parseInt(year[0]));
						c.set(Calendar.MINUTE, 0);
						c.set(Calendar.SECOND, 0);
						c.set(Calendar.HOUR_OF_DAY, 0);

						Calendar pc = Calendar.getInstance();
						pc.setTime(waterDTO.getPcDate());
						pc.set(Calendar.MINUTE, 0);
						pc.set(Calendar.SECOND, 0);
						pc.set(Calendar.HOUR_OF_DAY, 0);

						Date scheduleDate = null;
						if (UtilityService.compareDateField(c.getTime(), pc.getTime())) {
							scheduleDate = pc.getTime();
						} else {
							scheduleDate = c.getTime();
						}
						List<TbWtBillSchedule> tbWtBillScheduleEntity = tbWtBillScheduleService
								.getBillScheduleFromToYear(scheduleDate, org.getOrgid(), meterType, new Date());
						setScheduleAndBillMas(model, arrearsDeletionBillsForNonCurrFinYear, meterType, tbWtBillScheduleEntity);
					}else {
						model.addValidationError(ApplicationSession.getInstance().getMessage("Bill payment have been done. Cannot delete bills"));
						mv = new ModelAndView("ArrearEntryDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
						mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
								getModel().getBindingResult());
						return mv;
					}
				} else {
					// No records found to delete
					model.addValidationError(ApplicationSession.getInstance().getMessage("water.arrear.noRecord"));
					mv = new ModelAndView("ArrearEntryDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							getModel().getBindingResult());
					return mv;
				}
			} else {
				// not eligible
				model.addValidationError(ApplicationSession.getInstance().getMessage(
						"water.validMsg.billGenerated"));
				mv = new ModelAndView("ArrearEntryDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}
		} else {
			// validate connection number empty

			model.addValidationError(ApplicationSession.getInstance().getMessage("water.arrear.validconnNo"));
			return mv;
		}

		return new ModelAndView("ArrearEntryDeletionSearch", MainetConstants.FORM_NAME, getModel());
	}

	private void setScheduleAndBillMas(ArrearEntryDeletionModel model, List<TbBillMas> billList, String meterType,
			List<TbWtBillSchedule> tbWtBillScheduleEntity) throws Exception {
		Map<Integer, String> monthprefix = getModel().getMonthprefix();
		Organisation org = new Organisation(UserSession.getCurrent().getOrganisation().getOrgid());

		getModel().setBillMasList(new ArrayList<>(0));
		getModel().setSchedule(new ArrayList<>(0));
		getModel().setSortedschedule(new ArrayList<>());
		TbCsmrInfoDTO waterDTO = getModel().getCsmrInfo();

		setBillingSchedule(org, waterDTO, monthprefix, tbWtBillScheduleEntity, "N");

		BillingScheduleDto scheduleDTO = new BillingScheduleDto();
		scheduleDTO.setCnsMn(meterType);
		scheduleDTO.setCodIdWwz1(waterDTO.getCodDwzid1());
		scheduleDTO.setCodIdWwz2(waterDTO.getCodDwzid2());
		scheduleDTO.setCodIdWwz3(waterDTO.getCodDwzid3());
		scheduleDTO.setCodIdWwz4(waterDTO.getCodDwzid4());
		scheduleDTO.setCodIdWwz5(waterDTO.getCodDwzid5());
		scheduleDTO.setOrgid(waterDTO.getOrgId());
		scheduleDTO.setCnsCcgid1(waterDTO.getCsCcncategory1());

		tbWtBillScheduleEntity = tbWtBillScheduleService.searchBillingDataWithScheduleDetails(scheduleDTO,
				waterDTO.getOrgId());

		if (billList != null && !billList.isEmpty()) {
			Long taxSubCategoryId = setTaxSubCategoryForMeterAndNonMeter(org, meterType);
			final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
					PrefixConstants.NewWaterServiceConstants.BILL, PrefixConstants.NewWaterServiceConstants.CAA,
					UserSession.getCurrent().getOrganisation());
			
			final LookUp chargeApplicableAtBillReceipt = CommonMasterUtility.getValueFromPrefixLookUp(
					PrefixConstants.NewWaterServiceConstants.BILL_RECEIPT, PrefixConstants.NewWaterServiceConstants.CAA,
					UserSession.getCurrent().getOrganisation());
			final List<TbTaxMas> taxMaster = new ArrayList<>(0);
			// changes related to Defect #17462 For old connection numbers, inactive tax
			// names are not visible under arrears entry of data entry
			/*
			 * taxMaster.addAll(
			 * tbTaxMasService.fetchAllIndependentTaxes(UserSession.getCurrent().
			 * getOrganisation().getOrgid(), getModel().getDeptId(),
			 * chargeApplicableAt.getLookUpId(), taxSubCategoryId)); taxMaster
			 * .addAll(tbTaxMasService.fetchAllDepenentTaxes(UserSession.getCurrent().
			 * getOrganisation().getOrgid(), getModel().getDeptId(),
			 * chargeApplicableAt.getLookUpId(), taxSubCategoryId));
			 */
			taxMaster.addAll(tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), getModel().getDeptId(),
					chargeApplicableAt.getLookUpId()));

			taxMaster.addAll(tbTaxMasService.findAllTaxesForBillPayment(org.getOrgid(), getModel().getDeptId(),
					chargeApplicableAtBillReceipt.getLookUpId()));
			List<TbTaxMas> taxMasterList = new ArrayList<>(0);
			taxMaster.forEach(tax -> {
				String taxCategory1 = CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(), org)
						.getLookUpCode();
				if (!PrefixConstants.TAX_CATEGORY.ADVANCE.equals(taxCategory1)
						&& !PrefixConstants.TAX_CATEGORY.REBATE.equals(taxCategory1)) {
					taxMasterList.add(tax);
				}
			});

			this.getModel().setTaxesMaster(taxMasterList);
			getModel().setBillMasList(billList);
			TbBillMas billMas = billList.get(0);
			Date billFromDate = billMas.getBmFromdt();
			Date billToDate = billMas.getBmTodt();
			boolean startDateflag = false;
			boolean endDateflag = false;
			FinancialYear finYear = iFinancialYearService.getFinincialYearsById(billMas.getBmYear(), org.getOrgid());
			final String faYearString = Utility.getCompleteFinancialYear(finYear.getFaFromDate(),
					finYear.getFaToDate());
			for (TbWtBillSchedule billSchedule : tbWtBillScheduleEntity) {
				FinancialYear billFinYear = iFinancialYearService.getFinincialYearsById(billSchedule.getCnsYearid(),
						org.getOrgid());
				final String billFaYearString = Utility.getCompleteFinancialYear(billFinYear.getFaFromDate(),
						billFinYear.getFaToDate());
				if (billFaYearString.equals(faYearString)) {
					for (TbWtBillScheduleDetail billScheduleDetail : billSchedule.getBillScheduleDetail()) {
						final Date startDate = Utility.dateFromMonth(billFaYearString,
								billScheduleDetail.getCnsFromDate().intValue(), MainetConstants.FINYEAR_DATE.FIRST);
						final Date endDate = Utility.dateFromMonth(billFaYearString,
								billScheduleDetail.getCnsToDate().intValue(), MainetConstants.FINYEAR_DATE.LAST);
						startDateflag = Utility.comapreDates(billFromDate, startDate);
						endDateflag = Utility.comapreDates(billToDate, endDate);
						if (startDateflag && endDateflag) {
							getModel().setSchduleId(billScheduleDetail.getDetId());
						}
					}
				}
			}
		} else {
			getModel().setBillMasList(new ArrayList<>(0));
			getModel().setSchedule(new ArrayList<>(0));
			getModel().setSortedschedule(new ArrayList<>(0));
			getModel().setBillScheduleDetail(new ArrayList<>(0));
			getModel().setSchduleId(0l);
		}
	}

	private void setBillingSchedule(Organisation org, TbCsmrInfoDTO waterDTO, final Map<Integer, String> monthprefix,
			List<TbWtBillSchedule> tbWtBillScheduleEntity, String type) throws Exception {
		if ((tbWtBillScheduleEntity != null) && !tbWtBillScheduleEntity.isEmpty()) {
			long ward1 = -1;
			long ward2 = -1;
			long ward3 = -1;
			long ward4 = -1;
			long ward5 = -1;

			long Schward1 = -1;
			long Schward2 = -1;
			long Schward3 = -1;
			long Schward4 = -1;
			long Schward5 = -1;
			for (final TbWtBillSchedule billsch : tbWtBillScheduleEntity) {
				if (MainetConstants.Common_Constant.NUMBER.ONE.equals(billsch.getDependsOnType())) {
					if ((billsch.getCodIdWwz1() != null) && (billsch.getCodIdWwz1() > -1)) {
						if (waterDTO.getCodDwzid1() != null) {
							ward1 = waterDTO.getCodDwzid1().longValue();
						}
						if ((billsch.getCodIdWwz2() != null) && (billsch.getCodIdWwz2() > -1)) {
							if (waterDTO.getCodDwzid2() != null) {
								ward2 = waterDTO.getCodDwzid2().longValue();
							}
							if ((billsch.getCodIdWwz3() != null) && (billsch.getCodIdWwz3() > -1)) {
								if (waterDTO.getCodDwzid3() != null) {
									ward3 = waterDTO.getCodDwzid3().longValue();
								}
								if ((billsch.getCodIdWwz4() != null) && (billsch.getCodIdWwz4() > -1)) {
									if (waterDTO.getCodDwzid4() != null) {
										ward4 = waterDTO.getCodDwzid4().longValue();
									}
									if ((billsch.getCodIdWwz5() != null) && (billsch.getCodIdWwz5() > -1)) {
										if (waterDTO.getCodDwzid5() != null) {
											ward5 = waterDTO.getCodDwzid5().longValue();
										}
									}
								}
								
							}
						}
					}

					if (billsch.getCodIdWwz1() != null) {
						Schward1 = billsch.getCodIdWwz1().longValue();
					}
					if (billsch.getCodIdWwz2() != null) {
						Schward2 = billsch.getCodIdWwz2().longValue();
					}
					if (billsch.getCodIdWwz3() != null) {
						Schward3 = billsch.getCodIdWwz3().longValue();
					}
					if (billsch.getCodIdWwz4() != null) {
						Schward4 = billsch.getCodIdWwz4().longValue();
					}
					if (billsch.getCodIdWwz5() != null) {
						Schward5 = billsch.getCodIdWwz5().longValue();
					}
					if (((Schward1 == -1) || (ward1 == Schward1)) && ((Schward2 == -1) || (ward2 == Schward2))
							&& ((Schward3 == -1) || (ward3 == Schward3)) && ((Schward4 == -1) || (ward4 == Schward4))
							&& ((Schward5 == -1) || (ward5 == Schward5))) {
						for (final TbWtBillScheduleDetail schDetail : billsch.getBillScheduleDetail()) {
							setSchedule(org, monthprefix, billsch, schDetail, type, waterDTO);
						}
					}
				} else if (MainetConstants.Common_Constant.NUMBER.TWO.equals(billsch.getDependsOnType())) {
					if ((waterDTO.getCsCcncategory1() != null)
							&& waterDTO.getCsCcncategory1().equals(billsch.getCnsCcgid1())) {
						for (final TbWtBillScheduleDetail schDetail : billsch.getBillScheduleDetail()) {
							setSchedule(org, monthprefix, billsch, schDetail, type, waterDTO);
						}
					}
				}
			}
		}

		final List<FinancialYear> finYearAll = iFinancialYearService.getAllFinincialYear();
		Collections.reverse(finYearAll);

		finYearAll.forEach(finyear -> {
			if ("N".equals(type)) {
				getModel().getSchedule().forEach(sch -> {
					if (finyear.getFaYear() == sch.getLookUpParentId()) {
						getModel().getSortedschedule().add(sch);
					}
				});
			}

			/*
			 * else { getModel().getBillScheduleDetail().forEach(sch1 -> { if
			 * (finyear.getFaYear() == sch1.getYearId()) {
			 * 
			 * getModel().getSortedbillSchedule().add(sch1); } }); }
			 */
		});

		List<TbWtBillScheduleDetail> schedule = new ArrayList<>(0);
		getModel().getSortedschedule().forEach(detlookup -> {
			getModel().getBillScheduleDetail().forEach(detSche -> {
				if (detSche.getDetId().equals(detlookup.getLookUpId())) {
					schedule.add(detSche);
				}
			});
		});

		getModel().setBillScheduleDetail(new ArrayList<>());
		getModel().getBillScheduleDetail().addAll(schedule);

	}

	private Long setTaxSubCategoryForMeterAndNonMeter(final Organisation orgnisation, final String meterType) {
		Long taxSubCategoryId = null;
		final List<LookUp> taxSubCategory = CommonMasterUtility.getNextLevelData(PrefixConstants.LookUpPrefix.TAC,
				MainetConstants.INDEX.TWO, orgnisation.getOrgid());
		if (MainetConstants.NewWaterServiceConstants.METER.equals(meterType)) {
			for (final LookUp taxSubCat : taxSubCategory) {
				if (PrefixConstants.TAX_SUBCATEGORY.WATER_TAX_NONMETER.equals(taxSubCat.getLookUpCode())) {
					taxSubCategoryId = taxSubCat.getLookUpId();
				}
			}
		} else {
			for (final LookUp taxSubCat : taxSubCategory) {
				if (PrefixConstants.TAX_SUBCATEGORY.WATER_TAX_METER.equals(taxSubCat.getLookUpCode())) {
					taxSubCategoryId = taxSubCat.getLookUpId();
				}
			}
		}
		return taxSubCategoryId;
	}

	private void setSchedule(Organisation org, final Map<Integer, String> monthprefix, final TbWtBillSchedule billsch,
			final TbWtBillScheduleDetail schDetail, String type, TbCsmrInfoDTO waterDTO) throws Exception {
		FinancialYear finYear = iFinancialYearService.getFinincialYearsById(billsch.getCnsYearid(), org.getOrgid());
		String financialYear = Utility.getFinancialYearFromDate(finYear.getFaFromDate());

		Calendar schDate = Calendar.getInstance();
		schDate.setTime(finYear.getFaFromDate());
		schDate.set(Calendar.MONTH, schDetail.getCnsToDate().intValue() - 1); // January = 0, December = 11

		int schDateValue = Utility.getFinancialFormatedDateFromDate(schDate.getTime(), "yyyyMM");
		int currentDateValue = Utility.getFormatedDateFromDate(Calendar.getInstance().getTime(), "yyyyMM");

		int csmrPcInstFinancialYearWithMonth = Utility.getFormatedDateFromDate(waterDTO.getPcDate(), "yyyyMM");

		String currentFinYear = Utility.getFinancialYearFromDate(new Date());

		if (((financialYear.equals(currentFinYear)) && (schDateValue < currentDateValue)
				&& (csmrPcInstFinancialYearWithMonth <= schDateValue))
				|| ((!financialYear.equals(currentFinYear)) && (schDetail.getCnsFromDate() == 4))) {

			if ((!financialYear.equals(currentFinYear)) && (schDetail.getCnsFromDate() == 4)) {
				schDetail.setCnsToDate(3l);
			}

			LookUp look = new LookUp();
			look.setLookUpId(schDetail.getDetId());
			look.setDescLangFirst(financialYear + "->" + monthprefix.get(schDetail.getCnsFromDate().intValue()) + " - "
					+ monthprefix.get(schDetail.getCnsToDate().intValue()));
			look.setLookUpParentId(finYear.getFaYear());
			schDetail.setYearId(billsch.getCnsYearid());
			getModel().getSchedule().add(look);
			getModel().getBillScheduleDetail().add(schDetail);
		}

	}
	
	@RequestMapping(params = "searchForAligarh", method = RequestMethod.POST)
	public ModelAndView searchForAligarh(@RequestParam(value = "csCcn") String csCcn,@RequestParam(value = "csOldccn") String csOldccn,@RequestParam(value = "finId") Long finId, final HttpServletRequest httpServletRequest)
			throws Exception {
		ArrearEntryDeletionModel model = this.getModel();
		ModelAndView mv = null;
		model.bind(httpServletRequest);
		final Department dept = departmentService.getDepartment(MainetConstants.WATER_DEPARTMENT_CODE,
				MainetConstants.STATUS.ACTIVE);
		getModel().setDeptId(dept.getDpDeptid());
		Organisation org = new Organisation(UserSession.getCurrent().getOrganisation().getOrgid());
		
        // #95890-> Required search option with old connection number for water bill deletion
		if ((csCcn != null && !csCcn.isEmpty()) || (csOldccn != null && StringUtils.isNotEmpty(csOldccn))) {
			TbCsmrInfoDTO entity = waterCommonService.fetchConnectionDetailsByConnNoOrOldConnNo(csCcn,csOldccn, org.getOrgid(), "A");
			
			if (entity == null) {
				model.addValidationError(
						ApplicationSession.getInstance().getMessage("water.arrear.validmsg"));
				mv = new ModelAndView("ArrearEntryDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

				return mv;

			}
			model.setCsmrInfo(entity);
			
				
				BigDecimal totalAmount = BigDecimal.ZERO;
				final List<LookUp> monthlookup = CommonMasterUtility.getListLookup(
						PrefixConstants.WATERMODULEPREFIX.MONTH, UserSession.getCurrent().getOrganisation());

				for (final LookUp mon : monthlookup) {
				getModel().getMonthprefix().put(Integer.valueOf(mon.getLookUpCode()), mon.getLookUpDesc());
				}
				
				
				List<TbBillMas> arrearsDeletionBillsForNonCurrFinYear = tbWtBillMasService
						.getArrearsDeletionBillsByGreaterAndEqualfinId(entity.getCsIdn(), finId,
								UserSession.getCurrent().getOrganisation().getOrgid());
				model.setBillMasList(arrearsDeletionBillsForNonCurrFinYear);
				if (CollectionUtils.isNotEmpty(arrearsDeletionBillsForNonCurrFinYear)) {
					if(!model.checkPayDetail(arrearsDeletionBillsForNonCurrFinYear)) {
						for (TbBillMas billMas : arrearsDeletionBillsForNonCurrFinYear) {
							final String faYearString = Utility.getFinancialYearFromDate(billMas.getBmFromdt());
							Date fromDate = billMas.getBmFromdt();
							Calendar c = Calendar.getInstance();
							c.setTime(fromDate);
							int startMonth = c.get(Calendar.MONTH) + 1;
							Date endDate = billMas.getBmFromdt();
							c.setTime(endDate);
							int endMonth = c.get(Calendar.MONTH) + 1;
							String value = faYearString + "->" + getModel().getMonthprefix().get(startMonth) + " - "
									+ getModel().getMonthprefix().get(endMonth);
							billMas.setBmGenDes(value);
							BigDecimal arrearAmount = BigDecimal.valueOf(billMas.getBmLastRcptamt());
							totalAmount = totalAmount.add(arrearAmount);
						}
						String meterType = CommonMasterUtility.getNonHierarchicalLookUpObject(entity.getCsMeteredccn(), org)
								.getLookUpCode();
						NewWaterConnectionReqDTO connectionDTO = model.getNewConnectionDto();
						TbCsmrInfoDTO infoDTO = model.getCsmrInfo();
						connectionDTO.setCsmrInfo(infoDTO);

						TbCsmrInfoDTO waterDTO = getModel().getCsmrInfo();
						List<String> years = Utility.getNoOfFinancialYearIncludingCurrent(6);

						String[] year = years.get(5).split("-");
						Calendar c = Calendar.getInstance();
						c.setTime(new Date());
						c.set(Calendar.YEAR, Integer.parseInt(year[0]));
						c.set(Calendar.MINUTE, 0);
						c.set(Calendar.SECOND, 0);
						c.set(Calendar.HOUR_OF_DAY, 0);

						Calendar pc = Calendar.getInstance();
						pc.setTime(waterDTO.getPcDate());
						pc.set(Calendar.MINUTE, 0);
						pc.set(Calendar.SECOND, 0);
						pc.set(Calendar.HOUR_OF_DAY, 0);

						Date scheduleDate = null;
						if (UtilityService.compareDateField(c.getTime(), pc.getTime())) {
							scheduleDate = pc.getTime();
						} else {
							scheduleDate = c.getTime();
						}
						List<TbWtBillSchedule> tbWtBillScheduleEntity = tbWtBillScheduleService
								.getBillScheduleFromToYear(scheduleDate, org.getOrgid(), meterType, new Date());
						setScheduleAndBillMas(model, arrearsDeletionBillsForNonCurrFinYear, meterType,
								tbWtBillScheduleEntity);
					}else {
						model.addValidationError(ApplicationSession.getInstance().getMessage("Bill payment have been done. Cannot delete bills"));
						mv = new ModelAndView("ArrearEntryDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
						mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
								getModel().getBindingResult());
						return mv;
					}
				} else {
					// No records found to delete
					model.addValidationError(ApplicationSession.getInstance().getMessage("water.arrear.noRecord"));
					mv = new ModelAndView("ArrearEntryDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							getModel().getBindingResult());
					return mv;
				}
			 
		} else {
			// validate connection number empty

			model.addValidationError(ApplicationSession.getInstance().getMessage("water.arrear.validconnNo"));
			return mv;
		}

		return new ModelAndView("ArrearEntryDeletionSearch", MainetConstants.FORM_NAME, getModel());
	}


}
