package com.abm.mainet.mrm.ui.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.FileUploadClientService;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.mrm.dto.HusbandDTO;
import com.abm.mainet.mrm.dto.MarriageDTO;
import com.abm.mainet.mrm.dto.MarriageRequest;
import com.abm.mainet.mrm.dto.WifeDTO;
import com.abm.mainet.mrm.service.IMarriageService;
import com.abm.mainet.mrm.ui.model.MarriageRegistrationModel;

@Controller
@RequestMapping("MarriageCertificateGeneration.html")
public class MarriageCertificateController extends AbstractFormController<MarriageRegistrationModel> {

    private static final Logger LOGGER = Logger.getLogger(MarriageCertificateController.class);

    @Autowired
    private ServiceMasterService smService;

    @Autowired
    private IMarriageService marriageService;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    private IAttachDocsService attachDocsService;

    @Autowired
    IFileUploadService fileUpload;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        // make here husband name list and wife name list

        MarriageRequest mrgRequest = new MarriageRequest();
        mrgRequest.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        mrgRequest.setSkdclENVPresent(false);
        List<MarriageDTO> marriageDTOs = marriageService.fetchSearchData(mrgRequest);
        List<HusbandDTO> husbandList = new ArrayList<>();
        List<WifeDTO> wifeList = new ArrayList<>();
        // D#114091
        marriageDTOs.removeIf(
                marriage -> StringUtils.isEmpty(marriage.getSerialNo()) || marriage.getSerialNo().equalsIgnoreCase("NA"));
        marriageDTOs.forEach(marriageDTO -> {
            husbandList.add(marriageDTO.getHusbandDTO());
            wifeList.add(marriageDTO.getWifeDTO());
        });
        this.getModel().setHusbandList(husbandList);
        this.getModel().setWifeList(wifeList);
        this.getModel().setMarriageDTOs(marriageDTOs);
        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(params = "searchMRMCertificate", method = RequestMethod.POST)
    public List<MarriageDTO> searchMRMCertificate(@RequestParam("marriageDate") final Date marriageDate,
            @RequestParam("serialNo") final String serialNo,
            @RequestParam("husbandId") final Long husbandId,
            @RequestParam("wifeId") final Long wifeId, final HttpServletRequest request) {
        getModel().bind(request);

        MarriageRequest mrgReq = new MarriageRequest();
        mrgReq.setMarriageDate(marriageDate);
        mrgReq.setSerialNo(serialNo);
        mrgReq.setHusbandId(husbandId);
        mrgReq.setWifeId(wifeId);
        mrgReq.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        mrgReq.setSkdclENVPresent(false);
        return marriageService.fetchSearchData(mrgReq);
    }

