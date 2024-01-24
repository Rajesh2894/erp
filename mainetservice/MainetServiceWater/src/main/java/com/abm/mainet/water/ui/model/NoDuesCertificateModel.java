package com.abm.mainet.water.ui.model;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonApplicantDetailValidator;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.AdditionalOwnerInfoDTO;
import com.abm.mainet.water.dto.NewWaterConnectionReqDTO;
import com.abm.mainet.water.dto.NoDueCerticateDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.service.WaterCommonService;
import com.abm.mainet.water.service.WaterNoDuesCertificateService;
import com.abm.mainet.water.ui.validator.WaterNoDuesCertificateValidator;

@Component
@Scope("session")
public class NoDuesCertificateModel extends AbstractFormModel {

    private static final long serialVersionUID = -1112115377545505440L;

    @Resource
    private WaterNoDuesCertificateService noDuesCertificateService;

    @Resource
    private ICFCApplicationMasterService icfcApplicationMasterService;

    @Resource
    private CommonService commonService;

    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    private List<ChargeDetailDTO> chargesInfo;
    private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
    private NoDuesCertificateReqDTO reqDTO = new NoDuesCertificateReqDTO();
    private NoDuesCertificateRespDTO responseDTO = new NoDuesCertificateRespDTO();
    private NoDueCerticateDTO nodueCertiDTO = new NoDueCerticateDTO();
    private List<DocumentDetailsVO> checkList = new ArrayList<>();
    private Map<Long, Double> chargesMap = new HashMap<>();
    private Double charges = 0.0d;
    private String free = MainetConstants.PAYMENT.FREE;
    private Long orgId;
    private Long deptId;
    private Long langId;
    private boolean isDocumentSubmitted;
    private String applicantName;
    private String approveEmpName;
    private Date applicantDate;
    private List<FinYearDTO> finYear;
    private String checkListApplFlag;
    private List<CommonChallanDTO> offline;
    private boolean resultFound;
    private Long connectionSize;
    private String scrutinyFlag;
    
	public String getScrutinyFlag() {
		return scrutinyFlag;
	}

	public void setScrutinyFlag(String scrutinyFlag) {
		this.scrutinyFlag = scrutinyFlag;
	}

	public Long getConnectionSize() {
        return connectionSize;
    }

    public void setConnectionSize(Long connectionSize) {
        this.connectionSize = connectionSize;
    }

    public boolean isResultFound() {
        return resultFound;
    }

    public void setResultFound(boolean resultFound) {
        this.resultFound = resultFound;
    }

    public List<CommonChallanDTO> getOffline() {
        return offline;
    }

    public void setOffline(List<CommonChallanDTO> offline) {
        this.offline = offline;
    }

    public String getCheckListApplFlag() {
        return checkListApplFlag;
    }

    public void setCheckListApplFlag(String checkListApplFlag) {
        this.checkListApplFlag = checkListApplFlag;
    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        switch (parentCode) {

        case PrefixConstants.NewWaterServiceConstants.WWZ:
            return PrefixConstants.NewWaterServiceConstants.APP_DTO_DWZID;

        default:
            return null;

        }
    }

    /**
     * @return the isDocumentSubmitted
     */
    public boolean isDocumentSubmitted() {
        return isDocumentSubmitted;
    }

