package com.abm.mainet.bnd.ui.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.dto.TbBdCertCopyDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.bnd.service.IssuenceOfDeathCertificateService;
import com.abm.mainet.bnd.service.PrintBNDCertificateService;
import com.abm.mainet.bnd.ui.model.PrintCertificateModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.FileUploadClientService;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/PrintCertificate.html")
public class PrintCertificateController extends AbstractFormController<PrintCertificateModel>{ 

	@Autowired
	private PrintBNDCertificateService printBNDCertificateService;

	@Autowired
	private IdeathregCorrectionService ideathregCorrectionService;
	
	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
	@Autowired
	private IssuenceOfDeathCertificateService issuenceOfDeathCertificateService;
	
	@Autowired
	private IDeathRegistrationService iDeathRegistrationService;
	
	@Autowired
	private IAttachDocsService attachDocsService;
	
	@Autowired
	IFileUploadService fileUpload;
	
	private static final Logger LOGGER = Logger.getLogger(PrintCertificateController.class);
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		PrintCertificateModel printCertificateModel = this.getModel();
	    Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		return new ModelAndView("PrintCertificate",MainetConstants.FORM_NAME, getModel());
	}	
	@RequestMapping(params = "printbirthAndDeathCert", method = RequestMethod.POST,  produces = "Application/JSON")
	public @ResponseBody List<TbBdCertCopyDTO> SearchDeathCorrectionData(@RequestParam("applnId")final Long applnId,final HttpServletRequest request,final Model model ){
		getModel().bind(request);
		PrintCertificateModel printCertificateModel=this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<TbBdCertCopyDTO> list = printBNDCertificateService.getPrintCertificateDetails(applnId, orgId);
		if(!list.isEmpty()) {
			List<AttachDocs> attach = new ArrayList<>();
			attach = (attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
					applnId.toString()));
			if (!attach.isEmpty()) {
				list.get(0).setFilePath(attach.get(0).getAttPath()+ MainetConstants.operator.DOUBLE_BACKWARD_SLACE + attach.get(0).getAttFname());;
			}
		getModel().setTbBdCertCopyDTO(list.get(0));
		}
		return list;
	}	
	@SuppressWarnings("deprecation")
	@RequestMapping(params = "getCertificateData", method = RequestMethod.POST,  produces = "Application/JSON") 
	public ModelAndView  getCertificateDate(@RequestParam("ApplnId")final Long ApplnId,@RequestParam("certiNo")final String certiNo,@RequestParam("status")final String status, @RequestParam("bdId")final Long bdId, HttpServletRequest request,final Model model) {	
	    PrintCertificateModel deathRegCorrectionModel=this.getModel();
	    Long serviceId = getModel().getTbBdCertCopyDTO().getServiceId();
	    String serviceCode = printBNDCertificateService.findServiceBirthOrDeath(ApplnId, UserSession.getCurrent().getOrganisation().getOrgid(),serviceId);
	    
	    model.addAttribute("status",status);
	    model.addAttribute("bdId", bdId);
	    if(serviceCode.equals(BndConstants.IBC) || serviceCode.equals(BndConstants.BRC) ||  serviceCode.equals(BndConstants.INC) ) {
		   BirthRegistrationDTO birthRgDetail = issuenceOfBirthCertificateService.getBirthRegisteredAppliDetail(null,null,null,ApplnId.toString(),UserSession.getCurrent().getOrganisation().getOrgid());
		   TbServiceReceiptMasBean receipt=issuenceOfBirthCertificateService.getReceiptNo(ApplnId,birthRgDetail.getOrgId());
		   birthRgDetail.setBrCertNo(String.valueOf(certiNo));
		   if(receipt!=null && receipt.getApmApplicationId()!=null) {
		   birthRgDetail.setReceiptNo(receipt.getRmRcptno());
		   birthRgDetail.setReceiptDate(receipt.getRmDate());
		   if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && StringUtils.isNotBlank(receipt.getRmReceiptNo())) {
			   birthRgDetail.setRmReceiptNo(receipt.getRmReceiptNo());
			 }
		   
		   }
		   if(serviceCode.equals(BndConstants.BRC)){
           //code update for signature on certificate based in registration unit
		   if(birthRgDetail.getParentDetailDTO().getPdRegUnitId()!=null) {
		   LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(birthRgDetail.getParentDetailDTO().getPdRegUnitId());
		   birthRgDetail.setCpdRegUnit(lookup.getLookUpCode());
			   }
		   }if(serviceCode.equals(BndConstants.INC)) {
			 Long regUnitId=  issuenceOfBirthCertificateService.getUpdatedRegUnitByBrId(birthRgDetail.getBrId());
			 LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(regUnitId);
			 birthRgDetail.setCpdRegUnit(lookup.getLookUpCode());
		   }
		   deathRegCorrectionModel.setBirthRegDto(birthRgDetail);
			if (birthRgDetail.getAuthRemark()!=null && birthRgDetail.getAuthRemark().matches("^[0-9a-zA-Z\\\\s.?&!,-;() '\\\"////]*$"))
				deathRegCorrectionModel.setRemark(birthRgDetail.getAuthRemark());
			else
				deathRegCorrectionModel.setRemarkReg(birthRgDetail.getAuthRemark());
		 
		   model.addAttribute("brOrDrID",birthRgDetail.getBrId());
		   if(serviceCode.equals(BndConstants.IBC)){
		   model.addAttribute("serviceCode",BndConstants.IBC);
		   }else {
			   model.addAttribute("serviceCode",BndConstants.BRC);
		   }
		   return new ModelAndView("PrintCertificateValidnforBirth",MainetConstants.FORM_NAME, getModel());
	   }else if(serviceCode.equals(BndConstants.IDC)){
		 TbDeathregDTO certificateDetailList = issuenceOfDeathCertificateService.getDeathRegisteredAppliDetail(null,null,null,ApplnId.toString(),UserSession.getCurrent().getOrganisation().getOrgid());
		 TbServiceReceiptMasBean receipt=issuenceOfDeathCertificateService.getReceiptNo(ApplnId,certificateDetailList.getOrgId());
		 if(receipt!=null && receipt.getApmApplicationId()!=null) {
		 certificateDetailList.setReceiptNo(receipt.getRmRcptno());
		 certificateDetailList.setReceiptDate(receipt.getRmDate());
		 if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && StringUtils.isNotBlank(receipt.getRmReceiptNo())) {
			 certificateDetailList.setRmReceiptNo(receipt.getRmReceiptNo());
		 }
		 }
		 certificateDetailList.setDrCertNo(String.valueOf(certiNo));
		 deathRegCorrectionModel.setTbDeathregDTO(certificateDetailList); 
			if (certificateDetailList.getAuthRemark()!=null && certificateDetailList.getAuthRemark().matches("^[0-9a-zA-Z\\\\s.?&!,-;() '\\\"////]*$"))
				deathRegCorrectionModel.setRemark(certificateDetailList.getAuthRemark());
			else
				deathRegCorrectionModel.setRemarkReg(certificateDetailList.getAuthRemark());
		   model.addAttribute("brOrDrID",certificateDetailList.getDrId());
		   model.addAttribute("serviceCode",BndConstants.IDC);
		 return new ModelAndView("PrintCertificateValidnforDeath",MainetConstants.FORM_NAME, getModel());
	   }else if(serviceCode.equals(BndConstants.NBR)){
		   BirthCertificateDTO nacBirthCert = issuenceOfBirthCertificateService.getNacBirthCertdetail(ApplnId, UserSession.getCurrent().getOrganisation().getOrgid());
		   this.getModel().setChildOrDecasedName(nacBirthCert.getBrChildName());
		   this.getModel().setFatherName(nacBirthCert.getPdFathername());
		   this.getModel().setYear(nacBirthCert.getAdOrderNo());
		   this.getModel().setAddress(nacBirthCert.getPdParaddress());
		   this.getModel().setTalukaDesc(nacBirthCert.getApplicantAddress());
		   this.getModel().setDistrictDesc(nacBirthCert.getBrInformantAddr());
		   this.getModel().setStateDesc(nacBirthCert.getBrInformantAddrMar());
		   return new ModelAndView("PrintCertificateForNac",MainetConstants.FORM_NAME, getModel());
	   }else if(serviceCode.equals(BndConstants.NDR)){
		   DeathCertificateDTO nacDeathCert = issuenceOfDeathCertificateService.getNacDeathCertdetail(ApplnId, UserSession.getCurrent().getOrganisation().getOrgid());
		   this.getModel().setChildOrDecasedName(nacDeathCert.getDrDeceasedname());
		   this.getModel().setFatherName(nacDeathCert.getDrRelativeName());
		   this.getModel().setYear(nacDeathCert.getMcOthercond());
		   this.getModel().setAddress(nacDeathCert.getDrDeceasedaddr());
		   this.getModel().setTalukaDesc(nacDeathCert.getApplicantAddress());
		   this.getModel().setDistrictDesc(nacDeathCert.getDrInformantAddr());
		   this.getModel().setStateDesc(nacDeathCert.getDrMarInformantAddr());
		   return new ModelAndView("PrintCertificateForNac",MainetConstants.FORM_NAME, getModel());
		   
	   }
	   else {
		   TbDeathregDTO certificateDetailList = issuenceOfDeathCertificateService.getDeathRegisteredAppliDetail(null,null,null,ApplnId.toString(),UserSession.getCurrent().getOrganisation().getOrgid());
		   TbServiceReceiptMasBean receipt=issuenceOfDeathCertificateService.getReceiptNo(ApplnId,certificateDetailList.getOrgId());
			if(receipt!=null && receipt.getApmApplicationId()!=null) { 
		   certificateDetailList.setReceiptNo(receipt.getRmRcptno());
		   certificateDetailList.setReceiptDate(receipt.getRmDate());
		   if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && StringUtils.isNotBlank(receipt.getRmReceiptNo())) {
				 certificateDetailList.setRmReceiptNo(receipt.getRmReceiptNo());
			 }
			}
		   certificateDetailList.setDrCertNo(String.valueOf(certiNo));
		   //code update for signature on certificate based in registration unit
		   if(certificateDetailList.getCpdRegUnit()!=null && certificateDetailList.getCpdRegUnit()!=0) {
		    LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(certificateDetailList.getCpdRegUnit());
		    certificateDetailList.setPdRegUnitCode(lookup.getLookUpCode());
		   }
		    deathRegCorrectionModel.setTbDeathregDTO(certificateDetailList); 
			 if (certificateDetailList.getAuthRemark()!=null && certificateDetailList.getAuthRemark().matches("^[0-9a-zA-Z\\\\s.?&!,-;() '\\\"////]*$"))
					deathRegCorrectionModel.setRemark(certificateDetailList.getAuthRemark());
				else
					deathRegCorrectionModel.setRemarkReg(certificateDetailList.getAuthRemark());
			   model.addAttribute("brOrDrID",certificateDetailList.getDrId());
			   model.addAttribute("serviceCode",BndConstants.DRC);
			 return new ModelAndView("PrintCertificateValidnforDeath",MainetConstants.FORM_NAME, getModel()); 
		   
	   }
   }


	@RequestMapping(params = "updatePrintStatus", method = RequestMethod.POST,  produces = "Application/JSON")
	 public @ResponseBody Boolean updatePrintStatus(@RequestParam("certificateNo")final String certificateNo,@RequestParam("brOrdrID")final Long brOrdrID,@RequestParam("serviceCode")final String serviceCode,
			 @RequestParam("bdId")final Long bdId,final HttpServletRequest request,final Model model ){
	   getModel().bind(request);
	   Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	   Long noOfCopies = 1l;
	   if(serviceCode.equals(BndConstants.IBC)|| serviceCode.equals(BndConstants.BRC)) {
		   issuenceOfBirthCertificateService.updatNoOfcopyStatus(brOrdrID, orgId,bdId, noOfCopies);
	   }else {
		   iDeathRegistrationService.updatNoOfcopyStatus(brOrdrID, orgId,bdId, noOfCopies);
	   }
	   return true;
	}	
	
	@RequestMapping(params = "updatePrintStatusAfterPrint", method = RequestMethod.POST,  produces = "Application/JSON")
	 public @ResponseBody Boolean updatePrintStatusAfterPrint(@RequestParam("certificateNo")final String certificateNo,@RequestParam("brOrdrID")final Long brOrdrID,@RequestParam("serviceCode")final String serviceCode,
			 @RequestParam("bdId")final Long bdId,final HttpServletRequest request,final Model model ){
	   getModel().bind(request);
	   Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	   Long noOfCopies = 1l;
		   issuenceOfBirthCertificateService.updatPrintStatus(brOrdrID, orgId,bdId, noOfCopies);
	   
	   return true;
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
			boolean generatePdf= printBNDCertificateService.generateCertificate(srcPath,applicationNo,certificateNo);
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
			
			String finaldestPath = (ApplicationSession.getInstance().getMessage("dsc.target")
					+ MainetConstants.operator.DOUBLE_BACKWARD_SLACE + applicationNo + destPath.substring(destPath.lastIndexOf(MainetConstants.operator.DOUBLE_BACKWARD_SLACE))).replace(MainetConstants.operator.DOUBLE_BACKWARD_SLACE, MainetConstants.operator.FORWARD_SLACE);
			 
			LOGGER.info("----------------------------Destination Path>"+ finaldestPath);
			List<DocumentDetailsVO> documentList = new ArrayList<DocumentDetailsVO>();
			DocumentDetailsVO documentList1 = new DocumentDetailsVO();
			FileUploadDTO uploadDTO = new FileUploadDTO();
			uploadDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			uploadDTO.setStatus(MainetConstants.FlagA);
			uploadDTO.setDepartmentName(BndConstants.BIRTH_DEATH);
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


