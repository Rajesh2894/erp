package com.abm.mainet.water.ui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.TbMrdataJpaRepository;
import com.abm.mainet.water.domain.TbMrdataEntity;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.dto.MeterReadingDTO;
import com.abm.mainet.water.dto.MeterReadingMonthDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbWtBillSchedule;
import com.abm.mainet.water.dto.TbWtBillScheduleDetail;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.TbMrdataService;
import com.abm.mainet.water.service.TbMrdataServiceImpl;
import com.abm.mainet.water.service.TbWtBillScheduleService;
import com.abm.mainet.water.service.WaterDisconnectionService;
import com.abm.mainet.water.service.WaterExceptionalGapService;
import com.abm.mainet.water.ui.model.MeterReadingModel;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/MeterReading.html")
public class MeterReadingController extends AbstractFormController<MeterReadingModel> {

	private static final Logger logger = LoggerFactory.getLogger(MeterReadingController.class);

	@Resource
	IFileUploadService ifileUpload;

	@Autowired
	private TbWtBillScheduleService tbWtBillScheduleService;
	
	@Resource
	private WaterExceptionalGapService waterExceptionalGapService;
	
	@Resource
	private TbMrdataService tbMrDataService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest request) {
		sessionCleanup(request);
		ifileUpload.sessionCleanUpForFileUpload();
		this.getModel().setCommonHelpDocs("MeterReading.html");
		final MeterReadingDTO dto = getModel().getEntityDTO();
		dto.setMeterType(MainetConstants.NewWaterServiceConstants.DUE_DATE_Meter);
		final UserSession session = UserSession.getCurrent();
		dto.setOrgid(session.getOrganisation().getOrgid());
		dto.setUserId(session.getEmployee().getEmpId());
		dto.setLangId(session.getLanguageId());
		final List<TbWtBillSchedule> billSchedule = tbWtBillScheduleService.getBillScheduleByFinYearId(
				Long.valueOf(session.getFinYearId()), dto.getOrgid(), MainetConstants.NewWaterServiceConstants.METER);
		getModel().setBillSchedule(billSchedule);
		if ((billSchedule != null) && !billSchedule.isEmpty()) {
			getModel().setDependsOnType(billSchedule.get(0).getDependsOnType());
		}
		return defaultResult();
	}

	@RequestMapping(method = RequestMethod.POST, params = "searchWaterRecords")
	public ModelAndView searchWaterRecords(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		ifileUpload.sessionCleanUpForFileUpload();
		final MeterReadingModel model = getModel();
		model.setEntityList(null);
		final boolean validate = model.validateSearchCriteria();
		if (validate) {
			
			if(checkBackdatedFinancialBillScheduleActive()) {
				if(model.getEntityDTO().getCsCcn() != null) {
					TbCsmrInfoDTO consumerdto = ApplicationContextProvider.getApplicationContext().getBean(NewWaterConnectionService.class)
							.fetchConnectionDetailsByConnNo(model.getEntityDTO().getCsCcn(),
									UserSession.getCurrent().getOrganisation().getOrgid());
					if(consumerdto != null) {
						TbWtBillMasEntity lastBillMasEntity = ApplicationContextProvider.getApplicationContext().getBean(WaterDisconnectionService.class)
								.getWaterBillDues(consumerdto.getCsIdn(),
										UserSession.getCurrent().getOrganisation().getOrgid());
						if(lastBillMasEntity != null) {
							List<TbWtBillSchedule> billScheduleFromToYear = ApplicationContextProvider.getApplicationContext().getBean(TbWtBillScheduleService.class)
									.getBillScheduleFromToYear(lastBillMasEntity.getBmTodt(),
											UserSession.getCurrent().getOrganisation().getOrgid(),
											MainetConstants.NewWaterServiceConstants.METER, new Date());
							List<TbWtBillSchedule> backDatedBillSchedule = new ArrayList<TbWtBillSchedule>();
							if(billScheduleFromToYear.size() > 1) {
								for (int i = 1; i < billScheduleFromToYear.size(); i++) {
									TbWtBillSchedule billSchedule = new TbWtBillSchedule();
									BeanUtils.copyProperties(billScheduleFromToYear.get(i), billSchedule);
									backDatedBillSchedule.add(billSchedule);
								}
								getModel().setBillSchedule(backDatedBillSchedule);
								if ((billScheduleFromToYear != null) && !billScheduleFromToYear.isEmpty()) {
									getModel().setDependsOnType(billScheduleFromToYear.get(0).getDependsOnType());
								}
							}
						}	
					}
				}
			}
			final boolean result = model.getWaterRecords();
			if (!result) {
				getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));
			}
		}
		return index();
	}

	@RequestMapping(method = RequestMethod.POST, params = "viewDetails")
	public ModelAndView viewDetails(final HttpServletRequest httpServletRequest,
			@RequestParam("id") final Long meterMasId) {
		bindModel(httpServletRequest);
		final MeterReadingModel model = getModel();
		model.getWaterRecordsForViewDetails(meterMasId, UserSession.getCurrent().getOrganisation().getOrgid());
		return new ModelAndView("MeterReadingViewDetail", "command", model);
	}

	@RequestMapping(method = RequestMethod.POST, params = "openBillCycle")
	public ModelAndView openBillCycle(final HttpServletRequest httpServletRequest, @RequestParam("W1") final String wz1,
			@RequestParam("W2") final String wz2, @RequestParam("W3") final String wz3,
			@RequestParam("W4") final String wz4, @RequestParam("W5") final String wz5) {
		bindModel(httpServletRequest);

		if ((getModel().getBillSchedule() != null)
				&& MainetConstants.Common_Constant.NUMBER.ONE.equals(getModel().getDependsOnType())
				&& !MainetConstants.RnLCommon.S_FLAG.equals(getModel().getEntityDTO().getMeterType())) {
			final Map<Integer, String> monthprefix = new HashMap<>(0);
			getModel().setMonthDto(new ArrayList<MeterReadingMonthDTO>(0));
			final List<LookUp> monthlookup = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.MONTH,
					UserSession.getCurrent().getOrganisation());
			for (final LookUp mon : monthlookup) {
				monthprefix.put(Integer.valueOf(mon.getLookUpCode()), mon.getLookUpDesc());
			}
			MeterReadingMonthDTO mdto = null;
			for (final TbWtBillSchedule schedule : getModel().getBillSchedule()) {
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
				if ((schedule.getCodIdWwz1() != null) && (schedule.getCodIdWwz1() > -1)) {
					if ((wz1 != null) && !"undefined".equals(wz1)) {
						ward1 = Long.parseLong(wz1);
					}
					if ((schedule.getCodIdWwz2() != null) && (schedule.getCodIdWwz2() > -1)) {
						if ((wz2 != null) && !"undefined".equals(wz2)) {
							ward2 = Long.parseLong(wz2);
						}
						if ((schedule.getCodIdWwz3() != null) && (schedule.getCodIdWwz3() > -1)) {
							if ((wz3 != null) && !"undefined".equals(wz3)) {
								ward3 = Long.parseLong(wz3);
							}
							if ((schedule.getCodIdWwz4() != null) && (schedule.getCodIdWwz4() > -1)) {
								if ((wz4 != null) && !"undefined".equals(wz4)) {
									ward4 = Long.parseLong(wz4);
								}
								if ((schedule.getCodIdWwz1() != null) && (schedule.getCodIdWwz1() > -1)) {
									if ((wz5 != null) && !"undefined".equals(wz5)) {
										ward5 = Long.parseLong(wz5);
									}
								}
							}
						}
					}
				}

				if (schedule.getCodIdWwz1() != null) {
					Schward1 = schedule.getCodIdWwz1().longValue();
				}
				if (schedule.getCodIdWwz2() != null) {
					Schward2 = schedule.getCodIdWwz2().longValue();
				}
				if (schedule.getCodIdWwz3() != null) {
					Schward3 = schedule.getCodIdWwz3().longValue();
				}
				if (schedule.getCodIdWwz4() != null) {
					Schward4 = schedule.getCodIdWwz4().longValue();
				}
				if (schedule.getCodIdWwz5() != null) {
					Schward5 = schedule.getCodIdWwz5().longValue();
				}
				if ((ward1 == Schward1) && (ward2 == Schward2) && (ward3 == Schward3) && (ward4 == Schward4)
						&& (ward5 == Schward5)) {
					for (final TbWtBillScheduleDetail schDetail : schedule.getBillScheduleDetail()) {
						mdto = new MeterReadingMonthDTO();
						mdto.setFrom(schDetail.getCnsFromDate().intValue());
						mdto.setTo(schDetail.getCnsToDate().intValue());
						mdto.setMonthDesc(monthprefix.get(mdto.getFrom()) + " - " + monthprefix.get(mdto.getTo()));
						final Calendar cal = Calendar.getInstance();
						cal.set(Calendar.HOUR_OF_DAY, 0);
						cal.set(Calendar.MINUTE, 0);
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						final Date currDate = cal.getTime();
						final String finYear = Utility.getCurrentFinancialYear();
						final Date startDate = Utility.dateFromMonth(finYear, mdto.getFrom(),
								MainetConstants.FINYEAR_DATE.FIRST);
						final Date endDate = Utility.dateFromMonth(finYear, mdto.getTo(),
								MainetConstants.FINYEAR_DATE.LAST);
						if (startDate.before(currDate) || startDate.equals(currDate)) {
							if (endDate.before(currDate)) {
								mdto.setValueCheck(MainetConstants.RnLCommon.Y_FLAG);
								mdto.setCssProperty(MainetConstants.TRUE);
							}
							getModel().getMonthDto().add(mdto);
						}
					}
					break;
				}
			}
		}

		return new ModelAndView("MeterReadPeriodCycle", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "assignBillCycle")
	@ResponseBody
	public void calDaysAndConsumption(final HttpServletRequest httpServletRequest,
			@RequestParam("idarray") final String ids) {
		bindModel(httpServletRequest);
		final MeterReadingModel model = getModel();
		final List<String> monthIds = Arrays.asList(ids.split(MainetConstants.operator.COMMA));
		model.assignBillCycle(monthIds);
	}
	
