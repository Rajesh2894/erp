package com.abm.mainet.mrm.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionResponse;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.mrm.dao.IMarriageRegistrationDAO;
import com.abm.mainet.mrm.domain.Appointment;
import com.abm.mainet.mrm.domain.AppointmentHistory;
import com.abm.mainet.mrm.domain.Husband;
import com.abm.mainet.mrm.domain.HusbandHistory;
import com.abm.mainet.mrm.domain.Marriage;
import com.abm.mainet.mrm.domain.MarriageHistory;
import com.abm.mainet.mrm.domain.Wife;
import com.abm.mainet.mrm.domain.WifeHistory;
import com.abm.mainet.mrm.domain.WitnessDetails;
import com.abm.mainet.mrm.domain.WitnessDetailsHistory;
import com.abm.mainet.mrm.dto.AppointmentDTO;
import com.abm.mainet.mrm.dto.HusbandDTO;
import com.abm.mainet.mrm.dto.MRMRateMaster;
import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.dto.MarriageRequest;
import com.abm.mainet.mrm.dto.MarriageResponse;
import com.abm.mainet.mrm.dto.WifeDTO;
import com.abm.mainet.mrm.dto.WitnessDetailsDTO;
import com.abm.mainet.mrm.repository.AppointmentRepository;
import com.abm.mainet.mrm.repository.HusbandRepository;
import com.abm.mainet.mrm.repository.MarriageRepository;
import com.abm.mainet.mrm.repository.WifeRepository;
import com.abm.mainet.mrm.repository.WitnessDetailsRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.mrm.service.IMarriageService")
@Api(value = "/mrmService")
@Path("/mrmService")
@Service
public class MarriageServiceImpl implements IMarriageService {

    public static final float PADDING = 10f;
    public static final float TEXT_SIZE_10 = 10f;
    public static final float TEXT_SIZE_9 = 9f;
    public static final float TEXT_SIZE_8 = 8f;
    public static final float TEXT_SIZE_12 = 12f;
    public static final float TEXT_SIZE_15 = 15f;

    private static final Logger LOGGER = Logger.getLogger(MarriageServiceImpl.class);

    @Autowired
    MarriageRepository marriageRepository;

    @Autowired
    HusbandRepository husbandRepository;

    @Autowired
    WifeRepository wifeRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    IMarriageRegistrationDAO marriageRegistrationDAO;

    @Autowired
    WitnessDetailsRepository witnessDetailsRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private IFileUploadService uploadService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private ICFCApplicationMasterService cfcApplicationService;

    @Autowired
    private IWorkflowTyepResolverService iWorkflowTyepResolverService;

    final Base64 base64 = new Base64();

