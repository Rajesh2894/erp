package com.abm.mainet.property.ui.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.FileUploadClientService;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IDmsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.PropertyTransferMasterDto;
import com.abm.mainet.property.dto.PropertyTransferOwnerDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.AssesmentMastService;
import com.abm.mainet.property.service.MutationService;
import com.abm.mainet.property.service.PropertyTransferService;
import com.abm.mainet.property.ui.model.MutationModel;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Controller
@RequestMapping("/MutationPrintCertificate.html")
public class MutationPrintCertificateController extends AbstractFormController<MutationModel> {
	
	@Autowired
	private MutationService mutationService;

	@Autowired
	private PropertyTransferService propertyTransferService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private IAttachDocsService attachDocsService;

	@Autowired
	private IDmsService iDmsService;
	
	@Autowired
    private ISMSAndEmailService ismsAndEmailService;
	
    @Autowired
	private AssesmentMastService assesmentMastService;
	
	private static final Logger LOGGER = Logger.getLogger(MutationPrintCertificateController.class);


	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) throws Exception {
		sessionCleanup(request);
		return defaultResult();

	}

	@RequestMapping(params = "getMutationDetail", method = RequestMethod.POST)
	public ModelAndView getMutationDetail(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "propNo") String propNo,
			@RequestParam(value = "oldPropNo") String oldPropNo,
			@RequestParam(value = "applicationNo") Long applicationNo) {
		this.getModel().bind(httpServletRequest);
		
		ModelAndView mv = new ModelAndView("MutationPrintCertificateValidn", MainetConstants.FORM_NAME,
				this.getModel());
		ProvisionalAssesmentMstDto assMst1 = new ProvisionalAssesmentMstDto();
		assMst1.setApmApplicationId(applicationNo);
		assMst1.setAssNo(propNo);
		assMst1.setAssOldpropno(oldPropNo);
		this.getModel().setProvisionalAssesmentMstDto(assMst1);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<PropertyTransferMasterDto> assMst = mutationService.getMutationData(propNo, oldPropNo, applicationNo,
				orgId);
		if (assMst.isEmpty()) {
			this.getModel().addValidationError(getApplicationSession().getMessage("property.valid.mutation"));
		} else {
			List<AttachDocs> attach = new ArrayList<>();
			for (int i = 0; i <= assMst.size() - 1; i++) {
				attach = (attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
						assMst.get(i).getApmApplicationId().toString()));
				if (!attach.isEmpty()) {
					List<AttachDocs> attachsList = new ArrayList<>();
					attachsList.add(attach.get(attach.size() - 1));
					assMst.get(i).setAttachDocsList(attachsList);
				}
			}
			httpServletRequest.setAttribute("data", assMst);
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

		return mv;

	}		

	@RequestMapping(params = "MutationPrintReceipt", method = RequestMethod.POST)
	public @ResponseBody String viewMutationPrintReceipt(@RequestParam("proAssNo") String proAssNo,
			@RequestParam("receiptNo") Long receiptNo) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (orgId != null && receiptNo != null) {
			return ServiceEndpoints.BIRT_REPORT_URL + "=RP_PropertyMutationReceipt.rptdesign&RP_ORGID=" + orgId
					+ "&RP_RCPTNO=" + receiptNo;
		} else {
			return "f";
		}

	}

	@RequestMapping(params = "MutationPrintCertificate", method = RequestMethod.POST)
	public @ResponseBody String viewMutationPrintCertificate(@RequestParam("proAssNo") String proAssNo,
			@RequestParam("certificateNo") String certificateNo) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (StringUtils.isNotBlank(proAssNo) && orgId != null && StringUtils.isNotBlank(certificateNo)) {
			return ServiceEndpoints.BIRT_REPORT_URL + "=RP_MutationCertificate.rptdesign&RP_ORGID=" + orgId
					+ "&RP_PropNo=" + proAssNo + "&RP_CertNo=" + certificateNo;
		} else {
			return "f";
		}

	}
		
	@RequestMapping(params = "EmailCertificate", method = RequestMethod.POST)
	public @ResponseBody String emailCertificate(@RequestParam("proAssNo") String proAssNo,
			@RequestParam("certificateNo") String certificateNo, @RequestParam("applicationNo") Long applicationNo)  {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		List<AttachDocs> attachsList = new ArrayList<>();
		List<AttachDocs> attach = new ArrayList<>();
		Set<File> fileDetails = new LinkedHashSet<>();
		List<File> filesForAttach = new ArrayList<File>();
		String pdfNameGenarated = null;
		try {
			PropertyTransferMasterDto dto1 = propertyTransferService.getPropTransferMstByAppId(orgId, applicationNo);
			attach = (attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
					applicationNo.toString()));
			if (!attach.isEmpty()) {
				attachsList.add(attach.stream().collect(Collectors.toList()).get(attach.size() - 1));
				final byte[] byteArray = iDmsService.getDocumentById(attachsList.get(0).getDmsDocId());
				pdfNameGenarated = Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME
						+ MainetConstants.FILE_PATH_SEPARATOR + "BirtReports - " + Utility.getTimestamp()
						+ MainetConstants.PDF_EXTENSION;
				final File file = new File(pdfNameGenarated);
				FileOutputStream fos = null;
				fos = new FileOutputStream(file);
				fos.write(byteArray);
				fos.close();
				fileDetails.add(file);
			} else {
				String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "="
						+ ApplicationSession.getInstance().getMessage("property.birtName.mutationCertificate")
						+ "&__format=pdf&RP_ORGID=" + orgId + "&RP_PropNo=" + proAssNo + "&RP_CertNo=" + certificateNo;
				String filePath = Utility.getFilePathForPdfUsingBirt(URL);
				final File file = new File(filePath);
				fileDetails.add(file);
			}
			filesForAttach.addAll(fileDetails);
			for (int i = 0; i < dto1.getPropTransferOwnerList().size(); i++) {
				if (!dto1.getPropTransferOwnerList().get(i).geteMail().isEmpty()) {
					final SMSAndEmailDTO dto = new SMSAndEmailDTO();
					dto.setEmail(dto1.getPropTransferOwnerList().get(i).geteMail());
					dto.setMobnumber(dto1.getPropTransferOwnerList().get(i).getMobileno());
					dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
					dto.setFilesForAttach(filesForAttach);
					dto.setPropertyNo(proAssNo);
					dto.setAppNo((applicationNo).toString());
					ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
							MainetConstants.Property.PROP_DEPT_SHORT_CODE, "MutationAuthorization.html",
							PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, organisation, 1);
				}
			}
			return "t";
		} catch (Exception e) {
			return "f";
		}
	}

	@RequestMapping(params = "uploadSignCertificate", method = RequestMethod.POST)
	public @ResponseBody ModelAndView uploadSignCertificate(@RequestParam("applicationNo") Long applicationNo) {
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setApplicationNo(applicationNo);
		return new ModelAndView("UploadSignCertificate", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "saveSignCertificate", method = RequestMethod.POST)
	public @ResponseBody String saveSignCertificate(@RequestParam("applicationNo") Long applicationNo) {
		this.getModel().setCheckList(ApplicationContextProvider.getApplicationContext()
				.getBean(IFileUploadService.class).setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		if (this.getModel().getCheckList().isEmpty() || this.getModel().getCheckList() == null) {
			return "false";
		} else {
			try {
				FileUploadDTO uploadDTO = new FileUploadDTO();
				uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				uploadDTO.setStatus(MainetConstants.FlagA);
				uploadDTO.setDepartmentName(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
				uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				uploadDTO.setIdfId(applicationNo.toString());
				fileUpload.doMasterFileUpload(this.getModel().getCheckList(), uploadDTO);

			} catch (Exception e) {
				return "e";
			}
		}
		return "true";

	}
			
	@RequestMapping(params = "getMutationDetails", method = RequestMethod.POST)
	public ModelAndView getMutationDetailForPrintingCertificate(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "propNo") String propNo,
			@RequestParam(value = "oldPropNo") String oldPropNo,
			@RequestParam(value = "applicationNo") Long applicationNo,
			@RequestParam(value = "flatNo") String flatNo) {
		this.getModel().bind(httpServletRequest);
		ModelAndView mv = new ModelAndView("MutationPrintCertificateValidn", MainetConstants.FORM_NAME,
				this.getModel());
		ProvisionalAssesmentMstDto assMst = new ProvisionalAssesmentMstDto();
		assMst.setApmApplicationId(applicationNo);
		assMst.setAssNo(propNo);
		assMst.setAssOldpropno(oldPropNo);
		assMst.setFlatNo(flatNo);
		this.getModel().setProvisionalAssesmentMstDto(assMst);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<PropertyTransferMasterDto> transferMst = mutationService.getMutationDetails(propNo, oldPropNo,
				applicationNo,flatNo, orgId);
		if (transferMst.isEmpty()) {
			this.getModel().addValidationError(getApplicationSession().getMessage("property.valid.mutation"));
		} else {			
			httpServletRequest.setAttribute("data", transferMst);
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@RequestMapping(params = "MutationPrintCertificateSkdcl", method = RequestMethod.POST)
	public @ResponseBody String viewMutationPrintCertificateForSkdcl(@RequestParam("proAssNo") String proAssNo,
			@RequestParam("apmApplicationId") Long apmApplicationId, @RequestParam("flatNo") String flatNo) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (StringUtils.isNotBlank(proAssNo) && orgId != null && apmApplicationId != null) {

			SMSAndEmailDTO dto = new SMSAndEmailDTO();
			List<PropertyTransferMasterDto> transferMst = mutationService.getMutationDetails(proAssNo, null,
					apmApplicationId, flatNo, orgId);
			List<PropertyTransferMasterDto> transferMstNew = transferMst.stream()
					.filter(mast -> mast.getApmApplicationId().equals(apmApplicationId)).collect(Collectors.toList());
			StringBuilder owner = new StringBuilder();
			if (CollectionUtils.isNotEmpty(transferMst)) {
				for (PropertyTransferOwnerDto owners : transferMstNew.get(0).getPropTransferOwnerList()) {
					if (StringUtils.isBlank(owner.toString())) {
						owner.append(owners.geteMail());
					} else if (StringUtils.isNotBlank(owners.geteMail())) {
						owner.append(MainetConstants.operator.COMMA + owners.geteMail());
					}
				}
				dto.setEmail(owner.toString());
				dto.setMobnumber(transferMstNew.get(0).getPropTransferOwnerList().get(0).getMobileno());
			}
			dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			dto.setAppNo(apmApplicationId.toString());
			ismsAndEmailService.sendEmailSMS(MainetConstants.Property.PROP_DEPT_SHORT_CODE,
					"MutationPrintCertificate.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.COMPLETED_MESSAGE, dto,
					UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=Mutation_Certificate_Report.rptdesign&RP_ORGID="
					+ orgId + "&RP_AppId=" + apmApplicationId + "&RP_PropNo=" + proAssNo;
		} else {
			return "f";
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
		attach = attachDocsService.findByCode(orgId,applicationNo.toString());

		if (attach.isEmpty()) {
				/*
				 * String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "=" +
				 * ApplicationSession.getInstance().getMessage(
				 * "property.birtName.mutationCertificate") + "&__format=html&RP_ORGID=" + orgId
				 * + "&RP_PropNo=" + proAssNo + "&RP_CertNo=" + certificateNo;
				 */
			//String srcPath = Utility.getFilePathForHtmlBirt(URL, applicationNo);
			String srcPath = Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME
					+ MainetConstants.FILE_PATH_SEPARATOR + applicationNo + MainetConstants.PDF_EXTENSION;
			boolean generatePdf=mutationService.generateCertificate(srcPath,applicationNo,certificateNo,orgId);
			if(!generatePdf)
			return "e";
			String destPath =  ApplicationSession.getInstance().getMessage("dsc.source")+ MainetConstants.FILE_PATH_SEPARATOR + applicationNo+ MainetConstants.FILE_PATH_SEPARATOR + applicationNo + MainetConstants.PDF_EXTENSION;
			Files.createDirectories(Paths.get(destPath).getParent());
			LOGGER.info("----------------------------Source Path>"+ srcPath);
			LOGGER.info("----------------------------Destination Path>"+ destPath);
			try {
				result = Files.copy(Paths.get(srcPath), Paths.get(destPath),StandardCopyOption.REPLACE_EXISTING);
				LOGGER.info("----------------------------Result Path>"+ result);
				if (result == null) {
					return "e";
				}
			} catch (IOException e) {
				LOGGER.error(e);
				return "e";
			}
		}else {
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
	public @ResponseBody String saveSignedCertificate(@RequestParam("applicationNo") Long applicationNo ,@RequestParam("destPath") String destPath) {

		try {
			LOGGER.info("-----------------------------> saveSignedCertificate method started");
			 final FileUploadClientService fileUploadDownloadService = new FileUploadClientService();
			 byte[] bufa ;
			/*
			 * String destPath = ApplicationSession.getInstance().getMessage("dsc.target") +
			 * MainetConstants.FILE_PATH_SEPARATOR + applicationNo +
			 * MainetConstants.PDF_EXTENSION;
			 */
			 if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
			 String finaldestPath = (ApplicationSession.getInstance().getMessage("dsc.target")
						+ MainetConstants.operator.DOUBLE_BACKWARD_SLACE + applicationNo + destPath.substring(destPath.lastIndexOf(MainetConstants.operator.DOUBLE_BACKWARD_SLACE))).replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.FORWARD_SLACE);
			  LOGGER.info("----------------------------Destination Path>"+ finaldestPath);
			     bufa = fileUploadDownloadService.downloadFile(finaldestPath);
			 }else {
				 LOGGER.info("----------------------------Destination Path>"+ destPath);
				 bufa = fileUploadDownloadService.downloadFile(destPath);
			 }
				 
			List<DocumentDetailsVO> documentList = new ArrayList<DocumentDetailsVO>();
			DocumentDetailsVO documentList1 = new DocumentDetailsVO();
			FileUploadDTO uploadDTO = new FileUploadDTO();
			uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			uploadDTO.setStatus(MainetConstants.FlagA);
			uploadDTO.setDepartmentName(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
			uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			uploadDTO.setIdfId(applicationNo.toString());
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
	
	@RequestMapping(params = "MutationLoiPrintReceipt", method = RequestMethod.POST)
	public @ResponseBody String viewMutationLoiPrintReceipt(@RequestParam("proAssNo") String proAssNo,
			@RequestParam("receiptNo") Long receiptNo) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (orgId != null && receiptNo != null) {
			return ServiceEndpoints.BIRT_REPORT_URL + "=LOIMutationReceipt.rptdesign&RP_ORGID=" + orgId
					+ "&RP_RCPTNO=" + receiptNo;
		} else {
			return "f";
		}

	}
	
}
