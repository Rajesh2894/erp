/**
 * 
 */
package com.abm.mainet.swm.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.swm.dto.CollectorReqDTO;
import com.abm.mainet.swm.dto.CollectorResDTO;
import com.abm.mainet.swm.dto.MRFMasterDto;
import com.abm.mainet.swm.dto.WasteCollectorDTO;
import com.abm.mainet.swm.service.IWasteCollectorService;
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
    private static final long serialVersionUID = -8819410126744702057L;
    private CollectorReqDTO collectorReqDTO = new CollectorReqDTO();
    private List<TbLocationMas> locList = new ArrayList<>();
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<MRFMasterDto> mrfList = new ArrayList<>();
    private Double charges = 0.0d;
    private String free = "O";
    private boolean enableSubmit;
    private boolean enableCheckList = true;
    private boolean accept;
    private String isFree;
    private List<ChargeDetailDTO> chargesInfo;
    private double amountToPay;
    private Long deptId;
    private ServiceMaster service;
    private String docName;
    private String vehicleNumber;

    @Autowired
    private IWasteCollectorService iWasteCollectorService;

    @Resource
    private IFileUploadService fileUpload;

    public CollectorReqDTO getCollectorReqDTO() {
        return collectorReqDTO;
    }

    public void setCollectorReqDTO(CollectorReqDTO collectorReqDTO) {
        this.collectorReqDTO = collectorReqDTO;
    }

    public List<TbLocationMas> getLocList() {
        return locList;
    }

    public void setLocList(List<TbLocationMas> locList) {
        this.locList = locList;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
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

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
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

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public ServiceMaster getService() {
        return service;
    }

    public void setService(ServiceMaster service) {
        this.service = service;
    }

    public List<MRFMasterDto> getMrfList() {
        return mrfList;
    }

    public void setMrfList(List<MRFMasterDto> mrfList) {
        this.mrfList = mrfList;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    @Override
    public boolean saveForm() {
        final CommonChallanDTO offline = getOfflineDTO();
        final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        List<DocumentDetailsVO> docs = getAttachments();
        docs = fileUpload.prepareFileUpload(docs);
        getCollectorReqDTO().setDocumentList(docs);
        validateBean(this, WasteCollectorValidator.class);
        validateBean(offline, CommonOfflineMasterValidator.class);

        if ((getOfflineDTO().getOnlineOfflineCheck() == null) || getOfflineDTO().getOnlineOfflineCheck().isEmpty()) {
            addValidationError(getAppSession().getMessage(ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.PAYMENT_MODE)));
        }
        if ((getOfflineDTO().getOnlineOfflineCheck() != null) && !getOfflineDTO().getOnlineOfflineCheck().equalsIgnoreCase("P")) {
            addValidationError(getAppSession().getMessage( ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.PAY_MODE_CHNG)));
        }
        if (!accept) {
            addValidationError(getAppSession().getMessage( ApplicationSession.getInstance().getMessage("construct.demolition.applicant.accept.required")));
        }
        if (hasValidationErrors()) {
            return false;
        }
        setChallanDToandSaveChallanData(offline, getCollectorReqDTO().getApplicantDetailDto());
        final CollectorReqDTO collectorReqDTO = getCollectorReqDTO();
        final WasteCollectorDTO collectorDTO = collectorReqDTO.getCollectorDTO();
        collectorDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        collectorDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        collectorDTO.setCreatedDate(new Date());
        collectorDTO.setLgIpMac(Utility.getMacAddress());
        collectorDTO.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        collectorReqDTO.setPayAmount(getAmountToPay()); // change by
        collectorReqDTO.setServiceId(service.getSmServiceId());
        collectorReqDTO.setDeptId(service.getTbDepartment().getDpDeptid());
        collectorReqDTO.setPayAmount(getAmountToPay());
        setCollectorReqDTO(collectorReqDTO);
        final CollectorResDTO bookingResDTO = iWasteCollectorService.saveCnDApplicantForm(collectorReqDTO, offline, service.getSmServiceName(), getTaskId(), getWorkflowActionDto());
        setReceiptDTO(bookingResDTO.getChallanReceiptPrintDTO());
        getOfflineDTO().setAmountToShow(getAmountToPay());
        setOfflineDTO(offline);
        setSuccessMessage(ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.APP_NO) + bookingResDTO.getApplicationNo()
                + ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.RECEIPT_STATUS));
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

    private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final ApplicantDetailDTO applicantDetailDTO) {

        offline.setAmountToPay(Double.toString(getAmountToPay()));
        offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        offline.setLangId(UserSession.getCurrent().getLanguageId());
        offline.setLgIpMac(Utility.getMacAddress());
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        offline.setEmailId(getCollectorReqDTO().getApplicantDetailDto().getEmailId());
        offline.setApplicantName(getCollectorReqDTO().getApplicantDetailDto().getApplicantFirstName()
                + MainetConstants.WHITE_SPACE + getCollectorReqDTO().getApplicantDetailDto().getApplicantLastName());
        offline.setMobileNumber(getCollectorReqDTO().getApplicantDetailDto().getMobileNo());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        offline.setServiceId(service.getSmServiceId());
        offline.setApplicantAddress(getCollectorReqDTO().getApplicantDetailDto().getAreaName());

        for (final ChargeDetailDTO chargeDetailDTO : getChargesInfo()) {
            offline.getFeeIds().put(chargeDetailDTO.getChargeCode(), getAmountToPay());
        }
        offline.setDeptId(service.getTbDepartment().getDpDeptid());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());

    }
}
