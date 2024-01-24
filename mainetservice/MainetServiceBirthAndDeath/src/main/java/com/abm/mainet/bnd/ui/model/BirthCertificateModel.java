package com.abm.mainet.bnd.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.service.IBirthCertificateService;
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

@Component("bndBirthCertificateModel")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class BirthCertificateModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IBirthCertificateService birthCer;

	@Autowired
	private ServiceMasterService serviceMasterService;
	
	@Autowired
    private TbTaxMasService tbTaxMasService;

	RequestDTO requestDTO = new RequestDTO();
	
	BndAcknowledgementDto ackDto = new BndAcknowledgementDto();

	BirthCertificateDTO birthCertificateDto = new BirthCertificateDTO();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();

	private List<CFCAttachment> documentList = new ArrayList<>();

	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();
	
	@Autowired
	private ILocationMasService locationMasService;

	private Long applicationId;
	
	private String saveMode;
	
	private String formName;

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	private String applicantName;

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

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public BirthCertificateDTO getBirthCertificateDto() {
		return birthCertificateDto;
	}

	public void setBirthCertificateDto(BirthCertificateDTO birthCertificateDto) {
		this.birthCertificateDto = birthCertificateDto;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	@Override
	public boolean saveForm() {

		Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		birthCertificateDto.setUpdatedBy(employee.getEmpId());
		birthCertificateDto.setUpdatedDate(new Date());
		birthCertificateDto.setLmoddate(new Date());
		birthCertificateDto.setLgIpMac(employee.getEmppiservername());
		birthCertificateDto.setLgIpMacUpd(employee.getEmppiservername());
		birthCertificateDto.setOrgId(employee.getOrganisation().getOrgid());
		birthCertificateDto.setLangId(langId);
		birthCertificateDto.setUserId(employee.getUserId());
		this.getRequestDTO().setLangId(Long.valueOf(birthCertificateDto.getLangId()));
		List<DocumentDetailsVO> documents = getCheckList();
		 documents = ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.prepareFileUpload(checkList);
		validateInputs(documents);
		if (hasValidationErrors()) {
			return false;
		}
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.RBC,
				birthCertificateDto.getOrgId());
		
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
		setChargesAmount(birthCertificateDto.getAmount().toString());
	
		getOfflineDTO().setAmountToShow(birthCertificateDto.getAmount());
		getOfflineDTO().setOccupierName(birthCertificateDto.getBrChildName());
		getOfflineDTO().setDdDate(birthCertificateDto.getBrAcgd());
		getOfflineDTO().setRdV2(birthCertificateDto.getPdFathername());
		getOfflineDTO().setRdV3(birthCertificateDto.getPdMothername());
		getOfflineDTO().setPoDate(birthCertificateDto.getBrDob());						
		getOfflineDTO().setApplicantFullName(this.getRequestDTO().getfName()+MainetConstants.WHITE_SPACE+this.getRequestDTO().getmName()+MainetConstants.WHITE_SPACE+this.getRequestDTO().getlName());
			 ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
	            List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
             chargeDetailDTO.setChargeAmount(birthCertificateDto.getAmount());
            
             long applicableAt= CommonMasterUtility
                     .getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                             MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
                     .getLookUpId();
             long taxcategory=0;
             long taxSubcategory=0;
             
             List<LookUp> taxCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
     				MainetConstants.NUMBERS.ONE, UserSession.getCurrent().getOrganisation());
     		for (LookUp lookUp : taxCategories) {
     			if ( lookUp.getLookUpCode().equals("SC") ) {
     				taxcategory=lookUp.getLookUpId();
     				break;
     			}
     		}
     		List<LookUp> taxSubCategories = CommonMasterUtility.getLevelData(MainetConstants.CommonMasterUi.TAC,
     				MainetConstants.NUMBERS.TWO, UserSession.getCurrent().getOrganisation());
     		for (LookUp lookUp : taxSubCategories) {
     			if (lookUp.getLookUpCode().equals("COPY")) {
     				taxSubcategory=lookUp.getLookUpId();
     				break;
     			}

     		}
             
     		try {
     			
     			
     			  chargeDetailDTO.setChargeCode(tbTaxMasService.getTaxIdByServiceId(applicableAt,UserSession.getCurrent().getOrganisation().getOrgid(),
                          serviceMas.getTbDepartment().getDpDeptid(), taxcategory,taxSubcategory,serviceMas.getSmServiceId(),MainetConstants.FlagY));
           /*  chargeDetailDTO.setChargeCode(tbTaxMasService.getTaxId(applicableAt,UserSession.getCurrent().getOrganisation().getOrgid(),
                     serviceMas.getTbDepartment().getDpDeptid(), taxcategory,taxSubcategory));*/
             
             chargeDetailDTO.setChargeDescReg(getAppSession().getMessage("BirthCertificateDTO.serName"));
             chargeDetailDTO.setChargeDescEng(getAppSession().getMessage("BirthCertificateDTO.regserName"));
             chargesInfo.add(chargeDetailDTO);
             setChargesInfo(chargesInfo);
             
             
             

     		} catch (Exception ex) {
    			throw new FrameworkException("UNBALE TO SET  BND CHARGES", ex);
    		}
       }
		birthCertificateDto.setUploadDocument(checkList);
		// birthCertificateDto.setBrRegDate(new Date());
		
		if (serviceMas.getSmFeesSchedule() != 0) {
			CommonChallanDTO offline = getOfflineDTO();
			final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
			offline.setOfflinePaymentText(modeDesc);
			offline.setAmountToShow(Double.parseDouble(this.getChargesAmount()));
			if (offline.getAmountToShow() > 0d) {
				validateBean(offline, CommonOfflineMasterValidator.class);
				if (hasValidationErrors()) {
		            return false;
		        }
			}
			birthCertificateDto.setSmServiceId(serviceMas.getSmServiceId());
			this.setServiceId(birthCertificateDto.getSmServiceId());
			this.getChargesAmount();
		}

		birthCer.saveBirthCertificate(birthCertificateDto, this);
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
			 final LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(this.getRequestDTO().getAssWard1(), UserSession.getCurrent().getOrganisation());
		String locationNameById = locationMasService.getLocationNameById(this.getRequestDTO().getAssWard1(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		
		 
             if (lookUp.getLookUpId()==this.getRequestDTO().getAssWard1()) {
            	 getOfflineDTO().setApplicantAddress(lookUp.getLookUpDesc());
             }
         

		getOfflineDTO().setApplicantName(getAppSession().getMessage("receipt.label.forCertificateof")+MainetConstants.WHITE_SPACE+birthCertificateDto.getBrChildName()+MainetConstants.operator.RIGHT_BRACKET);
	
	   }
		this.setSuccessMessage(getAppSession().getMessage("BirthCertificateDTO.succes.msg")
				+ birthCertificateDto.getApmApplicationId());
		
		return true;

	}
	
	
	public void prepareContractDocumentsData(BirthCertificateDTO entity) {

		RequestDTO requestDTO = new RequestDTO();

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		requestDTO.setOrgId(orgId);

		requestDTO.setStatus(MainetConstants.FlagA);

		requestDTO.setDepartmentName("BND");

		requestDTO.setIdfId(entity.getApmApplicationId().toString()); 

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

	public BndAcknowledgementDto getAckDto() {
		return ackDto;
	}

	public void setAckDto(BndAcknowledgementDto ackDto) {
		this.ackDto = ackDto;
	}

	
}
