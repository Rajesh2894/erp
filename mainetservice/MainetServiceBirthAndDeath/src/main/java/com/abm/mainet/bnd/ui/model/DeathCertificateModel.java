package com.abm.mainet.bnd.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.service.IDeathCertificateServices;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Component("bndDeathCertificateModel")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DeathCertificateModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private IDeathCertificateServices iDeathCertificateServices;
	
	@Autowired
	private ILocationMasService locationMasService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	private DeathCertificateDTO deathCertificateDTO = new DeathCertificateDTO();
	
	BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	 private List<CFCAttachment> documentList = new ArrayList<>();
	 
	 private RequestDTO requestDTO = new RequestDTO();
	 
	 private String serviceName;
	 private Long applicationId;
	 private String applicantName;
	 private boolean noCheckListFound ;
	 private String errorMessage;
	 private String saveMode;
	private String formName;
	 
	 private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
	@Override
	public boolean saveForm() {
	
		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();

		deathCertificateDTO.setUpdatedBy(employee.getEmpId());
		deathCertificateDTO.setUpdatedDate(new Date());
		deathCertificateDTO.setLgIpMacUpd(employee.getEmppiservername());
		deathCertificateDTO.setLmoddate(new Date());
		deathCertificateDTO.setLgIpMac(employee.getEmppiservername());
		deathCertificateDTO.setOrgId(employee.getOrganisation().getOrgid());
		deathCertificateDTO.setLangId(langId);
		deathCertificateDTO.setUserId(employee.getUserId());
		requestDTO.setUpdatedBy(deathCertificateDTO.getUpdatedBy());
		requestDTO.setMacId(deathCertificateDTO.getLgIpMacUpd());
		requestDTO.setLangId(Long.valueOf(deathCertificateDTO.getLangId()));
		List<DocumentDetailsVO> documents = getCheckList();
		 documents = ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).prepareFileUpload(checkList);
		validateInputs(documents);
		if (hasValidationErrors()) {
            return false;
        }

		deathCertificateDTO.setDocumentList(checkList);
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.RDC, deathCertificateDTO.getOrgId());
		
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {

			setChargesAmount(String.valueOf(deathCertificateDTO.getAmount()));
			getOfflineDTO().setAmountToShow(deathCertificateDTO.getAmount());
			ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
			List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
			chargeDetailDTO.setChargeAmount(deathCertificateDTO.getAmount());

			long applicableAt = CommonMasterUtility
					.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation()).getLookUpId();
			long taxcategory = 0;
			long taxSubcategory = 0;

			List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
					MainetConstants.NUMBERS.ONE, UserSession.getCurrent().getOrganisation());
			for (LookUp lookUp : taxCategories) {
				if (lookUp.getLookUpCode().equals("SC")) {
					taxcategory = lookUp.getLookUpId();
					break;
				}
			}
			List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
					MainetConstants.NUMBERS.TWO, UserSession.getCurrent().getOrganisation());
			for (LookUp lookUp : taxSubCategories) {
				if (lookUp.getLookUpCode().equals("COPY")) {
					taxSubcategory = lookUp.getLookUpId();
					break;
				}

			}

			try {
				chargeDetailDTO.setChargeCode(tbTaxMasService.getTaxIdByServiceId(applicableAt,
						UserSession.getCurrent().getOrganisation().getOrgid(),
						serviceMas.getTbDepartment().getDpDeptid(), taxcategory, taxSubcategory,
						serviceMas.getSmServiceId(), MainetConstants.FlagY));
				chargeDetailDTO.setChargeDescReg(getAppSession().getMessage("TbDeathregDTO.drDeathIssuSerRegName"));
				chargeDetailDTO.setChargeDescEng(getAppSession().getMessage("TbDeathregDTO.drDeathIssuSerEngName"));
				chargesInfo.add(chargeDetailDTO);
				setChargesInfo(chargesInfo);
			} catch (Exception ex) {
				throw new FrameworkException("UNBALE TO SET  BND CHARGES", ex);
			}
		 }
     		
		if(serviceMas.getSmFeesSchedule()!=0)
		{ 
			CommonChallanDTO offline = getOfflineDTO();
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			getOfflineDTO().setApplicantFullName(this.getRequestDTO().getfName()+MainetConstants.WHITE_SPACE+this.getRequestDTO().getmName()+MainetConstants.WHITE_SPACE+this.getRequestDTO().getlName());
			offline.setAmountToShow(deathCertificateDTO.getAmount());
			if (offline.getAmountToShow() > 0d) {
				validateBean(offline, CommonOfflineMasterValidator.class);
				if (hasValidationErrors()) {
		            return false;
		        }
			}
			this.setServiceId(serviceMas.getSmServiceId());
			this.getChargesAmount();
		}
		this.setDeathCertificateDTO(deathCertificateDTO);
		this.setRequestDTO(requestDTO);
		iDeathCertificateServices.saveDeathCertificate(deathCertificateDTO, this);
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
			final LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(this.getRequestDTO().getAssWard1(), UserSession.getCurrent().getOrganisation());
			String locationNameById = locationMasService.getLocationNameById(this.getRequestDTO().getAssWard1(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			getOfflineDTO().setApplicantAddress(lookUp.getLookUpDesc());
			
			getOfflineDTO().setApplicantName(getAppSession().getMessage("receipt.label.forCertificateof")+MainetConstants.WHITE_SPACE+deathCertificateDTO.getDrDeceasedname()+MainetConstants.operator.RIGHT_BRACKET);
			getOfflineDTO().setOccupierName(deathCertificateDTO.getDrDeceasedname());
			getOfflineDTO().setDdDate(deathCertificateDTO.getDrCertiGen());
			getOfflineDTO().setRdV2(deathCertificateDTO.getDrRelativeName());
			getOfflineDTO().setRdV3(deathCertificateDTO.getDrHubandWfeNme());
			getOfflineDTO().setPoDate(deathCertificateDTO.getDrDod());
			 			
		 } 
		this.setSuccessMessage(getAppSession().getMessage("TbDeathregDTO.succes.msg")
	 					+ deathCertificateDTO.getApplnId());
	return true;
	}
	
	public void prepareContractDocumentsData(DeathCertificateDTO entity) {

		RequestDTO requestDTO = new RequestDTO();

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		requestDTO.setOrgId(orgId);

		requestDTO.setStatus(MainetConstants.FlagA);

		requestDTO.setDepartmentName("BND");

		requestDTO.setIdfId(entity.getApplnId().toString());

		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		setCommonFileAttachment(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)

				.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));

		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)

				.doMasterFileUpload(getCommonFileAttachment(), requestDTO);

	}
	
	public boolean validateInputs(final List<DocumentDetailsVO> dto) {
        boolean flag = false;
        if ((dto != null) && !dto.isEmpty()) {
            for (final DocumentDetailsVO doc : dto) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.FlagY)) {
                    if (doc.getDocumentByteCode() == null) {
                        addValidationError(
                                ApplicationSession.getInstance().getMessage("bnd.upload.mandatory.documents"));
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }
	
	public DeathCertificateDTO getDeathCertificateDTO() {
		return deathCertificateDTO;
	}

	public void setDeathCertificateDTO(DeathCertificateDTO deathCertificateDTO) {
		this.deathCertificateDTO = deathCertificateDTO;
	}


	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}


	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}


	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}


	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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


	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}


	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}


	public boolean isNoCheckListFound() {
		return noCheckListFound;
	}


	public void setNoCheckListFound(boolean noCheckListFound) {
		this.noCheckListFound = noCheckListFound;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public RequestDTO getRequestDTO() {
		return requestDTO;
	}


	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}


	public String getSaveMode() {
		return saveMode;
	}


	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
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
	
	
	
}
