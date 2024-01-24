package com.abm.mainet.bnd.ui.model;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
public class DeathRegCorrectionModel extends AbstractFormModel{

	private static final long serialVersionUID = -6058717265176985058L;
	
	private TbDeathregDTO tbDeathregDTO= new TbDeathregDTO();
	
	BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
	
	private List<TbDeathregDTO> tbDeathregDTOList;
	
	private String saveMode;
	
	private String viewMode;
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private List<DocumentDetailsVO> viewCheckList = new ArrayList<>();
	
	@Autowired
	private IdeathregCorrectionService ideathregCorrectionService;
	
	@Autowired
    private IPortalServiceMasterService iPortalServiceMasterService;
	
	
	
	@Override
	public boolean saveForm() {
		Employee employee = getUserSession().getEmployee();
		tbDeathregDTO.setLangId(UserSession.getCurrent().getLanguageId());
		tbDeathregDTO.setOrgId(Utility.getOrgId());
		tbDeathregDTO.setUserId(Long.valueOf(UserSession.getCurrent().getEmployee().getEmpmobno()));
		tbDeathregDTO.setMobileNo(employee.getEmploginname());
		List<DocumentDetailsVO> documents = getCheckList();
		 documents = ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
		.prepareFileUpload(checkList);
		 validateInputs(documents);
			if (hasValidationErrors()) {
	            return false;
	        }

		tbDeathregDTO.setDocumentList(checkList);
		tbDeathregDTO.setUploadDocument(checkList);
		tbDeathregDTO.setUpdatedBy(tbDeathregDTO.getUpdatedBy());
		tbDeathregDTO.setLgIpMacUpd(tbDeathregDTO.getLgIpMacUpd());
		//prepare request DTO
		tbDeathregDTO.getRequestDTO().setfName(UserSession.getCurrent().getEmployee().getEmpname());
		tbDeathregDTO.getRequestDTO().setmName(UserSession.getCurrent().getEmployee().getEmpMName());
		tbDeathregDTO.getRequestDTO().setlName(UserSession.getCurrent().getEmployee().getEmpLName());
		tbDeathregDTO.getRequestDTO().setMobileNo(UserSession.getCurrent().getEmployee().getEmpmobno());
		tbDeathregDTO.getRequestDTO().setEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		tbDeathregDTO.getRequestDTO().setCityName(UserSession.getCurrent().getEmployee().getEmpAddress());
		tbDeathregDTO.getRequestDTO().setUserId(employee.getEmpId());
		tbDeathregDTO.setDrDod(Utility.stringToDate(tbDeathregDTO.getDateOfDeath()));
		CommonChallanDTO offline = getOfflineDTO();
		TbDeathregDTO tbDeathregDetDTO = ideathregCorrectionService.savedeathCorrectionDeatils(tbDeathregDTO);
		this.setTbDeathregDTO(tbDeathregDetDTO);
		setSuccessMessage(getAppSession().getMessage("TbDeathregDTO.SuccessMsgDrc")
					+ tbDeathregDetDTO.getApmApplicationId());
		tbDeathregDetDTO.setApplnId(tbDeathregDetDTO.getApmApplicationId());
		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        Map<Long, Double> details = new HashMap<>(0);
        final Map<Long, Long> billDetails = new HashMap<>(0);
        setChallanDToandSaveChallanData(offline, details, billDetails, tbDeathregDetDTO);
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
        offline.setAmountToShow(billPayDto.getAmount());

        offline.setApplicantName(billPayDto.getDrInformantName());
        offline.setApplNo(billPayDto.getApplnId());
        offline.setApplicantAddress(billPayDto.getDrInformantAddr());
        offline.setUniquePrimaryId(String.valueOf(billPayDto.getApplnId()));
       // offline.setMobileNumber(billPayDto.getPrimaryOwnerMobNo());
        offline.setServiceId(billPayDto.getServiceId());
        offline.setDeptId(billPayDto.getDeptId());
        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
        if (CollectionUtils.isNotEmpty(billPayDto.getDocumentList())) {
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
	 
	 public TbDeathregDTO getTbDeathregDTO() {
			return tbDeathregDTO;
		}

		public void setTbDeathregDTO(TbDeathregDTO tbDeathregDTO) {
			this.tbDeathregDTO = tbDeathregDTO;
		}

		public List<TbDeathregDTO> getTbDeathregDTOList() {
			return tbDeathregDTOList;
		}

		public void setTbDeathregDTOList(List<TbDeathregDTO> tbDeathregDTOList) {
			this.tbDeathregDTOList = tbDeathregDTOList;
		}

		public String getSaveMode() {
			return saveMode;
		}

		public void setSaveMode(String saveMode) {
			this.saveMode = saveMode;
		}

		public List<DocumentDetailsVO> getCheckList() {
			return checkList;
		}

		public void setCheckList(List<DocumentDetailsVO> checkList) {
			this.checkList = checkList;
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