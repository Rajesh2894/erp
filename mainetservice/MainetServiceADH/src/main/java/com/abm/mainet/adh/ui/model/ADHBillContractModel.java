package com.abm.mainet.adh.ui.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.domain.ADHBillMasEntity;
import com.abm.mainet.adh.service.IADHBillMasService;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;


/**
 * @author cherupelli.srikanth
 * @since 15 November 2019
 */
@Component
@Scope("session")
public class ADHBillContractModel extends AbstractFormModel {

    private static final long serialVersionUID = -5949231710069334568L;

    @Autowired
    private IADHBillMasService ADHBillMasService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    private IChallanService iChallanService;

    private ContractAgreementSummaryDTO contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();

    private List<ADHBillMasEntity> adhBillEnityList = new ArrayList<>();

    private ServiceMaster serviceMaster;

    private String formFlag;

    private Double payAmount;

    @Override
    public boolean saveForm() {
	boolean status = false;

	if (getPayAmount() == null || getPayAmount() < 0 || getPayAmount() == 0) {
	    addValidationError(getAppSession().getMessage("adh.bill.validate.amount"));
	}
	List<ADHBillMasEntity> adhBillMasEnityList = getAdhBillEnityList();
	if (getPayAmount() != null && getPayAmount() < adhBillMasEnityList.get(0).getBalanceAmount()) {
	    addValidationError(getAppSession().getMessage("adh.bill.validate.pay.complete.installmant") + " Rs. "
		    + +adhBillMasEnityList.get(0).getBalanceAmount());
	}
	//Defect #125452 null check condition added
	//Defect #175225 validation removed for PSCL
	if(!(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))) {
		if (getPayAmount() !=null && getPayAmount() > getContractAgreementSummaryDTO().getBalanceAmount().doubleValue()) {
		    addValidationError(getAppSession().getMessage("adh.bill.validate.pay.balance.amount.only") + " " + " Rs. "
			    + +getContractAgreementSummaryDTO().getBalanceAmount().doubleValue());
		}
	}
	final CommonChallanDTO offline = getOfflineDTO();
	final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
	offline.setOfflinePaymentText(modeDesc);
	validateBean(offline, CommonOfflineMasterValidator.class);
	if (hasValidationErrors()) {
	    return false;
	}

	Map<Long, Double> details = new HashMap<>(0);
	final Map<Long, Long> billDetails = new HashMap<>(0);
	setServiceMaster(serviceMasterService.getServiceByShortName(MainetConstants.AdvertisingAndHoarding.ACP,
		UserSession.getCurrent().getOrganisation().getOrgid()));
	details.put(adhBillMasEnityList.get(0).getTaxId(), getPayAmount());
	setChallanDToandSaveChallanData(offline, details, billDetails);
	if (getPayAmount().equals(adhBillMasEnityList.get(0).getBalanceAmount())) {
	    ADHBillMasEntity adhBillMas = adhBillMasEnityList.get(0);
	    adhBillMas.setPaidAmount(getPayAmount());
	    adhBillMas.setBalanceAmount(0d);
	    adhBillMas.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	    adhBillMas.setUpdatedDate(new Date());
	    adhBillMas.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
	    adhBillMas.setPaidFlag(MainetConstants.Common_Constant.YES);
	    adhBillMas.setBillType(MainetConstants.STATUS.INACTIVE);
	    ADHBillMasService.updateRLBillMas(adhBillMas);
	} else {

	    Double actualPayAmount = getPayAmount();
	    for (int i = 0; i < adhBillMasEnityList.size(); i++) {
		if (actualPayAmount > 0) {
		    ADHBillMasEntity adhBillMas = adhBillMasEnityList.get(i);
		    if (actualPayAmount > adhBillMas.getBalanceAmount()
			    || actualPayAmount.equals(adhBillMas.getBalanceAmount())) {
			actualPayAmount = actualPayAmount - adhBillMas.getBalanceAmount();
			adhBillMas.setPaidAmount(adhBillMas.getBalanceAmount());
			adhBillMas.setBalanceAmount(0d);
			adhBillMas.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			adhBillMas.setUpdatedDate(new Date());
			adhBillMas.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			adhBillMas.setPaidFlag(MainetConstants.Common_Constant.YES);
			adhBillMas.setBillType(MainetConstants.STATUS.INACTIVE);

			ADHBillMasService.updateRLBillMas(adhBillMas);

		    } else {
			adhBillMas.setPaidAmount(actualPayAmount);
			adhBillMas.setBalanceAmount(adhBillMas.getBalanceAmount() - actualPayAmount);
			actualPayAmount = 0.0;
			adhBillMas.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			adhBillMas.setUpdatedDate(new Date());
			adhBillMas.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			adhBillMas.setPaidFlag(MainetConstants.FlagN);
			ADHBillMasService.updateRLBillMas(adhBillMas);

		    }
		} else {
		    break;
		}

	    }
	}
	 BigDecimal sumOfCurrentAmt = new BigDecimal(0.00);
     
