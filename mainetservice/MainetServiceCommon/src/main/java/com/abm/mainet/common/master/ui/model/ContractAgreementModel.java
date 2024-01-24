package com.abm.mainet.common.master.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.ContractAgreementSummaryDTO;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.ContractPart1DetailDTO;
import com.abm.mainet.common.master.dto.ContractPart2DetailDTO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbOrgDesignation;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrgDesignationService;
import com.abm.mainet.common.master.ui.validator.ContractAgreementFormValidator;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.DepartmentLookUp;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author apurva.salgaonkar
 *
 */
@Component
@Scope("session")
public class ContractAgreementModel extends AbstractFormModel {

	@Autowired
	private IContractAgreementService ContractAgreementService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	TbDepartmentService tbDepartmentService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	TbOrgDesignationService tbOrgDesignationService;
	
	@Resource
	private DepartmentService deptService;

	private String modeType;
	private Long photoId;
	private Long thumbId;
	private String uploadType;
	private Long viewuploadId;
	private String showForm;
	private Map<Long, String> uploadedfile = new HashMap<>(0);
	private ContractMastDTO contractMastDTO = new ContractMastDTO();
	private static final long serialVersionUID = 1L;
	private Map<String, File> UploadMap = new HashMap<>();
	private String formMode;
	private Long tndVendorId;
	

	private List<ContractAgreementSummaryDTO> summaryDTOList = new ArrayList<>();

	private Long fileId;
	private List<Long> fileCountUpload = new ArrayList<>();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private String removeCommonFileById;

	private String contracterNameFlag;
	// To set the bank details
	private Map<Long, String> bankMapList;
	private String contBGamount;
	private String termsFlag;
	private List<String> termsList;
	private String contMapFlag;
	
	private Long estateId;
	private Long propId;
	private String deptShortCode;

	public String getContMapFlag() {
		return contMapFlag;
	}

	public void setContMapFlag(String contMapFlag) {
		this.contMapFlag = contMapFlag;
	}

	public List<String> getTermsList() {
		return termsList;
	}

	public void setTermsList(List<String> termsList) {
		this.termsList = termsList;
	}

	public String getTermsFlag() {
		return termsFlag;
	}

	public void setTermsFlag(String termsFlag) {
		this.termsFlag = termsFlag;
	}

	public String getContBGamount() {
		return contBGamount;
	}

	public void setContBGamount(String contBGamount) {
		this.contBGamount = contBGamount;
	}

	public Map<Long, String> getBankMapList() {
		return bankMapList;
	}

	public void setBankMapList(Map<Long, String> bankMapList) {
		this.bankMapList = bankMapList;
	}

	public String getContracterNameFlag() {
		return contracterNameFlag;
	}

	public void setContracterNameFlag(String contracterNameFlag) {
		this.contracterNameFlag = contracterNameFlag;
	}

	public List<DepartmentLookUp> getdeptList() {
		final List<DepartmentLookUp> deptList = CommonMasterUtility
				.getDepartmentForWS(UserSession.getCurrent().getOrganisation());
		return deptList;
	}

	public List<DesignationBean> getDesgnationList() {
		final List<DesignationBean> list = designationService.findAll();
		return list;
	}