    @Value("${upload.physicalPath}")
    private String filenetPath;

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "saveMarriageRegistration", notes = "saveMarriageRegistration", response = Object.class)
    @Path("/saveMarriageRegistration")
    public MarriageDTO saveMarriageRegInDraftMode(MarriageDTO marriageDTO) {
        switch (marriageDTO.getUrlShortCode()) {
        case MainetConstants.MRM.MRG_URL_CODE:
            // D#139295 here we have to check workflow define or not
            try {
                iWorkflowTyepResolverService.resolveServiceWorkflowType(marriageDTO.getOrgId(),
                        marriageDTO.getDeptId(), marriageDTO.getServiceId(),
                        marriageDTO.getWard1(), marriageDTO.getWard2(), marriageDTO.getWard3(),
                        marriageDTO.getWard4(), marriageDTO.getWard5());
            } catch (Exception e) {
                // error add for workflow not defined
                List<String> error = new ArrayList<>();
                error.add("Workflow is missing For the service ! Please contact to Administrator !");
                marriageDTO.setErrorList(error);
                return marriageDTO;
            }
            marriageDTO.setMarId(saveMarriage(marriageDTO));

            break;
        case MainetConstants.MRM.HUS_URL_CODE:
            // document save
            if ((marriageDTO.getUploadMap() != null) && !marriageDTO.getUploadMap().isEmpty()) {
                final File photoFile = marriageDTO.getUploadMap().get("90H");
                final File thumbFile = marriageDTO.getUploadMap().get("91H");
                String fileNetPath = null;
                String dirPath = null;
                fileNetPath = messageSource.getMessage("upload.physicalPath", new Object[] {}, StringUtils.EMPTY,
                        Locale.ENGLISH);
                final StringBuilder builder = new StringBuilder();
                builder.append(marriageDTO.getOrgId()).append(File.separator).append("MARRIAGE")
                        .append(File.separator).append(Utility.getTimestamp());
                dirPath = builder.toString();

                if (StringUtils.isNotBlank(marriageDTO.getHitFrom()) && !marriageDTO.getHitFrom().equalsIgnoreCase("DEPT")) {
                    if (StringUtils.isNotEmpty(marriageDTO.getHusbandDTO().getCapturePhotoName())) {
                        uploadBase64Image(marriageDTO.getHusbandDTO().getCapturePhotoName(),
                                marriageDTO.getHusbandDTO().getCapturePhotoPath(), fileNetPath, dirPath);
                        marriageDTO.getHusbandDTO().setCapturePhotoPath(dirPath);
                    }
                    if (StringUtils.isNotEmpty(marriageDTO.getHusbandDTO().getCaptureFingerprintName())) {
                        uploadBase64Image(marriageDTO.getHusbandDTO().getCaptureFingerprintName(),
                                marriageDTO.getHusbandDTO().getCaptureFingerprintPath(), fileNetPath, dirPath);
                        marriageDTO.getHusbandDTO().setCaptureFingerprintPath(dirPath);
                    }

                } else {
                    if (photoFile != null) {
                        final String photoFileName = uploadImage(photoFile, fileNetPath, dirPath);
                        marriageDTO.getHusbandDTO().setCapturePhotoName(photoFileName);
                        marriageDTO.getHusbandDTO().setCapturePhotoPath(dirPath);
                    } else {
                        marriageDTO.getHusbandDTO().setCapturePhotoName(null);
                        marriageDTO.getHusbandDTO().setCapturePhotoPath(null);
                    }
                    if (thumbFile != null) {
                        final String thumbFileName = uploadImage(thumbFile, fileNetPath, dirPath);
                        marriageDTO.getHusbandDTO().setCaptureFingerprintName(thumbFileName);
                        marriageDTO.getHusbandDTO().setCaptureFingerprintPath(dirPath);
                    } else {
                        marriageDTO.getHusbandDTO().setCaptureFingerprintName(null);
                        marriageDTO.getHusbandDTO().setCaptureFingerprintPath(null);
                    }
                }

            }
            // String docPath = uploadDocument(marriageDTO.getOrgId(), FileNetApplicationClient.getInstance());
            marriageDTO.getHusbandDTO().setHusbandId(saveHusband(marriageDTO.getHusbandDTO()));
            break;

        case MainetConstants.MRM.WIFE_URL_CODE:

            if ((marriageDTO.getUploadMap() != null) && !marriageDTO.getUploadMap().isEmpty()) {
                final File photoFile = marriageDTO.getUploadMap().get("990W");
                final File thumbFile = marriageDTO.getUploadMap().get("991W");
                String fileNetPath = null;
                String dirPath = null;
                fileNetPath = messageSource.getMessage("upload.physicalPath", new Object[] {}, StringUtils.EMPTY,
                        Locale.ENGLISH);
                final StringBuilder builder = new StringBuilder();
                builder.append(marriageDTO.getOrgId()).append(File.separator).append("MARRIAGE")
                        .append(File.separator).append(Utility.getTimestamp());
                dirPath = builder.toString();

                if (StringUtils.isNotBlank(marriageDTO.getHitFrom()) && !marriageDTO.getHitFrom().equalsIgnoreCase("DEPT")) {
                    if (StringUtils.isNotEmpty(marriageDTO.getWifeDTO().getCapturePhotoName())) {
                        uploadBase64Image(marriageDTO.getWifeDTO().getCapturePhotoName(),
                                marriageDTO.getWifeDTO().getCapturePhotoPath(), fileNetPath, dirPath);
                        marriageDTO.getWifeDTO().setCapturePhotoPath(dirPath);
                    }
                    if (StringUtils.isNotEmpty(marriageDTO.getWifeDTO().getCaptureFingerprintName())) {
                        uploadBase64Image(marriageDTO.getWifeDTO().getCaptureFingerprintName(),
                                marriageDTO.getWifeDTO().getCaptureFingerprintPath(), fileNetPath, dirPath);
                        marriageDTO.getWifeDTO().setCaptureFingerprintPath(dirPath);
                    }

                } else {
                    if (photoFile != null) {
                        final String photoFileName = uploadImage(photoFile, fileNetPath, dirPath);
                        marriageDTO.getWifeDTO().setCapturePhotoName(photoFileName);
                        marriageDTO.getWifeDTO().setCapturePhotoPath(dirPath);
                    } else {
                        marriageDTO.getWifeDTO().setCapturePhotoName(null);
                        marriageDTO.getWifeDTO().setCapturePhotoPath(null);
                    }
                    if (thumbFile != null) {
                        final String thumbFileName = uploadImage(thumbFile, fileNetPath, dirPath);
                        marriageDTO.getWifeDTO().setCaptureFingerprintName(thumbFileName);
                        marriageDTO.getWifeDTO().setCaptureFingerprintPath(dirPath);
                    } else {
                        marriageDTO.getWifeDTO().setCaptureFingerprintName(null);
                        marriageDTO.getWifeDTO().setCaptureFingerprintPath(null);
                    }
                }

            }
            marriageDTO.getWifeDTO().setWifeId(saveWife(marriageDTO.getWifeDTO()));
            break;

        case MainetConstants.MRM.WITNESS_LEASE_URL_CODE:
            // witness document save
            List<WitnessDetails> witnessEntities = new ArrayList<>();
            List<Object> witnessDetHistory = new ArrayList<>();

            if ((marriageDTO.getUploadMap() != null) && !marriageDTO.getUploadMap().isEmpty()) {
                final String fileNetPath = messageSource.getMessage("upload.physicalPath", new Object[] {}, StringUtils.EMPTY,
                        Locale.ENGLISH);
                final StringBuilder builder = new StringBuilder();
                builder.append(marriageDTO.getOrgId()).append(File.separator).append("MARRIAGE").append(File.separator)
                        .append(Utility.getTimestamp());
                final String dirPath = builder.toString();

                marriageDTO.getWitnessDetailsDTO().forEach(witnessDTO -> {
                    WitnessDetails witness = new WitnessDetails();
                    BeanUtils.copyProperties(witnessDTO, witness);
                    Marriage marriage = new Marriage();
                    BeanUtils.copyProperties(marriageDTO, marriage);
                    witness.setMarId(marriage);
                    String photoKey = witness.getWitnessId() + "0WT";
                    String thumbKey = witness.getWitnessId() + "1WT";
                    final File photoFile = marriageDTO.getUploadMap().get(photoKey);
                    final File thumbFile = marriageDTO.getUploadMap().get(thumbKey);
                    if (photoFile != null) {
                        final String photoFileName = uploadImage(photoFile, fileNetPath, dirPath);
                        witness.setCapturePhotoName(photoFileName);
                        witness.setCapturePhotoPath(dirPath);
                    } else {
                        witness.setCapturePhotoName(null);
                        witness.setCapturePhotoPath(null);
                    }
                    if (thumbFile != null) {
                        final String thumbFileName = uploadImage(thumbFile, fileNetPath, dirPath);
                        witness.setCaptureFingerprintName(thumbFileName);
                        witness.setCaptureFingerprintPath(dirPath);
                    } else {
                        witness.setCaptureFingerprintName(null);
                        witness.setCaptureFingerprintPath(null);
                    }

                    witnessEntities.add(witness);
                });

            }

            // final save
            witnessDetailsRepository.save(witnessEntities);
            break;

        case MainetConstants.MRM.APPOINTMENT_URL_CODE:
            // marriageDTO.getAppointmentDTO().getMarId().setMarId(marId);

            break;

        }
        return marriageDTO;

    }

    private String uploadImage(final File uploadfile, final String fileNetPath, final String dirPath) {
        String bytestring = null;
        try {
            bytestring = base64.encodeToString(FileUtils.readFileToByteArray(uploadfile));
        } catch (final IOException e) {
            LOGGER.error("Could not convert from base64 encoding: " + uploadfile, e);
        }
        String uploadFileName = uploadfile.getName();
        if ((uploadFileName != null) && uploadFileName.contains("/")) {
            uploadFileName = uploadFileName.replace("/", "_");
        }
        final DocumentDetailsVO documentDetailsVO = new DocumentDetailsVO();
        documentDetailsVO.setDocumentByteCode(bytestring);
        uploadService.convertAndSaveFile(documentDetailsVO, fileNetPath, dirPath, uploadFileName);
        return uploadFileName;
    }

    private void uploadBase64Image(final String fileName, final String bytestring, final String fileNetPath,
            final String dirPath) {
        final DocumentDetailsVO documentDetailsVO = new DocumentDetailsVO();
        documentDetailsVO.setDocumentByteCode(bytestring);
        uploadService.convertAndSaveFile(documentDetailsVO, fileNetPath, dirPath, fileName);
    }

    @Override
    @Transactional
    public Long saveMarriage(MarriageDTO marriageDTO) {
        // save in TB_CFC_APPLICATION_MST common code
        if (!marriageDTO.getStatus().equals("APPROVED") && marriageDTO.getApplicationId() == null) {
            // D#110063 check here workflow define or not
            WorkflowMas mas = iWorkflowTyepResolverService.resolveServiceWorkflowType(marriageDTO.getOrgId(),
                    marriageDTO.getDeptId(), marriageDTO.getServiceId(),
                    marriageDTO.getWard1(), marriageDTO.getWard2(), marriageDTO.getWard3(),
                    marriageDTO.getWard4(), marriageDTO.getWard5());
            if (mas == null) {
                // error add for workflow not defined
                List<String> error = new ArrayList<>();
                error.add("workflow not defined");
                marriageDTO.setErrorList(error);
            }

            Long applicationId = setAndSaveApplicationDetails(marriageDTO);
            marriageDTO.setApplicationId(applicationId);
        }
        Marriage marriageEntity = new Marriage();
        BeanUtils.copyProperties(marriageDTO, marriageEntity);
        MarriageHistory marriageHistory = new MarriageHistory();
        marriageEntity = marriageRepository.save(marriageEntity);
        marriageHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
        // auditService.createHistory(marriageEntity, marriageHistory);
        // D#110852 here set page no input from certificate page
        if (marriageDTO.getAppointmentDTO().getPageNo() != null) {
            marriageRegistrationDAO.updateAppointmentData(marriageDTO.getMarId(), marriageDTO.getAppointmentDTO().getPageNo());
        }

        return marriageEntity.getMarId();
    }

    @Override
    @Transactional
    public Long saveHusband(HusbandDTO husbandDTO) {
        Husband husbandEntity = new Husband();
        BeanUtils.copyProperties(husbandDTO, husbandEntity);
        Marriage marriage = new Marriage();
        BeanUtils.copyProperties(husbandDTO.getMarId(), marriage);
        husbandEntity.setMarId(marriage);
        husbandEntity = husbandRepository.save(husbandEntity);
        return husbandEntity.getHusbandId();
    }

    @Override
    @Transactional
    public Long saveWife(WifeDTO wifeDTO) {
        Wife wifeEntity = new Wife();
        BeanUtils.copyProperties(wifeDTO, wifeEntity);
        // D#127367 same table column caste id in husband and wife
        wifeEntity.setCaste1(wifeDTO.getWcaste1());
        wifeEntity.setCaste2(wifeDTO.getWcaste2());
        wifeEntity.setCaste3(wifeDTO.getWcaste3());
        wifeEntity.setCaste4(wifeDTO.getWcaste4());
        wifeEntity.setCaste5(wifeDTO.getWcaste5());

        Marriage marriage = new Marriage();
        BeanUtils.copyProperties(wifeDTO.getMarId(), marriage);
        wifeEntity.setMarId(marriage);
        wifeEntity = wifeRepository.save(wifeEntity);
        return wifeEntity.getWifeId();
    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "saveWitnessDetails", notes = "saveWitnessDetails", response = Object.class)
    @Path("/saveWitnessDetails")
    @Transactional
    public MarriageDTO saveWitnessDetails(MarriageDTO marriageDTO) {
        List<WitnessDetails> witnessEntities = new ArrayList<>();
        List<Object> witnessDetHistory = new ArrayList<>();
        marriageDTO.getWitnessDetailsDTO().forEach(witnessDTO -> {
            WitnessDetails witness = new WitnessDetails();
            BeanUtils.copyProperties(witnessDTO, witness);
            Marriage marriage = new Marriage();
            BeanUtils.copyProperties(witnessDTO.getMarId(), marriage);
            witness.setMarId(marriage);
            witnessEntities.add(witness);
        });

        uploadService.doFileUpload(marriageDTO.getDocumentList(), marriageDTO);

        // update application id in TB_MRM_MARRIAGE
        marriageRegistrationDAO.updateMarriageRegData(marriageDTO.getMarId(), marriageDTO.getStatus(), null,
                marriageDTO.getOrgId(), null, marriageDTO.getCreatedBy(), new Date(), "SUBMIT");

        // D#129017 update status in TB_CFC_APPLICATION_MST
        final TbCfcApplicationMstEntity cfcEntity = cfcApplicationService
                .getCFCApplicationByApplicationId(marriageDTO.getApplicationId(), marriageDTO.getOrgId());
        cfcEntity.setApmMode(MainetConstants.FlagS);
        cfcEntity.setUpdatedBy(marriageDTO.getEmpId());
        cfcEntity.setUpdatedDate(new Date());
        cfcEntity.setLgIpMacUpd(marriageDTO.getLgIpMac());
        applicationService.updateApplication(cfcEntity);

        witnessDetailsRepository.save(witnessEntities);
        // doing this because reuse code at
        // a. when normal save
        // b. when some changes in Marriage certificate
        String hStatus = MainetConstants.InsertMode.ADD.getStatus();
        if (marriageDTO.getStatus().equalsIgnoreCase("APPROVED")) {
            hStatus = MainetConstants.InsertMode.UPDATE.getStatus();
        }
        for (WitnessDetails witness : witnessEntities) {
            WitnessDetailsHistory witnessHistory = new WitnessDetailsHistory();
            BeanUtils.copyProperties(witness, witnessHistory);
            witnessHistory.sethStatus(hStatus);
            witnessHistory.setMarId(witness.getMarId().getMarId());
            witnessHistory.setWitnessId(witness.getWitnessId());
            witnessDetHistory.add(witnessHistory);
        }
        // save in TB_MRM_MARRIAGE_HIST table
        Marriage marriageEntity = new Marriage();
        BeanUtils.copyProperties(marriageDTO, marriageEntity);
        MarriageHistory marriageHistory = new MarriageHistory();
        marriageHistory.sethStatus(hStatus);
        auditService.createHistory(marriageEntity, marriageHistory);

        // save in TB_MRM_HUSBAND_HIST table
        Husband husbandEntity = new Husband();
        BeanUtils.copyProperties(marriageDTO.getHusbandDTO(), husbandEntity);
        HusbandHistory husbandHistory = new HusbandHistory();
        husbandHistory.setMarId(marriageDTO.getMarId());
        husbandHistory.sethStatus(hStatus);
        auditService.createHistory(husbandEntity, husbandHistory);

        // save in TB_MRM_WIFE_HIST table
        Wife wifeEntity = new Wife();
        BeanUtils.copyProperties(marriageDTO.getWifeDTO(), wifeEntity);
        WifeHistory wifeHistory = new WifeHistory();
        wifeHistory.setMarId(marriageDTO.getMarId());
        wifeHistory.sethStatus(hStatus);
        auditService.createHistory(wifeEntity, wifeHistory);

        // save in TB_MRM_WITNESS_DET_HIST table
        auditService.createHistoryForListObj(witnessDetHistory);

        if (marriageDTO.getStatus().equalsIgnoreCase("APPROVED")) {

        } else {
            // initiate workflow when application time charge is free
            ServiceMaster serviceMaster = new ServiceMaster();

            serviceMaster = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getServiceByShortName(MainetConstants.MRM.SERVICE_CODE.MRG,
                            marriageDTO.getOrgId());
            boolean loiChargeApplflag = false;
            if (StringUtils.equalsIgnoreCase(serviceMaster.getSmScrutinyChargeFlag(), MainetConstants.FlagY)) {
                loiChargeApplflag = true;
            }
            marriageDTO.setServiceId(serviceMaster.getSmServiceId());
            if (marriageDTO.isFree()) {
                boolean workflowSuccess = initializeWorkFlowForFreeService(marriageDTO, loiChargeApplflag);
                if (!workflowSuccess) {
                    // add multiple error in future if getting and code place change accordingly
                    List<String> error = new ArrayList<>();
                    error.add("Exception occured while calling workflow");
                    marriageDTO.setErrorList(error);
                    return marriageDTO;
                }
            }
            // 1st MSG when application punch
            SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
            smsDto.setOrgId(marriageDTO.getOrgId());
            smsDto.setLangId(1);
            smsDto.setAppNo(String.valueOf(marriageDTO.getApplicationId()));
            smsDto.setServName(serviceMaster.getSmServiceName());

            smsDto.setMobnumber(marriageDTO.getApplicantDetailDto().getMobileNo());
            // smsDto.setAppName(marriageDTO.getApplicantName());
            smsDto.setAppName(marriageDTO.getApplicantDetailDto().getApplicantFirstName() + " "
                    + marriageDTO.getApplicantDetailDto().getApplicantLastName());
            smsDto.setUserId(marriageDTO.getCreatedBy());
            if (StringUtils.isNotBlank(marriageDTO.getApplicantDetailDto().getEmailId())) {
                smsDto.setEmail(marriageDTO.getApplicantDetailDto().getEmailId());
            }

            smsDto.setRegNo(marriageDTO.getSerialNo());
            Organisation org = new Organisation();
            org.setOrgid(marriageDTO.getOrgId());

            ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
                    MainetConstants.MRM.MRM_DEPT_CODE, MainetConstants.MRM.MAR_REG_URL,
                    PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED,
                    smsDto, org, 1);

        }

        return marriageDTO;

    }

    public Long setAndSaveApplicationDetails(MarriageDTO requestDto) {
        // Setting all fields in applicantDto to create application Number and saving
        // the data
        RequestDTO applicantDto = new RequestDTO();
        applicantDto.setOrgId(requestDto.getOrgId());
        applicantDto.setUserId(requestDto.getUserId());
        applicantDto.setLangId(requestDto.getLangId());
        applicantDto.setServiceId(requestDto.getServiceId());
        applicantDto.setDeptId(requestDto.getDeptId());
        applicantDto.setPayStatus(MainetConstants.FlagF);
        applicantDto.setTitleId(requestDto.getApplicantDetailDto().getApplicantTitle());
        applicantDto.setfName(requestDto.getApplicantDetailDto().getApplicantFirstName());
        applicantDto.setlName(requestDto.getApplicantDetailDto().getApplicantLastName());
        applicantDto.setMobileNo(requestDto.getApplicantDetailDto().getMobileNo());
        applicantDto.setAreaName(requestDto.getApplicantDetailDto().getAreaName());
        applicantDto.setPincodeNo(Long.valueOf(requestDto.getApplicantDetailDto().getPinCode()));
        if (StringUtils.isNotBlank(requestDto.getApplicantDetailDto().getRoadName())) {
            applicantDto.setRoadName(requestDto.getApplicantDetailDto().getRoadName());
        }
        if (StringUtils.isNotBlank(requestDto.getApplicantDetailDto().getVillageTownSub())) {
            applicantDto.setCityName(requestDto.getApplicantDetailDto().getVillageTownSub());
        }
        if (StringUtils.isNotBlank(requestDto.getApplicantDetailDto().getApplicantMiddleName())) {
            applicantDto.setmName(requestDto.getApplicantDetailDto().getApplicantMiddleName());
        }
        if (StringUtils.isNotBlank(requestDto.getApplicantDetailDto().getEmailId())) {
            applicantDto.setEmail(requestDto.getApplicantDetailDto().getEmailId());
        }
        if (StringUtils.isNotBlank(requestDto.getApplicantDetailDto().getAadharNo())) {
            applicantDto.setAadhaarNo(requestDto.getApplicantDetailDto().getAadharNo());
        }
        // D#129017
        applicantDto.setApmMode(MainetConstants.CommonMasterUi.D);
        if (requestDto.getApplicantDetailDto().getDwzid1() != null
                && requestDto.getApplicantDetailDto().getDwzid1() != 0) {
            applicantDto.setZoneNo(requestDto.getApplicantDetailDto().getDwzid1());
        }
        if (requestDto.getApplicantDetailDto().getDwzid2() != null
                && requestDto.getApplicantDetailDto().getDwzid2() != 0) {
            applicantDto.setWardNo(requestDto.getApplicantDetailDto().getDwzid2());
        }
        if (requestDto.getApplicantDetailDto().getDwzid3() != null
                && requestDto.getApplicantDetailDto().getDwzid3() != 0) {
            applicantDto.setBlockNo(String.valueOf(requestDto.getApplicantDetailDto().getDwzid3()));
        }
        applicantDto.setTableName("TB_MRM_MARRIAGE");
        applicantDto.setColumnName("APPLICATION_ID");
        return applicationService.createApplication(applicantDto);

    }

    public Boolean initializeWorkFlowForFreeService(MarriageDTO requestDto, boolean loiChargeApplFlag) {
        boolean checkList = false;
        if (CollectionUtils.isNotEmpty(requestDto.getDocumentList())) {
            checkList = true;
        }
        ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
        ApplicationMetadata applicationMetaData = new ApplicationMetadata();
        applicantDto.setApplicantFirstName(requestDto.getHusbandDTO().getFirstNameEng());
        applicantDto.setServiceId(requestDto.getServiceId());
        applicantDto.setDepartmentId(requestDto.getDeptId());
        applicantDto.setDwzid1(requestDto.getWard1());
        applicantDto.setDwzid2(requestDto.getWard2());
        applicantDto.setDwzid3(requestDto.getWard3());
        applicantDto.setDwzid4(requestDto.getWard4());
        applicantDto.setDwzid5(requestDto.getWard5());
        // applicantDto.setMobileNo();
        applicantDto.setUserId(requestDto.getUserId());
        applicationMetaData.setApplicationId(requestDto.getApplicationId());
        applicationMetaData.setIsCheckListApplicable(checkList);
        applicationMetaData.setOrgId(requestDto.getOrgId());
        applicationMetaData.setIsLoiApplicable(loiChargeApplFlag);
        try {
            commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
        } catch (Exception e) {
            throw new FrameworkException("Exception occured while calling workflow");
        }

        return true;

    }

    @Override
    @Transactional
    public Long saveAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointmentEntity = new Appointment();
        BeanUtils.copyProperties(appointmentDTO, appointmentEntity);
        if (appointmentDTO.getAppointmentDate() == null) {
            appointmentEntity.setAppointmentDate(stringToTimeConvet("00:00"));
        }
        if (StringUtils.isBlank(appointmentDTO.getAppointmentTime())) {
            appointmentEntity.setAppointmentTime(stringToTimeConvet("00:00"));
        } else {
            appointmentEntity.setAppointmentTime(stringToTimeConvet(appointmentDTO.getAppointmentTime()));
        }
        Marriage marriage = new Marriage();
        BeanUtils.copyProperties(appointmentDTO.getMarId(), marriage);
        appointmentEntity.setMarId(marriage);
        AppointmentHistory appointmentHistory = new AppointmentHistory();
        appointmentEntity = appointmentRepository.save(appointmentEntity);
        appointmentHistory.setMarId(appointmentEntity.getMarId().getMarId());
        appointmentHistory.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(appointmentEntity, appointmentHistory);
        return appointmentEntity.getAppointmentId();
    }

    public Date stringToTimeConvet(String time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Date timeValue = null;
        if (time != null)
            try {
                timeValue = new Date(formatter.parse(time).getTime());
            } catch (ParseException e) {
                LOGGER.error("Exception occured while converting string to time :: ", e);
            }
        return timeValue;
    }

    @Override
    @Transactional
    public boolean executeApprovalWorkflowAction(WorkflowTaskAction taskAction, Long smServiceId, String actionStatus) {
        boolean updateFlag = false;
        try {
            marriageRegistrationDAO.updateMarriageRegData(null, taskAction.getDecision(), null,
                    taskAction.getOrgId(), taskAction.getApplicationId(), taskAction.getCreatedBy(), new Date(), actionStatus);

            updateWorkflowTaskAction(taskAction, smServiceId);
            updateFlag = true;
        } catch (Exception exception) {
            LOGGER.error("Exception Occured while Updating workflow action task", exception);
            throw new FrameworkException("Error while Updating workflow action task", exception);
        }
        return updateFlag;
    }

    @Transactional
    private WorkflowTaskActionResponse updateWorkflowTaskAction(WorkflowTaskAction taskAction, Long serviceId) {
        WorkflowTaskActionResponse workflowResponse = null;
        try {
            String processName = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getProcessName(serviceId, taskAction.getOrgId());
            if (StringUtils.isNotBlank(processName)) {
                WorkflowProcessParameter workflowDto = new WorkflowProcessParameter();
                workflowDto.setProcessName(processName);
                workflowDto.setWorkflowTaskAction(taskAction);
                workflowResponse = ApplicationContextProvider.getApplicationContext()
                        .getBean(IWorkflowExecutionService.class).updateWorkflow(workflowDto);

            }
        } catch (Exception exception) {
            throw new FrameworkException("Error while Updating workflow action task", exception);
        }
        return workflowResponse;
    }

    @Override
    @Transactional
    public Map<Long, Double> getLoiCharges(Long orgId, Long serviceId, String serviceCode, Date marriageDate,
            Date applicationDate) {
        final List<MRMRateMaster> mrmChargeModelList = new ArrayList<>();
        WSRequestDTO requestDTO = new WSRequestDTO();
        List<MRMRateMaster> mrmRequiredCharges = ApplicationContextProvider.getApplicationContext()
                .getBean(IBRMSMRMService.class).getLoiChargesForMRM(requestDTO, orgId, serviceCode);

        for (final MRMRateMaster actualRate : mrmRequiredCharges) {

            actualRate.setDeptCode(MainetConstants.MRM.MRM_DEPT_CODE);
            actualRate.setOrgId(orgId);
            actualRate.setServiceCode(serviceCode);
            actualRate.setRateStartDate(new Date().getTime());
            actualRate.setNoOfDays(Utility.getDaysBetweenDates(marriageDate, applicationDate));
            mrmChargeModelList.add(actualRate);
        }

        requestDTO.setDataModel(mrmChargeModelList);
        final WSResponseDTO output = RestClient.callBRMS(requestDTO,
                ServiceEndpoints.BRMSMappingURL.MRM_SERVICE_CHARGE_URI);
        final List<?> mrmRateList = RestClient.castResponse(output, MRMRateMaster.class);
        MRMRateMaster loiCharges = null;
        final Map<Long, Double> chargeMap = new HashMap<>();
        for (final Object rate : mrmRateList) {
            loiCharges = (MRMRateMaster) rate;
            chargeMap.put(loiCharges.getTaxId(), loiCharges.getSlabRate1());

        }
        return chargeMap;
    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "Fetch Marriage Data Based on Filter", notes = "Fetch Marriage Data Based on Filter", response = Object.class)
    @Path("/fetchMarriageData")
    public List<MarriageDTO> fetchSearchData(MarriageRequest marriageRequest) {
        List<MarriageDTO> marriageDTOList = new ArrayList<MarriageDTO>();

        List<Object[]> objList = marriageRegistrationDAO.searchMarriageData(marriageRequest.getMarriageDate(),
                marriageRequest.getAppDate(), marriageRequest.getStatus(), marriageRequest.getSerialNo(),
                marriageRequest.getOrgId(), marriageRequest.getHusbandId(), marriageRequest.getWifeId());

        objList.forEach(marriage -> {
            // get object
            MarriageDTO dto = new MarriageDTO();
            dto.setMarId(convertBigIntTOLong(marriage[0]));
            dto.setApplicationId(convertBigIntTOLong(marriage[1]));
            dto.setMarDate((Date) marriage[2]);
            dto.setSerialNo((String) marriage[3] == null ? "NA" : (String) marriage[3]);

            HusbandDTO husbandDTO = new HusbandDTO();
            husbandDTO.setHusbandId(convertBigIntTOLong(marriage[7]));
            husbandDTO.setFirstNameEng((String) marriage[4]);
            husbandDTO.setLastNameEng((String) marriage[6]);
            WifeDTO wifeDTO = new WifeDTO();
            wifeDTO.setWifeId(convertBigIntTOLong(marriage[11]));
            wifeDTO.setFirstNameEng((String) marriage[8]);
            wifeDTO.setLastNameEng((String) marriage[10]);
            dto.setStatus((String) marriage[12]);
            dto.setAppDate((Date) marriage[14]);
            dto.setHusbandDTO(husbandDTO);
            dto.setWifeDTO(wifeDTO);

            if (dto.getApplicationId() != 0 && dto.getApplicationId() != null) {
                dto.setApplicantDetailDto(getApplicantData(dto.getApplicationId(), marriageRequest.getOrgId()));
                dto.setApplicantName(dto.getApplicantDetailDto().getApplicantFirstName() + " "
                        + dto.getApplicantDetailDto().getApplicantLastName());
            }
            if (marriageRequest.getSkdclENVPresent()) {
                // D#128597 display the application no in case of draft also
            } else if (dto.getStatus().equals("D")) {
                dto.setApplicationId(0l);
            }

            marriageDTOList.add(dto);
        });

        return marriageDTOList;
    }

    private Long convertBigIntTOLong(Object obj) {
        BigInteger bigInt = (BigInteger) obj;
        return bigInt != null ? bigInt.longValue() : null;
    }

    public MarriageDTO getMarriageDetailsById(Long marId, Long applicationId, String serialNo, Date marDate, Long orgId,
            String hitFrom) {
        MarriageDTO marriageDTO = new MarriageDTO();
        Marriage marriage = new Marriage();
        if (marId != null) {
            marriage = marriageRepository.findOne(marId);
        } else if (applicationId != null) {
            List<Marriage> marriageList = marriageRepository.findByApplicationIdAndOrgIdOrderByMarIdDesc(applicationId, orgId);
            if (!marriageList.isEmpty()) {
                marriage = marriageList.get(0);
            }
        } else if (!StringUtils.isEmpty(serialNo)) {
            marriage = marriageRepository.findBySerialNoAndOrgId(serialNo, orgId);
        }

        if (marriage != null) {
            HusbandDTO husbandDTO = new HusbandDTO();
            if (marriage.getHusband() != null) {
                BeanUtils.copyProperties(marriage.getHusband(), husbandDTO);
                // other than department hit convert it into BASE64
                if (StringUtils.isNotBlank(hitFrom) && !hitFrom.equals("DEPT")
                        && StringUtils.isNotEmpty(husbandDTO.getCapturePhotoPath())) {
                    husbandDTO
                            .setCapturePhotoPath(
                                    convertInBase64(husbandDTO.getCapturePhotoName(), husbandDTO.getCapturePhotoPath()));
                }
                if (StringUtils.isNotBlank(hitFrom) && !hitFrom.equals("DEPT")
                        && StringUtils.isNotEmpty(husbandDTO.getCaptureFingerprintPath())) {
                    husbandDTO.setCaptureFingerprintPath(
                            convertInBase64(husbandDTO.getCaptureFingerprintName(), husbandDTO.getCaptureFingerprintPath()));
                }
            }

            WifeDTO wifeDTO = new WifeDTO();
            if (marriage.getWife() != null) {
                BeanUtils.copyProperties(marriage.getWife(), wifeDTO);
                // D#127367 same table column caste id in husband and wife
                wifeDTO.setWcaste1(marriage.getWife().getCaste1());
                wifeDTO.setWcaste2(marriage.getWife().getCaste2());
                wifeDTO.setWcaste3(marriage.getWife().getCaste3());
                wifeDTO.setWcaste4(marriage.getWife().getCaste4());
                wifeDTO.setWcaste5(marriage.getWife().getCaste5());

                if (StringUtils.isNotBlank(hitFrom) && !hitFrom.equals("DEPT")
                        && StringUtils.isNotEmpty(wifeDTO.getCapturePhotoPath())) {
                    wifeDTO.setCapturePhotoPath(convertInBase64(wifeDTO.getCapturePhotoName(), wifeDTO.getCapturePhotoPath()));
                }
                if (StringUtils.isNotBlank(hitFrom) && !hitFrom.equals("DEPT")
                        && StringUtils.isNotEmpty(wifeDTO.getCaptureFingerprintPath())) {
                    wifeDTO.setCaptureFingerprintPath(
                            convertInBase64(wifeDTO.getCaptureFingerprintName(), wifeDTO.getCaptureFingerprintPath()));
                }
            }

            AppointmentDTO appointmentDTO = new AppointmentDTO();
            if (marriage.getAppointment() != null) {
                BeanUtils.copyProperties(marriage.getAppointment(), appointmentDTO);
                appointmentDTO
                        .setAppointmentTime(new SimpleDateFormat("HH:mm").format(marriage.getAppointment().getAppointmentTime()));
            }

            BeanUtils.copyProperties(marriage, marriageDTO);

            if (marriage.getWard1() != null) {
                marriageDTO.setWard1Desc(
                        CommonMasterUtility.getHierarchicalLookUp(marriage.getWard1(), marriage.getOrgId()).getDescLangFirst());
                marriageDTO.setWard1RegDesc(
                        CommonMasterUtility.getHierarchicalLookUp(marriage.getWard1(), marriage.getOrgId()).getDescLangSecond());
            }

            if (marriage.getWard2() != null) {
                marriageDTO.setWard2Desc(
                        CommonMasterUtility.getHierarchicalLookUp(marriage.getWard2(), marriage.getOrgId()).getDescLangFirst());
            }

            if (marriage.getWard3() != null) {
                marriageDTO.setWard3Desc(
                        CommonMasterUtility.getHierarchicalLookUp(marriage.getWard3(), marriage.getOrgId()).getDescLangFirst());
            }

            if (marriage.getWard4() != null) {
                marriageDTO.setWard4Desc(
                        CommonMasterUtility.getHierarchicalLookUp(marriage.getWard4(), marriage.getOrgId()).getDescLangFirst());
            }

            if (marriage.getWard5() != null) {
                marriageDTO.setWard5Desc(
                        CommonMasterUtility.getHierarchicalLookUp(marriage.getWard5(), marriage.getOrgId()).getDescLangFirst());
            }

            marriageDTO.setHusbandDTO(husbandDTO);
            marriageDTO.setWifeDTO(wifeDTO);
            AtomicInteger count = new AtomicInteger(1);
            // set witness data
            List<WitnessDetailsDTO> witnessDetailsDTO = new ArrayList<>();
            marriage.getWitnesses().forEach(witnessDet -> {
                WitnessDetailsDTO witnessDetDTO = new WitnessDetailsDTO();
                BeanUtils.copyProperties(witnessDet, witnessDetDTO);
                witnessDetDTO.setSrNo(count.getAndIncrement());
                // get occupation DESC from occupation prefix
                LookUp lookupOCU = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                        witnessDet.getOccupation(), orgId, "OCU");
                witnessDetDTO.setOccupationDesc(lookupOCU.getLookUpDesc());
                // D#127392 get Relation DESC from Relationship prefix
                LookUp lookupRLS = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(
                        witnessDet.getRelation(), orgId, "RLS");
                if (lookupRLS != null) {
                    witnessDetDTO.setRelationDesc(lookupRLS.getLookUpDesc());
                }

                if (lookupRLS != null && lookupRLS.getLookUpCode().equals("OT")
                        && StringUtils.isNotBlank(witnessDetDTO.getOtherRel())) {
                    witnessDetDTO.setRelationDesc(witnessDetDTO.getOtherRel());
                }

                witnessDetailsDTO.add(witnessDetDTO);
            });
            marriageDTO.setWitnessDetailsDTO(witnessDetailsDTO);
            marriageDTO.setAppointmentDTO(appointmentDTO);
        }

        return marriageDTO;
    }

    @Override
    @Transactional
    public Boolean saveAppointmentAndDecision(WorkflowTaskAction taskAction, RequestDTO requestDto,
            List<DocumentDetailsVO> approvalDocumentAttachment, ServiceMaster serviceMaster, AppointmentDTO appointmentDTO) {

        fileUpload.doFileUpload(approvalDocumentAttachment, requestDto);
        saveAppointment(appointmentDTO);

        return executeApprovalWorkflowAction(taskAction, serviceMaster.getSmServiceId(), "APPOINTMENT");
    }

    @Override
    public ApplicantDetailDTO getApplicantData(Long applicationId, Long orgId) {
        ApplicantDetailDTO detailDto = new ApplicantDetailDTO();
        TbCfcApplicationMstEntity masterEntity = cfcApplicationService.getCFCApplicationByApplicationId(applicationId, orgId);

        if (masterEntity != null) {
            detailDto.setApplicantTitle(masterEntity.getApmTitle());
            detailDto.setApplicantFirstName(masterEntity.getApmFname());
            detailDto.setApplicantLastName(masterEntity.getApmLname());
            if (StringUtils.isNotBlank(masterEntity.getApmMname())) {
                detailDto.setApplicantMiddleName(masterEntity.getApmMname());
            }
            if (StringUtils.isNotBlank(masterEntity.getApmBplNo())) {
                detailDto.setIsBPL(MainetConstants.YES);
                detailDto.setBplNo(masterEntity.getApmBplNo());
            } else {
                detailDto.setIsBPL(MainetConstants.NO);
            }

            if (StringUtils.isNotBlank(masterEntity.getApmResid())) {
                detailDto.setAadharNo(masterEntity.getApmResid());
            }
        }

        CFCApplicationAddressEntity addressEntity = cfcApplicationService.getApplicantsDetails(applicationId);
        if (addressEntity != null) {
            detailDto.setMobileNo(addressEntity.getApaMobilno());
            detailDto.setEmailId(addressEntity.getApaEmail());
            detailDto.setAreaName(addressEntity.getApaAreanm());
            detailDto.setPinCode(String.valueOf(addressEntity.getApaPincode()));
            if (addressEntity.getApaZoneNo() != null && addressEntity.getApaZoneNo() != 0) {
                detailDto.setDwzid1(addressEntity.getApaZoneNo());
            }
            if (addressEntity.getApaWardNo() != null && addressEntity.getApaWardNo() != 0) {
                detailDto.setDwzid2(addressEntity.getApaWardNo());
            }
            if (StringUtils.isNotBlank(addressEntity.getApaBlockno())) {
                detailDto.setDwzid3(Long.valueOf(addressEntity.getApaBlockno()));
            }
            if (StringUtils.isNotBlank(addressEntity.getApaCityName())) {
                detailDto.setVillageTownSub(addressEntity.getApaCityName());
            }
            if (StringUtils.isNotBlank(addressEntity.getApaRoadnm())) {
                detailDto.setRoadName(addressEntity.getApaRoadnm());
            }
        }
        return detailDto;
    }

    @Override
    @Consumes("application/json")
    @POST
    @ApiOperation(value = "get Marriage Response", notes = "get Marriage Response", response = Object.class)
    @Path("/getMarriageData")
    public MarriageResponse getMarriageData(@RequestBody MarriageRequest marriageRequest) {
        MarriageResponse marriageResponse = new MarriageResponse();
        // get service data
        ServiceMaster serviceDto = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceByShortName(MainetConstants.MRM.SERVICE_CODE.MRG, marriageRequest.getOrgId());
        marriageResponse.setDeptId(serviceDto.getTbDepartment().getDpDeptid());
        marriageResponse.setDeptName(serviceDto.getTbDepartment().getDpDeptdesc());
        marriageResponse.setServiceId(serviceDto.getSmServiceId());
        marriageResponse.setServiceName(serviceDto.getSmServiceName());
        marriageResponse.setSmAppliChargeFlag(serviceDto.getSmAppliChargeFlag());
        marriageResponse.setSmChklstVerify(serviceDto.getSmChklstVerify());
        marriageResponse.setSmSLA(serviceDto.getSmServiceDuration());
        marriageResponse.setMarriageDTO(getMarriageDetailsById(marriageRequest.getMarId(), marriageRequest.getApplicationId(),
                marriageRequest.getSerialNo(), marriageRequest.getMarriageDate(),
                marriageRequest.getOrgId(), marriageRequest.getHitFrom()));
        if (marriageResponse.getMarriageDTO().getApplicationId() != null) {
            marriageResponse.setApplicantDetailDTO(
                    getApplicantData(marriageResponse.getMarriageDTO().getApplicationId(), marriageRequest.getOrgId()));
        }

        return marriageResponse;
    }

    @Override
    public Boolean checkApplicationIdExist(Long applicationId, Long orgId) {
        return marriageRepository.checkApplicationIdExist(applicationId, orgId);
    }

    // method for convert in base64
    public String convertInBase64(String docName, String docPath) {
        String base64String = null;
        String existingPath = null;
        if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) {
            existingPath = docPath.replace('/', '\\');
        } else {
            existingPath = docPath.replace('\\', '/');
        }
        String directoryPath = existingPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMA);
        try {
            final byte[] image = FileNetApplicationClient.getInstance().getFileByte(docName, directoryPath);
            Base64 base64 = new Base64();
            base64String = base64.encodeToString(image);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return base64String;
    }

    @Override
    public WardZoneBlockDTO getWordZoneBlockByApplicationId(final Long applicationId, final Long serviceId, final Long orgId) {

        Marriage marriageEntity = marriageRepository.findByApplicationIdAndOrgId(applicationId, orgId);

        WardZoneBlockDTO wardZoneDTO = null;

        if (marriageEntity != null) {
            wardZoneDTO = new WardZoneBlockDTO();
            if (marriageEntity.getWard1() != null) {
                wardZoneDTO.setAreaDivision1(marriageEntity.getWard1());
            }
            if (marriageEntity.getWard2() != null) {
                wardZoneDTO.setAreaDivision2(marriageEntity.getWard2());
            }
            if (marriageEntity.getWard3() != null) {
                wardZoneDTO.setAreaDivision3(marriageEntity.getWard3());
            }
            if (marriageEntity.getWard4() != null) {
                wardZoneDTO.setAreaDivision4(marriageEntity.getWard4());
            }
            if (marriageEntity.getWard5() != null) {
                wardZoneDTO.setAreaDivision5(marriageEntity.getWard5());
            }
        }

        return wardZoneDTO;
    }

    @Override
    public boolean generateCertificate(String docPath, Long applicationNo, Long orgId) {
        final MarriageDTO marriageDTO = getMarriageDetailsById(null, applicationNo, null, null, orgId, null);
        if (marriageDTO != null && marriageDTO.getMarId() != null) {
            LOGGER.info("generateBirthCertificate   method started docPath: " + docPath + "applicationNo: " + applicationNo);
            final String MANGAL = ApplicationSession.getInstance().getMessage("mangalFont.path");
            LOGGER.info("MAngal Font" + MANGAL);
            final String ARIALUNICODE = ApplicationSession.getInstance().getMessage("arialUniCode.path");
            LOGGER.info("ARIALUNICODE Font" + ARIALUNICODE);

            try {
                PdfWriter writer = new PdfWriter(docPath);
                PdfDocument pdfDoc = new PdfDocument(writer);
                pdfDoc.addNewPage();
                Document document = new Document(pdfDoc);
                FontProgram fontProgram = FontProgramFactory.createFont(ARIALUNICODE);
                PdfFont font = PdfFontFactory.createFont(fontProgram, PdfEncodings.IDENTITY_H);
                document.setFont(font);
                document.setMargins(8f, 8f, 8f, 8f);
                LOGGER.info("Documnent started");
                // Adding table
                // Table table = new Table(4);
                Table table = new Table(new float[] { 145, 145, 145, 145 }) // in points
                        .setWidth(580); // 100 pt
                // table.setWidth(new UnitValue(UnitValue.PERCENT, 100));
                table.setHorizontalAlignment(HorizontalAlignment.LEFT);
                Border b1 = new DoubleBorder(4);
                table.setBorder(b1);
                float cellWidth = 140;
                SolidLine line = new SolidLine(1f);
                line.setColor(ColorConstants.BLACK);
                LineSeparator ls = new LineSeparator(line);
                ls.setBold();
                ls.setWidth(new UnitValue(UnitValue.PERCENT, 100));
                ls.setHorizontalAlignment(HorizontalAlignment.CENTER);

                Cell r1Cell1 = new Cell(1, 2);
                LOGGER.info("Left logo full path" + filenetPath + marriageDTO.getHusbandDTO().getCapturePhotoPath());
                ImageData leftImgData = ImageDataFactory
                        .create(filenetPath + MainetConstants.FILE_PATH_SEPARATOR
                                + marriageDTO.getHusbandDTO().getCapturePhotoPath()
                                + MainetConstants.FILE_PATH_SEPARATOR
                                + marriageDTO.getHusbandDTO().getCapturePhotoName());
                Image logoImg = new Image(leftImgData);
                logoImg.setHeight(95f);
                logoImg.setWidth(70f);
                r1Cell1.add(logoImg);
                r1Cell1.setHorizontalAlignment(HorizontalAlignment.LEFT);
                r1Cell1.setBorder(Border.NO_BORDER);
                table.addCell(r1Cell1);

                Cell emblemCell = new Cell();
                ImageData middleImgData = ImageDataFactory
                        .create(filenetPath + ApplicationSession.getInstance().getMessage("leftlogo"));
                Image middleImg = new Image(middleImgData);
                // middleImg.setMarginLeft(-20f);
                middleImg.setHeight(95f);
                middleImg.setWidth(71f);
                emblemCell.add(middleImg);
                emblemCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                emblemCell.setBorder(Border.NO_BORDER);
                table.addCell(emblemCell);

                Cell r1Cell3 = new Cell();
                LOGGER.info("Right logo" + filenetPath + MainetConstants.FILE_PATH_SEPARATOR
                        + marriageDTO.getWifeDTO().getCapturePhotoPath()
                        + MainetConstants.FILE_PATH_SEPARATOR
                        + marriageDTO.getWifeDTO().getCapturePhotoName());
                ImageData rightImgData = ImageDataFactory
                        .create(filenetPath + MainetConstants.FILE_PATH_SEPARATOR
                                + marriageDTO.getWifeDTO().getCapturePhotoPath()
                                + MainetConstants.FILE_PATH_SEPARATOR
                                + marriageDTO.getWifeDTO().getCapturePhotoName());
                Image logoImgRight = new Image(rightImgData);
                logoImgRight.setWidth(95f);
                logoImgRight.setHeight(71f);
                r1Cell3.add(logoImgRight.setHorizontalAlignment(HorizontalAlignment.RIGHT));
                r1Cell3.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                r1Cell3.setVerticalAlignment(VerticalAlignment.MIDDLE);
                r1Cell3.setBorder(Border.NO_BORDER);
                table.addCell(r1Cell3.setPadding(PADDING));

                // below emblem data set

                Cell r1Cell2 = new Cell(1, 4);
                ImageData govMahReg = ImageDataFactory.create(
                        Utility.textToImage(ApplicationSession.getInstance().getMessage("mrm.gov.mhr"), MANGAL, 12f, false));
                Image govMahRegImg = new Image(govMahReg);
                r1Cell2.add(govMahRegImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
                Paragraph govMahEng = new Paragraph("GOVERNMENT OF MAHARASHTRA");
                govMahEng.setHorizontalAlignment(HorizontalAlignment.CENTER);
                govMahEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
                govMahEng.setTextAlignment(TextAlignment.CENTER);
                govMahEng.setFontSize(12f);
                r1Cell2.add(govMahEng);

                ImageData line1AddReg = ImageDataFactory.create(Utility.textToImage(
                        ApplicationSession.getInstance().getMessage("mrm.kdmc.halfAdd") + " " + marriageDTO.getWard1RegDesc()
                                + " " + ApplicationSession.getInstance().getMessage("mrm.kdmc.fullAdd"),
                        MANGAL, 10f, false));
                Image line1AddRegImg = new Image(line1AddReg);
                r1Cell2.add(line1AddRegImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
                Paragraph line1AddEng = new Paragraph("Marriage Registration Office: " + marriageDTO.getWard1Desc()
                        + " Kalyan Dombivli Municipal Corporation, Kalyan (W)");
                line1AddEng.setHorizontalAlignment(HorizontalAlignment.CENTER);
                line1AddEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
                line1AddEng.setTextAlignment(TextAlignment.CENTER);
                line1AddEng.setFontSize(10f);
                // line1AddEng.setMarginLeft(35f);
                r1Cell2.add(line1AddEng);

                ImageData line2AddReg = ImageDataFactory.create(
                        Utility.textToImage(ApplicationSession.getInstance().getMessage("mrm.kdmc.subAdd"), MANGAL, 10f, false));
                Image line2AddRegImg = new Image(line2AddReg);
                r1Cell2.add(line2AddRegImg.setHorizontalAlignment(HorizontalAlignment.CENTER));
                Paragraph line2AddEng = new Paragraph("Taluka: Kalyan, District: Thane, Maharashtra, India");
                line2AddEng.setHorizontalAlignment(HorizontalAlignment.CENTER);
                line2AddEng.setVerticalAlignment(VerticalAlignment.MIDDLE);
                line2AddEng.setTextAlignment(TextAlignment.CENTER);
                line2AddEng.setFontSize(10f);
                r1Cell2.add(line2AddEng);
                r1Cell2.setHorizontalAlignment(HorizontalAlignment.CENTER);
                r1Cell2.setVerticalAlignment(VerticalAlignment.TOP);
                r1Cell2.setBorder(Border.NO_BORDER);
                table.addCell(r1Cell2);

                Cell r1Cell3a = new Cell(1, 4);
                r1Cell3a.setHorizontalAlignment(HorizontalAlignment.CENTER);
                r1Cell3a.setBorder(Border.NO_BORDER);
                r1Cell3a.add(ls);
                table.addCell(r1Cell3a);

                Cell keyValPara1Cell = new Cell(1, 4);
                keyValPara1Cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                keyValPara1Cell.setBorder(Border.NO_BORDER);
                ImageData formE = ImageDataFactory.create(Utility.textToImage(
                        ApplicationSession.getInstance().getMessage("mrm.formE") + " / Form 'E' ", ARIALUNICODE, TEXT_SIZE_10,
                        false));
                Image formEImg = new Image(formE);
                formEImg.setHorizontalAlignment(HorizontalAlignment.CENTER);
                // para1tableCell1Img.setTextAlignment(TextAlignment.LEFT);
                formEImg.setTextAlignment(TextAlignment.CENTER);
                // cell4tableCell1Img.setAutoScale(true);
                keyValPara1Cell.add(formEImg);
                table.addCell(keyValPara1Cell);

                Cell keyValPara2Cell = new Cell(1, 4);
                keyValPara2Cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                keyValPara2Cell.setBorder(Border.NO_BORDER);
                ImageData cert = ImageDataFactory.create(Utility.textToImage(
                        ApplicationSession.getInstance().getMessage("mrm.mrg.certificate"), MANGAL, TEXT_SIZE_10, false));
                Image certImg = new Image(cert);
                certImg.setHorizontalAlignment(HorizontalAlignment.CENTER);
                // para1tableCell1Img.setTextAlignment(TextAlignment.LEFT);
                certImg.setTextAlignment(TextAlignment.CENTER);
                // cell4tableCell1Img.setAutoScale(true);
                keyValPara2Cell.add(certImg);
                table.addCell(keyValPara2Cell);

                Cell keyValPara3Cell = new Cell(1, 4);
                keyValPara3Cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                keyValPara3Cell.setBorder(Border.NO_BORDER);
                Paragraph para3 = new Paragraph("Certificate of Registration of Marriage");
                para3.setHorizontalAlignment(HorizontalAlignment.LEFT);
                para3.setVerticalAlignment(VerticalAlignment.MIDDLE);
                para3.setTextAlignment(TextAlignment.CENTER);
                para3.setFontSize(TEXT_SIZE_10);
                // cell5tableCell1Img.setAutoScale(true);
                keyValPara3Cell.add(para3);
                table.addCell(keyValPara3Cell);

                Cell keyValPara4Cell = new Cell(1, 4);
                keyValPara4Cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                keyValPara4Cell.setBorder(Border.NO_BORDER);
                ImageData section = ImageDataFactory.create(Utility.textToImage(
                        ApplicationSession.getInstance().getMessage("mrm.cert.section"), ARIALUNICODE, TEXT_SIZE_10, false));
                Image sectionImg = new Image(section);
                sectionImg.setHorizontalAlignment(HorizontalAlignment.CENTER);
                // para1tableCell1Img.setTextAlignment(TextAlignment.LEFT);
                sectionImg.setTextAlignment(TextAlignment.CENTER);
                // cell4tableCell1Img.setAutoScale(true);
                keyValPara4Cell.add(sectionImg);
                table.addCell(keyValPara4Cell);

                String applicationDate = marriageDTO.getRegDate() == null ? Utility.dateToString(new Date())
                        : Utility.dateToString(marriageDTO.getRegDate());
                // Main Paragraph REG Start

                Cell mainRegCell = new Cell(1, 4);
                ImageData mainReg1 = ImageDataFactory.create(
                        Utility.textToImage(ApplicationSession.getInstance().getMessage("mrm.cert.reg.certified") +
                                ApplicationSession.getInstance().getMessage("mrm.cert.husb.name") +
                                marriageDTO.getHusbandDTO().getFirstNameReg() + " " +
                                marriageDTO.getHusbandDTO().getMiddleNameReg() + " " +
                                marriageDTO.getHusbandDTO().getLastNameReg() + ", " +
                                ApplicationSession.getInstance().getMessage("mrm.cert.UIDNO") +
                                Utility.convertToRegional("marathi", marriageDTO.getHusbandDTO().getUidNo()) + "," +
                                ApplicationSession.getInstance().getMessage("mrm.cert.locate.add") + " " +
                                marriageDTO.getHusbandDTO().getFullAddrReg() + ",", ARIALUNICODE, 9f, false));
                Image mainReg1Img = new Image(mainReg1);
                mainReg1Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
                mainReg1Img.setTextAlignment(TextAlignment.LEFT);
                mainRegCell.add(mainReg1Img);

                ImageData mainReg2 = ImageDataFactory.create(Utility.textToImage(
                        ApplicationSession.getInstance().getMessage("mrm.cert.wife.name") +
                                marriageDTO.getWifeDTO().getFirstNameReg() + " " +
                                marriageDTO.getWifeDTO().getMiddleNameReg() + " " +
                                marriageDTO.getWifeDTO().getLastNameReg() + " , " +
                                ApplicationSession.getInstance().getMessage("mrm.cert.UIDNO") +
                                Utility.convertToRegional("marathi", marriageDTO.getWifeDTO().getUidNo()) + ", " +
                                ApplicationSession.getInstance().getMessage("mrm.cert.locate.add") + " " +
                                marriageDTO.getWifeDTO().getFullAddrReg() + " ",
                        ARIALUNICODE, 9f, false));
                Image mainReg2Img = new Image(mainReg2);
                // line1AddEng.setMarginLeft(35f);
                mainRegCell.setBorder(Border.NO_BORDER);
                mainReg2Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
                mainReg2Img.setTextAlignment(TextAlignment.LEFT);
                mainRegCell.add(mainReg2Img);

                ImageData mainReg3 = ImageDataFactory.create(Utility.textToImage(
                        ApplicationSession.getInstance().getMessage("mrm.cert.both.mar") +
                                ApplicationSession.getInstance().getMessage("mrm.cert.date") + " " +
                                Utility.convertToRegional("marathi", Utility.dateToString(marriageDTO.getMarDate())) + " " +
                                ApplicationSession.getInstance().getMessage("mrm.cert.at") + " " +
                                marriageDTO.getPlaceMarR() + " " +
                                ApplicationSession.getInstance().getMessage("mrm.cert.mah1") + " ",
                        ARIALUNICODE, 9f, false));
                Image mainReg3Img = new Image(mainReg3);
                mainReg3Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
                mainReg3Img.setTextAlignment(TextAlignment.LEFT);
                mainRegCell.add(mainReg3Img);

                ImageData mainReg4 = ImageDataFactory.create(Utility.textToImage(
                        ApplicationSession.getInstance().getMessage("mrm.cert.mah2") + " " +
                                Utility.convertToRegional("marathi", marriageDTO.getVolume()) + " " +
                                ApplicationSession.getInstance().getMessage("mrm.cert.serialNo") + " " +
                                Utility.convertToRegional("marathi", marriageDTO.getSerialNo()) + " " +
                                ApplicationSession.getInstance().getMessage("mrm.cert.var") + " " +

                                // logic need for display current date
                                ApplicationSession.getInstance().getMessage("mrm.cert.date") +
                                Utility.convertToRegional("marathi", applicationDate) +
                                ApplicationSession.getInstance().getMessage("mrm.cert.endLine"),
                        ARIALUNICODE, TEXT_SIZE_9,
                        false));
                Image mainReg4Img = new Image(mainReg4);
                mainReg4Img.setHorizontalAlignment(HorizontalAlignment.LEFT);
                mainReg4Img.setTextAlignment(TextAlignment.LEFT);
                mainRegCell.add(mainReg4Img);

                table.addCell(mainRegCell);

                Cell keyValPara5Cell = new Cell(1, 4);
                keyValPara5Cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                keyValPara5Cell.setBorder(Border.NO_BORDER);
                ImageData mainParaReg = ImageDataFactory.create(
                        Utility.textToImage(ApplicationSession.getInstance().getMessage("mrm.cert.reg.certified") +
                                ApplicationSession.getInstance().getMessage("mrm.cert.husb.name") +
                                marriageDTO.getHusbandDTO().getFirstNameReg() +
                                marriageDTO.getHusbandDTO().getMiddleNameReg() +
                                marriageDTO.getHusbandDTO().getLastNameReg() + "," +
                                ApplicationSession.getInstance().getMessage("mrm.cert.UIDNO") +
                                Utility.convertToRegional("marathi", marriageDTO.getHusbandDTO().getUidNo()) + "," +
                                ApplicationSession.getInstance().getMessage("mrm.cert.locate.add") +
                                marriageDTO.getHusbandDTO().getFullAddrReg() + "," +
                                ApplicationSession.getInstance().getMessage("mrm.cert.wife.name") +
                                marriageDTO.getWifeDTO().getFirstNameReg() +
                                marriageDTO.getWifeDTO().getMiddleNameReg() +
                                marriageDTO.getWifeDTO().getLastNameReg() + "," +
                                ApplicationSession.getInstance().getMessage("mrm.cert.UIDNO") +
                                Utility.convertToRegional("marathi", marriageDTO.getWifeDTO().getUidNo()) + "," +
                                ApplicationSession.getInstance().getMessage("mrm.cert.locate.add") +
                                marriageDTO.getWifeDTO().getFullAddrReg() + "," +
                                ApplicationSession.getInstance().getMessage("mrm.cert.both.mar") +
                                ApplicationSession.getInstance().getMessage("mrm.cert.date") +

                                Utility.convertToRegional("marathi", Utility.dateToString(marriageDTO.getMarDate())) +
                                ApplicationSession.getInstance().getMessage("mrm.cert.at") +
                                marriageDTO.getPlaceMarR() +
                                ApplicationSession.getInstance().getMessage("mrm.cert.mah") +
                                Utility.convertToRegional("marathi", marriageDTO.getVolume()) +
                                ApplicationSession.getInstance().getMessage("mrm.cert.serialNo") +
                                Utility.convertToRegional("marathi", marriageDTO.getSerialNo()) +
                                ApplicationSession.getInstance().getMessage("mrm.cert.var") +

                                // logic need for display current date
                                ApplicationSession.getInstance().getMessage("mrm.cert.date") +
                                Utility.convertToRegional("marathi", applicationDate) +
                                ApplicationSession.getInstance().getMessage("mrm.cert.endLine"), ARIALUNICODE, TEXT_SIZE_9,
                                false));

                Image mainParaRegImg = new Image(mainParaReg);
                mainParaRegImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
                mainParaRegImg.setTextAlignment(TextAlignment.LEFT);

                // cell4tableCell1Img.setAutoScale(true);
                keyValPara5Cell.add(mainParaRegImg);
                // table.addCell(keyValPara5Cell);
                // Main Paragraph REG End

                table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
                table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));

                // Main Paragraph ENG Start

                Cell keyValPara6Cell = new Cell(1, 4);
                keyValPara6Cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                keyValPara6Cell.setBorder(Border.NO_BORDER);
                Paragraph para6 = new Paragraph("Certified that marriage between, Husband's name: " +
                        marriageDTO.getHusbandDTO().getFirstNameEng() + " " +
                        marriageDTO.getHusbandDTO().getMiddleNameEng() + " " +
                        marriageDTO.getHusbandDTO().getLastNameEng() + ", UID No." +
                        marriageDTO.getHusbandDTO().getUidNo() + " Residing at: " +
                        marriageDTO.getHusbandDTO().getFullAddrEng() + ", and Wife's name: " +
                        marriageDTO.getWifeDTO().getFirstNameEng() + " " +
                        marriageDTO.getWifeDTO().getMiddleNameEng() + "" +
                        marriageDTO.getWifeDTO().getLastNameEng() + ", UID No. " +
                        marriageDTO.getWifeDTO().getUidNo() + ", Residing at: " +
                        marriageDTO.getWifeDTO().getFullAddrEng() + " Solemnized on " +
                        Utility.dateToString(marriageDTO.getMarDate()) + " at: " +
                        marriageDTO.getPlaceMarE() + " is Registered by me on " +
                        applicationDate + " at Serial No." +
                        marriageDTO.getSerialNo() + " of Volume " +
                        marriageDTO.getVolume()
                        + " of register of Marriages maintained under the Maharashtra Regulation of Marriage Bureaus and Registration of Marriage Act, 1998.");
                para6.setHorizontalAlignment(HorizontalAlignment.LEFT);
                para6.setVerticalAlignment(VerticalAlignment.MIDDLE);
                // para6.setFirstLineIndent(20f);
                para6.setFontSize(TEXT_SIZE_9);
                // cell5tableCell1Img.setAutoScale(true);
                keyValPara6Cell.add(para6);
                table.addCell(keyValPara6Cell);

                table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
                table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));
                table.addCell(new Cell(1, 4).setBorder(Border.NO_BORDER));

                Cell placeCell = new Cell(1, 3);
                placeCell.setBorder(Border.NO_BORDER);
                Paragraph paraPlace = new Paragraph("Place: Kalyan");
                paraPlace.setHorizontalAlignment(HorizontalAlignment.LEFT);
                paraPlace.setFontSize(TEXT_SIZE_10);
                placeCell.add(paraPlace);
                table.addCell(placeCell);

                /*
                 * Cell keyValPara0Cell2 = new Cell(); keyValPara0Cell2.setVerticalAlignment(VerticalAlignment.MIDDLE);
                 * keyValPara0Cell2.setBorder(Border.NO_BORDER); Paragraph para1 = new Paragraph(""); keyValPara0Cell2.add(para1);
                 * table.addCell(keyValPara0Cell2);
                 */

                Cell paraRight1Cell = new Cell();
                paraRight1Cell.setBorder(Border.NO_BORDER);
                Paragraph paraRight1 = new Paragraph("Registrar of Marriages");
                paraRight1.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                paraRight1.setFontSize(TEXT_SIZE_10);
                paraRight1.setMultipliedLeading(0.5f);
                // cell5tableCell1Img.setAutoScale(true);
                paraRight1Cell.add(paraRight1);
                table.addCell(paraRight1Cell);

                Cell placeDate = new Cell(1, 3);
                placeDate.setBorder(Border.NO_BORDER);
                Paragraph paraPlaceDate = new Paragraph(Utility.dateToString(new Date()));
                paraPlaceDate.setHorizontalAlignment(HorizontalAlignment.LEFT);
                paraPlaceDate.setFontSize(TEXT_SIZE_10);
                placeDate.add(paraPlaceDate);
                table.addCell(placeDate);

                Cell paraRight2Cell = new Cell();
                paraRight2Cell.setBorder(Border.NO_BORDER);
                Paragraph paraRight2 = new Paragraph(marriageDTO.getWard1Desc() + ", ");
                paraRight2.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                paraRight2.setFontSize(TEXT_SIZE_10);
                paraRight2.setMultipliedLeading(0.5f);
                paraRight2.setBold();
                paraRight2.setMarginLeft(20);
                // cell5tableCell1Img.setAutoScale(true);
                paraRight2Cell.add(paraRight2);
                table.addCell(paraRight2Cell);

                Cell leftSpace = new Cell(1, 3);
                leftSpace.setBorder(Border.NO_BORDER);
                Paragraph paraLeftSpace = new Paragraph("");
                paraLeftSpace.setHorizontalAlignment(HorizontalAlignment.LEFT);
                paraLeftSpace.setFontSize(TEXT_SIZE_10);
                leftSpace.add(paraLeftSpace);
                table.addCell(leftSpace);

                Cell paraRight3Cell = new Cell();
                paraRight3Cell.setBorder(Border.NO_BORDER);
                Paragraph paraRight3 = new Paragraph(UserSession.getCurrent().getOrganisation().getONlsOrgname());
                paraRight3.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                paraRight3.setFontSize(TEXT_SIZE_10);
                paraRight3.setMultipliedLeading(0.5f);
                // cell5tableCell1Img.setAutoScale(true);
                paraRight3Cell.add(paraRight3);
                table.addCell(paraRight3Cell);

                table.addCell(leftSpace);

                Cell paraRight4Cell = new Cell();
                paraRight4Cell.setBorder(Border.NO_BORDER);
                Paragraph paraRight4 = new Paragraph(UserSession.getCurrent().getOrganisation().getOrgAddress());
                paraRight4.setHorizontalAlignment(HorizontalAlignment.RIGHT);
                paraRight4.setFontSize(TEXT_SIZE_10);
                paraRight4.setMultipliedLeading(1);
                // cell5tableCell1Img.setAutoScale(true);
                paraRight4Cell.add(paraRight4);
                table.addCell(paraRight4Cell);

                document.add(table);
                document.close();
                writer.close();
                return true;
            } catch (Exception e) {
                LOGGER.info("Exception Occure when marriage digital certificate generate" + e);
                return false;
            }
        }

        return false;
    }

}
