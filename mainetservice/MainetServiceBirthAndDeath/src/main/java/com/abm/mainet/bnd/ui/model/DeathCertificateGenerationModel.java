package com.abm.mainet.bnd.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.DeathCauseMasterDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterCorrDTO;
import com.abm.mainet.bnd.dto.DeceasedMasterDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.MedicalMasterCorrectionDTO;
import com.abm.mainet.bnd.dto.MedicalMasterDTO;
import com.abm.mainet.bnd.dto.TbBdDeathregCorrDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * 
 * @author Bhagyashri.Dongardive
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DeathCertificateGenerationModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;
	
	private String saveMode;
	
	 @Autowired
	 private IDeathRegistrationService iDeathRegistrationService;
	 
	 	 
	 private ServiceMaster serviceMaster = new ServiceMaster();
	 RequestDTO requestDTO = new RequestDTO();
	   private TbDeathregDTO tbDeathregDTO = new TbDeathregDTO();
	   
	   private TbBdDeathregCorrDTO tbBdDeathregCorrDTO = new TbBdDeathregCorrDTO();
	   
	   private CemeteryMasterDTO cemeteryMasterDTO = new CemeteryMasterDTO(); 
	
		private List<HospitalMasterDTO> hospitalMasterDTOList;

		private String hospitalList;
	   
		private List<CemeteryMasterDTO> cemeteryMasterDTOList;
		
		private String cemeteryList;
		
		private List<TbDeathregDTO> tbDeathregDTOList;
		
		private String tbDeathRegDtoList;
		
		private MedicalMasterDTO medicalMasterDto = new MedicalMasterDTO();

		private DeceasedMasterDTO deceasedMasterDTO = new DeceasedMasterDTO();
		
		private List<DocumentDetailsVO> checkList = new ArrayList<>();

		private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
		
		private List<TbBdDeathregCorrDTO> tbDeathregcorrDTOList;
		
		private TbBdDeathregCorrDTO tbDeathregcorrDTO = new TbBdDeathregCorrDTO();
		
		private MedicalMasterCorrectionDTO medicalMasterCorrDto = new MedicalMasterCorrectionDTO();
		
		private DeathCauseMasterDTO deathCauseMasterDTO = new DeathCauseMasterDTO();
		
		private DeceasedMasterCorrDTO deceasedMasterCorrDTO = new DeceasedMasterCorrDTO(); 
		private String successFlag;
		private String payableFlag;
		private Double totalLoiAmount;
		private double amountToPay;
		private String printBT;// click from summary Screen ,based on this hide BT
		private List<TbLoiDet> loiDetail = new ArrayList<>();
		
	
		public String getSaveMode() {
		return saveMode;
	}
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	public IDeathRegistrationService getiDeathRegistrationService() {
		return iDeathRegistrationService;
	}
	public void setiDeathRegistrationService(IDeathRegistrationService iDeathRegistrationService) {
		this.iDeathRegistrationService = iDeathRegistrationService;
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
	public List<CemeteryMasterDTO> getCemeteryMasterDTOList() {
		return cemeteryMasterDTOList;
	}
	public List<CemeteryMasterDTO> setCemeteryMasterDTOList(List<CemeteryMasterDTO> cemeteryMasterDTOList) {
		return this.cemeteryMasterDTOList = cemeteryMasterDTOList;
	}
	public String getCemeteryList() {
		return cemeteryList;
	}
	public void setCemeteryList(String cemeteryList) {
		this.cemeteryList = cemeteryList;
	}
	public List<TbDeathregDTO> getTbDeathregDTOList() {
		return tbDeathregDTOList;
	}
	public void setTbDeathregDTOList(List<TbDeathregDTO> tbDeathregDTOList) {
		this.tbDeathregDTOList = tbDeathregDTOList;
	}
	public String getTbDeathRegDtoList() {
		return tbDeathRegDtoList;
	}
	public void setTbDeathRegDtoList(String tbDeathRegDtoList) {
		this.tbDeathRegDtoList = tbDeathRegDtoList;
	}
	public MedicalMasterDTO getMedicalMasterDto() {
		return medicalMasterDto;
	}
	public void setMedicalMasterDto(MedicalMasterDTO medicalMasterDto) {
		this.medicalMasterDto = medicalMasterDto;
	}
	public DeceasedMasterDTO getDeceasedMasterDTO() {
		return deceasedMasterDTO;
	}
	public void setDeceasedMasterDTO(DeceasedMasterDTO deceasedMasterDTO) {
		this.deceasedMasterDTO = deceasedMasterDTO;
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

	public TbBdDeathregCorrDTO getTbDeathregcorrDTO() {
		return tbDeathregcorrDTO;
	}
	public void setTbDeathregcorrDTO(TbBdDeathregCorrDTO tbDeathregcorrDTO) {
		this.tbDeathregcorrDTO = tbDeathregcorrDTO;
	}
	
	public TbDeathregDTO getTbDeathregDTO() {
		return tbDeathregDTO;
	}
	public void setTbDeathregDTO(TbDeathregDTO tbDeathregDTO) {
		this.tbDeathregDTO = tbDeathregDTO;
	}

	public List<TbBdDeathregCorrDTO> getTbDeathregcorrDTOList() {
		return tbDeathregcorrDTOList;
	}

	public void setTbDeathregcorrDTOList(List<TbBdDeathregCorrDTO> tbDeathregcorrDTOList) {
		this.tbDeathregcorrDTOList = tbDeathregcorrDTOList;
	}

    public CemeteryMasterDTO getCemeteryMasterDTO() {
    	return cemeteryMasterDTO;
    }

	public void setCemeteryMasterDTO(CemeteryMasterDTO cemeteryMasterDTO) {
		this.cemeteryMasterDTO = cemeteryMasterDTO;
	}

	public MedicalMasterCorrectionDTO getMedicalMasterCorrDto() {
		return medicalMasterCorrDto;
	}

	public void setMedicalMasterCorrDto(MedicalMasterCorrectionDTO medicalMasterCorrDto) {
		this.medicalMasterCorrDto = medicalMasterCorrDto;
	}
	
	public DeathCauseMasterDTO getDeathCauseMasterDTO() {
		return deathCauseMasterDTO;
	}

	public void setDeathCauseMasterDTO(DeathCauseMasterDTO deathCauseMasterDTO) {
		this.deathCauseMasterDTO = deathCauseMasterDTO;
	}

	public DeceasedMasterCorrDTO getDeceasedMasterCorrDTO() {
		return deceasedMasterCorrDTO;
	}

	public void setDeceasedMasterCorrDTO(DeceasedMasterCorrDTO deceasedMasterCorrDTO) {
		this.deceasedMasterCorrDTO = deceasedMasterCorrDTO;
	}


	public String getSuccessFlag() {
		return successFlag;
	}


	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}


	public String getPayableFlag() {
		return payableFlag;
	}


	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}


	public Double getTotalLoiAmount() {
		return totalLoiAmount;
	}


	public void setTotalLoiAmount(Double totalLoiAmount) {
		this.totalLoiAmount = totalLoiAmount;
	}


	public double getAmountToPay() {
		return amountToPay;
	}


	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}


	public String getPrintBT() {
		return printBT;
	}


	public void setPrintBT(String printBT) {
		this.printBT = printBT;
	}


	public List<TbLoiDet> getLoiDetail() {
		return loiDetail;
	}


	public void setLoiDetail(List<TbLoiDet> loiDetail) {
		this.loiDetail = loiDetail;
	}


	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}


	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}


	public RequestDTO getRequestDTO() {
		return requestDTO;
	}


	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}
	public TbBdDeathregCorrDTO getTbBdDeathregCorrDTO() {
		return tbBdDeathregCorrDTO;
	}
	public void setTbBdDeathregCorrDTO(TbBdDeathregCorrDTO tbBdDeathregCorrDTO) {
		this.tbBdDeathregCorrDTO = tbBdDeathregCorrDTO;
	}
	
	
	
	
}
