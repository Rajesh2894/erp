/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.sfac.dto.EquityGrantDetailDto;
import com.abm.mainet.sfac.dto.EquityGrantMasterDto;
import com.abm.mainet.sfac.dto.FPOAdministrativeDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.service.EquityGrantRequestMasterService;
import com.abm.mainet.sfac.ui.model.EquityGrantRequestModel;


/**
 * @author Priyesh.Chourasia
 *
 */
@RequestMapping(MainetConstants.Sfac.EQUITY_GRANT_REQUEST_HTML)
@Controller
public class EquityGrantRequestController extends AbstractFormController<EquityGrantRequestModel>{

	private static final Logger log = Logger.getLogger(EquityGrantRequestController.class);

	@Autowired EquityGrantRequestMasterService equityGrantRequestMasterService;

	@Autowired
	private IOrganisationService orgService;


	@Autowired
	private DesignationService designationService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private IAttachDocsService attachDocsService;


	@RequestMapping(method = { RequestMethod.POST,RequestMethod.GET})
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		populateModel();
		return new ModelAndView(MainetConstants.Sfac.EQUITY_GRANT_REQUEST_SUMMARY_FORM,MainetConstants.FORM_NAME,getModel());
	}



	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		populateModel();
		this.getModel().setViewMode(MainetConstants.FlagA);
		this.getModel().setChecklistFlag(MainetConstants.FlagN);

		return new ModelAndView(MainetConstants.Sfac.EQUITY_GRANT_REQUEST, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchFpoManagementCostDetails(Long fpoId, String status ,final HttpServletRequest httpServletRequest) {
		List<EquityGrantMasterDto> DtoList = equityGrantRequestMasterService.getAppliacationDetails(fpoId, status);
		this.getModel().setEquityGrantMasterDtos(DtoList);

		return new ModelAndView(MainetConstants.Sfac.EQUITY_GRANT_REQUEST_SUMMARY_VALIDN, MainetConstants.FORM_NAME, getModel());
	}


	@RequestMapping(method = RequestMethod.POST, params = "getCheckList")
	public ModelAndView get(final HttpServletRequest request) {
		this.getModel().bind(request);
		EquityGrantMasterDto masDto = this.getModel().getDto();
		EquityGrantRequestModel model = this.getModel();
		// Defect #120380

		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgId(orgid);
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
				"EGR",
				orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid());
		model.setServiceMaster(sm);


		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify(),
				UserSession.getCurrent().getOrganisation());
		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setChecklistFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setChecklistFlag(MainetConstants.FlagN);
		}

		if (model.getChecklistFlag().equals("Y")) {

			model.getCheckListFromBrms();
		}




		return new ModelAndView(MainetConstants.Sfac.EQUITY_GRANT_REQUEST, MainetConstants.FORM_NAME, getModel());

	}

	@RequestMapping(params = "saveEquityGrantRequest", method = RequestMethod.POST)
	public ModelAndView saveEquityGrantRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		EquityGrantRequestModel model = this.getModel();
		List<DocumentDetailsVO> docs = model.getCheckList();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		model.getDto().setDocumentList(docs);
		fileUpload.validateUpload(model.getBindingResult());



		if (model.saveForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

		} else
			return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);


	}



	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long egId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
				"EGR", orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid()
				);
		this.getModel().setServiceMaster(sm);
		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify(),
				orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO));
		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setChecklistFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setChecklistFlag(MainetConstants.FlagN);
		}

		if (this.getModel().getChecklistFlag().equals("Y")) {

			this.getModel().getCheckListFromBrms();
		}




		EquityGrantMasterDto dto = equityGrantRequestMasterService.getDetailById(egId);
		dto.setContactNo(dto.getMobileNo());
		List<CFCAttachment>	documentList = checklistVerificationService.getDocumentUploaded(Long.valueOf(dto.getAppNumber()),
				orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO).getOrgid());
		if(CollectionUtils.isNotEmpty(documentList)) {
			this.getModel().setDocumentList(documentList);
			FileUploadUtility.getCurrent().setFileMap(
					getUploadedFileList(documentList, FileNetApplicationClient.getInstance()));
		}
		this.getModel()
		.setAttachDocsList(attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),"EGR" + MainetConstants.FILE_PATH_SEPARATOR + dto.getAppNumber()));

		dto.setEgId(egId);
		this.getModel().setDto(dto);
		return new ModelAndView(MainetConstants.Sfac.EQUITY_GRANT_REQUEST, MainetConstants.FORM_NAME, getModel());
	}

	private Map<Long, Set<File>> getUploadedFileList(List<CFCAttachment> documentList,
			FileNetApplicationClient fileNetApplicationClient) {
		Set<File> fileList = null;

		Long x = 0L;
		Map<Long, Set<File>> fileMap = new HashMap<>();
			for (CFCAttachment doc : documentList) {
				fileList = new HashSet<>();
				final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
						+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;
				String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();
				final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,
						existingPath);

				String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,
						existingPath);

				directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMMA);
				FileOutputStream fos = null;
				File file = null;
				try {
					final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

					Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);

					file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

					fos = new FileOutputStream(file);

					fos.write(image);

					fos.close();

				} catch (final Exception e) {
					throw new FrameworkException("Exception in getting getUploadedFileList", e);
				} finally {
					try {

						if (fos != null) {
							fos.close();
						}

					} catch (final IOException e) {
						throw new FrameworkException("Exception in getting getUploadedFileList", e);
					}
				}
				fileList.add(file);
				fileMap.put(x, fileList);
				x++;
			}
		

		return fileMap;
	}



	private void populateModel() {
		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setStateList(stateList);
		List<DesignationBean> designationBeans = designationService.findAll();
		this.getModel().setDesignlist(designationBeans);
		Long bodId = 0l;
		FPOMasterDto fpoMasterDto = equityGrantRequestMasterService.getFPODetails(UserSession.getCurrent().getEmployee().getMasId());
		EquityGrantMasterDto equityGrantMasterDto = new EquityGrantMasterDto();
		for(DesignationBean designationBean : designationBeans) {
			if(designationBean.getDsgname().equalsIgnoreCase("BOD"))
				bodId = designationBean.getDsgid();
				
		}
		List<EquityGrantDetailDto> equityGrantDetailDtos = new ArrayList<EquityGrantDetailDto>();
		List<EquityGrantDetailDto> equityGrantDetailDtosBOM = new ArrayList<EquityGrantDetailDto>();
		for(FPOAdministrativeDto fpoAdministrativeDto : fpoMasterDto.getFpoAdministrativeDto()) {
			if(fpoAdministrativeDto.getDsgId()==bodId) {
			EquityGrantDetailDto equityGrantDetailDto = new EquityGrantDetailDto();
			equityGrantDetailDto.setName(fpoAdministrativeDto.getName());
			equityGrantDetailDto.setRole(fpoAdministrativeDto.getDsgId().toString());
			equityGrantDetailDto.setContactNoAddress(fpoAdministrativeDto.getContactNo());
			
			equityGrantDetailDtos.add(equityGrantDetailDto);
			}else {
				EquityGrantDetailDto equityGrantDetailDto = new EquityGrantDetailDto();
				equityGrantDetailDto.setName(fpoAdministrativeDto.getName());
				equityGrantDetailDto.setRole(fpoAdministrativeDto.getDsgId().toString());
				equityGrantDetailDto.setContactNoAddress(fpoAdministrativeDto.getContactNo());
				
				equityGrantDetailDtosBOM.add(equityGrantDetailDto);	
			}

		}

		//this.getModel().setBankName(bankMasterRepository.findOne(fpoMasterDto.getBankName()).getBank());
		equityGrantMasterDto.setEquityGrantDetailDto(equityGrantDetailDtos);
		equityGrantMasterDto.setEquityGrantDetailDtoBOM(equityGrantDetailDtosBOM);
		equityGrantMasterDto.setMobileNo(UserSession.getCurrent().getEmployee().getEmpmobno());
		equityGrantMasterDto.setEmailId(UserSession.getCurrent().getEmployee().getEmpemail());
		equityGrantMasterDto.setContactNo(UserSession.getCurrent().getEmployee().getEmpmobno());
		equityGrantMasterDto.setFpoMasterDto(fpoMasterDto);
		this.getModel().setViewMode(MainetConstants.FlagA);
		this.getModel().setDto(equityGrantMasterDto);
	}


	@RequestMapping(params = "getDistrictList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getDistrictListByStateId(@RequestParam("state") Long state, HttpServletRequest request) {
		List<LookUp> lookUpList1 = new java.util.ArrayList<LookUp>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 2,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == state)
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			log.error("SDB Prefix not found");
			return lookUpList1;

		}
	}
}
