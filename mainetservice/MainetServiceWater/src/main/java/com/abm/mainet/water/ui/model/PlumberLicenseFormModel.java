/**
 * 
 */
package com.abm.mainet.water.ui.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.cfc.loi.ui.model.LoiGenerationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.domain.PlumberRenewalRegisterMaster;
import com.abm.mainet.water.dto.PlumberExperienceDTO;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;
import com.abm.mainet.water.dto.PlumberQualificationDTO;
import com.abm.mainet.water.service.BRMSWaterService;
import com.abm.mainet.water.service.PlumberLicenseService;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Component
@Scope("session")
public class PlumberLicenseFormModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = -6509117877250473347L;
    @Autowired
    private ICFCApplicationMasterService icfcApplicationMasterService;
    @Resource
    private PlumberLicenseService plumberLicenseService;

    @Autowired
    private ICFCApplicationMasterService cfcService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private BRMSWaterService brmsWaterService;

    @Resource
    private IChallanService iChallanService;

    @Resource
    private ServiceMasterService service;

    @Autowired
    private TbLoiMasService iTbLoiMasService;
    @Resource
    private IFileUploadService fileUploadService;

    private List<CFCAttachment> documentList = null;
    private PlumberLicenseRequestDTO plumberLicenseReqDTO = new PlumberLicenseRequestDTO();
    private CFCApplicationAddressEntity cfcAddressEntity;
    private TbCfcApplicationMstEntity cfcEntity;
    private boolean scrutinyApplicable;
    private ApplicantDetailDTO applicantDetailDto;
    private List<PlumberQualificationDTO> plumberQualificationDTOList = new ArrayList<>();
    private List<PlumberExperienceDTO> plumberExperienceDTOList = new ArrayList<>();
    private PlumberMaster plumberMaster;
    private Long applicationId;
    private Long serviceId;
    private Long plumberId;
    private String fileDownLoadPath;
    private String interviewRemark;
    private String interviewDateTime;
    private Date interviewDateTimeD;
    private String plumQualDeletedRow;
    private String plumExpDeletedRow;
    private String totalExp;
    private Map<Long, Double> chargesMap = new HashMap<>();
    private String checkListNCharges;
    private List<DocumentDetailsVO> checkList;
    private double amountToPay;
    private String isFree;
    private String plumberImage;
    private boolean fromEvent;
    private String serviceCode;
    private TbLoiMas entity = new TbLoiMas();
    private String loiId;
    private Double total = new Double(0d);
    private boolean finalProcess;
    private String payableFlag;
    private String smScrutinyChargeFlag;
    private List<TbLoiDet> loiDetail = new ArrayList<>();
    private Double totalLoiAmount;
    /** Fail Message. */
    private static final String FAIL = "water.PlumberLicense.fail";

    /** Success Message. */
    private static final String SUCCESS = "water.PlumberLicense.success";

    private List<LookUp> lookUpList = new ArrayList<>(0);

    private Long deptId;

    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    
    private String serviceName;
    
    private String deptName;
    
    private String plumLicenseType;


	/**
     * This method is used for view Application Details on Scrutiny level
     */
    @Override
    public void populateApplicationData(final long applicationId) {
        if (null != plumQualDeletedRow) {
            plumQualDeletedRow.isEmpty();
        }
        if (null != plumExpDeletedRow) {
            plumExpDeletedRow.isEmpty();
        }
        setApplicationId(applicationId);
        plumberLicenseReqDTO = getPlumberLicenseReqDTO();
        if (plumberLicenseReqDTO == null) {
            plumberLicenseReqDTO = new PlumberLicenseRequestDTO();
        }
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        applicantDetailDto = new ApplicantDetailDTO();
        cfcEntity = icfcApplicationMasterService.getCFCApplicationByApplicationId(applicationId, orgId);
        if (null != cfcEntity) {
            setServiceId(cfcEntity.getTbServicesMst().getSmServiceId());
            applicantDetailDto.setGender(Utility.getGenderId(cfcEntity.getApmSex()));
        }
		if (StringUtils.contains(getServiceCode(), "WPL")) {

			setPlumberMaster(plumberLicenseService.getPlumberDetailsByAppId(applicationId, orgId));
		}
        
        else if (StringUtils.contains(getServiceCode(), "PLR") && isFromEvent())
            setPlumberMaster(plumberLicenseService.getPlumberDetailsByPlumId(plumberLicenseService.getPlumMasId(applicationId),
                    orgId));
        else if (StringUtils.contains(getServiceCode(), "DPL"))
            setPlumberMaster(plumberLicenseService.getPlumberDetailsByAppId(plumberLicenseService
                    .getPlumberDetailsByLicenseNumber(orgId, cfcEntity.getRefNo()).getApplicationId(), orgId));
        else {

        	PlumberRenewalRegisterMaster plumberLicenceRenewalDetailsByAppId = plumberLicenseService.
            		getPlumberLicenceRenewalDetailsByAppId(applicationId, orgId);
            if(plumberLicenceRenewalDetailsByAppId!=null) {
            	PlumberMaster plumberDetailsByPlumId = plumberLicenseService.getPlumberDetailsByPlumId(
            			plumberLicenceRenewalDetailsByAppId.getPlum_id(), orgId);
            	setPlumberMaster(plumberDetailsByPlumId);
            }
        }
        if (null != plumberMaster) {
            plumberId = plumberMaster.getPlumId();
            plumberLicenseReqDTO.setPlumberId(plumberId);
            if (plumberMaster.getPlumLicFromDate() != null)
                plumberLicenseReqDTO.setPlumLicFromDate(plumberMaster.getPlumLicFromDate());
            if (plumberMaster.getPlumLicToDate() != null)
                plumberLicenseReqDTO.setPlumLicToDate(plumberMaster.getPlumLicToDate());
            if (plumberMaster.getPlumLicNo() != null)
                plumberLicenseReqDTO.setPlumberLicenceNo(plumberMaster.getPlumLicNo());
            setInterviewDateTimeD(plumberMaster.getPlumInterviewDate());
            setInterviewRemark(plumberMaster.getPlumInterviewRemark());
            if (plumberMaster.getPlumLicNo() != null && !plumberMaster.getPlumLicNo().isEmpty())
                plumberLicenseReqDTO.setPlumberLicenceNo(plumberMaster.getPlumLicNo());
            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
                    + MainetConstants.FILE_PATH_SEPARATOR + "SHOW_DOCS";
            setFileDownLoadPath(
                    Utility.downloadedFileUrl(plumberMaster.getPlumImagePath() + MainetConstants.FILE_PATH_SEPARATOR
                            + plumberMaster.getPlumImage(), outputPath, getFileNetClient()));
            setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
        }

        ApplicantDetailDTO dto = getApplicantDetailDto();
        if (dto == null) {
            dto = new ApplicantDetailDTO();
        }
        applicantDetailDto = initializeApplicantAddressDetail(initializeApplicationDetail(dto, cfcEntity),
                cfcService.getApplicantsDetails(applicationId));
        plumberLicenseReqDTO.setApplicant(applicantDetailDto);
        setApplicantDetailDto(applicantDetailDto);
        final List<PlumberQualificationDTO> plumberQualificationDTOs = plumberLicenseService
                .getPlumberQualificationDetails(plumberId, orgId);
        plumberLicenseReqDTO.setPlumberQualificationDTOList(plumberQualificationDTOs);
        setPlumberQualificationDTOList(plumberQualificationDTOs);
        final List<PlumberExperienceDTO> plumberExperienceDTOs = plumberLicenseService
                .getPlumberExperienceDetails(plumberId, orgId);
        plumberLicenseReqDTO.setPlumberExperienceDTOList(plumberExperienceDTOs);
        setPlumberExperienceDTOList(plumberExperienceDTOs);
        BigInteger sumYear = BigInteger.ZERO;
        BigInteger sumMonth = BigInteger.ZERO;
        BigInteger totalMonth = BigInteger.ZERO;
        if ((plumberExperienceDTOs != null) && !plumberExperienceDTOs.isEmpty()) {
            for (final PlumberExperienceDTO plumberExperienceDTO : plumberExperienceDTOs) {
                final Long year = plumberExperienceDTO.getPlumExpYear();
                final Long month = plumberExperienceDTO.getPlumExpMonth();
                if (year != null) {
                    sumYear = sumYear.add(BigInteger.valueOf(year));
                }
                if (month != null) {
                    sumMonth = sumMonth.add(BigInteger.valueOf(month));
                }
            }
        }
        String userName = (cfcEntity.getApmFname() == null ? MainetConstants.BLANK
                : cfcEntity.getApmFname() + MainetConstants.WHITE_SPACE);
        userName += cfcEntity.getApmMname() == null ? MainetConstants.BLANK
                : cfcEntity.getApmMname() + MainetConstants.WHITE_SPACE;
        userName += cfcEntity.getApmLname() == null ? MainetConstants.BLANK : cfcEntity.getApmLname();
        plumberLicenseReqDTO.setPlumberFullName(userName);
        final BigInteger yearToMonth = sumYear.multiply(BigInteger.valueOf(12));
        totalMonth = yearToMonth.add(sumMonth);
        final BigInteger divide = totalMonth.divide(BigInteger.valueOf(12));
        final BigInteger remainder = totalMonth.mod(BigInteger.valueOf(12));
        final String exp = divide.toString() + MainetConstants.operator.DOT + remainder.toString();
        setTotalExp(exp);
    }

    /**
     * This method is used for updated Application details on Scrutiny level
     */
    @Override
    public boolean saveForm() {
        setPlumberLicenseData();
        List<DocumentDetailsVO> docs = getCheckList();
        docs = setFileUploadMethod(docs);
        validateInputs(docs, getPlumberLicenseReqDTO().getPlumberImage());
        if (hasValidationErrors()) {
            return false;
        }
        getPlumberLicenseReqDTO().setDocumentList(docs);
        getPlumberLicenseReqDTO().setPlumberExperienceDTOList(getPlumberExperienceDTOList());
        PlumberLicenseResponseDTO outPutObject = plumberLicenseService
                .savePlumberLicenseDetails(getPlumberLicenseReqDTO());
        if (outPutObject != null && (outPutObject.getStatus() != null)
                && outPutObject.getStatus().equals(PrefixConstants.NewWaterServiceConstants.SUCCESS)) {

            if (PrefixConstants.NewWaterServiceConstants.SUCCESS.equals(outPutObject.getStatus())) { // free
                plumberLicenseService.initiateWorkFlowForFreeService(getPlumberLicenseReqDTO());
            }
            if ((getIsFree() != null) && getIsFree().equals(PrefixConstants.NewWaterServiceConstants.NO)) {
                save();

            }
            setSuccessMessage(
                    getAppSession().getMessage(SUCCESS, new Object[] { outPutObject.getApplicationId().toString() }));

        } else {
            setSuccessMessage(getAppSession().getMessage(FAIL));
        }
        return true;
    }

    private ApplicantDetailDTO initializeApplicantAddressDetail(final ApplicantDetailDTO dto,
            final CFCApplicationAddressEntity addressEntity) {
        final StringBuilder builder = new StringBuilder();
        dto.setMobileNo(addressEntity.getApaMobilno());
        dto.setEmailId(addressEntity.getApaEmail());
        dto.setFloorNo(addressEntity.getApaFloor());
        dto.setBuildingName(addressEntity.getApaBldgnm());
        dto.setRoadName(addressEntity.getApaRoadnm());
        dto.setAreaName(addressEntity.getApaAreanm());
        dto.setVillageTownSub(addressEntity.getApaCityName());
        dto.setBlockName(addressEntity.getApaBlockName());
        if (addressEntity.getApaPincode() != null) {
            dto.setPinCode(Long.toString(addressEntity.getApaPincode()));
        }
        dto.setDwzid1(addressEntity.getApaZoneNo());
        dto.setDwzid2(addressEntity.getApaWardNo());
        if(addressEntity.getApaBlockno() != null) {
        	 dto.setDwzid3(Long.valueOf(addressEntity.getApaBlockno()));
        }
        if ((null != addressEntity.getApaBldgnm()) && !addressEntity.getApaBldgnm().isEmpty()) {
            builder.append(addressEntity.getApaBldgnm());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if ((null != addressEntity.getApaFlatBuildingNo()) && !addressEntity.getApaFlatBuildingNo().isEmpty()) {
            builder.append(addressEntity.getApaFlatBuildingNo());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if ((null != addressEntity.getApaRoadnm()) && !addressEntity.getApaRoadnm().isEmpty()) {
            builder.append(addressEntity.getApaRoadnm());
            builder.append(MainetConstants.BLANK);
        }
        if ((null != addressEntity.getApaAreanm()) && !addressEntity.getApaAreanm().isEmpty()) {
            builder.append(addressEntity.getApaAreanm());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if ((null != addressEntity.getApaCityName()) && !addressEntity.getApaCityName().isEmpty()) {
            builder.append(addressEntity.getApaCityName());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && null != addressEntity.getApaPincode()) {
            builder.append(addressEntity.getApaPincode());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        getPlumberLicenseReqDTO().setPlumAddress(builder.toString());

        return dto;
    }

    private ApplicantDetailDTO initializeApplicationDetail(final ApplicantDetailDTO dto,
            final TbCfcApplicationMstEntity entity) {
        dto.setApplicantTitle(entity.getApmTitle());
        dto.setApplicantFirstName(entity.getApmFname());
        dto.setApplicantMiddleName(entity.getApmMname());
        dto.setApplicantLastName(entity.getApmLname());
        if (entity.getApmBplNo() != null) {
            dto.setBplNo(entity.getApmBplNo());
            dto.setIsBPL(MainetConstants.PlumberLicense.YES);
        } else {
            dto.setIsBPL(MainetConstants.PlumberLicense.NO);
        }
        if (entity.getApmUID() != null) {
            dto.setAadharNo(entity.getApmUID().toString());
        }
        return dto;
    }

    public boolean save() {

        final CommonChallanDTO offline = getOfflineDTO();
        final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
        offline.setOfflinePaymentText(modeDesc);
        // getPlumberLicenseReqDTO().setPayMode(offline.getOnlineOfflineCheck());
        if (((offline.getOnlineOfflineCheck() != null)
                && offline.getOnlineOfflineCheck().equals(PrefixConstants.NewWaterServiceConstants.NO))
                || (getAmountToPay() > 0d)) {
            offline.setApplNo(getPlumberLicenseReqDTO().getApplicationId());
            offline.setAmountToPay(Double.toString(getAmountToPay()));
            offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            offline.setLangId(UserSession.getCurrent().getLanguageId());
            offline.setDeptId(getPlumberLicenseReqDTO().getDeptId());
            offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
            if ((getCheckList() != null) && (getCheckList().size() > 0)) {
                offline.setDocumentUploaded(true);
            } else {
                offline.setDocumentUploaded(false);
            }
            offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
            offline.setFaYearId(UserSession.getCurrent().getFinYearId());
            offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
            offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
            offline.setServiceId(getServiceId());
            String userName = (getApplicantDetailDto().getApplicantFirstName() == null ? MainetConstants.BLANK
                    : getApplicantDetailDto().getApplicantFirstName() + MainetConstants.WHITE_SPACE);
            userName += getApplicantDetailDto().getApplicantMiddleName() == null ? MainetConstants.BLANK
                    : getApplicantDetailDto().getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
            userName += getApplicantDetailDto().getApplicantLastName() == null ? MainetConstants.BLANK
                    : getApplicantDetailDto().getApplicantLastName();
            offline.setApplicantName(userName);
            offline.setMobileNumber(getApplicantDetailDto().getMobileNo());
            offline.setEmailId(getApplicantDetailDto().getEmailId());
            for (final Map.Entry<Long, Double> entry : getChargesMap().entrySet()) {
                offline.getFeeIds().put(entry.getKey(), entry.getValue());
            }
            offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
            offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
                    offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
            if ((offline.getOnlineOfflineCheck() != null)
                    && offline.getOnlineOfflineCheck().equals(PrefixConstants.NewWaterServiceConstants.NO)) {
                final ChallanMaster challanNumber = iChallanService.InvokeGenerateChallan(offline);
                offline.setChallanNo(challanNumber.getChallanNo());
                offline.setChallanValidDate(challanNumber.getChallanValiDate());
                setOfflineDTO(offline);
            } else if ((offline.getOnlineOfflineCheck() != null)
                    && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
                final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, getServiceName());
                setReceiptDTO(printDto);
                setSuccessMessage(getAppSession().getMessage("water.receipt"));
            }
        }
        return true;
    }

    /**
     * This method is used for validate User Details
     */
    public boolean validateInputs(final List<DocumentDetailsVO> dto, final String plumberPhoto) {
        boolean flag = false;
        if ((dto != null) && !dto.isEmpty()) {
            for (final DocumentDetailsVO doc : dto) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.FlagY)) {
                    if (doc.getDocumentByteCode() == null) {
                        addValidationError(ApplicationSession.getInstance()
                                .getMessage("water.plumberLicense.valMsg.uploadDocument"));
                        flag = true;
                        break;
                    }
                }
            }

        }

        if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) && (plumberPhoto == null) || MainetConstants.BLANK.equals(plumberPhoto)) {
            addValidationError(
                    ApplicationSession.getInstance().getMessage("water.plumberLicense.valMsg.uploadPlumberPhoto"));
            flag = true;
        }
        if (MainetConstants.NewWaterServiceConstants.NO.equals(getIsFree())) {
            validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
            if (hasValidationErrors())
                flag = true;
        }
        return flag;

    }

    /**
     * This method is used for generating byte code for uploaded files
     * 
     * @param docs is list of uploaded file
     * @return uploaded file name and byte code
     */
    public List<DocumentDetailsVO> setFileUploadMethod(final List<DocumentDetailsVO> docs) {
        final Map<Long, String> listOfString = new HashMap<>();
        final Map<Long, String> fileName = new HashMap<>();
        if ((FileUploadUtility.getCurrent().getFileMap() != null)
                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            for (final Iterator<Entry<Long, Set<File>>> it = FileUploadUtility.getCurrent().getFileMap().entrySet()
                    .iterator(); it.hasNext();) {
                final Entry<Long, Set<File>> entry = it.next();
                if (entry.getKey().longValue() == 0) {
                    it.remove();
                    break;
                }
            }

            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                final List<File> list = new ArrayList<>(entry.getValue());
                for (final File file : list) {
                    try {
                        final Base64 base64 = new Base64();
                        final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                        fileName.put(entry.getKey(), file.getName());
                        listOfString.put(entry.getKey(), bytestring);
                    } catch (final IOException e) {
                        logger.error("Exception has been occurred in file byte to string conversions", e);
                    }
                }
            }
        }
        if (!listOfString.isEmpty() && !CollectionUtils.isEmpty(docs)) {
            for (final DocumentDetailsVO d : docs) {
                final long count = d.getDocumentSerialNo();
                if (listOfString.containsKey(count) && fileName.containsKey(count)) {
                    d.setDocumentByteCode(listOfString.get(count));
                    d.setDocumentName(fileName.get(count));
                }
            }
        }
        return docs;
    }

    public List<DocumentDetailsVO> mapCheckList(final List<DocumentDetailsVO> docs, final BindingResult bindingResult) {

        final List<DocumentDetailsVO> docList = fileUploadService.prepareFileUpload(docs);

        if ((docList != null) && !docList.isEmpty()) {
            for (final DocumentDetailsVO doc : docList) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y)) {
                    if ((doc.getDocumentByteCode() == null) || doc.getDocumentByteCode().isEmpty()) {
                        bindingResult.addError(new ObjectError(MainetConstants.BLANK,
                                ApplicationSession.getInstance().getMessage("water.fileuplaod.validtnMsg")));

                        break;
                    }
                }
            }
        }

        return docList;

    }

    /**
     * @return the applicationId
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * @return the serviceId
     */
    @Override
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    @Override
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the plumberId
     */
    public Long getPlumberId() {
        return plumberId;
    }

    /**
     * @param plumberId the plumberId to set
     */
    public void setPlumberId(final Long plumberId) {
        this.plumberId = plumberId;
    }

    public CFCApplicationAddressEntity getCfcAddressEntity() {
        return cfcAddressEntity;
    }

    public void setCfcAddressEntity(final CFCApplicationAddressEntity cfcAddressEntity) {
        this.cfcAddressEntity = cfcAddressEntity;
    }

    public boolean isScrutinyApplicable() {
        return scrutinyApplicable;
    }

    public void setScrutinyApplicable(final boolean scrutinyApplicable) {
        this.scrutinyApplicable = scrutinyApplicable;
    }

    public ApplicantDetailDTO getApplicantDetailDto() {
        return applicantDetailDto;
    }

    public void setApplicantDetailDto(final ApplicantDetailDTO applicantDetailDto) {
        this.applicantDetailDto = applicantDetailDto;
    }

    /**
     * @return the cfcEntity
     */
    public TbCfcApplicationMstEntity getCfcEntity() {
        return cfcEntity;
    }

    /**
     * @param cfcEntity the cfcEntity to set
     */
    public void setCfcEntity(final TbCfcApplicationMstEntity cfcEntity) {
        this.cfcEntity = cfcEntity;
    }

    public PlumberMaster getPlumberMaster() {
        return plumberMaster;
    }

    public void setPlumberMaster(final PlumberMaster plumberMaster) {
        this.plumberMaster = plumberMaster;
    }

    public List<PlumberQualificationDTO> getPlumberQualificationDTOList() {
        return plumberQualificationDTOList;
    }

    public void setPlumberQualificationDTOList(final List<PlumberQualificationDTO> plumberQualificationDTOList) {
        this.plumberQualificationDTOList = plumberQualificationDTOList;
    }

    public List<PlumberExperienceDTO> getPlumberExperienceDTOList() {
        return plumberExperienceDTOList;
    }

    public void setPlumberExperienceDTOList(final List<PlumberExperienceDTO> plumberExperienceDTOList) {
        this.plumberExperienceDTOList = plumberExperienceDTOList;
    }

    public String getFileDownLoadPath() {
        return fileDownLoadPath;
    }

    public void setFileDownLoadPath(final String fileDownLoadPath) {
        this.fileDownLoadPath = fileDownLoadPath;
    }

    public String getInterviewRemark() {
        return interviewRemark;
    }

    public void setInterviewRemark(final String interviewRemark) {
        this.interviewRemark = interviewRemark;
    }

    public String getPlumQualDeletedRow() {
        return plumQualDeletedRow;
    }

    public void setPlumQualDeletedRow(final String plumQualDeletedRow) {
        this.plumQualDeletedRow = plumQualDeletedRow;
    }

    public String getPlumExpDeletedRow() {
        return plumExpDeletedRow;
    }

    public void setPlumExpDeletedRow(final String plumExpDeletedRow) {
        this.plumExpDeletedRow = plumExpDeletedRow;
    }

    /**
     * @return the interviewDateTime
     */
    public String getInterviewDateTime() {
        return interviewDateTime;
    }

    /**
     * @param interviewDateTime the interviewDateTime to set
     */
    public void setInterviewDateTime(final String interviewDateTime) {
        this.interviewDateTime = interviewDateTime;
    }

    public String getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(final String totalExp) {
        this.totalExp = totalExp;
    }

    public Date getInterviewDateTimeD() {
        return interviewDateTimeD;
    }

    public void setInterviewDateTimeD(final Date interviewDateTimeD) {
        this.interviewDateTimeD = interviewDateTimeD;
    }

    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    /**
     * This method is used for getting checklist and charges.
     */
    @SuppressWarnings("unchecked")
    public void findApplicableCheckListAndCharges(final String serviceCode, final long orgId) {

        // [START] BRMS call initialize model
        final WSRequestDTO dto = new WSRequestDTO();
        dto.setModelName("ChecklistModel|WaterRateMaster");
        final WSRequestDTO initDTO = new WSRequestDTO();

        initDTO.setModelName(MainetConstants.NewWaterServiceConstants.CHECKLIST_WATERRATEMASTER_MODEL);
        final WSResponseDTO response = brmsCommonService.initializeModel(initDTO);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
            setCheckListNCharges(MainetConstants.FlagY);
            final List<Object> checklistModel = RestClient.castResponse(response, CheckListModel.class, 0);
            final List<Object> waterRateMasterList = RestClient.castResponse(response, WaterRateMaster.class, 1);
            final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
            final WaterRateMaster WaterRateMaster = (WaterRateMaster) waterRateMasterList.get(0);
            checkListModel2.setOrgId(orgId);
            checkListModel2.setServiceCode(serviceCode);
            checkListModel2.setIsBPL(getApplicantDetailDto().getIsBPL());
            checkListModel2.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
            dto.setDataModel(checkListModel2);
            WSRequestDTO checklistReqDto = new WSRequestDTO();
            checklistReqDto.setDataModel(checkListModel2);
            WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
            if (checklistRespDto.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                List<DocumentDetailsVO> checkListList = Collections.emptyList();
                checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

                long cnt = 1;
                for (final DocumentDetailsVO doc : checkListList) {
                    doc.setDocumentSerialNo(cnt);
                    cnt++;
                }
                if ((checkListList != null) && !checkListList.isEmpty()) {
                    setCheckList(checkListList);
                }
            } /*
               * else { throw new FrameworkException(
               * "Problem while checking Application level charges and Checklist applicable or not." ); }
               */
            final WSRequestDTO taxRequestDto = new WSRequestDTO();
            WaterRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            WaterRateMaster.setServiceCode(serviceCode);
            WaterRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
                    .getValueFromPrefixLookUp(MainetConstants.NewWaterServiceConstants.APL,
                            MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
                    .getLookUpId()));
            taxRequestDto.setDataModel(WaterRateMaster);
            WSResponseDTO res = brmsWaterService.getApplicableTaxes(taxRequestDto);
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(res.getWsStatus())) {
                if (!res.isFree()) {
                    final List<Object> rates = (List<Object>) res.getResponseObj();
                    final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
                    for (final Object rate : rates) {
                        final WaterRateMaster master1 = (WaterRateMaster) rate;
                        populateChargeModel(master1);
                        master1.setOrgId(orgId);
                        master1.setServiceCode(serviceCode);
                        requiredCHarges.add(master1);
                    }
                    WSRequestDTO chargeReqDto = new WSRequestDTO();
                    chargeReqDto.setModelName("WaterRateMaster");
                    chargeReqDto.setDataModel(requiredCHarges);
                    WSResponseDTO applicableCharges = brmsWaterService.getApplicableCharges(chargeReqDto);
                    List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
                    setChargesInfo(detailDTOs);
                    setAmountToPay(chargesToPay((List<ChargeDetailDTO>) applicableCharges.getResponseObj()));
                    setIsFree(MainetConstants.Common_Constant.NO);
                    this.setChargesMap(detailDTOs);
                    getOfflineDTO().setAmountToShow(getAmountToPay());
                    if (getAmountToPay() == 0.0d) {
                        throw new FrameworkException("Service charge amountToPay cannot be " + getAmountToPay()
                                + " if service configured as Chageable");
                    }

                } else {
                    getPlumberLicenseReqDTO().setAmount(0.0d);
                    setAmountToPay(0.0d);
                }
            } else {
                throw new FrameworkException(
                        "Problem while checking Application level charges and Checklist applicable or not.");
            }
        } else {
            throw new FrameworkException(
                    "Problem while checking Application level charges and Checklist applicable or not.");
        }

    }

    private void setPlumberLicenseData() {
        final UserSession session = UserSession.getCurrent();
        final PlumberLicenseRequestDTO requestDTO = getPlumberLicenseReqDTO();
        ApplicantDetailDTO dto = getApplicantDetailDto();
        final ServiceMaster service1 = service.getServiceMasterByShortCode("WPL",
                UserSession.getCurrent().getOrganisation().getOrgid());
        requestDTO.setServiceId(service1.getSmServiceId());
        setServiceId(service1.getSmServiceId());
        requestDTO.setOrgId(session.getOrganisation().getOrgid());
        requestDTO.setApplicant(dto);
        requestDTO.setDeptId(service1.getTbDepartment().getDpDeptid());
        requestDTO.setPlumberFName(dto.getApplicantFirstName());
        requestDTO.setPlumberMName(dto.getApplicantMiddleName());
        requestDTO.setPlumberLName(dto.getApplicantLastName());
        requestDTO.setPlumAppDate(new Date());
        requestDTO.setAmount(getAmountToPay());

        requestDTO.setUserId(session.getEmployee().getEmpId());
        requestDTO.setLangId((long) session.getLanguageId());
        requestDTO.setLgIpMac(session.getEmployee().getEmppiservername());
        final List<PlumberQualificationDTO> qualificationDTOs = getPlumberQualificationDTOList();
        final List<PlumberExperienceDTO> experienceDTOs = getPlumberExperienceDTOList();

        List<PlumberQualificationDTO> plumberQualificationsList = new ArrayList<>(0);
        PlumberQualificationDTO plumberQualification = null;
        for (final PlumberQualificationDTO qualification : qualificationDTOs) {

            plumberQualification = new PlumberQualificationDTO();
            plumberQualification.setPlumQualification(qualification.getPlumQualification());
            plumberQualification.setPlumInstituteName(qualification.getPlumInstituteName());
            plumberQualification.setPlumInstituteAddress(qualification.getPlumInstituteAddress());
            plumberQualification.setPlumPassYear(qualification.getPlumPassYear());
            plumberQualification.setPlumPassMonth(qualification.getPlumPassMonth());
            plumberQualification.setPlumPercentGrade(qualification.getPlumPercentGrade());
            plumberQualification.setUserId(session.getEmployee().getEmpId());
            plumberQualification.setLangId((int) (long) session.getLanguageId());
            plumberQualification.setOrgId(session.getOrganisation().getOrgid());
            plumberQualification.setLgIpMac(session.getEmployee().getEmppiservername());
            plumberQualificationsList.add(plumberQualification);
        }

        PlumberExperienceDTO plumberExperience = null;
        final List<PlumberExperienceDTO> plumberExperiencesList = new ArrayList<>();
        for (final PlumberExperienceDTO experienceDTO : experienceDTOs) {
            plumberExperience = new PlumberExperienceDTO();
            plumberExperience.setPlumCompanyName(experienceDTO.getPlumCompanyName());
            plumberExperience.setPlumCompanyAddress(experienceDTO.getPlumCompanyAddress());
            plumberExperience.setUserId(session.getEmployee().getEmpId());
            plumberExperience.setLangId((int) (long) session.getLanguageId());
            plumberExperience.setOrgId(session.getOrganisation().getOrgid());
            plumberExperience.setLgIpMac(session.getEmployee().getEmppiservername());
            plumberExperience.setLmodDate(new Date());
            plumberExperiencesList.add(plumberExperience);
        }
        requestDTO.setPlumberQualificationDTOList(plumberQualificationsList);
        requestDTO.setPlumberExperienceDTOList(plumberExperiencesList);
        setPlumberLicenseReqDTO(requestDTO);
    }

    private void populateChargeModel(final WaterRateMaster master1) {
        master1.setIsBPL(getApplicantDetailDto().getIsBPL());

        master1.setDeptCode(MainetConstants.DEPT_SHORT_NAME.WATER);
        master1.setRateStartDate(new Date().getTime());
       //#138629
       if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL))
            master1.setUsageSubtype1(PrefixConstants.NewWaterServiceConstants.WATER);
    }

    private void setChargesMap(final List<ChargeDetailDTO> charges) {
        final Map<Long, Double> chargesMap = new HashMap<>();
        for (final ChargeDetailDTO dto : charges) {
            chargesMap.put(dto.getChargeCode(), dto.getChargeAmount());
        }
        this.setChargesMap(chargesMap);

    }

    private double chargesToPay(final List<ChargeDetailDTO> charges) {
        double amountSum = 0.0;
        for (final ChargeDetailDTO charge : charges) {
            amountSum = amountSum + charge.getChargeAmount();
        }
        return amountSum;
    }

    public PlumberLicenseRequestDTO getPlumberLicenseReqDTO() {
        return plumberLicenseReqDTO;
    }

    public void setPlumberLicenseReqDTO(PlumberLicenseRequestDTO plumberLicenseReqDTO) {
        this.plumberLicenseReqDTO = plumberLicenseReqDTO;
    }

    public Map<Long, Double> getChargesMap() {
        return chargesMap;
    }

    public void setChargesMap(Map<Long, Double> chargesMap) {
        this.chargesMap = chargesMap;
    }

    public String getCheckListNCharges() {
        return checkListNCharges;
    }

    public void setCheckListNCharges(String checkListNCharges) {
        this.checkListNCharges = checkListNCharges;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getPlumberImage() {
        return plumberImage;
    }

    public void setPlumberImage(String plumberImage) {
        this.plumberImage = plumberImage;
    }

    public boolean plumberUpdateAction() {

        WorkflowTaskAction wfAction = this.getWorkflowActionDto();
        UserSession userSession = UserSession.getCurrent();
        Long empId = userSession.getEmployee().getEmpId();
        String empName = userSession.getEmployee().getEmpname();
        wfAction.setCreatedBy(empId);
        wfAction.setEmpName(empName);
        wfAction.setCreatedDate(new Date());
        wfAction.setDateOfAction(new Date());
        wfAction.setIsFinalApproval(isFinalProcess());
        wfAction.setIsObjectionAppealApplicable(MainetConstants.FAILED);
        wfAction.setEmpId(empId);
        wfAction.setOrgId(userSession.getOrganisation().getOrgid());
        return plumberLicenseService.executeWfAction(wfAction, getServiceCode(), getServiceId());

    }

    public boolean isFromEvent() {
        return fromEvent;
    }

    public void setFromEvent(boolean fromEvent) {
        this.fromEvent = fromEvent;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public boolean saveLoiData() {
        final UserSession session = UserSession.getCurrent();
        Long serviceId = this.getServiceId();
        Map<Long, Double> loiCharges = plumberLicenseService.getLoiCharges(applicationId, serviceId,
                session.getOrganisation().getOrgid(), getServiceCode(), getPlumberLicenseReqDTO().getPlumLicToDate());
        // T#90050
        if (MapUtils.isNotEmpty(loiCharges)) {
            TbLoiMas loiMasDto = ApplicationContextProvider.getApplicationContext().getBean(LoiGenerationModel.class)
                    .saveLOIAppData(
                            loiCharges, serviceId, loiDetail, false/* approvalLetterGenerationApplicable */,
                            getWorkflowActionDto());
            if (loiMasDto != null) {
                setLoiId(loiMasDto.getLoiNo());
                setLoiDetail(loiDetail);
                return true;
            }
        }
        return false;
    }

    public boolean savePlumberRenewalLoiData(Long applicationId, Long serviceId, Long orgId, Long userUId,
            String serviceName, Date LicTodate) {
        final UserSession session = UserSession.getCurrent();
        Organisation org = new Organisation();
        org.setOrgid(session.getOrganisation().getOrgid());
        Map<Long, Double> loiCharges = plumberLicenseService.getRenewalLoiCharges(applicationId, serviceId, orgId,
                serviceName, LicTodate);
        // T#90050
        if (MapUtils.isNotEmpty(loiCharges)) {
            List<TbLoiDet> loiDetail = new ArrayList<TbLoiDet>();
            TbLoiMas loiMasDto = ApplicationContextProvider.getApplicationContext().getBean(LoiGenerationModel.class)
                    .saveLOIAppData(
                            loiCharges, serviceId, loiDetail, false/* approvalLetterGenerationApplicable */,
                            getWorkflowActionDto());
            if (loiMasDto != null) {
                setLoiId(loiMasDto.getLoiNo());
                return true;
            }
        }
        return false;
    }

    public TbLoiMas getEntity() {
        return entity;
    }

    public void setEntity(TbLoiMas entity) {
        this.entity = entity;
    }

    public String getLoiId() {
        return loiId;
    }

    public void setLoiId(String loiId) {
        this.loiId = loiId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<LookUp> getLookUpList() {
        return lookUpList;
    }

    public void setLookUpList(List<LookUp> lookUpList) {
        this.lookUpList = lookUpList;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public List<DocumentDetailsVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<DocumentDetailsVO> attachments) {
        this.attachments = attachments;
    }

    public boolean isFinalProcess() {
        return finalProcess;
    }

    public void setFinalProcess(boolean finalProcess) {
        this.finalProcess = finalProcess;
    }

    public String getPayableFlag() {
        return payableFlag;
    }

    public void setPayableFlag(String payableFlag) {
        this.payableFlag = payableFlag;
    }

    public String getSmScrutinyChargeFlag() {
        return smScrutinyChargeFlag;
    }

    public void setSmScrutinyChargeFlag(String smScrutinyChargeFlag) {
        this.smScrutinyChargeFlag = smScrutinyChargeFlag;
    }

    public List<TbLoiDet> getLoiDetail() {
        return loiDetail;
    }

    public void setLoiDetail(List<TbLoiDet> loiDetail) {
        this.loiDetail = loiDetail;
    }

    public Double getTotalLoiAmount() {
        return totalLoiAmount;
    }

    public void setTotalLoiAmount(Double totalLoiAmount) {
        this.totalLoiAmount = totalLoiAmount;
    }

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getPlumLicenseType() {
		return plumLicenseType;
	}

	public void setPlumLicenseType(String plumLicenseType) {
		this.plumLicenseType = plumLicenseType;
	}
    
	 public void setApplicationData(final Long plumId, ApplicantDetailDTO applicantDetailDto) {
	        if (null != plumQualDeletedRow) {
	            plumQualDeletedRow.isEmpty();
	        }
	        if (null != plumExpDeletedRow) {
	            plumExpDeletedRow.isEmpty();
	        }
	        plumberLicenseReqDTO = getPlumberLicenseReqDTO();
	        if (plumberLicenseReqDTO == null) {
	            plumberLicenseReqDTO = new PlumberLicenseRequestDTO();
	        }
	        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	        if (StringUtils.contains(getServiceCode(), MainetConstants.WaterServiceShortCode.RENEWAL_PLUMBER_LICENSE) && isFromEvent())
	            setPlumberMaster(plumberLicenseService.getPlumberDetailsByPlumId(plumId, orgId));
	        else if (StringUtils.contains(getServiceCode(), "DPL"))
	            setPlumberMaster(plumberLicenseService.getPlumberDetailsByAppId(plumberLicenseService
	                .getPlumberDetailsByLicenseNumber(orgId, cfcEntity.getRefNo()).getApplicationId(), orgId));
	        else {

	        	PlumberRenewalRegisterMaster plumberLicenceRenewalDetailsByAppId = plumberLicenseService.
	            		getPlumberLicenceRenewalDetailsByAppId(applicationId, orgId);
	            if(plumberLicenceRenewalDetailsByAppId!=null) {
	            	PlumberMaster plumberDetailsByPlumId = plumberLicenseService.getPlumberDetailsByPlumId(
	            			plumberLicenceRenewalDetailsByAppId.getPlum_id(), orgId);
	            	setPlumberMaster(plumberDetailsByPlumId);
	            }
	        }
	        if (null != plumberMaster) {
	            plumberId = plumberMaster.getPlumId();
	            plumberLicenseReqDTO.setPlumberId(plumberId);
	            if (plumberMaster.getPlumLicFromDate() != null)
	                plumberLicenseReqDTO.setPlumLicFromDate(plumberMaster.getPlumLicFromDate());
	            if (plumberMaster.getPlumLicToDate() != null)
	                plumberLicenseReqDTO.setPlumLicToDate(plumberMaster.getPlumLicToDate());
	            if (plumberMaster.getPlumLicNo() != null)
	                plumberLicenseReqDTO.setPlumberLicenceNo(plumberMaster.getPlumLicNo());
	            setInterviewDateTimeD(plumberMaster.getPlumInterviewDate());
	            setInterviewRemark(plumberMaster.getPlumInterviewRemark());
	            if (plumberMaster.getPlumLicNo() != null && !plumberMaster.getPlumLicNo().isEmpty())
	                plumberLicenseReqDTO.setPlumberLicenceNo(plumberMaster.getPlumLicNo());
	            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
	                    + MainetConstants.FILE_PATH_SEPARATOR + "SHOW_DOCS";
	            setFileDownLoadPath(
	                    Utility.downloadedFileUrl(plumberMaster.getPlumImagePath() + MainetConstants.FILE_PATH_SEPARATOR
	                            + plumberMaster.getPlumImage(), outputPath, getFileNetClient()));
	            setDocumentList(iChecklistVerificationService.getDocumentUploaded(applicationId, orgId));
	        }

	        ApplicantDetailDTO dto = getApplicantDetailDto();
	        if (dto == null) {
	            dto = new ApplicantDetailDTO();
	        }
	        plumberLicenseReqDTO.setApplicant(applicantDetailDto);
	        setApplicantDetailDto(applicantDetailDto);
	        final List<PlumberQualificationDTO> plumberQualificationDTOs = plumberLicenseService
	                .getPlumberQualificationDetails(plumberId, orgId);
	        plumberLicenseReqDTO.setPlumberQualificationDTOList(plumberQualificationDTOs);
	        setPlumberQualificationDTOList(plumberQualificationDTOs);
	        final List<PlumberExperienceDTO> plumberExperienceDTOs = plumberLicenseService
	                .getPlumberExperienceDetails(plumberId, orgId);
	        plumberLicenseReqDTO.setPlumberExperienceDTOList(plumberExperienceDTOs);
	        setPlumberExperienceDTOList(plumberExperienceDTOs);
	        BigInteger sumYear = BigInteger.ZERO;
	        BigInteger sumMonth = BigInteger.ZERO;
	        BigInteger totalMonth = BigInteger.ZERO;
	        if ((plumberExperienceDTOs != null) && !plumberExperienceDTOs.isEmpty()) {
	            for (final PlumberExperienceDTO plumberExperienceDTO : plumberExperienceDTOs) {
	                final Long year = plumberExperienceDTO.getPlumExpYear();
	                final Long month = plumberExperienceDTO.getPlumExpMonth();
	                if (year != null) {
	                    sumYear = sumYear.add(BigInteger.valueOf(year));
	                }
	                if (month != null) {
	                    sumMonth = sumMonth.add(BigInteger.valueOf(month));
	                }
	            }
	        }
	        String userName = Stream.of(applicantDetailDto.getApplicantFirstName(), applicantDetailDto.getApplicantMiddleName(),
	        		applicantDetailDto.getApplicantLastName()).filter(nameString -> nameString!=null && !nameString.isEmpty()).
	        		collect(Collectors.joining(MainetConstants.BLANK));
	        plumberLicenseReqDTO.setPlumberFullName(userName);
	        final BigInteger yearToMonth = sumYear.multiply(BigInteger.valueOf(12));
	        totalMonth = yearToMonth.add(sumMonth);
	        final BigInteger divide = totalMonth.divide(BigInteger.valueOf(12));
	        final BigInteger remainder = totalMonth.mod(BigInteger.valueOf(12));
	        final String exp = divide.toString() + MainetConstants.operator.DOT + remainder.toString();
	        setTotalExp(exp);
	    }

}