    @RequestMapping(params = "showDetails", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView showDetails(
            @RequestParam(value = "appNo", required = false) Long applicationId,
            @RequestParam(value = "taskId", required = false) String taskId,
            @RequestParam(value = "actualTaskId", required = false) Long actualTaskId,
            final HttpServletRequest request) {

        getModel().bind(request);

        if (applicationId == null && actualTaskId == null) {
            applicationId = (Long) request.getSession().getAttribute("appNo");
            actualTaskId = (Long) request.getSession().getAttribute("actualTaskId");
        }

        this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
        this.getModel().getWorkflowActionDto().setApplicationId(applicationId);
        ServiceMaster serviceMaster = smService.getServiceByShortName(
                MainetConstants.MRM.SERVICE_CODE.MRG,
                UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setServiceMaster(serviceMaster);
        this.getModel().getMarriageDTO().setServiceId(serviceMaster.getSmServiceId());
        this.getModel().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().getMarriageDTO().setDeptId(serviceMaster.getTbDepartment().getDpDeptid());
        getModel().populateModel(this.getModel(), null,
                applicationId != null ? applicationId : this.getModel().getMarriageDTO().getApplicationId(), "E");

        // set husband photo and wife photo
        final String folderPath = FileUploadUtility.getCurrent().getExistingFolderPath();
        if (folderPath != null) {
            if (StringUtils.isNotEmpty(this.getModel().getMarriageDTO().getHusbandDTO().getCapturePhotoPath())) {
                this.getModel().getMarriageDTO().setHusbPhoPath(Utility.downloadedFileUrl(
                        this.getModel().getMarriageDTO().getHusbandDTO().getCapturePhotoPath()
                                + MainetConstants.FILE_PATH_SEPARATOR
                                + this.getModel().getMarriageDTO().getHusbandDTO().getCapturePhotoName(),
                        folderPath,
                        FileNetApplicationClient.getInstance()));
            }
            if (StringUtils.isNotEmpty(this.getModel().getMarriageDTO().getWifeDTO().getCapturePhotoPath())) {
                this.getModel().getMarriageDTO().setWifePhoPath(Utility.downloadedFileUrl(
                        this.getModel().getMarriageDTO().getWifeDTO().getCapturePhotoPath()
                                + MainetConstants.FILE_PATH_SEPARATOR
                                + this.getModel().getMarriageDTO().getWifeDTO().getCapturePhotoName(),
                        folderPath,
                        FileNetApplicationClient.getInstance()));
            }
        }

        this.getModel().setStatus("APPROVED");
        this.getModel().setPrintBT("N");
        Boolean DSCLENV = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL);
        if (DSCLENV) {
            return new ModelAndView("marriageCertificateDSCL", MainetConstants.FORM_NAME, this.getModel());
        } else {
            return new ModelAndView("marriageCertificate", MainetConstants.FORM_NAME, this.getModel());
        }

    }

    @RequestMapping(params = "generateSerialNo", method = RequestMethod.POST)
    public String generateSerialNo(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        final MarriageRegistrationModel marriageModel = this.getModel();

        // generate serial No and update volume
        // here generate serial no if already exist than don't
        if (StringUtils.isEmpty(this.getModel().getMarriageDTO().getSerialNo())) {
            SequenceConfigMasterDTO configMasterDTO = null;
            configMasterDTO = seqGenFunctionUtility.loadSequenceData(this.getModel().getOrgId(),
                    this.getModel().getMarriageDTO().getDeptId(),
                    "TB_MRM_APPOINTMENT", "SERIAL_NO");
            if (configMasterDTO.getSeqConfigId() == null) {
                LOGGER.error("first configure the sequence master for Marriage Certificate Serial No");
            }
            CommonSequenceConfigDto commonSequenceConfigDto = new CommonSequenceConfigDto();
            String sequenceNo = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonSequenceConfigDto);
            if (sequenceNo == null) {
                LOGGER.error("Marriage Certificate Serial No Not Generated");
            }
            marriageModel.getMarriageDTO().setSerialNo(sequenceNo);
        }

        marriageService.saveMarriage(marriageModel.getMarriageDTO());

        httpServletRequest.getSession().setAttribute("appNo", this.getModel().getMarriageDTO().getApplicationId());
        httpServletRequest.getSession().setAttribute("actualTaskId", this.getModel().getWorkflowActionDto().getTaskId());
        getModel().sendSmsEmail(marriageModel, "MarriageCertificateGeneration.html",
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG);

        return "redirect:MarriageCertificateGeneration.html?showDetails";

    }

    @RequestMapping(params = "finalSaveAfterMarriageCertificate", method = RequestMethod.POST)
    public String finalSaveAfterMarriageCertificate(HttpServletRequest request) {
        this.getModel().bind(request);
        final MarriageRegistrationModel marriageModel = this.getModel();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // complete the BPM task of Marriage Certificate because BPM last task is letter generation

        // JsonViewObject responseObj = null;

        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setOrgId(orgId);
        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setIsFinalApproval(true);
        taskAction.setIsObjectionAppealApplicable(false);
        if (StringUtils.isNotBlank(marriageModel.getMarriageDTO().getApplicantDetailDto().getEmailId())) {
            taskAction.setEmpEmail(marriageModel.getMarriageDTO().getApplicantDetailDto().getEmailId());
        }
        taskAction.setApplicationId(marriageModel.getMarriageDTO().getApplicationId());
        taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        taskAction.setTaskId(getModel().getWorkflowActionDto().getTaskId());

        if (marriageService.executeApprovalWorkflowAction(taskAction, getModel().getServiceMaster().getSmServiceId(),
                "CONCLUDE")) {
            marriageModel.getMarriageDTO().setStatus("CONCLUDE");
            marriageModel.getMarriageDTO().setRegDate(new Date());
            marriageService.saveMarriage(marriageModel.getMarriageDTO());
            // responseObj = JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("Application Approved
            // successfully"));
        } else {
            // responseObj = JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("Application Failed"));
        }

        /*
         * request.getSession().setAttribute("appNo", this.getModel().getMarriageDTO().getApplicationId());
         * request.getSession().setAttribute("actualTaskId", this.getModel().getWorkflowActionDto().getTaskId());
         */

        return "redirect:MarriageCertificateGeneration.html?showDetails";

    }