	public List<TbDepartment> getMapDeptList() {
		final List<TbDepartment> mapDeptList = tbDepartmentService
				.findAllMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid());
		return mapDeptList;
	}

	public List<TbOrgDesignation> getMapDesignationList() {
		final List<TbOrgDesignation> mapDesignationList = tbOrgDesignationService
				.findAllByOrigId(UserSession.getCurrent().getOrganisation().getOrgid());
		return mapDesignationList;
	}

	public String getFormMode() {
		return formMode;
	}

	public void setFormMode(String formMode) {
		this.formMode = formMode;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public Long getTndVendorId() {
		return tndVendorId;
	}

	public void setTndVendorId(Long tndVendorId) {
		this.tndVendorId = tndVendorId;
	}

	@Override
	public boolean saveForm() {
		boolean result = false;
		final ContractMastDTO contractMastDTO = getContractMastDTO();
		
		if (contractMastDTO.getContractDetailList().get(0).getContToPeriodUnit() == null
				|| contractMastDTO.getContractDetailList().get(0).getContToPeriodUnit() == 0) {
			contractMastDTO.getContractDetailList().get(0)
					.setContToDate(Utility.getAddedDateBy(
							Utility.dateToString(contractMastDTO.getContractDetailList().get(0).getContFromDate()),
							contractMastDTO.getContractDetailList().get(0).getContToPeriod().intValue()));
		} else {
			contractMastDTO.getContractDetailList().get(0).setContToDate(
					getAgreeentToDate(contractMastDTO.getContractDetailList().get(0).getContToPeriodUnit(),
							contractMastDTO.getContractDetailList().get(0).getContFromDate(),
							contractMastDTO.getContractDetailList().get(0).getContToPeriod().intValue()));
		}
		
      //D76342
		if (getContMapFlag() != null && getContMapFlag().equals(MainetConstants.FlagB)) {
			setModeType(MainetConstants.FlagE);
		}
		if (validatePartyData()) {
			return false;
		} else {
			if (contractMastDTO.getContMode().equals(MainetConstants.CommonConstants.N)) {
				contractMastDTO.setContractInstalmentDetailList(null);
			}
			//#71812
			if(getTermsFlag() != null && getTermsFlag().equals(MainetConstants.FlagY)) {
				if(!contractMastDTO.getContractTermsDetailList().isEmpty()){
					StringBuffer stringBuffer=new StringBuffer();
					stringBuffer.append(contractMastDTO.getContractTermsDetailList().get(0).getConttDescription());
					stringBuffer.append("EnCrYpTed");
					contractMastDTO.getContractTermsDetailList().get(0).setConttDescription(stringBuffer.toString());
				}
			}
			deptShortCode=deptService.getDeptCode(contractMastDTO.getContDept());
			ContractMastDTO mastDTO = ContractAgreementService.saveContractAgreement(contractMastDTO,
					UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId(),
					UserSession.getCurrent().getEmployee().getEmpId(), getModeType(), UploadMap);
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && mastDTO.getContId() != 0) {
				prepareContractDocumentsData(mastDTO);
			}
			result = true;
		}
		return result;
	}

	public void getAllUploadedFile(final String guidRanDNum) {
		Long party1Count = 1l;
		Long party2Count = 11l;
		Long party2WCount = 111l;
		for (final ContractPart1DetailDTO part1 : getContractMastDTO().getContractPart1List()) {
			if (part1.getContp1Type().equals(MainetConstants.CommonConstants.U)) {
				if (part1.getContp1PhotoFilePathName() != null) {
					getDownloadFile(part1.getContp1PhotoFilePathName(), part1.getContp1PhotoFileName(), "0",
							guidRanDNum, MainetConstants.CommonConstants.U);
				}
				if (part1.getContp1ThumbFilePathName() != null) {
					getDownloadFile(part1.getContp1ThumbFilePathName(), part1.getContp1ThumbFileName(), "1",
							guidRanDNum, MainetConstants.CommonConstants.U);
				}
			} else if (part1.getContp1Type().equals(MainetConstants.PAYMODE.WEB)) {
				final String photoId = party1Count.toString() + "0";
				final String thumbId = party1Count.toString() + "1";
				if (part1.getContp1PhotoFilePathName() != null) {
					getDownloadFile(part1.getContp1PhotoFilePathName(), part1.getContp1PhotoFileName(), photoId,
							guidRanDNum, "UW");
				}
				if (part1.getContp1ThumbFilePathName() != null) {
					getDownloadFile(part1.getContp1ThumbFilePathName(), part1.getContp1ThumbFileName(), thumbId,
							guidRanDNum, "UW");
				}
				party1Count++;
			}
		}
		if (CollectionUtils.isNotEmpty(getContractMastDTO().getContractPart2List())) {
			for (final ContractPart2DetailDTO part2 : getContractMastDTO().getContractPart2List()) {
				if (StringUtils.isNotBlank(part2.getContp2Type())
						&& part2.getContp2Type().equals(MainetConstants.MODE_VIEW)) {
					final String photoId = party2Count.toString() + "0";
					final String thumbId = party2Count.toString() + "1";

					if (part2.getContp2PhotoFilePathName() != null) {
						getDownloadFile(part2.getContp2PhotoFilePathName(), part2.getContp2PhotoFileName(), photoId,
								guidRanDNum, "V");
					}
					if (part2.getContp2ThumbFilePathName() != null) {
						getDownloadFile(part2.getContp2ThumbFilePathName(), part2.getContp2ThumbFileName(), thumbId,
								guidRanDNum, "V");
					}
					party2Count++;
				} else if (StringUtils.isNotBlank(part2.getContp2Type())
						&& part2.getContp2Type().equals(MainetConstants.PAYMODE.WEB)) {
					final String photoId = party2WCount.toString() + "0";
					final String thumbId = party2WCount.toString() + "1";

					if (part2.getContp2PhotoFilePathName() != null) {
						getDownloadFile(part2.getContp2PhotoFilePathName(), part2.getContp2PhotoFileName(), photoId,
								guidRanDNum, "VW");
					}
					if (part2.getContp2ThumbFilePathName() != null) {
						getDownloadFile(part2.getContp2ThumbFilePathName(), part2.getContp2ThumbFileName(), thumbId,
								guidRanDNum, "VW");
					}
					party2WCount++;
				}
			}
		}

		FileUploadUtility.getCurrent()
				.setExistingFolderPath(Filepaths.getfilepath() + MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
						+ MainetConstants.FILE_PATH_SEPARATOR + guidRanDNum + MainetConstants.FILE_PATH_SEPARATOR);
	}

	public void getDownloadFile(final String path1, final String name, final String val, final String guidRan,
			final String uploadType) {
		final String uploadKey = val + uploadType;
		final Map<Long, Set<File>> fileMap = FileUploadUtility.getCurrent().getFileMap();
		if (FileUploadUtility.getCurrent().isFolderCreated()) {
			final String uidPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
					+ MainetConstants.FILE_PATH_SEPARATOR + guidRan + MainetConstants.FILE_PATH_SEPARATOR;
			FileUploadUtility.getCurrent().setExistingFolderPath(uidPath);
		}
		final String folderPath = FileUploadUtility.getCurrent().getExistingFolderPath();
		if(folderPath!=null) 
			Utility.downloadedFileUrl(path1 + MainetConstants.FILE_PATH_SEPARATOR + name, folderPath,
				FileNetApplicationClient.getInstance());

		final String path = Filepaths.getfilepath() + FileUploadUtility.getCurrent().getExistingFolderPath()
				+ MainetConstants.FILE_PATH_SEPARATOR + name;
		final File file = new File(path);
		UploadMap.put(uploadKey, file);
		boolean flag = true;

		for (final Map.Entry<Long, Set<File>> entry : fileMap.entrySet()) {
			if (entry.getKey().toString().equals(val)) {
				final Set<File> set = entry.getValue();
				set.add(file);
				fileMap.put(Long.valueOf(val), set);
				flag = false;
			}
		}
		if (flag) {
			final Set<File> fileDetails = new LinkedHashSet<>();
			fileDetails.add(file);
			fileMap.put(Long.valueOf(val), fileDetails);
		}
		FileUploadUtility.getCurrent().setFolderCreated(true);
	}

	public Map<Long, String> getCachePathUpload(final String uploadType) {
		uploadedfile.clear();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			Long count = 0l;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (((entry.getKey() != null) && (getPhotoId() != null) && (entry.getKey().longValue() == getPhotoId()))
						|| ((entry.getKey() != null) &&(getThumbId() != null) && (entry.getKey().longValue() == getThumbId()))) {
					if ((entry.getKey() != null) && (entry.getKey().longValue() == getThumbId())) {
						count = 1l;
					}
					for (final File file : entry.getValue()) {
						final String mapKey = entry.getKey().toString() + uploadType;
						UploadMap.put(mapKey, file);
						String fileName = null;
						final String path = file.getPath().replace("\\", "/");
						fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
						uploadedfile.put(count, fileName);
						count = 0l;
					}
				}
			}
		}
		return uploadedfile;
	}

	public void deleteUploadedFile(final Long photoId, final Long thumbId) {
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			FileUploadUtility.getCurrent().getFileMap().remove(photoId);
			FileUploadUtility.getCurrent().getFileMap().remove(thumbId);
			UploadMap.remove(photoId + getUploadType());
			UploadMap.remove(thumbId + getUploadType());
		}
	}

	public Map<Long, String> deleteSingleUpload(final Long photoId, final Long id) {
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			FileUploadUtility.getCurrent().getFileMap().remove(photoId);
			uploadedfile.remove(id);
			UploadMap.remove(photoId + getUploadType());
		}
		return uploadedfile;
	}

	public void prepareContractDocumentsData(ContractMastDTO contractMastDTO) {
		RequestDTO requestDTO = new RequestDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgId);
		requestDTO.setStatus(MainetConstants.FlagA);
		requestDTO.setDepartmentName(
				tbDepartmentService.findDepartmentShortCodeByDeptId(contractMastDTO.getContDept(), orgId));
