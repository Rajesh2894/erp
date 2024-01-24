package com.abm.mainet.additionalservices.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.additionalservices.dto.NOCforOtherGovtDeptDto;
import com.abm.mainet.additionalservices.dto.NursingHomeSummaryDto;
import com.abm.mainet.additionalservices.service.NOCForOtherGovtDeptService;
import com.abm.mainet.additionalservices.ui.validator.CFCNOCFormValidator;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonAcknowledgementDto;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class NOCForOtherGovtDeptModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6635945054121457481L;

	private List<TbServicesMst> tbServicesMsts;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private NOCForOtherGovtDeptService nocForOtherGovtDeptService;
	
	@Autowired
	private ServiceMasterService serviceMasterService;

	private List<TbDepartment> tbDepartments;

	private List<Long> appIds;
	private List<NursingHomeSummaryDto> homeSummaryDtos;
	private String flag;
	private List<CFCAttachment> documentList = new ArrayList<>();
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private Map<Long, String> uploadedfile = new HashMap<>(0);
	private Map<String, File> UploadMap = new HashMap<>();
	private Map<Long, List<String>> fileNames = new HashMap<>();
	private String checkListApplFlag = null;
	private TbCfcApplicationMst cfcApplicationMst;

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private CFCApplicationAddressEntity cfcApplicationAddressEntity;
	
	CommonAcknowledgementDto ackDto = new CommonAcknowledgementDto();

	private String paymentCheck = null;

	private String viewMode;
	private String hideshowAddBtn;
	private String hideshowDeleteBtn;
	private String temporaryDateHide;
	private String saveMode;
	private String downloadMode;
	private String fileMode;
	private String openMode;
	private Long serviceId;
	private Long appId;
	private String refId;
	private List<String> refIds;
	private Long applicationId;
	private String applicantName;


	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public List<String> getRefIds() {
		return refIds;
	}

	public void setRefIds(List<String> refIds) {
		this.refIds = refIds;
	}

	private NOCforOtherGovtDeptDto noCforOtherGovtDeptDto;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public NOCforOtherGovtDeptDto getNoCforOtherGovtDeptDto() {
		return noCforOtherGovtDeptDto;
	}

	public void setNoCforOtherGovtDeptDto(NOCforOtherGovtDeptDto noCforOtherGovtDeptDto) {
		this.noCforOtherGovtDeptDto = noCforOtherGovtDeptDto;
	}
	
	private ServiceMaster serviceMaster = new ServiceMaster();

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}
	
	public CommonAcknowledgementDto getAckDto() {
		return ackDto;
	}

	public void setAckDto(CommonAcknowledgementDto ackDto) {
		this.ackDto = ackDto;
	}

	public boolean validateInputs() {
		validateBean(this, CFCNOCFormValidator.class);

		if (getServiceMaster().getSmAppliChargeFlag().equals("Y")) {
			validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		}

		if (hasValidationErrors()) {
			// this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
		return true;
	}

	@Override
	public boolean saveForm() throws FrameworkException {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
		Date newDate = new Date();
		Long langId = (long) UserSession.getCurrent().getLanguageId();

		List<DocumentDetailsVO> ownerDocs = getNoCforOtherGovtDeptDto().getAttachments();

		if (ownerDocs != null && !ownerDocs.isEmpty()) {
			try {
				ownerDocs = prepareFileUploadForImg(ownerDocs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		getNoCforOtherGovtDeptDto().setAttachments(ownerDocs);

		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS",
				UserSession.getCurrent().getOrganisation());

		CFCApplicationAddressEntity addressEntity = getCfcApplicationAddressEntity();
		addressEntity.setApaZoneNo(getNoCforOtherGovtDeptDto().getCfcWard1());
		addressEntity.setApaWardNo(getNoCforOtherGovtDeptDto().getCfcWard2());
		addressEntity.setOrgId(UserSession.getCurrent().getOrganisation());
		addressEntity.setLgIpMac(lgIp);
		addressEntity.setLangId(langId);
		addressEntity.setUserId(createdBy);

		TbCfcApplicationMst cfcApplicationMst = getCfcApplicationMst();
		cfcApplicationMst.setOrgid(orgId);
		cfcApplicationMst.setLangId(langId);
		cfcApplicationMst.setUserId(createdBy);
		cfcApplicationMst.setLgIpMac(lgIp);

		String refId = nocForOtherGovtDeptService.saveApplicantData(getNoCforOtherGovtDeptDto(), addressEntity,
				cfcApplicationMst, this);
		setSuccessMessage(getAppSession().getMessage("CFC.application.successMsg") + refId);
		//#137105
		String url = "NOCForOtherGovtDept.html";
		sendSmsEmail(this, url, PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED);
		return true;
	}

	//#137105
	private void sendSmsEmail(NOCForOtherGovtDeptModel model, String url, String msgType) {

		SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
		Organisation org = UserSession.getCurrent().getOrganisation();
		int langId = UserSession.getCurrent().getLanguageId();
		ServiceMaster sm = serviceMasterService.getServiceByShortName(MainetConstants.CFCServiceCode.NOC_For_Other_Govt_Dept,
				org.getOrgid());
		smsDto.setOrgId(org.getOrgid());
		smsDto.setLangId(langId);
		if (model.getCfcApplicationMst().getApmApplicationId() != null)
		smsDto.setAppNo(model.getCfcApplicationMst().getApmApplicationId().toString());
		if (langId == 1 && sm != null && StringUtils.isNotEmpty(sm.getSmServiceName()))
		 smsDto.setServName(sm.getSmServiceName());
		else
		if (sm != null && StringUtils.isNotEmpty(sm.getSmServiceNameMar()))
		smsDto.setServName(sm.getSmServiceNameMar());
		smsDto.setMobnumber(model.getCfcApplicationAddressEntity().getApaMobilno());
		String fullName = String.join(MainetConstants.WHITE_SPACE,
				Arrays.asList(model.getCfcApplicationMst().getApmFname(), model.getCfcApplicationMst().getApmMname(),
						model.getCfcApplicationMst().getApmLname()));
		smsDto.setAppName(fullName);
		smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		if (StringUtils.isNotBlank(model.getCfcApplicationAddressEntity().getApaEmail())) {
			smsDto.setEmail(model.getCfcApplicationAddressEntity().getApaEmail());
		}
		ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
				MainetConstants.DEPT_SHORT_NAME.CFC_CENTER, url, msgType, smsDto, org, langId);

	}

	public void getdataOfUploadedImage() {
		getFileNames().clear();
		List<String> fileNameList = null;
		Long count = 0L;
		Map<Long, List<String>> fileNames = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				fileNameList = new ArrayList<>();
				for (final File file : entry.getValue()) {
					String fileName = null;

					try {
						final String path = file.getPath().replace(MainetConstants.DOUBLE_BACK_SLACE,
								MainetConstants.operator.FORWARD_SLACE);
						fileName = path.replace(Filepaths.getfilepath(), StringUtils.EMPTY);
					} catch (final Exception e) {
						e.printStackTrace();
					}

					fileNameList.add(fileName);
				}
				fileNames.put(count, fileNameList);
				count++;
			}
		}
		setFileNames(fileNames);
	}

	public List<DocumentDetailsVO> prepareFileUploadForImg(List<DocumentDetailsVO> img) throws IOException {

		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
						// LOGGER.error("Exception has been occurred in file byte to string
						// conversions", e);
					}
				}
			}
		}
		if (img != null && !img.isEmpty() && !listOfString.isEmpty()) {
			long count = 100;
			for (final DocumentDetailsVO d : img) {
				if (d.getDocumentSerialNo() != null) {
					count = d.getDocumentSerialNo() - 1;

				}
				if (listOfString.containsKey(count) && fileName.containsKey(count)) {
					d.setDocumentByteCode(listOfString.get(count));
					d.setDocumentName(fileName.get(count));
				}
				count++;
			}
		}

		return img;
	}

	@SuppressWarnings("unchecked")
	public void getCheckListFromBrms(String smShortDesc) {

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName(MainetConstants.TradeLicense.CHECK_LIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
			checkListModel2.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			checkListModel2.setServiceCode(smShortDesc);

			/*
			 * List<LookUp> lookupListLevel1 = new ArrayList<LookUp>(); lookupListLevel1 =
			 * CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1,
			 * UserSession.getCurrent().getOrganisation().getOrgid());
			 */

			/*
			 * List<LookUp> level1 = lookupListLevel1.parallelStream() .filter(clList ->
			 * clList != null && clList.getLookUpId() ==
			 * tradeLicenseItemDetailDTO.getTriCod1()) .collect(Collectors.toList());
			 */

			// String categoryShortCode = level1.get(0).getLookUpCode();

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
				} else {
					addValidationError(ApplicationSession.getInstance().getMessage("No CheckList Found"));

				}
			}
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

	public List<NursingHomeSummaryDto> getHomeSummaryDtos() {
		return homeSummaryDtos;
	}

	public void setHomeSummaryDtos(List<NursingHomeSummaryDto> homeSummaryDtos) {
		this.homeSummaryDtos = homeSummaryDtos;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public Map<Long, String> getUploadedfile() {
		return uploadedfile;
	}

	public void setUploadedfile(Map<Long, String> uploadedfile) {
		this.uploadedfile = uploadedfile;
	}

	public Map<String, File> getUploadMap() {
		return UploadMap;
	}

	public void setUploadMap(Map<String, File> uploadMap) {
		UploadMap = uploadMap;
	}

	public Map<Long, List<String>> getFileNames() {
		return fileNames;
	}

	public void setFileNames(Map<Long, List<String>> fileNames) {
		this.fileNames = fileNames;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public TbCfcApplicationMst getCfcApplicationMst() {
		return cfcApplicationMst;
	}

	public void setCfcApplicationMst(TbCfcApplicationMst cfcApplicationMst) {
		this.cfcApplicationMst = cfcApplicationMst;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public CFCApplicationAddressEntity getCfcApplicationAddressEntity() {
		return cfcApplicationAddressEntity;
	}

	public void setCfcApplicationAddressEntity(CFCApplicationAddressEntity cfcApplicationAddressEntity) {
		this.cfcApplicationAddressEntity = cfcApplicationAddressEntity;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getHideshowAddBtn() {
		return hideshowAddBtn;
	}

	public void setHideshowAddBtn(String hideshowAddBtn) {
		this.hideshowAddBtn = hideshowAddBtn;
	}

	public String getHideshowDeleteBtn() {
		return hideshowDeleteBtn;
	}

	public void setHideshowDeleteBtn(String hideshowDeleteBtn) {
		this.hideshowDeleteBtn = hideshowDeleteBtn;
	}

	public String getTemporaryDateHide() {
		return temporaryDateHide;
	}

	public void setTemporaryDateHide(String temporaryDateHide) {
		this.temporaryDateHide = temporaryDateHide;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getDownloadMode() {
		return downloadMode;
	}

	public void setDownloadMode(String downloadMode) {
		this.downloadMode = downloadMode;
	}

	public String getFileMode() {
		return fileMode;
	}

	public void setFileMode(String fileMode) {
		this.fileMode = fileMode;
	}

	public String getOpenMode() {
		return openMode;
	}

	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}

	public List<Long> getAppIds() {
		return appIds;
	}

	public void setAppIds(List<Long> appIds) {
		this.appIds = appIds;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public List<TbServicesMst> getTbServicesMsts() {
		return tbServicesMsts;
	}

	public void setTbServicesMsts(List<TbServicesMst> tbServicesMsts) {
		this.tbServicesMsts = tbServicesMsts;
	}

	public List<TbDepartment> getTbDepartments() {
		return tbDepartments;
	}

	public void setTbDepartments(List<TbDepartment> tbDepartments) {
		this.tbDepartments = tbDepartments;
	}
	
	public List<CFCAttachment> preparePreviewOfFileUpload(final List<CFCAttachment> downloadDocs,
            List<DocumentDetailsVO> docs) {
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            long count = 1;
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    try {
                        CFCAttachment c = new CFCAttachment();
                        String path = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
                                file.getPath());
                        c.setAttPath(path);
                        c.setAttFname(file.getName());
                        c.setClmSrNo(count);
                        docs.stream().filter(doc -> doc.getDocumentSerialNo().equals(entry.getKey() + 1))
                                .forEach(doc -> {
                                    c.setClmDesc(doc.getDoc_DESC_ENGL());
                                    c.setClmId(doc.getDocumentId());
                                    c.setClmDesc(doc.getDoc_DESC_Mar());
                                    c.setClmDesc(doc.getDoc_DESC_ENGL());

                                });
                        count++;
                        downloadDocs.add(c);
                    } catch (final Exception e) {
                        // LOGGER.error("Exception has been occurred in file byte to string
                        // conversions", e);
                    }
                }
            }
        }

        return downloadDocs;
    }


}