    @RequestMapping(method = RequestMethod.POST, params = "generateLOIForMRM")
    public ModelAndView generateLOIForMRM(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        MarriageRegistrationModel model = this.getModel();
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
        taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmpname());
        taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setDateOfAction(new Date());
        taskAction.setCreatedDate(new Date());
        taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        taskAction.setIsFinalApproval(MainetConstants.FAILED);
        taskAction.setIsObjectionAppealApplicable(MainetConstants.FAILED);
        if (StringUtils.isNotBlank(model.getMarriageDTO().getApplicantDetailDto().getEmailId())) {
            taskAction.setEmpEmail(model.getMarriageDTO().getApplicantDetailDto().getEmailId());
        }
        taskAction.setApplicationId(model.getMarriageDTO().getApplicationId());
        taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
        taskAction.setTaskId(getModel().getWorkflowActionDto().getTaskId());
        taskAction.setLoiDetails(getModel().getWorkflowActionDto().getLoiDetails());
        marriageService.executeApprovalWorkflowAction(taskAction, getModel().getServiceMaster().getSmServiceId(), "LOI");
        return jsonResult(JsonViewObject
                .successResult(ApplicationSession.getInstance().getMessage("mrm.loi.generate.success", new Object[] {
                        getModel().getWorkflowActionDto().getLoiDetails().get(0).getLoiNumber() })));
    }

    @RequestMapping(params = "openMarriageCharge", method = { RequestMethod.POST })
    public ModelAndView openMarriageCharge(@RequestParam(value = "marId", required = false) Long marId,
            @RequestParam(value = "type", required = false) String type,
            final HttpServletRequest request) {
        this.getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster serviceDto = smService
                .getServiceByShortName(MainetConstants.MRM.SERVICE_CODE.MRG, orgId);
        if (serviceDto != null) {
            this.getModel().setServiceMaster(serviceDto);
            this.getModel().setServiceId(serviceDto.getSmServiceId());
            this.getModel().setServiceName(serviceDto.getSmServiceName());
        }
        this.getModel().setOrgId(orgId);
        getModel().populateModel(this.getModel(), marId, null, "V");
        this.getModel().setPrintBT("Y");
        return new ModelAndView("MarriageCharge", MainetConstants.CommonConstants.COMMAND, this.getModel());

    }

    @RequestMapping(params = "getCharges", method = { RequestMethod.POST })
    public ModelAndView getCharges(@RequestParam(value = "marId", required = false) Long marId,
            final HttpServletRequest request) {
        this.getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster serviceDto = smService
                .getServiceByShortName(MainetConstants.MRM.SERVICE_CODE.MRG, orgId);
        if (serviceDto != null) {
            this.getModel().setServiceMaster(serviceDto);
            this.getModel().setServiceId(serviceDto.getSmServiceId());
            this.getModel().setServiceName(serviceDto.getSmServiceName());
        }
        this.getModel().setOrgId(orgId);
        // getModel().populateModel(this.getModel(), marId, null, "V");

        Map<Long, Double> loiCharges = marriageService.getLoiCharges(orgId, serviceDto.getSmServiceId(),
                serviceDto.getSmShortdesc(),
                this.getModel().getMarriageDTO().getMarDate(), this.getModel().getMarriageDTO().getAppDate());

        // get charge id and amount
        final ChargeDetailDTO chargedto = new ChargeDetailDTO();
        final List<ChargeDetailDTO> detailDTOs = new ArrayList<>();
        for (final Entry<Long, Double> loiCharge : loiCharges.entrySet()) {
            chargedto.setChargeCode(loiCharge.getKey());
            String taxDesc = ApplicationContextProvider.getApplicationContext()
                    .getBean(TbTaxMasService.class).findTaxDescByTaxIdAndOrgId(loiCharge.getKey(),
                            UserSession.getCurrent().getOrganisation().getOrgid());
            chargedto.setChargeDescEng(taxDesc);
            chargedto.setChargeDescReg(taxDesc);
            chargedto.setChargeAmount(loiCharge.getValue() * getModel().getMarriageDTO().getNoOfCopies());
            detailDTOs.add(chargedto);
        }

        getModel().setAmountToPay(chargedto.getChargeAmount());
        getModel().setChargesInfo(detailDTOs);
        getModel().getOfflineDTO().setAmountToShow(getModel().getAmountToPay());
        getModel().setApplicationChargeApplFlag(MainetConstants.FlagN);
        getModel().setPayableFlag("Y");
        return new ModelAndView("MarriageChargeValidn", MainetConstants.CommonConstants.COMMAND, this.getModel());

    }

    // hit common CHALLAN and Cash Payment method
    @RequestMapping(params = "saveCommonPayment", method = RequestMethod.POST)
    public ModelAndView saveCommonPayment(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        final MarriageRegistrationModel model = getModel();
        ModelAndView mv = null;
        if (model.validatePayment()) {
            final CommonChallanDTO offline = model.getOfflineDTO();
            // Set Service Master data for Marriage Certificate (MRC)
            ServiceMaster serviceMaster = smService.getServiceByShortName("MRC",
                    UserSession.getCurrent().getOrganisation().getOrgid());
            this.getModel().setServiceMaster(serviceMaster);
            this.getModel().getMarriageDTO().setServiceId(serviceMaster.getSmServiceId());
            model.setAndSaveChallanDto(offline, model.getMarriageDTO());
            if ((offline.getOnlineOfflineCheck() != null)
                    && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT.OFFLINE)) {
                return jsonResult(JsonViewObject
                        .successResult(getApplicationSession().getMessage("continue.forchallan")));
            } else {
                return jsonResult(JsonViewObject
                        .successResult(getApplicationSession().getMessage("mrm.generate.certificate")));
            }

        }
        mv = new ModelAndView("MarriageChargeValidn", MainetConstants.FORM_NAME, getModel());
        return mv;
    }

    @RequestMapping(params = "printCertificate", method = { RequestMethod.POST })
    public ModelAndView printCertificate(@RequestParam(value = "marId", required = false) Long marId,
            @RequestParam(value = "printType", required = false) String printType,
            final HttpServletRequest request) {
        this.getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        ServiceMaster serviceDto = smService
                .getServiceByShortName(MainetConstants.MRM.SERVICE_CODE.MRG, orgId);
        if (serviceDto != null) {
            this.getModel().setServiceMaster(serviceDto);
            this.getModel().setServiceId(serviceDto.getSmServiceId());
            this.getModel().setServiceName(serviceDto.getSmServiceName());
        }
        this.getModel().setOrgId(orgId);
        getModel().populateModel(this.getModel(), marId, null, "V");
        // set husband photo and wife photo
        final String folderPath = FileUploadUtility.getCurrent().getExistingFolderPath();
        if (folderPath != null) {
            if (StringUtils.isNotEmpty(this.getModel().getMarriageDTO().getHusbandDTO().getCapturePhotoPath())) {
                this.getModel().getMarriageDTO().setHusbPhoPath(Utility.downloadedFileUrl(
                        this.getModel().getMarriageDTO().getHusbandDTO().getCapturePhotoPath()
                                + MainetConstants.FILE_PATH_SEPARATOR
                                + this.getModel().getMarriageDTO().getHusbandDTO().getCapturePhotoName(),
                        folderPath,
                        FileNetApplicationClient.getInstance()));
            }
            if (StringUtils.isNotEmpty(this.getModel().getMarriageDTO().getWifeDTO().getCapturePhotoPath())) {
                this.getModel().getMarriageDTO().setWifePhoPath(Utility.downloadedFileUrl(
                        this.getModel().getMarriageDTO().getWifeDTO().getCapturePhotoPath()
                                + MainetConstants.FILE_PATH_SEPARATOR
                                + this.getModel().getMarriageDTO().getWifeDTO().getCapturePhotoName(),
                        folderPath,
                        FileNetApplicationClient.getInstance()));
            }
        }

        this.getModel().setPrintBT("Y");
        // T#121452
        Boolean DSCLENV = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL);
        if (DSCLENV) {
            return new ModelAndView("marriageCertificateDSCL", MainetConstants.FORM_NAME, this.getModel());
        } else {
            return new ModelAndView("marriageCertificate", MainetConstants.FORM_NAME, this.getModel());
        }

    }

    @RequestMapping(params = "signCertificate", method = RequestMethod.POST)
    public @ResponseBody String signCertificate(@RequestParam("proAssNo") String proAssNo,
            @RequestParam("certificateNo") String certificateNo, @RequestParam("applicationNo") Long applicationNo) {

        List<AttachDocs> attach = new ArrayList<>();
        Path result = null;
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        try {
            LOGGER.info("-----------------------------> signCertificate method started");
            attach = attachDocsService.findByCode(orgId, applicationNo.toString());

            if (attach.isEmpty()) {
                String srcPath = Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME
                        + MainetConstants.FILE_PATH_SEPARATOR + applicationNo + MainetConstants.PDF_EXTENSION;
                boolean generatePdf = marriageService.generateCertificate(srcPath, applicationNo,
                        UserSession.getCurrent().getOrganisation().getOrgid());
                if (!generatePdf)
                    return "e";
                String destPath = ApplicationSession.getInstance().getMessage("dsc.source") + MainetConstants.FILE_PATH_SEPARATOR
                        + applicationNo + MainetConstants.FILE_PATH_SEPARATOR + applicationNo + MainetConstants.PDF_EXTENSION;
                Files.createDirectories(Paths.get(destPath).getParent());
                LOGGER.info("----------------------------Source Path>" + srcPath);
                LOGGER.info("----------------------------Destination Path>" + destPath);
                try {
                    result = Files.copy(Paths.get(srcPath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
                    LOGGER.info("----------------------------Result Path>" + result);
                    if (result == null) {
                        return "e";
                    }
                } catch (IOException e) {
                    LOGGER.error(e);
                    return "e";
                }
            } else {
                return "false";
            }
        } catch (Exception e) {
            LOGGER.error(e);
            return "e";
        }
        LOGGER.info("-----------------------------> signCertificate method ended");
        return "true";
    }

    @RequestMapping(params = "saveSignedCertificate", method = RequestMethod.POST)
    public @ResponseBody String saveSignedCertificate(@RequestParam("applicationNo") Long applicationNo,
            @RequestParam("destPath") String destPath) {

        try {
            LOGGER.info("-----------------------------> saveSignedCertificate method started");
            final FileUploadClientService fileUploadDownloadService = new FileUploadClientService();

            String finaldestPath = (ApplicationSession.getInstance().getMessage("dsc.target")
                    + MainetConstants.operator.DOUBLE_BACKWARD_SLACE + applicationNo
                    + destPath.substring(destPath.lastIndexOf(MainetConstants.operator.DOUBLE_BACKWARD_SLACE)))
                            .replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.FORWARD_SLACE);

            LOGGER.info("----------------------------Destination Path>" + finaldestPath);
            List<DocumentDetailsVO> documentList = new ArrayList<DocumentDetailsVO>();
            DocumentDetailsVO documentList1 = new DocumentDetailsVO();
            FileUploadDTO uploadDTO = new FileUploadDTO();
            uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            uploadDTO.setStatus(MainetConstants.FlagA);
            uploadDTO.setDepartmentName(MainetConstants.MRM.MRM_DEPT_CODE);
            uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            uploadDTO.setIdfId(applicationNo.toString());
            byte[] bufa = fileUploadDownloadService.downloadFile(finaldestPath);
            Base64 base64 = new Base64();
            String pdfDoc = base64.encodeToString(bufa);
            documentList1.setDocumentName(applicationNo + MainetConstants.PDF_EXTENSION);
            documentList1.setDocumentByteCode(pdfDoc);
            documentList.add(documentList1);
            fileUpload.doMasterFileUpload(documentList, uploadDTO);
        } catch (Exception e) {
            LOGGER.error(e);
            return "false";
        }
        LOGGER.info("-----------------------------> saveSignedCertificate method ended");
        return "true";
    }

}
