package com.abm.mainet.common.pg.ui.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.pg.dto.LoiPaymentSearchDTO;
import com.abm.mainet.common.service.IApplicationService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session")
public class PendingLoiFormModel extends AbstractFormModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5327674309057376136L;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@Autowired
	private IChallanService iChallanService;
	@Autowired
	private IApplicationService iApplicationService;

	private Long taskId;

	private LoiPaymentSearchDTO dto = new LoiPaymentSearchDTO();

	public LoiPaymentSearchDTO getDto() {
		return dto;
	}

	public void setDto(LoiPaymentSearchDTO dto) {
		this.dto = dto;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		Long orgId = null;
       //Defect #134500 for orgid dependancy in DSCL
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.DSCL)) {
			orgId = iApplicationService.getOrgId(Long.valueOf(getDto().getApplicationId()));

		} else {
			orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		}
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(), orgId);
		payURequestDTO.setUdf1(portalServiceMaster.getServiceId().toString());
		payURequestDTO.setUdf2(String.valueOf(getDto().getApplicationId()));
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getPsmDpDeptCode());
		payURequestDTO.setUdf7(String.valueOf(getDto().getApplicationId()));
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setApplicationId(getDto().getApplicationId().toString());
		payURequestDTO.setDueAmt(getDto().getLoiMasData().getLoiAmount());
		payURequestDTO.setEmail(getDto().getEmail());
		payURequestDTO.setMobNo(getDto().getMobileNo());
		payURequestDTO.setApplicantName(getDto().getApplicantName());
		payURequestDTO.setUdf10(String.valueOf(portalServiceMaster.getPsmDpDeptid()));
		payURequestDTO.setOrgId(orgId);
		if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
			payURequestDTO.setServiceName(getDto().getServiceName());
		} /*
			 * else { payURequestDTO.setServiceName(getDto().getServiceNameMar()); }
			 */
	}

	@Override
	public boolean saveForm() {

		logger.info("start the  saveForm()");
		final CommonChallanDTO offline = getOfflineDTO();
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		validateBean(offline, CommonOfflineMasterValidator.class);

		if (hasValidationErrors()) {
			return false;
		}
		setChallanDtoandSaveChallanData(offline, getDto());
		return true;
	}

	private void setChallanDtoandSaveChallanData(CommonChallanDTO offline,
			final LoiPaymentSearchDTO loiPaymentSearchDTO) {
        //Defect #134500 for orgid dependancy in DSCL
		Long orgId = null;
		if ((loiPaymentSearchDTO != null && loiPaymentSearchDTO.getOrgId() != null) && Utility
				.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.DSCL)) {
			orgId = loiPaymentSearchDTO.getOrgId();
		} else {
			orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		}
		offline.setTaskId(this.getTaskId());
		offline.setApplNo(loiPaymentSearchDTO.getLoiMasData().getLoiApplicationId());
		offline.setAmountToPay(loiPaymentSearchDTO.getLoiMasData().getLoiAmount().toString());
		offline.setEmailId(loiPaymentSearchDTO.getEmail());
		offline.setApplicantName(loiPaymentSearchDTO.getApplicantName());
		offline.setMobileNumber(loiPaymentSearchDTO.getMobileNo());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(getServiceId());
		offline.setDocumentUploaded(false);
		offline.setLoiNo(loiPaymentSearchDTO.getLoiMasData().getLoiNo());

		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setOrgId(orgId);
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		if (loiPaymentSearchDTO.getLoiCharges() != null) {
			for (final Entry<Long, Double> entry : loiPaymentSearchDTO.getLoiCharges().entrySet()) {
				offline.getFeeIds().put(entry.getKey(), entry.getValue());
			}
		}
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(), orgId);
		offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
		offline.setServiceId(portalServiceMaster.getServiceId());
		offline.setServiceName(portalServiceMaster.getServiceName());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.Property.MUT)
                && MainetConstants.Property.MUT.equalsIgnoreCase(portalServiceMaster.getShortName())) {
			setPropertyNoForLOIPayment(offline);
		}
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			offline = iChallanService.generateChallanNumber(offline);
		}
		setOfflineDTO(offline);
	}

	private void setPropertyNoForLOIPayment(CommonChallanDTO offline) {
        CommonChallanDTO commonChallanDTO = null;
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(offline,
        		ServiceEndpoints.PROPERTY_URL.PROP_BY_APP_ID);
        final JSONObject jsonObject = new JSONObject(responseVo);
            if (jsonObject != null) {
            	if(jsonObject.get("uniquePrimaryId") != null) {
            		offline.setUniquePrimaryId(jsonObject.get("uniquePrimaryId").toString());            		
            	}
            	if(jsonObject.has("flatNo") && jsonObject.get("flatNo") != null) {
            		offline.setFlatNo(jsonObject.get("flatNo").toString());            		
            	}
            }
        }
    
}
