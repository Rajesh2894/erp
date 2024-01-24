package com.abm.mainet.adh.ui.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.service.IADHBillMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;

@Component
@Scope("session")
public class ADHBillContractModel  extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
    private IADHBillMasService iADHBillMasService;
	
	@Autowired
    private IChallanService iChallanService;
	
	private ContractAgreementSummaryDTO contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
	
	   private String formFlag;

	   private Double payAmount;
	   
	   
	   @Override
	    public boolean saveForm() {

			if (getPayAmount() == null || getPayAmount() < 0 || getPayAmount() == 0) {
			    addValidationError(getAppSession().getMessage("adh.bill.validate.amount"));
			}
			if (getPayAmount() != null && getPayAmount() < contractAgreementSummaryDTO.getBillBalanceAmount()) {
			    addValidationError(getAppSession().getMessage("adh.bill.validate.pay.complete.installmant") + " Rs. "
				    + String.format("%.0f", contractAgreementSummaryDTO.getBillBalanceAmount()));
			}
			if (getPayAmount() !=null && getPayAmount() > getContractAgreementSummaryDTO().getBalanceAmount().doubleValue()) {
			    addValidationError(getAppSession().getMessage("adh.bill.validate.pay.balance.amount.only") + " " + " Rs. "
				    + +getContractAgreementSummaryDTO().getBalanceAmount().doubleValue());
			}
			if (hasValidationErrors()) {
			    return false;
			}
	    	contractAgreementSummaryDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	    	contractAgreementSummaryDTO.setInputAmount(getPayAmount());
	    	iADHBillMasService.updateBillPayment(contractAgreementSummaryDTO);
	        //setCustomViewName("propertyBillPayment");
	        final CommonChallanDTO offline = getOfflineDTO();
	        ContractAgreementSummaryDTO billPayDto = getContractAgreementSummaryDTO();
	        final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
	        offline.setOfflinePaymentText(modeDesc);
	        offline.setOfflinePaymentText(modeDesc);
	        Map<Long, Double> details = new HashMap<>(0);
	        final Map<Long, Long> billDetails = new HashMap<>(0);
	        setChallanDToandSaveChallanData(offline, details, billDetails, billPayDto);
	        setSuccessMessage(ApplicationSession.getInstance().getMessage("adh.bill.success.message"));
	        return true;
	    }
	    
	    
	    private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final Map<Long, Double> details,
	            final Map<Long, Long> billDetails, ContractAgreementSummaryDTO billPayDto) {
	        final UserSession session = UserSession.getCurrent();
	        offline.setAmountToPay(Double.toString(getPayAmount()));
	        offline.setUserId(session.getEmployee().getEmpId());
	        offline.setOrgId(billPayDto.getOrgId());
	        offline.setLangId(session.getLanguageId());
	        offline.setLgIpMac(session.getEmployee().getEmppiservername());
		    if ((details != null) && !details.isEmpty()) {
			    offline.setFeeIds(details);
		    }
		   if ((billDetails != null) && !billDetails.isEmpty()) {
			   offline.setBillDetIds(billDetails);
		    }

	        offline.setApplicantName(billPayDto.getContp2Name());
	        offline.setApplicantAddress(billPayDto.getAddress());
	        offline.setUniquePrimaryId(String.valueOf(billPayDto.getContId()));
	        offline.setMobileNumber(billPayDto.getMobileNo());
	        offline.setServiceId(billPayDto.getServiceId());
	        offline.setDeptId(billPayDto.getDeptId());
	        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
	        offline.setDocumentUploaded(false);
	        offline.setOfflinePaymentText(CommonMasterUtility
	                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
	                .getLookUpCode());
	        if ((offline.getOnlineOfflineCheck() != null)
	                && offline.getOnlineOfflineCheck().equals(
	                        MainetConstants.PAYMENT_TYPE.OFFLINE)) {
	            offline = iChallanService
	                    .generateChallanNumber(offline);
	        }
	        setOfflineDTO(offline);
	    }
	    
	    
	    @Override
	    public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PaymentRequestDTO payURequestDTO) {
	        payURequestDTO.setUdf3("CitizenHome.html");
	        payURequestDTO.setUdf5("ACP");
	        payURequestDTO.setUdf7(contractAgreementSummaryDTO.getContNo());
	        payURequestDTO.setOrgId(contractAgreementSummaryDTO.getOrgId());
	        payURequestDTO.setApplicantName(contractAgreementSummaryDTO.getContp2Name());
	        payURequestDTO.setServiceId(contractAgreementSummaryDTO.getServiceId());
	        payURequestDTO.setUdf2(contractAgreementSummaryDTO.getContNo());
	        payURequestDTO.setMobNo(contractAgreementSummaryDTO.getMobileNo());
	        payURequestDTO.setServiceName("Bill Payment");
	        payURequestDTO.setDueAmt(new BigDecimal(getPayAmount()));
	        payURequestDTO.setEmail(getApplicantDetailDto().getEmailId());
	        payURequestDTO.setApplicationId(contractAgreementSummaryDTO.getContNo());
	        
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

	public ContractAgreementSummaryDTO getContractAgreementSummaryDTO() {
		return contractAgreementSummaryDTO;
	}

	public void setContractAgreementSummaryDTO(ContractAgreementSummaryDTO contractAgreementSummaryDTO) {
		this.contractAgreementSummaryDTO = contractAgreementSummaryDTO;
	}
	   
	   

}