     BigDecimal arrearsAmt = new BigDecimal(0.00);
     BigDecimal arrearsAndCurrentAmt = new BigDecimal(0.00);
     List<ADHBillMasEntity> adhBillMasList = ADHBillMasService.finByContractId(
 			contractAgreementSummaryDTO.getContId(), UserSession.getCurrent().getOrganisation().getOrgid(),
 			MainetConstants.FlagN, MainetConstants.FlagB);
     if (CollectionUtils.isNotEmpty(adhBillMasList)) {
    	  
     for (final ADHBillMasEntity adhBillMastersDetail : adhBillMasList) {
	
		
		 Date dueDate = adhBillMastersDetail.getDueDate();
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
		 
		 
	
	 if((currentDate.compareTo(dueDate)<=0 && endDate.compareTo(dueDate)>0)) {
      	sumOfCurrentAmt = sumOfCurrentAmt.add(BigDecimal.valueOf(adhBillMastersDetail.getBillAmount()));
      	
      	 
      }
      if((currentDate.compareTo(dueDate)>0)) {
      	arrearsAmt = arrearsAmt.add(BigDecimal.valueOf(adhBillMastersDetail.getBillAmount()));
      	
      }
	 }
    }
	 arrearsAndCurrentAmt=arrearsAmt.add(sumOfCurrentAmt);
	 getReceiptDTO().setAmountPayable(arrearsAndCurrentAmt.doubleValue());
	 getReceiptDTO().setTotalAmountPayable(arrearsAndCurrentAmt.doubleValue());
	 getReceiptDTO().getPaymentList().get(0).setAmountPayable(arrearsAndCurrentAmt.doubleValue());
	setSuccessMessage(ApplicationSession.getInstance().getMessage("adh.bill.success.message"));
	
	status = true;
	return status;
    }

    private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
	    final Map<Long, Long> billDetails) {

	final UserSession userSession = UserSession.getCurrent();

	offline.setApplNo(getContractAgreementSummaryDTO().getContId());
	offline.setAmountToPay(String.valueOf(getPayAmount()));
	offline.setEmailId(getContractAgreementSummaryDTO().getEmailId());
	offline.setMobileNumber(getContractAgreementSummaryDTO().getMobileNo());
	offline.setApplicantAddress(getContractAgreementSummaryDTO().getAddress());
	offline.setApplicantName(getContractAgreementSummaryDTO().getContp2Name());
	offline.setApplicantFullName(getContractAgreementSummaryDTO().getContp2Name());
	offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
	offline.setDocumentUploaded(false);
	offline.setUniquePrimaryId(String.valueOf(getContractAgreementSummaryDTO().getContId()));

	offline.setPaymentCategory(MainetConstants.FlagB);// BILL_SCHEDULE_DATE

	offline.setUserId(userSession.getEmployee().getEmpId());
	offline.setOrgId(userSession.getOrganisation().getOrgid());
	offline.setLangId(userSession.getLanguageId());
	offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
	offline.setFaYearId(userSession.getFinYearId());
	offline.setFinYearStartDate(userSession.getFinStartDate());
	offline.setFinYearEndDate(userSession.getFinEndDate());
	// offline.getFeeIds().put(2151L, getPayAmount());
	if ((details != null) && !details.isEmpty()) {
	    offline.setFeeIds(details);
	}

	if ((billDetails != null) && !billDetails.isEmpty()) {
	    offline.setBillDetIds(billDetails);
	}
	offline.setServiceId(getServiceMaster().getSmServiceId());
	offline.setDeptId(getServiceMaster().getTbDepartment().getDpDeptid());
	offline.setOfflinePaymentText(CommonMasterUtility
		.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), userSession.getOrganisation())
		.getLookUpCode());

	if ((offline.getOnlineOfflineCheck() != null)
		&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
	    final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
	    offline.setChallanValidDate(master.getChallanValiDate());
	    offline.setChallanNo(master.getChallanNo());
	    setSuccessMessage(
		    ApplicationSession.getInstance().getMessage(MainetConstants.EstateContract.PRINT_CHALLAN_STATUS));
	} else if ((offline.getOnlineOfflineCheck() != null)
		&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
	    final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
		    getServiceMaster().getSmServiceName());
	 
	    setReceiptDTO(printDto);
	    setSuccessMessage(
		    ApplicationSession.getInstance().getMessage(MainetConstants.EstateContract.PAYMENT_RECEIPT_STATUS));
	}
	setOfflineDTO(offline);

    }

    public ContractAgreementSummaryDTO getContractAgreementSummaryDTO() {
	return contractAgreementSummaryDTO;
    }

    public void setContractAgreementSummaryDTO(ContractAgreementSummaryDTO contractAgreementSummaryDTO) {
	this.contractAgreementSummaryDTO = contractAgreementSummaryDTO;
    }

    public List<ADHBillMasEntity> getAdhBillEnityList() {
	return adhBillEnityList;
    }

    public void setAdhBillEnityList(List<ADHBillMasEntity> adhBillEnityList) {
	this.adhBillEnityList = adhBillEnityList;
    }

    public String getFormFlag() {
	return formFlag;
    }

    public void setFormFlag(String formFlag) {
	this.formFlag = formFlag;
    }

    public Double getPayAmount() {
	return payAmount;
    }

    public void setPayAmount(Double payAmount) {
	this.payAmount = payAmount;
    }

    public ServiceMaster getServiceMaster() {
	return serviceMaster;
    }

    public void setServiceMaster(ServiceMaster serviceMaster) {
	this.serviceMaster = serviceMaster;
    }

}
