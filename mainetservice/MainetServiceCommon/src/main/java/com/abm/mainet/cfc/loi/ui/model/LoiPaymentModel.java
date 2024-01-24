/**
 *
 */
package com.abm.mainet.cfc.loi.ui.model;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistSearchService;
import com.abm.mainet.cfc.loi.dto.LoiPaymentSearchDTO;
import com.abm.mainet.cfc.loi.dto.LoiPrintDTO;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WaterNoDueDto;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dao.IWorkflowTypeDAO;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author Rahul.Yadav
 *
 */
@Component
@Scope("session")
public class LoiPaymentModel extends AbstractFormModel {

    private static final long serialVersionUID = 918120968839859874L;

    private static final Logger logger = LoggerFactory.getLogger(LoiPaymentModel.class);

    @Autowired
    private TbLoiMasService itbLoiMasService;

    @Autowired
    private TbLoiDetService itbLoidetService;

    @Autowired
    private IChallanService iChallanService;

    @Autowired
    private ICFCApplicationMasterService iCFCApplicationMasterService;

    @Autowired
    private TbServicesMstService iTbServicesMstService;

    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;

    @Autowired
    private IFileUploadService fileUploadService;
    @Autowired
    private DepartmentService departmentService;
    
    @Resource
    private ServiceMasterService serviceMaster;
    
    @Autowired
    private IWorkflowTypeDAO iWorkflowTypeDAO;

    private TbLoiMas loiMaster = new TbLoiMas();

    private LoiPaymentSearchDTO searchDto = new LoiPaymentSearchDTO();

    private List<TbLoiDet> loiDetail = null;

    private ChallanReceiptPrintDTO receiptDto = null;

    private List<Object[]> deptList;
    private List<Object[]> serviceList;
    private List<Object[]> loiDetailList;

    private Long applicationId;

    private String loiNo;

    private Date loiDate;

    private String pageUrl;

    private Long orgId;

    private String status;

    private Map<String, Double> waterDues;

    private Map<String, Double> propDues;

    private String wndServiceFlag;
    private List<String> remarkList;

    private String departmentCode;
    
    private boolean showPropDues;
    
	private boolean flag;
	
	
	private List<TbCfcApplicationMstEntity> applNameList;
	

    public Map<String, Double> getWaterDues() {
        return waterDues;
    }

    public void setWaterDues(Map<String, Double> waterDues) {
        this.waterDues = waterDues;
    }

    public Map<String, Double> getPropDues() {
        return propDues;
    }

    public void setPropDues(Map<String, Double> propDues) {
        this.propDues = propDues;
    }

    public String getWndServiceFlag() {
        return wndServiceFlag;
    }

    public void setWndServiceFlag(String wndServiceFlag) {
        this.wndServiceFlag = wndServiceFlag;
    }