    /**
     * @return
     */
    public Boolean saveExeFormData() {

        final NoDueCerticateDTO reqDto = getNodueCertiDTO();
        reqDto.setApplicationId(getApmApplicationId());
        reqDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        final Boolean save = noDuesCertificateService.saveExeFormData(reqDto);
        if (save) {
            final NoDueCerticateDTO dto = noDuesCertificateService.getNoDuesApplicationData(getApmApplicationId(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            dto.setApproveBy(UserSession.getCurrent().getEmployee().getEmploginname());
            dto.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
            setNodueCertiDTO(dto);
            return true;
        } else {
            return false;
        }
    }

    public void setApplcationDeatil(final Long appId, final Long serviceId) {

        final TbCfcApplicationMstEntity cfcApplicationMaster = icfcApplicationMasterService
                .getCFCApplicationByApplicationId(appId, orgId);

        if (cfcApplicationMaster != null) {
            if ((cfcApplicationMaster.getApmFname() != null) && (cfcApplicationMaster.getApmLname() != null)
                    && (cfcApplicationMaster.getApmMname() != null)) {

                setApplicantName(cfcApplicationMaster.getApmFname() + cfcApplicationMaster.getApmMname()
                        + cfcApplicationMaster.getApmLname());
            } else {
                setApplicantName(cfcApplicationMaster.getApmFname());
            }

            setApplicantDate(cfcApplicationMaster.getApmApplicationDate());
        }
    }

    /**
     * @param applicantDate the applicantDate to set
     */
    public void setApplicantDate(final Date applicantDate) {
        this.applicantDate = applicantDate;
    }

    /**
     * @param applicantName the applicantName to set
     */
    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    /**
     * @param charges the charges to set
     */
    public void setCharges(final Double charges) {
        this.charges = charges;
    }

    /**
     * @param chargesMap the chargesMap to set
     */
    public void setChargesMap(final Map<Long, Double> chargesMap) {
        this.chargesMap = chargesMap;
    }

    /**
     * @param checkList the checkList to set
     */
    public void setCheckList(final List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    /**
     * @param reqDTO the reqDTO to set
     */
    public void setReqDTO(final NoDuesCertificateReqDTO reqDTO) {
        this.reqDTO = reqDTO;
    }

    /**
     * @param responseDTO the responseDTO to set
     */
    public void setResponseDTO(final NoDuesCertificateRespDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    public boolean validateInputs() {
        validateBean(this, WaterNoDuesCertificateValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        validateBean(getApplicantDetailDto(), CommonApplicantDetailValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

    public boolean validatePayment() {
        if (StringUtils.equals(getFree(), "Y")) {
            validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
        }
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

    /**
     * @return the deptId
     */
    public Long getDeptId() {
        return deptId;
    }

    public List<FinYearDTO> getFinYear() {
        return finYear;
    }

    /**
     * @return the free
     */
    public String getFree() {
        return free;
    }

    /**
     * @return the langId
     */
    public Long getLangId() {
        return langId;
    }

    /**
     * @return the nodueCertiDTO
     */
    public NoDueCerticateDTO getNodueCertiDTO() {
        return nodueCertiDTO;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @return the reqDTO
     */
    public NoDuesCertificateReqDTO getReqDTO() {
        return reqDTO;
    }

    /**
     * @return the responseDTO
     */
    public NoDuesCertificateRespDTO getResponseDTO() {
        return responseDTO;
    }

    /**
     * @param free the free to set
     */
    public void setFree(final String free) {
        this.free = free;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    /**
     * @param nodueCertiDTO the nodueCertiDTO to set
     */
    public void setNodueCertiDTO(final NoDueCerticateDTO nodueCertiDTO) {
        this.nodueCertiDTO = nodueCertiDTO;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    /**
     * @param isDocumentSubmitted the isDocumentSubmitted to set
     */
    public void setDocumentSubmitted(final boolean isDocumentSubmitted) {
        this.isDocumentSubmitted = isDocumentSubmitted;
    }

    /**
     * @param finYear the finYear to set
     */
    public void setFinYear(final List<FinYearDTO> finYear) {
        this.finYear = finYear;
    }

    /**
     * @return the applicantDate
     */
    public Date getApplicantDate() {
        return applicantDate;
    }

    /**
     * @return the applicantName
     */
    public String getApplicantName() {
        return applicantName;
    }

    /**
     * @return the charges
     */
    public Double getCharges() {
        return charges;
    }

    /**
     * @return the chargesMap
     */
    public Map<Long, Double> getChargesMap() {
        return chargesMap;
    }

    /**
     * @return the checkList
     */
    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    /**
     * @return the approveEmpName
     */
    public String getApproveEmpName() {
        return approveEmpName;
    }

    /**
     * @param approveEmpName the approveEmpName to set
     */
    public void setApproveEmpName(final String approveEmpName) {
        this.approveEmpName = approveEmpName;
    }

    /**
     * @return the applicantDetailDto
     */
    public ApplicantDetailDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    /**
     * @param applicantDetailDto the applicantDetailDto to set
     */
    public void setApplicantDetailDto(final ApplicantDetailDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    /**
     * @return the chargesInfo
     */
    @Override
    public List<ChargeDetailDTO> getChargesInfo() {
        return chargesInfo;
    }

    /**
     * @param chargesInfo the chargesInfo to set
     */
    @Override
    public void setChargesInfo(final List<ChargeDetailDTO> chargesInfo) {
        this.chargesInfo = chargesInfo;
    }

    public boolean populateNoDuesPaymentDetails(final CommonChallanDTO offline,
            final NoDuesCertificateModel noDuesCertificateModel, final UserSession userSession) {

        boolean setFalg = false;
        try {

            if (((offline.getOnlineOfflineCheck() != null)
                    && offline.getOnlineOfflineCheck().equals(PrefixConstants.NewWaterServiceConstants.NO))
                    || (noDuesCertificateModel.getCharges() > 0d)) {
                offline.setApplNo(noDuesCertificateModel.getResponseDTO().getApplicationNo());
                offline.setAmountToPay(noDuesCertificateModel.getCharges().toString());
                offline.setUserId(userSession.getEmployee().getEmpId());
                offline.setOrgId(userSession.getOrganisation().getOrgid());
                offline.setLangId(userSession.getLanguageId());
                offline.setLgIpMac(userSession.getEmployee().getEmppiservername());
                offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
                offline.setFaYearId(userSession.getFinYearId());
                offline.setFinYearStartDate(userSession.getFinStartDate());
                offline.setFinYearEndDate(userSession.getFinEndDate());
                offline.setServiceId(noDuesCertificateModel.getServiceId());
                offline.setApplicantName(noDuesCertificateModel.getApplicantDetailDto().getApplicantFirstName());
                offline.setMobileNumber(noDuesCertificateModel.getApplicantDetailDto().getMobileNo());
                offline.setEmailId(noDuesCertificateModel.getApplicantDetailDto().getEmailId());
                offline.setEmpType(userSession.getEmployee().getEmplType());
                for (final Map.Entry<Long, Double> entry : noDuesCertificateModel.getChargesMap().entrySet()) {
                    offline.getFeeIds().put(entry.getKey(), entry.getValue());
                }
                final ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(
                        MainetConstants.WaterServiceShortCode.WATER_NO_DUES, userSession.getOrganisation().getOrgid());
                if (serviceMaster != null) {
                    final Long deptId = serviceMaster.getTbDepartment().getDpDeptid();
                    offline.setDeptId(deptId);
                    offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
                            offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
                    if ((offline.getOnlineOfflineCheck() != null)
                            && offline.getOnlineOfflineCheck().equals(PrefixConstants.NewWaterServiceConstants.NO)) {

                        final ChallanMaster challanMaster = iChallanService.InvokeGenerateChallan(offline);
                        if (challanMaster != null) {
                            offline.setChallanNo(challanMaster.getChallanNo());
                            offline.setChallanValidDate(challanMaster.getChallanValiDate());
                            noDuesCertificateModel.setOfflineDTO(offline);
                        }
                    } else if ((offline.getOnlineOfflineCheck() != null)
                            && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
                        final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
                                serviceMaster.getSmServiceName());
                        setReceiptDTO(printDto);
                        setSuccessMessage(getAppSession().getMessage("water.receipt"));
                    }
                    setFalg = true;
                }
            }
        } catch (final Exception exception) {
            throw new FrameworkException("Exception occur in setPaymentDetail()", exception);

        }
        return setFalg;
    }
    
	/*
	 * private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();
	 * 
	 * @Resource private WaterCommonService waterCommonService; private
	 * NewWaterConnectionReqDTO req1DTO = new NewWaterConnectionReqDTO(); private
	 * List<CFCAttachment> documentList = new ArrayList<>();
	 * 
	 * public List<CFCAttachment> getDocumentList() { return documentList; }
	 * 
	 * public void setDocumentList(List<CFCAttachment> documentList) {
	 * this.documentList = documentList; }
	 */
	@Autowired
    private NewWaterConnectionService newWaterConnectionService;
	
	@Autowired
    private IChecklistVerificationService iChecklistVerificationService;
	
	/*
	 * private CFCApplicationAddressEntity cfcAddressEntity = new
	 * CFCApplicationAddressEntity();
	 * 
	 * public CFCApplicationAddressEntity getCfcAddressEntity() { return
	 * cfcAddressEntity; }
	 * 
	 * public void setCfcAddressEntity(CFCApplicationAddressEntity cfcAddressEntity)
	 * { this.cfcAddressEntity = cfcAddressEntity; }
	 */

	/*
	 * public NewWaterConnectionReqDTO getReq1DTO() { return req1DTO; }
	 * 
	 * public void setReqDTO(final NewWaterConnectionReqDTO reqDTO) { this.req1DTO =
	 * reqDTO; }
	 */
    
    public TbCsmrInfoDTO getPropertyDetailsByPropertyNumber(NewWaterConnectionReqDTO requestDTO) {
        return newWaterConnectionService.getPropertyDetailsByPropertyNumber(requestDTO);
    }
    
	/*
	 * @Autowired private ICFCApplicationMasterService cfcService;
	 */
	/*
	 * private TbCfcApplicationMstEntity cfcEntity = new
	 * TbCfcApplicationMstEntity();
	 */
    
	/*
	 * private boolean authView;
	 * 
	 * private boolean scrutinyApplicable = false;
	 * 
	 * public boolean isAuthView() { return authView; }
	 * 
	 * public void setAuthView(boolean authView) { this.authView = authView; }
	 * 
	 * public boolean isScrutinyApplicable() { return scrutinyApplicable; }
	 * 
	 * public void setScrutinyApplicable(boolean scrutinyApplicable) {
	 * this.scrutinyApplicable = scrutinyApplicable; }
	 */

	/*
	 * public TbCfcApplicationMstEntity getCfcEntity() { return cfcEntity; }
	 * 
	 * public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
	 * this.cfcEntity = cfcEntity; }
	 */
	private String scrutinyViewMode;
	private List<TbLocationMas> locationMasList;

	public List<TbLocationMas> getLocationMasList() {
		return locationMasList;
	}

	public void setLocationMasList(List<TbLocationMas> locationMasList) {
		this.locationMasList = locationMasList;
	}

	public String getScrutinyViewMode() {
		return scrutinyViewMode;
	}

	public void setScrutinyViewMode(String scrutinyViewMode) {
		this.scrutinyViewMode = scrutinyViewMode;
	}
	
	
	@Override
	public void populateApplicationData(long applicationId) {

		setApmApplicationId(applicationId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		NoDueCerticateDTO dto = noDuesCertificateService.getNoDuesApplicationData(getApmApplicationId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
		setNodueCertiDTO(dto);
		ServiceMaster master = ApplicationContextProvider.getApplicationContext().getBean(TbServicesMstService.class)
				.findShortCodeByOrgId(MainetConstants.CFCServiceCode.Nursing_Home_Reg, orgId);

		this.setLocationMasList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
				.getlocationByDeptId(master.getTbDepartment().getDpDeptid(), orgId));
		this.setScrutinyViewMode(MainetConstants.FlagV);
		
	}


}
