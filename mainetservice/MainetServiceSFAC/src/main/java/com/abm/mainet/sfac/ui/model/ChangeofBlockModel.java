/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonMasterDto;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.dto.TbOrganisation;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.BlockAllocationDto;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
//import com.abm.mainet.sfac.dto.ChangeofBlockDto;
import com.abm.mainet.sfac.service.AllocationOfBlocksService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class ChangeofBlockModel extends AbstractFormModel {
	private static final Logger logger = Logger.getLogger(ChangeofBlockModel.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3830530404005430982L;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private AllocationOfBlocksService alocOfBlocksService;

	@Autowired
	TbOrganisationService tbOrganisationService;

	@Autowired
	private IFileUploadService fileUpload;
	
	@Autowired
	private IOrganisationService orgService;

	private List<TbDepartment> departmentList = new ArrayList<>();

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private BlockAllocationDto blockAllocationDto = new BlockAllocationDto();

	private BlockAllocationDto newBlockAllocationDto = new BlockAllocationDto();

	private List<BlockAllocationDto> blockAllocationDtoList = new ArrayList<>();

	private List<BlockAllocationDto> blockDtoList = new ArrayList<>();

	private String checklistCheck;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private String showHideFlag;

	private List<LookUp> vacantBlockList = new ArrayList<>();

	List<CommonMasterDto> commonMasterDtoList = new ArrayList<>();

	List<CBBOMasterDto> cbboMasterList = new ArrayList<>();

	private String saveMode;

	List<LookUp> allocationCatgList = new ArrayList<>();
	List<LookUp> allocationSubCatgList = new ArrayList<>();
	List<LookUp> allcSubCatgTargetList = new ArrayList<>();

	private String showEdit;

	private String OrgShortNm;

	List<LookUp> stateList = new ArrayList<>();

	List<LookUp> districtList = new ArrayList<>();
	List<LookUp> blockList = new ArrayList<>();

	List<TbOrganisation> orgList = new ArrayList<>();

	private List<DocumentDetailsVO> documents = new ArrayList<>();

	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();

	private String removedIds;
	private List<Long> fileCountUpload;
	private Long length;

	private String removeCommonFileById;

	// private ChangeofBlockDto blockDto = new ChangeofBlockDto();

	/**
	 * @return the checklistCheck
	 */
	public String getChecklistCheck() {
		return checklistCheck;
	}

	/**
	 * @param checklistCheck the checklistCheck to set
	 */
	public void setChecklistCheck(String checklistCheck) {
		this.checklistCheck = checklistCheck;
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
	 * @return the blockDto
	 */
	/*
	 * public ChangeofBlockDto getBlockDto() { return blockDto; }
	 * 
	 * 
	 * 
	 *//**
		 * @param blockDto the blockDto to set
		 *//*
			 * public void setBlockDto(ChangeofBlockDto blockDto) { this.blockDto =
			 * blockDto; }
			 */

	@SuppressWarnings("unchecked")
	public void getCheckListFromBrms() {

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(MainetConstants.TradeLicense.CHECK_LIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			checkListModel2.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			checkListModel2.setServiceCode(MainetConstants.Sfac.CBR);

			WSRequestDTO checklistReqDto = new WSRequestDTO();
			checklistReqDto.setDataModel(checkListModel2);
			WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
					|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

				if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
					List<DocumentDetailsVO> checkListList = Collections.emptyList();
					checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();
					long cnt = 1;
					for (final DocumentDetailsVO doc : checkListList) {
						doc.setDocumentSerialNo(cnt);
						cnt++;
					}
					if ((checkListList != null) && !checkListList.isEmpty()) {
						setCheckList(checkListList);
					}
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				final Object object = list.get(position);
				responseMap = (LinkedHashMap<Long, Object>) object;
				final String jsonString = new JSONObject(responseMap).toString();
				dataModel = new ObjectMapper().readValue(jsonString, clazz);
				dataModelList.add(dataModel);
			}

		} catch (final IOException e) {
			logger.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}

	@Override
	public boolean saveForm() {
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		Employee emp = UserSession.getCurrent().getEmployee();
		BlockAllocationDto masDto = getBlockAllocationDto();
		Organisation npmaOrg = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.NPMA);
		Organisation cbboOrg = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
		
		if (StringUtils.isNotEmpty(emp.getEmpname()) && StringUtils.isNotEmpty(emp.getEmplname()))
			masDto.setUserName(emp.getEmpname() + "" + emp.getEmplname());
		else
			masDto.setUserName(emp.getEmpname());
		
		if (masDto.getCreatedBy() == null) {
			masDto.setCreatedBy(createdBy);
			masDto.setCreatedDate(newDate);
			masDto.setOrgId(npmaOrg.getOrgid());
			masDto.setLgIpMac(lgIp);
		} else {
			masDto.setUpdatedBy(createdBy);
			masDto.setUpdatedDate(newDate);
			masDto.setOrgId(npmaOrg.getOrgid());
			masDto.setLgIpMac(lgIp);
		}
		masDto.getBlockDetailDto().forEach(detDto -> {
			if (detDto.getCreatedBy() == null) {
				detDto.setCreatedBy(createdBy);
				detDto.setCreatedDate(newDate);
				detDto.setOrgId(cbboOrg.getOrgid());
				detDto.setLgIpMac(lgIp);
				if (detDto.getStatus() != null && detDto.getStatus().equals(MainetConstants.FlagI))
					 detDto.setStatus(MainetConstants.FlagI);
				else
				detDto.setStatus(MainetConstants.FlagA);
			} else {
				detDto.setUpdatedBy(createdBy);
				detDto.setUpdatedDate(newDate);
				detDto.setOrgId(cbboOrg.getOrgid());
				detDto.setLgIpMac(lgIp);
				if (detDto.getStatus() != null && detDto.getStatus().equals(MainetConstants.FlagI))
					 detDto.setStatus(MainetConstants.FlagI);
				else
				detDto.setStatus(MainetConstants.FlagA);
			}
		});

		
		BlockAllocationDto newlyAllocatedBlock = getNewBlockAllocationDto();
		newlyAllocatedBlock.getBlockDetailDto().forEach(block -> {
			if (block.getCreatedBy() == null) {	
			block.setCreatedBy(createdBy);
			block.setCreatedDate(newDate);
			block.setOrgId(cbboOrg.getOrgid());
			block.setLgIpMac(lgIp);
			block.setSdb1(block.getStateId());
			block.setSdb2(block.getDistId());
			block.setSdb3(block.getBlckId());
			block.setStatus(MainetConstants.FlagC);
			} else {
				block.setUpdatedBy(createdBy);
				block.setUpdatedDate(newDate);
				block.setOrgId(cbboOrg.getOrgid());
				block.setLgIpMac(lgIp);
				block.setSdb1(block.getStateId());
				block.setSdb2(block.getDistId());
				block.setSdb3(block.getBlckId());
				block.setStatus(MainetConstants.FlagC);
			}
		
		});
		masDto = alocOfBlocksService.saveChangedBlockDetails(masDto, newlyAllocatedBlock, this);
		this.setSuccessMessage((getAppSession().getMessage("sfac.change.block.save.msg")) + masDto.getApplicationId());
		prepareDocumentsData(masDto);
		return true;

	}

	/**
	 * @param masDto
	 */
	private void prepareDocumentsData(BlockAllocationDto masDto) {
		logger.info("prepareDocumentsData Started");

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setOrgId(masDto.getOrgId());
		requestDTO.setStatus(MainetConstants.FlagA);
		requestDTO.setDepartmentName(MainetConstants.Sfac.SFAC);
		requestDTO.setIdfId(MainetConstants.Sfac.CBR + MainetConstants.FILE_PATH_SEPARATOR + masDto.getApplicationId());
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
			alocOfBlocksService.deleteContractDocFileById(enclosureRemoveById,
					UserSession.getCurrent().getEmployee().getEmpId());

	}

	/**
	 * @return
	 */
	/*
	 * public boolean validateInputs() { // TODO Auto-generated method stub return
	 * false; }
	 */
	/**
	 * @return the departmentList
	 */
	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}

	/**
	 * @param departmentList the departmentList to set
	 */
	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	/**
	 * @return the faYears
	 */
	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	/**
	 * @param faYears the faYears to set
	 */
	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}

	/**
	 * @return the blockAllocationDto
	 */
	public BlockAllocationDto getBlockAllocationDto() {
		return blockAllocationDto;
	}

	/**
	 * @param blockAllocationDto the blockAllocationDto to set
	 */
	public void setBlockAllocationDto(BlockAllocationDto blockAllocationDto) {
		this.blockAllocationDto = blockAllocationDto;
	}

	/**
	 * @return the blockAllocationDtoList
	 */
	public List<BlockAllocationDto> getBlockAllocationDtoList() {
		return blockAllocationDtoList;
	}

	/**
	 * @param blockAllocationDtoList the blockAllocationDtoList to set
	 */
	public void setBlockAllocationDtoList(List<BlockAllocationDto> blockAllocationDtoList) {
		this.blockAllocationDtoList = blockAllocationDtoList;
	}

	/**
	 * @return the showHideFlag
	 */
	public String getShowHideFlag() {
		return showHideFlag;
	}

	/**
	 * @param showHideFlag the showHideFlag to set
	 */
	public void setShowHideFlag(String showHideFlag) {
		this.showHideFlag = showHideFlag;
	}

	/**
	 * @return the vacantBlockList
	 */
	public List<LookUp> getVacantBlockList() {
		return vacantBlockList;
	}

	/**
	 * @param vacantBlockList the vacantBlockList to set
	 */
	public void setVacantBlockList(List<LookUp> vacantBlockList) {
		this.vacantBlockList = vacantBlockList;
	}

	/**
	 * @return the blockDtoList
	 */
	public List<BlockAllocationDto> getBlockDtoList() {
		return blockDtoList;
	}

	/**
	 * @param blockDtoList the blockDtoList to set
	 */
	public void setBlockDtoList(List<BlockAllocationDto> blockDtoList) {
		this.blockDtoList = blockDtoList;
	}

	/**
	 * @return the stateList
	 */
	public List<LookUp> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(List<LookUp> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the districtList
	 */
	public List<LookUp> getDistrictList() {
		return districtList;
	}

	/**
	 * @param districtList the districtList to set
	 */
	public void setDistrictList(List<LookUp> districtList) {
		this.districtList = districtList;
	}

	/**
	 * @return the blockList
	 */
	public List<LookUp> getBlockList() {
		return blockList;
	}

	/**
	 * @param blockList the blockList to set
	 */
	public void setBlockList(List<LookUp> blockList) {
		this.blockList = blockList;
	}

	/**
	 * @return the orgList
	 */
	public List<TbOrganisation> getOrgList() {
		return orgList;
	}

	/**
	 * @param orgList the orgList to set
	 */
	public void setOrgList(List<TbOrganisation> orgList) {
		this.orgList = orgList;
	}

	/**
	 * @return the documents
	 */
	public List<DocumentDetailsVO> getDocuments() {
		return documents;
	}

	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(List<DocumentDetailsVO> documents) {
		this.documents = documents;
	}

	/**
	 * @return the attachments
	 */
	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return the attachDocsList
	 */
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	/**
	 * @param attachDocsList the attachDocsList to set
	 */
	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	/**
	 * @return the removeCommonFileById
	 */
	public String getRemoveCommonFileById() {
		return removeCommonFileById;
	}

	/**
	 * @param removeCommonFileById the removeCommonFileById to set
	 */
	public void setRemoveCommonFileById(String removeCommonFileById) {
		this.removeCommonFileById = removeCommonFileById;
	}

	/**
	 * @return the commonMasterDtoList
	 */
	public List<CommonMasterDto> getCommonMasterDtoList() {
		return commonMasterDtoList;
	}

	/**
	 * @param commonMasterDtoList the commonMasterDtoList to set
	 */
	public void setCommonMasterDtoList(List<CommonMasterDto> commonMasterDtoList) {
		this.commonMasterDtoList = commonMasterDtoList;
	}

	/**
	 * @return the cbboMasterList
	 */
	public List<CBBOMasterDto> getCbboMasterList() {
		return cbboMasterList;
	}

	/**
	 * @param cbboMasterList the cbboMasterList to set
	 */
	public void setCbboMasterList(List<CBBOMasterDto> cbboMasterList) {
		this.cbboMasterList = cbboMasterList;
	}

	/**
	 * @return the saveMode
	 */
	public String getSaveMode() {
		return saveMode;
	}

	/**
	 * @param saveMode the saveMode to set
	 */
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	/**
	 * @return the allocationCatgList
	 */
	public List<LookUp> getAllocationCatgList() {
		return allocationCatgList;
	}

	/**
	 * @param allocationCatgList the allocationCatgList to set
	 */
	public void setAllocationCatgList(List<LookUp> allocationCatgList) {
		this.allocationCatgList = allocationCatgList;
	}

	/**
	 * @return the allocationSubCatgList
	 */
	public List<LookUp> getAllocationSubCatgList() {
		return allocationSubCatgList;
	}

	/**
	 * @param allocationSubCatgList the allocationSubCatgList to set
	 */
	public void setAllocationSubCatgList(List<LookUp> allocationSubCatgList) {
		this.allocationSubCatgList = allocationSubCatgList;
	}

	/**
	 * @return the showEdit
	 */
	public String getShowEdit() {
		return showEdit;
	}

	/**
	 * @param showEdit the showEdit to set
	 */
	public void setShowEdit(String showEdit) {
		this.showEdit = showEdit;
	}

	/**
	 * @return the orgShortNm
	 */
	public String getOrgShortNm() {
		return OrgShortNm;
	}

	/**
	 * @param orgShortNm the orgShortNm to set
	 */
	public void setOrgShortNm(String orgShortNm) {
		OrgShortNm = orgShortNm;
	}

	/**
	 * @return the newBlockAllocationDto
	 */
	public BlockAllocationDto getNewBlockAllocationDto() {
		return newBlockAllocationDto;
	}

	/**
	 * @param newBlockAllocationDto the newBlockAllocationDto to set
	 */
	public void setNewBlockAllocationDto(BlockAllocationDto newBlockAllocationDto) {
		this.newBlockAllocationDto = newBlockAllocationDto;
	}

	/**
	 * @return the allcSubCatgTargetList
	 */
	public List<LookUp> getAllcSubCatgTargetList() {
		return allcSubCatgTargetList;
	}

	/**
	 * @param allcSubCatgTargetList the allcSubCatgTargetList to set
	 */
	public void setAllcSubCatgTargetList(List<LookUp> allcSubCatgTargetList) {
		this.allcSubCatgTargetList = allcSubCatgTargetList;
	}

	/**
	 * @return the fileUpload
	 */
	public IFileUploadService getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
	}

	/**
	 * @return the removedIds
	 */
	public String getRemovedIds() {
		return removedIds;
	}

	/**
	 * @param removedIds the removedIds to set
	 */
	public void setRemovedIds(String removedIds) {
		this.removedIds = removedIds;
	}

	/**
	 * @return the length
	 */
	public Long getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(Long length) {
		this.length = length;
	}

}
