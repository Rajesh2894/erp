package com.abm.mainet.additionalservices.service;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.additionalservices.ui.model.DuplicatePaymentReceiptModel;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbReceiptDuplicateDTO;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.IDuplicateReceiptService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

@Service
public class DuplicatePaymentReceiptServiceImpl implements DuplicatePaymentReceiptService{

	private static final Logger LOGGER = LoggerFactory.getLogger(DuplicatePaymentReceiptServiceImpl.class);

	@Autowired
	private IChallanService challanService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private IDuplicateReceiptService duplicateReceiptService;
	
	private boolean flag=false;
	 String errorMsg=null;

	
	@Override
	@Transactional
	public TbCfcApplicationMst saveData(TbCfcApplicationMst applicationMst, CFCApplicationAddressEntity addressEntity,
			TbReceiptDuplicateDTO receiptDuplicateDto,
			DuplicatePaymentReceiptModel duplicatePaymentReceiptModel) {
		this.errorMsg=null;
		this.flag=false;
		final CommonChallanDTO offline = duplicatePaymentReceiptModel.getOfflineDTO();
		Long  apmApplicationId = null;
		ServiceMaster serviceMaster = serviceMasterService.getServiceMasterByShortCode("DRP", UserSession.getCurrent().getOrganisation().getOrgid());
		applicationMst.setSmServiceId(serviceMaster.getSmServiceId());
		RequestDTO requestDto = setApplicantRequestDto(addressEntity, applicationMst, serviceMaster);

			apmApplicationId = applicationService.createApplication(requestDto);
			applicationMst.setApmApplicationId(apmApplicationId);
			if (serviceMaster.getSmFeesSchedule().longValue() != 0l) {
				if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
					setChallanDToandSaveChallanData(offline, applicationMst, addressEntity, duplicatePaymentReceiptModel);
                   //User Story #147721
					if(this.flag) {
						applicationMst.setApmAppRejFlag(this.errorMsg);
						return applicationMst;
					}
				}
			}
			
		duplicateReceiptService.save(receiptDuplicateDto);
		LOGGER.info("saveData() Data saved and application id is: "+apmApplicationId);
		return applicationMst;
	}
	
	@Override
	@Transactional
	public void setChallanDToandSaveChallanData(CommonChallanDTO offline, TbCfcApplicationMst applicationMst,CFCApplicationAddressEntity addressEntity,DuplicatePaymentReceiptModel model) {

		ServiceMaster serviceMas = serviceMasterService.getServiceMaster(applicationMst.getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setApplNo(applicationMst.getApmApplicationId());
		if (applicationMst.getRefNo() != null)
		offline.setReferenceNo(applicationMst.getRefNo());
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setServiceId(serviceMas.getSmServiceId());

		String fullName = String.join(" ", Arrays.asList(applicationMst.getApmFname(), applicationMst.getApmMname(),
				applicationMst.getApmLname()));
		offline.setApplicantName(fullName);
		offline.setApplicantAddress(addressEntity.getApaAreanm());
		offline.setMobileNumber(addressEntity.getApaMobilno());
		offline.setEmailId(addressEntity.getApaEmail());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		
		if (model.getChargesInfo() != null) {
			for (ChargeDetailDTO dto : model.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
				offline.setAmountToPay(String.valueOf(dto.getChargeAmount()));
			}
		}	
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
		offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
		offline.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());

		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {
			final ChallanMaster responseChallan = challanService.InvokeGenerateChallan(offline);
			offline.setChallanNo(responseChallan.getChallanNo());
			offline.setChallanValidDate(responseChallan.getChallanValiDate());
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			final ChallanReceiptPrintDTO printDto = challanService.savePayAtUlbCounter(offline,
					serviceMas.getSmServiceName());
            //User Story #147721
			if(printDto!=null && (StringUtils.isNotBlank(printDto.getPushToPayErrMsg()) ) ){
				this.flag=true;
				
				errorMsg=printDto.getPushToPayErrMsg();
				return;
			}

			model.setReceiptDTO(printDto);
			model.setSuccessMessage(model.getAppSession().getMessage("adh.receipt"));
		}
		model.setOfflineDTO(offline);
	}
	
	
	private RequestDTO setApplicantRequestDto(CFCApplicationAddressEntity addressEntity,
			TbCfcApplicationMst applicationMst, ServiceMaster sm) {

		RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		if (addressEntity != null) {
		requestDto.setUserId(addressEntity.getUserId());
		requestDto.setOrgId(addressEntity.getOrgId().getOrgid());
		requestDto.setLangId(addressEntity.getLangId());
		requestDto.setMobileNo(addressEntity.getApaMobilno());
		requestDto.setAreaName(addressEntity.getApaAreanm());
	    }
		// setting applicant info
		if (applicationMst != null ) {
		requestDto.setfName(applicationMst.getApmFname().concat(MainetConstants.WHITE_SPACE)+applicationMst.getApmLname());
		requestDto.setEmail(addressEntity.getApaEmail());
		requestDto.setGender(applicationMst.getApmSex());
		requestDto.setTitleId(applicationMst.getApmTitle());
		}
		return requestDto;

	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
