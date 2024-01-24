package com.abm.mainet.common.pg.ui.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.pg.dto.RePaymentDTO;
import com.abm.mainet.common.service.IApplicationService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;

@Component
@Scope(value = "session")
public class RePaymentFormModel extends AbstractFormModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1623519464087697248L;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;
	@Autowired
	private IApplicationService iApplicationService;

	private RePaymentDTO dto = new RePaymentDTO();

	public RePaymentDTO getDto() {
		return dto;
	}

	public void setDto(RePaymentDTO dto) {
		this.dto = dto;
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
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
			setChallanDtoandSaveChallanData(offline, getDto());
		}
		return true;
	}

	
		private void setChallanDtoandSaveChallanData(CommonChallanDTO offline,
				final RePaymentDTO rePaymt) {
	        //Defect #134500 for orgid dependancy in DSCL
			Long orgId = null;
		
		if ((rePaymt != null && rePaymt.getOrgId() != null) && Utility
				.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.DSCL)) {
			orgId = rePaymt.getOrgId();
		} else {
				orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			}
		 if(StringUtils.isNumeric(rePaymt.getRefId()))
			offline.setApplNo(Long.valueOf(rePaymt.getRefId()));
			offline.setAmountToPay(rePaymt.getAmount()+"");
			offline.setEmailId(rePaymt.getEmail());
			offline.setApplicantName(rePaymt.getPayeeName());
			offline.setMobileNumber(rePaymt.getPhoneNo());
			offline.setChallanServiceType(rePaymt.getChallanServiceType());
			offline.setServiceId(rePaymt.getServiceId());
			offline.setDocumentUploaded(false);
			offline.setFlatNo(rePaymt.getFlatNo());

			offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			offline.setOrgId(orgId);
			offline.setLangId(UserSession.getCurrent().getLanguageId());
			offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

			offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
			offline.setUniquePrimaryId(rePaymt.getRefId());
			final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(), orgId);
			offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
			offline.setServiceName(portalServiceMaster.getServiceName());
			if(StringUtils.equalsIgnoreCase(portalServiceMaster.getShortName(), MainetConstants.ServiceCode.WATER_BILL_PAYMENT)) {
				Long conId=iApplicationService.getConnId(rePaymt.getRefId(), orgId);
				if(conId!=null)
					offline.setUniquePrimaryId(conId.toString());	
			}
			if (StringUtils.isNotEmpty(rePaymt.getFeeIds()) && rePaymt.getFeeIds().length() > 2)
				offline.setFeeIds(getFeeId(rePaymt.getFeeIds(), rePaymt.getAmount()));
			if(StringUtils.equals(rePaymt.getDocumentUploaded(), MainetConstants.FLAGY))
				offline.setDocumentUploaded(true);
			else
				offline.setDocumentUploaded(false);
			
			setOfflineDTO(offline);
		}
		
	

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
//Defect #132264 for orgid dependancy in DSCL
		Long orgId = null;
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.DSCL)) {
			orgId = iApplicationService.getOrgId(Long.valueOf(getDto().getRefId()));

		} else {
			orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		}
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(), orgId);
		payURequestDTO.setUdf1(portalServiceMaster.getServiceId().toString());
		payURequestDTO.setUdf2(String.valueOf(getDto().getRefId()));
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getPsmDpDeptCode());
		payURequestDTO.setUdf7(String.valueOf(getDto().getRefId()));
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setApplicationId(getDto().getRefId());
		payURequestDTO.setDueAmt(new BigDecimal(getDto().getAmount()));
		payURequestDTO.setEmail(getDto().getEmail());
		payURequestDTO.setMobNo(getDto().getPhoneNo());
		payURequestDTO.setApplicantName(getDto().getPayeeName());
		payURequestDTO.setUdf10(String.valueOf(portalServiceMaster.getPsmDpDeptid()));
		if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
			payURequestDTO.setServiceName(getDto().getServiceName());
		} else {
			payURequestDTO.setServiceName(getDto().getServiceNameMar());
		}
		payURequestDTO.setFlatNo(getDto().getFlatNo());
	}
	private Map<Long, Double> getFeeId(String feeIds, double amt) {

		String feeIds1 = feeIds.replace("{", "").replace("}", "");
		String[] arr = feeIds1.split(",");
		Map<Long, Double> map = new HashMap();
		if (arr != null && arr.length > 0) {
			for (String s : arr) {
				String[] s1 = s.split("=");

				map.put(Long.valueOf(s1[0].toString().trim()), Double.valueOf(s1[1].toString().trim()));
			}
		} else if (amt > 0) {
			map.put(1L, amt);
		}
		return map;
	}
}