//		requestDTO.setIdfId(MainetConstants.CONTRACT + MainetConstants.DOUBLE_BACK_SLACE + contractMastDTO.getContId());
		requestDTO.setIdfId(MainetConstants.CONTRACT + MainetConstants.WINDOWS_SLASH+ contractMastDTO.getContId());
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
			ContractAgreementService.deleteContractDocFileById(enclosureRemoveById,
					UserSession.getCurrent().getEmployee().getEmpId());
	}

	public ContractMastDTO saveContractData(ContractMastDTO contractMastDTO) {

		contractMastDTO.getContractPart1List().clear();
		contractMastDTO.getContractPart2List().clear();
		if (contractMastDTO.getContractDetailList().get(0).getContToPeriodUnit() == null
				|| contractMastDTO.getContractDetailList().get(0).getContToPeriodUnit() == 0) {
			contractMastDTO.getContractDetailList().get(0)
					.setContToDate(Utility.getAddedDateBy(
							Utility.dateToString(contractMastDTO.getContractDetailList().get(0).getContFromDate()),
							contractMastDTO.getContractDetailList().get(0).getContToPeriod().intValue()));
		} else {
			contractMastDTO.getContractDetailList().get(0).setContToDate(
					getAgreeentToDate(contractMastDTO.getContractDetailList().get(0).getContToPeriodUnit(),
							contractMastDTO.getContractDetailList().get(0).getContFromDate(),
							contractMastDTO.getContractDetailList().get(0).getContToPeriod().intValue()));
		}
		if (contractMastDTO.getContractDetailList().get(0).getContractAmt() != null)
			contractMastDTO.getContractDetailList().get(0)
					.setContAmount(contractMastDTO.getContractDetailList().get(0).getContractAmt().doubleValue());
		validateBean(this, ContractAgreementFormValidator.class);
		if (hasValidationErrors()) {
			return null;
		} else {
			deptShortCode=deptService.getDeptCode(contractMastDTO.getContDept());
			ContractMastDTO mastDTO = ContractAgreementService.saveContractAgreement(contractMastDTO,
					UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId(),
					UserSession.getCurrent().getEmployee().getEmpId(), getModeType(), UploadMap);

			if (mastDTO.getContId() != 0) {
				prepareContractDocumentsData(mastDTO);
				if (getModeType().equals(MainetConstants.MODE_EDIT)) {
					ContractMastDTO masterDTO = ContractAgreementService.findById(mastDTO.getContId(),
							UserSession.getCurrent().getOrganisation().getOrgid());
					mastDTO.setContractPart1List(masterDTO.getContractPart1List());
					mastDTO.setContractPart2List(masterDTO.getContractPart2List());

				}
			}
			return mastDTO;
		}

	}

	public boolean validatePartyData() {
		boolean checkFlag = false;
		final ApplicationSession session = ApplicationSession.getInstance();
		// Commented For Works management Requirement of non-mandatory the Party1
		// Details and its Witnesses
		/*
		 * if ((getContractMastDTO().getContractPart1List().get(0).getDpDeptid() ==
		 * null) || (getContractMastDTO().getContractPart1List().get(0).getDpDeptid() ==
		 * 0)) {
		 * 
		 * addValidationError(session.getMessage("rnl.contract.ulb.dept")); checkFlag =
		 * true; } if ((getContractMastDTO().getContractPart1List().get(0).getDsgid() ==
		 * null) || (getContractMastDTO().getContractPart1List().get(0).getDsgid() ==
		 * 0)) { addValidationError(session.getMessage("rnl.contract.ulb.desgn"));
		 * checkFlag = true; } if
		 * ((getContractMastDTO().getContractPart1List().get(0).getEmpid() == null) ||
		 * (getContractMastDTO().getContractPart1List().get(0).getEmpid() == 0)) {
		 * addValidationError(session.getMessage("rnl.contract.ulb.repst.by"));
		 * checkFlag = true; }
		 */
		/*
		 * int k = 1; for (final ContractPart1DetailDTO contractPart1DetailDTO :
		 * getContractMastDTO().getContractPart1List()) {
		 * 
		 * if ((contractPart1DetailDTO.getContp1Type() != null) &&
		 * contractPart1DetailDTO.getContp1Type().equals("W")) { if
		 * ((contractPart1DetailDTO.getContp1Name() == null) ||
		 * contractPart1DetailDTO.getContp1Name().isEmpty()) {
		 * addValidationError(session.getMessage("rnl.contract.ulb.witness.name" + k));
		 * checkFlag = true; } if ((contractPart1DetailDTO.getContp1Address() == null)
		 * || contractPart1DetailDTO.getContp1Address().isEmpty()) {
		 * addValidationError(session.getMessage("rnl.contract.ulb.witness.add" + k));
		 * checkFlag = true; } if ((contractPart1DetailDTO.getContp1ProofIdNo() == null)
		 * || contractPart1DetailDTO.getContp1ProofIdNo().isEmpty()) {
		 * addValidationError(session.getMessage("rnl.contractulb.witness.proof" + k));
		 * checkFlag = true; } k++;
		 * 
		 * } }
		 */
		int i = 1;
		int j = 1;
		for (final ContractPart2DetailDTO contractPart2DetailDTO : getContractMastDTO().getContractPart2List()) {
			if ((contractPart2DetailDTO.getContp2Type() != null)
					&& contractPart2DetailDTO.getContp2Type().equals("V")) {
				if ((contractPart2DetailDTO.getContp2vType() == null)
						|| (contractPart2DetailDTO.getContp2vType() == 0)) {
					addValidationError(session.getMessage("rnl.contract.vender.type" + i));
					checkFlag = true;
				}
				if ((contractPart2DetailDTO.getVmVendorid() == null) || (contractPart2DetailDTO.getVmVendorid() == 0)) {
					addValidationError(session.getMessage("rnl.contract.vender.name" + i));
					checkFlag = true;
				}
				if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL) && !Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && !Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
				if ((contractPart2DetailDTO.getContp2Name() == null)
						|| contractPart2DetailDTO.getContp2Name().isEmpty()) {
					addValidationError(session.getMessage("rnl.contract.vender.repst.by" + i));
					checkFlag = true;
				}
			}
				i++;
			} else if ((contractPart2DetailDTO.getContp2Type() != null)
					&& contractPart2DetailDTO.getContp2Type().equals("W")) {
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)||Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)||Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
				/*if ((contractPart2DetailDTO.getContp2Name() == null)
						|| contractPart2DetailDTO.getContp2Name().isEmpty()) {
					addValidationError(session.getMessage("rnl.contractvender.witness.name" + j));
					checkFlag = true;
				}

				if ((contractPart2DetailDTO.getContp2Address() == null)
						|| contractPart2DetailDTO.getContp2Address().isEmpty()) {
					addValidationError(session.getMessage("rnl.contract.vender.witness.add" + j));
					checkFlag = true;
				}
				if ((contractPart2DetailDTO.getContp2ProofIdNo() == null)
						|| contractPart2DetailDTO.getContp2ProofIdNo().isEmpty()) {
					addValidationError(session.getMessage("rnl.contract.vender.proof" + j));
				}*/
				}else {
					if ((contractPart2DetailDTO.getContp2Name() == null)
					|| contractPart2DetailDTO.getContp2Name().isEmpty()) {
				addValidationError(session.getMessage("rnl.contractvender.witness.name" + j));
				checkFlag = true;
			}

			if ((contractPart2DetailDTO.getContp2Address() == null)
					|| contractPart2DetailDTO.getContp2Address().isEmpty()) {
				addValidationError(session.getMessage("rnl.contract.vender.witness.add" + j));
				checkFlag = true;
			}
			if ((contractPart2DetailDTO.getContp2ProofIdNo() == null)
					|| contractPart2DetailDTO.getContp2ProofIdNo().isEmpty()) {
		 		addValidationError(session.getMessage("rnl.contract.vender.proof" + j));
			}
					
				}
				j++;

			}
		}

		return checkFlag;
	}

	public ContractMastDTO getContractMastDTO() {
		return contractMastDTO;
	}

	public void setContractMastDTO(final ContractMastDTO contractMastDTO) {
		this.contractMastDTO = contractMastDTO;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(final Long photoId) {
		this.photoId = photoId;
	}

	public Long getThumbId() {
		return thumbId;
	}

	public void setThumbId(final Long thumbId) {
		this.thumbId = thumbId;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(final String modeType) {
		this.modeType = modeType;
	}

	public Map<Long, String> getUploadedfile() {
		return uploadedfile;
	}

	public void setUploadedfile(final Map<Long, String> uploadedfile) {
		this.uploadedfile = uploadedfile;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(final String uploadType) {
		this.uploadType = uploadType;
	}

	public Map<String, File> getUploadMap() {
		return UploadMap;
	}

	public void setUploadMap(final Map<String, File> uploadMap) {
		UploadMap = uploadMap;
	}

	public Long getViewuploadId() {
		return viewuploadId;
	}

	public void setViewuploadId(final Long viewuploadId) {
		this.viewuploadId = viewuploadId;
	}

	public String getShowForm() {
		return showForm;
	}

	public void setShowForm(final String showForm) {
		this.showForm = showForm;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public List<Long> getFileCountUpload() {
		return fileCountUpload;
	}

	public void setFileCountUpload(List<Long> fileCountUpload) {
		this.fileCountUpload = fileCountUpload;
	}

	public String getRemoveCommonFileById() {
		return removeCommonFileById;
	}

	public void setRemoveCommonFileById(String removeCommonFileById) {
		this.removeCommonFileById = removeCommonFileById;
	}

	public List<ContractAgreementSummaryDTO> getSummaryDTOList() {
		return summaryDTOList;
	}

	public void setSummaryDTOList(List<ContractAgreementSummaryDTO> summaryDTOList) {
		this.summaryDTOList = summaryDTOList;
	}
	
	
	

	// To get Date by adding entered unit
	// #78771
	private Date getAgreeentToDate(Long id, Date agreementFromDate, int num) {
		LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(id,
				UserSession.getCurrent().getOrganisation());
		Date date = new Date();
		if (lookUp.getLookUpCode().equals("D")) {
			date = Utility.getAddedDateBy2(agreementFromDate, num);
		} else if (lookUp.getLookUpCode().equals("H")) {
			date = getAddedHourBy(agreementFromDate, num);
		} else if (lookUp.getLookUpCode().equals("M")) {
			date = Utility.getAddedMonthsBy(agreementFromDate, num);
		} else if (lookUp.getLookUpCode().equals("Y")) {
			date = Utility.getAddedYearsBy(agreementFromDate, num);
		} else {
			date = agreementFromDate;
		}

		return date;
	}

	// Get the Agreement date by adding the hours
	// #78771
	private Date getAddedHourBy(final Date strDate, final int day) {

		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(strDate);

		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + day);

		return calendar.getTime();
	}

	public Long getEstateId() {
		return estateId;
	}

	public void setEstateId(Long estateId) {
		this.estateId = estateId;
	}

	public Long getPropId() {
		return propId;
	}

	public void setPropId(Long propId) {
		this.propId = propId;
	}

	public String getDeptShortCode() {
		return deptShortCode;
	}

	public void setDeptShortCode(String deptShortCode) {
		this.deptShortCode = deptShortCode;
	}

}
