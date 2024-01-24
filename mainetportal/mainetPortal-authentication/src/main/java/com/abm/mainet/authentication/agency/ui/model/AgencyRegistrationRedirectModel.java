package com.abm.mainet.authentication.agency.ui.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.Prefix;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.TPAgencyReqDTO;
import com.abm.mainet.common.dto.TPAgencyResDTO;
import com.abm.mainet.common.dto.TPTechPersonLicMasDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.payment.dto.PaymentRequestDTO;

@Component
@Scope("session")
public class AgencyRegistrationRedirectModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = 7520586933902278626L;

    public static final Logger LOGGER = Logger.getLogger(AgencyRegistrationRedirectModel.class);

    TPTechPersonLicMasDTO entity = new TPTechPersonLicMasDTO();

    @Autowired
    private IPortalServiceMasterService portalServiceMasterService;

    @Resource
    IPortalServiceMasterService iPortalService;
    private List<CFCAttachment> attachDocument = new ArrayList<>(0);
    private Employee employee;
    private Long pgServiceId;
    private String pgUrl;
    private String registrationInstitude;
    private long coloumnCount1;
    private String viewdata;
    private String fileSaveDirectory;
    private int rowCount;
    private Long isBeingInsertedAppNo;
    private double failurePaymentAmt;
    private boolean notMandatory;
    private double amt;
    private String licDob;
    private String mobileNo;
    private Long applicationId;
    private Double chargeAmountToPay;
    private String isFree;
    private String checkListNCharges;
    private List<DocumentDetailsVO> checkList;// being used for no of document checklist need to upload
    private String authStatus;
    List<DocumentDetailsVO> reUploadCheckList = new ArrayList<>();
    TPTechPersonLicMasDTO master = new TPTechPersonLicMasDTO();

    @Override
    protected void initializeModel() {
        super.initializeModel();
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        initializeLookupFields(PrefixConstants.Common.TRY);
        initializeLookupFields(Prefix.TITLE);
        setFileDirectory();
        loadFormData();
    }

    private void loadFormData() {

        if ((UserSession.getCurrent().getEmployee().getAuthStatus() != null)
                && (UserSession.getCurrent().getEmployee().getAuthStatus().equals(MainetConstants.AuthStatus.REJECTED)
                        || UserSession
                                .getCurrent().getEmployee().getAuthStatus().equals(MainetConstants.AuthStatus.ONHOLD))) {

            final TPAgencyReqDTO agencyReqDTO = new TPAgencyReqDTO();
            agencyReqDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            agencyReqDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            try {
                @SuppressWarnings("unchecked")
                final LinkedHashMap<Long, Object> response = (LinkedHashMap<Long, Object>) JersyCall
                        .callRestTemplateClient(agencyReqDTO, ServiceEndpoints.JercyCallURL.AGENCY_GET_LICENCE_MAS_DET);
                final String jsonString = new JSONObject(response).toString();
                final TPAgencyResDTO responseDTO = new ObjectMapper().readValue(jsonString, TPAgencyResDTO.class);
                if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getStatus())) {
                    master = responseDTO.getTpTechPersonLicMasDTO();
                }

            } catch (final Exception ex) {
                throw new FrameworkException(
                        "Error Occurred while making Jersy call: AgencyRegistrationRedirectModel.loadFormData()", ex);
            }

        } else {
            master = new TPTechPersonLicMasDTO();
            employee = UserSession.getCurrent().getEmployee();
            String str = null;
            Long num = null;

            str = employee.getEmpAddress();
            if ((str != null) && (str.length() > 0)) {
                master.setLicApplicantAddr(str);
            }
            num = employee.getEmplType();
            if (null != num) {
                master.setLicTechperType(num);
            }
            num = employee.getEmpId();
            if (null != num) {
                master.setUserid(num);
            }
            num = UserSession.getCurrent().getOrganisation().getOrgid();
            if (null != num) {
                master.setOrgid(num);
            }
            num = employee.getTitle();
            if (null != num) {
                master.setLicTitle(num);
            }
            str = employee.getEmpname();
            if ((str != null) && (str.length() > 0)) {
                master.setLicFname(str);
            }
            str = employee.getEmpMName();
            if ((str != null) && (str.length() > 0)) {
                master.setLicMname(str);
            }
            str = employee.getEmpLName();
            if ((str != null) && (str.length() > 0)) {
                master.setLicLname(str);
            }
            str = employee.getEmpemail();
            if ((str != null) && (str.length() > 0)) {
                master.setLicEmail(str);
            }
            str = employee.getAgencyName();
            if ((str != null) && (str.length() > 0)) {
                master.setLicAgency(str);
            }

        }

        setEntity(master);
    }

    private void setFileDirectory() {

        if ((UserSession.getCurrent().getEmployee().getAuthStatus() != null)
                && (UserSession.getCurrent().getEmployee().getAuthStatus() != null) && (UserSession.getCurrent()
                        .getEmployee().getAuthStatus().equals(MainetConstants.AuthStatus.REJECTED)
                        || UserSession
                                .getCurrent().getEmployee().getAuthStatus().equals(MainetConstants.AuthStatus.ONHOLD))) {
            fileSaveDirectory = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator
                    + MainetConstants.CFC_ATTACHMENT + File.separator + MainetConstants.AGENCY_UPLOADED_DOC + File.separator
                    + getUserSession().getEmployee().getEmpId() + File.separator + MainetConstants.AFTER_REJECT;
        } else {
            fileSaveDirectory = UserSession.getCurrent().getOrganisation().getOrgid() + File.separator
                    + MainetConstants.CFC_ATTACHMENT + File.separator + "TOWN_PLANNING_LICENSE" + File.separator
                    + Utility.getTimestamp();
        }
    }

    @Override
    public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PaymentRequestDTO payURequestDTO) {

        final String userName = getUserSession().getEmployee().getFullName();
        final PortalService portalServiceMaster = iPortalService.getService(getServiceId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        payURequestDTO.setUdf3("CitizenHome.html");
        payURequestDTO.setUdf5(portalServiceMaster.getShortName());
        payURequestDTO.setUdf7(String.valueOf(getApplicationId()));
        payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
        payURequestDTO.setUdf6(String.valueOf(getUserSession().getEmployee().getEmpId()));
        payURequestDTO.setApplicationId(getApplicationId().toString());
        payURequestDTO.setDueAmt(BigDecimal.valueOf(getAmt()));
        payURequestDTO.setEmail(getUserSession().getEmployee().getEmpemail());
        payURequestDTO.setMobNo(getUserSession().getEmployee().getEmpmobno());
        payURequestDTO.setApplicantName(userName);
        if (portalServiceMaster != null) {
            payURequestDTO.setUdf10(String.valueOf(portalServiceMaster
                    .getPsmDpDeptid()));
            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
            } else {
                payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean saveForm() throws JsonParseException, JsonMappingException, IOException {
        CommonChallanDTO offline = getOfflineDTO();
        UserSession.getCurrent().getEmployee().getEmpId();
        final Organisation organisation = UserSession.getCurrent().getOrganisation();

        if (null == master.getLicApplicationId()) {

            FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());

            LinkedHashMap<Long, Object> responseChallan = null;

            final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
            offline.setOfflinePaymentText(modeDesc);
            if ((offline.getOnlineOfflineCheck() == null) || offline.getOnlineOfflineCheck().isEmpty()) {
                validateBean(offline, CommonOfflineMasterValidator.class);
            }

            if (hasValidationErrors()) {
                return false;
            }

            final TPAgencyReqDTO tpReqDTO = new TPAgencyReqDTO();
            tpReqDTO.setTpTechPersonLicMasDTO(getEntity());

            tpReqDTO.setTitle(UserSession.getCurrent().getEmployee().getTitle());
            tpReqDTO.setfName(UserSession.getCurrent().getEmployee().getEmpname());
            tpReqDTO.setmName(UserSession.getCurrent().getEmployee().getEmpMName());
            tpReqDTO.setlName(UserSession.getCurrent().getEmployee().getEmpLName());
            tpReqDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            tpReqDTO.setMacId(Utility.getMacAddress());
            tpReqDTO.setLangaugeId(UserSession.getCurrent().getLanguageId());
            tpReqDTO.setServiceId(getServiceId());
            tpReqDTO.setAddress(UserSession.getCurrent().getEmployee().getEmpAddress());
            tpReqDTO.setIsDeleted(UserSession.getCurrent().getEmployee().getIsdeleted());
            tpReqDTO.setOrgId(organisation.getOrgid());
            tpReqDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            tpReqDTO.setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));
           // try {

                final LinkedHashMap<Long, Object> response = (LinkedHashMap<Long, Object>) JersyCall
                        .callRestTemplateClient(tpReqDTO, ServiceEndpoints.JercyCallURL.AGENCY_SAVE_LICENCE_MAS_DET);
                final String jsonString = new JSONObject(response).toString();
                final TPAgencyResDTO responseDTO = new ObjectMapper().readValue(jsonString, TPAgencyResDTO.class);
                if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getStatus())) {
                    setApplicationId(responseDTO.getApplicationNo());

                    final ApplicationPortalMaster applicationMaster = new ApplicationPortalMaster();
                    applicationMaster.setPamApplicationId(responseDTO.getApplicationNo());
                    applicationMaster.setPamApplicationDate(new Date());
                    applicationMaster.setSmServiceId(getServiceId());
                    applicationMaster.setDeleted(false);
                    applicationMaster.setOrgId(UserSession.getCurrent().getOrganisation());
                    applicationMaster.setLangId(UserSession.getCurrent().getLanguageId());
                    applicationMaster.setUserId(UserSession.getCurrent().getEmployee());
                    applicationMaster.setLgIpMac(Utility.getMacAddress());
                    applicationMaster.setLmodDate(new Date());
                    int documentSize = 0;
                    if (null != getCheckList()) {
                        documentSize = getCheckList().size();
                    }
                    portalServiceMasterService.saveApplicationMaster(applicationMaster, getAmt(), documentSize);
                    setSuccessMessage("Your application no:" + responseDTO.getApplicationNo()
                            + " for TP Technical Person Agency has been saved successfully.");

                    if (((offline.getOnlineOfflineCheck() != null)
                            && offline.getOnlineOfflineCheck().equals(MainetConstants.Common_Constant.NO))
                            || (getAmt() > 0d)) {
                        offline.setApplNo(responseDTO.getApplicationNo());
                        offline.setAmountToPay(Double.toString(getAmt()));
                        final String userName = getUserSession().getEmployee().getFullName();
                        offline.setApplicantName(userName);
                        offline.setApplicantAddress(UserSession.getCurrent().getEmployee().getEmpAddress());
                        offline.setMobileNumber(UserSession.getCurrent().getEmployee().getEmpmobno());
                        offline.setEmailId(UserSession.getCurrent().getEmployee().getEmpemail());
                        offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                        offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                        offline.setLangId(UserSession.getCurrent().getLanguageId());
                        offline.setLgIpMac(Utility.getMacAddress());
                        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
                        offline.setServiceId(getServiceId());
                        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
                        final PortalService portalServiceMaster = portalServiceMasterService.getService(getServiceId(),
                                UserSession.getCurrent().getOrganisation().getOrgid());
                        offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
                        offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
                                offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());

                        if ((offline.getOnlineOfflineCheck() != null)
                                && offline.getOnlineOfflineCheck().equals(MainetConstants.Common_Constant.NO)) {
                            responseChallan = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(offline,
                                    ServiceEndpoints.JercyCallURL.GENERATE_CHALLAN);
                            final String data = new JSONObject(responseChallan).toString();
                            offline = new ObjectMapper().readValue(data, CommonChallanDTO.class);
                            setOfflineDTO(offline);
                        }
                    }

                }
            /*} catch (final Exception ex) {

                throw new FrameworkException("Error Occurred while making Jersy call: AgencyRegistrationRedirectModel.saveForm()",
                        ex);
            }*/

        } else if ((UserSession.getCurrent().getEmployee().getAuthStatus() != null)
                && (UserSession.getCurrent().getEmployee().getAuthStatus() != null) && (UserSession.getCurrent()
                        .getEmployee().getAuthStatus().equals(MainetConstants.AuthStatus.REJECTED)
                        || UserSession
                                .getCurrent().getEmployee().getAuthStatus().equals(MainetConstants.AuthStatus.ONHOLD))) {

            FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());

            if (hasValidationErrors()) {
                return false;
            }

            try {
                final TPAgencyReqDTO tpReqDTO = new TPAgencyReqDTO();
                tpReqDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                tpReqDTO.setApplicationId(master.getLicApplicationId());
                tpReqDTO.setServiceId(master.getLicServiceId());
                tpReqDTO.setLangId(new Long(master.getLangId()));
                tpReqDTO.setUserId(master.getUserid());
                tpReqDTO.setUpdatedBy(master.getUserid());
                tpReqDTO.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
                tpReqDTO.setDeptId(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid());
                tpReqDTO.setDocumentList(getFileUploadList(getReUploadCheckList(), FileUploadUtility.getCurrent().getFileMap()));
                final LinkedHashMap<Long, Object> response = (LinkedHashMap<Long, Object>) JersyCall
                        .callRestTemplateClient(tpReqDTO, ServiceEndpoints.JercyCallURL.SAVE_REUPLOAD_DOC);
                final String jsonString = new JSONObject(response).toString();
                final TPAgencyResDTO responseDTO = new ObjectMapper().readValue(jsonString, TPAgencyResDTO.class);
                if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getStatus())) {

                }

            } catch (final Exception ex) {
                throw new FrameworkException("Error Occurred while making Jersy call: AgencyRegistrationRedirectModel.saveForm()",
                        ex);

            }
            return true;

        } else {

            offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
                    offline.getOflPaymentMode()).getLookUpCode());

            if ((offline.getOnlineOfflineCheck() != null) && !offline.getOnlineOfflineCheck().isEmpty()) {
                validateBean(offline, CommonOfflineMasterValidator.class);
            }
            if (hasValidationErrors()) {
                return false;
            }

            if (offline.getOnlineOfflineCheck().equalsIgnoreCase(MainetConstants.PAYMENT.OFFLINE)) {

                offline.setMobileNumber(UserSession.getCurrent().getEmployee().getEmpmobno());
                offline.setEmailId(UserSession.getCurrent().getEmployee().getEmpemail());
                offline.setApplicantName(UserSession.getCurrent().getEmployee().getFullName());
                offline.setServiceId(getServiceId());
                offline.setApplNo(isBeingInsertedAppNo);
                offline.setIsDeleted(MainetConstants.IsDeleted.NOT_DELETE);
                offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

            }

        }

        return true;
    }

    public String getApprovalStatus(final String clmAprStatus) {
        String status = null;
        switch (clmAprStatus) {
        case "Y":
            status = "APPROVRD";
            break;
        case "N":
            status = MainetConstants.AuthStatus.REJECT;
            break;
        case MainetConstants.AuthStatus.ONHOLD:
            status = MainetConstants.AuthStatus.HOLD;
            break;
        default:
            status = MainetConstants.BLANK;
            break;
        }
        return status;
    }

    public boolean isTownPlanningPerson() {

        final String emplType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                UserSession.getCurrent().getEmployee().getEmpId()).getLookUpCode();

        if (emplType.equals(MainetConstants.NEC.ARCHITECT) || emplType.equals(MainetConstants.NEC.ENGINEER)
                || emplType.equals(MainetConstants.NEC.STRUCTURAL_ENGINEER)
                || emplType.equals(MainetConstants.NEC.SUPERVISOR) || emplType.equals(MainetConstants.NEC.TOWN_PLANNER)
                || emplType.equals(MainetConstants.NEC.BUILDER)) {
            return true;
        }

        return false;
    }

    public boolean isBuilder() {

        final String emplType = CommonMasterUtility.getNonHierarchicalLookUpObject(
                UserSession.getCurrent().getEmployee().getEmplType()).getLookUpCode();

        if (emplType.equals(MainetConstants.NEC.BUILDER)) {
            return true;
        }

        return false;
    }

    /**
     * This method is used for getting checklist and charges.
     */
    public void findApplicableCheckListAndCharges(final long serviceId, final long orgId) {

    }

    public String getFileSaveDirectory() {
        return fileSaveDirectory;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(final Employee employee) {
        this.employee = employee;
    }

    public String getPgUrl() {
        return pgUrl;
    }

    public Long getPgServiceId() {
        return pgServiceId;
    }

    public List<CFCAttachment> getAttachDocument() {
        return attachDocument;
    }

    public void setAttachDocument(final List<CFCAttachment> attachDocument) {
        this.attachDocument = attachDocument;
    }

    public long getColoumnCount1() {
        return coloumnCount1;
    }

    /**
     * @param coloumnCount1 the coloumnCount1 to set
     */
    public void setColoumnCount1(final long coloumnCount1) {
        this.coloumnCount1 = coloumnCount1;
    }

    public String getViewdata() {
        return viewdata;
    }

    /**
     * @param viewdata the viewdata to set
     */
    public void setViewdata(final String viewdata) {
        this.viewdata = viewdata;
    }

    public String getRegistrationInstitude() {
        return registrationInstitude;
    }

    public void setRegistrationInstitude(final String registrationInstitude) {
        this.registrationInstitude = registrationInstitude;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public void setRowCount(final int rowCount) {
        this.rowCount = rowCount;
    }

    public Long getIsBeingInsertedAppNo() {
        return isBeingInsertedAppNo;
    }

    public void setIsBeingInsertedAppNo(final Long isBeingInsertedAppNo) {
        this.isBeingInsertedAppNo = isBeingInsertedAppNo;
    }

    public double getFailurePaymentAmt() {
        return failurePaymentAmt;
    }

    public void setFailurePaymentAmt(final double failurePaymentAmt) {
        this.failurePaymentAmt = failurePaymentAmt;
    }

    public TPTechPersonLicMasDTO getEntity() {
        return entity;
    }

    public void setEntity(final TPTechPersonLicMasDTO entity) {
        this.entity = entity;
    }

    public boolean getNotMandatory() {
        return notMandatory;
    }

    public void setNotMandatory(final boolean notMandatory) {
        this.notMandatory = notMandatory;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(final double amt) {
        this.amt = amt;
    }

    public String getLicDob() {
        return licDob;
    }

    public void setLicDob(final String licDob) {
        this.licDob = licDob;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public Double getChargeAmountToPay() {
        return chargeAmountToPay;
    }

    public void setChargeAmountToPay(final Double chargeAmountToPay) {
        this.chargeAmountToPay = chargeAmountToPay;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(final List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(final String authStatus) {
        this.authStatus = authStatus;
    }

    public List<DocumentDetailsVO> getReUploadCheckList() {
        return reUploadCheckList;
    }

    public void setReUploadCheckList(final List<DocumentDetailsVO> reUploadCheckList) {
        this.reUploadCheckList = reUploadCheckList;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(final String isFree) {
        this.isFree = isFree;
    }

    public String getCheckListNCharges() {
        return checkListNCharges;
    }

    public void setCheckListNCharges(final String checkListNCharges) {
        this.checkListNCharges = checkListNCharges;
    }

}
