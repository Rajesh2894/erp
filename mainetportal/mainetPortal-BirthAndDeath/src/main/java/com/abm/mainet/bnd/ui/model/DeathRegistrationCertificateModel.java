package com.abm.mainet.bnd.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
/**
 * 
 * @author bhagyashri.dongardive
 *
 */
@Component
@Scope(value =WebApplicationContext.SCOPE_SESSION)
public class DeathRegistrationCertificateModel extends AbstractFormModel{


	private static final long serialVersionUID = 1L;
	
	TbDeathregDTO tbDeathregDTO = new  TbDeathregDTO();
	
	BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
	
	private String saveMode;
	
	private String viewMode;
	
	private RequestDTO requestDTO = new RequestDTO();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private List<DocumentDetailsVO> documentList = new ArrayList<>();
	
	private String formName;
	
	@Autowired
	private IdeathregCorrectionService ideathregCorrectionService;
	
	@Autowired
    private IPortalServiceMasterService iPortalServiceMasterService;

	
	@Override
	public boolean saveForm() {
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		
		tbDeathregDTO.setUpdatedBy(employee.getEmpId());
		tbDeathregDTO.setUpdatedDate(new Date());
		tbDeathregDTO.setLmoddate(new Date());
		tbDeathregDTO.setLgIpMac(employee.getEmppiservername());
		tbDeathregDTO.setLgIpMacUpd(employee.getEmppiservername());
		tbDeathregDTO.setOrgId(Utility.getOrgId());
		tbDeathregDTO.setLangId(langId);
		tbDeathregDTO.setDrRegdate(tbDeathregDTO.getDrRegdate());
		tbDeathregDTO.setRegAplDate(new Date());
		tbDeathregDTO.setMobileNo(employee.getEmploginname());
		tbDeathregDTO.setUserId(Long.valueOf(UserSession.getCurrent().getEmployee().getEmpmobno()));
		if(tbDeathregDTO.getAmount()==null) {
		tbDeathregDTO.setAmount(0.0d);
		}
		// prepare request DTO
		tbDeathregDTO.getRequestDTO().setfName(UserSession.getCurrent().getEmployee().getEmpname());
		tbDeathregDTO.getRequestDTO().setmName(UserSession.getCurrent().getEmployee().getEmpMName());
		tbDeathregDTO.getRequestDTO().setlName(UserSession.getCurrent().getEmployee().getEmpLName());
		tbDeathregDTO.getRequestDTO().setMobileNo(UserSession.getCurrent().getEmployee().getEmpmobno());
		tbDeathregDTO.getRequestDTO().setEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		tbDeathregDTO.getRequestDTO().setCityName(UserSession.getCurrent().getEmployee().getEmpAddress());
		tbDeathregDTO.getRequestDTO().setUserId(employee.getEmpId());
		CommonChallanDTO offline = getOfflineDTO();
		TbDeathregDTO deathregDTO = ideathregCorrectionService.saveIssuanceDeathCertificateDetail(tbDeathregDTO);
		this.setTbDeathregDTO(deathregDTO);
		tbDeathregDTO.setApplnId(deathregDTO.getApmApplicationId());
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        Map<Long, Double> details = new HashMap<>(0);
        final Map<Long, Long> billDetails = new HashMap<>(0);
        setChallanDToandSaveChallanData(offline, details, billDetails, tbDeathregDTO);
		this.setSuccessMessage(getAppSession().getMessage("TbDeathregDTO.SuccessMsgDrCert") + deathregDTO.getApplicationNo());
		return true;
	}
	
	private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final Map<Long, Double> details,
            final Map<Long, Long> billDetails, TbDeathregDTO billPayDto) {
        final UserSession session = UserSession.getCurrent();
        
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
        //#157676-charges set in offile dto 
        if (this.getChargesInfo() != null) {
			for (ChargeDetailDTO dto : this.getChargesInfo()) {
				offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
			}
		}
        offline.setAmountToShow(billPayDto.getAmount());

        offline.setApplicantName(billPayDto.getDrInformantName());
        offline.setApplNo(billPayDto.getApplnId());
        offline.setApplicantAddress(billPayDto.getDrInformantAddr());
        offline.setUniquePrimaryId(String.valueOf(billPayDto.getApplnId()));
      
        offline.setServiceId(billPayDto.getServiceId());
        offline.setDeptId(billPayDto.getDeptId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        offline.setDocumentUploaded(false);
        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        offline.setOfflinePaymentText(CommonMasterUtility
                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode());
      
        setOfflineDTO(offline);
    }

    @Override
    public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PaymentRequestDTO payURequestDTO) {

        final PortalService portalServiceMaster = iPortalServiceMasterService.getService(tbDeathregDTO.getServiceId(),
        		tbDeathregDTO.getOrgId());
        payURequestDTO.setUdf3("CitizenHome.html");
        payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(String.valueOf(tbDeathregDTO.getApplnId()));
		String fullName = String.join(" ", Arrays.asList(UserSession.getCurrent().getEmployee().getEmpname(),UserSession.getCurrent().getEmployee().getEmpMName(),UserSession.getCurrent().getEmployee().getEmpLName()));
		payURequestDTO.setApplicantName(fullName);
		payURequestDTO.setServiceId(tbDeathregDTO.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(tbDeathregDTO.getApplnId()));
		
		if (tbDeathregDTO.getAmount() != null) {
			payURequestDTO.setDueAmt(new BigDecimal(tbDeathregDTO.getAmount()));
		}
		 
		payURequestDTO.setApplicationId(String.valueOf(tbDeathregDTO.getApplnId()));
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}

    }
	
	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public TbDeathregDTO getTbDeathregDTO() {
		return tbDeathregDTO;
	}

	public void setTbDeathregDTO(TbDeathregDTO tbDeathregDTO) {
		this.tbDeathregDTO = tbDeathregDTO;
	}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
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

}
