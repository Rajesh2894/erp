package com.abm.mainet.tradeLicense.ui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
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
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ICancellationLicenseService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.validator.CancellationLicenseFormValidator;

@Component
@Scope("session")
public class CancellationLicenseFormModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<TradeMasterDetailDTO> tradeMasterDetailDTO = new ArrayList<>();
    private String licenseDetails;
    private TradeMasterDetailDTO tradeDetailDTO = new TradeMasterDetailDTO();
    private String licFromDateDesc;
    private String licToDateDesc;
    private String checklistCheck;
    private String viewMode;
    private String checkListApplFlag;
    private String applicationchargeApplFlag;
    private ServiceMaster serviceMaster = new ServiceMaster();
    private List<DocumentDetailsVO> checkList = new ArrayList<>();
    private String paymentCheck;
    private String cancellationFlag;
    private String scrutunyEditMode;
    private List<CFCAttachment> documentList = new ArrayList<>();
    private Long applicationId;
	private String applicantName;
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private IChecklistVerificationService checklistVerificationService;

    @Autowired
    private ITradeLicenseApplicationService tradeLicenseApplicationService;

    @Autowired
    private TbCfcApplicationMstService cfcApplicationMasterService;
    
    
    @Autowired
   	TbTaxMasService tbTaxMasService;
    


    @Override
    public boolean saveForm() {

        TradeMasterDetailDTO masDto = getTradeDetailDTO();
        String lgIpMacUpd = UserSession.getCurrent().getEmployee().getEmppiservername();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        masDto.setOrgid(orgId);
        masDto.setLgIpMacUpd(lgIpMacUpd);
        masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
        ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        
        //124306 - trdstaus changed to transit till last approval
        long trdStatus= CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS",UserSession.getCurrent().getOrganisation()).getLookUpId();
        masDto.setTrdStatus(trdStatus);

        if (getScrutunyEditMode() != null && getScrutunyEditMode().equals("SM")
                && service.getSmScrutinyChargeFlag().equals(MainetConstants.FlagY)) {
            masDto.setApplicationchargeApplFlag(MainetConstants.FlagN);
            masDto = ApplicationContextProvider.getApplicationContext().getBean(ICancellationLicenseService.class)
                    .getscrutinyChargesBrmsRule(masDto);
        }

        BigDecimal paymentAmount = masDto.getTotalApplicationFee();
        if (paymentAmount != null) {
            this.getTradeDetailDTO().setFree(false);

        } else {
            this.getTradeDetailDTO().setFree(true);
        }

        masDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        masDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        masDto.setServiceId(service.getSmServiceId());
        masDto.setDeptId(service.getTbDepartment().getDpDeptid());
        ApplicationContextProvider.getApplicationContext().getBean(ICancellationLicenseService.class)
                .saveCancellationService(masDto);

        final CommonChallanDTO offline = getOfflineDTO();
        Map<Long, Double> details = new HashMap<>(0);
        final Map<Long, Long> billDetails = new HashMap<>(0);
        if ((service.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) && (this.getScrutunyEditMode() == null)) {
            setChallanDToandSaveChallanData(offline, details, billDetails, getTradeDetailDTO());
        }
        this.setSuccessMessage(getAppSession().getMessage("trade.cancellation.successMsg")
                + masDto.getApmApplicationId());

        if (getScrutunyEditMode() != null && getScrutunyEditMode().equals("SM")) {
            this.setSuccessMessage(getAppSession().getMessage("trade.successMessage"));
        }
       
        return true;
    }

    private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
            final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

        final UserSession session = UserSession.getCurrent();
        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE,
                        session.getOrganisation().getOrgid());
        setServiceMaster(sm);
        TradeLicenseOwnerDetailDTO ownDtlDto = tradeMaster.getTradeLicenseOwnerdetailDTO().get(0);
        if (tradeMaster.getTotalApplicationFee() != null) {
            offline.setAmountToPay(tradeMaster.getTotalApplicationFee().toString());
        }
        offline.setUserId(session.getEmployee().getEmpId());
        offline.setOrgId(session.getOrganisation()
                .getOrgid());
        offline.setLangId(session.getLanguageId());
        offline.setLgIpMac(session.getEmployee().getEmppiservername());
        if ((details != null) && !details.isEmpty()) {
            offline.setFeeIds(details);
        }
        if ((billDetails != null) && !billDetails.isEmpty()) {
            offline.setBillDetIds(billDetails);
        }
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setPaymentCategory(MainetConstants.TradeLicense.PAYMENT_CATEGORY);
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        offline.setEmailId(ownDtlDto.getTroEmailid());
        offline.setApplicantName(ownDtlDto.getTroName());
        offline.setApplNo(tradeMaster.getApmApplicationId());
        offline.setUniquePrimaryId(tradeMaster.getApmApplicationId().toString());
        offline.setApplicantAddress(ownDtlDto.getTroAddress());
        offline.setMobileNumber(ownDtlDto.getTroMobileno());
        offline.setDeptId(sm.getTbDepartment().getDpDeptid());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        offline.setServiceId(sm.getSmServiceId());
        offline.setLicNo(tradeMaster.getTrdLicno());
        offline.setServiceName(sm.getSmServiceName());
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            offline.setDocumentUploaded(true);
        }

        
        
        final LookUp chargeApplicableAt =
        		  CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.
        		 APL, PrefixConstants.NewWaterServiceConstants.CAA,
        		 session.getOrganisation());
        		 


        		final List<TbTaxMasEntity> taxesMaster = tbTaxMasService.fetchAllApplicableServiceCharge(sm.getSmServiceId(),
        				UserSession.getCurrent().getOrganisation().getOrgid(), chargeApplicableAt.getLookUpId());
          
          
        		 long appChargetaxId = CommonMasterUtility
  	                    .getHieLookupByLookupCode("AC", PrefixConstants.LookUpPrefix.TAC, 2,
  	                    		UserSession.getCurrent().getOrganisation().getOrgid())
  	                    .getLookUpId();
          
        		for (TbTaxMasEntity tbTaxMas : taxesMaster) {
      			if (tbTaxMas.getTaxCategory2() == appChargetaxId)
      			{
      			offline.getFeeIds().put(tbTaxMas.getTaxId(), Double.valueOf(tradeMaster.getApplicationCharge()));
      			}
      		}
          
           
        
        
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        if (offline.getOflPaymentMode() != 0l) {
            offline.setOfflinePaymentText(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                    .getLookUpCode());
        }
        if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(
                        MainetConstants.PAYMENT_TYPE.OFFLINE)) {
            final ChallanMaster master = iChallanService
                    .InvokeGenerateChallan(offline);
            offline.setChallanValidDate(master
                    .getChallanValiDate());
            offline.setChallanNo(master.getChallanNo());
        } else if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
            final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
                    sm.getSmServiceName());
            setReceiptDTO(printDto);
        }
        setOfflineDTO(offline);
    }

    @SuppressWarnings("unchecked")
    public void getCheckListFromBrms() {

        final WSRequestDTO requestDTO = new WSRequestDTO();
        requestDTO.setModelName(MainetConstants.TradeLicense.CHECK_LIST_MODEL);
        WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
            final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
            checkListModel2.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            checkListModel2.setServiceCode(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE);

            WSRequestDTO checklistReqDto = new WSRequestDTO();
            checklistReqDto.setDataModel(checkListModel2);
            WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
                    || MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

                if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
                    List<DocumentDetailsVO> checkListList = Collections.emptyList();
                    checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();
                    long cnt = 1;
                    for (final DocumentDetailsVO doc : checkListList) {
                        doc.setDocumentSerialNo(cnt);
                        cnt++;
                    }
                    if ((checkListList != null) && !checkListList.isEmpty()) {
                        setCheckList(checkListList);
                    }
                } else {
                    addValidationError(ApplicationSession.getInstance().getMessage("No CheckList Found"));

                }
            }
        }
    }

    @Override
    public void populateApplicationData(long applicationId) {

        TbCfcApplicationMst entity = cfcApplicationMasterService.findById(applicationId);
      //125445 code updated to show LOI Data  on portal Dashboard
        TradeMasterDetailDTO tradeDetail = tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId);
               // .getLicenseDetailsByLicenseNo(entity.getRefNo(), UserSession.getCurrent().getOrganisation().getOrgid());
        this.setLicFromDateDesc(Utility.dateToString(tradeDetail.getTrdLicfromDate()));
        this.setLicToDateDesc(Utility.dateToString(tradeDetail.getTrdLictoDate()));
        this.setTradeDetailDTO(tradeDetail);
        this.setScrutunyEditMode(null);
        
        TradeMasterDetailDTO masDto = getTradeDetailDTO();
        //122717
        String userName=null;
 		List<TradeLicenseOwnerDetailDTO> ownerDetailDTOList = null;
		ownerDetailDTOList = masDto.getTradeLicenseOwnerdetailDTO().parallelStream()
				.filter(ownDto -> ownDto != null && (ownDto.getTroPr() != null)
						&& ownDto.getTroPr().equalsIgnoreCase(MainetConstants.FlagA))
				.collect(Collectors.toList());
         for(TradeLicenseOwnerDetailDTO dto : ownerDetailDTOList) {
         	if(dto.getTroName() != null) {
         		if(userName == null) {
         		userName =dto.getTroName();
         		}else {
         		userName  +=" , "+dto.getTroName();
         		}
         	}
         }
         masDto.setTrdOwnerNm(userName);
         masDto.getTrdOwnerNm();

       
        List<CFCAttachment> attachment = null;
        for (int i = 0; i < masDto.getTradeLicenseOwnerdetailDTO().size(); i++) {
            attachment = new ArrayList<>();
            attachment = checklistVerificationService.getDocumentUploadedByRefNo(
                    masDto.getTradeLicenseOwnerdetailDTO().get(i).getTroId().toString(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            if (attachment != null) {
                masDto.getTradeLicenseOwnerdetailDTO().get(i).setViewImg(attachment);
            }
        }

        this.documentList = checklistVerificationService.getDocumentUploaded(applicationId,
                UserSession.getCurrent().getOrganisation().getOrgid());

    }

    @SuppressWarnings("unchecked")
    public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

        Object dataModel = null;
        LinkedHashMap<Long, Object> responseMap = null;
        final List<Object> dataModelList = new ArrayList<>();
        try {
            if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
                final List<?> list = (List<?>) response.getResponseObj();
                final Object object = list.get(position);
                responseMap = (LinkedHashMap<Long, Object>) object;
                final String jsonString = new JSONObject(responseMap).toString();
                dataModel = new ObjectMapper().readValue(jsonString, clazz);
                dataModelList.add(dataModel);
            }

        } catch (final IOException e) {
            logger.error("Error Occurred during cast response object while BRMS call is success!", e);
        }

        return dataModelList;

    }

    
    /* for validating Inputs and other functional validation */
    public boolean validateInputs() {
    	//Defect #108699
    	validateBean(this, CancellationLicenseFormValidator.class);
        if (this.tradeDetailDTO.getApplicationchargeApplFlag().equals("Y") && this.getCancellationFlag().equals("N")) {
            validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
        }
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }
    /* end of validation method */
    

    public String getLicenseDetails() {
        return licenseDetails;
    }

    public void setLicenseDetails(String licenseDetails) {
        this.licenseDetails = licenseDetails;
    }

    public List<TradeMasterDetailDTO> getTradeMasterDetailDTO() {
        return tradeMasterDetailDTO;
    }

    public void setTradeMasterDetailDTO(List<TradeMasterDetailDTO> tradeMasterDetailDTO) {
        this.tradeMasterDetailDTO = tradeMasterDetailDTO;
    }

    public TradeMasterDetailDTO getTradeDetailDTO() {
        return tradeDetailDTO;
    }

    public void setTradeDetailDTO(TradeMasterDetailDTO tradeDetailDTO) {
        this.tradeDetailDTO = tradeDetailDTO;
    }

    public String getLicFromDateDesc() {
        return licFromDateDesc;
    }

    public String getLicToDateDesc() {
        return licToDateDesc;
    }

    public void setLicFromDateDesc(String licFromDateDesc) {
        this.licFromDateDesc = licFromDateDesc;
    }

    public void setLicToDateDesc(String licToDateDesc) {
        this.licToDateDesc = licToDateDesc;
    }

    public String getChecklistCheck() {
        return checklistCheck;
    }

    public void setChecklistCheck(String checklistCheck) {
        this.checklistCheck = checklistCheck;
    }

    public String getViewMode() {
        return viewMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

    public String getCheckListApplFlag() {
        return checkListApplFlag;
    }

    public String getApplicationchargeApplFlag() {
        return applicationchargeApplFlag;
    }

    public void setCheckListApplFlag(String checkListApplFlag) {
        this.checkListApplFlag = checkListApplFlag;
    }

    public void setApplicationchargeApplFlag(String applicationchargeApplFlag) {
        this.applicationchargeApplFlag = applicationchargeApplFlag;
    }

    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    public void setServiceMaster(ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public String getPaymentCheck() {
        return paymentCheck;
    }

    public void setPaymentCheck(String paymentCheck) {
        this.paymentCheck = paymentCheck;
    }

    public String getCancellationFlag() {
        return cancellationFlag;
    }

    public void setCancellationFlag(String cancellationFlag) {
        this.cancellationFlag = cancellationFlag;
    }

    public String getScrutunyEditMode() {
        return scrutunyEditMode;
    }

    public void setScrutunyEditMode(String scrutunyEditMode) {
        this.scrutunyEditMode = scrutunyEditMode;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getHelpLine() {
		return helpLine;
	}

	public void setHelpLine(String helpLine) {
		this.helpLine = helpLine;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

}
