package com.abm.mainet.mrm.ui.model;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.model.Filepaths;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.client.FileNetApplicationClient;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.mrm.dto.HusbandDTO;
import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.dto.MarriageResponse;
import com.abm.mainet.mrm.dto.WifeDTO;
import com.abm.mainet.mrm.dto.WitnessDetailsDTO;
import com.abm.mainet.mrm.service.IMarriageService;
import com.abm.mainet.mrm.ui.validator.MarriageValidator;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class MarriageRegistrationModel extends AbstractFormModel {

	private static final long serialVersionUID = 7013176894774579712L;
	private MarriageDTO marriageDTO = new MarriageDTO();
	private List<MarriageDTO> marriageDTOs = new ArrayList<MarriageDTO>();
	private WitnessDetailsDTO witnessDTO = new WitnessDetailsDTO();
	private String modeType;
	private String witnessModeType;
	private String checkListApplFlag;
	private String applicationChargeApplFlag;
	private String payableFlag;
	private double amountToPay;
	private String successFlag;
	private String paymentMode;
	private String approvalProcess = "N";
	private String status = "D";
	private String printBT;// click from summary Screen ,based on this hide BT
	private Double totalLoiAmount;
	private ServiceMaster serviceMaster = new ServiceMaster();

	private Map<Long, String> uploadedfile = new HashMap<>(0);
	private Long photoId;
	private Long thumbId;
	private String uploadType;
	private Map<String, File> UploadMap = new HashMap<>();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private List<DocumentDetailsVO> documentList = new ArrayList<>();// use at view page to display documents
	// for search filter at summary screens
	private List<HusbandDTO> husbandList = new ArrayList<>();
	private List<WifeDTO> wifeList = new ArrayList<>();
	private Boolean applicableENV;
	private String photoThumbDisp="N";
	private String conditionFlag = "N";

	@Autowired
	IMarriageService marriageService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;
	
	@Autowired
	ICommonBRMSService iCommonBRMSService;

	public MarriageDTO getMarriageDTO() {
		return marriageDTO;
	}

	public void setMarriageDTO(MarriageDTO marriageDTO) {
		this.marriageDTO = marriageDTO;
	}

	public List<MarriageDTO> getMarriageDTOs() {
		return marriageDTOs;
	}

	public void setMarriageDTOs(List<MarriageDTO> marriageDTOs) {
		this.marriageDTOs = marriageDTOs;
	}

	

	public WitnessDetailsDTO getWitnessDTO() {
		return witnessDTO;
	}

	public void setWitnessDTO(WitnessDetailsDTO witnessDTO) {
		this.witnessDTO = witnessDTO;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public String getWitnessModeType() {
		return witnessModeType;
	}

	public void setWitnessModeType(String witnessModeType) {
		this.witnessModeType = witnessModeType;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}
	

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public String getApplicationChargeApplFlag() {
		return applicationChargeApplFlag;
	}

	public void setApplicationChargeApplFlag(String applicationChargeApplFlag) {
		this.applicationChargeApplFlag = applicationChargeApplFlag;
	}

	public String getPayableFlag() {
		return payableFlag;
	}

	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getApprovalProcess() {
		return approvalProcess;
	}

	public void setApprovalProcess(String approvalProcess) {
		this.approvalProcess = approvalProcess;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrintBT() {
		return printBT;
	}

	public void setPrintBT(String printBT) {
		this.printBT = printBT;
	}

	public Double getTotalLoiAmount() {
		return totalLoiAmount;
	}

	public void setTotalLoiAmount(Double totalLoiAmount) {
		this.totalLoiAmount = totalLoiAmount;
	}

	public Map<Long, String> getUploadedfile() {
		return uploadedfile;
	}

	public void setUploadedfile(Map<Long, String> uploadedfile) {
		this.uploadedfile = uploadedfile;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public Long getThumbId() {
		return thumbId;
	}

	public void setThumbId(Long thumbId) {
		this.thumbId = thumbId;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<HusbandDTO> getHusbandList() {
		return husbandList;
	}

	public void setHusbandList(List<HusbandDTO> husbandList) {
		this.husbandList = husbandList;
	}

	public List<WifeDTO> getWifeList() {
		return wifeList;
	}

	public void setWifeList(List<WifeDTO> wifeList) {
		this.wifeList = wifeList;
	}
	
	

	public Boolean getApplicableENV() {
		return applicableENV;
	}

	public void setApplicableENV(Boolean applicableENV) {
		this.applicableENV = applicableENV;
	}
	
	

	public String getPhotoThumbDisp() {
		return photoThumbDisp;
	}

	public void setPhotoThumbDisp(String photoThumbDisp) {
		this.photoThumbDisp = photoThumbDisp;
	}
	
	

	public String getConditionFlag() {
		return conditionFlag;
	}

	public void setConditionFlag(String conditionFlag) {
		this.conditionFlag = conditionFlag;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public Map<String, File> getUploadMap() {
		return UploadMap;
	}

	public void setUploadMap(Map<String, File> uploadMap) {
		UploadMap = uploadMap;
	}

	public boolean validatePayment() {

		validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);

		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}
	
	// D#113261
    public boolean validateInputs() {
        validateBean(this, MarriageValidator.class);
        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

	public boolean saveForm(List<WitnessDetailsDTO> witnessDetailsDTOs) throws Exception {
		CommonChallanDTO offline = getOfflineDTO();
		boolean status = false;
		MarriageDTO marriageDTO = getMarriageDTO();
		List<DocumentDetailsVO> documents = getCheckList();
		documents = fileUpload.prepareFileUpload(documents);
		validateInputs(documents);
		if (!marriageDTO.isFree()) {
			validateBean(offline, CommonOfflineMasterValidator.class);
		}
		if (hasValidationErrors()) {
			return false;
		}

		marriageDTO.setDocumentList(documents);
		marriageDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		marriageDTO.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		String empMobNo=UserSession.getCurrent().getEmployee().getEmpmobno();
		marriageDTO.setUserId(StringUtils.isNotEmpty(empMobNo)?Long.parseLong(empMobNo):UserSession.getCurrent().getEmployee().getEmpId());
		marriageDTO.setStatus(getStatus());

		// save in TB_MRM_WITNESS_DET
		marriageDTO.setWitnessDetailsDTO(witnessDetailsDTOs);
		marriageDTO = marriageService.saveWitnessDetails(marriageDTO);

		if (marriageDTO != null && !marriageDTO.getErrorList().isEmpty()) {
			for (String error : marriageDTO.getErrorList()) {
				addValidationError(error);
				return false;
			}
		} else if (marriageDTO != null && marriageDTO.getApplicationId() != null
				&& !getStatus().equalsIgnoreCase("APPROVED")) {
			if (!marriageDTO.isFree()) {
				setAndSaveChallanDto(offline, marriageDTO);
			}
			
			setSuccessMessage(ApplicationSession.getInstance().getMessage("mrm.save.marriage.success", new Object[] {
                    String.valueOf(marriageDTO.getApplicationId())}));
			
			
			status = true;
		}
		return status;
	}

	public void setAndSaveChallanDto(CommonChallanDTO offline, MarriageDTO marriageDTO) {

		offline.setApplNo(marriageDTO.getApplicationId());
		offline.setAmountToPay(Double.toString(getAmountToPay()));
		offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setServiceId(marriageDTO.getServiceId());
		offline.setDeptId(marriageDTO.getDeptId());
		String aplicantName = marriageDTO.getApplicantName();
		offline.setApplicantName(aplicantName);
		offline.setMobileNumber(getMarriageDTO().getApplicantDetailDto().getMobileNo());
		offline.setEmailId(getMarriageDTO().getApplicantDetailDto().getEmailId());

		offline.setApplicantAddress(marriageDTO.getHusbandDTO().getFullAddrEng());
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());

		for (ChargeDetailDTO dto : getChargesInfo()) {
			offline.getFeeIds().put(dto.getChargeCode(), dto.getChargeAmount());
		}

		if (CollectionUtils.isNotEmpty(getCheckList())) {
			offline.setDocumentUploaded(true);
		} else {
			offline.setDocumentUploaded(false);
		}
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());

		if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals("Y")) {
			setPaymentMode("Online");
		}
		if ((offline.getOnlineOfflineCheck() != null) && offline.getOnlineOfflineCheck().equals("N")) {
			setPaymentMode(CommonMasterUtility.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(),
					UserSession.getCurrent().getOrganisation()).getLookUpDesc());
			offline = challanService.generateChallanNumber(offline);
			setOfflineDTO(offline);
		}

	}

	public boolean validateInputs(final List<DocumentDetailsVO> dto) {
		boolean flag = false;
		if ((dto != null) && !dto.isEmpty()) {
			for (final DocumentDetailsVO doc : dto) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.FlagY)) {
					if (doc.getDocumentByteCode() == null) {
						addValidationError(
								ApplicationSession.getInstance().getMessage("mrm.upload.mandatory.documents"));
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

	public Map<Long, String> getCachePathUpload(final String uploadType) {
		uploadedfile.clear();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			Long count = 0l;
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if ((getPhotoId() != null && (entry.getKey() != null) && (entry.getKey().longValue() == getPhotoId()))
						|| (getThumbId() != null && (entry.getKey() != null)
								&& (entry.getKey().longValue() == getThumbId()))) {
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

	public void getDownloadFile(final String path1, final String name, final String val, final String guidRan,
			final String uploadType) {
		final String uploadKey = val + uploadType;
		final Map<Long, Set<File>> fileMap = FileUploadUtility.getCurrent().getFileMap();
		final String uidPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
				+ guidRan + MainetConstants.FILE_PATH_SEPARATOR;
		FileUploadUtility.getCurrent().setExistingFolderPath(uidPath);

		final String folderPath = FileUploadUtility.getCurrent().getExistingFolderPath();
		if (folderPath != null)
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

	public void populateModel(MarriageRegistrationModel marriageModel, String mode, MarriageResponse marResponse) {
		// set applicableENV
        List<LookUp> envLookUpList = CommonMasterUtility.getLookUps(MainetConstants.ENV,
                UserSession.getCurrent().getOrganisation());
        Boolean envPresent = envLookUpList.stream().anyMatch(
                env -> env.getLookUpCode().equals(MainetConstants.APP_NAME.SKDCL)
                        && StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
        if (envPresent) {
            marriageModel.setApplicableENV(true);
        } else {
            marriageModel.setApplicableENV(false);
        }
		marriageModel.getMarriageDTO().setServiceId(marriageModel.getServiceMaster().getSmServiceId());
		if (mode.equals("C")) {
			marriageModel.setMarriageDTO(new MarriageDTO());
			marriageModel.setModeType("C");
		} else {
			marriageDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			marriageModel.setMarriageDTO(marResponse.getMarriageDTO());
			if (mode.equals("V")) {
				marriageModel.setModeType("V");
			} else {
				marriageModel.setModeType(MainetConstants.MRM.STATUS.FORM_STATUS_DRAFT);
			}
			ApplicantDetailDTO applicantDetail = new ApplicantDetailDTO();
			if (marriageDTO.getApplicationId() != null) {
				applicantDetail = marResponse.getApplicantDetailDTO();
				marriageModel.getMarriageDTO().setApplicantName(
						applicantDetail.getApplicantFirstName() + " " + applicantDetail.getApplicantLastName());
			}
			// image fetch
			final String guidRanDNum = Utility.getGUIDNumber();
			final StringBuilder builder = new StringBuilder();
	         builder.append(marriageDTO.getOrgId()).append(File.separator).append("MARRIAGE")
	                 .append(File.separator).append(Utility.getTimestamp());
			if (StringUtils.isNotEmpty(marriageDTO.getHusbandDTO().getCapturePhotoPath())) {
				getDownloadFile(saveFileInPortal(marriageDTO.getHusbandDTO().getCapturePhotoName(),marriageDTO.getHusbandDTO().getCapturePhotoPath(),builder),
						marriageDTO.getHusbandDTO().getCapturePhotoName(), "90", guidRanDNum, "H");
			}

			if (StringUtils.isNotEmpty(marriageDTO.getHusbandDTO().getCaptureFingerprintPath())) {
				getDownloadFile(saveFileInPortal(marriageDTO.getHusbandDTO().getCaptureFingerprintName(),marriageDTO.getHusbandDTO().getCaptureFingerprintPath(),builder),
						marriageDTO.getHusbandDTO().getCaptureFingerprintName(), "91", guidRanDNum, "H");
			}

			if (StringUtils.isNotEmpty(marriageDTO.getWifeDTO().getCapturePhotoPath())) {
				getDownloadFile(saveFileInPortal(marriageDTO.getWifeDTO().getCapturePhotoName(),marriageDTO.getWifeDTO().getCapturePhotoPath(),builder),
						marriageDTO.getWifeDTO().getCapturePhotoName(), "990", guidRanDNum, "W");
			}

			if (StringUtils.isNotEmpty(marriageDTO.getWifeDTO().getCaptureFingerprintPath())) {
				getDownloadFile(saveFileInPortal(marriageDTO.getWifeDTO().getCaptureFingerprintName(),marriageDTO.getWifeDTO().getCaptureFingerprintPath(),builder),
						marriageDTO.getWifeDTO().getCaptureFingerprintName(), "991", guidRanDNum, "W");
			}

			
			List<DocumentDetailsVO> checkList = iCommonBRMSService.getChecklistDocument(marriageModel.getMarriageDTO().getApplicationId().toString(), UserSession.getCurrent().getOrganisation().getOrgid(), "Y");
			
            //D#130053 set only approve document start
            List<DocumentDetailsVO> filterDocList = checkList.stream()
                    .filter(doc -> doc.getClmAprStatus() != null && doc.getClmAprStatus().equalsIgnoreCase(MainetConstants.FlagY))
                    .collect(Collectors.toList());
            if (filterDocList.isEmpty()) {
            	marriageModel.setDocumentList(checkList);
            } else {
                marriageModel.setDocumentList(filterDocList);
            }
            //D#130053 set only approve document end
			
			marriageModel.getMarriageDTO().setApplicantDetailDto(applicantDetail);
		}
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {

		MarriageDTO marriageDTO = getMarriageDTO();
		PortalService portalServiceMaster = null;
		try {
			portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
		} catch (Exception e) {
			System.out.println("catch service master issue");
		}

		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf7(String.valueOf(marriageDTO.getApplicationId()));
		payURequestDTO.setApplicantName(marriageDTO.getApplicantName());
		payURequestDTO.setServiceId(marriageDTO.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(marriageDTO.getApplicationId()));
		payURequestDTO.setMobNo(marriageDTO.getApplicantDetailDto().getMobileNo());
		payURequestDTO.setDueAmt(new BigDecimal(getAmountToPay()));
		payURequestDTO.setEmail(marriageDTO.getApplicantDetailDto().getEmailId());
		payURequestDTO.setApplicationId(String.valueOf(marriageDTO.getApplicationId()));
		payURequestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		// Adding department Id in udf10 of payURequestDTO object
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			payURequestDTO.setUdf5(portalServiceMaster.getShortName());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
	}
	
	//method for save file at portal path file
	private String saveFileInPortal(String docName,String docByteCode,StringBuilder builder) {
		String physicalPath = ApplicationSession.getInstance().getMessage("upload.physicalPath");
		DocumentDetailsVO attachments = new DocumentDetailsVO();
		attachments.setDocumentName(docName);
		attachments.setDocumentByteCode(docByteCode);
		String dirPath = null;
		 
         dirPath = builder.toString();
		fileUpload.convertAndSaveFile(attachments, physicalPath,dirPath,docName);
		return dirPath;
		
	}

}
