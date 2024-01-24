package com.abm.mainet.water.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.FinYearDTORespDTO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.water.dto.NoDueCerticateDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.dto.WaterRateMaster;
import com.abm.mainet.water.ui.model.NoDuesCertificateModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NoDuesCertificateServiceImpl implements INoDuesCertificateService {

	private static Logger LOGGER = Logger.getLogger(NoDuesCertificateServiceImpl.class);

	private static String saveUrl = ServiceEndpoints.ServiceCallURI.NO_DUE_CERT_SAVE;
	private static String conenctionUrl = ServiceEndpoints.ServiceCallURI.NO_DUE_CERT_CONNECTION_DET;
	private static String finUrl = ServiceEndpoints.ServiceCallURI.FINANCIAL_YEAR;
	private static String noDuesPrint = ServiceEndpoints.ServiceCallURI.NO_DUE_PRINT_CERT;
	@Autowired
	private IPortalServiceMasterService iPortalService;

	@Override
	public NoDuesCertificateReqDTO getConnectionDetail(final UserSession usersession, final String consumerNo,
			final NoDuesCertificateReqDTO noDuesCertificateReqDTO,
			final NoDuesCertificateModel noDuesCertificateModel) {

		try {

			final NoDuesCertificateReqDTO requestDTO = new NoDuesCertificateReqDTO();
			requestDTO.setApplicantDTO(noDuesCertificateModel.getApplicantDetailDto());
			requestDTO.setConsumerNo(consumerNo);
			requestDTO.setOrgId(noDuesCertificateModel.getOrgId());
			requestDTO.setServiceId(noDuesCertificateModel.getServiceId());
			requestDTO.setUserId(usersession.getEmployee().getEmpId());
			requestDTO.setEmpId(usersession.getEmployee().getEmpId());

			@SuppressWarnings("unchecked")
			final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
					.callRestTemplateClient(requestDTO,
							conenctionUrl);
			final String d = new JSONObject(responseVo).toString();

			final NoDuesCertificateRespDTO resDTO = new ObjectMapper().readValue(d, NoDuesCertificateRespDTO.class);
			if ((resDTO != null) && (resDTO.getStatus() != null)
					&& resDTO.getStatus().equalsIgnoreCase(MainetConstants.WebServiceStatus.SUCCESS)) {

				noDuesCertificateReqDTO.setConsumerName(resDTO.getConsumerName());
				if (resDTO.getConsumerAddress() != null) {
					noDuesCertificateReqDTO.setConsumerAddress(resDTO.getConsumerAddress());
				} else {
					noDuesCertificateReqDTO.setConsumerAddress(resDTO.getCsAdd());
				}
				if (resDTO.getDuesList() != null && !resDTO.getDuesList().isEmpty()) {
					for (final Map.Entry<String, Double> map : resDTO.getDuesList().entrySet()) {
						noDuesCertificateReqDTO.setWaterDues(map.getKey());
						noDuesCertificateReqDTO.setDuesAmount(map.getValue());
					}
				}
				noDuesCertificateReqDTO.setDues(resDTO.isDues());
				noDuesCertificateModel.setResponseDTO(resDTO);
				noDuesCertificateReqDTO.setApplicantDTO(noDuesCertificateModel.getApplicantDetailDto());
				noDuesCertificateReqDTO.setConsumerNo(consumerNo);
				noDuesCertificateReqDTO.setOrgId(noDuesCertificateModel.getOrgId());
				noDuesCertificateReqDTO.setServiceId(noDuesCertificateModel.getServiceId());
				noDuesCertificateReqDTO.setUserId(usersession.getEmployee().getEmpId());
				noDuesCertificateReqDTO.setEmpId(usersession.getEmployee().getEmpId());
				noDuesCertificateReqDTO.setMobileNo(resDTO.getCsContactno());
				noDuesCertificateModel.setCheckListApplFlag(resDTO.getCheckListApplFlag());
				noDuesCertificateModel.setConnectionSize(resDTO.getCsCcnsize());
				if (resDTO.getEmail() != null && !resDTO.getEmail().isEmpty()) {
					noDuesCertificateReqDTO.setEmail(resDTO.getEmail());
				}
				if (resDTO.isBillGenerated()) {
					noDuesCertificateReqDTO.setBillGenerated(true);
				} else {
					noDuesCertificateReqDTO.setBillGenerated(false);
				}
			} else {
				noDuesCertificateReqDTO.setError(true);
			}

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in getConnectionDetail()", exception);

		}
		return null;
	}

	@Override
	public NoDuesCertificateRespDTO saveForm(final NoDuesCertificateReqDTO certificateReqDTO) {

		NoDuesCertificateRespDTO respDTO = null;
		try {
			@SuppressWarnings("unchecked")
			final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
					.callRestTemplateClient(certificateReqDTO, saveUrl);
			final String d = new JSONObject(responseVo).toString();

			respDTO = new ObjectMapper().readValue(d, NoDuesCertificateRespDTO.class);
			if ((respDTO != null) && (respDTO.getStatus() != null)
					&& respDTO.getStatus().equalsIgnoreCase(MainetConstants.WebServiceStatus.SUCCESS)) {
				return respDTO;
			}

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in saevForm()", exception);
		}
		return respDTO;
	}

	@Override
	public FinYearDTORespDTO getFinancialYear(final RequestDTO dto) {
		try {
			@SuppressWarnings("unchecked")
			final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
					.callRestTemplateClient(dto, finUrl);
			final String d = new JSONObject(responseVo).toString();
			final FinYearDTORespDTO respDTO = new ObjectMapper().readValue(d, FinYearDTORespDTO.class);
			if ((respDTO != null) && (respDTO.getStatus() != null)
					&& respDTO.getStatus().equals(MainetConstants.SUCCESS)) {
				return respDTO;
			}

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in getFinancialYear()", exception);
		}
		return null;
	}

	@Override
	public void populateCheckListModel(final NoDuesCertificateModel model, final CheckListModel checklistModel) {

		checklistModel.setOrgId(model.getOrgId());
		checklistModel.setServiceCode(MainetConstants.ServiceShortCode.WATER_NO_DUES);
		checklistModel.setIsBPL(model.getApplicantDetailDto().getIsBPL());
		checklistModel.setDeptCode(MainetConstants.DeptCode.WATER);
		checklistModel.setUsageSubtype1(model.getResponseDTO().getUsageSubtype1());
		checklistModel.setUsageSubtype2(model.getResponseDTO().getUsageSubtype2());
		checklistModel.setUsageSubtype3(model.getResponseDTO().getUsageSubtype3());
		checklistModel.setUsageSubtype4(model.getResponseDTO().getUsageSubtype4());
		checklistModel.setUsageSubtype5(model.getResponseDTO().getUsageSubtype5());
		/*
		 * checklistModel.setApplicantType(model.getResponseDTO().getApplicantType());
		 */

	}

	@Override
	public WaterRateMaster populateChargeModel(final NoDuesCertificateModel model, final WaterRateMaster chargeModel) {
		chargeModel.setOrgId(model.getOrgId());
		chargeModel.setDeptCode(MainetConstants.DeptCode.WATER);
		chargeModel.setServiceCode(MainetConstants.ServiceShortCode.WATER_NO_DUES);
		chargeModel.setIsBPL(model.getApplicantDetailDto().getIsBPL());
		chargeModel.setNoOfCopies(model.getReqDTO().getNoOfCopies().intValue());
		chargeModel.setConnectionSize(Double.valueOf(CommonMasterUtility
				.getNonHierarchicalLookUpObject(model.getConnectionSize(), UserSession.getCurrent().getOrganisation())
				.getDescLangFirst()));
		chargeModel.setRateStartDate(new Date().getTime());
		return chargeModel;
	}

	private String getSubCategoryDesc(final String taxsubCategory, final Organisation org) {
		String subCategoryDesc = MainetConstants.BLANK;
		final List<LookUp> subCategryLookup = CommonMasterUtility.getLevelData(PrefixConstants.WATERMODULEPREFIX.TAC,
				MainetConstants.INDEX.TWO, org);
		for (final LookUp lookup : subCategryLookup) {
			if (lookup.getLookUpCode().equals(taxsubCategory)) {
				subCategoryDesc = lookup.getDescLangFirst();
			}
		}
		return subCategoryDesc;
	}

	@Override
	public void setCommonField(final Organisation organisation, final NoDuesCertificateModel noDuesCertificateModel) {

		final Long orgId = organisation.getOrgid();
		noDuesCertificateModel.getReqDTO().setOrgId(orgId);
		noDuesCertificateModel.setOrgId(orgId);
		final Long serviceId = iPortalService.getServiceId(MainetConstants.ServiceShortCode.WATER_NO_DUES,
				organisation.getOrgid());
		final Long deptId = iPortalService.getServiceDeptIdId(serviceId);
		noDuesCertificateModel.setServiceId(serviceId);
		noDuesCertificateModel.setServiceId(serviceId);
		noDuesCertificateModel.setDeptId(deptId);
		noDuesCertificateModel.getReqDTO().setDeptId(deptId);
		final Long langId = (long) UserSession.getCurrent().getLanguageId();
		noDuesCertificateModel.setLangId(langId);

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean setPaymentDetail(CommonChallanDTO offline, final NoDuesCertificateModel noDuesCertificateModel,
			final UserSession userSession) {
		boolean setFalg = false;
		try {
			LinkedHashMap<Long, Object> responseChallan = null;
			if (((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO))
					|| (noDuesCertificateModel.getCharges() > 0d)) {
				offline.setApplNo(noDuesCertificateModel.getResponseDTO().getApplicationNo());
				offline.setAmountToPay(noDuesCertificateModel.getCharges().toString());
				offline.setUserId(userSession.getEmployee().getEmpId());
				offline.setOrgId(userSession.getOrganisation().getOrgid());
				offline.setEmpType(userSession.getEmployee().getEmplType());
				offline.setLangId(userSession.getLanguageId());
				offline.setLgIpMac(userSession.getEmployee().getEmppiservername());
				offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
				offline.setServiceId(noDuesCertificateModel.getServiceId());
				offline.setApplicantName((noDuesCertificateModel.getApplicantDetailDto().getApplicantFirstName() != null
						? noDuesCertificateModel.getApplicantDetailDto().getApplicantFirstName() + " "
						: MainetConstants.BLANK)
						+ (noDuesCertificateModel.getApplicantDetailDto().getApplicantMiddleName() != null
								? noDuesCertificateModel.getApplicantDetailDto().getApplicantMiddleName() + " "
								: MainetConstants.WHITE_SPACE)
						+ (noDuesCertificateModel.getApplicantDetailDto().getApplicantLastName() != null
								? noDuesCertificateModel.getApplicantDetailDto().getApplicantLastName()
								: MainetConstants.BLANK));
				offline.setMobileNumber(noDuesCertificateModel.getApplicantDetailDto().getMobileNo());
				offline.setEmailId(noDuesCertificateModel.getApplicantDetailDto().getEmailId());
				for (final Map.Entry<Long, Double> entry : noDuesCertificateModel.getChargesMap().entrySet()) {
					offline.getFeeIds().put(entry.getKey(), entry.getValue());
				}
				final PortalService portalServiceMaster = iPortalService
						.getService(noDuesCertificateModel.getServiceId(), userSession.getOrganisation().getOrgid());
				offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
				offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
						offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
				if ((offline.getOnlineOfflineCheck() != null)
						&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {
					responseChallan = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(offline,
							ServiceEndpoints.PAYMENT_URL.CHALLAN_GENERATION_URL);
					final String data = new JSONObject(responseChallan).toString();

					offline = new ObjectMapper().readValue(data, CommonChallanDTO.class);
					noDuesCertificateModel.setOfflineDTO(offline);
				}
				setFalg = true;
			}

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in setPaymentDetail()", exception);

		}
		return setFalg;
	}

	@Override
	public void setPayRequestDTO(final PaymentRequestDTO payURequestDTO,
			final NoDuesCertificateModel noDuesCertificateModel, final UserSession userSession) {
		try {

			final NoDuesCertificateRespDTO responseDTO = noDuesCertificateModel.getResponseDTO();
			payURequestDTO.setApplicantName(responseDTO.getConsumerName().trim());
			final PortalService portalServiceMaster = iPortalService.getService(noDuesCertificateModel.getServiceId(),
					userSession.getOrganisation().getOrgid());
			payURequestDTO.setUdf1(responseDTO.getApplicationNo().toString());
			payURequestDTO.setUdf2(String.valueOf(responseDTO.getApplicationNo()));
			payURequestDTO.setUdf3("CitizenHome.html");
			payURequestDTO.setUdf7(String.valueOf(responseDTO.getApplicationNo()));
			payURequestDTO.setMobNo(responseDTO.getCsContactno());
			payURequestDTO.setApplicationId(responseDTO.getApplicationNo().toString());
			if (noDuesCertificateModel.getCharges() != null) {
				payURequestDTO.setDueAmt(new BigDecimal(noDuesCertificateModel.getCharges()));
			}
			payURequestDTO.setEmail(noDuesCertificateModel.getApplicantDetailDto().getEmailId());

			if (portalServiceMaster != null) {
				payURequestDTO.setUdf5(portalServiceMaster.getShortName());
				payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
				payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
					payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
				} else {
					payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
				}
			}

		} catch (final Exception exception) {
			throw new FrameworkException("Exception occur in setPayRequestDTO()", exception);
		}
	}

	@Override
	public NoDueCerticateDTO getNoDuesApplicationData(NoDuesCertificateReqDTO requestDTO) {
		NoDueCerticateDTO certificateDto = null;
		try {
			@SuppressWarnings("unchecked")
			final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall
					.callRestTemplateClient(requestDTO, noDuesPrint);
			final String d = new JSONObject(responseVo).toString();

			certificateDto = new ObjectMapper().readValue(d, NoDueCerticateDTO.class);
			if ((certificateDto != null) && (certificateDto.getStatus() != null)
					&& certificateDto.getStatus().equalsIgnoreCase(MainetConstants.WebServiceStatus.SUCCESS)) {
				return certificateDto;
			}

		} catch (final Exception exception) {
			LOGGER.error("Exception occur in getNoDuesApplicationData()", exception);
		}
		return certificateDto;
	}

}
