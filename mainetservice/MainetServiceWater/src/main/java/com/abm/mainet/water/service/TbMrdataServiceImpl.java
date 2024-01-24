package com.abm.mainet.water.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.NewWaterServiceConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.BillTaxDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.dao.MeterCutOffRestorationRepository;
import com.abm.mainet.water.dao.NewWaterRepository;
import com.abm.mainet.water.dao.TbMrdataJpaRepository;
import com.abm.mainet.water.dao.WaterDisconnectionRepository;
import com.abm.mainet.water.datamodel.Consumption;
import com.abm.mainet.water.datamodel.NoOfDays;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbMrdataEntity;
import com.abm.mainet.water.domain.TbWtBillMasEntity;
import com.abm.mainet.water.dto.MeterReadingDTO;
import com.abm.mainet.water.dto.MeterReadingMonthDTO;
import com.abm.mainet.water.dto.TbMeterMas;
import com.abm.mainet.water.dto.TbWtBillSchedule;
import com.abm.mainet.water.dto.TbWtBillScheduleDetail;
import com.abm.mainet.water.mapper.TbMrdataServiceMapper;
import com.abm.mainet.water.repository.TbWtBillMasJpaRepository;

/**
 * Implementation of TbMrdataService
 */
@Service
public class TbMrdataServiceImpl implements TbMrdataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TbMrdataServiceImpl.class);

	@Resource
	private TbMrdataJpaRepository tbMrdataJpaRepository;

	@Resource
	private NewWaterRepository newWaterRepository;

	@Resource
	private TbMeterMasService tbMeterMasService;

	@Resource
	private IFileUploadService iFileUploadService;

	@Resource
	private TbMrdataServiceMapper tbMrdataServiceMapper;

	@Autowired
	private TbWtBillMasService tbWtBillMasService;

	@Resource
	private MeterCutOffRestorationRepository meterCutOffRestorationRepository;

	@Resource
	private WaterDisconnectionRepository waterDisconnectionRepository;

	@Resource
	private MessageSource messageSource;

	@Resource
	private WaterExceptionalGapService waterExceptionalGapService;

	@Autowired
	private WaterCommonService waterCommonService;

	@Autowired
	private TbWtBillMasJpaRepository billMasRepository;

	private final Long ONE_DAY = 24 * 60 * 60  * 1000l;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.TbMrdataService#findWaterRecords(
	 * com.abm.mainet.water.dto.MeterReadingDTO)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MeterReadingDTO> findWaterRecords(final MeterReadingDTO entityDTO, final String finYearId,
			final List<TbWtBillSchedule> billSchedule, final String dependsOnType,
			final List<MeterReadingMonthDTO> monthList) {
		final Organisation organisation = new Organisation();
		final Map<Integer, String> monthprefix = new HashMap<>(0);
		organisation.setOrgid(entityDTO.getOrgid());
		final Long meteredtype = CommonMasterUtility
				.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.METER,
						MainetConstants.NewWaterServiceConstants.WMN, organisation)
				.getLookUpId();
		final List<MeterReadingDTO> dto = new ArrayList<>(0);
		List<MeterReadingMonthDTO> month = null;
		MeterReadingDTO data = null;

		int end = 0;
		Date endDate = null;
		if (MainetConstants.Common_Constant.NUMBER.ONE.equals(dependsOnType) && (monthList != null)) {
			for (final MeterReadingMonthDTO monthdto : monthList) {
				if (MainetConstants.YES.equals(monthdto.getValueCheck())) {
					end = monthdto.getTo();
				}
			}
			endDate = Utility.dateFromMonth(Utility.getCurrentFinancialYear(), end, MainetConstants.FINYEAR_DATE.LAST);
		}
		final List<Object[]> waterList = newWaterRepository.findWaterRecordsForMeterReading(entityDTO, meteredtype,
				endDate);

		final List<Object[]> newWaterList = newWaterRepository.findNewWaterRecordsForMeterReading(entityDTO,
				meteredtype);

		if (((waterList != null) && !waterList.isEmpty()) || ((newWaterList != null) && !newWaterList.isEmpty())) {
			final List<LookUp> monthlookup = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.MONTH,
					organisation);
			for (final LookUp mon : monthlookup) {
				monthprefix.put(Integer.valueOf(mon.getLookUpCode()), mon.getDescLangFirst());
			}
		}
		if ((waterList != null) && !waterList.isEmpty()) {
			for (final Object[] water : waterList) {
				String address = MainetConstants.CommonConstants.BLANK;
				String name = MainetConstants.CommonConstants.BLANK;
				String metered = MainetConstants.CommonConstants.BLANK;
				data = new MeterReadingDTO();
				data.setLgIpMac(Utility.getMacAddress());

				if (water[0] != null) {
					data.setCsIdn(Long.valueOf(water[0].toString()));
				}
				if (water[1] != null) {
					data.setMrdId(Long.valueOf(water[1].toString()));
				}
				if (water[2] != null) {
					data.setCsCcn(water[2].toString());
				}
				if (water[3] != null) {
					data.setMtrNumber(water[3].toString());
				}
				if (water[4] != null) {
					address += water[4].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[5] != null) {
					address += water[5].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[6] != null) {
					address += water[6].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[7] != null) {
					address += water[7].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[8] != null) {
					address += water[8].toString() + MainetConstants.WHITE_SPACE;
				}
				data.setAddress(address);
				if (water[10] != null) {
					name += water[10].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[11] != null) {
					name += water[11].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[12] != null) {
					name += water[12].toString() + MainetConstants.WHITE_SPACE;
				}
				data.setName(name);
				final long gapcode = CommonMasterUtility
						.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.NOG,
								PrefixConstants.NewWaterServiceConstants.GAP_CODE, organisation)
						.getLookUpId();
				data.setCpdGap(water[13]!=null? Long.valueOf(water[13].toString()) : null);
				final long mtrStatus = CommonMasterUtility
						.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.MOK,
								PrefixConstants.NewWaterServiceConstants.METER_STATUS, organisation)
						.getLookUpId();
				data.setCpdMtrstatus(water[14]!=null? Long.valueOf(water[14].toString()) : null);
				if (water[15] != null) {
					data.setLastMtrRead(Long.valueOf(water[15].toString()));
				}
				if (water[16] != null) {
					data.setMmMtnid(Long.valueOf(water[16].toString()));
				}
				Long wtpRB = null;
				if (water[17] != null) {
					if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)) {
						if (isMeterCutRestoration(data.getCsIdn(), data.getMmMtnid(), entityDTO.getOrgid(),
								MainetConstants.MeterCutOffRestoration.METER_RESTORATION)) {
							Object[] restorationData = meterCutOffRestorationRepository.getCutOffReadingAndDate(data.getCsIdn(), data.getMmMtnid(),
									entityDTO.getOrgid(), MainetConstants.MeterCutOffRestoration.METER_RESTORATION);
							Date meteResotationDate = restorationData != null && restorationData.length > 0 && restorationData[0] != null ? 
									(Date) restorationData[0] : null;
							Date meterReadingDate = water[29] != null ? (Date) water[29] : null;
							data.setRestorationDate(meteResotationDate);
							data.setCutOffReading(restorationData[1]!=null ? Long.valueOf(restorationData[1].toString()) : null);
							if(meteResotationDate != null && meterReadingDate != null && meteResotationDate.before(meterReadingDate)) {
								wtpRB = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.WATERMODULEPREFIX.RB,
										PrefixConstants.WATERMODULEPREFIX.WTP, organisation).getLookUpId();
							}
							else {
								wtpRB = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.WATERMODULEPREFIX.RT,
										PrefixConstants.WATERMODULEPREFIX.WTP, organisation).getLookUpId();
							}
						} else if (isMeterCutRestoration(data.getCsIdn(), data.getMmMtnid(), entityDTO.getOrgid(),
								MainetConstants.MeterCutOffRestoration.METER_CUTOFF)) {
							
							Object[] cutOffData = meterCutOffRestorationRepository.getCutOffReadingAndDate(data.getCsIdn(), data.getMmMtnid(), 
									entityDTO.getOrgid(), MainetConstants.MeterCutOffRestoration.METER_CUTOFF);
							data.setCutOffDate(cutOffData[0]!=null ?(Date) cutOffData[0] : null);
							data.setCutOffReading(cutOffData[1]!=null ? Long.valueOf(cutOffData[1].toString()) : null);
							
							wtpRB = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.WATERMODULEPREFIX.CT,
									PrefixConstants.WATERMODULEPREFIX.WTP, organisation).getLookUpId();
						}
						// need to change for
						// disconnection======================================================================
						else {
							wtpRB = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.WATERMODULEPREFIX.RB,
									PrefixConstants.WATERMODULEPREFIX.WTP, organisation).getLookUpId();
						}
					}
					else {
						
						if (isMeterCutRestoration(data.getCsIdn(), data.getMmMtnid(), entityDTO.getOrgid(),
								MainetConstants.MeterCutOffRestoration.METER_RESTORATION)) {
							wtpRB = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.WATERMODULEPREFIX.RT,
									PrefixConstants.WATERMODULEPREFIX.WTP, organisation).getLookUpId();
						} else if (isMeterCutRestoration(data.getCsIdn(), data.getMmMtnid(), entityDTO.getOrgid(),
								MainetConstants.MeterCutOffRestoration.METER_CUTOFF)) {
							wtpRB = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.WATERMODULEPREFIX.CT,
									PrefixConstants.WATERMODULEPREFIX.WTP, organisation).getLookUpId();
						}
						// need to change for
						// disconnection======================================================================
						else {
							wtpRB = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.WATERMODULEPREFIX.RB,
									PrefixConstants.WATERMODULEPREFIX.WTP, organisation).getLookUpId();
						}
					}
				}
				data.setMrdCpdIdWtp(wtpRB);
				if (water[18] != null) {
					data.setCsMeteredccn(Long.valueOf(water[18].toString()));
					metered = CommonMasterUtility
							.getNonHierarchicalLookUpObject(Long.valueOf(water[18].toString()), organisation)
							.getLookUpCode();
				}
				if (water[19] != null) {
					data.setPcDate((Date) water[19]);
				}
				if (water[26] != null) {
					data.setMaxMeterRead(Long.valueOf(water[26].toString()));
				}
				if (water[27] != null) {
					data.setInstallMeterRead(Long.valueOf(water[27].toString()));
				}
				if (water[28] != null) {
					data.setMeterInstallDate((Date) water[28]);
				}
				if ((water[30] == null) || water[30].toString().equals(MainetConstants.RnLCommon.N_FLAG)) {
					if (water[29] != null) {
						data.setMrdMrdate((Date) water[29]);
					}
					if(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)) {
						if ((entityDTO.getMeterType() != null)) {
							data.setMrdMtrread(data.getLastMtrRead());
						}
					}
					else {
						if ((entityDTO.getMeterType() != null)
								&& entityDTO.getMeterType().equals(MainetConstants.RnLCommon.S_FLAG)) {
							data.setMrdMtrread(data.getLastMtrRead());
						}
					}
					
				}
				month = new ArrayList<>(0);
				MeterReadingMonthDTO mdto = null;

				for (final TbWtBillSchedule billsch : billSchedule) {

					if ((entityDTO.getMeterType() != null)
							&& entityDTO.getMeterType().equals(MainetConstants.RnLCommon.S_FLAG)) {

						if (MainetConstants.Common_Constant.NUMBER.ONE.equals(billsch.getDependsOnType())) {
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

							if ((billsch.getCodIdWwz1() != null) && (billsch.getCodIdWwz1() > -1)) {
								if (water[21] != null) {
									ward1 = Long.parseLong(water[21].toString());
								}
								if ((billsch.getCodIdWwz2() != null) && (billsch.getCodIdWwz2() > -1)) {
									if (water[22] != null) {
										ward2 = Long.parseLong(water[22].toString());
									}
									if ((billsch.getCodIdWwz3() != null) && (billsch.getCodIdWwz3() > -1)) {
										if (water[23] != null) {
											ward3 = Long.parseLong(water[23].toString());
										}
										if ((billsch.getCodIdWwz4() != null) && (billsch.getCodIdWwz4() > -1)) {
											if (water[24] != null) {
												ward4 = Long.parseLong(water[24].toString());
											}
											if ((billsch.getCodIdWwz1() != null) && (billsch.getCodIdWwz1() > -1)) {
												if (water[25] != null) {
													ward5 = Long.parseLong(water[25].toString());
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
							if ((ward1 == Schward1) && (ward2 == Schward2) && (ward3 == Schward3) && (ward4 == Schward4)
									&& (ward5 == Schward5)) {
								for (final TbWtBillScheduleDetail schDetail : billsch.getBillScheduleDetail()) {
									mdto = new MeterReadingMonthDTO();
									mdto.setFrom(schDetail.getCnsFromDate().intValue());
									mdto.setTo(schDetail.getCnsToDate().intValue());
									mdto.setMonthDesc(
											monthprefix.get(mdto.getFrom()) + " - " + monthprefix.get(mdto.getTo()));
									final Calendar cal = Calendar.getInstance();
									cal.set(Calendar.HOUR_OF_DAY, 0);
									cal.set(Calendar.MINUTE, 0);
									cal.set(Calendar.SECOND, 0);
									cal.set(Calendar.MILLISECOND, 0);
									final Date currDate = cal.getTime();
									final String finYear = Utility.getCurrentFinancialYear();
									final Date startDate = Utility.dateFromMonth(finYear, mdto.getFrom(),
											MainetConstants.FINYEAR_DATE.FIRST);
									final Date endDate1 = Utility.dateFromMonth(finYear, mdto.getTo(),
											MainetConstants.FINYEAR_DATE.LAST);
									if (startDate.before(currDate) || startDate.equals(currDate)) {
										if (endDate1.before(currDate)) {
											mdto.setValueCheck(MainetConstants.RnLCommon.Y_FLAG);
											mdto.setCssProperty(MainetConstants.TRUE);
										}
										month.add(mdto);
									}
								}
								break;
							}
						} else if (MainetConstants.Common_Constant.NUMBER.TWO.equals(billsch.getDependsOnType())) {
							if ((water[20] != null) && water[20].toString().equals(billsch.getCnsCcgid1().toString())
									&& metered.equals(billsch.getCnsMn())) {
								for (final TbWtBillScheduleDetail schDetail : billsch.getBillScheduleDetail()) {
									mdto = new MeterReadingMonthDTO();
									mdto.setFrom(schDetail.getCnsFromDate().intValue());
									mdto.setTo(schDetail.getCnsToDate().intValue());
									mdto.setMonthDesc(
											monthprefix.get(mdto.getFrom()) + " - " + monthprefix.get(mdto.getTo()));
									final Calendar cal = Calendar.getInstance();
									cal.set(Calendar.HOUR_OF_DAY, 0);
									cal.set(Calendar.MINUTE, 0);
									cal.set(Calendar.SECOND, 0);
									cal.set(Calendar.MILLISECOND, 0);
									final Date currDate = cal.getTime();
									final String finYear = Utility.getCurrentFinancialYear();
									final Date startDate = Utility.dateFromMonth(finYear, mdto.getFrom(),
											MainetConstants.FINYEAR_DATE.FIRST);
									final Date endDate1 = Utility.dateFromMonth(finYear, mdto.getTo(),
											MainetConstants.FINYEAR_DATE.LAST);
									if (startDate.before(currDate) || startDate.equals(currDate)) {
										if (endDate1.before(currDate)) {
											mdto.setValueCheck(MainetConstants.RnLCommon.Y_FLAG);
											mdto.setCssProperty(MainetConstants.TRUE);
										}
										month.add(mdto);
									}
								}
							}
						}
					}

					else {
						if ((billsch != null) && !MainetConstants.Common_Constant.NUMBER.ONE.equals(dependsOnType)) {
							if ((water[20] != null) && water[20].toString().equals(billsch.getCnsCcgid1().toString())
									&& metered.equals(billsch.getCnsMn())) {
								for (final TbWtBillScheduleDetail schDetail : billsch.getBillScheduleDetail()) {
									mdto = new MeterReadingMonthDTO();
									mdto.setFrom(schDetail.getCnsFromDate().intValue());
									mdto.setTo(schDetail.getCnsToDate().intValue());
									mdto.setMonthDesc(
											monthprefix.get(mdto.getFrom()) + " - " + monthprefix.get(mdto.getTo()));
									final Calendar cal = Calendar.getInstance();
									cal.set(Calendar.HOUR_OF_DAY, 0);
									cal.set(Calendar.MINUTE, 0);
									cal.set(Calendar.SECOND, 0);
									cal.set(Calendar.MILLISECOND, 0);
									final Date currDate = cal.getTime();
									final String finYear = Utility.getCurrentFinancialYear();
									final Date startDate = Utility.dateFromMonth(finYear, mdto.getFrom(),
											MainetConstants.FINYEAR_DATE.FIRST);
									final Date endDate1 = Utility.dateFromMonth(finYear, mdto.getTo(),
											MainetConstants.FINYEAR_DATE.LAST);
									if (startDate.before(currDate) || startDate.equals(currDate)) {
										if (endDate1.before(currDate)) {
											mdto.setValueCheck(MainetConstants.RnLCommon.Y_FLAG);
											mdto.setCssProperty(MainetConstants.TRUE);
										}
										month.add(mdto);
									}
								}
							}
						} else {
							month = monthList;
							break;
						}
					}

				}
				data.setMonth(month);
				dto.add(data);
			}
		}

		if ((newWaterList != null) && !newWaterList.isEmpty()) {
			for (final Object[] water : newWaterList) {
				String address = MainetConstants.CommonConstants.BLANK;
				String name = MainetConstants.CommonConstants.BLANK;
				String metered = MainetConstants.CommonConstants.BLANK;
				data = new MeterReadingDTO();
				if (water[0] != null) {
					data.setCsIdn(Long.valueOf(water[0].toString()));
				}
				if (water[1] != null) {
					data.setCsCcn(water[1].toString());
				}
				if (water[2] != null) {
					data.setMtrNumber(water[2].toString());
				}

				if (water[3] != null) {
					address += water[3].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[4] != null) {
					address += water[4].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[5] != null) {
					address += water[5].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[6] != null) {
					address += water[6].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[7] != null) {
					address += water[7].toString() + MainetConstants.WHITE_SPACE;
				}
				data.setAddress(address);
				if (water[9] != null) {
					name += water[9].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[10] != null) {
					name += water[10].toString() + MainetConstants.WHITE_SPACE;
				}
				if (water[11] != null) {
					name += water[11].toString() + MainetConstants.WHITE_SPACE;
				}
				data.setName(name);
				final long gapcode = CommonMasterUtility
						.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.NOG,
								PrefixConstants.NewWaterServiceConstants.GAP_CODE, organisation)
						.getLookUpId();
				data.setCpdGap(gapcode);
				final long mtrStatus = CommonMasterUtility
						.getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.MOK,
								PrefixConstants.NewWaterServiceConstants.METER_STATUS, organisation)
						.getLookUpId();
				data.setCpdMtrstatus(mtrStatus);
				final Long wtpFB = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.WATERMODULEPREFIX.FB,
						PrefixConstants.WATERMODULEPREFIX.WTP, organisation).getLookUpId();
				data.setMrdCpdIdWtp(wtpFB);
				if (water[12] != null) {
					data.setMmMtnid(Long.valueOf(water[12].toString()));
				}
				if (water[13] != null) {
					data.setCsMeteredccn(Long.valueOf(water[13].toString()));
					metered = CommonMasterUtility
							.getNonHierarchicalLookUpObject(Long.valueOf(water[13].toString()), organisation)
							.getLookUpCode();
				}
				if (water[14] != null) {
					data.setPcDate((Date) water[14]);
				}

				if (water[21] != null) {
					data.setMaxMeterRead(Long.valueOf(water[21].toString()));
				}
				if (water[22] != null) {
					data.setLastMtrRead(Long.valueOf(water[22].toString()));
					data.setInstallMeterRead(Long.valueOf(water[22].toString()));
				}
				if (water[23] != null) {
					data.setMeterInstallDate((Date) water[23]);
				}

				month = new ArrayList<>(0);
				MeterReadingMonthDTO mdto = null;
				for (final TbWtBillSchedule billsch : billSchedule) {

					if ((entityDTO.getMeterType() != null)
							&& entityDTO.getMeterType().equals(MainetConstants.RnLCommon.S_FLAG)) {

						if (MainetConstants.Common_Constant.NUMBER.ONE.equals(billsch.getDependsOnType())) {
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

							if ((billsch.getCodIdWwz1() != null) && (billsch.getCodIdWwz1() > -1)) {
								if (water[16] != null) {
									ward1 = Long.parseLong(water[16].toString());
								}
								if ((billsch.getCodIdWwz2() != null) && (billsch.getCodIdWwz2() > -1)) {
									if (water[17] != null) {
										ward2 = Long.parseLong(water[17].toString());
									}
									if ((billsch.getCodIdWwz3() != null) && (billsch.getCodIdWwz3() > -1)) {
										if (water[18] != null) {
											ward3 = Long.parseLong(water[18].toString());
										}
										if ((billsch.getCodIdWwz4() != null) && (billsch.getCodIdWwz4() > -1)) {
											if (water[19] != null) {
												ward4 = Long.parseLong(water[19].toString());
											}
											if ((billsch.getCodIdWwz1() != null) && (billsch.getCodIdWwz1() > -1)) {
												if (water[20] != null) {
													ward5 = Long.parseLong(water[20].toString());
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
							if ((ward1 == Schward1) && (ward2 == Schward2) && (ward3 == Schward3) && (ward4 == Schward4)
									&& (ward5 == Schward5)) {
								for (final TbWtBillScheduleDetail schDetail : billsch.getBillScheduleDetail()) {
									mdto = new MeterReadingMonthDTO();
									mdto.setFrom(schDetail.getCnsFromDate().intValue());
									mdto.setTo(schDetail.getCnsToDate().intValue());
									mdto.setMonthDesc(
											monthprefix.get(mdto.getFrom()) + " - " + monthprefix.get(mdto.getTo()));
									final Calendar cal = Calendar.getInstance();
									cal.set(Calendar.HOUR_OF_DAY, 0);
									cal.set(Calendar.MINUTE, 0);
									cal.set(Calendar.SECOND, 0);
									cal.set(Calendar.MILLISECOND, 0);
									final Date currDate = cal.getTime();
									//final String finYear = Utility.getCurrentFinancialYear();
									final FinancialYear finYearData = ApplicationContextProvider.getApplicationContext()
											.getBean(IFinancialYearService.class)
											.getFinincialYearsById(billsch.getCnsYearid(), billsch.getOrgid());
									final String finYear = Utility.getCompleteFinancialYear(finYearData.getFaFromDate(),
							                finYearData.getFaToDate());
									final Date startDate = Utility.dateFromMonth(finYear, mdto.getFrom(),
											MainetConstants.FINYEAR_DATE.FIRST);
									final Date endDate1 = Utility.dateFromMonth(finYear, mdto.getTo(),
											MainetConstants.FINYEAR_DATE.LAST);
									mdto.setBillFromdate(startDate);
									mdto.setBillTodate(endDate1);
									if (startDate.before(currDate) || startDate.equals(currDate)) {
										if (endDate1.before(currDate)) {
											mdto.setValueCheck(MainetConstants.RnLCommon.Y_FLAG);
											mdto.setCssProperty(MainetConstants.TRUE);
										}
										month.add(mdto);
									}
								}
								//break;
							}
						} else if (MainetConstants.Common_Constant.NUMBER.TWO.equals(billsch.getDependsOnType())) {
							if ((water[15] != null) && water[15].toString().equals(billsch.getCnsCcgid1().toString())
									&& metered.equals(billsch.getCnsMn())) {
								for (final TbWtBillScheduleDetail schDetail : billsch.getBillScheduleDetail()) {
									mdto = new MeterReadingMonthDTO();
									mdto.setFrom(schDetail.getCnsFromDate().intValue());
									mdto.setTo(schDetail.getCnsToDate().intValue());
									mdto.setMonthDesc(
											monthprefix.get(mdto.getFrom()) + " - " + monthprefix.get(mdto.getTo()));
									final Calendar cal = Calendar.getInstance();
									cal.set(Calendar.HOUR_OF_DAY, 0);
									cal.set(Calendar.MINUTE, 0);
									cal.set(Calendar.SECOND, 0);
									cal.set(Calendar.MILLISECOND, 0);
									final Date currDate = cal.getTime();
									final String finYear = Utility.getCurrentFinancialYear();
									final Date startDate = Utility.dateFromMonth(finYear, mdto.getFrom(),
											MainetConstants.FINYEAR_DATE.FIRST);
									final Date endDate1 = Utility.dateFromMonth(finYear, mdto.getTo(),
											MainetConstants.FINYEAR_DATE.LAST);
									mdto.setMonthDesc(
											startDate + " - " + endDate1);
									if (startDate.before(currDate) || startDate.equals(currDate)) {
										if (endDate1.before(currDate)) {
											mdto.setValueCheck(MainetConstants.RnLCommon.Y_FLAG);
											mdto.setCssProperty(MainetConstants.TRUE);
										}
										month.add(mdto);
									}
								}
							}
						}
					}

					else {
						if ((billsch != null) && !MainetConstants.Common_Constant.NUMBER.ONE.equals(dependsOnType)) {
							if ((water[20] != null) && water[20].toString().equals(billsch.getCnsCcgid1().toString())
									&& metered.equals(billsch.getCnsMn())) {
								for (final TbWtBillScheduleDetail schDetail : billsch.getBillScheduleDetail()) {
									mdto = new MeterReadingMonthDTO();
									mdto.setFrom(schDetail.getCnsFromDate().intValue());
									mdto.setTo(schDetail.getCnsToDate().intValue());
									mdto.setMonthDesc(
											monthprefix.get(mdto.getFrom()) + " - " + monthprefix.get(mdto.getTo()));
									final Calendar cal = Calendar.getInstance();
									cal.set(Calendar.HOUR_OF_DAY, 0);
									cal.set(Calendar.MINUTE, 0);
									cal.set(Calendar.SECOND, 0);
									cal.set(Calendar.MILLISECOND, 0);
									final Date currDate = cal.getTime();
									final String finYear = Utility.getCurrentFinancialYear();
									final Date startDate = Utility.dateFromMonth(finYear, mdto.getFrom(),
											MainetConstants.FINYEAR_DATE.FIRST);
									final Date endDate1 = Utility.dateFromMonth(finYear, mdto.getTo(),
											MainetConstants.FINYEAR_DATE.LAST);
									if (startDate.before(currDate) || startDate.equals(currDate)) {
										if (endDate1.before(currDate)) {
											mdto.setValueCheck(MainetConstants.RnLCommon.Y_FLAG);
											mdto.setCssProperty(MainetConstants.TRUE);
										}
										month.add(mdto);
									}
								}
							}
						} else {
							month = monthList;
							break;
						}
					}
				}
				data.setMonth(month);
				dto.add(data);
			}
		}
		return dto;
	}

	/**
	 * @param mmMtnid
	 * @param orgid
	 * @param long1
	 * @return
	 */
	private boolean isMeterCutRestoration(final Long csIdn, final Long mmMtnid, final Long orgid, final String flag) {
		final Object[] data = meterCutOffRestorationRepository.getCutOffReadingAndDate(csIdn, mmMtnid, orgid, flag);
		if (data != null) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainetservice.water.service.TbMrdataService#
	 * getWaterRecordsForViewDetails(java.lang.Long,
	 * com.abm.mainet.common.domain.Organisation)
	 */
	@Override
	@Transactional(readOnly = true)
	public TbMeterMas getWaterRecordsForViewDetails(final Long meterMasId, final long organisation) {
		final TbMeterMas meterMas = tbMeterMasService.findById(meterMasId, organisation);
		return meterMas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.TbMrdataService#saveMeterReadingData
	 * (java.util.List)
	 */
	@Override
	@Transactional
	public boolean saveMeterReadingData(final List<MeterReadingDTO> entityList, final Date mrdDate,
			final Organisation organisation, final List<MeterReadingMonthDTO> monthDto, final String changeCycle,
			final Long empId, final List<Long> csIdn) {
		final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
		if ((entityList != null) && !entityList.isEmpty()) {
			final String fileNetPath = messageSource.getMessage("upload.physicalPath", new Object[] {},
					StringUtils.EMPTY, Locale.ENGLISH);
			final List<LookUp> lookUpVal = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.WFB,
					organisation);
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
							MainetConstants.NewWaterServiceConstants.WMN, organisation)
					.getDescLangFirst();
			final Map<Long, List<TbMrdataEntity>> tbMrdataEntityData = meterReadingDataByCsidnAndOrgId(csIdn,
					organisation.getOrgid());
			final Map<Long, Long> exceptionalGapData = waterExceptionalGapService.fetchForExceptionGap(csIdn,
					organisation.getOrgid(), "Y");
			entityList.stream().filter(data -> data.getMrdMtrread() != null).forEach(data -> {
				
				
				
				data.setMrdMrdate(mrdDate);
				data.setMrdTo(mrdDate);
				data.setOrgid(organisation.getOrgid());
				data.setUserId(empId);
				data.setLangId(MainetConstants.ENGLISH);
				data.setLmoddate(new Date());
				data.setLgIpMac(Utility.getMacAddress());
				TbMrdataEntity firstRecord = null;
				Date readingUpto = null;
				final List<TbMrdataEntity> tbMrdataEntityById = tbMrdataEntityData.get(data.getCsIdn());
				
				//codeWater
				double maxReadArr [] = null;
				//codeWater
						
				if ((tbMrdataEntityById != null) && !tbMrdataEntityById.isEmpty()) {

				//codeWater
				if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)) 
				{	
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
							LOGGER.info("No Last 4 billings available for CSIDN : {}", data.getCsIdn());
				}
				//codeWater
					
					
					firstRecord = tbMrdataEntityById.get(0);
					readingUpto = firstRecord.getMrdTo();
					data.setMrdId(firstRecord.getMrdId());
					data.setLastMtrRead(firstRecord.getMrdMtrread());
					data.setLastMtrReadDate(firstRecord.getMrdMrdate());
					data.setPreviousReading1(firstRecord.getMrdMtrread());
					data.setPreviousReading2(firstRecord.getPreviousReading1());
					data.setPreviousReading3(firstRecord.getPreviousReading2());
					data.setPreviousReading4(firstRecord.getPreviousReading3());
					data.setPreviousReading5(firstRecord.getPreviousReading4());
					data.setPreviousReading6(firstRecord.getPreviousReading5());
					data.setPreviousReading7(firstRecord.getPreviousReading6());
					data.setPreviousReading8(firstRecord.getPreviousReading7());
					data.setPreviousReading9(firstRecord.getPreviousReading8());
					data.setPreviousReading10(firstRecord.getPreviousReading9());
					data.setPreviousReading11(firstRecord.getPreviousReading10());
					data.setPreviousDays1(firstRecord.getNdays());
					data.setPreviousDays2(firstRecord.getPreviousDays1());
					data.setPreviousDays3(firstRecord.getPreviousDays2());
					data.setPreviousDays4(firstRecord.getPreviousDays3());
					data.setPreviousDays5(firstRecord.getPreviousDays4());
					data.setPreviousDays6(firstRecord.getPreviousDays5());
					data.setPreviousDays7(firstRecord.getPreviousDays6());
					data.setPreviousDays8(firstRecord.getPreviousDays7());
					data.setPreviousDays9(firstRecord.getPreviousDays8());
					data.setPreviousDays10(firstRecord.getPreviousDays9());
					data.setPreviousDays11(firstRecord.getPreviousDays10());

					final Calendar cycle = Calendar.getInstance();
					cycle.setTime(firstRecord.getMrdFrom());
					final int monthFrom = cycle.get(Calendar.MONTH) + 1;
					cycle.setTime(firstRecord.getMrdTo());
					String monthFromCycle = null;
					String monthToCycle = null;
					if (monthFrom < 10) {
						monthFromCycle = MainetConstants.ZERO + monthFrom;
					} else {
						monthFromCycle = monthFrom + MainetConstants.CommonConstants.BLANK;
					}
					final int monthTo = cycle.get(Calendar.MONTH) + 1;
					if (monthTo < 10) {
						monthToCycle = MainetConstants.ZERO + monthTo;
					} else {
						monthToCycle = monthTo + MainetConstants.CommonConstants.BLANK;
					}
					String previouscyclefrom = MainetConstants.CommonConstants.BLANK;
					String previouscycleto = MainetConstants.CommonConstants.BLANK;
					final List<LookUp> monthlookup = CommonMasterUtility
							.getListLookup(PrefixConstants.WATERMODULEPREFIX.MONTH, organisation);
					for (final LookUp mon : monthlookup) {
						if (mon.getLookUpCode().equals(String.valueOf(monthFromCycle))) {
							previouscyclefrom = mon.getLookUpDesc() + MainetConstants.operator.MINUS;
						}
						if (mon.getLookUpCode().equals(String.valueOf(monthToCycle))) {
							previouscycleto = mon.getLookUpDesc();
						}
					}
					data.setPreviousCycle1(firstRecord.getMrdFrom() +" "+"to"+ firstRecord.getMrdTo());
					data.setPreviousCycle2(firstRecord.getPreviousCycle1());
					data.setPreviousCycle3(firstRecord.getPreviousCycle2());
					data.setPreviousCycle4(firstRecord.getPreviousCycle3());
					data.setPreviousCycle5(firstRecord.getPreviousCycle4());
					data.setPreviousCycle6(firstRecord.getPreviousCycle5());
					data.setPreviousCycle7(firstRecord.getPreviousCycle6());
					data.setPreviousCycle8(firstRecord.getPreviousCycle7());
					data.setPreviousCycle9(firstRecord.getPreviousCycle8());
					data.setPreviousCycle10(firstRecord.getPreviousCycle9());
					data.setPreviousCycle11(firstRecord.getPreviousCycle10());
				}

				
				final List<Object> values = getNoOfDaysAndConsumption(data, organisation, monthDto, changeCycle,
						lookUpBilling, metered, readingUpto, exceptionalGapData.get(data.getCsIdn()) , maxReadArr);
				double perDayCsmp = 0.0;
				if ((values != null) && !values.isEmpty()) {
					if (values.get(0) != null) {
						perDayCsmp = Double.valueOf(values.get(0).toString());
						data.setCsmp(Double.valueOf(values.get(0).toString()).longValue());
					}
					if (values.get(1) != null) {
						data.setNdays(Long.valueOf(values.get(1).toString()));
					}
					
					if ((FileUploadUtility.getCurrent().getFileMap() != null)
							&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
						for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap()
								.entrySet()) {
							final List<File> list = new ArrayList<>(entry.getValue());
							for (final File file : list) {
								final Base64 base64 = new Base64();
								String bytestring = null;
								try {
									bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
								} catch (final IOException e) {
									LOGGER.error("error while encoding", e);
									throw new FrameworkException("error while encoding", e);
								}
								String fileName = file.getName();
								if ((fileName != null) && fileName.contains(MainetConstants.operator.FORWARD_SLACE)) {
									fileName = fileName.replace(MainetConstants.operator.FORWARD_SLACE,
											MainetConstants.operator.UNDER_SCORE);
								}
								final StringBuilder builder = new StringBuilder();
								builder.append(organisation.getOrgid()).append(MainetConstants.DOUBLE_BACK_SLACE)
										.append("Meter_Photo").append(MainetConstants.DOUBLE_BACK_SLACE)
										.append(Utility.getTimestamp());

								final String dirPath = builder.toString();
								data.setMrdImageName(fileName);
								data.setMrdImagePath(dirPath);
								final DocumentDetailsVO documentDetailsVO = new DocumentDetailsVO();
								documentDetailsVO.setDocumentByteCode(bytestring);
								iFileUploadService.convertAndSaveFile(documentDetailsVO, fileNetPath, dirPath,
										fileName);
							}
						}
					}
					data.setBillGen(MainetConstants.RnLCommon.N_FLAG);
					
					
					if (Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)) {
						if(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.WATERMODULEPREFIX.RB,
								PrefixConstants.WATERMODULEPREFIX.WTP, organisation).getLookUpId() == data.getMrdCpdIdWtp())
						{
							Calendar cal = Calendar.getInstance();
							cal.setTime(firstRecord.getMrdTo());
							cal.add(Calendar.DATE, 1);
							data.setMrdFrom(cal.getTime());
							data.setMrdId(null);
							
							final LookUp gapCodeData = CommonMasterUtility.getNonHierarchicalLookUpObject(data.getCpdGap(), organisation);
							String gapCode = gapCodeData.getLookUpCode();
							
							if(PrefixConstants.WATERMODULEPREFIX.TBM.equals(gapCode) || PrefixConstants.WATERMODULEPREFIX.THM.equals(gapCode) 
									|| PrefixConstants.WATERMODULEPREFIX.TPM.equals(gapCode) || PrefixConstants.WATERMODULEPREFIX.AVG.equals(gapCode) 
									|| PrefixConstants.WATERMODULEPREFIX.MUM.equals(gapCode))
							{
								if(PrefixConstants.WATERMODULEPREFIX.THM.equals(gapCode) || PrefixConstants.WATERMODULEPREFIX.TPM.equals(gapCode)
										|| PrefixConstants.WATERMODULEPREFIX.AVG.equals(gapCode) ) {
									data.setNdays(1L);
								}
								double csmp = perDayCsmp * data.getNdays(); 
								data.setCsmp(Math.round(csmp));
								data.setMrdMtrread(0L);
								//data.setMrdMtrread(firstRecord.getMrdMtrread() + data.getCsmp());
							}
						}
					}
					
					final TbMrdataEntity tbMrdataEntity = new TbMrdataEntity();
					tbMrdataServiceMapper.mapTbMrdataToTbMrdataEntity(data, tbMrdataEntity);
					tbMrdataJpaRepository.saveTbMrDataEntity(tbMrdataEntity);
					atomicBoolean.set(true);
				}
			});
		}
		return atomicBoolean.get();
	}

	private Map<Long, List<TbMrdataEntity>> meterReadingDataByCsidnAndOrgId(final List<Long> csIdn, final long orgid) {
		final List<TbMrdataEntity> meterReading = tbMrdataJpaRepository.meterReadingDataByCsidnAndOrgId(csIdn, orgid);
		final Map<Long, List<TbMrdataEntity>> mrData = new HashMap<>(0);
		List<TbMrdataEntity> data = null;
		if ((meterReading != null) && !meterReading.isEmpty()) {
			for (final TbMrdataEntity reading : meterReading) {
				data = mrData.get(reading.getTbCsmrInfo().getCsIdn());
				if (data == null) {
					data = new ArrayList<>(0);
				}
				data.add(reading);
				mrData.put(reading.getTbCsmrInfo().getCsIdn(), data);
			}
		}
		return mrData;
	}

	/**
	 * @param changeCycle
	 * @param monthDto
	 * @param metered
	 * @param lookUpBilling
	 * @param meterReadUpto
	 * @param gapDays
	 * @param periodType
	 * @param consumption
	 * @param monthIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getNoOfDaysAndConsumption(final MeterReadingDTO data, final Organisation organisation,
			final List<MeterReadingMonthDTO> monthDto, final String changeCycle, final LookUp lookUpBilling,
			final String metered, final Date meterReadUpto, Long gapDays ,double maxReadArr[]) {
		final List<Object> resultValue = new ArrayList<>(0);
		final WSRequestDTO request = new WSRequestDTO();
		request.setModelName("NoOfDays|Consumption");
		
		final WSResponseDTO response = RestClient.callBRMS(request,
				ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
		
		
		
		if ((response != null) && MainetConstants.Req_Status.SUCCESS.equals(response.getWsStatus())) {
			populateModelConsumptionAndNoOfDays(data, organisation, request, response, monthDto, changeCycle,
					lookUpBilling, metered, meterReadUpto, gapDays , maxReadArr);
			final WSResponseDTO result = RestClient.callBRMS(request,
					ServiceEndpoints.BRMSMappingURL.WATER_CONSUMPTION_NODAYS_URL);
			
						
			
			if ((result != null) && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(result.getWsStatus())) {
				final List<Object> list = (List<Object>) result.getResponseObj();
				if ((list != null) && !list.isEmpty()) {
					final Object object1 = list.get(0);// consumption
					final Object object2 = list.get(1);// no. of Days
					resultValue.add(Double.valueOf(object1.toString()));
					resultValue.add(Long.valueOf(object2.toString()));
				}
			}
		}
		return resultValue;
	}

	/**
	 * @param data
	 * @param organisation
	 * @param request
	 * @param changeCycle
	 * @param monthDto
	 * @param metered
	 * @param lookUpBilling
	 * @param meterReadUpto
	 * @param exceptionalGapData
	 * @param daysDTO
	 * @param consumptionDTO
	 */
	private void populateModelConsumptionAndNoOfDays(final MeterReadingDTO data, final Organisation organisation,
			final WSRequestDTO request, final WSResponseDTO response, final List<MeterReadingMonthDTO> monthDto,
			final String changeCycle, final LookUp lookUpBilling, final String metered, final Date meterReadUpto,
			final Long gapDays , double maxReadArr[]) {
		final List<Object> noofdays = RestClient.castResponse(response, NoOfDays.class, 0);
		final List<Object> waterRateMasterList = RestClient.castResponse(response, Consumption.class, 1);
		final NoOfDays daysDTO = (NoOfDays) noofdays.get(0);
		final Consumption consumptionDTO = (Consumption) waterRateMasterList.get(0);
		daysDTO.setOrgId(organisation.getOrgid());
		consumptionDTO.setOrgId(organisation.getOrgid());
		// days DTO Start
		if (metered != null) {
			daysDTO.setTypeOfConnection(metered);
			consumptionDTO.setTypeOfConnection(metered);
		}
		if (lookUpBilling != null) {
			daysDTO.setBillingMethod(lookUpBilling.getDescLangFirst());
		}
		final LookUp typeOfPeriod = CommonMasterUtility.getNonHierarchicalLookUpObject(data.getMrdCpdIdWtp(),
				organisation);
		if (typeOfPeriod != null) {
			daysDTO.setTypeOfPeriod(typeOfPeriod.getDescLangFirst());
			consumptionDTO.setTypeOfPeriod(typeOfPeriod.getDescLangFirst());
		}
		String flag = null;

		if (PrefixConstants.WATERMODULEPREFIX.CT.equals(typeOfPeriod.getLookUpCode())) {
			flag = MainetConstants.NewWaterServiceConstants.CUT_OFF;

			if(data.getCutOffDate()!=null) {
				final Date time = (Date) data.getCutOffDate();
				daysDTO.setDisconnectionDate(time.getTime());
			}
			consumptionDTO.setDisconnectionReading(data.getCutOffReading());	
		} else if (PrefixConstants.WATERMODULEPREFIX.RT.equals(typeOfPeriod.getLookUpCode())) {
			flag = MainetConstants.NewWaterServiceConstants.RESTORATION;

			if(data.getRestorationDate()!=null) {
				final Date time = (Date) data.getRestorationDate();
				daysDTO.setRestorationDate(time.getTime());
			}
			consumptionDTO.setRestorationReading(data.getCutOffReading());	
	
		}
		if (data.getMrdMrdate() != null) {
			daysDTO.setCurrentMeterReadingDate(data.getMrdMrdate().getTime());
		}
		if (data.getPcDate() != null) {
			daysDTO.setPhysicalConnectionDate(data.getPcDate().getTime());
		}
		if (data.getLastMtrReadDate() != null) {
			daysDTO.setLastMeterReadingDate(data.getLastMtrReadDate().getTime());
		}
		if (data.getMeterInstallDate() != null) {
			daysDTO.setMeterInstallationDate(data.getMeterInstallDate().getTime());
		}
		if (gapDays != null) {
			daysDTO.setExceptionPeriod(gapDays);
		}

		int start = 0;
		int end = 0;
		int readmonth = 0;
		boolean startSet = false;
		boolean next = false;
		final Calendar cal = Calendar.getInstance();
		if (meterReadUpto != null) {
			cal.setTime(meterReadUpto);
			readmonth = cal.get(Calendar.MONTH) + 1;
		}
		if (MainetConstants.MENU.Y.equals(changeCycle)) {
			for (final MeterReadingMonthDTO month : monthDto) {
				if (MainetConstants.MENU.Y.equals(month.getValueCheck())) {
					if (!startSet && (readmonth <= 0)) {
						start = month.getFrom();
						startSet = true;
					} else if ((readmonth > 0) && !startSet) {
						if (readmonth == month.getFrom()) {
							next = true;
							continue;
						}
						if (next && !startSet) {
							start = month.getFrom();
							startSet = true;
						}
					}
					end = month.getTo();
				}
			}
		} else if ((data.getMonth() != null) && !data.getMonth().isEmpty()) {
			cal.setTime(data.getMrdMrdate());
			readmonth = cal.get(Calendar.MONTH) + 1;
			for (final MeterReadingMonthDTO month : data.getMonth()) {
				if ((month.getFrom() <= readmonth) && (month.getTo() >= readmonth)) {
					start = month.getFrom();
					end = month.getTo();
					break;
				}
			}
		}
		final String finYear = Utility.getCurrentFinancialYear();
		final Date startDate = Utility.dateFromMonth(finYear, start, MainetConstants.FINYEAR_DATE.FIRST);
		final Date endDate = Utility.dateFromMonth(finYear, end, MainetConstants.FINYEAR_DATE.LAST);
		daysDTO.setBillingCycleStartDate(startDate.getTime());
		daysDTO.setBillingCycleEndDate(endDate.getTime());
		data.setMrdFrom(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL) ? data.getLastMtrReadDate() : startDate);
		data.setMrdTo(Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)? data.getMrdMrdate() : endDate);
		if(!Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL) && CollectionUtils.isNotEmpty(monthDto)) {
			if(monthDto.get(0).getBillFromdate() != null) {
				data.setMrdFrom(monthDto.get(0).getBillFromdate());
			}else {
				data.setMrdFrom(startDate);
			}
			if(!Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL)) {
				if(monthDto.get(monthDto.size() - 1).getBillTodate() != null) {
					data.setMrdTo(monthDto.get(monthDto.size() - 1).getBillTodate());
				}
			}
			
		}
		if (!Utility.isEnvPrefixAvailable(organisation, MainetConstants.ENV_SKDCL) && data.getMrdMrdate() != null) {
			daysDTO.setRestorationDate(data.getMrdMrdate().getTime());
		}

		// consumption DTO start
		final LookUp gapCodeData = CommonMasterUtility.getNonHierarchicalLookUpObject(data.getCpdGap(), organisation);
		if (gapCodeData != null) {
			consumptionDTO.setGapCode(gapCodeData.getLookUpCode());
		}
		consumptionDTO.setBaseRate(0d);

		consumptionDTO.setMaxMeterReading(data.getMaxMeterRead());
		consumptionDTO.setInstallationReading(data.getInstallMeterRead());

		if (data.getLastMtrRead() != null) {
			consumptionDTO.setLastMeterReading(data.getLastMtrRead().longValue());
		}
		consumptionDTO.setCurrentMeterReading(data.getMrdMtrread());
		if (data.getPreviousReading1() != null) {
			consumptionDTO.setCurrent_2Reading(data.getPreviousReading1());
		}
		if (data.getPreviousReading2() != null) {
			consumptionDTO.setCurrent_3Reading(data.getPreviousReading2());
		}
		if (data.getPreviousReading3() != null) {
			consumptionDTO.setCurrent_4Reading(data.getPreviousReading3());
		}
		if (data.getPreviousReading4() != null) {
			consumptionDTO.setCurrent_5Reading(data.getPreviousReading4());
		}
		if (data.getPreviousReading5() != null) {
			consumptionDTO.setCurrent_6Reading(data.getPreviousReading5());
		}
		if (data.getPreviousReading6() != null) {
			consumptionDTO.setCurrent_7Reading(data.getPreviousReading6());
		}
		if (data.getPreviousReading7() != null) {
			consumptionDTO.setCurrent_8Reading(data.getPreviousReading7());
		}
		if (data.getPreviousReading8() != null) {
			consumptionDTO.setCurrent_9Reading(data.getPreviousReading8());
		}
		if (data.getPreviousReading9() != null) {
			consumptionDTO.setCurrent_10Reading(data.getPreviousReading9());
		}
		if (data.getPreviousReading10() != null) {
			consumptionDTO.setCurrent_11Reading(data.getPreviousReading10());
		}
		if (data.getPreviousReading11() != null) {
			consumptionDTO.setCurrent_12Reading(data.getPreviousReading11());
		}
		
		if(data.getMrdMrdate() != null)
		{
			consumptionDTO.setCurrentMeterReadingDate(data.getMrdMrdate().getTime());
		}
		
		if(data.getMeterInstallDate() != null)
		{
			consumptionDTO.setMeterInstallationDate(data.getMeterInstallDate().getTime());
		}
		
		if(data.getPcDate() != null)
		{
			consumptionDTO.setPhysicalConnectionDate(data.getPcDate().getTime());
		}
		
		/*if(daysDTO.getNoOfDays() >= 0)
		{
			consumptionDTO.setNoOfDays(daysDTO.getNoOfDays());
		}*/
		
		if(maxReadArr != null && maxReadArr.length ==2)
		{
			consumptionDTO.setMaxNoOfDaysLast4Mrd(maxReadArr[0]);
			consumptionDTO.setMaxMeterReadLast4Mrd(maxReadArr[1]);
		}
		
		final List<Object> object = new ArrayList<>(0);
		object.add(consumptionDTO);
		object.add(daysDTO);
		request.setDataModel(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.TbMrdataService#getWaterDetailsForMrData
	 * (java.lang.Long, long)
	 */
	@Override
	@Transactional(readOnly = true)
	public MeterReadingDTO getMeterDataByCsidn(final Long csIdn, final long orgid) {
		final List<TbMrdataEntity> waterList = tbMrdataJpaRepository.getMrDataByCsidnAndOrgId(csIdn, orgid);
		if ((waterList != null) && !waterList.isEmpty()) {
			final TbMrdataEntity mrMasData = waterList.get(0);
			return tbMrdataServiceMapper.mapTbMrdataEntityToTbMrdata(mrMasData);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.TbMrdataService#getMeterReadingData
	 * (java.util.List, long)
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<Long, BillTaxDTO> getMeterReadingData(final List<Long> csIdn, final long orgid) {
		final List<TbMrdataEntity> waterList = tbMrdataJpaRepository.getMrDataByCsidnAndOrgId(csIdn, orgid);
		final Map<Long, BillTaxDTO> dto = new HashMap<>();
		BillTaxDTO taxdata = null;
		if ((waterList != null) && !waterList.isEmpty()) {
			for (final TbMrdataEntity mrdata : waterList) {
				taxdata = new BillTaxDTO();
				taxdata.setNdays(mrdata.getNdays());
				taxdata.setCsmp(mrdata.getCsmp());
				taxdata.setMrdCpdIdWtp(mrdata.getMrdCpdIdWtp());
				taxdata.setCpdGap(mrdata.getTbComparamDet().getCpdValue());
				taxdata.setMrdFrom(mrdata.getMrdFrom());
				taxdata.setMrdTo(mrdata.getMrdTo());
				dto.put(mrdata.getTbCsmrInfo().getCsIdn(), taxdata);
			}
		}
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.TbMrdataService#
	 * findMeterReadingByCsidnAndOrgid(java.util.List, long)
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<Long, MeterReadingDTO> findMeterReadingByCsidnAndOrgid(final List<Long> conIds,
			final Organisation organisation, final FileNetApplicationClient fileNetApplicationClient) {
		final Map<Long, MeterReadingDTO> beans = new HashMap<>(0);
		MeterReadingDTO data = null;
		final List<TbMrdataEntity> meterList = tbMrdataJpaRepository.getMrDataByCsidnAndOrgId(conIds,
				organisation.getOrgid());
		for (final TbMrdataEntity entity : meterList) {
			data = new MeterReadingDTO();
			data = tbMrdataServiceMapper.mapTbMrdataEntityToTbMrdata(entity);

			/*
			 * data.setMeterStatusCode(CommonMasterUtility
			 * .getNonHierarchicalLookUpObject(entity.getTbComparamDet2().getCpdId(),
			 * organisation) .getLookUpDesc());
			 */
			data.setMeterStatusCode(CommonMasterUtility.getCPDDescription(entity.getTbComparamDet2().getCpdId(),
					PrefixConstants.D2KFUNCTION.ENGLISH_DESC, organisation.getOrgid()));
			data.setMeterGapCode(CommonMasterUtility.getCPDDescription(entity.getTbComparamDet().getCpdId(),
					PrefixConstants.D2KFUNCTION.ENGLISH_DESC, organisation.getOrgid()));
			/*
			 * data.setMeterGapCode(CommonMasterUtility
			 * .getNonHierarchicalLookUpObject(entity.getTbComparamDet().getCpdId(),
			 * organisation) .getLookUpDesc());
			 */
			final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
					+ MainetConstants.FILE_PATH_SEPARATOR + "SHOW_DOCS";
			data.setFileDownLoadPath(Utility.downloadedFileUrl(
					data.getMrdImagePath() + MainetConstants.FILE_PATH_SEPARATOR + data.getMrdImageName(), outputPath,
					fileNetApplicationClient));

			beans.put(entity.getTbCsmrInfo().getCsIdn(), data);
		}
		return beans;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abm.mainet.water.service.TbMrdataService#setViewPreviousMeterDetails(java
	 * .lang.Long, long, com.abm.mainet.water.dto.MeterReadingDTO,
	 * com.abm.mainet.common.integration.dto.TbWtBillMas)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Object> setViewPreviousMeterDetails(final Long meterMasId, final long orgId,
			final MeterReadingDTO meterReadingDTO) {
		final List<Object> object = new ArrayList<>(0);
		final Organisation organisation = new Organisation();
		organisation.setOrgid(orgId);
		TbBillMas tbWtBillMas = null;
		final TbMeterMas meterData = getWaterRecordsForViewDetails(meterMasId, organisation.getOrgid());
		final MeterReadingDTO Mrdata = getMeterDataByCsidn(meterData.getCsIdn(), organisation.getOrgid());
		if (Mrdata != null) {
			tbWtBillMas = tbWtBillMasService.getBillMaster(meterData.getCsIdn(), organisation.getOrgid(),
					Mrdata.getMrdFrom(), Mrdata.getMrdTo());
		}

		TbKCsmrInfoMH csmrInfo = waterCommonService.getWaterConnectionDetailsById(meterData.getCsIdn(),
				organisation.getOrgid());

		if (csmrInfo != null) {
			String address = MainetConstants.CommonConstants.BLANK;
			String name = MainetConstants.CommonConstants.BLANK;
			if (csmrInfo.getPcDate() != null) {
				meterReadingDTO.setPcDate(csmrInfo.getPcDate());
			}
			if (csmrInfo.getCsCcn() != null) {
				meterReadingDTO.setCsCcn(csmrInfo.getCsCcn());
			}
			if (csmrInfo.getCsCcnstatus() != null) {
				meterReadingDTO.setConStatus(CommonMasterUtility
						.getNonHierarchicalLookUpObject(csmrInfo.getCsCcnstatus(), organisation).getDescLangFirst());
			}

			if (csmrInfo.getCsAdd() != null) {
				address += csmrInfo.getCsAdd() + MainetConstants.WHITE_SPACE;
			}
			if (csmrInfo.getCsFlatno() != null) {
				address += csmrInfo.getCsFlatno() + MainetConstants.WHITE_SPACE;
			}
			if (csmrInfo.getCsBldplt() != null) {
				address += csmrInfo.getCsBldplt() + MainetConstants.WHITE_SPACE;
			}
			if (csmrInfo.getCsLanear() != null) {
				address += csmrInfo.getCsLanear() + MainetConstants.WHITE_SPACE;
			}
			if (csmrInfo.getCsCcityName() != null) {
				address += csmrInfo.getCsCcityName() + MainetConstants.WHITE_SPACE;
			}
			if (csmrInfo.getCsRdcross() != null) {
				address += csmrInfo.getCsRdcross() + MainetConstants.WHITE_SPACE;
			}
			meterReadingDTO.setAddress(address);

			if (csmrInfo.getCsName() != null) {
				name += csmrInfo.getCsName() + MainetConstants.WHITE_SPACE;
			}
			if (csmrInfo.getCsMname() != null) {
				name += csmrInfo.getCsMname() + MainetConstants.WHITE_SPACE;
			}
			if (csmrInfo.getCsLname() != null) {
				name += csmrInfo.getCsLname() + MainetConstants.WHITE_SPACE;
			}
			meterReadingDTO.setName(name);
		}
		if (Mrdata != null) {
			meterReadingDTO.setMrdMrdate(Mrdata.getMrdMrdate());
			meterReadingDTO.setMrdMtrread(Mrdata.getMrdMtrread());
			meterReadingDTO.setMeterStatusCode(CommonMasterUtility
					.getNonHierarchicalLookUpObject(Mrdata.getCpdMtrstatus(), organisation).getLookUpCode());
			meterReadingDTO.setMeterGapCode(CommonMasterUtility
					.getNonHierarchicalLookUpObject(Mrdata.getCpdGap(), organisation).getLookUpCode());
			meterReadingDTO.setNdays(Mrdata.getNdays());
			meterReadingDTO.setCsmp(Mrdata.getCsmp());
			meterReadingDTO.setMrdImagePath(Mrdata.getMrdImagePath());
			meterReadingDTO.setMrdImageName(Mrdata.getMrdImageName());
			meterReadingDTO.setStatus(CommonMasterUtility
					.getNonHierarchicalLookUpObject(Mrdata.getMrdCpdIdWtp(), organisation).getLookUpCode());
		}
		meterData.setMeterOwnerShip(CommonMasterUtility
				.getNonHierarchicalLookUpObject(meterData.getMmOwnership(), organisation).getLookUpDesc());
		object.add(meterData);
		object.add(tbWtBillMas);
		return object;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.water.service.TbMrdataService#getSecondMeterDataByCsidn
	 * (java.lang.Long, long)
	 */
	@Override
	@Transactional(readOnly = true)
	public List getMeterStatusAndReadByCsidn(final Long csIdn, final long orgid) {
		final List<TbMrdataEntity> waterList = tbMrdataJpaRepository.getMrDataByCsidnAndOrgId(csIdn, orgid);
		if ((waterList != null) && !waterList.isEmpty() && waterList.size() > 0) {
			List resultList = new ArrayList<>();
			TbMrdataEntity mrMasData = waterList.get(0);
			resultList.add(mrMasData.getTbComparamDet2().getCpdId());
			
			if(waterList.size() > 1 ) {
				mrMasData = waterList.get(1);
				resultList.add(mrMasData.getMrdMtrread());
			}
			else {
				resultList.add(mrMasData.getMrdMtrread());
			}
			
			return resultList;
		}
		return null;
	}
	
	public Map<Long, List<TbMrdataEntity>> meterReadingDataByCsidnAndOrg(final List<Long> csIdn, final long orgid) {
		final List<TbMrdataEntity> meterReading = tbMrdataJpaRepository.meterReadingDataByCsidnAndOrgId(csIdn, orgid);
		final Map<Long, List<TbMrdataEntity>> mrData = new HashMap<>(0);
		List<TbMrdataEntity> data = null;
		if ((meterReading != null) && !meterReading.isEmpty()) {
			for (final TbMrdataEntity reading : meterReading) {
				data = mrData.get(reading.getTbCsmrInfo().getCsIdn());
				if (data == null) {
					data = new ArrayList<>(0);
				}
				data.add(reading);
				mrData.put(reading.getTbCsmrInfo().getCsIdn(), data);
			}
		}
		return mrData;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, BillTaxDTO> getUnbilledMeterReading(final List<Long> csIdnList, final long orgid) {
		final List<TbMrdataEntity> waterList = tbMrdataJpaRepository.meterReadingDataForBillGen(csIdnList, orgid);
		final Map<Long, BillTaxDTO> dto = new HashMap<>();
		BillTaxDTO taxdata = null;
		List<TbMrdataEntity> listOfUnbilledReadings = waterList.stream().filter(reading -> reading.getBillGen()!=null && reading.getBillGen().equals(NewWaterServiceConstants.NO)).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(listOfUnbilledReadings)) {
			
			Long cumulativeConsumption = listOfUnbilledReadings.stream().mapToLong(reading->reading.getCsmp()).sum();
			Long totalNoOfDaysSinceLastUnbilledReading = listOfUnbilledReadings.stream().mapToLong(reading->reading.getNdays()).sum();
			if ((waterList != null) && !waterList.isEmpty()) {
				TbMrdataEntity mrdata = waterList.get(0);
				taxdata = new BillTaxDTO();
				taxdata.setNdays(totalNoOfDaysSinceLastUnbilledReading);
				taxdata.setCsmp(cumulativeConsumption);
				taxdata.setMrdCpdIdWtp(mrdata.getMrdCpdIdWtp());
				taxdata.setCpdGap(mrdata.getTbComparamDet().getCpdValue());
				List<TbWtBillMasEntity> billMasList = billMasRepository.getBillMasByConnectionId(mrdata.getTbCsmrInfo().getCsIdn());
				if(billMasList!=null && !billMasList.isEmpty() ) {
					taxdata.setMrdFrom(new Date(billMasList.get(billMasList.size()-1).getBmBilldt().getTime()
		        			+ ONE_DAY));
				}else {
					TbKCsmrInfoMH csmrInfo = newWaterRepository.fetchConnectionByCsIdn(csIdnList.get(0), orgid);
					taxdata.setMrdFrom(csmrInfo.getPcDate());
				}
				taxdata.setMrdTo(listOfUnbilledReadings.get(0).getMrdMrdate());
				dto.put(mrdata.getTbCsmrInfo().getCsIdn(), taxdata);
			}
		}
    	
		return dto;
	}

}
