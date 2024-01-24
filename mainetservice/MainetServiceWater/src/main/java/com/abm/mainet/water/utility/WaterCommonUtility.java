
package com.abm.mainet.water.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.datamodel.WaterTaxCalculation;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.service.BRMSWaterService;

/**
 * @author deepika.pimpale
 *
 */
@Component
public final class WaterCommonUtility {

	/**
	 * Logger to log error if something goes wrong
	 */
	private static final Logger logger = LoggerFactory.getLogger(WaterCommonUtility.class);

	private static BRMSWaterService brmsWaterService;

	private static ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private BRMSWaterService autowiredComponent;

	@Autowired
	private ISMSAndEmailService ismsAndEmailServiceautowire;

	@PostConstruct
	private void init() {
		brmsWaterService = this.autowiredComponent;
		ismsAndEmailService = this.ismsAndEmailServiceautowire;
	}

	/**
	 * restrict object instantiation for the class
	 */
	private WaterCommonUtility() {
	};

	/**
	 * @param taxSubCategory----Tax
	 *            Sub Category
	 * @param dependsOnFactorList----Depends
	 *            On Factors
	 * @param rateCharge----Actual
	 *            WaterRateMaster for BRMS rule
	 * @param rate----waterRateMaster
	 *            Service Specific
	 */
	public static void setFactorSpecificData(WaterRateMaster actualRateMaster, WaterRateMaster factorsDependRateMaster,
			Organisation org) {
		actualRateMaster.setRateStartDate(new Date().getTime());
		if (actualRateMaster != null && factorsDependRateMaster != null) {
			if (actualRateMaster.getDependsOnFactorList() != null
					&& !actualRateMaster.getDependsOnFactorList().isEmpty()) {
				for (String factor : actualRateMaster.getDependsOnFactorList()) {
					if (MainetConstants.DependsOnFactors.RDL.equals(factor))
						actualRateMaster.setRoadLength(factorsDependRateMaster.getRoadLength());
					if (MainetConstants.DependsOnFactors.RDT.equals(factor))
						actualRateMaster.setRoadType(factorsDependRateMaster.getRoadType());
				}
			}
		}
	}

