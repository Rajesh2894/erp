package com.abm.mainet.tradeLicense.ui.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.FileUploadClientService;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IDmsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.TradeLicenseReportFormatModel;

@Controller
@RequestMapping("/TradeLicenseReportFormat.html")
public class TradeLicenseReportFormatController extends AbstractFormController<TradeLicenseReportFormatModel> {
	
	private static final Logger LOGGER = Logger.getLogger(TradeLicenseReportFormatController.class);

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	ICFCApplicationMasterDAO cfcApplicationMasterDAO;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private TbApprejMasService tbApprejMasService;

	@Autowired
	ICFCApplicationMasterService cfcApplicationService;

	@Autowired
	private TbLoiMasService tbLoiMasService;
	
	@Autowired
	private IAttachDocsService attachDocsService;
	
	@Autowired
	private IDmsService iDmsService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		LookUp trasitStatuslookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS",
				UserSession.getCurrent().getOrganisation());

		this.getModel()
				.setTradeMasterDetailDTO(tradeLicenseApplicationService.getActiveApplicationIdByOrgId(orgId).stream()
						.filter(k -> k.getTrdStatus() == trasitStatuslookUp.getLookUpId())
						.collect(Collectors.toList()));
		this.getModel().setOrgId(orgId);
		ModelAndView mv = null;
		mv = new ModelAndView(MainetConstants.TradeLicense.TRADE_LICENSE_PRINT, MainetConstants.FORM_NAME, getModel());
		return mv;

	}

	/**
	 * view trade license certificate
	 * 
	 * @param applicationId
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.VIEW_LICENSE, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView viewLicense(@RequestParam("trdLicno") String trdLicno,
			final HttpServletRequest request) {
		getModel().bind(request);
		final TradeLicenseReportFormatModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		TradeMasterDetailDTO masDto = this.getModel().getTradeDetailDTO();
		TradeMasterDetailDTO tradeMasterDetailDTO = null;

		try {
			//125445-to get license details from data entry suite also
			tradeMasterDetailDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);	
			//tradeMasterDetailDTO = tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(masDto.getApmApplicationId());			
		} catch (Exception e) {
			ModelAndView mv = defaultMyResult();
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			model.addValidationError("Invalid Application No");
			return mv;

		}
		model.setTradeDetailDTO(tradeMasterDetailDTO);
		Date licenseEndDate = tradeMasterDetailDTO.getTrdLictoDate();
		if (licenseEndDate != null) {
			tradeMasterDetailDTO.setLicenseDateDesc(Utility.dateToString(licenseEndDate));
		}
		model.setDateDesc(Utility.dateToString(tradeMasterDetailDTO.getCreatedDate()));
		List<TradeLicenseOwnerDetailDTO> newOwnerDetailDTOList = model.getTradeDetailDTO()
				.getTradeLicenseOwnerdetailDTO().stream().filter(k -> k.getTroPr() != null)
				.filter(k -> k.getTroPr().equalsIgnoreCase(MainetConstants.FlagA)).collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(newOwnerDetailDTOList)) {
			List<CFCAttachment> imgList = checklistVerificationService
					.getDocumentUploadedByRefNo(newOwnerDetailDTOList.get(0).getTroId().toString(), orgId);
			model.setDocumentList(imgList);
			if (!imgList.isEmpty() && imgList != null) {
				model.setImagePath(getPropImages(imgList.get(0)));
			}
		}
		Long artId = 0l;
		final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.REM,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp Lookup1 : lookUpList) {

			if (Lookup1.getLookUpCode().equals(PrefixConstants.WATERMODULEPREFIX.APP)) {
				artId = Lookup1.getLookUpId();
			}

		}
				
		if (tradeMasterDetailDTO.getApmApplicationId() != null) {
		TbCfcApplicationMstEntity cfcApplicationDetails = cfcApplicationMasterDAO
				.getCFCApplicationByApplicationId(tradeMasterDetailDTO.getApmApplicationId(), orgId);
		Long smServiceId = cfcApplicationService.getServiceIdByApplicationId(tradeMasterDetailDTO.getApmApplicationId(), orgId);

		model.setCfcEntity(cfcApplicationDetails);
		model.setDateDesc(Utility.dateToString(cfcApplicationDetails.getApmApplicationDate()));
		tradeMasterDetailDTO.setCreatedDateDesc(Utility.dateToString(new Date()));
	 	
		/*
		 * Date licenseIssuanceDate = tradeMasterDetailDTO.getTrdLicisdate(); if
		 * (licenseIssuanceDate != null) {
		 */
		 model.setIssuanceDateDesc(Utility.dateToString(new Date()));
		
		List<TbLoiMas> tbLoiMas = null;
		try {
			tbLoiMas = tbLoiMasService.getloiByApplicationId(tradeMasterDetailDTO.getApmApplicationId(), smServiceId,
					UserSession.getCurrent().getOrganisation().getOrgid());
			model.setTbLoiMasList(tbLoiMas);
			model.setLoiDateDesc(Utility.dateToString(tbLoiMas.get(0).getLoiDate()));
			List<TradeMasterDetailDTO> dto = tradeLicenseApplicationService.getpaymentMode(orgId,
					tbLoiMas.get(0).getLoiNo());
			model.setRmRcptno(dto.get(0).getRmRcptno());
			model.setRmAmount(dto.get(0).getRmAmount());
			model.setPayMode(dto.get(0).getCpdDesc());
		} catch (Exception e) {
			// throw new FrameworkException("unable to fetch LOI details", e);
		 }
           List<TbApprejMas> apprejMasList = tbApprejMasService.findByRemarkType(smServiceId, artId);
             model.setApprejMasList(apprejMasList);
	     }	
		// to disable license fee details in case of data entry suit application
		if(tradeMasterDetailDTO.getTrdEty().equals(MainetConstants.FlagD) && tradeMasterDetailDTO.getApmApplicationId() == null) {
			this.getModel().setLoidetFlag(MainetConstants.FlagY);
		}else {
			this.getModel().setLoidetFlag(MainetConstants.FlagN);
		}


		model.setLicenseFromDateDesc(Utility.dateToString(tradeMasterDetailDTO.getTrdLicfromDate()));
		return new ModelAndView(MainetConstants.TradeLicense.TRADE_LICENSE_REPORT_FORMAT, MainetConstants.FORM_NAME,
				this.getModel());

	}

	private String getPropImages(final CFCAttachment attachDocs) {

		new ArrayList<String>();
		final UUID uuid = UUID.randomUUID();
		final String randomUUIDString = uuid.toString();
		final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
				+ MainetConstants.FILE_PATH_SEPARATOR + UserSession.getCurrent().getOrganisation().getOrgid()
				+ MainetConstants.FILE_PATH_SEPARATOR + randomUUIDString + MainetConstants.FILE_PATH_SEPARATOR
				+ "PROPERTY";
		final String path1 = attachDocs.getAttPath();
		final String name = attachDocs.getAttFname();
		final String data = Utility.downloadedFileUrl(path1 + MainetConstants.FILE_PATH_SEPARATOR + name, outputPath,
				FileNetApplicationClient.getInstance());
		return data;
	}

	/* Method for to Print License Report */
	/*
	 * @RequestMapping(params = "viewLicense", method = RequestMethod.POST) public
	 * ModelAndView viewLicense(@RequestParam("applicationId") Long applicationId,
	 * final HttpServletRequest request, final HttpServletResponse
	 * httpServletResponse) { final TradeLicenseReportFormatModel model =
	 * getModel(); getModel().bind(request); List<TradeLicensePrintReportDTO>
	 * dtoList=new ArrayList<>(); TradeMasterDetailDTO
	 * masterDTO=tradeLicenseApplicationService.
	 * getTradeLicenseWithAllDetailsByApplicationId(1219010300007l);
	 * TbCfcApplicationMstEntity cfcApplicationDetails =
	 * cfcApplicationMasterDAO.getCFCApplicationByApplicationId(masterDTO.
	 * getApmApplicationId(),UserSession.getCurrent().getOrganisation().getOrgid());
	 * dtoList =
	 * settradeReportDto(getModel().getTradeLicensePrintReportDTO(),masterDTO);
	 * 
	 * final Map<String, Object> map = new HashMap<>(); final String subReportSource
	 * = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME +
	 * MainetConstants.FILE_PATH_SEPARATOR; map.put("SUBREPORT_DIR",
	 * subReportSource); String jrxmlName = ""; jrxmlName =
	 * "TradeLicensePrint.jrxml"; final String jrxmlFileLocation =
	 * Filepaths.getfilepath() + "jasperReport" +
	 * MainetConstants.FILE_PATH_SEPARATOR + jrxmlName; final String fileName =
	 * ReportUtility.generateReportFromCollectionUtility(request,
	 * httpServletResponse, jrxmlFileLocation, map, dtoList,
	 * UserSession.getCurrent().getEmployee().getEmpId()); if
	 * (!fileName.equals(MainetConstants.SERVER_ERROR)) {
	 * getModel().setFilePath(fileName); }
	 * getModel().setRedirectURL("AdminHome.html"); return new
	 * ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, "command", model); }
	 */

	/*
	 * private List<TradeLicensePrintReportDTO> settradeReportDto(
	 * List<TradeLicensePrintReportDTO> tradeLicensePrintReportDTO,
	 * TradeMasterDetailDTO masterDTO) { TradeLicensePrintReportDTO dto=new
	 * TradeLicensePrintReportDTO();
	 * 
	 * ApplicationSession appsession=ApplicationSession.getInstance();
	 * 
	 * dto.setApplicationDateL(appsession.getMessage("jasper.content1"));
	 * dto.setApplicationNumberL(appsession.getMessage("jasper.content2"));
	 * dto.setSewaKendraL(appsession.getMessage("jasper.content3"));
	 * dto.setNetAmountL(appsession.getMessage("jasper.content4"));
	 * 
	 * dto.setReportBody1(appsession.getMessage("jasper.content5", new Object[]
	 * {UserSession.getCurrent().getOrganisation().getoNlsOrgnameMar(),masterDTO.
	 * getTradeLicenseOwnerdetailDTO().get(0).getTroName(),masterDTO.
	 * getTradeLicenseOwnerdetailDTO().get(0).getTroMname(),masterDTO.getTrdBusnm(),
	 * masterDTO.getTrdBusadd(),masterDTO.getTrdLicisdate()}));
	 * 
	 * dto.setReportBody2(appsession.getMessage("jasper.content6"));
	 * dto.setFooterContent(appsession.getMessage("jasper.content12"));
	 * dto.setOrgNameV(UserSession.getCurrent().getOrganisation().getONlsOrgnameMar(
	 * )); dto.setApplicationNumberV(masterDTO.getApmApplicationId().toString());
	 * dto.setSewaKendraV(UserSession.getCurrent().getOrganisation().toString());
	 * //dto.setNetAmountL();
	 * dto.setApplicantNameV(masterDTO.getTradeLicenseOwnerdetailDTO().get(0).
	 * getTroName());
	 * dto.setFatherNameV(masterDTO.getTradeLicenseOwnerdetailDTO().get(0).
	 * getTroMname()); dto.setBusinessNameV(masterDTO.getTrdBusnm());
	 * dto.setBusinessAddressV(masterDTO.getTrdBusadd());
	 * dto.setLicenseDateV(masterDTO.getLicenseToDate());
	 * 
	 * tradeLicensePrintReportDTO.add(dto);
	 * 
	 * return tradeLicensePrintReportDTO;
	 * 
	 * }
	 */
	@RequestMapping(params = "PrintCertificate", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView printCertificater(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		ModelAndView mv = null;
		mv = new ModelAndView("PrintLicenseCertificate", MainetConstants.FORM_NAME, getModel());
		return mv;

	}

	@RequestMapping(params = "getLicenseDetail", method = RequestMethod.POST)
	public ModelAndView getMutationDetail(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "trdLicno") String trdLicno) {
		this.getModel().bind(httpServletRequest);
		ModelAndView mv = new ModelAndView("PrintLicenseCertificateValidn", MainetConstants.FORM_NAME, this.getModel());
		List<TradeMasterDetailDTO> masDto = new ArrayList<>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().getTradeDetailDTO().setTrdLicno(trdLicno);
		TradeMasterDetailDTO masDto1 = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);
		if (masDto1 == null) {
			this.getModel().addValidationError(getApplicationSession().getMessage("property.valid.mutation"));
		} else {
			List<AttachDocs> attach = new ArrayList<>();
			attach = (attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
					(masDto1.getTrdLicno()+MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid())));
			if (!attach.isEmpty()) {
				List<AttachDocs> attachsList = new ArrayList<>();
				attachsList.add(attach.get(attach.size() - 1));
				this.getModel().setAttachDocsList(attachsList);
			}
			masDto.add(masDto1);
			httpServletRequest.setAttribute("data", masDto);
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

		return mv;

	}

	@RequestMapping(params = "PrintLicenseCertificate", method = RequestMethod.POST)
	public @ResponseBody String viewMutationPrintCertificate(@RequestParam("trdLicno") String trdLicno) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (orgId != null && StringUtils.isNotBlank(trdLicno)) {
			return ServiceEndpoints.BIRT_REPORT_URL + "=RP_LicenseCertificate_DMS.rptdesign&RP_ORGID=" + orgId + "&RP_LicenseNo=" + trdLicno;
		} else {
			return "f";
		}

	}

	@RequestMapping(params = "EmailCertificate", method = RequestMethod.POST)
	public @ResponseBody String emailCertificate(@RequestParam("trdLicno") String trdLicno) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		List<AttachDocs> attachsList = new ArrayList<>();
		List<AttachDocs> attach = new ArrayList<>();
		Set<File> fileDetails = new LinkedHashSet<>();
		List<File> filesForAttach = new ArrayList<File>();
		String pdfNameGenarated = null;
		try {
			TradeMasterDetailDTO masDto1 = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);
			attach = (attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
					(trdLicno +MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid())));
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
				String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "=" + ApplicationSession.getInstance().getMessage("trade.birtName.license")
						+ "&__format=pdf&RP_ORGID=" + orgId + "&RP_LicenseNo="
						+ trdLicno;
				String filePath = Utility.getFilePathForPdfUsingBirt(URL);
				final File file = new File(filePath);
				fileDetails.add(file);
			}
			filesForAttach.addAll(fileDetails);
			for (int i = 0; i < masDto1.getTradeLicenseOwnerdetailDTO().size(); i++) {
				if (!masDto1.getTradeLicenseOwnerdetailDTO().get(i).getTroEmailid().isEmpty()) {
					final SMSAndEmailDTO dto = new SMSAndEmailDTO();
					dto.setEmail(masDto1.getTradeLicenseOwnerdetailDTO().get(i).getTroEmailid());
					dto.setMobnumber(masDto1.getTradeLicenseOwnerdetailDTO().get(i).getTroMobileno());
					dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
					dto.setFilesForAttach(filesForAttach);
					dto.setAppNo(trdLicno);
					ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
							"ML", "TradeLicenseReportFormat.html", PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG,
							dto, organisation, 1);
				}
			}
			return "t";
		} catch (Exception e) {
			return "f";
		}
	}

	@RequestMapping(params = "uploadSignCertificate", method = RequestMethod.POST)
	public @ResponseBody ModelAndView uploadSignCertificate(@RequestParam("trdLicno") String trdLicno) {
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setTrdLicno(trdLicno);
		return new ModelAndView("uploadLicenseCertificate", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "saveSignCertificate", method = RequestMethod.POST)
	public @ResponseBody String saveSignCertificate(@RequestParam("trdLicno") String trdLicno) {
		this.getModel().setCheckList(ApplicationContextProvider.getApplicationContext()
				.getBean(IFileUploadService.class).setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		if (this.getModel().getCheckList().isEmpty() || this.getModel().getCheckList() == null) {
			return "false";
		} else {
			try {
				FileUploadDTO uploadDTO = new FileUploadDTO();
				uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				uploadDTO.setStatus(MainetConstants.FlagA);
				uploadDTO.setDepartmentName("NTL");
				uploadDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
				uploadDTO.setIdfId(trdLicno +MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid());
				fileUpload.doMasterFileUpload(this.getModel().getCheckList(), uploadDTO);

			} catch (Exception e) {
				return "e";
			}
		}
		return "true";
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
			String srcPath = Filepaths.getfilepath() + MainetConstants.PDFFOLDERNAME
					+ MainetConstants.FILE_PATH_SEPARATOR + applicationNo + MainetConstants.PDF_EXTENSION;
			TradeMasterDetailDTO masDto1 = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(certificateNo, orgId);
			boolean generatePdf=tradeLicenseApplicationService.generateCertificate(srcPath,applicationNo,certificateNo,orgId,masDto1);
				/*
				 * if(!generatePdf) return "e";
				 */
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
			uploadDTO.setDepartmentName("NTL");
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
}
