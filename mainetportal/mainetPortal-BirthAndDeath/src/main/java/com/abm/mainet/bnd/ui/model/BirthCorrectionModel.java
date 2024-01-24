package com.abm.mainet.bnd.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.payment.dto.PaymentRequestDTO;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class BirthCorrectionModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IBirthRegService iBirthRegSevice;

	BirthRegistrationDTO birthRegDto = new BirthRegistrationDTO();
	
	BndAcknowledgementDto ackDto = new BndAcknowledgementDto();

	private List<BirthRegistrationDTO> birthRegistrationDTOList;

	private String saveMode;
	
	private String viewMode;
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private List<DocumentDetailsVO> viewCheckList = new ArrayList<>();
	
	@Autowired
    private IPortalServiceMasterService iPortalServiceMasterService;
	
	
	@Override
	public boolean saveForm() {
		
			if (hasValidationErrors()) {
				return false;
			} else {
				Employee employee = getUserSession().getEmployee();
				int langId = UserSession.getCurrent().getLanguageId();

				birthRegDto.setUpdatedBy(employee.getEmpId());
				birthRegDto.setUpdatedDate(new Date());
				birthRegDto.setLmodDate(new Date());
				birthRegDto.setLgIpMac(employee.getEmppiservername());
				birthRegDto.setLgIpMacUpd(employee.getEmppiservername());
				birthRegDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				birthRegDto.setLangId(langId);
				birthRegDto.setUserId(Long.valueOf(UserSession.getCurrent().getEmployee().getEmpmobno()));
				birthRegDto.setMobileNo(employee.getEmploginname());
				birthRegDto.setBrDob(Utility.stringToDate(birthRegDto.getBrDateOfBirth()));
				CommonChallanDTO offline = getOfflineDTO();
				List<DocumentDetailsVO> documents = getCheckList();
				 documents = ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
						.prepareFileUpload(checkList);
				 validateInputs(documents);
					if (hasValidationErrors()) {
			            return false;
			        }
				birthRegDto.setUploadDocument(checkList);
				//prepare request DTO
				birthRegDto.getRequestDTO().setfName(UserSession.getCurrent().getEmployee().getEmpname());
				birthRegDto.getRequestDTO().setmName(UserSession.getCurrent().getEmployee().getEmpMName());
				birthRegDto.getRequestDTO().setlName(UserSession.getCurrent().getEmployee().getEmpLName());
				birthRegDto.getRequestDTO().setMobileNo(UserSession.getCurrent().getEmployee().getEmpmobno());
				birthRegDto.getRequestDTO().setEmail(UserSession.getCurrent().getEmployee().getEmpemail());
				birthRegDto.getRequestDTO().setCityName(UserSession.getCurrent().getEmployee().getEmpAddress());
				birthRegDto.getRequestDTO().setUserId(employee.getEmpId());
				this.setBirthRegDto(birthRegDto);
				BirthRegistrationDTO birthRegDto1=iBirthRegSevice.saveBirthCorrectionDet(birthRegDto);
				this.setBirthRegDto(birthRegDto1);
				this.setSuccessMessage(getAppSession().getMessage("BirthRegDto.SuccessMsgBrc") + birthRegDto1.getApmApplicationId());
				
				birthRegDto.setApplnId(birthRegDto.getApmApplicationId());
				final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		        offline.setOfflinePaymentText(modeDesc);
		        Map<Long, Double> details = new HashMap<>(0);
		        final Map<Long, Long> billDetails = new HashMap<>(0);
		        setChallanDToandSaveChallanData(offline, details, billDetails, birthRegDto);
			}
		 
		   return true;
		}
	
	public boolean validateInputs(final List<DocumentDetailsVO> dto) {
		boolean flag = false;
		if ((dto != null) && !dto.isEmpty()) {
			for (final DocumentDetailsVO doc : dto) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.FlagY)) {
					if (doc.getDocumentByteCode() == null) {
						addValidationError(getAppSession().getMessage("bnd.upload.mandatory.documents"));
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final Map<Long, Double> details,
            final Map<Long, Long> billDetails, BirthRegistrationDTO billPayDto) {
        final UserSession session = UserSession.getCurrent();
        if(billPayDto.getAmount() != null)
        offline.setAmountToPay(Double.toString(billPayDto.getAmount()));
        offline.setUserId(session.getEmployee().getEmpId());
        offline.setOrgId(billPayDto.getOrgId());
        offline.setLangId(session.getLanguageId());
        offline.setLgIpMac(session.getEmployee().getEmppiservername());
        if ((details != null) && !details.isEmpty()) {
            offline.setFeeIds(details);
        }
        if ((billDetails != null) && !billDetails.isEmpty()) {
            offline.setBillDetIds(billDetails);
        }
        offline.setAmountToShow(billPayDto.getAmount());

        offline.setApplicantName(billPayDto.getBrInformantName());
        offline.setApplNo(billPayDto.getApplnId());
        offline.setApplicantAddress(billPayDto.getBrInformantAddr());
        offline.setUniquePrimaryId(String.valueOf(billPayDto.getApplnId()));
       // offline.setMobileNumber(billPayDto.getPrimaryOwnerMobNo());
        offline.setServiceId(billPayDto.getServiceId());
        offline.setDeptId(billPayDto.getDeptId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        if (CollectionUtils.isNotEmpty(billPayDto.getUploadDocument())) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());
      
        setOfflineDTO(offline);
    }
	
	 @Override
	    public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PaymentRequestDTO payURequestDTO) {
		
	        final PortalService portalServiceMaster = iPortalServiceMasterService.getService(birthRegDto.getServiceId(),
	        		birthRegDto.getOrgId());
	        payURequestDTO.setUdf3("CitizenHome.html");
	        payURequestDTO.setUdf5(portalServiceMaster.getShortName());
			payURequestDTO.setUdf7(String.valueOf(birthRegDto.getApmApplicationId()));
			String fullName = String.join(" ", Arrays.asList(UserSession.getCurrent().getEmployee().getEmpname(),UserSession.getCurrent().getEmployee().getEmpMName(),UserSession.getCurrent().getEmployee().getEmpLName()));
			payURequestDTO.setApplicantName(fullName);
			payURequestDTO.setServiceId(birthRegDto.getServiceId());
			payURequestDTO.setUdf2(String.valueOf(birthRegDto.getApmApplicationId()));
			
			if (birthRegDto.getAmount() != null) {
				payURequestDTO.setDueAmt(new BigDecimal(birthRegDto.getAmount()));
			}
			 
			payURequestDTO.setApplicationId(String.valueOf(birthRegDto.getApmApplicationId()));
			if (portalServiceMaster != null) {
				payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
					payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
				} else {
					payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
				}
			}

	    }

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<BirthRegistrationDTO> getBirthRegistrationDTOList() {
		return birthRegistrationDTOList;
	}

	public void setBirthRegistrationDTOList(List<BirthRegistrationDTO> birthRegistrationDTOList) {
		this.birthRegistrationDTOList = birthRegistrationDTOList;
	}

	public BirthRegistrationDTO getBirthRegDto() {
		return birthRegDto;
	}

	public void setBirthRegDto(BirthRegistrationDTO birthRegDto) {
		this.birthRegDto = birthRegDto;
	}


	public BndAcknowledgementDto getAckDto() {
		return ackDto;
	}


	public void setAckDto(BndAcknowledgementDto ackDto) {
		this.ackDto = ackDto;
	}


	public String getViewMode() {
		return viewMode;
	}


	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}


	public List<DocumentDetailsVO> getViewCheckList() {
		return viewCheckList;
	}


	public void setViewCheckList(List<DocumentDetailsVO> viewCheckList) {
		this.viewCheckList = viewCheckList;
	}
	

}
