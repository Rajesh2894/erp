/**
 * 
 */
package com.abm.mainet.swm.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.swm.dto.CollectorReqDTO;
import com.abm.mainet.swm.ui.validator.WasteCollectorValidator;

/**
 * @author sarojkumar.yadav
 *
 */
@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION)
public class WasteCollectorModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 7416484007614003461L;
    /**
     * 
     */

    private CollectorReqDTO collectorReqDTO = new CollectorReqDTO();
    private List<DocumentDetailsVO> checkList = new ArrayList<>();
    private Double charges = 0.0d;
    private String free = "O";
    private boolean enableSubmit;
    private boolean enableCheckList = true;
    private boolean accept;
    private String isFree;
    private List<ChargeDetailDTO> chargesInfo;
    private double amountToPay;
    private Long deptId;
    private Long applicationNo;
    private String docName;
    private Long serviceId;
    private ServiceMaster service;
    private List<LocationDTO> locList = new ArrayList<>();

    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private IPortalServiceMasterService iPortalServiceMasterService;

    public CollectorReqDTO getCollectorReqDTO() {
        return collectorReqDTO;
    }

    public void setCollectorReqDTO(CollectorReqDTO collectorReqDTO) {
        this.collectorReqDTO = collectorReqDTO;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public boolean isEnableSubmit() {
        return enableSubmit;
    }

    public void setEnableSubmit(boolean enableSubmit) {
        this.enableSubmit = enableSubmit;
    }

    public boolean isEnableCheckList() {
        return enableCheckList;
    }

    public void setEnableCheckList(boolean enableCheckList) {
        this.enableCheckList = enableCheckList;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public List<ChargeDetailDTO> getChargesInfo() {
        return chargesInfo;
    }

    public void setChargesInfo(List<ChargeDetailDTO> chargesInfo) {
        this.chargesInfo = chargesInfo;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(Long applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public ServiceMaster getService() {
        return service;
    }

    public void setService(ServiceMaster service) {
        this.service = service;
    }

    public List<LocationDTO> getLocList() {
        return locList;
    }

    public void setLocList(List<LocationDTO> locList) {
        this.locList = locList;
    }

    @Override
    public boolean saveForm() {

        CommonChallanDTO offline = getOfflineDTO();
        offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
        validateBean(this, WasteCollectorValidator.class);
        if ((getOfflineDTO().getOnlineOfflineCheck() == null) || getOfflineDTO().getOnlineOfflineCheck().isEmpty()) {
            addValidationError(getAppSession().getMessage("Payment Mode is Changed"));
        }

        if (!accept) {
            addValidationError(getAppSession().getMessage("Please accept Terms & Conditions"));
        }
        if (hasValidationErrors()) {
            return false;
        }
        offline.setApplNo(getApplicationNo());
        offline.setAmountToPay(Double.toString(getAmountToPay()));
        offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        offline.setLangId(UserSession.getCurrent().getLanguageId());
        offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setEmailId(getCollectorReqDTO().getApplicantDetailDto().getEmailId());
        offline.setApplicantName(getCollectorReqDTO().getApplicantDetailDto().getApplicantFirstName() + MainetConstants.WHITE_SPACE + getCollectorReqDTO().getApplicantDetailDto().getApplicantMiddleName() + MainetConstants.WHITE_SPACE + getCollectorReqDTO().getApplicantDetailDto().getApplicantLastName());
        offline.setMobileNumber(getCollectorReqDTO().getApplicantDetailDto().getMobileNo());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        offline.setServiceId(getServiceId());
        offline.setDeptId(getDeptId());
        for (final ChargeDetailDTO chargeDetailDTO : getChargesInfo()) {
            offline.getFeeIds().put(chargeDetailDTO.getChargeCode(), chargeDetailDTO.getChargeAmount());
        }
        if ((getOfflineDTO().getOnlineOfflineCheck() != null) && !getOfflineDTO().getOnlineOfflineCheck().equalsIgnoreCase("Y")) {
            offline = iChallanService.generateChallanNumber(offline);
            setOfflineDTO(offline);
        }
        return true;
    }

    public boolean validateInputs() {
        boolean status = true;
        validateBean(this, WasteCollectorValidator.class);

        if (hasValidationErrors()) {
            status = false;
        }
        return status;
    }

    @Override
    public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PaymentRequestDTO payURequestDTO) {

        final CollectorReqDTO bookingReqDTO = getCollectorReqDTO();
        final ApplicantDetailDTO applicantDetailDTO = bookingReqDTO.getApplicantDetailDto();
        String userName = (applicantDetailDTO.getApplicantFirstName() == null ? " " : applicantDetailDTO.getApplicantFirstName()) + " ";
        userName += applicantDetailDTO.getApplicantMiddleName() == null ? " " : applicantDetailDTO.getApplicantMiddleName() + " ";
        userName += applicantDetailDTO.getApplicantLastName() == null ? " " : applicantDetailDTO.getApplicantLastName();
        final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(), UserSession.getCurrent().getOrganisation().getOrgid());
        payURequestDTO.setUdf3("CitizenHome.html");
        payURequestDTO.setUdf5(portalServiceMaster.getShortName());
        payURequestDTO.setUdf7(String.valueOf(getApplicationNo()));
        payURequestDTO.setApplicantName(userName);
        payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
        payURequestDTO.setUdf2(String.valueOf(getApplicationNo()));
        payURequestDTO.setMobNo(bookingReqDTO.getApplicantDetailDto().getMobileNo());
        payURequestDTO.setDueAmt(new BigDecimal(getAmountToPay()));
        payURequestDTO.setEmail(applicantDetailDTO.getEmailId());
        payURequestDTO.setApplicationId(getApplicationNo().toString());
        // Adding department Id in udf10 of payURequestDTO object
        if (portalServiceMaster != null) {
            payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
            } else {
                payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
            }
        }
    }
}
