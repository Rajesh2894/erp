package com.abm.mainet.property.ui.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.PropertyBillPaymentDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.ui.validator.PropertyBillPaymentValidator;

@Component
@Scope("session")
public class PropertyBillPaymentModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;

    private Map<String, List<BillDisplayDto>> displayMap = new HashMap<>();

    private BillPaymentDetailDto billPayDto = new BillPaymentDetailDto();

    private PropertyBillPaymentDto propBillPaymentDto = new PropertyBillPaymentDto();

    private ProperySearchDto searchDto = new ProperySearchDto();

    private Double totalRebate;
    private List<String> flatNoList;
    private String billingMethod;
    private String occupierName;
    private String propReceiptDate;

    private String receiptDownloadValue;
    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private IPortalServiceMasterService iPortalServiceMasterService;

    @Override
    public boolean saveForm() {
        setCustomViewName("propertyBillPayment");
        final CommonChallanDTO offline = getOfflineDTO();
        BillPaymentDetailDto billPayDto = getBillPayDto();
        final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        validateBean(offline, CommonOfflineMasterValidator.class);
        validateBean(billPayDto, PropertyBillPaymentValidator.class);

        if (hasValidationErrors()) {
            return false;
        }
        /*
         * if (billPayDto.getTotalPaidAmt() <= 0) { addValidationError("Payable Amount Should be greater than 0"); return false; }
         */
        offline.setOfflinePaymentText(modeDesc);
        Map<Long, Double> details = new HashMap<>(0);
        final Map<Long, Long> billDetails = new HashMap<>(0);
        setChallanDToandSaveChallanData(offline, details, billDetails, billPayDto);
        return true;
    }

    private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final Map<Long, Double> details,
            final Map<Long, Long> billDetails, BillPaymentDetailDto billPayDto) {
        final UserSession session = UserSession.getCurrent();
        offline.setAmountToPay(Double.toString(billPayDto.getTotalPaidAmt()));
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

        if(StringUtils.isNotBlank(this.getBillingMethod()) && StringUtils.equals(MainetConstants.Property.INDIVIDUAL, this.getBillingMethod())) {
        	offline.setApplicantName(occupierName);
        }else {
        	offline.setApplicantName(billPayDto.getPrimaryOwnerName());
        }
        offline.setApplNo(billPayDto.getApplNo());
        offline.setApplicantAddress(billPayDto.getAddress());
        offline.setUniquePrimaryId(billPayDto.getPropNo());
        offline.setMobileNumber(billPayDto.getPrimaryOwnerMobNo());
        offline.setServiceId(billPayDto.getServiceId());
        offline.setDeptId(billPayDto.getDeptId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        offline.setDocumentUploaded(false);
        offline.setFlatNo(this.propBillPaymentDto.getFlatNo());
        offline.setOccupierName(occupierName);
        // Defect #93137
        if (billPayDto.getWard1() != null) {
            offline.getDwzDTO().setAreaDivision1(billPayDto.getWard1());
        }
        if (billPayDto.getWard2() != null) {
            offline.getDwzDTO().setAreaDivision2(billPayDto.getWard2());
        }
        if (billPayDto.getWard3() != null) {
            offline.getDwzDTO().setAreaDivision3(billPayDto.getWard3());
        }
        if (billPayDto.getWard4() != null) {
            offline.getDwzDTO().setAreaDivision4(billPayDto.getWard4());
        }
        if (billPayDto.getWard5() != null) {
            offline.getDwzDTO().setAreaDivision5(billPayDto.getWard5());

        }
        offline.setPlotNo(billPayDto.getPlotNo());
        offline.setApplicantFullName(billPayDto.getOwnerFullName());
        offline.setPdRv(billPayDto.getPdRv());
        offline.setPropNoConnNoEstateNoV(billPayDto.getPropNo());
        offline.setPropNoConnNoEstateNoL(getAppSession().getMessage("propertydetails.PropertyNo."));
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setUsageType(billPayDto.getUsageType1());
        offline.setReferenceNo(billPayDto.getOldpropno());
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

    	PortalService portalServiceMaster = null;
    	final Long serviceId = iPortalServiceMasterService.getServiceId(MainetConstants.Property.PROP_BILL_PAYMENT, billPayDto.getOrgId());
        portalServiceMaster = iPortalServiceMasterService.getService(serviceId, billPayDto.getOrgId());
        if(portalServiceMaster == null) {
        	portalServiceMaster = iPortalServiceMasterService.getService(billPayDto.getServiceId(), billPayDto.getOrgId());
        }
        payURequestDTO.setUdf3("CitizenHome.html");
        payURequestDTO.setUdf5(portalServiceMaster.getShortName());
        payURequestDTO.setUdf7(String.valueOf(billPayDto.getApplNo()));
        payURequestDTO.setOrgId(billPayDto.getOrgId());
        if(StringUtils.isNotBlank(this.getBillingMethod())&& this.getBillingMethod().equals("I") ) {
        	payURequestDTO.setApplicantName(this.getOccupierName());
        }else {
        payURequestDTO.setApplicantName(billPayDto.getPrimaryOwnerName());
        }
        payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
        payURequestDTO.setUdf2(String.valueOf(billPayDto.getApplNo()));
        payURequestDTO.setMobNo(billPayDto.getPrimaryOwnerMobNo());
        payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
        payURequestDTO.setDueAmt(new BigDecimal(billPayDto.getTotalPaidAmt()));
        payURequestDTO.setEmail(getApplicantDetailDto().getEmailId());
        if(StringUtils.isNotBlank(this.getBillingMethod())&& this.getBillingMethod().equals("I") ) {
        	payURequestDTO.setEmail(billPayDto.getEmailId());
        	payURequestDTO.setMobNo(billPayDto.getMobileNo());
        }
        //As per RJ Sir set that value to null 
        payURequestDTO.setUdf8(null);
        // In case of bill payment always property no should go
            payURequestDTO.setApplicationId(billPayDto.getPropNo());
        
        payURequestDTO.setFlatNo(this.getPropBillPaymentDto().getFlatNo());
        payURequestDTO.setUdf10(String.valueOf(billPayDto.getDeptId()));//setting deptId
        payURequestDTO.setAddField6(MainetConstants.Property.PROP_DEPT_SHORT_CODE);//Property department code
        payURequestDTO.setAddField7(MainetConstants.Property.PROP_BILL_PAYMENT);//Property bill payment short code
        payURequestDTO.setAddField8(MainetConstants.Property.PORTAL);//Identify call from portal side
    }

    public Map<String, List<BillDisplayDto>> getDisplayMap() {
        return displayMap;
    }

    public void setDisplayMap(Map<String, List<BillDisplayDto>> displayMap) {
        this.displayMap = displayMap;
    }

    public PropertyBillPaymentDto getPropBillPaymentDto() {
        return propBillPaymentDto;
    }

    public void setPropBillPaymentDto(PropertyBillPaymentDto propBillPaymentDto) {
        this.propBillPaymentDto = propBillPaymentDto;
    }

    public BillPaymentDetailDto getBillPayDto() {
        return billPayDto;
    }

    public void setBillPayDto(BillPaymentDetailDto billPayDto) {
        this.billPayDto = billPayDto;
    }

    public ProperySearchDto getSearchDto() {
        return searchDto;
    }

    public void setSearchDto(ProperySearchDto searchDto) {
        this.searchDto = searchDto;
    }

    public Double getTotalRebate() {
        return totalRebate;
    }

    public void setTotalRebate(Double totalRebate) {
        this.totalRebate = totalRebate;
    }

	public List<String> getFlatNoList() {
		return flatNoList;
	}

	public void setFlatNoList(List<String> flatNoList) {
		this.flatNoList = flatNoList;
	}

	public String getBillingMethod() {
		return billingMethod;
	}

	public void setBillingMethod(String billingMethod) {
		this.billingMethod = billingMethod;
	}

	public String getOccupierName() {
		return occupierName;
	}

	public void setOccupierName(String occupierName) {
		this.occupierName = occupierName;
	}

	public String getReceiptDownloadValue() {
		return receiptDownloadValue;
	}

	public void setReceiptDownloadValue(String receiptDownloadValue) {
		this.receiptDownloadValue = receiptDownloadValue;
	}

	public String getPropReceiptDate() {
		return propReceiptDate;
	}

	public void setPropReceiptDate(String propReceiptDate) {
		this.propReceiptDate = propReceiptDate;
	}

}