	/**
	 * This Method is For Setting Mandatory Fields of WaterRateMaster
	 * 
	 * @param actualRate
	 * @param tempRate
	 * @param road
	 * @param master
	 * @return
	 */
	public static WaterRateMaster populateLOINewWaterConnectionModel(WaterRateMaster actualRateMaster,
			TbKCsmrInfoMH master, Organisation org) {

		if (CollectionUtils.isNotEmpty(actualRateMaster.getDependsOnFactorList())) {
			actualRateMaster.getDependsOnFactorList().forEach(dependFactors -> {

				if (StringUtils.equalsIgnoreCase(dependFactors, "NOF")) {
					if (master.getNoOfFamilies() != null)
						actualRateMaster.setNoOfFamilies(master.getNoOfFamilies().intValue());
				}
				if (StringUtils.equalsIgnoreCase(dependFactors, "NOU")) {
					if (master.getCsNoofusers() != null) {
						actualRateMaster.setNoOfRoomsORTabel(master.getCsNoofusers().doubleValue());
					}
				}
				if (StringUtils.equalsIgnoreCase(dependFactors, "BPL")) {
					actualRateMaster.setIsBPL(master.getBplFlag());
				}

				if (StringUtils.equalsIgnoreCase(dependFactors, "WTC")) {
					if (master.getTrmGroup1() != null) {
						actualRateMaster.setUsageSubtype1(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup1(), org).getDescLangFirst());
					}
					if (master.getTrmGroup2() != null) {
						actualRateMaster.setUsageSubtype2(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup2(), org).getDescLangFirst());
					}
					if (master.getTrmGroup3() != null) {
						actualRateMaster.setUsageSubtype3(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup3(), org).getDescLangFirst());
					}
					if (master.getTrmGroup4() != null) {
						actualRateMaster.setUsageSubtype4(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup4(), org).getDescLangFirst());
					}
					if (master.getTrmGroup5() != null) {
						actualRateMaster.setUsageSubtype5(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup5(), org).getDescLangFirst());
					}
				}

				if (StringUtils.equalsIgnoreCase(dependFactors, "CON")) {
					if (master.getCsCcnsize() != null) {
						actualRateMaster.setConnectionSize(Double.valueOf(CommonMasterUtility
								.getNonHierarchicalLookUpObject(master.getCsCcnsize(), org).getDescLangFirst()));
					}
					if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
						if (master.getTrmGroup1() != null) {
							actualRateMaster.setUsageSubtype1(CommonMasterUtility
									.getHierarchicalLookUp(master.getTrmGroup1(), org).getDescLangFirst());
						}
						if (master.getTrmGroup2() != null) {
							actualRateMaster.setUsageSubtype2(CommonMasterUtility
									.getHierarchicalLookUp(master.getTrmGroup2(), org).getDescLangFirst());
						}
					}
				}
				if (StringUtils.equalsIgnoreCase(dependFactors, "TAP")) {
					if (master.getCsTaxPayerFlag() == null || master.getCsTaxPayerFlag().isEmpty()) {
						actualRateMaster.setTaxPayer(MainetConstants.FlagN);
					} else {
						actualRateMaster.setTaxPayer(master.getCsTaxPayerFlag());
					}
				}
			});
		}

		if (master.getCsMeteredccn() != null)
			actualRateMaster.setMeterType(getMeterTypeDesc(master.getCsMeteredccn(), org));
		actualRateMaster.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		return actualRateMaster;
	}

	/**
	 * @param csMeteredccn
	 * @param org
	 * @return
	 */
	private static String getMeterTypeDesc(Long meterTypeId, Organisation org) {
		LookUp meterLookup = CommonMasterUtility.getNonHierarchicalLookUpObject(meterTypeId, org);
		return meterLookup.getDescLangFirst();
	}

	/**
	 * @param requestDTO
	 * @param orgId
	 * @param serviceId
	 * @return
	 */
	public static List<WaterRateMaster> getChargesForWaterRateMaster(WSRequestDTO requestDTO, Long orgId,
			String serviceShortCode) {
		requestDTO.setModelName(MainetConstants.WATER_RATE_MASTER);
		List<WaterRateMaster> requiredCharges = new ArrayList<WaterRateMaster>();
		WSResponseDTO response = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			List<Object> waterRateMasterList = RestClient.castResponse(response, WaterRateMaster.class);
			WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
			WaterRateMaster.setOrgId(orgId);
			WaterRateMaster.setServiceCode(serviceShortCode);
			WaterRateMaster.setChargeApplicableAt(Long.toString(
					CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NewWaterServiceConstants.SCRUTINY,
							PrefixConstants.LookUp.CHARGE_MASTER_CAA).getLookUpId()));
			requestDTO.setDataModel(WaterRateMaster);
			WSResponseDTO res = brmsWaterService.getApplicableTaxes(requestDTO);
			// WSResponseDTO res = RestClient.callBRMS(requestDTO,
			// ServiceEndpoints.WaterBRMSMappingURI.DEPENDENT_PARAM_URI);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {

				List<?> rates = castResponse(res, WaterRateMaster.class);
				for (Object rate : rates) {
					requiredCharges.add((WaterRateMaster) rate);
				}
			} else {
				logger.error("Error in Initializing other fields for taxes");
			}
		} else {
			logger.error("Error in Initializing model");
		}
		return requiredCharges;
	}

	public static List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz) {

		Object dataModel = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.COMMON_STATUS.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				for (final Object object : list) {
					WaterRateMaster responseMap = (WaterRateMaster) object;
					final String jsonString = new JSONObject(responseMap).toString();
					dataModel = new ObjectMapper().readValue(jsonString, clazz);
					dataModelList.add(dataModel);
				}
			}

		} catch (final IOException e) {
			logger.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}

	public static double getAndSetBaseRate(Double compareParam, WaterRateMaster rateMaster,
			WaterTaxCalculation waterTaxCalculation, String taxType) {
		double baseRate = 0d;
		switch (taxType) {
		case PrefixConstants.TAX_TYPE.FLAT:// flat
			baseRate = rateMaster.getFlatRate();
			break;

		case PrefixConstants.TAX_TYPE.SLAB:// slab
			if ((compareParam > rateMaster.getSlab1()) && (compareParam <= rateMaster.getSlab2())) {
				baseRate = rateMaster.getSlabRate1();
			} else if ((compareParam > rateMaster.getSlab2()) && (compareParam <= rateMaster.getSlab3())) {
				baseRate = rateMaster.getSlabRate2();
			} else if ((compareParam > rateMaster.getSlab3()) && (compareParam <= rateMaster.getSlab4())) {
				baseRate = rateMaster.getSlabRate3();
			} else if (compareParam > rateMaster.getSlab4() && compareParam <= rateMaster.getSlab5()) {
				baseRate = rateMaster.getSlabRate4();
			} else if (compareParam > rateMaster.getSlab5()) {
				baseRate = rateMaster.getSlabRate5();
			}

			break;
		case PrefixConstants.TAX_TYPE.PERCENTAGE:// percentage
			baseRate = rateMaster.getPercentageRate() / 100;
			break;
		case PrefixConstants.TAX_TYPE.TELESCOPIC:// telescopic
			if ((compareParam > rateMaster.getSlab1()) && (compareParam <= rateMaster.getSlab2())) {
				baseRate = compareParam * rateMaster.getSlabRate1();
			} else if ((compareParam > rateMaster.getSlab2()) && (compareParam <= rateMaster.getSlab3())) {
				baseRate = (rateMaster.getSlab2() * rateMaster.getSlabRate1())
						+ ((compareParam - rateMaster.getSlab2()) * rateMaster.getSlabRate2());
			} else if ((compareParam > rateMaster.getSlab3()) && (compareParam <= rateMaster.getSlab4())) {
				baseRate = (rateMaster.getSlab2() * rateMaster.getSlabRate1())
						+ (rateMaster.getSlabRate2() * rateMaster.getSlab3())
						+ ((compareParam - rateMaster.getSlab3()) * rateMaster.getSlabRate3());
			} else if (compareParam > rateMaster.getSlab4() && compareParam <= rateMaster.getSlab5()) {
				baseRate = (rateMaster.getSlab2() * rateMaster.getSlabRate1())
						+ (rateMaster.getSlabRate2() * rateMaster.getSlab3())
						+ (rateMaster.getSlabRate3() * rateMaster.getSlab4())
						+ ((compareParam - rateMaster.getSlab4()) * rateMaster.getSlabRate4());
			} else if (compareParam > rateMaster.getSlab5()) {
				baseRate = (rateMaster.getSlab2() * rateMaster.getSlabRate1())
						+ (rateMaster.getSlabRate2() * rateMaster.getSlab3())
						+ (rateMaster.getSlabRate3() * rateMaster.getSlab4())
						+ (rateMaster.getSlabRate4() * rateMaster.getSlab5())
						+ ((compareParam - rateMaster.getSlab5()) * rateMaster.getSlabRate5());
			}
			break;
		default:
			break;
		}
		if (waterTaxCalculation != null) {
			waterTaxCalculation.setBaseRate(baseRate);
			// waterTaxCalculation.setTax(baseRate);
		}
		return baseRate;
	}

	/**
	 * This Method is For Setting Mandatory Fields of WaterRateMaster
	 * 
	 * @param actualRate
	 * @param tempRate
	 * @param road
	 * @param master
	 * @return
	 */
	public static WaterRateMaster populateLOIForChangeOfOwner(WaterRateMaster actualRateMaster, TbKCsmrInfoMH master,
			Organisation org) {

		if (CollectionUtils.isNotEmpty(actualRateMaster.getDependsOnFactorList())) {
			actualRateMaster.getDependsOnFactorList().forEach(dependFactors -> {

				if (StringUtils.equalsIgnoreCase(dependFactors, "NOF")) {
					if (master.getNoOfFamilies() != null)
						actualRateMaster.setNoOfFamilies(master.getNoOfFamilies().intValue());
				}
				if (StringUtils.equalsIgnoreCase(dependFactors, "NOU")) {
					if (master.getCsNoofusers() != null) {
						actualRateMaster.setNoOfRoomsORTabel(master.getCsNoofusers().doubleValue());
					}
				}
				if (StringUtils.equalsIgnoreCase(dependFactors, "BPL")) {
					actualRateMaster.setIsBPL(master.getBplFlag());
				}
				if (StringUtils.equalsIgnoreCase(dependFactors, "WTC")) {
					if (master.getTrmGroup1() != null) {
						actualRateMaster.setUsageSubtype1(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup1(), org).getDescLangFirst());
					}
					if (master.getTrmGroup2() != null) {
						actualRateMaster.setUsageSubtype2(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup2(), org).getDescLangFirst());
					}
					if (master.getTrmGroup3() != null) {
						actualRateMaster.setUsageSubtype3(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup3(), org).getDescLangFirst());
					}
					if (master.getTrmGroup4() != null) {
						actualRateMaster.setUsageSubtype4(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup4(), org).getDescLangFirst());
					}
					if (master.getTrmGroup5() != null) {
						actualRateMaster.setUsageSubtype5(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup5(), org).getDescLangFirst());
					}
				}
				if (StringUtils.equalsIgnoreCase(dependFactors, "CON")) {
					if (master.getCsCcnsize() != null) {
						actualRateMaster.setConnectionSize(Double.valueOf(CommonMasterUtility
								.getNonHierarchicalLookUpObject(master.getCsCcnsize(), org).getDescLangFirst()));
					}
				}
				if (StringUtils.equalsIgnoreCase(dependFactors, "TAP")) {
					if (master.getCsTaxPayerFlag() == null || master.getCsTaxPayerFlag().isEmpty()) {
						actualRateMaster.setTaxPayer(MainetConstants.FlagN);
					} else {
						actualRateMaster.setTaxPayer(master.getCsTaxPayerFlag());
					}
				}
			});
		}

		actualRateMaster.setMeterType(getMeterTypeDesc(master.getCsMeteredccn(), org));
		actualRateMaster.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		return actualRateMaster;
	}

	/**
	 * This Method is For Setting Mandatory Fields of WaterRateMaster
	 * 
	 * @param actualRate
	 * @param tempRate
	 * @param road
	 * @param master
	 * @return
	 */
	public static WaterRateMaster populateLOIForWaterReconnection(WaterRateMaster actualRateMaster,
			TbKCsmrInfoMH master, Organisation org) {

		if (CollectionUtils.isNotEmpty(actualRateMaster.getDependsOnFactorList())) {
			actualRateMaster.getDependsOnFactorList().forEach(dependFactors -> {

				if (StringUtils.equalsIgnoreCase(dependFactors, "NOF")) {
					if (master.getNoOfFamilies() != null)
						actualRateMaster.setNoOfFamilies(master.getNoOfFamilies().intValue());
				}
				if (StringUtils.equalsIgnoreCase(dependFactors, "NOU")) {
					if (master.getCsNoofusers() != null) {
						actualRateMaster.setNoOfRoomsORTabel(master.getCsNoofusers().doubleValue());
					}
				}
				if (StringUtils.equalsIgnoreCase(dependFactors, "BPL")) {
					actualRateMaster.setIsBPL(master.getBplFlag());
				}
				if (StringUtils.equalsIgnoreCase(dependFactors, "CON")) {
					if (master.getCsCcnsize() != null) {
						actualRateMaster.setConnectionSize(Double.valueOf(CommonMasterUtility
								.getNonHierarchicalLookUpObject(master.getCsCcnsize(), org).getDescLangFirst()));
					}
					if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
						if (master.getTrmGroup1() != null) {
							actualRateMaster.setUsageSubtype1(CommonMasterUtility
									.getHierarchicalLookUp(master.getTrmGroup1(), org).getDescLangFirst());
						}
						if (master.getTrmGroup2() != null) {
							actualRateMaster.setUsageSubtype2(CommonMasterUtility
									.getHierarchicalLookUp(master.getTrmGroup2(), org).getDescLangFirst());
						}
					}
				}

				if (StringUtils.equalsIgnoreCase(dependFactors, "TAP")) {
					if (master.getCsTaxPayerFlag() == null || master.getCsTaxPayerFlag().isEmpty()) {
						actualRateMaster.setTaxPayer(MainetConstants.FlagN);
					} else {
						actualRateMaster.setTaxPayer(master.getCsTaxPayerFlag());
					}
				}

				if (StringUtils.equalsIgnoreCase(dependFactors, "WTC")) {
					if (master.getTrmGroup1() != null) {
						actualRateMaster.setUsageSubtype1(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup1(), org).getDescLangFirst());
					}
					if (master.getTrmGroup2() != null) {
						actualRateMaster.setUsageSubtype2(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup2(), org).getDescLangFirst());
					}
					if (master.getTrmGroup3() != null) {
						actualRateMaster.setUsageSubtype3(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup3(), org).getDescLangFirst());
					}
					if (master.getTrmGroup4() != null) {
						actualRateMaster.setUsageSubtype4(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup4(), org).getDescLangFirst());
					}
					if (master.getTrmGroup5() != null) {
						actualRateMaster.setUsageSubtype5(CommonMasterUtility
								.getHierarchicalLookUp(master.getTrmGroup5(), org).getDescLangFirst());
					}
				}
			});
		}

		actualRateMaster.setMeterType(getMeterTypeDesc(master.getCsMeteredccn(), org));
		actualRateMaster.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		return actualRateMaster;
	}

	public static void sendSMSandEMail(final ApplicantDetailDTO applicantDto, final Long applicationNo,
			final Double payAmount, final String serviceShortCode, final Organisation organisation) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		if (!StringUtils.isEmpty(serviceShortCode)) {
			ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
					.getServiceMasterByShortCode(serviceShortCode, organisation.getOrgid());
			dto.setServName(sm.getSmServiceName());
		}
		dto.setAppNo(applicationNo.toString());
		dto.setEmail(applicantDto.getEmailId());
		dto.setMobnumber(applicantDto.getMobileNo());
		dto.setAppName(getApplicantFullName(applicantDto));
		if (payAmount != null)
			dto.setAppAmount(payAmount.toString());
		dto.setDeptShortCode(MainetConstants.DEPT_SHORT_NAME.WATER);
		dto.setLangId(applicantDto.getLangId());
		// Added Changes As per told by Rajesh Sir For Sms and Email
		dto.setUserId(applicantDto.getUserId());
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.WATER,
				MainetConstants.URLBasedOnShortCode.valueOf(serviceShortCode).getUrl(),
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, organisation, applicantDto.getLangId());
	}

	private static String getApplicantFullName(final ApplicantDetailDTO applicantDto) {
		final StringBuilder builder = new StringBuilder();
		builder.append(applicantDto.getApplicantFirstName() == null ? "" : applicantDto.getApplicantFirstName());
		builder.append(MainetConstants.WHITE_SPACE);
		builder.append(applicantDto.getApplicantMiddleName() == null ? "" : applicantDto.getApplicantMiddleName());
		builder.append(MainetConstants.WHITE_SPACE);
		builder.append(applicantDto.getApplicantLastName() == null ? "" : applicantDto.getApplicantLastName());
		return builder.toString();
	}

}