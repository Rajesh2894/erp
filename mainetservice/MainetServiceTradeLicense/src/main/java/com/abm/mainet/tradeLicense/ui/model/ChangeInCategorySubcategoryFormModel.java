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

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
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
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.domain.TbMlItemDetail;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IChangeCategorySubCategoryService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;

@Component
@Scope("session")
public class ChangeInCategorySubcategoryFormModel extends AbstractFormModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
    private TradeMasterDetailDTO tradeDTO = null;
    private String licenseDetails;
    private String licFromDateDesc;
    private String licToDateDesc;
    private String checklistCheck;
    private String paymentCheck;
    private String propertyActiveStatus;
    private List<DocumentDetailsVO> checkList = new ArrayList<>();
    private ServiceMaster serviceMaster = new ServiceMaster();
    private List<CFCAttachment> documentList = new ArrayList<>();
    private String scrutunyEditMode;
    private List<TbMlItemDetail> itemList = new ArrayList<>();
    private List<TradeLicenseItemDetailDTO> itemDetailsList = new ArrayList<>();
    private String editMode;
    private String newEntry;
    private String checkListApplFlag;
    private String applicationchargeApplFlag;
    private String scrutinyAppFlag;
    private String checklistFlag;
    private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
    private Long applicationId;
	private Date appDate;
	private String appTime;
	private String departmentName;
	private String applicantName;
	private Date dueDate;
	private String viewMode;
	private String showHideFlag;
	private String editAppFlag;
    

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private ITradeLicenseApplicationService tradeLicenseApplicationService;

    @Autowired
    private IChecklistVerificationService checklistVerificationService;

    @Autowired
    private TbCfcApplicationMstService cfcApplicationMasterService;

    @Override
    public boolean saveForm() {

        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
        String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
        Date newDate = new Date();
        TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
        TradeMasterDetailDTO tradeDto = getTradeDTO();
        if (masDto.getCreatedBy() == null) {
            masDto.setCreatedBy(createdBy);
            masDto.setCreatedDate(newDate);
            masDto.setOrgid(orgId);
            masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
            masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
                ownDto.setCreatedBy(createdBy);
                ownDto.setCreatedDate(newDate);
                ownDto.setOrgid(orgId);
                ownDto.setLgIpMac(lgIp);
            });

            masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
                itemDto.setCreatedBy(createdBy);
                itemDto.setCreatedDate(newDate);
                itemDto.setOrgid(orgId);
                itemDto.setLgIpMac(lgIp);
            });

        } else {
            masDto.setUpdatedBy(createdBy);
            masDto.setUpdatedDate(newDate);
            masDto.setOrgid(orgId);
            masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
            masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
                if (ownDto.getCreatedBy() == null) {
                    ownDto.setCreatedBy(createdBy);
                    ownDto.setCreatedDate(newDate);
                    ownDto.setOrgid(orgId);
                    ownDto.setLgIpMac(lgIp);
                } else {
                    ownDto.setUpdatedBy(createdBy);
                    ownDto.setUpdatedDate(newDate);
                    ownDto.setOrgid(orgId);
                    ownDto.setLgIpMac(lgIp);
                }
            });

            masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
                if (itemDto.getCreatedBy() == null) {
                    itemDto.setCreatedBy(createdBy);
                    itemDto.setCreatedDate(newDate);
                    itemDto.setOrgid(orgId);
                    itemDto.setLgIpMac(lgIp);
                } else {
                    itemDto.setUpdatedBy(createdBy);
                    itemDto.setUpdatedDate(newDate);
                    itemDto.setOrgid(orgId);
                    itemDto.setLgIpMac(lgIp);
                }
            });

        }

        masDto.setOrgId(orgId);
        masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
        TradeMasterDetailDTO tradeDetailDto = this.getTradeMasterDetailDTO();
        TradeMasterDetailDTO tradeDTO = new TradeMasterDetailDTO();
        List<TradeLicenseItemDetailDTO> itemdDetailsList = new ArrayList<>();
        BeanUtils.copyProperties(tradeDetailDto, tradeDTO);
        tradeDTO.setLangId((long) UserSession.getCurrent().getLanguageId());
        tradeDetailDto.getTradeLicenseItemDetailDTO().forEach(itemdDetails -> {
            TradeLicenseItemDetailDTO itemDto = new TradeLicenseItemDetailDTO();
            BeanUtils.copyProperties(itemdDetails, itemDto);
            itemDto.setTriCod1(itemdDetails.getTriCategory1());
            itemDto.setTriCod2(itemdDetails.getTriCategory2());
            itemDto.setTriCod3(itemdDetails.getTriCategory3());
            itemDto.setTriCod4(itemdDetails.getTriCategory4());
            itemDto.setTriCod5(itemdDetails.getTriCategory5());
            itemdDetailsList.add(itemDto);
        });

        tradeDTO.setTradeLicenseItemDetailDTO(itemdDetailsList);
        tradeDetailDto.setTradeLicenseItemDetailDTO(itemdDetailsList);
        setTradeMasterDetailDTO(tradeDTO);

        if (getScrutunyEditMode() != null && getScrutunyEditMode().equals("SM")) {

            masDto = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(masDto.getTrdLicno(), orgId);
            masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
            masDto.setTradeLicenseItemDetailDTO( tradeDetailDto.getTradeLicenseItemDetailDTO());
            masDto = ApplicationContextProvider.getApplicationContext().getBean(IChangeCategorySubCategoryService.class)
                    .getCategoryWiseLoiChargesFromBrmsRule(masDto);
            
           
            
        }
        
        
        BigDecimal paymentAmount = masDto.getTotalApplicationFee();
        if (paymentAmount != null) {
            tradeMasterDetailDTO.setFree(false);
            masDto.setFree(false);
        } else {
            tradeMasterDetailDTO.setFree(true);
            masDto.setFree(true);
        }
        
        
        masDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

        masDto = ApplicationContextProvider.getApplicationContext().getBean(IChangeCategorySubCategoryService.class)
                .saveChangeCategorySubcategoryService(masDto, tradeDto);
        setTradeMasterDetailDTO(masDto);

        
      if(getServiceMaster().getSmAppliChargeFlag().equalsIgnoreCase(MainetConstants.FlagY))
        {
        
        
        final CommonChallanDTO offline = getOfflineDTO();
        Map<Long, Double> details = new HashMap<>(0);
        final Map<Long, Long> billDetails = new HashMap<>(0);
        getTradeMasterDetailDTO().setTotalApplicationFee(new BigDecimal(100));
        setChallanDToandSaveChallanData(offline, details, billDetails, getTradeMasterDetailDTO());
        RequestDTO requestDTO1 = new RequestDTO();
        requestDTO1.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO1.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        requestDTO1.setStatus(MainetConstants.FlagA);
        requestDTO1.setIdfId(masDto.getApmApplicationId().toString());
        requestDTO1.setDepartmentName(MainetConstants.TradeLicense.MARKET_LICENSE);
        requestDTO1.setApplicationId(masDto.getApmApplicationId());
        requestDTO1.setDeptId(getServiceMaster().getTbDepartment().getDpDeptid());
        requestDTO1.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
        requestDTO1.setServiceId(getServiceMaster().getSmServiceId());
        requestDTO1.setReferenceId(getTradeMasterDetailDTO().getApmApplicationId().toString());
        }

        this.setSuccessMessage(getAppSession().getMessage("trade.successMsg") + masDto.getApmApplicationId());
        return true;
    }

    private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final Map<Long, Double> details,
            final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

        final UserSession session = UserSession.getCurrent();
        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(
                        MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE,
                        session.getOrganisation().getOrgid());
        setServiceMaster(sm);
        TradeLicenseOwnerDetailDTO ownDtlDto = tradeMaster.getTradeLicenseOwnerdetailDTO().get(0);
        offline.setAmountToPay(tradeMaster.getTotalApplicationFee().toString());
        offline.setUserId(session.getEmployee().getEmpId());
        offline.setOrgId(session.getOrganisation().getOrgid());
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
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            offline.setDocumentUploaded(true);
        }

        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
                PrefixConstants.NewWaterServiceConstants.CAA, session.getOrganisation());

        final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
                .fetchAllApplicableServiceCharge(sm.getSmServiceId(),
                        UserSession.getCurrent().getOrganisation().getOrgid(), chargeApplicableAt.getLookUpId());

        for (TbTaxMasEntity tbTaxMas : taxesMaster) {
            offline.getFeeIds().put(tbTaxMas.getTaxId(), tradeMaster.getTotalApplicationFee().doubleValue());
        }
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());
     /*   if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
            final ChallanMaster master = ApplicationContextProvider.getApplicationContext().getBean(IChallanService.class)
                    .InvokeGenerateChallan(offline);
            offline.setChallanValidDate(master.getChallanValiDate());
            offline.setChallanNo(master.getChallanNo());
        } else if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER))*/ {
            final ChallanReceiptPrintDTO printDto = ApplicationContextProvider.getApplicationContext()
                    .getBean(IChallanService.class)
                    .savePayAtUlbCounter(offline, sm.getSmServiceName());
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
            checkListModel2.setServiceCode(MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE);

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
                }
            }
        }
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

       // validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }
    /* end of validation method */

    @Override
    public void populateApplicationData(long applicationId) {
        /*Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        TbCfcApplicationMst entity = cfcApplicationMasterService.findById(applicationId);*/
        TradeMasterDetailDTO tradeDetail = tradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(applicationId);
        List<TradeLicenseItemDetailDTO> itemDto = new ArrayList<>();
        if (null != tradeDetail && CollectionUtils.isNotEmpty(tradeDetail.getTradeLicenseItemDetailDTO()) ) {
        List<TradeLicenseItemDetailDTO> licenseHistDetBuTrdId = tradeLicenseApplicationService.getTradeLicenseHistDetBuTrdId(tradeDetail.getTradeLicenseItemDetailDTO().get(0).getTriId());
        if (CollectionUtils.isNotEmpty(licenseHistDetBuTrdId))
        itemDto  = licenseHistDetBuTrdId.stream().filter(t-> t.getTriStatus().equals(MainetConstants.FlagN)).collect(Collectors.toList());
        }
        List<TradeLicenseItemDetailDTO> detListNew = tradeDetail.getTradeLicenseItemDetailDTO().stream()
                .filter(s -> s.getTriStatus().equals("A") || s.getTriStatus().equals("Y")).collect(Collectors.toList());

        tradeDetail.setTradeLicenseItemDetailDTO(itemDto);
        setTradeDTO(tradeDetail);
        
        TradeMasterDetailDTO tradeNewDTO = new TradeMasterDetailDTO();
        BeanUtils.copyProperties(tradeDetail, tradeNewDTO);
        tradeNewDTO.setTradeLicenseItemDetailDTO(detListNew);
        setTradeMasterDetailDTO(tradeNewDTO);
        setLicFromDateDesc(Utility.dateToString(getTradeMasterDetailDTO().getTrdLicfromDate()));
        setLicToDateDesc(Utility.dateToString(getTradeMasterDetailDTO().getTrdLicfromDate()));
        this.setScrutunyEditMode(null);
        this.setLicenseDetails("Y");
        this.setEditMode("E");
        this.documentList = checklistVerificationService.getDocumentUploaded(applicationId,
                UserSession.getCurrent().getOrganisation().getOrgid());
    }

    public TradeMasterDetailDTO getTradeMasterDetailDTO() {
        return tradeMasterDetailDTO;
    }

    public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
        this.tradeMasterDetailDTO = tradeMasterDetailDTO;
    }

    public String getLicenseDetails() {
        return licenseDetails;
    }

    public void setLicenseDetails(String licenseDetails) {
        this.licenseDetails = licenseDetails;
    }

    public String getLicFromDateDesc() {
        return licFromDateDesc;
    }

    public void setLicFromDateDesc(String licFromDateDesc) {
        this.licFromDateDesc = licFromDateDesc;
    }

    public String getLicToDateDesc() {
        return licToDateDesc;
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

    public String getPaymentCheck() {
        return paymentCheck;
    }

    public void setPaymentCheck(String paymentCheck) {
        this.paymentCheck = paymentCheck;
    }

    public String getPropertyActiveStatus() {
        return propertyActiveStatus;
    }

    public void setPropertyActiveStatus(String propertyActiveStatus) {
        this.propertyActiveStatus = propertyActiveStatus;
    }

    public TradeMasterDetailDTO getTradeDTO() {
        return tradeDTO;
    }

    public void setTradeDTO(TradeMasterDetailDTO tradeDTO) {
        this.tradeDTO = tradeDTO;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    public void setServiceMaster(ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    public String getScrutunyEditMode() {
        return scrutunyEditMode;
    }

    public void setScrutunyEditMode(String scrutunyEditMode) {
        this.scrutunyEditMode = scrutunyEditMode;
    }

    public List<TbMlItemDetail> getItemList(List<TbMlItemDetail> itemdetail) {
        return itemList;
    }

    public void setItemList(List<TbMlItemDetail> itemList) {
        this.itemList = itemList;
    }

    public List<TradeLicenseItemDetailDTO> getItemDetailsList() {
        return itemDetailsList;
    }

    public void setItemDetailsList(List<TradeLicenseItemDetailDTO> itemDetailsList) {
        this.itemDetailsList = itemDetailsList;
    }

    public String getEditMode() {
        return editMode;
    }

    public void setEditMode(String editMode) {
        this.editMode = editMode;
    }

    public String getNewEntry() {
        return newEntry;
    }

    public void setNewEntry(String newEntry) {
        this.newEntry = newEntry;
    }

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public String getApplicationchargeApplFlag() {
		return applicationchargeApplFlag;
	}

	public void setApplicationchargeApplFlag(String applicationchargeApplFlag) {
		this.applicationchargeApplFlag = applicationchargeApplFlag;
	}

	public String getScrutinyAppFlag() {
		return scrutinyAppFlag;
	}

	public void setScrutinyAppFlag(String scrutinyAppFlag) {
		this.scrutinyAppFlag = scrutinyAppFlag;
	}

	public String getChecklistFlag() {
		return checklistFlag;
	}

	public void setChecklistFlag(String checklistFlag) {
		this.checklistFlag = checklistFlag;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
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

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getShowHideFlag() {
		return showHideFlag;
	}

	public void setShowHideFlag(String showHideFlag) {
		this.showHideFlag = showHideFlag;
	}

	public String getEditAppFlag() {
		return editAppFlag;
	}

	public void setEditAppFlag(String editAppFlag) {
		this.editAppFlag = editAppFlag;
	}
}