    @Autowired
    private IChecklistSearchService checklistSearchService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    /**
     * @return
     * @throws LinkageError
     * @throws ClassNotFoundException
     */
    public boolean getLoiData(String status) throws ClassNotFoundException, LinkageError {

        logger.info("start the  getLoiData()");
        LoiPaymentSearchDTO dto = null;
        final LoiPaymentSearchDTO searchdata = getSearchDto();
        final ApplicationSession appSession = ApplicationSession.getInstance();

        if ((searchdata.getApplicationId() == null)
        		&& (searchdata.getReferenceNo() == null)
                && ((searchdata.getLoiNo() == null) || searchdata.getLoiNo().isEmpty())
                && (searchdata.getLoiDate() == null)) {
            setLoiMaster(new TbLoiMas());
            addValidationError(appSession.getMessage("loiPay.msg.appId"));
            return false;
        } else if ((searchdata.getLoiNo() != null) && !searchdata.getLoiNo().isEmpty()
                && (searchdata.getLoiDate() == null)) {
            setLoiMaster(new TbLoiMas());
            addValidationError(appSession.getMessage("loiPay.msg.appId"));
            return false;
        } else if (((searchdata.getLoiNo() == null) || searchdata.getLoiNo().isEmpty())
                && (searchdata.getLoiDate() != null)) {
            setLoiMaster(new TbLoiMas());
            addValidationError(appSession.getMessage("loiPay.msg.appId"));
            return false;
        }
        getSearchDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        if(searchdata.getReferenceNo() != null && !searchdata.getReferenceNo().isEmpty()){
        TbCfcApplicationMstEntity cfcMst = iCFCApplicationMasterService.getCFCApplicationByRefNoOrAppNo(searchDto.getReferenceNo(), null, UserSession.getCurrent().getOrganisation().getOrgid());
        searchdata.setApplicationId(cfcMst.getApmApplicationId());
        }
        final TbLoiMas master = itbLoiMasService.findLoiMasBySearchCriteria(searchdata, status);

        if (master != null) {
        	//Defect #135030 change request dto orgid to master orgid
            TbCfcApplicationMstEntity applicationMaster = null;
            // code added for restrict the orgid dependancy for RTI module
            String seviceShortCode = null;
            Boolean flag = false;
            //D#136539
             TbServicesMst serviceMst=null;
            if (master.getLoiServiceId() != null) {
                seviceShortCode = iTbServicesMstService.getServiceShortDescByServiceId(master.getLoiServiceId());
               serviceMst = iTbServicesMstService.findById(master.getLoiServiceId());
               if(serviceMst.getTbDepartment()!=null) {
            	   master.setDeptShortCode(serviceMst.getTbDepartment().getDpDeptcode());
               }
               if(serviceMst.getSmShortdesc()!= null){
            	   master.setServiceShortCode(serviceMst.getSmShortdesc());
               }
            }

            dto = itbLoidetService.findLoiDetailsByLoiMasAndOrgId(master, master.getOrgid());
            LookUp lookUp = null;
            try {
                lookUp = CommonMasterUtility.getValueFromPrefixLookUp("SCW", MainetConstants.ENV,
                        UserSession.getCurrent().getOrganisation());
            } catch (Exception e) {
            }

            if (lookUp != null && StringUtils.equals(MainetConstants.FlagY, lookUp.getOtherField())) {
                WaterNoDueDto waterNoDueDto = null;
                if (StringUtils.equals("WND", seviceShortCode)) {

                    Class<?> clazz = null;
                    Object dynamicServiceInstance = null;
                    String serviceClassName = null;
                    serviceClassName = "com.abm.mainet.water.service.WaterNoDuesCertificateServiceImpl";

                    clazz = ClassUtils.forName(serviceClassName,
                            ApplicationContextProvider.getApplicationContext().getClassLoader());

                    dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
                            .getAutowireCapableBeanFactory().autowire(clazz, 4, false);
                    final Method method = ReflectionUtils.findMethod(clazz,
                            ApplicationSession.getInstance().getMessage("getNoDuesApplicationDataForScrutiny"),
                            new Class[] { Long.class, Long.class });
                    waterNoDueDto = (WaterNoDueDto) ReflectionUtils.invokeMethod(method, dynamicServiceInstance,
                            new Object[] { master.getLoiApplicationId(),
                                    UserSession.getCurrent().getOrganisation().getOrgid() });
                    Map<String, Double> propDue = new HashMap<String, Double>();
                    Map<String, Double> waterDue = new HashMap<String, Double>();

                    propDue.put("Property Due Amount", waterNoDueDto.getPropDueAmt());
                    waterDue.put("Water Due Amount", waterNoDueDto.getWaterDueAmt());
                    setPropDues(propDue);
                    setWaterDues(waterDue);
                    setWndServiceFlag(MainetConstants.FlagY);
                    Double total = dto.getTotal();
                    total += waterNoDueDto.getPropDueAmt();
                    total += waterNoDueDto.getWaterDueAmt();
                    dto.setTotal(total);
                }

            }
            
            fetchPropOutstandingAmount(master.getLoiApplicationId(),master.getLoiServiceId());

            if (seviceShortCode != null && (seviceShortCode.equals(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE)
                    || seviceShortCode.equals(MainetConstants.RTISERVICE.RTISECONDAPPEALSERVICE)
                    || seviceShortCode.equals(MainetConstants.RTISERVICE.RTIFIRSTAPPEAL))) {
                applicationMaster = iCFCApplicationMasterService
                        .getCFCApplicationOnlyByApplicationId(master.getLoiApplicationId());
                flag = true;
            } else {
                applicationMaster = iCFCApplicationMasterService
                        .getCFCApplicationByApplicationId(master.getLoiApplicationId(), searchdata.getOrgId());
            }
            if(seviceShortCode!= null && seviceShortCode.equals("DCS"))
             getOfflineDTO().setPropNoConnNoEstateNoV(master.getLoiV3());
          //D#140132 
			if (applicationMaster != null) {
				String userName = (applicationMaster.getApmFname() == null ? MainetConstants.BLANK
						: applicationMaster.getApmFname() + MainetConstants.WHITE_SPACE);
				userName += applicationMaster.getApmMname() == null ? MainetConstants.BLANK
						: applicationMaster.getApmMname() + MainetConstants.WHITE_SPACE;
				userName += applicationMaster.getApmLname() == null ? MainetConstants.BLANK
						: applicationMaster.getApmLname();
				getSearchDto().setApplicantName(userName);
			}
            //User Story #147721
			if (applicationMaster.getApmApplicationDate() != null) {
				getSearchDto().setApplicationDate(Utility.dateToString(applicationMaster.getApmApplicationDate()));
			}
			//getSearchDto().setReferenceNo(applicationMaster.getRefNo());
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.Property.MUT)
                    && applicationMaster.getRefNo() != null) {
                getSearchDto().setReferenceNo(applicationMaster.getRefNo());
            }
            if (serviceMst != null) {
                getSearchDto().setServiceId(serviceMst.getSmServiceId());
                //#149095
                getSearchDto().setServiceName(serviceMst.getSmServiceName());
                getSearchDto().setServiceNameMar(serviceMst.getSmServiceNameMar());
                setDepartmentCode(serviceMst.getTbDepartment().getDpDeptcode());
                setServiceCode(serviceMst.getSmShortdesc());
            }
            // START Defect#126605
            List<TbLoiMas> loiMasList = itbLoiMasService.getloiByApplicationId(master.getLoiApplicationId(),
                    master.getLoiServiceId(), searchdata.getOrgId());
            if (CollectionUtils.isNotEmpty(loiMasList)) {
                TbLoiMas loiMas = loiMasList.get(loiMasList.size() - 1);
                if (loiMas != null && loiMas.getLoiRemark() != null) {
                    String loiRemark = loiMas.getLoiRemark();
                    List<String> remarkList = Stream.of(loiRemark.split(",")).map(rem -> new String(rem))
                            .collect(Collectors.toList());
                    setRemarkList(remarkList);
                }
            }// END Defect#126605
            CFCApplicationAddressEntity address = null;
            if (flag) {
                address = iCFCApplicationMasterService.getApplicantsDetails(master.getLoiApplicationId());
            } else {
                address = iCFCApplicationAddressService.getApplicationAddressByAppId(master.getLoiApplicationId(),
                        searchdata.getOrgId());
            }
            if (address != null) {
                getSearchDto().setEmail(address.getApaEmail());
                getSearchDto().setMobileNo(address.getApaMobilno());
                getSearchDto().setAddress(address.getApaAreanm());
            }
            //#132875 to get task Id 
			if (master.getLoiApplicationId() != null) {
				Long taskId = iWorkflowTypeDAO.getTaskIdByAppIdAndOrgId(Long.valueOf(master.getLoiApplicationId()),
						master.getOrgid());
				if (taskId != null) {
					master.setTaskId(taskId);
					this.setTaskId(taskId);
				}
			}
        }
        getSearchDto().setLoiMasData(master);
       
        if (dto != null) {
            getSearchDto().setChargeDesc(dto.getChargeDesc());
            getSearchDto().setTotal(dto.getTotal());
            getSearchDto().setLoiCharges(dto.getLoiCharges());
            getSearchDto().setApplicationFee(dto.getApplicationFee());
            getSearchDto().setTotalAmntInclApplFee(dto.getTotalAmntInclApplFee());
            getSearchDto().setApplicationFeeTaxId(dto.getApplicationFeeTaxId());
            getOfflineDTO().setAmountToShow(dto.getTotal());
        }

        if ((searchdata != null) && (searchdata.getLoiMasData() != null)) {
            setLoiMaster(searchdata.getLoiMasData());
            getLoiMaster().setLoiRecordFound(MainetConstants.Common_Constant.YES);
            return true;
        }
        setLoiMaster(new TbLoiMas());
        getLoiMaster().setLoiRecordFound(MainetConstants.Common_Constant.NO);
        logger.info("end the  getLoiData()");
        return false;
    }

    @Override
    public boolean saveForm() {

        logger.info("start the  saveForm()");
        final CommonChallanDTO offline = getOfflineDTO();
        final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        validateBean(offline, CommonOfflineMasterValidator.class);
        
        try {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				fetchPropOutstandingAmount(getLoiMaster().getLoiApplicationId(), getLoiMaster().getLoiServiceId());
				if (isShowPropDues() && !getPropDues().isEmpty() && getPropDues().get("Property Due Amount") > 0d) {
					addValidationError("Kindly pay property bill outstanding first");
				}
			}
		} catch (Exception e) {
			throw new FrameworkException("exception occured" + e);
		} 

        final ApplicationSession appSession = ApplicationSession.getInstance();

        if (hasValidationErrors()) {
            return false;
        }

        // 96773 - Save owner names in property tables after LOI payment in SKDCL
        // requirement
        TbServicesMst serviceMst = iTbServicesMstService.findById(getSearchDto().getServiceId());
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.Property.MUT)
                && serviceMst.getSmShortdesc().equalsIgnoreCase(MainetConstants.Property.MUT)) {
            offline.setApplNo(getSearchDto().getLoiMasData().getLoiApplicationId());
            offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            Boolean savestatus = saveMutationAfterloiPayment(offline);
            if (!savestatus) {
                addValidationError(getAppSession().getMessage("mutation.saveMutationAfterLoiPay"));
                return false;
            }
            SMSAndEmailDTO dto = new SMSAndEmailDTO();
            dto.setEmail(getSearchDto().getEmail());
            dto.setMobnumber(getSearchDto().getMobileNo());
            dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            dto.setAppNo(getSearchDto().getLoiMasData().getLoiApplicationId().toString());
            ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE, "LoiPayment.html",
                    PrefixConstants.SMS_EMAIL_ALERT_TYPE.COMPLETED_MESSAGE, dto,
                    UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());
        }
        setChallanDToandSaveChallanData(offline, getSearchDto());
     //User Story #147721
        if(this.isFlag()) {
			return false;
		}

        if (StringUtils.equals(getServiceName(), "WNC")) {
            final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
                    UserSession.getCurrent().getOrganisation());
            LookUp taxCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("A",
                    PrefixConstants.LookUpPrefix.TAC, 1, UserSession.getCurrent().getOrganisation().getOrgid());
            LookUp taxSubCategoryLookUp = CommonMasterUtility.getHieLookupByLookupCode("AP",
                    PrefixConstants.LookUpPrefix.TAC, 2, UserSession.getCurrent().getOrganisation().getOrgid());

            Long taxId = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class).getTaxId(
                    chargeApplicableAt.getLookUpId(), UserSession.getCurrent().getOrganisation().getOrgid(),
                    offline.getDeptId(), taxCategoryLookUp.getLookUpId(), taxSubCategoryLookUp.getLookUpId());

            Map<Long, Double> advancePayment = searchDto.getLoiCharges().entrySet().stream()
                    .filter(x -> x.getKey().equals(taxId))
                    .collect(Collectors.toMap(charge -> charge.getKey(), charge -> charge.getValue()));
            if (MapUtils.isNotEmpty(advancePayment)) {
                try {
                    itbLoidetService.saveServiceWiseAdvanceCharges(getServiceName(), advancePayment,
                            loiMaster.getLoiApplicationId(), getReceiptDTO().getReceiptId(), taxId, offline.getDeptId(),
                            UserSession.getCurrent().getEmployee().getEmpId(),
                            UserSession.getCurrent().getOrganisation().getOrgid());
                } catch (Exception exception) {
                    logger.error("Error occured while calling saveAdvancePayment method");
                }
            }
        }

        setSuccessMessage(appSession.getMessage("loiGen.msg.loiNo") + getLoiMaster().getLoiNo()
                + MainetConstants.WHITE_SPACE + appSession.getMessage("loiPay.msg.paySuccess"));
        return true;
    }

    private void setPropertyNoForLOIPayment(CommonChallanDTO offline) {
        CommonChallanDTO commonChallanDTO = null;
        final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(offline,
                ServiceEndpoints.PROP_BY_APP_ID);
        if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
            commonChallanDTO = (CommonChallanDTO) RestClient.castResponse(responseEntity, CommonChallanDTO.class);
            if (commonChallanDTO != null) {
                offline.setPropNoConnNoEstateNoL(commonChallanDTO.getPropNoConnNoEstateNoL());
                offline.setFlatNo(commonChallanDTO.getFlatNo());
                offline.setTransferAddress(commonChallanDTO.getTransferAddress());
                offline.setTransferOwnerFullName(commonChallanDTO.getTransferOwnerFullName());
                offline.setTransferDate(commonChallanDTO.getTransferDate());
                offline.setTransferInitiatedDate(commonChallanDTO.getTransferInitiatedDate());
                offline.setRegNo(commonChallanDTO.getRegNo());
                offline.setCertificateNo(commonChallanDTO.getCertificateNo());
            }
        }
    }

    private Boolean saveMutationAfterloiPayment(CommonChallanDTO offline) {
        Boolean status = false;
        final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(offline,
                ServiceEndpoints.SAVE_MUTATION_AFTER_LOI_PAYMENT);
        if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
            status = (Boolean) responseEntity.getBody();
        }
        return status;
    }

    private void setChallanDToandSaveChallanData(final CommonChallanDTO offline,
            final LoiPaymentSearchDTO loiPaymentSearchDTO) {

        offline.setTaskId(this.getTaskId());
        offline.setApplNo(loiPaymentSearchDTO.getLoiMasData().getLoiApplicationId());
        offline.setAmountToPay(loiPaymentSearchDTO.getLoiMasData().getLoiAmount().toString());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)
				&& StringUtils.equals(getServiceCode(), "MUT")) {
			offline.setAmountToPay(loiPaymentSearchDTO.getTotal().toString());
		}
        offline.setEmailId(loiPaymentSearchDTO.getEmail());
        offline.setApplicantName(loiPaymentSearchDTO.getApplicantName());
        // Defect #112044
        offline.setApplicantFullName(loiPaymentSearchDTO.getApplicantName());
        offline.setMobileNumber(loiPaymentSearchDTO.getMobileNo());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        offline.setServiceId(loiPaymentSearchDTO.getLoiMasData().getLoiServiceId());
        offline.setDocumentUploaded(false);
        offline.setLoiNo(loiPaymentSearchDTO.getLoiMasData().getLoiNo());
        offline.setApplicantAddress(loiPaymentSearchDTO.getAddress());

        offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        offline.setLangId(UserSession.getCurrent().getLanguageId());
        offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        if(getDepartmentCode().equals("AS") && StringUtils.isNotBlank(loiPaymentSearchDTO.getReferenceNo())) {
        	try {
        		PropertyDetailDto detailDTO = null;
                PropertyInputDto propInputDTO = new PropertyInputDto();
                propInputDTO.setPropertyNo(loiPaymentSearchDTO.getReferenceNo());
                propInputDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
                propInputDTO.setApplicationId(loiPaymentSearchDTO.getLoiMasData().getLoiApplicationId());
                final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(propInputDTO,
                        ServiceEndpoints.PROP_BY_PROP_ID);
                if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {

                    detailDTO = (PropertyDetailDto) RestClient.castResponse(responseEntity, PropertyDetailDto.class);
                    if(detailDTO != null) {
                    	 offline.setPropNoConnNoEstateNoL(getAppSession().getMessage("propertydetails.PropertyNo."));
                         offline.setPropNoConnNoEstateNoV(loiPaymentSearchDTO.getReferenceNo());
                         offline.setReferenceNo(detailDTO.getOldPropNo());
                         offline.setPlotNo(detailDTO.getTppPlotNo());
                         offline.setUniquePropertyId(detailDTO.getUniquePropertyId());
                         offline.setTransferType(detailDTO.getTransferTypeDesc());
                    }
                }
        	}catch (Exception e) {
        		logger.error("Exception while calling property details");
			}
        }
        if (loiPaymentSearchDTO.getLoiCharges() != null) {
    		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)
    				&& StringUtils.equals(getServiceCode(), "MUT")) {
    			loiPaymentSearchDTO.getLoiCharges().remove(loiPaymentSearchDTO.getApplicationFeeTaxId());
    		}
            for (final Entry<Long, Double> entry : loiPaymentSearchDTO.getLoiCharges().entrySet()) {
                offline.getFeeIds().put(entry.getKey(), entry.getValue());
            }
        }
        final TbServicesMst serviceMst = iTbServicesMstService.findById(getSearchDto().getServiceId());
        // #96773 Requirement For Show Property no & Flat no in LOI Payment
        Organisation org = UserSession.getCurrent().getOrganisation();
        if (Utility.isEnvPrefixAvailable(org, MainetConstants.Property.MUT)
                && serviceMst.getSmShortdesc().equalsIgnoreCase(MainetConstants.Property.MUT)) {
            TbCfcApplicationMstEntity applicationMstEntity = iCFCApplicationMasterService
                    .getCFCApplicationByApplicationId(loiPaymentSearchDTO.getLoiMasData().getLoiApplicationId(),
                            UserSession.getCurrent().getOrganisation().getOrgid());
            if (applicationMstEntity != null && applicationMstEntity.getRefNo() != null) {
                offline.setReferenceNo(applicationMstEntity.getRefNo());
            }
            offline.setServiceCode(serviceMst.getSmShortdesc());
            String deptCode = departmentService.getDeptCode(serviceMst.getCdmDeptId());
            offline.setDeptCode(deptCode);
            if (StringUtils.isNotEmpty(org.getOrgAddressMar())) {
                offline.setOrgAddressMar(org.getOrgAddressMar());
            }
            if (serviceMst.getSmServiceDuration() > 0) {
                offline.setSlaSmDuration(serviceMst.getSmServiceDuration());
            }
            setPropertyNoForLOIPayment(offline);
        }

        // end
        // #102456 By Arun - To get department wise details
        CommonChallanDTO commonChallanDTO = itbLoiMasService.getDepartmentWiseLoiData(serviceMst.getSmShortdesc(),
                loiPaymentSearchDTO.getLoiMasData().getLoiApplicationId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        List<String> trdLicNo = itbLoiMasService
                .getLicNumber(loiPaymentSearchDTO.getLoiMasData().getLoiApplicationId());
        // adding code for handling AIOB exception
        if (trdLicNo != null && (!(trdLicNo.isEmpty()) && trdLicNo.get(0) != null)) {
            offline.setLicNo(trdLicNo.get(0).toString());
        }
        if (commonChallanDTO != null) {
            offline.setApplicantFullName(commonChallanDTO.getApplicantFullName());
            offline.setPropNoConnNoEstateNoV(commonChallanDTO.getPropNoConnNoEstateNoV());
            offline.setReferenceNo(commonChallanDTO.getReferenceNo());
            offline.setPlotNo(commonChallanDTO.getPlotNo());
            if (commonChallanDTO.getLicNo() != null) {
                offline.setLicNo(commonChallanDTO.getLicNo());
            }
            if (commonChallanDTO.getFromedate() != null && commonChallanDTO.getToDate() != null) {
                offline.setFromedate(commonChallanDTO.getFromedate());
                offline.setToDate(commonChallanDTO.getToDate());
            }
        }
        // end

        // for setting for stop calling workflow at the time of dishonour charge payment
        // via LOIPayment node D#130924

        if ((getSearchDto().getLoiMasData() != null && getSearchDto().getLoiMasData().getLoiPayMode() != null)) {
            offline.setWorkflowEnable(getSearchDto().getLoiMasData().getLoiPayMode());
        }
        // 126164
        setServiceName(serviceMst.getSmServiceName());
        offline.setServiceName(serviceMst.getSmServiceName());
        offline.setDeptId(serviceMst.getCdmDeptId());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());
        if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
            final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
            offline.setChallanValidDate(master.getChallanValiDate());
            offline.setChallanNo(master.getChallanNo());
        } else if ((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
            final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
                    serviceMst.getSmServiceName());
             //User Story #147721
            if(printDto!=null && (StringUtils.isNotBlank(printDto.getPushToPayErrMsg()) ) ){
				this.addValidationError(printDto.getPushToPayErrMsg());
				this.setFlag(true);
				return;
			}
            printDto.setReceiverName(UserSession.getCurrent().getEmployee().getEmpname() + MainetConstants.WHITE_SPACE
                    + UserSession.getCurrent().getEmployee().getEmplname());
            
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
					MainetConstants.Property.MUT)) {
				SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
				printDto.setDate(formatter.format(new Date()));
			}
			setReceiptDTO(printDto);

            // US#102672 // pushing document to DMS
            String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "="
                    + ApplicationSession.getInstance().getMessage("birtName.mutationReceipt")
                    + "&__format=pdf&RP_ORGID=" + offline.getOrgId() + "&RP_RCPTNO=" + printDto.getReceiptId();
            Utility.pushDocumentToDms(URL, loiPaymentSearchDTO.getLoiMasData().getLoiApplicationId().toString(),
                    MainetConstants.CommonConstants.COM, fileUploadService);

        }

        setOfflineDTO(offline);
    }
    // code updated for D#121724

    /*public List<LoiPrintDTO> getLoiPrintingData(final long gmId, final Long orgid, final Long deptId,
            final Long serviceId, final Long appId, final String loiNo) {

        logger.info("start the getLoiPrintingData()");
        List<LoiPrintDTO> list = null;
        final List<TbLoiMas> loimas = itbLoiMasService.getLoiPrintIngData(orgid, deptId, serviceId, appId, loiNo);
        if (loimas != null) {
            list = new ArrayList<>();
            LoiPrintDTO printDTO = null;

            for (final TbLoiMas master : loimas) {
                printDTO = new LoiPrintDTO();
                final TbServicesMst serviceMst = iTbServicesMstService.findById(master.getLoiServiceId());
                if ((serviceMst != null) && master.getLoiPaid().equalsIgnoreCase(MainetConstants.Common_Constant.NO)) {
                    printDTO.setLoiServiceId(serviceMst.getSmServiceId());
                    printDTO.setServiceName(serviceMst.getSmServiceName());
                    printDTO.setLoiAmount(master.getLoiAmount().doubleValue());
                    printDTO.setLoiApplicationId(master.getLoiApplicationId());
                    printDTO.setLoiDate(master.getLoiDate());
                    printDTO.setLoiId(master.getLoiId());
                    printDTO.setLoiNo(master.getLoiNo());
                    printDTO.setLoiRefId(master.getLoiRefId());
                    printDTO.setLoiServiceId(master.getLoiServiceId());
                    printDTO.setLoiPaid(MainetConstants.PAYMENT_STATUS.PENDING);
                    list.add(printDTO);
                }
            }
        }

        return list;
    }*/
    
	public void fetchPropOutstandingAmount(final Long applicationId, final Long serviceId)
			throws ClassNotFoundException, LinkageError {
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
			if(serviceId!=null) {
			ServiceMaster service = serviceMaster.getServiceMaster(serviceId,UserSession.getCurrent().getOrganisation().getOrgid());
			if (service != null) {
				setServiceId(service.getSmServiceId());

				if (MainetConstants.WaterServiceShortCode.WATER_NEW_CONNECION.equals(service.getSmShortdesc())) {
					LookUp propDuesCheck = null;
					try {
						propDuesCheck = CommonMasterUtility.getDefaultValue(PrefixConstants.NewWaterServiceConstants.CHECK_PROPERTY_DUES,
								UserSession.getCurrent().getOrganisation());
						if (propDuesCheck != null) {
							if (PrefixConstants.NewWaterServiceConstants.AT_TIME_OF_SCRUTINY.equals(propDuesCheck.getLookUpCode())
									|| PrefixConstants.NewWaterServiceConstants.BOTH.equals(propDuesCheck.getLookUpCode())) {
								setShowPropDues(true);
								Class<?> clazz = null;
								Object dynamicServiceInstance = null;
								String serviceClassName = null;
								serviceClassName = "com.abm.mainet.water.service.NewWaterConnectionServiceImpl";
								clazz = ClassUtils.forName(serviceClassName,
										ApplicationContextProvider.getApplicationContext().getClassLoader());
								dynamicServiceInstance = ApplicationContextProvider.getApplicationContext()
										.getAutowireCapableBeanFactory().autowire(clazz, 2, false);
								final Method method = ReflectionUtils.findMethod(clazz,MainetConstants.Property.GET_PROPERY_OUTSTANDING,
										new Class[] { Long.class, Long.class });
								PropertyDetailDto dto = (PropertyDetailDto) ReflectionUtils.invokeMethod(method,
										dynamicServiceInstance, new Object[] { applicationId,
												UserSession.getCurrent().getOrganisation().getOrgid() });

									if (dto != null && dto.getTotalOutsatandingAmt() > 0d) {
										Map<String, Double> propDue = new HashMap<String, Double>();
										propDue.put("Property Due Amount", dto.getTotalOutsatandingAmt());
										setPrFlatNo(dto.getFlatNo());
										setPrPropertyNo(dto.getPropNo());
										setPropDues(propDue);
									}else {
										setShowPropDues(false);
									}
							}
						}
					} catch (Exception exception) {
						throw new FrameworkException("No prefix found for CPD(Check Property Dues)");
					}
				}
			} // END
		}
		}
	}

    public TbLoiMas getLoiMaster() {
        return loiMaster;
    }

    public void setLoiMaster(final TbLoiMas loiMaster) {
        this.loiMaster = loiMaster;
    }

    public List<TbLoiDet> getLoiDetail() {
        return loiDetail;
    }

    public void setLoiDetail(final List<TbLoiDet> loiDetail) {
        this.loiDetail = loiDetail;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getLoiNo() {
        return loiNo;
    }

    public void setLoiNo(final String loiNo) {
        this.loiNo = loiNo;
    }

    public Date getLoiDate() {
        return loiDate;
    }

    public void setLoiDate(final Date loiDate) {
        this.loiDate = loiDate;
    }

    public LoiPaymentSearchDTO getSearchDto() {
        return searchDto;
    }

    public void setSearchDto(final LoiPaymentSearchDTO searchDto) {
        this.searchDto = searchDto;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(final String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public ChallanReceiptPrintDTO getReceiptDto() {
        return receiptDto;
    }

    public void setReceiptDto(final ChallanReceiptPrintDTO receiptDto) {
        this.receiptDto = receiptDto;
    }

    public List<Object[]> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<Object[]> deptList) {
        this.deptList = deptList;
    }

    public List<Object[]> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Object[]> serviceList) {
        this.serviceList = serviceList;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<Object[]> getLoiDetailList() {
        return loiDetailList;
    }

    public void setLoiDetailList(List<Object[]> loiDetailList) {
        this.loiDetailList = loiDetailList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<String> remarkList) {
        this.remarkList = remarkList;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

	public boolean isShowPropDues() {
		return showPropDues;
	}

	public void setShowPropDues(boolean showPropDues) {
		this.showPropDues = showPropDues;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<TbCfcApplicationMstEntity> getApplNameList() {
		return applNameList;
	}

	public void setApplNameList(List<TbCfcApplicationMstEntity> applNameList) {
		this.applNameList = applNameList;
	}


}
