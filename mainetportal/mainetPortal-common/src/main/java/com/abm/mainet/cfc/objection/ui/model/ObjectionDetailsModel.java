package com.abm.mainet.cfc.objection.ui.model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.IObjectionDetailService;
import com.abm.mainet.cfc.objection.ui.validator.ObjectionDetailsValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;

@Component
@Scope("session")
public class ObjectionDetailsModel extends AbstractFormModel {

    private static final Logger LOGGER = Logger.getLogger(ObjectionDetailsModel.class);
   
    @Autowired
    IObjectionDetailService iObjectionDetailsService;
    
    @Autowired
    private IChallanService challanService;
    
    @Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

    private static final long serialVersionUID = 1L;

    private Long orgId;

    private ServiceMaster serviceMaster = new ServiceMaster();

    private ObjectionDetailsDto objectionDetailsDto = new ObjectionDetailsDto();

    private List<ObjectionDetailsDto> listObjectionDetails = new ArrayList<>();

    private String isValidationError;
    
    private Set<LookUp> departments = new HashSet<>();

    private Set<LookUp> serviceList = new HashSet<>();

	private String deptCode;

    private String onlyInspecMode;

    private Set<LookUp> locations = new HashSet<>();
    
    private CommonChallanDTO offlineDTO = new CommonChallanDTO();

    private Double charges = 0.0d;

    private Map<Long, Double> chargesMap = new HashMap<>();

    private String paymentCharge;
    
    private PortalService portalserviceMaster = new PortalService();

    @Override
    public boolean saveForm() {
        final UserSession session = UserSession.getCurrent();
        ObjectionDetailsDto objectionDto = this.getObjectionDetailsDto();
        CommonChallanDTO offline = getOfflineDTO();
        objectionDto.setLangId((long) session.getLanguageId());
        objectionDto.setUserId(session.getEmployee().getEmpId());
        objectionDto.setObjectionStatus(MainetConstants.ApplicationMasterConstant.APPLICATION_STATUS_PENDING);
        objectionDto.setIpAddress(session.getEmployee().getEmppiservername());
        Employee emp = UserSession.getCurrent().getEmployee();
        objectionDto.setEname(emp.getEmpname());
        objectionDto.setEmpType(emp.getEmplType());
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "DSCL")) {
        	objectionDto.setOrgId(getOrgId());
        }
        if (paymentCharge != null && paymentCharge.equals(MainetConstants.FLAGY)) {
            objectionDto.setIsPaymentGenerated(true);
        } else {
            objectionDto.setIsPaymentGenerated(false);
        }
        if (offline.getOnlineOfflineCheck() != null
                && MainetConstants.FLAGN.equalsIgnoreCase(offline.getOnlineOfflineCheck())) {
            objectionDto.setPaymentMode(MainetConstants.FLAGN);
        }
       
        List<DocumentDetailsVO> docs = prepareFileUpload();
        objectionDto.setDocs(docs);
       
        validateBean(this, ObjectionDetailsValidator.class);
        ObjectionDetailsDto objDto=null;
        if (hasValidationErrors()) {
        }
        if(CollectionUtils.isEmpty(objectionDetailsDto.getErrorList())) {
        	objDto=iObjectionDetailsService.saveObjectionAndCallWorkFlow(objectionDto);
        this.setObjectionDetailsDto(objDto);
        
        if(CollectionUtils.isEmpty(objectionDetailsDto.getErrorList())) {
        	  setSuccessMessage(
                      getAppSession().getMessage("obj.success") + " "
                              + "Objection Application Number : " + objDto.getApmApplicationId()
                              + ",Objection Number : " + objDto.getObjectionNumber());
        }
        else {
        	for(String error:objectionDto.getErrorList()){
                addValidationError(error);
                return false;
        	}
        }}else {
        	for(String error:objectionDto.getErrorList()){
        		if(error!=null) {
                addValidationError(error);
        		}
                return false;
        	}
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
            	if ((offline.getOnlineOfflineCheck() != null)
                        && offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {
    	        	 offline = challanService.generateChallanNumber(offline);
    	           /* offline.setChallanValidDate(master
    	                    .getChallanValiDate());
    	            offline.setChallanNo(master.getChallanNo());*/
    	        } 
            	setOfflineDTO(offline);

                setSuccessMessage(
                        getAppSession().getMessage("obj.success") + " "
                                + "Objection Application Number : " + objDto.getApmApplicationId()
                                + ",Objection Number : " + objDto.getObjectionNumber());

            }
        }
    return true;
    }

    
    private void setOfflineDetailsDTO(CommonChallanDTO offline, ObjectionDetailsDto objectionDto) {

      /*  offline.setFaYearId(UserSession.getCurrent().getFinYearId());
        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());*/
        offline.setChallanServiceType("O");
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
    
	 @Override
	 public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PaymentRequestDTO payURequestDTO) {
		 ObjectionDetailsDto objectionDto = this.getObjectionDetailsDto();
		 
		 final Long serviceId = iPortalServiceMasterService.getServiceId("RFA", orgId);
			final PortalService service = iPortalServiceMasterService.getService(serviceId, orgId);
			setPortalserviceMaster(service);
			setServiceId(service.getServiceId());
		 
	  final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
	                UserSession.getCurrent().getOrganisation().getOrgid());
	        payURequestDTO.setUdf3("CitizenHome.html");
	        payURequestDTO.setUdf5(portalServiceMaster.getShortName());
	        payURequestDTO.setUdf7(String.valueOf(objectionDto.getApmApplicationId()));
	        //String fullName = String.join(" ", Arrays.asList(reqDTO.getfName(), reqDTO.getmName(), reqDTO.getlName()));
	        payURequestDTO.setApplicantName(objectionDto.getfName());
	        payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
	        payURequestDTO.setUdf2(String.valueOf(objectionDto.getApmApplicationId()));
	        payURequestDTO.setMobNo(objectionDto.getMobileNo());
	        payURequestDTO.setDueAmt(BigDecimal.valueOf(objectionDto.getCharges()));
	        payURequestDTO.setEmail(objectionDto.geteMail());
	        payURequestDTO.setApplicationId(objectionDto.getApmApplicationId().toString());
	      //  payURequestDTO.setFeeIds(objectionDto.getFeeIds().toString());
	        if (portalServiceMaster != null) {
	            payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
	            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
	                payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
	            } else {
	                payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
	            }
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

    public String getOnlyInspecMode() {
        return onlyInspecMode;
    }

    public void setOnlyInspecMode(String onlyInspecMode) {
        this.onlyInspecMode = onlyInspecMode;
    }

	public String getPaymentCharge() {
		return paymentCharge;
	}

	public void setPaymentCharge(String paymentCharge) {
		this.paymentCharge = paymentCharge;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

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


	public PortalService getPortalserviceMaster() {
		return portalserviceMaster;
	}


	public void setPortalserviceMaster(PortalService portalserviceMaster) {
		this.portalserviceMaster = portalserviceMaster;
	}


}
