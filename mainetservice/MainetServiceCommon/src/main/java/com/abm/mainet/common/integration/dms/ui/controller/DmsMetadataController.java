package com.abm.mainet.common.integration.dms.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.domain.DmsDocsMetadata;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IDmsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dms.ui.model.DmsMetadataModel;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping(value = "/DmsMetadata.html")
public class DmsMetadataController extends AbstractEntryFormController<DmsMetadataModel> {

	@Autowired
	private IFileUploadService fileUploadService;
	
	@Autowired
	private IDmsService dmsService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		fileUploadService.sessionCleanUpForFileUpload();
		DmsDocsMetadataDto dmsMetadaDto=new DmsDocsMetadataDto();
		dmsMetadaDto.setUserId(UserSession.getCurrent().getEmployee());
		dmsMetadaDto.setOrgId(UserSession.getCurrent().getOrganisation());
		dmsMetadaDto.setLmodDate(new Date());
		dmsMetadaDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		dmsMetadaDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
		dmsMetadaDto.setStatus(MainetConstants.Council.STATUS_DRAFT);
		dmsMetadaDto=dmsService.saveDms(dmsMetadaDto);
		this.getModel().setDmsId(dmsMetadaDto.getDmsId());
		getModel().setEntity(new DmsDocsMetadata());
		List<LookUp> prefixListNew = new ArrayList<LookUp>();
		String dept = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode();
		List<LookUp> prefixList = CommonMasterUtility.getLevelData(MainetConstants.Dms.MTD, MainetConstants.NUMBERS.ONE,
				UserSession.getCurrent().getOrganisation());
		prefixList.forEach(prefix -> {
			if (prefix.getLookUpCode().equals(dept)) {
				prefixListNew.add(prefix);
			}
		});
		getModel().setDepartmentList(prefixListNew);
		return new ModelAndView("DmsMetadata", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "getMetadata", method = RequestMethod.POST)
	public ModelAndView getMetadata(@RequestParam("deptId") Long deptId, HttpServletRequest request, Model model) {
		//sessionCleanup(request);
		getModel().bind(request);
		fileUploadService.sessionCleanUpForFileUpload();
		getModel().setEntity(new DmsDocsMetadata());
		getModel().setDeptId(deptId);
		getModel().setDmsId(this.getModel().getDmsId());
		List<LookUp> prefixListNew = new ArrayList<LookUp>();
		List<LookUp> prefixListNewDoc = new ArrayList<LookUp>();
		String dept = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode();
		List<LookUp> metadataList = CommonMasterUtility.getChildLookUpsFromParentId(deptId);
		List<LookUp> prefixList = CommonMasterUtility.getLevelData(MainetConstants.Dms.MTD, MainetConstants.NUMBERS.ONE,
				UserSession.getCurrent().getOrganisation());
		LookUp lookUp=CommonMasterUtility.getHierarchicalLookUp(deptId);
		if(lookUp!=null)
		getModel().setDeptCode(lookUp.getLookUpCode());
		String prefixList1 = CommonMasterUtility.getHierarchicalLookUp(deptId).getLookUpCode();
		List<LookUp> atta = CommonMasterUtility.getListLookup(MainetConstants.Dms.DCT,
				UserSession.getCurrent().getOrganisation());
		atta.forEach(prefix -> {
			if (prefix.getOtherField().equals(prefixList1)) {
				prefixListNewDoc.add(prefix);
			}
		});
		getModel().setDocTypeList(prefixListNewDoc);
		prefixList.forEach(prefix -> {
			if (prefix.getLookUpCode().equals(dept)) {
				prefixListNew.add(prefix);
			}
		});
		getModel().setDepartmentList(prefixListNew);
		getModel().setMetadataList(metadataList);
		return new ModelAndView("DmsMetadataValidn", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "fileCountUpload")
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		this.getModel().getFileCountUpload().clear();
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			this.getModel().getFileCountUpload().add(entry.getKey());
		}
		int fileCount = FileUploadUtility.getCurrent().getFileMap().entrySet().size();
		this.getModel().getFileCountUpload().add(fileCount + 1L);
		List<DocumentDetailsVO> attachments = new ArrayList<>();
		for (int i = 0; i <= this.getModel().getAttachments().size(); i++)
			attachments.add(new DocumentDetailsVO());
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			attachments.get(entry.getKey().intValue()).setDoc_DESC_ENGL(
					this.getModel().getAttachments().get(entry.getKey().intValue()).getDoc_DESC_ENGL());
		}
		if (attachments.get(this.getModel().getAttachments().size()).getDoc_DESC_ENGL() == null)
			attachments.get(this.getModel().getAttachments().size()).setDoc_DESC_ENGL(MainetConstants.BLANK);
		else {
			DocumentDetailsVO ob = new DocumentDetailsVO();
			ob.setDoc_DESC_ENGL(MainetConstants.BLANK);
			attachments.add(ob);
		}
		this.getModel().setAttachments(attachments);
		return new ModelAndView("MetadataFileUpload", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "doFileUpload")
	public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
			final HttpServletResponse response, final String fileCode, @RequestParam final String browserType) {
		UserSession.getCurrent().setBrowserType(browserType);
		final MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
		final JsonViewObject jsonViewObject = FileUploadUtility.getCurrent().doDmsFileUpload(request, fileCode,
				browserType);
		return jsonViewObject;
	}

	@RequestMapping(params = "setUrl", method = RequestMethod.POST)
	public @ResponseBody String scanDocumentUrl(final HttpServletRequest request) {
		
		bindModel(request);
		String id=request.getParameter("id");
		this.getModel();
		Organisation org=UserSession.getCurrent().getOrganisation();
		String orgCode=UserSession.getCurrent().getOrganisation().getOrgShortNm();
		String empLoginId=UserSession.getCurrent().getEmployee().getEmploginname();
		String deptCode=this.getModel().getDeptCode();
		long fromId=this.getModel().getDmsId();
		//Map<String, String> dmsServiceMap = new HashMap<String, String>();
		StringBuilder lookupData=new StringBuilder();
		this.getModel().getLookUpList().forEach(data -> {
			if(data.getOtherField()!=null)
			{
				//dmsServiceMap.put(CommonMasterUtility.getHierarchicalLookUp(data.getLookUpId(), org.getOrgid()).getLookUpCode(),data.getOtherField());
				lookupData.append(CommonMasterUtility.getHierarchicalLookUp(data.getLookUpId(), org.getOrgid()).getLookUpCode());
				lookupData.append(MainetConstants.operator.QUOTES);
				lookupData.append(data.getOtherField());
				lookupData.append(MainetConstants.operator.COMMA);
			}
		});
		String dmsDataList=lookupData.toString().substring(0, lookupData.toString().length()-1);
		StringBuilder docType = new StringBuilder();
		for (int i = 0; i < this.getModel().getAttachments().size(); i++) {
			if (this.getModel().getAttachments().get(i).getDoc_DESC_ENGL() != null) {
				docType.append(this.getModel().getAttachments().get(i).getDoc_DESC_ENGL() + MainetConstants.operator.COMMA);
			}
		}
		docType = docType.deleteCharAt((docType.length() - 1));
	//	this.getModel().setAttachments(fileUploadService.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		
		String[] arr = docType.toString().split(MainetConstants.operator.COMMA);
		
		for (int i = 0; i < this.getModel().getAttachments().size(); i++) {
			LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(arr[i]), org);
			this.getModel().getAttachments().get(i).setDescriptionType(lookup.getLookUpCode());
		}

		//List<Map<String, String>> attachmentList = new ArrayList<Map<String, String>>();
		StringBuilder docData=new StringBuilder();
		//this.getModel().getAttachments().forEach(data1 -> {
			//Map<String, String> dmsServiceMap1 = new HashMap<String, String>();
			//dmsServiceMap1.put("descLangFirst",data1.getDocumentName());
			//dmsServiceMap1.put("lookUpType",data1.getDescriptionType());
			//attachmentList.add(dmsServiceMap1);
			
			docData.append("descLangFirst");
			docData.append(MainetConstants.operator.QUOTES);
			docData.append(this.getModel().getAttachments().get(Integer.parseInt(id)).getDocumentName());
			docData.append(MainetConstants.operator.COMMA);
			docData.append("lookUpType");
			docData.append(MainetConstants.operator.QUOTES);
			docData.append(this.getModel().getAttachments().get(Integer.parseInt(id)).getDescriptionType());
			//docData.append(MainetConstants.operator.COMMA);
		//});
		String docDataList=docData.toString();
		String url = ServiceEndpoints.DMS_SCAN_URL + "orgCode=" + orgCode + "&empLoginId="
				+ empLoginId + "&deptCode=" + deptCode + "&dmsMetadataList=" + dmsDataList + "&attachmentList="
				+ docDataList + "&formId=" +fromId;
		return url;

	}
}
