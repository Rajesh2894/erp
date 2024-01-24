package com.abm.mainet.rnl.ui.model;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.rnl.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.rnl.service.IRLBILLMasterService;



/**
 * @author ritesh.patil
 *
 */
@Component
@Scope("session")
public class EstateContractBillPaymentModel extends AbstractFormModel {
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IChallanService iChallanService;
    
    @Autowired
    private IRLBILLMasterService iRLBILLMasterService;

    
    private ContractAgreementSummaryDTO contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
    private List<ContractAgreementSummaryDTO> contractAgreementList = new ArrayList<ContractAgreementSummaryDTO>();
    private List<Object[]> propertyDetails = new ArrayList<Object[]>();
    private String contractNo;
    private String propertyContractNo;
    private String formFlag = "N";
    private Double bmTotalAmount;
    private Double bmTotalBalAmount;
    private Double payAmount;
    private Double inputAmount;
    
    
    @Override
    public boolean saveForm() {
    	contractAgreementSummaryDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
    	contractAgreementSummaryDTO.setInputAmount(getInputAmount());
		//iRLBILLMasterService.updateBillPayment(contractAgreementSummaryDTO);
        //setCustomViewName("propertyBillPayment");
        final CommonChallanDTO offline = getOfflineDTO();
        ContractAgreementSummaryDTO billPayDto = getContractAgreementSummaryDTO();
        final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        offline.setOfflinePaymentText(modeDesc);
        Map<Long, Double> details = new HashMap<>(0);
        final Map<Long, Long> billDetails = new HashMap<>(0);
        setChallanDToandSaveChallanData(offline, details, billDetails, billPayDto);
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
        offline.setUniquePrimaryId(billPayDto.getContNo());
        offline.setMobileNumber(billPayDto.getMobileNo());
        offline.setServiceId(billPayDto.getServiceId());
        offline.setDeptId(billPayDto.getDeptId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        offline.setDocumentUploaded(false);
        offline.setApplNo(billPayDto.getContId());
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
        payURequestDTO.setUdf5("RBC");
        payURequestDTO.setUdf7(contractAgreementSummaryDTO.getContNo());
        payURequestDTO.setOrgId(contractAgreementSummaryDTO.getOrgId());
        payURequestDTO.setApplicantName(contractAgreementSummaryDTO.getContp2Name());
        payURequestDTO.setServiceId(contractAgreementSummaryDTO.getServiceId());
        payURequestDTO.setUdf2(contractAgreementSummaryDTO.getContNo());
        payURequestDTO.setMobNo(contractAgreementSummaryDTO.getMobileNo());
        payURequestDTO.setServiceName("Bill PAyment");
        payURequestDTO.setDueAmt(new BigDecimal(getPayAmount()));
        payURequestDTO.setEmail(getApplicantDetailDto().getEmailId());
        payURequestDTO.setApplicationId(contractAgreementSummaryDTO.getContNo());

    }
    
	public ContractAgreementSummaryDTO getContractAgreementSummaryDTO() {
		return contractAgreementSummaryDTO;
	}
	public void setContractAgreementSummaryDTO(ContractAgreementSummaryDTO contractAgreementSummaryDTO) {
		this.contractAgreementSummaryDTO = contractAgreementSummaryDTO;
	}
	public List<ContractAgreementSummaryDTO> getContractAgreementList() {
		return contractAgreementList;
	}
	public void setContractAgreementList(List<ContractAgreementSummaryDTO> contractAgreementList) {
		this.contractAgreementList = contractAgreementList;
	}
	public List<Object[]> getPropertyDetails() {
		return propertyDetails;
	}
	public void setPropertyDetails(List<Object[]> propertyDetails) {
		this.propertyDetails = propertyDetails;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getPropertyContractNo() {
		return propertyContractNo;
	}
	public void setPropertyContractNo(String propertyContractNo) {
		this.propertyContractNo = propertyContractNo;
	}
	public String getFormFlag() {
		return formFlag;
	}
	public void setFormFlag(String formFlag) {
		this.formFlag = formFlag;
	}
	public Double getBmTotalAmount() {
		return bmTotalAmount;
	}
	public void setBmTotalAmount(Double bmTotalAmount) {
		this.bmTotalAmount = bmTotalAmount;
	}
	public Double getBmTotalBalAmount() {
		return bmTotalBalAmount;
	}
	public void setBmTotalBalAmount(Double bmTotalBalAmount) {
		this.bmTotalBalAmount = bmTotalBalAmount;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Double getInputAmount() {
		return inputAmount;
	}
	public void setInputAmount(Double inputAmount) {
		this.inputAmount = inputAmount;
	}

}
