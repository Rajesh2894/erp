package com.abm.mainet.legal.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CaseEntryDetailDTO;
import com.abm.mainet.legal.dto.CounterAffidavitDTO;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.ICounterAffidavit;

@Component
@Scope("session")
public class CounterAffidavitModel extends AbstractFormModel {

	private static final long serialVersionUID = -4707392996684157566L;

	private List<CaseEntryDTO> caseEntryDTOList;

	private String saveMode;

	private CaseEntryDTO caseEntryDTO;

	private List<AttachDocs> caseAttachDocsList = new ArrayList<>();

	private List<AttachDocs> attachDocsList = new ArrayList<>();

	private String removeCommonFileById;

	private CounterAffidavitDTO counterAffidavitDTO;
	
	private List<CaseEntryDetailDTO> plenfiffEntryDetailDTOList;
	private List<CaseEntryDetailDTO> defenderEntryDetailDTOList;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private ICaseEntryService caseEntryService;

	@Autowired
	private TbDepartmentService tbDepartmentService;
	@Autowired
	ICounterAffidavit iCounterAffidavit;

	public List<CaseEntryDTO> getCaseEntryDTOList() {
		return caseEntryDTOList;
	}

	public void setCaseEntryDTOList(List<CaseEntryDTO> caseEntryDTOList) {
		this.caseEntryDTOList = caseEntryDTOList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public CaseEntryDTO getCaseEntryDTO() {
		return caseEntryDTO;
	}

	public void setCaseEntryDTO(CaseEntryDTO caseEntryDTO) {
		this.caseEntryDTO = caseEntryDTO;
	}

	public List<AttachDocs> getCaseAttachDocsList() {
		return caseAttachDocsList;
	}

	public void setCaseAttachDocsList(List<AttachDocs> caseAttachDocsList) {
		this.caseAttachDocsList = caseAttachDocsList;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public String getRemoveCommonFileById() {
		return removeCommonFileById;
	}

	public void setRemoveCommonFileById(String removeCommonFileById) {
		this.removeCommonFileById = removeCommonFileById;
	}

	public CounterAffidavitDTO getCounterAffidavitDTO() {
		return counterAffidavitDTO;
	}

	public void setCounterAffidavitDTO(CounterAffidavitDTO counterAffidavitDTO) {
		this.counterAffidavitDTO = counterAffidavitDTO;
	}

	public List<CaseEntryDetailDTO> getPlenfiffEntryDetailDTOList() {
		return plenfiffEntryDetailDTOList;
	}

	public void setPlenfiffEntryDetailDTOList(List<CaseEntryDetailDTO> plenfiffEntryDetailDTOList) {
		this.plenfiffEntryDetailDTOList = plenfiffEntryDetailDTOList;
	}

	public List<CaseEntryDetailDTO> getDefenderEntryDetailDTOList() {
		return defenderEntryDetailDTOList;
	}

	public void setDefenderEntryDetailDTOList(List<CaseEntryDetailDTO> defenderEntryDetailDTOList) {
		this.defenderEntryDetailDTOList = defenderEntryDetailDTOList;
	}

	@Override
	public boolean saveForm() {

		counterAffidavitDTO.setCaseId(caseEntryDTO.getCseId());
		if (counterAffidavitDTO.getCafId() != null) {
			counterAffidavitDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			counterAffidavitDTO.setUpdatedDate(new Date());
			counterAffidavitDTO.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			iCounterAffidavit.updateEntry(counterAffidavitDTO);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.editCounterAffidavit"));
		} else {
			counterAffidavitDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			counterAffidavitDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			counterAffidavitDTO.setCreatedDate(new Date());
			counterAffidavitDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			iCounterAffidavit.saveEntry(counterAffidavitDTO);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("lgl.saveCounterAffidavit"));
		}

		prepareContractDocumentsData(caseEntryDTO);

		return true;
	}

	public void prepareContractDocumentsData(CaseEntryDTO caseEntryDTO) {
		RequestDTO requestDTO = new RequestDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgId);
		requestDTO.setStatus(MainetConstants.FlagA);
		requestDTO.setDepartmentName(
				tbDepartmentService.findDepartmentShortCodeByDeptId(caseEntryDTO.getCseDeptid(), orgId));
		requestDTO.setIdfId("CounterAffidavit" + MainetConstants.DOUBLE_BACK_SLACE + caseEntryDTO.getCseId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		List<DocumentDetailsVO> dto = getCommonFileAttachment();

		setCommonFileAttachment(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		int i = 0;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			getCommonFileAttachment().get(i).setDoc_DESC_ENGL(dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
			i++;
		}
		fileUpload.doMasterFileUpload(getCommonFileAttachment(), requestDTO);
		List<Long> enclosureRemoveById = null;
		String fileId = getRemoveCommonFileById();
		if (fileId != null && !fileId.isEmpty()) {
			enclosureRemoveById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				enclosureRemoveById.add(Long.valueOf(fields));
			}
		}
		if (enclosureRemoveById != null && !enclosureRemoveById.isEmpty())
			caseEntryService.deleteContractDocFileById(enclosureRemoveById,
					UserSession.getCurrent().getEmployee().getEmpId());
	}

}
