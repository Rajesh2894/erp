/**
 * 
 */
package com.abm.mainet.workManagement.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Component
@Scope("session")
public class ContractAgreementPrintModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6085000887279897660L;
  
	private List<ContractAgreementSummaryDTO> contractSummaryDTOList = new ArrayList<>();
	private ContractAgreementSummaryDTO contractAgreementSummaryDTO = new ContractAgreementSummaryDTO();
	TenderWorkDto tenderWorkDto = new TenderWorkDto();
	private List<WorkEstimateMasterDto> workMasterDtosList =new ArrayList<>();
	private String totalAmount ;
	private TenderWorkDto printNoticeInvintingTender = new TenderWorkDto();
	private String mode ;
	private int langId;
	private String sudaEnv;
	
	public int getLangId() {
		return langId;
	}
	public void setLangId(int langId) {
		this.langId = langId;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<WorkEstimateMasterDto> getWorkMasterDtosList() {
		return workMasterDtosList;
	}
	public void setWorkMasterDtosList(List<WorkEstimateMasterDto> workMasterDtosList) {
		this.workMasterDtosList = workMasterDtosList;
	}
	public TenderWorkDto getTenderWorkDto() {
		return tenderWorkDto;
	}
	public void setTenderWorkDto(TenderWorkDto tenderWorkDto) {
		this.tenderWorkDto = tenderWorkDto;
	}
	public ContractAgreementSummaryDTO getContractAgreementSummaryDTO() {
		return contractAgreementSummaryDTO;
	}
	public void setContractAgreementSummaryDTO(ContractAgreementSummaryDTO contractAgreementSummaryDTO) {
		this.contractAgreementSummaryDTO = contractAgreementSummaryDTO;
	}
	public List<ContractAgreementSummaryDTO> getContractSummaryDTOList() {
		return contractSummaryDTOList;
	}
	public void setContractSummaryDTOList(List<ContractAgreementSummaryDTO> contractSummaryDTOList) {
		this.contractSummaryDTOList = contractSummaryDTOList;
	}
	public TenderWorkDto getPrintNoticeInvintingTender() {
		return printNoticeInvintingTender;
	}
	public void setPrintNoticeInvintingTender(TenderWorkDto printNoticeInvintingTender) {
		this.printNoticeInvintingTender = printNoticeInvintingTender;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getSudaEnv() {
		return sudaEnv;
	}
	public void setSudaEnv(String sudaEnv) {
		this.sudaEnv = sudaEnv;
	}
}
