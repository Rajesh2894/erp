package com.abm.mainet.cfc.objection.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.objection.dto.HearingInspectionDto;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.cfc.objection.ui.validator.ObjectionDetailsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@Component
@Scope("session")
public class ObjectionDetailsModel extends AbstractFormModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectionDetailsModel.class);
    @Autowired
    IObjectionDetailsService iObjectionDetailsService;

    @Resource
    private IWorkflowExecutionService workflowExecutionService;

    @Resource
    private IChallanService iChallanService;
    
    @Autowired
	ServiceMasterService serviceMasterService;    

    private static final long serialVersionUID = 1L;

    private Long orgId;

    private ServiceMaster serviceMaster = new ServiceMaster();

    private ObjectionDetailsDto objectionDetailsDto = new ObjectionDetailsDto();

    private HearingInspectionDto hearingInspectionDto = new HearingInspectionDto();

    private HearingInspectionDto inspectionDto = new HearingInspectionDto();

    private List<ObjectionDetailsDto> listObjectionDetails = new ArrayList<>();

    private String isValidationError;

    private Set<LookUp> departments = new HashSet<>();

    private Set<LookUp> serviceList = new HashSet<>();

    private String deptCode;

    private String serviceCode;

    private String onlyInspecMode;

    private Set<LookUp> locations = new HashSet<>();

    private List<CFCAttachment> documentList = new ArrayList<>();

    private List<LookUp> empList = new ArrayList<>();

    private CommonChallanDTO offlineDTO = new CommonChallanDTO();

    // private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

    private Double charges = 0.0d;

    private Map<Long, Double> chargesMap = new HashMap<>();

    private String paymentCharge;

    private String pioName;
    
    private List<LookUp> objOrAppealList = new ArrayList<>();
    
    private List<String> flatNoList;
    
    private String billingMethod;
    
    private List<DesignationBean> designlist = new ArrayList<>();

    private String skdclEnv;
    
    @Override
    public boolean saveForm() {
        final UserSession session = UserSession.getCurrent();
        ObjectionDetailsDto objectionDto = this.getObjectionDetailsDto();
        CommonChallanDTO offline = getOfflineDTO();
        objectionDto.setLangId((long) session.getLanguageId());
        objectionDto.setUserId(session.getEmployee().getEmpId());
        objectionDto.setObjectionStatus(MainetConstants.TASK_STATUS_PENDING);
        objectionDto.setIpAddress(session.getEmployee().getEmppiservername());
        Employee emp = UserSession.getCurrent().getEmployee();
        objectionDto.setEname(emp.getEmpname());
        objectionDto.setEmpType(emp.getEmplType());
        if (paymentCharge != null && paymentCharge.equals(MainetConstants.FlagY)) {
            objectionDto.setIsPaymentGenerated(true);
        } else {
            objectionDto.setIsPaymentGenerated(false);
        }
        if (offline.getOnlineOfflineCheck() != null
                && MainetConstants.N_FLAG.equalsIgnoreCase(offline.getOnlineOfflineCheck())) {
            objectionDto.setPaymentMode(MainetConstants.FlagN);
        }
        if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
            objectionDto.setPaymentMode(MainetConstants.FlagY);
        }
        List<DocumentDetailsVO> docs = prepareFileUpload();
        objectionDto.setDocs(docs);

		ServiceMaster service = serviceMasterService.getServiceMaster(objectionDto.getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
        
        String objOn = CommonMasterUtility.getNonHierarchicalLookUpObject(objectionDto.getObjectionOn(),
                UserSession.getCurrent().getOrganisation()).getLookUpCode();
		if (!MainetConstants.Objection.ObjectionOn.BILL.equals(objOn)
				&& (service != null && !service.getSmShortdesc().equalsIgnoreCase(MainetConstants.Property.MOS))) {
            Map<String, Long> result = iObjectionDetailsService.getApplicationNumberByRefNo(objectionDto);
            if (result != null) { //#149753
            objectionDto.setApmApplicationId(result.get(MainetConstants.Objection.APPLICTION_NO));
            objectionDto.setObjTime(result.get(MainetConstants.Objection.PERIOD));
            }
        }

        validateBean(this, ObjectionDetailsValidator.class);
        if (hasValidationErrors()) {
            return false;
        }

        iObjectionDetailsService.saveObjectionAndCallWorkFlow(objectionDto);
        if (objectionDto.getApmApplicationId() != null) {
            setSuccessMessage(
                    getAppSession().getMessage("obj.success") + " "
                            + getAppSession().getMessage("obj.success.msg1")+" " + objectionDto.getApmApplicationId()
                            +","+ getAppSession().getMessage("obj.success.msg2")+" " + objectionDto.getObjectionNumber());
        } else {
            setSuccessMessage(
                    getAppSession().getMessage("obj.success") + " "
                            + "Objection Number : " + objectionDto.getObjectionNumber());
        }

        Double paymentAmount = offline.getAmountToShow();

        if (paymentAmount != null && paymentAmount > 0) {
            objectionDto.setFree(false);
            objectionDto.setChargesMap(this.getChargesMap());
            setOfflineDetailsDTO(offline, objectionDto);
            offline.setAmountToPay(paymentAmount.toString());
            offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
                    offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
            if (offline.getAmountToShow() != null) {
                if (offline.getOnlineOfflineCheck() != null
                        && MainetConstants.N_FLAG.equalsIgnoreCase(offline.getOnlineOfflineCheck())) {
                    objectionDto.setPaymentMode(MainetConstants.FlagN);
                    final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
                    offline.setChallanNo(master.getChallanNo());
                    offline.setChallanValidDate(master.getChallanValiDate());
                    setOfflineDTO(offline);

                } else if ((offline.getOnlineOfflineCheck() != null)
                        && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
                    objectionDto.setPaymentMode(MainetConstants.FlagY);
                    ChallanReceiptPrintDTO receiptDTO = iChallanService.savePayAtUlbCounter(offline,
                            MainetConstants.RTISERVICE.RTIFIRSTAPPEAL);
                    setReceiptDTO(receiptDTO);

                }

                setSuccessMessage(
                        getAppSession().getMessage("obj.success") + " "
                                + "Objection Application Number : " + objectionDto.getApmApplicationId()
                                + ",Objection Number : " + objectionDto.getObjectionNumber());

            }
        }

        return true;
    }

    private void setOfflineDetailsDTO(CommonChallanDTO offline, ObjectionDetailsDto objectionDto) {

        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.OBJECTION);
        offline.setOrgId(objectionDto.getOrgId());
        offline.setDeptId(Long.valueOf(objectionDto.getObjectionDeptId()));
        offline.setApplNo(objectionDto.getApmApplicationId());
        offline.setObjectionNumber(objectionDto.getObjectionNumber());

        String fullName = String.join(" ",
                Arrays.asList(objectionDto.getfName(), objectionDto.getmName(), objectionDto.getlName()));
        offline.setApplicantName(fullName);
        offline.setApplicantAddress(objectionDto.getAddress());
        offline.setMobileNumber(objectionDto.getMobileNo());
        offline.setEmailId(objectionDto.geteMail());
        offline.setServiceId(objectionDto.getServiceId());
        offline.setUserId(objectionDto.getUserId());
        offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        offline.setLangId(UserSession.getCurrent().getLanguageId());
        for (final Map.Entry<Long, Double> entry : getChargesMap().entrySet()) {
            offline.getFeeIds().put(entry.getKey(), entry.getValue());
        }
    }

    public List<DocumentDetailsVO> prepareFileUpload() {
        long count = 0;
        List<DocumentDetailsVO> docs = null;
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            docs = new ArrayList<>();
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    try {
                        DocumentDetailsVO d = new DocumentDetailsVO();
                        final Base64 base64 = new Base64();
                        final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                        d.setDocumentByteCode(bytestring);
                        d.setDocumentName(file.getName());
                        d.setDocumentSerialNo(count);
                        count++;
                        docs.add(d);
                    } catch (final IOException e) {
                        LOGGER.error("Exception has been occurred in file byte to string conversions", e);
                    }
                }
            }
        }
        return docs;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    public void setServiceMaster(ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    public ObjectionDetailsDto getObjectionDetailsDto() {
        return objectionDetailsDto;
    }

    public void setObjectionDetailsDto(ObjectionDetailsDto objectionDetailsDto) {
        this.objectionDetailsDto = objectionDetailsDto;
    }

    public List<ObjectionDetailsDto> getListObjectionDetails() {
        return listObjectionDetails;
    }

    public void setListObjectionDetails(List<ObjectionDetailsDto> listObjectionDetails) {
        this.listObjectionDetails = listObjectionDetails;
    }

    public String getIsValidationError() {
        return isValidationError;
    }

    public void setIsValidationError(String isValidationError) {
        this.isValidationError = isValidationError;
    }

    public Set<LookUp> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<LookUp> departments) {
        this.departments = departments;
    }

    public Set<LookUp> getServiceList() {
        return serviceList;
    }

    public void setServiceList(Set<LookUp> serviceList) {
        this.serviceList = serviceList;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Set<LookUp> getLocations() {
        return locations;
    }

    public void setLocations(Set<LookUp> locations) {
        this.locations = locations;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    public HearingInspectionDto getHearingInspectionDto() {
        return hearingInspectionDto;
    }

    public void setHearingInspectionDto(HearingInspectionDto hearingInspectionDto) {
        this.hearingInspectionDto = hearingInspectionDto;
    }

    public HearingInspectionDto getInspectionDto() {
        return inspectionDto;
    }

    public void setInspectionDto(HearingInspectionDto inspectionDto) {
        this.inspectionDto = inspectionDto;
    }

    public String getOnlyInspecMode() {
        return onlyInspecMode;
    }

    public void setOnlyInspecMode(String onlyInspecMode) {
        this.onlyInspecMode = onlyInspecMode;
    }

    public List<LookUp> getEmpList() {
        return empList;
    }

    public void setEmpList(List<LookUp> empList) {
        this.empList = empList;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public CommonChallanDTO getOfflineDTO() {
        return offlineDTO;
    }

    public void setOfflineDTO(CommonChallanDTO offlineDTO) {
        this.offlineDTO = offlineDTO;
    }

    /*
     * public ApplicantDetailDTO getApplicantDetailDto() { return applicantDetailDto; } public void
     * setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) { this.applicantDetailDto = applicantDetailDto; }
     */

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }

    public Map<Long, Double> getChargesMap() {
        return chargesMap;
    }

    public void setChargesMap(Map<Long, Double> chargesMap) {
        this.chargesMap = chargesMap;
    }

    public String getPaymentCharge() {
        return paymentCharge;
    }

    public void setPaymentCharge(String paymentCharge) {
        this.paymentCharge = paymentCharge;
    }

    public String getPioName() {
        return pioName;
    }

    public void setPioName(String pioName) {
        this.pioName = pioName;
    }
    
    

    /*
     * public String getPaymentMode() { return paymentMode; } public void setPaymentMode(String paymentMode) { this.paymentMode =
     * paymentMode; }
     */

    public List<LookUp> getObjOrAppealList() {
        return objOrAppealList;
    }

    public void setObjOrAppealList(List<LookUp> objOrAppealList) {
        this.objOrAppealList = objOrAppealList;
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

	public boolean saveRTSObjectionForm() {
        final UserSession session = UserSession.getCurrent();
        ObjectionDetailsDto objectionDto = this.getObjectionDetailsDto();
        objectionDto.setLangId((long) session.getLanguageId());
        objectionDto.setUserId(session.getEmployee().getEmpId());
        objectionDto.setObjectionStatus(MainetConstants.TASK_STATUS_PENDING);
        objectionDto.setIpAddress(session.getEmployee().getEmppiservername());
        Employee emp = UserSession.getCurrent().getEmployee();
        objectionDto.setEname(emp.getEmpname());
        objectionDto.setEmpType(emp.getEmplType());
        objectionDto.setIsPaymentGenerated(false);
        objectionDto.setPaymentMode(MainetConstants.FlagN);
        List<DocumentDetailsVO> docs = prepareFileUpload();
        objectionDto.setDocs(docs);
        objectionDto = iObjectionDetailsService.saveRTSAppealInObjection(objectionDto);
        if (objectionDto.getApmApplicationId() != null) {
            setSuccessMessage(
                    getAppSession().getMessage("obj.success") + " "
                            + "Objection Application Number : " + objectionDto.getApmApplicationId()
                            + ",Objection Number : " + objectionDto.getObjectionNumber());
        } else {
            setSuccessMessage(
                    getAppSession().getMessage("obj.success") + " "
                            + "Objection Number : " + objectionDto.getObjectionNumber());
        }
        return true;
    }

         //Defect #112673
	public boolean saveLicenseObjectionForm() {
		final UserSession session = UserSession.getCurrent();
        ObjectionDetailsDto objectionDto = this.getObjectionDetailsDto();
        objectionDto.setLangId((long) session.getLanguageId());
        objectionDto.setUserId(session.getEmployee().getEmpId());
        objectionDto.setObjectionStatus(MainetConstants.TASK_STATUS_PENDING);
        objectionDto.setIpAddress(session.getEmployee().getEmppiservername());
        Employee emp = UserSession.getCurrent().getEmployee();
        objectionDto.setEname(emp.getEmpname());
        objectionDto.setEmpType(emp.getEmplType());
        objectionDto.setIsPaymentGenerated(false);
        objectionDto.setPaymentMode(MainetConstants.FlagN);
        List<DocumentDetailsVO> docs = prepareFileUpload();
        objectionDto.setDocs(docs);      

        String objOn = CommonMasterUtility.getNonHierarchicalLookUpObject(objectionDto.getObjectionOn(),
                UserSession.getCurrent().getOrganisation()).getLookUpCode();
        if (!MainetConstants.Objection.ObjectionOn.BILL.equals(objOn)) {
            Map<String, Long> result = iObjectionDetailsService.getApplicationNumberByRefNo(objectionDto);
            objectionDto.setApmApplicationId(result.get(MainetConstants.Objection.APPLICTION_NO));
            objectionDto.setObjTime(result.get(MainetConstants.Objection.PERIOD));
        }

        validateBean(this, ObjectionDetailsValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        objectionDto = iObjectionDetailsService.saveLicenseObjectionData(objectionDto);
        if (objectionDto.getApmApplicationId() != null) {
            setSuccessMessage(
                    getAppSession().getMessage("obj.success") + " "
                            + "Objection Application Number : " + objectionDto.getApmApplicationId()
                            + ",Objection Number : " + objectionDto.getObjectionNumber());
        } else {
            setSuccessMessage(
                    getAppSession().getMessage("obj.success") + " "
                            + "Objection Number : " + objectionDto.getObjectionNumber());
        }
		return true;
	}

	public List<DesignationBean> getDesignlist() {
		return designlist;
	}

	public void setDesignlist(List<DesignationBean> designlist) {
		this.designlist = designlist;
	}

	public String getSkdclEnv() {
		return skdclEnv;
	}

	public void setSkdclEnv(String skdclEnv) {
		this.skdclEnv = skdclEnv;
	}

}
