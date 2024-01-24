package com.abm.mainet.bnd.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BirthRegDraftDto;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.ui.validator.BirthRegValidator;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class BirthRegistrationModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IBirthRegService iBirthRegSevice;

	BirthRegistrationDTO birthRegDto = new BirthRegistrationDTO();

	private List<HospitalMasterDTO> hospitalMasterDTOList;

	private String hospitalList;

	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
	
	private BigDecimal amountToPaid;
	
	private String saveMode;
	
	private String chargesFetched;
	
	private List<BirthRegDraftDto> birthRegDraftDtoList;
	
	@Autowired
	private ServiceMasterService serviceMasterService;

	
	@Override
	public boolean saveForm() {
		
			if(birthRegDto.getStatusFlag().equals("O")) {
			validateBean(birthRegDto, BirthRegValidator.class);
	      }
			if (hasValidationErrors()) {
				return false;
			} 
			
			else {
				Employee employee = getUserSession().getEmployee();
				int langId = UserSession.getCurrent().getLanguageId();

				birthRegDto.setUpdatedBy(employee.getEmpId());
				birthRegDto.setUpdatedDate(new Date());
				birthRegDto.setLmodDate(new Date());
				birthRegDto.setLgIpMac(employee.getEmppiservername());
				birthRegDto.setLgIpMacUpd(employee.getEmppiservername());
				birthRegDto.setOrgId(employee.getOrganisation().getOrgid());
				birthRegDto.setLangId(langId);
				birthRegDto.setUserId(employee.getEmpId());

				List<DocumentDetailsVO> documents = getCheckList();
				 documents = ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
						.prepareFileUpload(checkList);
				validateInputs(documents);
				if (hasValidationErrors()) {
					return false;
				}
				birthRegDto.setUploadDocument(checkList);
                             if(this.getApmApplicationId()!=0) {
					birthRegDto.setApmApplicationId(this.getApmApplicationId());
					}
                   
				if(birthRegDto.getStatusFlag().equals("D")){
					birthRegDto.getBrDraftId();
					iBirthRegSevice.saveBirthRegDraft(birthRegDto);
					this.setSuccessMessage(getAppSession().getMessage("BirthRegDto.SuccessMsgBrDraft") + birthRegDto.getApmApplicationId());
				}else {
					ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.BR, birthRegDto.getOrgId());
					if(serviceMas.getSmFeesSchedule()!=0)
					{
					CommonChallanDTO offline = getOfflineDTO();
					final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
					offline.setOfflinePaymentText(modeDesc);
					if(this.getChargesAmount()!=null) {
					offline.setAmountToShow(Double.parseDouble(this.getChargesAmount()));
					} else {
						offline.setAmountToShow(0.0);
					}
					
					if (offline.getAmountToShow() > 0d) {
						validateBean(offline, CommonOfflineMasterValidator.class);
						if (hasValidationErrors()) {
							return false;
						}
					}
					this.setServiceId(birthRegDto.getServiceId());
					this.getChargesAmount();
					}
					this.setBirthRegDto(birthRegDto);
					iBirthRegSevice.saveBirthRegDet(birthRegDto,this);
					this.setSuccessMessage(getAppSession().getMessage("BirthRegDto.SuccessMsgBrApproval") + birthRegDto.getApmApplicationId());
				}
	
		return true;
		}
			
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
	public WorkflowTaskAction prepareWorkFlowTaskAction() {
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
			taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
			//taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType()); //getting null value ashish
			taskAction.setDateOfAction(new Date());
			taskAction.setCreatedDate(new Date());
			taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
			//taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail()); //getting null value ashish
			//taskAction.setReferenceId(tbDeathregDTO.getDrId().toString());
			taskAction.setApplicationId(birthRegDto.getApmApplicationId());
			taskAction.setComments(birthRegDto.getAuthRemark());
			taskAction.setPaymentMode("F");
			taskAction.setDecision("SUBMITED");
		return taskAction;
	}
	
	

	public List<HospitalMasterDTO> getHospitalMasterDTOList() {
		return hospitalMasterDTOList;
	}

	public List<HospitalMasterDTO> setHospitalMasterDTOList(List<HospitalMasterDTO> hospitalMasterDTOList) {
		return this.hospitalMasterDTOList = hospitalMasterDTOList;
	}

	public String getHospitalList() {
		return hospitalList;
	}

	public void setHospitalList(String hospitalList) {
		this.hospitalList = hospitalList;
	}

	public BirthRegistrationDTO getBirthRegDto() {
		return birthRegDto;
	}

	public void setBirthRegDto(BirthRegistrationDTO birthRegDto) {
		this.birthRegDto = birthRegDto;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public BigDecimal getAmountToPaid() {
		return amountToPaid;
	}

	public void setAmountToPaid(BigDecimal amountToPaid) {
		this.amountToPaid = amountToPaid;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public List<BirthRegDraftDto> getBirthRegDraftDtoList() {
		return birthRegDraftDtoList;
	}

	public void setBirthRegDraftDtoList(List<BirthRegDraftDto> birthRegDraftDtoList) {
		this.birthRegDraftDtoList = birthRegDraftDtoList;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public String getChargesFetched() {
		return chargesFetched;
	}

	public void setChargesFetched(String chargesFetched) {
		this.chargesFetched = chargesFetched;
	}
	
	

}
