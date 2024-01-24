package com.abm.mainet.workManagement.roadcutting.ui.model;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.workManagement.roadcutting.service.IRoadCuttingService;
import com.abm.mainet.workManagement.roadcutting.ui.dto.RoadCuttingDto;
import com.abm.mainet.workManagement.roadcutting.ui.validator.RoadCuttingValidator;

/**
 * @author satish.rathore
 *
 */
@Component
@Scope("session")
public class RoadCuttingModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3201998820759671093L;

	/**
	 * 
	 */

	
	@Autowired
	private IRoadCuttingService brmsRoadCuttingService;
	
	@Autowired
	private IChallanService iChallanService;
	
	@Autowired
	private IPortalServiceMasterService serviceMasterService;
	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;
	
	/* @Autowired
	 private IChecklistVerificationService checklistVerificationService;*/
	
	private RoadCuttingDto  roadCuttingDto = new RoadCuttingDto();
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	  private List<CFCAttachment> documentList = new ArrayList<>();
	private String isFree;
    private List<ChargeDetailDTO> chargesInfo;
    private Double charges = 0.0d;
    private CommonChallanDTO offlineDTO = new CommonChallanDTO();
    private Map<Long, Double> chargesMap = new HashMap<>();
    private String free = "O";
    private BigDecimal amountToPaid;
    private Long orgId;
    private List<LookUp> listTEC=new ArrayList<>();
    private List<LookUp> listROT=new ArrayList<>();
    private PortalService serviceMaster = new PortalService();
    
    
   
  

	

	/**
	 * @return the listTEC
	 */
	public List<LookUp> getListTEC() {
		return listTEC;
	}

	/**
	 * @param listTEC the listTEC to set
	 */
	public void setListTEC(List<LookUp> listTEC) {
		this.listTEC = listTEC;
	}

	/**
	 * @return the listROT
	 */
	public List<LookUp> getListROT() {
		return listROT;
	}

	/**
	 * @param listROT the listROT to set
	 */
	public void setListROT(List<LookUp> listROT) {
		this.listROT = listROT;
	}

	
	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the free
	 */
	public String getFree() {
		return free;
	}

	/**
	 * @param free the free to set
	 */
	public void setFree(String free) {
		this.free = free;
	}

	/**
	 * @return the chargesMap
	 */
	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	/**
	 * @param chargesMap the chargesMap to set
	 */
	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	/**
	 * @return the isFree
	 */
	public String getIsFree() {
		return isFree;
	}

	/**
	 * @param isFree the isFree to set
	 */
	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	/**
	 * @return the chargesInfo
	 */
	public List<ChargeDetailDTO> getChargesInfo() {
		return chargesInfo;
	}

	/**
	 * @param chargesInfo the chargesInfo to set
	 */
	public void setChargesInfo(List<ChargeDetailDTO> chargesInfo) {
		this.chargesInfo = chargesInfo;
	}

	/**
	 * @return the charges
	 */
	public Double getCharges() {
		return charges;
	}

	/**
	 * @param charges the charges to set
	 */
	public void setCharges(Double charges) {
		this.charges = charges;
	}

	/**
	 * @return the offlineDTO
	 */
	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	/**
	 * @param offlineDTO the offlineDTO to set
	 */
	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	/**
	 * @return the roadCuttingDto
	 */
	public RoadCuttingDto getRoadCuttingDto() {
		return roadCuttingDto;
	}

	/**
	 * @param roadCuttingDto the roadCuttingDto to set
	 */
	public void setRoadCuttingDto(RoadCuttingDto roadCuttingDto) {
		this.roadCuttingDto = roadCuttingDto;
	}

	/**
	 * @return the checkList
	 */
	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	/**
	 * @param checkList the checkList to set
	 */
	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}
	
	
	
	/**
	 * @return the amountToPaid
	 */
	public BigDecimal getAmountToPaid() {
		return amountToPaid;
	}

	/**
	 * @param amountToPaid the amountToPaid to set
	 */
	public void setAmountToPaid(BigDecimal amountToPaid) {
		this.amountToPaid = amountToPaid;
	}
	
	

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	/* for validating Inputs and other functional validation */
	public boolean validateInputs() {
		validateBean(roadCuttingDto, RoadCuttingValidator.class);
		FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());
		 validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		if (hasValidationErrors()) {
		//	this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
		return true;
	}

	
 public boolean saveRoadCuttingForm() {
	 setCommonFields(this);
	 
	
		final RoadCuttingDto reqDTO = this.getRoadCuttingDto();
		reqDTO.setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent()
                 .getFileMap()));
		 prepareDocumentsData();
	 RoadCuttingDto requestDTO =brmsRoadCuttingService.saveRoadCutting(roadCuttingDto);
	 this.setRoadCuttingDto(requestDTO);
	     CommonChallanDTO offline = getOfflineDTO();
	    // RoadCuttingDto requestDTO = this.getRoadCuttingDto();
	     requestDTO.setChargesMap(this.getChargesMap());
		setOfflineDetailsDTO(offline, getRoadCuttingDto());
		offline.setApplicantAddress(requestDTO.getAreaName());
		offline.setDocumentUploaded(!FileUploadUtility.getCurrent().getFileMap().isEmpty());
		if (!requestDTO.isFree()) { // offline or pay@ULB
			offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
					offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
			offline.setAmountToPay(amountToPaid.toString());
		 
			if (offline.getOnlineOfflineCheck() != null
					&& MainetConstants.PAY_STATUS_FLAG.NO.equalsIgnoreCase(offline.getOnlineOfflineCheck())) {
				    offline = iChallanService.generateChallanNumber(offline);
	                setOfflineDTO(offline);
			}
			setSuccessMessage(getAppSession().getMessage("roadcuttingsavesuccess") + " Application Number : "
					+ requestDTO.getApplicationId());
	    }
	 
	 
		return true;
	}
 
 private void setOfflineDetailsDTO(CommonChallanDTO offline, RoadCuttingDto requestDTO) {
		/* Setting Offline DTO */
		/*offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());*/
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setOrgId(requestDTO.getOrgId());
		offline.setDeptId(requestDTO.getWmsDeptId());
		offline.setApplNo(requestDTO.getApplicationId());

		String fullName = String.join(" ",
				Arrays.asList(requestDTO.getfName(), requestDTO.getmName(), requestDTO.getlName()));
		offline.setApplicantName(fullName);
		String applicantAddress = String.join(" ", Arrays.asList(requestDTO.getFlatBuildingNo(),
				requestDTO.getBlockName(), requestDTO.getRoadName(), requestDTO.getCityName()));
		offline.setApplicantAddress(applicantAddress);
		offline.setMobileNumber(requestDTO.getMobileNo());
		offline.setEmailId(requestDTO.getEmail());
		offline.setServiceId(requestDTO.getServiceId());
		offline.setUserId(requestDTO.getUserId());
		offline.setLgIpMac(Utility.getMacAddress());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		for (final Map.Entry<Long, Double> entry : requestDTO.getChargesMap().entrySet()) {
			offline.getFeeIds().put(entry.getKey(), entry.getValue());
		}
		setOfflineDTO(offline);

	}
 
 
	private void setCommonFields(RoadCuttingModel model) {
		final Date sysDate = new Date();
		final RoadCuttingDto dto = model.getRoadCuttingDto();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgId(orgId);
		model.setOrgId(orgId);
		final Long serviceId = serviceMasterService.getServiceId(MainetConstants.RoadCuttingConstant.RCP, orgId);
		final PortalService service = serviceMasterService.getService(serviceId, orgId);
		model.setServiceMaster(service);
		model.setServiceId(service.getServiceId());
		model.getRoadCuttingDto().setServiceId(service.getServiceId());
		dto.setMobileNo(dto.getPersonMobileNo2().toString());
		dto.setAreaName(dto.getPersonAddress2());
		dto.setEmail(dto.getPersonEmailId2());
		dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		dto.setfName(dto.getPersonName1());
		dto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		dto.setmName("");
		dto.setlName("");
		dto.setLgIpMac(dto.getLgIpMac());
		dto.setUserId(dto.getCreatedBy());
		dto.setlModDate(sysDate);
		dto.setApmApplicationDate(sysDate);
		dto.setCreatedDate(sysDate);
		long deptId = service.getPsmDpDeptid();
		dto.setDeptId(deptId);
		dto.setWmsDeptId(deptId);
}

	@Override
    public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PaymentRequestDTO payURequestDTO) {
        final RoadCuttingDto reqDTO = this.getRoadCuttingDto();
        final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
                UserSession.getCurrent().getOrganisation().getOrgid());
        payURequestDTO.setUdf3("CitizenHome.html");
        payURequestDTO.setUdf5(portalServiceMaster.getShortName());
        payURequestDTO.setUdf7(String.valueOf(reqDTO.getApplicationId()));
        payURequestDTO.setApplicantName(reqDTO.getPersonName1());
        payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
        payURequestDTO.setUdf2(String.valueOf(reqDTO.getApplicationId()));
        payURequestDTO.setMobNo(reqDTO.getPersonMobileNo1().toString());
        payURequestDTO.setDueAmt(amountToPaid);
        payURequestDTO.setEmail(reqDTO.getPersonEmailId1());
        payURequestDTO.setApplicationId(reqDTO.getApplicationId().toString());
        if (portalServiceMaster != null) {
            payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
            } else {
                payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
            }
        }
    }

	   //Add Project Location Image Save Code
	private void prepareDocumentsData() {
        List<DocumentDetailsVO> list = new ArrayList<>();
       
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            if (entry.getKey() >= 100L) {
                Base64 base64 = null;
                List<File> fileList = null;
                fileList = new ArrayList<>(entry.getValue());
                for (final File file : fileList) {
                    try {
                        base64 = new Base64();
                        final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));

                        DocumentDetailsVO d = new DocumentDetailsVO();
                        d.setDocumentName(file.getName());
                        d.setDocumentByteCode(bytestring);
                        d.setDocumentSerialNo(entry.getKey());
                        list.add(d);

                    } catch (final IOException e) {
                        throw new FrameworkException("Exception has been occurred in file byte to string conversions", e);
                    }
                }
            }
        }

        roadCuttingDto.setProjectLocation(list);

	}
}