//	@RequestMapping(method = RequestMethod.POST, params = "getConsumptionAndNoOfDays")
//	public  @ResponseBody String getConsumptionAndNoOfDays(final HttpServletRequest httpServletRequest, @RequestParam("fromDate") final String fromDate,@RequestParam("toDate") final String toDate,
//			@RequestParam("currentMeterReading") final String currentMeterReading, @RequestParam("meterStatus") final String meterStatus, @RequestParam("gapCode") final String gapCode,
//			@RequestParam("csCcn") final String csCcn) {
//		bindModel(httpServletRequest);
//		ifileUpload.sessionCleanUpForFileUpload();
//		final MeterReadingModel model = getModel();
//		final MeterReadingDTO dto = model.getEntityList() != null && model.getEntityList().size() > 0 ? model.getEntityList().get(0) : null;
//		TbCsmrInfoDTO consumerdto = ApplicationContextProvider.getApplicationContext().getBean(NewWaterConnectionService.class)
//				.fetchConnectionDetailsByConnNo(dto.getCsCcn(),
//						UserSession.getCurrent().getOrganisation().getOrgid());
//		StringBuilder builder = new StringBuilder();
//		if(dto != null) {
//			final List<LookUp> lookUpVal = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.WFB,
//					UserSession.getCurrent().getOrganisation());
//			LookUp lookUpBillingSchedule = null;
//			for (final LookUp lookUp : lookUpVal) {
//				if (PrefixConstants.IsLookUp.STATUS.YES.equals(lookUp.getDefaultVal())) {
//					lookUpBillingSchedule = lookUp;
//					break;
//				}
//			}
//			final LookUp lookUpBilling = lookUpBillingSchedule;
//			final String metered = CommonMasterUtility
//					.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.METER,
//							MainetConstants.NewWaterServiceConstants.WMN, UserSession.getCurrent().getOrganisation())
//					.getDescLangFirst();
//			final List<Long> csIdn = model.getEntityList()
//	                .parallelStream()
//	                .filter(waterMeter -> waterMeter.getMrdMtrread() != null)
//	                .map(MeterReadingDTO::getCsIdn)
//	                .collect(Collectors.toList());
//			
//			final Map<Long, List<TbMrdataEntity>> tbMrdataEntityData = tbMrDataService.meterReadingDataByCsidnAndOrg(csIdn,
//					UserSession.getCurrent().getOrganisation().getOrgid());
//			final List<TbMrdataEntity> tbMrdataEntityById = tbMrdataEntityData.get(consumerdto.getCsIdn());
//			
//			final Map<Long, Long> exceptionalGapData = waterExceptionalGapService.fetchForExceptionGap(csIdn,
//					UserSession.getCurrent().getOrganisation().getOrgid(), "Y");
//			
//			TbMrdataEntity firstRecord = null;
//			Date readingUpto = null;
//			double maxReadArr [] = null;
//			
//			if ((tbMrdataEntityById != null) && !tbMrdataEntityById.isEmpty()) {
//
//					int noOfLastRecs = 4 , findLastRec = 4;
//					
//				if (tbMrdataEntityById.size() < noOfLastRecs)
//						findLastRec =  tbMrdataEntityById.size();
//					
//					if(findLastRec > 0)
//					{
//						maxReadArr = new double [2]; 				
//						double maxReadCsmp = 0 , maxNoDays = 0 ;
//						
//						for(int i=0; i< findLastRec; i++)
//						{
//							if(tbMrdataEntityById.get(i).getCsmp() > maxReadCsmp)
//							{
//								maxReadCsmp = tbMrdataEntityById.get(i).getCsmp();
//								maxReadArr[1] = maxReadCsmp;
//							}
//							
//							if(tbMrdataEntityById.get(i).getNdays() > maxNoDays)
//							{
//								maxNoDays = tbMrdataEntityById.get(i).getNdays();
//								maxReadArr[0] = maxNoDays;
//							}
//						}
//					}
//					else
//						logger.info("No Last 4 billings available for CSIDN : {}", consumerdto.getCsIdn());
//			
//					firstRecord = tbMrdataEntityById.get(0);
//					readingUpto = firstRecord.getMrdTo();
//					dto.setLastMtrReadDate(new Date(fromDate));
//					dto.setMrdMtrread(Long.valueOf(currentMeterReading));
//					dto.setMrdMrdate(new Date(toDate));
//					dto.setCpdMtrstatus(Long.valueOf(meterStatus));
//					dto.setCpdGap(Long.valueOf(gapCode));
//					TbMrdataServiceImpl tbMrdataServiceImpl = new TbMrdataServiceImpl();
//					final List<Object> values = tbMrdataServiceImpl.getNoOfDaysAndConsumption(dto, UserSession.getCurrent().getOrganisation(), model.getMonthDto(), model.getChangeCycle(),
//							lookUpBilling, metered, readingUpto, exceptionalGapData.get(consumerdto.getCsIdn()) , maxReadArr);
//					long noDays = 0l;
//					long csmp = 0l;
//					double csmpWithDecimal = 0d;
//					if ((values != null) && !values.isEmpty()) {
//						if (values.get(0) != null) {
//							csmpWithDecimal = Double.valueOf(values.get(0).toString());
//						}
//						if (values.get(1) != null) {
//							 noDays = Long.valueOf(values.get(1).toString());
//						}
//						final LookUp gapCodeData = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(gapCode), UserSession.getCurrent().getOrganisation());
//						String gapCodeString = gapCodeData.getLookUpCode();
//						if(PrefixConstants.WATERMODULEPREFIX.TBM.equals(gapCodeString) || PrefixConstants.WATERMODULEPREFIX.THM.equals(gapCodeString) 
//								|| PrefixConstants.WATERMODULEPREFIX.TPM.equals(gapCodeString) || PrefixConstants.WATERMODULEPREFIX.AVG.equals(gapCodeString) 
//								|| PrefixConstants.WATERMODULEPREFIX.MUM.equals(gapCodeString))
//						{
//							if(PrefixConstants.WATERMODULEPREFIX.THM.equals(gapCodeString) || PrefixConstants.WATERMODULEPREFIX.TPM.equals(gapCodeString)
//									|| PrefixConstants.WATERMODULEPREFIX.AVG.equals(gapCodeString) ) {
//								noDays = 1L;
//							}
//							csmp = Math.round(csmpWithDecimal * noDays);
//						}
//						else {
//							csmp = Math.round(csmpWithDecimal);
//						}
//						
//						
//						builder.append(csmp);
//						builder.append(MainetConstants.operator.COMMA);
//						builder.append(noDays);
//					}	
//					else {
//						builder.append('N');
//					}
//			}
//			
//		}
//		else {
//			builder.append('N');
//		}
//		return builder.toString();
//		
//	}

	
	private boolean checkBackdatedFinancialBillScheduleActive() {

		boolean billScheduleActive = false;
		long lookUpId = 0;
		try {
			lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("MBS", "FBS",
					UserSession.getCurrent().getOrganisation().getOrgid());
		} catch (Exception exception) {

		}
		if (lookUpId > 0) {
			billScheduleActive = true;
		}
		return billScheduleActive;

	}

	@RequestMapping(method = RequestMethod.POST, params = "getConsumptionAndNoOfDays")
	public  @ResponseBody String getConsumptionAndNoOfDays(final HttpServletRequest httpServletRequest, @RequestParam("toDate") final String toDate,
			@RequestParam("currentMeterReading") final String currentMeterReading, @RequestParam("meterStatus") final String meterStatus, @RequestParam("gapCode") final String gapCode,
			@RequestParam("csCcn") final String csCcn) throws ParseException {
		bindModel(httpServletRequest);
		ifileUpload.sessionCleanUpForFileUpload();
		final MeterReadingModel model = getModel();
		final MeterReadingDTO dto = model.getEntityList() != null && model.getEntityList().size() > 0 ? model.getEntityList().get(0) : null;
		TbCsmrInfoDTO consumerdto = ApplicationContextProvider.getApplicationContext().getBean(NewWaterConnectionService.class)
				.fetchConnectionDetailsByConnNo(dto.getCsCcn(),
						UserSession.getCurrent().getOrganisation().getOrgid());
		StringBuilder builder = new StringBuilder();
		if(dto != null) {
			final List<LookUp> lookUpVal = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.WFB,
					UserSession.getCurrent().getOrganisation());
			LookUp lookUpBillingSchedule = null;
			for (final LookUp lookUp : lookUpVal) {
				if (PrefixConstants.IsLookUp.STATUS.YES.equals(lookUp.getDefaultVal())) {
					lookUpBillingSchedule = lookUp;
					break;
				}
			}
			final LookUp lookUpBilling = lookUpBillingSchedule;
			final String metered = CommonMasterUtility
					.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.METER,
							MainetConstants.NewWaterServiceConstants.WMN, UserSession.getCurrent().getOrganisation())
					.getDescLangFirst();
			if( model.getEntityList() != null && !model.getEntityList().isEmpty()) {
				model.getEntityList().get(0).setMrdMtrread(Long.valueOf(currentMeterReading));
			}
			final List<Long> csIdn = model.getEntityList()
	                .parallelStream()
	                .filter(waterMeter -> waterMeter.getMrdMtrread() != null)
	                .map(MeterReadingDTO::getCsIdn)
	                .collect(Collectors.toList());
			
			final Map<Long, List<TbMrdataEntity>> tbMrdataEntityData = tbMrDataService.meterReadingDataByCsidnAndOrg(csIdn,
					UserSession.getCurrent().getOrganisation().getOrgid());
			final List<TbMrdataEntity> tbMrdataEntityById = tbMrdataEntityData.get(consumerdto.getCsIdn());
			
			final Map<Long, Long> exceptionalGapData = waterExceptionalGapService.fetchForExceptionGap(csIdn,
					UserSession.getCurrent().getOrganisation().getOrgid(), "Y");
			
			TbMrdataEntity firstRecord = null;
			Date readingUpto = null;
			double maxReadArr [] = null;
			
			if ((tbMrdataEntityById != null) && !tbMrdataEntityById.isEmpty()) {

					int noOfLastRecs = 4 , findLastRec = 4;
					
				if (tbMrdataEntityById.size() < noOfLastRecs)
						findLastRec =  tbMrdataEntityById.size();
					
					if(findLastRec > 0)
					{
						maxReadArr = new double [2]; 				
						double maxReadCsmp = 0 , maxNoDays = 0 ;
						
						for(int i=0; i< findLastRec; i++)
						{
							if(tbMrdataEntityById.get(i).getCsmp() > maxReadCsmp)
							{
								maxReadCsmp = tbMrdataEntityById.get(i).getCsmp();
								maxReadArr[1] = maxReadCsmp;
							}
							
							if(tbMrdataEntityById.get(i).getNdays() > maxNoDays)
							{
								maxNoDays = tbMrdataEntityById.get(i).getNdays();
								maxReadArr[0] = maxNoDays;
							}
						}
					}
					else
						logger.info("No Last 4 billings available for CSIDN : {}", consumerdto.getCsIdn());
			
					firstRecord = tbMrdataEntityById.get(0);
//					readingUpto = firstRecord.getMrdTo();
//					dto.setLastMtrReadDate(new Date(fromDate));
					
					readingUpto = new Date();
					dto.setLastMtrReadDate(firstRecord.getMrdMrdate());
					dto.setMrdMtrread(Long.valueOf(currentMeterReading));
					dto.setMrdMrdate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).parse(toDate));
					dto.setCpdMtrstatus(Long.valueOf(meterStatus));
					dto.setCpdGap(Long.valueOf(gapCode));
					TbMrdataServiceImpl tbMrdataServiceImpl = new TbMrdataServiceImpl();
					final List<Object> values = tbMrdataServiceImpl.getNoOfDaysAndConsumption(dto, UserSession.getCurrent().getOrganisation(), model.getMonthDto(), model.getChangeCycle(),
							lookUpBilling, metered, readingUpto, exceptionalGapData.get(consumerdto.getCsIdn()) , maxReadArr);
					long noDays = 0l;
					long csmp = 0l;
					double csmpWithDecimal = 0d;
					if ((values != null) && !values.isEmpty()) {
						if (values.get(0) != null) {
							csmpWithDecimal = Double.valueOf(values.get(0).toString());
						}
						if (values.get(1) != null) {
							 noDays = Long.valueOf(values.get(1).toString());
						}
						final LookUp gapCodeData = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(gapCode), UserSession.getCurrent().getOrganisation());
						String gapCodeString = gapCodeData.getLookUpCode();
						if(PrefixConstants.WATERMODULEPREFIX.TBM.equals(gapCodeString) || PrefixConstants.WATERMODULEPREFIX.THM.equals(gapCodeString) 
								|| PrefixConstants.WATERMODULEPREFIX.TPM.equals(gapCodeString) || PrefixConstants.WATERMODULEPREFIX.AVG.equals(gapCodeString) 
								|| PrefixConstants.WATERMODULEPREFIX.MUM.equals(gapCodeString))
						{
							if(PrefixConstants.WATERMODULEPREFIX.THM.equals(gapCodeString) || PrefixConstants.WATERMODULEPREFIX.TPM.equals(gapCodeString)
									|| PrefixConstants.WATERMODULEPREFIX.AVG.equals(gapCodeString) ) {
								noDays = 1L;
							}
							csmp = Math.round(csmpWithDecimal * noDays);
						}
						else {
							csmp = Math.round(csmpWithDecimal);
						}
						
						
						builder.append(csmp);
						builder.append(MainetConstants.operator.COMMA);
						builder.append(noDays);
					}	
					else {
						builder.append('N');
					}
			}
			
		}
		else {
			builder.append('N');
		}
		return builder.toString();
		
	}

}
